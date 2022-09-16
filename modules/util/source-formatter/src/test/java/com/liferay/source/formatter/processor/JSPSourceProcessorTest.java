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
 * @author André de Oliveira
 */
public class JSPSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testCombineJavaSourceBlocks() throws Exception {
		test(
			"CombineJavaSourceBlocks1.testjsp",
			"'<%!...%>' block should come after <%...%> blcok", 19);
		test(
			"CombineJavaSourceBlocks2.testjsp",
			"Combine <%!...%> blocks at line '29' and '34'");
	}

	@Test
	public void testFormatBooleanScriptlet() throws Exception {
		test("FormatBooleanScriptlet.testjsp");
	}

	@Test
	public void testFormatImportsAndTaglibs() throws Exception {
		test("FormatImportsAndTaglibs.testjsp");
	}

	@Test
	public void testFormatJSPExpressionTag() throws Exception {
		test("FormatJSPExpressionTag.testjsp");
	}

	@Test
	public void testFormatSelfClosingTags() throws Exception {
		test("FormatSelfClosingTags.testjsp");
	}

	@Test
	public void testFormatTagAttributes() throws Exception {
		test("FormatTagAttributes.testjsp");
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
	public void testGetStaticResourceURL() throws Exception {
		test("GetStaticResourceURL.testjsp");
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
	public void testIncorrectMethodCalls() throws Exception {
		test(
			"IncorrectMethodCalls.testjsp",
			new String[] {
				"Use type 'LiferayPortletResponse' to call 'getNamespace()'",
				"Use type 'LiferayPortletResponse' to call 'getNamespace()'"
			},
			new Integer[] {21, 28});
	}

	@Test
	public void testLanguageUtilCall() throws Exception {
		test(
			"LanguageUtilCall.testjsp",
			"Use <liferay-ui:message> tag instead of LanguageUtil.get", 17);
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