/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			return "<p>Performance data is not available.</p>";
		}

		Element divElement = new DefaultElement("div");

		Element pElement = new DefaultElement("p");

		divElement.add(pElement);

		pElement.addText(
			(JenkinsPerformanceDataUtil.getSlaveCount() + 1) + " Slaves");

		pElement.add(new DefaultElement("br"));

		pElement.addText(JenkinsPerformanceDataUtil.getTestCount() + " Tests");

		pElement.add(new DefaultElement("br"));

		pElement.addText(
			JenkinsPerformanceDataUtil.getTotalDuration() + " Seconds of CPU " +
				"Time");

		pElement.add(new DefaultElement("br"));

		Element tableElement = new DefaultElement("table");

		divElement.add(tableElement);

		tableElement.addAttribute("border", "1");
		tableElement.addAttribute("cellspacing", "0");

		tableElement.add(
			_createRowElement(
				"th", "Axis", "Class Name", "Duration (Seconds)", "Job Name",
				"Name", "Status", null));

		for (JenkinsPerformanceDataUtil.Result result : results) {
			tableElement.add(
				_createRowElement(
					"td", result.getAxis(), result.getClassName(),
					String.valueOf(result.getDuration()), result.getJobName(),
					result.getName(), result.getStatus(), result.getUrl()));
		}

		JenkinsPerformanceDataUtil.reset();

		System.out.println(Dom4JUtil.format(pElement));

		return Dom4JUtil.format(divElement);
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