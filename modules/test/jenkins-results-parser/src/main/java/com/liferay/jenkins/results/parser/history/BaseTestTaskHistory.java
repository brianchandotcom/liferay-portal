/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestTaskHistory implements TestTaskHistory {

	@Override
	public BatchHistory getBatchHistory() {
		return _batchHistory;
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
		BatchHistory batchHistory, String testTaskName) {

		_batchHistory = batchHistory;
		_testTaskName = testTaskName;
	}

	private final BatchHistory _batchHistory;
	private boolean _latestReportMissing;
	private final String _testTaskName;

}