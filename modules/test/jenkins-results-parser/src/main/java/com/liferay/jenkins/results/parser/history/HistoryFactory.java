/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.testray.TestrayRoutine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class HistoryFactory {

	public static BatchHistory newBatchHistory(
		String batchName, JobHistory jobHistory, JSONObject jsonObject) {

		if (jobHistory == null) {
			throw new RuntimeException("Job History is null");
		}

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

		_batchHistories.put(batchName, batchHistory);

		return batchHistory;
	}

	public static JobHistory newJobHistory(
		int maxBuildCount, String portalUpstreamBranchName,
		List<TestrayRoutine> testrayRoutines) {

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalUpstreamBranchName)) {
			return null;
		}

		JobHistory jobHistory = _jobHistories.get(portalUpstreamBranchName);

		if (jobHistory != null) {
			return jobHistory;
		}

		if ((testrayRoutines != null) && !testrayRoutines.isEmpty()) {
			jobHistory = new TestrayJobHistory(
				maxBuildCount, portalUpstreamBranchName, testrayRoutines);
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
		BatchHistory batchHistory, JSONObject jsonObject,
		String testClassName) {

		if (batchHistory == null) {
			throw new RuntimeException("Batch History is null");
		}

		TestClassHistory testClassHistory = _testClassHistories.get(
			testClassName);

		if (testClassHistory != null) {
			return testClassHistory;
		}

		if (batchHistory instanceof TestrayBatchHistory) {
			testClassHistory = new TestrayTestClassHistory(
				batchHistory, testClassName);
		}
		else {
			testClassHistory = new CachedTestClassHistory(
				batchHistory, jsonObject, testClassName);
		}

		return testClassHistory;
	}

	public static TestTaskHistory newTestTaskHistory(
		BatchHistory batchHistory, JSONObject jsonObject, String testTaskName) {

		if (batchHistory == null) {
			throw new RuntimeException("Batch History is null");
		}

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

		_testTaskHistories.put(testTaskName, testTaskHistory);

		return testTaskHistory;
	}

	private static final Map<String, BatchHistory> _batchHistories =
		new HashMap<>();
	private static final Map<String, JobHistory> _jobHistories =
		new HashMap<>();
	private static final Map<String, TestClassHistory> _testClassHistories =
		new HashMap<>();
	private static final Map<String, TestTaskHistory> _testTaskHistories =
		new HashMap<>();

}