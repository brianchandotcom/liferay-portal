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

package com.liferay.portal.kernel.util;

import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jiaxu Wei
 */
public class SystemPropertiesTest {

	@After
	public void tearDown() {
		SystemProperties.clear(_TEST_KEY);
	}

	@Test
	public void testGetArray() {
		Assert.assertTrue(
			ArrayUtil.isEmpty(SystemProperties.getArray(_TEST_KEY)));

		SystemProperties.set(_TEST_KEY, "test.array.value,test.array.value");

		Assert.assertArrayEquals(
			new String[] {"test.array.value", "test.array.value"},
			SystemProperties.getArray(_TEST_KEY));
	}

	@Test
	public void testGetProperties() {
		Properties properties1 = SystemProperties.getProperties();

		Assert.assertNull(properties1.getProperty(_TEST_KEY));

		SystemProperties.set(_TEST_KEY, _TEST_VALUE);

		Properties properties2 = SystemProperties.getProperties();

		Assert.assertEquals(properties1.size() + 1, properties2.size());

		Assert.assertNull(properties1.getProperty(_TEST_KEY));
		Assert.assertEquals(_TEST_VALUE, properties2.get(_TEST_KEY));
	}

	@Test
	public void testGetSetAndClear() {
		Assert.assertNull(SystemProperties.get(_TEST_KEY));

		// Property set via SystemProperties is also set to System.props

		SystemProperties.set(_TEST_KEY, _TEST_VALUE);

		Assert.assertEquals(_TEST_VALUE, SystemProperties.get(_TEST_KEY));
		Assert.assertEquals(_TEST_VALUE, System.getProperty(_TEST_KEY));

		// Property cleared via SystemProperties is also removed from
		// System.props

		SystemProperties.clear(_TEST_KEY);

		Assert.assertNull(SystemProperties.get(_TEST_KEY));
		Assert.assertNull(System.getProperty(_TEST_KEY));

		// Property in System.props is also accessible via SystemProperties

		System.setProperty(_TEST_KEY, _TEST_VALUE);

		Assert.assertEquals(_TEST_VALUE, SystemProperties.get(_TEST_KEY));
		Assert.assertEquals(_TEST_VALUE, System.getProperty(_TEST_KEY));
	}

	@Test
	public void testGetWithDefaultValue() {
		Assert.assertNull(SystemProperties.get(_TEST_KEY));

		Assert.assertEquals(
			"defaultValue", SystemProperties.get(_TEST_KEY, "defaultValue"));

		SystemProperties.set(_TEST_KEY, _TEST_VALUE);

		Assert.assertEquals(
			_TEST_VALUE, SystemProperties.get(_TEST_KEY, "defaultValue"));
	}

	private static final String _TEST_KEY =
		SystemPropertiesTest.class.getName() + ".test.key";

	private static final String _TEST_VALUE = "test.value";

}