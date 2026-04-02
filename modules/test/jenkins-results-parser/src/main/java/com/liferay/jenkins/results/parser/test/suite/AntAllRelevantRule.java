/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Properties;

/**
 * @author Kenji Heigel
 */
public class AntAllRelevantRule extends RelevantRule {

	public AntAllRelevantRule(
		String filePath, GitWorkingDirectory gitWorkingDirectory, Job job,
		String name, Properties properties) {

		super(filePath, gitWorkingDirectory, job, name, properties);
	}

	@Override
	public boolean matches(File modifiedFile) {
		if (!super.matches(modifiedFile)) {
			return false;
		}

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		File modulesDir = new File(
			gitWorkingDirectory.getWorkingDirectory(), "modules");

		if (!JenkinsResultsParserUtil.isFileInDirectory(
				modulesDir, modifiedFile)) {

			return true;
		}

		try {
			List<File> modifiedModuleProjectDirsList =
				getModifiedModuleProjectDirsList();

			if (modifiedModuleProjectDirsList.size() > 5) {
				return true;
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return false;
	}

}