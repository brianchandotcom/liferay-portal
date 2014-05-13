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

import org.junit.Test;

import com.liferay.portal.kernel.expression.ExpressionEvaluationException;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class SimpleExpressionTest extends BaseExpresssionTest {

	@Test(expected=ExpressionEvaluationException.class)
	public void testEvaluateBlankExpression() throws Exception {

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData("");
		expressionTestData.addIntegerVariable("var1", null, 5);
		expressionTestData.addIntegerVariable("var2", null, 6);
		expressionTestData.setReturnType(Boolean.class);
		expressionTestData.setExpectedResult(false);

		verifyBooleanExpression(expressionTestData);
	}

	@Test
	public void testEvaluateBooleanExpression() throws Exception {

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData("var1 >= var2");
		expressionTestData.addIntegerVariable("var1", null, 5);
		expressionTestData.addIntegerVariable("var2", null, 6);
		expressionTestData.setReturnType(Boolean.class);
		expressionTestData.setExpectedResult(false);

		verifyBooleanExpression(expressionTestData);

		expressionTestData =
			ExpressionTestData.newExpressionTestData("var1 = var2");
		expressionTestData.addBooleanVariable("var1", null, true);
		expressionTestData.addBooleanVariable("var2", null, false);
		expressionTestData.setReturnType(Boolean.class);
		expressionTestData.setExpectedResult(false);

		verifyBooleanExpression(expressionTestData);
	}

	@Test
	public void testEvaluateDoubleExpression() throws Exception {

		double var1Value = 5.5;
		double var2value = var1Value + 3;
		double var3Value = var1Value + var2value;
		double expectedResult = var1Value + var2value + var3Value;

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData("var1 + var2 + var3");
		expressionTestData.addDoubleVariable("var1", null, var1Value);
		expressionTestData.addDoubleVariable("var2", "var1 + 3",null);
		expressionTestData.addDoubleVariable("var3", "var2 + var1", null);
		expressionTestData.setReturnType(Double.class);
		expressionTestData.setExpectedResult(expectedResult);

		verifyDoubleExpression(expressionTestData);
	}

	@Test(expected=ExpressionEvaluationException.class)
	public void testEvaluateExpressionWithError() throws Exception {

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData("var1 >=+P var2");
		expressionTestData.addIntegerVariable("var1", null, 5);
		expressionTestData.addIntegerVariable("var2", null, 6);
		expressionTestData.setReturnType(Boolean.class);
		expressionTestData.setExpectedResult(false);

		verifyBooleanExpression(expressionTestData);
	}

	@Test
	public void testEvaluateFloatExpression() throws Exception {

		float var1Value = 5.5F;
		float var2value = var1Value + 3;
		float var3Value = var1Value + var2value;
		float expectedResult = var1Value + var2value + var3Value;

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData("var1 + var2 + var3");
		expressionTestData.addFloatVariable("var1", null, var1Value);
		expressionTestData.addFloatVariable("var2", "var1 + 3", null);
		expressionTestData.addFloatVariable("var3","var2 + var1", null);
		expressionTestData.setReturnType(Float.class);
		expressionTestData.setExpectedResult(expectedResult);

		verifyFloatExpression(expressionTestData);
	}

	@Test
	public void testEvaluateIntegerExpression() throws Exception {

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData("var1 + var2 + var3");
		expressionTestData.addIntegerVariable("var1", null, 5);
		expressionTestData.addIntegerVariable("var2", "var1 + 3", 5);
		expressionTestData.addIntegerVariable("var3","var2 + var1", 5);
		expressionTestData.setReturnType(Integer.class);
		expressionTestData.setExpectedResult(5 + (5 + 3) + ((5 + 3) + 5));

		verifyIntegerExpression(expressionTestData);
	}

	@Test
	public void testEvaluateLongExpression() throws Exception {

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData("var1 + var2 + var3");
		expressionTestData.addLongVariable("var1", null, 5L);
		expressionTestData.addLongVariable("var2", "var1 + 3", 5L);
		expressionTestData.addLongVariable("var3", "var2 + var1", 5L);
		expressionTestData.setReturnType(Long.class);
		expressionTestData.setExpectedResult(
			(long) (5 + (5 + 3) + ((5 + 3) + 5)));

		verifyLongExpression(expressionTestData);
	}


	@Test(expected=ExpressionEvaluationException.class)
	public void testEvaluateNullExpression() throws Exception {

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData(null);
		expressionTestData.addIntegerVariable("var1", null, 5);
		expressionTestData.addIntegerVariable("var2", null, 6);
		expressionTestData.setReturnType(Boolean.class);
		expressionTestData.setExpectedResult(false);

		verifyBooleanExpression(expressionTestData);
	}

	@Test
	public void testEvaluateStringExpression() throws Exception {

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData("var1 + var2");
		expressionTestData.addStringVariable("var1", null, "Life");
		expressionTestData.addStringVariable("var2", null, "ray");
		expressionTestData.setReturnType(String.class);
		expressionTestData.setExpectedResult("Liferay");

		verifyStringExpression(expressionTestData);
	}

}