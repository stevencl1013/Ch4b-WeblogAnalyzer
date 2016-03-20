/**
 * Read web server data and analyse
 * hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version 2011.07.31
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private int[] dailyCounts;
    private int[] monthlyCounts;
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String fileName)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dailyCounts = new int[32];
        monthlyCounts = new int[13];
        // Create the reader to obtain the data.
        reader = new LogfileReader(fileName);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    public void analyzeDailyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dailyCounts[day]++;
        }
    }
    
    public void analyzeMonthlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int month = entry.getMonth();
            monthlyCounts[month]++;
        }
    }
    
    public int numberOfAccesses()
    {
        int total = 0;
        for(int i = 0; i < hourCounts.length; i++)
        {  
            total += hourCounts[i];
        }
        return total;
    }
    
    public int busiestHour()
    {
        int busiest = 0;
        for(int i = 1; i < hourCounts.length; i++)
        {
            if(hourCounts[i] > hourCounts[busiest])
                busiest = i;
        }
        return busiest;
    }
    
    public int quietestHour()
    {
        int quietest = 0;
        for(int i = 1; i < hourCounts.length; i++)
        {
            if(hourCounts[i] < hourCounts[quietest])
                quietest = i;
        }
        return quietest;
    }
    
    public int busiestTwoHours()
    {
        int twoHourCounts[] = new int[24];
        int busiest = 0;
        for(int i = 0; i < 24; i++)
        {
            twoHourCounts[i] = hourCounts[i]+hourCounts[(i+1) % 24];
            if(twoHourCounts[i] > twoHourCounts[busiest])
                busiest = i;
        }
        return busiest;
    }
    
    public int quietestDay()
    {
        int quietest = 1;
        for(int i = 1; i < dailyCounts.length; i++)
        {
            if(dailyCounts[i] < dailyCounts[quietest])
                quietest = i;
        }
        return quietest;
    }
    
    public int busiestDay()
    {
        int busiest = 1;
        for(int i = 1; i < dailyCounts.length; i++)
        {
            System.out.println(dailyCounts[i]);
            if(dailyCounts[i] > dailyCounts[busiest])
                busiest = i;
        }
        return busiest;
    }
    
    public int totalAccessesPerMonth()
    {
        int numMonths = 0;
        int totalAccesses = 0;
        for(int i = 1; i <= 12; i++)
        {
            totalAccesses += monthlyCounts[i];
            if(monthlyCounts[i] > 0)
                numMonths++;
        }
        return totalAccesses/numMonths;
    }
    
    public int quietestMonth()
    {
        int quietest = 1;
        for(int i = 1; i <= 12; i++)
        {
            if(monthlyCounts[i] < monthlyCounts[quietest])
                quietest = i;
        }
        return quietest;
    }
    
    public int busiestMonth()
    {
        int busiest = 1;
        for(int i = 1; i <= 12; i++)
        {
            if(monthlyCounts[i] > monthlyCounts[busiest])
                busiest = i;
        }
        return busiest;
    }
   
}
