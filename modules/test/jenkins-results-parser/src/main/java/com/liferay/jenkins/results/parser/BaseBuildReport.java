/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildReport implements BuildReport {

	@Override
	public int getBuildNumber() {
		Matcher matcher = _buildURLPattern.matcher(
			String.valueOf(getBuildURL()));

		if (!matcher.find()) {
			throw new RuntimeException("Invalid Build URL: " + getBuildURL());
		}

		return Integer.parseInt(matcher.group("buildNumber"));
	}

	@Override
	public JSONObject getBuildReportJSONObject() {
		if ((buildReportJSONObject != null) &&
			!JenkinsResultsParserUtil.isNullOrEmpty(
				buildReportJSONObject.optString("result"))) {

			return buildReportJSONObject;
		}

		JSONObject buildJSONObject = getBuildJSONObject();

		buildReportJSONObject = new JSONObject();

		buildReportJSONObject.put(
			"duration", buildJSONObject.get("duration")
		).put(
			"result", buildJSONObject.get("result")
		).put(
			"startTime", buildJSONObject.get("timestamp")
		);

		return buildReportJSONObject;
	}

	@Override
	public URL getBuildURL() {
		return _buildURL;
	}

	@Override
	public long getDuration() {
		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		return buildReportJSONObject.getLong("duration");
	}

	@Override
	public String getFailureMessage() {
		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		return buildReportJSONObject.optString("failureMessage");
	}

	@Override
	public JenkinsMaster getJenkinsMaster() {
		if (_jenkinsMaster != null) {
			return _jenkinsMaster;
		}

		_jenkinsMaster = JenkinsResultsParserUtil.getJenkinsMaster(
			getBuildURL());

		return _jenkinsMaster;
	}

	@Override
	public String getJobName() {
		Matcher matcher = _buildURLPattern.matcher(
			String.valueOf(getBuildURL()));

		if (!matcher.find()) {
			throw new RuntimeException("Invalid Build URL: " + getBuildURL());
		}

		return matcher.group("jobName");
	}

	@Override
	public JobReport getJobReport() {
		if (_jobReport != null) {
			return _jobReport;
		}

		Matcher matcher = _buildURLPattern.matcher(
			String.valueOf(getBuildURL()));

		if (!matcher.find()) {
			throw new RuntimeException("Invalid Build URL: " + getBuildURL());
		}

		try {
			_jobReport = JobReport.getInstance(
				new URL(matcher.group("jobURL")));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}

		return _jobReport;
	}

	@Override
	public String getResult() {
		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		return buildReportJSONObject.getString("result");
	}

	@Override
	public Date getStartDate() {
		if (_startDate != null) {
			return _startDate;
		}

		JSONObject buildJSONObject = getBuildJSONObject();

		_startDate = new Date(buildJSONObject.getLong("timestamp"));

		return _startDate;
	}

	@Override
	public StopWatchRecordsGroup getStopWatchRecordsGroup() {
		return new StopWatchRecordsGroup(getBuildReportJSONObject());
	}

	@Override
	public List<URL> getTestrayAttachmentURLs() {
		List<URL> testrayAttachmentURLs = new ArrayList<>();

		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		JSONArray testrayAttachmentURLsJSONArray =
			buildReportJSONObject.optJSONArray("testrayAttachmentURLs");

		if (testrayAttachmentURLsJSONArray == null) {
			return testrayAttachmentURLs;
		}

		for (int i = 0; i < testrayAttachmentURLsJSONArray.length(); i++) {
			try {
				testrayAttachmentURLs.add(
					new URL(testrayAttachmentURLsJSONArray.getString(i)));
			}
			catch (MalformedURLException malformedURLException) {
				throw new RuntimeException(malformedURLException);
			}
		}

		return testrayAttachmentURLs;
	}

	@Override
	public boolean isFailing() {
		String result = getResult();

		if (result.equals("FAILURE") || result.equals("REGRESSION") ||
			result.equals("UNSTABLE")) {

			return true;
		}

		return false;
	}

	protected BaseBuildReport(JSONObject buildReportJSONObject) {
		this.buildReportJSONObject = buildReportJSONObject;

		String buildURLString = buildReportJSONObject.getString("buildURL");

		Matcher matcher = _buildURLPattern.matcher(buildURLString);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid Build URL: " + buildURLString);
		}

		try {
			_buildURL = new URL(
				JenkinsResultsParserUtil.combine(
					"https://", matcher.group("masterHostname"),
					".liferay.com/job/", matcher.group("jobName"), "/",
					matcher.group("buildNumber")));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	protected BaseBuildReport(JSONObject buildJSONObject, JobReport jobReport) {
		_buildJSONObject = buildJSONObject;
		_jobReport = jobReport;

		String buildURLString = buildJSONObject.getString("url");

		Matcher matcher = _buildURLPattern.matcher(buildURLString);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid Build URL: " + buildURLString);
		}

		try {
			_buildURL = new URL(
				JenkinsResultsParserUtil.combine(
					"https://", matcher.group("masterHostname"),
					".liferay.com/job/", matcher.group("jobName"), "/",
					matcher.group("buildNumber")));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	protected BaseBuildReport(String buildURLString) {
		Matcher matcher = _buildURLPattern.matcher(buildURLString);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid Build URL: " + buildURLString);
		}

		try {
			_buildURL = new URL(
				JenkinsResultsParserUtil.combine(
					"https://", matcher.group("masterHostname"),
					".liferay.com/job/", matcher.group("jobName"), "/",
					matcher.group("buildNumber")));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	protected BaseBuildReport(URL buildURL) {
		this(String.valueOf(buildURL));
	}

	protected JSONObject getBuildJSONObject() {
		if (_buildJSONObject != null) {
			return _buildJSONObject;
		}

		try {
			_buildJSONObject = JenkinsResultsParserUtil.toJSONObject(
				getBuildURL() + "/api/json");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return _buildJSONObject;
	}

	protected JSONObject getDownstreamBuildJSONObject(Build build) {
		JSONObject downstreamBuildJSONObject = new JSONObject();

		if (build instanceof AxisBuild) {
			AxisBuild axisBuild = (AxisBuild)build;

			downstreamBuildJSONObject.put("axisName", axisBuild.getAxisName());
		}
		else if (build instanceof DownstreamBuild) {
			DownstreamBuild downstreamBuild = (DownstreamBuild)build;

			downstreamBuildJSONObject.put(
				"axisName", downstreamBuild.getAxisName());
		}

		downstreamBuildJSONObject.put(
			"buildURL", build.getBuildURL()
		).put(
			"duration", build.getDuration()
		);

		JSONObject testReportJSONObject = build.getTestReportJSONObject(false);

		if (testReportJSONObject != null) {
			downstreamBuildJSONObject.put(
				"failCount", testReportJSONObject.optInt("failCount")
			).put(
				"passCount", testReportJSONObject.optInt("passCount")
			).put(
				"skipCount", testReportJSONObject.optInt("skipCount")
			);
		}

		if (build.isFailing()) {
			downstreamBuildJSONObject.put(
				"failureMessage", build.getFailureMessage());
		}

		downstreamBuildJSONObject.put(
			"result", build.getResult()
		).put(
			"startTime", build.getStartTime()
		);

		StopWatchRecordsGroup stopWatchRecordsGroup =
			build.getStopWatchRecordsGroup();

		if (stopWatchRecordsGroup != null) {
			downstreamBuildJSONObject.put(
				"stopWatchRecords", stopWatchRecordsGroup.getJSONArray());
		}

		downstreamBuildJSONObject.put(
			"testrayAttachmentURLs", build.getTestrayAttachmentURLs());

		JSONArray testResultsJSONArray = new JSONArray();

		for (TestResult testResult : build.getTestResults(null)) {
			testResultsJSONArray.put(_getTestResultJSONObject(testResult));
		}

		downstreamBuildJSONObject.put("testResults", testResultsJSONArray);

		return downstreamBuildJSONObject;
	}

	protected void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	protected JSONObject buildReportJSONObject;

	private JSONObject _getTestResultJSONObject(TestResult testResult) {
		JSONObject testResultJSONObject = new JSONObject();

		testResultJSONObject.put("duration", testResult.getDuration());

		String errorDetails = testResult.getErrorDetails();

		if (errorDetails != null) {
			if (errorDetails.contains("\n")) {
				int index = errorDetails.indexOf("\n");

				errorDetails = errorDetails.substring(0, index);
			}

			if (errorDetails.length() > 200) {
				errorDetails = errorDetails.substring(0, 200);
			}

			testResultJSONObject.put("errorDetails", errorDetails);
		}

		if (testResult.isFailing()) {
			testResultJSONObject.put(
				"errorStackTrace", testResult.getErrorStackTrace());
		}

		testResultJSONObject.put(
			"name", testResult.getDisplayName()
		).put(
			"status", testResult.getStatus()
		).put(
			"testTaskName", _getTestTaskName(testResult)
		);

		return testResultJSONObject;
	}

	private String _getTestTaskName(TestResult testResult) {
		if (!(testResult instanceof JUnitTestResult)) {
			return null;
		}

		TestClassResult testClassResult = testResult.getTestClassResult();

		if (testClassResult == null) {
			return null;
		}

		TestClass testClass = testClassResult.getTestClass();

		if (testClass == null) {
			return null;
		}

		Matcher matcher = _testClassFilePathPattern.matcher(
			String.valueOf(testClass.getTestClassFile()));

		if (!matcher.find()) {
			return null;
		}

		String relativePath = matcher.group("relativePath");

		return JenkinsResultsParserUtil.combine(
			relativePath.replaceAll("\\/", ":"), ":", matcher.group("type"));
	}

	private static final Pattern _buildURLPattern = Pattern.compile(
		"(?<jobURL>https?://(?<masterHostname>test-\\d+-\\d+)" +
			"(\\.liferay\\.com)?/job/(?<jobName>[^/]+))" +
				"(/AXIS_VARIABLE=(?<axisVariable>\\d+))?/(?<buildNumber>\\d+)");
	private static final Pattern _testClassFilePathPattern = Pattern.compile(
		".+/modules(?<relativePath>/.+)/src/(?<type>test|testIntegration)/.*");

	private JSONObject _buildJSONObject;
	private final URL _buildURL;
	private JenkinsMaster _jenkinsMaster;
	private JobReport _jobReport;
	private Date _startDate;

}