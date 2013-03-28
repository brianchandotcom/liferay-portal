/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.seleniumbuilder;

import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael Hashimoto
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class SeleniumBuilderTest {

	@Before
	public void setUp() throws Exception {
		_seleniumBuilderFileUtil = new SeleniumBuilderFileUtil(".");
	}

	@Test
	public void testFunction() throws Exception {
		test("Function.function");
	}

	@Test
	public void testFunctionCommandElement1001() throws Exception {
		test(
			"FunctionCommandElement1001.function",
			"Error 1001: Missing (execute|if) child element in " + _DIR_NAME +
				"/FunctionCommandElement1001.function:2");
	}

	@Test
	public void testFunctionCommandElement1002() throws Exception {
		test(
			"FunctionCommandElement1002.function",
			"Error 1002: Invalid var element in " + _DIR_NAME +
				"/FunctionCommandElement1002.function:3");
	}

	@Test
	public void testFunctionCommandElement1003_1() throws Exception {
		test(
			"FunctionCommandElement1003_1.function",
			"Error 1003: Missing name attribute in " + _DIR_NAME +
				"/FunctionCommandElement1003_1.function:2");
	}

	@Test
	public void testFunctionCommandElement1003_2() throws Exception {
		test(
			"FunctionCommandElement1003_2.function",
			"Error 1003: Missing name attribute in " + _DIR_NAME +
				"/FunctionCommandElement1003_2.function:2");
	}

	@Test
	public void testFunctionCommandElement1003_3() throws Exception {
		test(
			"FunctionCommandElement1003_3.function",
			"Error 1003: Missing name attribute in " + _DIR_NAME +
				"/FunctionCommandElement1003_3.function:2");
	}

	@Test
	public void testFunctionDefinitionElement1000() throws Exception {
		test(
			"FunctionDefinitionElement1000.function",
			"Error 1000: Invalid root element in " + _DIR_NAME +
				"/FunctionDefinitionElement1000.function:1");
	}

	@Test
	public void testFunctionDefinitionElement1001() throws Exception {
		test(
			"FunctionDefinitionElement1001.function",
			"Error 1001: Missing (command) child element in " + _DIR_NAME +
				"/FunctionDefinitionElement1001.function:1");
	}

	@Test
	public void testFunctionDefinitionElement1002() throws Exception {
		test(
			"FunctionDefinitionElement1002.function",
			"Error 1002: Invalid command-fail element in " + _DIR_NAME +
				"/FunctionDefinitionElement1002.function:2");
	}

	@Test
	public void testFunctionExecuteElement1004_1() throws Exception {
		test(
			"FunctionExecuteElement1004_1.function",
			"Error 1004: Missing (function|selenium) attribute in " +
				_DIR_NAME + "/FunctionExecuteElement1004_1.function:3");
	}

	@Test
	public void testFunctionExecuteElement1004_2() throws Exception {
		test(
			"FunctionExecuteElement1004_2.function",
			"Error 1004: Missing (function|selenium) attribute in " +
				_DIR_NAME + "/FunctionExecuteElement1004_2.function:3");
	}

	@Test
	public void testFunctionExecuteElement1006() throws Exception {
		test(
			"FunctionExecuteElement1006.function",
			"Error 1006: Invalid function attribute value in " + _DIR_NAME +
				"/FunctionExecuteElement1006.function:3");
	}

	protected void test(String fileName) throws Exception {
		test(fileName, null);
	}

	protected void test(String fileName, String errorMessage) throws Exception {
		try {
			_seleniumBuilderFileUtil.getRootElement(_DIR_NAME + "/" + fileName);
		}
		catch (IllegalArgumentException e) {
			Assert.assertEquals(errorMessage, e.getMessage());
		}
	}

	private static final String _DIR_NAME =
		"portal-impl/test/integration/com/liferay/portal/tools/" +
			"seleniumbuilder/dependencies";

	private SeleniumBuilderFileUtil _seleniumBuilderFileUtil;

}