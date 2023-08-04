/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression.internal;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionRegistry;
import com.liferay.dynamic.data.mapping.expression.model.AndExpression;
import com.liferay.dynamic.data.mapping.expression.model.ArithmeticExpression;
import com.liferay.dynamic.data.mapping.expression.model.ComparisonExpression;
import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.expression.model.FunctionCallExpression;
import com.liferay.dynamic.data.mapping.expression.model.IntegerLiteral;
import com.liferay.dynamic.data.mapping.expression.model.MinusExpression;
import com.liferay.dynamic.data.mapping.expression.model.NotExpression;
import com.liferay.dynamic.data.mapping.expression.model.OrExpression;
import com.liferay.dynamic.data.mapping.expression.model.Parenthesis;
import com.liferay.dynamic.data.mapping.expression.model.Term;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class DDMExpressionModelTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_ddmExpressionFunctionRegistry = Mockito.mock(
			DDMExpressionFunctionRegistry.class);

		Mockito.when(
			_ddmExpressionFunctionRegistry.getDDMExpressionFunctionFactories(
				Mockito.any())
		).thenReturn(
			HashMapBuilder.<String, DDMExpressionFunctionFactory>put(
				"date", new TestDDMExpressionFunctionFactory()
			).put(
				"equals", new TestDDMExpressionFunctionFactory()
			).put(
				"sum", new TestDDMExpressionFunctionFactory()
			).build()
		);
	}

	@Test
	public void testAndExpression() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionRegistry, "true && (2 != 3)");

		Expression modelExpression = ddmExpressionImpl.getModel();

		Assert.assertEquals(AndExpression.class, modelExpression.getClass());

		AndExpression andExpression = (AndExpression)modelExpression;

		Expression leftOperandExpression1 =
			andExpression.getLeftOperandExpression();
		Expression rightOperandExpression1 =
			andExpression.getRightOperandExpression();

		Assert.assertEquals(Term.class, leftOperandExpression1.getClass());
		Assert.assertEquals(
			ComparisonExpression.class, rightOperandExpression1.getClass());

		Term term = (Term)leftOperandExpression1;

		Assert.assertEquals("true", term.getValue());

		ComparisonExpression comparisonExpression =
			(ComparisonExpression)rightOperandExpression1;

		Expression leftOperandExpression2 =
			comparisonExpression.getLeftOperandExpression();
		Expression rightOperandExpression2 =
			comparisonExpression.getRightOperandExpression();

		Assert.assertEquals("!=", comparisonExpression.getOperator());

		Assert.assertEquals(
			IntegerLiteral.class, leftOperandExpression2.getClass());
		Assert.assertEquals(
			IntegerLiteral.class, rightOperandExpression2.getClass());

		term = (Term)leftOperandExpression2;

		Assert.assertEquals("2", term.getValue());

		term = (Term)rightOperandExpression2;

		Assert.assertEquals("3", term.getValue());
	}

	@Test
	public void testArithmeticExpression() throws Exception {
		DDMExpressionImpl<Double> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionRegistry, "a + b * c - d");

		Expression modelExpression = ddmExpressionImpl.getModel();

		Assert.assertEquals(
			ArithmeticExpression.class, modelExpression.getClass());

		ArithmeticExpression arithmeticExpression1 =
			(ArithmeticExpression)modelExpression;

		Expression leftOperandExpression1 =
			arithmeticExpression1.getLeftOperandExpression();
		Expression rightOperandExpression1 =
			arithmeticExpression1.getRightOperandExpression();

		Assert.assertEquals(
			ArithmeticExpression.class, leftOperandExpression1.getClass());
		Assert.assertEquals(Term.class, rightOperandExpression1.getClass());
		Assert.assertEquals("-", arithmeticExpression1.getOperator());

		Term term = (Term)rightOperandExpression1;

		Assert.assertEquals("d", term.getValue());

		ArithmeticExpression arithmeticExpression2 =
			(ArithmeticExpression)leftOperandExpression1;

		Expression leftOperandExpression2 =
			arithmeticExpression2.getLeftOperandExpression();
		Expression rightOperandExpression2 =
			arithmeticExpression2.getRightOperandExpression();

		Assert.assertEquals(Term.class, leftOperandExpression2.getClass());
		Assert.assertEquals(
			ArithmeticExpression.class, rightOperandExpression2.getClass());
		Assert.assertEquals("+", arithmeticExpression2.getOperator());

		term = (Term)leftOperandExpression2;

		Assert.assertEquals("a", term.getValue());

		ArithmeticExpression arithmeticExpression3 =
			(ArithmeticExpression)rightOperandExpression2;

		Expression leftOperandExpression3 =
			arithmeticExpression3.getLeftOperandExpression();
		Expression rightOperandExpression3 =
			arithmeticExpression3.getRightOperandExpression();

		Assert.assertEquals(Term.class, leftOperandExpression3.getClass());
		Assert.assertEquals(Term.class, rightOperandExpression3.getClass());
		Assert.assertEquals("*", arithmeticExpression3.getOperator());

		term = (Term)leftOperandExpression3;

		Assert.assertEquals("b", term.getValue());

		term = (Term)rightOperandExpression3;

		Assert.assertEquals("c", term.getValue());
	}

	@Test
	public void testFunctionCallExpression() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionRegistry, "date()");

		Expression modelExpression = ddmExpressionImpl.getModel();

		Assert.assertEquals(
			FunctionCallExpression.class, modelExpression.getClass());

		FunctionCallExpression functionCallExpression =
			(FunctionCallExpression)modelExpression;

		Assert.assertEquals("date", functionCallExpression.getFunctionName());
		Assert.assertEquals(0, functionCallExpression.getArity());
	}

	@Test
	public void testGreaterThanExpression() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionRegistry, "(2 * 5) > 3");

		Expression modelExpression = ddmExpressionImpl.getModel();

		Assert.assertEquals(
			ComparisonExpression.class, modelExpression.getClass());

		ComparisonExpression comparisonExpression =
			(ComparisonExpression)modelExpression;

		Expression leftOperandExpression =
			comparisonExpression.getLeftOperandExpression();

		Expression rightOperandExpression =
			comparisonExpression.getRightOperandExpression();

		Assert.assertEquals(">", comparisonExpression.getOperator());

		Assert.assertEquals(
			Parenthesis.class, leftOperandExpression.getClass());
		Assert.assertEquals(
			IntegerLiteral.class, rightOperandExpression.getClass());

		Parenthesis parenthesis = (Parenthesis)leftOperandExpression;

		ArithmeticExpression arithmeticExpression =
			(ArithmeticExpression)parenthesis.getOperandExpression();

		Expression arithmeticLeftOperandExpression =
			arithmeticExpression.getLeftOperandExpression();

		Expression arithmeticRightOperandExpression =
			arithmeticExpression.getRightOperandExpression();

		Assert.assertEquals("*", arithmeticExpression.getOperator());

		Assert.assertEquals(
			IntegerLiteral.class, arithmeticLeftOperandExpression.getClass());
		Assert.assertEquals(
			IntegerLiteral.class, arithmeticRightOperandExpression.getClass());

		Term term = (Term)arithmeticLeftOperandExpression;

		Assert.assertEquals("2", term.getValue());

		term = (Term)arithmeticRightOperandExpression;

		Assert.assertEquals("5", term.getValue());

		term = (Term)rightOperandExpression;

		Assert.assertEquals("3", term.getValue());
	}

	@Test
	public void testLessThanEqualExpression() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionRegistry,
			"((1 + 4) / (5 - 2)) <= sum(Var1,Var2)");

		Expression modelExpression = ddmExpressionImpl.getModel();

		Assert.assertEquals(
			ComparisonExpression.class, modelExpression.getClass());

		ComparisonExpression comparisonExpression =
			(ComparisonExpression)modelExpression;

		Expression comparisonLeftOperandExpression =
			comparisonExpression.getLeftOperandExpression();

		Expression comparisonRightOperandExpression =
			comparisonExpression.getRightOperandExpression();

		Assert.assertEquals("<=", comparisonExpression.getOperator());

		Assert.assertEquals(
			Parenthesis.class, comparisonLeftOperandExpression.getClass());
		Assert.assertEquals(
			FunctionCallExpression.class,
			comparisonRightOperandExpression.getClass());

		Parenthesis parenthesis0 = (Parenthesis)comparisonLeftOperandExpression;

		ArithmeticExpression arithmeticExpression1 =
			(ArithmeticExpression)parenthesis0.getOperandExpression();

		Expression arithmeticLeftOperandExpression1 =
			arithmeticExpression1.getLeftOperandExpression();

		Expression arithmeticRightOperandExpression1 =
			arithmeticExpression1.getRightOperandExpression();

		Assert.assertEquals("/", arithmeticExpression1.getOperator());

		Assert.assertEquals(
			Parenthesis.class, arithmeticLeftOperandExpression1.getClass());
		Assert.assertEquals(
			Parenthesis.class, arithmeticRightOperandExpression1.getClass());

		Parenthesis parenthesis1 =
			(Parenthesis)arithmeticLeftOperandExpression1;

		Parenthesis parenthesis2 =
			(Parenthesis)arithmeticRightOperandExpression1;

		ArithmeticExpression arithmeticExpression2 =
			(ArithmeticExpression)parenthesis1.getOperandExpression();

		Expression arithmeticLeftOperandExpression2 =
			arithmeticExpression2.getLeftOperandExpression();

		Expression arithmeticRightOperandExpression2 =
			arithmeticExpression2.getRightOperandExpression();

		Assert.assertEquals("+", arithmeticExpression2.getOperator());

		Assert.assertEquals(
			IntegerLiteral.class, arithmeticLeftOperandExpression2.getClass());
		Assert.assertEquals(
			IntegerLiteral.class, arithmeticRightOperandExpression2.getClass());

		Term term = (Term)arithmeticLeftOperandExpression2;

		Assert.assertEquals("1", term.getValue());

		term = (Term)arithmeticRightOperandExpression2;

		Assert.assertEquals("4", term.getValue());

		ArithmeticExpression arithmeticExpression3 =
			(ArithmeticExpression)parenthesis2.getOperandExpression();

		Expression arithmeticLeftOperandExpression3 =
			arithmeticExpression3.getLeftOperandExpression();

		Expression arithmeticRightOperandExpression3 =
			arithmeticExpression3.getRightOperandExpression();

		Assert.assertEquals("-", arithmeticExpression3.getOperator());

		Assert.assertEquals(
			IntegerLiteral.class, arithmeticLeftOperandExpression3.getClass());
		Assert.assertEquals(
			IntegerLiteral.class, arithmeticRightOperandExpression3.getClass());

		term = (Term)arithmeticLeftOperandExpression3;

		Assert.assertEquals("5", term.getValue());

		term = (Term)arithmeticRightOperandExpression3;

		Assert.assertEquals("2", term.getValue());

		FunctionCallExpression functionCallExpression =
			(FunctionCallExpression)comparisonRightOperandExpression;

		Assert.assertEquals("sum", functionCallExpression.getFunctionName());
		Assert.assertEquals(2, functionCallExpression.getArity());

		List<Expression> parameterExpressions =
			functionCallExpression.getParameterExpressions();

		Assert.assertNotNull(parameterExpressions);
		Assert.assertEquals(
			parameterExpressions.toString(), 2, parameterExpressions.size());

		Expression parameterExpression = parameterExpressions.get(0);

		Assert.assertEquals(Term.class, parameterExpression.getClass());

		term = (Term)parameterExpression;

		Assert.assertEquals("Var1", term.getValue());

		parameterExpression = parameterExpressions.get(1);

		Assert.assertEquals(Term.class, parameterExpression.getClass());

		term = (Term)parameterExpression;

		Assert.assertEquals("Var2", term.getValue());
	}

	@Test
	public void testNotExpression() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionRegistry, "not false");

		Expression modelExpression = ddmExpressionImpl.getModel();

		Assert.assertEquals(NotExpression.class, modelExpression.getClass());

		NotExpression notExpression = (NotExpression)modelExpression;

		Expression operandExpression = notExpression.getOperandExpression();

		Assert.assertEquals(Term.class, operandExpression.getClass());

		Term term = (Term)operandExpression;

		Assert.assertEquals("false", term.getValue());
	}

	@Test
	public void testOrExpression() throws Exception {
		DDMExpressionImpl<Boolean> ddmExpressionImpl = new DDMExpressionImpl<>(
			_ddmExpressionFunctionRegistry,
			"(-3 < Var1) || (not equals(Var2,sum(Var3,Var4)))");

		Expression modelExpression = ddmExpressionImpl.getModel();

		Assert.assertEquals(OrExpression.class, modelExpression.getClass());

		OrExpression orExpression = (OrExpression)modelExpression;

		Expression leftOperandExpression1 =
			orExpression.getLeftOperandExpression();
		Expression rightOperandExpression1 =
			orExpression.getRightOperandExpression();

		Assert.assertEquals(
			ComparisonExpression.class, leftOperandExpression1.getClass());
		Assert.assertEquals(
			NotExpression.class, rightOperandExpression1.getClass());

		ComparisonExpression comparisonExpression =
			(ComparisonExpression)leftOperandExpression1;

		Expression leftOperandExpression2 =
			comparisonExpression.getLeftOperandExpression();
		Expression rightOperandExpression2 =
			comparisonExpression.getRightOperandExpression();

		Assert.assertEquals("<", comparisonExpression.getOperator());

		Assert.assertEquals(
			MinusExpression.class, leftOperandExpression2.getClass());
		Assert.assertEquals(Term.class, rightOperandExpression2.getClass());

		MinusExpression minusExpression =
			(MinusExpression)leftOperandExpression2;

		Expression minusOperandExpression =
			minusExpression.getOperandExpression();

		Assert.assertEquals(
			IntegerLiteral.class, minusOperandExpression.getClass());

		Term term = (Term)minusOperandExpression;

		Assert.assertEquals("3", term.getValue());

		term = (Term)rightOperandExpression2;

		Assert.assertEquals("Var1", term.getValue());

		NotExpression notExpression = (NotExpression)rightOperandExpression1;

		Expression notOperandExpression = notExpression.getOperandExpression();

		Assert.assertEquals(
			FunctionCallExpression.class, notOperandExpression.getClass());

		FunctionCallExpression functionCallExpression1 =
			(FunctionCallExpression)notOperandExpression;

		Assert.assertEquals(
			"equals", functionCallExpression1.getFunctionName());
		Assert.assertEquals(2, functionCallExpression1.getArity());

		List<Expression> parameterExpressions1 =
			functionCallExpression1.getParameterExpressions();

		Assert.assertEquals(
			parameterExpressions1.toString(), 2, parameterExpressions1.size());

		Expression parameterExpression1 = parameterExpressions1.get(0);

		Assert.assertEquals(Term.class, parameterExpression1.getClass());

		term = (Term)parameterExpression1;

		Assert.assertEquals("Var2", term.getValue());

		Expression parameterExpression2 = parameterExpressions1.get(1);

		Assert.assertEquals(
			FunctionCallExpression.class, parameterExpression2.getClass());

		FunctionCallExpression functionCallExpression2 =
			(FunctionCallExpression)parameterExpression2;

		Assert.assertEquals("sum", functionCallExpression2.getFunctionName());
		Assert.assertEquals(2, functionCallExpression2.getArity());

		List<Expression> parameterExpressions2 =
			functionCallExpression2.getParameterExpressions();

		Assert.assertEquals(
			parameterExpressions2.toString(), 2, parameterExpressions2.size());

		Expression parameterExpression3 = parameterExpressions2.get(0);

		Assert.assertEquals(Term.class, parameterExpression3.getClass());

		term = (Term)parameterExpression3;

		Assert.assertEquals("Var3", term.getValue());

		Expression parameterExpression4 = parameterExpressions2.get(1);

		Assert.assertEquals(Term.class, parameterExpression4.getClass());

		term = (Term)parameterExpression4;

		Assert.assertEquals("Var4", term.getValue());
	}

	private static DDMExpressionFunctionRegistry _ddmExpressionFunctionRegistry;

	private static class TestDDMExpressionFunctionFactory
		implements DDMExpressionFunctionFactory {

		@Override
		public DDMExpressionFunction create() {
			return null;
		}

	}

}