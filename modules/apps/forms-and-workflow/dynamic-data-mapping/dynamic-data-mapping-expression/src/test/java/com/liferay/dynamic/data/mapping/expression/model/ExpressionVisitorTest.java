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

package com.liferay.dynamic.data.mapping.expression.model;

import com.liferay.portal.kernel.util.StringBundler;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class ExpressionVisitorTest {

	@Test
	public void testAndExpresion() {
		Expression expression = new AndExpression(
			new Term("true"), new Term("false"));

		Assert.assertEquals(
			"true and false", expression.accept(_printExpressionVisitor));
	}

	@Test
	public void testArithmeticExpresion() {
		Expression expression = new ArithmeticExpression(
			"+", new Term("1"), new Term("1"));

		Assert.assertEquals(
			"1 + 1", expression.accept(_printExpressionVisitor));
	}

	@Test
	public void testComparisonExpresion() {
		Expression expression = new ComparisonExpression(
			">", new Term("2"), new Term("1"));

		Assert.assertEquals(
			"2 > 1", expression.accept(_printExpressionVisitor));
	}

	@Test
	public void testFunctionCallExpression() {
		Expression expression = new FunctionCallExpression(
			"sum", Arrays.<Expression>asList(new Term("1"), new Term("2")));

		Assert.assertEquals(
			"sum(1, 2)", expression.accept(_printExpressionVisitor));
	}

	@Test
	public void testMinusExpression() {
		Expression expression = new MinusExpression(new Term("1"));

		Assert.assertEquals("-(1)", expression.accept(_printExpressionVisitor));
	}

	@Test
	public void testNotExpression() {
		Expression expression = new NotExpression(new Term("false"));

		Assert.assertEquals(
			"not(false)", expression.accept(_printExpressionVisitor));
	}

	private final ExpressionVisitor<String> _printExpressionVisitor =
		new PrintExpressionVisitor();

	private static class PrintExpressionVisitor
		extends ExpressionVisitor<String> {

		@Override
		public String visit(AndExpression andExpression) {
			return formatBinaryExpression(
				andExpression.getOperator(),
				andExpression.getLeftOperandExpression(),
				andExpression.getRightOperandExpression());
		}

		@Override
		public String visit(ArithmeticExpression arithmeticExpression) {
			return formatBinaryExpression(
				arithmeticExpression.getOperator(),
				arithmeticExpression.getLeftOperandExpression(),
				arithmeticExpression.getRightOperandExpression());
		}

		@Override
		public String visit(ComparisonExpression comparisonExpression) {
			return formatBinaryExpression(
				comparisonExpression.getOperator(),
				comparisonExpression.getLeftOperandExpression(),
				comparisonExpression.getRightOperandExpression());
		}

		@Override
		public String visit(FunctionCallExpression functionCallExpression) {
			StringBundler sb = new StringBundler();

			for (Expression parameter :
					functionCallExpression.getParameterExpressions()) {

				sb.append(visit(parameter));
				sb.append(", ");
			}

			sb.setIndex(sb.index() - 1);

			return String.format(
				"%s(%s)", functionCallExpression.getFunctionName(),
				sb.toString());
		}

		@Override
		public String visit(MinusExpression minusExpression) {
			return String.format(
				"-(%s)", visit(minusExpression.getOperandExpression()));
		}

		@Override
		public String visit(NotExpression notExpression) {
			return String.format(
				"not(%s)", visit(notExpression.getOperandExpression()));
		}

		@Override
		public String visit(OrExpression orExpression) {
			return formatBinaryExpression(
				orExpression.getOperator(),
				orExpression.getLeftOperandExpression(),
				orExpression.getRightOperandExpression());
		}

		@Override
		public String visit(Term term) {
			return term.getValue();
		}

		protected String formatBinaryExpression(
			String operator, Expression leftExpression,
			Expression rightExpression) {

			return String.format(
				"%s %s %s", leftExpression.accept(this), operator,
				rightExpression.accept(this));
		}

		protected String visit(Expression expression) {
			return expression.accept(this);
		}

	}

}