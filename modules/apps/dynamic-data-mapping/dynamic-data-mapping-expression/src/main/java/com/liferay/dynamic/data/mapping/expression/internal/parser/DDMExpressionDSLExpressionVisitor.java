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

package com.liferay.dynamic.data.mapping.expression.internal.parser;

import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionBaseVisitor;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.AdditionExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.BooleanParenthesisContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.DivisionExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.EqualsExpressionContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.FloatingPointLiteralContext;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser.FunctionCallExpressionContext;
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
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * @author Guilherme Camacho
 */
public class DDMExpressionDSLExpressionVisitor
	extends DDMExpressionBaseVisitor<Object> {

	@Override
	public Object visitAdditionExpression(
		@NotNull AdditionExpressionContext context) {

		Expression<Number> expression1 = (Expression<Number>)_getExpression(
			visitChild(context, 0));
		Expression<Number> expression2 = (Expression<Number>)_getExpression(
			visitChild(context, 2));

		return DSLFunctionFactoryUtil.add(expression1, expression2);
	}

	@Override
	public Object visitAndExpression(
		@NotNull DDMExpressionParser.AndExpressionContext context) {

		throw new UnsupportedOperationException(
			"Unsupported method visitAndExpression with and expression " +
				context.getText());
	}

	@Override
	public Object visitBooleanParenthesis(
		@NotNull BooleanParenthesisContext context) {

		return visitChild(context, 1);
	}

	@Override
	public Object visitDivisionExpression(
		@NotNull DivisionExpressionContext context) {

		Expression<Number> expression1 = (Expression<Number>)_getExpression(
			visitChild(context, 0));
		Expression<Number> expression2 = (Expression<Number>)_getExpression(
			visitChild(context, 2));

		return DSLFunctionFactoryUtil.divide(expression1, expression2);
	}

	@Override
	public Object visitEqualsExpression(
		@NotNull EqualsExpressionContext context) {

		throw new UnsupportedOperationException(
			"Unsupported method visitEqualsExpression with equals expression " +
				context.getText());
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

		throw new UnsupportedOperationException(
			"Unsupported method visitFunctionCallExpression with function " +
				"call expression " + context.getText());
	}

	@Override
	public Object visitGreaterThanExpression(
		@NotNull GreaterThanExpressionContext context) {

		throw new UnsupportedOperationException(
			"Unsupported method visitGreaterThanExpression with greater than " +
				"expression " + context.getText());
	}

	@Override
	public Object visitGreaterThanOrEqualsExpression(
		@NotNull GreaterThanOrEqualsExpressionContext context) {

		throw new UnsupportedOperationException(
			"Unsupported method visitGreaterThanOrEqualsExpression with " +
				"greater than or equals expression " + context.getText());
	}

	@Override
	public Object visitIntegerLiteral(
		@NotNull DDMExpressionParser.IntegerLiteralContext context) {

		return new BigDecimal(context.getText());
	}

	@Override
	public Object visitLessThanExpression(
		@NotNull LessThanExpressionContext context) {

		throw new UnsupportedOperationException(
			"Unsupported method visitLessThanExpression with less than " +
				"expression " + context.getText());
	}

	@Override
	public Object visitLessThanOrEqualsExpression(
		@NotNull LessThanOrEqualsExpressionContext context) {

		throw new UnsupportedOperationException(
			"Unsupported method visitLessThanOrEqualsExpression with less " +
				"than or equals expression " + context.getText());
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

		if (variableValue == null) {
			throw new IllegalStateException(
				String.format("Variable \"%s\" not defined", variable));
		}

		return variableValue;
	}

	@Override
	public Object visitMinusExpression(
		@NotNull MinusExpressionContext context) {

		throw new UnsupportedOperationException(
			"Unsupported method visitMinusExpression with minus expression " +
				context.getText());
	}

	@Override
	public Object visitMultiplicationExpression(
		@NotNull MultiplicationExpressionContext context) {

		Expression<Number> expression1 = (Expression<Number>)_getExpression(
			visitChild(context, 0));
		Expression<Number> expression2 = (Expression<Number>)_getExpression(
			visitChild(context, 2));

		return DSLFunctionFactoryUtil.multiply(expression1, expression2);
	}

	@Override
	public Object visitNotEqualsExpression(
		@NotNull NotEqualsExpressionContext context) {

		throw new UnsupportedOperationException(
			"Unsupported method visitNotEqualsExpression with not equals " +
				"expression " + context.getText());
	}

	@Override
	public Object visitNotExpression(
		@NotNull DDMExpressionParser.NotExpressionContext context) {

		throw new UnsupportedOperationException(
			"Unsupported method visitNotExpression with not expression " +
				context.getText());
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
	public Object visitOrExpression(
		@NotNull DDMExpressionParser.OrExpressionContext context) {

		throw new UnsupportedOperationException(
			"Unsupported method visitOrExpression with or expression " +
				context.getText());
	}

	@Override
	public Object visitStringLiteral(
		@NotNull DDMExpressionParser.StringLiteralContext context) {

		return StringUtil.unquote(context.getText());
	}

	@Override
	public Object visitSubtractionExpression(
		@NotNull SubtractionExpressionContext context) {

		Expression<Number> expression1 = (Expression<Number>)_getExpression(
			visitChild(context, 0));
		Expression<Number> expression2 = (Expression<Number>)_getExpression(
			visitChild(context, 2));

		return DSLFunctionFactoryUtil.subtract(expression1, expression2);
	}

	@Override
	public Object visitToFloatingPointArray(
		ToFloatingPointArrayContext context) {

		return _getBigDecimalArray(context.FloatingPointLiteral());
	}

	@Override
	public Object visitToIntegerArray(
		DDMExpressionParser.ToIntegerArrayContext context) {

		return _getBigDecimalArray(context.IntegerLiteral());
	}

	@Override
	public Object visitToStringArray(
		DDMExpressionParser.ToStringArrayContext context) {

		List<TerminalNode> stringTerminalNodes = context.STRING();

		String[] values = new String[stringTerminalNodes.size()];

		for (int i = 0; i < stringTerminalNodes.size(); i++) {
			TerminalNode terminalNode = stringTerminalNodes.get(i);

			values[i] = StringUtil.unquote(terminalNode.getText());
		}

		return values;
	}

	public DDMExpressionDSLExpressionVisitor(Map<String, Object> variables) {
		_variables = variables;
	}

	protected <T> T visitChild(
		ParserRuleContext parserRuleContext, int childIndex) {

		ParseTree parseTree = parserRuleContext.getChild(childIndex);

		return (T)parseTree.accept(this);
	}

	private BigDecimal _getBigDecimal(Comparable<?> comparable) {
		if (comparable == null) {
			return BigDecimal.ZERO;
		}

		if (comparable instanceof BigDecimal) {
			return (BigDecimal)comparable;
		}

		String value = comparable.toString();

		if (Validator.isNull(value)) {
			return BigDecimal.ZERO;
		}

		return new BigDecimal(value);
	}

	private BigDecimal[] _getBigDecimalArray(List<TerminalNode> terminalNodes) {
		BigDecimal[] values = new BigDecimal[terminalNodes.size()];

		for (int i = 0; i < terminalNodes.size(); i++) {
			TerminalNode terminalNode = terminalNodes.get(i);

			values[i] = new BigDecimal(terminalNode.getText());
		}

		return values;
	}

	private Expression<?> _getExpression(Object object) {
		if (object instanceof Expression) {
			return (Expression<?>)object;
		}

		if (object instanceof BigDecimal) {
			object = _getBigDecimal((Comparable<?>)object);
		}

		return new Scalar<>(object);
	}

	private final Map<String, Object> _variables;

}