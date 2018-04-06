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

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.dom4j.Element;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kevin Yen
 */
public class BatchBuild extends BaseBuild {

	@Override
	public void addTimelineData(BaseBuild.TimelineData timelineData) {
		addDownstreamBuildsTimelineData(timelineData);
	}

	@Override
	public String getAppServer() {
		return getEnvironment("app.server");
	}

	@Override
	public String getBrowser() {
		return getEnvironment("browser");
	}

	@Override
	public String getDatabase() {
		return getEnvironment("database");
	}

	@Override
	public Element getGitHubMessageElement() {
		Collections.sort(
			downstreamBuilds, new BaseBuild.BuildDisplayNameComparator());

		Element messageElement = super.getGitHubMessageElement();

		if (messageElement == null) {
			return messageElement;
		}

		String result = getResult();

		if (result.equals("ABORTED") && (getDownstreamBuildCount(null) == 0)) {
			return messageElement;
		}

		Map<Build, Element> downstreamBuildFailureMessages =
			getDownstreamBuildMessages("ABORTED", "FAILURE", "UNSTABLE");

		List<Element> failureElements = new ArrayList<>();
		List<Element> upstreamJobFailureElements = new ArrayList<>();

		for (Map.Entry<Build, Element> entry :
				downstreamBuildFailureMessages.entrySet()) {

			Build failedDownstreamBuild = entry.getKey();

			Element upstreamJobFailureElement =
				failedDownstreamBuild.
					getGitHubMessageUpstreamJobFailureElement();

			if (upstreamJobFailureElement != null) {
				upstreamJobFailureElements.add(upstreamJobFailureElement);
			}

			Element failureElement = entry.getValue();

			if (failureElement == null) {
				continue;
			}

			if (isHighPriorityBuildFailureElement(failureElement)) {
				failureElements.add(0, failureElement);

				continue;
			}

			failureElements.add(failureElement);
		}

		if (!upstreamJobFailureElements.isEmpty()) {
			upstreamJobFailureMessageElement = getGitHubMessageElement(true);

			Dom4JUtil.getOrderedListElement(
				upstreamJobFailureElements, upstreamJobFailureMessageElement,
				4);
		}

		Dom4JUtil.getOrderedListElement(failureElements, messageElement, 4);

		if (failureElements.size() >= 4) {
			Dom4JUtil.getNewElement(
				"strong", messageElement, "Click ",
				Dom4JUtil.getNewAnchorElement(
					getBuildURL() + "testReport", "here"),
				" for more failures.");
		}

		if (failureElements.isEmpty()) {
			return null;
		}

		return messageElement;
	}

