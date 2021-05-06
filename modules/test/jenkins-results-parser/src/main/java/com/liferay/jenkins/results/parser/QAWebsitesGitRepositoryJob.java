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
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class QAWebsitesGitRepositoryJob
	extends GitRepositoryJob implements PortalTestClassJob, TestSuiteJob {

	public String getBranchName() {
		return _upstreamBranchName;
	}

	@Override
	public Set<String> getDistTypes() {
		return new HashSet<>();
	}

	@Override
	public GitWorkingDirectory getGitWorkingDirectory() {
		return gitWorkingDirectory;
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		return GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
			"master");
	}

	public List<String> getProjectNames() {
		return _projectNames;
	}

	@Override
	public String getTestSuiteName() {
		return _testSuiteName;
	}

	protected QAWebsitesGitRepositoryJob(
		String jobName, BuildProfile buildProfile, String testSuiteName,
		String upstreamBranchName, List<String> projectNames) {

		super(jobName, buildProfile);

		_testSuiteName = testSuiteName;
		_upstreamBranchName = upstreamBranchName;
		_projectNames = projectNames;

		gitWorkingDirectory = GitWorkingDirectoryFactory.newGitWorkingDirectory(
			_upstreamBranchName, _getQAWebsitesGitRepositoryDir(),
			_getQAWebsitesRepositoryName());

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

	private File _getQAWebsitesGitRepositoryDir() {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		String qaWebsitesDirPath = JenkinsResultsParserUtil.getProperty(
			buildProperties, "qa.websites.dir", getBranchName());

		if (JenkinsResultsParserUtil.isNullOrEmpty(qaWebsitesDirPath)) {
			throw new RuntimeException(
				"Unable to find QA Websites directory path");
		}

		File qaWebsitesDir = new File(qaWebsitesDirPath);

		if (!qaWebsitesDir.exists()) {
			throw new RuntimeException("Unable to find QA Websites directory");
		}

		return qaWebsitesDir;
	}

	private String _getQAWebsitesRepositoryName() {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		String qaWebsitesRepository = JenkinsResultsParserUtil.getProperty(
			buildProperties, "qa.websites.repository", getBranchName());

		if (JenkinsResultsParserUtil.isNullOrEmpty(qaWebsitesRepository)) {
			throw new RuntimeException("Could not find QA Websites repository");
		}

		return qaWebsitesRepository;
	}

	private final List<String> _projectNames;
	private final String _testSuiteName;
	private final String _upstreamBranchName;

}