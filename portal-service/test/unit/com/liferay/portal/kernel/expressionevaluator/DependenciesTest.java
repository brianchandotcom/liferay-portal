
package com.liferay.portal.kernel.expressionevaluator;

import com.liferay.portal.kernel.expressionevaluator.model.VariableDependencies;

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

		Map<String, VariableDependencies> dependenciesMap = evaluator
			.getDependenciesMap();

		Assert.assertTrue(dependenciesMap.get("variable01").getDependencies()
			.isEmpty());

		Assert.assertTrue(dependenciesMap.get("variable01").getDependents()
			.contains("variable02"));

		Assert.assertTrue(dependenciesMap.get("variable01").getDependents()
			.contains("variable03"));

		Assert.assertTrue(dependenciesMap.get("variable02").getDependencies()
			.contains("variable01"));

		Assert.assertTrue(dependenciesMap.get("variable02").getDependents()
			.contains("variable03"));

		Assert.assertTrue(dependenciesMap.get("variable03").getDependencies()
			.contains("variable01"));

		Assert.assertTrue(dependenciesMap.get("variable03").getDependencies()
			.contains("variable02"));

		Assert.assertTrue(dependenciesMap.get("variable03").getDependents()
			.isEmpty());
	}

}