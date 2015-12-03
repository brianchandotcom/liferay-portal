/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.load.balance;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.Project;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class LoadBalanceUtil {

	public static String getMostAvailableSlaveMasterURL(Project project)
		throws Exception {

		int retryCount = 0;

		while (true) {
			String baseInvocationURL = project.getProperty(
				"base.invocation.url");

			String hostNamePrefix = getHostNamePrefix(baseInvocationURL);

			if (hostNamePrefix.equals(baseInvocationURL)) {
				return baseInvocationURL;
			}

			int hostNameCount = getHostNameCount(project, hostNamePrefix);

			File baseDir = new File(
				project.getProperty("jenkins.shared.dir") + "/" +
					hostNamePrefix);

			File semaphoreFile = new File(
				baseDir, hostNamePrefix + ".semaphore");

			waitForTurn(semaphoreFile, hostNameCount);

			JenkinsResultsParserUtil.write(semaphoreFile, _MY_HOST_NAME);

			Map<String, Integer> recentJobMap = getRecentJobCountMap(
				new File(baseDir, "recentJob"));

			List<String> hostNames = new ArrayList<>(hostNameCount);
			int maxAvailableSlaveCount = Integer.MIN_VALUE;
			int x = -1;

			try {
				List<FutureTask<Integer>> futureTasks = new ArrayList<>(
					hostNameCount);

				startParallelTasks(
					recentJobMap, hostNames, hostNamePrefix, hostNameCount,
					project, futureTasks);

				List<Integer> badIndices = new ArrayList<>(futureTasks.size());
				List<Integer> maxIndices = new ArrayList<>(futureTasks.size());

				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < futureTasks.size(); i++) {
					FutureTask<Integer> futureTask = futureTasks.get(i);

					Integer availableSlaveCount = futureTask.get();

					if (availableSlaveCount == null) {
						badIndices.add(i);

						continue;
					}

					sb.append(hostNames.get(i));
					sb.append(" : ");
					sb.append(availableSlaveCount);
					sb.append("\n");

					if (availableSlaveCount > maxAvailableSlaveCount) {
						maxAvailableSlaveCount = availableSlaveCount;

						maxIndices.clear();
					}

					if (availableSlaveCount >= maxAvailableSlaveCount) {
						maxIndices.add(i);
					}
				}

				if (maxAvailableSlaveCount == Integer.MIN_VALUE) {
					if (retryCount == 3) {
						throw new RuntimeException(
							"Retry limit exceeded. Unable to communicate " +
								" with masters.");
					}

					retryCount++;

					continue;
				}

				if (maxIndices.size() > 0) {
					x = maxIndices.get(
						getRandomValue(0, maxIndices.size() - 1));
				}
				else {
					while (true) {
						x = getRandomValue(0, hostNameCount - 1);

						if (badIndices.contains(x)) {
							continue;
						}

						break;
					}
				}

				sb.append("\nMaster with the most available slaves ");
				sb.append(hostNames.get(x));
				sb.append(" has ");
				sb.append(maxAvailableSlaveCount);
				sb.append(" available slaves.");

				System.out.println(sb);

				return hostNames.get(x);
			}
			finally {
				JenkinsResultsParserUtil.write(semaphoreFile, "");

				if (recentJobPeriod > 0) {
					StringBuilder sb = new StringBuilder();

					File recentJobFile = new File(
						baseDir, "recentJob/" + hostNames.get(x));

					if (recentJobFile.exists()) {
						sb.append(JenkinsResultsParserUtil.read(recentJobFile));

						if (sb.length() > 0) {
							sb.append("|");
						}
					}

					String invokedJobBatchSize = project.getProperty(
						"invoked.job.batch.size");

					if ((invokedJobBatchSize == null) ||
						(invokedJobBatchSize.length() == 0)) {

						invokedJobBatchSize = "1";
					}

					sb.append(invokedJobBatchSize);
					sb.append("-");
					sb.append(System.currentTimeMillis());

					JenkinsResultsParserUtil.write(
						recentJobFile, sb.toString());
				}
			}
		}
	}

	protected static int getHostNameCount(
		Project project, String hostNamePrefix) {

		int i = 1;

		while (true) {
			String jenkinsLocalURL = project.getProperty(
				"jenkins.local.url[" + hostNamePrefix + "-" + i + "]");

			if ((jenkinsLocalURL != null) && (jenkinsLocalURL.length() > 0)) {
				i++;

				continue;
			}

			return i - 1;
		}
	}

	protected static String getHostNamePrefix(String baseInvocationURL) {
		Matcher matcher = _urlPattern.matcher(baseInvocationURL);

		if (!matcher.find()) {
			return baseInvocationURL;
		}

		return matcher.group("hostNamePrefix");
	}

	protected static int getRandomValue(int start, int end) {
		int size = Math.abs(end - start);

		double randomDouble = Math.random();

		return start + (int)Math.round(size * randomDouble);
	}

	protected static Map<String, Integer> getRecentJobCountMap(File dir)
		throws Exception {

		Map<String, Integer> jobCountMap = new HashMap<>();

		if (!dir.exists()) {
			return jobCountMap;
		}

		for (File file : dir.listFiles()) {
			if ((System.currentTimeMillis() - file.lastModified()) >
					recentJobPeriod) {

				file.delete();

				continue;
			}

			try {
				String content = JenkinsResultsParserUtil.read(file);

				if (content.length() == 0) {
					continue;
				}

				int totalJobCount = 0;
				StringBuilder sb = new StringBuilder();

				for (String jobCountData : content.split("\\|")) {
					int x = jobCountData.indexOf("-");

					int jobCount = Integer.parseInt(
						jobCountData.substring(0, x));
					long timestamp = Long.parseLong(
						jobCountData.substring(x + 1));

					if ((timestamp + recentJobPeriod) >
							System.currentTimeMillis()) {

						if (sb.length() > 0) {
							sb.append("|");
						}

						sb.append(jobCountData);
						totalJobCount += jobCount;
					}
				}

				jobCountMap.put(file.getName(), totalJobCount);

				if (sb.length() > 0) {
					JenkinsResultsParserUtil.write(file, sb.toString());
				}
				else {
					file.delete();
				}
			}
			catch (Exception e) {
				file.delete();
			}
		}

		return jobCountMap;
	}

	protected static void startParallelTasks(
			Map<String, Integer> recentJobMap, List<String> hostNames,
			String hostNamePrefix, int hostNameCount, Project project,
			List<FutureTask<Integer>> futureTasks)
		throws Exception {

		ExecutorService executorService = Executors.newFixedThreadPool(
			hostNameCount);

		for (int i = 1; i <= hostNameCount; i++) {
			String targetHostName = hostNamePrefix + "-" + i;

			hostNames.add(targetHostName);

			FutureTask<Integer> futureTask = new FutureTask<>(
				new AvailableSlaveCallable(
					recentJobMap.get(targetHostName),
					project.getProperty(
						"jenkins.local.url[" + targetHostName + "]")));

			executorService.execute(futureTask);

			futureTasks.add(futureTask);
		}

		executorService.shutdown();
	}

	protected static void waitForTurn(File file, int hostNameCount)
		throws Exception {

		while (true) {
			if (!file.exists()) {
				JenkinsResultsParserUtil.write(file, "");
				return;
			}

			long age = System.currentTimeMillis() - file.lastModified();
			String content = JenkinsResultsParserUtil.read(file);

			if ((age < _MAX_AGE) && (content.length() > 0)) {
				Thread.sleep(1000);

				continue;
			}

			return;
		}
	}

	protected static long recentJobPeriod = 120 * 1000;

	private static final long _MAX_AGE = 30 * 1000;

	private static final String _MY_HOST_NAME;

	private static final Pattern _urlPattern = Pattern.compile(
		"http://(?<hostNamePrefix>.+-\\d?).liferay.com");

	static {
		String inetHostName = null;

		try {
			InetAddress inetAddress = InetAddress.getLocalHost();

			inetHostName = inetAddress.getHostName();
		}
		catch (UnknownHostException uhe) {
			inetHostName = "UNKNOWN";
		}

		_MY_HOST_NAME = inetHostName;
	}

	private static class AvailableSlaveCallable implements Callable<Integer> {

		@Override
		public Integer call() throws Exception {
			JSONObject computerJSONObject = null;
			JSONObject queueJSONObject = null;

			try {
				computerJSONObject = JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(
						url + "/computer/api/json?pretty&tree=computer" +
						"[displayName,idle,offline]"),
					false, 5000);
				queueJSONObject = JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(
						url + "/queue/api/json?pretty"),
					false, 5000);
			}
			catch (Exception e) {
				System.out.println("Unable to read " + url);

				return null;
			}

			JSONArray computers = computerJSONObject.getJSONArray("computer");

			int idleCount = 0;

			for (int i = 0; i < computers.length(); i++) {
				JSONObject idle = computers.getJSONObject(i);

				if (idle.getBoolean("idle") && !idle.getBoolean("offline")) {
					String displayName = idle.getString("displayName");

					if (!displayName.equals("master")) {
						idleCount++;
					}
				}
			}

			int queueCount = 0;

			if (queueJSONObject.has("items")) {
				JSONArray items = queueJSONObject.getJSONArray("items");

				queueCount = items.length();
			}

			int availableSlaveCount = idleCount - queueCount;

			if (recentJobCount != null) {
				availableSlaveCount -= recentJobCount;
			}

			StringBuilder sb = new StringBuilder();

			sb.append("{available=");
			sb.append(availableSlaveCount);
			sb.append(", idle=");
			sb.append(idleCount);
			sb.append(", queue=");
			sb.append(queueCount);
			sb.append(", recentJobs=");
			sb.append(recentJobCount);
			sb.append(", url=");
			sb.append(url);
			sb.append("}");

			System.out.println(sb.toString());

			return availableSlaveCount;
		}

		protected AvailableSlaveCallable(Integer recentJobCount, String url) {
			this.recentJobCount = recentJobCount;

			this.url = url;
		}

		protected Integer recentJobCount;
		protected String url;

	}

}