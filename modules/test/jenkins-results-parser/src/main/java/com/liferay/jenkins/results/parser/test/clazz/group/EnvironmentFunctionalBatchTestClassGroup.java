/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalEnvironmentJob;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;

import java.io.File;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class EnvironmentFunctionalBatchTestClassGroup
	extends FunctionalBatchTestClassGroup {

	@Override
	public String getBatchJobName() {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			portalTestClassJob.getPortalGitWorkingDirectory();

		return JenkinsResultsParserUtil.combine(
			getBatchName(), "(",
			portalGitWorkingDirectory.getUpstreamBranchName(), ")");
	}

	@Override
	public List<File> getTestBaseDirs() {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		return Arrays.asList(
			new File(
				portalGitWorkingDirectory.getWorkingDirectory(),
				"portal-web/test/functional"));
	}

	protected EnvironmentFunctionalBatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);
	}

	protected EnvironmentFunctionalBatchTestClassGroup(
		String batchName, PortalEnvironmentJob portalEnvironmentJob) {

		super(batchName, portalEnvironmentJob);
	}

}