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
public abstract class BasePortalTestBatch<T extends PortalBatchBuildData>
	extends BaseTestBatch<T> {

	protected BasePortalTestBatch(T batchBuildData, Workspace workspace) {
		super(batchBuildData, workspace);
	}

	@Override
	protected void executeBatch() throws AntException {
		PortalBatchBuildData portalBatchBuildData = getBatchBuildData();

		Map<String, String> buildParameters = new HashMap<>();

		buildParameters.put(
			"axis.variable",
			JenkinsResultsParserUtil.join(
				",", portalBatchBuildData.getTestList()));

		buildParameters.put(
			"test.batch.name", portalBatchBuildData.getBatchName());

		Map<String, String> environmentVariables = new HashMap<>();

		environmentVariables.put(
			"TEST_PORTAL_BRANCH_NAME",
			portalBatchBuildData.getPortalUpstreamBranchName());

		if (JenkinsResultsParserUtil.isCINode()) {
			String batchName = portalBatchBuildData.getBatchName();

			environmentVariables.put("ANT_OPTS", getAntOpts(batchName));
			environmentVariables.put("JAVA_HOME", getJavaHome(batchName));
			environmentVariables.put("PATH", getPath(batchName));
		}

		environmentVariables.putAll(
			portalBatchBuildData.getTopLevelBuildParameters());

		environmentVariables.putAll(portalBatchBuildData.getBuildParameters());

		AntUtil.callTarget(
			getPrimaryPortalWorkspaceDirectory(), "build-test-batch.xml",
			portalBatchBuildData.getBatchName(), buildParameters,
			environmentVariables, getAntLibDir());
	}

	protected File getAntLibDir() {
		File antLibDir = new File(System.getenv("WORKSPACE"), "lib");

		if (antLibDir.exists()) {
			return antLibDir;
		}

		return null;
	}

	@Override
	protected T getBatchBuildData() {
		return super.getBatchBuildData();
	}

	protected File getPrimaryPortalWorkspaceDirectory() {
		Workspace workspace = getWorkspace();

		WorkspaceGitRepository workspaceGitRepository =
			workspace.getPrimaryWorkspaceGitRepository();

		return workspaceGitRepository.getDirectory();
	}

	@Override
	protected void publishResults() {
		try {
			AntUtil.callTarget(
				getPrimaryPortalWorkspaceDirectory(), "build-test.xml",
				"merge-test-results", null, null, getAntLibDir());
		}
		catch (AntException antException) {
			throw new RuntimeException(antException);
		}

		File sourceFile = new File(
			getPrimaryPortalWorkspaceDirectory(),
			"test-results/TESTS-TestSuites.xml");

		if (!sourceFile.exists()) {
			return;
		}

		BatchBuildData batchBuildData = getBatchBuildData();

		File targetFile = new File(
			batchBuildData.getWorkspaceDir(),
			"test-results/TESTS-TestSuites.xml");

		try {
			JenkinsResultsParserUtil.copy(sourceFile, targetFile);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to copy test results file from ",
					sourceFile.getPath(), " to ", targetFile.getPath()),
				ioException);
		}
	}

}