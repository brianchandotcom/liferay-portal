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