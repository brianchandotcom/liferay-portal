/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public Job.BuildProfile getBuildProfile() {
		String buildProfileString = jsonObject.optString(
			"build_profile", "dxp");

		return Job.BuildProfile.getByString(buildProfileString);
	}

	public WorkspaceGitRepository getLegacyWorkspaceGitRepository() {
		return getWorkspaceGitRepository("liferay-qa-portal-legacy-ee");
	}

	public PluginsWorkspaceGitRepository getPluginsWorkspaceGitRepository() {
		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			getPortalWorkspaceGitRepository();

		WorkspaceGitRepository workspaceGitRepository =
			getWorkspaceGitRepository(
				portalWorkspaceGitRepository.getPluginsRepositoryDirName());

		if (!(workspaceGitRepository instanceof
				PluginsWorkspaceGitRepository)) {

			return null;
		}

		return (PluginsWorkspaceGitRepository)workspaceGitRepository;
	}

	public PortalWorkspaceGitRepository getPortalWorkspaceGitRepository() {
		WorkspaceGitRepository workspaceGitRepository =
			getPrimaryWorkspaceGitRepository();

		if (!(workspaceGitRepository instanceof PortalWorkspaceGitRepository)) {
			throw new RuntimeException(
				"The portal workspace Git repository is not set");
		}

		return (PortalWorkspaceGitRepository)workspaceGitRepository;
	}

	public void setBuildProfile(Job.BuildProfile buildProfile) {
		if (buildProfile == null) {
			throw new RuntimeException("Invalid build profile " + buildProfile);
		}

		jsonObject.put("build_profile", buildProfile.toString());
	}

	public void setCommitOSBAsahModule(boolean commitOSBAsahModule) {
		_commitOSBAsahModule = commitOSBAsahModule;
	}

	public void setOSBAsahGitHubURL(String osbAsahGitHubURL) {
		_osbAsahGitHubURL = osbAsahGitHubURL;
	}

	public void setPortalPrivateGitHubURL(String portalPrivateGitHubURL) {
		_portalPrivateGitHubURL = portalPrivateGitHubURL;
	}

	@Override
	public void setUp() {
		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			getPortalWorkspaceGitRepository();

		portalWorkspaceGitRepository.setUp();

		_configureBladeSamplesWorkspaceGitRepository();
		_configureLiferayFacesAlloyWorkspaceGitRepository();
		_configureLiferayFacesBridgeImplWorkspaceGitRepository();
		_configureLiferayFacesPortalWorkspaceGitRepository();
		_configureLiferayFacesShowcaseWorkspaceGitRepository();
		_configureOSBAsahWorkspaceGitRepository();
		_configurePluginsWorkspaceGitRepository();
		_configurePortalPrivateWorkspaceGitRepository();
		_configurePortalsPlutoWorkspaceGitRepository();
		_configureReleaseToolWorkspaceGitRepository();

		super.setUp();

		Job.BuildProfile buildProfile = getBuildProfile();

		if (buildProfile == Job.BuildProfile.DXP) {
			portalWorkspaceGitRepository.setUpPortalProfile();
		}

		portalWorkspaceGitRepository.setUpTCKHome();

		updateOSBAsahModule();
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

	protected void copyOSBAsahRepositoryToModule() {
		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			getPortalWorkspaceGitRepository();

		File modulesDir = new File(
			portalWorkspaceGitRepository.getDirectory(),
			"modules/dxp/apps/osb/osb-asah");

		if (!modulesDir.exists()) {
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

		String gitStatus = gitWorkingDirectory.status();

		System.out.println(gitStatus);

		if (!_commitOSBAsahModule || gitStatus.contains("nothing to commit")) {
			return;
		}

		gitWorkingDirectory.commitFileToCurrentBranch(
			"modules/dxp/apps/osb/osb-asah",
			JenkinsResultsParserUtil.combine(
				"Committing changes from ", workspaceGitRepository.getName(),
				" at ", workspaceGitRepository.getSenderBranchSHA(),
				" for testing on CI"));
	}

	protected WorkspaceGitRepository getOSBAsahWorkspaceGitRepository() {
		return getWorkspaceGitRepository("com-liferay-osb-asah-private");
	}

	protected void updateOSBAsahModule() {
		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			getPortalWorkspaceGitRepository();

		File ciMergeFile = new File(
			portalWorkspaceGitRepository.getDirectory(),
			"modules/dxp/apps/osb/osb-asah/ci-merge");

		if (!ciMergeFile.exists()) {
			return;
		}

		copyOSBAsahRepositoryToModule();
	}

	private void _configureBladeSamplesWorkspaceGitRepository() {
		_updateWorkspaceGitRepository(
			"git-commit-blade-samples", "liferay-blade-samples");
	}

	private void _configureLiferayFacesAlloyWorkspaceGitRepository() {
		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			getPortalWorkspaceGitRepository();

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
			getPortalWorkspaceGitRepository();

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
			getPortalWorkspaceGitRepository();

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
			getPortalWorkspaceGitRepository();

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

	private void _configurePluginsWorkspaceGitRepository() {
		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			getPortalWorkspaceGitRepository();

		_updateWorkspaceGitRepository(
			"git-commit-plugins",
			portalWorkspaceGitRepository.getPluginsRepositoryDirName());

		PluginsWorkspaceGitRepository pluginsWorkspaceGitRepository =
			getPluginsWorkspaceGitRepository();

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

		String portalPrivateDirectoryName =
			portalWorkspaceGitRepository.getPortalPrivateRepositoryDirName();

		WorkspaceGitRepository workspaceGitRepository =
			getWorkspaceGitRepository(portalPrivateDirectoryName);

		if ((workspaceGitRepository == null) ||
			(_portalPrivateGitHubURL == null)) {

			return;
		}

		workspaceGitRepository.setGitHubURL(_portalPrivateGitHubURL);

		_updateWorkspaceGitRepository(
			"git-commit-portal-private", portalPrivateDirectoryName);
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
			getPortalWorkspaceGitRepository();

		releaseToolWorkspaceGitRepository.setPortalUpstreamBranchName(
			portalWorkspaceGitRepository.getUpstreamBranchName());
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

	private boolean _commitOSBAsahModule;
	private String _osbAsahGitHubURL;
	private String _portalPrivateGitHubURL;

}