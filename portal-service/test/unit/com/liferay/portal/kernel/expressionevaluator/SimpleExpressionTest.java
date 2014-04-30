
package com.liferay.portal.kernel.expressionevaluator;

import org.junit.Test;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class SimpleExpressionTest extends BaseExpresssionTest {

	@Test
	public void testEvaluateBooleanExpression() throws Exception {

		ExpressionTestData expressionTestData = ExpressionTestData
			.newExpressionTestData("variable01 >= variable02")
			.usingIntegerVariable("variable01", null, 5)
			.usingIntegerVariable("variable02", null, 6)
			.withReturnType(Boolean.class)
			.withExpectedResult(false);

		testExpression(expressionTestData);
	}

	@Test
	public void testEvaluateDoubleExpression() throws Exception {

		double variable01Value = 5.5;
		double variable02value = variable01Value + 3;
		double variable03Value = variable01Value + variable02value;
		double expectedResult =
			variable01Value + variable02value + variable03Value;

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData(
				"variable01 + variable02 + variable03")
				.usingDoubleVariable("variable01", null, variable01Value)
				.usingDoubleVariable("variable02", "variable01 + 3", null)
				.usingDoubleVariable("variable03", "variable02 + variable01",
					null)
					.withReturnType(Double.class)
					.withExpectedResult(expectedResult);

		testExpression(expressionTestData);
	}

	@Test
	public void testEvaluateFloatExpression() throws Exception {

		float variable01Value = 5.5F;
		float variable02value = variable01Value + 3;
		float variable03Value = variable01Value + variable02value;
		float expectedResult =
			variable01Value + variable02value + variable03Value;

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData(
				"variable01 + variable02 + variable03")
				.usingFloatVariable("variable01", null, variable01Value)
				.usingFloatVariable("variable02", "variable01 + 3", null)
				.usingFloatVariable("variable03", "variable02 + variable01",
					null)
					.withReturnType(Float.class)
					.withExpectedResult(expectedResult);

		testExpression(expressionTestData);
	}

	@Test
	public void testEvaluateIntegerExpression() throws Exception {

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData(
				"variable01 + variable02 + variable03")
				.usingIntegerVariable("variable01", null, 5)
				.usingIntegerVariable("variable02", "variable01 + 3", 5)
				.usingIntegerVariable("variable03", "variable02 + variable01",
					5)
					.withReturnType(Integer.class)
					.withExpectedResult(5 + (5 + 3) + ((5 + 3) + 5));

		testExpression(expressionTestData);
	}

	@Test
	public void testEvaluateLongExpression() throws Exception {

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData(
				"variable01 + variable02 + variable03")
				.usingLongVariable("variable01", null, 5L)
				.usingLongVariable("variable02", "variable01 + 3",
					5L)
					.usingLongVariable("variable03", "variable02 + variable01", 5L)
					.withReturnType(Long.class)
					.withExpectedResult((long) (5 + (5 + 3) + ((5 + 3) + 5)));

		testExpression(expressionTestData);
	}

	@Test
	public void testEvaluateStringExpression() throws Exception {

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData("variable01 + variable02")
			.usingStringVariable("variable01", null, "Life")
			.usingStringVariable("variable02", null, "ray")
			.withReturnType(String.class)
			.withExpectedResult("Liferay");

		testExpression(expressionTestData);
	}
}