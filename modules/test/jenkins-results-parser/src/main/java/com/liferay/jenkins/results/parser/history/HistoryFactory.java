/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import com.liferay.jenkins.results.parser.testray.TestrayRoutine;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class HistoryFactory {

	public static BatchHistory newBatchHistory(
		String batchName, JobHistory jobHistory, JSONObject jsonObject) {

		BatchHistory batchHistory = _batchHistories.get(batchName);

		if (batchHistory != null) {
			return batchHistory;
		}

		if (jobHistory instanceof TestrayJobHistory) {
			batchHistory = new TestrayBatchHistory(batchName, jobHistory);
		}
		else {
			batchHistory = new CachedBatchHistory(
				batchName, jobHistory, jsonObject);
		}

		return batchHistory;
	}

	public static JobHistory newJobHistory(
		int maxBuildCount, String portalUpstreamBranchName,
		TestrayRoutine testrayRoutine) {

		JobHistory jobHistory = _jobHistories.get(portalUpstreamBranchName);

		if (jobHistory != null) {
			return jobHistory;
		}

		if (testrayRoutine != null) {
			jobHistory = new TestrayJobHistory(
				maxBuildCount, portalUpstreamBranchName, testrayRoutine);
		}
		else {
			jobHistory = new CachedJobHistory(portalUpstreamBranchName);
		}

		_jobHistories.put(portalUpstreamBranchName, jobHistory);

		return jobHistory;
	}

	public static JobHistory newJobHistory(String portalUpstreamBranchName) {
		return newJobHistory(0, portalUpstreamBranchName, null);
	}

	public static TestClassHistory newTestClassHistory(
		BatchHistory batchHistory, JSONObject jsonObject) {

		return new DefaultTestClassHistory(batchHistory, jsonObject);
	}

	public static TestTaskHistory newTestTaskHistory(
		BatchHistory batchHistory, JSONObject jsonObject, String testTaskName) {

		TestTaskHistory testTaskHistory = _testTaskHistories.get(testTaskName);

		if (testTaskHistory != null) {
			return testTaskHistory;
		}

		if (batchHistory instanceof TestrayBatchHistory) {
			testTaskHistory = new TestrayTestTaskHistory(
				batchHistory, testTaskName);
		}
		else {
			testTaskHistory = new CachedTestTaskHistory(
				batchHistory, jsonObject, testTaskName);
		}

		return testTaskHistory;
	}

	private static final Map<String, BatchHistory> _batchHistories =
		new HashMap<>();
	private static final Map<String, JobHistory> _jobHistories =
		new HashMap<>();
	private static final Map<String, TestTaskHistory> _testTaskHistories =
		new HashMap<>();

}