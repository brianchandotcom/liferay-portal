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

		WorkspaceGitRepository workspaceGitRepository =
			_workspace.getPrimaryWorkspaceGitRepository();

		workspaceGitRepository.addPropertyOption(
			String.valueOf(portalTopLevelBuildData.getBuildProfile()));
		workspaceGitRepository.addPropertyOption(
			portalTopLevelBuildData.getPortalUpstreamBranchName());

		workspaceGitRepository.setGitHubURL(
			portalTopLevelBuildData.getPortalGitHubURL());

		return _workspace;
	}

	protected PortalTopLevelBuildRunner(T portalTopLevelBuildData) {
		super(portalTopLevelBuildData);
	}

	private Workspace _workspace;

}