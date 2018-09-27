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
import java.io.IOException;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CompanionPortalWorkspaceGitRepository
	extends BasePortalWorkspaceGitRepository {

	@Override
	public String getType() {
		return _TYPE;
	}

	@Override
	public void setUp() {
		super.setUp();

		String parentUpstreamBranchName =
			_parentWorkspaceGitRepository.getUpstreamBranchName();

		if (parentUpstreamBranchName.contains("-private")) {
			AntUtil.callTarget(
				_parentWorkspaceGitRepository.getDirectory(),
				"build-working-dir.xml", "prepare-working-dir");

			return;
		}

		File modulesPrivateDir = new File(getDirectory(), "modules/private");

		if (!modulesPrivateDir.exists()) {
			return;
		}

		File parentModulesPrivateDir = new File(
			_parentWorkspaceGitRepository.getDirectory(), "modules/private");

		try {
			JenkinsResultsParserUtil.copy(
				modulesPrivateDir, parentModulesPrivateDir);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	protected CompanionPortalWorkspaceGitRepository(JSONObject jsonObject) {
		super(jsonObject);
	}

	protected CompanionPortalWorkspaceGitRepository(
		PullRequest pullRequest, String upstreamBranchName,
		WorkspaceGitRepository parentWorkspaceGitRepository) {

		super(pullRequest, upstreamBranchName);

		_parentWorkspaceGitRepository = parentWorkspaceGitRepository;
	}

	protected CompanionPortalWorkspaceGitRepository(
		RemoteGitRef remoteGitRef, String upstreamBranchName,
		WorkspaceGitRepository parentWorkspaceGitRepository) {

		super(remoteGitRef, upstreamBranchName);

		_parentWorkspaceGitRepository = parentWorkspaceGitRepository;
	}

	private static final String _TYPE = "portal.companion";

	private final WorkspaceGitRepository _parentWorkspaceGitRepository;

}