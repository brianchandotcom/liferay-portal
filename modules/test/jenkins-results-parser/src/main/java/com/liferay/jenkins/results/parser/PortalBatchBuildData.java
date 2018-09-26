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

	public static boolean isValidJSONObject(JSONObject jsonObject) {
		return isValidJSONObject(jsonObject, _TYPE);
	}

	@Override
	public String getPortalGitHubURL() {
		return getString("portal_github_url");
	}

	public PortalTopLevelBuildData getPortalTopLevelBuildData() {
		if (_portalTopLevelBuildData != null) {
			return _portalTopLevelBuildData;
		}

		TopLevelBuildData topLevelBuildData = getTopLevelBuildData();

		if (!(topLevelBuildData instanceof PortalTopLevelBuildData)) {
			return null;
		}

		_portalTopLevelBuildData = (PortalTopLevelBuildData)topLevelBuildData;

		return _portalTopLevelBuildData;
	}

	@Override
	public String getPortalUpstreamBranchName() {
		return getString("portal_upstream_branch_name");
	}

	protected PortalBatchBuildData(JSONObject jsonObject) {
		super(jsonObject);

		validateKeys(_REQUIRED_KEYS);
	}

	protected PortalBatchBuildData(Map<String, String> buildParameters) {
		super(buildParameters);

		_portalTopLevelBuildData =
			(PortalTopLevelBuildData)getTopLevelBuildData();

		put("portal_github_url", _getPortalGitHubURL(buildParameters));
		put(
			"portal_upstream_branch_name",
			_getPortalUpstreamBranchName(buildParameters));

		validateKeys(_REQUIRED_KEYS);
	}

	@Override
	protected String getType() {
		return _TYPE;
	}

	private String _getPortalGitHubURL(Map<String, String> buildParameters) {
		PortalTopLevelBuildData portalTopLevelBuildData =
			getPortalTopLevelBuildData();

		if (portalTopLevelBuildData != null) {
			return portalTopLevelBuildData.getPortalGitHubURL();
		}

		if (!buildParameters.containsKey("PORTAL_GITHUB_URL")) {
			throw new RuntimeException("Please set PORTAL_GITHUB_URL");
		}

		return buildParameters.get("PORTAL_GITHUB_URL");
	}

	private String _getPortalUpstreamBranchName(
		Map<String, String> buildParameters) {

		PortalTopLevelBuildData portalTopLevelBuildData =
			getPortalTopLevelBuildData();

		if (portalTopLevelBuildData != null) {
			return portalTopLevelBuildData.getPortalUpstreamBranchName();
		}

		if (!buildParameters.containsKey("PORTAL_UPSTREAM_BRANCH_NAME")) {
			throw new RuntimeException(
				"Please set PORTAL_UPSTREAM_BRANCH_NAME");
		}

		return buildParameters.get("PORTAL_UPSTREAM_BRANCH_NAME");
	}

	private static final String[] _REQUIRED_KEYS =
		{"portal_github_url", "portal_upstream_branch_name"};

	private static final String _TYPE = "portal_batch";

	private PortalTopLevelBuildData _portalTopLevelBuildData;

}