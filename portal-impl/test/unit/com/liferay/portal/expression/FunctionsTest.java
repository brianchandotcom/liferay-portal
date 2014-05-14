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
import com.liferay.portal.kernel.util.MathUtil;

import org.junit.Before;
import org.junit.Test;

import org.testng.Assert;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class FunctionsTest {

	@Before
	public void setUp() throws Exception {
		setupExpressionFactory();
	}

	@Test
	public void testSumWithDoubleValues() throws Exception {
		Expression<Double> expression =
			ExpressionFactoryUtil.createDoubleExpression(
				"sum(var1, var2, var3)");

		double var1 = 5.5;

		expression.addDoubleVariable("var1", var1);
		expression.addExpressionVariable("var2", Double.class, "var1 + 3.5");
		expression.addExpressionVariable("var3", Double.class, "var2 + var1");

		double var2 = var1 + 3.5;
		double var3 = var1 + var2;

		double actual = expression.evaluate();

		double expected = MathUtil.sum(var1, var2, var3);

		Assert.assertEquals(actual, expected);
	}

	@Test
	public void testSumWithLongValues() throws Exception {
		Expression<Long> expression =
			ExpressionFactoryUtil.createLongExpression("sum(var1, var2, var3)");

		long var1 = 5;

		expression.addLongVariable("var1", var1);
		expression.addExpressionVariable("var2", Long.class, "var1 + 3");
		expression.addExpressionVariable("var3", Long.class, "var2 + var1");

		long var2 = var1 + 3;
		long var3 = var1 + var2;

		long actual = expression.evaluate();

		long expected = MathUtil.sum(var1, var2, var3);

		Assert.assertEquals(actual, expected);
	}

	protected void setupExpressionFactory() {
		ExpressionFactoryUtil expressionFactoryUtil =
			new ExpressionFactoryUtil();

		expressionFactoryUtil.setExpressionFactory(new ExpressionFactoryImpl());
	}

}