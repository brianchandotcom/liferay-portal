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

import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionBaseListener;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.AdditionExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.AndExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.DivisionExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.EqualsExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.FunctionParametersContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.GreaterThanExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.GreaterThanOrEqualsExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.LessThanExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.LessThanOrEqualsExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.LogicalConstantContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.MinusExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.MultiplicationExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.NotEqualsExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.NotExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.NumericLiteralContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.OrExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.StringLiteralContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.SubtractionExpressionContext;
import com.liferay.dynamic.data.mapping.expression.model.AndExpression;
import com.liferay.dynamic.data.mapping.expression.model.ArithmeticExpression;
import com.liferay.dynamic.data.mapping.expression.model.ComparisonExpression;
import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.expression.model.FunctionCallExpression;
import com.liferay.dynamic.data.mapping.expression.model.MinusExpression;
import com.liferay.dynamic.data.mapping.expression.model.NotExpression;
import com.liferay.dynamic.data.mapping.expression.model.OrExpression;
import com.liferay.dynamic.data.mapping.expression.model.Term;
import com.liferay.portal.kernel.util.StringPool;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * @author Marcellus Tavares
 */
public class DDMExpressionListener extends DDMExpressionBaseListener {

	@Override
	public void enterAdditionExpression(
		@NotNull AdditionExpressionContext context) {

		pushArithmeticExpression(context.getChild(1));
	}

	@Override
	public void enterAndExpression(@NotNull AndExpressionContext context) {
		AndExpression andExpression = new AndExpression();

		_expressionTokens.push(andExpression);
	}

	@Override
	public void enterDivisionExpression(
		@NotNull DivisionExpressionContext context) {

		pushArithmeticExpression(context.getChild(1));
	}

	@Override
	public void enterEqualsExpression(
		@NotNull EqualsExpressionContext context) {

		pushComparisonExpression(context.getChild(1));
	}

	@Override
	public void enterFunctionCallExpression(
		@NotNull DDMExpressionParser.FunctionCallExpressionContext context) {

		_functionNames.add(context.functionName.getText());

		FunctionParametersContext functionParametersContext =
			context.functionParameters();

		FunctionCallExpression functionCallExpression =
			new FunctionCallExpression(
				context.functionName.getText(),
				getNumberOfFunctionParameters(functionParametersContext));

		_expressionTokens.push(functionCallExpression);
	}

	@Override
	public void enterGreaterThanExpression(
		@NotNull GreaterThanExpressionContext context) {

		pushComparisonExpression(context.getChild(1));
	}

	@Override
	public void enterGreaterThanOrEqualsExpression(
		@NotNull GreaterThanOrEqualsExpressionContext context) {

		pushComparisonExpression(context.getChild(1));
	}

	@Override
	public void enterLessThanExpression(
		@NotNull LessThanExpressionContext context) {

		pushComparisonExpression(context.getChild(1));
	}

	@Override
	public void enterLessThanOrEqualsExpression(
		@NotNull LessThanOrEqualsExpressionContext context) {

		pushComparisonExpression(context.getChild(1));
	}

	@Override
	public void enterLogicalConstant(@NotNull LogicalConstantContext context) {
		pushTerm(context.getText());
	}

	@Override
	public void enterLogicalVariable(
		@NotNull DDMExpressionParser.LogicalVariableContext context) {

		_variableNames.add(context.getText());

		pushTerm(context.getText());
	}

	@Override
	public void enterMinusExpression(@NotNull MinusExpressionContext context) {
		MinusExpression minusExpression = new MinusExpression();

		_expressionTokens.push(minusExpression);
	}

	@Override
	public void enterMultiplicationExpression(
		@NotNull MultiplicationExpressionContext context) {

		pushArithmeticExpression(context.getChild(1));
	}

	@Override
	public void enterNotEqualsExpression(
		@NotNull NotEqualsExpressionContext context) {

		pushComparisonExpression(context.getChild(1));
	}

	@Override
	public void enterNotExpression(@NotNull NotExpressionContext context) {
		NotExpression notExpression = new NotExpression();

		_expressionTokens.push(notExpression);
	}

	@Override
	public void enterNumericLiteral(@NotNull NumericLiteralContext context) {
		pushTerm(context.getText());
	}

	@Override
	public void enterNumericVariable(
		@NotNull DDMExpressionParser.NumericVariableContext context) {

		_variableNames.add(context.getText());

		pushTerm(context.getText());
	}

	@Override
	public void enterOrExpression(@NotNull OrExpressionContext context) {
		OrExpression orExpression = new OrExpression();

		_expressionTokens.push(orExpression);
	}

	@Override
	public void enterStringLiteral(@NotNull StringLiteralContext context) {
		pushTerm(context.getText());
	}

	@Override
	public void enterSubtractionExpression(
		@NotNull SubtractionExpressionContext context) {

		pushArithmeticExpression(context.getChild(1));
	}

	public Stack<Expression> getExpressionTokens() {
		return _expressionTokens;
	}

	public Set<String> getFunctionNames() {
		return _functionNames;
	}

	public Set<String> getVariableNames() {
		return _variableNames;
	}

	protected int getNumberOfFunctionParameters(
		FunctionParametersContext functionParametersContext) {

		if (functionParametersContext == null) {
			return 0;
		}

		int numberOfParameters = 0;

		for (ParseTree parseTree : functionParametersContext.children) {
			String text = parseTree.getText();

			if (!text.equals(StringPool.COMMA)) {
				numberOfParameters++;
			}
		}

		return numberOfParameters;
	}

	protected void pushArithmeticExpression(ParseTree operator) {
		ArithmeticExpression arithmeticExpression = new ArithmeticExpression(
			operator.getText());

		_expressionTokens.push(arithmeticExpression);
	}

	protected void pushComparisonExpression(ParseTree operator) {
		ComparisonExpression comparisonExpression = new ComparisonExpression(
			operator.getText());

		_expressionTokens.push(comparisonExpression);
	}

	protected void pushTerm(Object value) {
		Term term = new Term(value);

		_expressionTokens.push(term);
	}

	private final Stack<Expression> _expressionTokens = new Stack<>();
	private final Set<String> _functionNames = new HashSet<>();
	private final Set<String> _variableNames = new HashSet<>();

}