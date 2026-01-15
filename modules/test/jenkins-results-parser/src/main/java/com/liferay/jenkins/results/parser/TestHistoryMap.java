/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.history.BatchHistory;
import com.liferay.jenkins.results.parser.history.JobHistory;
import com.liferay.jenkins.results.parser.history.TestClassHistory;
import com.liferay.jenkins.results.parser.history.TestTaskHistory;
import com.liferay.jenkins.results.parser.testray.TestrayBuild;
import com.liferay.jenkins.results.parser.testray.TestrayCaseType;
import com.liferay.jenkins.results.parser.testray.TestrayFactory;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class TestHistoryMap {

	public TestHistoryMap(TestrayRoutine testrayRoutine, int maxBuildCount) {
		_testrayRoutine = testrayRoutine;

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		List<TestrayBuild> testrayBuilds = testrayRoutine.getTestrayBuilds(
			maxBuildCount);

		if (testrayBuilds.size() > maxBuildCount) {
			testrayBuilds = testrayBuilds.subList(0, maxBuildCount);
		}

		for (TestrayBuild testrayBuild : testrayBuilds) {
			TopLevelBuildReport topLevelBuildReport =
				testrayBuild.getTopLevelBuildReport();

			if ((topLevelBuildReport == null) ||
				JenkinsResultsParserUtil.isNullOrEmpty(
					topLevelBuildReport.getResult())) {

				continue;
			}

			if (_latestTestrayBuild == null) {
				setLatestTestrayBuild(testrayBuild);
			}

			for (DownstreamBuildReport downstreamBuildReport :
					topLevelBuildReport.getDownstreamBuildReports()) {

				String batchName = downstreamBuildReport.getBatchName();

				BatchHistory batchHistory = _batchHistoryMap.get(batchName);

				if (batchHistory == null) {
					batchHistory = new PrivateBatchHistory(batchName);

					_batchHistoryMap.put(batchName, batchHistory);
				}

				if (batchHistory instanceof PrivateBatchHistory) {
					PrivateBatchHistory privateBatchHistory =
						(PrivateBatchHistory)batchHistory;

					privateBatchHistory.addBuildReport(downstreamBuildReport);
				}
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

	public void setLatestTestrayBuild(TestrayBuild testrayBuild) {
		_latestTestrayBuild = testrayBuild;
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
					"averageDuration", testClassHistory.getAverageDuration()
				).put(
					"averageOverheadDuration",
					testClassHistory.getAverageOverheadDuration()
				).put(
					"failureCount", testClassHistory.getFailureCount()
				).put(
					"statusChanges", testClassHistory.getStatusChanges()
				).put(
					"testCount", testClassHistory.getTestCount()
				).put(
					"testName", testClassHistory.getTestClassName()
				).put(
					"testTaskName", testClassHistory.getTestTaskName()
				);

				testsJSONArray.put(testJSONObject);
			}

			JSONArray testTasksJSONArray = new JSONArray();

			for (TestTaskHistory testTaskHistory :
					batchHistory.getTestTaskHistories()) {

				JSONObject testJSONObject = new JSONObject();

				testJSONObject.put(
					"averageDuration", testTaskHistory.getAverageDuration()
				).put(
					"averageTotalDuration",
					testTaskHistory.getAverageTotalDuration()
				).put(
					"latestReportMissing",
					testTaskHistory.isLatestReportMissing()
				).put(
					"longestDuration", testTaskHistory.getLongestDuration()
				).put(
					"testTaskCount", testTaskHistory.getTestTaskCount()
				).put(
					"testTaskName", testTaskHistory.getTestTaskName()
				);

				testTasksJSONArray.put(testJSONObject);
			}

			JSONObject batchJSONObject = new JSONObject();

			batchJSONObject.put(
				"averageDuration", batchHistory.getAverageDuration()
			).put(
				"batchName", batchHistory.getBatchName()
			).put(
				"tests", testsJSONArray
			).put(
				"testTasks", testTasksJSONArray
			);

			batchesJSONArray.put(batchJSONObject);
		}

		JSONObject ciHistoryJSONObject = new JSONObject();

		ciHistoryJSONObject.put("batches", batchesJSONArray);

		TestrayServer testrayServer = _latestTestrayBuild.getTestrayServer();

		ciHistoryJSONObject.put(
			"testray_url", String.valueOf(testrayServer.getURL())
		).put(
			"upstream_branch_name", _latestTestrayBuild.getPortalBranch()
		);

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

	public void writeFlakyTestDataJavaScriptFile(String filePath)
		throws IOException {

		JSONArray flakyTestDataJSONArray = new JSONArray();

		flakyTestDataJSONArray.put(
			new String[] {"Name", "Batch Type", "Results", "Status Changes"});

		for (BatchHistory batchHistory : _batchHistoryMap.values()) {
			for (TestClassHistory testClassHistory :
					batchHistory.getTestClassHistories()) {

				if (!testClassHistory.isFlaky()) {
					continue;
				}

				JSONArray jsonArray = new JSONArray();

				jsonArray.put(testClassHistory.getTestClassName());

				jsonArray.put(testClassHistory.getBatchName());

				JSONArray statusesJSONArray = new JSONArray();

				if (testClassHistory instanceof PrivateTestClassHistory) {
					PrivateTestClassHistory privateTestClassHistory =
						(PrivateTestClassHistory)testClassHistory;

					for (TestClassReport testClassReport :
							privateTestClassHistory.getTestClassReports()) {

						JSONArray statusJSONArray = new JSONArray();

						statusJSONArray.put(
							_fixStatus(testClassReport.getStatus()));

						DownstreamBuildReport downstreamBuildReport =
							testClassReport.getDownstreamBuildReport();

						statusJSONArray.put(
							downstreamBuildReport.getBuildURL());

						statusesJSONArray.put(statusJSONArray);
					}
				}

				jsonArray.put(statusesJSONArray);

				jsonArray.put(testClassHistory.getStatusChanges());

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
	private static final Pattern _stopWatchGroupTestTaskNamePattern =
		Pattern.compile("test\\.execution\\.duration(?<testTaskName>\\..+)");

	private final Map<String, BatchHistory> _batchHistoryMap = new HashMap<>();
	private TestrayBuild _latestTestrayBuild;
	private int _minimumStatusChanges = 3;
	private long _minimumTestDuration = 60 * 1000;
	private final TestrayRoutine _testrayRoutine;

	private class PrivateBatchHistory implements BatchHistory {

		public PrivateBatchHistory(String batchName) {
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
					testClassHistory = new PrivateTestClassHistory(
						this, testClassName);

					_testClassHistoryMap.put(testClassName, testClassHistory);
				}

				if (testClassHistory instanceof PrivateTestClassHistory) {
					PrivateTestClassHistory privateTestClassHistory =
						(PrivateTestClassHistory)testClassHistory;

					privateTestClassHistory.addTestClassReport(testClassReport);
				}
			}

			if (!(downstreamBuildReport instanceof
					ModulesJUnitDownstreamBuildReport)) {

				return;
			}

			boolean latestReport = false;

			TestrayBuild latestTestrayBuild = getLatestTestrayBuild();

			if (Objects.equals(
					downstreamBuildReport.getTopLevelBuildReport(),
					latestTestrayBuild.getTopLevelBuildReport())) {

				latestReport = true;
			}

			ModulesJUnitDownstreamBuildReport
				modulesJUnitDownstreamBuildReport =
					(ModulesJUnitDownstreamBuildReport)downstreamBuildReport;

			List<TestTaskReport> testTaskReports =
				modulesJUnitDownstreamBuildReport.getTestTaskReports();

			if ((testTaskReports == null) || testTaskReports.isEmpty()) {
				testTaskReports = new ArrayList<>();

				StopWatchRecordsGroup stopWatchRecordsGroup =
					downstreamBuildReport.getStopWatchRecordsGroup();

				for (StopWatchRecord stopWatchRecord :
						stopWatchRecordsGroup.getAllStopWatchRecords()) {

					Matcher matcher =
						_stopWatchGroupTestTaskNamePattern.matcher(
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
			}

			for (TestTaskReport testTaskReport : testTaskReports) {
				String testTaskName = testTaskReport.getName();

				TestTaskHistory testTaskHistory = _testTaskHistoryMap.get(
					testTaskName);

				if (testTaskHistory == null) {
					testTaskHistory = new PrivateTestTaskHistory(testTaskName);
				}

				if (latestReport && testTaskReport.isMissing()) {
					testTaskHistory.setLatestReportMissing(true);
				}

				if (testTaskHistory instanceof PrivateTestTaskHistory) {
					PrivateTestTaskHistory privateTestTaskHistory =
						(PrivateTestTaskHistory)testTaskHistory;

					privateTestTaskHistory.addTestTaskReport(testTaskReport);
				}

				_testTaskHistoryMap.put(testTaskName, testTaskHistory);
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

		@Override
		public String getBatchName() {
			return _batchName;
		}

		@Override
		public JobHistory getJobHistory() {
			return null;
		}

		@Override
		public List<TestClassHistory> getTestClassHistories() {
			return new ArrayList<>(_testClassHistoryMap.values());
		}

		@Override
		public TestClassHistory getTestClassHistory(String testClassName) {
			return _testClassHistoryMap.get(testClassName);
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

				_testrayCaseType = testrayServer.getTestrayCaseTypeByName(
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

			_testrayRun = TestrayFactory.newTestrayRun(
				getLatestTestrayBuild(), getBatchName(), new ArrayList<File>());

			return _testrayRun;
		}

		@Override
		public List<TestTaskHistory> getTestTaskHistories() {
			return new ArrayList<>(_testTaskHistoryMap.values());
		}

		@Override
		public TestTaskHistory getTestTaskHistory(String key) {
			return null;
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
		private final Map<String, TestTaskHistory> _testTaskHistoryMap =
			new HashMap<>();

	}

	private class PrivateTestClassHistory implements TestClassHistory {

		public PrivateTestClassHistory(
			BatchHistory batchHistory, String testClassName) {

			_batchHistory = batchHistory;
			_testClassName = testClassName;
		}

		public void addTestClassReport(TestClassReport testClassReport) {
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
			if (getStatusChanges() >= _minimumStatusChanges) {
				return true;
			}

			return false;
		}

		private final BatchHistory _batchHistory;
		private final String _testClassName;
		private final List<TestClassReport> _testClassReports =
			new ArrayList<>();

	}

	private class PrivateTestTaskHistory implements TestTaskHistory {

		public PrivateTestTaskHistory(String testTaskName) {
			_testTaskName = testTaskName;
		}

		public void addTestTaskReport(TestTaskReport testTaskReport) {
			_testTaskReports.add(testTaskReport);
		}

		@Override
		public long getAverageDuration() {
			if (_testTaskReports.isEmpty()) {
				return 0;
			}

			long totalDuration = 0;

			for (TestTaskReport testTaskReport : _testTaskReports) {
				totalDuration += testTaskReport.getOverheadDuration();
			}

			return totalDuration / _testTaskReports.size();
		}

		@Override
		public long getAverageTotalDuration() {
			if (_testTaskReports.isEmpty()) {
				return 0;
			}

			long totalDuration = 0;

			for (TestTaskReport testTaskReport : _testTaskReports) {
				totalDuration += testTaskReport.getDuration();
			}

			return totalDuration / _testTaskReports.size();
		}

		@Override
		public BatchHistory getBatchHistory() {
			return null;
		}

		@Override
		public long getLongestDuration() {
			long longestDuration = 0L;

			for (TestTaskReport testTaskReport : _testTaskReports) {
				if (longestDuration <= testTaskReport.getDuration()) {
					longestDuration = testTaskReport.getDuration();
				}
			}

			return longestDuration;
		}

		@Override
		public long getTestTaskCount() {
			return _testTaskReports.size();
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

		private boolean _latestReportMissing;
		private final String _testTaskName;
		private final List<TestTaskReport> _testTaskReports = new ArrayList<>();

	}

}