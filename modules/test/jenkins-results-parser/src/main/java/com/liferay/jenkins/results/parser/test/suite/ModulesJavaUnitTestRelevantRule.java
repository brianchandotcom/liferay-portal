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
public class ModulesJavaUnitTestRelevantRule extends RelevantRule {

	public ModulesJavaUnitTestRelevantRule(
		String filePath, GitWorkingDirectory gitWorkingDirectory, Job job,
		String name, Properties properties) {

		super(filePath, gitWorkingDirectory, job, name, properties);
	}

	@Override
	public List<TestScriptCommand> getTestScriptCommands() {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			(PortalGitWorkingDirectory)getGitWorkingDirectory();

		try {
			List<File> modifiedModuleDirsList =
				portalGitWorkingDirectory.getModifiedModuleDirsList();

			if (modifiedModuleDirsList.isEmpty()) {
				return new ArrayList<>();
			}

			List<TestScriptCommand> testScriptCommands = new ArrayList<>();

			StringBuilder commandSb = new StringBuilder();

			commandSb.append("../gradlew");

			for (File modifiedModuleDir : modifiedModuleDirsList) {
				commandSb.append(" ");
				commandSb.append(_getGradlePackageName(modifiedModuleDir));
				commandSb.append(":test");
			}

			testScriptCommands.add(
				new TestScriptCommand(commandSb.toString(), "modules"));

			return testScriptCommands;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private String _getGradlePackageName(File moduleDir) {
		String absolutePath = JenkinsResultsParserUtil.getCanonicalPath(
			moduleDir);

		int x = absolutePath.indexOf("/modules/");

		if (x == -1) {
			return "";
		}

		String relativePath = absolutePath.substring(x + 9);

		return ":" + relativePath.replace('/', ':');
	}

}