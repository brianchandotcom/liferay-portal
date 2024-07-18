/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.GitRepositoryJob;
import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.Job;
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
		Job job = getJob();

		GitRepositoryJob gitRepositoryJob = (GitRepositoryJob)job;

		GitWorkingDirectory gitWorkingDirectory =
			gitRepositoryJob.getGitWorkingDirectory();

		File workingDirectory = gitWorkingDirectory.getWorkingDirectory();

		return new File(workingDirectory, "playwright");
	}

}