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

import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PluginsWorkspaceGitRepository extends BaseWorkspaceGitRepository {

	public String getPortalUpstreamBranchName() {
		return optString("portal_upstream_branch_name", "master");
	}

	public void setPortalUpstreamBranchName(String portalUpstreamBranchName) {
		put("portal_upstream_branch_name", portalUpstreamBranchName);
	}

	@Override
	public void writePropertiesFiles() {
		_writeBuildPropertiesFile();
	}

	protected PluginsWorkspaceGitRepository(JSONObject jsonObject) {
		super(jsonObject);
	}

	protected PluginsWorkspaceGitRepository(
		PullRequest pullRequest, String upstreamBranchName) {

		super(pullRequest, upstreamBranchName);
	}

	protected PluginsWorkspaceGitRepository(
		RemoteGitRef remoteGitRef, String upstreamBranchName) {

		super(remoteGitRef, upstreamBranchName);
	}

	@Override
	protected Set<String> getPropertyOptions() {
		Set<String> propertyOptions = new HashSet<>(super.getPropertyOptions());

		propertyOptions.add(getPortalUpstreamBranchName());

		return propertyOptions;
	}

	private void _writeBuildPropertiesFile() {
		JenkinsResultsParserUtil.writePropertiesFile(
			new File(
				getDirectory(),
				JenkinsResultsParserUtil.combine(
					"build.", System.getenv("HOSTNAME"), ".properties")),
			getProperties("plugins.build.properties"), true);
	}

}