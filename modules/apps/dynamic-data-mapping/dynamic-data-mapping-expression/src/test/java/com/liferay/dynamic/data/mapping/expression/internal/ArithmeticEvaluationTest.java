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
		Assert.assertEquals(2, _evaluateInt("1 + 1"));
		Assert.assertEquals(4L, _evaluateLong("2 + 2"));
		Assert.assertEquals(3.5F, _evaluateFloat("2.5 + 1"), .1);
		Assert.assertEquals(.5D, _evaluateDouble("-2 + 2.5"), .1);
	}

	@Test
	public void testCombinedDecimalExpression() throws Exception {
		int expected =
			-((1 + 3) - (4 * (2 - ((2 + (4 - 5)) * (5 - 2) * 5))) + 1);

		int actual = _evaluateInt(
			"-((1 + 3) - 4 * (2 - (2 + (4 - 5)) * (5 - 2) * 5) + 1)");

		Assert.assertEquals(expected, actual);

		expected =
			(1 * 2) - (5 * 4) - 3 - (5 * 2 * 5) - 5 + 7 - 10 - (4 * 3) - 2 + 1;

		actual = _evaluateInt(
			"1 * 2 - 5 * 4 - 3 - 5 * 2 * 5 - 5 + 7 - 10 - 4 * 3 - 2 + 1");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testCombinedFloatingPointExpression() throws Exception {
		double expected =
			-(-2.5 +
				(4 *
					(2.1 + (((11 + 3.2) - 2) * (.5 + 6)) - ((2.6 * 1.1) - 4))));

		double actual = _evaluateDouble(
			"-(-2.5 + 4 * (2.1 + ((11 + 3.2) - 2) * (.5 + 6) - (2.6 * 1.1 - " +
				"4)))");

		Assert.assertEquals(expected, actual, .1);
	}

	@Test
	public void testDecimalLiteral() throws Exception {
		Assert.assertEquals(42, _evaluateInt("42"));
		Assert.assertEquals(10000000000L, _evaluateLong("10000000000"));
	}

	@Test
	public void testDivisionExpression() throws Exception {
		Assert.assertEquals(2, _evaluateInt("4 / 2"));
		Assert.assertEquals(4L, _evaluateLong("12 / 3"));
		Assert.assertEquals(7.5F, _evaluateFloat("15 / 2"), .1);
		Assert.assertEquals(8.5D, _evaluateDouble("17 / 2"), .1);
	}

	@Test
	public void testFloatingPointLiteral() throws Exception {
		Assert.assertEquals(42.5F, _evaluateFloat("42.5"), .1);
		Assert.assertEquals(
			10000000000.5D, _evaluateDouble("10000000000.5"), .1);
	}

	@Test
	public void testMinusExpression() throws Exception {
		Assert.assertEquals(-1, _evaluateInt("-1"));
		Assert.assertEquals(1, _evaluateInt("--1"));
		Assert.assertEquals(-.5F, _evaluateFloat("-.5"), .1);
		Assert.assertEquals(5.5D, _evaluateDouble("--5.5"), .1);
	}

	@Test
	public void testMultiplicationExpression() throws Exception {
		Assert.assertEquals(2, _evaluateInt("2 * 1"));
		Assert.assertEquals(8L, _evaluateLong("4 * 2"));
		Assert.assertEquals(5F, _evaluateFloat("2.5 * 2"), .1);
		Assert.assertEquals(7D, _evaluateDouble("2 * 3.5"), .1);
	}

	@Test
	public void testScientificNotation() throws Exception {
		Assert.assertEquals(100000L, _evaluateLong("1e5"));
		Assert.assertEquals(100000000L, _evaluateLong("1E8"));
		Assert.assertEquals(123, _evaluateLong(".123e+3"));
		Assert.assertEquals(.2D, _evaluateDouble("2e-1"), .01);
		Assert.assertEquals(.123, _evaluateDouble("123E-3"), .001);
	}

	@Test
	public void testSubtractionExpression() throws Exception {
		Assert.assertEquals(1, _evaluateInt("2 - 1"));
		Assert.assertEquals(2L, _evaluateLong("4 - 2"));
		Assert.assertEquals(.5F, _evaluateFloat("2.5 - 2"), .1);
		Assert.assertEquals(-.5D, _evaluateDouble("2 - 2.5"), .1);
	}

	protected Number evaluate(String expressionString) throws Exception {
		DDMExpression<Number> ddmExpression = new DDMExpressionImpl<>(
			expressionString, Number.class);

		return ddmExpression.evaluate();
	}

	private double _evaluateDouble(String expressionString) throws Exception {
		Number number = evaluate(expressionString);

		return number.doubleValue();
	}

	private float _evaluateFloat(String expressionString) throws Exception {
		Number number = evaluate(expressionString);

		return number.floatValue();
	}

	private int _evaluateInt(String expressionString) throws Exception {
		Number number = evaluate(expressionString);

		return number.intValue();
	}

	private long _evaluateLong(String expressionString) throws Exception {
		Number number = evaluate(expressionString);

		return number.longValue();
	}

}