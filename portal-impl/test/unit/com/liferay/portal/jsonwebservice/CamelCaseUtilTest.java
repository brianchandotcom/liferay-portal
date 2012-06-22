/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.CamelCaseUtil;

import junit.framework.TestCase;

/**
 * @author Igor Spasic
 */
public class CamelCaseUtilTest extends TestCase {

	public void testFromCamelCase() {
		assertEquals("camel-case", CamelCaseUtil.fromCamelCase("camelCase"));
		assertEquals(
			"camel-case-word", CamelCaseUtil.fromCamelCase("camelCASEWord"));
		assertEquals("camel-case", CamelCaseUtil.fromCamelCase("camelCASE"));
	}

	public void testNormalization() {
		assertEquals(
			"camelCase", CamelCaseUtil.normalizeCamelCase("camelCase"));
		assertEquals(
			"camelCaseWord", CamelCaseUtil.normalizeCamelCase("camelCASEWord"));
		assertEquals(
			"camelCase", CamelCaseUtil.normalizeCamelCase("camelCASE"));
	}

	public void testToCamelCase() {
		assertEquals("camelCase", CamelCaseUtil.toCamelCase("camel-case"));
		assertEquals(
			"camelCASEWord", CamelCaseUtil.toCamelCase("camel-CASE-word"));
		assertEquals("camelCASE", CamelCaseUtil.toCamelCase("camel-CASE"));
	}

}