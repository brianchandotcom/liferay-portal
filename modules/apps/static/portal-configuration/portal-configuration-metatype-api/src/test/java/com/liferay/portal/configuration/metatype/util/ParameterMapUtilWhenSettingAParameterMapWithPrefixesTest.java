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

package com.liferay.portal.configuration.metatype.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Jorge Ferrer
 */
public class ParameterMapUtilWhenSettingAParameterMapWithPrefixesTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws ConfigurationException {
		_testBean = ParameterMapUtil.setParameterMap(
			ParameterMapUtilTestUtil.TestBean.class,
			ParameterMapUtilTestUtil.getTestBean(),
			HashMapBuilder.put(
				"prefix--testBoolean1--", new String[] {"false"}
			).put(
				"prefix--testString1--",
				new String[] {ParameterMapUtilTestUtil.PARAMETER_MAP_STRING}
			).put(
				"prefix--testStringArray1--",
				ParameterMapUtilTestUtil.PARAMETER_MAP_STRING_ARRAY
			).build(),
			"prefix--", StringPool.DOUBLE_DASH);
	}

	@Test
	public void testValuesInTheParameterMapAreReadFirst() {
		Assert.assertEquals(false, _testBean.testBoolean1());
		Assert.assertEquals(
			ParameterMapUtilTestUtil.PARAMETER_MAP_STRING,
			_testBean.testString1());
		Assert.assertArrayEquals(
			ParameterMapUtilTestUtil.PARAMETER_MAP_STRING_ARRAY,
			_testBean.testStringArray1());
	}

	@Test
	public void testValuesNotInTheParameterMapAreReadFromBean() {
		Assert.assertEquals(true, _testBean.testBoolean2());
		Assert.assertEquals(
			ParameterMapUtilTestUtil.TEST_BEAN_STRING, _testBean.testString2());
		Assert.assertArrayEquals(
			ParameterMapUtilTestUtil.TEST_BEAN_STRING_ARRAY,
			_testBean.testStringArray2());
	}

	private ParameterMapUtilTestUtil.TestBean _testBean;

}