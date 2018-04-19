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

import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

/**
 * @author Michael Hashimoto
 */
public abstract class PortalRepositoryJob extends RepositoryJob {

	@Override
	public Set<String> getBatchNames() {
		String testBatchNames = getProperty(
			getPortalTestProperties(), "test.batch.names");

		return getSetFromString(testBatchNames);
	}

	@Override
	public Set<String> getDistTypes() {
		String testBatchDistAppServers = getProperty(
			getPortalTestProperties(), "test.batch.dist.app.servers");

		return getSetFromString(testBatchDistAppServers);
	}

	@Override
	public GitWorkingDirectory getGitWorkingDirectory() {
		if (gitWorkingDirectory != null) {
			return gitWorkingDirectory;
		}

		checkRepositoryDir();

		try {
			gitWorkingDirectory = new PortalGitWorkingDirectory(
				getBranchName(), repositoryDir.getAbsolutePath());
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to create portal Git working directory " +
					repositoryDir.getPath(),
				ioe);
		}

		return gitWorkingDirectory;
	}

	public String getPoshiQuery(String testBatchName) {
		String propertyName = JenkinsResultsParserUtil.combine(
			"test.batch.run.property.query[", testBatchName, "]");

		Properties portalTestProperties = getPortalTestProperties();

		if (portalTestProperties.containsKey(propertyName)) {
			String propertyValue = getProperty(
				portalTestProperties, propertyName);

			if ((propertyValue != null) && !propertyValue.isEmpty()) {
				return propertyValue;
			}
		}

		return null;
	}

	@Override
	public void setRepositoryDir(File repositoryDir) {
	}

	protected PortalRepositoryJob(String jobName) {
		super(jobName);

		_findRepositoryDir();
	}

	protected Properties getPortalTestProperties() {
		if (portalTestProperties != null) {
			return portalTestProperties;
		}

		checkRepositoryDir();

		portalTestProperties = JenkinsResultsParserUtil.getProperties(
			new File(repositoryDir, "test.properties"));

		return portalTestProperties;
	}

	protected Set<String> getSetFromString(String string) {
		if (string == null) {
			return Collections.emptySet();
		}

		Set<String> set = new TreeSet<>();

		for (String item : StringUtils.split(string, ",")) {
			if (item.startsWith("#")) {
				continue;
			}

			set.add(item.trim());
		}

		return set;
	}

	protected Properties portalTestProperties;

	private void _findRepositoryDir() {
		String branchName = getBranchName();

		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties", ioe);
		}

		String workingDirectoryPath = buildProperties.getProperty(
			"base.repository.dir") + "/liferay-portal";

		if (!branchName.equals("master")) {
			workingDirectoryPath = JenkinsResultsParserUtil.combine(
				workingDirectoryPath, "-", branchName);
		}

		super.setRepositoryDir(new File(workingDirectoryPath));
	}

}