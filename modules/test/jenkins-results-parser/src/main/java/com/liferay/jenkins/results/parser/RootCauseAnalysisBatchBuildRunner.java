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
public class RootCauseAnalysisBatchBuildRunner
	<T extends PortalBatchBuildData, S extends PortalWorkspace>
		extends PortalBatchBuildRunner<T, S> {

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

		PortalWorkspace portalWorkspace = getWorkspace();

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			portalWorkspace.getPrimaryPortalWorkspaceGitRepository();

		GitWorkingDirectory gitWorkingDirectory =
			portalWorkspaceGitRepository.getGitWorkingDirectory();

		for (String portalCherryPickSHA : portalCherryPickSHAs) {
			gitWorkingDirectory.cherryPick(portalCherryPickSHA);
		}
	}

	private List<String> _getPortalCherryPickSHAs() {
		BuildData buildData = getBuildData();

		Map<String, String> topLevelBuildParameters =
			buildData.getTopLevelBuildParameters();

		String portalCherryPickSHAs = topLevelBuildParameters.get(
			_NAME_BUILD_PARAMETER_PORTAL_CHERRY_PICK_SHAS);

		List<String> portalCherryPickSHAList = new ArrayList<>();

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalCherryPickSHAs)) {
			return portalCherryPickSHAList;
		}

		for (String portalCherryPickSHA : portalCherryPickSHAs.split(",")) {
			portalCherryPickSHAList.add(portalCherryPickSHA.trim());
		}

		return portalCherryPickSHAList;
	}

	private static final String _NAME_BUILD_PARAMETER_PORTAL_CHERRY_PICK_SHAS =
		"PORTAL_CHERRY_PICK_SHAS";

}