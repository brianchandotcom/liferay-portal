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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import org.dom4j.Element;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 * @author Yi-Chen Tsai
 */
public class JenkinsReportUtil {

	public static Element getHTMLBodyElement(TopLevelBuild topLevelBuild) {
		Map<String, Build> axisBuilds = new TreeMap<>();
		Map<String, Build> batchBuilds = new TreeMap<>();
		Map<String, TestResult> testResults = new TreeMap<>();

		for (Build batchBuild : topLevelBuild.getDownstreamBuilds(null)) {
			String batchBuildDisplayName = batchBuild.getDisplayName();

			if (batchBuildDisplayName == null) {
				return null;
			}

			batchBuilds.put(batchBuild.getDisplayName(), batchBuild);

			for (Build axisBuild : batchBuild.getDownstreamBuilds(null)) {
				String axisKey =
					batchBuild.getDisplayName() + "/" +
						JenkinsResultsParserUtil.getAxisVariable(
							axisBuild.getBuildURL());

				axisBuilds.put(axisKey, axisBuild);

				for (TestResult testResult : axisBuild.getTestResults(null)) {
					String displayName = testResult.getDisplayName();

					if (displayName != null) {
						testResults.put(
							testResult.getDisplayName(), testResult);
					}
				}
			}
		}

		Element h1Element = Dom4JUtil.getNewElement("h1");

		String buildURL = topLevelBuild.getBuildURL();

		Dom4JUtil.addToElement(
			h1Element, "Jenkins report for ",
			Dom4JUtil.getNewAnchorElement(buildURL, buildURL));

		JSONObject jobJSONObject = topLevelBuild.getBuildJSONObject();

		String jobDescription = "";

		try {
			jobDescription = jobJSONObject.getString("description");
		}
		catch (JSONException jsone) {
			jsone.printStackTrace();
		}

		Element h2Element = Dom4JUtil.getNewElement("h2");

		Dom4JUtil.addToElement(h2Element, jobDescription);

		Element bodyElement = Dom4JUtil.getNewElement("body");

		Dom4JUtil.addToElement(
			bodyElement, h1Element, h2Element,
			getSummaryElement(
				topLevelBuild, axisBuilds, batchBuilds, testResults),
			getTimelineElement(topLevelBuild, axisBuilds),
			getTopLevelTableElement(topLevelBuild),
			getBatchReportElement(batchBuilds));

		return bodyElement;
	}

	public static Element getHTMLHeadElement() {
		Element headElement = Dom4JUtil.getNewElement("head");

		StringBuilder sb = new StringBuilder();

		sb.append("caption, table, td, th {");

		sb.append("text-align: left;");

		sb.append("padding: .5em;");

		sb.append("white-space: nowrap;");

		sb.append("}");

		sb.append("th:first-child {");

		sb.append("text-indent: 1em;");

		sb.append("}");

		sb.append("td:first-child {");

		sb.append("text-indent: 4em;");

		sb.append("}");

		sb.append("td {");

		sb.append("}");

		sb.append("caption {");

		sb.append("font-size: 150%;");

		sb.append("font-weight: bold;");

		sb.append("}");

		Dom4JUtil.addToElement(
			headElement, Dom4JUtil.getNewElement("style", null, sb.toString()));

		return headElement;
	}

	protected static void addAxisInfoToTableElement(
		List<Build> builds, Element tableElement) {

		for (Build build : builds) {
			if (!(build instanceof AxisBuild)) {
				continue;
			}

			AxisBuild axisBuild = (AxisBuild)build;

			String axisName = JenkinsResultsParserUtil.combine(
				"AXIS_VARIABLE=", axisBuild.getAxisNumber());

			Dom4JUtil.getNewElement(
				"tr", tableElement,
				(Object[])getCommonBuildInfoElements(build, axisName, "td"));
		}
	}

