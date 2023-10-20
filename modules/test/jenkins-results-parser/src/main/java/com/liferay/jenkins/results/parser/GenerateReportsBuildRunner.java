/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.metrics.BuildHistoryReport;

import java.io.File;
import java.io.IOException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

/**
 * @author Kenji Heigel
 */
public class GenerateReportsBuildRunner extends BaseBuildRunner<BuildData> {

	@Override
	public Workspace getWorkspace() {
		if (_workspace != null) {
			return _workspace;
		}

		_workspace = WorkspaceFactory.newWorkspace();

		return _workspace;
	}

	@Override
	public void run() {
		_validateBuildParameters();

		setUpWorkspace();

		_copyArchivedBuildData();

		_generateReports();
	}

	@Override
	public void tearDown() {
		try {
			FileUtils.deleteDirectory(new File(_TMP_BASE_DIR_PATH));
		}
		catch (IOException ioException) {
			System.out.println(
				"Unable to delete directory: " + _TMP_BASE_DIR_PATH);
		}

		super.tearDown();
	}

	public enum Report {

		BUILD_HISTORY("Build History"),
		PULL_REQUEST_HISTORY("Pull Request History");

		public String getDirName() {
			return _reportDirNames.get(_string);
		}

		@Override
		public String toString() {
			return _string;
		}

		private Report(String string) {
			_string = string;
		}

		private final String _string;

	}

	protected GenerateReportsBuildRunner(BuildData buildData) {
		super(buildData);
	}

	protected String getBuildParameter(String key) {
		BuildData buildData = getBuildData();

		return buildData.getBuildParameter(key);
	}

	private void _copyArchivedBuildData() {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		String[] dateStrings = JenkinsResultsParserUtil.getDateStrings(
			_REPORT_DURATION_DAYS,
			LocalDate.parse(_START_DATE_STRING, _dateTimeFormatter));

		File baseDir = new File(
			buildProperties.getProperty("archive.ci.build.data.archive.dir"));

		for (String dateString : dateStrings) {
			File archiveFile = new File(baseDir, dateString + ".tar.gz");

			if (archiveFile.exists()) {
				JenkinsResultsParserUtil.unTarGzip(
					archiveFile, new File(_TMP_ARCHIVE_DIR_PATH, dateString));
			}
		}
	}

	private void _generateBuildHistoryReport(String filePath)
		throws IOException {

		BuildHistoryReport aggregateBuildHistoryReport =
			BuildHistoryReport.newAggregateReport(
				_REPORT_DURATION_DAYS, new File(filePath), _START_DATE_STRING);

		aggregateBuildHistoryReport.write();
	}

	private void _generatePullRequestReport(String filePath)
		throws IOException {

		BuildHistoryReport testSuiteBuildHistoryReport =
			BuildHistoryReport.newTestSuiteReport(
				_REPORT_DURATION_DAYS, new File(filePath), _START_DATE_STRING);

		testSuiteBuildHistoryReport.write();
	}

	private void _generateReports() {
		String[] reportNames = _getReportNames();

		if (reportNames == null) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		for (String reportName : reportNames) {
			String reportFilePath =
				_TMP_REPORT_DIR_PATH + _getReportDirName(reportName);

			try {
				if (reportName.equals(Report.BUILD_HISTORY.toString())) {
					_generateBuildHistoryReport(reportFilePath);
				}

				if (reportName.equals(Report.PULL_REQUEST_HISTORY.toString())) {
					_generatePullRequestReport(reportFilePath);
				}
			}
			catch (IOException ioException) {
				System.out.println(
					"Unable to write " + reportName + " to " + reportFilePath);

				continue;
			}

			JenkinsResultsParserUtil.rsync(
				"test-1-0", _REPORT_RSYNC_DESTINATION_DIR_PATH, null,
				reportFilePath);

			LocalDate localDate = LocalDate.parse(
				_START_DATE_STRING, _dateTimeFormatter);

			localDate = localDate.plusDays(_REPORT_DURATION_DAYS);

			JenkinsResultsParserUtil.rsync(
				"test-1-0",
				_REPORT_RSYNC_DESTINATION_DIR_PATH + "archived-reports/" +
					localDate.format(_dateTimeFormatter),
				null, reportFilePath);

			sb.append("<a href=\"http://test-1-0/userContent/reports/");

			sb.append(_getReportDirName(reportName));

			sb.append("/index.html\">");

			sb.append(reportName);

			sb.append(" Report</a><br />");
		}

		BuildData buildData = getBuildData();

		buildData.setBuildDescription(sb.toString());

		updateBuildDescription();
	}

	private String _getReportDirName(String reportName) {
		return _reportDirNames.get(reportName);
	}

	private String[] _getReportNames() {
		String buildParameter = getBuildParameter("REPORT_NAMES");

		if (buildParameter == null) {
			return null;
		}

		return buildParameter.split("\\s*,\\s*");
	}

	private void _validateBuildParameters() {
		String[] reportNames = _getReportNames();

		if (reportNames == null) {
			throw new RuntimeException("REPORT_NAMES parameter must be set");
		}

		for (String reportName : reportNames) {
			if (!_validReportNames.contains(reportName)) {
				throw new RuntimeException(
					"REPORT_NAMES parameter contains invalid report type: " +
						reportName);
			}
		}
	}

	private static final long _REPORT_DURATION_DAYS = 14;

	private static final String _REPORT_RSYNC_DESTINATION_DIR_PATH =
		"/opt/java/jenkins/userContent/reports/";

	private static final String _START_DATE_STRING;

	private static final String _TMP_ARCHIVE_DIR_PATH =
		GenerateReportsBuildRunner._TMP_BASE_DIR_PATH + "jenkins/";

	private static final String _TMP_BASE_DIR_PATH =
		"/opt/dev/projects/github/liferay-jenkins-ee/tmp/";

	private static final String _TMP_REPORT_DIR_PATH =
		_TMP_BASE_DIR_PATH + "reports/";

	private static final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern("yyyyMMdd");
	private static final Map<String, String> _reportDirNames =
		new HashMap<String, String>() {
			{
				put(Report.BUILD_HISTORY.toString(), "build-history-report");
				put(
					Report.PULL_REQUEST_HISTORY.toString(),
					"pull-request-report");
			}
		};
	private static final List<String> _validReportNames = Arrays.asList(
		Report.BUILD_HISTORY.toString(),
		Report.PULL_REQUEST_HISTORY.toString());

	static {
		Instant instant = Instant.now();

		instant = instant.minus(Period.ofDays((int)_REPORT_DURATION_DAYS));

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

		_START_DATE_STRING = zonedDateTime.format(_dateTimeFormatter);
	}

	private Workspace _workspace;

}