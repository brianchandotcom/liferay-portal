/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.GitRepositoryJob;
import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;

import java.io.File;

import org.json.JSONObject;

/**
 * @author Calum Ragan
 */
public class QAWebsitesPlaywrightBatchTestClassGroup
	extends PlaywrightBatchTestClassGroup {

	public QAWebsitesPlaywrightBatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);
	}

	public QAWebsitesPlaywrightBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected File getPlaywrightBaseDir() {
		GitRepositoryJob gitRepositoryJob = (GitRepositoryJob)getJob();

		GitWorkingDirectory gitWorkingDirectory =
			gitRepositoryJob.getGitWorkingDirectory();

		return new File(
			gitWorkingDirectory.getWorkingDirectory(), "playwright");
	}

}