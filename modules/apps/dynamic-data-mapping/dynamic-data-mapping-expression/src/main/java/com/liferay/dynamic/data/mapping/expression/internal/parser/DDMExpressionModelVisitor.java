/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression.internal.parser;

import com.liferay.dynamic.data.mapping.expression.internal.parser.generated.DDMExpressionBaseVisitor;
import com.liferay.dynamic.data.mapping.expression.internal.parser.generated.DDMExpressionParser;
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

		Expression leftExpression = visitChild(context, 0);
		Expression rightExpression = visitChild(context, 2);

		return new ArithmeticExpression("+", leftExpression, rightExpression);
	}

	@Override
	public Expression visitAndExpression(
		@NotNull DDMExpressionParser.AndExpressionContext context) {

		Expression leftExpression = visitChild(context, 0);
		Expression rightExpression = visitChild(context, 2);

		return new AndExpression(leftExpression, rightExpression);
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

		Expression leftExpression = visitChild(context, 0);
		Expression rightExpression = visitChild(context, 2);

		return new ArithmeticExpression("/", leftExpression, rightExpression);
	}

	@Override
	public Expression visitEqualsExpression(
		@NotNull DDMExpressionParser.EqualsExpressionContext context) {

		Expression leftExpression = visitChild(context, 0);
		Expression rightExpression = visitChild(context, 2);

		return new ComparisonExpression("=", leftExpression, rightExpression);
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

		Expression leftExpression = visitChild(context, 0);
		Expression rightExpression = visitChild(context, 2);

		return new ComparisonExpression(">", leftExpression, rightExpression);
	}

	@Override
	public Expression visitGreaterThanOrEqualsExpression(
		@NotNull DDMExpressionParser.GreaterThanOrEqualsExpressionContext
			context) {

		Expression leftExpression = visitChild(context, 0);
		Expression rightExpression = visitChild(context, 2);

		return new ComparisonExpression(">=", leftExpression, rightExpression);
	}

	@Override
	public Expression visitIntegerLiteral(
		@NotNull DDMExpressionParser.IntegerLiteralContext context) {

		return new IntegerLiteral(context.getText());
	}

	@Override
	public Expression visitLessThanExpression(
		@NotNull DDMExpressionParser.LessThanExpressionContext context) {

		Expression leftExpression = visitChild(context, 0);
		Expression rightExpression = visitChild(context, 2);

		return new ComparisonExpression("<", leftExpression, rightExpression);
	}

	@Override
	public Expression visitLessThanOrEqualsExpression(
		@NotNull DDMExpressionParser.LessThanOrEqualsExpressionContext
			context) {

		Expression leftExpression = visitChild(context, 0);
		Expression rightExpression = visitChild(context, 2);

		return new ComparisonExpression("<=", leftExpression, rightExpression);
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

		Expression leftExpression = visitChild(context, 0);
		Expression rightExpression = visitChild(context, 2);

		return new ArithmeticExpression("*", leftExpression, rightExpression);
	}

	@Override
	public Expression visitNotEqualsExpression(
		@NotNull DDMExpressionParser.NotEqualsExpressionContext context) {

		Expression leftExpression = visitChild(context, 0);
		Expression rightExpression = visitChild(context, 2);

		return new ComparisonExpression("!=", leftExpression, rightExpression);
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

		Expression leftExpression = visitChild(context, 0);
		Expression rightExpression = visitChild(context, 2);

		return new OrExpression(leftExpression, rightExpression);
	}

	@Override
	public Expression visitStringLiteral(
		@NotNull DDMExpressionParser.StringLiteralContext context) {

		return new StringLiteral(StringUtil.unquote(context.getText()));
	}

	@Override
	public Expression visitSubtractionExpression(
		@NotNull DDMExpressionParser.SubtractionExpressionContext context) {

		Expression leftExpression = visitChild(context, 0);
		Expression rightExpression = visitChild(context, 2);

		return new ArithmeticExpression("-", leftExpression, rightExpression);
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
			Expression parameterExpression = visitChild(context, i);

			parameters.add(parameterExpression);
		}

		return parameters;
	}

	protected <T> T visitChild(
		ParserRuleContext parserRuleContext, int childIndex) {

		ParseTree parseTree = parserRuleContext.getChild(childIndex);

		return (T)parseTree.accept(this);
	}

}