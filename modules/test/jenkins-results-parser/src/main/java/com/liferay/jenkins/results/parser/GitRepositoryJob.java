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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class GitRepositoryJob extends BaseJob {

	public String getBranchName() {
		if (_branchName != null) {
			return _branchName;
		}

		Matcher matcher = _jobNamePattern.matcher(getJobName());

		if (matcher.find()) {
			_branchName = matcher.group("branchName");

			return _branchName;
		}

		_branchName = "master";

		return _branchName;
	}

	public GitWorkingDirectory getGitWorkingDirectory() {
		if (gitWorkingDirectory != null) {
			return gitWorkingDirectory;
		}

		checkGitRepositoryDir();

		gitWorkingDirectory = GitWorkingDirectoryFactory.newGitWorkingDirectory(
			getBranchName(),
			JenkinsResultsParserUtil.getCanonicalPath(gitRepositoryDir));

		return gitWorkingDirectory;
	}

	@Override
	public List<String> getJobPropertyOptions() {
		List<String> jobPropertyOptions = super.getJobPropertyOptions();

		jobPropertyOptions.add(getBranchName());

		jobPropertyOptions.removeAll(Collections.singleton(null));

		return jobPropertyOptions;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put("branch", _getBranchJSONObject());

		return jsonObject;
	}

	public void setGitRepositoryDir(File gitRepositoryDir) {
		if (this.gitRepositoryDir != null) {
			throw new IllegalStateException(
				"Repository directory is already set to " +
					this.gitRepositoryDir.getPath());
		}

		this.gitRepositoryDir = gitRepositoryDir;
	}

	protected GitRepositoryJob(String jobName, BuildProfile buildProfile) {
		super(jobName, buildProfile);
	}

	protected GitRepositoryJob(
		String jobName, BuildProfile buildProfile, String branchName) {

		super(jobName, buildProfile);

		_branchName = branchName;
	}

	protected void checkGitRepositoryDir() {
		if (gitRepositoryDir == null) {
			throw new IllegalStateException("Repository directory is not set");
		}

		if (!gitRepositoryDir.exists()) {
			throw new IllegalStateException(
				gitRepositoryDir.getPath() + " does not exist");
		}
	}

	protected File gitRepositoryDir;
	protected GitWorkingDirectory gitWorkingDirectory;

	private JSONObject _getBranchJSONObject() {
		JSONObject branchJSONObject = new JSONObject();

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		LocalGitBranch currentLocalGitBranch =
			gitWorkingDirectory.getCurrentLocalGitBranch();

		branchJSONObject.put(
			"current_branch_name", currentLocalGitBranch.getName());
		branchJSONObject.put(
			"current_branch_sha", currentLocalGitBranch.getSHA());

		LocalGitBranch upstreamLocalGitBranch =
			gitWorkingDirectory.getUpstreamLocalGitBranch();

		branchJSONObject.put(
			"upstream_branch_name", upstreamLocalGitBranch.getName());
		branchJSONObject.put(
			"upstream_branch_sha", upstreamLocalGitBranch.getSHA());

		branchJSONObject.put(
			"merge_branch_sha",
			gitWorkingDirectory.getMergeBaseCommitSHA(
				currentLocalGitBranch, upstreamLocalGitBranch));

		File workingDirectory = gitWorkingDirectory.getWorkingDirectory();

		List<String> modifiedFiles = new ArrayList<>();

		for (File modifiedFile : gitWorkingDirectory.getModifiedFilesList()) {
			modifiedFiles.add(
				JenkinsResultsParserUtil.getPathRelativeTo(
					modifiedFile, workingDirectory));
		}

		branchJSONObject.put("modified_files", modifiedFiles);

		if (gitWorkingDirectory instanceof PortalGitWorkingDirectory) {
			PortalGitWorkingDirectory portalGitWorkingDirectory =
				(PortalGitWorkingDirectory)gitWorkingDirectory;

			List<String> modifiedModuleDirs = new ArrayList<>();

			try {
				for (File modifiedModuleDir :
						portalGitWorkingDirectory.getModifiedModuleDirsList()) {

					modifiedModuleDirs.add(
						JenkinsResultsParserUtil.getPathRelativeTo(
							modifiedModuleDir, workingDirectory));
				}

				branchJSONObject.put("modified_modules", modifiedModuleDirs);
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}

		return branchJSONObject;
	}

	private static final Pattern _jobNamePattern = Pattern.compile(
		"[^\\(]+\\((?<branchName>[^\\)]+)\\)");

	private String _branchName;

}