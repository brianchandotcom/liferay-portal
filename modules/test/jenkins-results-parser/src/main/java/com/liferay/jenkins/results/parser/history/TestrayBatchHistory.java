/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import com.liferay.jenkins.results.parser.DownstreamBuildReport;
import com.liferay.jenkins.results.parser.ModulesJUnitDownstreamBuildReport;
import com.liferay.jenkins.results.parser.StopWatchRecord;
import com.liferay.jenkins.results.parser.StopWatchRecordsGroup;
import com.liferay.jenkins.results.parser.TestClassReport;
import com.liferay.jenkins.results.parser.TestTaskReport;
import com.liferay.jenkins.results.parser.TestTaskReportFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayBatchHistory extends BaseBatchHistory {

	public void addBuildReport(DownstreamBuildReport downstreamBuildReport) {
		if ((downstreamBuildReport == null) ||
			_downstreamBuildReports.contains(downstreamBuildReport)) {

			return;
		}

		_downstreamBuildReports.add(downstreamBuildReport);

		for (TestClassReport testClassReport :
				downstreamBuildReport.getTestClassReports()) {

			String testClassName = testClassReport.getTestClassName();

			TestClassHistory testClassHistory = getTestClassHistory(
				testClassName);

			if (testClassHistory == null) {
				testClassHistory = HistoryFactory.newTestClassHistory(
					this, null, testClassName);

				addTestClassHistory(testClassHistory);
			}

			if (!(testClassHistory instanceof TestrayTestClassHistory)) {
				continue;
			}

			TestrayTestClassHistory testrayTestClassHistory =
				(TestrayTestClassHistory)testClassHistory;

			testrayTestClassHistory.addTestClassReport(testClassReport);
		}

		for (TestTaskReport testTaskReport :
				_getTestTaskReports(downstreamBuildReport)) {

			String testTaskName = testTaskReport.getName();

			TestTaskHistory testTaskHistory = getTestTaskHistory(testTaskName);

			if (testTaskHistory == null) {
				testTaskHistory = HistoryFactory.newTestTaskHistory(
					this, null, testTaskName);

				addTestTaskHistory(testTaskHistory);
			}

			if (!(testTaskHistory instanceof TestrayTestTaskHistory)) {
				continue;
			}

			TestrayTestTaskHistory testrayTestTaskHistory =
				(TestrayTestTaskHistory)testTaskHistory;

			testrayTestTaskHistory.addTestTaskReport(testTaskReport);
		}
	}

	@Override
	public long getAverageDuration() {
		long count = 0;
		long totalDuration = 0;

		for (DownstreamBuildReport downstreamBuildReport :
				_downstreamBuildReports) {

			long duration = downstreamBuildReport.getDuration();

			if (duration > _MAXIMUM_BATCH_DURATION) {
				continue;
			}

			count++;
			totalDuration = totalDuration + duration;
		}

		if (count == 0) {
			return 0;
		}

		return totalDuration / count;
	}

	protected TestrayBatchHistory(String batchName, JobHistory jobHistory) {
		super(batchName, jobHistory);
	}

	private List<TestTaskReport> _getTestTaskReports(
		DownstreamBuildReport downstreamBuildReport) {

		if (!(downstreamBuildReport instanceof
				ModulesJUnitDownstreamBuildReport)) {

			return new ArrayList<>();
		}

		ModulesJUnitDownstreamBuildReport modulesJUnitDownstreamBuildReport =
			(ModulesJUnitDownstreamBuildReport)downstreamBuildReport;

		List<TestTaskReport> testTaskReports =
			modulesJUnitDownstreamBuildReport.getTestTaskReports();

		if ((testTaskReports != null) && !testTaskReports.isEmpty()) {
			return testTaskReports;
		}

		testTaskReports = new ArrayList<>();

		StopWatchRecordsGroup stopWatchRecordsGroup =
			downstreamBuildReport.getStopWatchRecordsGroup();

		for (StopWatchRecord stopWatchRecord :
				stopWatchRecordsGroup.getAllStopWatchRecords()) {

			Matcher matcher = _stopWatchGroupTestTaskNamePattern.matcher(
				stopWatchRecord.getName());

			if (!matcher.find()) {
				continue;
			}

			String testTaskName = matcher.group("testTaskName");

			JSONObject jsonObject = new JSONObject();

			jsonObject.put(
				"duration", stopWatchRecord.getDuration()
			).put(
				"name", testTaskName.replaceAll("\\.", ":")
			);

			TestTaskReport testTaskReport =
				TestTaskReportFactory.newTestTaskReport(
					downstreamBuildReport, jsonObject);

			if (testTaskReports.contains(testTaskReport)) {
				continue;
			}

			testTaskReports.add(testTaskReport);
		}

		return testTaskReports;
	}

	private static final long _MAXIMUM_BATCH_DURATION = 24 * 60 * 60 * 1000;

	private static final Pattern _stopWatchGroupTestTaskNamePattern =
		Pattern.compile("test\\.execution\\.duration(?<testTaskName>\\..+)");

	private final List<DownstreamBuildReport> _downstreamBuildReports =
		new ArrayList<>();

}