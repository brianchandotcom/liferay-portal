/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.testray.TestrayS3Bucket;
import com.liferay.jenkins.results.parser.testray.TestrayS3Object;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTopLevelBuildReport
	extends BaseBuildReport implements TopLevelBuildReport {

	@Override
	public void addTestrayAttachmentURL(URL testrayAttachmentURL) {
		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		if (buildReportJSONObject == null) {
			return;
		}

		JSONArray jsonArray = buildReportJSONObject.optJSONArray(
			"testrayAttachmentURLs");

		if (jsonArray == null) {
			jsonArray = new JSONArray();
		}

		jsonArray.put(String.valueOf(testrayAttachmentURL));

		buildReportJSONObject.put("testrayAttachmentURLs", jsonArray);
	}

	@Override
	public Map<String, String> getBuildParameters() {
		Map<String, String> buildParameters = new HashMap<>();

		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		if ((buildReportJSONObject == null) ||
			!buildReportJSONObject.has("buildParameters")) {

			return buildParameters;
		}

		JSONObject buildParametersJSONObject =
			buildReportJSONObject.getJSONObject("buildParameters");

		for (String key : buildParametersJSONObject.keySet()) {
			buildParameters.put(key, buildParametersJSONObject.getString(key));
		}

		return buildParameters;
	}

	@Override
	public Job.BuildProfile getBuildProfile() {
		Map<String, String> buildParameters = getBuildParameters();

		String buildProfileString = buildParameters.get(
			"TEST_PORTAL_BUILD_PROFILE");

		Job.BuildProfile buildProfile = Job.BuildProfile.getByString(
			buildProfileString);

		if (buildProfile != null) {
			return buildProfile;
		}

		return Job.BuildProfile.DXP;
	}

	@Override
	public URL getBuildReportJSONTestrayURL() {
		JobReport jobReport = getJobReport();

		JenkinsMaster jenkinsMaster = jobReport.getJenkinsMaster();

		try {
			return new URL(
				JenkinsResultsParserUtil.combine(
					"https://storage.cloud.google.com/testray-results/",
					getStartYearMonth(), "/", jenkinsMaster.getName(), "/",
					jobReport.getJobName(), "/",
					String.valueOf(getBuildNumber()), "/build-report.json.gz"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public URL getBuildReportJSONUserContentURL() {
		JobReport jobReport = getJobReport();

		JenkinsMaster jenkinsMaster = jobReport.getJenkinsMaster();

		try {
			return new URL(
				JenkinsResultsParserUtil.combine(
					"https://", jenkinsMaster.getName(),
					".liferay.com/userContent/jobs/", jobReport.getJobName(),
					"/builds/", String.valueOf(getBuildNumber()),
					"/build-report.json.gz"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public TestrayS3Object getBuildReportTestrayS3Object() {
		JobReport jobReport = getJobReport();

		JenkinsMaster jenkinsMaster = jobReport.getJenkinsMaster();

		TestrayS3Bucket testrayS3Bucket = TestrayS3Bucket.getInstance();

		return testrayS3Bucket.getTestrayS3Object(
			JenkinsResultsParserUtil.combine(
				getStartYearMonth(), "/", jenkinsMaster.getName(), "/",
				jobReport.getJobName(), "/", String.valueOf(getBuildNumber()),
				"/build-report.json.gz"));
	}

	@Override
	public ControllerBuildReport getControllerBuildReport() {
		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		if ((buildReportJSONObject == null) ||
			!buildReportJSONObject.has("controller")) {

			return null;
		}

		JSONObject controllerJSONObject = buildReportJSONObject.getJSONObject(
			"controller");

		if (!controllerJSONObject.has("buildURL")) {
			return null;
		}

		_controllerBuildReport = BuildReportFactory.newControllerBuildReport(
			controllerJSONObject, this);

		return _controllerBuildReport;
	}

	@Override
	public DownstreamBuildReport getDownstreamBuildReport(String axisName) {
		for (DownstreamBuildReport downstreamBuildReport :
				getDownstreamBuildReports()) {

			if (Objects.equals(downstreamBuildReport.getAxisName(), axisName)) {
				return downstreamBuildReport;
			}
		}

		return null;
	}

	@Override
	public List<DownstreamBuildReport> getDownstreamBuildReports() {
		if (_downstreamBuildReports != null) {
			return _downstreamBuildReports;
		}

		_downstreamBuildReports = new ArrayList<>();

		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		if (buildReportJSONObject == null) {
			return _downstreamBuildReports;
		}

		JSONArray batchesJSONArray = buildReportJSONObject.optJSONArray(
			"batches");

		if (batchesJSONArray == null) {
			return _downstreamBuildReports;
		}

		for (int i = 0; i < batchesJSONArray.length(); i++) {
			JSONObject batchJSONObject = batchesJSONArray.optJSONObject(i);

			if (batchJSONObject == null) {
				continue;
			}

			String batchName = batchJSONObject.optString("batchName");
			JSONArray buildsJSONArray = batchJSONObject.optJSONArray("builds");

			if (JenkinsResultsParserUtil.isNullOrEmpty(batchName) ||
				(buildsJSONArray == null)) {

				continue;
			}

			for (int j = 0; j < buildsJSONArray.length(); j++) {
				_downstreamBuildReports.add(
					BuildReportFactory.newDownstreamBuildReport(
						batchName, buildsJSONArray.getJSONObject(j), this));
			}
		}

		_downstreamBuildReports.removeAll(Collections.singleton(null));

		return _downstreamBuildReports;
	}

	@Override
	public URL getJenkinsReportURL() {
		JenkinsMaster jenkinsMaster = getJenkinsMaster();

		try {
			return new URL(
				JenkinsResultsParserUtil.combine(
					"https://", jenkinsMaster.getName(), ".liferay.com/",
					"userContent/jobs/", getJobName(), "/builds/",
					String.valueOf(getBuildNumber()), "/jenkins-report.html"));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(
				"Unable to get Jenkins report URL", malformedURLException);
		}
	}

	@Override
	public String getTestrayBuildDateString() {
		return JenkinsResultsParserUtil.toDateString(
			getStartDate(), "yyyy-MM-dd HH:mm:ss", "America/Los_Angeles");
	}

	@Override
	public URL getTestResultsJSONUserContentURL() {
		JobReport jobReport = getJobReport();

		try {
			return new URL(
				JenkinsResultsParserUtil.combine(
					"https://test-1-0.liferay.com/userContent/testResults/",
					jobReport.getJobName(), "/builds/",
					String.valueOf(getBuildNumber()), "/test.results.json"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public String getTestSuiteName() {
		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		if (buildReportJSONObject == null) {
			return null;
		}

		return buildReportJSONObject.optString("testSuiteName");
	}

	@Override
	public long getTopLevelActiveDuration() {
		long topLevelPassiveBuildDuration = getTopLevelPassiveDuration();

		if (topLevelPassiveBuildDuration == 0L) {
			return 0L;
		}

		return getDuration() - topLevelPassiveBuildDuration;
	}

	@Override
	public long getTopLevelPassiveDuration() {
		StopWatchRecordsGroup stopWatchRecordsGroup =
			getStopWatchRecordsGroup();

		if (stopWatchRecordsGroup == null) {
			return 0L;
		}

		StopWatchRecord waitForInvokedJobsStopWatchRecord =
			stopWatchRecordsGroup.get("wait.for.invoked.jobs");
		StopWatchRecord waitForInvokedSmokeJobsStopWatchRecord =
			stopWatchRecordsGroup.get("wait.for.invoked.smoke.jobs");

		if ((waitForInvokedJobsStopWatchRecord != null) ||
			(waitForInvokedSmokeJobsStopWatchRecord != null)) {

			long topLevelPassiveBuildDuration = 0L;

			if (waitForInvokedJobsStopWatchRecord != null) {
				topLevelPassiveBuildDuration +=
					waitForInvokedJobsStopWatchRecord.getDuration();
			}

			if (waitForInvokedSmokeJobsStopWatchRecord != null) {
				topLevelPassiveBuildDuration +=
					waitForInvokedSmokeJobsStopWatchRecord.getDuration();
			}

			return topLevelPassiveBuildDuration;
		}

		StopWatchRecord invokeDownstreamBuildsStopWatchRecord =
			stopWatchRecordsGroup.get("invoke.downstream.builds");

		if (invokeDownstreamBuildsStopWatchRecord != null) {
			return invokeDownstreamBuildsStopWatchRecord.getDuration();
		}

		return 0L;
	}

	public void setControllerBuildReport(
		ControllerBuildReport controllerBuildReport) {

		_controllerBuildReport = controllerBuildReport;
	}

	@Override
	public long getTotalDuration() {
		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		if (buildReportJSONObject == null) {
			return 0L;
		}

		return buildReportJSONObject.optLong("totalDuration");
	}

	protected BaseTopLevelBuildReport(JSONObject buildReportJSONObject) {
		super(buildReportJSONObject);

		setStartDate(new Date(buildReportJSONObject.getLong("startTime")));
	}

	protected BaseTopLevelBuildReport(
		JSONObject buildJSONObject, JobReport jobReport) {

		super(buildJSONObject, jobReport);

		setStartDate(new Date(buildJSONObject.getLong("timestamp")));
	}

	protected BaseTopLevelBuildReport(TopLevelBuild topLevelBuild) {
		super(topLevelBuild.getBuildURL());

		setStartDate(new Date(topLevelBuild.getStartTime()));
	}

	protected BaseTopLevelBuildReport(URL buildURL) {
		super(buildURL);
	}

	protected String getStartYearMonth() {
		return JenkinsResultsParserUtil.toDateString(
			getStartDate(), "yyyy-MM", "America/Los_Angeles");
	}

	private ControllerBuildReport _controllerBuildReport;
	private List<DownstreamBuildReport> _downstreamBuildReports;

}