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

import com.liferay.jenkins.results.parser.testray.TestrayBuild;
import com.liferay.jenkins.results.parser.testray.TestrayCaseResult;
import com.liferay.jenkins.results.parser.testray.TestrayCaseType;
import com.liferay.jenkins.results.parser.testray.TestrayRoutine;
import com.liferay.jenkins.results.parser.testray.TestrayRun;
import com.liferay.jenkins.results.parser.testray.TestrayServer;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class TestHistoryMap {

	public TestHistoryMap(TestrayRoutine testrayRoutine, int maxBuildCount) {
		_testrayRoutine = testrayRoutine;

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		List<TestrayBuild> testrayBuilds = testrayRoutine.getTestrayBuilds();

		if (testrayBuilds.size() > maxBuildCount) {
			testrayBuilds = testrayBuilds.subList(0, maxBuildCount);
		}

		_latestTestrayBuild = testrayBuilds.get(0);

		for (TestrayBuild testrayBuild : testrayBuilds) {
			TopLevelBuildReport topLevelBuildReport =
				testrayBuild.getTopLevelBuildReport();

			if ((topLevelBuildReport == null) ||
				JenkinsResultsParserUtil.isNullOrEmpty(
					topLevelBuildReport.getResult())) {

				continue;
			}

			for (DownstreamBuildReport downstreamBuildReport :
					topLevelBuildReport.getDownstreamBuildReports()) {

				String batchName = downstreamBuildReport.getBatchName();

				BatchHistory batchHistory = _batchHistoryMap.get(batchName);

				if (batchHistory == null) {
					batchHistory = new BatchHistory(batchName);

					_batchHistoryMap.put(batchName, batchHistory);
				}

				batchHistory.addBuildReport(downstreamBuildReport);
			}
		}

		for (BatchHistory batchHistory : _batchHistoryMap.values()) {
			TestrayCaseType testrayCaseType = batchHistory.getTestrayCaseType();
			TestrayRun testrayRun = batchHistory.getTestrayRun();

			if ((testrayCaseType == null) || (testrayRun == null)) {
				continue;
			}

			List<TestrayCaseResult> testrayCaseResults =
				_latestTestrayBuild.getTestrayCaseResults(
					testrayCaseType, testrayRun);

			for (TestrayCaseResult testrayCaseResult : testrayCaseResults) {
				String testClassName = testrayCaseResult.getName();

				TestClassHistory testClassHistory =
					batchHistory.getTestClassHistory(testClassName);

				if (testClassHistory == null) {
					continue;
				}

				testClassHistory.setTestrayCaseResult(testrayCaseResult);
			}
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Test history map populated in ",
				JenkinsResultsParserUtil.toDurationString(
					JenkinsResultsParserUtil.getCurrentTimeMillis() - start)));
	}

	public TestrayBuild getLatestTestrayBuild() {
		return _latestTestrayBuild;
	}

	public void setMinimumStatusChanges(int minimumStatusChanges) {
		_minimumStatusChanges = minimumStatusChanges;
	}

	public void setMinimumTestDuration(long minimumTestDuration) {
		_minimumTestDuration = minimumTestDuration;
	}

	public void writeCIHistoryJSONObjectFile(String filePath)
		throws IOException {

		JSONArray batchesJSONArray = new JSONArray();

		for (BatchHistory batchHistory : _batchHistoryMap.values()) {
			JSONArray testsJSONArray = new JSONArray();

			for (TestClassHistory testClassHistory :
					batchHistory.getTestClassHistories()) {

				JSONObject testJSONObject = new JSONObject();

				testJSONObject.put(
					"averageDuration", testClassHistory.getAverageDuration());
				testJSONObject.put(
					"averageOverheadDuration",
					testClassHistory.getAverageOverheadDuration());
				testJSONObject.put(
					"failureCount", testClassHistory.getFailureCount());
				testJSONObject.put(
					"statusChanges", testClassHistory.getStatusChanges());
				testJSONObject.put(
					"testCount", testClassHistory.getTestCount());
				testJSONObject.put(
					"testName", testClassHistory.getTestClassName());

				TestrayCaseResult testrayCaseResult =
					testClassHistory.getTestrayCaseResult();

				if (testrayCaseResult != null) {
					testJSONObject.put(
						"testrayCaseResultID", testrayCaseResult.getID());
				}

				testsJSONArray.put(testJSONObject);
			}

			JSONObject batchJSONObject = new JSONObject();

			batchJSONObject.put(
				"averageDuration", batchHistory.getAverageDuration());
			batchJSONObject.put("batchName", batchHistory.getBatchName());
			batchJSONObject.put("tests", testsJSONArray);

			batchesJSONArray.put(batchJSONObject);
		}

		JSONObject ciHistoryJSONObject = new JSONObject();

		ciHistoryJSONObject.put("batches", batchesJSONArray);

		TestrayServer testrayServer = _latestTestrayBuild.getTestrayServer();

		ciHistoryJSONObject.put(
			"testray_url", String.valueOf(testrayServer.getURL()));

		ciHistoryJSONObject.put(
			"upstream_branch_name", _latestTestrayBuild.getPortalBranch());

		File file = new File(filePath);

		File tempFile = new File(
			file.getParentFile(),
			JenkinsResultsParserUtil.getDistinctTimeStamp());

		try {
			JenkinsResultsParserUtil.write(
				tempFile, ciHistoryJSONObject.toString());

			JenkinsResultsParserUtil.gzip(tempFile, file);
		}
		finally {
			if (tempFile.exists()) {
				JenkinsResultsParserUtil.delete(tempFile);
			}
		}
	}

	public void writeDurationDataJavaScriptFile(
			String filePath, String batchNameRegex)
		throws IOException {

		JSONArray durationDataJSONArray = new JSONArray();

		durationDataJSONArray.put(
			new String[] {
				"Name", "Batch Type", "Results", "Duration", "Average Duration"
			});

		for (BatchHistory batchHistory : _batchHistoryMap.values()) {
			String batchName = batchHistory.getBatchName();

			if (!batchName.matches(batchNameRegex)) {
				continue;
			}

			for (TestHistory testHistory : batchHistory.getTestHistories()) {
				if (testHistory.getAverageDuration() <= _minimumTestDuration) {
					continue;
				}

				JSONArray jsonArray = new JSONArray();

				jsonArray.put(testHistory.getTestName());

				jsonArray.put(testHistory.getBatchName());

				JSONArray durationJSONArray = new JSONArray();
				JSONArray statusesJSONArray = new JSONArray();

				long totalDuration = 0;

				for (TestReport testReport : testHistory.getTestReports()) {
					long duration = testReport.getDuration();

					if (duration > _MAXIMUM_TEST_DURATION) {
						continue;
					}

					totalDuration = totalDuration + duration;

					durationJSONArray.put(duration);

					JSONArray statusJSONArray = new JSONArray();

					statusJSONArray.put(_fixStatus(testReport.getStatus()));

					DownstreamBuildReport downstreamBuildReport =
						testReport.getDownstreamBuildReport();

					statusJSONArray.put(downstreamBuildReport.getBuildURL());

					statusesJSONArray.put(statusJSONArray);
				}

				jsonArray.put(statusesJSONArray);

				jsonArray.put(durationJSONArray);

				jsonArray.put(testHistory.getAverageDuration());

				durationDataJSONArray.put(jsonArray);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var durationData = ");
		sb.append(durationDataJSONArray);
		sb.append(";\nvar durationDataGeneratedDate = new Date(");
		sb.append(JenkinsResultsParserUtil.getCurrentTimeMillis());
		sb.append(");\nvar testrayRoutineURL = \"");
		sb.append(_testrayRoutine.getURL());
		sb.append("\";\nvar testrayRoutineName = \"");
		sb.append(_testrayRoutine.getName());
		sb.append("\";");

		JenkinsResultsParserUtil.write(filePath, sb.toString());
	}

	public void writeFlakyTestDataJavaScriptFile(String filePath)
		throws IOException {

		JSONArray flakyTestDataJSONArray = new JSONArray();

		flakyTestDataJSONArray.put(
			new String[] {"Name", "Batch Type", "Results", "Status Changes"});

		for (BatchHistory batchHistory : _batchHistoryMap.values()) {
			for (TestHistory testHistory : batchHistory.getTestHistories()) {
				if (!testHistory.isFlaky()) {
					continue;
				}

				JSONArray jsonArray = new JSONArray();

				jsonArray.put(testHistory.getTestName());

				jsonArray.put(testHistory.getBatchName());

				JSONArray statusesJSONArray = new JSONArray();

				for (TestReport testReport : testHistory.getTestReports()) {
					JSONArray statusJSONArray = new JSONArray();

					statusJSONArray.put(_fixStatus(testReport.getStatus()));

					DownstreamBuildReport downstreamBuildReport =
						testReport.getDownstreamBuildReport();

					statusJSONArray.put(downstreamBuildReport.getBuildURL());

					statusesJSONArray.put(statusJSONArray);
				}

				jsonArray.put(statusesJSONArray);

				jsonArray.put(testHistory.getStatusChanges());

				flakyTestDataJSONArray.put(jsonArray);
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var flakyTestData = ");
		sb.append(flakyTestDataJSONArray);
		sb.append(";\nvar flakyTestDataGeneratedDate = new Date(");
		sb.append(JenkinsResultsParserUtil.getCurrentTimeMillis());
		sb.append(");\nvar testrayRoutineURL = \"");
		sb.append(_testrayRoutine.getURL());
		sb.append("\";\nvar testrayRoutineName = \"");
		sb.append(_testrayRoutine.getName());
		sb.append("\";");

		JenkinsResultsParserUtil.write(filePath, sb.toString());
	}

	private String _fixStatus(String status) {
		status = status.replace("REGRESSION", "FAILED");
		status = status.replace("FIXED", "PASSED");

		return status;
	}

	private List<String> _getExcludedTestNameRegexes() {
		if (_excludedTestNameRegexes != null) {
			return _excludedTestNameRegexes;
		}

		try {
			String excludedTestNames = JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"flaky.test.report.test.name.excludes");

			_excludedTestNameRegexes = Arrays.asList(
				excludedTestNames.split("\\s*,\\s*"));
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}

		return _excludedTestNameRegexes;
	}

	private static final long _MAXIMUM_TEST_DURATION = 2 * 60 * 60 * 1000;

	private static List<String> _excludedTestNameRegexes;

	private final Map<String, BatchHistory> _batchHistoryMap = new HashMap<>();
	private final TestrayBuild _latestTestrayBuild;
	private int _minimumStatusChanges = 3;
	private long _minimumTestDuration = 60 * 1000;
	private final TestrayRoutine _testrayRoutine;

	private class BatchHistory {

		public BatchHistory(String batchName) {
			_batchName = batchName;
		}

		public void addBuildReport(
			DownstreamBuildReport downstreamBuildReport) {

			_downstreamBuildReports.add(downstreamBuildReport);

			for (TestClassReport testClassReport :
					downstreamBuildReport.getTestClassReports()) {

				if (_excludeTestClassReport(testClassReport)) {
					continue;
				}

				String testClassName = testClassReport.getTestClassName();

				TestClassHistory testClassHistory = _testClassHistoryMap.get(
					testClassName);

				if (testClassHistory == null) {
					testClassHistory = new TestClassHistory(
						this, testClassName);

					_testClassHistoryMap.put(testClassName, testClassHistory);
				}

				testClassHistory.addTestClassReport(testClassReport);
			}
		}

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

		public String getBatchName() {
			return _batchName;
		}

		public List<TestClassHistory> getTestClassHistories() {
			return new ArrayList<>(_testClassHistoryMap.values());
		}

		public TestClassHistory getTestClassHistory(String testClassName) {
			return _testClassHistoryMap.get(testClassName);
		}

		public List<TestHistory> getTestHistories() {
			List<TestHistory> testHistories = new ArrayList<>();

			for (TestClassHistory testClassHistory :
					_testClassHistoryMap.values()) {

				testHistories.addAll(testClassHistory.getTestHistories());
			}

			return testHistories;
		}

		public TestrayCaseType getTestrayCaseType() {
			if (_testrayCaseType != null) {
				return _testrayCaseType;
			}

			try {
				String testrayCaseTypeName =
					JenkinsResultsParserUtil.getProperty(
						JenkinsResultsParserUtil.getBuildProperties(),
						"testray.case.type", getBatchName());

				if (JenkinsResultsParserUtil.isNullOrEmpty(
						testrayCaseTypeName)) {

					return null;
				}

				TestrayServer testrayServer =
					_latestTestrayBuild.getTestrayServer();

				_testrayCaseType = testrayServer.getTestrayCaseType(
					testrayCaseTypeName);

				return _testrayCaseType;
			}
			catch (IOException ioException) {
				return null;
			}
		}

		public TestrayRun getTestrayRun() {
			if (_testrayRun != null) {
				return _testrayRun;
			}

			_testrayRun = new TestrayRun(
				getLatestTestrayBuild(), getBatchName(), new ArrayList<File>());

			return _testrayRun;
		}

		private boolean _excludeTestClassReport(
			TestClassReport testClassReport) {

			String status = _fixStatus(testClassReport.getStatus());

			if (status.equals("SKIPPED")) {
				return true;
			}

			String testClassName = testClassReport.getTestClassName();

			if (testClassName.contains("PortalLogAssertorTest") ||
				testClassName.contains("JenkinsLogAsserterTest")) {

				return true;
			}

			for (String excludedTestNameRegex : _getExcludedTestNameRegexes()) {
				if (testClassName.matches(
						".*" + excludedTestNameRegex + ".*")) {

					return true;
				}
			}

			return false;
		}

		private static final long _MAXIMUM_BATCH_DURATION = 24 * 60 * 60 * 1000;

		private final String _batchName;
		private final List<DownstreamBuildReport> _downstreamBuildReports =
			new ArrayList<>();
		private final Map<String, TestClassHistory> _testClassHistoryMap =
			new HashMap<>();
		private TestrayCaseType _testrayCaseType;
		private TestrayRun _testrayRun;

	}

	private class TestClassHistory {

		public TestClassHistory(
			BatchHistory batchHistory, String testClassName) {

			_batchHistory = batchHistory;
			_testClassName = testClassName;
		}

		public void addTestClassReport(TestClassReport testClassReport) {
			_testClassReports.add(testClassReport);

			for (TestReport testReport : testClassReport.getTestReports()) {
				if (_excludeTestReport(testReport)) {
					continue;
				}

				String testName = testReport.getTestName();

				TestHistory testHistory = _testHistoryMap.get(testName);

				if (testHistory == null) {
					testHistory = new TestHistory(_batchHistory, testName);

					_testHistoryMap.put(testName, testHistory);
				}

				testHistory.addTestReport(testReport);
			}
		}

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

		public int getTestCount() {
			return _testClassReports.size();
		}

		public List<TestHistory> getTestHistories() {
			return new ArrayList<>(_testHistoryMap.values());
		}

		public TestrayCaseResult getTestrayCaseResult() {
			return _testrayCaseResult;
		}

		public void setTestrayCaseResult(TestrayCaseResult testrayCaseResult) {
			_testrayCaseResult = testrayCaseResult;
		}

		private boolean _excludeTestReport(TestReport testReport) {
			String status = _fixStatus(testReport.getStatus());

			if (status.equals("SKIPPED")) {
				return true;
			}

			String testName = testReport.getTestName();

			if (testName.contains("PortalLogAssertorTest") ||
				testName.contains("JenkinsLogAsserterTest")) {

				return true;
			}

			for (String excludedTestNameRegex : _getExcludedTestNameRegexes()) {
				if (testName.matches(".*" + excludedTestNameRegex + ".*")) {
					return true;
				}
			}

			return false;
		}

		private final BatchHistory _batchHistory;
		private final String _testClassName;
		private final List<TestClassReport> _testClassReports =
			new ArrayList<>();
		private final Map<String, TestHistory> _testHistoryMap =
			new HashMap<>();
		private TestrayCaseResult _testrayCaseResult;

	}

	private class TestHistory {

		public TestHistory(BatchHistory batchHistory, String testName) {
			_batchHistory = batchHistory;
			_testName = testName;
		}

		public void addTestReport(TestReport testReport) {
			_testReports.add(testReport);
		}

		public long getAverageDuration() {
			long count = 0;
			long totalDuration = 0;

			for (TestReport testReport : _testReports) {
				DownstreamBuildReport downstreamBuildReport =
					testReport.getDownstreamBuildReport();

				long duration = testReport.getDuration();

				if ((duration <= 0) || (duration >= _MAXIMUM_TEST_DURATION) ||
					(duration >= downstreamBuildReport.getDuration())) {

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

		public String getBatchName() {
			return _batchHistory.getBatchName();
		}

		public int getStatusChanges() {
			int statusChanges = 0;

			String lastStatus = null;

			for (TestReport testReport : _testReports) {
				String status = _fixStatus(testReport.getStatus());

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

		public String getTestName() {
			return _testName;
		}

		public List<TestReport> getTestReports() {
			return _testReports;
		}

		public boolean isFlaky() {
			if (getStatusChanges() >= _minimumStatusChanges) {
				return true;
			}

			return false;
		}

		private final BatchHistory _batchHistory;
		private final String _testName;
		private final List<TestReport> _testReports = new ArrayList<>();

	}

}