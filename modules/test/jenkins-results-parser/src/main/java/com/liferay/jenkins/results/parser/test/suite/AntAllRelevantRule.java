/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
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
	public List<TestScriptCommand> getTestScriptCommands() {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			(PortalGitWorkingDirectory)getGitWorkingDirectory();

		try {
			boolean hasDxpModules = false;

			for (File modifiedModuleDir :
					portalGitWorkingDirectory.getModifiedModuleDirsList()) {

				String modifiedModuleDirFilePath =
					JenkinsResultsParserUtil.getCanonicalPath(
						modifiedModuleDir);

				if (modifiedModuleDirFilePath.contains("/modules/dxp/")) {
					hasDxpModules = true;

					break;
				}
			}

			List<TestScriptCommand> testScriptCommands = new ArrayList<>();

			if (hasDxpModules) {
				testScriptCommands.add(
					new TestScriptCommand("ant setup-profile-dxp all", "."));
			}
			else {
				testScriptCommands.add(new TestScriptCommand("ant all", "."));
			}

			return testScriptCommands;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

}