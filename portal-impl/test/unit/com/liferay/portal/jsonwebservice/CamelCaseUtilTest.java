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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Igor Spasic
 */
public class CamelCaseUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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
			"camelCase", CamelCaseUtil.normalizeCamelCase("camelCase", true));
		Assert.assertEquals(
			"camelCase", CamelCaseUtil.normalizeCamelCase("camelCase", false));
		Assert.assertEquals(
			"camelCaseWord",
			CamelCaseUtil.normalizeCamelCase("camelCASEWord", true));
		Assert.assertEquals(
			"camelCaseWord",
			CamelCaseUtil.normalizeCamelCase("camelCASEWord", false));
		Assert.assertEquals(
			"camelCase", CamelCaseUtil.normalizeCamelCase("camelCASE", true));
		Assert.assertEquals(
			"camelCase", CamelCaseUtil.normalizeCamelCase("camelCASE", false));
		Assert.assertEquals(
			"camelCase.fooUrl",
			CamelCaseUtil.normalizeCamelCase("camelCASE.fooURL", true));
		Assert.assertEquals(
			"camelCase.fooURL",
			CamelCaseUtil.normalizeCamelCase("camelCASE.fooURL", false));
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