	protected static Element getBatchInfoTableElement(
		List<Build> batchBuilds, String statusCountCaption) {

		Element tableElement = Dom4JUtil.getNewElement("table");

		tableElement.add(
			Dom4JUtil.getNewElement("caption", null, statusCountCaption));

		if (!batchBuilds.isEmpty()) {
			tableElement.add(getTableColumnHeaderElement());
		}

		for (Build batchBuild : batchBuilds) {
			String jobName = batchBuild.getJobName();

			String batchName = batchBuild.getDisplayName();

			if (batchName.contains("-dist(")) {
				batchName = batchName.replace(jobName, "dist");
			}
			else {
				batchName = batchName.replace(jobName + "/", "");
			}

			Element trBatchElement = Dom4JUtil.getNewElement("tr");

			for (Element batchInfoElement : getCommonBuildInfoElements(
					batchBuild, batchName, "th")) {

				trBatchElement.add(batchInfoElement);
			}

			tableElement.add(trBatchElement);

			List<Build> axisBuilds = batchBuild.getDownstreamBuilds(null);

			addAxisInfoToTableElement(axisBuilds, tableElement);
		}

		return tableElement;
	}

	protected static Element getBatchReportElement(
		Map<String, Build> batchBuilds) {

		List<Build> abortedBuilds = new ArrayList();

		List<Build> failureBuilds = new ArrayList();

		List<Build> missingBuilds = new ArrayList();

		List<Build> queuedBuilds = new ArrayList();

		List<Build> runningBuilds = new ArrayList();

		List<Build> startingBuilds = new ArrayList();

		List<Build> successBuilds = new ArrayList();

		Set<String> batchBuildsKeySet = batchBuilds.keySet();

		for (String key : batchBuildsKeySet) {
			Build build = batchBuilds.get(key);

			switch (build.getStatus()) {

				case "completed":
					String result = build.getResult();

					if (result.equals("SUCCESS")) {
						successBuilds.add(build);
					}
					else if (result.equals("FAILURE") ||
							 result.equals("UNSTABLE")) {

						failureBuilds.add(build);
					}
					else if (result.equals("ABORTED")) {
						abortedBuilds.add(build);
					}

					break;

				case "missing":
					missingBuilds.add(build);
					break;

				case "queued":
					queuedBuilds.add(build);
					break;

				case "running":
					runningBuilds.add(build);
					break;

				case "starting":
					startingBuilds.add(build);
					break;
			}
		}

		Element abortedBatchElement = getBatchInfoTableElement(
			abortedBuilds, "---- Aborted: " + abortedBuilds.size());

		Element failureBatchElement = getBatchInfoTableElement(
			failureBuilds, "---- Failure: " + failureBuilds.size());

		Element successBatchElement = getBatchInfoTableElement(
			successBuilds, "---- Success: " + successBuilds.size());

		Element completedBatchElement = Dom4JUtil.getNewElement("table");

		completedBatchElement.add(
			Dom4JUtil.getNewElement("caption", null, "Completed: "));

		Dom4JUtil.addToElement(
			completedBatchElement, abortedBatchElement, failureBatchElement,
			successBatchElement);

		Element missingBatchElement = getBatchInfoTableElement(
			missingBuilds, "Missing: " + missingBuilds.size());

		Element queuedBatchElement = getBatchInfoTableElement(
			queuedBuilds, "Queued: " + queuedBuilds.size());

		Element runningBatchElement = getBatchInfoTableElement(
			runningBuilds, "Running: " + runningBuilds.size());

		Element startingBatchElement = getBatchInfoTableElement(
			startingBuilds, "Starting: " + startingBuilds.size());

		Element divElement = Dom4JUtil.getNewElement("div");

		Dom4JUtil.addToElement(
			divElement, queuedBatchElement, startingBatchElement,
			runningBatchElement, missingBatchElement, completedBatchElement);

		return divElement;
	}

	protected static Element getChartJSScriptElement(
		String xData, String y1Data, String y2Data) {

		String resource = null;

		try {
			Class<?> clazz = JenkinsResultsParserUtil.class;

			resource = JenkinsResultsParserUtil.readInputStream(
				clazz.getResourceAsStream("chart-template.js"));
		}
		catch (IOException ioe) {
		}

		resource = resource.replace("'xData'", xData);
		resource = resource.replace("'y1Data'", y1Data);
		resource = resource.replace("'y2Data'", y2Data);

		Element scriptElement = Dom4JUtil.getNewElement("script");

		scriptElement.addText(resource);

		return scriptElement;
	}

