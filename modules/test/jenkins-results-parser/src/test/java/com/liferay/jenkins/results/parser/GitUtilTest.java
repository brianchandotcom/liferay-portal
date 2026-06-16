/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Kenji Heigel
 */
public class GitUtilTest extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testGetDefaultBranchName() throws Exception {
		Shell shell = mockShell();

		Mockito.doReturn(
			new Shell.ExecutionResult(
				0, "", "master\nFinished executing Bash commands.\n")
		).when(
			shell
		).doExecute(
			Mockito.argThat(
				executionRequest ->
					(executionRequest != null) &&
					executionRequest.getCommands()[0].contains(
						"git remote show origin"))
		);

		Assert.assertEquals(
			"master", GitUtil.getDefaultBranchName(new File(".")));
	}

}