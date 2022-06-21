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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseDownstreamBuildReport
	extends BaseBuildReport implements DownstreamBuildReport {

	@Override
	public String getAxisName() {
		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		return buildReportJSONObject.optString("axisName", null);
	}

	@Override
	public String getBatchName() {
		return _batchName;
	}

	@Override
	public List<TestReport> getTestReports() {
		List<TestReport> testReports = new ArrayList<>();

		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		JSONArray testResultsJSONArray = buildReportJSONObject.optJSONArray(
			"testResults");

		if (testResultsJSONArray == null) {
			return testReports;
		}

		for (int i = 0; i < testResultsJSONArray.length(); i++) {
			testReports.add(
				TestReportFactory.newTestReport(
					this, testResultsJSONArray.getJSONObject(i)));
		}

		return testReports;
	}

	@Override
	public TopLevelBuildReport getTopLevelBuildReport() {
		return _topLevelBuildReport;
	}

	protected BaseDownstreamBuildReport(
		String batchName, JSONObject buildReportJSONObject,
		TopLevelBuildReport topLevelBuildReport) {

		super(buildReportJSONObject);

		_batchName = batchName;
		_topLevelBuildReport = topLevelBuildReport;
	}

	private final String _batchName;
	private final TopLevelBuildReport _topLevelBuildReport;

}