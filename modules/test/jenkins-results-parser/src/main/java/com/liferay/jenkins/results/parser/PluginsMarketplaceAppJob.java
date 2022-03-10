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

import java.io.File;

import java.util.Collections;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PluginsMarketplaceAppJob
	extends BaseJob implements PortalTestClassJob, TestSuiteJob {

	@Override
	public Set<String> getDistTypes() {
		return Collections.emptySet();
	}

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put("app_type", _appType);
		jsonObject.put(
			"portal_upstream_branch_name", _portalUpstreamBranchName);

		return jsonObject;
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		return _portalGitWorkingDirectory;
	}

	@Override
	public String getTestSuiteName() {
		return _appType;
	}

	protected PluginsMarketplaceAppJob(
		BuildProfile buildProfile, String jobName,
		String portalUpstreamBranchName) {

		super(buildProfile, jobName);

		String appType = System.getenv("TEST_APP_TYPE");

		if (JenkinsResultsParserUtil.isNullOrEmpty(appType)) {
			appType = "community";
		}

		_portalUpstreamBranchName = portalUpstreamBranchName;

		_appType = appType;

		_initialize();
	}

	protected PluginsMarketplaceAppJob(JSONObject jsonObject) {
		super(jsonObject);

		_appType = jsonObject.getString("app_type");
		_portalUpstreamBranchName = jsonObject.getString(
			"portal_upstream_branch_name");

		_initialize();
	}

	private void _initialize() {
		_portalGitWorkingDirectory =
			GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
				_portalUpstreamBranchName);

		jobPropertiesFiles.add(
			new File(
				_portalGitWorkingDirectory.getWorkingDirectory(),
				"test.properties"));
	}

	private final String _appType;
	private PortalGitWorkingDirectory _portalGitWorkingDirectory;
	private final String _portalUpstreamBranchName;

}