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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import com.udojava.evalex.Expression.ExpressionException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Marcellus Tavares
 * @author Leonardo Barros
 */
public class TokenExtractor {

	public TokenExtractor(String expressionString)
		throws DDMExpressionException {

		if (Validator.isNull(expressionString)) {
			throw new IllegalArgumentException("Expression is null");
		}

		_expression = expressionString;

		_extract();
	}

	public String getExpression() {
		return _expression;
	}

	public Map<String, String> getVariableMap() {
		return _variableMap;
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

		_expression = _expression.replaceAll(
			"\\b" + token + "\\b", variableName);
	}

	protected Iterator<String> getExpressionTokens() {
		com.udojava.evalex.Expression expression =
			new com.udojava.evalex.Expression(_expression);

		return expression.getExpressionTokenizer();
	}

	protected boolean isBooleanConstant(String token) {
		return _BOOLEAN_CONSTANTS.contains(StringUtil.toLowerCase(token));
	}

	protected boolean isFunction(String token) {
		return _AVAILABLE_FUNCTIONS.contains(StringUtil.toLowerCase(token));
	}

	protected boolean isFunctionAllowed(String token) {
		return _ALLOWED_FUNCTIONS.contains(StringUtil.toLowerCase(token));
	}

	protected boolean isOperator(String token) {
		Matcher tokenMatcher = _operatorsPattern.matcher(token);

		return tokenMatcher.matches();
	}

	private void _extract() throws DDMExpressionException {
		try {
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
		}
		catch (ExpressionException ee) {
			throw new DDMExpressionException(ee);
		}
	}

	private static final Set<String> _ALLOWED_FUNCTIONS = SetUtil.fromArray(
		new String[] {
			"between", "concat", "contains", "equals", "if", "isemailaddress",
			"isurl", "max", "min", "not", "sum"
		}
	);

	private static final Set<String> _AVAILABLE_FUNCTIONS = SetUtil.fromArray(
		new String[] {
			"abs", "acos", "asin", "atan", "between", "ceiling", "concat",
			"contains", "cos", "cosh", "deg", "equals", "floor", "if",
			"isemailaddress", "isurl", "log", "log10", "max", "min", "not",
			"rad", "random", "round", "sin", "sinh", "sqrt", "sum", "tan",
			"tanh"
		}
	);

	private static final Set<String> _BOOLEAN_CONSTANTS = SetUtil.fromArray(
		new String[] {"false", "true"}
	);

	private static final Pattern _operatorsPattern = Pattern.compile(
		"[+-/\\*%\\^\\(\\)]|[<>=!]=?|&&|\\|\\|");
	private static final Pattern _stringPattern = Pattern.compile(
		"\"([^\"]*)\"");
	private static final Pattern _variablePattern = Pattern.compile(
		"\\b([a-zA-Z]+[\\w_]*)(?!\\()\\b");

	private String _expression;
	private Map<String, String> _variableMap = new HashMap<>();

}