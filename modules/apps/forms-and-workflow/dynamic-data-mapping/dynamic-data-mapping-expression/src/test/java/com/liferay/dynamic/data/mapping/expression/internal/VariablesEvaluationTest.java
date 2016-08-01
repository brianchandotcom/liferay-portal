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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class VariablesEvaluationTest {

	@Test
	public void testBooleanVariable() throws Exception {
		DDMExpression<Boolean> ddmExpression = new DDMExpressionImpl<>(
			"x and y", Boolean.class);

		ddmExpression.setBooleanVariableValue("x", true);
		ddmExpression.setBooleanVariableValue("y", false);

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testDoubleVariable() throws Exception {
		DDMExpression<Double> ddmExpression = new DDMExpressionImpl<>(
			"x + y", Double.class);

		ddmExpression.setDoubleVariableValue("x", 1.0);
		ddmExpression.setDoubleVariableValue("y", .5);

		Assert.assertEquals(1.5d, ddmExpression.evaluate(), .001);
	}

	@Test
	public void testFloatVariable() throws Exception {
		DDMExpression<Float> ddmExpression = new DDMExpressionImpl<>(
			"x + y", Float.class);

		ddmExpression.setFloatVariableValue("x", 1.0f);
		ddmExpression.setFloatVariableValue("y", .5f);

		Assert.assertEquals(1.5f, ddmExpression.evaluate(), .001);
	}

	@Test
	public void testIntegerVariable() throws Exception {
		DDMExpression<Boolean> ddmExpression = new DDMExpressionImpl<>(
			"x == 0", Boolean.class);

		ddmExpression.setIntegerVariableValue("x", 0);

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testLongVariable() throws Exception {
		DDMExpression<Long> ddmExpression = new DDMExpressionImpl<>(
			"x / y", Long.class);

		ddmExpression.setLongVariableValue("x", 2L);
		ddmExpression.setLongVariableValue("y", 1L);

		Assert.assertEquals(2, (long)ddmExpression.evaluate());
	}

	@Test
	public void testNestedVariables() throws Exception {
		DDMExpression<Number> ddmExpression = new DDMExpressionImpl<>(
			"x + y + z", Number.class);

		int x = 1;

		ddmExpression.setNumberVariableValue("x", x);

		ddmExpression.setExpressionStringVariableValue("y", "x + 1");
		ddmExpression.setExpressionStringVariableValue("z", "x - y");

		int y = x + 1;
		int z = x - y;

		Assert.assertEquals(x + y + z, ddmExpression.evaluate().intValue());
	}

	@Test
	public void testNumericVariable() throws Exception {
		DDMExpression<Number> ddmExpression = new DDMExpressionImpl<>(
			"x + y", Number.class);

		ddmExpression.setNumberVariableValue("x", 1);
		ddmExpression.setNumberVariableValue("y", 2);

		Assert.assertEquals(3, ddmExpression.evaluate().intValue());
	}

	@Test
	public void testStringVariable() throws Exception {
		DDMExpression<String> ddmExpression = new DDMExpressionImpl<>(
			"var1", String.class);

		ddmExpression.setStringVariableValue("var1", "Ray Charles");

		Assert.assertEquals("Ray Charles", ddmExpression.evaluate());
	}

	protected Number evaluate(String expressionString) throws Exception {
		DDMExpression<Number> ddmExpression = new DDMExpressionImpl<>(
			expressionString, Number.class);

		return ddmExpression.evaluate();
	}

}