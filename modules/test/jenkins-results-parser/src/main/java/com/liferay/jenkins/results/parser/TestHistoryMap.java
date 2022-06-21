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

import java.net.URL;

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
			if (JenkinsResultsParserUtil.exists(
					topLevelBuildReport.getBuildReportJSONUserContentURL())) {

				put(topLevelBuildReport);
			}

			URL buildResultJSONUserContentURL =
				topLevelBuildReport.getBuildResultJSONUserContentURL();

			if (!JenkinsResultsParserUtil.exists(
					buildResultJSONUserContentURL)) {

				continue;
			}

			try {
				JSONObject buildResultJSONObject =
					JenkinsResultsParserUtil.toJSONObject(
						buildResultJSONUserContentURL.toString());

				JSONArray batchResultsJSONArray =
					buildResultJSONObject.getJSONArray("batchResults");

				for (int i = 0; i < batchResultsJSONArray.length(); i++) {
					JSONObject batchResultJSONObject =
						batchResultsJSONArray.getJSONObject(i);

					String batchName = batchResultJSONObject.getString(
						"jobVariant");

					batchName = batchName.replaceAll("(.*)/.*", "$1");

					JSONArray testResultsJSONArray =
						batchResultJSONObject.getJSONArray("testResults");

					for (int j = 0; j < testResultsJSONArray.length(); j++) {
						JSONObject testResultJSONObject =
							testResultsJSONArray.getJSONObject(j);

						String status = testResultJSONObject.optString(
							"status");

						status = status.replace("REGRESSION", "FAILED");
						status = status.replace("FIXED", "PASSED");

						String testName = testResultJSONObject.optString(
							"name");

						if (status.equals("SKIPPED") ||
							testName.startsWith("PortalLogAssertorTest") ||
							testName.startsWith("JenkinsLogAsserterTest")) {

							continue;
						}

						put(
							testName, batchName,
							testResultJSONObject.optString("buildURL"),
							testResultJSONObject.optLong("duration"),
							testResultJSONObject.optString("errorDetails"),
							status);
					}
				}
			}
			catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}

		long duration = JenkinsResultsParserUtil.getCurrentTimeMillis() - start;

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Test history map populated in ",
				JenkinsResultsParserUtil.toDurationString(duration)));
	}

	public void put(
		String testName, String batchName, String buildURL, long duration,
		String errorSnippet, String status) {

		for (String excludedTestNameRegex : _excludedTestNameRegexes) {
			if (testName.matches(".*" + excludedTestNameRegex + ".*")) {
				return;
			}
		}

		Map<String, String> key = Collections.singletonMap(testName, batchName);

		if (containsKey(key)) {
			TestHistory testHistory = get(key);

			testHistory.add(buildURL, duration, errorSnippet, status);

			return;
		}

		put(
			key,
			new TestHistory(
				testName, batchName, buildURL, duration, errorSnippet, status));
	}

	public void put(TopLevelBuildReport topLevelBuildReport) {
		for (DownstreamBuildReport downstreamBuildReport :
				topLevelBuildReport.getDownstreamBuildReports()) {

			for (TestReport testReport :
					downstreamBuildReport.getTestReports()) {

				String testName = testReport.getName();

				for (String excludedTestNameRegex : _excludedTestNameRegexes) {
					if (testName.matches(".*" + excludedTestNameRegex + ".*")) {
						return;
					}
				}

				Map<String, String> key = Collections.singletonMap(
					testName, downstreamBuildReport.getBatchName());

				if (containsKey(key)) {
					TestHistory testHistory = get(key);

					testHistory.add(
						String.valueOf(downstreamBuildReport.getBuildURL()),
						testReport.getDuration(), testReport.getErrorDetails(),
						testReport.getStatus());

					return;
				}

				put(
					key,
					new TestHistory(
						testName, downstreamBuildReport.getBatchName(),
						String.valueOf(downstreamBuildReport.getBuildURL()),
						testReport.getDuration(), testReport.getErrorDetails(),
						testReport.getStatus()));
			}
		}
	}

	public void setMinimumStatusChanges(int minimumStatusChanges) {
		_minimumStatusChanges = minimumStatusChanges;
	}

	public void setMinimumTestDuration(long minimumTestDuration) {
		_minimumTestDuration = minimumTestDuration;
	}

	public void writeAverageDurationJSONObjectFile(String filePath)
		throws IOException {

		JSONObject jsonObject = new JSONObject();

		for (TestHistory testHistory : values()) {
			String batchName = testHistory.getBatchName();

			JSONObject batchJSONObject = new JSONObject();

			if (jsonObject.has(batchName)) {
				batchJSONObject = jsonObject.getJSONObject(batchName);
			}

			long averageDuration = testHistory.getAverageDuration();
			String testName = testHistory.getName();

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

				if (batchJSONObject.has(testName)) {
					averageDuration += batchJSONObject.optLong(testName, 0L);
				}
			}

			batchJSONObject.put(testName, averageDuration);

			jsonObject.put(batchName, batchJSONObject);
		}

		File file = new File(filePath);

		File tempFile = new File(
			file.getParentFile(),
			JenkinsResultsParserUtil.getDistinctTimeStamp());

		try {
			JenkinsResultsParserUtil.write(tempFile, jsonObject.toString());

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

		public TestHistory(
			String name, String batchName, String buildURL, long duration,
			String errorSnippet, String status) {

			_name = name;
			_batchName = batchName;

			add(buildURL, duration, errorSnippet, status);
		}

		public void add(
			String buildURL, long duration, String errorSnippet,
			String status) {

			_testHistoryEntries.add(
				new TestHistoryEntry(buildURL, duration, errorSnippet, status));
		}

		public long getAverageDuration() {
			long count = 0;
			long totalDuration = 0;

			for (TestHistoryEntry testHistoryEntry : _testHistoryEntries) {
				long duration = testHistoryEntry.getDuration();

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

		public String getName() {
			return _name;
		}

		public List<String> getStatuses() {
			List<String> statuses = new ArrayList<>();

			for (TestHistoryEntry testHistoryEntry : _testHistoryEntries) {
				statuses.add(testHistoryEntry.getStatus());
			}

			return statuses;
		}

		public boolean isFlaky() {
			String lastStatus = null;

			for (TestHistoryEntry testHistoryEntry : _testHistoryEntries) {
				String status = testHistoryEntry.getStatus();

				if (lastStatus == null) {
					lastStatus = status;

					continue;
				}

				if (!lastStatus.equals(status)) {
					lastStatus = status;

					_statusChanges++;
				}
			}

			if (_statusChanges >= _minimumStatusChanges) {
				return true;
			}

			return false;
		}

		public JSONArray toDurationJSONArray() {
			JSONArray jsonArray = new JSONArray();

			jsonArray.put(getName());
			jsonArray.put(getBatchName());

			JSONArray durationJSONArray = new JSONArray();
			JSONArray statusesJSONArray = new JSONArray();

			long totalDuration = 0;

			for (TestHistoryEntry testHistoryEntry : _testHistoryEntries) {
				long duration = testHistoryEntry.getDuration();

				if (duration > _MAXIMUM_TEST_DURATION) {
					continue;
				}

				totalDuration = totalDuration + duration;

				durationJSONArray.put(duration);

				JSONArray statusJSONArray = new JSONArray();

				statusJSONArray.put(testHistoryEntry.getStatus());
				statusJSONArray.put(testHistoryEntry.getBuildURL());

				statusesJSONArray.put(statusJSONArray);
			}

			jsonArray.put(statusesJSONArray);
			jsonArray.put(durationJSONArray);
			jsonArray.put(getAverageDuration());

			return jsonArray;
		}

		public JSONArray toStatusJSONArray() {
			JSONArray jsonArray = new JSONArray();

			jsonArray.put(getName());
			jsonArray.put(getBatchName());

			JSONArray statusesJSONArray = new JSONArray();

			for (TestHistoryEntry testHistoryEntry : _testHistoryEntries) {
				JSONArray statusJSONArray = new JSONArray();

				statusJSONArray.put(testHistoryEntry.getStatus());
				statusJSONArray.put(testHistoryEntry.getBuildURL());

				statusesJSONArray.put(statusJSONArray);
			}

			jsonArray.put(statusesJSONArray);
			jsonArray.put(_statusChanges);

			return jsonArray;
		}

		private final String _batchName;
		private final String _name;
		private int _statusChanges;
		private final List<TestHistoryEntry> _testHistoryEntries =
			new ArrayList<>();

		private class TestHistoryEntry {

			public TestHistoryEntry(
				String buildURL, long duration, String errorSnippet,
				String status) {

				_buildURL = buildURL;
				_duration = duration;
				_errorSnippet = errorSnippet;
				_status = status;
			}

			public String getBuildURL() {
				return _buildURL;
			}

			public long getDuration() {
				return _duration;
			}

			public String getErrorSnippet() {
				return _errorSnippet;
			}

			public String getStatus() {
				return _status;
			}

			private final String _buildURL;
			private final long _duration;
			private final String _errorSnippet;
			private final String _status;

		}

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