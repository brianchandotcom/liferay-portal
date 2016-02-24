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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import com.udojava.evalex.Expression.ExpressionException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Marcellus Tavares
 * @author Leonardo Barros
 */
public class TokenExtractor {

	public TokenExtractor(String expressionString) {
		if (Validator.isNull(expressionString)) {
			throw new IllegalArgumentException("Expression cannot be null");
		}

		_expression = expressionString;
	}

	public String getExpression() {
		return _expression;
	}

	public Map<String, String> getVariableMap() throws DDMExpressionException {
		try {
			_variableMap = new HashMap<>();

			Matcher matcher = _stringPattern.matcher(_expression);

			while (matcher.find()) {
				createStringVariable(matcher.group(1));
			}

			Iterator<String> tokenIterator = getExpressionTokens();

			while (tokenIterator.hasNext()) {
				String token = tokenIterator.next();

				if (isFunction(token) && !isFunctionAllowed(token)) {
					throw new DDMExpressionException.FunctionNotAllowed(token);
				}

				if (!isOperator(token) && !isFunctionAllowed(token) &&
					!isBooleanConstant(token)) {

					Matcher variableMatcher = _variablePattern.matcher(token);

					if (variableMatcher.matches()) {
						if (!_variableMap.containsKey(token)) {
							_variableMap.put(token, token);
						}
					}
					else {
						createVariable(token);
					}
				}
			}

			return _variableMap;
		}
		catch (ExpressionException ee) {
			throw new DDMExpressionException(ee);
		}
	}

	protected String createRandomVariableName() {
		return StringUtil.randomId();
	}

	protected void createStringVariable(String token) {
		String variableName = createRandomVariableName();

		_variableMap.put(variableName, token);

		_expression = StringUtil.replace(
			_expression, "\"" + token + "\"", variableName);
	}

	protected void createVariable(String token) {
		String variableName = createRandomVariableName();

		_variableMap.put(variableName, token);

		_expression = StringUtil.replace(_expression, token, variableName);
	}

	protected Iterator<String> getExpressionTokens() {
		com.udojava.evalex.Expression expression =
			new com.udojava.evalex.Expression(_expression);

		return expression.getExpressionTokenizer();
	}

	protected boolean isBooleanConstant(String token) {
		return ArrayUtil.contains(
			_BOOLEAN_CONSTANTS, StringUtil.toLowerCase(token));
	}

	protected boolean isFunction(String token) {
		return ArrayUtil.contains(
			_AVAILABLE_FUNCTIONS, StringUtil.toLowerCase(token));
	}

	protected boolean isFunctionAllowed(String token) {
		return ArrayUtil.contains(
			_ALLOWED_FUNCTIONS, StringUtil.toLowerCase(token));
	}

	protected boolean isOperator(String token) {
		Matcher tokenMatcher = _operatorsPattern.matcher(token);

		return tokenMatcher.matches();
	}

	private static final String[] _ALLOWED_FUNCTIONS = {
		"not", "if", "min", "max", "between", "concat", "contains", "equals",
		"isemailaddress", "isurl", "sum"
	};

	private static final String[] _AVAILABLE_FUNCTIONS = {
		"not", "if", "random", "min", "max", "abs", "round", "floor", "ceiling",
		"log", "log10", "sqrt", "sin", "cos", "tan", "asin", "acos", "atan",
		"sinh", "cosh", "tanh", "rad", "deg", "between", "concat", "contains",
		"equals", "isemailaddress", "isurl", "sum"
	};

	private static final String[] _BOOLEAN_CONSTANTS = {"false", "true"};

	private static final Pattern _operatorsPattern = Pattern.compile(
		"[+-/\\*%\\^\\(\\)]|[<>=!]=?|&&|\\|\\|");
	private static final Pattern _stringPattern = Pattern.compile(
		"\"([^\"]*)\"");
	private static final Pattern _variablePattern = Pattern.compile(
		"\\b([a-zA-Z]+[\\w_]*)(?!\\()\\b");

	private String _expression;
	private Map<String, String> _variableMap;

}