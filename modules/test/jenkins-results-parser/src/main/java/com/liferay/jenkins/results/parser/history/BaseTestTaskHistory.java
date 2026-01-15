/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestTaskHistory implements TestTaskHistory {

	@Override
	public long getAverageDuration() {
		return _averageDuration;
	}

	@Override
	public long getAverageTotalDuration() {
		return _averageTotalDuration;
	}

	@Override
	public BatchHistory getBatchHistory() {
		return _batchHistory;
	}

	@Override
	public long getLongestDuration() {
		return _longestDuration;
	}

	@Override
	public long getTestTaskCount() {
		return _testTaskCount;
	}

	@Override
	public String getTestTaskName() {
		return _testTaskName;
	}

	@Override
	public boolean isLatestReportMissing() {
		return _latestReportMissing;
	}

	@Override
	public void setLatestReportMissing(boolean latestReportMissing) {
		_latestReportMissing = latestReportMissing;
	}

	protected BaseTestTaskHistory(
		BatchHistory batchHistory, JSONObject jsonObject) {

		_batchHistory = batchHistory;

		_averageDuration = jsonObject.optLong("averageDuration");
		_latestReportMissing = jsonObject.optBoolean("latestReportMissing");
		_averageTotalDuration = jsonObject.optLong("averageTotalDuration");
		_longestDuration = jsonObject.optLong("longestDuration");
		_testTaskCount = jsonObject.optInt("testTaskCount");
		_testTaskName = jsonObject.optString("testTaskName");
	}

	private final long _averageDuration;
	private final long _averageTotalDuration;
	private final BatchHistory _batchHistory;
	private boolean _latestReportMissing;
	private final long _longestDuration;
	private final int _testTaskCount;
	private final String _testTaskName;

}