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
import java.io.IOException;

import java.nio.file.PathMatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		_configureBladeSamplesWorkspaceGitRepository();
		_configureLiferayFacesAlloyWorkspaceGitRepository();
		_configureLiferayFacesBridgeImplWorkspaceGitRepository();
		_configureLiferayFacesPortalWorkspaceGitRepository();
		_configureLiferayFacesShowcaseWorkspaceGitRepository();
		_configureOSBAsahWorkspaceGitRepository();
		_configureOSBFaroWorkspaceGitRepository();
		_configurePluginsWorkspaceGitRepository();
		_configurePortalPrivateWorkspaceGitRepository();
		_configurePortalsPlutoWorkspaceGitRepository();
		_configureReleaseToolWorkspaceGitRepository();

		super.setUp();

		_setUpOSBAsahWorkspaceGitRepository();
	}

	protected PortalWorkspace(JSONObject jsonObject) {
		super(jsonObject);
	}

	protected PortalWorkspace(
		String primaryRepositoryName, String upstreamBranchName) {

		super(primaryRepositoryName, upstreamBranchName);
	}

	protected PortalWorkspace(
		String primaryRepositoryName, String upstreamBranchName,
		String jobName) {

		super(primaryRepositoryName, upstreamBranchName, jobName);
	}

	private void _configureBladeSamplesWorkspaceGitRepository() {
		_updateWorkspaceGitRepository(
			"git-commit-blade-samples", "liferay-blade-samples");
	}

	private void _configureLiferayFacesAlloyWorkspaceGitRepository() {
		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_getPortalWorkspaceGitRepository();

		String gitHubURL =
			portalWorkspaceGitRepository.getLiferayFacesAlloyURL();

		if (JenkinsResultsParserUtil.isNullOrEmpty(gitHubURL)) {
			return;
		}

		WorkspaceGitRepository workspaceGitRepository =
			getWorkspaceGitRepository("liferay-faces-alloy");

		if (workspaceGitRepository == null) {
			return;
		}

		workspaceGitRepository.setGitHubURL(gitHubURL);
	}

	private void _configureLiferayFacesBridgeImplWorkspaceGitRepository() {
		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_getPortalWorkspaceGitRepository();

		String gitHubURL =
			portalWorkspaceGitRepository.getLiferayFacesBridgeImplURL();

		if (JenkinsResultsParserUtil.isNullOrEmpty(gitHubURL)) {
			return;
		}

		WorkspaceGitRepository workspaceGitRepository =
			getWorkspaceGitRepository("liferay-faces-bridge-impl");

		if (workspaceGitRepository == null) {
			return;
		}

		workspaceGitRepository.setGitHubURL(gitHubURL);
	}

	private void _configureLiferayFacesPortalWorkspaceGitRepository() {
		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_getPortalWorkspaceGitRepository();

		String gitHubURL =
			portalWorkspaceGitRepository.getLiferayFacesPortalURL();

		if (JenkinsResultsParserUtil.isNullOrEmpty(gitHubURL)) {
			return;
		}

		WorkspaceGitRepository workspaceGitRepository =
			getWorkspaceGitRepository("liferay-faces-portal");

		if (workspaceGitRepository == null) {
			return;
		}

		workspaceGitRepository.setGitHubURL(gitHubURL);
	}

	private void _configureLiferayFacesShowcaseWorkspaceGitRepository() {
		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_getPortalWorkspaceGitRepository();

		String gitHubURL =
			portalWorkspaceGitRepository.getLiferayFacesShowcaseURL();

		if (JenkinsResultsParserUtil.isNullOrEmpty(gitHubURL)) {
			return;
		}

		WorkspaceGitRepository workspaceGitRepository =
			getWorkspaceGitRepository("liferay-faces-showcase");

		if (workspaceGitRepository == null) {
			return;
		}

		workspaceGitRepository.setGitHubURL(gitHubURL);
	}

	private void _configureOSBAsahWorkspaceGitRepository() {
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

	private void _configureOSBFaroWorkspaceGitRepository() {
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

	private void _configurePluginsWorkspaceGitRepository() {
		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_getPortalWorkspaceGitRepository();

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

	private void _configurePortalPrivateWorkspaceGitRepository() {
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

	private void _configurePortalsPlutoWorkspaceGitRepository() {
		_updateWorkspaceGitRepository(
			"git-commit-portals-pluto", "portals-pluto");
	}

	private void _configureReleaseToolWorkspaceGitRepository() {
		_updateWorkspaceGitRepository(
			"git-commit/liferay-release-tool-ee", "liferay-release-tool-ee");

		ReleaseToolWorkspaceGitRepository releaseToolWorkspaceGitRepository =
			_getReleaseToolWorkspaceGitRepository();

		if (releaseToolWorkspaceGitRepository == null) {
			return;
		}

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_getPortalWorkspaceGitRepository();

		releaseToolWorkspaceGitRepository.setPortalUpstreamBranchName(
			portalWorkspaceGitRepository.getUpstreamBranchName());
	}

	private PluginsWorkspaceGitRepository _getPluginsWorkspaceGitRepository() {
		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_getPortalWorkspaceGitRepository();

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
			throw new RuntimeException(
				"The portal workspace Git repository is not set");
		}

		return (PortalWorkspaceGitRepository)workspaceGitRepository;
	}

	private ReleaseToolWorkspaceGitRepository
		_getReleaseToolWorkspaceGitRepository() {

		WorkspaceGitRepository workspaceGitRepository =
			getWorkspaceGitRepository("liferay-release-tool-ee");

		if (!(workspaceGitRepository instanceof
				ReleaseToolWorkspaceGitRepository)) {

			return null;
		}

		return (ReleaseToolWorkspaceGitRepository)workspaceGitRepository;
	}

	private void _setUpOSBAsahWorkspaceGitRepository() {
		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_getPortalWorkspaceGitRepository();

		File modulesDir = new File(
			portalWorkspaceGitRepository.getDirectory(),
			"modules/dxp/apps/osb/osb-asah");

		if (!modulesDir.exists()) {
			return;
		}

		File ciMergeFile = new File(modulesDir, "ci-merge");

		if (!ciMergeFile.exists()) {
			return;
		}

		WorkspaceGitRepository workspaceGitRepository =
			getWorkspaceGitRepository("com-liferay-osb-asah-private");

		if (workspaceGitRepository == null) {
			return;
		}

		List<PathMatcher> deleteExcludePathMatchers =
			JenkinsResultsParserUtil.toPathMatchers(
				JenkinsResultsParserUtil.combine(
					JenkinsResultsParserUtil.getCanonicalPath(modulesDir),
					File.separator),
				".gradle/", ".gitrepo", "ci-merge", "gradle/");

		try {
			JenkinsResultsParserUtil.delete(
				modulesDir, null, deleteExcludePathMatchers);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		List<PathMatcher> copyExcludePathMatchers =
			JenkinsResultsParserUtil.toPathMatchers(
				JenkinsResultsParserUtil.combine(
					JenkinsResultsParserUtil.getCanonicalPath(
						workspaceGitRepository.getDirectory()),
					File.separator),
				".git/", ".gradle/", "gradle/", "settings.gradle");

		try {
			JenkinsResultsParserUtil.copy(
				workspaceGitRepository.getDirectory(), modulesDir, null,
				copyExcludePathMatchers);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		try {
			Map<String, String> parameters = new HashMap<>();

			parameters.put("module.dir", "dxp/apps/osb/osb-asah");
			parameters.put(
				"portal.dir",
				JenkinsResultsParserUtil.getCanonicalPath(
					portalWorkspaceGitRepository.getDirectory()));

			AntUtil.callTarget(
				portalWorkspaceGitRepository.getDirectory(), "build-test.xml",
				"clean-version-override", parameters);
		}
		catch (AntException antException) {
			throw new RuntimeException(antException);
		}

		List<File> versionOverrideFiles = JenkinsResultsParserUtil.findFiles(
			modulesDir,
			JenkinsResultsParserUtil.combine(
				".version-override-", modulesDir.getName(), ".properties"));

		for (File versionOverrideFile : versionOverrideFiles) {
			JenkinsResultsParserUtil.delete(versionOverrideFile);
		}

		GitWorkingDirectory gitWorkingDirectory =
			portalWorkspaceGitRepository.getGitWorkingDirectory();

		System.out.println(gitWorkingDirectory.status());

		gitWorkingDirectory.commitFileToCurrentBranch(
			"modules/dxp/apps/osb/osb-asah",
			JenkinsResultsParserUtil.combine(
				"Committing changes from ", workspaceGitRepository.getName(),
				" at ", workspaceGitRepository.getSenderBranchSHA(),
				" for testing on CI"));
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