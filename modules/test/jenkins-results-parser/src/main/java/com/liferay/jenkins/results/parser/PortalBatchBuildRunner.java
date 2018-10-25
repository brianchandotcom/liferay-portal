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
public abstract class PortalBatchBuildRunner<T extends PortalBatchBuildData>
	extends BatchBuildRunner<T, BatchPortalWorkspace> {

	@Override
	public void run() {
		updateBuildDescription();

		setUpWorkspace();

		runTestBatch();

		publishTestResults();
	}

	protected PortalBatchBuildRunner(T portalBatchBuildData) {
		super(portalBatchBuildData);
	}

	@Override
	protected void initWorkspace() {
		PortalBatchBuildData portalBatchBuildData = getBuildData();

		BatchWorkspace batchWorkspace = WorkspaceFactory.newBatchWorkspace(
			portalBatchBuildData.getPortalGitHubURL(),
			portalBatchBuildData.getPortalUpstreamBranchName(),
			portalBatchBuildData.getBatchName(),
			portalBatchBuildData.getPortalBranchSHA());

		if (!(batchWorkspace instanceof BatchPortalWorkspace)) {
			throw new RuntimeException("Invalid workspace");
		}

		workspace = (BatchPortalWorkspace)batchWorkspace;
	}

	protected void publishTestResults() {
		AntUtil.callTarget(
			_getPrimaryPortalDirectory(), "build-test.xml",
			"merge-test-results");

		File source = new File(
			_getPrimaryPortalDirectory(), "test-results/TESTS-TestSuites.xml");

		if (!source.exists()) {
			return;
		}

		PortalBatchBuildData portalBatchBuildData = getBuildData();

		File target = new File(
			portalBatchBuildData.getWorkspaceDir(),
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

	protected void runTestBatch() {
		Map<String, String> parameters = new HashMap<>();

		PortalBatchBuildData portalBatchBuildData = getBuildData();

		parameters.put(
			"axis.variable",
			JenkinsResultsParserUtil.join(
				",", portalBatchBuildData.getTestList()));

		String batchName = portalBatchBuildData.getBatchName();

		Map<String, String> environmentVariables = new HashMap<>();

		if (JenkinsResultsParserUtil.isCINode()) {
			environmentVariables.put("ANT_OPTS", _getAntOpts(batchName));
			environmentVariables.put("JAVA_HOME", _getJavaHome(batchName));
			environmentVariables.put("PATH", _getPath(batchName));
		}

		AntUtil.callTarget(
			_getPrimaryPortalDirectory(), "build-test-batch.xml",
			portalBatchBuildData.getBatchName(), parameters,
			environmentVariables);
	}

	private String _getAntOpts(String batchName) {
		String antOpts = System.getenv("ANT_OPTS");

		if (batchName.endsWith("-jdk7")) {
			return antOpts.replace("MetaspaceSize", "PermSize");
		}

		if (batchName.endsWith("-jdk8")) {
			return antOpts.replace("PermSize", "MetaspaceSize");
		}

		return antOpts;
	}

	private String _getJavaHome(String batchName) {
		if (batchName.endsWith("-jdk7")) {
			return "/opt/java/jdk8";
		}

		if (batchName.endsWith("-jdk8")) {
			return "/opt/java/jdk8";
		}

		return "/opt/java/jdk";
	}

	private String _getPath(String batchName) {
		String path = System.getenv("PATH");

		if (batchName.endsWith("-jdk7")) {
			return path.replace("jdk", "jdk7");
		}

		if (batchName.endsWith("-jdk8")) {
			return path.replace("jdk", "jdk8");
		}

		return path;
	}

	private File _getPrimaryPortalDirectory() {
		WorkspaceGitRepository workspaceGitRepository =
			workspace.getPrimaryPortalWorkspaceGitRepository();

		return workspaceGitRepository.getDirectory();
	}

}