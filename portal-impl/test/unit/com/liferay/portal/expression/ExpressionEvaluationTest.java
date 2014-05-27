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

package com.liferay.portal.expression;

import com.liferay.portal.kernel.expression.Expression;
import com.liferay.portal.kernel.expression.ExpressionEvaluationException;
import com.liferay.portal.kernel.expression.ExpressionFactoryUtil;
import com.liferay.portal.kernel.util.MathUtil;

import org.junit.Before;
import org.junit.Test;

import org.testng.Assert;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class ExpressionEvaluationTest {

	@Before
	public void setUp() throws Exception {
		setupExpressionFactory();
	}

	@Test(expected = ExpressionEvaluationException.class)
	public void testEvaluateBlankExpression() throws Exception {
		Expression<Boolean> expression =
			ExpressionFactoryUtil.createBooleanExpression("");

		expression.setIntegerVariableValue("var1", 5);
		expression.setIntegerVariableValue("var2", 6);

		Assert.assertFalse(expression.evaluate());
	}

	@Test
	public void testEvaluateDoubleExpression() throws Exception {
		Expression<Double> expression =
			ExpressionFactoryUtil.createDoubleExpression("var1 + var2 + var3");

		double var1 = 5.5;

		expression.setDoubleVariableValue("var1", var1);
		expression.setExpressionVariableValue("var2", Double.class, "var1 + 3");
		expression.setExpressionVariableValue(
			"var3", Double.class, "var2 + var1");

		double var2 = var1 + 3;
		double var3 = var1 + var2;

		double expected = var1 + var2 + var3;

		Assert.assertEquals(expression.evaluate(), expected);
	}

	@Test
	public void testEvaluateEqualsExpression() throws Exception {
		Expression<Boolean> expression =
			ExpressionFactoryUtil.createBooleanExpression("var1 = var2");

		expression.setBooleanVariableValue("var1", true);
		expression.setBooleanVariableValue("var2", false);

		Assert.assertFalse(expression.evaluate());
	}

	@Test(expected = ExpressionEvaluationException.class)
	public void testEvaluateExpressionWithError() throws Exception {
		Expression<Boolean> expression =
			ExpressionFactoryUtil.createBooleanExpression("var1 >=+P var2");

		expression.setIntegerVariableValue("var1", 5);
		expression.setIntegerVariableValue("var2", 6);

		Assert.assertFalse(expression.evaluate());
	}

	@Test
	public void testEvaluateFloatExpression() throws Exception {
		Expression<Float> expression =
			ExpressionFactoryUtil.createFloatExpression("var1 + var2 + var3");

		float var1 = 5.5f;

		expression.setFloatVariableValue("var1", var1);
		expression.setExpressionVariableValue("var2", Float.class, "var1 + 3");
		expression.setExpressionVariableValue(
			"var3", Float.class, "var2 + var1");

		float var2 = var1 + 3;
		float var3 = var1 + var2;

		float expected = var1 + var2 + var3;

		Assert.assertEquals(expression.evaluate(), expected);
	}

	@Test
	public void testEvaluateGreaterThanExpression() throws Exception {
		Expression<Boolean> expression =
			ExpressionFactoryUtil.createBooleanExpression("var1 > var2");

		expression.setIntegerVariableValue("var1", 5);
		expression.setIntegerVariableValue("var2", 6);

		Assert.assertFalse(expression.evaluate());
	}

	@Test
	public void testEvaluateIntegerExpression() throws Exception {
		Expression<Integer> expression =
			ExpressionFactoryUtil.createIntegerExpression("var1 + var2 + var3");

		int var1 = 5;

		expression.setIntegerVariableValue("var1", var1);
		expression.setExpressionVariableValue(
			"var2", Integer.class, "var1 + 3");
		expression.setExpressionVariableValue(
			"var3", Integer.class, "var2 + var1");

		int var2 = var1 + 3;
		int var3 = var1 + var2;

		int actual = expression.evaluate();

		int expected = var1 + var2 + var3;

		Assert.assertEquals(actual, expected);
	}

	@Test
	public void testEvaluateLongExpression() throws Exception {
		Expression<Long> expression =
			ExpressionFactoryUtil.createLongExpression("var1 + var2 + var3");

		long var1 = 5l;

		expression.setLongVariableValue("var1", var1);
		expression.setExpressionVariableValue("var2", Long.class, "var1 + 3");
		expression.setExpressionVariableValue(
			"var3", long.class, "var2 + var1");

		long var2 = var1 + 3;
		long var3 = var1 + var2;

		long actual = expression.evaluate();

		long expected = var1 + var2 + var3;

		Assert.assertEquals(actual, expected);
	}

	@Test(expected = ExpressionEvaluationException.class)
	public void testEvaluateNullExpression() throws Exception {
		Expression<Boolean> expression = ExpressionFactoryUtil.createExpression(
			null, Boolean.class);

		expression.setIntegerVariableValue("var1", 5);
		expression.setIntegerVariableValue("var2", 6);

		Assert.assertFalse(expression.evaluate());
	}

	@Test
	public void testEvaluateStringExpression() throws Exception {
		Expression<String> expression =
			ExpressionFactoryUtil.createStringExpression("var1 + var2");

		expression.setStringVariableValue("var1", "Life");
		expression.setStringVariableValue("var2", "ray");

		Assert.assertEquals(expression.evaluate(), "Liferay");
	}

	@Test
	public void testSumWithDoubleValues() throws Exception {
		Expression<Double> expression =
			ExpressionFactoryUtil.createDoubleExpression(
				"sum(var1, var2, var3)");

		double var1 = 5.5;

		expression.setDoubleVariableValue("var1", var1);
		expression.setExpressionVariableValue(
			"var2", Double.class, "var1 + 3.5");
		expression.setExpressionVariableValue(
			"var3", Double.class, "var2 + var1");

		double var2 = var1 + 3.5;
		double var3 = var1 + var2;

		double actual = expression.evaluate();

		double expected = MathUtil.sum(var1, var2, var3);

		Assert.assertEquals(actual, expected);
	}

	@Test
	public void testSumWithLongValues() throws Exception {
		Expression<Long> expression =
			ExpressionFactoryUtil.createLongExpression("sum(var1, var2, var3)");

		long var1 = 5;

		expression.setLongVariableValue("var1", var1);
		expression.setExpressionVariableValue("var2", Long.class, "var1 + 3");
		expression.setExpressionVariableValue(
			"var3", Long.class, "var2 + var1");

		long var2 = var1 + 3;
		long var3 = var1 + var2;

		long actual = expression.evaluate();

		long expected = MathUtil.sum(var1, var2, var3);

		Assert.assertEquals(actual, expected);
	}

	protected void setupExpressionFactory() {
		ExpressionFactoryUtil expressionFactoryUtil =
			new ExpressionFactoryUtil();

		expressionFactoryUtil.setExpressionFactory(new ExpressionFactoryImpl());
	}

}