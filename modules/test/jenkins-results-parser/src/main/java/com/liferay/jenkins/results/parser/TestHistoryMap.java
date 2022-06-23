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

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class TestHistoryMap
	extends HashMap<Map<String, String>, TestHistoryMap.TestHistory> {

	public TestHistoryMap(String jenkinsJobURL, int maxBuildCount) {
		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		JobReport jobReport = JobReport.getInstance(jenkinsJobURL);

		List<TopLevelBuildReport> topLevelBuildReports =
			jobReport.getTopLevelBuildReports(maxBuildCount);

		for (TopLevelBuildReport topLevelBuildReport : topLevelBuildReports) {
			for (DownstreamBuildReport downstreamBuildReport :
					topLevelBuildReport.getDownstreamBuildReports()) {

				for (TestReport testReport :
						downstreamBuildReport.getTestReports()) {

					String status = testReport.getStatus();

					status = status.replace("REGRESSION", "FAILED");
					status = status.replace("FIXED", "PASSED");

					String testName = testReport.getName();

					if (status.equals("SKIPPED") ||
						testName.startsWith("PortalLogAssertorTest") ||
						testName.startsWith("JenkinsLogAsserterTest")) {

						continue;
					}

					boolean excludeTest = false;

					for (String excludedTestNameRegex :
							_excludedTestNameRegexes) {

						if (testName.matches(
								".*" + excludedTestNameRegex + ".*")) {

							excludeTest = true;
						}
					}

					if (excludeTest) {
						continue;
					}

					Map<String, String> key = Collections.singletonMap(
						downstreamBuildReport.getBatchName(), testName);

					TestHistory testHistory = get(key);

					if (testHistory == null) {
						testHistory = new TestHistory(
							downstreamBuildReport.getBatchName(), testName);

						put(key, testHistory);
					}

					testHistory.addTestReport(testReport);
				}
			}
		}

		long duration = JenkinsResultsParserUtil.getCurrentTimeMillis() - start;

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Test history map populated in ",
				JenkinsResultsParserUtil.toDurationString(duration)));
	}

	public void setMinimumStatusChanges(int minimumStatusChanges) {
		_minimumStatusChanges = minimumStatusChanges;
	}

	public void setMinimumTestDuration(long minimumTestDuration) {
		_minimumTestDuration = minimumTestDuration;
	}

	public void writeAverageDurationJSONObjectFile(String filePath)
		throws IOException {

		Map<String, List<JSONObject>> batchJSONObjectsMap = new HashMap<>();

		for (TestHistory testHistory : values()) {
			String batchName = testHistory.getBatchName();

			List<JSONObject> batchJSONObjects = batchJSONObjectsMap.get(
				batchName);

			if (batchJSONObjects == null) {
				batchJSONObjects = new ArrayList<>();
			}

			long averageDuration = testHistory.getAverageDuration();
			String testName = testHistory.getTestName();

			if (averageDuration < 0) {
				averageDuration = 0L;
			}

			if (batchName.startsWith("integration") ||
				batchName.startsWith("modules-integration") ||
				batchName.startsWith("modules-unit") ||
				batchName.startsWith("unit")) {

				Matcher matcher = _jUnitTestNamePattern.matcher(testName);

				if (matcher.find()) {
					testName = matcher.group("testClassName");
				}

				boolean found = false;

				for (JSONObject batchJSONObject : batchJSONObjects) {
					if (!testName.equals(
							batchJSONObject.optString("testName"))) {

						continue;
					}

					averageDuration += batchJSONObject.optLong(
						"averageDuration", 0L);

					batchJSONObject.put("averageDuration", averageDuration);

					found = true;

					break;
				}

				if (found) {
					continue;
				}
			}

			JSONObject batchJSONObject = new JSONObject();

			batchJSONObject.put("averageDuration", averageDuration);
			batchJSONObject.put(
				"statusChanges", testHistory.getStatusChanges());
			batchJSONObject.put("testName", testName);

			batchJSONObjects.add(batchJSONObject);

			batchJSONObjectsMap.put(batchName, batchJSONObjects);
		}

		JSONObject averageDurationJSONObject = new JSONObject();

		JSONArray batchesJSONArray = new JSONArray();

		for (Map.Entry<String, List<JSONObject>> entry :
				batchJSONObjectsMap.entrySet()) {

			JSONObject batchJSONObject = new JSONObject();

			batchJSONObject.put("batchName", entry.getKey());
			batchJSONObject.put("tests", entry.getValue());

			batchesJSONArray.put(batchJSONObject);
		}

		averageDurationJSONObject.put("batches", batchesJSONArray);

		File file = new File(filePath);

		File tempFile = new File(
			file.getParentFile(),
			JenkinsResultsParserUtil.getDistinctTimeStamp());

		try {
			JenkinsResultsParserUtil.write(
				tempFile, averageDurationJSONObject.toString());

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

		for (TestHistory testHistory : values()) {
			String batchName = testHistory.getBatchName();

			if (batchName.matches(batchNameRegex) &&
				(testHistory.getAverageDuration() > _minimumTestDuration)) {

				durationDataJSONArray.put(testHistory.toDurationJSONArray());
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var durationData = ");
		sb.append(durationDataJSONArray.toString());
		sb.append(";\nvar durationDataGeneratedDate = new Date(");
		sb.append(JenkinsResultsParserUtil.getCurrentTimeMillis());
		sb.append(");");

		JenkinsResultsParserUtil.write(filePath, sb.toString());
	}

	public void writeFlakyTestDataJavaScriptFile(String filePath)
		throws IOException {

		JSONArray flakyTestDataJSONArray = new JSONArray();

		flakyTestDataJSONArray.put(
			new String[] {"Name", "Batch Type", "Results", "Status Changes"});

		for (TestHistory testHistory : values()) {
			if (testHistory.isFlaky()) {
				flakyTestDataJSONArray.put(testHistory.toStatusJSONArray());
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var flakyTestData = ");
		sb.append(flakyTestDataJSONArray.toString());
		sb.append(";\nvar flakyTestDataGeneratedDate = new Date(");
		sb.append(JenkinsResultsParserUtil.getCurrentTimeMillis());
		sb.append(");");

		JenkinsResultsParserUtil.write(filePath, sb.toString());
	}

	public class TestHistory {

		public TestHistory(String batchName, String testName) {
			_batchName = batchName;
			_testName = testName;
		}

		public void addTestReport(TestReport testReport) {
			_testReports.add(testReport);
		}

		public long getAverageDuration() {
			long count = 0;
			long totalDuration = 0;

			for (TestReport testReport : _testReports) {
				long duration = testReport.getDuration();

				if (duration > _MAXIMUM_TEST_DURATION) {
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

		public int getStatusChanges() {
			int statusChanges = 0;

			String lastStatus = null;

			for (TestReport testReport : _testReports) {
				String status = testReport.getStatus();

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

		public List<String> getStatuses() {
			List<String> statuses = new ArrayList<>();

			for (TestReport testReport : _testReports) {
				statuses.add(testReport.getStatus());
			}

			return statuses;
		}

		public String getTestName() {
			return _testName;
		}

		public boolean isFlaky() {
			if (getStatusChanges() >= _minimumStatusChanges) {
				return true;
			}

			return false;
		}

		public JSONArray toDurationJSONArray() {
			JSONArray jsonArray = new JSONArray();

			jsonArray.put(getTestName());

			jsonArray.put(getBatchName());

			JSONArray durationJSONArray = new JSONArray();
			JSONArray statusesJSONArray = new JSONArray();

			long totalDuration = 0;

			for (TestReport testReport : _testReports) {
				long duration = testReport.getDuration();

				if (duration > _MAXIMUM_TEST_DURATION) {
					continue;
				}

				totalDuration = totalDuration + duration;

				durationJSONArray.put(duration);

				JSONArray statusJSONArray = new JSONArray();

				statusJSONArray.put(testReport.getStatus());

				DownstreamBuildReport downstreamBuildReport =
					testReport.getDownstreamBuildReport();

				statusJSONArray.put(downstreamBuildReport.getBuildURL());

				statusesJSONArray.put(statusJSONArray);
			}

			jsonArray.put(statusesJSONArray);

			jsonArray.put(durationJSONArray);

			jsonArray.put(getAverageDuration());

			return jsonArray;
		}

		public JSONArray toStatusJSONArray() {
			JSONArray jsonArray = new JSONArray();

			jsonArray.put(getTestName());

			jsonArray.put(getBatchName());

			JSONArray statusesJSONArray = new JSONArray();

			for (TestReport testReport : _testReports) {
				JSONArray statusJSONArray = new JSONArray();

				statusJSONArray.put(testReport.getStatus());

				DownstreamBuildReport downstreamBuildReport =
					testReport.getDownstreamBuildReport();

				statusJSONArray.put(downstreamBuildReport.getBuildURL());

				statusesJSONArray.put(statusJSONArray);
			}

			jsonArray.put(statusesJSONArray);

			jsonArray.put(getStatusChanges());

			return jsonArray;
		}

		private final String _batchName;
		private final String _testName;
		private final List<TestReport> _testReports = new ArrayList<>();

	}

	private static final long _MAXIMUM_TEST_DURATION = 2 * 60 * 60 * 1000;

	private static final List<String> _excludedTestNameRegexes =
		new ArrayList<String>() {
			{
				Properties buildProperties = null;

				try {
					buildProperties =
						JenkinsResultsParserUtil.getBuildProperties();

					String excludedTestNames = buildProperties.getProperty(
						"flaky.test.report.test.name.excludes");

					Collections.addAll(
						this, excludedTestNames.split("\\s*,\\s*"));
				}
				catch (IOException ioException) {
					throw new RuntimeException(
						"Unable to get build properties", ioException);
				}
			}
		};

	private static final Pattern _jUnitTestNamePattern = Pattern.compile(
		"(?<testClassName>.*Test)\\.(?<testName>test.*)");

	private int _minimumStatusChanges = 3;
	private long _minimumTestDuration = 60 * 1000;

}