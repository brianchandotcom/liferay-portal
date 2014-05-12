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

import com.liferay.portal.kernel.expression.ExpressionEvaluator;
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
			ExpressionTestData.newExpressionTestData(
				"variable1 + variable2 + variable3")
				.usingLongVariable("variable1", null, 5L)
				.usingLongVariable("variable2", "variable1 + 3", 5L)
				.usingLongVariable("variable3", "variable2 + variable1", 5L);

		ExpressionEvaluator evaluator = new ExpressionEvaluatorImpl(
			expressionTestData.getVariables());

		Map<String, VariableDependencies> dependenciesMap =
			evaluator.getVariableDependenciesMap();

		VariableDependencies variable1Dependencies = dependenciesMap.get(
			"variable1");

		Assert.assertTrue(
			variable1Dependencies.getRequiredVariableNames().isEmpty());

		Assert.assertTrue(
			affectedVariablesContains(variable1Dependencies, "variable2"));

		Assert.assertTrue(
			affectedVariablesContains(variable1Dependencies, "variable3"));

		VariableDependencies variable2Dependencies = dependenciesMap.get(
			"variable2");

		Assert.assertTrue(
			requiredVariablesContains(variable2Dependencies, "variable1"));

		Assert.assertTrue(
			affectedVariablesContains(variable2Dependencies, "variable3"));

		VariableDependencies variable3Dependencies = dependenciesMap.get(
			"variable3");

		Assert.assertTrue(
			requiredVariablesContains(variable3Dependencies, "variable1"));

		Assert.assertTrue(
			requiredVariablesContains(variable3Dependencies, "variable2"));

		Assert.assertTrue(
			variable3Dependencies.getAffectedVariableNames().isEmpty());
	}

	protected boolean affectedVariablesContains(
		VariableDependencies variableDependencies, String variableName) {

		List<String> affectedVariableNames =
			variableDependencies.getAffectedVariableNames();

		return affectedVariableNames.contains(variableName);
	}

	protected boolean requiredVariablesContains(
		VariableDependencies variableDependencies, String variableName) {

		List<String> requiredVariableNames =
			variableDependencies.getRequiredVariableNames();

		return requiredVariableNames.contains(variableName);
	}

}