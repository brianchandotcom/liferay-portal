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

import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionBaseVisitor;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser;
import com.liferay.dynamic.data.mapping.expression.model.AndExpression;
import com.liferay.dynamic.data.mapping.expression.model.ArithmeticExpression;
import com.liferay.dynamic.data.mapping.expression.model.ArrayExpression;
import com.liferay.dynamic.data.mapping.expression.model.ComparisonExpression;
import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.expression.model.FloatingPointLiteral;
import com.liferay.dynamic.data.mapping.expression.model.FunctionCallExpression;
import com.liferay.dynamic.data.mapping.expression.model.IntegerLiteral;
import com.liferay.dynamic.data.mapping.expression.model.MinusExpression;
import com.liferay.dynamic.data.mapping.expression.model.NotExpression;
import com.liferay.dynamic.data.mapping.expression.model.OrExpression;
import com.liferay.dynamic.data.mapping.expression.model.Parenthesis;
import com.liferay.dynamic.data.mapping.expression.model.StringLiteral;
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
		@NotNull DDMExpressionParser.AdditionExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ArithmeticExpression("+", l, r);
	}

	@Override
	public Expression visitAndExpression(
		@NotNull DDMExpressionParser.AndExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new AndExpression(l, r);
	}

	@Override
	public Expression visitArray(
		@NotNull DDMExpressionParser.ArrayContext context) {

		return new ArrayExpression(context.getText());
	}

	@Override
	public Expression visitBooleanParenthesis(
		@NotNull DDMExpressionParser.BooleanParenthesisContext context) {

		return visitChild(context, 1);
	}

	@Override
	public Expression visitDivisionExpression(
		@NotNull DDMExpressionParser.DivisionExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ArithmeticExpression("/", l, r);
	}

	@Override
	public Expression visitEqualsExpression(
		@NotNull DDMExpressionParser.EqualsExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ComparisonExpression("=", l, r);
	}

	@Override
	public Expression visitExpression(
		@NotNull DDMExpressionParser.ExpressionContext context) {

		DDMExpressionParser.LogicalOrExpressionContext
			logicalOrExpressionContext = context.logicalOrExpression();

		return logicalOrExpressionContext.accept(this);
	}

	@Override
	public Expression visitFloatingPointLiteral(
		@NotNull DDMExpressionParser.FloatingPointLiteralContext context) {

		return new FloatingPointLiteral(context.getText());
	}

	@Override
	public Expression visitFunctionCallExpression(
		@NotNull DDMExpressionParser.FunctionCallExpressionContext context) {

		String functionName = getFunctionName(context.functionName);

		List<Expression> parameters = getFunctionParameters(
			context.functionParameters());

		return new FunctionCallExpression(functionName, parameters);
	}

	@Override
	public Expression visitGreaterThanExpression(
		@NotNull DDMExpressionParser.GreaterThanExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ComparisonExpression(">", l, r);
	}

	@Override
	public Expression visitGreaterThanOrEqualsExpression(
		@NotNull
			DDMExpressionParser.GreaterThanOrEqualsExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ComparisonExpression(">=", l, r);
	}

	@Override
	public Expression visitIntegerLiteral(
		@NotNull DDMExpressionParser.IntegerLiteralContext context) {

		return new IntegerLiteral(context.getText());
	}

	@Override
	public Expression visitLessThanExpression(
		@NotNull DDMExpressionParser.LessThanExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ComparisonExpression("<", l, r);
	}

	@Override
	public Expression visitLessThanOrEqualsExpression(
		@NotNull
			DDMExpressionParser.LessThanOrEqualsExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ComparisonExpression("<=", l, r);
	}

	@Override
	public Expression visitLogicalConstant(
		@NotNull DDMExpressionParser.LogicalConstantContext context) {

		return new Term(context.getText());
	}

	@Override
	public Expression visitLogicalVariable(
		@NotNull DDMExpressionParser.LogicalVariableContext context) {

		return new Term(context.getText());
	}

	@Override
	public Expression visitMinusExpression(
		@NotNull DDMExpressionParser.MinusExpressionContext context) {

		Expression expression = visitChild(context, 1);

		return new MinusExpression(expression);
	}

	@Override
	public Expression visitMultiplicationExpression(
		@NotNull DDMExpressionParser.MultiplicationExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ArithmeticExpression("*", l, r);
	}

	@Override
	public Expression visitNotEqualsExpression(
		@NotNull DDMExpressionParser.NotEqualsExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ComparisonExpression("!=", l, r);
	}

	@Override
	public Expression visitNotExpression(
		@NotNull DDMExpressionParser.NotExpressionContext context) {

		Expression expression = visitChild(context, 1);

		if (expression instanceof Parenthesis) {
			Parenthesis parenthesis = (Parenthesis)expression;

			expression = parenthesis.getOperandExpression();
		}

		return new NotExpression(expression);
	}

	@Override
	public Expression visitNumericParenthesis(
		@NotNull DDMExpressionParser.NumericParenthesisContext context) {

		return new Parenthesis(visitChild(context, 1));
	}

	@Override
	public Expression visitNumericVariable(
		@NotNull DDMExpressionParser.NumericVariableContext context) {

		return new Term(context.getText());
	}

	@Override
	public Expression visitOrExpression(
		@NotNull DDMExpressionParser.OrExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new OrExpression(l, r);
	}

	@Override
	public Expression visitStringLiteral(
		@NotNull DDMExpressionParser.StringLiteralContext context) {

		return new StringLiteral(StringUtil.unquote(context.getText()));
	}

	@Override
	public Expression visitSubtractionExpression(
		@NotNull DDMExpressionParser.SubtractionExpressionContext context) {

		Expression l = visitChild(context, 0);
		Expression r = visitChild(context, 2);

		return new ArithmeticExpression("-", l, r);
	}

	protected String getFunctionName(Token functionNameToken) {
		return functionNameToken.getText();
	}

	protected List<Expression> getFunctionParameters(
		DDMExpressionParser.FunctionParametersContext context) {

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