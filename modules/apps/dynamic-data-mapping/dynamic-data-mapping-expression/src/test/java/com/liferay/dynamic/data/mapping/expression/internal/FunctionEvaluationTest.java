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
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class FunctionEvaluationTest {

	@Test
	public void testCustomFunction1() throws Exception {
		int expected = Math.abs(-5);

		DDMExpression<Number> ddmExpression = new DDMExpressionImpl<>(
			"abs(-5)", Number.class);

		ddmExpression.setDDMExpressionFunction("abs", new AbsFunction());

		Number actual = ddmExpression.evaluate();

		Assert.assertEquals(expected, actual.intValue());
	}

	@Test
	public void testCustomFunction2() throws Exception {
		DDMExpression<Boolean> ddmExpression = new DDMExpressionImpl<>(
			"not(length(\"123\") > length(\"1\"))", Boolean.class);

		ddmExpression.setDDMExpressionFunction("length", new LengthFunction());

		Assert.assertEquals(false, ddmExpression.evaluate());
	}

	@Test
	public void testCustomFunction3() throws Exception {
		DDMExpression<Boolean> ddmExpression = new DDMExpressionImpl<>(
			"length('abc') == length('abc')", Boolean.class);

		ddmExpression.setDDMExpressionFunction("length", new LengthFunction());

		Assert.assertEquals(true, ddmExpression.evaluate());
	}

	@Test
	public void testCustomFunction4() throws Exception {
		DDMExpression<Boolean> ddmExpression = new DDMExpressionImpl<>(
			"pow(2, 4) > (16 - 1)", Boolean.class);

		ddmExpression.setDDMExpressionFunction("pow", new PowFunction());

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testCustomFunction5() throws Exception {
		double expected = Math.pow(2., Math.pow(2., Math.pow(2., 4.)));

		DDMExpression<Double> ddmExpression = new DDMExpressionImpl<>(
			"pow(2., pow(2., pow(2.,4.)))", Double.class);

		ddmExpression.setDDMExpressionFunction("pow", new PowFunction());

		double actual = ddmExpression.evaluate();

		Assert.assertEquals(expected, actual, 0.01);
	}

	@Test(expected = DDMExpressionException.FunctionNotDefined.class)
	public void testUndefinedFunction() throws Exception {
		DDMExpression<Number> ddmExpression = new DDMExpressionImpl<>(
			"time()", Number.class);

		ddmExpression.evaluate();
	}

	private static class AbsFunction implements DDMExpressionFunction {

		public Object evaluate(Object... parameters) {
			double parameter = (double)parameters[0];

			return Math.abs(parameter);
		}

	}

	private static class LengthFunction implements DDMExpressionFunction {

		public Object evaluate(Object... parameters) {
			String parameter = (String)parameters[0];

			return parameter.length();
		}

	}

	private static class PowFunction implements DDMExpressionFunction {

		public Object evaluate(Object... parameters) {
			double parameter1 = (double)parameters[0];
			double parameter2 = (double)parameters[1];

			return Math.pow(parameter1, parameter2);
		}

	}

}