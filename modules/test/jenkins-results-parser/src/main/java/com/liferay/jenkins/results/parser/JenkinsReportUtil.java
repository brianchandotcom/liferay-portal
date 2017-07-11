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

import org.dom4j.Element;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @author Kenji Heigel
 */
public class JenkinsReportUtil {
	public static Element getTimelineElement(
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

//		timeData[0] = topLevelStartTime;
		timeData[0] = 0;

		for (int i = 1; i < timeData.length; i++) {
			timeData[i] = timeData[0] + i * topLevelDuration / dataPoints ;
		}

		Element canvasElement = Dom4JUtil.getNewElement("canvas");

		canvasElement.addAttribute("height", "300");
		canvasElement.addAttribute("id", "timeline");

		Element scriptElement = Dom4JUtil.getNewElement("script");

		scriptElement.addAttribute("src", _CHART_JS_FILE);
		scriptElement.addText("");

		Element chartJSScriptElement =
			getChartJSScriptElement(
				Arrays.toString(timeData), Arrays.toString(slaveUsageData),
				Arrays.toString(invocationData));

		Element divElement = Dom4JUtil.getNewElement("div");

		Dom4JUtil.addToElement(
			divElement, canvasElement, scriptElement, chartJSScriptElement);

		return divElement;
	}

	public static Element getBasicHeaderElement(
	Build topLevelBuild, Map<String, Build> builds) {

		long totalTime = 0;

		for (Build axisBuild : builds.values()) {
			totalTime = totalTime + axisBuild.getDuration();
		}

		Element divElement = Dom4JUtil.getNewElement("div");

		long hoursTotalTime = totalTime / 3600000;

		divElement.addText(
			"Total CI Usage: " + hoursTotalTime + " server hours");

		return divElement;
	}

	public static Element getBatchReportElement(
		Build topLevelBuild, Map<String, Build> builds) {

		Set<String> buildsKeySet = builds.keySet();

		for (String key : buildsKeySet) {
			Build build = builds.get(key);

			long buildDuration = build.getDuration();
			long buildStartTime = build.getStartTimestamp();

			long buildEndTime = buildDuration + buildStartTime;
		}

		return null;
	}

	public static Element getChartJSScriptElement(
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

	private static final String _CHART_JS_FILE =
		"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js";
}