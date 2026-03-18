/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import com.liferay.petra.string.StringBundler;

import org.junit.Test;

/**
 * @author Alan Huang
 */
public class SHSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testLocalVariablesAssignedViaSubshells() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"LocalVariablesAssignedViaSubshells.testsh"
			).addExpectedMessage(
				StringBundler.concat(
					"Do not declare and assign 'local' variables using ",
					"subshell outputs in a single line, extract 'local' ",
					"variable declaration and assignment via subshell into ",
					"two separate lines"),
				8
			).addExpectedMessage(
				StringBundler.concat(
					"Do not declare and assign 'local' variables using ",
					"subshell outputs in a single line, extract 'local' ",
					"variable declaration and assignment via subshell into ",
					"two separate lines"),
				9
			).addExpectedMessage(
				StringBundler.concat(
					"Do not declare and assign 'local' variables using ",
					"subshell outputs in a single line, extract 'local' ",
					"variable declaration and assignment via subshell into ",
					"two separate lines"),
				10
			).addExpectedMessage(
				StringBundler.concat(
					"Do not declare and assign 'local' variables using ",
					"subshell outputs in a single line, extract 'local' ",
					"variable declaration and assignment via subshell into ",
					"two separate lines"),
				12
			));
	}

}