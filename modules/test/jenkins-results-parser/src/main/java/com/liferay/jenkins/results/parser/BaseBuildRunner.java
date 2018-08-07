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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildRunner {

	public void setup() {
		for (LocalGitBranch localGitBranch : _localGitBranches) {
			localGitBranch.setupWorkspace();

			LocalRepository localRepository =
				localGitBranch.getLocalRepository();

			localRepository.writeRepositoryPropertiesFiles();
		}
	}

	protected BaseBuildRunner(Job job) {
		_job = job;
	}

	protected void addLocalGitBranch(LocalGitBranch localGitBranch) {
		if (localGitBranch != null) {
			_localGitBranches.add(localGitBranch);
		}
	}

	protected Job getJob() {
		return _job;
	}

	protected PortalLocalGitBranch getPortalLocalGitBranch() {
		return _portalLocalGitBranch;
	}

	protected PortalLocalGitBranch getPortalLocalGitBranch(
		String portalGitHubURL) {

		if (_portalLocalGitBranch != null) {
			return _portalLocalGitBranch;
		}

		PortalTestClassJob portalTestClassJob = (PortalTestClassJob)_job;

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			portalTestClassJob.getPortalGitWorkingDirectory();

		LocalRepository localRepository = RepositoryFactory.getLocalRepository(
			portalGitWorkingDirectory.getRepositoryName(),
			portalGitWorkingDirectory.getUpstreamBranchName());

		if (!(localRepository instanceof PortalLocalRepository)) {
			throw new RuntimeException(
				"Invalid local repository " + localRepository);
		}

		_portalLocalRepository = (PortalLocalRepository)localRepository;

		LocalGitBranch localGitBranch;

		if (PullRequest.isValidGitHubPullRequestURL(portalGitHubURL)) {
			PullRequest pullRequest = new PullRequest(
				portalGitHubURL, getTestSuiteName());

			localGitBranch = LocalGitSyncUtil.createCachedLocalGitBranch(
				_portalLocalRepository, pullRequest, synchronizeBranches());
		}
		else if (GitUtil.isValidGitHubRefURL(portalGitHubURL)) {
			RemoteGitRef remoteGitRef = GitUtil.getRemoteGitRef(
				portalGitHubURL);

			localGitBranch = LocalGitSyncUtil.createCachedLocalGitBranch(
				_portalLocalRepository, remoteGitRef, synchronizeBranches());
		}
		else {
			throw new RuntimeException(
				"Invalid portal github url " + portalGitHubURL);
		}

		if (!(localGitBranch instanceof PortalLocalGitBranch)) {
			throw new RuntimeException(
				"Invalid local git branch " + localGitBranch);
		}

		_portalLocalGitBranch = (PortalLocalGitBranch)localGitBranch;

		return _portalLocalGitBranch;
	}

	protected PortalLocalRepository getPortalLocalRepository() {
		return _portalLocalRepository;
	}

	protected String getTestSuiteName() {
		if (_job instanceof TestSuiteJob) {
			TestSuiteJob testSuiteJob = (TestSuiteJob)_job;

			return testSuiteJob.getTestSuiteName();
		}

		return null;
	}

	protected abstract boolean synchronizeBranches();

	private final Job _job;
	private final List<LocalGitBranch> _localGitBranches = new ArrayList<>();
	private PortalLocalGitBranch _portalLocalGitBranch;
	private PortalLocalRepository _portalLocalRepository;

}