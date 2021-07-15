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

	public static List<JenkinsMaster> getAvailableJenkinsMasters(
		String masterPrefix, String blacklistString, int minimumRAM,
		int maximumSlavesPerHost, Properties properties) {

		return getAvailableJenkinsMasters(
			masterPrefix, blacklistString, minimumRAM, maximumSlavesPerHost,
			properties, true);
	}

	public static List<JenkinsMaster> getAvailableJenkinsMasters(
		String masterPrefix, String blacklistString, int minimumRAM,
		int maximumSlavesPerHost, Properties properties, boolean verbose) {

		List<JenkinsMaster> allJenkinsMasters = null;

		if (!_jenkinsMasters.containsKey(masterPrefix)) {
			allJenkinsMasters = JenkinsResultsParserUtil.getJenkinsMasters(
				properties, JenkinsMaster.getSlaveRAMMinimumDefault(),
				JenkinsMaster.getSlavesPerHostDefault(), masterPrefix);

			_jenkinsMasters.put(masterPrefix, allJenkinsMasters);
		}
		else {
			allJenkinsMasters = _jenkinsMasters.get(masterPrefix);
		}

		List<String> blacklist = _getBlacklist(properties, verbose);

		if ((blacklistString != null) && !blacklistString.isEmpty()) {
			blacklistString = blacklistString.toLowerCase();

			for (String blacklistItem : blacklistString.split("\\s*,\\s*")) {
				if (!blacklist.contains(blacklistItem)) {
					blacklist.add(blacklistItem);
				}
			}
		}

		List<JenkinsMaster> availableJenkinsMasters = new ArrayList<>(
			allJenkinsMasters.size());

		for (JenkinsMaster jenkinsMaster : allJenkinsMasters) {
			if (blacklist.contains(jenkinsMaster.getName()) ||
				(jenkinsMaster.getSlaveRAM() < minimumRAM) ||
				(jenkinsMaster.getSlavesPerHost() > maximumSlavesPerHost)) {

				continue;
			}

			availableJenkinsMasters.add(jenkinsMaster);
		}

		return availableJenkinsMasters;
	}

	public static String getMostAvailableMasterURL(
			boolean verbose, String... overridePropertiesArray)
		throws Exception {

		return getMostAvailableMasterURL(
			null, overridePropertiesArray, verbose);
	}

	public static String getMostAvailableMasterURL(Properties properties) {
		return getMostAvailableMasterURL(properties, true);
	}

	public static String getMostAvailableMasterURL(
		Properties properties, boolean verbose) {

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		int retries = 0;

		while (true) {
			try {
				String baseInvocationURL = properties.getProperty(
					"base.invocation.url");

				String masterPrefix = getMasterPrefix(baseInvocationURL);

				if (masterPrefix.equals(baseInvocationURL)) {
					return baseInvocationURL;
				}

				String blacklistString = properties.getProperty("blacklist");

				Integer minimumRAM = JenkinsMaster.getSlaveRAMMinimumDefault();

				String minimumRAMString = properties.getProperty("minimum.ram");

				if ((minimumRAMString != null) &&
					minimumRAMString.matches("\\d+")) {

					minimumRAM = Integer.valueOf(minimumRAMString);
				}

				Integer maximumSlavesPerHost =
					JenkinsMaster.getSlavesPerHostDefault();

				String maximumSlavesPerHostString = properties.getProperty(
					"maximum.slaves.per.host");

				if ((maximumSlavesPerHostString != null) &&
					maximumSlavesPerHostString.matches("\\d+")) {

					maximumSlavesPerHost = Integer.valueOf(
						maximumSlavesPerHost);
				}

				List<JenkinsMaster> jenkinsMasters = getAvailableJenkinsMasters(
					masterPrefix, blacklistString, minimumRAM,
					maximumSlavesPerHost, properties, verbose);

				long nextUpdateTimestamp = _getNextUpdateTimestamp(
					masterPrefix);

				if (nextUpdateTimestamp <
						JenkinsResultsParserUtil.getCurrentTimeMillis()) {

					_updateJenkinsMasters(jenkinsMasters);

					_setNextUpdateTimestamp(
						masterPrefix,
						JenkinsResultsParserUtil.getCurrentTimeMillis() +
							_updateInterval);
				}

				Collections.sort(jenkinsMasters);

				JenkinsMaster mostAvailableJenkinsMaster = jenkinsMasters.get(
					0);

				if (verbose) {
					StringBuilder sb = new StringBuilder();

					for (JenkinsMaster jenkinsMaster : jenkinsMasters) {
						sb.append(jenkinsMaster.getName());
						sb.append(" : ");
						sb.append(jenkinsMaster.getAvailableSlavesCount());
						sb.append(" : ");
						sb.append(jenkinsMaster.getAverageQueueLength());
						sb.append("\n");
					}

					System.out.println(sb.toString());

					sb = new StringBuilder();

					sb.append("\nMost available master ");
					sb.append(mostAvailableJenkinsMaster.getName());
					sb.append(" has ");
					sb.append(
						mostAvailableJenkinsMaster.getAvailableSlavesCount());
					sb.append(" available slaves.");

					System.out.println(sb.toString());
				}

				int invokedBatchSize = 0;

				try {
					invokedBatchSize = Integer.parseInt(
						properties.getProperty("invoked.job.batch.size"));
				}
				catch (Exception exception) {
					invokedBatchSize = 1;
				}

				mostAvailableJenkinsMaster.addRecentBatch(invokedBatchSize);

				return "http://" + mostAvailableJenkinsMaster.getName();
			}
			catch (Exception exception) {
				if (retries < _RETRIES_SIZE_MAX) {
					retries++;

					continue;
				}

				throw exception;
			}
			finally {
				if (verbose) {
					String durationString =
						JenkinsResultsParserUtil.toDurationString(
							JenkinsResultsParserUtil.getCurrentTimeMillis() -
								start);

					System.out.println(
						"Got most available master URL in " + durationString);
				}
			}
		}
	}

	public static String getMostAvailableMasterURL(
			String... overridePropertiesArray)
		throws Exception {

		return getMostAvailableMasterURL(true, overridePropertiesArray);
	}

	public static String getMostAvailableMasterURL(
			String propertiesURL, String[] overridePropertiesArray)
		throws Exception {

		return getMostAvailableMasterURL(
			propertiesURL, overridePropertiesArray, true);
	}

	public static String getMostAvailableMasterURL(
			String propertiesURL, String[] overridePropertiesArray,
			boolean verbose)
		throws Exception {

		Properties properties = new Properties();

		if (propertiesURL == null) {
			properties = JenkinsResultsParserUtil.getBuildProperties(false);
		}
		else {
			properties = new Properties();
			String propertiesString = JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(propertiesURL), false,
				true);

			properties.load(new StringReader(propertiesString));
		}

		if ((overridePropertiesArray != null) &&
			(overridePropertiesArray.length > 0) &&
			((overridePropertiesArray.length % 2) == 0)) {

			for (int i = 0; i < overridePropertiesArray.length; i += 2) {
				String overridePropertyValue = overridePropertiesArray[i + 1];

				if (overridePropertyValue == null) {
					continue;
				}

				String overridePropertyName = overridePropertiesArray[i];

				properties.setProperty(
					overridePropertyName, overridePropertyValue);
			}
		}

		return getMostAvailableMasterURL(properties, verbose);
	}

	public static void setUpdateInterval(long interval) {
		_updateInterval = interval;
	}

	protected static String getMasterPrefix(String baseInvocationURL) {
		Matcher matcher = _urlPattern.matcher(baseInvocationURL);

		if (!matcher.find()) {
			return baseInvocationURL;
		}

		return matcher.group("masterPrefix");
	}

	private static List<String> _getBlacklist(
		Properties properties, boolean verbose) {

		String blacklistString = properties.getProperty(
			"jenkins.load.balancer.blacklist", "");

		if (verbose) {
			System.out.println("Blacklist: " + blacklistString);
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

		return _nextUpdateTimestampMap.get(masterPrefix);
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
		catch (InterruptedException interruptedException) {
			throw new RuntimeException(interruptedException);
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

	private static final int _RETRIES_SIZE_MAX = 3;

	private static final Map<String, List<JenkinsMaster>> _jenkinsMasters =
		new HashMap<>();
	private static final Map<String, Long> _nextUpdateTimestampMap =
		new HashMap<>();
	private static long _updateInterval = 1000 * 10;
	private static final Pattern _urlPattern = Pattern.compile(
		"http://(?<masterPrefix>.+-\\d?).liferay.com");

}