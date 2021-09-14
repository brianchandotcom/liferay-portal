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

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalEnvironmentJob;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;

import java.io.File;

import java.util.Arrays;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class EnvironmentFunctionalBatchTestClassGroup
	extends FunctionalBatchTestClassGroup {

	@Override
	public String getBatchJobName() {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			portalTestClassJob.getPortalGitWorkingDirectory();

		return JenkinsResultsParserUtil.combine(
			getBatchName(), "(",
			portalGitWorkingDirectory.getUpstreamBranchName(), ")");
	}

	@Override
	public List<File> getTestBaseDirs() {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		return Arrays.asList(
			new File(
				portalGitWorkingDirectory.getWorkingDirectory(),
				"portal-web/test/functional"));
	}

	protected EnvironmentFunctionalBatchTestClassGroup(
		String batchName, PortalEnvironmentJob portalEnvironmentJob) {

		super(batchName, portalEnvironmentJob);
	}

	@Override
	protected String getDefaultTestBatchRunPropertyQuery(
		File testBaseDir, String testSuiteName) {

		String propertyQuery = System.getenv("TEST_BATCH_RUN_PROPERTY_QUERY");

		if (JenkinsResultsParserUtil.isNullOrEmpty(propertyQuery)) {
			propertyQuery = getBuildStartProperty(
				"TEST_BATCH_RUN_PROPERTY_QUERY");
		}

		if ((propertyQuery != null) && !propertyQuery.isEmpty()) {
			return propertyQuery;
		}

		String jobName = portalTestClassJob.getJobName();

		return JenkinsResultsParserUtil.getProperty(
			portalTestClassJob.getJobProperties(),
			"test.batch.run.property.query",
			jobName.replaceAll("([^\\(]+)\\([^\\)]+\\)", "$1"));
	}

}