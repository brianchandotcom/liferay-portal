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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.io.IOException;

import java.net.URL;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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

		Map<String, String> properties = SystemProperties.getProperties(
			"test.reference.key", false);

		for (String propertyKey : properties.keySet()) {
			SystemProperties.clear(propertyKey);
		}
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
	public void testGetPropertiesWithPrefix() {
		Map<String, String> propertiesWithPrefix =
			SystemProperties.getProperties(_PREFIX, false);

		Assert.assertTrue(propertiesWithPrefix.isEmpty());

		SystemProperties.set(_TEST_KEY, _TEST_VALUE);

		Assert.assertEquals(
			Collections.singletonMap("test.key", _TEST_VALUE),
			SystemProperties.getProperties(_PREFIX, true));

		Assert.assertEquals(
			Collections.singletonMap(_TEST_KEY, _TEST_VALUE),
			SystemProperties.getProperties(_PREFIX, false));
	}

	@Test
	public void testGetPropertyNames() {
		Set<String> propertyNames = SystemProperties.getPropertyNames();

		Assert.assertFalse(propertyNames.contains(_TEST_KEY));

		SystemProperties.set(_TEST_KEY, _TEST_VALUE);

		propertyNames = SystemProperties.getPropertyNames();

		Assert.assertTrue(propertyNames.contains(_TEST_KEY));
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

	@Test
	public void testLoad() throws IOException {
		Properties properties = new Properties();

		ReflectionTestUtil.invoke(
			SystemProperties.class, "_load",
			new Class<?>[] {URL.class, Properties.class},
			SystemProperties.class.getResource(
				"dependencies/system.properties"),
			properties);

		Assert.assertEquals(_TEST_VALUE, properties.get(_TEST_KEY));
	}

	@Test
	public void testReference() {
		SystemProperties.set("test.reference.key.origin", "test.value");

		// Correct References

		SystemProperties.set(
			"test.reference.key.1", "${test.reference.key.origin}");
		SystemProperties.set("test.reference.key.2", "${test.reference.key.1}");
		SystemProperties.set("test.reference.key.3", "${test.reference.key.2}");
		SystemProperties.set(
			"test.reference.key.4",
			"${test.reference.key.1},${test.reference.key.2}");

		Assert.assertEquals(
			"test.value", SystemProperties.get("test.reference.key.1"));
		Assert.assertEquals(
			"test.value", SystemProperties.get("test.reference.key.2"));
		Assert.assertEquals(
			"test.value", SystemProperties.get("test.reference.key.3"));
		Assert.assertEquals(
			"test.value,test.value",
			SystemProperties.get("test.reference.key.4"));

		SystemProperties.clear("test.reference.key.2");

		Assert.assertNull(SystemProperties.get("test.reference.key.2"));
		Assert.assertEquals(
			"test.value", SystemProperties.get("test.reference.key.1"));
		Assert.assertEquals(
			"${test.reference.key.2}",
			SystemProperties.get("test.reference.key.3"));
		Assert.assertEquals(
			"test.value,${test.reference.key.2}",
			SystemProperties.get("test.reference.key.4"));

		// Blank reference

		SystemProperties.set("test.reference.key.blank", "${}");

		Assert.assertEquals(
			"${}", SystemProperties.get("test.reference.key.blank"));

		// Nested references

		SystemProperties.set(
			"test.reference.key.nested",
			"${test.reference.key.origin${test.reference.key.origin}}");

		Assert.assertEquals(
			"${test.reference.key.origin${test.reference.key.origin}}",
			SystemProperties.get("test.reference.key.nested"));

		// Value contains a single symbol "}"

		SystemProperties.set(
			"test.reference.key.right.part",
			"test.reference.key.origin}${test.reference.key.origin}");

		Assert.assertEquals(
			"test.reference.key.origin}test.value",
			SystemProperties.get("test.reference.key.right.part"));

		// Value contains a single symbol "${"

		SystemProperties.set(
			"test.reference.key.left.part",
			"test.reference.key.origin${test.reference.key.origin}${");

		Assert.assertEquals(
			"test.reference.key.origintest.value${",
			SystemProperties.get("test.reference.key.left.part"));
	}

	private static final String _PREFIX =
		SystemPropertiesTest.class.getName() + StringPool.PERIOD;

	private static final String _TEST_KEY = _PREFIX + "test.key";

	private static final String _TEST_VALUE = "test.value";

}