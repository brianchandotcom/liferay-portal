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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PortalWorkspace extends BaseWorkspace {

	public String getPortalBuildProfile() {
		return jsonObject.optString("portal_build_profile", "dxp");
	}

	public void setOSBAsahGitHubURL(String osbAsahGitHubURL) {
		_osbAsahGitHubURL = osbAsahGitHubURL;
	}

	public void setOSBFaroGitHubURL(String osbFaroGitHubURL) {
		_osbFaroGitHubURL = osbFaroGitHubURL;
	}

	public void setPortalBuildProfile(String portalBuildProfile) {
		portalBuildProfile = portalBuildProfile.toLowerCase();

		if (!portalBuildProfile.equals("dxp") &&
			!portalBuildProfile.equals("portal")) {

			throw new RuntimeException(
				"Invalid portal build profile " + portalBuildProfile);
		}

		jsonObject.put("portal_build_profile", portalBuildProfile);
	}

	@Override
	public void setUp() {
		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_getPortalWorkspaceGitRepository();

		portalWorkspaceGitRepository.setUp();

		String portalBuildProfile = getPortalBuildProfile();

		if (portalBuildProfile.equals("dxp")) {
			portalWorkspaceGitRepository.setUpPortalProfile();
		}

		portalWorkspaceGitRepository.setUpTCKHome();

		_updateBladeSamplesWorkspaceGitRepository();
		_updateOSBAsahWorkspaceGitRepository();
		_updateOSBFaroWorkspaceGitRepository();
		_updatePluginsWorkspaceGitRepository();
		_updatePortalPrivateWorkspaceGitRepository();
		_updatePortalsPlutoWorkspaceGitRepository();
		_updateReleaseToolWorkspaceGitRepository();

		super.setUp();
	}

	protected PortalWorkspace(JSONObject jsonObject) {
		super(jsonObject);
	}

	protected PortalWorkspace(
		String primaryRepositoryName, String upstreamBranchName) {

		super(primaryRepositoryName, upstreamBranchName);
	}

	private PluginsWorkspaceGitRepository _getPluginsWorkspaceGitRepository() {
		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_getPortalWorkspaceGitRepository();

		if (portalWorkspaceGitRepository == null) {
			return null;
		}

		WorkspaceGitRepository workspaceGitRepository =
			getWorkspaceGitRepository(
				portalWorkspaceGitRepository.getPluginsRepositoryDirName());

		if (!(workspaceGitRepository instanceof
				PluginsWorkspaceGitRepository)) {

			return null;
		}

		return (PluginsWorkspaceGitRepository)workspaceGitRepository;
	}

	private PortalWorkspaceGitRepository _getPortalWorkspaceGitRepository() {
		WorkspaceGitRepository workspaceGitRepository =
			getPrimaryWorkspaceGitRepository();

		if (!(workspaceGitRepository instanceof PortalWorkspaceGitRepository)) {
			return null;
		}

		return (PortalWorkspaceGitRepository)workspaceGitRepository;
	}

	private ReleaseToolWorkspaceGitRepository
		_getReleaseToolWorkspaceGitRepository() {

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_getPortalWorkspaceGitRepository();

		if (portalWorkspaceGitRepository == null) {
			return null;
		}

		WorkspaceGitRepository workspaceGitRepository =
			getWorkspaceGitRepository("liferay-release-tool-ee");

		if (!(workspaceGitRepository instanceof
				ReleaseToolWorkspaceGitRepository)) {

			return null;
		}

		return (ReleaseToolWorkspaceGitRepository)workspaceGitRepository;
	}

	private void _updateBladeSamplesWorkspaceGitRepository() {
		_updateWorkspaceGitRepository(
			"git-commit-blade-samples", "liferay-blade-samples");
	}

	private void _updateOSBAsahWorkspaceGitRepository() {
		boolean updated = _updateWorkspaceGitRepository(
			"modules/dxp/apps/osb/osb-asah/ci-merge",
			"com-liferay-osb-asah-private");

		if (updated || (_osbAsahGitHubURL == null)) {
			return;
		}

		WorkspaceGitRepository workspaceGitRepository =
			getWorkspaceGitRepository("com-liferay-osb-asah-private");

		if (workspaceGitRepository == null) {
			return;
		}

		workspaceGitRepository.setGitHubURL(_osbAsahGitHubURL);
	}

	private void _updateOSBFaroWorkspaceGitRepository() {
		if (_osbFaroGitHubURL == null) {
			return;
		}

		WorkspaceGitRepository workspaceGitRepository =
			getWorkspaceGitRepository("com-liferay-osb-faro-private");

		if (workspaceGitRepository == null) {
			return;
		}

		workspaceGitRepository.setGitHubURL(_osbFaroGitHubURL);
	}

	private void _updatePluginsWorkspaceGitRepository() {
		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_getPortalWorkspaceGitRepository();

		if (portalWorkspaceGitRepository == null) {
			return;
		}

		_updateWorkspaceGitRepository(
			"git-commit-plugins",
			portalWorkspaceGitRepository.getPluginsRepositoryDirName());

		PluginsWorkspaceGitRepository pluginsWorkspaceGitRepository =
			_getPluginsWorkspaceGitRepository();

		if (pluginsWorkspaceGitRepository == null) {
			return;
		}

		pluginsWorkspaceGitRepository.setPortalUpstreamBranchName(
			portalWorkspaceGitRepository.getUpstreamBranchName());
	}

	private void _updatePortalPrivateWorkspaceGitRepository() {
		WorkspaceGitRepository primaryWorkspaceGitRepository =
			getPrimaryWorkspaceGitRepository();

		if (!(primaryWorkspaceGitRepository instanceof
				PortalWorkspaceGitRepository)) {

			return;
		}

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			(PortalWorkspaceGitRepository)primaryWorkspaceGitRepository;

		_updateWorkspaceGitRepository(
			"git-commit-portal-private",
			portalWorkspaceGitRepository.getPortalPrivateRepositoryDirName());
	}

	private void _updatePortalsPlutoWorkspaceGitRepository() {
		_updateWorkspaceGitRepository(
			"git-commit-portals-pluto", "portals-pluto");
	}

	private void _updateReleaseToolWorkspaceGitRepository() {
		_updateWorkspaceGitRepository(
			"git-commit/liferay-release-tool-ee", "liferay-release-tool-ee");

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_getPortalWorkspaceGitRepository();

		if (portalWorkspaceGitRepository == null) {
			return;
		}

		ReleaseToolWorkspaceGitRepository releaseToolWorkspaceGitRepository =
			_getReleaseToolWorkspaceGitRepository();

		if (releaseToolWorkspaceGitRepository == null) {
			return;
		}

		releaseToolWorkspaceGitRepository.setPortalUpstreamBranchName(
			portalWorkspaceGitRepository.getUpstreamBranchName());
	}

	private boolean _updateWorkspaceGitRepository(
		String gitCommitFilePath, String gitRepositoryName) {

		WorkspaceGitRepository workspaceGitRepository =
			getWorkspaceGitRepository(gitRepositoryName);

		if (workspaceGitRepository == null) {
			return false;
		}

		WorkspaceGitRepository primaryWorkspaceGitRepository =
			getPrimaryWorkspaceGitRepository();

		String gitCommit = primaryWorkspaceGitRepository.getFileContent(
			gitCommitFilePath);

		if (JenkinsResultsParserUtil.isNullOrEmpty(gitCommit)) {
			return false;
		}

		if (JenkinsResultsParserUtil.isSHA(gitCommit)) {
			workspaceGitRepository.setSenderBranchSHA(gitCommit);

			return true;
		}

		if (!GitUtil.isValidGitHubRefURL(gitCommit) &&
			!PullRequest.isValidGitHubPullRequestURL(gitCommit)) {

			return false;
		}

		workspaceGitRepository.setGitHubURL(gitCommit);

		return true;
	}

	private String _osbAsahGitHubURL;
	private String _osbFaroGitHubURL;

}