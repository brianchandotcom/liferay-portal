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
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael Hashimoto
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class SeleniumBuilderContextTest {

	@Test
	public void testAction() throws Exception {
		test("Action.action");
	}

	@Test
	public void testActionName1008() throws Exception {
		test(
			"BaseLiferay.action",
			"Error 1008: Duplicate name BaseLiferay at " + _DIR_NAME +
				"/BaseLiferay.action");
	}

	@Test
	public void testFunction() throws Exception {
		test("Function.function");
	}

	@Test
	public void testFunctionName1008() throws Exception {
		test(
			"Click.function",
			"Error 1008: Duplicate name Click at " + _DIR_NAME +
				"/Click.function");
	}

	@Test
	public void testMacro() throws Exception {
		test("Macro.macro");
	}

	@Test
	public void testMacroName1008() throws Exception {
		test(
			"BlogsEntry.macro",
			"Error 1008: Duplicate name BlogsEntry at " + _DIR_NAME +
				"/BlogsEntry.macro");
	}

	@Test
	public void testTestCase() throws Exception {
		test("TestCase.testcase");
	}

	@Test
	public void testTestCaseName1008() throws Exception {
		test(
			"CPBlogsAcceptance.testcase",
			"Error 1008: Duplicate name CPBlogsAcceptance at " + _DIR_NAME +
				"/CPBlogsAcceptance.testcase");
	}

	@Test
	public void testTestSuite() throws Exception {
		test("TestSuite.testsuite");
	}

	@Test
	public void testTestSuiteName1008() throws Exception {
		test(
			"CollaborationAcceptance.testsuite",
			"Error 1008: Duplicate name CollaborationAcceptance at " +
				_DIR_NAME + "/CollaborationAcceptance.testsuite");
	}

	protected void test(String fileName) throws Exception {
		test(fileName, null, false);
	}

	protected void test(String fileName, String expectedErrorMessage)
		throws Exception {

		test(fileName, expectedErrorMessage, true);
	}

	protected void test(
			String fileName, String expectedErrorMessage,
			boolean expectException)
		throws Exception {

		String actualErrorMessage = null;

		try {
			SeleniumBuilderContext seleniumBuilderContext =
				new SeleniumBuilderContext(_BASE_DIR);

			seleniumBuilderContext.addFile(_DIR_NAME + "/" + fileName);
		}
		catch (IllegalArgumentException iae) {
			actualErrorMessage = iae.getMessage();
		}
		finally {
			if (expectException) {
				Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
			}
			else {
				Assert.assertEquals(null, actualErrorMessage);
			}
		}
	}

	private static final String _BASE_DIR = "./portal-web/test/functional/";

	private static final String _DIR_NAME =
		"/../../../portal-impl/test/integration/com/liferay/portal/tools/" +
			"seleniumbuilder/dependencies";

}