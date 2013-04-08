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

	protected void test(String fileName) throws Exception {
		test(fileName, null, false);
	}

	protected void test(String fileName, String expectedErrorMessage)
		throws Exception {

		test(fileName, expectedErrorMessage, true);
	}

	protected void test(
		String fileName, String expectedErrorMessage, boolean expectException)
			throws Exception {

		String actualErrorMessage = null;

		try {
			SeleniumBuilderContext seleniumBuilderContext =
				new SeleniumBuilderContext(
					_BASE_DIR, _DIR_NAME + "/" + fileName);
		}
		catch (IllegalArgumentException e) {
			actualErrorMessage = e.getMessage();
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

	private SeleniumBuilderFileUtil _seleniumBuilderFileUtil;

}