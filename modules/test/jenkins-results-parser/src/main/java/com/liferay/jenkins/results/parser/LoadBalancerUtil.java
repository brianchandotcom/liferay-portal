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

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class LoadBalancerUtil {

	public static String getMostAvailableMasterURL(Properties properties)
		throws Exception {

		long start = System.currentTimeMillis();

		int retryCount = 0;

		while (true) {
			String baseInvocationURL = properties.getProperty(
				"base.invocation.url");

			String hostNamePrefix = getHostNamePrefix(baseInvocationURL);

			if (hostNamePrefix.equals(baseInvocationURL)) {
				return baseInvocationURL;
			}

			List<String> hostNames = getHostNames(properties, hostNamePrefix);

			if (hostNames.size() == 1) {
				return "http://" + hostNamePrefix + "-1";
			}

			int maxAvailableSlaveCount = Integer.MIN_VALUE;
			int x = -1;

			try {
				List<FutureTask<Integer>> futureTasks = new ArrayList<>(
					hostNames.size());

				startParallelTasks(
					hostNames, hostNamePrefix, properties, futureTasks);

				List<Integer> badIndices = new ArrayList<>(futureTasks.size());
				List<Integer> maxIndices = new ArrayList<>(futureTasks.size());

				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < futureTasks.size(); i++) {
					Integer availableSlaveCount = null;

					FutureTask<Integer> futureTask = futureTasks.get(i);

					try {
						availableSlaveCount = futureTask.get(
							15, TimeUnit.SECONDS);
					}
					catch (TimeoutException te) {
						System.out.println(
							"Unable to assess master availability for " +
								hostNames.get(i) + ".");

						availableSlaveCount = null;
					}

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
						x = getRandomValue(0, hostNames.size() - 1);

						if (badIndices.contains(x)) {
							continue;
						}

						break;
					}
				}

				sb.append("\nMost available master ");
				sb.append(hostNames.get(x));
				sb.append(" has ");
				sb.append(maxAvailableSlaveCount);
				sb.append(" available slaves.");

				System.out.println(sb);

				return "http://" + hostNames.get(x);
			}
			finally {
				if (recentBuildsPeriod > 0) {
					Map<Long, Integer> hostRecentBuildsMap =
						_recentBuildsMap.get(hostNames.get(x));

					if (hostRecentBuildsMap == null) {
						hostRecentBuildsMap = new HashMap<>();
						_recentBuildsMap.put(
							hostNames.get(x), hostRecentBuildsMap);
					}

					int invokedBuildBatchSize = 0;

					try {
						invokedBuildBatchSize = Integer.parseInt(
							properties.getProperty("invoked.job.batch.size"));
					}
					catch (Exception e) {
						invokedBuildBatchSize = 1;
					}

					if (invokedBuildBatchSize != 0) {
						hostRecentBuildsMap.put(
							System.currentTimeMillis(), invokedBuildBatchSize);
					}
				}

				System.out.println(
					"Got most available master URL in " +
						((System.currentTimeMillis() - start) / 1000F) +
							" seconds.");
			}
		}
	}

	public static String getMostAvailableMasterURL(
			String... overridePropertiesArray)
		throws Exception {

		return getMostAvailableMasterURL(
			"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
				"/liferay-jenkins-ee/commands/build.properties",
			overridePropertiesArray);
	}

	public static String getMostAvailableMasterURL(
			String propertiesURL, String[] overridePropertiesArray)
		throws Exception {

		Properties properties = new Properties();

		String propertiesString = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(propertiesURL), false);

		properties.load(new StringReader(propertiesString));

		if ((overridePropertiesArray != null) &&
			(overridePropertiesArray.length > 0) &&
			((overridePropertiesArray.length % 2) == 0)) {

			for (int i = 0; i < overridePropertiesArray.length; i += 2) {
				String overridePropertyName = overridePropertiesArray[i];
				String overridePropertyValue = overridePropertiesArray[i + 1];

				if (overridePropertyValue == null) {
					continue;
				}

				properties.setProperty(
					overridePropertyName, overridePropertyValue);
			}
		}

		return getMostAvailableMasterURL(properties);
	}

	protected static List<String> getBlacklist(Properties properties) {
		String blacklistString = properties.getProperty(
			"jenkins.load.balancer.blacklist", "");

		System.out.println("Blacklist: " + blacklistString);

		if (blacklistString.isEmpty()) {
			return Collections.emptyList();
		}

		List<String> blacklist = new ArrayList<>();

		for (String blacklistItem : blacklistString.split(",")) {
			blacklist.add(blacklistItem.trim());
		}

		return blacklist;
	}

	protected static String getHostNamePrefix(String baseInvocationURL) {
		Matcher matcher = _urlPattern.matcher(baseInvocationURL);

		if (!matcher.find()) {
			return baseInvocationURL;
		}

		return matcher.group("hostNamePrefix");
	}

	protected static List<String> getHostNames(
		Properties properties, String hostNamePrefix) {

		List<String> blacklist = getBlacklist(properties);
		List<String> hostNames = new ArrayList<>();
		int i = 1;

		while (true) {
			String jenkinsLocalURL = properties.getProperty(
				"jenkins.local.url[" + hostNamePrefix + "-" + i + "]");

			if ((jenkinsLocalURL != null) && (jenkinsLocalURL.length() > 0)) {
				Matcher matcher = _hostnamePattern.matcher(jenkinsLocalURL);

				if (!matcher.find()) {
					continue;
				}

				String jenkinsLocalHostName = matcher.group("hostname");

				if (!blacklist.contains(jenkinsLocalHostName)) {
					hostNames.add(jenkinsLocalHostName);
				}

				i++;
				continue;
			}

			System.out.println("Host name prefix: " + hostNamePrefix);
			System.out.println("Host names: " + hostNames);

			return hostNames;
		}
	}

	protected static int getRandomValue(int start, int end) {
		int size = Math.abs(end - start);

		double randomDouble = Math.random();

		return start + (int)Math.round(size * randomDouble);
	}

	protected static int getRecentBuildsCount(String hostName)
		throws Exception {

		Map<Long, Integer> hostRecentBuildsMap = _recentBuildsMap.get(hostName);

		if ((hostRecentBuildsMap == null) || hostRecentBuildsMap.isEmpty()) {
			return 0;
		}

		int totalBuildCount = 0;

		Set<Map.Entry<Long, Integer>> recentBuildsMapEntrySet =
			hostRecentBuildsMap.entrySet();
		List<Map.Entry<Long, Integer>> recentBuildsEntriesToBeDeleted =
			new ArrayList<>(hostRecentBuildsMap.size());

		for (Map.Entry<Long, Integer> recentBuild : recentBuildsMapEntrySet) {
			int buildCount = recentBuild.getValue();
			long timestamp = recentBuild.getKey();

			if ((timestamp + recentBuildsPeriod) >
					System.currentTimeMillis()) {

				totalBuildCount += buildCount;
			}
			else {
				recentBuildsEntriesToBeDeleted.add(recentBuild);
			}
		}

		recentBuildsMapEntrySet.removeAll(recentBuildsEntriesToBeDeleted);

		return totalBuildCount;
	}

	protected static void startParallelTasks(
			List<String> hostNames, String hostNamePrefix,
			Properties properties, List<FutureTask<Integer>> futureTasks)
		throws Exception {

		ExecutorService executorService = Executors.newFixedThreadPool(
			hostNames.size());

		for (String targetHostName : hostNames) {
			FutureTask<Integer> futureTask = new FutureTask<>(
				new AvailableSlaveCallable(
					getRecentBuildsCount(targetHostName),
					properties.getProperty(
						"jenkins.local.url[" + targetHostName + "]")));

			executorService.execute(futureTask);

			futureTasks.add(futureTask);
		}

		executorService.shutdown();
	}

	protected static void waitForTurn(File file, int hostNameCount)
		throws Exception {

		long start = System.currentTimeMillis();

		try {
			while (true) {
				if (!file.exists()) {
					JenkinsResultsParserUtil.write(file, "");

					return;
				}

				long age = System.currentTimeMillis() - file.lastModified();
				String content = JenkinsResultsParserUtil.read(file);

				if (content.length() > 0) {
					if (age < _MAX_AGE) {
						Thread.sleep(1000);

						continue;
					}
					else {
						System.out.println(
							"Sempahore file " + file + " timed out: " +
								content);
					}
				}

				return;
			}
		}
		finally {
			System.out.println(
				"Waited " + ((System.currentTimeMillis() - start) / 1000F) +
					" seconds.");
		}
	}

	protected static long recentBuildsPeriod = 120 * 1000;

	private static final long _MAX_AGE = 30 * 1000;

	private static final Pattern _hostnamePattern =
		Pattern.compile(".*/(?<hostname>[^/]+)/?");
	private static final Map<String, Map<Long, Integer>> _recentBuildsMap =
		new HashMap<>();
	private static final Pattern _urlPattern = Pattern.compile(
		"http://(?<hostNamePrefix>.+-\\d?).liferay.com");

	private static class AvailableSlaveCallable implements Callable<Integer> {

		@Override
		public Integer call() throws Exception {
			long start = System.currentTimeMillis();

			JSONObject computerJSONObject = null;
			JSONObject queueJSONObject = null;

			try {
				computerJSONObject = JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(
						url + "/computer/api/json?tree=computer[displayName," +
							"idle,offline]"),
					false, 5000);
				queueJSONObject = JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(
						url + "/queue/api/json?tree=items[task[name],why]"),
					false, 5000);
			}
			catch (Exception e) {
				System.out.println("Unable to read " + url);

				return null;
			}

			int idleCount = 0;

			JSONArray computersJSONArray = computerJSONObject.getJSONArray(
				"computer");

			for (int i = 0; i < computersJSONArray.length(); i++) {
				JSONObject curComputerJSONObject =
					computersJSONArray.getJSONObject(i);

				if (curComputerJSONObject.getBoolean("idle") &&
					!curComputerJSONObject.getBoolean("offline")) {

					String displayName = curComputerJSONObject.getString(
						"displayName");

					if (!displayName.equals("master")) {
						idleCount++;
					}
				}
			}

			int queueCount = 0;

			if (queueJSONObject.has("items")) {
				JSONArray itemsJSONArray = queueJSONObject.getJSONArray(
					"items");

				for (int i = 0; i < itemsJSONArray.length(); i++) {
					JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

					if (itemJSONObject.has("why")) {
						String why = itemJSONObject.getString("why");

						if (why.endsWith("is offline")) {
							continue;
						}
					}

					if (itemJSONObject.has("task")) {
						JSONObject taskJSONObject =
							itemJSONObject.getJSONObject("task");

						String taskName = taskJSONObject.getString("name");

						if (taskName.equals("verification-node")) {
							continue;
						}
					}

					queueCount++;
				}
			}

			int availableSlaveCount = idleCount - queueCount;

			if (recentBuildsCount != null) {
				availableSlaveCount -= recentBuildsCount;
			}

			StringBuilder sb = new StringBuilder();

			sb.append("{available=");
			sb.append(availableSlaveCount);
			sb.append(", duration=");
			sb.append(System.currentTimeMillis() - start);
			sb.append("ms, idle=");
			sb.append(idleCount);
			sb.append(", queue=");
			sb.append(queueCount);
			sb.append(", recentBuilds=");
			sb.append(recentBuildsCount);
			sb.append(", url=");
			sb.append(url);
			sb.append("}");

			System.out.println(sb.toString());

			return availableSlaveCount;
		}

		protected AvailableSlaveCallable(
			Integer recentBuildsCount, String url) {

			this.recentBuildsCount = recentBuildsCount;

			this.url = url;
		}

		protected Integer recentBuildsCount;
		protected String url;

	}

}