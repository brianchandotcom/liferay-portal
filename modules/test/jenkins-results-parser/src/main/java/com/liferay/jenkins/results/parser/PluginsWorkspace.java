/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PluginsWorkspace extends PortalWorkspace {

	@Override
	public PluginsWorkspaceGitRepository getPluginsWorkspaceGitRepository() {
		WorkspaceGitRepository workspaceGitRepository =
			getPrimaryWorkspaceGitRepository();

		if (!(workspaceGitRepository instanceof
				PluginsWorkspaceGitRepository)) {

			return null;
		}

		return (PluginsWorkspaceGitRepository)workspaceGitRepository;
	}

	@Override
	public PortalWorkspaceGitRepository getPortalWorkspaceGitRepository() {
		WorkspaceGitRepository workspaceGitRepository =
			getPrimaryWorkspaceGitRepository();

		String upstreamBranchName =
			workspaceGitRepository.getUpstreamBranchName();

		String repositoryName = "liferay-portal";

		if (!upstreamBranchName.equals("master")) {
			repositoryName += "-ee";
		}

		String directoryName = JenkinsResultsParserUtil.getGitDirectoryName(
			repositoryName, upstreamBranchName);

		WorkspaceGitRepository portalWorkspaceGitRepository =
			getWorkspaceGitRepository(directoryName);

		if (!(portalWorkspaceGitRepository instanceof
				PortalWorkspaceGitRepository)) {

			throw new RuntimeException(
				"The portal workspace Git repository is not set");
		}

		return (PortalWorkspaceGitRepository)portalWorkspaceGitRepository;
	}

	protected PluginsWorkspace(JSONObject jsonObject) {
		super(jsonObject);
	}

	protected PluginsWorkspace(
		String primaryRepositoryName, String upstreamBranchName) {

		super(primaryRepositoryName, upstreamBranchName);
	}

	protected PluginsWorkspace(
		String primaryRepositoryName, String upstreamBranchName,
		String jobName) {

		super(primaryRepositoryName, upstreamBranchName, jobName);
	}

}