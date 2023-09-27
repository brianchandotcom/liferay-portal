/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression.model;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class ExpressionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAndExpresion() {
		Expression expression = new AndExpression(
			new Term("true"), new Term("false"));

		Assert.assertEquals("true and false", expression.toString());
	}

	@Test
	public void testArithmeticExpresion() {
		Expression expression = new ArithmeticExpression(
			"+", new Term("1"), new Term("1"));

		Assert.assertEquals("1 + 1", expression.toString());
	}

	@Test
	public void testComparisonExpresion() {
		Expression expression = new ComparisonExpression(
			">", new Term("2"), new Term("1"));

		Assert.assertEquals("2 > 1", expression.toString());
	}

	@Test
	public void testFunctionCallExpression() {
		Expression expression = new FunctionCallExpression(
			"sum", Arrays.<Expression>asList(new Term("1"), new Term("2")));

		Assert.assertEquals("sum(1, 2)", expression.toString());
	}

	@Test
	public void testMinusExpression() {
		Expression parentesisExpression = new Parenthesis(new Term("1"));

		Expression minusExpression = new MinusExpression(parentesisExpression);

		Assert.assertEquals("-(1)", minusExpression.toString());
	}

	@Test
	public void testNotExpression1() {
		Expression expression = new NotExpression(new Term("false"));

		Assert.assertEquals("not(false)", expression.toString());
	}

	@Test
	public void testNotExpression2() {
		Expression getValueExpression = new FunctionCallExpression(
			"getValue", Arrays.asList(new StringLiteral("Field1")));

		Expression equalsExpression = new FunctionCallExpression(
			"equals",
			Arrays.asList(getValueExpression, new StringLiteral("Joe")));

		Expression expression = new NotExpression(equalsExpression);

		Assert.assertEquals(
			"not(equals(getValue('Field1'), 'Joe'))", expression.toString());
	}

	@Test
	public void testParenthesis() {
		ArithmeticExpression arithmeticExpression0 = new ArithmeticExpression(
			"+", new Term("1"), new Term("3"));

		ArithmeticExpression arithmeticExpression1 = new ArithmeticExpression(
			"-", new Term("2"), new Term("4"));

		ArithmeticExpression arithmeticExpression3 = new ArithmeticExpression(
			"*", new Parenthesis(arithmeticExpression0),
			new Parenthesis(arithmeticExpression1));

		Expression expression = new Parenthesis(arithmeticExpression3);

		Assert.assertEquals("((1 + 3) * (2 - 4))", expression.toString());
	}

}