/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CachedTestClassHistory extends BaseTestClassHistory {

	@Override
	public long getAverageDuration() {
		return _averageDuration;
	}

	@Override
	public long getAverageOverheadDuration() {
		return _averageOverheadDuration;
	}

	@Override
	public int getFailureCount() {
		return _failureCount;
	}

	@Override
	public int getStatusChanges() {
		return _statusChanges;
	}

	@Override
	public long getTestCount() {
		return _testCount;
	}

	@Override
	public String getTestTaskName() {
		return _testTaskName;
	}

	@Override
	public boolean isFlaky() {
		return false;
	}

	protected CachedTestClassHistory(
		BatchHistory batchHistory, JSONObject jsonObject,
		String testClassName) {

		super(batchHistory, testClassName);

		_averageDuration = jsonObject.optLong("averageDuration");
		_averageOverheadDuration = jsonObject.optLong(
			"averageOverheadDuration");
		_failureCount = jsonObject.optInt("failureCount");
		_statusChanges = jsonObject.optInt("statusChanges");
		_testCount = jsonObject.optInt("testCount");
		_testTaskName = jsonObject.optString("testTaskName");
	}

	private final long _averageDuration;
	private final long _averageOverheadDuration;
	private final int _failureCount;
	private final int _statusChanges;
	private final int _testCount;
	private final String _testTaskName;

}