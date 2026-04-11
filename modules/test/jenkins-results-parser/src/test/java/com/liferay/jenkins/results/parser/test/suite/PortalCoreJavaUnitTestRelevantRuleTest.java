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
public class PortalCoreJavaUnitTestRelevantRuleTest {

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

		PortalCoreJavaUnitTestRelevantRule portalCoreJavaUnitTestRelevantRule =
			Mockito.spy(
				new PortalCoreJavaUnitTestRelevantRule(
					null, gitWorkingDirectory, Mockito.mock(Job.class),
					"test-rule", new Properties()));

		List<File> modifiedDirs = new ArrayList<>();

		modifiedDirs.add(new File("other"));
		modifiedDirs.add(new File("portal-impl"));
		modifiedDirs.add(new File("portal-kernel"));

		Mockito.doReturn(
			modifiedDirs
		).when(
			portalCoreJavaUnitTestRelevantRule
		).getModifiedDirsList(
			workingDirectory
		);

		List<RelevantRule.TestScriptCommand> testScriptCommands =
			portalCoreJavaUnitTestRelevantRule.getTestScriptCommands();

		Assert.assertEquals(
			testScriptCommands.toString(), 3, testScriptCommands.size());

		RelevantRule.TestScriptCommand testScriptCommand =
			testScriptCommands.get(0);

		Assert.assertEquals(".", testScriptCommand.getCommandDirPath());

		Assert.assertEquals("ant compile-test", testScriptCommand.getCommand());

		testScriptCommand = testScriptCommands.get(1);

		Assert.assertEquals(
			"portal-impl", testScriptCommand.getCommandDirPath());

		Assert.assertEquals("ant test-unit", testScriptCommand.getCommand());

		testScriptCommand = testScriptCommands.get(2);

		Assert.assertEquals(
			"portal-kernel", testScriptCommand.getCommandDirPath());

		Assert.assertEquals("ant test-unit", testScriptCommand.getCommand());
	}

	@Test
	public void testGetTestScriptCommandsEmpty() {
		GitWorkingDirectory gitWorkingDirectory = Mockito.mock(
			GitWorkingDirectory.class);

		File workingDirectory = new File(".");

		Mockito.doReturn(
			workingDirectory
		).when(
			gitWorkingDirectory
		).getWorkingDirectory();

		PortalCoreJavaUnitTestRelevantRule portalCoreJavaUnitTestRelevantRule =
			Mockito.spy(
				new PortalCoreJavaUnitTestRelevantRule(
					null, gitWorkingDirectory, Mockito.mock(Job.class),
					"test-rule", new Properties()));

		List<File> modifiedDirs = new ArrayList<>();

		modifiedDirs.add(new File("other"));

		Mockito.doReturn(
			modifiedDirs
		).when(
			portalCoreJavaUnitTestRelevantRule
		).getModifiedDirsList(
			workingDirectory
		);

		List<RelevantRule.TestScriptCommand> testScriptCommands =
			portalCoreJavaUnitTestRelevantRule.getTestScriptCommands();

		Assert.assertTrue(testScriptCommands.isEmpty());
	}

}