/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public abstract class PortalTopLevelBuildRunner
	<T extends PortalTopLevelBuildData>
		extends TopLevelBuildRunner<T> {

	@Override
	public Workspace getWorkspace() {
		if (_workspace != null) {
			return _workspace;
		}

		T portalTopLevelBuildData = getBuildData();

		_workspace = WorkspaceFactory.newWorkspace(
			portalTopLevelBuildData.getPortalGitHubRepositoryName(),
			portalTopLevelBuildData.getPortalUpstreamBranchName(),
			portalTopLevelBuildData.getTopLevelJobName());

		for (WorkspaceGitRepository workspaceGitRepository :
				_workspace.getWorkspaceGitRepositories()) {

			workspaceGitRepository.addPropertyOption(
				String.valueOf(portalTopLevelBuildData.getBuildProfile()));
			workspaceGitRepository.addPropertyOption(
				workspaceGitRepository.getUpstreamBranchName());

			String dockerEnabled = System.getenv("DOCKER_ENABLED");

			if ((dockerEnabled != null) && dockerEnabled.equals("true")) {
				workspaceGitRepository.addPropertyOption("docker");
			}

			if (JenkinsResultsParserUtil.isWindows()) {
				workspaceGitRepository.addPropertyOption("windows");
			}
			else {
				workspaceGitRepository.addPropertyOption("unix");
			}
		}

		WorkspaceGitRepository primaryWorkspaceGitRepository =
			_workspace.getPrimaryWorkspaceGitRepository();

		primaryWorkspaceGitRepository.setGitHubURL(
			portalTopLevelBuildData.getPortalGitHubURL());

		return _workspace;
	}

	protected PortalTopLevelBuildRunner(T portalTopLevelBuildData) {
		super(portalTopLevelBuildData);
	}

	private Workspace _workspace;

}