
package com.liferay.portal.kernel.expressionevaluator;

import org.junit.Test;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class FunctionsTest extends BaseExpresssionTest {

	@Test
	public void testSum() throws Exception {

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData(
				"sum(variable01,variable02,variable03)").
				usingLongVariable("variable01", null, 5L)
				.usingLongVariable("variable02", "variable01 + 3", null)
				.usingLongVariable("variable03", "variable02 + variable01",
					null)
				.withExpectedResult((long) (5 + (5 + 3) + ((5 + 3) + 5)));

		testExpression(expressionTestData);

		ExpressionTestData doubleExpressionTestData =
			ExpressionTestData.newExpressionTestData(
				"sum(variable01,variable02,variable03)")
				.usingDoubleVariable("variable01", null, 5.5)
				.usingDoubleVariable("variable02", "variable01 + 3.5", null)
				.usingDoubleVariable("variable03", "variable02 + variable01",
					null)
				.withExpectedResult(5.5 + (5.5 + 3.5) + ((5.5 + 3.5) + 5.5));

		testExpression(doubleExpressionTestData);
	}
}