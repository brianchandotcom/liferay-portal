/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class ModulesPortalTestBatch
	extends BasePortalTestBatch<PortalBatchBuildData> {

	protected ModulesPortalTestBatch(
		PortalBatchBuildData portalBatchBuildData, Workspace workspace) {

		super(portalBatchBuildData, workspace);
	}

	@Override
	protected void executeBatch() throws AntException {
		PortalBatchBuildData portalBatchBuildData = getBatchBuildData();

		Map<String, String> buildParameters = new HashMap<>();

		buildParameters.put(
			"modules.test.class.group",
			JenkinsResultsParserUtil.join(
				" ", portalBatchBuildData.getTestList()));
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