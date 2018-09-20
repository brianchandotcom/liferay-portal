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
public abstract class BaseWorkspace implements Workspace {

	@Override
	public void addJenkinsWorkspaceGitRepository(String jenkinsGitHubURL) {
		if (!JenkinsResultsParserUtil.isCINode()) {
			return;
		}

		if (jenkinsGitHubURL == null) {
			return;
		}

		_jenkinsWorkspaceGitRepository =
			GitRepositoryFactory.newWorkspaceGitRepository(
				jenkinsGitHubURL, "master");
	}

	@Override
	public WorkspaceGitRepository getJenkinsWorkspaceGitRepository() {
		return _jenkinsWorkspaceGitRepository;
	}

	@Override
	public void setUp() {
		setUp(null);
	}

	@Override
	public void setUp(Job job) {
		setUpWorkspaceGitRepositories();

		if (job != null) {
			setWorkspaceGitRepositoryJobProperties(job);
		}

		writeWorkspaceGitRepositoryPropertiesFiles();
	}

	@Override
	public void tearDown() {
		tearDownWorkspaceGitRepositories();
	}

	protected void setUpJenkinsWorkspaceGitRepository() {
		if (_jenkinsWorkspaceGitRepository != null) {
			_jenkinsWorkspaceGitRepository.setUp();
		}
	}

	protected void setUpWorkspaceGitRepositories() {
		setUpJenkinsWorkspaceGitRepository();
	}

	protected abstract void setWorkspaceGitRepositoryJobProperties(Job job);

	protected void tearDownJenkinsWorkspaceGitRepository() {
		if (_jenkinsWorkspaceGitRepository != null) {
			_jenkinsWorkspaceGitRepository.tearDown();
		}
	}

	protected void tearDownWorkspaceGitRepositories() {
		tearDownJenkinsWorkspaceGitRepository();
	}

	protected abstract void writeWorkspaceGitRepositoryPropertiesFiles();

	private WorkspaceGitRepository _jenkinsWorkspaceGitRepository;

}