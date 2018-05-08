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

package com.liferay.petra.model.adapter.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class ModelAdapterUtilTest {

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