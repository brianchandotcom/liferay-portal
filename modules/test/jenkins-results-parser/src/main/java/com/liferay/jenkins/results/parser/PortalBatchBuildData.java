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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PortalBatchBuildData
	extends BatchBuildData implements PortalBuildData {

	@Override
	public String getPortalGitHubURL() {
		return _portalGitHubURL;
	}

	@Override
	public String getPortalUpstreamBranchName() {
		return _portalUpstreamBranchName;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = super.toJSONObject();

		jsonObject.put("portal_github_url", getPortalGitHubURL());
		jsonObject.put(
			"portal_upstream_branch_name", getPortalUpstreamBranchName());

		return jsonObject;
	}

	protected PortalBatchBuildData(
		BuildDataJSONObject buildDataJSONObject,
		Map<String, String> buildParameters, String runID) {

		super(buildDataJSONObject, buildParameters, runID);

		if (buildDataJSONObject != null) {
			if (buildDataJSONObject.has(runID)) {
				JSONObject jsonObject = buildDataJSONObject.getJSONObject(
					runID);

				if (jsonObject.has("portal_github_url")) {
					_portalGitHubURL = jsonObject.getString(
						"portal_github_url");
					_portalUpstreamBranchName = jsonObject.getString(
						"portal_upstream_branch_name");

					return;
				}
			}

			if (buildDataJSONObject.has(TOP_LEVEL_RUN_ID)) {
				TopLevelBuildData topLevelBuildData =
					BuildDataFactory.newTopLevelBuildData(
						buildDataJSONObject, buildParameters);

				PortalTopLevelBuildData portalTopLevelBuildData =
					(PortalTopLevelBuildData)topLevelBuildData;

				_portalGitHubURL = portalTopLevelBuildData.getPortalGitHubURL();
				_portalUpstreamBranchName =
					portalTopLevelBuildData.getPortalUpstreamBranchName();

				addBuildData();

				return;
			}
		}

		if (!buildParameters.containsKey("PORTAL_GITHUB_URL")) {
			throw new RuntimeException("Please set PORTAL_GITHUB_URL");
		}

		_portalGitHubURL = buildParameters.get("PORTAL_GITHUB_URL");

		if (!buildParameters.containsKey("PORTAL_UPSTREAM_BRANCH_NAME")) {
			throw new RuntimeException(
				"Please set PORTAL_UPSTREAM_BRANCH_NAME");
		}

		_portalUpstreamBranchName = buildParameters.get(
			"PORTAL_UPSTREAM_BRANCH_NAME");

		addBuildData();
	}

	private final String _portalGitHubURL;
	private final String _portalUpstreamBranchName;

}