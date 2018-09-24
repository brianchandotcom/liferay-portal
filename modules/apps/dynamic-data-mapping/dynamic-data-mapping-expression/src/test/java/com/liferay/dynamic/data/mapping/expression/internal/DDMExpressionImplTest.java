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
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionTracker;
import com.liferay.dynamic.data.mapping.expression.internal.functions.AbsFunction;
import com.liferay.dynamic.data.mapping.expression.internal.functions.AddFunction;
import com.liferay.dynamic.data.mapping.expression.internal.functions.MaxFunction;
import com.liferay.dynamic.data.mapping.expression.internal.functions.MultiplyFunction;
import com.liferay.dynamic.data.mapping.expression.internal.functions.SquareFunction;
import com.liferay.dynamic.data.mapping.expression.internal.functions.ZeroFunction;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcellus Tavares
 * @author Leonardo Barros
 */
@RunWith(PowerMockRunner.class)
public class DDMExpressionImplTest extends PowerMockito {

	@Test
	public void testAddition() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = createDDMExpression(
			"1 + 3 + 6");

		Assert.assertEquals(new BigDecimal("10"), ddmExpression.evaluate());
	}

	@Test
	public void testAndExpression1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"3 > 1 && 1 < 2");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testAndExpression2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"4 > 2 && 1 < 0");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testAndExpression3() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"3 >= 4 and 2 <= 4");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testDivision1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = createDDMExpression(
			"6 / 3");

		Assert.assertEquals(new BigDecimal(2), ddmExpression.evaluate());
	}

	@Test
	public void testDivision2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = createDDMExpression(
			"15 / 2");

		Assert.assertEquals(new BigDecimal(7.5), ddmExpression.evaluate());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyExpression() throws Exception {
		createDDMExpression("");
	}

	@Test
	public void testEquals1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"3 == '3'");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testExpressionVariableNames() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = createDDMExpression(
			"a - b");

		Set<String> variables = new HashSet() {
			{
				add("a");
				add("b");
			}
		};

		Assert.assertEquals(
			variables, ddmExpression.getExpressionVariableNames());
	}

	@Test
	public void testFunction0() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = spy(
			createDDMExpression("zero()"));

		Map<String, DDMExpressionFunction> functions = new HashMap() {
			{
				put("zero", new ZeroFunction());
			}
		};

		when(
			ddmExpression.getDDMExpressionFunctions()
		).thenReturn(
			functions
		);

		Assert.assertEquals(BigDecimal.ZERO, ddmExpression.evaluate());
	}

	@Test
	public void testFunction1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = spy(
			createDDMExpression("multiply([1,2,3])"));

		Map<String, DDMExpressionFunction> functions = new HashMap() {
			{
				put("multiply", new MultiplyFunction());
			}
		};

		when(
			ddmExpression.getDDMExpressionFunctions()
		).thenReturn(
			functions
		);

		BigDecimal actual = ddmExpression.evaluate();

		Assert.assertEquals(0, actual.compareTo(new BigDecimal("6")));
	}

	@Test
	public void testFunction2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = spy(
			createDDMExpression("max([1,2,3,4])"));

		Map<String, DDMExpressionFunction> functions = new HashMap() {
			{
				put("max", new MaxFunction());
			}
		};

		when(
			ddmExpression.getDDMExpressionFunctions()
		).thenReturn(
			functions
		);

		BigDecimal actual = ddmExpression.evaluate();

		Assert.assertEquals(0, actual.compareTo(new BigDecimal("4")));
	}

	@Test
	public void testFunctions() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = spy(
			createDDMExpression("square(a) + add(3, abs(b))"));

		ddmExpression.setVariable("a", 2);
		ddmExpression.setVariable("b", -3);

		Map<String, DDMExpressionFunction> functions = new HashMap() {
			{
				put("abs", new AbsFunction());
				put("add", new AddFunction());
				put("square", new SquareFunction());
			}
		};

		when(
			ddmExpression.getDDMExpressionFunctions()
		).thenReturn(
			functions
		);

		BigDecimal actual = ddmExpression.evaluate();

		Assert.assertEquals(0, actual.compareTo(new BigDecimal("10")));
	}

	@Test
	public void testGreaterThan1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"3 > 2.0");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testGreaterThan2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression("4 > 5");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testGreaterThanOrEquals1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"-2 >= -3");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testGreaterThanOrEquals2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"1 >= 2");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test(expected = DDMExpressionException.InvalidSyntax.class)
	public void testInvalidSyntax1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = createDDMExpression(
			"1 ++ 2");

		ddmExpression.evaluate();
	}

	@Test(expected = DDMExpressionException.InvalidSyntax.class)
	public void testInvalidSyntax2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = createDDMExpression(
			"(1 * 2");

		ddmExpression.evaluate();
	}

	@Test
	public void testLessThan1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression("0 < 4");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testLessThan2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"0 < -1.5");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testLessThanOrEquals1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"1.6 <= 1.7");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testLessThanOrEquals2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"1.9 <= 1.89");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testLogicalConstant() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"TRUE || false");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testMultiplication1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = createDDMExpression(
			"2.45 * 2");

		BigDecimal actual = ddmExpression.evaluate();

		Assert.assertEquals(0, actual.compareTo(new BigDecimal("4.9")));
	}

	@Test
	public void testMultiplication2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = createDDMExpression(
			"-2 * -3.55");

		BigDecimal actual = ddmExpression.evaluate();

		Assert.assertEquals(0, actual.compareTo(new BigDecimal("7.10")));
	}

	@Test
	public void testNot() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"not(-1 != 1.0)");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testNotEquals() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"1.6 != 1.66");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testNotEquals2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"2 != 2.0");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullExpression() throws Exception {
		createDDMExpression(null);
	}

	@Test
	public void testOr1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"2 >= 1 || 1 < 0");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testOr2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"4 == 3 or -1 >= -2");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testOr3() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression(
			"2 < 2 or 0 > 1");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testParenthesis() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = createDDMExpression(
			"(8 + 2) / 2.5");

		Assert.assertEquals(new BigDecimal(4), ddmExpression.evaluate());
	}

	@Test
	public void testPrecedence() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = createDDMExpression(
			"4 - 2 * 6");

		BigDecimal expected = new BigDecimal("-8");

		BigDecimal actual = ddmExpression.evaluate();

		Assert.assertEquals(0, actual.compareTo(expected));
	}

	@Test
	public void testSubtraction1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = createDDMExpression(
			"-2 -3.55");

		BigDecimal actual = ddmExpression.evaluate();

		Assert.assertEquals(0, actual.compareTo(new BigDecimal("-5.55")));
	}

	@Test
	public void testSubtraction2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = createDDMExpression(
			"4 - 2 - 1");

		BigDecimal actual = ddmExpression.evaluate();

		Assert.assertEquals(0, actual.compareTo(new BigDecimal("1")));
	}

	@Test(expected = DDMExpressionException.class)
	public void testUnavailableLogicalVariable() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression("a > 5");

		ddmExpression.evaluate();
	}

	@Test(expected = DDMExpressionException.class)
	public void testUnavailableNumericVariable() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression = createDDMExpression("b + 1");

		ddmExpression.evaluate();
	}

	@Test(expected = DDMExpressionException.FunctionNotDefined.class)
	public void testUndefinedFunction() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = createDDMExpression(
			"sum(1,b)");

		ddmExpression.evaluate();
	}

	@Test
	public void testVariableExpression() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression = createDDMExpression(
			"a + b");

		ddmExpression.setVariable("a", 2);
		ddmExpression.setVariable("b", 3);

		Assert.assertEquals(new BigDecimal(5), ddmExpression.evaluate());
	}

	protected <T> DDMExpressionImpl<T> createDDMExpression(String expression)
		throws DDMExpressionException {

		DDMExpressionImpl<T> ddmExpression = new DDMExpressionImpl<>(
			expression);

		ddmExpression.setDDMExpressionFunctionTracker(
			mock(DDMExpressionFunctionTracker.class));

		return ddmExpression;
	}

}