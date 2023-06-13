/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
	public void testLanguageKeyContext() throws Exception {
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
				"The single-word key 'add' should include a word of context " +
					"at the end, within a [], to indicate specific meaning"
			).addExpectedMessage(
				"The single-word key 'alert' should include a word of " +
					"context at the end, within a [], to indicate specific " +
						"meaning"
			).addExpectedMessage(
				"The single-word key 'alerts' should include a word of " +
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
		test("FormatProperties/liferay-plugin-package.testproperties");
		test("FormatProperties/TLiferayBatchFileProperties.testproperties");
	}

	@Test
	public void testSortProperties() throws Exception {
		test("test.testproperties");
	}

	@Test
	public void testSQLStylingCheck() throws Exception {
		test("testSQLStylingCheck.testproperties");
	}

	@Test
	public void testStylingCheck() throws Exception {
		test("StylingCheck.testproperties");
	}

}