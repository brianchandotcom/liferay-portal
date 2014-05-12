
package com.liferay.portal.expression;

import com.liferay.portal.kernel.expression.ExpressionEvaluator;
import com.liferay.portal.kernel.expression.VariableDependencies;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class DependenciesTest extends BaseExpresssionTest {

	@Test
	public void testVariableDependenciesMap() {

		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData(
				"variable01 + variable02 + variable03")
				.usingLongVariable("variable01", null, 5L)
				.usingLongVariable("variable02", "variable01 + 3", 5L)
				.usingLongVariable("variable03", "variable02 + variable01", 5L);

		ExpressionEvaluator evaluator = new ExpressionEvaluatorImpl(
			expressionTestData.getVariables());

		Map<String, VariableDependencies> dependenciesMap =
			evaluator.getVariableDependenciesMap();

		Assert.assertTrue(dependenciesMap.get("variable01").getRequiredVariableNames()
			.isEmpty());

		Assert.assertTrue(dependenciesMap.get("variable01").getAffectedVariableNames()
			.contains("variable02"));

		Assert.assertTrue(dependenciesMap.get("variable01").getAffectedVariableNames()
			.contains("variable03"));

		Assert.assertTrue(dependenciesMap.get("variable02").getRequiredVariableNames()
			.contains("variable01"));

		Assert.assertTrue(dependenciesMap.get("variable02").getAffectedVariableNames()
			.contains("variable03"));

		Assert.assertTrue(dependenciesMap.get("variable03").getRequiredVariableNames()
			.contains("variable01"));

		Assert.assertTrue(dependenciesMap.get("variable03").getRequiredVariableNames()
			.contains("variable02"));

		Assert.assertTrue(dependenciesMap.get("variable03").getAffectedVariableNames()
			.isEmpty());
	}

}