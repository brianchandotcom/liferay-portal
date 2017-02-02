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

import com.liferay.dynamic.data.mapping.expression.model.AndExpression;
import com.liferay.dynamic.data.mapping.expression.model.ArithmeticExpression;
import com.liferay.dynamic.data.mapping.expression.model.ComparisonExpression;
import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.expression.model.FunctionCallExpression;
import com.liferay.dynamic.data.mapping.expression.model.MinusExpression;
import com.liferay.dynamic.data.mapping.expression.model.NotExpression;
import com.liferay.dynamic.data.mapping.expression.model.OrExpression;
import com.liferay.dynamic.data.mapping.expression.model.Term;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class ExpressionModelTest {

	@Test
	public void testAndExpression() throws Exception {
		DDMExpressionImpl<Boolean> expression = new DDMExpressionImpl<>(
			"true && (2 != 3)", Boolean.class);

		Expression expressionModel = expression.getModel();

		Assert.assertEquals(AndExpression.class, expressionModel.getClass());

		AndExpression andExpression = (AndExpression)expressionModel;

		Expression leftOperandExpression =
			andExpression.getLeftOperandExpression();
		Expression rightOperandExpression =
			andExpression.getRightOperandExpression();

		Assert.assertEquals(Term.class, leftOperandExpression.getClass());
		Assert.assertEquals(
			ComparisonExpression.class, rightOperandExpression.getClass());

		Term term = (Term)leftOperandExpression;

		Assert.assertEquals("true", term.getValue());

		ComparisonExpression comparisonExpression =
			(ComparisonExpression)rightOperandExpression;

		Expression leftOperandExpression2 =
			comparisonExpression.getLeftOperandExpression();
		Expression rightOperandExpression2 =
			comparisonExpression.getRightOperandExpression();

		Assert.assertEquals("!=", comparisonExpression.getOperator());

		Assert.assertEquals(Term.class, leftOperandExpression2.getClass());
		Assert.assertEquals(Term.class, rightOperandExpression2.getClass());

		term = (Term)leftOperandExpression2;

		Assert.assertEquals("2", term.getValue());

		term = (Term)rightOperandExpression2;

		Assert.assertEquals("3", term.getValue());
	}

	@Test
	public void testArithmeticExpression() throws Exception {
		DDMExpressionImpl<Double> expression = new DDMExpressionImpl<>(
			"a + b * c - d", Double.class);

		Expression expressionModel = expression.getModel();

		Assert.assertEquals(
			ArithmeticExpression.class, expressionModel.getClass());

		ArithmeticExpression arithmeticExpression =
			(ArithmeticExpression)expressionModel;

		Expression leftOperandExpression =
			arithmeticExpression.getLeftOperandExpression();
		Expression rightOperandExpression =
			arithmeticExpression.getRightOperandExpression();

		Assert.assertEquals(
			ArithmeticExpression.class, leftOperandExpression.getClass());
		Assert.assertEquals(Term.class, rightOperandExpression.getClass());
		Assert.assertEquals("-", arithmeticExpression.getOperator());

		Term term = (Term)rightOperandExpression;

		Assert.assertEquals("d", term.getValue());

		ArithmeticExpression arithmeticExpression2 =
			(ArithmeticExpression)leftOperandExpression;

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
		DDMExpressionImpl<Boolean> expression = new DDMExpressionImpl<>(
			"date()", Boolean.class);

		Expression expressionModel = expression.getModel();

		Assert.assertEquals(
			FunctionCallExpression.class, expressionModel.getClass());

		FunctionCallExpression functionCallExpression =
			(FunctionCallExpression)expressionModel;

		Assert.assertEquals("date", functionCallExpression.getFunctionName());
		Assert.assertEquals(0, functionCallExpression.getArity());
	}

	@Test
	public void testGreaterThanExpression() throws Exception {
		DDMExpressionImpl<Boolean> expression = new DDMExpressionImpl<>(
			"(2 * 5) > 3", Boolean.class);

		Expression expressionModel = expression.getModel();

		Assert.assertEquals(
			ComparisonExpression.class, expressionModel.getClass());

		ComparisonExpression comparisonExpression =
			(ComparisonExpression)expressionModel;

		Expression leftOperandExpression =
			comparisonExpression.getLeftOperandExpression();

		Expression rightOperandExpression =
			comparisonExpression.getRightOperandExpression();

		Assert.assertEquals(">", comparisonExpression.getOperator());

		Assert.assertEquals(
			ArithmeticExpression.class, leftOperandExpression.getClass());
		Assert.assertEquals(Term.class, rightOperandExpression.getClass());

		ArithmeticExpression arithmeticExpression =
			(ArithmeticExpression)leftOperandExpression;

		Expression arithmeticLeftOperandExpression =
			arithmeticExpression.getLeftOperandExpression();

		Expression arithmeticRightOperandExpression =
			arithmeticExpression.getRightOperandExpression();

		Assert.assertEquals("*", arithmeticExpression.getOperator());

		Assert.assertEquals(
			Term.class, arithmeticLeftOperandExpression.getClass());
		Assert.assertEquals(
			Term.class, arithmeticRightOperandExpression.getClass());

		Term term = (Term)arithmeticLeftOperandExpression;

		Assert.assertEquals("2", term.getValue());

		term = (Term)arithmeticRightOperandExpression;

		Assert.assertEquals("5", term.getValue());

		term = (Term)rightOperandExpression;

		Assert.assertEquals("3", term.getValue());
	}

	@Test
	public void testLessThanEqualExpression() throws Exception {
		DDMExpressionImpl<Boolean> expression = new DDMExpressionImpl<>(
			"((1 + 4) / (5 - 2)) <= sum(Var1,Var2)", Boolean.class);

		Expression expressionModel = expression.getModel();

		Assert.assertEquals(
			ComparisonExpression.class, expressionModel.getClass());

		ComparisonExpression comparisonExpression =
			(ComparisonExpression)expressionModel;

		Expression comparisonLeftOperandExpression =
			comparisonExpression.getLeftOperandExpression();

		Expression comparisonRightOperandExpression =
			comparisonExpression.getRightOperandExpression();

		Assert.assertEquals("<=", comparisonExpression.getOperator());

		Assert.assertEquals(
			ArithmeticExpression.class,
			comparisonLeftOperandExpression.getClass());
		Assert.assertEquals(
			FunctionCallExpression.class,
			comparisonRightOperandExpression.getClass());

		ArithmeticExpression arithmeticExpression =
			(ArithmeticExpression)comparisonLeftOperandExpression;

		Expression arithmeticLeftOperandExpression =
			arithmeticExpression.getLeftOperandExpression();

		Expression arithmeticRightOperandExpression =
			arithmeticExpression.getRightOperandExpression();

		Assert.assertEquals("/", arithmeticExpression.getOperator());

		Assert.assertEquals(
			ArithmeticExpression.class,
			arithmeticLeftOperandExpression.getClass());
		Assert.assertEquals(
			ArithmeticExpression.class,
			arithmeticRightOperandExpression.getClass());

		ArithmeticExpression arithmeticExpression2 =
			(ArithmeticExpression)arithmeticLeftOperandExpression;

		Expression arithmeticLeftOperandExpression2 =
			arithmeticExpression2.getLeftOperandExpression();

		Expression arithmeticRightOperandExpression2 =
			arithmeticExpression2.getRightOperandExpression();

		Assert.assertEquals("+", arithmeticExpression2.getOperator());

		Assert.assertEquals(
			Term.class, arithmeticLeftOperandExpression2.getClass());
		Assert.assertEquals(
			Term.class, arithmeticRightOperandExpression2.getClass());

		Term term = (Term)arithmeticLeftOperandExpression2;

		Assert.assertEquals("1", term.getValue());

		term = (Term)arithmeticRightOperandExpression2;

		Assert.assertEquals("4", term.getValue());

		ArithmeticExpression arithmeticExpression3 =
			(ArithmeticExpression)arithmeticRightOperandExpression;

		Expression arithmeticLeftOperandExpression3 =
			arithmeticExpression3.getLeftOperandExpression();

		Expression arithmeticRightOperandExpression3 =
			arithmeticExpression3.getRightOperandExpression();

		Assert.assertEquals("-", arithmeticExpression3.getOperator());

		Assert.assertEquals(
			Term.class, arithmeticLeftOperandExpression3.getClass());
		Assert.assertEquals(
			Term.class, arithmeticRightOperandExpression3.getClass());

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
		DDMExpressionImpl<Boolean> expression = new DDMExpressionImpl<>(
			"not false", Boolean.class);

		Expression expressionModel = expression.getModel();

		Assert.assertEquals(NotExpression.class, expressionModel.getClass());

		NotExpression notExpression = (NotExpression)expressionModel;

		Expression operandExpression = notExpression.getOperandExpression();

		Assert.assertEquals(Term.class, operandExpression.getClass());

		Term term = (Term)operandExpression;

		Assert.assertEquals("false", term.getValue());
	}

	@Test
	public void testOrExpression() throws Exception {
		DDMExpressionImpl<Boolean> expression = new DDMExpressionImpl<>(
			"(-3 < Var1) || (not equals(Var2,sum(Var3,Var4)))", Boolean.class);

		Expression expressionModel = expression.getModel();

		Assert.assertEquals(OrExpression.class, expressionModel.getClass());

		OrExpression orExpression = (OrExpression)expressionModel;

		Expression leftOperandExpression =
			orExpression.getLeftOperandExpression();
		Expression rightOperandExpression =
			orExpression.getRightOperandExpression();

		Assert.assertEquals(
			ComparisonExpression.class, leftOperandExpression.getClass());
		Assert.assertEquals(
			NotExpression.class, rightOperandExpression.getClass());

		ComparisonExpression comparisonExpression =
			(ComparisonExpression)leftOperandExpression;

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

		Assert.assertEquals(Term.class, minusOperandExpression.getClass());

		Term term = (Term)minusOperandExpression;

		Assert.assertEquals("3", term.getValue());

		term = (Term)rightOperandExpression2;

		Assert.assertEquals("Var1", term.getValue());

		NotExpression notExpression = (NotExpression)rightOperandExpression;

		Expression notOperandExpression = notExpression.getOperandExpression();

		Assert.assertEquals(
			FunctionCallExpression.class, notOperandExpression.getClass());

		FunctionCallExpression functionCallExpression =
			(FunctionCallExpression)notOperandExpression;

		Assert.assertEquals("equals", functionCallExpression.getFunctionName());
		Assert.assertEquals(2, functionCallExpression.getArity());

		List<Expression> parameterExpressions =
			functionCallExpression.getParameterExpressions();

		Assert.assertEquals(
			parameterExpressions.toString(), 2, parameterExpressions.size());

		Expression parameterExpression1 = parameterExpressions.get(0);

		Assert.assertEquals(Term.class, parameterExpression1.getClass());

		term = (Term)parameterExpression1;

		Assert.assertEquals("Var2", term.getValue());

		Expression parameterExpression2 = parameterExpressions.get(1);

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

}