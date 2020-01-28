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
import java.util.HashMap;
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
		catch (Exception exception) {
			throw new RuntimeException(
				"Unable to determine URL for master " + _masterName, exception);
		}
	}

	public synchronized void addRecentBatch(int batchSize) {
		_batchSizes.put(
			System.currentTimeMillis() + maxRecentBatchAge, batchSize);

		getAvailableSlavesCount();
	}

	@Override
	public int compareTo(JenkinsMaster jenkinsMaster) {
		Integer value = null;

		Integer availableSlavesCount = getAvailableSlavesCount();
		Integer otherAvailableSlavesCount =
			jenkinsMaster.getAvailableSlavesCount();

		if ((availableSlavesCount > 0) || (otherAvailableSlavesCount > 0)) {
			value = availableSlavesCount.compareTo(otherAvailableSlavesCount);
		}

		if ((value == null) || (value == 0)) {
			Float averageQueueLength = getAverageQueueLength();
			Float otherAverageQueueLength =
				jenkinsMaster.getAverageQueueLength();

			value = -1 * averageQueueLength.compareTo(otherAverageQueueLength);
		}

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
		return getIdleSlavesCount() - _queueCount - _getRecentBatchSizesTotal();
	}

	public float getAverageQueueLength() {
		return ((float)_queueCount + _getRecentBatchSizesTotal()) /
			getOnlineSlavesCount();
	}

	public int getIdleSlavesCount() {
		int idleSlavesCount = 0;

		for (JenkinsSlave jenkinsSlave : _jenkinsSlavesMap.values()) {
			if (jenkinsSlave.isOffline()) {
				continue;
			}

			if (jenkinsSlave.isIdle()) {
				idleSlavesCount++;
			}
		}

		return idleSlavesCount;
	}

	public JenkinsSlave getJenkinsSlave(String jenkinsSlaveName) {
		if (_jenkinsSlavesMap.isEmpty()) {
			update();
		}

		return _jenkinsSlavesMap.get(jenkinsSlaveName);
	}

	public String getName() {
		return _masterName;
	}

	public int getOnlineSlavesCount() {
		int onlineSlavesCount = 0;

		for (JenkinsSlave jenkinsSlave : _jenkinsSlavesMap.values()) {
			if (!jenkinsSlave.isOffline()) {
				onlineSlavesCount++;
			}
		}

		return onlineSlavesCount;
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
		return JenkinsResultsParserUtil.combine(
			"{availableSlavesCount=", String.valueOf(getAvailableSlavesCount()),
			", masterURL=", _masterURL, ", recentBatchSizesTotal=",
			String.valueOf(_getRecentBatchSizesTotal()),
			", reportedAvailableSlavesCount=",
			String.valueOf(_reportedAvailableSlavesCount), "}");
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
		catch (Exception exception) {
			System.out.println("Unable to read " + _masterURL);

			_available = false;

			return;
		}

		_available = true;

		JSONArray computerJSONArray = computerJSONObject.getJSONArray(
			"computer");

		for (int i = 0; i < computerJSONArray.length(); i++) {
			JSONObject curComputerJSONObject = computerJSONArray.getJSONObject(
				i);

			String jenkinsSlaveName = curComputerJSONObject.getString(
				"displayName");

			if (jenkinsSlaveName.equals("master")) {
				continue;
			}

			JenkinsSlave jenkinsSlave = _jenkinsSlavesMap.get(jenkinsSlaveName);

			if (jenkinsSlave != null) {
				jenkinsSlave.update(curComputerJSONObject);
			}
			else {
				jenkinsSlave = new JenkinsSlave(this, curComputerJSONObject);

				_jenkinsSlavesMap.put(jenkinsSlave.getName(), jenkinsSlave);
			}
		}

		_queueCount = 0;

		if (!queueJSONObject.has("items")) {
			return;
		}

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

				if (why.startsWith("There are no nodes") ||
					why.contains("already in progress") ||
					why.endsWith("is offline")) {

					continue;
				}
			}

			_queueCount++;
		}
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
	private final Map<String, JenkinsSlave> _jenkinsSlavesMap = new HashMap<>();
	private final String _masterName;
	private final String _masterURL;
	private int _queueCount;
	private int _reportedAvailableSlavesCount;
	private final Integer _slaveRAM;

}