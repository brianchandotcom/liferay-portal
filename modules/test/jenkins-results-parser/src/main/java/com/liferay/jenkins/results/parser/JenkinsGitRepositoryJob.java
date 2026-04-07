/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JenkinsGitRepositoryJob extends GitRepositoryJob {

	@Override
	public Set<String> getAppServerTypes() {
		return new HashSet<>();
	}

	@Override
	public String getBranchName() {
		return "master";
	}

	@Override
	public GitWorkingDirectory getGitWorkingDirectory() {
		return gitWorkingDirectory;
	}

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put("test_suite_name", _testSuiteName);

		return jsonObject;
	}

	@Override
	public Set<String> getRawBatchNames() {
		return new HashSet<>();
	}

	@Override
	public String getRepositoryName() {
		return "liferay-jenkins-ee";
	}

	@Override
	public String getTestSuiteName() {
		return _testSuiteName;
	}

	protected JenkinsGitRepositoryJob(
		BuildProfile buildProfile, String jobName, String testSuiteName) {

		super(buildProfile, jobName);

		_testSuiteName = testSuiteName;

		_initialize();
	}

	protected JenkinsGitRepositoryJob(JSONObject jsonObject) {
		super(jsonObject);

		_testSuiteName = jsonObject.getString("test_suite_name");

		_initialize();
	}

	private File _getJenkinsGitRepositoryDir() {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		String jenkinsDirPath = JenkinsResultsParserUtil.getProperty(
			buildProperties, "jenkins.dir", getBranchName());

		if (JenkinsResultsParserUtil.isNullOrEmpty(jenkinsDirPath)) {
			String baseRepositoryDirPath = JenkinsResultsParserUtil.getProperty(
				buildProperties, "base.repository.dir");

			jenkinsDirPath = baseRepositoryDirPath + "/" + getRepositoryName();
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(jenkinsDirPath)) {
			throw new RuntimeException("Unable to find Jenkins directory path");
		}

		File jenkinsDir = new File(jenkinsDirPath);

		if (!jenkinsDir.exists()) {
			throw new RuntimeException("Unable to find Jenkins directory");
		}

		return jenkinsDir;
	}

	private void _initialize() {
		gitWorkingDirectory = GitWorkingDirectoryFactory.newGitWorkingDirectory(
			getBranchName(), _getJenkinsGitRepositoryDir(),
			getRepositoryName());

		setGitRepositoryDir(gitWorkingDirectory.getWorkingDirectory());

		checkGitRepositoryDir();

		jobPropertiesFiles.add(new File(gitRepositoryDir, "test.properties"));
	}

	private final String _testSuiteName;

}