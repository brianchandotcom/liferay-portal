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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class RootCauseAnalysisBatchBuildRunner<T extends PortalBatchBuildData>
	extends PortalBatchBuildRunner<T> {

	protected RootCauseAnalysisBatchBuildRunner(T portalBatchBuildData) {
		super(portalBatchBuildData);
	}

	@Override
	protected void setUpWorkspace() {
		super.setUpWorkspace();

		List<String> portalCherryPickSHAs = _getPortalCherryPickSHAs();

		if (portalCherryPickSHAs.isEmpty()) {
			return;
		}

		Workspace workspace = getWorkspace();

		WorkspaceGitRepository workspaceGitRepository =
			workspace.getPrimaryWorkspaceGitRepository();

		GitWorkingDirectory gitWorkingDirectory =
			workspaceGitRepository.getGitWorkingDirectory();

		for (String portalCherryPickSHA : portalCherryPickSHAs) {
			gitWorkingDirectory.cherryPick(portalCherryPickSHA);
		}
	}

	private List<String> _getPortalCherryPickSHAs() {
		List<String> portalCherryPickSHAs = new ArrayList<>();

		BuildData buildData = getBuildData();

		Map<String, String> topLevelBuildParameters =
			buildData.getTopLevelBuildParameters();

		String portalCherryPickSHAsString = topLevelBuildParameters.get(
			_NAME_BUILD_PARAMETER_PORTAL_CHERRY_PICK_SHAS);

		if (JenkinsResultsParserUtil.isNullOrEmpty(
				portalCherryPickSHAsString)) {

			return portalCherryPickSHAs;
		}

		for (String portalCherryPickSHA :
				portalCherryPickSHAsString.split(",")) {

			portalCherryPickSHAs.add(portalCherryPickSHA.trim());
		}

		return portalCherryPickSHAs;
	}

	private static final String _NAME_BUILD_PARAMETER_PORTAL_CHERRY_PICK_SHAS =
		"PORTAL_CHERRY_PICK_SHAS";

}