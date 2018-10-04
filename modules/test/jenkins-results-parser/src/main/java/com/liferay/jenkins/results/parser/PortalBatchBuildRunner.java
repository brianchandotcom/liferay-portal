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

/**
 * @author Michael Hashimoto
 */
public class PortalBatchBuildRunner
	extends BatchBuildRunner<PortalBatchBuildData> {

	@Override
	public void run() {
		super.run();

		runBatch();
	}

	protected PortalBatchBuildRunner(
		PortalBatchBuildData portalBatchBuildData) {

		super(portalBatchBuildData);
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

	protected void runBatch() {
		Map<String, String> parameters = new HashMap<>();

		parameters.put("test.class", "PortalSmoke#Smoke");

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