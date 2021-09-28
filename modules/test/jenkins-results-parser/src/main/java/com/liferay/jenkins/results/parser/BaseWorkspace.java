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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseWorkspace implements Workspace {

	@Override
	public JSONObject getJSONObject() {
		return _jsonObject;
	}

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

		String workspaceRepositoryDirNames = _jsonObject.getString(
			"workspace_repository_dir_names");

		if (JenkinsResultsParserUtil.isNullOrEmpty(
				workspaceRepositoryDirNames)) {

			return new ArrayList<>(_workspaceGitRepositories.values());
		}

		for (String workspaceRepositoryDirName :
				workspaceRepositoryDirNames.split(",")) {

			workspaceGitRepository workspaceGitRepository =
				GitRepositoryFactory.getWorkspaceGitRepository(
					workspaceRepositoryDirName);

			_workspaceGitRepositories.put(
				workspaceGitRepository.getDirectoryName(),
				workspaceGitRepository);
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
	public void synchronizeToGitHubDev() {
		for (WorkspaceGitRepository workspaceGitRepository :
				getWorkspaceGitRepositories()) {

			workspaceGitRepository.synchronizeToGitHubDev();
		}
	}

	@Override
	public void tearDown() {
		for (WorkspaceGitRepository workspaceGitRepository :
				getWorkspaceGitRepositories()) {

			workspaceGitRepository.tearDown();
		}
	}

	protected BaseWorkspace(JSONObject jsonObject) {
		_jsonObject = jsonObject;

		_validateKeys();

		_primaryWorkspaceGitRepository =
			GitRepositoryFactory.getWorkspaceGitRepository(
				_jsonObject.getString("primary_repository_name"),
				_jsonObject.getString("primary_upstream_branch_name"));

		BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase();

		buildDatabase.putWorkspace(this);
	}

	protected BaseWorkspace(
		String primaryRepositoryName, String upstreamBranchName) {

		_primaryWorkspaceGitRepository =
			GitRepositoryFactory.getWorkspaceGitRepository(
				primaryRepositoryName, upstreamBranchName);

		_jsonObject = new JSONObject();

		_jsonObject.put(
			"primary_repository_name",
			_primaryWorkspaceGitRepository.getName());

		_jsonObject.put(
			"primary_repository_dir_name",
			_primaryWorkspaceGitRepository.getDirectoryName());

		_jsonObject.put(
			"primary_upstream_branch_name",
			_primaryWorkspaceGitRepository.getUpstreamBranchName());

		try {
			_jsonObject.put(
				"workspace_repository_dir_names",
				JenkinsResultsParserUtil.getProperty(
					JenkinsResultsParserUtil.getBuildProperties(),
					"workspace.repository.dir.names",
					_primaryWorkspaceGitRepository.getName(),
					_primaryWorkspaceGitRepository.getUpstreamBranchName()));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		_validateKeys();

		BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase();

		buildDatabase.putWorkspace(this);
	}

	protected WorkspaceGitRepository getWorkspaceGitRepository(
		String repositoryDirName) {

		if (_workspaceGitRepositories == null) {
			getWorkspaceGitRepositories();
		}

		return _workspaceGitRepositories.get(repositoryDirName);
	}

	private void _validateKeys() {
		for (String requiredKey : _REQUIRED_KEYS) {
			if (!_jsonObject.has(requiredKey)) {
				throw new RuntimeException("Missing " + requiredKey);
			}
		}
	}

	private static final String[] _REQUIRED_KEYS = {
		"primary_repository_name", "primary_repository_dir_name",
		"primary_upstream_branch_name", "workspace_repository_dir_names"
	};

	private final JSONObject _jsonObject;
	private final WorkspaceGitRepository _primaryWorkspaceGitRepository;
	private Map<String, WorkspaceGitRepository> _workspaceGitRepositories;

}