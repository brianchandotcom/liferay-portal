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
 * @author Hugo Huijser
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
	public void testFunctionDefinitionElement1000() throws Exception {
		test(
			"FunctionDefinitionElement1000.function",
			"Error 1000: Invalid root element at portal-impl/test/" +
				"integration/com/liferay/portal/tools/seleniumbuilder/" +
				"dependencies/FunctionDefinitionElement1000.function:1");
	}

	@Test
	public void testFunctionDefinitionElement1001() throws Exception {
		test(
			"FunctionDefinitionElement1001.function",
			"Error 1001: Missing child elements at portal-impl/test/" +
				"integration/com/liferay/portal/tools/seleniumbuilder/" +
				"dependencies/FunctionDefinitionElement1001.function:1");
	}

	@Test
	public void testFunctionDefinitionElement1002() throws Exception {
		test(
			"FunctionDefinitionElement1002.function",
			"Error 1002: Invalid child element at portal-impl/test/" +
				"integration/com/liferay/portal/tools/seleniumbuilder/" +
				"dependencies/FunctionDefinitionElement1002.function:1");
	}

	protected void test(String fileName) throws Exception {
		test(fileName, null);
	}

	protected void test(String fileName, String errorMessage) throws Exception {
		fileName =
			"portal-impl/test/integration/com/liferay/portal/tools/" +
				"seleniumbuilder/dependencies/" + fileName;

		try {
			_seleniumBuilderFileUtil.getRootElement(fileName);
		}
		catch (IllegalArgumentException e) {
			Assert.assertEquals(e.getMessage(), errorMessage);
		}
	}

	private SeleniumBuilderFileUtil _seleniumBuilderFileUtil;

}