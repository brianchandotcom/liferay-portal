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

		Expression leftOperand = andExpression.getLeftOperand();
		Expression rightOperand = andExpression.getRightOperand();

		Assert.assertEquals(Term.class, leftOperand.getClass());
		Assert.assertEquals(
			ComparisonExpression.class, rightOperand.getClass());

		Term term = (Term)leftOperand;

		Assert.assertEquals("true", term.getValue());

		ComparisonExpression comparisonExpression =
			(ComparisonExpression)rightOperand;

		Expression leftOperand2 = comparisonExpression.getLeftOperand();
		Expression rightOperand2 = comparisonExpression.getRightOperand();

		Assert.assertEquals("!=", comparisonExpression.getOperator());

		Assert.assertEquals(Term.class, leftOperand2.getClass());
		Assert.assertEquals(Term.class, rightOperand2.getClass());

		term = (Term)leftOperand2;

		Assert.assertEquals("2", term.getValue());

		term = (Term)rightOperand2;

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

		Expression leftOperand = arithmeticExpression.getLeftOperand();
		Expression rightOperand = arithmeticExpression.getRightOperand();

		Assert.assertEquals(ArithmeticExpression.class, leftOperand.getClass());
		Assert.assertEquals(Term.class, rightOperand.getClass());
		Assert.assertEquals("-", arithmeticExpression.getOperator());

		Term term = (Term)rightOperand;

		Assert.assertEquals("d", term.getValue());

		ArithmeticExpression arithmeticExpression2 =
			(ArithmeticExpression)leftOperand;

		Expression leftOperand2 = arithmeticExpression2.getLeftOperand();
		Expression rightOperand2 = arithmeticExpression2.getRightOperand();

		Assert.assertEquals(Term.class, leftOperand2.getClass());
		Assert.assertEquals(
			ArithmeticExpression.class, rightOperand2.getClass());
		Assert.assertEquals("+", arithmeticExpression2.getOperator());

		term = (Term)leftOperand2;

		Assert.assertEquals("a", term.getValue());

		ArithmeticExpression arithmeticExpression3 =
			(ArithmeticExpression)rightOperand2;

		Expression leftOperand3 = arithmeticExpression3.getLeftOperand();
		Expression rightOperand3 = arithmeticExpression3.getRightOperand();

		Assert.assertEquals(Term.class, leftOperand3.getClass());
		Assert.assertEquals(Term.class, rightOperand3.getClass());
		Assert.assertEquals("*", arithmeticExpression3.getOperator());

		term = (Term)leftOperand3;

		Assert.assertEquals("b", term.getValue());

		term = (Term)rightOperand3;

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

		Expression comparisonLeftOperand =
			comparisonExpression.getLeftOperand();

		Expression comparisonRightOperand =
			comparisonExpression.getRightOperand();

		Assert.assertEquals(">", comparisonExpression.getOperator());

		Assert.assertEquals(
			ArithmeticExpression.class, comparisonLeftOperand.getClass());
		Assert.assertEquals(Term.class, comparisonRightOperand.getClass());

		ArithmeticExpression arithmeticExpression =
			(ArithmeticExpression)comparisonLeftOperand;

		Expression arithmeticLeftOperand =
			arithmeticExpression.getLeftOperand();

		Expression arithmeticRightOperand =
			arithmeticExpression.getRightOperand();

		Assert.assertEquals("*", arithmeticExpression.getOperator());

		Assert.assertEquals(Term.class, arithmeticLeftOperand.getClass());
		Assert.assertEquals(Term.class, arithmeticRightOperand.getClass());

		Term term = (Term)arithmeticLeftOperand;

		Assert.assertEquals("2", term.getValue());

		term = (Term)arithmeticRightOperand;

		Assert.assertEquals("5", term.getValue());

		term = (Term)comparisonRightOperand;

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

		Expression comparisonLeftOperand =
			comparisonExpression.getLeftOperand();

		Expression comparisonRightOperand =
			comparisonExpression.getRightOperand();

		Assert.assertEquals("<=", comparisonExpression.getOperator());

		Assert.assertEquals(
			ArithmeticExpression.class, comparisonLeftOperand.getClass());
		Assert.assertEquals(
			FunctionCallExpression.class, comparisonRightOperand.getClass());

		ArithmeticExpression arithmeticExpression =
			(ArithmeticExpression)comparisonLeftOperand;

		Expression arithmeticLeftOperand =
			arithmeticExpression.getLeftOperand();

		Expression arithmeticRightOperand =
			arithmeticExpression.getRightOperand();

		Assert.assertEquals("/", arithmeticExpression.getOperator());

		Assert.assertEquals(
			ArithmeticExpression.class, arithmeticLeftOperand.getClass());
		Assert.assertEquals(
			ArithmeticExpression.class, arithmeticRightOperand.getClass());

		ArithmeticExpression arithmeticExpression2 =
			(ArithmeticExpression)arithmeticLeftOperand;

		Expression arithmeticLeftOperand2 =
			arithmeticExpression2.getLeftOperand();

		Expression arithmeticRightOperand2 =
			arithmeticExpression2.getRightOperand();

		Assert.assertEquals("+", arithmeticExpression2.getOperator());

		Assert.assertEquals(Term.class, arithmeticLeftOperand2.getClass());
		Assert.assertEquals(Term.class, arithmeticRightOperand2.getClass());

		Term term = (Term)arithmeticLeftOperand2;

		Assert.assertEquals("1", term.getValue());

		term = (Term)arithmeticRightOperand2;

		Assert.assertEquals("4", term.getValue());

		ArithmeticExpression arithmeticExpression3 =
			(ArithmeticExpression)arithmeticRightOperand;

		Expression arithmeticLeftOperand3 =
			arithmeticExpression3.getLeftOperand();

		Expression arithmeticRightOperand3 =
			arithmeticExpression3.getRightOperand();

		Assert.assertEquals("-", arithmeticExpression3.getOperator());

		Assert.assertEquals(Term.class, arithmeticLeftOperand3.getClass());
		Assert.assertEquals(Term.class, arithmeticRightOperand3.getClass());

		term = (Term)arithmeticLeftOperand3;

		Assert.assertEquals("5", term.getValue());

		term = (Term)arithmeticRightOperand3;

		Assert.assertEquals("2", term.getValue());

		FunctionCallExpression functionCallExpression =
			(FunctionCallExpression)comparisonRightOperand;

		Assert.assertEquals("sum", functionCallExpression.getFunctionName());
		Assert.assertEquals(2, functionCallExpression.getArity());

		List<Expression> parameters = functionCallExpression.getParameters();

		Assert.assertNotNull(parameters);
		Assert.assertEquals(2, parameters.size());

		Expression parameter = parameters.get(0);

		Assert.assertEquals(Term.class, parameter.getClass());

		term = (Term)parameter;

		Assert.assertEquals("Var1", term.getValue());

		parameter = parameters.get(1);

		Assert.assertEquals(Term.class, parameter.getClass());

		term = (Term)parameter;

		Assert.assertEquals("Var2", term.getValue());
	}

	@Test
	public void testNotExpression() throws Exception {
		DDMExpressionImpl<Boolean> expression = new DDMExpressionImpl<>(
			"not false", Boolean.class);

		Expression expressionModel = expression.getModel();

		Assert.assertEquals(NotExpression.class, expressionModel.getClass());

		NotExpression notExpression = (NotExpression)expressionModel;

		Expression operand = notExpression.getOperand();

		Assert.assertEquals(Term.class, operand.getClass());

		Term term = (Term)operand;

		Assert.assertEquals("false", term.getValue());
	}

	@Test
	public void testOrExpression() throws Exception {
		DDMExpressionImpl<Boolean> expression = new DDMExpressionImpl<>(
			"(-3 < Var1) || (not equals(Var2,sum(Var3,Var4)))", Boolean.class);

		Expression expressionModel = expression.getModel();

		Assert.assertEquals(OrExpression.class, expressionModel.getClass());

		OrExpression orExpression = (OrExpression)expressionModel;

		Expression leftOperand = orExpression.getLeftOperand();
		Expression rightOperand = orExpression.getRightOperand();

		Assert.assertEquals(ComparisonExpression.class, leftOperand.getClass());
		Assert.assertEquals(NotExpression.class, rightOperand.getClass());

		ComparisonExpression comparisonExpression =
			(ComparisonExpression)leftOperand;

		Expression leftOperand2 = comparisonExpression.getLeftOperand();
		Expression rightOperand2 = comparisonExpression.getRightOperand();

		Assert.assertEquals("<", comparisonExpression.getOperator());

		Assert.assertEquals(MinusExpression.class, leftOperand2.getClass());
		Assert.assertEquals(Term.class, rightOperand2.getClass());

		MinusExpression minusExpression = (MinusExpression)leftOperand2;

		Expression minusOperand = minusExpression.getOperand();

		Assert.assertEquals(Term.class, minusOperand.getClass());

		Term term = (Term)minusOperand;

		Assert.assertEquals("3", term.getValue());

		term = (Term)rightOperand2;

		Assert.assertEquals("Var1", term.getValue());

		NotExpression notExpression = (NotExpression)rightOperand;

		Expression notOperand = notExpression.getOperand();

		Assert.assertEquals(
			FunctionCallExpression.class, notOperand.getClass());

		FunctionCallExpression functionCallExpression =
			(FunctionCallExpression)notOperand;

		Assert.assertEquals("equals", functionCallExpression.getFunctionName());
		Assert.assertEquals(2, functionCallExpression.getArity());

		List<Expression> parameters = functionCallExpression.getParameters();

		Assert.assertEquals(2, parameters.size());

		Expression parameter1 = parameters.get(0);

		Assert.assertEquals(Term.class, parameter1.getClass());

		term = (Term)parameter1;

		Assert.assertEquals("Var2", term.getValue());

		Expression parameter2 = parameters.get(1);

		Assert.assertEquals(
			FunctionCallExpression.class, parameter2.getClass());

		FunctionCallExpression functionCallExpression2 =
			(FunctionCallExpression)parameter2;

		Assert.assertEquals("sum", functionCallExpression2.getFunctionName());
		Assert.assertEquals(2, functionCallExpression2.getArity());

		List<Expression> parameters2 = functionCallExpression2.getParameters();

		Assert.assertEquals(2, parameters2.size());

		Expression parameter3 = parameters2.get(0);

		Assert.assertEquals(Term.class, parameter3.getClass());

		term = (Term)parameter3;

		Assert.assertEquals("Var3", term.getValue());

		Expression parameter4 = parameters2.get(1);

		Assert.assertEquals(Term.class, parameter4.getClass());

		term = (Term)parameter4;

		Assert.assertEquals("Var4", term.getValue());
	}

}