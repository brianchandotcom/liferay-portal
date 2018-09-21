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
public class OtherPortalWorkspaceGitRepository
	extends BasePortalWorkspaceGitRepository {

	protected OtherPortalWorkspaceGitRepository(
		PortalWorkspaceGitRepository primaryPortalWorkspaceGitRepository) {

		super(
			_getGitHubURL(primaryPortalWorkspaceGitRepository),
			_getUpstreamBranchName(primaryPortalWorkspaceGitRepository), null);

		this.primaryPortalWorkspaceGitRepository =
			primaryPortalWorkspaceGitRepository;
	}

	protected final PortalWorkspaceGitRepository
		primaryPortalWorkspaceGitRepository;

	private static String _getGitHubURL(
		PortalWorkspaceGitRepository primaryPortalWorkspaceGitRepository) {

		String upstreamBranchName = _getUpstreamBranchName(
			primaryPortalWorkspaceGitRepository);

		StringBuilder sb = new StringBuilder();

		sb.append("https://github.com/liferay/liferay-portal");

		if (!upstreamBranchName.equals("master")) {
			sb.append("-ee");
		}

		sb.append("/tree/");
		sb.append(upstreamBranchName);

		return sb.toString();
	}

	private static String _getUpstreamBranchName(
		PortalWorkspaceGitRepository primaryPortalWorkspaceGitRepository) {

		String portalUpstreamBranchName =
			primaryPortalWorkspaceGitRepository.getUpstreamBranchName();

		if (portalUpstreamBranchName.contains("7.0.x")) {
			return portalUpstreamBranchName.replace("7.0.x", "master");
		}
		else if (portalUpstreamBranchName.contains("7.1.x")) {
			return portalUpstreamBranchName.replace("7.1.x", "7.0.x");
		}

		return portalUpstreamBranchName.replace("master", "7.0.x");
	}

}