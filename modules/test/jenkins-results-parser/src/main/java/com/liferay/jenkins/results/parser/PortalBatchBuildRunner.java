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
public class PortalBatchBuildRunner
	extends BatchBuildRunner<PortalBatchBuildData> {

	@Override
	public void run() {
		updateBuildDescription();

		setUpWorkspace();

		runTestBatch();

		copyTestResults();
	}

	protected PortalBatchBuildRunner(
		PortalBatchBuildData portalBatchBuildData) {

		super(portalBatchBuildData);
	}

	protected void copyTestResults() {
		AntUtil.callTarget(
			_getPrimaryPortalDirectory(), "build-test.xml",
			"merge-test-results");

		BuildData buildData = getBuildData();

		File source = new File(
			_getPrimaryPortalDirectory(), "test-results/TESTS-TestSuites.xml");
		File target = new File(
			buildData.getWorkspaceDir(), "test-results/TESTS-TestSuites.xml");

		if (!source.exists()) {
			return;
		}

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

	@Override
	protected void initWorkspace() {
		PortalBatchBuildData portalBatchBuildData = getBuildData();

		workspace = WorkspaceFactory.newBatchWorkspace(
			portalBatchBuildData.getPortalGitHubURL(),
			portalBatchBuildData.getPortalUpstreamBranchName(), getBatchName());

		if (!(workspace instanceof BatchPortalWorkspace)) {
			throw new RuntimeException("Invalid workspace");
		}

		_batchPortalWorkspace = (BatchPortalWorkspace)workspace;
	}

	protected void runTestBatch() {
		Map<String, String> parameters = new HashMap<>();

		parameters.put("axis.variable", "PortalSmoke#Smoke");

		AntUtil.callTarget(
			_getPrimaryPortalDirectory(), "build-test-batch.xml",
			getBatchName(), parameters);
	}

	private File _getPrimaryPortalDirectory() {
		WorkspaceGitRepository workspaceGitRepository =
			_batchPortalWorkspace.getPrimaryPortalWorkspaceGitRepository();

		return workspaceGitRepository.getDirectory();
	}

	private BatchPortalWorkspace _batchPortalWorkspace;

}