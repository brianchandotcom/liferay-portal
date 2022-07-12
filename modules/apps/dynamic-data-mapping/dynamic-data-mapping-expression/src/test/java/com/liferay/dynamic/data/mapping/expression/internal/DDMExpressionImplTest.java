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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionTracker;
import com.liferay.dynamic.data.mapping.expression.internal.functions.AbsFunction;
import com.liferay.dynamic.data.mapping.expression.internal.functions.AddFunction;
import com.liferay.dynamic.data.mapping.expression.internal.functions.MaxFunction;
import com.liferay.dynamic.data.mapping.expression.internal.functions.MultiplyFunction;
import com.liferay.dynamic.data.mapping.expression.internal.functions.SquareFunction;
import com.liferay.dynamic.data.mapping.expression.internal.functions.ZeroFunction;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Marcellus Tavares
 * @author Leonardo Barros
 */
public class DDMExpressionImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_ddmExpressionFunctionTracker = Mockito.mock(
			DDMExpressionFunctionTracker.class);

		Mockito.when(
			_ddmExpressionFunctionTracker.getDDMExpressionFunctionFactories(
				Mockito.any())
		).thenReturn(
			HashMapBuilder.<String, DDMExpressionFunctionFactory>put(
				"abs", () -> new AbsFunction()
			).put(
				"add", () -> new AddFunction()
			).put(
				"max", () -> new MaxFunction()
			).put(
				"multiply", () -> new MultiplyFunction()
			).put(
				"square", () -> new SquareFunction()
			).put(
				"zero", () -> new ZeroFunction()
			).build()
		);
	}

	@Test
	public void testAddition() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "1 + 3 + 6");

		Assert.assertEquals(new BigDecimal("10"), ddmExpressionImpl.evaluate());
	}

	@Test
	public void testAndExpression1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "3 > 1 && 1 < 2");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testAndExpression2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "4 > 2 && 1 < 0");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testAndExpression3() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "3 >= 4 and 2 <= 4");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testDivision1() throws Exception {
		BigDecimal bigDecimal = new BigDecimal(2);

		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "6 / 3");

		Assert.assertEquals(
			bigDecimal.setScale(2), ddmExpressionImpl.evaluate());
	}

	@Test
	public void testDivision2() throws Exception {
		BigDecimal bigDecimal = new BigDecimal(7.5);

		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "15 / 2");

		Assert.assertEquals(
			bigDecimal.setScale(2), ddmExpressionImpl.evaluate());
	}

	@Test
	public void testDivision3() throws Exception {
		BigDecimal bigDecimal = new BigDecimal(1.11);

		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "10 / 9");

		Assert.assertEquals(
			bigDecimal.setScale(2, RoundingMode.FLOOR),
			ddmExpressionImpl.evaluate());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyExpression() throws Exception {
		new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "");
	}

	@Test
	public void testEquals1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "3 == '3'");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testEquals2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "2 == 2.0");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testExpressionVariableNames() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "a - b");

		Set<String> variables = new HashSet<String>() {
			{
				add("a");
				add("b");
			}
		};

		Assert.assertEquals(
			variables, ddmExpressionImpl.getExpressionVariableNames());
	}

	@Test
	public void testFunction0() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "zero()");

		Assert.assertEquals(BigDecimal.ZERO, ddmExpressionImpl.evaluate());
	}

	@Test
	public void testFunction1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(
				_ddmExpressionFunctionTracker, "multiply([1,2,3])");

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal("6")));
	}

	@Test
	public void testFunction2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(
				_ddmExpressionFunctionTracker, "max([1,2,3,4])");

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal("4")));
	}

	@Test
	public void testFunctions() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(
				_ddmExpressionFunctionTracker, "square(a) + add(3, abs(b))");

		ddmExpressionImpl.setVariable("a", 2);
		ddmExpressionImpl.setVariable("b", -3);

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal("10")));
	}

	@Test
	public void testGreaterThan1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "3 > 2.0");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testGreaterThan2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "4 > 5");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testGreaterThanOrEquals1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "-2 >= -3");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testGreaterThanOrEquals2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "1 >= 2");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test(expected = DDMExpressionException.InvalidSyntax.class)
	public void testInvalidSyntax1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "1 ++ 2");

		ddmExpressionImpl.evaluate();
	}

	@Test(expected = DDMExpressionException.InvalidSyntax.class)
	public void testInvalidSyntax2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "(1 * 2");

		ddmExpressionImpl.evaluate();
	}

	@Test
	public void testLessThan1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "0 < 4");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testLessThan2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "0 < -1.5");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testLessThanOrEquals1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "1.6 <= 1.7");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testLessThanOrEquals2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "1.9 <= 1.89");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testLogicalConstant() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "TRUE || false");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testMultiplication1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "2.45 * 2");

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal("4.9")));
	}

	@Test
	public void testMultiplication2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(
				_ddmExpressionFunctionTracker, "-2 * -3.55");

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal("7.10")));
	}

	@Test
	public void testNestedFunctions() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(
				_ddmExpressionFunctionTracker, "add(2, multiply(2,3,2))");

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal("14")));
	}

	@Test
	public void testNot() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "not(-1 != 1.0)");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testNotEquals() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "1.6 != 1.66");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testNotEquals2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "2 != 2.0");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullExpression() throws Exception {
		new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, null);
	}

	@Test
	public void testOr1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "2 >= 1 || 1 < 0");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testOr2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "4 == 3 or -1 >= -2");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testOr3() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "2 < 2 or 0 > 1");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testParenthesis() throws Exception {
		BigDecimal bigDecimal = new BigDecimal(4);

		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(
				_ddmExpressionFunctionTracker, "(8 + 2) / 2.5");

		Assert.assertEquals(
			bigDecimal.setScale(2), ddmExpressionImpl.evaluate());
	}

	@Test
	public void testPrecedence() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "4 - 2 * 6");

		BigDecimal expected = new BigDecimal("-8");

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(expected));
	}

	@Test
	public void testRegexExpression1() throws Exception {
		new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "'\\d{10}'");
	}

	@Test
	public void testRegexExpression2() throws Exception {
		new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "'\\d+'");
	}

	@Test
	public void testSubtraction1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "-2 -3.55");

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal("-5.55")));
	}

	@Test
	public void testSubtraction2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "4 - 2 - 1");

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal("1")));
	}

	@Test(expected = DDMExpressionException.class)
	public void testUnavailableLogicalVariable() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "a > 5");

		ddmExpressionImpl.evaluate();
	}

	@Test(expected = DDMExpressionException.class)
	public void testUnavailableNumericVariable() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionTracker, "b + 1");

		ddmExpressionImpl.evaluate();
	}

	@Test(expected = DDMExpressionException.FunctionNotDefined.class)
	public void testUndefinedFunction() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "sum(1,b)");

		ddmExpressionImpl.evaluate();
	}

	@Test
	public void testVariableExpression() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>(_ddmExpressionFunctionTracker, "a + b");

		ddmExpressionImpl.setVariable("a", 2);
		ddmExpressionImpl.setVariable("b", 3);

		Assert.assertEquals(new BigDecimal(5), ddmExpressionImpl.evaluate());
	}

	private static DDMExpressionFunctionTracker _ddmExpressionFunctionTracker;

}