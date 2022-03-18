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
		return _upstreamBranchName;
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
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put("branch", _getBranchJSONObject());

		jsonObject.put("git_repository_dir", gitRepositoryDir);
		jsonObject.put("upstream_branch_name", _upstreamBranchName);

		return jsonObject;
	}

	public String getUpstreamBranchName() {
		return _upstreamBranchName;
	}

	public void setGitRepositoryDir(File gitRepositoryDir) {
		this.gitRepositoryDir = gitRepositoryDir;
	}

	protected GitRepositoryJob(BuildProfile buildProfile, String jobName) {
		this(buildProfile, jobName, null);
	}

	protected GitRepositoryJob(
		BuildProfile buildProfile, String jobName, String upstreamBranchName) {

		super(buildProfile, jobName);

		if (JenkinsResultsParserUtil.isNullOrEmpty(upstreamBranchName)) {
			upstreamBranchName = "master";

			Matcher matcher = _jobNamePattern.matcher(getJobName());

			if (matcher.find()) {
				upstreamBranchName = matcher.group("upstreamBranchName");
			}
		}

		_upstreamBranchName = upstreamBranchName;
	}

	protected GitRepositoryJob(JSONObject jsonObject) {
		super(jsonObject);

		gitRepositoryDir = new File(jsonObject.getString("git_repository_dir"));
		_upstreamBranchName = jsonObject.getString("upstream_branch_name");
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
		if ((jsonObject != null) && jsonObject.has("branch")) {
			return jsonObject.getJSONObject("branch");
		}

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
		"[^\\(]+\\((?<upstreamBranchName>[^\\)]+)\\)");

	private final String _upstreamBranchName;

}