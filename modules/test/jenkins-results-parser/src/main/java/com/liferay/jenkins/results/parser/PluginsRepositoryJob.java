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

import java.util.Properties;
import java.util.Set;

/**
 * @author Peter Yoo
 */
public class PluginsRepositoryJob
	extends RepositoryJob implements PortalTestClassJob {

	@Override
	public Set<String> getBatchNames() {
		String testBatchNames = JenkinsResultsParserUtil.getProperty(
			jobProperties, "test.batch.names");

		return getSetFromString(testBatchNames);
	}

	@Override
	public Set<String> getDistTypes() {
		String testBatchDistAppServers = JenkinsResultsParserUtil.getProperty(
			jobProperties, "test.batch.dist.app.servers");

		return getSetFromString(testBatchDistAppServers);
	}

	@Override
	public GitWorkingDirectory getGitWorkingDirectory() {
		if (gitWorkingDirectory != null) {
			return gitWorkingDirectory;
		}

		String pluginsBranchName = getBranchName();

		String workingDirectoryPath = getBuildPropertyValue(
			JenkinsResultsParserUtil.combine(
				"plugins.dir[", pluginsBranchName, "]"));

		gitWorkingDirectory = GitWorkingDirectoryFactory.newGitWorkingDirectory(
			pluginsBranchName, workingDirectoryPath);

		return gitWorkingDirectory;
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		return portalGitWorkingDirectory;
	}

	public String getPoshiQuery(String testBatchName) {
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

	protected PluginsRepositoryJob(String jobName) {
		super(jobName);

		getGitWorkingDirectory();

		setRepositoryDir(gitWorkingDirectory.getWorkingDirectory());

		checkRepositoryDir();

		String portalBranchName = getBuildPropertyValue(
			JenkinsResultsParserUtil.combine(
				"plugins.portal.branch.name[", getBranchName(), "]"));

		File portalRepositoryDir = new File(
			getBuildPropertyValue(
				JenkinsResultsParserUtil.combine(
					"portal.dir[", portalBranchName, "]")));

		jobProperties.putAll(
			JenkinsResultsParserUtil.getProperties(
				new File(portalRepositoryDir, "test.properties")));

		portalGitWorkingDirectory =
			(PortalGitWorkingDirectory)
				GitWorkingDirectoryFactory.newGitWorkingDirectory(
					portalBranchName, portalRepositoryDir.getPath());
	}

	protected String getBuildPropertyValue(String buildPropertyName) {
		if (buildProperties == null) {
			try {
				buildProperties = JenkinsResultsParserUtil.getBuildProperties();
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to get build properties", ioe);
			}
		}

		return buildProperties.getProperty(buildPropertyName);
	}

	protected Properties buildProperties;
	protected PortalGitWorkingDirectory portalGitWorkingDirectory;

}