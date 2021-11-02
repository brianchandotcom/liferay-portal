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
public class LegacyTopLevelBuild
	extends DefaultTopLevelBuild implements PortalWorkspaceBuild {

	public LegacyTopLevelBuild(String url) {
		super(url);
	}

	public LegacyTopLevelBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	@Override
	public String getBaseGitRepositoryName() {
		String branchName = getBranchName();

		if (!branchName.equals("master")) {
			return "liferay-portal-ee";
		}

		return "liferay-portal";
	}

	@Override
	public String getBranchName() {
		String portalUpstreamBranchName = getParameterValue(
			"PORTAL_UPSTREAM_BRANCH_NAME");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(portalUpstreamBranchName)) {
			return portalUpstreamBranchName;
		}

		return "master";
	}

	@Override
	public PortalWorkspace getPortalWorkspace() {
		Workspace workspace = getWorkspace();

		if (!(workspace instanceof PortalWorkspace)) {
			return null;
		}

		return (PortalWorkspace)workspace;
	}

	@Override
	public Workspace getWorkspace() {
		Workspace workspace = WorkspaceFactory.newWorkspace(
			getBaseGitRepositoryName(), getBranchName(), getJobName());

		if (!(workspace instanceof PortalWorkspace)) {
			return workspace;
		}

		PortalWorkspace portalWorkspace = (PortalWorkspace)workspace;

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			portalWorkspace.getPortalWorkspaceGitRepository();

		String portalGitHubURL = _getPortalGitHubURL();

		if ((portalWorkspaceGitRepository != null) &&
			!JenkinsResultsParserUtil.isNullOrEmpty(portalGitHubURL)) {

			portalWorkspaceGitRepository.setGitHubURL(portalGitHubURL);
		}

		WorkspaceGitRepository legacyWorkspaceGitRepository =
			portalWorkspace.getLegacyWorkspaceGitRepository();

		String legacyGitHubURL = _getLegacyGitHubURL();

		if ((legacyWorkspaceGitRepository != null) &&
			!JenkinsResultsParserUtil.isNullOrEmpty(legacyGitHubURL)) {

			legacyWorkspaceGitRepository.setGitHubURL(legacyGitHubURL);
		}

		return workspace;
	}

	private String _getLegacyGitHubURL() {
		String legacyGitHubBranchName = getParameterValue(
			"LEGACY_GITHUB_BRANCH_NAME");
		String legacyGitHubBranchUsername = getParameterValue(
			"LEGACY_GITHUB_BRANCH_USERNAME");

		if (JenkinsResultsParserUtil.isNullOrEmpty(legacyGitHubBranchName) ||
			JenkinsResultsParserUtil.isNullOrEmpty(
				legacyGitHubBranchUsername)) {

			return null;
		}

		return JenkinsResultsParserUtil.combine(
			"https://github.com/", legacyGitHubBranchUsername,
			"/liferay-qa-portal-legacy-ee/tree/", legacyGitHubBranchName);
	}

	private String _getPortalGitHubURL() {
		String portalGitHubBranchName = getParameterValue(
			"PORTAL_GITHUB_BRANCH_NAME");
		String portalGitHubBranchUsername = getParameterValue(
			"PORTAL_GITHUB_BRANCH_USERNAME");

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalGitHubBranchName) ||
			JenkinsResultsParserUtil.isNullOrEmpty(
				portalGitHubBranchUsername)) {

			return null;
		}

		return JenkinsResultsParserUtil.combine(
			"https://github.com/", portalGitHubBranchUsername,
			"/liferay-portal/tree/", portalGitHubBranchName);
	}

}