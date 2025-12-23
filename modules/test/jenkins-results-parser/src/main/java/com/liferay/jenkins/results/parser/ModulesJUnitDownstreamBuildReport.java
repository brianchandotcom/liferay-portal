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

	public List<TestTaskReport> getTestTaskReports() {
		List<TestTaskReport> testTaskReports = new ArrayList<>();

		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		JSONArray testTasksJSONArray = buildReportJSONObject.optJSONArray(
			"testTasks");

		if (testTasksJSONArray == null) {
			return testTaskReports;
		}

		for (int i = 0; i < testTasksJSONArray.length(); i++) {
			testTaskReports.add(
				TestTaskReportFactory.newTestTaskReport(
					testTasksJSONArray.getJSONObject(i)));
		}

		return testTaskReports;
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