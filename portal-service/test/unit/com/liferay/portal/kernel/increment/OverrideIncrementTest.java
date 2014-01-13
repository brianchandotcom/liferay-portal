/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.increment;

import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class OverrideIncrementTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testConstructorAndFactory() {
		IntegerOverrideIncrement integerOverrideIncrement =
			new IntegerOverrideIncrement(1);

		Assert.assertEquals(Integer.valueOf(1), integerOverrideIncrement.value);

		IntegerOverrideIncrement anotherIntegerOverrideIncrement =
			integerOverrideIncrement.createOverrideIncrement(2);

		Assert.assertNotSame(
			integerOverrideIncrement, anotherIntegerOverrideIncrement);

		Assert.assertEquals(
			Integer.valueOf(2), anotherIntegerOverrideIncrement.value);
	}

	@Test
	public void testGetterAndSetter() throws Exception {
		IntegerOverrideIncrement integerOverrideIncrement =
			new IntegerOverrideIncrement(1);

		Assert.assertEquals(
			Integer.valueOf(1), integerOverrideIncrement.getValue());

		integerOverrideIncrement.setValue(2);

		Method bridgeGetValue = ReflectionUtil.getBridgeMethod(
			OverrideIncrement.class, "getValue");

		Assert.assertEquals(
			Integer.valueOf(2),
			bridgeGetValue.invoke(integerOverrideIncrement));

		Assert.assertEquals(
			Integer.valueOf(2), integerOverrideIncrement.getValue());
	}

	@Test
	public void testIncreaseAndDecrease() {
		IntegerOverrideIncrement integerOverrideIncrement =
			new IntegerOverrideIncrement(1);

		integerOverrideIncrement.increase(2);

		Assert.assertEquals(
			Integer.valueOf(2), integerOverrideIncrement.getValue());

		integerOverrideIncrement.increase(1);

		Assert.assertEquals(
			Integer.valueOf(2), integerOverrideIncrement.getValue());

		integerOverrideIncrement.decrease(1);

		Assert.assertEquals(
			Integer.valueOf(1), integerOverrideIncrement.getValue());

		integerOverrideIncrement.decrease(2);

		Assert.assertEquals(
			Integer.valueOf(1), integerOverrideIncrement.getValue());
	}

	@Test
	public void testIncreaseAndDecreaseForNew() {
		IntegerOverrideIncrement integerOverrideIncrement =
			new IntegerOverrideIncrement(1);

		OverrideIncrement<Integer> overrideIncrement =
			integerOverrideIncrement.increaseForNew(2);

		Assert.assertNotSame(integerOverrideIncrement, overrideIncrement);
		Assert.assertEquals(
			Integer.valueOf(1), integerOverrideIncrement.getValue());
		Assert.assertEquals(Integer.valueOf(2), overrideIncrement.getValue());

		overrideIncrement = integerOverrideIncrement.increaseForNew(0);

		Assert.assertNotSame(integerOverrideIncrement, overrideIncrement);
		Assert.assertEquals(
			Integer.valueOf(1), integerOverrideIncrement.getValue());
		Assert.assertEquals(Integer.valueOf(1), overrideIncrement.getValue());

		overrideIncrement = integerOverrideIncrement.decreaseForNew(0);

		Assert.assertNotSame(integerOverrideIncrement, overrideIncrement);
		Assert.assertEquals(
			Integer.valueOf(1), integerOverrideIncrement.getValue());
		Assert.assertEquals(Integer.valueOf(0), overrideIncrement.getValue());

		overrideIncrement = integerOverrideIncrement.decreaseForNew(2);

		Assert.assertNotSame(integerOverrideIncrement, overrideIncrement);
		Assert.assertEquals(
			Integer.valueOf(1), integerOverrideIncrement.getValue());
		Assert.assertEquals(Integer.valueOf(1), overrideIncrement.getValue());
	}

	private static class IntegerOverrideIncrement
		extends OverrideIncrement<Integer> {

		public IntegerOverrideIncrement(Integer value) {
			super(value);
		}

		@Override
		protected IntegerOverrideIncrement createOverrideIncrement(
			Integer value) {

			return new IntegerOverrideIncrement(value);
		}

	}

}