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

import com.liferay.jenkins.results.parser.failure.message.generator.CIFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.CompileFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.FailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.GenericFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.GradleTaskFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.IntegrationTestTimeoutFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.LocalGitMirrorFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.ModulesCompilationFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.PMDFailureMessageGenerator;
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

	public String getBatchName() {
		BatchBuild parentBatchBuild = getParentBatchBuild();

		return parentBatchBuild.getBatchName();
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

			Dom4JUtil.getNewElement(
				"strong", listItemElement, testResult.getDisplayName());

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
		catch (IOException ioException) {
			throw new RuntimeException("Unable to generate html", ioException);
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
				jobURL, "/", axisVariable, "/", String.valueOf(buildNumber),
				"/");
		}

		try {
			jobURL = JenkinsResultsParserUtil.decode(jobURL);
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(
				"Unable to decode " + jobURL, unsupportedEncodingException);
		}

		String buildURL = JenkinsResultsParserUtil.combine(
			jobURL, "/", axisVariable, "/", String.valueOf(buildNumber), "/");

		try {
			return JenkinsResultsParserUtil.encode(buildURL);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(
				"Could not encode " + buildURL, malformedURLException);
		}
		catch (URISyntaxException uriSyntaxException) {
			throw new RuntimeException(
				"Could not encode " + buildURL, uriSyntaxException);
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
			getAxisVariable(), " #", String.valueOf(getBuildNumber()));
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
				if (!testResult.isFailing()) {
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

	public BatchBuild getParentBatchBuild() {
		Build parentBuild = getParentBuild();

		if (parentBuild instanceof BatchBuild) {
			return (BatchBuild)parentBuild;
		}

		return null;
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
			catch (IOException ioException) {
				throw new RuntimeException("Unable to get build properties");
			}

			SimpleDateFormat sdf = new SimpleDateFormat(
				buildProperties.getProperty("jenkins.report.date.format"));

			Date date = null;

			try {
				date = sdf.parse(matcher.group("startTime"));
			}
			catch (ParseException parseException) {
				throw new RuntimeException(
					"Unable to get start time", parseException);
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

		if (testReportJSONObject == null) {
			System.out.println(
				"Unable to get test results for: " + getBuildURL());

			return Collections.emptyList();
		}

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
	protected void extractBuildURLComponents(Matcher matcher) {
		super.extractBuildURLComponents(matcher);

		axisVariable = matcher.group("axisVariable");
	}

	@Override
	protected Pattern getArchiveBuildURLPattern() {
		return archiveBuildURLPattern;
	}

	@Override
	protected MultiPattern getBuildURLMultiPattern() {
		return buildURLMultiPattern;
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
	protected String getStopPropertiesTempMapURL() {
		if (fromArchive) {
			return getBuildURL() + "/stop-properties.json";
		}

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		JenkinsMaster topLevelBuildJenkinsMaster =
			topLevelBuild.getJenkinsMaster();

		return JenkinsResultsParserUtil.combine(
			URL_BASE_TEMP_MAP, topLevelBuildJenkinsMaster.getName(), "/",
			topLevelBuild.getJobName(), "/",
			String.valueOf(topLevelBuild.getBuildNumber()), "/", getJobName(),
			"/", getAxisVariable(), "/", getParameterValue("JOB_VARIANT"), "/",
			"stop.properties");
	}

	protected static final Pattern archiveBuildURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"(", Pattern.quote("${dependencies.url}"), "|",
			Pattern.quote(JenkinsResultsParserUtil.URL_DEPENDENCIES_FILE), "|",
			Pattern.quote(JenkinsResultsParserUtil.URL_DEPENDENCIES_HTTP),
			")/*(?<archiveName>.*)/(?<master>[^/]+)/+(?<jobName>[^/]+)/",
			"(?<axisVariable>AXIS_VARIABLE=[^,]+,[^/]+)/",
			"(?<buildNumber>\\d+)/?"));
	protected static final MultiPattern buildURLMultiPattern = new MultiPattern(
		JenkinsResultsParserUtil.combine(
			"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+)/",
			"(?<buildNumber>\\d+)/",
			"(?<axisVariable>AXIS_VARIABLE=[^,]+,[^/]+)/?"),
		JenkinsResultsParserUtil.combine(
			"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+)/",
			"(?<axisVariable>AXIS_VARIABLE=[^,]+,[^/]+)/",
			"(?<buildNumber>\\d+)/?"));
	protected static final String defaultLogBaseURL =
		"https://testray.liferay.com/reports/production/logs";

	protected String axisVariable;

	// Skip JavaParser

	private static final FailureMessageGenerator[] _FAILURE_MESSAGE_GENERATORS =
		{
			new ModulesCompilationFailureMessageGenerator(),
			//
			new CompileFailureMessageGenerator(),
			new IntegrationTestTimeoutFailureMessageGenerator(),
			new LocalGitMirrorFailureMessageGenerator(),
			new PMDFailureMessageGenerator(),
			new PluginFailureMessageGenerator(),
			new PluginGitIDFailureMessageGenerator(),
			new SemanticVersioningFailureMessageGenerator(),
			new SourceFormatFailureMessageGenerator(),
			new StartupFailureMessageGenerator(),
			//
			new GradleTaskFailureMessageGenerator(),
			//
			new CIFailureMessageGenerator(),
			new GenericFailureMessageGenerator()
		};

	private static final Pattern _axisStartTimestampPattern = Pattern.compile(
		"\\s*\\[echo\\] startTime: (?<startTime>[^\\n]+)");
	private static final Pattern _axisVariablePattern = Pattern.compile(
		"AXIS_VARIABLE=(?<axisNumber>[^,]+),.*");

}