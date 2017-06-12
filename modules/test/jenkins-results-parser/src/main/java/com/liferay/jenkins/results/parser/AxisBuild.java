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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

		sb.append(getMaster());
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
			String displayName = testResult.getDisplayName();

			if (displayName.contains("JenkinsLogAsserterTest")) {
				continue;
			}

			String testrayLogsURL = getTestrayLogsURL();

			Element listItemElement = Dom4JUtil.getNewElement(
				"li", unorderedListElement);

			Dom4JUtil.getNewElement("strong", listItemElement, displayName);

			Element reportLinksUnorderedListElement = Dom4JUtil.getNewElement(
				"ul", listItemElement);

			Element poshiReportListItemElement = Dom4JUtil.getNewElement(
				"li", reportLinksUnorderedListElement);

			Dom4JUtil.getNewAnchorElement(
				testResult.getPoshiReportURL(testrayLogsURL),
				poshiReportListItemElement, "Poshi Report");

			Element poshiSummaryListItemElement = Dom4JUtil.getNewElement(
				"li", reportLinksUnorderedListElement);

			Dom4JUtil.getNewAnchorElement(
				testResult.getPoshiSummaryURL(testrayLogsURL),
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
		StringBuffer sb = new StringBuffer();

		sb.append("http[s]*:\\/\\/");
		sb.append(JenkinsResultsParserUtil.getRegexLiteral(getMaster()));
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
				getBuildURL(), null, getDisplayName()));

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
			List<Element> elements = new ArrayList<>();

			for (TestResult testResult : getTestResults(null)) {
				String testStatus = testResult.getStatus();

				if (testStatus.equals("PASSED") ||
					testStatus.equals("SKIPPED")) {

					continue;
				}

				elements.add(testResult.getGitHubElement(getTestrayLogsURL()));
			}

			Dom4JUtil.getOrderedListElement(elements, messageElement, 3);
		}

		return messageElement;
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

	public String getTestrayLogsURL() {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties", ioe);
		}

		String logBaseURL = null;

		if (buildProperties.containsKey("log.base.url")) {
			logBaseURL = buildProperties.getProperty("log.base.url");
		}

		if (logBaseURL == null) {
			logBaseURL = defaultLogBaseURL;
		}

		Map<String, String> startPropertiesTempMap =
			getStartPropertiesTempMap();

		return JenkinsResultsParserUtil.combine(
			logBaseURL, "/",
			startPropertiesTempMap.get("TOP_LEVEL_MASTER_HOSTNAME"), "/",
			startPropertiesTempMap.get("TOP_LEVEL_START_TIME"), "/",
			startPropertiesTempMap.get("TOP_LEVEL_JOB_NAME"), "/",
			startPropertiesTempMap.get("TOP_LEVEL_BUILD_NUMBER"), "/",
			getParameterValue("JOB_VARIANT"), "/", getAxisNumber());
	}

	@Override
	public List<TestResult> getTestResults(String testStatus) {
		String status = getStatus();

		if (!status.equals("completed")) {
			return Collections.emptyList();
		}

		JSONObject testReportJSONObject = getTestReportJSONObject();

		return TestResult.getTestResults(
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
	protected String getStopPropertiesTempMapURL() {
		if (fromArchive) {
			return getBuildURL() + "/stop-properties.json";
		}

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		return JenkinsResultsParserUtil.combine(
			"http://cloud-10-0-0-31.lax.liferay.com/osb-jenkins-web/map/",
			topLevelBuild.getMaster(), "/", topLevelBuild.getJobName(), "/",
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
		master = matcher.group("master");

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

	private static final Pattern _axisVariablePattern = Pattern.compile(
		"AXIS_VARIABLE=(?<axisNumber>[^,]+),.*");

}