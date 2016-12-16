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

package com.liferay.portal.configuration.metatype.bnd.util;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class ConfigurableUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testCreateConfigurable() {

		// Test Dictionary

		Dictionary<String, String> dictionary = new HashMapDictionary<>();

		dictionary.put("testReqiredString", "testReqiredString1");

		_assertTestConfiguration(
			ConfigurableUtil.createConfigurable(
				TestConfiguration.class, dictionary),
			"testReqiredString1");

		// Test Map

		Map<String, String> properties = new HashMap<>();

		properties.put("testReqiredString", "testReqiredString2");

		_assertTestConfiguration(
			ConfigurableUtil.createConfigurable(
				TestConfiguration.class, properties),
			"testReqiredString2");
	}

	@Test
	public void testMisc() {

		// Exception

		try {
			ConfigurableUtil.createConfigurable(
				TestConfiguration.class, Collections.emptyMap());

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertEquals(
				"Unable to create snapshot class for " +
					TestConfiguration.class,
				re.getMessage());

			Throwable throwable = re.getCause();

			throwable = throwable.getCause();

			Assert.assertTrue(throwable instanceof IllegalStateException);
			Assert.assertEquals(
				"Attribute is required but not set testReqiredString",
				throwable.getMessage());
		}

		// Constructor

		new ConfigurableUtil();
	}

	private void _assertTestConfiguration(
		TestConfiguration testConfiguration, String requiredString) {

		Assert.assertTrue(testConfiguration.testBoolean());
		Assert.assertEquals(1, testConfiguration.testByte());
		Assert.assertEquals(1.0, testConfiguration.testDouble(), 0);
		Assert.assertEquals(TestEnum.TEST_VALUE, testConfiguration.testEnum());
		Assert.assertEquals(1.0, testConfiguration.testFloat(), 0);
		Assert.assertEquals(100, testConfiguration.testInt());
		Assert.assertEquals(
			requiredString, testConfiguration.testReqiredString());
		Assert.assertEquals(1, testConfiguration.testShort());
		Assert.assertEquals("test_string", testConfiguration.testString());
		Assert.assertArrayEquals(
			new String[] {"test_string_1", "test_string_2"},
			testConfiguration.testStringArray());

		try {
			testConfiguration.testClass();

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
			Assert.assertEquals("Not supported yet.", uoe.getMessage());
		}
	}

	private class TestClass {
	}

	private interface TestConfiguration {

		@Meta.AD(deflt = "true", required = false)
		public boolean testBoolean();

		@Meta.AD(deflt = "1", required = false)
		public byte testByte();

		@Meta.AD(required = false)
		public TestClass testClass();

		@Meta.AD(deflt = "1.0", required = false)
		public double testDouble();

		@Meta.AD(deflt = "TEST_VALUE", required = false)
		public TestEnum testEnum();

		@Meta.AD(deflt = "1.0", required = false)
		public float testFloat();

		@Meta.AD(deflt = "100", required = false)
		public int testInt();

		@Meta.AD(deflt = "100", required = false)
		public long testLong();

		@Meta.AD(required = true)
		public String testReqiredString();

		@Meta.AD(deflt = "1", required = false)
		public short testShort();

		@Meta.AD(deflt = "test_string", required = false)
		public String testString();

		@Meta.AD(deflt = "test_string_1|test_string_2", required = false)
		public String[] testStringArray();

	}

	private enum TestEnum {

		TEST_VALUE

	}

}