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

import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionTracker;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Leonardo Barros
 */
@RunWith(MockitoJUnitRunner.class)
public class DDMExpressionFactoryImplTest extends PowerMockito {

	@Test
	public void testCreateDDMExpression() throws Exception {
		DDMExpressionFactoryImpl ddmExpressionFactory =
			new DDMExpressionFactoryImpl();

		ddmExpressionFactory.ddmExpressionFunctionTracker =
			_ddmExpressionFunctionTracker;

		DDMExpressionFunction.Function2<BigDecimal, BigDecimal, BigDecimal>
			pow = (n1, n2) -> n1.pow(n2.intValue());

		Map<String, DDMExpressionFunction> functions = new HashMap() {
			{
				put("pow", pow);
			}
		};

		when(
			_ddmExpressionFunctionTracker.getDDMExpressionFunctions()
		).thenReturn(
			functions
		);

		DDMExpression<BigDecimal> ddmExpression =
			ddmExpressionFactory.createDDMExpression("pow(2,3)");

		Assert.assertEquals(new BigDecimal(8), ddmExpression.evaluate());
	}

	@Mock
	private DDMExpressionFunctionTracker _ddmExpressionFunctionTracker;

}