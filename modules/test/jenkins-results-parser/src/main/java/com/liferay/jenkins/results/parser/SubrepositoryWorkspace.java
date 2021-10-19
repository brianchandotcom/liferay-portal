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
public class SubrepositoryWorkspace extends PortalWorkspace {

	protected SubrepositoryWorkspace(JSONObject jsonObject) {
		super(jsonObject);
	}

	protected SubrepositoryWorkspace(
		String primaryRepositoryName, String upstreamBranchName) {

		super(primaryRepositoryName, upstreamBranchName);
	}

	protected SubrepositoryWorkspace(
		String primaryRepositoryName, String upstreamBranchName,
		String jobName) {

		super(primaryRepositoryName, upstreamBranchName, jobName);
	}

	@Override
	protected PortalWorkspaceGitRepository getPortalWorkspaceGitRepository() {
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

	@Override
	protected void updateOSBAsahModule() {
		WorkspaceGitRepository primaryWorkspaceGitRepository =
			getPrimaryWorkspaceGitRepository();

		String repositoryName = primaryWorkspaceGitRepository.getName();

		if (repositoryName.equals("com-liferay-osb-asah-private")) {
			copyOSBAsahRepositoryToModule();
		}
	}

}