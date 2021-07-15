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

import com.liferay.jenkins.results.parser.failure.message.generator.ClosedChannelExceptionFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.FailureMessageGenerator;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

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
		return getSpiraPropertyValue("app.server");
	}

	@Override
	public URL getArtifactsBaseURL() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		StringBuilder sb = new StringBuilder();

		sb.append(topLevelBuild.getArtifactsBaseURL());
		sb.append("/");
		sb.append(getParameterValue("JOB_VARIANT"));

		try {
			return new URL(sb.toString());
		}
		catch (MalformedURLException malformedURLException) {
			return null;
		}
	}

	public String getBatchName() {
		return batchName;
	}

	@Override
	public String getBrowser() {
		return getSpiraPropertyValue("browser");
	}

	@Override
	public String getDatabase() {
		return getSpiraPropertyValue("database");
	}

	public List<AxisBuild> getDownstreamAxisBuilds() {
		List<AxisBuild> downstreamAxisBuilds = new ArrayList<>();

		List<Build> downstreamBuilds = getDownstreamBuilds(null);

		for (Build downstreamBuild : downstreamBuilds) {
			if (!(downstreamBuild instanceof AxisBuild)) {
				continue;
			}

			downstreamAxisBuilds.add((AxisBuild)downstreamBuild);
		}

		Collections.sort(
			downstreamAxisBuilds, new BaseBuild.BuildDisplayNameComparator());

		return downstreamAxisBuilds;
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
			getDownstreamBuildMessages(getFailedDownstreamBuilds());

		if (result.equals("FAILURE") &&
			downstreamBuildFailureMessages.isEmpty()) {

			return messageElement;
		}

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
			catch (IOException ioException) {
				throw new RuntimeException(
					"Unable to get build properties", ioException);
			}

			SimpleDateFormat sdf = new SimpleDateFormat(
				buildProperties.getProperty("jenkins.report.date.format"));

			Date date = null;

			try {
				date = sdf.parse(matcher.group("invokedTime"));
			}
			catch (ParseException parseException) {
				throw new RuntimeException(
					"Unable to get invoked time", parseException);
			}

			invokedTime = date.getTime();

			return invokedTime;
		}

		return getStartTime();
	}

	@Override
	public String getJDK() {
		return getSpiraPropertyValue("java.jdk");
	}

	@Override
	public Map<String, String> getMetricLabels() {
		Map<String, String> metricLabels = super.getMetricLabels();

		metricLabels.put("job_type", batchName);

		return metricLabels;
	}

	@Override
	public String getOperatingSystem() {
		return getSpiraPropertyValue("operating.system");
	}

	public String getSpiraPropertyValue(String propertyType) {
		String propertyName = _getSpiraPropertyNameFromBatchName(propertyType);

		return JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.batch.spira.property.value", propertyType,
			propertyName);
	}

	@Override
	public synchronized List<TestClassResult> getTestClassResults() {
		List<TestClassResult> testClassResults = new ArrayList<>();

		for (AxisBuild axisBuild : getDownstreamAxisBuilds()) {
			testClassResults.addAll(axisBuild.getTestClassResults());
		}

		return testClassResults;
	}

	@Override
	public synchronized List<TestResult> getTestResults() {
		List<TestResult> testResults = new ArrayList<>();

		for (AxisBuild axisBuild : getDownstreamAxisBuilds()) {
			testResults.addAll(axisBuild.getTestResults());
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
		return getTotalSlavesUsedCount(null, false);
	}

	@Override
	public int getTotalSlavesUsedCount(
		String status, boolean modifiedBuildsOnly) {

		return getTotalSlavesUsedCount(status, modifiedBuildsOnly, true);
	}

	@Override
	public synchronized void update() {
		super.update();

		if (badBuildNumbers.size() >= REINVOCATIONS_SIZE_MAX) {
			return;
		}

		String status = getStatus();
		String result = getResult();

		if ((status.equals("completed") && result.equals("SUCCESS")) ||
			fromArchive) {

			return;
		}

		boolean reinvoked = false;

		List<Build> builds = new ArrayList<>();

		builds.add(this);

		builds.addAll(getDownstreamBuilds("completed"));

		for (Build build : builds) {
			if (reinvoked) {
				break;
			}

			for (ReinvokeRule reinvokeRule : reinvokeRules) {
				String buildResult = build.getResult();

				if ((buildResult == null) || buildResult.equals("SUCCESS") ||
					!reinvokeRule.matches(build)) {

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

		String jobVariant = getJobVariant();

		if ((jobVariant != null) && !jobVariant.isEmpty()) {
			Matcher matcher = _jobVariantPattern.matcher(jobVariant);

			if (!matcher.matches()) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to find batch name of batch build from ",
						"job variant '", jobVariant,
						"'. Job variant must match pattern '",
						_jobVariantPattern.pattern(), "'."));
			}

			batchName = matcher.group("batchName");
		}
		else {
			batchName = null;
		}
	}

	protected AxisBuild getAxisBuild(String axisVariable) {
		for (AxisBuild downstreamAxisBuild : getDownstreamAxisBuilds()) {
			if (axisVariable.equals(downstreamAxisBuild.getAxisVariable())) {
				return downstreamAxisBuild;
			}
		}

		return null;
	}

	@Override
	protected ExecutorService getExecutorService() {
		return _executorService;
	}

	@Override
	protected Element getFailureMessageElement() {
		for (FailureMessageGenerator failureMessageGenerator :
				getFailureMessageGenerators()) {

			Element failureMessage = failureMessageGenerator.getMessageElement(
				this);

			if (failureMessage != null) {
				return failureMessage;
			}
		}

		return null;
	}

	@Override
	protected FailureMessageGenerator[] getFailureMessageGenerators() {
		return _FAILURE_MESSAGE_GENERATORS;
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

		if (result.equals("UNSTABLE")) {
			failCount = getTestCountByStatus("FAILURE");
			successCount = getTestCountByStatus("SUCCESS");

			if (isCompareToUpstream()) {
				List<TestResult> upstreamJobFailureTestResults =
					getUpstreamJobFailureTestResults();

				int upstreamFailCount = upstreamJobFailureTestResults.size();

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
				"p", null, String.valueOf(successCount),
				JenkinsResultsParserUtil.getNounForm(
					successCount, " Tests", " Test"),
				" Passed.", Dom4JUtil.getNewElement("br"),
				String.valueOf(failCount),
				JenkinsResultsParserUtil.getNounForm(
					failCount, " Tests", " Test"),
				" Failed."));
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

		for (AxisBuild downstreamAxisBuild : getDownstreamAxisBuilds()) {
			tableRowElements.addAll(
				downstreamAxisBuild.getJenkinsReportTableRowElements(
					downstreamAxisBuild.getResult(),
					downstreamAxisBuild.getStatus()));
		}

		return tableRowElements;
	}

	@Override
	protected int getTestCountByStatus(String status) {
		JSONObject testReportJSONObject = getTestReportJSONObject(false);

		int failCount = testReportJSONObject.getInt("failCount");

		if (status.equals("SUCCESS")) {
			int totalCount = testReportJSONObject.getInt("totalCount");
			int skipCount = testReportJSONObject.getInt("skipCount");

			return totalCount - skipCount - failCount;
		}

		if (status.equals("FAILURE")) {
			return failCount;
		}

		throw new IllegalArgumentException("Invalid status: " + status);
	}

	protected final String batchName;
	protected final Pattern majorVersionPattern = Pattern.compile(
		"((\\d+)\\.?(\\d+?)).*");

	private String _getSpiraPropertyNameFromBatchName(String propertyType) {
		String batchName = getBatchName();

		if ((batchName == null) || batchName.isEmpty()) {
			return null;
		}

		Properties jobProperties = getJobProperties();

		String propertyNamePrefix = JenkinsResultsParserUtil.combine(
			"test.batch.spira.property.name[", propertyType, "]");

		Set<String> propertyNames = new HashSet<>();

		for (Object jobPropertyNameObject : jobProperties.keySet()) {
			if (!(jobPropertyNameObject instanceof String)) {
				continue;
			}

			String jobPropertyNameRegex = JenkinsResultsParserUtil.combine(
				Pattern.quote(propertyNamePrefix), "\\[([^\\]+)\\]");

			String jobPropertyName = jobPropertyNameObject.toString();

			if (!jobPropertyName.matches(jobPropertyNameRegex)) {
				continue;
			}

			String propertyName = jobPropertyName.replaceAll(
				jobPropertyNameRegex, "$1");

			if (!batchName.contains(propertyName)) {
				continue;
			}

			propertyNames.add(propertyName);
		}

		if (propertyNames.isEmpty()) {
			return null;
		}

		String targetPropertyName = "";

		for (String propertyName : propertyNames) {
			if (propertyName.length() > targetPropertyName.length()) {
				targetPropertyName = propertyName;
			}
		}

		return targetPropertyName;
	}

	private static final FailureMessageGenerator[] _FAILURE_MESSAGE_GENERATORS =
		{new ClosedChannelExceptionFailureMessageGenerator()};

	private static final ExecutorService _executorService =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(10, true);
	private static final Pattern _jobVariantPattern = Pattern.compile(
		"(?<batchName>[^/]+)(/.*)?");

}