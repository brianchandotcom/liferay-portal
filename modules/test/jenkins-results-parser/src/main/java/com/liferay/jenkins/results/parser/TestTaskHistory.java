/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestTaskHistory {

	public long getAverageDuration() {
		return _averageDuration;
	}

	public long getAverageTotalDuration() {
		return _averageTotalDuration;
	}

	public BatchHistory getBatchHistory() {
		return _batchHistory;
	}

	public long getLongestDuration() {
		return _longestDuration;
	}

	public long getTestTaskCount() {
		return _testTaskCount;
	}

	public String getTestTaskName() {
		return _testTaskName;
	}

	protected TestTaskHistory(
		BatchHistory batchHistory, JSONObject jsonObject) {

		_batchHistory = batchHistory;

		_averageDuration = jsonObject.optLong("averageDuration");
		_averageTotalDuration = jsonObject.optLong("averageTotalDuration");
		_longestDuration = jsonObject.optLong("longestDuration");
		_testTaskCount = jsonObject.optInt("testTaskCount");
		_testTaskName = jsonObject.optString("testTaskName");
	}

	private final long _averageDuration;
	private final long _averageTotalDuration;
	private final BatchHistory _batchHistory;
	private final long _longestDuration;
	private final int _testTaskCount;
	private final String _testTaskName;

}