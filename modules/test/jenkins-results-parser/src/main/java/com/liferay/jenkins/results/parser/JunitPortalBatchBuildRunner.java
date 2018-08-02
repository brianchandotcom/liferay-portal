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

	protected JunitPortalBatchBuildRunner(
		Job job, String batchName, String htmlURL) {

		super(job, batchName, htmlURL);

		_setPortalBuildProperties();
	}

	private void _setPortalBuildProperties() {
		PortalLocalGitBranch portalLocalGitBranch = getPortalLocalGitBranch();

		LocalRepository localRepository =
			portalLocalGitBranch.getLocalRepository();
	
		String portalUpstreamBranchName =
			localRepository.getUpstreamBranchName();

		if (portalUpstreamBranchName.contains("7.0.x") ||
			portalUpstreamBranchName.contains("7.1.x") ||
			portalUpstreamBranchName.contains("master")) {

			String otherPortalBranchName = null;

			if (portalUpstreamBranchName.contains("7.0.x")) {
				otherPortalBranchName = portalUpstreamBranchName.replace(
					"7.0.x", "master");
			}
			else if (portalUpstreamBranchName.contains("7.1.x")) {
				otherPortalBranchName = portalUpstreamBranchName.replace(
					"7.1.x", "7.0.x");
			}
			else {
				otherPortalBranchName = portalUpstreamBranchName.replace(
					"master", "7.0.x");
			}

			String otherPortalRepositoryName = "liferay-portal";

			if (!otherPortalBranchName.equals("master")) {
				otherPortalRepositoryName += "-ee";
			}

			LocalRepository otherPortalLocalRepository =
				RepositoryFactory.getLocalRepository(
					otherPortalRepositoryName, otherPortalBranchName);

			File otherPortalRepositoryDir =
				otherPortalLocalRepository.getDirectory();

			Properties properties = new Properties();

			properties.put(
				"release.versions.test.other.dir",
				otherPortalRepositoryDir.toString());

			portalLocalRepository.setBuildProperties(properties);
		}
	}

}