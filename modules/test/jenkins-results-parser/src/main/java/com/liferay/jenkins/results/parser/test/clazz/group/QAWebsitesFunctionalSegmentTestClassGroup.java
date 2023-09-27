/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.QAWebsitesGitRepositoryJob;

import java.io.File;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class QAWebsitesFunctionalSegmentTestClassGroup
	extends FunctionalSegmentTestClassGroup {

	@Override
	public String getTestCasePropertiesContent() {
		StringBuilder sb = new StringBuilder();

		sb.append(super.getTestCasePropertiesContent());
		sb.append("\n");

		sb.append("PROJECT_NAME=");
		sb.append(_getProjectName());

		return sb.toString();
	}

	protected QAWebsitesFunctionalSegmentTestClassGroup(
		BatchTestClassGroup parentBatchTestClassGroup) {

		super(parentBatchTestClassGroup);
	}

	protected QAWebsitesFunctionalSegmentTestClassGroup(
		BatchTestClassGroup parentBatchTestClassGroup, JSONObject jsonObject) {

		super(parentBatchTestClassGroup, jsonObject);
	}

	private String _getProjectName() {
		File testBaseDir = getTestBaseDir();

		Job job = getJob();

		if (!(job instanceof QAWebsitesGitRepositoryJob)) {
			return testBaseDir.getName();
		}

		QAWebsitesGitRepositoryJob qaWebsitesGitRepositoryJob =
			(QAWebsitesGitRepositoryJob)job;

		GitWorkingDirectory gitWorkingDirectory =
			qaWebsitesGitRepositoryJob.getGitWorkingDirectory();

		return JenkinsResultsParserUtil.getPathRelativeTo(
			testBaseDir, gitWorkingDirectory.getWorkingDirectory());
	}

}