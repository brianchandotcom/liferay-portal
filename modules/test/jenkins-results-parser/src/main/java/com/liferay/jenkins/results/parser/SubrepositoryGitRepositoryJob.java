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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SubrepositoryGitRepositoryJob
	extends GitRepositoryJob implements SubrepositoryTestClassJob {

	@Override
	public GitWorkingDirectory getGitWorkingDirectory() {
		if (gitWorkingDirectory != null) {
			return gitWorkingDirectory;
		}

		checkGitRepositoryDir();

		gitWorkingDirectory = GitWorkingDirectoryFactory.newGitWorkingDirectory(
			_upstreamBranchName, gitRepositoryDir.getPath());

		return gitWorkingDirectory;
	}

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put(
			"portal_upstream_branch_name", _portalUpstreamBranchName);
		jsonObject.put("repository_name", _repositoryName);
		jsonObject.put("upstream_branch_name", _upstreamBranchName);

		return jsonObject;
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		if (portalGitWorkingDirectory == null) {
			portalGitWorkingDirectory =
				GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
					_portalUpstreamBranchName);
		}

		return portalGitWorkingDirectory;
	}

	@Override
	public SubrepositoryGitWorkingDirectory
		getSubrepositoryGitWorkingDirectory() {

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		if (!(gitWorkingDirectory instanceof
				SubrepositoryGitWorkingDirectory)) {

			throw new RuntimeException(
				"Invalid subrepository Git working directory");
		}

		return (SubrepositoryGitWorkingDirectory)gitWorkingDirectory;
	}

	@Override
	public boolean isValidationRequired() {
		return validationRequired;
	}

	@Override
	public void setGitRepositoryDir(File repositoryDir) {
		String dirName = repositoryDir.getName();

		if (!dirName.endsWith("-private")) {
			dirName += "-private";

			repositoryDir = new File(repositoryDir.getParentFile(), dirName);
		}

		super.setGitRepositoryDir(repositoryDir);
	}

	protected SubrepositoryGitRepositoryJob(
		BuildProfile buildProfile, String jobName,
		String portalUpstreamBranchName, String repositoryName,
		String upstreamBranchName) {

		super(buildProfile, jobName, upstreamBranchName);

		_portalUpstreamBranchName = portalUpstreamBranchName;
		_repositoryName = repositoryName;
		_upstreamBranchName = upstreamBranchName;

		_initialize();
	}

	protected SubrepositoryGitRepositoryJob(JSONObject jsonObject) {
		super(jsonObject);

		_repositoryName = jsonObject.getString("repository_name");
		_portalUpstreamBranchName = jsonObject.getString(
			"portal_upstream_branch_name");
		_upstreamBranchName = jsonObject.getString("upstream_branch_name");

		_initialize();
	}

	protected PortalGitWorkingDirectory portalGitWorkingDirectory;
	protected boolean validationRequired;

	private void _initialize() {
		gitWorkingDirectory =
			GitWorkingDirectoryFactory.newSubrepositoryGitWorkingDirectory(
				_upstreamBranchName, _repositoryName);

		setGitRepositoryDir(gitWorkingDirectory.getWorkingDirectory());

		checkGitRepositoryDir();

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		jobPropertiesFiles.add(
			new File(
				portalGitWorkingDirectory.getWorkingDirectory(),
				"test.properties"));

		jobPropertiesFiles.add(
			new File(
				gitWorkingDirectory.getWorkingDirectory(), "test.properties"));

		try {
			jobPropertiesFiles.add(
				new File(
					JenkinsResultsParserUtil.combine(
						JenkinsResultsParserUtil.getProperty(
							JenkinsResultsParserUtil.getBuildProperties(),
							"base.repository.dir"),
						"/liferay-jenkins-ee/commands/dependencies",
						"/test-subrepository-batch.properties")));
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build properties", ioException);
		}
	}

	private final String _portalUpstreamBranchName;
	private final String _repositoryName;
	private final String _upstreamBranchName;

}