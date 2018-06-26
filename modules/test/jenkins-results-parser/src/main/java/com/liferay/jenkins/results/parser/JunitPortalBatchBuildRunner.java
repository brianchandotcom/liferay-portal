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

import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class JunitPortalBatchBuildRunner extends PortalBatchBuildRunner {

	protected JunitPortalBatchBuildRunner(Job job, String batchName) {
		super(job, batchName);

		_setPortalBuildProperties();
	}

	private void _setPortalBuildProperties() {
		Properties properties = new Properties();

		String portalBranchName =
			portalGitWorkingDirectory.getUpstreamBranchName();

		if (portalBranchName.contains("7.0.x") ||
			portalBranchName.contains("7.1.x") ||
			portalBranchName.contains("master")) {

			String otherPortalBranchName = null;

			if (portalBranchName.contains("7.0.x")) {
				otherPortalBranchName = portalBranchName.replace(
					"7.0.x", "master");
			}
			else if (portalBranchName.contains("7.1.x")) {
				otherPortalBranchName = portalBranchName.replace(
					"7.1.x", "7.0.x");
			}
			else if (portalBranchName.contains("master")) {
				otherPortalBranchName = portalBranchName.replace(
					"master", "7.0.x");
			}
			else {
				throw new RuntimeException(
					"Invalid portal branch name " + portalBranchName);
			}

			Workspace otherPortalWorkspace = WorkspaceFactory.newWorkspace(
				"liferay-portal", otherPortalBranchName);

			File otherPortalRepositoryDir =
				otherPortalWorkspace.getRepositoryDir();

			properties.put(
				"release.versions.test.other.dir",
				otherPortalRepositoryDir.toString());
		}

		portalWorkspace.setBuildProperties(properties);
	}

}