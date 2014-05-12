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

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class SimpleExpressionTest extends BaseExpresssionTest {

	@Test
	public void testEvaluateBooleanExpression() throws Exception {
		ExpressionTestData expressionTestData = ExpressionTestData
			.newExpressionTestData("variable1 >= variable2")
			.usingIntegerVariable("variable1", null, 5)
			.usingIntegerVariable("variable2", null, 6)
			.withReturnType(Boolean.class)
			.withExpectedResult(false);

		testExpression(expressionTestData);
	}

	@Test
	public void testEvaluateDoubleExpression() throws Exception {
		double variable1Value = 5.5;
		double variable2value = variable1Value + 3;
		double variable3Value = variable1Value + variable2value;
		double expectedResult =
			variable1Value + variable2value + variable3Value;

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData(
				"variable1 + variable2 + variable3")
				.usingDoubleVariable("variable1", null, variable1Value)
				.usingDoubleVariable("variable2", "variable1 + 3", null)
				.usingDoubleVariable("variable3", "variable2 + variable1", null)
				.withReturnType(Double.class)
				.withExpectedResult(expectedResult);

		testExpression(expressionTestData);
	}

	@Test
	public void testEvaluateFloatExpression() throws Exception {
		float variable1Value = 5.5F;
		float variable2value = variable1Value + 3;
		float variable3Value = variable1Value + variable2value;
		float expectedResult = variable1Value + variable2value + variable3Value;

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData(
				"variable1 + variable2 + variable3")
				.usingFloatVariable("variable1", null, variable1Value)
				.usingFloatVariable("variable2", "variable1 + 3", null)
				.usingFloatVariable("variable3", "variable2 + variable1", null)
				.withReturnType(Float.class)
				.withExpectedResult(expectedResult);

		testExpression(expressionTestData);
	}

	@Test
	public void testEvaluateIntegerExpression() throws Exception {
		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData(
				"variable1 + variable2 + variable3")
				.usingIntegerVariable("variable1", null, 5)
				.usingIntegerVariable("variable2", "variable1 + 3", 5)
				.usingIntegerVariable("variable3", "variable2 + variable1", 5)
				.withReturnType(Integer.class)
				.withExpectedResult(5 + (5 + 3) + ((5 + 3) + 5));

		testExpression(expressionTestData);
	}

	@Test
	public void testEvaluateLongExpression() throws Exception {
		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData(
				"variable1 + variable2 + variable3")
				.usingLongVariable("variable1", null, 5L)
				.usingLongVariable("variable2", "variable1 + 3", 5L)
				.usingLongVariable("variable3", "variable2 + variable1", 5L)
				.withReturnType(Long.class)
				.withExpectedResult((long) (5 + (5 + 3) + ((5 + 3) + 5)));

		testExpression(expressionTestData);
	}

	@Test
	public void testEvaluateStringExpression() throws Exception {
		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData("variable1 + variable2")
			.usingStringVariable("variable1", null, "Life")
			.usingStringVariable("variable2", null, "ray")
			.withReturnType(String.class)
			.withExpectedResult("Liferay");

		testExpression(expressionTestData);
	}

}