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

package com.liferay.portal.expression;

import com.liferay.portal.kernel.expression.ExpressionEvaluationException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class Expression<T> {

	public static Expression<Boolean> createBooleanExpression(
		String stringExpression) {

		return new Expression<Boolean>(stringExpression, Boolean.class);
	}

	public static Expression<Double> createDoubleExpression(
		String stringExpression) {

		return new Expression<Double>(stringExpression, Double.class);
	}

	public static <T> Expression<T> createExpression(String stringExpression,
	Class<T> returnedType) {

		return new Expression<T>(stringExpression, returnedType);
	}

	public static Expression<Float> createFloatExpression(
		String stringExpression) {

		return new Expression<Float>(stringExpression, Float.class);
	}

	public static Expression<Integer> createIntegerExpression(
		String stringExpression) {

		return new Expression<Integer>(stringExpression, Integer.class);
	}

	public static Expression<Long> createLongExpression(
		String stringExpression) {

		return new Expression<Long>(stringExpression, Long.class);
	}

	public static Expression<String> createStringExpression(
		String stringExpression) {

		return new Expression<String>(stringExpression, String.class);
	}

	public void addVariable(Class<?> variableType, String variableName,
	Object variableValue) throws ExpressionEvaluationException {

		if (_variableNames.contains(variableName)) {
			_variableTypes.add(variableType);
			_variableValues.add(variableValue);
		}
		else {
			throw new ExpressionEvaluationException(
				"Expression doesn't contain a variable named "+ variableName);
		}
	}

	public T evaluate() throws ExpressionEvaluationException {

		return (T)ExpressionEvaluatorUtil.evaluate(getRewritedExpression(),
		_returnedType, getVariableNames(), getVariableTypes(),
		getVariableValues());
	}

	public String getOriginalExpression() {
		return _originalExpression;
	}

	public String getRewritedExpression() {
		if (_rewritedExpression == null) {
			return _originalExpression;
		}

		return _rewritedExpression;
	}

	public String[] getVariableNames() {
		return _variableNames.toArray(new String[_variableNames.size()]);
	}

	public Class<?>[] getVariableTypes() {
		return _variableTypes.toArray(new Class<?>[_variableTypes.size()]);
	}

	public Object[] getVariableValues() {
		return _variableValues.toArray();
	}

	public void setRewritedExpression(String rewritedExpression) {
		_rewritedExpression = rewritedExpression;
	}

	private Expression(String originalExpression, Class<?> returnedType) {
		_originalExpression = originalExpression;
		_returnedType = returnedType;

		String[] variableNames = _extractVariables(originalExpression);

		for (String name : variableNames) {
			_variableNames.add(name);
		}
	}

	private String[] _extractVariables(String expression) {
		if (expression == null) {
			return new String[0];
		}

		List<String> variableNames = new ArrayList<String>();

		Matcher matcher = VARIABLE_REGEX_PATTERN.matcher(expression);

		while (matcher.find()) {
			variableNames.add(matcher.group(1));
		}

		return variableNames.toArray(new String[variableNames.size()]);
	}

	private static final Pattern VARIABLE_REGEX_PATTERN = Pattern.compile(
		"\\b([a-zA-Z]+[\\w\\._]+)(?!\\()\\b", Pattern.MULTILINE);

	private String _originalExpression;
	private Class<?> _returnedType;
	private String _rewritedExpression;
	private List<String> _variableNames = new ArrayList<String>();;
	private List<Class<?>> _variableTypes = new ArrayList<Class<?>>();
	private List<Object> _variableValues = new ArrayList<Object>();

}