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

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Kenji Heigel
 */
public class WorkspaceBuildRelevantRuleTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		Class.forName("com.liferay.jenkins.results.parser.GitWorkingDirectory");
	}

	@Test
	public void testGetTestScriptCommands() {
		GitWorkingDirectory gitWorkingDirectory = Mockito.mock(
			GitWorkingDirectory.class);

		File workingDirectory = new File(".");

		Mockito.doReturn(
			workingDirectory
		).when(
			gitWorkingDirectory
		).getWorkingDirectory();

		WorkspaceBuildRelevantRule workspaceBuildRelevantRule = Mockito.spy(
			new WorkspaceBuildRelevantRule(
				null, gitWorkingDirectory, Mockito.mock(Job.class), "test-rule",
				new Properties()));

		List<File> modifiedDirs = new ArrayList<>();

		modifiedDirs.add(new File("workspaces/test-workspace-1"));
		modifiedDirs.add(new File("workspaces/test-workspace-2"));

		Mockito.doReturn(
			modifiedDirs
		).when(
			workspaceBuildRelevantRule
		).getModifiedDirsList(
			Mockito.any(File.class)
		);

		List<RelevantRule.TestScriptCommand> testScriptCommands =
			workspaceBuildRelevantRule.getTestScriptCommands();

		Assert.assertEquals(
			testScriptCommands.toString(), 2, testScriptCommands.size());

		RelevantRule.TestScriptCommand testScriptCommand =
			testScriptCommands.get(0);

		Assert.assertEquals(
			"workspaces/test-workspace-1",
			testScriptCommand.getCommandDirPath());

		Assert.assertEquals("./gradlew build", testScriptCommand.getCommand());

		testScriptCommand = testScriptCommands.get(1);

		Assert.assertEquals(
			"workspaces/test-workspace-2",
			testScriptCommand.getCommandDirPath());

		Assert.assertEquals("./gradlew build", testScriptCommand.getCommand());
	}

}