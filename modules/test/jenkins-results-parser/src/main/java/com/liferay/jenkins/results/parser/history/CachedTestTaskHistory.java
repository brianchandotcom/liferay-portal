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
		return _jsonObject.optLong("averageDuration");
	}

	@Override
	public long getAverageTotalDuration() {
		return _jsonObject.optLong("averageTotalDuration");
	}

	@Override
	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	@Override
	public long getLongestDuration() {
		return _jsonObject.optLong("longestDuration");
	}

	@Override
	public long getTestTaskCount() {
		return _jsonObject.optInt("testTaskCount");
	}

	protected CachedTestTaskHistory(
		BatchHistory batchHistory, JSONObject jsonObject, String testTaskName) {

		super(batchHistory, testTaskName);

		_jsonObject = jsonObject;

		setLatestReportMissing(jsonObject.optBoolean("latestReportMissing"));
	}

	private final JSONObject _jsonObject;

}