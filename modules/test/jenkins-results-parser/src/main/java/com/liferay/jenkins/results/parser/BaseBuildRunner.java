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

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildRunner {

	public Job getJob() {
		return job;
	}

	public void setup() {
		primaryLocalRepository.setup();
	}

	protected BaseBuildRunner(Job job) {
		this.job = job;
	}

	protected PortalLocalGitBranch getPortalLocalGitBranch() {
		return _portalLocalGitBranch;
	}

	protected PortalLocalGitBranch getPortalLocalGitBranch(
		String portalHtmlURL) {

		if (_portalLocalGitBranch != null) {
			return _portalLocalGitBranch;
		}

		PortalTestClassJob portalTestClassJob = (PortalTestClassJob)job;

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

		if (PullRequest.isValidHtmlURL(portalHtmlURL)) {
			PullRequest pullRequest = new PullRequest(
				portalHtmlURL, getTestSuiteName());

			localGitBranch = LocalGitSyncUtil.createCachedLocalGitBranch(
				_portalLocalRepository, pullRequest);
		}
		else if (Ref.isValidHtmlURL(portalHtmlURL)) {
			Ref ref = new Ref(portalHtmlURL);

			localGitBranch = LocalGitSyncUtil.createCachedLocalGitBranch(
				_portalLocalRepository, ref);
		}
		else {
			throw new RuntimeException("Invalid html url " + portalHtmlURL);
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
		if (job instanceof TestSuiteJob) {
			TestSuiteJob testSuiteJob = (TestSuiteJob)job;

			return testSuiteJob.getTestSuiteName();
		}

		return null;
	}

	protected final Job job;
	protected LocalRepository primaryLocalRepository;

	private PortalLocalGitBranch _portalLocalGitBranch;
	private PortalLocalRepository _portalLocalRepository;

}