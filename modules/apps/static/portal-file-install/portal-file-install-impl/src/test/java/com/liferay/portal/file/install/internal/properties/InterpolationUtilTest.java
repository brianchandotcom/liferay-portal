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

package com.liferay.portal.file.install.internal.properties;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class InterpolationUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_oldSystemValue = System.setProperty(_SYSTEM_KEY, _SYSTEM_VALUE);
	}

	@AfterClass
	public static void tearDownClass() {
		if (_oldSystemValue == null) {
			System.clearProperty(_SYSTEM_KEY);
		}
		else {
			System.setProperty(_SYSTEM_KEY, _oldSystemValue);
		}
	}

	@Test
	public void testBlank() {
		Assert.assertEquals(
			StringPool.BLANK,
			InterpolationUtil.substVars("${testSystemKeyNonexistent}"));
	}

	@Test
	public void testExtraCloseBrace() {
		Assert.assertEquals(
			_SYSTEM_VALUE.concat(StringPool.CLOSE_CURLY_BRACE),
			InterpolationUtil.substVars(
				_SUBSTITUTION.concat(StringPool.CLOSE_CURLY_BRACE)));
	}

	@Test
	public void testInlineSubstitution() {
		Assert.assertEquals(
			"test" + _SYSTEM_VALUE + "test",
			InterpolationUtil.substVars("test" + _SUBSTITUTION + "test"));
	}

	@Test
	public void testMultipleSubstitution() {
		Assert.assertEquals(
			_SYSTEM_VALUE + _SYSTEM_VALUE,
			InterpolationUtil.substVars(_SUBSTITUTION + _SUBSTITUTION));
	}

	@Test
	public void testNestedSub() {
		String systemKey2 = "testSystemKey2";

		String oldSystemValue = System.setProperty(systemKey2, _SYSTEM_KEY);

		try {
			Assert.assertEquals(
				_SYSTEM_VALUE,
				InterpolationUtil.substVars("${${" + systemKey2 + "}}"));
		}
		finally {
			if (oldSystemValue == null) {
				System.clearProperty(systemKey2);
			}
			else {
				System.setProperty(systemKey2, _oldSystemValue);
			}
		}
	}

	@Test
	public void testSubstitution() {
		Assert.assertEquals(
			_SYSTEM_VALUE, InterpolationUtil.substVars(_SUBSTITUTION));
	}

	private static final String _SUBSTITUTION =
		"${" + InterpolationUtilTest._SYSTEM_KEY + "}";

	private static final String _SYSTEM_KEY = "testSystemKey";

	private static final String _SYSTEM_VALUE = "testSystemValue";

	private static String _oldSystemValue;

}