/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CachedTestTaskHistory extends BaseTestTaskHistory {

	@Override
	public long getAverageDuration() {
		return _averageDuration;
	}

	@Override
	public long getAverageTotalDuration() {
		return _averageTotalDuration;
	}

	@Override
	public long getLongestDuration() {
		return _longestDuration;
	}

	@Override
	public long getTestTaskCount() {
		return _testTaskCount;
	}

	protected CachedTestTaskHistory(
		BatchHistory batchHistory, JSONObject jsonObject, String testTaskName) {

		super(batchHistory, testTaskName);

		_averageDuration = jsonObject.optLong("averageDuration");
		_averageTotalDuration = jsonObject.optLong("averageTotalDuration");
		_longestDuration = jsonObject.optLong("longestDuration");
		_testTaskCount = jsonObject.optInt("testTaskCount");

		setLatestReportMissing(jsonObject.optBoolean("latestReportMissing"));
	}

	private final long _averageDuration;
	private final long _averageTotalDuration;
	private final long _longestDuration;
	private final int _testTaskCount;

}