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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionTracker;
import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class AllFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_allFunction = new AllFunction(_ddmExpressionFactoryImpl);

		ReflectionTestUtil.setFieldValue(
			_ddmExpressionFactoryImpl, "ddmExpressionFunctionTracker",
			Mockito.mock(DDMExpressionFunctionTracker.class));
	}

	@Test
	public void testArrayFalse() {
		Boolean result = _allFunction.apply(
			"#value# >= 1",
			new BigDecimal[] {
				new BigDecimal(0), new BigDecimal(2), new BigDecimal(3)
			});

		Assert.assertFalse(result);
	}

	@Test
	public void testArrayTrue() {
		Boolean result = _allFunction.apply(
			"#value# >= 1",
			new BigDecimal[] {
				new BigDecimal(1), new BigDecimal(2), new BigDecimal(3)
			});

		Assert.assertTrue(result);
	}

	@Test
	public void testEmptyArray() {
		Boolean result = _allFunction.apply("#value# >= 1", new BigDecimal[0]);

		Assert.assertFalse(result);
	}

	@Test
	public void testInvalidExpression1() {
		Boolean result = _allFunction.apply("#invalid# > 10", 11);

		Assert.assertFalse(result);
	}

	@Test
	public void testInvalidExpression2() {
		Boolean result = _allFunction.apply("#value# >>> 10", 11);

		Assert.assertFalse(result);
	}

	@Test
	public void testSingleValue() {
		Boolean result = _allFunction.apply("#value# >= 1", 2);

		Assert.assertTrue(result);
	}

	private static AllFunction _allFunction;
	private static final DDMExpressionFactoryImpl _ddmExpressionFactoryImpl =
		new DDMExpressionFactoryImpl();

}