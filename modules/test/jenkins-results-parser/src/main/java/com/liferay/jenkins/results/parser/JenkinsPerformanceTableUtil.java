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

		Element tableElement = new DefaultElement("table");

		tableElement.add(createTableHeaderElement());

		for (JenkinsPerformanceDataUtil.Result result : results) {
			tableElement.add(createRowElement(result));
		}

		JenkinsPerformanceDataUtil.reset();

		StringBuilder sb = new StringBuilder();

		sb.append("<style>\n");
		sb.append(" table {\n");
		sb.append("  table-layout: fixed;\n");
		sb.append("  width: 95%;\n");
		sb.append(" }\n");
		sb.append(" table, th, td {\n");
		sb.append("  border: 1px solid black;\n");
		sb.append("  border-collapse: collapse;\n");
		sb.append(" }\n");
		sb.append(" th, td {\n");
		sb.append("  word-wrap: break-word;\n");
		sb.append("  padding: 3px;\n");
		sb.append("  text-align: left;\n");
		sb.append(" }\n");
		sb.append("</style>\n");
		sb.append(JenkinsResultsParserUtil.format(tableElement));

		return sb.toString();
	}

	private static Element createAxisCellElement(
		String axis, String tag, String width) {

		String text = axis;

		if (axis.contains("=")) {
			text = axis.substring(axis.indexOf("=") + 1);
		}

		return createCellElement(text, tag, width);
	}

	private static Element createCellElement(String text, String tag, String width) {
		Element cellElement = new DefaultElement(tag);

		cellElement.addAttribute("width", width);

		return cellElement.addText(text);
	}

	private static Element createJobCellElement(String job, String tag, String width) {
		String text = job;

		if (job.contains("/")) {
			text = job.substring(job.indexOf("/") + 1);
		}

		return createCellElement(text, tag, width);
	}

	private static Element createNameCellElement(
		String name, String tag, String url, String width) {

		if ((url == null) || (url.length() == 0)) {
			return createCellElement(name, tag, width);
		}

		Element cellElement = new DefaultElement(tag);

		cellElement.addAttribute("width", width);

		Element anchorElement = new DefaultElement("a");

		anchorElement.addAttribute("href", url);
		anchorElement.addText(name);

		cellElement.add(anchorElement);

		return cellElement;
	}

	private static Element createRowElement(JenkinsPerformanceDataUtil.Result result) {
		return createRowElement(
			"td", result.getAxis(), result.getClassName(),
			Float.toString(result.getDuration()), result.getJob(),
			result.getName(), result.getStatus(), result.getUrl());
	}

	private static Element createRowElement(
		String tag, String axis, String className, String duration, String job,
		String name, String status, String url) {

		Element rowElement = new DefaultElement("tr");

		rowElement.add(createJobCellElement(job, tag, "16%"));

		rowElement.add(createAxisCellElement(axis, tag, "12%"));

		rowElement.add(createCellElement(className, tag, "30%"));

		rowElement.add(createNameCellElement(name, tag, url, "30%"));

		rowElement.add(createCellElement(status, tag, "8%"));

		rowElement.add(createCellElement(duration, tag, "4%"));

		return rowElement;
	}

	private static Element createTableHeaderElement() {
		return createRowElement(
			"th", "Axis", "Class Name", "Duration (Seconds)", "Job", "Name",
			"Status", null);
	}

}