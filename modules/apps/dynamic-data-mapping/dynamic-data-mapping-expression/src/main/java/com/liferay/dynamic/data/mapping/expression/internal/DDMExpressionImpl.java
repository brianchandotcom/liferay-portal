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

import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionActionHandler;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionTracker;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionObserver;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionLexer;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser;
import com.liferay.dynamic.data.mapping.expression.model.Expression;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * @author Miguel Angelo Caldas Gallindo
 * @author Marcellus Tavares
 */
public class DDMExpressionImpl<T> implements DDMExpression<T> {

	@Override
	public T evaluate() throws DDMExpressionException {
		Map<String, DDMExpressionFunctionFactory> ddmExpressionFunctions =
			_ddmExpressionFunctionTracker.getDDMExpressionFunctionFactories(
				_ddmExpressionFunctionNames);

		try {
			Set<String> undefinedFunctionNames = new HashSet<>(
				_ddmExpressionFunctionNames);

			undefinedFunctionNames.removeAll(ddmExpressionFunctions.keySet());

			if (!undefinedFunctionNames.isEmpty()) {
				throw new DDMExpressionException.FunctionNotDefined(
					undefinedFunctionNames);
			}

			DDMExpressionEvaluatorVisitor ddmExpressionEvaluatorVisitor =
				new DDMExpressionEvaluatorVisitor(
					ddmExpressionFunctions, _variables,
					_ddmExpressionActionHandler, _ddmExpressionFieldAccessor,
					_ddmExpressionObserver, _ddmExpressionParameterAccessor);

			return (T)_expressionContext.accept(ddmExpressionEvaluatorVisitor);
		}
		catch (DDMExpressionException ddmee) {
			throw ddmee;
		}
		catch (Exception e) {
			throw new DDMExpressionException(e);
		}
	}

	@Override
	public Expression getModel() {
		DDMExpressionModelVisitor ddmExpressionModelVisitor =
			new DDMExpressionModelVisitor();

		return _expressionContext.accept(ddmExpressionModelVisitor);
	}

	@Override
	public void setVariable(String name, Object value) {
		if (value instanceof Number) {
			value = new BigDecimal(value.toString());
		}

		_variables.put(name, value);
	}

	protected DDMExpressionImpl(String expressionString)
		throws DDMExpressionException {

		_expressionString = expressionString;

		if ((expressionString == null) || expressionString.isEmpty()) {
			throw new IllegalArgumentException();
		}

		CharStream charStream = new ANTLRInputStream(_expressionString);

		DDMExpressionLexer ddmExpressionLexer = new DDMExpressionLexer(
			charStream);

		DDMExpressionParser ddmExpressionParser = new DDMExpressionParser(
			new CommonTokenStream(ddmExpressionLexer));

		ddmExpressionParser.setErrorHandler(new BailErrorStrategy());

		try {
			_expressionContext = ddmExpressionParser.expression();
		}
		catch (Exception e) {
			throw new DDMExpressionException.InvalidSyntax(e);
		}

		ParseTreeWalker parseTreeWalker = new ParseTreeWalker();

		DDMExpressionListener ddmExpressionListener =
			new DDMExpressionListener();

		parseTreeWalker.walk(ddmExpressionListener, _expressionContext);

		_ddmExpressionFunctionNames = ddmExpressionListener.getFunctionNames();

		for (String variableName : ddmExpressionListener.getVariableNames()) {
			_variables.put(variableName, null);
		}
	}

	protected Set<String> getExpressionVariableNames() {
		return _variables.keySet();
	}

	protected void setDDMExpressionActionHandler(
		DDMExpressionActionHandler ddmExpressionActionHandler) {

		_ddmExpressionActionHandler = ddmExpressionActionHandler;
	}

	protected void setDDMExpressionFieldAccessor(
		DDMExpressionFieldAccessor ddmExpressionFieldAccessor) {

		_ddmExpressionFieldAccessor = ddmExpressionFieldAccessor;
	}

	protected void setDDMExpressionFunctionTracker(
		DDMExpressionFunctionTracker ddmExpressionFunctionTracker) {

		_ddmExpressionFunctionTracker = ddmExpressionFunctionTracker;
	}

	protected void setDDMExpressionObserver(
		DDMExpressionObserver ddmExpressionObserver) {

		_ddmExpressionObserver = ddmExpressionObserver;
	}

	protected void setDDMExpressionParameterAccessor(
		DDMExpressionParameterAccessor ddmExpressionParameterAccessor) {

		_ddmExpressionParameterAccessor = ddmExpressionParameterAccessor;
	}

	private DDMExpressionActionHandler _ddmExpressionActionHandler;
	private DDMExpressionFieldAccessor _ddmExpressionFieldAccessor;
	private final Set<String> _ddmExpressionFunctionNames;
	private DDMExpressionFunctionTracker _ddmExpressionFunctionTracker;
	private DDMExpressionObserver _ddmExpressionObserver;
	private DDMExpressionParameterAccessor _ddmExpressionParameterAccessor;
	private final DDMExpressionParser.ExpressionContext _expressionContext;
	private final String _expressionString;
	private final Map<String, Object> _variables = new HashMap<>();

}