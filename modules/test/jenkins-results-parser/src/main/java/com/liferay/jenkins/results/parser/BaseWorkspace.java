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

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseWorkspace implements Workspace {

	@Override
	public WorkspaceGitRepository getPrimaryWorkspaceGitRepository() {
		return _primaryWorkspaceGitRepository;
	}

	@Override
	public List<WorkspaceGitRepository> getWorkspaceGitRepositories() {
		if (_workspaceGitRepositories != null) {
			return new ArrayList<>(_workspaceGitRepositories.values());
		}

		_workspaceGitRepositories = new HashMap<>();

		if (JenkinsResultsParserUtil.isNullOrEmpty(
				_workspaceRepositoryDirNames)) {

			return new ArrayList<>(_workspaceGitRepositories.values());
		}

		for (String workspaceRepositoryDirName :
				_workspaceRepositoryDirNames.split(",")) {

			_workspaceGitRepositories.put(
				workspaceRepositoryDirName,
				GitRepositoryFactory.getWorkspaceGitRepository(
					workspaceRepositoryDirName));
		}

		return new ArrayList<>(_workspaceGitRepositories.values());
	}

	@Override
	public void setUp() {
		for (WorkspaceGitRepository workspaceGitRepository :
				getWorkspaceGitRepositories()) {

			workspaceGitRepository.setUp();

			workspaceGitRepository.writePropertiesFiles();
		}
	}

	@Override
	public void tearDown() {
		for (WorkspaceGitRepository workspaceGitRepository :
				getWorkspaceGitRepositories()) {

			workspaceGitRepository.tearDown();
		}
	}

	protected BaseWorkspace(
		String primaryRepositoryName, String upstreamBranchName) {

		_primaryWorkspaceGitRepository =
			GitRepositoryFactory.getWorkspaceGitRepository(
				primaryRepositoryName, upstreamBranchName);

		try {
			_workspaceRepositoryDirNames = JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"workspace.repository.dir.names",
				workspaceGitRepository.getName(),
				workspaceGitRepository.getUpstreamBranchName());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected WorkspaceGitRepository getWorkspaceGitRepository(
		String repositoryDirName) {

		if (_workspaceGitRepositories == null) {
			getWorkspaceGitRepositories();
		}

		return _workspaceGitRepositories.get(repositoryDirName);
	}

	private final WorkspaceGitRepository _primaryWorkspaceGitRepository;
	private Map<String, WorkspaceGitRepository> _workspaceGitRepositories;
	private final String _workspaceRepositoryDirNames;

}