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

import java.nio.charset.Charset;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.input.ReversedLinesFileReader;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CISystemHistoryReportUtil {

	public static void generateCISystemHistoryReport(
			String jobName, String testSuiteName)
		throws IOException {

		writeAllDurationsJavaScriptFile();

		writeDateDurationsJavaScriptFiles(jobName, testSuiteName);

		writeIndexHtmlFile();
	}

	protected static void writeAllDurationsJavaScriptFile() throws IOException {
		StringBuilder sb = new StringBuilder();

		for (DurationReport durationReport : _getDurationReports()) {
			sb.append(durationReport.getAllDurationsJavaScriptContent());
		}

		JenkinsResultsParserUtil.write(
			new File(_CI_SYSTEM_HISTORY_REPORT_DIR, "js/all-durations.js"),
			sb.toString());
	}

	protected static void writeDateDurationsJavaScriptFile(
			String jobName, String testSuiteName, String dateString)
		throws IOException {

		List<JSONObject> buildResultJSONObjects = _getBuildResultJSONObjects(
			jobName, testSuiteName, dateString);

		StringBuilder sb = new StringBuilder();

		for (DurationReport durationReport : _getDurationReports()) {
			sb.append(
				durationReport.getDateDurationsJavaScriptContent(
					buildResultJSONObjects, dateString));
		}

		JenkinsResultsParserUtil.write(
			new File(
				_CI_SYSTEM_HISTORY_REPORT_DIR,
				"js/durations-" + dateString + ".js"),
			sb.toString());
	}

	protected static void writeDateDurationsJavaScriptFiles(
			String jobName, String testSuiteName)
		throws IOException {

		int size = _dateStrings.size();

		for (int i = size - _MONTH_RECORD_COUNT; i < size; i++) {
			writeDateDurationsJavaScriptFile(
				jobName, testSuiteName, _dateStrings.get(i));
		}
	}

	protected static void writeIndexHtmlFile() throws IOException {
		File indexHtmlFile = new File(
			_CI_SYSTEM_HISTORY_REPORT_DIR, "index.html");

		if (!indexHtmlFile.exists()) {
			return;
		}

		String content = JenkinsResultsParserUtil.read(indexHtmlFile);

		StringBuilder sb = new StringBuilder();

		for (String dateString : _dateStrings) {
			sb.append("\t\t<script src=\"js/durations-");
			sb.append(dateString);
			sb.append(".js\"></script>\n");
		}

		sb.append("\n\t\t<script src=\"js/all-durations.js\"></script>\n");

		JenkinsResultsParserUtil.write(
			indexHtmlFile,
			content.replaceAll("\\t\\t<script-durations />\\n", sb.toString()));
	}

	private static int _getBuildPropertyInt(
		String propertyName, int defaultValue) {

		try {
			return Integer.parseInt(
				JenkinsResultsParserUtil.getProperty(
					_buildProperties, propertyName));
		}
		catch (Exception exception) {
			return defaultValue;
		}
	}

	private static List<JSONObject> _getBuildResultJSONObjects(
		String jobName, final String testSuiteName, String dateString) {

		List<File> jenkinsConsoleGzFiles = _getJenkinsConsoleGzFiles(
			jobName, dateString);

		List<Callable<JSONObject>> callables = new ArrayList<>();

		_totalFileCacheSize = 0L;

		System.out.println(
			"Processing " + jenkinsConsoleGzFiles.size() + " files");

		for (final File jenkinsConsoleGzFile : jenkinsConsoleGzFiles) {
			callables.add(
				new Callable<JSONObject>() {

					@Override
					public JSONObject call() throws Exception {
						long start =
							JenkinsResultsParserUtil.getCurrentTimeMillis();

						try {
							if (!Objects.equals(
									testSuiteName,
									_getCurrentTestSuiteName())) {

								return null;
							}

							return _getBuildResultJSONObject();
						}
						finally {
							long end =
								JenkinsResultsParserUtil.getCurrentTimeMillis();

							System.out.println(
								JenkinsResultsParserUtil.combine(
									JenkinsResultsParserUtil.getCanonicalPath(
										jenkinsConsoleGzFile),
									" processed in ",
									JenkinsResultsParserUtil.toDurationString(
										end - start)));
						}
					}

					private JSONObject _getBuildResultJSONObject() {
						File buildResultGzFile = new File(
							jenkinsConsoleGzFile.getParentFile(),
							"build-result.json.gz");

						if (!buildResultGzFile.exists()) {
							return null;
						}

						String timestamp =
							JenkinsResultsParserUtil.getDistinctTimeStamp();

						File buildResultGzTempFile = new File(
							"build-result-" + timestamp + ".json.gz");

						try {
							JenkinsResultsParserUtil.copy(
								buildResultGzFile, buildResultGzTempFile);
						}
						catch (Exception exception) {
							return null;
						}

						File buildResultTempFile = new File(
							"build-result-" + timestamp + ".json");

						try {
							JenkinsResultsParserUtil.unGzip(
								buildResultGzTempFile, buildResultTempFile);
						}
						catch (Exception exception) {
							return null;
						}
						finally {
							if (buildResultGzTempFile.exists()) {
								JenkinsResultsParserUtil.delete(
									buildResultGzTempFile);
							}
						}

						synchronized (_totalFileCacheSize) {
							_totalFileCacheSize += buildResultTempFile.length();

							if (_totalFileCacheSize >= _MAX_FILE_CACHE_SIZE) {
								System.out.println(
									"SKIPPED: " + buildResultGzFile);

								return null;
							}
						}

						try {
							return new JSONObject(
								JenkinsResultsParserUtil.read(
									buildResultTempFile));
						}
						catch (Exception exception) {
							return null;
						}
						finally {
							if (buildResultTempFile.exists()) {
								JenkinsResultsParserUtil.delete(
									buildResultTempFile);
							}
						}
					}

					private String _getCurrentTestSuiteName() {
						if (!jenkinsConsoleGzFile.exists()) {
							return null;
						}

						String timestamp =
							JenkinsResultsParserUtil.getDistinctTimeStamp();

						File jenkinsConsoleGzTempFile = new File(
							"jenkins-console-" + timestamp + ".txt.gz");

						try {
							JenkinsResultsParserUtil.copy(
								jenkinsConsoleGzFile, jenkinsConsoleGzTempFile);
						}
						catch (Exception exception) {
							return null;
						}

						File jenkinsConsoleTempFile = new File(
							"jenkins-console-" + timestamp + ".txt");

						try {
							JenkinsResultsParserUtil.unGzip(
								jenkinsConsoleGzTempFile,
								jenkinsConsoleTempFile);
						}
						catch (Exception exception) {
							return null;
						}
						finally {
							if (jenkinsConsoleGzTempFile.exists()) {
								JenkinsResultsParserUtil.delete(
									jenkinsConsoleGzTempFile);
							}
						}

						try (ReversedLinesFileReader reversedLinesFileReader =
								new ReversedLinesFileReader(
									jenkinsConsoleTempFile,
									Charset.defaultCharset())) {

							String line;

							long start =
								JenkinsResultsParserUtil.getCurrentTimeMillis();

							while ((line =
										reversedLinesFileReader.readLine()) !=
											null) {

								long end =
									JenkinsResultsParserUtil.
										getCurrentTimeMillis();

								long duration = end - start;

								if (duration >= (10 * 1000)) {
									break;
								}

								Matcher matcher =
									_jenkinsConsolePattern.matcher(line);

								if (matcher.find()) {
									return matcher.group("testSuiteName");
								}
							}
						}
						catch (Exception exception) {
							return null;
						}
						finally {
							if (jenkinsConsoleTempFile.exists()) {
								JenkinsResultsParserUtil.delete(
									jenkinsConsoleTempFile);
							}
						}

						return null;
					}

				});
		}

		ParallelExecutor<JSONObject> parallelExecutor = new ParallelExecutor<>(
			callables, _executorService);

		List<JSONObject> buildResultJSONObjects = parallelExecutor.execute();

		buildResultJSONObjects.removeAll(Collections.singleton(null));

		System.out.println(
			"Found " + buildResultJSONObjects.size() + " build results");

		return buildResultJSONObjects;
	}

	private static List<DurationReport> _getDurationReports() {
		List<DurationReport> durationReports = new ArrayList<>();

		for (String propertyName : _buildProperties.stringPropertyNames()) {
			Matcher matcher = _durationPropertyPattern.matcher(propertyName);

			if (!matcher.find()) {
				continue;
			}

			durationReports.add(
				new DurationReport(
					matcher.group("buildType"),
					matcher.group("durationReportType")));
		}

		Collections.sort(durationReports);

		return durationReports;
	}

	private static List<File> _getJenkinsConsoleGzFiles(
		String jobName, String dateString) {

		List<File> jenkinsConsoleGzFiles = new ArrayList<>();

		File testrayLogsDateDir = new File(_TESTRAY_LOGS_DIR, dateString);

		if (!testrayLogsDateDir.exists()) {
			return jenkinsConsoleGzFiles;
		}

		Process process;

		try {
			process = JenkinsResultsParserUtil.executeBashCommands(
				true, _TESTRAY_LOGS_DIR, 1000 * 60 * 60,
				JenkinsResultsParserUtil.combine(
					"find ", dateString, "/*/",
					JenkinsResultsParserUtil.escapeForBash(jobName),
					"/*/jenkins-console.txt.gz"));
		}
		catch (IOException | TimeoutException exception) {
			return jenkinsConsoleGzFiles;
		}

		int exitValue = process.exitValue();

		if (exitValue != 0) {
			return jenkinsConsoleGzFiles;
		}

		String output = null;

		try {
			output = JenkinsResultsParserUtil.readInputStream(
				process.getInputStream());

			output = output.replace("Finished executing Bash commands.\n", "");

			output = output.trim();
		}
		catch (IOException ioException) {
			return jenkinsConsoleGzFiles;
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(output)) {
			return jenkinsConsoleGzFiles;
		}

		for (String jenkinsConsoleGzFilePath : output.split("\n")) {
			jenkinsConsoleGzFiles.add(
				new File(_TESTRAY_LOGS_DIR, jenkinsConsoleGzFilePath));
		}

		return jenkinsConsoleGzFiles;
	}

	private static final File _CI_SYSTEM_HISTORY_REPORT_DIR;

	private static final long _MAX_FILE_CACHE_SIZE = 600 * 1024 * 1024;

	private static final int _MONTH_COUNT;

	private static final int _MONTH_RECORD_COUNT;

	private static final long _START_TIME =
		JenkinsResultsParserUtil.getCurrentTimeMillis();

	private static final File _TESTRAY_LOGS_DIR;

	private static final Properties _buildProperties;
	private static final List<String> _dateStrings;
	private static final Pattern _durationPropertyPattern = Pattern.compile(
		"ci.system.history.title\\[(?<buildType>[^\\]]+)\\]" +
			"\\[(?<durationReportType>[^\\]]+)\\]");
	private static final ExecutorService _executorService =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(50, true);
	private static final Pattern _jenkinsConsolePattern = Pattern.compile(
		"[\\s\\S]*CI_TEST_SUITE[=](?<testSuiteName>[^&]+)[\\s\\S]*");
	private static Long _totalFileCacheSize;

	static {
		_buildProperties = new Properties() {
			{
				try {
					putAll(JenkinsResultsParserUtil.getBuildProperties());
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			}
		};

		_CI_SYSTEM_HISTORY_REPORT_DIR = new File(
			_buildProperties.getProperty("ci.system.history.report.dir"));

		_MONTH_COUNT = _getBuildPropertyInt(
			"ci.system.history.report.month.count", 12);

		_MONTH_RECORD_COUNT = _getBuildPropertyInt(
			"ci.system.history.report.month.record.count", 2);

		_dateStrings = new ArrayList<String>() {
			{
				LocalDate currentLocalDate = LocalDate.now();

				for (int i = _MONTH_COUNT - 1; i >= 0; i--) {
					LocalDate localDate = currentLocalDate.minusMonths(i);

					add(
						localDate.format(
							DateTimeFormatter.ofPattern("yyyy-MM")));
				}
			}
		};

		_TESTRAY_LOGS_DIR = new File(
			_buildProperties.getProperty("jenkins.testray.results.dir"),
			"production/logs");
	}

	private static class DurationReport implements Comparable<DurationReport> {

		@Override
		public int compareTo(DurationReport durationReport) {
			String id = durationReport._getID();

			return id.compareTo(_getID());
		}

		public String getAllDurationsJavaScriptContent() {
			StringBuilder sb = new StringBuilder();

			sb.append("var ");
			sb.append(getAllDurationsJavaScriptVarName());
			sb.append(" = ");
			sb.append(getAllDurationsJavaScriptVarValue());
			sb.append(";\n");

			sb.append("createContainer(");
			sb.append(getAllDurationsJavaScriptVarName());
			sb.append(");\n\n");

			return sb.toString();
		}

		public String getAllDurationsJavaScriptVarName() {
			return JenkinsResultsParserUtil.combine(
				_getJavaScriptID(), "_all_durations");
		}

		public String getAllDurationsJavaScriptVarValue() {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("description", _description);
			jsonObject.put("durations", getDurationsJavaScriptVarNames());
			jsonObject.put("durations_dates", getDateJavaScriptVarNames());
			jsonObject.put("id", _getID());
			jsonObject.put(
				"modification_date", "new Date(" + _START_TIME + ")");
			jsonObject.put("title", _title);

			String javascriptVarValue = jsonObject.toString();

			return javascriptVarValue.replaceAll(
				"\\\"([^\\\"]+_\\d{4}_\\d{2})\\\"", "$1");
		}

		public String getDateDurationsJavaScriptContent(
			List<JSONObject> buildResultJSONObjects, String dateString) {

			List<Long> durations = getDurations(buildResultJSONObjects);

			StringBuilder sb = new StringBuilder();

			sb.append("var ");
			sb.append(getDateJavaScriptVarName(dateString));
			sb.append(" = ");
			sb.append(getDateJavaScriptVarValue(dateString, durations));

			sb.append("\nvar ");
			sb.append(getDurationsJavaScriptVarName(dateString));
			sb.append(" = ");
			sb.append(durations);
			sb.append("\n\n");

			return sb.toString();
		}

		public String getDateJavaScriptVarName(String dateString) {
			return JenkinsResultsParserUtil.combine(
				_getJavaScriptID(), "_date_", dateString.replaceAll("-", "_"));
		}

		public List<String> getDateJavaScriptVarNames() {
			List<String> dateDurationJavaScriptVars = new ArrayList<>();

			for (String dateString : _dateStrings) {
				dateDurationJavaScriptVars.add(
					getDateJavaScriptVarName(dateString));
			}

			return dateDurationJavaScriptVars;
		}

		public String getDateJavaScriptVarValue(
			String dateString, List<Long> durations) {

			JSONArray jsonArray = new JSONArray();

			jsonArray.put(dateString);

			if ((durations == null) || durations.isEmpty()) {
				return jsonArray.toString();
			}

			String meanString = JenkinsResultsParserUtil.toDurationString(
				JenkinsResultsParserUtil.getAverage(durations));

			jsonArray.put("mean: " + meanString);

			jsonArray.put("total: " + String.format("%,d", durations.size()));

			return jsonArray.toString();
		}

		public List<Long> getDurations(
			List<JSONObject> buildResultJSONObjects) {

			List<Long> durations = new ArrayList<>();

			for (JSONObject buildResultJSONObject : buildResultJSONObjects) {
				if (!buildResultJSONObject.has("duration")) {
					continue;
				}

				if (_buildType.equals("top.level")) {
					StopWatchRecordsGroup stopWatchRecordsGroup =
						new StopWatchRecordsGroup(buildResultJSONObject);

					if (_durationReportType.equals("active.duration")) {
						durations.add(
							_getActiveDuration(stopWatchRecordsGroup));

						continue;
					}

					if (_durationReportType.equals("passive.duration")) {
						durations.add(
							_getPassiveDuration(stopWatchRecordsGroup));

						continue;
					}

					durations.add(
						_getDuration(
							stopWatchRecordsGroup, _durationReportType));

					continue;
				}

				if (!_buildType.equals("downstream") ||
					!buildResultJSONObject.has("batchResults")) {

					continue;
				}

				JSONArray batchResultsJSONArray =
					buildResultJSONObject.getJSONArray("batchResults");

				for (int i = 0; i < batchResultsJSONArray.length(); i++) {
					JSONObject batchResultsJSONObject =
						batchResultsJSONArray.getJSONObject(i);

					if (!batchResultsJSONObject.has("buildResults")) {
						continue;
					}

					JSONArray buildResultsJSONArray =
						batchResultsJSONObject.getJSONArray("buildResults");

					for (int j = 0; j < buildResultsJSONArray.length(); j++) {
						StopWatchRecordsGroup stopWatchRecordsGroup =
							new StopWatchRecordsGroup(
								buildResultsJSONArray.getJSONObject(j));

						durations.add(
							_getDuration(
								stopWatchRecordsGroup, _durationReportType));
					}
				}
			}

			durations.removeAll(Collections.singleton(0L));

			return durations;
		}

		public String getDurationsJavaScriptVarName(String dateString) {
			return JenkinsResultsParserUtil.combine(
				_getJavaScriptID(), "_durations_",
				dateString.replaceAll("-", "_"));
		}

		public List<String> getDurationsJavaScriptVarNames() {
			List<String> durationsJavaScriptVarNames = new ArrayList<>();

			for (String dateString : _dateStrings) {
				durationsJavaScriptVarNames.add(
					getDurationsJavaScriptVarName(dateString));
			}

			return durationsJavaScriptVarNames;
		}

		private DurationReport(String buildType, String durationReportType) {
			_buildType = buildType;

			_description = JenkinsResultsParserUtil.getProperty(
				_buildProperties, "ci.system.history.description", buildType,
				durationReportType);

			_title = JenkinsResultsParserUtil.getProperty(
				_buildProperties, "ci.system.history.title", buildType,
				durationReportType);

			_durationReportType = durationReportType;
		}

		private long _getActiveDuration(
			StopWatchRecordsGroup stopWatchRecordsGroup) {

			if (stopWatchRecordsGroup == null) {
				return 0L;
			}

			long passiveDuration = _getPassiveDuration(stopWatchRecordsGroup);

			if (passiveDuration == 0) {
				return 0L;
			}

			long totalDuration = _getDuration(
				stopWatchRecordsGroup, "total.duration");

			if (passiveDuration > totalDuration) {
				return totalDuration;
			}

			return totalDuration - passiveDuration;
		}

		private long _getDuration(
			StopWatchRecordsGroup stopWatchRecordsGroup,
			String durationReportType) {

			if (stopWatchRecordsGroup == null) {
				return 0L;
			}

			StopWatchRecord stopWatchRecord = stopWatchRecordsGroup.get(
				durationReportType);

			if (stopWatchRecord == null) {
				return 0L;
			}

			long duration = stopWatchRecord.getDuration();

			if (duration < 0) {
				return 0L;
			}

			return duration;
		}

		private String _getID() {
			String id = _buildType + "-" + _durationReportType;

			id = id.replaceAll("_", "-");
			id = id.replaceAll("\\.", "-");

			return id;
		}

		private String _getJavaScriptID() {
			String javascriptID = _buildType + "_" + _durationReportType;

			javascriptID = javascriptID.replaceAll("-", "_");
			javascriptID = javascriptID.replaceAll("\\.", "_");

			return javascriptID;
		}

		private long _getPassiveDuration(
			StopWatchRecordsGroup stopWatchRecordsGroup) {

			if (stopWatchRecordsGroup == null) {
				return 0L;
			}

			long passiveDuration = 0L;

			passiveDuration += _getDuration(
				stopWatchRecordsGroup, "wait.for.invoked.jobs");
			passiveDuration += _getDuration(
				stopWatchRecordsGroup, "wait.for.invoked.smoke.jobs");

			if (passiveDuration > 0L) {
				return passiveDuration;
			}

			return _getDuration(
				stopWatchRecordsGroup, "invoke.downstream.builds");
		}

		private final String _buildType;
		private final String _description;
		private final String _durationReportType;
		private final String _title;

	}

}