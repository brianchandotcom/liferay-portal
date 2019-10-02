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
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionTracker;
import com.liferay.dynamic.data.mapping.expression.internal.functions.AbsFunction;
import com.liferay.dynamic.data.mapping.expression.internal.functions.AddFunction;
import com.liferay.dynamic.data.mapping.expression.internal.functions.MaxFunction;
import com.liferay.dynamic.data.mapping.expression.internal.functions.MultiplyFunction;
import com.liferay.dynamic.data.mapping.expression.internal.functions.SquareFunction;
import com.liferay.dynamic.data.mapping.expression.internal.functions.ZeroFunction;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Collections;
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
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl = createDDMExpression(
			"1 + 3 + 6");

		Assert.assertEquals(new BigDecimal("10"), ddmExpressionImpl.evaluate());
	}

	@Test
	public void testAndExpression1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"3 > 1 && 1 < 2");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testAndExpression2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"4 > 2 && 1 < 0");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testAndExpression3() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"3 >= 4 and 2 <= 4");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testDivision1() throws Exception {
		BigDecimal bigDecimal = new BigDecimal(2);

		DDMExpressionImpl<BigDecimal> ddmExpressionImpl = createDDMExpression(
			"6 / 3");

		Assert.assertEquals(
			bigDecimal.setScale(2), ddmExpressionImpl.evaluate());
	}

	@Test
	public void testDivision2() throws Exception {
		BigDecimal bigDecimal = new BigDecimal(7.5);

		DDMExpressionImpl<BigDecimal> ddmExpressionImpl = createDDMExpression(
			"15 / 2");

		Assert.assertEquals(
			bigDecimal.setScale(2), ddmExpressionImpl.evaluate());
	}

	@Test
	public void testDivision3() throws Exception {
		BigDecimal bigDecimal = new BigDecimal(1.11);

		DDMExpressionImpl<BigDecimal> ddmExpressionImpl = createDDMExpression(
			"10 / 9");

		Assert.assertEquals(
			bigDecimal.setScale(2, RoundingMode.FLOOR),
			ddmExpressionImpl.evaluate());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyExpression() throws Exception {
		createDDMExpression("");
	}

	@Test
	public void testEquals1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"3 == '3'");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testExpressionVariableNames() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl = createDDMExpression(
			"a - b");

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
			new DDMExpressionImpl<>("zero()");

		ddmExpressionImpl.setDDMExpressionFunctionTracker(
			new DDMExpressionFunctionTracker() {

				@Override
				public Map<String, DDMExpressionFunctionFactory>
					getDDMExpressionFunctionFactories(
						Set<String> functionNames) {

					return _createDDMExpressionFunctionFactory(
						new ZeroFunction());
				}

				@Override
				public Map<String, DDMExpressionFunction>
					getDDMExpressionFunctions(Set<String> functionNames) {

					return Collections.singletonMap("zero", new ZeroFunction());
				}

				@Override
				public void ungetDDMExpressionFunctions(
					Map<String, DDMExpressionFunction>
						ddmExpressionFunctionsMap) {
				}

			});

		Assert.assertEquals(BigDecimal.ZERO, ddmExpressionImpl.evaluate());
	}

	@Test
	public void testFunction1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>("multiply([1,2,3])");

		ddmExpressionImpl.setDDMExpressionFunctionTracker(
			new DDMExpressionFunctionTracker() {

				@Override
				public Map<String, DDMExpressionFunctionFactory>
					getDDMExpressionFunctionFactories(
						Set<String> functionNames) {

					return _createDDMExpressionFunctionFactory(
						new MultiplyFunction());
				}

				@Override
				public Map<String, DDMExpressionFunction>
					getDDMExpressionFunctions(Set<String> functionNames) {

					return Collections.singletonMap(
						"multiply", new MultiplyFunction());
				}

				@Override
				public void ungetDDMExpressionFunctions(
					Map<String, DDMExpressionFunction>
						ddmExpressionFunctionsMap) {
				}

			});

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal("6")));
	}

	@Test
	public void testFunction2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>("max([1,2,3,4])");

		ddmExpressionImpl.setDDMExpressionFunctionTracker(
			new DDMExpressionFunctionTracker() {

				@Override
				public Map<String, DDMExpressionFunctionFactory>
					getDDMExpressionFunctionFactories(
						Set<String> functionNames) {

					return _createDDMExpressionFunctionFactory(
						new MaxFunction());
				}

				@Override
				public Map<String, DDMExpressionFunction>
					getDDMExpressionFunctions(Set<String> functionNames) {

					return Collections.singletonMap("max", new MaxFunction());
				}

				@Override
				public void ungetDDMExpressionFunctions(
					Map<String, DDMExpressionFunction>
						ddmExpressionFunctionsMap) {
				}

			});

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal("4")));
	}

	@Test
	public void testFunctions() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl =
			new DDMExpressionImpl<>("square(a) + add(3, abs(b))");

		ddmExpressionImpl.setDDMExpressionFunctionTracker(
			new DDMExpressionFunctionTracker() {

				@Override
				public Map<String, DDMExpressionFunctionFactory>
					getDDMExpressionFunctionFactories(
						Set<String> functionNames) {

					return _createDDMExpressionFunctionFactory(
						new AbsFunction(), new AddFunction(),
						new SquareFunction());
				}

				@Override
				public Map<String, DDMExpressionFunction>
					getDDMExpressionFunctions(Set<String> functionNames) {

					return new HashMap() {
						{
							put("abs", new AbsFunction());
							put("add", new AddFunction());
							put("square", new SquareFunction());
						}
					};
				}

				@Override
				public void ungetDDMExpressionFunctions(
					Map<String, DDMExpressionFunction>
						ddmExpressionFunctionsMap) {
				}

			});
		ddmExpressionImpl.setVariable("a", 2);
		ddmExpressionImpl.setVariable("b", -3);

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal("10")));
	}

	@Test
	public void testGreaterThan1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"3 > 2.0");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testGreaterThan2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"4 > 5");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testGreaterThanOrEquals1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"-2 >= -3");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testGreaterThanOrEquals2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"1 >= 2");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test(expected = DDMExpressionException.InvalidSyntax.class)
	public void testInvalidSyntax1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl = createDDMExpression(
			"1 ++ 2");

		ddmExpressionImpl.evaluate();
	}

	@Test(expected = DDMExpressionException.InvalidSyntax.class)
	public void testInvalidSyntax2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl = createDDMExpression(
			"(1 * 2");

		ddmExpressionImpl.evaluate();
	}

	@Test
	public void testLessThan1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"0 < 4");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testLessThan2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"0 < -1.5");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testLessThanOrEquals1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"1.6 <= 1.7");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testLessThanOrEquals2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"1.9 <= 1.89");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testLogicalConstant() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"TRUE || false");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testMultiplication1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl = createDDMExpression(
			"2.45 * 2");

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal("4.9")));
	}

	@Test
	public void testMultiplication2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl = createDDMExpression(
			"-2 * -3.55");

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal("7.10")));
	}

	@Test
	public void testNot() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"not(-1 != 1.0)");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testNotEquals() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"1.6 != 1.66");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testNotEquals2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"2 != 2.0");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullExpression() throws Exception {
		createDDMExpression(null);
	}

	@Test
	public void testOr1() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"2 >= 1 || 1 < 0");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testOr2() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"4 == 3 or -1 >= -2");

		Assert.assertTrue(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testOr3() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"2 < 2 or 0 > 1");

		Assert.assertFalse(ddmExpressionImpl.evaluate());
	}

	@Test
	public void testParenthesis() throws Exception {
		BigDecimal bigDecimal = new BigDecimal(4);

		DDMExpressionImpl<BigDecimal> ddmExpressionImpl = createDDMExpression(
			"(8 + 2) / 2.5");

		Assert.assertEquals(
			bigDecimal.setScale(2), ddmExpressionImpl.evaluate());
	}

	@Test
	public void testPrecedence() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl = createDDMExpression(
			"4 - 2 * 6");

		BigDecimal expected = new BigDecimal("-8");

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(expected));
	}

	@Test
	public void testRegexExpression1() throws Exception {
		createDDMExpression("'\\d{10}'");
	}

	@Test
	public void testRegexExpression2() throws Exception {
		createDDMExpression("'\\d+'");
	}

	@Test
	public void testSubtraction1() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl = createDDMExpression(
			"-2 -3.55");

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal("-5.55")));
	}

	@Test
	public void testSubtraction2() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl = createDDMExpression(
			"4 - 2 - 1");

		BigDecimal bigDecimal = ddmExpressionImpl.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal("1")));
	}

	@Test(expected = DDMExpressionException.class)
	public void testUnavailableLogicalVariable() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"a > 5");

		ddmExpressionImpl.evaluate();
	}

	@Test(expected = DDMExpressionException.class)
	public void testUnavailableNumericVariable() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = createDDMExpression(
			"b + 1");

		ddmExpressionImpl.evaluate();
	}

	@Test(expected = DDMExpressionException.FunctionNotDefined.class)
	public void testUndefinedFunction() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl = createDDMExpression(
			"sum(1,b)");

		ddmExpressionImpl.evaluate();
	}

	@Test
	public void testVariableExpression() throws Exception {
		DDMExpressionImpl<BigDecimal> ddmExpressionImpl = createDDMExpression(
			"a + b");

		ddmExpressionImpl.setVariable("a", 2);
		ddmExpressionImpl.setVariable("b", 3);

		Assert.assertEquals(new BigDecimal(5), ddmExpressionImpl.evaluate());
	}

	protected <T> DDMExpressionImpl<T> createDDMExpression(String expression)
		throws DDMExpressionException {

		DDMExpressionImpl<T> ddmExpressionImpl = new DDMExpressionImpl<>(
			expression);

		ddmExpressionImpl.setDDMExpressionFunctionTracker(
			mock(DDMExpressionFunctionTracker.class));

		return ddmExpressionImpl;
	}

	private Map<String, DDMExpressionFunctionFactory>
		_createDDMExpressionFunctionFactory(
			DDMExpressionFunction... ddmExpressionFunctions) {

		Map<String, DDMExpressionFunctionFactory>
			ddmExpressionFunctionFactoryMap = new HashMap<>();

		for (DDMExpressionFunction ddmExpressionFunction :
				ddmExpressionFunctions) {

			ddmExpressionFunctionFactoryMap.put(
				ddmExpressionFunction.getName(), () -> ddmExpressionFunction);
		}

		return ddmExpressionFunctionFactoryMap;
	}

}