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

		publishArtifacts();

		updateBuildDescription();
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

	protected void publishArtifacts() {
		PortalBatchBuildData portalBatchBuildData = getBuildData();

		File artifactDir = portalBatchBuildData.getArtifactDir();

		if (artifactDir.exists()) {
			publishToUserContentDir(artifactDir);
		}
	}

	protected void runTestBatch() {
		TestBatch testBatch = TestBatchFactory.newTestBatch(
			getBuildData(), workspace);

		testBatch.run();
	}

}