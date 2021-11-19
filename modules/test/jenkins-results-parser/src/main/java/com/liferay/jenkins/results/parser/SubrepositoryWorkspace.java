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

	@Override
	public PortalWorkspaceGitRepository getPortalWorkspaceGitRepository() {
		if (_portalUpstreamBranchName == null) {
			WorkspaceGitRepository workspaceGitRepository =
				getPrimaryWorkspaceGitRepository();

			_portalUpstreamBranchName =
				workspaceGitRepository.getUpstreamBranchName();
		}

		String repositoryName = "liferay-portal";

		if (!_portalUpstreamBranchName.equals("master")) {
			repositoryName += "-ee";
		}

		String directoryName = JenkinsResultsParserUtil.getGitDirectoryName(
			repositoryName, _portalUpstreamBranchName);

		WorkspaceGitRepository portalWorkspaceGitRepository =
			getWorkspaceGitRepository(directoryName);

		if (!(portalWorkspaceGitRepository instanceof
				PortalWorkspaceGitRepository)) {

			throw new RuntimeException(
				"The portal workspace Git repository is not set");
		}

		return (PortalWorkspaceGitRepository)portalWorkspaceGitRepository;
	}

	public void setPortalUpstreamBranchName(String portalUpstreamBranchName) {
		_portalUpstreamBranchName = portalUpstreamBranchName;
	}

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
	protected void updateOSBAsahModule() {
		WorkspaceGitRepository primaryWorkspaceGitRepository =
			getPrimaryWorkspaceGitRepository();

		String repositoryName = primaryWorkspaceGitRepository.getName();

		if (repositoryName.equals("com-liferay-osb-asah-private")) {
			copyOSBAsahRepositoryToModule();
		}
	}

	private String _portalUpstreamBranchName;

}