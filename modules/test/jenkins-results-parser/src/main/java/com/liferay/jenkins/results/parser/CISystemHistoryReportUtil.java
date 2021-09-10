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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CISystemHistoryReportUtil {

	public static void generateCISystemHistoryReport(
			String jobName, String testSuiteName)
		throws IOException {

		writeAllDurationsJavascriptFile();

		writeIndexHtmlFile();
	}

	protected static void writeAllDurationsJavascriptFile() throws IOException {
		StringBuilder sb = new StringBuilder();

		for (DurationReport durationReport : _getDurationReports()) {
			sb.append(durationReport.getAllDurationsJavascriptContent());
		}

		JenkinsResultsParserUtil.write(
			new File(_CI_SYSTEM_HISTORY_REPORT_DIR, "js/all-durations.js"),
			sb.toString());
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

	private static final File _CI_SYSTEM_HISTORY_REPORT_DIR;

	private static final int _MONTHS_PER_YEAR = 12;

	private static final long _START_TIME =
		JenkinsResultsParserUtil.getCurrentTimeMillis();

	private static final Properties _buildProperties;
	private static final List<String> _dateStrings;
	private static final Pattern _durationPropertyPattern = Pattern.compile(
		"ci.system.history.title\\[(?<buildType>[^\\]]+)\\]" +
			"\\[(?<durationReportType>[^\\]]+)\\]");

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

		_dateStrings = new ArrayList() {
			{
				LocalDate currentLocalDate = LocalDate.now();

				for (int i = _MONTHS_PER_YEAR - 1; i >= 0; i--) {
					LocalDate localDate = currentLocalDate.minusMonths(i);

					add(
						localDate.format(
							DateTimeFormatter.ofPattern("yyyy-MM")));
				}
			}
		};
	}

	private static class DurationReport implements Comparable<DurationReport> {

		@Override
		public int compareTo(DurationReport durationReport) {
			String id = durationReport._getID();

			return id.compareTo(_getID());
		}

		public String getAllDurationsJavascriptContent() {
			StringBuilder sb = new StringBuilder();

			sb.append("var ");
			sb.append(getAllDurationsJavascriptVarName());
			sb.append(" = ");
			sb.append(getAllDurationsJavascriptVarValue());
			sb.append(";\n");

			sb.append("createContainer(");
			sb.append(getAllDurationsJavascriptVarName());
			sb.append(");\n\n");

			return sb.toString();
		}

		public String getAllDurationsJavascriptVarName() {
			return JenkinsResultsParserUtil.combine(
				_getJavascriptID(), "_all_durations");
		}

		public String getAllDurationsJavascriptVarValue() {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("description", _description);
			jsonObject.put("durations", getDurationsJavascriptVarNames());
			jsonObject.put("durations_dates", getDateJavascriptVarNames());
			jsonObject.put("id", _getID());
			jsonObject.put(
				"modification_date", "new Date(" + _START_TIME + ")");
			jsonObject.put("title", _title);

			String javascriptVarValue = jsonObject.toString();

			return javascriptVarValue.replaceAll(
				"\\\"([^\\\"]+_\\d{4}_\\d{2})\\\"", "$1");
		}

		public String getDateJavascriptVarName(String dateString) {
			return JenkinsResultsParserUtil.combine(
				_getJavascriptID(), "_date_", dateString.replaceAll("-", "_"));
		}

		public List<String> getDateJavascriptVarNames() {
			List<String> dateDurationJavascriptVars = new ArrayList<>();

			for (String dateString : _dateStrings) {
				dateDurationJavascriptVars.add(
					getDateJavascriptVarName(dateString));
			}

			return dateDurationJavascriptVars;
		}

		public String getDurationsJavascriptVarName(String dateString) {
			return JenkinsResultsParserUtil.combine(
				_getJavascriptID(), "_durations_",
				dateString.replaceAll("-", "_"));
		}

		public List<String> getDurationsJavascriptVarNames() {
			List<String> durationsJavascriptVarNames = new ArrayList<>();

			for (String dateString : _dateStrings) {
				durationsJavascriptVarNames.add(
					getDurationsJavascriptVarName(dateString));
			}

			return durationsJavascriptVarNames;
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

		private String _getID() {
			String id = _buildType + "-" + _durationReportType;

			id = id.replaceAll("_", "-");
			id = id.replaceAll("\\.", "-");

			return id;
		}

		private String _getJavascriptID() {
			String javascriptID = _buildType + "_" + _durationReportType;

			javascriptID = javascriptID.replaceAll("-", "_");
			javascriptID = javascriptID.replaceAll("\\.", "_");

			return javascriptID;
		}

		private final String _buildType;
		private final String _description;
		private final String _durationReportType;
		private final String _title;

	}

}