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

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Leonardo Barros
 */
@RunWith(MockitoJUnitRunner.class)
public class AllFunctionTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_allFunction = new AllFunction(_ddmExpressionFactoryImpl);

		field(
			DDMExpressionFactoryImpl.class, "ddmExpressionFunctionTracker"
		).set(
			_ddmExpressionFactoryImpl, mock(DDMExpressionFunctionTracker.class)
		);
	}

	@Test
	public void testArrayFalse() throws Exception {
		Boolean result = (Boolean)_allFunction.apply(
			"#value# >= 1",
			new BigDecimal[] {
				new BigDecimal(0), new BigDecimal(2), new BigDecimal(3)
			});

		Assert.assertFalse(result);
	}

	@Test
	public void testArrayTrue() throws Exception {
		Boolean result = (Boolean)_allFunction.apply(
			"#value# >= 1",
			new BigDecimal[] {
				new BigDecimal(1), new BigDecimal(2), new BigDecimal(3)
			});

		Assert.assertTrue(result);
	}

	@Test
	public void testEmptyArray() throws Exception {
		Boolean result = (Boolean)_allFunction.apply(
			"#value# >= 1", new BigDecimal[0]);

		Assert.assertFalse(result);
	}

	@Test
	public void testInvalidExpression1() throws Exception {
		Boolean result = (Boolean)_allFunction.apply("#invalid# > 10", 11);

		Assert.assertFalse(result);
	}

	@Test
	public void testInvalidExpression2() throws Exception {
		Boolean result = (Boolean)_allFunction.apply("#value# >>> 10", 11);

		Assert.assertFalse(result);
	}

	@Test
	public void testSingleValue() throws Exception {
		Boolean result = (Boolean)_allFunction.apply("#value# >= 1", 2);

		Assert.assertTrue(result);
	}

	private AllFunction _allFunction;
	private final DDMExpressionFactoryImpl _ddmExpressionFactoryImpl =
		new DDMExpressionFactoryImpl();

}