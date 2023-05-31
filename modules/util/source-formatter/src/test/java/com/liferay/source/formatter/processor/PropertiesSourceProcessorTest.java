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
				"LanguageKeysContext.testproperties"
			).addExpectedMessage(
				"The Key 'a' should with [context] to indicate specific meaning"
			).addExpectedMessage(
				"The Key 'add' should with [context] to indicate specific " +
					"meaning"
			).addExpectedMessage(
				"The Key 'alert' should with [context] to indicate specific " +
					"meaning"
			).addExpectedMessage(
				"The Key 'answer[n]' contain unqualified context for 'n'"
			).addExpectedMessage(
				"The Key 'average' should with [context] to indicate " +
					"specific meaning"
			).addExpectedMessage(
				"The Key 'order' should with [context] to indicate specific " +
					"meaning"
			).addExpectedMessage(
				"The Key 'order[...]' contain unqualified context for '...'"
			).addExpectedMessage(
				"The Key 'order[0]' contain unqualified context for '0'"
			).addExpectedMessage(
				"The Key 'order[123]' contain unqualified context for '123'"
			).addExpectedMessage(
				"The Key 'order[]' contain unqualified context for ''"
			).addExpectedMessage(
				"The Key 'order[abc]' contain unqualified context for 'abc'"
			).addExpectedMessage(
				"The Key 'order[x]' contain unqualified context for 'x'"
			).addExpectedMessage(
				"The Key 'order[xyz]' contain unqualified context for 'xyz'"
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