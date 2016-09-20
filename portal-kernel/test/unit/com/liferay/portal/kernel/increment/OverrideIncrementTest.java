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

package com.liferay.portal.kernel.increment;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class OverrideIncrementTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructorAndFactory() {
		IntegerOverrideIncrement integerOverrideIncrement1 =
			new IntegerOverrideIncrement(1);

		Assert.assertEquals(
			Integer.valueOf(1), integerOverrideIncrement1.value);

		IntegerOverrideIncrement integerOverrideIncrement2 =
			integerOverrideIncrement1.createOverrideIncrement(2);

		Assert.assertNotSame(
			integerOverrideIncrement1, integerOverrideIncrement2);

		Assert.assertEquals(
			Integer.valueOf(2), integerOverrideIncrement2.value);
	}

	@Test
	public void testGetterAndSetter() {
		IntegerOverrideIncrement integerOverrideIncrement =
			new IntegerOverrideIncrement(1);

		Assert.assertEquals(
			Integer.valueOf(1), integerOverrideIncrement.getValue());

		integerOverrideIncrement.setValue(2);

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

}