	protected static Element[] getCommonBuildInfoElements(
		Build build, String buildName, String tagName) {

		String buildURL = build.getBuildURL();

		String buildConsoleURL = buildURL + "console";

		List<Element> elements = new ArrayList<>();

		elements.add(
			Dom4JUtil.getNewElement(
				tagName, null,
				Dom4JUtil.getNewAnchorElement(
					buildConsoleURL, null, "Console")));

		elements.add(
			Dom4JUtil.getNewElement(
				tagName, null,
				JenkinsResultsParserUtil.toDurationString(
					build.getDuration())));

		elements.add(
			Dom4JUtil.getNewElement(
				tagName, null,
				Dom4JUtil.getNewAnchorElement(buildURL, null, buildName)));

		Date buildStartDate = new Date(build.getStartTimestamp());

		elements.add(
			Dom4JUtil.getNewElement(
				tagName, null, buildStartDate.toLocaleString()));

		String buildTestReportURL = buildURL + "testReport";

		elements.add(
			Dom4JUtil.getNewElement(
				tagName, null,
				Dom4JUtil.getNewAnchorElement(
					buildTestReportURL, null, "Test Report")));

		String status = build.getStatus();

		if (status != null) {
			status = StringUtils.upperCase(status);
		}
		else {
			status = "";
		}

		elements.add(Dom4JUtil.getNewElement(tagName, null, status));

		String result = build.getResult();

		if (result == null) {
			result = "";
		}

		elements.add(Dom4JUtil.getNewElement(tagName, null, result));

		return elements.toArray(new Element[elements.size()]);
	}

	protected static Element getLongestAxisElement(
		Map<String, Build> axisBuilds) {

		String jobName = "Unavailable";

		long longestAxisDuration = 0;

		String longestAxisName = "Unavailable";

		String longestAxisParentName = "Unavailable";

		String longestAxisURL = "Unavailable";

		Set<String> axisBuildsKeySet = axisBuilds.keySet();

		for (String key : axisBuildsKeySet) {
			Build axisBuild = axisBuilds.get(key);

			long axisDuration = axisBuild.getDuration();

			if (axisDuration > longestAxisDuration) {
				longestAxisDuration = axisDuration;

				String axisNumber = ((AxisBuild)axisBuild).getAxisNumber();

				longestAxisName = "AXIS_VARAIBLE=" + axisNumber;

				longestAxisParentName =
					axisBuild.getParentBuild().getDisplayName();

				jobName = axisBuild.getJobName();

				longestAxisURL = axisBuild.getBuildURL();
			}
		}

		longestAxisParentName = longestAxisParentName.replace(
			jobName + "/", "");

		StringBuilder sb = new StringBuilder();

		sb.append(longestAxisParentName);

		sb.append("/");

		sb.append(longestAxisName);

		String longestAxisDisplayName = sb.toString();

		Element longestAxisElement = Dom4JUtil.getNewElement(
			"p", null, "Longest Axis: ",
			Dom4JUtil.getNewAnchorElement(
				longestAxisURL, longestAxisDisplayName),
			" in: ",
			JenkinsResultsParserUtil.toDurationString(longestAxisDuration));

		return longestAxisElement;
	}

	protected static Element getLongestBatchElement(
		Map<String, Build> batchBuilds) {

		String jobName = "Unavailable";

		long longestBatchDuration = 0;

		String longestBatchName = "Unavailable";

		String longestBatchURL = "Unavailable";

		Set<String> batchBuildsKeySet = batchBuilds.keySet();

		for (String key : batchBuildsKeySet) {
			Build build = batchBuilds.get(key);

			long batchDuration = build.getDuration();

			if (longestBatchDuration < batchDuration) {
				longestBatchDuration = batchDuration;

				jobName = build.getJobName();

				longestBatchName = build.getDisplayName();

				longestBatchURL = build.getBuildURL();
			}
		}

		String longestBatchDisplayName = longestBatchName.replace(
			jobName + "/", "");

		Element longestBatchElement = Dom4JUtil.getNewElement(
			"p", null, "Longest Batch: ",
			Dom4JUtil.getNewAnchorElement(
				longestBatchURL, longestBatchDisplayName),
			" in: ",
			JenkinsResultsParserUtil.toDurationString(longestBatchDuration));

		return longestBatchElement;
	}

