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

package com.liferay.source.formatter;

import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class JSPSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testFormatBooleanScriptlet() throws Exception {
		test("FormatBooleanScriptlet.testjsp");
	}

	@Test
	public void testFormatImportsAndTaglibs() throws Exception {
		test("FormatImportsAndTaglibs.testjsp");
	}

	@Test
	public void testFormatSelfClosingTags() throws Exception {
		test("FormatSelfClosingTags.testjsp");
	}

	@Test
	public void testFormatTaglibs() throws Exception {
		test("FormatTaglibs.testjsp");
	}

	@Test
	public void testFormatTagLineBreaks() throws Exception {
		test("FormatTagLineBreaks.testjsp");
	}

	@Test
	public void testIncorrectEmptyLine() throws Exception {
		test("IncorrectEmptyLine.testjsp");
	}

	@Test
	public void testIncorrectIndentation() throws Exception {
		test("IncorrectIndentation.testjsp");
	}

	@Test
	public void testMisplacedImport() throws Exception {
		test("MisplacedImport.testjsp", "Move imports to init.jsp");
	}

	@Test
	public void testMissingTaglibs() throws Exception {
		test(
			"MissingTaglibs.testjsp",
			new String[] {
				"Missing taglib for tag with prefix 'aui'",
				"Missing taglib for tag with prefix 'liferay-portlet'",
				"Missing taglib for tag with prefix 'liferay-ui'"
			});
	}

	@Test
	public void testSortTagAttributes() throws Exception {
		test("SortTagAttributes.testjsp");
	}

}