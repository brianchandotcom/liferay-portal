/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author Kenji Heigel
 */
public class GenerateReportsControllerBuildRunner
	extends BaseBuildRunner<BuildData> {

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
		invokeGenerateReportsBuild();
	}

	@Override
	public void tearDown() {
	}

	protected GenerateReportsControllerBuildRunner(BuildData buildData) {
		super(buildData);
	}

	protected void invokeGenerateReportsBuild() {
		List<String> reportNames = _getSelectedReportNames();

		if (reportNames.isEmpty()) {
			System.out.println("There are no reports to create at this time.");

			keepJenkinsBuild(false);

			return;
		}

		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		StringBuilder sb = new StringBuilder();

		String jenkinsMasterName = buildProperties.getProperty(
			"report.generate.reports.jenkins.master");

		String jobURL = "http://" + jenkinsMasterName + "/job/generate-reports";

		sb.append(jobURL);

		sb.append("/buildWithParameters?token=");

		sb.append(buildProperties.getProperty("jenkins.authentication.token"));

		Map<String, String> invocationParameters = new HashMap<>();

		BuildData buildData = getBuildData();

		invocationParameters.put(
			"JENKINS_GITHUB_URL", buildData.getJenkinsGitHubURL());

		Collections.sort(reportNames);

		invocationParameters.put("REPORT_NAMES", String.join(",", reportNames));

		for (Map.Entry<String, String> invocationParameter :
				invocationParameters.entrySet()) {

			String invocationParameterValue = invocationParameter.getValue();

			if (JenkinsResultsParserUtil.isNullOrEmpty(
					invocationParameterValue)) {

				continue;
			}

			sb.append("&");
			sb.append(invocationParameter.getKey());
			sb.append("=");
			sb.append(invocationParameterValue);
		}

		try {
			JenkinsResultsParserUtil.toString(sb.toString());

			System.out.println(
				"The following reports will be generated at: " + jobURL);

			for (String reportName : reportNames) {
				System.out.println(reportName);
			}
		}
		catch (IOException ioException) {
			System.out.println(
				"Unable to invoke a new build to generate reports");

			ioException.printStackTrace();
		}

		_updateBuildDescription(reportNames);
	}

	private String _getBuildDescription(Build build) {
		String buildDescription = build.getBuildDescription();

		if (buildDescription == null) {
			return "";
		}

		return buildDescription;
	}

	private List<Build> _getBuildHistory() {
		BuildData buildData = getBuildData();

		Build build = BuildFactory.newBuild(buildData.getBuildURL(), null);

		Job job = JobFactory.newJob(buildData.getJobName());

		return job.getBuildHistory(build.getJenkinsMaster());
	}

	private Map<String, Long> _getLatestReportUpdateTimes() {
		Map<String, Long> latestReportUpdateTimes = new HashMap<>();

		List<Build> builds = _getBuildHistory();

		BuildData buildData = getBuildData();

		Build currentBuild = BuildFactory.newBuild(
			buildData.getBuildURL(), null);

		builds.remove(currentBuild);

		for (GenerateReportsBuildRunner.Report report :
				GenerateReportsBuildRunner.Report.values()) {

			String reportName = report.toString();

			for (Build build : builds) {
				String buildDescription = _getBuildDescription(build);

				if (buildDescription.contains(reportName)) {
					latestReportUpdateTimes.put(
						reportName, build.getStartTime());

					break;
				}
			}
		}

		for (GenerateReportsBuildRunner.Report report :
				GenerateReportsBuildRunner.Report.values()) {

			String reportName = report.toString();

			long defaultStartTime =
				buildData.getStartTime() - _getReportStaleDuration(reportName);

			if (!latestReportUpdateTimes.containsKey(reportName)) {
				latestReportUpdateTimes.put(reportName, defaultStartTime);
			}
		}

		return latestReportUpdateTimes;
	}

	private long _getReportStaleDuration(String reportName) {
		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		String reportStaleDuration = buildProperties.getProperty(
			JenkinsResultsParserUtil.combine(
				"report.stale.duration[", reportName, "]"));

		if (reportStaleDuration == null) {
			return _DEFAULT_REPORT_STALE_DURATION;
		}

		return TimeUnit.MINUTES.toMillis(Long.parseLong(reportStaleDuration));
	}

	private List<String> _getSelectedReportNames() {
		if (_selectedReportNames != null) {
			return _selectedReportNames;
		}

		_selectedReportNames = new ArrayList<>();

		Map<String, Long> latestReportUpdateTimes =
			_getLatestReportUpdateTimes();

		BuildData buildData = getBuildData();

		long startTime = buildData.getStartTime();

		for (Map.Entry<String, Long> entry :
				latestReportUpdateTimes.entrySet()) {

			String reportName = entry.getKey();

			long reportStaleDuration = startTime - entry.getValue();

			if (reportStaleDuration >= _getReportStaleDuration(reportName)) {
				System.out.println(
					JenkinsResultsParserUtil.combine(
						reportName, " was last generated ",
						JenkinsResultsParserUtil.toDurationString(
							reportStaleDuration),
						" ago which exceeds the stale duration of ",
						JenkinsResultsParserUtil.toDurationString(
							_getReportStaleDuration(reportName))));

				_selectedReportNames.add(reportName);
			}
		}

		return _selectedReportNames;
	}

	private void _updateBuildDescription(List<String> reportNames) {
		StringBuilder sb = new StringBuilder();

		for (String reportName : reportNames) {
			sb.append(reportName);

			sb.append(" - ");
		}

		sb.setLength(sb.length() - 3);

		BuildData buildData = getBuildData();

		buildData.setBuildDescription(sb.toString());

		updateBuildDescription();
	}

	private static final long _DEFAULT_REPORT_STALE_DURATION = 1440 * 60 * 1000;

	private List<String> _selectedReportNames;
	private Workspace _workspace;

}