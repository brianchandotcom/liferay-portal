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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionBaseVisitor;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * @author Marcellus Tavares
 */
public class DDMExpressionEvaluatorVisitor
	extends DDMExpressionBaseVisitor<Object> {

	public void addFunctions(
		Map<String, DDMExpressionFunction> ddmExpressionFunctions) {

		_functions.putAll(ddmExpressionFunctions);
	}

	public void addVariable(String name, Object value) {
		_variables.put(name, value);
	}

	@Override
	public Object visitAdditionExpression(
		@NotNull AdditionExpressionContext context) {

		Number l = visitChild(context, 0);
		Number r = visitChild(context, 2);

		return l.doubleValue() + r.doubleValue();
	}

	@Override
	public Object visitAndExpression(@NotNull AndExpressionContext context) {
		boolean l = visitChild(context, 0);
		boolean r = visitChild(context, 2);

		return l && r;
	}

	@Override
	public Object visitBooleanParenthesis(
		@NotNull BooleanParenthesisContext context) {

		return visitChild(context, 1);
	}

	@Override
	public Object visitDivisionExpression(
		@NotNull DivisionExpressionContext context) {

		Number l = visitChild(context, 0);
		Number r = visitChild(context, 2);

		return l.doubleValue() / r.doubleValue();
	}

	@Override
	public Object visitEqualsExpression(
		@NotNull EqualsExpressionContext context) {

		Object l = visitChild(context, 0);
		Object r = visitChild(context, 2);

		return l.equals(r);
	}

	@Override
	public Object visitExpression(@NotNull ExpressionContext context) {
		DDMExpressionParser.LogicalOrExpressionContext
			logicalOrExpressionContext = context.logicalOrExpression();

		return logicalOrExpressionContext.accept(this);
	}

	@Override
	public Object visitFloatingPointLiteral(
		@NotNull FloatingPointLiteralContext context) {

		return Double.parseDouble(context.getText());
	}

	@Override
	public Object visitFunctionCallExpression(
		@NotNull FunctionCallExpressionContext context) {

		String functionName = getFunctionName(context.functionName);

		DDMExpressionFunction ddmExpressionFunction = _functions.get(
			functionName);

		if (ddmExpressionFunction == null) {
			throw new IllegalStateException(
				String.format("Function \"%s\" not defined", functionName));
		}

		Object[] params = getFunctionParameters(context.functionParameters());

		return ddmExpressionFunction.evaluate(params);
	}

	@Override
	public Object visitGreaterThanExpression(
		@NotNull GreaterThanExpressionContext context) {

		Number l = visitChild(context, 0);
		Number r = visitChild(context, 2);

		return l.doubleValue() > r.doubleValue();
	}

	@Override
	public Object visitGreaterThanOrEqualsExpression(
		@NotNull GreaterThanOrEqualsExpressionContext context) {

		Number l = visitChild(context, 0);
		Number r = visitChild(context, 2);

		return l.doubleValue() >= r.doubleValue();
	}

	@Override
	public Object visitIntegerLiteral(@NotNull IntegerLiteralContext context) {
		Number number = Long.parseLong(context.getText());

		return number.doubleValue();
	}

	@Override
	public Object visitLessThanExpression(
		@NotNull LessThanExpressionContext context) {

		Number l = visitChild(context, 0);
		Number r = visitChild(context, 2);

		return l.doubleValue() < r.doubleValue();
	}

	@Override
	public Object visitLessThanOrEqualsExpression(
		@NotNull LessThanOrEqualsExpressionContext context) {

		Number l = visitChild(context, 0);
		Number r = visitChild(context, 2);

		return l.doubleValue() <= r.doubleValue();
	}

	@Override
	public Object visitLogicalConstant(
		@NotNull LogicalConstantContext context) {

		String boleanString = StringUtil.toLowerCase(context.getText());

		return Boolean.parseBoolean(boleanString);
	}

	@Override
	public Object visitLogicalVariable(
		@NotNull LogicalVariableContext context) {

		String variable = context.getText();

		Object variableValue = _variables.get(variable);

		if (variableValue == null) {
			throw new IllegalStateException(
				String.format("Variable \"%s\" not defined", variable));
		}

		return variableValue;
	}

	@Override
	public Object visitMinusExpression(
		@NotNull MinusExpressionContext context) {

		Number number = visitChild(context, 1);

		return -(number.doubleValue());
	}

	@Override
	public Object visitMultiplicationExpression(
		@NotNull MultiplicationExpressionContext context) {

		Number l = visitChild(context, 0);
		Number r = visitChild(context, 2);

		return l.doubleValue() * r.doubleValue();
	}

	@Override
	public Object visitNotEqualsExpression(
		@NotNull NotEqualsExpressionContext context) {

		Object l = visitChild(context, 0);
		Object r = visitChild(context, 2);

		return !l.equals(r);
	}

	@Override
	public Object visitNotExpression(@NotNull NotExpressionContext context) {
		boolean b = visitChild(context, 1);

		return !b;
	}

	@Override
	public Object visitNumericParenthesis(
		@NotNull NumericParenthesisContext context) {

		return visitChild(context, 1);
	}

	@Override
	public Object visitNumericVariable(
		@NotNull NumericVariableContext context) {

		String variable = context.getText();

		Object variableValue = _variables.get(variable);

		if (variableValue == null) {
			throw new IllegalStateException(
				String.format("variable %s not defined", variable));
		}

		return variableValue;
	}

	@Override
	public Object visitOrExpression(@NotNull OrExpressionContext context) {
		boolean l = visitChild(context, 0);
		boolean r = visitChild(context, 2);

		return l || r;
	}

	@Override
	public Object visitStringLiteral(@NotNull StringLiteralContext context) {
		return StringUtil.unquote(context.getText());
	}

	@Override
	public Object visitSubtractionExpression(
		@NotNull SubtractionExpressionContext context) {

		Number l = visitChild(context, 0);
		Number r = visitChild(context, 2);

		return l.doubleValue() - r.doubleValue();
	}

	protected String getFunctionName(Token functionNameToken) {
		return functionNameToken.getText();
	}

	protected Object[] getFunctionParameters(
		FunctionParametersContext context) {

		if (context == null) {
			return new Object[0];
		}

		List parameters = new ArrayList<>();

		for (int i = 0; i < context.getChildCount(); i += 2) {
			Object parameter = visitChild(context, i);

			parameters.add(parameter);
		}

		return parameters.toArray(new Object[parameters.size()]);
	}

	protected <T> T visitChild(
		ParserRuleContext parserRuleContext, int childIndex) {

		ParseTree parseTree = parserRuleContext.getChild(childIndex);

		return (T)parseTree.accept(this);
	}

	private final Map<String, DDMExpressionFunction> _functions =
		new HashMap<>();
	private final Map<String, Object> _variables = new HashMap<>();

}