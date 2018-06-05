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

import java.util.Set;

/**
 * @author Leslie Wong
 */
public class PortalUpstreamJob
	extends PortalRepositoryJob implements BatchDependentJob {

	public PortalUpstreamJob(String jobName) {
		super(jobName);

		try {
			GitWorkingDirectory jenkinsGitWorkingDirectory =
				JenkinsResultsParserUtil.getJenkinsGitWorkingDirectory();

			jobProperties.putAll(
				JenkinsResultsParserUtil.getProperties(
					new File(
						jenkinsGitWorkingDirectory.getWorkingDirectory(),
						"commands/dependencies" +
							"/test-upstream-batch.properties")));
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to create a Git working directory", ioe);
		}
	}

	@Override
	public Set<String> getBatchNames() {
		String testBatchNames = JenkinsResultsParserUtil.getProperty(
			jobProperties,
			"test.batch.names[portal-upstream(" + getBranchName() + ")]");

		return getSetFromString(testBatchNames);
	}

	@Override
	public Set<String> getDependentBatchNames() {
		String testBatchNames = JenkinsResultsParserUtil.getProperty(
			jobProperties, "test.batch.names.smoke[" + getBranchName() + "]");

		return getSetFromString(testBatchNames);
	}

}