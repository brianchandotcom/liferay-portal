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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class ExpressionEvaluationTest {

	@Before
	public void setUp() throws Exception {
		setUpExpressionFactory();
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

		expression.setExpressionStringVariableValue(
			"var2", "var1 + 3", Double.class);

		expression.setExpressionStringVariableValue(
			"var3", "var2 + var1", Double.class);

		double var2 = var1 + 3;

		double var3 = var1 + var2;

		Assert.assertEquals(
			(Double)(var1 + var2 + var3), expression.evaluate());
	}

	@Test
	public void testEvaluateEqualsExpression() throws Exception {
		Expression<Boolean> expression =
			ExpressionFactoryUtil.createBooleanExpression("var1 = var2");

		expression.setBooleanVariableValue("var1", true);
		expression.setBooleanVariableValue("var2", false);

		Assert.assertFalse(expression.evaluate());
	}

	@Test
	public void testEvaluateFloatExpression() throws Exception {
		Expression<Float> expression =
			ExpressionFactoryUtil.createFloatExpression("var1 + var2 + var3");

		float var1 = 5.5f;

		expression.setFloatVariableValue("var1", var1);

		expression.setExpressionStringVariableValue(
			"var2", "var1 + 3", Float.class);

		expression.setExpressionStringVariableValue(
			"var3", "var2 + var1", Float.class);

		float var2 = var1 + 3;

		float var3 = var1 + var2;

		Assert.assertEquals((Float)(var1 + var2 + var3), expression.evaluate());
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

		expression.setExpressionStringVariableValue(
			"var2", "var1 + 3", Integer.class);

		expression.setExpressionStringVariableValue(
			"var3", "var2 + var1", Integer.class);

		int var2 = var1 + 3;

		int var3 = var1 + var2;

		Assert.assertEquals(var1 + var2 + var3, (int)expression.evaluate());
	}

	@Test(expected = ExpressionEvaluationException.class)
	public void testEvaluateInvalidExpression() throws Exception {
		Expression<Boolean> expression =
			ExpressionFactoryUtil.createBooleanExpression("var1 >=+P var2");

		expression.setIntegerVariableValue("var1", 5);
		expression.setIntegerVariableValue("var2", 6);

		Assert.assertFalse(expression.evaluate());
	}

	@Test
	public void testEvaluateLongExpression() throws Exception {
		Expression<Long> expression =
			ExpressionFactoryUtil.createLongExpression("var1 + var2 + var3");

		long var1 = 5l;

		expression.setLongVariableValue("var1", var1);

		expression.setExpressionStringVariableValue(
			"var2", "var1 + 3", Long.class);

		expression.setExpressionStringVariableValue(
			"var3", "var2 + var1", long.class);

		long var2 = var1 + 3;

		long var3 = var1 + var2;

		Assert.assertEquals(var1 + var2 + var3, (long)expression.evaluate());
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

		Assert.assertEquals("Liferay", expression.evaluate());
	}

	@Test
	public void testSumWithDoubleValues() throws Exception {
		Expression<Double> expression =
			ExpressionFactoryUtil.createDoubleExpression(
				"sum(var1, var2, var3)");

		double var1 = 5.5;

		expression.setDoubleVariableValue("var1", var1);

		expression.setExpressionStringVariableValue(
			"var2", "var1 + 3.5", Double.class);

		expression.setExpressionStringVariableValue(
			"var3", "var2 + var1", Double.class);

		double var2 = var1 + 3.5;

		double var3 = var1 + var2;

		Assert.assertEquals(
			(Double)MathUtil.sum(var1, var2, var3), expression.evaluate());
	}

	@Test
	public void testSumWithLongValues() throws Exception {
		Expression<Long> expression =
			ExpressionFactoryUtil.createLongExpression("sum(var1, var2, var3)");

		long var1 = 5;

		expression.setLongVariableValue("var1", var1);

		expression.setExpressionStringVariableValue(
			"var2", "var1 + 3", Long.class);

		expression.setExpressionStringVariableValue(
			"var3", "var2 + var1", Long.class);

		long var2 = var1 + 3;

		long var3 = var1 + var2;

		Assert.assertEquals(
			MathUtil.sum(var1, var2, var3), (long)expression.evaluate());
	}

	protected void setUpExpressionFactory() {
		ExpressionFactoryUtil expressionFactoryUtil =
			new ExpressionFactoryUtil();

		expressionFactoryUtil.setExpressionFactory(new ExpressionFactoryImpl());
	}

}