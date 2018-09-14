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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public abstract class PortalWorkspace extends BaseWorkspace {

	public static boolean isPortalGitHubURL(String gitHubURL) {
		Matcher matcher = _portalGitHubURLPattern.matcher(gitHubURL);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	protected PortalWorkspace(
		String portalGitHubURL, String portalUpstreamBranchName) {

		Workbench workbench = WorkbenchFactory.newWorkbench(
			portalGitHubURL, portalUpstreamBranchName);

		if (!(workbench instanceof PortalWorkbench)) {
			throw new RuntimeException("Invalid workbench");
		}

		_primaryPortalWorkbench = (PortalWorkbench)workbench;

		_primaryPortalWorkbench.setUp();
	}

	protected PortalWorkbench getPrimaryPortalWorkbench() {
		return _primaryPortalWorkbench;
	}

	@Override
	protected void setUpWorkbenches() {
		setUpJenkinsWorkbench();

		_primaryPortalWorkbench.setUp();

		_checkoutBasePortalLocalGitBranch();

		_checkoutCompanionPortalLocalGitBranch();

		_checkoutOtherPortalLocalGitBranch();

		_checkoutPluginsLocalGitBranch();
	}

	@Override
	protected void tearDownWorkbenches() {
		tearDownJenkinsWorkbench();

		_primaryPortalWorkbench.tearDown();

		cleanupLocalGitBranch(_basePortalLocalGitBranch);
		cleanupLocalGitBranch(_companionPortalLocalGitBranch);
		cleanupLocalGitBranch(_otherPortalLocalGitBranch);
		cleanupLocalGitBranch(_pluginsLocalGitBranch);
	}

	protected PortalLocalGitBranch getBasePortalLocalGitBranch() {
		if (_basePortalLocalGitBranch != null) {
			return _basePortalLocalGitBranch;
		}

		String portalUpstreamBranchName =
			_primaryPortalWorkbench.getUpstreamBranchName();

		if (!portalUpstreamBranchName.contains("-private")) {
			return null;
		}

		String basePortalBranchName = portalUpstreamBranchName.replace(
			"-private", "");

		String basePortalGitRepositoryName = "liferay-portal-ee";

		if (basePortalBranchName.equals("master")) {
			basePortalGitRepositoryName = basePortalGitRepositoryName.replace(
				"-ee", "");
		}

		LocalGitRepository basePortalLocalGitRepository =
			GitRepositoryFactory.getLocalGitRepository(
				basePortalGitRepositoryName, basePortalBranchName);

		LocalGitBranch basePortalLocalGitBranch =
			_getLocalGitBranchFromGitCommit(
				"git-commit-portal", basePortalLocalGitRepository);

		_basePortalLocalGitBranch =
			(PortalLocalGitBranch)basePortalLocalGitBranch;

		return _basePortalLocalGitBranch;
	}

	protected PortalLocalGitBranch getCompanionPortalLocalGitBranch() {
		if (_companionPortalLocalGitBranch != null) {
			return _companionPortalLocalGitBranch;
		}

		String portalUpstreamBranchName =
			_primaryPortalWorkbench.getUpstreamBranchName();

		if (!portalUpstreamBranchName.equals("7.0.x") &&
			!portalUpstreamBranchName.equals("7.1.x") &&
			!portalUpstreamBranchName.equals("master")) {

			return null;
		}

		String otherPortalBranchName = portalUpstreamBranchName + "-private";

		LocalGitRepository otherPortalLocalGitRepository =
			GitRepositoryFactory.getLocalGitRepository(
				"liferay-portal-ee", otherPortalBranchName);

		LocalGitBranch companionPortalLocalGitBranch =
			_getLocalGitBranchFromGitCommit(
				"git-commit-portal-private", otherPortalLocalGitRepository);

		_companionPortalLocalGitBranch =
			(PortalLocalGitBranch)companionPortalLocalGitBranch;

		return _companionPortalLocalGitBranch;
	}

	protected PortalLocalGitBranch getOtherPortalLocalGitBranch() {
		if (_otherPortalLocalGitBranch != null) {
			return _otherPortalLocalGitBranch;
		}

		String portalUpstreamBranchName =
			_primaryPortalWorkbench.getUpstreamBranchName();

		String otherPortalBranchName;

		if (portalUpstreamBranchName.contains("7.0.x")) {
			otherPortalBranchName = portalUpstreamBranchName.replace(
				"7.0.x", "master");
		}
		else if (portalUpstreamBranchName.contains("7.1.x")) {
			otherPortalBranchName = portalUpstreamBranchName.replace(
				"7.1.x", "7.0.x");
		}
		else if (portalUpstreamBranchName.contains("master")) {
			otherPortalBranchName = portalUpstreamBranchName.replace(
				"master", "7.0.x");
		}
		else {
			return null;
		}

		String otherPortalGitRepositoryName = "liferay-portal-ee";

		if (otherPortalBranchName.equals("master")) {
			otherPortalGitRepositoryName = otherPortalGitRepositoryName.replace(
				"-ee", "");
		}

		LocalGitRepository otherPortalLocalGitRepository =
			GitRepositoryFactory.getLocalGitRepository(
				otherPortalGitRepositoryName, otherPortalBranchName);

		RemoteGitRef remoteGitRef = GitUtil.getRemoteGitRef(
			JenkinsResultsParserUtil.combine(
				"https://github.com/liferay/", otherPortalGitRepositoryName,
				"/tree/", otherPortalBranchName));

		LocalGitBranch otherPortalLocalGitBranch =
			GitHubDevSyncUtil.createCachedLocalGitBranch(
				otherPortalLocalGitRepository, remoteGitRef,
				true);

		_otherPortalLocalGitBranch =
			(PortalLocalGitBranch)otherPortalLocalGitBranch;

		return _otherPortalLocalGitBranch;
	}

	protected PluginsLocalGitBranch getPluginsLocalGitBranch() {
		if (_pluginsLocalGitBranch != null) {
			return _pluginsLocalGitBranch;
		}

		String pluginsBranchName =
			_primaryPortalWorkbench.getUpstreamBranchName();

		if (pluginsBranchName.contains("7.0.x") ||
			pluginsBranchName.contains("7.1.x") ||
			pluginsBranchName.contains("master")) {

			pluginsBranchName = "7.0.x";
		}

		LocalGitRepository pluginsLocalGitRepository =
			GitRepositoryFactory.getLocalGitRepository(
				"liferay-plugins-ee", pluginsBranchName);

		LocalGitBranch pluginsLocalGitBranch = _getLocalGitBranchFromGitCommit(
			"git-commit-plugins", pluginsLocalGitRepository);

		_pluginsLocalGitBranch = (PluginsLocalGitBranch)pluginsLocalGitBranch;

		return _pluginsLocalGitBranch;
	}

	@Override
	protected void setWorkbenchJobProperties(Job job) {
		_primaryPortalWorkbench.setPortalJobProperties(job);
	}

	@Override
	protected void writeWorkbenchPropertiesFiles() {
		_primaryPortalWorkbench.writePropertiesFiles();
	}

	private void _checkoutBasePortalLocalGitBranch() {
		PortalLocalGitBranch basePortalLocalGitBranch =
			getBasePortalLocalGitBranch();

		if (basePortalLocalGitBranch == null) {
			return;
		}

		checkoutLocalGitBranch(basePortalLocalGitBranch);

		LocalGitRepository primaryPortalLocalGitRepository =
			_primaryPortalWorkbench.getLocalGitRepository();

		GitWorkingDirectory gitWorkingDirectory =
			primaryPortalLocalGitRepository.getGitWorkingDirectory();

		gitWorkingDirectory.fetch(basePortalLocalGitBranch);

		File gitCommitPortalFile = new File(
			primaryPortalLocalGitRepository.getDirectory(),
			"git-commit-portal");

		try {
			JenkinsResultsParserUtil.write(
				gitCommitPortalFile, basePortalLocalGitBranch.getSHA());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		AntUtil.callTarget(
			primaryPortalLocalGitRepository.getDirectory(),
			"build-working-dir.xml", "prepare-working-dir", null);
	}

	private void _checkoutCompanionPortalLocalGitBranch() {
		PortalLocalGitBranch companionPortalLocalGitBranch =
			getCompanionPortalLocalGitBranch();

		if (companionPortalLocalGitBranch == null) {
			return;
		}

		checkoutLocalGitBranch(companionPortalLocalGitBranch);

		try {
			String path = "modules/private";

			LocalGitRepository primaryPortalLocalGitRepository =
				_primaryPortalWorkbench.getLocalGitRepository();

			JenkinsResultsParserUtil.copy(
				new File(companionPortalLocalGitBranch.getDirectory(), path),
				new File(primaryPortalLocalGitRepository.getDirectory(), path));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private void _checkoutOtherPortalLocalGitBranch() {
		PortalLocalGitBranch otherPortalLocalGitBranch =
			getOtherPortalLocalGitBranch();

		if (otherPortalLocalGitBranch == null) {
			return;
		}

		checkoutLocalGitBranch(otherPortalLocalGitBranch);
	}

	private void _checkoutPluginsLocalGitBranch() {
		PluginsLocalGitBranch pluginsLocalGitBranch =
			getPluginsLocalGitBranch();

		if (pluginsLocalGitBranch == null) {
			return;
		}

		checkoutLocalGitBranch(pluginsLocalGitBranch);
	}

	private LocalGitBranch _getLocalGitBranchFromGitCommit(
		String gitCommitFileName, LocalGitRepository localGitRepository) {

		String gitCommitFileContent = _getPortalLocalGitRepositoryFileContent(
			gitCommitFileName);

		LocalGitBranch localGitBranch = null;

		if (gitCommitFileContent.matches("[0-9a-f]{5,40}")) {
			localGitBranch = GitHubDevSyncUtil.createCachedLocalGitBranch(
				localGitRepository, localGitRepository.getUpstreamBranchName(),
				gitCommitFileContent, true);
		}
		else if (PullRequest.isValidGitHubPullRequestURL(
					gitCommitFileContent)) {

			PullRequest pullRequest = new PullRequest(gitCommitFileContent);

			localGitBranch = GitHubDevSyncUtil.createCachedLocalGitBranch(
				localGitRepository, pullRequest, true);
		}
		else if (GitUtil.isValidGitHubRefURL(gitCommitFileContent)) {
			RemoteGitRef remoteGitRef = GitUtil.getRemoteGitRef(
				gitCommitFileContent);

			localGitBranch = GitHubDevSyncUtil.createCachedLocalGitBranch(
				localGitRepository, remoteGitRef, true);
		}

		if (localGitBranch == null) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Invalid ", gitCommitFileName, " ", gitCommitFileContent));
		}

		return localGitBranch;
	}

	private String _getPortalLocalGitRepositoryFileContent(String fileName) {
		LocalGitRepository localGitRepository =
			_primaryPortalWorkbench.getLocalGitRepository();

		File file = new File(localGitRepository.getDirectory(), fileName);

		try {
			String fileContent = JenkinsResultsParserUtil.read(file);

			return fileContent.trim();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private static final Pattern _portalGitHubURLPattern = Pattern.compile(
		"https://github.com/[^/]+/(?<gitRepositoryName>" +
			"liferay-portal(-ee)?)/.*");

	private PortalLocalGitBranch _basePortalLocalGitBranch;
	private PortalLocalGitBranch _companionPortalLocalGitBranch;
	private PortalLocalGitBranch _otherPortalLocalGitBranch;
	private PluginsLocalGitBranch _pluginsLocalGitBranch;
	private final PortalWorkbench _primaryPortalWorkbench;

}