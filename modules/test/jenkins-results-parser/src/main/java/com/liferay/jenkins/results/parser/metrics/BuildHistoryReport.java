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
import java.util.function.Function;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class BuildHistoryReport {

	public static BuildHistoryReport newAggregateReport(
		long durationDays, File outputDir, String startDateString) {

		BuildHistoryReport buildHistoryReport = new BuildHistoryReport(
			outputDir);

		buildHistoryReport.addFilesFromResource(
			"dependencies/metrics/aggregate-report", "/index.html");

		long startTime = _getStartTime(startDateString);

		long duration = TimeUnit.DAYS.toMillis(durationDays);

		Collection<BuildHistory> buildHistories =
			BuildHistoryProcessor.newAggregateJobHistories(duration, startTime);

		buildHistoryReport.addFile(
			"js/table-data.js",
			_getTableDataJSFileContent(buildHistories, "Job Category"));
		buildHistoryReport.addFile(
			"js/timeline-data.js",
			_getTimelineDataJSFileContent(buildHistories, duration, startTime));

		return buildHistoryReport;
	}

	public static BuildHistoryReport newPullRequestTestSuiteReport(
		long durationDays, File outputDir, String startDateString) {

		return _newTestSuiteReport(
			durationDays, _portalMasterPullRequestJobNamePattern, outputDir,
			"liferay-portal/master Pull Request History Report",
			startDateString);
	}

	public static BuildHistoryReport newReleaseTestSuiteReport(
		long durationDays, File outputDir, String startDateString) {

		return _newTestSuiteReport(
			durationDays, _portalReleaseJobNamePattern, outputDir,
			"Portal Release History Report", startDateString);
	}

	public static BuildHistoryReport newUpstreamTestSuiteReport(
		long durationDays, File outputDir, String startDateString) {

		return _newTestSuiteReport(
			durationDays, _portalMasterUpstreamJobNamePattern, outputDir,
			"liferay-portal/master Upstream History Report", startDateString);
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
				buildHistories, "[Total]");

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
		Collection<BuildHistory> buildHistories, long duration,
		long startTime) {

		JSONObject jsonObject = new JSONObject();

		JSONArray jsonArray = new JSONArray();

		for (BuildHistory buildHistory : buildHistories) {
			jsonArray.put(buildHistory.getTimelineJSONObject());
		}

		jsonObject.put(
			"jobTimelines", jsonArray
		).put(
			"time", BuildHistory.getTimeJSONArray(duration, startTime)
		);

		return "var timelineData = " + jsonObject.toString();
	}

	private static BuildHistoryReport _newTestSuiteReport(
		long durationDays, Pattern jobNamePattern, File outputDir,
		String reportName, String startDateString) {

		BuildHistoryReport buildHistoryReport = new BuildHistoryReport(
			outputDir);

		buildHistoryReport.addFilesFromResource(
			"dependencies/metrics/test-suite-report", "/index.html");

		long duration = TimeUnit.DAYS.toMillis(durationDays);

		Collection<BuildHistory> buildHistories =
			BuildHistoryProcessor.newTestSuiteJobHistories(
				duration, jobNamePattern, _getStartTime(startDateString));

		StringBuilder sb = new StringBuilder();

		sb.append(
			_getTableDataJSFileContent(buildHistories, "Test Suite Name"));

		sb.append("\nvar reportName = \"");

		sb.append(reportName);

		sb.append("\";");

		buildHistoryReport.addFile("js/table-data.js", sb.toString());

		return buildHistoryReport;
	}

	private static final Pattern _portalMasterPullRequestJobNamePattern =
		Pattern.compile(
			"test-portal-acceptance-pullrequest(|-downstream)\\(master\\)");
	private static final Pattern _portalMasterUpstreamJobNamePattern =
		Pattern.compile(
			"test-portal-(acceptance-upstream-dxp|testsuite-upstream)" +
				"(|-downstream)\\(master\\)");
	private static final Pattern _portalReleaseJobNamePattern = Pattern.compile(
		"test-portal(|-fixpack|-hotfix)-release(|-downstream)");

	private final Map<File, String> _fileMap = new HashMap<>();
	private final File _outputDir;

	private static class GroupByTopLevelTestSuiteAndUpstreamJob
		implements Function<BuildJSONObject, String> {

		public String apply(BuildJSONObject buildJSONObject) {
			String jobName = buildJSONObject.getJobName();

			if (jobName.contains("acceptance-upstream-dxp")) {
				return "acceptance-dxp";
			}

			if (buildJSONObject.isTopLevelBuild()) {
				Map<String, String> parameters =
					buildJSONObject.getParameters();

				if (parameters.containsKey("CI_TEST_SUITE")) {
					_topLevelBuildTestSuiteMap.put(
						buildJSONObject.getURL(),
						parameters.get("CI_TEST_SUITE"));

					return parameters.get("CI_TEST_SUITE");
				}

				return "[Unknown]";
			}

			String topLevelBuildURL = buildJSONObject.getTopLevelBuildURL();

			if (_topLevelBuildTestSuiteMap.containsKey(topLevelBuildURL)) {
				return _topLevelBuildTestSuiteMap.get(topLevelBuildURL);
			}

			return "[Unknown]";
		}

		private final Map<String, String> _topLevelBuildTestSuiteMap =
			new HashMap<>();

	}

}