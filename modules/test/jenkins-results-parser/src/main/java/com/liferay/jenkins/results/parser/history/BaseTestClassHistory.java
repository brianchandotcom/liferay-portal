/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestClassHistory implements TestClassHistory {

	@Override
	public BatchHistory getBatchHistory() {
		return _batchHistory;
	}

	@Override
	public String getBatchName() {
		return _batchHistory.getBatchName();
	}

	@Override
	public String getPortalUpstreamBranchName() {
		BatchHistory batchHistory = getBatchHistory();

		if (batchHistory == null) {
			return null;
		}

		return batchHistory.getPortalUpstreamBranchName();
	}

	@Override
	public String getTestClassName() {
		return _testClassName;
	}

	@Override
	public TestTaskHistory getTestTaskHistory() {
		return _batchHistory.getTestTaskHistory(getTestTaskName());
	}

	protected BaseTestClassHistory(
		BatchHistory batchHistory, String testClassName) {

		_batchHistory = batchHistory;
		_testClassName = testClassName;
	}

	private final BatchHistory _batchHistory;
	private final String _testClassName;

}