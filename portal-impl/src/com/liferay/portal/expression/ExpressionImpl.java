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

import com.liferay.portal.kernel.expression.Expression;
import com.liferay.portal.kernel.expression.ExpressionEvaluationException;
import com.liferay.portal.kernel.expression.ExpressionVariable;
import com.liferay.portal.kernel.expression.VariableDependencies;
import com.liferay.portal.kernel.util.MathUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.janino.ExpressionEvaluator;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class ExpressionImpl<T> implements Expression<T> {

	public ExpressionImpl(String expression, Class<T> expressionType) {
		_expression = expression;
		_expressionType = expressionType;

		for (String name : _extractVariables(expression)) {
			ExpressionVariable expressionVariable = new ExpressionVariable(
				name);

			_expressionVariablesMap.put(name, expressionVariable);
		}
	}

	@Override
	public void addBooleanVariable(String variableName, Boolean variableValue) {
		addVariable(variableName, Boolean.class, variableValue);
	}

	@Override
	public void addDoubleVariable(String variableName, Double variableValue) {
		addVariable(variableName, Double.class, variableValue);
	}

	@Override
	public void addExpressionVariable(
		String variableName, Class<?> variableType,
		String variableValueExpresion) {

		ExpressionVariable expressionVariable = _expressionVariablesMap.get(
			variableName);

		if (expressionVariable == null) {
			return;
		}

		expressionVariable.setType(variableType);
		expressionVariable.setValueExpression(variableValueExpresion);
	}

	@Override
	public void addFloatVariable(String variableName, Float variableValue) {
		addVariable(variableName, Float.class, variableValue);
	}

	@Override
	public void addIntegerVariable(String variableName, Integer variableValue) {
		addVariable(variableName, Integer.class, variableValue);
	}

	@Override
	public void addLongVariable(String variableName, Long variableValue) {
		addVariable(variableName, Long.class, variableValue);
	}

	@Override
	public void addStringVariable(String variableName, String variableValue) {
		addVariable(variableName, String.class, variableValue);
	}

	@Override
	public void addVariable(
		String variableName, Class<?> variableType, Object variableValue) {

		ExpressionVariable expressionVariable = _expressionVariablesMap.get(
			variableName);

		if (expressionVariable == null) {
			return;
		}

		expressionVariable.setType(variableType);
		expressionVariable.setValue(variableValue);
	}

	@Override
	public T evaluate() throws ExpressionEvaluationException {
		try {
			ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();

			expressionEvaluator.setExpressionType(_expressionType);
			expressionEvaluator.setParameters(
				getVariableNames(), getVariableTypes());
			expressionEvaluator.setExtendedClass(MathUtil.class);
			expressionEvaluator.cook(_expression);

			return (T)expressionEvaluator.evaluate(getVariableValues());
		}
		catch (Exception e) {
			throw new ExpressionEvaluationException(e);
		}
	}

	@Override
	public Map<String, VariableDependencies> getVariableDependenciesMap() {
		for (ExpressionVariable variable : _expressionVariablesMap.values()) {
			populateVariableDependencies(variable);
		}

		return _variableDependenciesMap;
	}

	@Override
	public String[] getVariableNames() {
		List<String> variableNames = new ArrayList<String>();

		for (ExpressionVariable expressionVariable :
				_expressionVariablesMap.values()) {

			variableNames.add(expressionVariable.getName());
		}

		return variableNames.toArray(new String[variableNames.size()]);
	}

	protected List<String> _extractVariables(String expression) {
		if (expression == null) {
			return Collections.emptyList();
		}

		List<String> variableNames = new ArrayList<String>();

		Matcher matcher = _VARIABLE_REGEX_PATTERN.matcher(expression);

		while (matcher.find()) {
			variableNames.add(matcher.group(1));
		}

		return variableNames;
	}

	protected Class<?>[] getVariableTypes() {
		List<Class<?>> variableTypes = new ArrayList<Class<?>>();

		for (ExpressionVariable expressionVariable :
				_expressionVariablesMap.values()) {

			variableTypes.add(expressionVariable.getType());
		}

		return variableTypes.toArray(new Class<?>[variableTypes.size()]);
	}

	protected Object getVariableValue(ExpressionVariable variable)
		throws ExpressionEvaluationException {

		if (_evaluatedVariableValues.containsKey(variable.getName())) {
			return _evaluatedVariableValues.get(variable.getName());
		}

		if (variable.getValueExpression() != null) {
			Expression<?> expression = new ExpressionImpl(
				variable.getValueExpression(), variable.getType());

			String[] variableNames = expression.getVariableNames();

			for (String variableName : variableNames) {
				ExpressionVariable expressionVariable =
					_expressionVariablesMap.get(variableName);

				expression.addVariable(
					variableName, variable.getType(),
					getVariableValue(expressionVariable));
			}

			Object variableValue = expression.evaluate();

			_evaluatedVariableValues.put(variable.getName(), variableValue);

			return variableValue;
		}

		return variable.getValue();
	}

	protected Object[] getVariableValues()
		throws ExpressionEvaluationException {

		List<Object> variableValues = new ArrayList<Object>();

		for (ExpressionVariable expressionVariable :
				_expressionVariablesMap.values()) {

			Object variableValue = getVariableValue(expressionVariable);

			variableValues.add(variableValue);
		}

		return variableValues.toArray(new Object[variableValues.size()]);
	}

	protected VariableDependencies populateVariableDependencies(
		ExpressionVariable expressionVariable) {

		if (_variableDependenciesMap.containsKey(
				expressionVariable.getName())) {

			return _variableDependenciesMap.get(expressionVariable.getName());
		}

		VariableDependencies variableDependencies = new VariableDependencies(
			expressionVariable.getName());

		_variableDependenciesMap.put(
			expressionVariable.getName(), variableDependencies);

		if (expressionVariable.getValueExpression() != null) {
			Expression<?> expression = new ExpressionImpl(
				expressionVariable.getValueExpression(),
				expressionVariable.getType());

			String[] variableNames = expression.getVariableNames();

			for (String variableName : variableNames) {
				VariableDependencies populateVariableDependencies =
					populateVariableDependencies(
						_expressionVariablesMap.get(variableName));

				variableDependencies.addRequiredVariable(
					populateVariableDependencies.getVariableName());

				populateVariableDependencies.addAffectedVariable(
					variableDependencies.getVariableName());
			}
		}

		return variableDependencies;
	}

	private static final Pattern _VARIABLE_REGEX_PATTERN = Pattern.compile(
		"\\b([a-zA-Z]+[\\w\\._]+)(?!\\()\\b", Pattern.MULTILINE);

	private Map<String, Object> _evaluatedVariableValues =
		new TreeMap<String, Object>();
	private String _expression;
	private Class<?> _expressionType;
	private Map<String, ExpressionVariable> _expressionVariablesMap =
		new TreeMap<String, ExpressionVariable>();
	private Map<String, VariableDependencies> _variableDependenciesMap =
		new TreeMap<String, VariableDependencies>();

}