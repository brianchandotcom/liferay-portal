/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.adapter;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class ModelAdapterUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAdapt() {
		Assert.assertEquals(
			ModelAdapterUtil.adapt(TestInterface.class, new TestClass(1)),
			new TestClass(1));
		Assert.assertEquals(
			ModelAdapterUtil.adapt(TestInterface.class, new TestClass(1)),
			ModelAdapterUtil.adapt(TestInterface.class, new TestClass(1)));

		TestInterface proxyObject = ModelAdapterUtil.adapt(
			TestInterface.class, new TestClass(1));

		Assert.assertEquals(0, proxyObject.compareTo(new TestClass(1)));
		Assert.assertEquals(-1, proxyObject.compareTo(new TestClass(2)));
		Assert.assertEquals(1, proxyObject.compareTo(new TestClass(0)));

		Assert.assertEquals(
			0,
			proxyObject.compareTo(
				ModelAdapterUtil.adapt(TestInterface.class, new TestClass(1))));
		Assert.assertEquals(
			-1,
			proxyObject.compareTo(
				ModelAdapterUtil.adapt(TestInterface.class, new TestClass(2))));
		Assert.assertEquals(
			1,
			proxyObject.compareTo(
				ModelAdapterUtil.adapt(TestInterface.class, new TestClass(0))));
	}

	private class TestClass implements TestInterface {

		@Override
		public int compareTo(TestInterface testInterface) {
			if (testInterface instanceof TestClass) {
				TestClass testClass = (TestClass)testInterface;

				return _id - testClass._id;
			}

			throw new IllegalArgumentException(
				"Unable to compare with " + testInterface);
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof TestClass)) {
				return false;
			}

			TestClass testClass = (TestClass)object;

			if (_id == testClass._id) {
				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return _id;
		}

		private TestClass(int id) {
			_id = id;
		}

		private final int _id;

	}

	private interface TestInterface extends Comparable<TestInterface> {
	}

}