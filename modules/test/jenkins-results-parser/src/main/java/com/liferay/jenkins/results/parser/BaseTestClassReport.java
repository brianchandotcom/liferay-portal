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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestClassReport implements TestClassReport {

	@Override
	public void addTestReport(TestReport testReport) {
		_testReports.add(testReport);
	}

	@Override
	public DownstreamBuildReport getDownstreamBuildReport() {
		return _downstreamBuildReport;
	}

	public long getDuration() {
		long duration = 0L;

		for (TestReport testReport : getTestReports()) {
			long testReportDuration = testReport.getDuration();

			if (testReportDuration < 0L) {
				continue;
			}

			duration += testReportDuration;
		}

		return duration;
	}

	@Override
	public long getOverheadDuration() {
		DownstreamBuildReport downstreamBuildReport =
			getDownstreamBuildReport();

		StopWatchRecordsGroup stopWatchRecordsGroup =
			downstreamBuildReport.getStopWatchRecordsGroup();

		if (stopWatchRecordsGroup == null) {
			return 0L;
		}

		StopWatchRecord stopWatchRecord = stopWatchRecordsGroup.get(
			"test.execution.duration");

		if (stopWatchRecord == null) {
			return 0L;
		}

		long testExecutionDuration = stopWatchRecord.getDuration();

		if (testExecutionDuration <= 0L) {
			return 0L;
		}

		List<TestClassReport> testClassReports =
			downstreamBuildReport.getTestClassReports();

		if (testClassReports.isEmpty()) {
			return 0L;
		}

		return testExecutionDuration / testClassReports.size();
	}

	@Override
	public String getStatus() {
		for (TestReport testReport : getTestReports()) {
			String status = testReport.getStatus();

			if (status.equals("REGRESSION") || status.equals("FAILED")) {
				return "FAILED";
			}

			if (status.equals("FIXED") || status.equals("PASSED") ||
				status.equals("SKIPPED")) {

				continue;
			}

			throw new RuntimeException("Invalid status " + status);
		}

		return "PASSED";
	}

	@Override
	public String getTestClassName() {
		return _testClassName;
	}

	@Override
	public List<TestReport> getTestReports() {
		return _testReports;
	}

	protected BaseTestClassReport(
		DownstreamBuildReport downstreamBuildReport, String testClassName) {

		_downstreamBuildReport = downstreamBuildReport;
		_testClassName = testClassName;
	}

	private final DownstreamBuildReport _downstreamBuildReport;
	private final String _testClassName;
	private final List<TestReport> _testReports = new ArrayList<>();

}