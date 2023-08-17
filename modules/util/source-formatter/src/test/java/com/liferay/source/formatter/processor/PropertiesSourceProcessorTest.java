/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import org.junit.Test;

/**
 * @author Alan Huang
 */
public class PropertiesSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testIncorrectWhitespaceCheck() throws Exception {
		test("IncorrectWhitespaceCheck.testproperties");
	}

	@Test
	public void testLanguageKeysContext() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"content/Language.testproperties"
			).addExpectedMessage(
				"The context '' is invalid in the key 'order[]'"
			).addExpectedMessage(
				"The context '...' is invalid in the key 'order[...]'"
			).addExpectedMessage(
				"The context '0' is invalid in the key 'order[0]'"
			).addExpectedMessage(
				"The context '123' is invalid in the key 'order[123]'"
			).addExpectedMessage(
				"The context 'abc' is invalid in the key 'order[abc]'"
			).addExpectedMessage(
				"The context 'x' is invalid in the key 'order[x]'"
			).addExpectedMessage(
				"The context 'xyz' is invalid in the key 'order[xyz]'"
			).addExpectedMessage(
				"The key 'a' should include a word of context at the end, " +
					"within a [], to indicate specific meaning"
			).addExpectedMessage(
				"The key 'add' should include a word of context at the end, " +
					"within a [], to indicate specific meaning"
			).addExpectedMessage(
				"The key 'alert' should include a word of context at the " +
					"end, within a [], to indicate specific meaning"
			).addExpectedMessage(
				"The key 'average' should include a word of context at the " +
					"end, within a [], to indicate specific meaning"
			).addExpectedMessage(
				"The key 'order' should include a word of context at the " +
					"end, within a [], to indicate specific meaning"
			).addExpectedMessage(
				"The single-word key 'abstract' should include a word of " +
					"context at the end, within a [], to indicate specific " +
						"meaning"
			).addExpectedMessage(
				"The single-word key 'alert' should include a word of " +
					"context at the end, within a [], to indicate specific " +
						"meaning"
			).addExpectedMessage(
				"The single-word key 'average' should include a word of " +
					"context at the end, within a [], to indicate specific " +
						"meaning"
			).addExpectedMessage(
				"The single-word key 'order' should include a word of " +
					"context at the end, within a [], to indicate specific " +
						"meaning"
			));
	}

	@Test
	public void testSortDefinitionKeys() throws Exception {
		test("FormatProperties1/liferay-plugin-package.testproperties");
		test("FormatProperties1/TLiferayBatchFileProperties.testproperties");
	}

	@Test
	public void testSortProperties() throws Exception {
		test("FormatProperties2/test.testproperties");
	}

	@Test
	public void testSQLStylingCheck() throws Exception {
		test("FormatProperties3/test.testproperties");
	}

	@Test
	public void testStylingCheck() throws Exception {
		test("StylingCheck.testproperties");
	}

}