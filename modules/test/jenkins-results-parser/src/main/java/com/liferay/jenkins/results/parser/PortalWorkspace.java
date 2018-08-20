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
public class PortalWorkspace extends BaseWorkspace {

	public static boolean isPortalGitHubURL(String gitHubURL) {
		Matcher matcher = _portalGitHubURLPattern.matcher(gitHubURL);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	public void setPortalJobProperties(Job job) {
		_primaryPortalLocalRepository.setJobProperties(job);
	}

	@Override
	public void setupWorkspace() {
		_checkoutPrimaryPortalLocalGitBranch();

		_checkoutBasePortalLocalGitBranch();

		_checkoutCompanionPortalLocalGitBranch();

		_checkoutOtherPortalLocalGitBranch();

		_checkoutPluginsLocalGitBranch();

		_primaryPortalLocalRepository.writeRepositoryPropertiesFiles();
	}

	protected PortalWorkspace(
		String portalGitHubURL, String portalUpstreamBranchName) {

		this(portalGitHubURL, portalUpstreamBranchName, false);
	}

	protected PortalWorkspace(
		String portalGitHubURL, String portalUpstreamBranchName,
		boolean synchronizeBranches) {

		_synchronizeBranches = synchronizeBranches;

		String portalRepositoryName = _getPortalRepositoryName(portalGitHubURL);

		_primaryPortalLocalRepository = _getPortalLocalRepository(
			portalRepositoryName, portalUpstreamBranchName);

		_primaryPortalLocalGitBranch = _getCachedPortalLocalGitBranch(
			_primaryPortalLocalRepository, portalGitHubURL);
	}

	protected PortalLocalGitBranch getBasePortalLocalGitBranch() {
		if (_basePortalLocalGitBranch != null) {
			return _basePortalLocalGitBranch;
		}

		String portalUpstreamBranchName =
			_primaryPortalLocalGitBranch.getUpstreamBranchName();

		if (!portalUpstreamBranchName.contains("-private")) {
			return null;
		}

		String branchName = portalUpstreamBranchName.replace("-private", "");

		String repositoryName = "liferay-portal-ee";

		if (branchName.equals("master")) {
			repositoryName = repositoryName.replace("-ee", "");
		}

		LocalRepository localRepository = RepositoryFactory.getLocalRepository(
			repositoryName, branchName);

		LocalGitBranch localGitBranch = _getLocalGitBranchFromGitCommit(
			"git-commit-portal", localRepository, _synchronizeBranches);

		_basePortalLocalGitBranch = (PortalLocalGitBranch)localGitBranch;

		return _basePortalLocalGitBranch;
	}

	protected PortalLocalGitBranch getCompanionPortalLocalGitBranch() {
		if (_companionPortalLocalGitBranch != null) {
			return _companionPortalLocalGitBranch;
		}

		String portalUpstreamBranchName =
			_primaryPortalLocalGitBranch.getUpstreamBranchName();

		if (!portalUpstreamBranchName.equals("7.0.x") &&
			!portalUpstreamBranchName.equals("7.1.x") &&
			!portalUpstreamBranchName.equals("master")) {

			return null;
		}

		String branchName = portalUpstreamBranchName + "-private";

		LocalRepository localRepository = RepositoryFactory.getLocalRepository(
			"liferay-portal-ee", branchName);

		LocalGitBranch localGitBranch = _getLocalGitBranchFromGitCommit(
			"git-commit-portal-private", localRepository, _synchronizeBranches);

		_companionPortalLocalGitBranch = (PortalLocalGitBranch)localGitBranch;

		return _companionPortalLocalGitBranch;
	}

	protected PortalLocalGitBranch getOtherPortalLocalGitBranch() {
		if (_otherPortalLocalGitBranch != null) {
			return _otherPortalLocalGitBranch;
		}

		String branchName =
			_primaryPortalLocalGitBranch.getUpstreamBranchName();

		if (branchName.contains("7.0.x")) {
			branchName = branchName.replace("7.0.x", "master");
		}
		else if (branchName.contains("7.1.x")) {
			branchName = branchName.replace("7.1.x", "7.0.x");
		}
		else if (branchName.contains("master")) {
			branchName = branchName.replace("master", "7.0.x");
		}
		else {
			return null;
		}

		String repositoryName = "liferay-portal-ee";

		if (branchName.equals("master")) {
			repositoryName = repositoryName.replace("-ee", "");
		}

		LocalRepository localRepository = RepositoryFactory.getLocalRepository(
			repositoryName, branchName);

		RemoteGitRef remoteGitRef = GitUtil.getRemoteGitRef(
			JenkinsResultsParserUtil.combine(
				"https://github.com/liferay/", repositoryName, "/tree/",
				branchName));

		LocalGitBranch localGitBranch =
			LocalGitSyncUtil.createCachedLocalGitBranch(
				localRepository, remoteGitRef, _synchronizeBranches);

		_otherPortalLocalGitBranch = (PortalLocalGitBranch)localGitBranch;

		return _otherPortalLocalGitBranch;
	}

	protected PluginsLocalGitBranch getPluginsLocalGitBranch() {
		if (_pluginsLocalGitBranch != null) {
			return _pluginsLocalGitBranch;
		}

		String branchName =
			_primaryPortalLocalGitBranch.getUpstreamBranchName();

		if (branchName.contains("7.0.x") || branchName.contains("7.1.x") ||
			branchName.contains("master")) {

			branchName = "7.0.x";
		}

		LocalRepository localRepository = RepositoryFactory.getLocalRepository(
			"liferay-plugins-ee", branchName);

		LocalGitBranch localGitBranch = _getLocalGitBranchFromGitCommit(
			"git-commit-plugins", localRepository, _synchronizeBranches);

		_pluginsLocalGitBranch = (PluginsLocalGitBranch)localGitBranch;

		return _pluginsLocalGitBranch;
	}

	protected PortalLocalRepository getPrimaryPortalRepository() {
		return _primaryPortalLocalRepository;
	}

	private void _checkoutBasePortalLocalGitBranch() {
		PortalLocalGitBranch basePortalLocalGitBranch =
			getBasePortalLocalGitBranch();

		if (basePortalLocalGitBranch == null) {
			return;
		}

		checkoutBranch(basePortalLocalGitBranch);

		GitWorkingDirectory gitWorkingDirectory =
			_primaryPortalLocalRepository.getGitWorkingDirectory();

		gitWorkingDirectory.fetch(basePortalLocalGitBranch);

		File gitCommitPortalFile = new File(
			_primaryPortalLocalRepository.getDirectory(), "git-commit-portal");

		try {
			JenkinsResultsParserUtil.write(
				gitCommitPortalFile, basePortalLocalGitBranch.getSHA());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		AntUtil.callTarget(
			_primaryPortalLocalRepository.getDirectory(),
			"build-working-dir.xml", "prepare-working-dir", null);
	}

	private void _checkoutCompanionPortalLocalGitBranch() {
		PortalLocalGitBranch companionPortalLocalGitBranch =
			getCompanionPortalLocalGitBranch();

		if (companionPortalLocalGitBranch == null) {
			return;
		}

		checkoutBranch(companionPortalLocalGitBranch);

		try {
			String path = "modules/private";

			JenkinsResultsParserUtil.copy(
				new File(companionPortalLocalGitBranch.getDirectory(), path),
				new File(_primaryPortalLocalGitBranch.getDirectory(), path));
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

		checkoutBranch(otherPortalLocalGitBranch);
	}

	private void _checkoutPluginsLocalGitBranch() {
		PluginsLocalGitBranch pluginsLocalGitBranch =
			getPluginsLocalGitBranch();

		if (pluginsLocalGitBranch == null) {
			return;
		}

		checkoutBranch(pluginsLocalGitBranch);
	}

	private void _checkoutPrimaryPortalLocalGitBranch() {
		checkoutBranch(_primaryPortalLocalGitBranch);
	}

	private PortalLocalGitBranch _getCachedPortalLocalGitBranch(
		PortalLocalRepository portalLocalRepository, String portalGitHubURL) {

		LocalGitBranch localGitBranch;

		if (PullRequest.isValidGitHubPullRequestURL(portalGitHubURL)) {
			PullRequest pullRequest = new PullRequest(portalGitHubURL);

			localGitBranch = LocalGitSyncUtil.createCachedLocalGitBranch(
				portalLocalRepository, pullRequest, _synchronizeBranches);
		}
		else if (GitUtil.isValidGitHubRefURL(portalGitHubURL)) {
			RemoteGitRef remoteGitRef = GitUtil.getRemoteGitRef(
				portalGitHubURL);

			localGitBranch = LocalGitSyncUtil.createCachedLocalGitBranch(
				portalLocalRepository, remoteGitRef, _synchronizeBranches);
		}
		else {
			throw new RuntimeException(
				"Invalid portal github url " + portalGitHubURL);
		}

		if (!(localGitBranch instanceof PortalLocalGitBranch)) {
			throw new RuntimeException(
				"Invalid local git branch " + localGitBranch);
		}

		return (PortalLocalGitBranch)localGitBranch;
	}

	private LocalGitBranch _getLocalGitBranchFromGitCommit(
		String gitCommitFileName, LocalRepository localRepository,
		boolean synchronizeBranches) {

		String gitCommitFileContent = _getPortalRepositoryFileContent(
			gitCommitFileName);

		LocalGitBranch localGitBranch = null;

		if (gitCommitFileContent.matches("[0-9a-f]{5,40}")) {
			localGitBranch = LocalGitSyncUtil.createCachedLocalGitBranch(
				localRepository, localRepository.getUpstreamBranchName(),
				gitCommitFileContent, synchronizeBranches);
		}
		else if (PullRequest.isValidGitHubPullRequestURL(
					 gitCommitFileContent)) {

			PullRequest pullRequest = new PullRequest(gitCommitFileContent);

			localGitBranch = LocalGitSyncUtil.createCachedLocalGitBranch(
				localRepository, pullRequest, synchronizeBranches);
		}
		else if (GitUtil.isValidGitHubRefURL(gitCommitFileContent)) {
			RemoteGitRef remoteGitRef = GitUtil.getRemoteGitRef(
				gitCommitFileContent);

			localGitBranch = LocalGitSyncUtil.createCachedLocalGitBranch(
				localRepository, remoteGitRef, synchronizeBranches);
		}

		if (localGitBranch == null) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Invalid ", gitCommitFileName, " ", gitCommitFileContent));
		}

		return localGitBranch;
	}

	private PortalLocalRepository _getPortalLocalRepository(
		String portalRepositoryName, String portalUpstreamBranchName) {

		LocalRepository localRepository = RepositoryFactory.getLocalRepository(
			portalRepositoryName, portalUpstreamBranchName);

		if (!(localRepository instanceof PortalLocalRepository)) {
			throw new RuntimeException(
				"Invalid local repository " + localRepository);
		}

		return (PortalLocalRepository)localRepository;
	}

	private String _getPortalRepositoryFileContent(
		String portalRepositoryFileName) {

		File gitCommitFile = new File(
			_primaryPortalLocalGitBranch.getDirectory(),
			portalRepositoryFileName);

		try {
			String gitCommit = JenkinsResultsParserUtil.read(gitCommitFile);

			return gitCommit.trim();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private String _getPortalRepositoryName(String portalGitHubURL) {
		Matcher matcher = _portalGitHubURLPattern.matcher(portalGitHubURL);

		if (!matcher.find()) {
			throw new RuntimeException(
				"Invalid portal github url " + portalGitHubURL);
		}

		return matcher.group("repositoryName");
	}

	private static final Pattern _portalGitHubURLPattern = Pattern.compile(
		"https://github.com/[^/]+/(?<repositoryName>liferay-portal(-ee)?)/.*");

	private PortalLocalGitBranch _basePortalLocalGitBranch;
	private PortalLocalGitBranch _companionPortalLocalGitBranch;
	private PortalLocalGitBranch _otherPortalLocalGitBranch;
	private PluginsLocalGitBranch _pluginsLocalGitBranch;
	private final PortalLocalGitBranch _primaryPortalLocalGitBranch;
	private final PortalLocalRepository _primaryPortalLocalRepository;
	private final boolean _synchronizeBranches;

}