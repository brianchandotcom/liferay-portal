/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;

import java.io.File;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author Kenji Heigel
 */
public class WorkspaceBuildRelevantRule extends RelevantRule {

	public WorkspaceBuildRelevantRule(
		String filePath, GitWorkingDirectory gitWorkingDirectory, Job job,
		String name, Properties properties) {

		super(filePath, gitWorkingDirectory, job, name, properties);
	}

	@Override
	public List<TestScriptCommand> getTestScriptCommands() {
		List<TestScriptCommand> testScriptCommands = new ArrayList<>();

		Set<String> modifiedWorkspaceDirs = new HashSet<>();

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		String baseDirPath = JenkinsResultsParserUtil.getCanonicalPath(
			gitWorkingDirectory.getWorkingDirectory());

		for (File modifiedFile :
				gitWorkingDirectory.getModifiedFilesList(true)) {

			String modifiedFilePath = JenkinsResultsParserUtil.getCanonicalPath(
				modifiedFile);

			if (modifiedFilePath.startsWith(baseDirPath + "/workspaces/")) {
				String relativePath = modifiedFilePath.substring(
					baseDirPath.length() + 12);

				int index = relativePath.indexOf('/');

				if (index != -1) {
					String workspaceDirName = relativePath.substring(0, index);

					modifiedWorkspaceDirs.add(workspaceDirName);
				}
			}
		}

		for (String workspaceDirName : modifiedWorkspaceDirs) {
			testScriptCommands.add(
				new TestScriptCommand(
					"./gradlew build", "workspaces/" + workspaceDirName));
		}

		return testScriptCommands;
	}

}