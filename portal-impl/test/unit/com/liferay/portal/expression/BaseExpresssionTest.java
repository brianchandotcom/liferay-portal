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

import com.liferay.portal.kernel.expression.InterdependentExpressionEvaluator;

import org.junit.Assert;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public abstract class BaseExpresssionTest {

	public void verifyBooleanExpression(ExpressionTestData expressionTestData)
		throws Exception {

		InterdependentExpressionEvaluator evaluator =
			new InterdependentExpressionEvaluatorImpl(
				expressionTestData.getVariables());

		Boolean result = evaluator.evaluateBooleanExpression(
			expressionTestData.getExpression());

		Assert.assertEquals(result, expressionTestData.getExpectedResult());
	}

	public void verifyDoubleExpression(ExpressionTestData expressionTestData)
		throws Exception {

		InterdependentExpressionEvaluator evaluator =
			new InterdependentExpressionEvaluatorImpl(
				expressionTestData.getVariables());

		Double result = evaluator.evaluateDoubleExpression(
			expressionTestData.getExpression());

		Assert.assertEquals(result, expressionTestData.getExpectedResult());
	}

	public void verifyExpression(ExpressionTestData expressionTestData)
		throws Exception {

		InterdependentExpressionEvaluator evaluator =
			new InterdependentExpressionEvaluatorImpl(
				expressionTestData.getVariables());

		Object result = evaluator.evaluateExpression(
			expressionTestData.getExpression(),
			expressionTestData.getReturnType());

		Assert.assertEquals(result, expressionTestData.getExpectedResult());
	}

	public void verifyFloatExpression(ExpressionTestData expressionTestData)
		throws Exception {

		InterdependentExpressionEvaluator evaluator =
			new InterdependentExpressionEvaluatorImpl(
				expressionTestData.getVariables());

		Float result = evaluator.evaluateFloatExpression(
			expressionTestData.getExpression());

		Assert.assertEquals(result, expressionTestData.getExpectedResult());
	}

	public void verifyIntegerExpression(ExpressionTestData expressionTestData)
		throws Exception {

		InterdependentExpressionEvaluator evaluator =
			new InterdependentExpressionEvaluatorImpl(
				expressionTestData.getVariables());

		Integer result = evaluator.evaluateIntegerExpression(
			expressionTestData.getExpression());

		Assert.assertEquals(result, expressionTestData.getExpectedResult());
	}

	public void verifyLongExpression(ExpressionTestData expressionTestData)
		throws Exception {

		InterdependentExpressionEvaluator evaluator =
			new InterdependentExpressionEvaluatorImpl(
				expressionTestData.getVariables());

		Long result = evaluator.evaluateLongExpression(
			expressionTestData.getExpression());

		Assert.assertEquals(result, expressionTestData.getExpectedResult());
	}

	public void verifyStringExpression(ExpressionTestData expressionTestData)
		throws Exception {

		InterdependentExpressionEvaluator evaluator =
			new InterdependentExpressionEvaluatorImpl(
				expressionTestData.getVariables());

		String result = evaluator.evaluateStringExpression(
			expressionTestData.getExpression());

		Assert.assertEquals(result, expressionTestData.getExpectedResult());
	}

}