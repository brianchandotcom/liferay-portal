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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class BetweenFunctionTest {

	@Test
	public void testApplyFalse1() {
		BetweenFunction betweenFunction = new BetweenFunction();

		boolean result = betweenFunction.apply(
			BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ONE);

		Assert.assertFalse(result);
	}

	@Test
	public void testApplyFalse2() {
		BetweenFunction betweenFunction = new BetweenFunction();

		boolean result = betweenFunction.apply(
			BigDecimal.ONE, new BigDecimal(2), BigDecimal.TEN);

		Assert.assertFalse(result);
	}

	@Test
	public void testApplyTrue() {
		BetweenFunction betweenFunction = new BetweenFunction();

		boolean result = betweenFunction.apply(
			BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.TEN);

		Assert.assertTrue(result);
	}

}