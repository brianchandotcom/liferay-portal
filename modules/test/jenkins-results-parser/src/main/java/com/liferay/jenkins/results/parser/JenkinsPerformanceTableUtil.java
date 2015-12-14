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

		tableElement.add(createTableHeader());

		for (JenkinsPerformanceDataUtil.Result result : results) {
			tableElement.add(createRow(result));
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

	private static Element createAxisCell(
		String axis, String tag, String width) {

		String text = axis;

		if (axis.contains("=")) {
			text = axis.substring(axis.indexOf("=") + 1);
		}

		return createCell(text, tag, width);
	}

	private static Element createCell(String text, String tag, String width) {
		Element cell = new DefaultElement(tag);

		cell.addAttribute("width", width);

		return cell.addText(text);
	}

	private static Element createJobCell(
		String job, String tag, String width) {

		String text = job;

		if (job.contains("/")) {
			text = job.substring(job.indexOf("/") + 1);
		}

		return createCell(text, tag, width);
	}

	private static Element createNameCell(
		String name, String tag, String url, String width) {

		if ((url == null) || (url.length() == 0)) {
			return createCell(name, tag, width);
		}

		Element cell = new DefaultElement(tag);

		cell.addAttribute("width", width);

		Element anchor = new DefaultElement("a");

		anchor.addAttribute("href", url);
		anchor.addText(name);

		cell.add(anchor);

		return cell;
	}

	private static Element createRow(JenkinsPerformanceDataUtil.Result result) {
		return createRow(
			"td", result.getAxis(), result.getClassName(), Float.toString(result.getDuration()),
			result.getJob(), result.getName(),
			result.getStatus(), result.getUrl());
	}

	private static Element createRow(
		String tag, String axis, String className, String duration,
		String job, String name, String status, String url) {

		Element row = new DefaultElement("tr");

		row.add(createJobCell(job, tag, "16%"));

		row.add(createAxisCell(axis, tag, "12%"));

		row.add(createCell(className, tag, "30%"));

		row.add(createNameCell(name, tag, url, "30%"));

		row.add(createCell(status, tag, "8%"));

		row.add(createCell(duration, tag, "4%"));

		return row;
	}

	private static Element createTableHeader() {
		return createRow(
			"th", "Axis", "Class Name", "Duration (Seconds)", "Job", "Name",
			"Status", null);
	}

}