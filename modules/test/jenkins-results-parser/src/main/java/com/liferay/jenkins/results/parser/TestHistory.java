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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestHistory {

	public long getAverageDuration() {
		return _averageDuration;
	}

	public long getAverageOverheadDuration() {
		return _averageOverheadDuration;
	}

	public BatchHistory getBatchHistory() {
		return _batchHistory;
	}

	public int getFailureCount() {
		return _failureCount;
	}

	public int getStatusChanges() {
		return _statusChanges;
	}

	public long getTestCount() {
		return _testCount;
	}

	public String getTestName() {
		return _testName;
	}

	public long getTestrayCaseResultID() {
		return _testrayCaseResultID;
	}

	public String getTestrayCaseResultURL() {
		long testrayCaseResultID = getTestrayCaseResultID();

		if (testrayCaseResultID <= 0L) {
			return null;
		}

		BatchHistory batchHistory = getBatchHistory();

		if (batchHistory == null) {
			return null;
		}

		JobHistory jobHistory = batchHistory.getJobHistory();

		if (jobHistory == null) {
			return null;
		}

		return JenkinsResultsParserUtil.combine(
			String.valueOf(jobHistory.getTestrayURL()),
			"/home/-/testray/case_results/",
			String.valueOf(testrayCaseResultID), "/history");
	}

	protected TestHistory(BatchHistory batchHistory, JSONObject jsonObject) {
		_batchHistory = batchHistory;

		_averageDuration = jsonObject.optLong("averageDuration");
		_averageOverheadDuration = jsonObject.optLong(
			"averageOverheadDuration");
		_failureCount = jsonObject.optInt("failureCount");
		_statusChanges = jsonObject.optInt("statusChanges");
		_testName = jsonObject.getString("testName");
		_testCount = jsonObject.optInt("testCount");
		_testrayCaseResultID = jsonObject.optLong("testrayCaseResultID");
	}

	private final long _averageDuration;
	private final long _averageOverheadDuration;
	private final BatchHistory _batchHistory;
	private final int _failureCount;
	private final int _statusChanges;
	private final int _testCount;
	private final String _testName;
	private final long _testrayCaseResultID;

}