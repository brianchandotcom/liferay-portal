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

import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class PortalTopLevelBuildData
	extends TopLevelBuildData implements PortalBuildData {

	@Override
	public String getPortalGitHubURL() {
		return getString("portal_github_url");
	}

	@Override
	public String getPortalUpstreamBranchName() {
		return getString("portal_upstream_branch_name");
	}

	protected PortalTopLevelBuildData(
		Map<String, String> buildParameters,
		JenkinsJSONObject jenkinsJSONObject, String runID) {

		super(buildParameters, jenkinsJSONObject, runID);

		if (!has("portal_github_url")) {
			put("portal_github_url", _getPortalGitHubURL(buildParameters));
		}

		if (!has("portal_upstream_branch_name")) {
			put(
				"portal_upstream_branch_name",
				_getPortalUpstreamBranchName(buildParameters));
		}
	}

	private String _getPortalGitHubURL(Map<String, String> buildParameters) {
		if (!buildParameters.containsKey("PORTAL_GITHUB_URL")) {
			throw new RuntimeException("Please set PORTAL_GITHUB_URL");
		}

		return buildParameters.get("PORTAL_GITHUB_URL");
	}

	private String _getPortalUpstreamBranchName(
		Map<String, String> buildParameters) {

		if (!buildParameters.containsKey("PORTAL_UPSTREAM_BRANCH_NAME")) {
			throw new RuntimeException(
				"Please set PORTAL_UPSTREAM_BRANCH_NAME");
		}

		return buildParameters.get("PORTAL_UPSTREAM_BRANCH_NAME");
	}

}