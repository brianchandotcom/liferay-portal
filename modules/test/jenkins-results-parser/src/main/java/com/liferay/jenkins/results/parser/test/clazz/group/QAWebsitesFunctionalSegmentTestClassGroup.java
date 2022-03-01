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

import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.QAWebsitesGitRepositoryJob;

import java.io.File;

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
		QAWebsitesFunctionalBatchTestClassGroup
			parentQAWebsitesFunctionalBatchTestClassGroup) {

		super(parentQAWebsitesFunctionalBatchTestClassGroup);
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