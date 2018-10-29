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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public abstract class BasePortalTestBatch
	<T extends PortalBatchBuildData, S extends PortalWorkspace>
		extends BaseTestBatch<T, S> {

	protected BasePortalTestBatch(T batchBuildData, S workspace) {
		super(batchBuildData, workspace);
	}

	@Override
	protected void executeBatch() {
		BatchBuildData batchBuildData = getBatchBuildData();

		Map<String, String> buildParameters = new HashMap<>();

		buildParameters.put(
			"axis.variable",
			JenkinsResultsParserUtil.join(",", batchBuildData.getTestList()));

		String batchName = batchBuildData.getBatchName();

		Map<String, String> environmentVariables = new HashMap<>();

		if (JenkinsResultsParserUtil.isCINode()) {
			environmentVariables.put("ANT_OPTS", getAntOpts(batchName));
			environmentVariables.put("JAVA_HOME", getJavaHome(batchName));
			environmentVariables.put("PATH", getPath(batchName));
		}

		AntUtil.callTarget(
			getPrimaryPortalWorkspaceDirectory(), "build-test-batch.xml",
			batchBuildData.getBatchName(), buildParameters,
			environmentVariables);
	}

	@Override
	protected T getBatchBuildData() {
		return super.getBatchBuildData();
	}

	protected File getPrimaryPortalWorkspaceDirectory() {
		PortalWorkspace portalWorkspace = getWorkspace();

		WorkspaceGitRepository workspaceGitRepository =
			portalWorkspace.getPrimaryPortalWorkspaceGitRepository();

		return workspaceGitRepository.getDirectory();
	}

	@Override
	protected S getWorkspace() {
		return super.getWorkspace();
	}

	@Override
	protected void publishResults() {
		AntUtil.callTarget(
			getPrimaryPortalWorkspaceDirectory(), "build-test.xml",
			"merge-test-results");

		File source = new File(
			getPrimaryPortalWorkspaceDirectory(),
			"test-results/TESTS-TestSuites.xml");

		if (!source.exists()) {
			return;
		}

		BatchBuildData batchBuildData = getBatchBuildData();

		File target = new File(
			batchBuildData.getWorkspaceDir(),
			"test-results/TESTS-TestSuites.xml");

		try {
			JenkinsResultsParserUtil.copy(source, target);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to copy test results file from ", source.getPath(),
					" to ", target.getPath()),
				ioe);
		}
	}

}