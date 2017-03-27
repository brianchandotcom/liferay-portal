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

package com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.functions;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.DDMExpressionFunctionRegister;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Leonardo Barros
 */
@RunWith(MockitoJUnitRunner.class)
public class AllFunctionTest extends PowerMockito {

	@Test
	public void testEvaluateFalse() throws Exception {
		AllFunction allFunction = new AllFunction(
			_ddmExpressionFactory, _ddmExpressionFunctionRegister);

		Boolean result = (Boolean)allFunction.evaluate("#value# > 10");

		Assert.assertFalse(result);
	}

	@Test
	public void testEvaluateFalse2() throws Exception {
		AllFunction allFunction = new AllFunction(
			_ddmExpressionFactory, _ddmExpressionFunctionRegister);

		Object parameters = new Integer[] {1, 2, 4};

		Boolean result = (Boolean)allFunction.evaluate(
			"invalid expression", parameters);

		Assert.assertFalse(result);
	}

	@Test
	public void testEvaluateFalse3() throws Exception {
		AllFunction allFunction = new AllFunction(
			_ddmExpressionFactory, _ddmExpressionFunctionRegister);

		Object parameters = new Integer[] {1, 2, 4};

		Boolean result = (Boolean)allFunction.evaluate(
			"#value# < 4", parameters);

		Assert.assertFalse(result);
	}

	@Test
	public void testEvaluateTrue() throws Exception {
		_ddmExpressionFunctionRegister.registerDDMExpressionFunction(
			"eval", new EvalFunction());

		AllFunction allFunction = new AllFunction(
			_ddmExpressionFactory, _ddmExpressionFunctionRegister);

		Object parameters = new Double[] {1D, 2D, 4.5D};

		Boolean result = (Boolean)allFunction.evaluate(
			"eval(#value#,1,10)", parameters);

		Assert.assertTrue(result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgument() throws Exception {
		AllFunction allFunction = new AllFunction(
			_ddmExpressionFactory, _ddmExpressionFunctionRegister);

		allFunction.evaluate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgument2() throws Exception {
		AllFunction allFunction = new AllFunction(
			_ddmExpressionFactory, _ddmExpressionFunctionRegister);

		allFunction.evaluate(null);
	}

	private final DDMExpressionFactory _ddmExpressionFactory =
		new DDMExpressionFactoryImpl();
	private final DDMExpressionFunctionRegister _ddmExpressionFunctionRegister =
		new DDMExpressionFunctionRegister();

	private static class EvalFunction implements DDMExpressionFunction {

		@Override
		public Object evaluate(Object... parameters) {
			Double parameter0 = (Double)parameters[0];
			Double parameter1 = (Double)parameters[1];
			Double parameter2 = (Double)parameters[2];

			return parameter0 >= parameter1 && parameter0 <= parameter2;
		}

	}

}