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
public class ModulesCompileRelevantRule extends RelevantRule {

	public ModulesCompileRelevantRule(
		String filePath, GitWorkingDirectory gitWorkingDirectory, Job job,
		String name, Properties properties) {

		super(filePath, gitWorkingDirectory, job, name, properties);
	}

	@Override
	public List<TestScriptCommand> getTestScriptCommands() {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			(PortalGitWorkingDirectory)getGitWorkingDirectory();

		try {
			List<File> modulesToCompile = new ArrayList<>();

			List<File> modifiedModuleDirsList =
				portalGitWorkingDirectory.getModifiedModuleDirsList();

			if (modifiedModuleDirsList.size() <= 5) {
				modulesToCompile.addAll(modifiedModuleDirsList);
			}
			else {
				for (File modifiedModuleDir : modifiedModuleDirsList) {
					File lfrbuildPortalFile = new File(
						modifiedModuleDir, ".lfrbuild-portal");

					if (!lfrbuildPortalFile.exists()) {
						modulesToCompile.add(modifiedModuleDir);
					}
				}
			}

			if (modulesToCompile.isEmpty()) {
				return new ArrayList<>();
			}

			List<TestScriptCommand> testScriptCommands = new ArrayList<>();

			testScriptCommands.add(
				new TestScriptCommand(
					"ant setup-profile-dxp compile install-portal-snapshots",
					"."));

			StringBuilder sb = new StringBuilder();

			sb.append("../gradlew");

			for (File modifiedModuleDir : modulesToCompile) {
				sb.append(" ");
				sb.append(_getGradlePackageName(modifiedModuleDir));
				sb.append(":deploy");
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

	@Override
	public boolean matches(File modifiedFile) {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			(PortalGitWorkingDirectory)getGitWorkingDirectory();

		try {
			List<File> modifiedModuleDirsList =
				portalGitWorkingDirectory.getModifiedModuleDirsList();

			if (modifiedModuleDirsList.size() > 5) {
				boolean hasNonPortalModule = false;

				for (File modifiedModuleDir : modifiedModuleDirsList) {
					File lfrbuildPortalFile = new File(
						modifiedModuleDir, ".lfrbuild-portal");

					if (!lfrbuildPortalFile.exists()) {
						hasNonPortalModule = true;

						break;
					}
				}

				if (!hasNonPortalModule) {
					return false;
				}
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return super.matches(modifiedFile);
	}

	private String _getGradlePackageName(File moduleDir) {
		String moduleDirFilePath = JenkinsResultsParserUtil.getCanonicalPath(
			moduleDir);

		int index = moduleDirFilePath.indexOf("/modules/");

		if (index == -1) {
			return "";
		}

		String relativeModuleDirFilePath = moduleDirFilePath.substring(
			index + 9);

		return ":" + relativeModuleDirFilePath.replace('/', ':');
	}

}