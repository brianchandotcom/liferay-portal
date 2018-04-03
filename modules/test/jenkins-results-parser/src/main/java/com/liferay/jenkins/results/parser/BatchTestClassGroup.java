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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public abstract class BatchTestClassGroup extends BaseTestClassGroup {

	public int getAxisCount() {
		return axisTestClassGroups.size();
	}

	public AxisTestClassGroup getAxisTestClassGroup(int axisId) {
		return axisTestClassGroups.get(axisId);
	}

	public String getBatchName() {
		return batchName;
	}

	public GitWorkingDirectory getGitWorkingDirectory() {
		return gitWorkingDirectory;
	}

	public Properties getPortalTestProperties() {
		return portalTestProperties;
	}

	protected BatchTestClassGroup(
		String batchName, GitWorkingDirectory gitWorkingDirectory,
		String testSuiteName) {

		this.batchName = batchName;
		this.gitWorkingDirectory = gitWorkingDirectory;
		this.testSuiteName = testSuiteName;

		portalTestProperties = JenkinsResultsParserUtil.getProperties(
			new File(
				this.gitWorkingDirectory.getWorkingDirectory(),
				"test.properties"));
	}

	protected final Map<Integer, AxisTestClassGroup> axisTestClassGroups =
		new HashMap<>();
	protected final String batchName;
	protected final GitWorkingDirectory gitWorkingDirectory;
	protected final Properties portalTestProperties;
	protected final String testSuiteName;

}