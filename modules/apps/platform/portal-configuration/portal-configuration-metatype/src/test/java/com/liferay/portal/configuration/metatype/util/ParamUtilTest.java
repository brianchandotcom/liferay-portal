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

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.StringPool;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Jorge Ferrer
 */
@RunWith(Enclosed.class)
public class ParamUtilTest {

	public static final String BEAN_STRING = "BEAN";

	public static final String[] BEAN_STRING_ARRAY =
		new String[] {"BEAN1", "BEAN2"};

	public static final String PARAM_STRING = "PARAM";

	public static final String[] PARAM_STRING_ARRAY =
		new String[] {"PARAM1", "PARAM2"};

	public static class WhenSettingAParameterMap {

		@Before
		public void setUp() throws ConfigurationException {
			TestBeanInterface originalBean = _getTestBeanInterface();

			Map<String, String[]> parameterMap = new HashMap<>();

			parameterMap.put("testBoolean1", new String[] {"false"});
			parameterMap.put("testString1", new String[] {PARAM_STRING});
			parameterMap.put("testStringArray1", PARAM_STRING_ARRAY);

			_testBean = ParamUtil.setParameterMap(
				TestBeanInterface.class, originalBean, parameterMap);
		}

		@Test
		public void valuesInTheParameterMapAreReadFirst() throws Exception {
			Assert.assertEquals(false, _testBean.testBoolean1());
			Assert.assertEquals(PARAM_STRING, _testBean.testString1());
			Assert.assertArrayEquals(
				PARAM_STRING_ARRAY, _testBean.testStringArray1());
		}

		@Test
		public void valuesNotInTheParameterMapAreReadFromBean()
			throws Exception {

			Assert.assertEquals(true, _testBean.testBoolean2());
			Assert.assertEquals(BEAN_STRING, _testBean.testString2());
			Assert.assertArrayEquals(
				BEAN_STRING_ARRAY, _testBean.testStringArray2());
		}

		private TestBeanInterface _testBean;

	}

	public static class WhenSettingAParameterMapWithPrefixes {

		@Before
		public void setUp() throws ConfigurationException {
			TestBeanInterface originalBean = _getTestBeanInterface();

			Map<String, String[]> parameterMap = new HashMap<>();

			parameterMap.put("prefix--testBoolean1--", new String[] {"false"});
			parameterMap.put(
				"prefix--testString1--", new String[] {PARAM_STRING});
			parameterMap.put("prefix--testStringArray1--", PARAM_STRING_ARRAY);

			_testBean = ParamUtil.setParameterMap(
				TestBeanInterface.class, originalBean, parameterMap, "prefix--",
				StringPool.DOUBLE_DASH);
		}

		@Test
		public void valuesInTheParameterMapAreReadFirst() throws Exception {
			Assert.assertEquals(false, _testBean.testBoolean1());
			Assert.assertEquals(PARAM_STRING, _testBean.testString1());
			Assert.assertArrayEquals(
				PARAM_STRING_ARRAY, _testBean.testStringArray1());
		}

		@Test
		public void valuesNotInTheParameterMapAreReadFromBean()
			throws Exception {

			Assert.assertEquals(true, _testBean.testBoolean2());
			Assert.assertEquals(BEAN_STRING, _testBean.testString2());
			Assert.assertArrayEquals(
				BEAN_STRING_ARRAY, _testBean.testStringArray2());
		}

		private TestBeanInterface _testBean;

	}

	private static TestBeanInterface _getTestBeanInterface() {
		return new TestBeanInterface() {

			@Override
			public boolean testBoolean1() {
				return true;
			}

			@Override
			public boolean testBoolean2() {
				return true;
			}

			@Override
			public String testString1() {
				return BEAN_STRING;
			}

			@Override
			public String testString2() {
				return BEAN_STRING;
			}

			@Override
			public String[] testStringArray1() {
				return BEAN_STRING_ARRAY;
			}

			@Override
			public String[] testStringArray2() {
				return BEAN_STRING_ARRAY;
			}

		};
	}

	private interface TestBeanInterface {

		public boolean testBoolean1();

		public boolean testBoolean2();

		public String testString1();

		public String testString2();

		public String[] testStringArray1();

		public String[] testStringArray2();

	}

}