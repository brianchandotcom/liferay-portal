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
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseDownstreamBuildReport
	extends BaseBuildReport implements DownstreamBuildReport {

	@Override
	public String getAxisName() {
		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		return buildReportJSONObject.optString("axisName", null);
	}

	@Override
	public String getBatchName() {
		return _batchName;
	}

	@Override
	public long getOverheadDuration() {
		long overheadDuration = getDuration() - getTestExecutionDuration();

		if (overheadDuration <= 0L) {
			return 0L;
		}

		return overheadDuration;
	}

	@Override
	public List<TestClassReport> getTestClassReports() {
		if (_testClassReportsMap != null) {
			return new ArrayList<>(_testClassReportsMap.values());
		}

		_testClassReportsMap = new TreeMap<>();

		String batchName = getBatchName();

		for (TestReport testReport : getTestReports()) {
			String testClassName = testReport.getTestName();

			if (batchName.startsWith("integration") ||
				batchName.startsWith("modules-integration") ||
				batchName.startsWith("modules-unit") ||
				batchName.startsWith("unit")) {

				Matcher matcher = _jUnitTestNamePattern.matcher(testClassName);

				if (matcher.find()) {
					testClassName = matcher.group("testClassName");
				}
			}

			TestClassReport testClassReport = _testClassReportsMap.get(
				testClassName);

			if (testClassReport == null) {
				testClassReport = TestReportFactory.newTestClassReport(
					this, testClassName);

				_testClassReportsMap.put(testClassName, testClassReport);
			}

			testClassReport.addTestReport(testReport);
		}

		return new ArrayList<>(_testClassReportsMap.values());
	}

	@Override
	public long getTestExecutionDuration() {
		StopWatchRecordsGroup stopWatchRecordsGroup =
			getStopWatchRecordsGroup();

		if (stopWatchRecordsGroup != null) {
			StopWatchRecord stopWatchRecord = stopWatchRecordsGroup.get(
				"test.execution.duration");

			if (stopWatchRecord != null) {
				long duration = stopWatchRecord.getDuration();

				if (duration > 0L) {
					return duration;
				}
			}
		}

		long testExecutionDuration = 0L;

		for (TestReport testReport : getTestReports()) {
			long testDuration = testReport.getDuration();

			if (testDuration < 0L) {
				continue;
			}

			testExecutionDuration += testDuration;
		}

		return testExecutionDuration;
	}

	@Override
	public List<TestReport> getTestReports() {
		List<TestReport> testReports = new ArrayList<>();

		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		JSONArray testResultsJSONArray = buildReportJSONObject.optJSONArray(
			"testResults");

		if (testResultsJSONArray == null) {
			return testReports;
		}

		for (int i = 0; i < testResultsJSONArray.length(); i++) {
			testReports.add(
				TestReportFactory.newTestReport(
					this, testResultsJSONArray.getJSONObject(i)));
		}

		return testReports;
	}

	@Override
	public TopLevelBuildReport getTopLevelBuildReport() {
		return _topLevelBuildReport;
	}

	protected BaseDownstreamBuildReport(
		String batchName, JSONObject buildReportJSONObject,
		TopLevelBuildReport topLevelBuildReport) {

		super(buildReportJSONObject);

		_batchName = batchName;
		_topLevelBuildReport = topLevelBuildReport;
	}

	private static final Pattern _jUnitTestNamePattern = Pattern.compile(
		"(?<testClassName>.*Test)\\.(?<testName>test.*)");

	private final String _batchName;
	private Map<String, TestClassReport> _testClassReportsMap;
	private final TopLevelBuildReport _topLevelBuildReport;

}