
package com.liferay.portal.kernel.expressionevaluator;

import org.junit.Assert;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class BaseExpresssionTest {

	public void testExpression(ExpressionTestData expressionTestData)
		throws Exception {

		ExpressionEvaluator evaluator = new ExpressionEvaluatorImpl(
			expressionTestData.getVariables());

		Object result = evaluator.evaluateExpression(
			expressionTestData.getExpression(),
			expressionTestData.getReturnType());

		Assert.assertEquals(result, expressionTestData.getExpectedResult());
	}

}