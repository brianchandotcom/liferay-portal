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
import java.util.Random;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class JenkinsMaster implements Comparable<JenkinsMaster> {

	public JenkinsMaster(String masterName, String masterURL) {
		_masterName = masterName;
		_masterURL = masterURL;
		_recentBatchesMap = new TreeMap<>();
	}

	public void addRecentBatch(int batchSize) {
		_recentBatchesMap.put(
			System.currentTimeMillis() + maxRecentBatchAge, batchSize);

		getAvailableSlavesCount();
	}

	@Override
	public int compareTo(JenkinsMaster otherJenkinsMaster) {
		Integer availableSlavesCount = getAvailableSlavesCount();
		Integer otherAvailableSlavesCount =
			otherJenkinsMaster.getAvailableSlavesCount();

		int availableSlavesCountCompareToResult =
			-1 * (availableSlavesCount.compareTo(otherAvailableSlavesCount));

		if (availableSlavesCountCompareToResult != 0) {
			return availableSlavesCountCompareToResult;
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
		int totalRecentBatchSizes = _getTotalRecentBatcheSizes();

		int availableSlavesCount =
			_reportedSlavesAvailable - totalRecentBatchSizes;

		StringBuilder sb = new StringBuilder();

		sb.append("{available=");
		sb.append(availableSlavesCount);
		sb.append(", reportedSlavesAvailable=");
		sb.append(_reportedSlavesAvailable);
		sb.append(", recentBatchSizesTotal=");
		sb.append(totalRecentBatchSizes);
		sb.append(", url=");
		sb.append(_masterURL);
		sb.append("}");

		_summary = sb.toString();

		return availableSlavesCount;
	}

	public String getMasterName() {
		return _masterName;
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

		JSONArray computersJSONArray = computerJSONObject.getJSONArray(
			"computer");

		for (int i = 0; i < computersJSONArray.length(); i++) {
			JSONObject curComputerJSONObject = computersJSONArray.getJSONObject(
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

				if (itemJSONObject.has("why")) {
					String why = itemJSONObject.getString("why");

					if (why.endsWith("is offline")) {
						continue;
					}
				}

				if (itemJSONObject.has("task")) {
					JSONObject taskJSONObject = itemJSONObject.getJSONObject(
						"task");

					String taskName = taskJSONObject.getString("name");

					if (taskName.equals("verification-node")) {
						continue;
					}
				}

				queueCount++;
			}
		}

		_reportedSlavesAvailable = idleCount - queueCount;

		getAvailableSlavesCount();
	}

	protected static long maxRecentBatchAge = 120 * 1000;

	private int _getTotalRecentBatcheSizes() {
		long currentTimestamp = System.currentTimeMillis();
		int totalRecentBatchesSize = 0;

		List<Long> expiredTimestamps = new ArrayList<>(
			_recentBatchesMap.size());

		for (long expiryTimestamp : _recentBatchesMap.keySet()) {
			if (expiryTimestamp < currentTimestamp) {
				expiredTimestamps.add(expiryTimestamp);

				continue;
			}

			totalRecentBatchesSize += _recentBatchesMap.get(expiryTimestamp);
		}

		for (Long expiredTimestamp : expiredTimestamps) {
			_recentBatchesMap.remove(expiredTimestamp);
		}

		return totalRecentBatchesSize;
	}

	private boolean _available;
	private final String _masterName;
	private final String _masterURL;
	private final Map<Long, Integer> _recentBatchesMap;
	private int _reportedSlavesAvailable;
	private String _summary;

}