	@Override
	public Long getInvokedTime() {
		if (invokedTime != null) {
			return invokedTime;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("\\s*\\[echo\\]\\s*");

		sb.append(Pattern.quote(getJobName()));

		String jobVariant = getJobVariant();

		if ((jobVariant != null) && !jobVariant.isEmpty()) {
			sb.append("/");

			sb.append(Pattern.quote(jobVariant));
		}

		sb.append("\\s*invoked time: (?<invokedTime>[^\\n]*)");

		Pattern pattern = Pattern.compile(sb.toString());

		Build parentBuild = getParentBuild();

		String parentConsoleText = parentBuild.getConsoleText();

		for (String line : parentConsoleText.split("\n")) {
			Matcher matcher = pattern.matcher(line);

			if (!matcher.find()) {
				continue;
			}

			Properties buildProperties = null;

			try {
				buildProperties = JenkinsResultsParserUtil.getBuildProperties();
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to get build properties", ioe);
			}

			SimpleDateFormat sdf = new SimpleDateFormat(
				buildProperties.getProperty("jenkins.report.date.format"));

			Date date = null;

			try {
				date = sdf.parse(matcher.group("invokedTime"));
			}
			catch (ParseException pe) {
				throw new RuntimeException("Unable to get invoked time", pe);
			}

			invokedTime = date.getTime();

			return invokedTime;
		}

		return getStartTime();
	}

	@Override
	public String getJDK() {
		return getEnvironment("java.jdk");
	}

	@Override
	public String getOperatingSystem() {
		return getEnvironment("operating.system");
	}

	@Override
	public List<TestResult> getTestResults(String testStatus) {
		String status = getStatus();

		if (!status.equals("completed")) {
			return Collections.emptyList();
		}

		List<TestResult> testResults = new ArrayList<>();

		JSONObject testReportJSONObject = getTestReportJSONObject();

		JSONArray childReportsJSONArray = testReportJSONObject.optJSONArray(
			"childReports");

		if (childReportsJSONArray == null) {
			return Collections.emptyList();
		}

		for (int i = 0; i < childReportsJSONArray.length(); i++) {
			JSONObject childReportJSONObject =
				childReportsJSONArray.optJSONObject(i);

			if (childReportJSONObject == null) {
				continue;
			}

			JSONObject childJSONObject = childReportJSONObject.optJSONObject(
				"child");

			if (childJSONObject == null) {
				continue;
			}

			String axisBuildURL = childJSONObject.optString("url");

			if (axisBuildURL == null) {
				continue;
			}

			Matcher axisBuildURLMatcher = null;

			if (fromArchive) {
				axisBuildURLMatcher = AxisBuild.archiveBuildURLPattern.matcher(
					axisBuildURL);
			}
			else {
				axisBuildURLMatcher = AxisBuild.buildURLPattern.matcher(
					axisBuildURL);
			}

			axisBuildURLMatcher.find();

			String axisVariable = axisBuildURLMatcher.group("axisVariable");

			JSONObject resultJSONObject = childReportJSONObject.optJSONObject(
				"result");

			if (resultJSONObject == null) {
				continue;
			}

			JSONArray suitesJSONArray = resultJSONObject.getJSONArray("suites");

			if (suitesJSONArray == null) {
				continue;
			}

			AxisBuild axisBuild = getAxisBuild(axisVariable);

			if (axisBuild == null) {
				continue;
			}

			testResults.addAll(
				getTestResults(axisBuild, suitesJSONArray, testStatus));
		}

		return testResults;
	}

	@Override
	public long getTotalDuration() {
		long totalDuration = super.getTotalDuration();

		return totalDuration - getDuration();
	}

	@Override
	public int getTotalSlavesUsedCount() {
		return super.getTotalSlavesUsedCount() - 1;
	}

	@Override
	public void update() {
		super.update();

		String status = getStatus();

		if (badBuildNumbers.size() >= MAX_REINVOCATIONS) {
			return;
		}

		String result = getResult();

		if ((status.equals("completed") && result.equals("SUCCESS")) ||
			fromArchive) {

			return;
		}

		boolean reinvoked = false;

		for (Build downstreamBuild : getDownstreamBuilds("completed")) {
			if (reinvoked) {
				break;
			}

			for (ReinvokeRule reinvokeRule : reinvokeRules) {
				String downstreamBuildResult = downstreamBuild.getResult();

				if ((downstreamBuildResult == null) ||
					downstreamBuildResult.equals("SUCCESS")) {

					continue;
				}

				if (!reinvokeRule.matches(downstreamBuild)) {
					continue;
				}

				reinvoke(reinvokeRule);

				reinvoked = true;

				break;
			}
		}
	}

	protected BatchBuild(String url) {
		this(url, null);
	}

	protected BatchBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	protected AxisBuild getAxisBuild(String axisVariable) {
		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			AxisBuild downstreamAxisBuild = (AxisBuild)downstreamBuild;

			if (axisVariable.equals(downstreamAxisBuild.getAxisVariable())) {
				return downstreamAxisBuild;
			}
		}

		return null;
	}

	protected String getBatchComponent(
		String batchName, String environmentOption) {

		int x = batchName.indexOf(environmentOption);

		int y = batchName.indexOf("-", x);

		if (y == -1) {
			y = batchName.length();
		}

		return batchName.substring(x, y);
	}

	protected String getEnvironment(String environmentType) {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties", ioe);
		}

		List<String> environmentOptions = new ArrayList<>(
			Arrays.asList(
				StringUtils.split(
					buildProperties.getProperty(environmentType + ".types"),
					",")));

		String batchName = getJobVariant();

		for (String environmentOption : environmentOptions) {
			if (batchName.contains(environmentOption)) {
				String batchComponent = getBatchComponent(
					batchName, environmentOption);

				return buildProperties.getProperty(
					"env.option." + environmentType + "." + batchComponent);
			}
		}

		String name = buildProperties.getProperty(environmentType + ".type");

