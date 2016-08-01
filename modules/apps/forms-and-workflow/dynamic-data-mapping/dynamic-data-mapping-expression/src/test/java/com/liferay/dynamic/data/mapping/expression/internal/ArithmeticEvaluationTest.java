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

package com.liferay.dynamic.data.mapping.expression.internal;

import com.liferay.dynamic.data.mapping.expression.DDMExpression;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class ArithmeticEvaluationTest {

	@Test
	public void testAdditionExpression() throws Exception {
		Assert.assertEquals(2, evaluate("1 + 1").intValue());
		Assert.assertEquals(4L, evaluate("2 + 2").longValue());
		Assert.assertEquals(3.5f, evaluate("2.5 + 1").floatValue(), .1);
		Assert.assertEquals(.5d, evaluate("-2 + 2.5").doubleValue(), .1);
	}

	@Test
	public void testCombinedDecimalExpression() throws Exception {
		int expected = -((1+3)-4*(2-(2+(4-5))*(5-2)*5)+1);

		Assert.assertEquals(
			expected,
			evaluate("-((1+3)-4*(2-(2+(4-5))*(5-2)*5)+1)").intValue());

		expected = 1*2-5*4-3-5*2*5-5+7-10-4*3*4-2+11-11+4*3-1;

		Assert.assertEquals(
			expected,
			evaluate("1*2-5*4-3-5*2*5-5+7-10-4*3*4-2+11-11+4*3-1").intValue());
	}

	@Test
	public void testCombinedFloatingPointExpression() throws Exception {
		double expected = -(-2.5+4*(2.15+((11+63.2)-2)*(.5+6)-(2.6*1.1-4)));

		double actual = evaluate(
			"-(-2.5+4*(2.15+((11+63.2)-2)*(.5+6)-(2.6*1.1-4)))").doubleValue();

		Assert.assertEquals(expected, actual, .1);
	}

	@Test
	public void testDecimalLiteral() throws Exception {
		Assert.assertEquals(42, evaluate("42").intValue());
		Assert.assertEquals(10000000000L, evaluate("10000000000").longValue());
	}

	@Test
	public void testDivisionExpression() throws Exception {
		Assert.assertEquals(2, evaluate("4 / 2").intValue());
		Assert.assertEquals(4L, evaluate("12 / 3").longValue());
		Assert.assertEquals(7.5f, evaluate("15 / 2").floatValue(), .1);
		Assert.assertEquals(8.5d, evaluate("17 / 2").doubleValue(), .1);
	}

	@Test
	public void testFloatingPointLiteral() throws Exception {
		Assert.assertEquals(42.5f, evaluate("42.5").floatValue(), .1);
		Assert.assertEquals(
			10000000000.5d, evaluate("10000000000.5").doubleValue(), .1);
	}

	@Test
	public void testMinusExpression() throws Exception {
		Assert.assertEquals(-1, evaluate("-1").intValue());
		Assert.assertEquals(1, evaluate("--1").intValue());
		Assert.assertEquals(-.5f, evaluate("-.5").floatValue(), .1);
		Assert.assertEquals(5.5d, evaluate("--5.5").doubleValue(), .1);
	}

	@Test
	public void testMultiplicationExpression() throws Exception {
		Assert.assertEquals(2, evaluate("2 * 1").intValue());
		Assert.assertEquals(8L, evaluate("4 * 2").longValue());
		Assert.assertEquals(5f, evaluate("2.5 * 2").floatValue(), .1);
		Assert.assertEquals(7d, evaluate("2 * 3.5").doubleValue(), .1);
	}

	@Test
	public void testScientificNotation() throws Exception {
		Assert.assertEquals(100000L, evaluate("1e5").longValue());
		Assert.assertEquals(100000000L, evaluate("1E8").longValue());
		Assert.assertEquals(123, evaluate(".123e+3").longValue());
		Assert.assertEquals(.2d, evaluate("2e-1").doubleValue(), .01);
		Assert.assertEquals(.123, evaluate("123E-3").doubleValue(), .001);
	}

	@Test
	public void testSubtractionExpression() throws Exception {
		Assert.assertEquals(1, evaluate("2 - 1").intValue());
		Assert.assertEquals(2L, evaluate("4 - 2").longValue());
		Assert.assertEquals(.5f, evaluate("2.5 - 2").floatValue(), .1);
		Assert.assertEquals(-.5d, evaluate("2 - 2.5").doubleValue(), .1);
	}

	protected Number evaluate(String expressionString) throws Exception {
		DDMExpression<Number> ddmExpression = new DDMExpressionImpl<>(
			expressionString, Number.class);

		return ddmExpression.evaluate();
	}

}