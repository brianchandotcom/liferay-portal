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
import com.liferay.portal.kernel.expression.VariableDependencies;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class DependenciesTest {

	@Test
	public void testVariableDependenciesMap() {
		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData("var1 + var2 + var3");
		expressionTestData.addLongVariable("var1", null, 5L);
		expressionTestData.addLongVariable("var2", "var1 + 3", 5L);
		expressionTestData.addLongVariable("var3", "var2 + var1", 5L);

		InterdependentExpressionEvaluator evaluator =
			new InterdependentExpressionEvaluatorImpl(
				expressionTestData.getVariables());

		Map<String, VariableDependencies> dependenciesMap =
			evaluator.getVariableDependenciesMap();

		VariableDependencies var1Dependencies = dependenciesMap.get("var1");

		Assert.assertTrue(var1Dependencies.getRequiredVariableNames().isEmpty()
			);

		Assert.assertTrue(affectedVariablesContains(var1Dependencies, "var2"));

		Assert.assertTrue(affectedVariablesContains(var1Dependencies, "var3"));

		VariableDependencies var2Dependencies = dependenciesMap.get("var2");

		Assert.assertTrue(requiredVariablesContains(var2Dependencies, "var1"));

		Assert.assertTrue(affectedVariablesContains(var2Dependencies, "var3"));

		VariableDependencies var3Dependencies = dependenciesMap.get( "var3");

		Assert.assertTrue(requiredVariablesContains(var3Dependencies, "var1"));

		Assert.assertTrue(requiredVariablesContains(var3Dependencies, "var2"));

		Assert.assertTrue(var3Dependencies.getAffectedVariableNames().isEmpty()
			);
	}

	protected boolean affectedVariablesContains(
		VariableDependencies varDependencies, String varName) {

		List<String> affectedVariableNames =
			varDependencies.getAffectedVariableNames();

		return affectedVariableNames.contains(varName);
	}

	protected boolean requiredVariablesContains(
		VariableDependencies varDependencies, String varName) {

		List<String> requiredVariableNames =
			varDependencies.getRequiredVariableNames();

		return requiredVariableNames.contains(varName);
	}

}