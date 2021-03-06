package com.sharpwisdom.third.duplicateNumber;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Created by wuys on 2016/8/16.
 */
public class JobMain {

    public static void main(String[] args) throws Exception{
        Configuration configuration = new Configuration();
        Job job = new Job(configuration,"duplicateNumber-job");
        job.setJarByClass(JobMain.class);

        job.setMapperClass(DuplicateNumberMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(DuplicateNumberReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.addInputPath(job,new Path(args[0]));
        Path outputDir = new Path(args[1]);
        FileSystem fs = FileSystem.get(configuration);
        if(fs.exists(outputDir)){
            fs.delete(outputDir,true);
        }
        FileOutputFormat.setOutputPath(job,outputDir);
        job.setNumReduceTasks(1);
        System.exit(job.waitForCompletion(true)?0:1);
    }
}
