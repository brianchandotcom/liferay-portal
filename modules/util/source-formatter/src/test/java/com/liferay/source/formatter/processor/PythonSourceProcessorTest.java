/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import org.junit.Test;

/**
 * @author Alan Huang
 */
public class PythonSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testIncorrectClassesAndMethodsOrder() throws Exception {
		test("IncorrectClassesAndMethodsOrder.testpy");
	}

	@Test
	public void testIncorrectImportsOrder() throws Exception {
		test("IncorrectImportsOrder.testpy");
	}

	@Test
	public void testIncorrectWhitespace() throws Exception {
		test("IncorrectWhitespace.testpy");
	}

}