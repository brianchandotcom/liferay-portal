/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.writer;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carlos Correa
 */
public class ItemClassIndexUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void test() throws Exception {
		Assert.assertEquals(
			HashMapBuilder.put(
				"abcTestEnum",
				new ObjectValuePair<>(
					TestClass.class.getDeclaredField("abcTestEnum"),
					TestClass.class.getMethod("getABCTestEnum"))
			).put(
				"missingGetterTestStringField1",
				new ObjectValuePair<>(
					TestClass.class.getDeclaredField(
						"missingGetterTestStringField1"),
					null)
			).put(
				"missingGetterTestStringField2",
				new ObjectValuePair<>(
					TestClass.class.getDeclaredField(
						"_missingGetterTestStringField2"),
					null)
			).put(
				"testIntegerField1",
				new ObjectValuePair<>(
					TestClass.class.getDeclaredField("testIntegerField1"),
					TestClass.class.getMethod("getTestIntegerField1"))
			).put(
				"testIntegerField2",
				new ObjectValuePair<>(
					TestClass.class.getDeclaredField("_testIntegerField2"),
					TestClass.class.getMethod("getTestIntegerField2"))
			).put(
				"testStringField1",
				new ObjectValuePair<>(
					TestClass.class.getDeclaredField("testStringField1"),
					TestClass.class.getMethod("getTestStringField1"))
			).put(
				"testStringField2",
				new ObjectValuePair<>(
					TestClass.class.getDeclaredField("_testStringField2"),
					TestClass.class.getMethod("getTestStringField2"))
			).build(),
			ItemClassIndexUtil.index(TestClass.class));
	}

	private static class TestClass {

		public ABCTestEnum getABCTestEnum() {
			return abcTestEnum;
		}

		public Integer getTestIntegerField1() {
			return testIntegerField1;
		}

		public Integer getTestIntegerField2() {
			return _testIntegerField2;
		}

		public String getTestStringField1() {
			return testStringField1;
		}

		public String getTestStringField2() {
			return _testStringField2;
		}

		protected ABCTestEnum abcTestEnum;
		protected String missingGetterTestStringField1;
		protected Integer testIntegerField1;
		protected String testStringField1;

		private String _missingGetterTestStringField2;
		private Integer _testIntegerField2;
		private String _testStringField2;

		private enum ABCTestEnum {

			VALUE_1, VALUE_2

		}

	}

}