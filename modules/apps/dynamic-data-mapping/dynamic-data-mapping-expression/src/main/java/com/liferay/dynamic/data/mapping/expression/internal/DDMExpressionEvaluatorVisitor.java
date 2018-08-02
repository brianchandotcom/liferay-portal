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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionActionHandler;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionActionHandlerAware;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessorAware;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionObserver;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionObserverAware;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessorAware;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionBaseVisitor;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * @author Marcellus Tavares
 */
public class DDMExpressionEvaluatorVisitor
	extends DDMExpressionBaseVisitor<Object> {

	@Override
	public Object visitAdditionExpression(
		@NotNull AdditionExpressionContext context) {

		BigDecimal bigDecimal1 = visitChild(context, 0);
		BigDecimal bigDecimal2 = visitChild(context, 2);

		return bigDecimal1.add(bigDecimal2);
	}

	@Override
	public Object visitAndExpression(@NotNull AndExpressionContext context) {
		Boolean boolean1 = visitChild(context, 0);
		Boolean boolean2 = visitChild(context, 2);

		return boolean1 && boolean2;
	}

	@Override
	public Object visitBooleanParenthesis(
		@NotNull BooleanParenthesisContext context) {

		return visitChild(context, 1);
	}

	@Override
	public Object visitDivisionExpression(
		@NotNull DivisionExpressionContext context) {

		BigDecimal bigDecimal1 = visitChild(context, 0);
		BigDecimal bigDecimal2 = visitChild(context, 2);

		return bigDecimal1.divide(bigDecimal2);
	}

	@Override
	public Object visitEqualsExpression(
		@NotNull EqualsExpressionContext context) {

		Object object1 = visitChild(context, 0);
		Object object2 = visitChild(context, 2);

		return Objects.equals(object1, object2);
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

		return new BigDecimal(GetterUtil.getDouble(context.getText()));
	}

	@Override
	public Object visitFunctionCallExpression(
		@NotNull FunctionCallExpressionContext context) {

		String functionName = getFunctionName(context.functionName);

		DDMExpressionFunction ddmExpressionFunction =
			_ddmExpressionFunctions.get(functionName);

		if (ddmExpressionFunction instanceof DDMExpressionObserverAware) {
			((DDMExpressionObserverAware)ddmExpressionFunction).
				setDDMExpressionObserver(_ddmExpressionObserver);
		}

		if (ddmExpressionFunction instanceof DDMExpressionActionHandlerAware) {
			((DDMExpressionActionHandlerAware)ddmExpressionFunction).
				setDDMExpressionActionHandler(_ddmExpressionActionHandler);
		}

		if (ddmExpressionFunction instanceof
				DDMExpressionParameterAccessorAware) {

			((DDMExpressionParameterAccessorAware)ddmExpressionFunction).
				setDDMExpressionParameterAccessor(
					_ddmExpressionParameterAccessor);
		}

		if (ddmExpressionFunction instanceof DDMExpressionFieldAccessorAware) {
			((DDMExpressionFieldAccessorAware)ddmExpressionFunction).
				setDDMExpressionFieldAccessor(_ddmExpressionFieldAccessor);
		}

		Object[] params = getFunctionParameters(context.functionParameters());

		if (params.length == 0) {
			DDMExpressionFunction.Function0 function0 =
				(DDMExpressionFunction.Function0)ddmExpressionFunction;

			return function0.apply();
		}
		else if (params.length == 1) {
			DDMExpressionFunction.Function1 function1 =
				(DDMExpressionFunction.Function1)ddmExpressionFunction;

			return function1.apply(params[0]);
		}
		else if (params.length == 2) {
			DDMExpressionFunction.Function2 function2 =
				(DDMExpressionFunction.Function2)ddmExpressionFunction;

			return function2.apply(params[0], params[1]);
		}
		else if (params.length == 3) {
			DDMExpressionFunction.Function3 function3 =
				(DDMExpressionFunction.Function3)ddmExpressionFunction;

			return function3.apply(params[0], params[1], params[2]);
		}
		else if (params.length == 4) {
			DDMExpressionFunction.Function4 function4 =
				(DDMExpressionFunction.Function4)ddmExpressionFunction;

			return function4.apply(params[0], params[1], params[2], params[3]);
		}

		return null;
	}

	@Override
	public Object visitGreaterThanExpression(
		@NotNull GreaterThanExpressionContext context) {

		Comparable comparable1 = visitChild(context, 0);
		Comparable comparable2 = visitChild(context, 2);

		return comparable1.compareTo(comparable2) == 1;
	}

	@Override
	public Object visitGreaterThanOrEqualsExpression(
		@NotNull GreaterThanOrEqualsExpressionContext context) {

		Comparable comparable1 = visitChild(context, 0);
		Comparable comparable2 = visitChild(context, 2);

		return comparable1.compareTo(comparable2) >= 0;
	}

	@Override
	public Object visitIntegerLiteral(@NotNull IntegerLiteralContext context) {
		return new BigDecimal(GetterUtil.getLong(context.getText()));
	}

	@Override
	public Object visitLessThanExpression(
		@NotNull LessThanExpressionContext context) {

		Comparable comparable1 = visitChild(context, 0);
		Comparable comparable2 = visitChild(context, 2);

		return comparable1.compareTo(comparable2) == -1;
	}

	@Override
	public Object visitLessThanOrEqualsExpression(
		@NotNull LessThanOrEqualsExpressionContext context) {

		Comparable comparable1 = visitChild(context, 0);
		Comparable comparable2 = visitChild(context, 2);

		return comparable1.compareTo(comparable2) <= 0;
	}

	@Override
	public Object visitLogicalConstant(
		@NotNull LogicalConstantContext context) {

		return Boolean.parseBoolean(context.getText());
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

		BigDecimal bigDecimal1 = visitChild(context, 1);

		return bigDecimal1.multiply(new BigDecimal(-1));
	}

	@Override
	public Object visitMultiplicationExpression(
		@NotNull MultiplicationExpressionContext context) {

		BigDecimal bigDecimal1 = visitChild(context, 0);
		BigDecimal bigDecimal2 = visitChild(context, 2);

		return bigDecimal1.multiply(bigDecimal2);
	}

	@Override
	public Object visitNotEqualsExpression(
		@NotNull NotEqualsExpressionContext context) {

		Object object1 = visitChild(context, 0);
		Object object2 = visitChild(context, 2);

		return !Objects.equals(object1, object2);
	}

	@Override
	public Object visitNotExpression(@NotNull NotExpressionContext context) {
		boolean boolean1 = visitChild(context, 1);

		return !boolean1;
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
		boolean boolean1 = visitChild(context, 0);
		boolean boolean2 = visitChild(context, 2);

		return boolean1 || boolean2;
	}

	@Override
	public Object visitStringLiteral(@NotNull StringLiteralContext context) {
		return StringUtil.unquote(context.getText());
	}

	@Override
	public Object visitSubtractionExpression(
		@NotNull SubtractionExpressionContext context) {

		BigDecimal bigDecimal1 = visitChild(context, 0);
		BigDecimal bigDecimal2 = visitChild(context, 2);

		return bigDecimal1.subtract(bigDecimal2);
	}

	protected DDMExpressionEvaluatorVisitor(
		Map<String, DDMExpressionFunction> ddmExpressionFunctions,
		Map<String, Object> variables,
		DDMExpressionActionHandler ddmExpressionActionHandler,
		DDMExpressionFieldAccessor ddmExpressionFieldAccessor,
		DDMExpressionObserver ddmExpressionObserver,
		DDMExpressionParameterAccessor ddmExpressionParameterAccessor) {

		_ddmExpressionFunctions = ddmExpressionFunctions;
		_variables = variables;
		_ddmExpressionActionHandler = ddmExpressionActionHandler;
		_ddmExpressionFieldAccessor = ddmExpressionFieldAccessor;
		_ddmExpressionObserver = ddmExpressionObserver;
		_ddmExpressionParameterAccessor = ddmExpressionParameterAccessor;
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

	private final DDMExpressionActionHandler _ddmExpressionActionHandler;
	private final DDMExpressionFieldAccessor _ddmExpressionFieldAccessor;
	private final Map<String, DDMExpressionFunction> _ddmExpressionFunctions;
	private final DDMExpressionObserver _ddmExpressionObserver;
	private final DDMExpressionParameterAccessor
		_ddmExpressionParameterAccessor;
	private final Map<String, Object> _variables;

}