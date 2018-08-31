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

/**
 * @author Michael Hashimoto
 */
public class PortalTopLevelBuildRunner extends TopLevelBuildRunner {

	protected PortalTopLevelBuildRunner(BuildData buildData) {
		super(buildData);

		if (!(buildData instanceof PortalTopLevelBuildData)) {
			throw new RuntimeException(
				"Invalid build data " + buildData.toJSONObject());
		}

		_portalTopLevelBuildData = (PortalTopLevelBuildData)buildData;
	}

	@Override
	protected void initWorkspace() {
		workspace = WorkspaceFactory.newTopLevelWorkspace(
			_portalTopLevelBuildData.getPortalGitHubURL(),
			_portalTopLevelBuildData.getPortalUpstreamBranchName());

		if (!(workspace instanceof TopLevelPortalWorkspace)) {
			throw new RuntimeException("Invalid workspace");
		}

		if (JenkinsResultsParserUtil.isCINode()) {
			workspace.addJenkinsLocalGitBranch(
				_portalTopLevelBuildData.getJenkinsGitHubURL());
		}
	}

	private final PortalTopLevelBuildData _portalTopLevelBuildData;

}