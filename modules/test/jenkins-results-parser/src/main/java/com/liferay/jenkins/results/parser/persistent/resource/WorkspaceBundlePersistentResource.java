/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.persistent.resource;

import com.liferay.jenkins.results.parser.BuildDatabase;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.Workspace;
import com.liferay.jenkins.results.parser.WorkspaceGitRepository;

import java.io.File;

import java.util.Collections;
import java.util.Set;

/**
 * @author Brittney Nguyen
 */
public class WorkspaceBundlePersistentResource
	extends BaseBundlePersistentResource {

	@Override
	public Type getType() {
		return Type.WORKSPACE_BUNDLE;
	}

	protected WorkspaceBundlePersistentResource(
		BuildDatabase buildDatabase, TopLevelBuild topLevelBuild,
		String workspaceName) {

		super(buildDatabase, topLevelBuild);

		if (JenkinsResultsParserUtil.isNullOrEmpty(workspaceName)) {
			throw new IllegalArgumentException(
				"Workspace name is null or empty");
		}

		_workspaceName = workspaceName;
	}

	@Override
	protected Set<String> getArtifactNames() {
		return Collections.singleton(
			"liferay-docker-" + _workspaceName + ".tar");
	}

	@Override
	protected String getAxisVariable() {
		return getType() + "-" + _workspaceName;
	}

	@Override
	protected WorkspaceGitRepository getBundleWorkspaceGitRepository() {
		Workspace workspace = getWorkspace();

		WorkspaceGitRepository workspaceGitRepository =
			workspace.getWorkspaceGitRepository(
				"liferay-portal-master-private");

		if (workspaceGitRepository != null) {
			File workspaceDir = new File(
				workspaceGitRepository.getDirectory(),
				"workspaces/" + _workspaceName);

			if (workspaceDir.isDirectory()) {
				return workspaceGitRepository;
			}
		}

		return workspace.getPrimaryWorkspaceGitRepository();
	}

	@Override
	protected String getS3ObjectPathName() {
		return getType() + "/" + _workspaceName;
	}

	private final String _workspaceName;

}