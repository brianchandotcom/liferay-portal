package com.liferay.jenkins.load.balancer;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.Project;
import org.json.JSONArray;
import org.json.JSONObject;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

public class LoadBalanceUtil {
	
	public static String getMostAvailableMasterURL(Project project)
			throws Exception {
		String baseInvocationUrl = project.getProperty("base.invocation.url");
		String hostNamePrefix = null;
		
		Matcher matcher = _urlPattern.matcher(baseInvocationUrl);
		if (!matcher.find()) {
			return baseInvocationUrl;
		}
		else {
			hostNamePrefix = matcher.group("hostNamePrefix");
		}
		System.out.println("hostNamePrefix: " + hostNamePrefix);
		
		int maxHostNames = calculateMaxHostNames(project, hostNamePrefix);
		
		File file = new File(
			project.getProperty(
				"jenkins.shared.dir") + "/" + hostNamePrefix + ".semaphore");

		waitForTurn(file, maxHostNames);

		JenkinsResultsParserUtil.write(file,  HOSTNAME);

		try {
			ExecutorService executor = Executors.newFixedThreadPool(20);
			List<String> hostNameList = new ArrayList<>(maxHostNames);
			List<FutureTask<Integer>> taskList = new ArrayList<>(maxHostNames);		
	
			for (int i = 1; i <= maxHostNames; i++) {
				String hostName = hostNamePrefix + "-" + i;
	
				hostNameList.add(hostName);
	
				IdleSlaveCounterCallable callable =
					new IdleSlaveCounterCallable(project.getProperty(
						"jenkins.local.url[" + hostName + "]") +
						"/computer/api/json");
	
				FutureTask<Integer> futureTask = new FutureTask<>(callable);
	
				executor.execute(futureTask);
				
				taskList.add(futureTask);
			}
			
			executor.shutdown();
	
			List<Integer> maxIndexes = new ArrayList<>(taskList.size());
			int max = 0;
			for (int i = 0; i < taskList.size(); i++) {
				FutureTask<Integer> task = taskList.get(i);
				try {
					Integer result = task.get();
					if (result > max) {
						max = result;
						maxIndexes.clear();
					}
					if (result >= max) {
						maxIndexes.add(i);
					}
				}
				catch (ExecutionException ee) {
					throw new RuntimeException(ee);
				}
				catch (InterruptedException ie) {
					throw new RuntimeException(ie);
				}
			}
			int x = -1;
			
			if (maxIndexes.size() > 0) {
				x = maxIndexes.get(getRandomValue(
					0, maxIndexes.size() - 1));
			} else {
				x = getRandomValue(0, maxHostNames);
			}

			return hostNameList.get(x);
		} finally {
			JenkinsResultsParserUtil.write(file,  "");
		}
	}
	
	private static int calculateMaxHostNames(Project project, String hostNamePrefix) {
		int i=1;
		while (true) {
			String propertyValue =
				project.getProperty(
					"jenkins.local.url[" + hostNamePrefix + "-" + i + "]");

			if (propertyValue != null && propertyValue.length() > 0) {
				i++;
				
				continue;
			}
			
			return i - 1;
		}
	}
	
	private static int getRandomValue(int start, int end) {
		
		int size = Math.abs(end - start);
		
		double randomDouble = Math.random();
		
		return start + (int)Math.round(size * randomDouble);
	}
	
	private static class IdleSlaveCounterCallable implements Callable<Integer> {
		
		private IdleSlaveCounterCallable(String url) {
			this.url = url;
		}
		

		@Override
		public Integer call() throws Exception {
			
			JSONObject jsonObject =
				JenkinsResultsParserUtil.toJSONObject(url, false);

			JSONArray jsonArray = jsonObject.getJSONArray("computer");
			
			int idle = 0;
			for (int i = 0; i < jsonArray.length();  i++) {
				JSONObject idleJsonObject = jsonArray.getJSONObject(i);

				if (idleJsonObject.getBoolean("idle")) {
					idle++;
				}
			}
			return idle;
			
		}
		
		private String url;
		
	}
	
	private static Project getProject() {
		Project project = new Project();
		
		project.setProperty("base.invocation.url", "http://test-1.liferay.com");
		project.setProperty("jenkins.shared.dir",
			"mnt/mfs-ssd1-10.10/jenkins");
		project.setProperty("jenkins.local.url[test-1-1]", "http://test-1-1");
		project.setProperty("jenkins.local.url[test-1-2]", "http://test-1-2");
		project.setProperty("jenkins.local.url[test-1-3]", "http://test-1-3");
		project.setProperty("jenkins.local.url[test-1-4]", "http://test-1-4");
		project.setProperty("jenkins.local.url[test-1-5]", "http://test-1-5");
		project.setProperty("jenkins.local.url[test-1-6]", "http://test-1-6");
		project.setProperty("jenkins.local.url[test-1-7]", "http://test-1-7");
		project.setProperty("jenkins.local.url[test-1-8]", "http://test-1-8");
		project.setProperty("jenkins.local.url[test-1-9]", "http://test-1-9");
		project.setProperty("jenkins.local.url[test-1-10]", "http://test-1-10");
		project.setProperty("jenkins.local.url[test-1-11]", "http://test-1-11");
		project.setProperty("jenkins.local.url[test-1-12]", "http://test-1-12");
		project.setProperty("jenkins.local.url[test-1-13]", "http://test-1-13");
		project.setProperty("jenkins.local.url[test-1-14]", "http://test-1-14");
		project.setProperty("jenkins.local.url[test-1-15]", "http://test-1-15");
		project.setProperty("jenkins.local.url[test-1-16]", "http://test-1-16");
		project.setProperty("jenkins.local.url[test-1-17]", "http://test-1-17");
		project.setProperty("jenkins.local.url[test-1-18]", "http://test-1-18");
		project.setProperty("jenkins.local.url[test-1-19]", "http://test-1-19");
		project.setProperty("jenkins.local.url[test-1-20]", "http://test-1-20");

		return project;
	}
	
	private static void waitForTurn(File file, int maxHostNames) throws Exception {
		boolean bypass = false;

		while(true) {
			if (!file.exists()) {
				JenkinsResultsParserUtil.write(file, "");
				bypass = true;
			}
			
			long age = System.currentTimeMillis() - file.lastModified();
			String content = JenkinsResultsParserUtil.read(file);
			
			if (!bypass && (age < MIN_RUN_INTERVAL ||
				(content.length() > 0 && age < MAX_AGE))) {
				
				long sleepPeriod = MIN_RUN_INTERVAL - age +
					(getRandomValue(0, maxHostNames) * 10);
				
				System.out.println("Waiting " + sleepPeriod + " milliseconds.");
			
				Thread.sleep(sleepPeriod);

				continue;
			}
			return;
		}
		
	}
	
	private static final long MIN_RUN_INTERVAL = 15 * 1000;
	private static final String HOSTNAME;
	static {
		InetAddress inetAddress = null;
		String hostName = null;
		try {
			inetAddress = InetAddress.getLocalHost();
			hostName = inetAddress.getHostName();
		} catch (UnknownHostException uhe) {
			hostName = "UNKNOWN";
		}
		HOSTNAME = hostName;
	}
	private static final long MAX_AGE = 30 * 1000;
	
	private static final Pattern _urlPattern = Pattern.compile(
		"http://(?<hostNamePrefix>[\\S&&[^-]]+-\\d+).liferay.com");
	
	public static void main(String[] args) {
		try {
			System.out.println(
				"Most available master: " + 
					getMostAvailableMasterURL(getProject()));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
