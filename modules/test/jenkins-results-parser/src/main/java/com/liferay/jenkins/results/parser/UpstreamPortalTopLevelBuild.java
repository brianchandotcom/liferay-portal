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

import java.io.IOException;

import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class UpstreamPortalTopLevelBuild
	extends PortalTopLevelBuild implements PortalWorkspaceBuild {

	public UpstreamPortalTopLevelBuild(
		String url, TopLevelBuild topLevelBuild) {

		super(url, topLevelBuild);
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

		if (workspace instanceof PortalWorkspace) {
			PortalWorkspace portalWorkspace = (PortalWorkspace)workspace;

			portalWorkspace.setBuildProfile(getBuildProfile());
			portalWorkspace.setOSBAsahGitHubURL(_getOSBAsahGitHubURL());
			portalWorkspace.setOSBFaroGitHubURL(_getOSBFaroGitHubURL());
			portalWorkspace.setPortalPrivateGitHubURL(
				_getPortalPrivateGitHubURL());
		}

		WorkspaceGitRepository workspaceGitRepository =
			workspace.getPrimaryWorkspaceGitRepository();

		String portalGitHubURL = _getPortalGitHubURL();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(portalGitHubURL)) {
			workspaceGitRepository.setGitHubURL(portalGitHubURL);
		}

		String portalGitCommit = _getPortalGitCommit();

		if (JenkinsResultsParserUtil.isSHA(portalGitCommit)) {
			workspaceGitRepository.setSenderBranchSHA(portalGitCommit);
		}

		return workspace;
	}

	private String _getOSBAsahGitHubURL() {
		String osbAsahGitHubURL = getParameterValue("OSB_ASAH_GITHUB_URL");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(osbAsahGitHubURL)) {
			return osbAsahGitHubURL;
		}

		Build controllerBuild = getControllerBuild();

		if (controllerBuild != null) {
			return controllerBuild.getParameterValue("OSB_ASAH_GITHUB_URL");
		}

		return null;
	}

	private String _getOSBFaroGitHubURL() {
		String osbFaroGitHubURL = getParameterValue("OSB_FARO_GITHUB_URL");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(osbFaroGitHubURL)) {
			return osbFaroGitHubURL;
		}

		Build controllerBuild = getControllerBuild();

		if (controllerBuild != null) {
			return controllerBuild.getParameterValue("OSB_FARO_GITHUB_URL");
		}

		return null;
	}

	private String _getPortalGitCommit() {
		String portalGitCommit = getParameterValue("PORTAL_GIT_COMMIT");

		if (JenkinsResultsParserUtil.isSHA(portalGitCommit)) {
			return portalGitCommit;
		}

		Build controllerBuild = getControllerBuild();

		if (controllerBuild != null) {
			portalGitCommit = controllerBuild.getParameterValue(
				"PORTAL_GIT_COMMIT");

			if (JenkinsResultsParserUtil.isSHA(portalGitCommit)) {
				return portalGitCommit;
			}
		}

		String portalBundlesDistURL = getParameterValue(
			"PORTAL_BUNDLES_DIST_URL");

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalBundlesDistURL)) {
			return null;
		}

		try {
			URL portalBundlesGitHashURL = new URL(
				JenkinsResultsParserUtil.getLocalURL(portalBundlesDistURL) +
					"/git-hash");

			if (!JenkinsResultsParserUtil.exists(portalBundlesGitHashURL)) {
				return null;
			}

			String portalBundlesGitHash = JenkinsResultsParserUtil.toString(
				portalBundlesGitHashURL.toString());

			portalBundlesGitHash = portalBundlesGitHash.trim();

			if (JenkinsResultsParserUtil.isSHA(portalBundlesGitHash)) {
				return portalBundlesGitHash;
			}

			return null;
		}
		catch (IOException ioException) {
			return null;
		}
	}

	private String _getPortalGitHubURL() {
		String gitHubURL = getParameterValue("PORTAL_GITHUB_URL");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(gitHubURL)) {
			return gitHubURL;
		}

		Build controllerBuild = getControllerBuild();

		if (controllerBuild != null) {
			gitHubURL = controllerBuild.getParameterValue("PORTAL_GITHUB_URL");

			if (!JenkinsResultsParserUtil.isNullOrEmpty(gitHubURL)) {
				return gitHubURL;
			}
		}

		String gitHubBranchName = getParameterValue(
			"PORTAL_GITHUB_BRANCH_NAME");
		String gitHubBranchUsername = getParameterValue(
			"PORTAL_GITHUB_BRANCH_USERNAME");

		if (JenkinsResultsParserUtil.isNullOrEmpty(gitHubBranchName) ||
			JenkinsResultsParserUtil.isNullOrEmpty(gitHubBranchUsername)) {

			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("https://github.com/");
		sb.append(gitHubBranchUsername);
		sb.append("/liferay-portal");

		String branchName = getBranchName();

		if (!branchName.equals("master")) {
			sb.append("-ee");
		}

		sb.append("/tree/");
		sb.append(gitHubBranchName);

		return sb.toString();
	}

	private String _getPortalPrivateGitHubURL() {
		String branchName = getBranchName();

		if (branchName.startsWith("ee-") || branchName.endsWith("-private")) {
			return null;
		}

		String portalGitHubURL = _getPortalGitHubURL();

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalGitHubURL)) {
			return null;
		}

		Matcher matcher = _pattern.matcher(portalGitHubURL);

		if (!matcher.find()) {
			return null;
		}

		return JenkinsResultsParserUtil.combine(
			"https://github.com/", matcher.group("username"),
			"/liferay-portal-ee/tree/", branchName, "-private");
	}

	private static final Pattern _pattern = Pattern.compile(
		"https://github.com/(?<username>[^/]+)/(?<repositoryName>[^/]+)/tree" +
			"/(?<branchName>[^/]+)");

}