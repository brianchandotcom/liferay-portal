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

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

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
	public String getTestSuiteName() {
		return _testSuiteName;
	}

	protected FixPackBuilderGitRepositoryJob(
		String jobName, BuildProfile buildProfile, String testSuiteName,
		String upstreamBranchName) {

		super(jobName, buildProfile);

		_testSuiteName = testSuiteName;

		_upstreamBranchName = upstreamBranchName;

		gitWorkingDirectory = GitWorkingDirectoryFactory.newGitWorkingDirectory(
			_upstreamBranchName, _getFixPackBuilderGitRepositoryDir(),
			_getFixPackBuilderRepositoryName());

		setGitRepositoryDir(gitWorkingDirectory.getWorkingDirectory());

		checkGitRepositoryDir();

		jobPropertiesFiles.add(new File(gitRepositoryDir, "test.properties"));

		readJobProperties();
	}

	@Override
	protected Set<String> getRawBatchNames() {
		return getSetFromString(
			JenkinsResultsParserUtil.getProperty(
				getJobProperties(), "test.batch.names"));
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

	private final String _testSuiteName;
	private final String _upstreamBranchName;

}