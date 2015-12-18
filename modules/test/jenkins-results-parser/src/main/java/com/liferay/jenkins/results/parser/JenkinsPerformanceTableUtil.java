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

import java.util.List;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * @author Peter Yoo
 */
public class JenkinsPerformanceTableUtil {

	public static String generateHTML() throws IOException {
		List<JenkinsPerformanceDataUtil.Result> results =
			JenkinsPerformanceDataUtil.getSlowestResults();

		if (results == null) {
			return "";
		}

		Element element = new DefaultElement("table");

		element.addAttribute("border", "1");
		element.addAttribute("cellspacing", "0");

		element.add(
			_createRowElement(
				"th", "Axis", "Class Name", "Duration (Seconds)", "Job Name",
				"Name", "Status", null));

		for (JenkinsPerformanceDataUtil.Result result : results) {
			element.add(
				_createRowElement(
					"td", result.getAxis(), result.getClassName(),
					Float.toString(result.getDuration()), result.getJobName(),
					result.getName(), result.getStatus(), result.getUrl()));
		}

		JenkinsPerformanceDataUtil.reset();

		StringBuilder sb = new StringBuilder();

		sb.append(JenkinsResultsParserUtil.format(element));

		return sb.toString();
	}

	private static Element _createAxisElement(
		String elementName, String axis, String width) {

		String text = axis;

		if (axis.contains("=")) {
			text = axis.substring(axis.indexOf("=") + 1);
		}

		return _createElement(elementName, text, width);
	}

	private static Element _createElement(
		String elementName, String text, String width) {

		Element element = new DefaultElement(elementName);

		element.addAttribute("width", width);

		return element.addText(text);
	}

	private static Element _createJobElement(
		String elementName, String jobName, String width) {

		String text = jobName;

		if (jobName.contains("/")) {
			text = jobName.substring(jobName.indexOf("/") + 1);
		}

		return _createElement(elementName, text, width);
	}

	private static Element _createNameElement(
		String elementName, String name, String url, String width) {

		if ((url == null) || (url.length() == 0)) {
			return _createElement(elementName, name, width);
		}

		Element element = new DefaultElement(elementName);

		Element anchorElement = new DefaultElement("a");

		anchorElement.addAttribute("href", url);
		anchorElement.addText(name);

		element.add(anchorElement);

		element.addAttribute("width", width);

		return element;
	}

	private static Element _createRowElement(
		String elementName, String axis, String className, String duration,
		String jobName, String name, String status, String url) {

		Element element = new DefaultElement("tr");

		element.add(_createJobElement(elementName, jobName, "16%"));
		element.add(_createAxisElement(elementName, axis, "12%"));
		element.add(_createElement(elementName, className, "30%"));
		element.add(_createNameElement(elementName, name, url, "30%"));
		element.add(_createElement(elementName, status, "8%"));
		element.add(_createElement(elementName, duration, "4%"));

		return element;
	}

}