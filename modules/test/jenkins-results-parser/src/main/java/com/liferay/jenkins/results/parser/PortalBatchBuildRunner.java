/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

/**
 * @author Michael Hashimoto
 */
public abstract class PortalBatchBuildRunner<T extends PortalBatchBuildData>
	extends BatchBuildRunner<T> {

	@Override
	public Workspace getWorkspace() {
		if (_workspace != null) {
			return _workspace;
		}

		PortalBatchBuildData portalBatchBuildData = getBuildData();

		_workspace = WorkspaceFactory.newWorkspace(
			portalBatchBuildData.getPortalGitHubRepositoryName(),
			portalBatchBuildData.getPortalUpstreamBranchName());

		for (WorkspaceGitRepository workspaceGitRepository :
				_workspace.getWorkspaceGitRepositories()) {

			workspaceGitRepository.addPropertyOption(
				portalBatchBuildData.getBatchName());
			workspaceGitRepository.addPropertyOption(
				String.valueOf(portalBatchBuildData.getBuildProfile()));
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

		primaryWorkspaceGitRepository.setSenderBranchSHA(
			portalBatchBuildData.getPortalBranchSHA());

		return _workspace;
	}

	@Override
	public void run() {
		updateBuildDescription();

		setUpWorkspace();

		runTestBatch();

		publishArtifacts();

		updateBuildDescription();
	}

	protected PortalBatchBuildRunner(T portalBatchBuildData) {
		super(portalBatchBuildData);
	}

	protected void publishArtifacts() {
		PortalBatchBuildData portalBatchBuildData = getBuildData();

		File artifactDir = portalBatchBuildData.getArtifactDir();

		if (artifactDir.exists()) {
			publishToUserContentDir(artifactDir);
		}
	}

	protected void runTestBatch() {
		TestBatch testBatch = TestBatchFactory.newTestBatch(
			getBuildData(), getWorkspace());

		testBatch.run();
	}

	private Workspace _workspace;

}