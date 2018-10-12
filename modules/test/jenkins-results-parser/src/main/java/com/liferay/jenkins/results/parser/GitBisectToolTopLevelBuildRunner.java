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

import java.util.Arrays;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class GitBisectToolTopLevelBuildRunner
	extends PortalTopLevelBuildRunner {

	protected GitBisectToolTopLevelBuildRunner(
		PortalTopLevelBuildData portalTopLevelBuildData) {

		super(portalTopLevelBuildData);
	}

	@Override
	protected void prepareInvocationBuildDataList() {
		PortalTopLevelBuildData portalTopLevelBuildData = getBuildData();

		String downstreamJobName =
			portalTopLevelBuildData.getJobName() + "-batch";

		for (String portalBranchSHA : _getPortalBranchSHAs()) {
			BatchBuildData batchBuildData = BuildDataFactory.newBatchBuildData(
				null, downstreamJobName, null);

			if (!(batchBuildData instanceof PortalBatchBuildData)) {
				throw new RuntimeException("Invalid build data");
			}

			PortalBatchBuildData portalBatchBuildData =
				(PortalBatchBuildData)batchBuildData;

			portalBatchBuildData.setBuildDescription(
				_getDownstreamBuildDescription(portalBranchSHA));

			portalBatchBuildData.setBatchName(_getBatchName());
			portalBatchBuildData.setPortalBranchSHA(portalBranchSHA);
			portalBatchBuildData.setTestList(_getTestList());

			addInvocationBuildData(portalBatchBuildData);
		}
	}

	private String _getBatchName() {
		BuildData buildData = getBuildData();

		return JenkinsResultsParserUtil.getBuildParameter(
			buildData.getBuildURL(), "PORTAL_BATCH_NAME");
	}

	private String _getDownstreamBuildDescription(String portalBranchSHA) {
		PortalTopLevelBuildData portalTopLevelBuildData = getBuildData();

		StringBuilder sb = new StringBuilder();

		sb.append(portalBranchSHA);
		sb.append(" - ");
		sb.append(_getBatchName());
		sb.append(" - ");
		sb.append(JenkinsResultsParserUtil.join(",", _getTestList()));
		sb.append(" - ");
		sb.append("<a href=\"https://");
		sb.append(portalTopLevelBuildData.getTopLevelMasterHostname());
		sb.append(".liferay.com/userContent/");
		sb.append(portalTopLevelBuildData.getUserContentRelativePath());
		sb.append("jenkins-report.html\">Jenkins Report</a>");

		return sb.toString();
	}

	private List<String> _getPortalBranchSHAs() {
		PortalTopLevelBuildData portalTopLevelBuildData = getBuildData();

		String portalGitCommits = JenkinsResultsParserUtil.getBuildParameter(
			portalTopLevelBuildData.getBuildURL(), "PORTAL_BRANCH_SHAS");

		return Arrays.asList(portalGitCommits.split(","));
	}

	private List<String> _getTestList() {
		PortalTopLevelBuildData portalTopLevelBuildData = getBuildData();

		String portalBatchTestSelector =
			JenkinsResultsParserUtil.getBuildParameter(
				portalTopLevelBuildData.getBuildURL(),
				"PORTAL_BATCH_TEST_SELECTOR");

		return Arrays.asList(portalBatchTestSelector.split(","));
	}

}