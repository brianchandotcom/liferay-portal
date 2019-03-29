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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PortalTestSuiteUpstreamControllerBuildRunner
	<S extends PortalTopLevelBuildData>
		extends BaseBuildRunner<S, Workspace> {

	@Override
	public void run() {
		S buildData = getBuildData();

		String previousPortalBranchSHA = _getPreviousPortalBranchSHA();

		if (!previousPortalBranchSHA.equals(buildData.getPortalBranchSHA())) {
			updateBuildDescription();

			keepLogs(true);
		}
	}

	@Override
	public void tearDown() {
	}

	protected PortalTestSuiteUpstreamControllerBuildRunner(S buildData) {
		super(buildData);
	}

	@Override
	protected void initWorkspace() {
		setWorkspace(WorkspaceFactory.newSimpleWorkspace());
	}

	@Override
	protected void updateBuildDescription() {
		S buildData = getBuildData();

		buildData.setBuildDescription(
			JenkinsResultsParserUtil.combine(
				"<strong>GIT ID</strong> - ", buildData.getPortalBranchSHA()));

		super.updateBuildDescription();
	}

	private String _getPreviousPortalBranchSHA() {
		BuildData buildData = getBuildData();

		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(buildData.getJobURL()) +
					"api/json");

			JSONArray buildsJSONArray = jsonObject.getJSONArray("builds");

			for (int i = 0; i < buildsJSONArray.length(); i++) {
				JSONObject buildJSONObject = buildsJSONArray.getJSONObject(i);

				JSONObject previousBuildJSONObject =
					JenkinsResultsParserUtil.toJSONObject(
						JenkinsResultsParserUtil.getLocalURL(
							buildJSONObject.getString("url") + "api/json"));

				String description = previousBuildJSONObject.optString(
					"description", "");

				Matcher matcher = _pattern.matcher(description);

				if (!matcher.find()) {
					continue;
				}

				return matcher.group("sha");
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		return "";
	}

	private static final Pattern _pattern = Pattern.compile(
		"<strong>GIT ID<\\/strong> - (?<sha>[0-9a-f]{7,40})");

}