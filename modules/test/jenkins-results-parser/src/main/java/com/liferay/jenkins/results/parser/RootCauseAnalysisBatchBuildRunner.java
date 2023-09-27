/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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