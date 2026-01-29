/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBatchHistory implements BatchHistory {

	@Override
	public String getBatchName() {
		return _batchName;
	}

	@Override
	public JobHistory getJobHistory() {
		return _jobHistory;
	}

	@Override
	public String getPortalUpstreamBranchName() {
		JobHistory jobHistory = getJobHistory();

		if (jobHistory == null) {
			return null;
		}

		return jobHistory.getPortalUpstreamBranchName();
	}

	@Override
	public List<TestClassHistory> getTestClassHistories() {
		return new ArrayList<>(_testClassHistories.values());
	}

	@Override
	public TestClassHistory getTestClassHistory(String key) {
		return _testClassHistories.get(key);
	}

	@Override
	public List<TestTaskHistory> getTestTaskHistories() {
		return new ArrayList<>(_testTaskHistories.values());
	}

	@Override
	public TestTaskHistory getTestTaskHistory(String key) {
		return _testTaskHistories.get(key);
	}

	protected BaseBatchHistory(String batchName, JobHistory jobHistory) {
		_batchName = batchName;
		_jobHistory = jobHistory;
	}

	protected void addTestClassHistory(TestClassHistory testClassHistory) {
		if (testClassHistory == null) {
			return;
		}

		_testClassHistories.put(
			testClassHistory.getTestClassName(), testClassHistory);
	}

	protected void addTestTaskHistory(TestTaskHistory testTaskHistory) {
		if (testTaskHistory == null) {
			return;
		}

		_testTaskHistories.put(
			testTaskHistory.getTestTaskName(), testTaskHistory);
	}

	private final String _batchName;
	private final JobHistory _jobHistory;
	private final Map<String, TestClassHistory> _testClassHistories =
		new HashMap<>();
	private final Map<String, TestTaskHistory> _testTaskHistories =
		new HashMap<>();

}