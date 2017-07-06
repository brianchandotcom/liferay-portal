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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Yoo
 */
public class LoadBalancerUtil {

	public static String getMostAvailableMasterURL(Properties properties)
		throws Exception {

		long start = System.currentTimeMillis();

		int retries = 0;

		while (true) {
			try {
				String baseInvocationURL = properties.getProperty(
					"base.invocation.url");

				String masterPrefix = getMasterPrefix(baseInvocationURL);

				if (masterPrefix.equals(baseInvocationURL)) {
					return baseInvocationURL;
				}

				List<JenkinsMaster> jenkinsMasters = getAvailableJenkinsMasters(
					masterPrefix, properties);

				long nextUpdateTimestamp = _getNextUpdateTimestamp(
					masterPrefix);

				if (nextUpdateTimestamp < System.currentTimeMillis()) {
					_updateJenkinsMasters(jenkinsMasters);

					_setNextUpdateTimestamp(
						masterPrefix,
						System.currentTimeMillis() + _updateInterval);
				}

				Collections.sort(jenkinsMasters);

				JenkinsMaster mostAvailableJenkinsMaster = jenkinsMasters.get(
					0);

				StringBuilder sb = new StringBuilder();

				for (JenkinsMaster jenkinsMaster : jenkinsMasters) {
					sb.append(jenkinsMaster.getMasterName());
					sb.append(" : ");
					sb.append(jenkinsMaster.getAvailableSlavesCount());
					sb.append("\n");
				}

				System.out.print(sb);

				sb = new StringBuilder();

				sb.append("\nMost available master ");
				sb.append(mostAvailableJenkinsMaster.getMasterName());
				sb.append(" has ");
				sb.append(mostAvailableJenkinsMaster.getAvailableSlavesCount());
				sb.append(" available slaves.");

				System.out.println(sb);

				int invokedBatchSize = 0;

				try {
					invokedBatchSize = Integer.parseInt(
						properties.getProperty("invoked.job.batch.size"));
				}
				catch (Exception e) {
					invokedBatchSize = 1;
				}

				mostAvailableJenkinsMaster.addRecentBatch(invokedBatchSize);

				return "http://" + mostAvailableJenkinsMaster.getMasterName();
			}
			catch (Exception e) {
				if (retries < _MAX_RETRIES) {
					retries++;

					continue;
				}

				throw e;
			}
			finally {
				System.out.println(
					"Got most available master URL in " +
						JenkinsResultsParserUtil.toDurationString(
							System.currentTimeMillis() - start));
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

	public static void setUpdateInterval(long interval) {
		_updateInterval = interval;
	}

	protected static List<JenkinsMaster> getAvailableJenkinsMasters(
		String masterPrefix, Properties properties) {

		List<JenkinsMaster> allJenkinsMasters = null;

		if (!_jenkinsMasters.containsKey(masterPrefix)) {
			allJenkinsMasters = JenkinsResultsParserUtil.getJenkinsMasters(
				properties, masterPrefix);

			_jenkinsMasters.put(masterPrefix, allJenkinsMasters);
		}
		else {
			allJenkinsMasters = _jenkinsMasters.get(masterPrefix);
		}

		List<String> blacklist = _getBlacklist(properties);

		if (blacklist.isEmpty()) {
			return new ArrayList<>(allJenkinsMasters);
		}

		List<JenkinsMaster> availableJenkinsMasters = new ArrayList<>(
			allJenkinsMasters.size());

		for (JenkinsMaster jenkinsMaster : allJenkinsMasters) {
			if (blacklist.contains(jenkinsMaster.getMasterName())) {
				continue;
			}

			availableJenkinsMasters.add(jenkinsMaster);
		}

		return availableJenkinsMasters;
	}

	protected static String getMasterPrefix(String baseInvocationURL) {
		Matcher matcher = _urlPattern.matcher(baseInvocationURL);

		if (!matcher.find()) {
			return baseInvocationURL;
		}

		return matcher.group("masterPrefix");
	}

	private static List<String> _getBlacklist(Properties properties) {
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

	private static long _getNextUpdateTimestamp(String masterPrefix) {
		if (!_nextUpdateTimestampMap.containsKey(masterPrefix)) {
			return 0;
		}

		else return _nextUpdateTimestampMap.get(masterPrefix);
	}

	private static void _setNextUpdateTimestamp(
		String masterPrefix, long nextUpdateTimestamp) {

		_nextUpdateTimestampMap.put(masterPrefix, nextUpdateTimestamp);
	}

	private static void _updateJenkinsMasters(
		List<JenkinsMaster> jenkinsMasters) {

		ExecutorService executorService = Executors.newFixedThreadPool(
			jenkinsMasters.size());

		for (final JenkinsMaster jenkinsMaster : jenkinsMasters) {
			executorService.execute(
				new Runnable() {

					@Override
					public void run() {
						jenkinsMaster.update();
					}

				});
		}

		executorService.shutdown();

		try {
			executorService.awaitTermination(10, TimeUnit.SECONDS);
		}
		catch (InterruptedException ie) {
			throw new RuntimeException(ie);
		}

		List<JenkinsMaster> unavailableJenkinsMasters = new ArrayList<>(
			jenkinsMasters.size());

		for (JenkinsMaster jenkinsMaster : jenkinsMasters) {
			if (!jenkinsMaster.isAvailable()) {
				unavailableJenkinsMasters.add(jenkinsMaster);
			}
		}

		jenkinsMasters.removeAll(unavailableJenkinsMasters);

		if (jenkinsMasters.isEmpty()) {
			throw new RuntimeException(
				"Unable to communicate with any Jenkins masters");
		}
	}

	private static final int _MAX_RETRIES = 3;

	private static final Map<String, List<JenkinsMaster>> _jenkinsMasters =
		new HashMap<>();
	private static final Map<String, Long> _nextUpdateTimestampMap =
		new HashMap<>();
	private static long _updateInterval = 1000 * 10;
	private static final Pattern _urlPattern = Pattern.compile(
		"http://(?<masterPrefix>.+-\\d?).liferay.com");

}