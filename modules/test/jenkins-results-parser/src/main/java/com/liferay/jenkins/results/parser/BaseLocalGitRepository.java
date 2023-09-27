/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.ArrayList;
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BaseLocalGitRepository)) {
			return false;
		}

		BaseLocalGitRepository baseLocalGitRepository =
			(BaseLocalGitRepository)object;

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
		String directoryPath = getString("directory");

		if (JenkinsResultsParserUtil.isWindows() &&
			directoryPath.startsWith("/")) {

			directoryPath = "C:" + directoryPath;
		}

		return new File(directoryPath);
	}

	@Override
	public String getDirectoryName() {
		return getString("directory_name");
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
		String hash = JenkinsResultsParserUtil.combine(
			JenkinsResultsParserUtil.getCanonicalPath(getDirectory()),
			getName(), getUpstreamBranchName());

		return hash.hashCode();
	}

	protected BaseLocalGitRepository(JSONObject jsonObject) {
		super(jsonObject);

		validateKeys(_KEYS_REQUIRED);
	}

	protected BaseLocalGitRepository(String name, String upstreamBranchName) {
		super(name);

		if (JenkinsResultsParserUtil.isNullOrEmpty(upstreamBranchName)) {
			throw new IllegalArgumentException("Upstream branch name is null");
		}

		_setDirectoryName(upstreamBranchName);
		_setUpstreamBranchName(upstreamBranchName);

		File directory = new File(
			JenkinsResultsParserUtil.getBaseGitRepositoryDir(),
			getDirectoryName());

		if (!directory.exists()) {
			throw new IllegalArgumentException("Unable to find " + directory);
		}

		_setDirectory(directory);

		validateKeys(_KEYS_REQUIRED);
	}

	protected BaseLocalGitRepository(
		String name, String upstreamBranchName, File repositoryDir) {

		super(name);

		if (JenkinsResultsParserUtil.isNullOrEmpty(upstreamBranchName)) {
			throw new IllegalArgumentException("Upstream branch name is null");
		}

		_setUpstreamBranchName(upstreamBranchName);

		if (!repositoryDir.exists()) {
			throw new IllegalArgumentException(
				"Unable to find " + repositoryDir);
		}

		put("directory_name", repositoryDir.getName());

		_setDirectory(repositoryDir);

		validateKeys(_KEYS_REQUIRED);
	}

	private void _setDirectory(File directory) {
		put("directory", JenkinsResultsParserUtil.getCanonicalPath(directory));
	}

	private void _setDirectoryName(String upstreamBranchName) {
		String gitDirectoryName = JenkinsResultsParserUtil.getGitDirectoryName(
			getName(), upstreamBranchName);

		if (JenkinsResultsParserUtil.isNullOrEmpty(gitDirectoryName)) {
			gitDirectoryName = getName();
		}

		put("directory_name", gitDirectoryName);
	}

	private void _setUpstreamBranchName(String upstreamBranchName) {
		if (JenkinsResultsParserUtil.isNullOrEmpty(upstreamBranchName)) {
			throw new IllegalArgumentException("Upstream branch name is null");
		}

		put("upstream_branch_name", upstreamBranchName);
	}

	private static final String[] _KEYS_REQUIRED = {
		"directory", "directory_name", "upstream_branch_name"
	};

	private GitWorkingDirectory _gitWorkingDirectory;

}