	protected static Element getLongestTestElement(
		Map<String, TestResult> testResults) {

		long longestTestDuration = 0;

		String longestTestName = "Unavailable";

		String longestTestParentName = "Unavailable";

		TestResult longestTestResult = null;

		String longestTestURL = "Unavailable";

		Set<String> testResultsKeySet = testResults.keySet();

		for (String key : testResultsKeySet) {
			TestResult testResult = testResults.get(key);

			long testDuration = testResult.getDuration();

			if (longestTestDuration < testDuration) {
				longestTestDuration = testDuration;

				longestTestResult = testResult;
			}
		}

		if (longestTestResult != null) {
			longestTestName = longestTestResult.getDisplayName();

			longestTestURL = longestTestResult.getTestReportURL();

			Build testAxisBuild = longestTestResult.getAxisBuild();

			Build testBatchBuild = testAxisBuild.getParentBuild();

			String testBatchBuildName = testBatchBuild.getDisplayName();

			String testBatchJobName = testBatchBuild.getJobName();

			longestTestParentName = testBatchBuildName.replace(
				testBatchJobName + "/", "");
		}

		StringBuilder sb = new StringBuilder();

		sb.append(longestTestParentName);

		sb.append("/");

		sb.append(longestTestName);

		String longestTestDisplayName = sb.toString();

		Element longestTestElement = Dom4JUtil.getNewElement(
			"p", null, "Longest Test: ",
			Dom4JUtil.getNewAnchorElement(
				longestTestURL, longestTestDisplayName),
			" in: ",
			JenkinsResultsParserUtil.toDurationString(longestTestDuration));

		return longestTestElement;
	}

	protected static Element getSummaryElement(
		Build topLevelBuild, Map<String, Build> axisBuilds,
		Map<String, Build> batchBuilds, Map<String, TestResult> testResults) {

		Element buildTimeElement = Dom4JUtil.getNewElement(
			"p", null, "Build Time: ",
			JenkinsResultsParserUtil.toDurationString(
				topLevelBuild.getDuration()));

		Element ciUsageElement = getTotalCIUsageElement(axisBuilds);

		Element longestAxisElement = getLongestAxisElement(axisBuilds);

		Element longestBatchElement = getLongestBatchElement(batchBuilds);

		Element longestTestElement = getLongestTestElement(testResults);

		Date startTime = new Date(topLevelBuild.getStartTimestamp());

		Element startTimeElement = Dom4JUtil.getNewElement(
			"p", null, "Start Time: ", startTime.toLocaleString());

		Element vmUsageElement = getTotalVMUSageElement(axisBuilds);

		Element divElement = Dom4JUtil.getNewElement("div");

		Dom4JUtil.addToElement(
			divElement, startTimeElement, buildTimeElement, ciUsageElement,
			vmUsageElement, longestBatchElement, longestAxisElement,
			longestTestElement);

		return divElement;
	}

	protected static Element getTableColumnHeaderElement() {
		Element nameElement = Dom4JUtil.getNewElement("th", null, "Name");

		Element consoleElement = Dom4JUtil.getNewElement("th", null, "Console");

		Element testReportElement = Dom4JUtil.getNewElement(
			"th", null, "Test Report");

		Element startTimeElement = Dom4JUtil.getNewElement(
			"th", null, "Start Time");

		Element buildTimeElement = Dom4JUtil.getNewElement(
			"th", null, "Build Time");

		Element statusElement = Dom4JUtil.getNewElement("th", null, "Status");

		Element resultElement = Dom4JUtil.getNewElement("th", null, "Result");

		Element tableColumnHeaderElement = Dom4JUtil.getNewElement("tr");

		Dom4JUtil.addToElement(
			tableColumnHeaderElement, nameElement, consoleElement,
			testReportElement, startTimeElement, buildTimeElement,
			statusElement, resultElement);

		return tableColumnHeaderElement;
	}

