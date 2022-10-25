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

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BatchHistory {

	public long getAverageDuration() {
		return _averageDuration;
	}

	public String getBatchName() {
		return _batchName;
	}

	public JobHistory getJobHistory() {
		return _jobHistory;
	}

	public TestHistory getTestHistory(String key) {
		return _testHistories.get(key);
	}

	protected BatchHistory(JobHistory jobHistory, JSONObject jsonObject) {
		_jobHistory = jobHistory;

		_averageDuration = jsonObject.optLong("averageDuration");
		_batchName = jsonObject.getString("batchName");

		JSONArray testsJSONArray = jsonObject.optJSONArray("tests");

		if ((testsJSONArray == JSONObject.NULL) || testsJSONArray.isEmpty()) {
			return;
		}

		for (int i = 0; i < testsJSONArray.length(); i++) {
			TestHistory testHistory = new TestHistory(
				this, testsJSONArray.getJSONObject(i));

			_testHistories.put(testHistory.getTestName(), testHistory);
		}
	}

	private final long _averageDuration;
	private final String _batchName;
	private final JobHistory _jobHistory;
	private final Map<String, TestHistory> _testHistories = new HashMap<>();

}