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

/**
 * @author Michael Hashimoto
 */
public class PortalLocalGitBranch extends LocalGitBranch {

	public PortalLocalGitBranch getBasePortalLocalGitBranch() {
		if (_basePortalLocalGitBranch != null) {
			return _basePortalLocalGitBranch;
		}

		String portalUpstreamBranchName = getUpstreamBranchName();

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
			"git-commit-portal", localRepository);

		_basePortalLocalGitBranch = (PortalLocalGitBranch)localGitBranch;

		return _basePortalLocalGitBranch;
	}

	public PortalLocalGitBranch getCompanionPortalLocalGitBranch() {
		if (_companionPortalLocalGitBranch != null) {
			return _companionPortalLocalGitBranch;
		}

		String portalUpstreamBranchName = getUpstreamBranchName();

		if (!portalUpstreamBranchName.equals("7.0.x") &&
			!portalUpstreamBranchName.equals("7.1.x") &&
			!portalUpstreamBranchName.equals("master")) {

			return null;
		}

		String branchName = portalUpstreamBranchName + "-private";

		LocalRepository localRepository = RepositoryFactory.getLocalRepository(
			"liferay-portal-ee", branchName);

		LocalGitBranch localGitBranch = _getLocalGitBranchFromGitCommit(
			"git-commit-portal-private", localRepository);

		_companionPortalLocalGitBranch = (PortalLocalGitBranch)localGitBranch;

		return _companionPortalLocalGitBranch;
	}

	public PortalLocalGitBranch getOtherPortalLocalGitBranch() {
		if (_otherPortalLocalGitBranch != null) {
			return _otherPortalLocalGitBranch;
		}

		String branchName = getUpstreamBranchName();

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
				localRepository, remoteGitRef, _synchronize);

		_otherPortalLocalGitBranch = (PortalLocalGitBranch)localGitBranch;

		return _otherPortalLocalGitBranch;
	}

	public PluginsLocalGitBranch getPluginsLocalGitBranch() {
		if (_pluginsLocalGitBranch != null) {
			return _pluginsLocalGitBranch;
		}

		String branchName = getUpstreamBranchName();

		if (branchName.contains("7.0.x") || branchName.contains("7.1.x") ||
			branchName.contains("master")) {

			branchName = "7.0.x";
		}

		LocalRepository localRepository = RepositoryFactory.getLocalRepository(
			"liferay-plugins-ee", branchName);

		LocalGitBranch localGitBranch = _getLocalGitBranchFromGitCommit(
			"git-commit-plugins", localRepository);

		_pluginsLocalGitBranch = (PluginsLocalGitBranch)localGitBranch;

		return _pluginsLocalGitBranch;
	}

	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		return (PortalGitWorkingDirectory)gitWorkingDirectory;
	}

	public PortalLocalRepository getPortalLocalRepository() {
		LocalRepository localRepository = getLocalRepository();

		return (PortalLocalRepository)localRepository;
	}

	@Override
	public void setupWorkspace() {
		super.setupWorkspace();

		_setupBasePortalWorkspace();

		_setupCompanionPortalWorkspace();

		_setupOtherPortalWorkspace();

		_setupPluginsWorkspace();
	}

	protected PortalLocalGitBranch(
		LocalRepository localRepository, String name, String sha) {

		this(localRepository, name, sha, false);
	}

	protected PortalLocalGitBranch(
		LocalRepository localRepository, String name, String sha,
		boolean synchronize) {

		super(localRepository, name, sha);

		if (!(localRepository instanceof PortalLocalRepository)) {
			throw new IllegalArgumentException(
				"Local repository is not a portal repository");
		}

		_synchronize = synchronize;
	}

	private String _getGitCommit(String gitCommitFileName) {
		PortalLocalRepository portalLocalRepository =
			getPortalLocalRepository();

		File gitCommitFile = new File(
			portalLocalRepository.getDirectory(), gitCommitFileName);

		try {
			String gitCommit = JenkinsResultsParserUtil.read(gitCommitFile);

			return gitCommit.trim();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private LocalGitBranch _getLocalGitBranchFromGitCommit(
		String gitCommitFileName, LocalRepository localRepository) {

		String gitCommit = _getGitCommit(gitCommitFileName);

		LocalGitBranch localGitBranch = null;

		if (gitCommit.matches("[0-9a-f]{5,40}")) {
			localGitBranch = LocalGitSyncUtil.createCachedLocalGitBranch(
				localRepository, localRepository.getUpstreamBranchName(),
				gitCommit, _synchronize);
		}
		else if (PullRequest.isValidGitHubPullRequestURL(gitCommit)) {
			PullRequest pullRequest = new PullRequest(gitCommit);

			localGitBranch = LocalGitSyncUtil.createCachedLocalGitBranch(
				localRepository, pullRequest, _synchronize);
		}
		else if (GitUtil.isValidGitHubRefURL(gitCommit)) {
			RemoteGitRef remoteGitRef = GitUtil.getRemoteGitRef(gitCommit);

			localGitBranch = LocalGitSyncUtil.createCachedLocalGitBranch(
				localRepository, remoteGitRef, _synchronize);
		}

		if (localGitBranch == null) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Invalid ", gitCommitFileName, " ", gitCommit));
		}

		return localGitBranch;
	}

	private void _setupBasePortalWorkspace() {
		PortalLocalGitBranch basePortalLocalGitBranch =
			getBasePortalLocalGitBranch();

		if (basePortalLocalGitBranch == null) {
			return;
		}

		setupWorkspace(basePortalLocalGitBranch);

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		gitWorkingDirectory.fetch(basePortalLocalGitBranch);

		File gitCommitPortalFile = new File(
			getDirectory(), "git-commit-portal");

		try {
			JenkinsResultsParserUtil.write(
				gitCommitPortalFile, basePortalLocalGitBranch.getSHA());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		AntUtil.callTarget(
			getDirectory(), "build-working-dir.xml", "prepare-working-dir",
			null);
	}

	private void _setupCompanionPortalWorkspace() {
		PortalLocalGitBranch companionPortalLocalGitBranch =
			getCompanionPortalLocalGitBranch();

		if (companionPortalLocalGitBranch == null) {
			return;
		}

		setupWorkspace(companionPortalLocalGitBranch);

		File companionPrivateModulesDir = new File(
			companionPortalLocalGitBranch.getDirectory(), "modules/private");
		File privateModulesDir = new File(getDirectory(), "modules/private");

		try {
			JenkinsResultsParserUtil.copy(
				companionPrivateModulesDir, privateModulesDir);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private void _setupOtherPortalWorkspace() {
		PortalLocalGitBranch otherPortalLocalGitBranch =
			getOtherPortalLocalGitBranch();

		if (otherPortalLocalGitBranch == null) {
			return;
		}

		setupWorkspace(otherPortalLocalGitBranch);
	}

	private void _setupPluginsWorkspace() {
		PluginsLocalGitBranch pluginsLocalGitBranch =
			getPluginsLocalGitBranch();

		if (pluginsLocalGitBranch == null) {
			return;
		}

		setupWorkspace(pluginsLocalGitBranch);
	}

	private PortalLocalGitBranch _basePortalLocalGitBranch;
	private PortalLocalGitBranch _companionPortalLocalGitBranch;
	private PortalLocalGitBranch _otherPortalLocalGitBranch;
	private PluginsLocalGitBranch _pluginsLocalGitBranch;
	private final boolean _synchronize;

}