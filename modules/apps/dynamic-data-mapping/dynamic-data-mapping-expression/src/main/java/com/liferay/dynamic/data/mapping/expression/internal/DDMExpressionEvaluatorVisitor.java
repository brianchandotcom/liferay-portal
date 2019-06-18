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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionActionHandler;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionActionHandlerAware;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessorAware;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionObserver;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionObserverAware;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessorAware;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionBaseVisitor;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.AdditionExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.BooleanParenthesisContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.DivisionExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.EqualsExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.FloatingPointLiteralContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.FunctionCallExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.FunctionParametersContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.GreaterThanExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.GreaterThanOrEqualsExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.LessThanExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.LessThanOrEqualsExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.LogicalConstantContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.LogicalVariableContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.MinusExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.MultiplicationExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.NotEqualsExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.NumericParenthesisContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.NumericVariableContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.SubtractionExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.ToFloatingPointArrayContext;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

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
	public Object visitAndExpression(
		@NotNull DDMExpressionParser.AndExpressionContext context) {

		Boolean boolean1 = visitChild(context, 0);

		if (!boolean1) {
			return Boolean.FALSE;
		}

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

		if (bigDecimal2.compareTo(BigDecimal.ZERO) == 0) {
			return "NaN";
		}

		return bigDecimal1.divide(bigDecimal2, 2, RoundingMode.FLOOR);
	}

	@Override
	public Object visitEqualsExpression(
		@NotNull EqualsExpressionContext context) {

		Object object1 = visitChild(context, 0);
		Object object2 = visitChild(context, 2);

		return Objects.equals(object1, object2);
	}

	@Override
	public Object visitExpression(
		@NotNull DDMExpressionParser.ExpressionContext context) {

		DDMExpressionParser.LogicalOrExpressionContext
			logicalOrExpressionContext = context.logicalOrExpression();

		return logicalOrExpressionContext.accept(this);
	}

	@Override
	public Object visitFloatingPointLiteral(
		@NotNull FloatingPointLiteralContext context) {

		return new BigDecimal(context.getText());
	}

	@Override
	public Object visitFunctionCallExpression(
		@NotNull FunctionCallExpressionContext context) {

		String functionName = getFunctionName(context.functionName);

		DDMExpressionFunctionFactory ddmExpressionFunctionFactory =
			_ddmExpressionFunctionFactories.get(functionName);

		DDMExpressionFunction ddmExpressionFunction =
			ddmExpressionFunctionFactory.create();

		if (ddmExpressionFunction instanceof DDMExpressionObserverAware) {
			DDMExpressionObserverAware ddmExpressionObserverAware =
				(DDMExpressionObserverAware)ddmExpressionFunction;

			ddmExpressionObserverAware.setDDMExpressionObserver(
				_ddmExpressionObserver);
		}

		if (ddmExpressionFunction instanceof DDMExpressionActionHandlerAware) {
			DDMExpressionActionHandlerAware ddmExpressionActionHandlerAware =
				(DDMExpressionActionHandlerAware)ddmExpressionFunction;

			ddmExpressionActionHandlerAware.setDDMExpressionActionHandler(
				_ddmExpressionActionHandler);
		}

		if (ddmExpressionFunction instanceof
				DDMExpressionParameterAccessorAware) {

			DDMExpressionParameterAccessorAware
				ddmExpressionParameterAccessorAware =
					(DDMExpressionParameterAccessorAware)ddmExpressionFunction;

			ddmExpressionParameterAccessorAware.
				setDDMExpressionParameterAccessor(
					_ddmExpressionParameterAccessor);
		}

		if (ddmExpressionFunction instanceof DDMExpressionFieldAccessorAware) {
			DDMExpressionFieldAccessorAware ddmExpressionFieldAccessorAware =
				(DDMExpressionFieldAccessorAware)ddmExpressionFunction;

			ddmExpressionFieldAccessorAware.setDDMExpressionFieldAccessor(
				_ddmExpressionFieldAccessor);
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

		BigDecimal bigDecimal1 = getBigDecimal(visitChild(context, 0));
		BigDecimal bigDecimal2 = getBigDecimal(visitChild(context, 2));

		return bigDecimal1.compareTo(bigDecimal2) == 1;
	}

	@Override
	public Object visitGreaterThanOrEqualsExpression(
		@NotNull GreaterThanOrEqualsExpressionContext context) {

		BigDecimal bigDecimal1 = getBigDecimal(visitChild(context, 0));
		BigDecimal bigDecimal2 = getBigDecimal(visitChild(context, 2));

		return bigDecimal1.compareTo(bigDecimal2) >= 0;
	}

	@Override
	public Object visitIntegerLiteral(
		@NotNull DDMExpressionParser.IntegerLiteralContext context) {

		return new BigDecimal(context.getText());
	}

	@Override
	public Object visitLessThanExpression(
		@NotNull LessThanExpressionContext context) {

		BigDecimal bigDecimal1 = getBigDecimal(visitChild(context, 0));
		BigDecimal bigDecimal2 = getBigDecimal(visitChild(context, 2));

		return bigDecimal1.compareTo(bigDecimal2) == -1;
	}

	@Override
	public Object visitLessThanOrEqualsExpression(
		@NotNull LessThanOrEqualsExpressionContext context) {

		BigDecimal bigDecimal1 = getBigDecimal(visitChild(context, 0));
		BigDecimal bigDecimal2 = getBigDecimal(visitChild(context, 2));

		return bigDecimal1.compareTo(bigDecimal2) <= 0;
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

		if ((variableValue == null) && (_variables.size() > 1)) {
			for (Map.Entry<String, Object> entry : _variables.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				if (key.startsWith(variable) && (value != null)) {
					variableValue = value;
				}
			}
		}

		if ((variableValue == null) &&
			_ddmExpressionFieldAccessor.isField(variable)) {

			GetFieldPropertyRequest.Builder builder =
				GetFieldPropertyRequest.Builder.newBuilder(variable, "value");

			GetFieldPropertyResponse getFieldPropertyResponse =
				_ddmExpressionFieldAccessor.getFieldProperty(builder.build());

			variableValue = getFieldPropertyResponse.getValue();
		}

		if (variableValue == null) {
			throw new IllegalStateException(
				String.format("Variable \"%s\" not defined", variable));
		}

		return variableValue;
	}

	@Override
	public Object visitMinusExpression(
		@NotNull MinusExpressionContext context) {

		BigDecimal bigDecimal1 = getBigDecimal(visitChild(context, 1));

		return bigDecimal1.multiply(new BigDecimal(-1));
	}

	@Override
	public Object visitMultiplicationExpression(
		@NotNull MultiplicationExpressionContext context) {

		BigDecimal bigDecimal1 = getBigDecimal(visitChild(context, 0));
		BigDecimal bigDecimal2 = getBigDecimal(visitChild(context, 2));

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
	public Object visitNotExpression(
		@NotNull DDMExpressionParser.NotExpressionContext context) {

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

		if ((variableValue == null) &&
			_ddmExpressionFieldAccessor.isField(variable)) {

			GetFieldPropertyRequest.Builder builder =
				GetFieldPropertyRequest.Builder.newBuilder(variable, "value");

			GetFieldPropertyResponse getFieldPropertyResponse =
				_ddmExpressionFieldAccessor.getFieldProperty(builder.build());

			variableValue = getFieldPropertyResponse.getValue();
		}

		if (variableValue == null) {
			throw new IllegalStateException(
				String.format("variable %s not defined", variable));
		}

		return variableValue;
	}

	@Override
	public Object visitOrExpression(
		@NotNull DDMExpressionParser.OrExpressionContext context) {

		boolean boolean1 = visitChild(context, 0);

		if (boolean1) {
			return Boolean.TRUE;
		}

		boolean boolean2 = visitChild(context, 2);

		return boolean1 || boolean2;
	}

	@Override
	public Object visitStringLiteral(
		@NotNull DDMExpressionParser.StringLiteralContext context) {

		return StringUtil.unquote(context.getText());
	}

	@Override
	public Object visitSubtractionExpression(
		@NotNull SubtractionExpressionContext context) {

		BigDecimal bigDecimal1 = getBigDecimal(visitChild(context, 0));
		BigDecimal bigDecimal2 = getBigDecimal(visitChild(context, 2));

		return bigDecimal1.subtract(bigDecimal2);
	}

	@Override
	public Object visitToFloatingPointArray(
		ToFloatingPointArrayContext context) {

		List<TerminalNode> floatingPointLiteralTerminalNodes =
			context.FloatingPointLiteral();

		Stream<TerminalNode> stream =
			floatingPointLiteralTerminalNodes.stream();

		return stream.map(
			floatingPoint -> new BigDecimal(floatingPoint.getText())
		).toArray(
			BigDecimal[]::new
		);
	}

	@Override
	public Object visitToIntegerArray(
		DDMExpressionParser.ToIntegerArrayContext context) {

		List<TerminalNode> integerLiteralTerminalNodes =
			context.IntegerLiteral();

		Stream<TerminalNode> stream = integerLiteralTerminalNodes.stream();

		return stream.map(
			integerLiteral -> new BigDecimal(integerLiteral.getText())
		).toArray(
			BigDecimal[]::new
		);
	}

	@Override
	public Object visitToStringArray(
		DDMExpressionParser.ToStringArrayContext context) {

		List<TerminalNode> stringTerminalNodes = context.STRING();

		Stream<TerminalNode> stream = stringTerminalNodes.stream();

		return stream.map(
			floatingPoint -> StringUtil.unquote(floatingPoint.getText())
		).toArray(
			String[]::new
		);
	}

	protected DDMExpressionEvaluatorVisitor(
		Map<String, DDMExpressionFunctionFactory>
			ddmExpressionFunctionFactories,
		Map<String, Object> variables,
		DDMExpressionActionHandler ddmExpressionActionHandler,
		DDMExpressionFieldAccessor ddmExpressionFieldAccessor,
		DDMExpressionObserver ddmExpressionObserver,
		DDMExpressionParameterAccessor ddmExpressionParameterAccessor) {

		_ddmExpressionFunctionFactories = ddmExpressionFunctionFactories;
		_variables = variables;
		_ddmExpressionActionHandler = ddmExpressionActionHandler;
		_ddmExpressionFieldAccessor = ddmExpressionFieldAccessor;
		_ddmExpressionObserver = ddmExpressionObserver;
		_ddmExpressionParameterAccessor = ddmExpressionParameterAccessor;
	}

	protected BigDecimal getBigDecimal(Comparable<?> comparable) {
		if (comparable instanceof BigDecimal) {
			return (BigDecimal)comparable;
		}

		return new BigDecimal(comparable.toString());
	}

	protected String getFunctionName(Token functionNameToken) {
		return functionNameToken.getText();
	}

	protected Object[] getFunctionParameters(
		FunctionParametersContext context) {

		if (context == null) {
			return new Object[0];
		}

		List<Object> parameters = new ArrayList<>();

		for (int i = 0; i < context.getChildCount(); i += 2) {
			Object parameter = visitChild(context, i);

			parameters.add(parameter);
		}

		return parameters.toArray(new Object[0]);
	}

	protected <T> T visitChild(
		ParserRuleContext parserRuleContext, int childIndex) {

		ParseTree parseTree = parserRuleContext.getChild(childIndex);

		return (T)parseTree.accept(this);
	}

	private final DDMExpressionActionHandler _ddmExpressionActionHandler;
	private final DDMExpressionFieldAccessor _ddmExpressionFieldAccessor;
	private final Map<String, DDMExpressionFunctionFactory>
		_ddmExpressionFunctionFactories;
	private final DDMExpressionObserver _ddmExpressionObserver;
	private final DDMExpressionParameterAccessor
		_ddmExpressionParameterAccessor;
	private final Map<String, Object> _variables;

}