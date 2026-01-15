/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestClassHistory implements TestClassHistory {

	public long getAverageDuration() {
		return _averageDuration;
	}

	public long getAverageOverheadDuration() {
		return _averageOverheadDuration;
	}

	public BatchHistory getBatchHistory() {
		return _batchHistory;
	}

	@Override
	public String getBatchName() {
		return _batchHistory.getBatchName();
	}

	public int getFailureCount() {
		return _failureCount;
	}

	public int getStatusChanges() {
		return _statusChanges;
	}

	public String getTestClassName() {
		return _testClassName;
	}

	public long getTestCount() {
		return _testCount;
	}

	public TestTaskHistory getTestTaskHistory() {
		return _batchHistory.getTestTaskHistory(getTestTaskName());
	}

	public String getTestTaskName() {
		return _testTaskName;
	}

	@Override
	public boolean isFlaky() {
		return false;
	}

	protected BaseTestClassHistory(
		BatchHistory batchHistory, JSONObject jsonObject) {

		_batchHistory = batchHistory;

		_averageDuration = jsonObject.optLong("averageDuration");
		_averageOverheadDuration = jsonObject.optLong(
			"averageOverheadDuration");
		_failureCount = jsonObject.optInt("failureCount");
		_statusChanges = jsonObject.optInt("statusChanges");
		_testClassName = jsonObject.getString("testName");
		_testCount = jsonObject.optInt("testCount");
		_testTaskName = jsonObject.optString("testTaskName");
	}

	private final long _averageDuration;
	private final long _averageOverheadDuration;
	private final BatchHistory _batchHistory;
	private final int _failureCount;
	private final int _statusChanges;
	private final String _testClassName;
	private final int _testCount;
	private final String _testTaskName;

}