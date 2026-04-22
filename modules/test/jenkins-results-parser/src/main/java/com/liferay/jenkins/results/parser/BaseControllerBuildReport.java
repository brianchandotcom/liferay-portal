/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseControllerBuildReport
	extends BaseBuildReport implements ControllerBuildReport {

	@Override
	public JSONObject getBuildReportJSONObject() {
		return _buildReportJSONObject;
	}

	@Override
	public String getDescription() {
		if (_description != null) {
			return _description;
		}

		JSONObject jsonObject = JenkinsAPIUtil.getAPIJSONObject(
			String.valueOf(getBuildURL()), "description");

		if (jsonObject == null) {
			return null;
		}

		_description = jsonObject.optString("description");

		return _description;
	}

	@Override
	public String getSHA() {
		Matcher matcher = _buildDescriptionPattern.matcher(getDescription());

		if (!matcher.find()) {
			return null;
		}

		return matcher.group("sha");
	}

	@Override
	public String getTestrayBuildDateString() {
		return JenkinsResultsParserUtil.toDateString(
			getStartDate(), "yyyy-MM-dd HH:mm:ss", "America/Los_Angeles");
	}

	@Override
	public TopLevelBuildReport getTopLevelBuildReport() {
		return _topLevelBuildReport;
	}

	protected BaseControllerBuildReport(
		Build controllerBuild, TopLevelBuildReport topLevelBuildReport) {

		super(controllerBuild.getBuildURL());

		_topLevelBuildReport = topLevelBuildReport;

		_buildReportJSONObject = controllerBuild.getBuildReportJSONObject();
	}

	protected BaseControllerBuildReport(
		JSONObject buildReportJSONObject,
		TopLevelBuildReport topLevelBuildReport) {

		super(buildReportJSONObject.getString("buildURL"));

		_buildReportJSONObject = buildReportJSONObject;
		_topLevelBuildReport = topLevelBuildReport;
	}

	private static final Pattern _buildDescriptionPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"<strong[^>]*>(?<status>[A-Z]+)<\\/strong> - <a href=\\\"",
			"(?<buildURL>[^\\\"]+)\\\">Build URL<\\/a>.*<strong>Git ID:",
			"</strong> <a href=\"https://github.com/[^/]+/[^/]+/commit/",
			"(?<sha>[0-9a-f]{7,40})\">[0-9a-f]{7}</a>"));

	private final JSONObject _buildReportJSONObject;
	private String _description;
	private final TopLevelBuildReport _topLevelBuildReport;

}