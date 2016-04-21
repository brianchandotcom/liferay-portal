package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlaveListGeneratorUtil {
	
	private static Integer tasksCompleteCount = 0;
	
	public static List<String> getSlaveList(String masterName, String propertiesURL, boolean verify) {
		Properties properties = new Properties();
		try {
			properties.load(new StringReader(
				JenkinsResultsParserUtil.toString(
					JenkinsResultsParserUtil.getLocalURL(propertiesURL))));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		List<String> slaves = new ArrayList<>();

		String key = "master.slaves(" + masterName + ")";

		if (properties.containsKey(key)) {
			String value = properties.getProperty(key);
			for (String token : value.split(",")) {
				if (token.contains("..")) {
					slaves.addAll(_expand(token));
					continue;
				}
				slaves.add(token);
			}
		}
		
		if (verify) {
			try {
				Map<String, FutureTask<Boolean>> validationTasks = _startParallelTasks(slaves);
				List<String> badSlaves = new ArrayList<>(slaves.size());

				int timeoutCount = 0;

				for (String slave : slaves) {
					FutureTask<Boolean> futureTask = validationTasks.get(slave);

					try {
						if (!futureTask.get(30, TimeUnit.SECONDS)) {
							badSlaves.add(slave);
						}
					} catch(TimeoutException te) {
						timeoutCount++;
						badSlaves.add(slave);
					}
				}

				System.out.println("timeout count: " + timeoutCount);
				System.out.println(badSlaves.size() + " Bad Slaves: \n" + badSlaves);

				slaves.removeAll(badSlaves);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return slaves;
	}
	
	public static void main(String[] args) {
		List<String> slaves = getSlaveList("test-1-1", 
			"http://mirrors-no-cache.lax.liferay.com/github.com/liferay/liferay-jenkins-ee/build.properties",
			true);
		
		System.out.println("Total number of slaves: " + slaves.size());

		Collections.sort(slaves);
		
		System.out.println(slaves);		
	}
	
	public static class SlaveVerifier implements Callable<Boolean> {
		
		public SlaveVerifier(String slaveName) {
			_slaveName = slaveName;
		}

		@Override
		public Boolean call() throws Exception {
			//System.out.println("Verifying: " + _slaveName);
			//ProcessBuilder pb = new ProcessBuilder("ping", "-c", "1", _slaveName);
			//ProcessBuilder pb = new ProcessBuilder("ssh", _slaveName, "'ls /'");
			//ProcessBuilder pb = new ProcessBuilder("echo", _slaveName);
			//System.out.println("Executing command: " + pb.command());
			//Process process = pb.start();
			//System.out.println(_getOutput(process));
			String fileName = _slaveName + ".sh";
			File shellFile = new File(fileName);
			

			try {
				try (FileWriter shellFileWriter = new FileWriter(shellFile)) {
					shellFileWriter.write("echo " + _slaveName + "\n");
//					shellFileWriter.write("ping -c 1 " + _slaveName + "\n");
					shellFileWriter.write("ssh " + _slaveName + " 'ls /'\n");
				}

				shellFile.setExecutable(true);
				
				boolean shellFileRan = false;
				Integer exitValue = null;
				while (!shellFileRan) {
					Process process = null;
					long start = System.currentTimeMillis();
					try {
						process = Runtime.getRuntime().exec("./" + fileName);
					}
					catch (IOException ioe) {
						if (ioe.getMessage().contains("Text file busy") && (System.currentTimeMillis() - start) < 2000) {
							Thread.sleep(100);
							continue;
						}
						throw ioe;
					}
					shellFileRan = true;
	
					String output = _getOutput(process);
					String errorOutput = _getErrorOutput(process);
					
					if (errorOutput.contains("No buffer space available") || errorOutput.contains("Temporary failure in name resolution")) {
						
						synchronized(tasksCompleteCount) {
							if (tasksCompleteCount > 0) {
								System.out.println(tasksCompleteCount + " tasks have been completed.");
							}
						}
						shellFileRan = false;
						
						System.out.println("Out of buffer space OR Temporary DNS failure - Retry in 1 minutes.");
						Thread.sleep(1000 * 60);
						
						continue;
					}
					exitValue = process.waitFor();
					
					process.destroy();
					
					synchronized(tasksCompleteCount) {
						tasksCompleteCount++;
					}
	
					if (exitValue != 0) {
						System.out.println("Exit Value: " + exitValue + "\n" + output + "\n\n" + errorOutput);
					}
				}
				if (exitValue == null) {
					return false;
				}
				
				return exitValue == 0;
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			finally {
				shellFile.deleteOnExit();
			}
			
			
		}
		
		private String _slaveName;
		
		private static String _getOutput(Process process) {
			try {
				InputStream is = process.getInputStream();
				byte[] inputBytes = new byte[1024];
				int size = is.read(inputBytes);
				StringBuffer sb = new StringBuffer();
				while (size > 0) {
					sb.append(new String(Arrays.copyOf(inputBytes, size)));
					size = is.read(inputBytes);
				}
				String output = sb.toString();
				
				return output.trim();
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}
		
		private static String _getErrorOutput(Process process) {
			try {
				InputStream is = process.getErrorStream();
				byte[] inputBytes = new byte[1024];
				int size = is.read(inputBytes);
				StringBuffer sb = new StringBuffer();
				while (size > 0) {
					sb.append(new String(Arrays.copyOf(inputBytes, size)));
					size = is.read(inputBytes);
				}
				String output = sb.toString();
				
				return output.trim();
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}
	}

	private static List<String> _expand(String token) {
		Pattern tokenPattern = Pattern.compile(
			"(?<prefix>cloud-\\d+-\\d+-\\d+-)(?<min>\\d+)\\.\\.(?<max>\\d+)");
		Matcher matcher = tokenPattern.matcher(token);

		if (matcher.find()) {
			int max = Integer.parseInt(matcher.group("max"));
			int min = Integer.parseInt(matcher.group("min"));
			String prefix = matcher.group("prefix");
			
			List<String> expandList = new ArrayList<>(max - min + 1);
			for (int i = min; i <= max; i++) {
				expandList.add(prefix + i);
			}
			return expandList;
		}
		return new ArrayList<String>(0);
	}
	
	private static Map<String, FutureTask<Boolean>> _startParallelTasks(List<String> slaves)
	throws Exception {
		
		tasksCompleteCount = 0;

		ExecutorService executorService = Executors.newFixedThreadPool(
			20);
		
		Map<String, FutureTask<Boolean>> futureTasksMap = new HashMap<>(slaves.size());
	
		for (String slave : slaves) {
			FutureTask<Boolean> futureTask = new FutureTask<>(
				new SlaveVerifier(slave));
	
			executorService.execute(futureTask);
	
			futureTasksMap.put(slave, futureTask);
		}
	
		executorService.shutdown();
		
		return futureTasksMap;
	}
}
