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

		try {
			for (Build batchBuild : topLevelBuild.getDownstreamBuilds(null)) {
				batchBuilds.put(batchBuild.getDisplayName(), batchBuild);

				for (Build axisBuild : batchBuild.getDownstreamBuilds(null)) {
					try {
						String axisKey =
							batchBuild.getDisplayName() + "/" +
								JenkinsResultsParserUtil.getAxisVariable(
									axisBuild.getBuildURL());

						axisBuilds.put(axisKey, axisBuild);
					}
					catch (Exception e) {
					}

					for (TestResult testResult :
							axisBuild.getTestResults(null)) {

						try {
							testResults.put(
								testResult.getDisplayName(), testResult);
						}
						catch (Exception e) {
						}
					}
				}
			}
		}
		catch (Exception e) {
			return null;
		}

		Element h1Element = Dom4JUtil.getNewElement("h1");

		String buildURL = topLevelBuild.getBuildURL();

		Dom4JUtil.addToElement(
			h1Element, "Jenkins report for ",
			Dom4JUtil.getNewAnchorElement(buildURL, buildURL));

		JSONObject jobJSONObject = topLevelBuild.getBuildJSONObject();

		String jobDescription = jobJSONObject.getString("description");

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

		return headElement;
	}

	protected static void addAxisInfoToTableElement(
		List<Build> axisBuilds, Element tableElement) {

		for (Build axisBuild : axisBuilds) {
			String axisName =
				"AXIS_VARIABLE=" + ((AxisBuild)axisBuild).getAxisNumber();

			Element trAxisInfoElement = Dom4JUtil.getNewElement("tr");

			List<Element> axisInfoElements = getCommonBuildInfoElementList(
				axisBuild, axisName, "td");

			for (Element axisInfoElement : axisInfoElements) {
				trAxisInfoElement.add(axisInfoElement);
			}

			tableElement.add(trAxisInfoElement);
		}
	}

	protected static Element getBatchInfoTableElement(
		List<Build> batchBuilds, String statusCountCaption) {

		Element tableElement = Dom4JUtil.getNewElement("table");

		tableElement.add(
			Dom4JUtil.getNewElement("caption", null, statusCountCaption));

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

			List<Element> batchInfoElements = getCommonBuildInfoElementList(
				batchBuild, batchName, "th");

			for (Element batchInfoElement : batchInfoElements) {
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

	protected static List<Element> getCommonBuildInfoElementList(
		Build build, String buildName, String elementTag) {

		String buildURL = build.getBuildURL();

		String buildConsoleURL = buildURL + "console";

		String buildTestReportURL = buildURL + "testReport";

		long buildDuration = build.getDuration();

		Date buildStartDate = new Date(build.getStartTimestamp());

		Element buildConsoleElement = Dom4JUtil.getNewElement(
			elementTag, null,
			Dom4JUtil.getNewAnchorElement(buildConsoleURL, null, "Console"));

		Element buildDurationElement = Dom4JUtil.getNewElement(
			elementTag, null,
			JenkinsResultsParserUtil.toDurationString(buildDuration));

		Element buildNameElement = Dom4JUtil.getNewElement(
			elementTag, null,
			Dom4JUtil.getNewAnchorElement(buildURL, null, buildName));

		Element buildStartTimeElement = Dom4JUtil.getNewElement(
			elementTag, null, buildStartDate.toLocaleString());

		Element buildTestReportElement = Dom4JUtil.getNewElement(
			elementTag, null,
			Dom4JUtil.getNewAnchorElement(
				buildTestReportURL, null, "Test Report"));

		Element buildStatusElement = Dom4JUtil.getNewElement(elementTag);

		String status = build.getStatus();

		if (status != null) {
			buildStatusElement.addText(StringUtils.upperCase(status));
		}
		else {
			buildStatusElement.addText("");
		}

		Element buildResultElement = Dom4JUtil.getNewElement(elementTag);

		String result = build.getResult();

		if (result != null) {
			buildResultElement.addText(result);
		}
		else {
			buildResultElement.addText("");
		}

		List<Element> elementList = new ArrayList();

		elementList.add(buildNameElement);

		elementList.add(buildConsoleElement);

		elementList.add(buildTestReportElement);

		elementList.add(buildStartTimeElement);

		elementList.add(buildDurationElement);

		elementList.add(buildStatusElement);

		elementList.add(buildResultElement);

		return elementList;
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
					"Top Level Build - <strong>" + status + "</strong>"));
		}

		String jobName = topLevelBuild.getJobName();

		String topLevelName = topLevelBuild.getDisplayName();

		topLevelName = topLevelName.replace(jobName + "/", "");

		Element trTopLevelElement = Dom4JUtil.getNewElement("tr");

		List<Element> topLevelInfoElements = getCommonBuildInfoElementList(
			topLevelBuild, topLevelName, "th");

		for (Element topLevelInfoElement : topLevelInfoElements) {
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