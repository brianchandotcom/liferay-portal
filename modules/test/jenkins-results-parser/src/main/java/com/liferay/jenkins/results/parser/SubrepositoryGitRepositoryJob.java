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

import java.net.URL;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Michael Hashimoto
 */
public abstract class SubrepositoryGitRepositoryJob extends GitRepositoryJob {

	@Override
	public Set<String> getBatchNames() {
		Set<String> batchNames = _getRequiredTestBatchNames();

		String testBatchNames = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.batch.names");

		if (!Objects.isNull(testBatchNames)) {
			Collections.addAll(batchNames, testBatchNames.split(","));
		}

		return batchNames;
	}

	@Override
	public Set<String> getDistTypes() {
		String distTypes = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "subrepo.dist.app.servers");

		return new TreeSet<>(Arrays.asList(distTypes.split(",")));
	}

	@Override
	public GitWorkingDirectory getGitWorkingDirectory() {
		if (gitWorkingDirectory != null) {
			return gitWorkingDirectory;
		}

		checkGitRepositoryDir();

		gitWorkingDirectory = GitWorkingDirectoryFactory.newGitWorkingDirectory(
			getBranchName(), gitRepositoryDir.getPath());

		return gitWorkingDirectory;
	}

	public String getPoshiQuery(String testBatchName) {
		Properties jobProperties = getJobProperties();

		String propertyName = JenkinsResultsParserUtil.combine(
			"test.batch.run.property.query[", testBatchName, "]");

		if (jobProperties.containsKey(propertyName)) {
			String propertyValue = JenkinsResultsParserUtil.getProperty(
				jobProperties, propertyName);

			if ((propertyValue != null) && !propertyValue.isEmpty()) {
				return propertyValue;
			}
		}

		return null;
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
		String jobName, String repositoryName) {

		super(jobName);

		gitWorkingDirectory =
			JenkinsResultsParserUtil.getSubrepositoryGitWorkingDirectory(
				jobName, repositoryName);

		setGitRepositoryDir(gitWorkingDirectory.getWorkingDirectory());

		checkGitRepositoryDir();

		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties", ioe);
		}

		jobPropertiesFiles.add(
			new File(
				JenkinsResultsParserUtil.combine(
					buildProperties.getProperty("base.repository.dir"),
					"/liferay-jenkins-ee/commands/dependencies",
					"/test-subrepository-batch.properties")));

		jobPropertiesFiles.add(new File(gitRepositoryDir, "test.properties"));
	}

	private boolean _containsIntegrationTest() throws IOException {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		List<URL> urls = JenkinsResultsParserUtil.getIncludedResourceURLs(
			new String[] {"**/src/testIntegration"},
			gitWorkingDirectory.getWorkingDirectory());

		if (urls.isEmpty()) {
			return false;
		}

		return true;
	}

	private Set<String> _getRequiredTestBatchNames() {
		String testRequiredBatchNames = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.required.batch.names");

		Set<String> testRequiredBatchNamesSet = new HashSet<>();

		try {
			if (!_containsIntegrationTest()) {
				for (String testBatchName : testRequiredBatchNames.split(",")) {
					if (!testBatchName.contains("integration")) {
						testRequiredBatchNamesSet.add(testBatchName);
					}
				}
			}
			else {
				testRequiredBatchNamesSet = new HashSet<>(
					Arrays.asList(testRequiredBatchNames.split(",")));
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to search for integration tests");
		}

		return testRequiredBatchNamesSet;
	}

}