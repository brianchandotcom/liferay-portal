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

import com.liferay.jenkins.results.parser.failure.message.generator.CompileFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.FailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.GenericFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.IntegrationTestTimeoutFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.LocalGitMirrorFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.ModulesCompilationFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.PluginFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.PluginGitIDFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.SemanticVersioningFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.SourceFormatFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.StartupFailureMessageGenerator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class AxisBuild extends BaseBuild {

	@Override
	public void addTimelineData(BaseBuild.TimelineData timelineData) {
		timelineData.addTimelineData(this);
	}

	@Override
	public void findDownstreamBuilds() {
	}

	@Override
	public String getAppServer() {
		Build parentBuild = getParentBuild();

		return parentBuild.getAppServer();
	}

	@Override
	public String getArchivePath() {
		if (archiveName == null) {
			System.out.println(
				"Build URL " + getBuildURL() + " has a null archive name");
		}

		StringBuilder sb = new StringBuilder(archiveName);

		if (!archiveName.endsWith("/")) {
			sb.append("/");
		}

		JenkinsMaster jenkinsMaster = getJenkinsMaster();

		sb.append(jenkinsMaster.getName());

		sb.append("/");
		sb.append(getJobName());
		sb.append("/");
		sb.append(getAxisVariable());
		sb.append("/");
		sb.append(getBuildNumber());

		return sb.toString();
	}

	public String getAxisNumber() {
		Matcher matcher = _axisVariablePattern.matcher(getAxisVariable());

		if (matcher.find()) {
			return matcher.group("axisNumber");
		}

		throw new RuntimeException(
			"Invalid axis variable: " + getAxisVariable());
	}

	public String getAxisVariable() {
		return axisVariable;
	}

	@Override
	public String getBrowser() {
		Build parentBuild = getParentBuild();

		return parentBuild.getBrowser();
	}

	public String getBuildDescriptionTestrayReports() {
		Element unorderedListElement = Dom4JUtil.getNewElement("ul");

		for (TestResult testResult : getTestResults(null)) {
			if (!(testResult instanceof PoshiTestResult)) {
				continue;
			}

			Element listItemElement = Dom4JUtil.getNewElement(
				"li", unorderedListElement);

			String displayName = testResult.getDisplayName();

			Dom4JUtil.getNewElement("strong", listItemElement, displayName);

			Element reportLinksUnorderedListElement = Dom4JUtil.getNewElement(
				"ul", listItemElement);

			Element poshiReportListItemElement = Dom4JUtil.getNewElement(
				"li", reportLinksUnorderedListElement);

			PoshiTestResult poshiTestResult = (PoshiTestResult)testResult;

			Dom4JUtil.getNewAnchorElement(
				poshiTestResult.getPoshiReportURL(), poshiReportListItemElement,
				"Poshi Report");

			Element poshiSummaryListItemElement = Dom4JUtil.getNewElement(
				"li", reportLinksUnorderedListElement);

			Dom4JUtil.getNewAnchorElement(
				poshiTestResult.getPoshiSummaryURL(),
				poshiSummaryListItemElement, "Poshi Summary");
		}

		Dom4JUtil.addToElement(
			unorderedListElement, Dom4JUtil.getNewElement("br"));

		try {
			return Dom4JUtil.format(unorderedListElement, false);
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to generate html", ioe);
		}
	}

	@Override
	public String getBuildURL() {
		String jobURL = getJobURL();
		int buildNumber = getBuildNumber();

		if ((jobURL == null) || (buildNumber == -1)) {
			return null;
		}

		if (fromArchive) {
			return JenkinsResultsParserUtil.combine(
				jobURL, "/", axisVariable, "/", Integer.toString(buildNumber),
				"/");
		}

		try {
			jobURL = JenkinsResultsParserUtil.decode(jobURL);
		}
		catch (UnsupportedEncodingException uee) {
			throw new RuntimeException("Unable to decode " + jobURL, uee);
		}

		String buildURL = JenkinsResultsParserUtil.combine(
			jobURL, "/", axisVariable, "/", Integer.toString(buildNumber), "/");

		try {
			return JenkinsResultsParserUtil.encode(buildURL);
		}
		catch (MalformedURLException murle) {
			throw new RuntimeException("Could not encode " + buildURL, murle);
		}
		catch (URISyntaxException urise) {
			throw new RuntimeException("Could not encode " + buildURL, urise);
		}
	}

	@Override
	public String getBuildURLRegex() {
		JenkinsMaster jenkinsMaster = getJenkinsMaster();

		StringBuffer sb = new StringBuffer();

		sb.append("http[s]*:\\/\\/");
		sb.append(
			JenkinsResultsParserUtil.getRegexLiteral(jenkinsMaster.getName()));
		sb.append("[^\\/]*");
		sb.append("[\\/]+job[\\/]+");

		String jobNameRegexLiteral = JenkinsResultsParserUtil.getRegexLiteral(
			getJobName());

		jobNameRegexLiteral = jobNameRegexLiteral.replace("\\(", "(\\(|%28)");
		jobNameRegexLiteral = jobNameRegexLiteral.replace("\\)", "(\\)|%29)");

		sb.append(jobNameRegexLiteral);

		sb.append("[\\/]+");
		sb.append(JenkinsResultsParserUtil.getRegexLiteral(getAxisVariable()));
		sb.append("[\\/]+");
		sb.append(getBuildNumber());
		sb.append("[\\/]*");

		return sb.toString();
	}

	@Override
	public String getDatabase() {
		Build parentBuild = getParentBuild();

		return parentBuild.getDatabase();
	}

	@Override
	public String getDisplayName() {
		return JenkinsResultsParserUtil.combine(
			getAxisVariable(), " #", Integer.toString(getBuildNumber()));
	}

	@Override
	public Element getGitHubMessageElement() {
		String status = getStatus();

		if (!status.equals("completed") && (getParentBuild() != null)) {
			return null;
		}

		String result = getResult();

		if (result.equals("SUCCESS")) {
			return null;
		}

		Element messageElement = Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewAnchorElement(
				getBuildURL() + "/consoleText", null, getDisplayName()));

		if (result.equals("ABORTED")) {
			messageElement.add(
				Dom4JUtil.toCodeSnippetElement("Build was aborted"));
		}

		if (result.equals("FAILURE")) {
			Element failureMessageElement = getFailureMessageElement();

			if (failureMessageElement != null) {
				messageElement.add(failureMessageElement);
			}
		}

		if (result.equals("UNSTABLE")) {
			List<Element> failureElements = new ArrayList<>();
			List<Element> upstreamJobFailureElements = new ArrayList<>();

			for (TestResult testResult : getTestResults(null)) {
				String testStatus = testResult.getStatus();

				if (testStatus.equals("FIXED") || testStatus.equals("PASSED") ||
					testStatus.equals("SKIPPED")) {

					continue;
				}

				if (UpstreamFailureUtil.isTestFailingInUpstreamJob(
						testResult)) {

					upstreamJobFailureElements.add(
						testResult.getGitHubElement());

					continue;
				}

				failureElements.add(testResult.getGitHubElement());
			}

			if (!upstreamJobFailureElements.isEmpty()) {
				upstreamJobFailureMessageElement = messageElement.createCopy();

				Dom4JUtil.getOrderedListElement(
					upstreamJobFailureElements,
					upstreamJobFailureMessageElement, 3);
			}

			Dom4JUtil.getOrderedListElement(failureElements, messageElement, 3);

			if (failureElements.isEmpty()) {
				return null;
			}
		}

		return messageElement;
	}

	@Override
	public Long getInvokedTime() {
		if (invokedTime != null) {
			return invokedTime;
		}

		Build parentBuild = getParentBuild();

		invokedTime = parentBuild.getStartTime();

		return invokedTime;
	}

	@Override
	public String getJDK() {
		Build parentBuild = getParentBuild();

		return parentBuild.getJDK();
	}

	@Override
	public String getOperatingSystem() {
		Build parentBuild = getParentBuild();

		return parentBuild.getOperatingSystem();
	}

	@Override
	public Long getStartTime() {
		if (startTime != null) {
			return startTime;
		}

		String consoleText = getConsoleText();

		for (String line : consoleText.split("\n")) {
			Matcher matcher = _axisStartTimestampPattern.matcher(line);

			if (!matcher.find()) {
				continue;
			}

			Properties buildProperties = null;

			try {
				buildProperties = JenkinsResultsParserUtil.getBuildProperties();
			}
			catch (IOException ioe) {
				throw new RuntimeException("Unable to get build properties");
			}

			SimpleDateFormat sdf = new SimpleDateFormat(
				buildProperties.getProperty("jenkins.report.date.format"));

			Date date = null;

			try {
				date = sdf.parse(matcher.group("startTime"));
			}
			catch (ParseException pe) {
				throw new RuntimeException("Unable to get start time", pe);
			}

			startTime = date.getTime();

			break;
		}

		return startTime;
	}

	@Override
	public List<TestResult> getTestResults(String testStatus) {
		String status = getStatus();

		if (!status.equals("completed")) {
			return Collections.emptyList();
		}

		JSONObject testReportJSONObject = getTestReportJSONObject();

		return getTestResults(
			this, testReportJSONObject.getJSONArray("suites"), testStatus);
	}

	@Override
	public void reinvoke() {
		throw new RuntimeException("Axis builds cannot be reinvoked");
	}

	protected AxisBuild(String url) {
		this(url, null);
	}

	protected AxisBuild(String url, BatchBuild parentBuild) {
		super(JenkinsResultsParserUtil.getLocalURL(url), parentBuild);
	}

	@Override
	protected void checkForReinvocation(String consoleText) {
	}

	@Override
	protected FailureMessageGenerator[] getFailureMessageGenerators() {
		return _FAILURE_MESSAGE_GENERATORS;
	}

	@Override
	protected Element getGitHubMessageJobResultsElement() {
		return null;
	}

	@Override
	protected List<Element> getJenkinsReportTableRowElements(
		String result, String status) {

		return Collections.emptyList();
	}

	@Override
	protected String getStopPropertiesTempMapURL() {
		if (fromArchive) {
			return getBuildURL() + "/stop-properties.json";
		}

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		JenkinsMaster topLevelBuildJenkinsMaster =
			topLevelBuild.getJenkinsMaster();

		return JenkinsResultsParserUtil.combine(
			"http://cloud-10-0-0-31.lax.liferay.com/osb-jenkins-web/map/",
			topLevelBuildJenkinsMaster.getName(), "/",
			topLevelBuild.getJobName(), "/",
			Integer.toString(topLevelBuild.getBuildNumber()), "/", getJobName(),
			"/", getAxisVariable(), "/", getParameterValue("JOB_VARIANT"), "/",
			"stop.properties");
	}

	@Override
	protected void setBuildURL(String buildURL) {
		try {
			buildURL = JenkinsResultsParserUtil.decode(buildURL);
		}
		catch (UnsupportedEncodingException uee) {
			throw new IllegalArgumentException(
				"Unable to decode " + buildURL, uee);
		}

		try {
			String archiveMarkerContent = JenkinsResultsParserUtil.toString(
				buildURL + "/archive-marker", false, 0, 0, 0);

			if ((archiveMarkerContent != null) &&
				!archiveMarkerContent.isEmpty()) {

				fromArchive = true;
			}
			else {
				fromArchive = false;
			}
		}
		catch (IOException ioe) {
			fromArchive = false;
		}

		Matcher matcher = buildURLPattern.matcher(buildURL);

		if (!matcher.find()) {
			matcher = archiveBuildURLPattern.matcher(buildURL);

			if (!matcher.find()) {
				throw new IllegalArgumentException(
					"Invalid build URL " + buildURL);
			}

			archiveName = matcher.group("archiveName");
		}

		axisVariable = matcher.group("axisVariable");
		jobName = matcher.group("jobName");
		setJenkinsMaster(new JenkinsMaster(matcher.group("master")));

		setBuildNumber(Integer.parseInt(matcher.group("buildNumber")));

		loadParametersFromBuildJSONObject();

		setStatus("running");
	}

	protected static final Pattern archiveBuildURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"(", Pattern.quote("${dependencies.url}"), "|",
			Pattern.quote(JenkinsResultsParserUtil.DEPENDENCIES_URL_FILE), "|",
			Pattern.quote(JenkinsResultsParserUtil.DEPENDENCIES_URL_HTTP),
			")/*(?<archiveName>.*)/(?<master>[^/]+)/+(?<jobName>[^/]+)/",
			"(?<axisVariable>AXIS_VARIABLE=[^,]+,[^/]+)/",
			"(?<buildNumber>\\d+)/?"));
	protected static final Pattern buildURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+)/",
			"(?<axisVariable>AXIS_VARIABLE=[^,]+,[^/]+)/",
			"(?<buildNumber>\\d+)/?"));
	protected static final String defaultLogBaseURL =
		"https://testray.liferay.com/reports/production/logs";

	protected String axisVariable;

	private static final FailureMessageGenerator[] _FAILURE_MESSAGE_GENERATORS =
		{
			new ModulesCompilationFailureMessageGenerator(),

			new CompileFailureMessageGenerator(),
			new IntegrationTestTimeoutFailureMessageGenerator(),
			new LocalGitMirrorFailureMessageGenerator(),
			new PluginFailureMessageGenerator(),
			new PluginGitIDFailureMessageGenerator(),
			new SemanticVersioningFailureMessageGenerator(),
			new SourceFormatFailureMessageGenerator(),
			new StartupFailureMessageGenerator(),

			new GenericFailureMessageGenerator()
		};

	private static final Pattern _axisStartTimestampPattern = Pattern.compile(
		"\\s*\\[echo\\] startTime: (?<startTime>[^\\n]+)");
	private static final Pattern _axisVariablePattern = Pattern.compile(
		"AXIS_VARIABLE=(?<axisNumber>[^,]+),.*");

}