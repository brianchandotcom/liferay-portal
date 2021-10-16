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
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseWorkspace implements Workspace {

	@Override
	public JSONObject getJSONObject() {
		return jsonObject;
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

		String workspaceRepositoryDirNames = jsonObject.getString(
			"workspace_repository_dir_names");

		if (JenkinsResultsParserUtil.isNullOrEmpty(
				workspaceRepositoryDirNames)) {

			return new ArrayList<>(_workspaceGitRepositories.values());
		}

		List<Callable<WorkspaceGitRepository>> callables = new ArrayList<>();

		for (final String workspaceRepositoryDirName :
				workspaceRepositoryDirNames.split(",")) {

			Callable<WorkspaceGitRepository> callable =
				new Callable<WorkspaceGitRepository>() {

					@Override
					public WorkspaceGitRepository call() {
						return GitRepositoryFactory.getWorkspaceGitRepository(
							workspaceRepositoryDirName);
					}

				};

			callables.add(callable);
		}

		ParallelExecutor<WorkspaceGitRepository> parallelExecutor =
			new ParallelExecutor<>(callables, threadPoolExecutor);

		List<WorkspaceGitRepository> workspaceGitRepositories =
			parallelExecutor.execute();

		for (WorkspaceGitRepository workspaceGitRepository :
				workspaceGitRepositories) {

			_workspaceGitRepositories.put(
				workspaceGitRepository.getDirectoryName(),
				workspaceGitRepository);
		}

		return new ArrayList<>(_workspaceGitRepositories.values());
	}

	@Override
	public WorkspaceGitRepository getWorkspaceGitRepository(
		String repositoryDirName) {

		if (_workspaceGitRepositories == null) {
			getWorkspaceGitRepositories();
		}

		return _workspaceGitRepositories.get(repositoryDirName);
	}

	@Override
	public void setUp() {
		List<Callable<Object>> callables = new ArrayList<>();

		for (final WorkspaceGitRepository workspaceGitRepository :
				getWorkspaceGitRepositories()) {

			Callable<Object> callable = new Callable<Object>() {

				@Override
				public Object call() {
					workspaceGitRepository.setUp();

					return null;
				}

			};

			callables.add(callable);
		}

		ParallelExecutor<Object> parallelExecutor = new ParallelExecutor<>(
			callables, threadPoolExecutor);

		parallelExecutor.execute();
	}

	@Override
	public synchronized void startSynchronizeToGitHubDev() {
		if (_parallelExecutor != null) {
			return;
		}

		List<Callable<Object>> callables = new ArrayList<>();

		for (final WorkspaceGitRepository workspaceGitRepository :
				getWorkspaceGitRepositories()) {

			Callable<Object> callable = new Callable<Object>() {

				@Override
				public Object call() {
					workspaceGitRepository.synchronizeToGitHubDev();

					return null;
				}

			};

			callables.add(callable);
		}

		_parallelExecutor = new ParallelExecutor<>(
			callables, threadPoolExecutor);

		_parallelExecutor.start();
	}

	@Override
	public synchronized void synchronizeToGitHubDev() {
		startSynchronizeToGitHubDev();

		waitForSynchronizeToGitHubDev();
	}

	@Override
	public void tearDown() {
		List<Callable<Object>> callables = new ArrayList<>();

		for (final WorkspaceGitRepository workspaceGitRepository :
				getWorkspaceGitRepositories()) {

			Callable<Object> callable = new Callable<Object>() {

				@Override
				public Object call() {
					try {
						workspaceGitRepository.tearDown();
					}
					catch (Exception exception) {
						exception.printStackTrace();
					}

					return null;
				}

			};

			callables.add(callable);
		}

		ParallelExecutor<Object> parallelExecutor = new ParallelExecutor<>(
			callables, threadPoolExecutor);

		parallelExecutor.execute();
	}

	@Override
	public void waitForSynchronizeToGitHubDev() {
		if (_parallelExecutor == null) {
			throw new RuntimeException(
				"Synchronize to GitHub dev did not start");
		}

		_parallelExecutor.waitFor();
	}

	protected BaseWorkspace(JSONObject jsonObject) {
		this.jsonObject = jsonObject;

		_validateKeys();

		_primaryWorkspaceGitRepository =
			GitRepositoryFactory.getWorkspaceGitRepository(
				this.jsonObject.getString("primary_repository_name"),
				this.jsonObject.getString("primary_upstream_branch_name"));
	}

	protected BaseWorkspace(
		String primaryRepositoryName, String upstreamBranchName) {

		this(primaryRepositoryName, upstreamBranchName, null);
	}

	protected BaseWorkspace(
		String primaryRepositoryName, String upstreamBranchName,
		String jobName) {

		_primaryWorkspaceGitRepository =
			GitRepositoryFactory.getWorkspaceGitRepository(
				primaryRepositoryName, upstreamBranchName);

		jsonObject = new JSONObject();

		jsonObject.put(
			"primary_repository_dir_name",
			_primaryWorkspaceGitRepository.getDirectoryName());
		jsonObject.put(
			"primary_repository_name",
			_primaryWorkspaceGitRepository.getName());
		jsonObject.put(
			"primary_upstream_branch_name",
			_primaryWorkspaceGitRepository.getUpstreamBranchName());

		try {
			jsonObject.put(
				"workspace_repository_dir_names",
				JenkinsResultsParserUtil.getProperty(
					JenkinsResultsParserUtil.getBuildProperties(),
					"workspace.repository.dir.names",
					_primaryWorkspaceGitRepository.getName(),
					_primaryWorkspaceGitRepository.getUpstreamBranchName(),
					jobName));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		_validateKeys();
	}

	protected static final ThreadPoolExecutor threadPoolExecutor =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(16, true);

	protected final JSONObject jsonObject;

	private void _validateKeys() {
		for (String requiredKey : _REQUIRED_KEYS) {
			if (!jsonObject.has(requiredKey)) {
				throw new RuntimeException("Missing " + requiredKey);
			}
		}
	}

	private static final String[] _REQUIRED_KEYS = {
		"primary_repository_dir_name", "primary_repository_name",
		"primary_upstream_branch_name", "workspace_repository_dir_names"
	};

	private ParallelExecutor<Object> _parallelExecutor;
	private final WorkspaceGitRepository _primaryWorkspaceGitRepository;
	private Map<String, WorkspaceGitRepository> _workspaceGitRepositories;

}