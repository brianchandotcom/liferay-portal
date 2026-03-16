/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

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
				"Do not assign subshell outputs directly to 'local' " +
					"variables, extract the subshell call into a separate " +
						"assignment",
				8
			).addExpectedMessage(
				"Do not assign subshell outputs directly to 'local' " +
					"variables, extract the subshell call into a separate " +
						"assignment",
				9
			).addExpectedMessage(
				"Do not assign subshell outputs directly to 'local' " +
					"variables, extract the subshell call into a separate " +
						"assignment",
				10
			).addExpectedMessage(
				"Do not assign subshell outputs directly to 'local' " +
					"variables, extract the subshell call into a separate " +
						"assignment",
				12
			));
	}

}