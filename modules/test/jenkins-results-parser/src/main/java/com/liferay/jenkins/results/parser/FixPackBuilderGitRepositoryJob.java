/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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
 * @author Kenji Heigel
 */
public class FixPackBuilderGitRepositoryJob
	extends GitRepositoryJob implements TestSuiteJob {

	@Override
	public Set<String> getDistTypes() {
		return new HashSet<>();
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
		jsonObject.put("upstream_branch_name", _upstreamBranchName);

		return jsonObject;
	}

	@Override
	public String getTestSuiteName() {
		return _testSuiteName;
	}

	protected FixPackBuilderGitRepositoryJob(
		BuildProfile buildProfile, String jobName, String testSuiteName,
		String upstreamBranchName) {

		super(buildProfile, jobName);

		_testSuiteName = testSuiteName;
		_upstreamBranchName = upstreamBranchName;

		_initialize();
	}

	protected FixPackBuilderGitRepositoryJob(JSONObject jsonObject) {
		super(jsonObject);

		_testSuiteName = jsonObject.getString("test_suite_name");
		_upstreamBranchName = jsonObject.getString("upstream_branch_name");

		_initialize();
	}

	private File _getFixPackBuilderGitRepositoryDir() {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		String fixPackBuilderDirPath = JenkinsResultsParserUtil.getProperty(
			buildProperties, "fix.pack.builder.dir", getBranchName());

		if (JenkinsResultsParserUtil.isNullOrEmpty(fixPackBuilderDirPath)) {
			throw new RuntimeException(
				"Unable to find Fix Pack Builder directory path");
		}

		File fixPackBuilderDir = new File(fixPackBuilderDirPath);

		if (!fixPackBuilderDir.exists()) {
			throw new RuntimeException(
				"Unable to find Fix Pack Builder directory");
		}

		return fixPackBuilderDir;
	}

	private String _getFixPackBuilderRepositoryName() {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		String fixPackBuilderRepository = JenkinsResultsParserUtil.getProperty(
			buildProperties, "fix.pack.builder.repository", getBranchName());

		if (JenkinsResultsParserUtil.isNullOrEmpty(fixPackBuilderRepository)) {
			throw new RuntimeException(
				"Unable to find Fix Pack Builder repository");
		}

		return fixPackBuilderRepository;
	}

	private void _initialize() {
		gitWorkingDirectory = GitWorkingDirectoryFactory.newGitWorkingDirectory(
			_upstreamBranchName, _getFixPackBuilderGitRepositoryDir(),
			_getFixPackBuilderRepositoryName());

		setGitRepositoryDir(gitWorkingDirectory.getWorkingDirectory());

		checkGitRepositoryDir();

		jobPropertiesFiles.add(
			new File(
				gitWorkingDirectory.getWorkingDirectory(), "test.properties"));
	}

	private final String _testSuiteName;
	private final String _upstreamBranchName;

}