/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.Job;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		List<File> modifiedDirs = getModifiedDirsList(
			new File(gitWorkingDirectory.getWorkingDirectory(), "workspaces"));

		for (File modifiedDir : modifiedDirs) {
			String workspaceDirName = modifiedDir.getName();

			testScriptCommands.add(
				new TestScriptCommand(
					"./gradlew build", "workspaces/" + workspaceDirName));
		}

		return testScriptCommands;
	}

}