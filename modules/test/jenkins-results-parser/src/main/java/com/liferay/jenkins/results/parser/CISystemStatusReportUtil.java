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

import java.text.DecimalFormat;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class CISystemStatusReportUtil {

	public static void writeJenkinsDataJavaScriptFile(String filePath)
		throws IOException {

		JenkinsCohort jenkinsCohort = new JenkinsCohort(
			JenkinsResultsParserUtil.getBuildProperty(
				"ci.system.status.report.jenkins.cohort"));

		jenkinsCohort.writeDataJavaScriptFile(filePath);
	}

	public static void writeSpiraDataJavaScriptFile(String filePath)
		throws IOException {

		StringBuilder sb = new StringBuilder();

		sb.append("var relevantSuiteBuildData = ");

		JSONObject relevantSuiteBuildDataJSONObject = new JSONObject();

		sb.append(relevantSuiteBuildDataJSONObject.toString());

		sb.append("\nvar spiraDataGeneratedDate = new Date(");
		sb.append(JenkinsResultsParserUtil.getCurrentTimeMillis());
		sb.append(");\nvar successRateData = ");

		JSONArray successRateTableDataJSONArray = new JSONArray();

		sb.append(successRateTableDataJSONArray.toString());

		sb.append(";");

		JenkinsResultsParserUtil.write(filePath, sb.toString());
	}

	protected static String getPercentage(Integer dividend, Integer divisor) {
		double quotient = 0;

		if (divisor != 0) {
			quotient = (double)dividend / (double)divisor;
		}

		DecimalFormat decimalFormat = new DecimalFormat("###.##%");

		return decimalFormat.format(quotient);
	}

}