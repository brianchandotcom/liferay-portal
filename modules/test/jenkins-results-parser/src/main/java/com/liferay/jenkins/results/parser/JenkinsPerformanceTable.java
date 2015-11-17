package com.liferay.jenkins.results.parser;

import java.util.List;

import org.apache.tools.ant.Project;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

public class JenkinsPerformanceTable {

	protected static Element createRow(
		String cellTag, String axis, String batch, String className,
		String duration, String name, String status, String url) {

		Element row = new DefaultElement("tr");

		Element axisCell = new DefaultElement(cellTag);
		Element batchCell = new DefaultElement(cellTag);
		Element classNameCell = new DefaultElement(cellTag);
		Element durationCell = new DefaultElement(cellTag);
		Element nameCell = new DefaultElement(cellTag);
		Element statusCell = new DefaultElement(cellTag);

		int x = axis.indexOf("=");

		String shortAxis = axis.substring(x + 1);

		int y = batch.indexOf("/");

		String shortBatch = batch.substring(y + 1);

		axisCell.addText(shortAxis);
		batchCell.addText(shortBatch);
		classNameCell.addText(className);
		durationCell.addText(duration);
		statusCell.addText(status);

		if (url != null) {
			Element anchor = new DefaultElement("a");

			anchor.addAttribute("href", url);
			anchor.addText(name);

			nameCell.add(anchor);
		}
		else {
			nameCell.addText(name);
		}

		axisCell.addAttribute("width", "8%");
		batchCell.addAttribute("width", "16%");
		classNameCell.addAttribute("width", "30%");
		durationCell.addAttribute("width", "4%");
		nameCell.addAttribute("width", "30%");
		statusCell.addAttribute("width", "8%");

		row.add(batchCell);
		row.add(axisCell);
		row.add(classNameCell);
		row.add(nameCell);
		row.add(statusCell);
		row.add(durationCell);

		return row;
	}

	protected static Element createTableHeader() {
		return createRow(
			"th", "Axis", "Batch", "Class Name",
			"Duration (Seconds)", "Name", "Status", null);
	}

	protected static Element createRow(Result result) {
		return createRow(
			"td", result.getAxis(), result.getBatch(),
			result.getClassName(),
			Float.toString(result.getDuration()),
			result.getName(), result.getStatus(),
			result.getUrl());
	}	
	
	public static String generateTableHTML(Project project) {
		Element tableElement = new DefaultElement("table");

		Element headerElement = createTableHeader();

		tableElement.add(headerElement);
		
		if (JenkinsPerformanceDataProcessor._globalList == null) {
			return "";
		}
		
		for (Result result : JenkinsPerformanceDataProcessor._globalList) {
			Element row = createRow(result);

			tableElement.add(row);
		}

		JenkinsPerformanceDataProcessor._globalList.clear();

		StringBuilder sb = new StringBuilder();

		sb.append("<style>\n");
		sb.append("    table {\n");
		sb.append("        table-layout: fixed;\n");
		sb.append("        width: 95%;\n");
		sb.append("    }\n");
		sb.append("    table, th, td {\n");
		sb.append("        border: 1px solid black;\n");
		sb.append("        border-collapse: collapse;\n");
		sb.append("    }\n");
		sb.append("    th, td {\n");
		sb.append("        word-wrap: break-word;\n");
		sb.append("        padding: 3px;\n");
		sb.append("        text-align: left;\n");
		sb.append("    }\n");
		sb.append("</style>\n");

		sb.append(tableElement.asXML());

		return sb.toString();
	}
}
