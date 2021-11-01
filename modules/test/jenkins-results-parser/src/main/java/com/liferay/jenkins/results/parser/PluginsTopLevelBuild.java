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

import java.util.Objects;

/**
 * @author Michael Hashimoto
 */
public class PluginsTopLevelBuild
	extends DefaultTopLevelBuild
	implements PluginsBranchInformationBuild, PortalBranchInformationBuild,
			   PortalWorkspaceBuild {

	public PluginsTopLevelBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	@Override
	public String getBaseGitRepositoryName() {
		String repositoryName = "liferay-plugins";

		if (!Objects.equals(getBranchName(), "master")) {
			repositoryName += "-ee";
		}

		return repositoryName;
	}

	@Override
	public String getBranchName() {
		return getParameterValue("TEST_PLUGINS_BRANCH_NAME");
	}

	public String getPluginName() {
		return getParameterValue("TEST_PLUGIN_NAME");
	}

	@Override
	public BranchInformation getPluginsBranchInformation() {
		if (fromArchive) {
			return getBranchInformation("plugins");
		}

		PortalWorkspace portalWorkspace = getPortalWorkspace();

		if (portalWorkspace == null) {
			return null;
		}

		WorkspaceGitRepository workspaceGitRepository =
			portalWorkspace.getPluginsWorkspaceGitRepository();

		if (workspaceGitRepository == null) {
			return null;
		}

		return new WorkspaceBranchInformation(workspaceGitRepository);
	}

	@Override
	public BranchInformation getPortalBaseBranchInformation() {
		if (!fromArchive) {
			return null;
		}

		BranchInformation portalBranchInformation =
			getPortalBranchInformation();

		String upstreamBranchName =
			portalBranchInformation.getUpstreamBranchName();

		if (upstreamBranchName.contains("-private")) {
			return getBranchInformation("portal.base");
		}

		return null;
	}

	@Override
	public BranchInformation getPortalBranchInformation() {
		if (fromArchive) {
			return getBranchInformation("portal");
		}

		PortalWorkspace portalWorkspace = getPortalWorkspace();

		if (portalWorkspace == null) {
			return null;
		}

		WorkspaceGitRepository workspaceGitRepository =
			portalWorkspace.getPortalWorkspaceGitRepository();

		if (workspaceGitRepository == null) {
			return null;
		}

		return new WorkspaceBranchInformation(workspaceGitRepository);
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
	public String getTestSuiteName() {
		return getPluginName();
	}

	@Override
	public Workspace getWorkspace() {
		Workspace workspace = WorkspaceFactory.newWorkspace(
			getBaseGitRepositoryName(), getBranchName(), getJobName());

		if (!(workspace instanceof PortalWorkspace)) {
			return workspace;
		}

		PortalWorkspace portalWorkspace = (PortalWorkspace)workspace;

		portalWorkspace.setBuildProfile(getBuildProfile());

		String portalGitHubURL = _getPortalGitHubURL();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(portalGitHubURL)) {
			PortalWorkspaceGitRepository portalWorkspaceGitRepository =
				portalWorkspace.getPortalWorkspaceGitRepository();

			portalWorkspaceGitRepository.setGitHubURL(portalGitHubURL);
		}

		PluginsWorkspaceGitRepository pluginsWorkspaceGitRepository =
			portalWorkspace.getPluginsWorkspaceGitRepository();

		String pluginsGitHubURL = _getPluginsGitHubURL();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(pluginsGitHubURL)) {
			pluginsWorkspaceGitRepository.setGitHubURL(pluginsGitHubURL);
		}

		String pluginsBranchSHA = _getPluginsBranchSHA();

		if (JenkinsResultsParserUtil.isSHA(pluginsBranchSHA)) {
			pluginsWorkspaceGitRepository.setSenderBranchSHA(pluginsBranchSHA);
		}

		return workspace;
	}

	private String _getPluginsBranchSHA() {
		return getParameterValue("TEST_PLUGINS_GIT_ID");
	}

	private String _getPluginsGitHubURL() {
		String pluginsBranchName = getParameterValue(
			"TEST_PLUGINS_RELEASE_TAG");
		String pluginsBranchUsername = getParameterValue(
			"TEST_PLUGINS_BRANCH_USERNAME");

		if (JenkinsResultsParserUtil.isNullOrEmpty(pluginsBranchName) ||
			JenkinsResultsParserUtil.isNullOrEmpty(pluginsBranchUsername)) {

			return null;
		}

		return JenkinsResultsParserUtil.combine(
			"https://github.com/", pluginsBranchUsername, "/",
			getBaseGitRepositoryName(), "/tree/", pluginsBranchName);
	}

	private String _getPortalGitHubURL() {
		String portalBranchName = getParameterValue(
			"PORTAL_GITHUB_BRANCH_NAME");
		String portalBranchUsername = getParameterValue(
			"PORTAL_GITHUB_BRANCH_USERNAME");

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalBranchName) ||
			JenkinsResultsParserUtil.isNullOrEmpty(portalBranchUsername)) {

			return null;
		}

		String portalRepositoryName = "liferay-portal";

		if (!Objects.equals(getBranchName(), "master")) {
			portalRepositoryName += "-ee";
		}

		return JenkinsResultsParserUtil.combine(
			"https://github.com/", portalBranchUsername, "/",
			portalRepositoryName, "/tree/", portalBranchName);
	}

}