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

import com.liferay.portal.expression.internal.ExpressionFactoryImpl;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class VariableDependenciesTest {

	@Test
	public void testVariableDependenciesMap() {
		Expression<Long> expression = _expressionFactory.createLongExpression(
			"var1 + var2 + var3");

		expression.setLongVariableValue("var1", 5l);
		expression.setExpressionStringVariableValue(
			"var2", "var1 + 3", Long.class);
		expression.setExpressionStringVariableValue(
			"var3", "var2 + var1", Long.class);

		Map<String, VariableDependencies> variableDependenciesMap =
			expression.getVariableDependenciesMap();

		VariableDependencies var1VariableDependencies =
			variableDependenciesMap.get("var1");

		Assert.assertTrue(
			hasAffectedVariableName(var1VariableDependencies, "var2"));
		Assert.assertTrue(
			hasAffectedVariableName(var1VariableDependencies, "var3"));

		List<String> var1RequiredVariableNames =
			var1VariableDependencies.getRequiredVariableNames();

		Assert.assertTrue(var1RequiredVariableNames.isEmpty());

		VariableDependencies var2VariableDependencies =
			variableDependenciesMap.get("var2");

		Assert.assertTrue(
			hasRequiredVariableName(var2VariableDependencies, "var1"));
		Assert.assertTrue(
			hasAffectedVariableName(var2VariableDependencies, "var3"));

		VariableDependencies var3VariableDependencies =
			variableDependenciesMap.get("var3");

		List<String> var3AffectedVariableNames =
			var3VariableDependencies.getAffectedVariableNames();

		Assert.assertTrue(var3AffectedVariableNames.isEmpty());

		Assert.assertTrue(
			hasRequiredVariableName(var3VariableDependencies, "var1"));
		Assert.assertTrue(
			hasRequiredVariableName(var3VariableDependencies, "var2"));
	}

	protected boolean hasAffectedVariableName(
		VariableDependencies variableDependencies, String variableName) {

		List<String> affectedVariableNames =
			variableDependencies.getAffectedVariableNames();

		return affectedVariableNames.contains(variableName);
	}

	protected boolean hasRequiredVariableName(
		VariableDependencies variableDependencies, String variableName) {

		List<String> requiredVariableNames =
			variableDependencies.getRequiredVariableNames();

		return requiredVariableNames.contains(variableName);
	}

	private final ExpressionFactory _expressionFactory =
		new ExpressionFactoryImpl();

}