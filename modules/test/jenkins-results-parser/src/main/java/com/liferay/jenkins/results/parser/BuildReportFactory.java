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

import com.liferay.jenkins.results.parser.testray.TestrayBuild;

import java.io.File;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BuildReportFactory {

	public static DownstreamBuildReport newDownstreamBuildReport(
		String batchName, JSONObject buildReportJSONObject,
		TopLevelBuildReport topLevelBuildReport) {

		if (!buildReportJSONObject.has("buildURL")) {
			return null;
		}

		if (batchName.startsWith("functional-")) {
			return new FunctionalDownstreamBuildReport(
				batchName, buildReportJSONObject, topLevelBuildReport);
		}

		return new DefaultDownstreamBuildReport(
			batchName, buildReportJSONObject, topLevelBuildReport);
	}

	public static TopLevelBuildReport newTopLevelBuildReport(
		File jenkinsConsoleFile) {

		if ((jenkinsConsoleFile == null) || !jenkinsConsoleFile.exists()) {
			return null;
		}

		return new FileTopLevelBuildReport(jenkinsConsoleFile);
	}

	public static TopLevelBuildReport newTopLevelBuildReport(
		JSONObject buildJSONObject, JobReport jobReport) {

		String buildURLString = JenkinsResultsParserUtil.getRemoteURL(
			buildJSONObject.getString("url"));

		if (!_topLevelBuildReports.containsKey(buildURLString)) {
			_topLevelBuildReports.put(
				buildURLString,
				new URLTopLevelBuildReport(buildJSONObject, jobReport));
		}

		return _topLevelBuildReports.get(buildURLString);
	}

	public static TopLevelBuildReport newTopLevelBuildReport(
		TestrayBuild testrayBuild) {

		URL topLevelBuildURL = testrayBuild.getTopLevelBuildURL();

		if (topLevelBuildURL == null) {
			return null;
		}

		String buildURLString = String.valueOf(topLevelBuildURL);

		if (!_topLevelBuildReports.containsKey(buildURLString)) {
			_topLevelBuildReports.put(
				buildURLString, new TestrayTopLevelBuildReport(testrayBuild));
		}

		return _topLevelBuildReports.get(buildURLString);
	}

	public static TopLevelBuildReport newTopLevelBuildReport(
		TopLevelBuild topLevelBuild) {

		String buildURLString = topLevelBuild.getBuildURL();

		if (!_topLevelBuildReports.containsKey(buildURLString)) {
			_topLevelBuildReports.put(
				buildURLString, new DefaultTopLevelBuildReport(topLevelBuild));
		}

		return _topLevelBuildReports.get(buildURLString);
	}

	public static TopLevelBuildReport newTopLevelBuildReport(URL buildURL) {
		String buildURLString = JenkinsResultsParserUtil.getRemoteURL(
			buildURL.toString());

		if (!_topLevelBuildReports.containsKey(buildURLString)) {
			_topLevelBuildReports.put(
				buildURLString, new URLTopLevelBuildReport(buildURL));
		}

		return _topLevelBuildReports.get(buildURLString);
	}

	private static final Map<String, TopLevelBuildReport>
		_topLevelBuildReports = new HashMap<>();

}