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

package com.liferay.dynamic.data.mapping.expression.internal;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionTracker;
import com.liferay.dynamic.data.mapping.expression.internal.functions.DateValidationFunction;
import com.liferay.dynamic.data.mapping.expression.internal.functions.PowFunction;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Leonardo Barros
 */
@RunWith(MockitoJUnitRunner.class)
public class DDMExpressionFactoryImplTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpDDMExpressionFunctionTracker();
	}

	@Test
	public void testCreateDDMExpression() throws Exception {
		DDMExpression<BigDecimal> ddmExpression =
			_ddmExpressionFactoryImpl.createExpression(
				CreateExpressionRequest.Builder.newBuilder(
					"pow(2,3)"
				).build());

		BigDecimal bigDecimal = ddmExpression.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal(8)));
	}

	@Test
	public void testCreateDDMExpressionDateValidation() throws Exception {
		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactoryImpl.createExpression(
				CreateExpressionRequest.Builder.newBuilder(
					"dateValidation(\"{\"startsFrom\": \"responseDate\"}\")"
				).withDDMExpressionDateValidation(
					true
				).build());

		Assert.assertTrue(ddmExpression.evaluate());
	}

	private void _setUpDDMExpressionFunctionTracker() throws Exception {
		DDMExpressionFunctionTracker ddmExpressionFunctionTracker = mock(
			DDMExpressionFunctionTracker.class);

		when(
			ddmExpressionFunctionTracker.getDDMExpressionFunctionFactories(
				Matchers.any())
		).thenReturn(
			HashMapBuilder.<String, DDMExpressionFunctionFactory>put(
				"dateValidation", () -> new DateValidationFunction()
			).put(
				"pow", () -> new PowFunction()
			).build()
		);

		field(
			DDMExpressionFactoryImpl.class, "ddmExpressionFunctionTracker"
		).set(
			_ddmExpressionFactoryImpl, ddmExpressionFunctionTracker
		);
	}

	private final DDMExpressionFactoryImpl _ddmExpressionFactoryImpl =
		new DDMExpressionFactoryImpl();

}