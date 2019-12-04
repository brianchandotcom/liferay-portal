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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class JenkinsMaster implements Comparable<JenkinsMaster> {

	public static final Integer SLAVE_RAM_DEFAULT = 16;

	public JenkinsMaster(String masterName) {
		if (masterName.contains(".")) {
			_masterName = masterName.substring(0, masterName.indexOf("."));
		}
		else {
			_masterName = masterName;
		}

		try {
			Properties properties =
				JenkinsResultsParserUtil.getBuildProperties();

			_masterURL = properties.getProperty(
				JenkinsResultsParserUtil.combine(
					"jenkins.local.url[", _masterName, "]"));

			Integer slaveRAM = SLAVE_RAM_DEFAULT;

			String slaveRAMString = JenkinsResultsParserUtil.getProperty(
				properties,
				JenkinsResultsParserUtil.combine(
					"master.property(", _masterName, "/slave.ram)"));

			if ((slaveRAMString != null) && slaveRAMString.matches("\\d+")) {
				slaveRAM = Integer.valueOf(slaveRAMString);
			}

			_slaveRAM = slaveRAM;
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to determine URL for master " + _masterName, e);
		}
	}

	public synchronized void addRecentBatch(int batchSize) {
		_batchSizes.put(
			System.currentTimeMillis() + maxRecentBatchAge, batchSize);

		getAvailableSlavesCount();
	}

	@Override
	public int compareTo(JenkinsMaster jenkinsMaster) {
		Integer availableSlavesCount = getAvailableSlavesCount();

		int value = availableSlavesCount.compareTo(
			jenkinsMaster.getAvailableSlavesCount());

		if (value != 0) {
			return -value;
		}

		Random random = new Random();

		while (true) {
			int result = random.nextInt(3) - 1;

			if (result != 0) {
				return result;
			}
		}
	}

	public int getAvailableSlavesCount() {
		int recentBatchSizesTotal = _getRecentBatchSizesTotal();

		StringBuilder sb = new StringBuilder();

		sb.append("{availableSlavesCount=");

		int availableSlavesCount =
			_reportedAvailableSlavesCount - recentBatchSizesTotal;

		sb.append(availableSlavesCount);

		sb.append(", masterURL=");
		sb.append(_masterURL);
		sb.append(", recentBatchSizesTotal=");
		sb.append(recentBatchSizesTotal);
		sb.append(", reportedAvailableSlavesCount=");
		sb.append(_reportedAvailableSlavesCount);
		sb.append("}");

		_summary = sb.toString();

		return availableSlavesCount;
	}

	public String getName() {
		return _masterName;
	}

	public Integer getSlaveRAM() {
		return _slaveRAM;
	}

	public String getURL() {
		return _masterURL;
	}

	public boolean isAvailable() {
		return _available;
	}

	@Override
	public String toString() {
		getAvailableSlavesCount();

		return _summary;
	}

	public void update() {
		JSONObject computerJSONObject = null;
		JSONObject queueJSONObject = null;

		try {
			computerJSONObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					JenkinsResultsParserUtil.combine(
						_masterURL,
						"/computer/api/json?tree=computer[displayName,",
						"idle,offline]")),
				false, 5000);
			queueJSONObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					_masterURL + "/queue/api/json?tree=items[task[name],why]"),
				false, 5000);
		}
		catch (Exception e) {
			System.out.println("Unable to read " + _masterURL);

			_available = false;

			return;
		}

		_available = true;

		int idleCount = 0;

		JSONArray computerJSONArray = computerJSONObject.getJSONArray(
			"computer");

		for (int i = 0; i < computerJSONArray.length(); i++) {
			JSONObject curComputerJSONObject = computerJSONArray.getJSONObject(
				i);

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
			JSONArray itemsJSONArray = queueJSONObject.getJSONArray("items");

			for (int i = 0; i < itemsJSONArray.length(); i++) {
				JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

				if (itemJSONObject.has("task")) {
					JSONObject taskJSONObject = itemJSONObject.getJSONObject(
						"task");

					String taskName = taskJSONObject.getString("name");

					if (taskName.equals("verification-node")) {
						continue;
					}
				}

				if (itemJSONObject.has("why")) {
					String why = itemJSONObject.optString("why");

					if (why.endsWith("is offline")) {
						continue;
					}
				}

				queueCount++;
			}
		}

		_reportedAvailableSlavesCount = idleCount - queueCount;

		getAvailableSlavesCount();
	}

	protected static long maxRecentBatchAge = 120 * 1000;

	private synchronized int _getRecentBatchSizesTotal() {
		long currentTimestamp = System.currentTimeMillis();
		int recentBatchSizesTotal = 0;

		List<Long> expiredTimestamps = new ArrayList<>(_batchSizes.size());

		for (Map.Entry<Long, Integer> entry : _batchSizes.entrySet()) {
			Long expirationTimestamp = entry.getKey();

			if (expirationTimestamp < currentTimestamp) {
				expiredTimestamps.add(expirationTimestamp);

				continue;
			}

			recentBatchSizesTotal += entry.getValue();
		}

		for (Long expiredTimestamp : expiredTimestamps) {
			_batchSizes.remove(expiredTimestamp);
		}

		return recentBatchSizesTotal;
	}

	private boolean _available;
	private final Map<Long, Integer> _batchSizes = new TreeMap<>();
	private final String _masterName;
	private final String _masterURL;
	private int _reportedAvailableSlavesCount;
	private final Integer _slaveRAM;
	private String _summary;

}