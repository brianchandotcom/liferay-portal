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

		if (!portalUpstreamBranchName.startsWith("ee-")) {
			_companionPortalWorkbench =
				WorkbenchFactory.newCompanionPortalWorkbench(
					_primaryPortalWorkbench);
			_otherPortalWorkbench = WorkbenchFactory.newOtherPortalWorkbench(
				_primaryPortalWorkbench);
		}
		else {
			_companionPortalWorkbench = null;
			_otherPortalWorkbench = null;
		}
	}

	protected OtherPortalWorkbench getOtherPortalWorkbench() {
		return _otherPortalWorkbench;
	}

	protected PortalWorkbench getPrimaryPortalWorkbench() {
		return _primaryPortalWorkbench;
	}

	@Override
	protected void setUpWorkbenches() {
		setUpJenkinsWorkbench();

		_primaryPortalWorkbench.setUp();

		if (_companionPortalWorkbench != null) {
			_companionPortalWorkbench.setUp();
		}

		if (_otherPortalWorkbench != null) {
			_otherPortalWorkbench.setUp();
		}


		_checkoutPluginsLocalGitBranch();
	}

	@Override
	protected void tearDownWorkbenches() {
		tearDownJenkinsWorkbench();

		_primaryPortalWorkbench.tearDown();

		if (_companionPortalWorkbench != null) {
			_companionPortalWorkbench.tearDown();
		}

		if (_otherPortalWorkbench != null) {
			_otherPortalWorkbench.tearDown();
		}

		cleanupLocalGitBranch(_pluginsLocalGitBranch);
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

	private final CompanionPortalWorkbench _companionPortalWorkbench;
	private final OtherPortalWorkbench _otherPortalWorkbench;
	private PluginsLocalGitBranch _pluginsLocalGitBranch;
	private final PortalWorkbench _primaryPortalWorkbench;

}