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

import com.liferay.portal.kernel.expression.Expression;
import com.liferay.portal.kernel.expression.ExpressionFactoryUtil;
import com.liferay.portal.kernel.expression.VariableDependencies;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class VariableDependenciesTest {

	@Before
	public void setUp() throws Exception {
		setupExpressionFactory();
	}

	@Test
	public void testVariableDependenciesMap() {
		Expression<Long> expression =
			ExpressionFactoryUtil.createLongExpression("var1 + var2 + var3");

		expression.setLongVariableValue("var1", 5l);
		expression.setExpressionVariableValue("var2", Long.class, "var1 + 3");
		expression.setExpressionVariableValue(
			"var3", Long.class, "var2 + var1");

		Map<String, VariableDependencies> dependenciesMap =
			expression.getVariableDependenciesMap();

		VariableDependencies var1Dependencies = dependenciesMap.get("var1");

		Assert.assertTrue(
			var1Dependencies.getRequiredVariableNames().isEmpty());

		Assert.assertTrue(affectedVariablesContains(var1Dependencies, "var2"));

		Assert.assertTrue(affectedVariablesContains(var1Dependencies, "var3"));

		VariableDependencies var2Dependencies = dependenciesMap.get("var2");

		Assert.assertTrue(requiredVariablesContains(var2Dependencies, "var1"));

		Assert.assertTrue(affectedVariablesContains(var2Dependencies, "var3"));

		VariableDependencies var3Dependencies = dependenciesMap.get( "var3");

		Assert.assertTrue(requiredVariablesContains(var3Dependencies, "var1"));

		Assert.assertTrue(requiredVariablesContains(var3Dependencies, "var2"));

		Assert.assertTrue(
			var3Dependencies.getAffectedVariableNames().isEmpty());
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

	protected void setupExpressionFactory() {
		ExpressionFactoryUtil expressionFactoryUtil =
			new ExpressionFactoryUtil();

		expressionFactoryUtil.setExpressionFactory(new ExpressionFactoryImpl());
	}

}