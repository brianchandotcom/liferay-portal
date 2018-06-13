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
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 * @author Leonardo Barros
 */
public class DDMExpressionImplTest {

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyExpression() throws Exception {
		new DDMExpressionImpl<>("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullExpression() throws Exception {
		new DDMExpressionImpl<>(null);
	}

	@Test
	public void testAddition() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("1 + 3 + 6");

		Assert.assertEquals(BigDecimal.TEN, ddmExpression.evaluate());
	}

	@Test
	public void testAndExpression1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("3 > 1 && 1 < 2");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testAndExpression2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("4 > 2 && 1 < 0");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testAndExpression3() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("3 >= 4 and 2 <= 4");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testDivision1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("6 / 3");

		Assert.assertEquals(new BigDecimal(2), ddmExpression.evaluate());
	}

	@Test
	public void testDivision2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("15 / 2");

		Assert.assertEquals(new BigDecimal(7.5), ddmExpression.evaluate());
	}

	@Test
	public void testEquals1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("3 == '3'");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testEquals2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("2 == 2.0");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testGreaterThan1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("3 > 2.0");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testGreaterThan2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("4 > 5");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testGreaterThanOrEquals1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("-2 >= -3");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testGreaterThanOrEquals2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("1 >= 2");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testLessThan1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("0 < 4");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testLessThan2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("0 < -1.5");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testLessThanOrEquals1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("1.6 <= 1.7");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testLessThanOrEquals2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("1.9 <= 1.89");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testMultiplication1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("2.45 * 2");

		BigDecimal expected = new BigDecimal(4.9);

		expected.setScale(4, RoundingMode.CEILING);

		BigDecimal actual = ddmExpression.evaluate();

		actual.setScale(4, RoundingMode.CEILING);

		Assert.assertEquals(0, expected.compareTo(actual));
	}

	@Test
	public void testMultiplication2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("-2 * -3.55");

		BigDecimal expected = new BigDecimal(7.10);

		expected.setScale(4, RoundingMode.CEILING);

		BigDecimal actual = ddmExpression.evaluate();

		actual.setScale(4, RoundingMode.CEILING);

		Assert.assertEquals(0, expected.compareTo(actual));
	}

	@Test
	public void testNotEquals() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("1.6 != 1.66");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testNot() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("not(-1 != 1.0)");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testOr1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("2 >= 1 || 1 < 0");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testOr2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("4 == 3 or -1 >= -2");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testOr3() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("2 < 2 or 0 > 1");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testSubtraction1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("-2 -3.55");

		Assert.assertEquals(new BigDecimal(-5.55), ddmExpression.evaluate());
	}

	@Test
	public void testSubtraction2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("4 - 2 - 1");

		Assert.assertEquals(new BigDecimal(1), ddmExpression.evaluate());
	}

	@Test
	public void testPrecedence() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("4 - 2 * 6");

		Assert.assertEquals(new BigDecimal(-8), ddmExpression.evaluate());
	}

	@Test
	public void testParenthesis() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("(8 + 2) / 2.5");

		Assert.assertEquals(new BigDecimal(4), ddmExpression.evaluate());
	}

	@Test
	public void testVariableExpression() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("a + b");

		ddmExpression.setVariable("a", 2);
		ddmExpression.setVariable("b", 3);

		Assert.assertEquals(new BigDecimal(5), ddmExpression.evaluate());
	}

	@Test
	public void testExpressionVariableNames() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("a - b");

		Set<String> variables = new HashSet() {
			{
				add("a");
				add("b");
			}
		};

		Assert.assertEquals(
			variables, ddmExpression.getExpressionVariableNames());
	}

	@Test(expected = DDMExpressionException.FunctionNotDefined.class)
	public void testUndefinedFunction() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("sum(1,b)");

		ddmExpression.evaluate();
	}

	@Test(expected = DDMExpressionException.InvalidSyntax.class)
	public void testInvalidSyntax1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("1 ++ 2");

		ddmExpression.evaluate();
	}

	@Test(expected = DDMExpressionException.InvalidSyntax.class)
	public void testInvalidSyntax2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("(1 * 2");

		ddmExpression.evaluate();
	}

	@Test
	public void testFunction0() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("prime()");

		DDMExpressionFunction.Function0<BigDecimal> prime =
			() -> new BigDecimal(3);

		Map<String, DDMExpressionFunction> functions = new HashMap() {
			{
				put("prime", prime);
			}
		};

		ddmExpression.addFunctions(functions);

		Assert.assertEquals(new BigDecimal(3), ddmExpression.evaluate());
	}

	@Test
	public void testFunction4() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("max(1,2,3,4)");

		DDMExpressionFunction.Function4
			<BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal>
				max =
					(n1, n2, n3, n4) ->
						Stream.of(
							n1, n2, n3, n4
						).max(
							BigDecimal::compareTo
						).get();

		Map<String, DDMExpressionFunction> functions = new HashMap() {
			{
				put("max", max);
			}
		};

		ddmExpression.addFunctions(functions);

		Assert.assertEquals(new BigDecimal(4), ddmExpression.evaluate());
	}

	@Test
	public void testFunction3() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("multiply(1,2,3)");

		DDMExpressionFunction.Function3
			<BigDecimal, BigDecimal, BigDecimal, BigDecimal> multiply =
				(n1, n2, n3) ->
					n1.multiply(
						n2
					).multiply(
						n3
					);


		Map<String, DDMExpressionFunction> functions = new HashMap() {
			{
				put("multiply", multiply);
			}
		};

		ddmExpression.addFunctions(functions);

		Assert.assertEquals(new BigDecimal(6), ddmExpression.evaluate());
	}

	@Test
	public void testFunctions() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpression =
			new DDMExpressionImpl<>("square(a) + add(3, abs(b))");

		ddmExpression.setVariable("a", 2);
		ddmExpression.setVariable("b", -3);

		DDMExpressionFunction.Function1<BigDecimal, BigDecimal> abs =
			n -> n.abs();

		DDMExpressionFunction.Function2<BigDecimal, BigDecimal, BigDecimal>
			add = (n1, n2) -> n1.add(n2);

		DDMExpressionFunction.Function1<BigDecimal, BigDecimal> square =
			n -> n.multiply(n);

		Map<String, DDMExpressionFunction> functions = new HashMap() {
			{
				put("abs", abs);
				put("add", add);
				put("square", square);
			}
		};

		ddmExpression.addFunctions(functions);

		Assert.assertEquals(new BigDecimal(10), ddmExpression.evaluate());
	}

	@Test(expected = DDMExpressionException.class)
	public void testUnavailableLogicalVariable() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("a > 5");

		ddmExpression.evaluate();
	}

	@Test(expected = DDMExpressionException.class)
	public void testUnavailableNumericVariable() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("b + 1");

		ddmExpression.evaluate();
	}

	@Test
	public void testLogicalConstant() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpression =
			new DDMExpressionImpl<>("TRUE || false");

		Assert.assertTrue(ddmExpression.evaluate());
	}
}