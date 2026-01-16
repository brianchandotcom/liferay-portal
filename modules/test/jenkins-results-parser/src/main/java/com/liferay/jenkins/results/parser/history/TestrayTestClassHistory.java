/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import com.liferay.jenkins.results.parser.DownstreamBuildReport;
import com.liferay.jenkins.results.parser.TestClassReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Michael Hashimoto
 */
public class TestrayTestClassHistory extends BaseTestClassHistory {

	public void addTestClassReport(TestClassReport testClassReport) {
		if ((testClassReport == null) ||
			_testClassReports.contains(testClassReport)) {

			return;
		}

		_testClassReports.add(testClassReport);
	}

	@Override
	public long getAverageDuration() {
		long count = 0;
		long totalDuration = 0;

		for (TestClassReport testClassReport : _testClassReports) {
			DownstreamBuildReport downstreamBuildReport =
				testClassReport.getDownstreamBuildReport();

			long duration = testClassReport.getDuration();

			if ((duration <= 0) || (duration >= _MAXIMUM_TEST_DURATION) ||
				(duration >= downstreamBuildReport.getDuration())) {

				continue;
			}

			count++;
			totalDuration += duration;
		}

		if (count == 0) {
			return 0;
		}

		return totalDuration / count;
	}

	@Override
	public long getAverageOverheadDuration() {
		long count = 0;
		long totalOverheadDuration = 0;

		for (TestClassReport testClassReport : _testClassReports) {
			long overheadDuration = testClassReport.getOverheadDuration();

			if (overheadDuration > _MAXIMUM_TEST_DURATION) {
				continue;
			}

			count++;
			totalOverheadDuration += overheadDuration;
		}

		if (count == 0) {
			return 0;
		}

		return totalOverheadDuration / count;
	}

	@Override
	public BatchHistory getBatchHistory() {
		return _batchHistory;
	}

	public String getBatchName() {
		return _batchHistory.getBatchName();
	}

	@Override
	public int getFailureCount() {
		int failureCount = 0;

		for (TestClassReport testClassReport : _testClassReports) {
			String status = _fixStatus(testClassReport.getStatus());

			if (!Objects.equals(status, "PASSED")) {
				failureCount++;
			}
		}

		return failureCount;
	}

	@Override
	public int getStatusChanges() {
		int statusChanges = 0;

		String lastStatus = null;

		for (TestClassReport testClassReport : _testClassReports) {
			String status = _fixStatus(testClassReport.getStatus());

			if (lastStatus == null) {
				lastStatus = status;

				continue;
			}

			if (!lastStatus.equals(status)) {
				lastStatus = status;

				statusChanges++;
			}
		}

		return statusChanges;
	}

	public String getTestClassName() {
		return _testClassName;
	}

	public List<TestClassReport> getTestClassReports() {
		return _testClassReports;
	}

	@Override
	public long getTestCount() {
		return _testClassReports.size();
	}

	@Override
	public TestTaskHistory getTestTaskHistory() {
		return null;
	}

	@Override
	public String getTestTaskName() {
		if (_testClassReports.isEmpty()) {
			return null;
		}

		TestClassReport testClassReport = _testClassReports.get(0);

		if (testClassReport == null) {
			return null;
		}

		return testClassReport.getTestTaskName();
	}

	@Override
	public boolean isFlaky() {
		if (getStatusChanges() >= _MINIMUM_STATUS_CHANGES) {
			return true;
		}

		return false;
	}

	protected TestrayTestClassHistory(
		BatchHistory batchHistory, String testClassName) {

		super(batchHistory, null);

		_batchHistory = batchHistory;
		_testClassName = testClassName;
	}

	private String _fixStatus(String status) {
		status = status.replace("REGRESSION", "FAILED");
		status = status.replace("FIXED", "PASSED");

		return status;
	}

	private static final long _MAXIMUM_TEST_DURATION = 2 * 60 * 60 * 1000;

	private static final int _MINIMUM_STATUS_CHANGES = 3;

	private final BatchHistory _batchHistory;
	private final String _testClassName;
	private final List<TestClassReport> _testClassReports = new ArrayList<>();

}