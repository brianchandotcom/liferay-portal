/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.jsonwebservice;

import junit.framework.TestCase;

/**
 * @author Igor Spasic
 */
public class CamelCaseUtilTest extends TestCase/* BaseTestCase*/ {

	public void testFromCamelCase() {
		assertEquals("camel-case", CamelCaseUtil.toSeparateWords("camelCase"));
		assertEquals(
			"camel-case-word", CamelCaseUtil.toSeparateWords("camelCASEWord"));
		assertEquals(
			"camel-case", CamelCaseUtil.toSeparateWords("camelCASE"));
	}

	public void testNormalization() {
		assertEquals("camelCase", CamelCaseUtil.fixCamelCase("camelCase"));
		assertEquals("camelCaseWord",
			CamelCaseUtil.fixCamelCase("camelCASEWord"));
		assertEquals("camelCase",
			CamelCaseUtil.fixCamelCase("camelCASE"));
	}

	public void testToCamelCase() {
		assertEquals("camelCase", CamelCaseUtil.toCamelCase("camel-case"));
		assertEquals(
			"camelCASEWord", CamelCaseUtil.toCamelCase("camel-CASE-word"));
		assertEquals(
			"camelCASE", CamelCaseUtil.toCamelCase("camel-CASE"));
	}

}