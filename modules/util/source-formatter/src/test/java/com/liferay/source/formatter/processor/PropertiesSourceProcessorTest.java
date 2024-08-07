/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import com.liferay.petra.string.StringBundler;

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
				"IncorrectMethodCalls.testjsp"
			).addExpectedMessage(
				"The key 'a[weekday]' should include a word of context at " +
					"the end, within a [], to indicate specific meaning",
				13
			).addExpectedMessage(
				StringBundler.concat(
					"The single-word key 'abstract' should include a word of ",
					"context at the end, within a [], to indicate specific ",
					"meaning"),
				16
			).addExpectedMessage(
				"The key 'add[button]' should include a word of context at " +
					"the end, within a [], to indicate specific meaning",
				29
			).addExpectedMessage(
				"The key 'alert[noun]' should include a word of context at " +
					"the end, within a [], to indicate specific meaning",
				32
			).addExpectedMessage(
				"The key 'alert[adj]' should include a word of context at " +
					"the end, within a [], to indicate specific meaning",
				33
			).addExpectedMessage(
				StringBundler.concat(
					"The single-word key 'average' should include a word of ",
					"context at the end, within a [], to indicate specific ",
					"meaning"),
				48
			).addExpectedMessage(
				"The key 'average[verb]' should include a word of context at " +
					"the end, within a [], to indicate specific meaning",
				49
			).addExpectedMessage(
				"The key 'average[v]' should include a word of context at " +
					"the end, within a [], to indicate specific meaning",
				50
			).addExpectedMessage(
				StringBundler.concat(
					"The key 'average[adjective]' should include a word of ",
					"context at the end, within a [], to indicate specific ",
					"meaning"),
				51
			).addExpectedMessage(
				StringBundler.concat(
					"The single-word key 'order' should include a word of ",
					"context at the end, within a [], to indicate specific ",
					"meaning"),
				53
			).addExpectedMessage(
				"The key 'order[list]' should include a word of context at " +
					"the end, within a [], to indicate specific meaning",
				54
			).addExpectedMessage(
				"The context '' is invalid in the key 'order[]'", 55
			).addExpectedMessage(
				"The key 'order[]' should include a word of context at the " +
					"end, within a [], to indicate specific meaning",
				55
			).addExpectedMessage(
				"The context '...' is invalid in the key 'order[...]'", 56
			).addExpectedMessage(
				"The key 'order[...]' should include a word of context at " +
					"the end, within a [], to indicate specific meaning",
				56
			).addExpectedMessage(
				"The context 'abc' is invalid in the key 'order[abc]'", 57
			).addExpectedMessage(
				"The key 'order[abc]' should include a word of context at " +
					"the end, within a [], to indicate specific meaning",
				57
			).addExpectedMessage(
				"The context 'xyz' is invalid in the key 'order[xyz]'", 58
			).addExpectedMessage(
				"The key 'order[xyz]' should include a word of context at " +
					"the end, within a [], to indicate specific meaning",
				58
			).addExpectedMessage(
				"The context '123' is invalid in the key 'order[123]'", 59
			).addExpectedMessage(
				"The key 'order[123]' should include a word of context at " +
					"the end, within a [], to indicate specific meaning",
				59
			).addExpectedMessage(
				"The context '0' is invalid in the key 'order[0]'", 60
			).addExpectedMessage(
				"The key 'order[0]' should include a word of context at the " +
					"end, within a [], to indicate specific meaning",
				60
			).addExpectedMessage(
				"The context 'x' is invalid in the key 'order[x]'", 61
			).addExpectedMessage(
				"The key 'order[x]' should include a word of context at the " +
					"end, within a [], to indicate specific meaning",
				61
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