	protected static Element getTimelineElement(
		Build topLevelBuild, Map<String, Build> builds) {

		long topLevelDuration = topLevelBuild.getDuration();
		long topLevelStartTime = topLevelBuild.getStartTimestamp();

		int dataPoints = 500;

		long[] invocationData = new long[dataPoints];
		long[] slaveUsageData = new long[dataPoints];

		for (String key : builds.keySet()) {
			Build build = builds.get(key);

			long buildDuration = build.getDuration();
			long buildStartTime = build.getStartTimestamp();

			long buildEndTime = buildDuration + buildStartTime;

			long dataEnd =
				(buildEndTime - topLevelStartTime) * dataPoints /
					topLevelDuration;

			long dataStart =
				(buildStartTime - topLevelStartTime) * dataPoints /
					topLevelDuration;

			for (int i = (int)dataStart; i < dataEnd; i++) {
				slaveUsageData[i] = ++slaveUsageData[i];
			}

			invocationData[(int)dataStart] = ++invocationData[(int)dataStart];
		}

		long[] timeData = new long[dataPoints];

		timeData[0] = 0;

		for (int i = 1; i < timeData.length; i++) {
			timeData[i] = timeData[0] + i * topLevelDuration / dataPoints;
		}

		Element canvasElement = Dom4JUtil.getNewElement("canvas");

		canvasElement.addAttribute("height", "300");
		canvasElement.addAttribute("id", "timeline");

		Element scriptElement = Dom4JUtil.getNewElement("script");

		scriptElement.addAttribute("src", _CHART_JS_FILE);
		scriptElement.addText("");

		Element chartJSScriptElement = getChartJSScriptElement(
			Arrays.toString(timeData), Arrays.toString(slaveUsageData),
			Arrays.toString(invocationData));

		Element divElement = Dom4JUtil.getNewElement("div");

		Dom4JUtil.addToElement(
			divElement, canvasElement, scriptElement, chartJSScriptElement);

		return divElement;
	}

	protected static Element getTopLevelTableElement(Build topLevelBuild) {
		Element topLevelTableElement = Dom4JUtil.getNewElement("table");

		String status = topLevelBuild.getStatus();

		String result = topLevelBuild.getResult();

		if (result != null) {
			topLevelTableElement.add(
				Dom4JUtil.getNewElement(
					"caption", null,
					"Top Level Build - <strong>" + result + "</strong>"));
		}
		else {
			topLevelTableElement.add(
				Dom4JUtil.getNewElement(
					"caption", null,
					"Top Level Build - <strong>" +
						StringUtils.upperCase(status) + "</strong>"));
		}

		topLevelTableElement.add(getTableColumnHeaderElement());

		String jobName = topLevelBuild.getJobName();

		String topLevelName = topLevelBuild.getDisplayName();

		topLevelName = topLevelName.replace(jobName + "/", "");

		Element trTopLevelElement = Dom4JUtil.getNewElement("tr");

		for (Element topLevelInfoElement : getCommonBuildInfoElements(
				topLevelBuild, topLevelName, "th")) {

			trTopLevelElement.add(topLevelInfoElement);
		}

		topLevelTableElement.add(trTopLevelElement);

		return topLevelTableElement;
	}

	protected static Element getTotalCIUsageElement(
		Map<String, Build> axisBuilds) {

		long totalTime = 0;

		for (Build axisBuild : axisBuilds.values()) {
			long axisDuration = axisBuild.getDuration();

			totalTime = totalTime + axisDuration;
		}

		long hoursTotalTime = totalTime / 3600000;

		Element totalCIUsageElement = Dom4JUtil.getNewElement(
			"p", null, "Total CI Usage: " + hoursTotalTime + " server hours");

		return totalCIUsageElement;
	}

	protected static Element getTotalVMUSageElement(
		Map<String, Build> axisBuilds) {

		long totalVMUsed = axisBuilds.size();

		Element totalVMUsageElement = Dom4JUtil.getNewElement(
			"p", null, "Total VM used: " + totalVMUsed + " slaves");

		return totalVMUsageElement;
	}

	private static final String _CHART_JS_FILE =
		"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js";

}