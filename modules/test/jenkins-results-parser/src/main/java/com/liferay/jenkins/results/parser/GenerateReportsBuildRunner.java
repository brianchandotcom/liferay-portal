/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.metrics.BuildHistoryProcessor;
import com.liferay.jenkins.results.parser.metrics.BuildHistoryReport;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

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

		BUILD_HISTORY("Build History"), CI_SYSTEM_HISTORY("CI System History"),
		CI_SYSTEM_STATUS("CI System Status"),
		PULL_REQUEST_HISTORY("Pull Request History"),
		RELEASE_HISTORY("Release History"),
		UPSTREAM_HISTORY("Upstream History"), UTILIZATION("Utilization");

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

	private void _archiveReport(String filePath) {
		JenkinsResultsParserUtil.rsync(
			"test-1-0",
			_REPORT_RSYNC_DESTINATION_DIR_PATH + "archived-reports/" +
				_CURRENT_DATE_STRING,
			null, filePath);
	}

	private void _copyArchivedBuildData(
		long durationDays, String startDateString) {

		String[] dateStrings = JenkinsResultsParserUtil.getDateStrings(
			durationDays, LocalDate.parse(startDateString, _dateTimeFormatter));

		File baseDir = new File(
			_buildProperties.getProperty("archive.ci.build.data.archive.dir"));

		List<Callable<Void>> callables = new ArrayList<>();

		for (final String dateString : dateStrings) {
			callables.add(
				new Callable<Void>() {

					@Override
					public Void call() {
						File archiveFile = new File(
							baseDir, dateString + ".tar.gz");

						File unarchivedDir = new File(
							_TMP_ARCHIVE_DIR_PATH, dateString);

						if (archiveFile.exists() && !unarchivedDir.exists()) {
							System.out.println(
								"Extracting " + archiveFile + " to " +
									unarchivedDir);

							JenkinsResultsParserUtil.unTarGzip(
								archiveFile, unarchivedDir);
						}

						return null;
					}

				});
		}

		ParallelExecutor<Void> parallelExecutor = new ParallelExecutor<>(
			callables, BuildHistoryProcessor.getExecutorService(),
			"_copyArchivedBuildData");

		try {
			parallelExecutor.execute();
		}
		catch (TimeoutException timeoutException) {
			throw new RuntimeException(timeoutException);
		}
	}

	private void _copyArchivedNodeData(
			long durationDays, String startDateString)
		throws IOException {

		String[] dateStrings = JenkinsResultsParserUtil.getDateStrings(
			durationDays, LocalDate.parse(startDateString, _dateTimeFormatter));

		File dataArchiveDir = new File(
			_buildProperties.getProperty("archive.ci.build.data.archive.dir"));

		File baseArchiveDir = dataArchiveDir.getParentFile();

		for (String dateString : dateStrings) {
			File nodeDataArchiveFile = new File(
				baseArchiveDir, "reports/" + dateString + "/node.json");

			File nodeDataFile = new File(
				_TMP_BASE_DIR_PATH + "/nodes/" + dateString, "node.json");

			if (nodeDataArchiveFile.exists()) {
				FileUtils.copyFile(nodeDataArchiveFile, nodeDataFile);
			}
		}
	}

	private void _generateBuildHistoryReport(String reportName)
		throws IOException {

		long reportDurationDays = _getReportDurationDays(reportName);
		String startDateString = _getStartDateString(reportName);

		_copyArchivedBuildData(reportDurationDays, startDateString);

		String filePath = _getReportFilePath(reportName);

		BuildHistoryReport aggregateBuildHistoryReport =
			BuildHistoryReport.newAggregateReport(
				reportDurationDays, new File(filePath), startDateString);

		aggregateBuildHistoryReport.write();

		_updateReport(filePath);

		_archiveReport(filePath);
	}

	private void _generateCISystemHistoryReport(String reportName)
		throws IOException {

		String filePath = _getReportFilePath(reportName);

		CISystemHistoryReportUtil.generateCISystemHistoryReport(
			filePath,
			_buildProperties.getProperty("ci.system.history.report.job.name"),
			_buildProperties.getProperty(
				"ci.system.history.report.test.suite.name"));

		_updateReport(filePath);
	}

	private void _generateCISystemStatusReport(String reportName)
		throws IOException {

		String filePath = _getReportFilePath(reportName);

		CISystemStatusReportUtil.copyBaseReportFiles(filePath);

		Files.deleteIfExists(Paths.get(filePath, "js/testray-data.js"));

		long reportDurationDays = _getReportDurationDays(reportName);

		_copyArchivedNodeData(
			_getReportDurationDays(reportName),
			_getStartDateString(reportDurationDays - 1));

		CISystemStatusReportUtil.writeJenkinsDataJavaScriptFile(
			filePath + "/js/jenkins-data.js");

		String testrayDataFilepath = null;

		try {
			Process process = JenkinsResultsParserUtil.executeBashCommands(
				1000 * 30,
				JenkinsResultsParserUtil.combine(
					"ssh test-1-0 'find ", _REPORT_RSYNC_DESTINATION_DIR_PATH,
					_getReportDirName(Report.CI_SYSTEM_STATUS.toString()),
					"/js -name testray-data.js -mmin +60'"));

			testrayDataFilepath = JenkinsResultsParserUtil.readInputStream(
				process.getInputStream());
		}
		catch (IOException | TimeoutException exception) {
			System.out.println("Unable to get age of testray-data.js");
		}

		if ((testrayDataFilepath != null) &&
			testrayDataFilepath.contains("testray-data.js")) {

			CISystemStatusReportUtil.writeTestrayDataJavaScriptFile(
				filePath + "/js/testray-data.js",
				_buildProperties.getProperty(
					"ci.system.status.report.job.name"),
				_buildProperties.getProperty(
					"ci.system.status.report.test.suite.name"));
		}

		_updateReport(filePath);

		_updateNodeDataFile(filePath);

		_archiveReport(filePath);
	}

	private void _generatePullRequestReport(String reportName)
		throws IOException {

		long reportDurationDays = _getReportDurationDays(reportName);
		String startDateString = _getStartDateString(reportName);

		_copyArchivedBuildData(reportDurationDays, startDateString);

		String filePath = _getReportFilePath(reportName);

		BuildHistoryReport testSuiteBuildHistoryReport =
			BuildHistoryReport.newPullRequestTestSuiteReport(
				reportDurationDays, new File(filePath), startDateString);

		testSuiteBuildHistoryReport.write();

		_updateReport(filePath);

		_archiveReport(filePath);
	}

	private void _generateReleaseReport(String reportName) throws IOException {
		long reportDurationDays = _getReportDurationDays(reportName);
		String startDateString = _getStartDateString(reportName);

		_copyArchivedBuildData(reportDurationDays, startDateString);

		String filePath = _getReportFilePath(reportName);

		BuildHistoryReport testSuiteBuildHistoryReport =
			BuildHistoryReport.newReleaseTestSuiteReport(
				reportDurationDays, new File(filePath), startDateString);

		testSuiteBuildHistoryReport.write();

		_updateReport(filePath);

		_archiveReport(filePath);
	}

	private void _generateReports() {
		String[] reportNames = _getReportNames();

		if (reportNames == null) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		for (String reportName : reportNames) {
			try {
				if (reportName.equals(Report.BUILD_HISTORY.toString())) {
					_generateBuildHistoryReport(reportName);
				}

				if (reportName.equals(Report.CI_SYSTEM_HISTORY.toString())) {
					_generateCISystemHistoryReport(reportName);
				}

				if (reportName.equals(Report.CI_SYSTEM_STATUS.toString())) {
					_generateCISystemStatusReport(reportName);
				}

				if (reportName.equals(Report.PULL_REQUEST_HISTORY.toString())) {
					_generatePullRequestReport(reportName);
				}

				if (reportName.equals(Report.RELEASE_HISTORY.toString())) {
					_generateReleaseReport(reportName);
				}

				if (reportName.equals(Report.UPSTREAM_HISTORY.toString())) {
					_generateUpstreamReport(reportName);
				}

				if (reportName.equals(Report.UTILIZATION.toString())) {
					_generateUtilizationReport(reportName);
				}
			}
			catch (IOException ioException) {
				System.out.println(
					"Unable to write " + reportName + " to " +
						_getReportFilePath(reportName));

				continue;
			}

			sb.append("<a href=\"");
			sb.append("http://test-1-0.liferay.com/userContent/reports/");

			sb.append(_getReportDirName(reportName));

			sb.append("/index.html\">");

			sb.append(reportName);

			sb.append(" Report</a><br />");
		}

		BuildData buildData = getBuildData();

		buildData.setBuildDescription(sb.toString());

		updateBuildDescription();
	}

	private void _generateUpstreamReport(String reportName) throws IOException {
		long reportDurationDays = _getReportDurationDays(reportName);
		String startDateString = _getStartDateString(reportName);

		_copyArchivedBuildData(reportDurationDays, startDateString);

		String filePath = _getReportFilePath(reportName);

		BuildHistoryReport testSuiteBuildHistoryReport =
			BuildHistoryReport.newUpstreamTestSuiteReport(
				reportDurationDays, new File(filePath), startDateString);

		testSuiteBuildHistoryReport.write();

		_updateReport(filePath);

		_archiveReport(filePath);
	}

	private void _generateUtilizationReport(String reportName)
		throws IOException {

		String startDateString = _getStartDateString(reportName);

		LocalDate localDate = LocalDate.parse(
			startDateString, _dateTimeFormatter);

		DayOfWeek dayOfWeek = localDate.getDayOfWeek();

		while (dayOfWeek.getValue() != 1) {
			localDate = localDate.minusDays(1);

			dayOfWeek = localDate.getDayOfWeek();
		}

		startDateString = localDate.format(_dateTimeFormatter);

		long reportDurationDays = _getReportDurationDays(reportName);

		_copyArchivedBuildData(reportDurationDays, startDateString);

		String filePath = _getReportFilePath(reportName);

		BuildHistoryReport utilizationReport =
			BuildHistoryReport.newUtilizationReport(
				reportDurationDays, new File(filePath), startDateString);

		utilizationReport.write();

		_updateReport(filePath);

		_archiveReport(filePath);
	}

	private String _getReportDirName(String reportName) {
		return _reportDirNames.get(reportName);
	}

	private long _getReportDurationDays(String reportName) {
		String reportDurationDays = _buildProperties.getProperty(
			JenkinsResultsParserUtil.combine(
				"report.duration.days[", reportName, "]"));

		if (reportDurationDays == null) {
			reportDurationDays = _buildProperties.getProperty(
				"report.duration.days");
		}

		return Long.parseLong(reportDurationDays);
	}

	private String _getReportFilePath(String reportName) {
		return _TMP_REPORT_DIR_PATH + _getReportDirName(reportName);
	}

	private String[] _getReportNames() {
		String buildParameter = getBuildParameter("REPORT_NAMES");

		if (buildParameter == null) {
			return null;
		}

		return buildParameter.split("\\s*,\\s*");
	}

	private String _getStartDateString(long daysAgo) {
		LocalDate localDate = LocalDate.parse(
			_CURRENT_DATE_STRING, _dateTimeFormatter);

		localDate = localDate.minusDays(daysAgo);

		return localDate.format(_dateTimeFormatter);
	}

	private String _getStartDateString(String reportName) {
		return _getStartDateString(_getReportDurationDays(reportName));
	}

	private void _updateNodeDataFile(String filePath) throws IOException {
		File dataArchiveDir = new File(
			_buildProperties.getProperty("archive.ci.build.data.archive.dir"));

		File baseArchiveDir = dataArchiveDir.getParentFile();

		File nodeDataArchiveFile = new File(
			baseArchiveDir, "reports/" + _CURRENT_DATE_STRING + "/node.json");

		File nodeDataFile = new File(filePath, "node.json");

		if (nodeDataArchiveFile.exists()) {
			FileUtils.copyFile(nodeDataArchiveFile, nodeDataFile);
		}

		JenkinsCohort jenkinsCohort = JenkinsCohort.getInstance("test-1");

		jenkinsCohort.writeNodeDataJSONFile(nodeDataFile.getPath());

		FileUtils.copyFile(nodeDataFile, nodeDataArchiveFile);

		FileUtils.delete(nodeDataFile);
	}

	private void _updateReport(String filePath) {
		JenkinsResultsParserUtil.rsync(
			"test-1-0", _REPORT_RSYNC_DESTINATION_DIR_PATH, null, filePath);
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

	private static final String _CURRENT_DATE_STRING;

	private static final String _REPORT_RSYNC_DESTINATION_DIR_PATH =
		"/opt/java/jenkins/userContent/reports/";

	private static final String _TMP_ARCHIVE_DIR_PATH;

	private static final String _TMP_BASE_DIR_PATH;

	private static final String _TMP_REPORT_DIR_PATH;

	private static final Properties _buildProperties;
	private static final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern("yyyyMMdd");
	private static final Map<String, String> _reportDirNames =
		new HashMap<String, String>() {
			{
				put(Report.BUILD_HISTORY.toString(), "build-history-report");
				put(Report.CI_SYSTEM_HISTORY.toString(), "ci-system-history");
				put(Report.CI_SYSTEM_STATUS.toString(), "ci-system-status");
				put(
					Report.PULL_REQUEST_HISTORY.toString(),
					"pull-request-report");
				put(Report.RELEASE_HISTORY.toString(), "release-report");
				put(Report.UPSTREAM_HISTORY.toString(), "upstream-report");
				put(Report.UTILIZATION.toString(), "utilization-report");
			}
		};
	private static final List<String> _validReportNames = Arrays.asList(
		Report.BUILD_HISTORY.toString(), Report.CI_SYSTEM_HISTORY.toString(),
		Report.CI_SYSTEM_STATUS.toString(),
		Report.PULL_REQUEST_HISTORY.toString(),
		Report.RELEASE_HISTORY.toString(), Report.UPSTREAM_HISTORY.toString(),
		Report.UTILIZATION.toString());

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

		Instant instant = Instant.now();

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

		_CURRENT_DATE_STRING = zonedDateTime.format(_dateTimeFormatter);

		_TMP_BASE_DIR_PATH = _buildProperties.getProperty(
			"archive.ci.build.data.tmp.dir");

		_TMP_ARCHIVE_DIR_PATH = _TMP_BASE_DIR_PATH + "/builds/";

		_TMP_REPORT_DIR_PATH = _TMP_BASE_DIR_PATH + "/reports/";
	}

	private Workspace _workspace;

}