		String environmentVersion = (String)buildProperties.get(
			environmentType + "." + name + ".version");

		Matcher matcher = majorVersionPattern.matcher(
			buildProperties.getProperty(
				environmentType + "." + name + ".version"));

		String environmentMajorVersion;

		if (matcher.matches()) {
			environmentMajorVersion = matcher.group(1);
		}
		else {
			environmentMajorVersion = environmentVersion;
		}

		if (environmentType.equals("java.jdk")) {
			return buildProperties.getProperty(
				"env.option." + environmentType + "." + name + "." +
					environmentMajorVersion.replace(".", ""));
		}

		return buildProperties.getProperty(
			"env.option." + environmentType + "." + name +
				environmentMajorVersion.replace(".", ""));
	}

	@Override
	protected ExecutorService getExecutorService() {
		return _executorService;
	}

	@Override
	protected Element getFailureMessageElement() {
		return null;
	}

	@Override
	protected Element getGitHubMessageJobResultsElement() {
		return getGitHubMessageJobResultsElement(false);
	}

	@Override
	protected Element getGitHubMessageJobResultsElement(
		boolean showCommonFailuresCount) {

		String result = getResult();

		int failCount = getDownstreamBuildCountByResult("FAILURE");
		int successCount = getDownstreamBuildCountByResult("SUCCESS");
		int upstreamFailCount = 0;

		if (result.equals("UNSTABLE")) {
			failCount = getTestCountByStatus("FAILURE");
			successCount = getTestCountByStatus("SUCCESS");

			if (isCompareToUpstream()) {
				for (TestResult testResult : getTestResults(null)) {
					String testStatus = testResult.getStatus();

					if (testStatus.equals("FIXED") ||
						testStatus.equals("PASSED") ||
						testStatus.equals("SKIPPED")) {

						continue;
					}

					if (UpstreamFailureUtil.isTestFailingInUpstreamJob(
							testResult)) {

						upstreamFailCount++;
					}
				}

				if (showCommonFailuresCount) {
					failCount = upstreamFailCount;
				}
				else {
					failCount = failCount - upstreamFailCount;
				}
			}
		}

		return Dom4JUtil.getNewElement(
			"div", null, Dom4JUtil.getNewElement("h6", null, "Job Results:"),
			Dom4JUtil.getNewElement(
				"p", null, Integer.toString(successCount),
				JenkinsResultsParserUtil.getNounForm(
					successCount, " Tests", " Test"),
				" Passed.", Dom4JUtil.getNewElement("br"),
				Integer.toString(failCount),
				JenkinsResultsParserUtil.getNounForm(
					failCount, " Tests", " Test"),
				" Failed.", getFailureMessageElement()));
	}

	@Override
	protected String getJenkinsReportBuildInfoCellElementTagName() {
		return "th";
	}

	@Override
	protected List<Element> getJenkinsReportTableRowElements(
		String result, String status) {

		List<Element> tableRowElements = new ArrayList<>();

		tableRowElements.add(getJenkinsReportTableRowElement());

		List<Build> downstreamBuilds = getDownstreamBuilds(null);

		Collections.sort(
			downstreamBuilds, new BaseBuild.BuildDisplayNameComparator());

		for (Build downstreamBuild : downstreamBuilds) {
			if (!(downstreamBuild instanceof AxisBuild)) {
				continue;
			}

			AxisBuild downstreamAxisBuild = (AxisBuild)downstreamBuild;

			tableRowElements.add(
				downstreamAxisBuild.getJenkinsReportTableRowElement());
		}

		return tableRowElements;
	}

	@Override
	protected int getTestCountByStatus(String status) {
		JSONObject testReportJSONObject = getTestReportJSONObject();

		int failCount = testReportJSONObject.getInt("failCount");
		int skipCount = testReportJSONObject.getInt("skipCount");
		int totalCount = testReportJSONObject.getInt("totalCount");

		if (status.equals("SUCCESS")) {
			return totalCount - skipCount - failCount;
		}

		if (status.equals("FAILURE")) {
			return failCount;
		}

		throw new IllegalArgumentException("Invalid status: " + status);
	}

	protected final Pattern majorVersionPattern = Pattern.compile(
		"((\\d+)\\.?(\\d+?)).*");

	private static ExecutorService _executorService =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(20, true);

}