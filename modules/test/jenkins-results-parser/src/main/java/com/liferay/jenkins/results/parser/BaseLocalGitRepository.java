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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Peter Yoo
 * @author Michael Hashimoto
 */
public abstract class BaseLocalGitRepository
	extends BaseGitRepository implements LocalGitRepository {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BaseLocalGitRepository)) {
			return false;
		}

		BaseLocalGitRepository baseLocalGitRepository =
			(BaseLocalGitRepository)obj;

		if (Objects.equals(
				getDirectory(), baseLocalGitRepository.getDirectory()) &&
			Objects.equals(
				getGitWorkingDirectory(),
				baseLocalGitRepository.getGitWorkingDirectory()) &&
			JenkinsResultsParserUtil.isJSONObjectEqual(
				getJSONObject(), baseLocalGitRepository.getJSONObject()) &&
			Objects.equals(getName(), baseLocalGitRepository.getName()) &&
			Objects.equals(
				getUpstreamBranchName(),
				baseLocalGitRepository.getUpstreamBranchName())) {

			return true;
		}

		return false;
	}

	@Override
	public File getDirectory() {
		return getFile("directory");
	}

	@Override
	public GitWorkingDirectory getGitWorkingDirectory() {
		if (_gitWorkingDirectory != null) {
			return _gitWorkingDirectory;
		}

		_gitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				getUpstreamBranchName(), getDirectory(), getName());

		return _gitWorkingDirectory;
	}

	@Override
	public List<LocalGitCommit> getRangeLocalGitCommits(
		String earliestSHA, String latestSHA) {

		List<LocalGitCommit> rangeLocalGitCommits = new ArrayList<>();

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		int index = 0;

		while (index < COMMITS_HISTORY_SIZE_MAX) {
			int currentGroupSize = COMMITS_HISTORY_GROUP_SIZE;

			if (index >
					(COMMITS_HISTORY_SIZE_MAX - COMMITS_HISTORY_GROUP_SIZE)) {

				currentGroupSize =
					COMMITS_HISTORY_SIZE_MAX % COMMITS_HISTORY_GROUP_SIZE;
			}

			List<LocalGitCommit> localGitCommits = gitWorkingDirectory.log(
				index, currentGroupSize, latestSHA);

			for (LocalGitCommit localGitCommit : localGitCommits) {
				rangeLocalGitCommits.add(localGitCommit);

				if (earliestSHA.equals(localGitCommit.getSHA())) {
					return rangeLocalGitCommits;
				}
			}

			index += COMMITS_HISTORY_GROUP_SIZE;
		}

		return rangeLocalGitCommits;
	}

	@Override
	public String getUpstreamBranchName() {
		return getString("upstream_branch_name");
	}

	@Override
	public int hashCode() {
		File directory = getDirectory();

		String hash = JenkinsResultsParserUtil.combine(
			JenkinsResultsParserUtil.getCanonicalPath(directory), getName(),
			getUpstreamBranchName());

		return hash.hashCode();
	}

	protected BaseLocalGitRepository(JSONObject jsonObject) {
		super(jsonObject);

		validateKeys(_KEYS_REQUIRED);
	}

	protected BaseLocalGitRepository(String name, String upstreamBranchName) {
		super(name);

		_setDirectory(upstreamBranchName);
		_setUpstreamBranchName(upstreamBranchName);

		validateKeys(_KEYS_REQUIRED);
	}

	protected String getDefaultRelativeGitRepositoryDirPath(
		String upstreamBranchName) {

		return getName();
	}

	private void _setDirectory(String upstreamBranchName) {
		File directory = null;

		String repositoryDirPath = JenkinsResultsParserUtil.getProperty(
			getRepositoryProperties(), "repository.dir", getName(),
			upstreamBranchName);

		if (repositoryDirPath != null) {
			directory = new File(repositoryDirPath);
		}

		if ((directory == null) || !directory.exists()) {
			directory = new File(
				JenkinsResultsParserUtil.getBaseGitRepositoryDir(),
				getDefaultRelativeGitRepositoryDirPath(upstreamBranchName));
		}

		try {
			put(
				"directory",
				JenkinsResultsParserUtil.getCanonicalPath(directory));
		}
		catch (RuntimeException re) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to find Git repository directory.\n",
					"Please set this location in repository.dir[", getName(),
					"][", getUpstreamBranchName(),
					"] in repository.properties."),
				re);
		}
	}

	private void _setUpstreamBranchName(String upstreamBranchName) {
		if ((upstreamBranchName == null) || upstreamBranchName.isEmpty()) {
			throw new IllegalArgumentException("Upstream branch name is null");
		}

		String upstreamBranchNamesString = JenkinsResultsParserUtil.getProperty(
			getRepositoryProperties(), "upstream.branch.names", getName());

		if (upstreamBranchNamesString != null) {
			List<String> upstreamBranchNames = Arrays.asList(
				upstreamBranchNamesString.split(","));

			if (!upstreamBranchNames.contains(upstreamBranchName)) {
				throw new IllegalArgumentException(
					"Invalid upstream branch name: " + upstreamBranchName);
			}
		}

		put("upstream_branch_name", upstreamBranchName);
	}

	private static final String[] _KEYS_REQUIRED = {
		"directory", "upstream_branch_name"
	};

	private GitWorkingDirectory _gitWorkingDirectory;

}