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
			portalTestProperties, "test.batch.names");

		return getSetFromString(testBatchNames);
	}

	@Override
	public Set<String> getDistTypes() {
		String testBatchDistAppServers = getProperty(
			portalTestProperties, "test.batch.dist.app.servers");

		return getSetFromString(testBatchDistAppServers);
	}

	@Override
	public GitWorkingDirectory getGitWorkingDirectory() {
		return getPortalGitWorkingDirectory();
	}

	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		if ((gitWorkingDirectory != null) &&
			gitWorkingDirectory instanceof PortalGitWorkingDirectory) {

			return (PortalGitWorkingDirectory)gitWorkingDirectory;
		}

		String workingDirectoryPath = "/opt/dev/projects/github/liferay-portal";

		if (!branchName.equals("master")) {
			workingDirectoryPath = JenkinsResultsParserUtil.combine(
				workingDirectoryPath, "-", branchName);
		}

		PortalGitWorkingDirectory portalGitWorkingDirectory = null;

		try {
			portalGitWorkingDirectory = new PortalGitWorkingDirectory(
				branchName, workingDirectoryPath);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Invalid Git working directory " + workingDirectoryPath, ioe);
		}

		gitWorkingDirectory = portalGitWorkingDirectory;

		return portalGitWorkingDirectory;
	}

	public String getPoshiQuery(String testBatchName) {
		String propertyName = JenkinsResultsParserUtil.combine(
			"test.batch.run.property.query[", testBatchName, "]");

		if (portalTestProperties.containsKey(propertyName)) {
			String propertyValue = getProperty(
				portalTestProperties, propertyName);

			if ((propertyValue != null) && !propertyValue.isEmpty()) {
				return propertyValue;
			}
		}

		return null;
	}

	protected PortalRepositoryJob(String jobName) {
		super(jobName);

		gitWorkingDirectory = getPortalGitWorkingDirectory();

		portalTestProperties = JenkinsResultsParserUtil.getProperties(
			new File(
				gitWorkingDirectory.getWorkingDirectory(), "test.properties"));
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

			set.add(item);
		}

		return set;
	}

	protected final Properties portalTestProperties;

}