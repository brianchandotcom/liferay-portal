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

import com.liferay.portal.kernel.expression.ExpressionVariable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class ExpressionTestData {

	public static ExpressionTestData newExpressionTestData(String expression) {
		return new ExpressionTestData(expression);
	}

	public ExpressionTestData addBooleanVariable(String name, String expression,
	Boolean value) {

		addVariable(name, expression, Boolean.class, value);

		return this;
	}

	public ExpressionTestData addDoubleVariable(String name, String expression,
	Double value) {

		addVariable(name, expression, Double.class, value);

		return this;
	}

	public ExpressionTestData addFloatVariable(String name, String expression,
	Float value) {

		addVariable(name, expression, Float.class, value);

		return this;
	}

	public ExpressionTestData addIntegerVariable(String name, String expression,
	Integer value) {

		addVariable(name, expression, Integer.class, value);

		return this;
	}

	public ExpressionTestData addLongVariable(String name, String expression,
	Long value) {

		addVariable(name, expression, Long.class, value);

		return this;
	}

	public ExpressionTestData addStringVariable(String name, String expression,
	String value) {

		addVariable(name, expression, String.class, value);

		return this;
	}

	public Object getExpectedResult() {
		return _expectedResult;
	}

	public String getExpression() {
		return _expression;
	}

	public Class<?> getReturnType() {
		return _returnType;
	}

	public Map<String, ExpressionVariable> getVariables() {
		return _variables;
	}

	public ExpressionTestData setExpectedResult(Object value) {
		_expectedResult = value;

		return this;
	}

	public ExpressionTestData setReturnType(Class<?> returnType) {
		_returnType = returnType;

		return this;
	}

	private ExpressionTestData(String expression) {
		_expression = expression;
	}

	private void addVariable(String name, String expression, Class<?> type,
	Object value) {

		ExpressionVariable variable = new ExpressionVariable();
		variable.setCalculatedValue(value == null ? null : value.toString());
		variable.setDataType(type);
		variable.setName(name);
		variable.setValueExpression(expression);
		_variables.put(name, variable);
	}

	private Object _expectedResult;
	private final String _expression;
	private Class<?> _returnType;
	private final Map<String, ExpressionVariable> _variables =
		new HashMap<String, ExpressionVariable>();

}