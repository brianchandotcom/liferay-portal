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

import java.io.StringReader;

import java.util.ArrayList;
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

			String masterPrefix = getMasterPrefix(baseInvocationURL);

			if (masterPrefix.equals(baseInvocationURL)) {
				return baseInvocationURL;
			}

			List<String> masters = JenkinsResultsParserUtil.getMasters(
				properties, masterPrefix);

			masters.removeAll(getBlacklist(properties));

			if (masters.size() == 1) {
				return "http://" + masterPrefix + "-1";
			}

			int maxAvailableSlaveCount = Integer.MIN_VALUE;
			int x = -1;

			try {
				List<FutureTask<Integer>> futureTasks = new ArrayList<>(
					masters.size());

				startParallelTasks(
					futureTasks, masters, masterPrefix, properties);

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
								masters.get(i) + ".");

						availableSlaveCount = null;
					}

					if (availableSlaveCount == null) {
						badIndices.add(i);

						continue;
					}

					sb.append(masters.get(i));
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
								"with masters.");
					}

					retryCount++;

					continue;
				}

				if (!maxIndices.isEmpty()) {
					x = maxIndices.get(
						JenkinsResultsParserUtil.getRandomValue(
							0, maxIndices.size() - 1));
				}
				else {
					while (true) {
						x = JenkinsResultsParserUtil.getRandomValue(
							0, masters.size() - 1);

						if (badIndices.contains(x)) {
							continue;
						}

						break;
					}
				}

				sb.append("\nMost available master ");
				sb.append(masters.get(x));
				sb.append(" has ");
				sb.append(maxAvailableSlaveCount);
				sb.append(" available slaves.");

				System.out.println(sb);

				return "http://" + masters.get(x);
			}
			finally {
				if (RECENT_BATCH_AGE > 0) {
					List<BatchRecord> recentBatchSizeRecords =
						_recentBatchRecordsMap.get(masters.get(x));

					if (recentBatchSizeRecords == null) {
						recentBatchSizeRecords = new ArrayList<>();

						_recentBatchRecordsMap.put(
							masters.get(x), recentBatchSizeRecords);
					}

					int invokedBatchSize = 0;

					try {
						invokedBatchSize = Integer.parseInt(
							properties.getProperty("invoked.job.batch.size"));
					}
					catch (Exception e) {
						invokedBatchSize = 1;
					}

					if (invokedBatchSize != 0) {
						recentBatchSizeRecords.add(
							new BatchRecord(
								invokedBatchSize, System.currentTimeMillis()));
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

		return getMostAvailableMasterURL(null, overridePropertiesArray);
	}

	public static String getMostAvailableMasterURL(
			String propertiesURL, String[] overridePropertiesArray)
		throws Exception {

		Properties properties = new Properties();

		if (propertiesURL == null) {
			properties = JenkinsResultsParserUtil.getBuildProperties();
		}
		else {
			properties = new Properties();
			String propertiesString = JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(propertiesURL), false);

			properties.load(new StringReader(propertiesString));
		}

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

	protected static String getMasterPrefix(String baseInvocationURL) {
		Matcher matcher = _urlPattern.matcher(baseInvocationURL);

		if (!matcher.find()) {
			return baseInvocationURL;
		}

		return matcher.group("masterPrefix");
	}

	protected static int getRecentBatchSizesTotal(String master)
		throws Exception {

		List<BatchRecord> recentBatchRecords = _recentBatchRecordsMap.get(
			master);

		if ((recentBatchRecords == null) || recentBatchRecords.isEmpty()) {
			return 0;
		}

		int recentBatchSizesTotal = 0;

		List<BatchRecord> oldBatchRecords = new ArrayList<>(
			recentBatchRecords.size());

		for (BatchRecord recentBatchRecord : recentBatchRecords) {
			if ((recentBatchRecord.timestamp + RECENT_BATCH_AGE) >
					System.currentTimeMillis()) {

				recentBatchSizesTotal += recentBatchRecord.size;
			}
			else {
				oldBatchRecords.add(recentBatchRecord);
			}
		}

		recentBatchRecords.removeAll(oldBatchRecords);

		return recentBatchSizesTotal;
	}

	protected static void startParallelTasks(
			List<FutureTask<Integer>> futureTasks, List<String> masters,
			String masterPrefix, Properties properties)
		throws Exception {

		ExecutorService executorService = Executors.newFixedThreadPool(
			masters.size());

		for (String targetMaster : masters) {
			FutureTask<Integer> futureTask = new FutureTask<>(
				new AvailableSlaveCallable(
					getRecentBatchSizesTotal(targetMaster),
					properties.getProperty(
						"jenkins.local.url[" + targetMaster + "]")));

			executorService.execute(futureTask);

			futureTasks.add(futureTask);
		}

		executorService.shutdown();
	}

	protected static long RECENT_BATCH_AGE = 120 * 1000;

	private static final Map<String, List<BatchRecord>> _recentBatchRecordsMap =
		new HashMap<>();
	private static final Pattern _urlPattern = Pattern.compile(
		"http://(?<masterPrefix>.+-\\d?).liferay.com");

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

			if (recentBatchSizesTotal != null) {
				availableSlaveCount -= recentBatchSizesTotal;
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
			sb.append(", recentBatchSizesTotal=");
			sb.append(recentBatchSizesTotal);
			sb.append(", url=");
			sb.append(url);
			sb.append("}");

			System.out.println(sb.toString());

			return availableSlaveCount;
		}

		protected AvailableSlaveCallable(
			Integer recentBatchSizesTotal, String url) {

			this.recentBatchSizesTotal = recentBatchSizesTotal;

			this.url = url;
		}

		protected Integer recentBatchSizesTotal;
		protected String url;

	}

	private static class BatchRecord {

		public int size;
		public long timestamp;

		private BatchRecord(int size, long timestamp) {
			this.size = size;
			this.timestamp = timestamp;
		}

	}

}