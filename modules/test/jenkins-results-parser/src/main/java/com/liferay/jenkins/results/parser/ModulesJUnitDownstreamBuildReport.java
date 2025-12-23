/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ModulesJUnitDownstreamBuildReport
	extends BaseDownstreamBuildReport {

	public List<TestClassTaskReport> getTestClassTaskReports() {
		List<TestClassTaskReport> testClassTaskReports = new ArrayList<>();

		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		JSONArray testTasksJSONArray = buildReportJSONObject.optJSONArray(
			"testTasks");

		if (testTasksJSONArray == null) {
			return testClassTaskReports;
		}

		for (int i = 0; i < testTasksJSONArray.length(); i++) {
			testClassTaskReports.add(
				TestClassTaskReportFactory.newTestClassTaskReport(
					testTasksJSONArray.getJSONObject(i)));
		}

		return testClassTaskReports;
	}

	protected ModulesJUnitDownstreamBuildReport(
		DownstreamBuild downstreamBuild) {

		super(downstreamBuild);
	}

	protected ModulesJUnitDownstreamBuildReport(
		String batchName, JSONObject buildReportJSONObject,
		TopLevelBuildReport topLevelBuildReport) {

		super(batchName, buildReportJSONObject, topLevelBuildReport);
	}

}