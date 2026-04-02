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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Kenji Heigel
 */
public class ModulesRelevantRule extends RelevantRule {

	public ModulesRelevantRule(
		String filePath, GitWorkingDirectory gitWorkingDirectory, Job job,
		String name, Properties properties) {

		super(filePath, gitWorkingDirectory, job, name, properties);
	}

	@Override
	public List<TestScriptCommand> getTestScriptCommands() {
		try {
			List<File> modifiedModuleProjectDirsList =
				getModifiedModuleProjectDirsList();

			if (modifiedModuleProjectDirsList.isEmpty()) {
				return new ArrayList<>();
			}

			List<TestScriptCommand> testScriptCommands = new ArrayList<>();

			StringBuilder sb = new StringBuilder();

			sb.append("../gradlew");

			for (File modifiedModuleProjectDir :
					modifiedModuleProjectDirsList) {

				sb.append(" ");
				sb.append(getGradlePackageName(modifiedModuleProjectDir));
				sb.append(":");
				sb.append(getTestScriptModuleGradleTaskName());
			}

			sb.append(" --parallel");

			testScriptCommands.add(
				new TestScriptCommand(sb.toString(), "modules"));

			return testScriptCommands;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public String getTestScriptModuleGradleTaskName() {
		return JenkinsResultsParserUtil.getProperty(
			getProperties(), "test.script.module.gradle.task.name", getName(),
			getTestSuiteName());
	}

}