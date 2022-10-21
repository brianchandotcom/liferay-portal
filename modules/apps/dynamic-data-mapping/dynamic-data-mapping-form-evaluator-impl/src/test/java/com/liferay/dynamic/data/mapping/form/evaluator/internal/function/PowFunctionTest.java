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

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Selton Guedes
 */
@RunWith(Parameterized.class)
public class PowFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(name = "n1={0}, n2={1}, expectedObject={2}")
	public static List<BigDecimal[]> data() {
		return Arrays.asList(
			new BigDecimal[][] {
				{new BigDecimal(3), new BigDecimal(4), new BigDecimal(81)},
				{new BigDecimal(6), new BigDecimal(3), new BigDecimal(216)}
			});
	}

	@Test
	public void testApply() {
		PowFunction powFunction = new PowFunction();

		BigDecimal actualResult = powFunction.apply(n1, n2);

		Assert.assertEquals(expectedObject, actualResult);
	}

	@Parameterized.Parameter(2)
	public BigDecimal expectedObject;

	@Parameterized.Parameter
	public BigDecimal n1;

	@Parameterized.Parameter(1)
	public BigDecimal n2;

}