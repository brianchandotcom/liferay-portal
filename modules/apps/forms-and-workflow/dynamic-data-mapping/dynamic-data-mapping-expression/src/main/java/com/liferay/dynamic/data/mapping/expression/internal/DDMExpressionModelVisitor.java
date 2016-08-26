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

import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.AdditionExpressionContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.AndExpressionContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.BooleanParenthesisContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.DivisionExpressionContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.EqualsExpressionContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.ExpressionContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.FloatingPointLiteralContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.FunctionCallExpressionContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.FunctionParametersContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.GreaterThanExpressionContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.GreaterThanOrEqualsExpressionContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.IntegerLiteralContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.LessThanExpressionContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.LessThanOrEqualsExpressionContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.LogicalConstantContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.LogicalVariableContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.MinusExpressionContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.MultiplicationExpressionContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.NotEqualsExpressionContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.NotExpressionContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.NumericParenthesisContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.NumericVariableContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.OrExpressionContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.StringLiteralContext;
import static com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.SubtractionExpressionContext;

import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionBaseVisitor;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser;
import com.liferay.dynamic.data.mapping.expression.model.AndExpression;
import com.liferay.dynamic.data.mapping.expression.model.ArithmeticExpression;
import com.liferay.dynamic.data.mapping.expression.model.ComparisonExpression;
import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.expression.model.FunctionCallExpression;
import com.liferay.dynamic.data.mapping.expression.model.MinusExpression;
import com.liferay.dynamic.data.mapping.expression.model.NotExpression;
import com.liferay.dynamic.data.mapping.expression.model.OrExpression;
import com.liferay.dynamic.data.mapping.expression.model.Term;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * @author Marcellus Tavares
 */
public class DDMExpressionModelVisitor
	extends DDMExpressionBaseVisitor<Expression> {

	@Override
	public Expression visitAdditionExpression(
		@NotNull AdditionExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ArithmeticExpression("+", l, r);
	}

	@Override
	public Expression visitAndExpression(
		@NotNull AndExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new AndExpression(l, r);
	}

	@Override
	public Expression visitBooleanParenthesis(
		@NotNull BooleanParenthesisContext context) {

		return visitChild(context, 1);
	}

	@Override
	public Expression visitDivisionExpression(
		@NotNull DivisionExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ArithmeticExpression("/", l, r);
	}

	@Override
	public Expression visitEqualsExpression(
		@NotNull EqualsExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ComparisonExpression("=", l, r);
	}

	@Override
	public Expression visitExpression(@NotNull ExpressionContext context) {
		DDMExpressionParser.LogicalOrExpressionContext
			logicalOrExpressionContext = context.logicalOrExpression();

		return logicalOrExpressionContext.accept(this);
	}

	@Override
	public Expression visitFloatingPointLiteral(
		@NotNull FloatingPointLiteralContext context) {

		return new Term(context.getText());
	}

	@Override
	public Expression visitFunctionCallExpression(
		@NotNull FunctionCallExpressionContext context) {

		String functionName = getFunctionName(context.functionName);

		List<Expression> parameters = getFunctionParameters(
			context.functionParameters());

		return new FunctionCallExpression(functionName, parameters);
	}

	@Override
	public Expression visitGreaterThanExpression(
		@NotNull GreaterThanExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ComparisonExpression(">", l, r);
	}

	@Override
	public Expression visitGreaterThanOrEqualsExpression(
		@NotNull GreaterThanOrEqualsExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ComparisonExpression(">=", l, r);
	}

	@Override
	public Expression visitIntegerLiteral(
		@NotNull IntegerLiteralContext context) {

		return new Term(context.getText());
	}

	@Override
	public Expression visitLessThanExpression(
		@NotNull LessThanExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ComparisonExpression("<", l, r);
	}

	@Override
	public Expression visitLessThanOrEqualsExpression(
		@NotNull LessThanOrEqualsExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ComparisonExpression("<=", l, r);
	}

	@Override
	public Expression visitLogicalConstant(
		@NotNull LogicalConstantContext context) {

		return new Term(context.getText());
	}

	@Override
	public Expression visitLogicalVariable(
		@NotNull LogicalVariableContext context) {

		return new Term(context.getText());
	}

	@Override
	public Expression visitMinusExpression(
		@NotNull MinusExpressionContext context) {

		Expression expression = visitChild(context, 1);

		return new MinusExpression(expression);
	}

	@Override
	public Expression visitMultiplicationExpression(
		@NotNull MultiplicationExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ArithmeticExpression("*", l, r);
	}

	@Override
	public Expression visitNotEqualsExpression(
		@NotNull NotEqualsExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ComparisonExpression("!=", l, r);
	}

	@Override
	public Expression visitNotExpression(
		@NotNull NotExpressionContext context) {

		Expression expression = visitChild(context, 1);

		return new NotExpression(expression);
	}

	@Override
	public Expression visitNumericParenthesis(
		@NotNull NumericParenthesisContext context) {

		return visitChild(context, 1);
	}

	@Override
	public Expression visitNumericVariable(
		@NotNull NumericVariableContext context) {

		return new Term(context.getText());
	}

	@Override
	public Expression visitOrExpression(@NotNull OrExpressionContext context) {
		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new OrExpression(l, r);
	}

	@Override
	public Expression visitStringLiteral(
		@NotNull StringLiteralContext context) {

		return new Term(StringUtil.unquote(context.getText()));
	}

	@Override
	public Expression visitSubtractionExpression(
		@NotNull SubtractionExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ArithmeticExpression("-", l, r);
	}

	protected String getFunctionName(Token functionNameToken) {
		return functionNameToken.getText();
	}

	protected List<Expression> getFunctionParameters(
		FunctionParametersContext context) {

		if (context == null) {
			return Collections.emptyList();
		}

		List<Expression> parameters = new ArrayList<>();

		for (int i = 0; i < context.getChildCount(); i += 2) {
			Expression parameter = visitChild(context, i);

			parameters.add(parameter);
		}

		return parameters;
	}

	protected <T> T visitChild(
		ParserRuleContext parserRuleContext, int childIndex) {

		ParseTree parseTree = parserRuleContext.getChild(childIndex);

		return (T)parseTree.accept(this);
	}

}