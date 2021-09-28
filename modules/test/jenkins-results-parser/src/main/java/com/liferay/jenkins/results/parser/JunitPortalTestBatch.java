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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class JunitPortalTestBatch
	extends BasePortalTestBatch<PortalBatchBuildData> {

	protected JunitPortalTestBatch(
		PortalBatchBuildData portalBatchBuildData, Workspace workspace) {

		super(portalBatchBuildData, workspace);
	}

	@Override
	protected void executeBatch() throws AntException {
		PortalBatchBuildData portalBatchBuildData = getBatchBuildData();

		Map<String, String> buildParameters = new HashMap<>();

		buildParameters.put("axis.variable", "0");
		buildParameters.put(
			"test.batch.name", portalBatchBuildData.getBatchName());
		buildParameters.put("test.batch.size", "1");

		Map<String, String> environmentVariables = new HashMap<>();

		environmentVariables.put(
			"TEST_PORTAL_BRANCH_NAME",
			portalBatchBuildData.getPortalUpstreamBranchName());

		if (JenkinsResultsParserUtil.isCINode()) {
			String batchName = portalBatchBuildData.getBatchName();

			environmentVariables.put("ANT_OPTS", getAntOpts(batchName));
			environmentVariables.put("JAVA_HOME", getJavaHome(batchName));
			environmentVariables.put("PATH", getPath(batchName));

			environmentVariables.put(
				"TOP_LEVEL_JOB_NAME",
				portalBatchBuildData.getTopLevelJobName());
		}

		environmentVariables.putAll(
			portalBatchBuildData.getTopLevelBuildParameters());

		environmentVariables.putAll(portalBatchBuildData.getBuildParameters());

		AntUtil.callTarget(
			getPrimaryPortalWorkspaceDirectory(), "build-test-batch.xml",
			portalBatchBuildData.getBatchName(), buildParameters,
			environmentVariables, getAntLibDir());
	}

}