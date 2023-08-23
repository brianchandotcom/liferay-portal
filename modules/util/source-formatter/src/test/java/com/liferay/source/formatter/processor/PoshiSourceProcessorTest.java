/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import org.junit.Test;

/**
 * @author Alan Huang
 */
public class PoshiSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testIncorrectComments() throws Exception {
		test("IncorrectComments.testmacro");
	}

	@Test
	public void testIncorrectIndentation() throws Exception {
		test("IncorrectIndentation.testmacro");
	}

	@Test
	public void testMissingCiRetirtiesDisabledSmoke() throws Exception {
		test(
			"MissingCiRetriesDisabledSmoke.testtestcase",
			"Missing property ci.retries.disabled = \"true\" in definition " +
				"for smoke test");
	}

	@Test
	public void testMultipleSpacesInTaskDefinitions() throws Exception {
		test("MultipleSpacesInTaskDefinitions.testmacro");
	}

	@Test
	public void testPoshiPauseUsage() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"PoshiPauseUsage.testmacro"
			).addExpectedMessage(
				"Missing a comment before using 'Pause'", 6
			).addExpectedMessage(
				"Missing a required JIRA project in comment before using " +
					"'Pause'",
				10
			));
	}

	@Test
	public void testSortAntCommandParameters() throws Exception {
		test("SortAntCommandParameters.testtestcase");
	}

}