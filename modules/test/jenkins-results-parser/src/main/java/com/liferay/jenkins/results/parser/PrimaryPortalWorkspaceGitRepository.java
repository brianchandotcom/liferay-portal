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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PrimaryPortalWorkspaceGitRepository
	extends BasePortalWorkspaceGitRepository {

	public static final String TYPE = "portal";

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public void setUp() {
		super.setUp();

		String upstreamBranchName = getUpstreamBranchName();

		if (!upstreamBranchName.startsWith("ee-") &&
			!upstreamBranchName.endsWith("-private")) {

			_setupProfileDXP();
		}
	}

	protected PrimaryPortalWorkspaceGitRepository(JSONObject jsonObject) {
		super(jsonObject);
	}

	protected PrimaryPortalWorkspaceGitRepository(
		PullRequest pullRequest, String upstreamBranchName) {

		super(pullRequest, upstreamBranchName);
	}

	protected PrimaryPortalWorkspaceGitRepository(
		RemoteGitRef remoteGitRef, String upstreamBranchName) {

		super(remoteGitRef, upstreamBranchName);
	}

	private void _setupProfileDXP() {
		try {
			AntUtil.callTarget(
				getDirectory(), "build.xml", "setup-profile-dxp");
		}
		catch (AntException antException) {
			throw new RuntimeException(
				"Unable to set up DXP profile", antException);
		}
	}

}