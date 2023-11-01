/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.metrics;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class BuildHistoryReport {

	public static BuildHistoryReport newAggregateReport(
		File outputDir, String startDateString, long durationDays) {

		BuildHistoryReport buildHistoryReport = new BuildHistoryReport(
			outputDir);

		buildHistoryReport.addFilesFromResource(
			"dependencies/metrics/", "css/report.css", "js/utils.js");

		buildHistoryReport.addFilesFromResource(
			"dependencies/metrics/aggregate-report", "/css/main.css",
			"/index.html", "/js/main.js");

		long startTime = _getStartTime(startDateString);

		long duration = TimeUnit.DAYS.toMillis(durationDays);

		Collection<BuildHistory> buildHistories =
			BuildHistoryProcessor.newAggregateJobHistories(startTime, duration);

		buildHistoryReport.addFile(
			"js/table-data.js",
			_getTableDataJSFileContent(buildHistories, "Job Category"));

		buildHistoryReport.addFile(
			"js/timeline-data.js",
			_getTimelineDataJSFileContent(buildHistories, startTime, duration));

		return buildHistoryReport;
	}

	public static BuildHistoryReport newTestSuiteReport(
		File outputDir, String startDateString, long durationDays) {

		BuildHistoryReport buildHistoryReport = new BuildHistoryReport(
			outputDir);

		buildHistoryReport.addFilesFromResource(
			"dependencies/metrics/", "css/report.css", "js/utils.js");

		buildHistoryReport.addFilesFromResource(
			"dependencies/metrics/test-suite-report", "/css/main.css",
			"/index.html", "/js/main.js");

		long startTime = _getStartTime(startDateString);

		long duration = TimeUnit.DAYS.toMillis(durationDays);

		Collection<BuildHistory> buildHistories =
			BuildHistoryProcessor.newTestSuiteJobHistories(
				startTime, duration, _portalMasterPullRequestJobNamePattern);

		buildHistoryReport.addFile(
			"js/table-data.js",
			_getTableDataJSFileContent(buildHistories, "Test Suite Name"));

		buildHistoryReport.addFile(
			"js/timeline-data.js",
			_getTimelineDataJSFileContent(buildHistories, startTime, duration));

		return buildHistoryReport;
	}

	public BuildHistoryReport(File outputDir) {
		_outputDir = outputDir;
	}

	public void addFile(String fileName, String fileContent) {
		_fileMap.put(new File(_outputDir, fileName), fileContent);
	}

	public void addFilesFromResource(
		String resourceDirPath, String... fileNames) {

		for (String fileName : fileNames) {
			try {
				addFile(
					fileName,
					JenkinsResultsParserUtil.getResourceFileContent(
						resourceDirPath + fileName));
			}
			catch (IOException ioException) {
				System.out.println(
					"Unable to get file content from resource: " +
						resourceDirPath + fileName);
			}
		}
	}

	public void write() throws IOException {
		FileUtils.deleteDirectory(_outputDir);

		for (Map.Entry<File, String> entry : _fileMap.entrySet()) {
			File file = entry.getKey();

			String filePath = file.getCanonicalPath();

			if (filePath.contains(".html")) {
				System.out.println("Report created at: file://" + filePath);
			}

			JenkinsResultsParserUtil.write(entry.getKey(), entry.getValue());
		}
	}

	private static LocalDateTime _getLocalDateTime(String startDateString) {
		return LocalDateTime.parse(
			startDateString + " 00:00:00",
			DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss"));
	}

	private static long _getStartTime(String startDateString) {
		return JenkinsResultsParserUtil.getMillis(
			_getLocalDateTime(startDateString));
	}

	private static String _getTableDataJSFileContent(
		Collection<BuildHistory> buildHistories, String groupIdentifierName) {

		JSONArray jsonArray = new JSONArray();

		boolean removeHeader = false;

		BuildHistory totalBuildHistory =
			BuildHistoryProcessor.mergeBuildHistories(
				"[Total]", buildHistories);

		buildHistories.add(totalBuildHistory);

		for (BuildHistory buildHistory : buildHistories) {
			JSONArray tableJSONArray = buildHistory.getTableJSONArray(
				groupIdentifierName);

			if (removeHeader) {
				tableJSONArray.remove(0);
			}
			else {
				removeHeader = true;
			}

			jsonArray.putAll(tableJSONArray);
		}

		buildHistories.remove(totalBuildHistory);

		return "var tableData = " + jsonArray.toString();
	}

	private static String _getTimelineDataJSFileContent(
		Collection<BuildHistory> buildHistories, long startTime,
		long duration) {

		JSONObject jsonObject = new JSONObject();

		JSONArray jsonArray = new JSONArray();

		for (BuildHistory buildHistory : buildHistories) {
			jsonArray.put(buildHistory.getTimelineJSONObject());
		}

		jsonObject.put(
			"jobTimelines", jsonArray
		).put(
			"time", BuildHistory.Timeline.getTimeJSONArray(startTime, duration)
		);

		return "var timelineData = " + jsonObject.toString();
	}

	private static final Pattern _portalMasterPullRequestJobNamePattern =
		Pattern.compile(
			"test-portal-acceptance-pullrequest(|-downstream)\\(master\\)");

	private final Map<File, String> _fileMap = new HashMap<>();
	private final File _outputDir;

}