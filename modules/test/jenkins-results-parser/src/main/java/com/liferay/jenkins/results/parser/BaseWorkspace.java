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
			WorkspaceUtil.getWorkspaceGitRepository(
				JenkinsWorkspaceGitRepository.TYPE, jenkinsGitHubURL, "master");
	}

	@Override
	public WorkspaceGitRepository getJenkinsWorkspaceGitRepository() {
		return _jenkinsWorkspaceGitRepository;
	}

	@Override
	public void setBuildData(BuildData buildData) {
		_buildData = buildData;
	}

	@Override
	public void setJob(Job job) {
		_job = job;
	}

	@Override
	public void setUp() {
		setUpWorkspaceGitRepositories();

		setWorkspaceDefaultProperties();

		if (_buildData != null) {
			setWorkspaceBuildDataProperties(_buildData);
		}

		if (_job != null) {
			setWorkspaceJobProperties(_job);
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

	protected abstract void setWorkspaceBuildDataProperties(
		BuildData buildData);

	protected abstract void setWorkspaceDefaultProperties();

	protected abstract void setWorkspaceJobProperties(Job job);

	protected void tearDownJenkinsWorkspaceGitRepository() {
		if (_jenkinsWorkspaceGitRepository != null) {
			_jenkinsWorkspaceGitRepository.tearDown();
		}
	}

	protected void tearDownWorkspaceGitRepositories() {
		tearDownJenkinsWorkspaceGitRepository();
	}

	protected abstract void writeWorkspaceGitRepositoryPropertiesFiles();

	private BuildData _buildData;
	private WorkspaceGitRepository _jenkinsWorkspaceGitRepository;
	private Job _job;

}