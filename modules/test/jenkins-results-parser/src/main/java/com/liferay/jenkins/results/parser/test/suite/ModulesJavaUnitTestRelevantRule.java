/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.GitWorkingDirectory;
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
			getPortalGitWorkingDirectory();

		try {
			List<File> modifiedModuleDirsList =
				portalGitWorkingDirectory.getModifiedModuleDirsList();

			if (modifiedModuleDirsList.isEmpty()) {
				return new ArrayList<>();
			}

			List<TestScriptCommand> testScriptCommands = new ArrayList<>();

			StringBuilder sb = new StringBuilder();

			sb.append("../gradlew");

			for (File modifiedModuleDir : modifiedModuleDirsList) {
				sb.append(" ");
				sb.append(getGradlePackageName(modifiedModuleDir));
				sb.append(":test");
			}

			testScriptCommands.add(
				new TestScriptCommand(sb.toString(), "modules"));

			return testScriptCommands;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

}