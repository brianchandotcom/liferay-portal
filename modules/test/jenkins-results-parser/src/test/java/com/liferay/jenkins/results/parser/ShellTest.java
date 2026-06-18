/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Kenji Heigel
 */
public class ShellTest extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testExecuteBashCommandsAdaptsExecutionResult()
		throws Exception {

		Shell shell = mockShell();

		Mockito.doReturn(
			new Shell.ExecutionResult(0, _STANDARD_ERROR, _STANDARD_OUT)
		).when(
			shell
		).doExecute(
			Mockito.any(Shell.ExecutionRequest.class)
		);

		Process process = JenkinsResultsParserUtil.executeBashCommands(
			"echo hello");

		Assert.assertEquals(0, process.exitValue());
		Assert.assertEquals(0, process.waitFor());
		Assert.assertEquals(
			_STANDARD_OUT,
			JenkinsResultsParserUtil.readInputStream(process.getInputStream()));
		Assert.assertEquals(
			_STANDARD_ERROR,
			JenkinsResultsParserUtil.readInputStream(process.getErrorStream()));
	}

	private static final String _STANDARD_ERROR = "standard error";

	private static final String _STANDARD_OUT = "standard output";

}