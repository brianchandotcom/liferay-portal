/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CachedBatchHistory extends BaseBatchHistory {

	@Override
	public long getAverageDuration() {
		return _averageDuration;
	}

	protected CachedBatchHistory(
		String batchName, JobHistory jobHistory, JSONObject jsonObject) {

		super(batchName, jobHistory);

		_averageDuration = jsonObject.optLong("averageDuration");

		JSONArray testsJSONArray = jsonObject.optJSONArray("tests");

		if ((testsJSONArray != null) && !testsJSONArray.isEmpty()) {
			for (int i = 0; i < testsJSONArray.length(); i++) {
				JSONObject testJSONObject = testsJSONArray.getJSONObject(i);

				String testClassName = testJSONObject.optString(
					"testClassName");

				if (JenkinsResultsParserUtil.isNullOrEmpty(testClassName)) {
					testClassName = testJSONObject.optString("testName");
				}

				addTestClassHistory(
					HistoryFactory.newTestClassHistory(
						this, testJSONObject, testClassName));
			}
		}

		JSONArray testTasksJSONArray = jsonObject.optJSONArray("testTasks");

		if ((testTasksJSONArray != null) && !testTasksJSONArray.isEmpty()) {
			for (int i = 0; i < testTasksJSONArray.length(); i++) {
				JSONObject testTaskJSONObject =
					testTasksJSONArray.getJSONObject(i);

				addTestTaskHistory(
					HistoryFactory.newTestTaskHistory(
						this, testTaskJSONObject,
						testTaskJSONObject.getString("testTaskName")));
			}
		}
	}

	private final long _averageDuration;

}