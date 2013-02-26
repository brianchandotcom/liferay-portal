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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Spasic
 */
public class CamelCaseUtilTest {

	@Test
	public void testFromCamelCase() {
		Assert.assertEquals(
			"camel-case", CamelCaseUtil.fromCamelCase("camelCase"));
		Assert.assertEquals(
			"camel-case-word", CamelCaseUtil.fromCamelCase("camelCASEWord"));
		Assert.assertEquals(
			"camel-case", CamelCaseUtil.fromCamelCase("camelCASE"));
	}

	@Test
	public void testNormalization() {
		Assert.assertEquals(
			"camelCase", CamelCaseUtil.normalizeCamelCase("camelCase"));
		Assert.assertEquals(
			"camelCaseWord", CamelCaseUtil.normalizeCamelCase("camelCASEWord"));
		Assert.assertEquals(
			"camelCase", CamelCaseUtil.normalizeCamelCase("camelCASE"));
	}

	@Test
	public void testToCamelCase() {
		Assert.assertEquals(
			"camelCase", CamelCaseUtil.toCamelCase("camel-case"));
		Assert.assertEquals(
			"camelCASEWord", CamelCaseUtil.toCamelCase("camel-CASE-word"));
		Assert.assertEquals(
			"camelCASE", CamelCaseUtil.toCamelCase("camel-CASE"));
	}

}