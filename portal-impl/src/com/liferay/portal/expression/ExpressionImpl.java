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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.janino.ExpressionEvaluator;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class ExpressionImpl<T> implements Expression<T> {

	public ExpressionImpl(String expression, Class<T> expressionType) {
		_expression = expression;
		_expressionType = expressionType;

		List<String> variableNames =
			_expressionVariablesExtractor.extractVariables(expression);

		for (String variableName : variableNames) {
			ExpressionVariable expressionVariable = new ExpressionVariable(
				variableName);

			_expressionVariablesMap.put(variableName, expressionVariable);
		}
	}

	@Override
	public void addBooleanVariable(String name, Boolean value) {
		addVariable(name, Boolean.class, value);
	}

	@Override
	public void addDoubleVariable(String name, Double value) {
		addVariable(name, Double.class, value);
	}

	@Override
	public void addExpressionVariable(
		String name, Class<?> type, String valueExpression) {

		ExpressionVariable expressionVariable = _expressionVariablesMap.get(
			name);

		if (expressionVariable == null) {
			return;
		}

		expressionVariable.setType(type);
		expressionVariable.setValueExpression(valueExpression);
	}

	@Override
	public void addFloatVariable(String name, Float value) {
		addVariable(name, Float.class, value);
	}

	@Override
	public void addIntegerVariable(String name, Integer value) {
		addVariable(name, Integer.class, value);
	}

	@Override
	public void addLongVariable(String name, Long value) {
		addVariable(name, Long.class, value);
	}

	@Override
	public void addStringVariable(String name, String value) {
		addVariable(name, String.class, value);
	}

	@Override
	public void addVariable(String name, Class<?> type, Object value) {
		ExpressionVariable expressionVariable = _expressionVariablesMap.get(
			name);

		if (expressionVariable == null) {
			return;
		}

		expressionVariable.setType(type);
		expressionVariable.setValue(value);
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
		Map<String, VariableDependencies> variableDependenciesMap =
			new HashMap<String, VariableDependencies>();

		for (ExpressionVariable expressionVariable :
				_expressionVariablesMap.values()) {

			populateVariableDependenciesMap(
				expressionVariable, variableDependenciesMap);
		}

		return variableDependenciesMap;
	}

	protected String[] getVariableNames() {
		List<String> variableNames = new ArrayList<String>();

		for (ExpressionVariable expressionVariable :
				_expressionVariablesMap.values()) {

			variableNames.add(expressionVariable.getName());
		}

		return variableNames.toArray(new String[variableNames.size()]);
	}

	protected Class<?>[] getVariableTypes() {
		List<Class<?>> variableTypes = new ArrayList<Class<?>>();

		for (ExpressionVariable expressionVariable :
				_expressionVariablesMap.values()) {

			variableTypes.add(expressionVariable.getType());
		}

		return variableTypes.toArray(new Class<?>[variableTypes.size()]);
	}

	protected Object getVariableValue(ExpressionVariable expressionVariable)
		throws ExpressionEvaluationException {

		Object variableValue = _evaluatedVariableValues.get(
			expressionVariable.getName());

		if (variableValue != null) {
			return variableValue;
		}

		Expression<?> expression = getVariableValueExpression(
			expressionVariable);

		if (expression == null) {
			return expressionVariable.getValue();
		}

		variableValue = expression.evaluate();

		_evaluatedVariableValues.put(
			expressionVariable.getName(), variableValue);

		return variableValue;
	}

	protected Expression<?> getVariableValueExpression(
			ExpressionVariable expressionVariable)
		throws ExpressionEvaluationException {

		if (expressionVariable.getValueExpression() == null) {
			return null;
		}

		Expression<?> expression = new ExpressionImpl(
			expressionVariable.getValueExpression(),
			expressionVariable.getType());

		List<String> variableNames =
			_expressionVariablesExtractor.extractVariables(
				expressionVariable.getValueExpression());

		for (String variableName : variableNames) {
			ExpressionVariable variable = _expressionVariablesMap.get(
				variableName);

			expression.addVariable(
				variableName, variable.getType(), getVariableValue(variable));
		}

		return expression;
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

	protected VariableDependencies populateVariableDependenciesMap(
		ExpressionVariable expressionVariable,
		Map<String, VariableDependencies> variableDependenciesMap) {

		VariableDependencies variableDependencies = variableDependenciesMap.get(
			expressionVariable.getName());

		if (variableDependencies != null) {
			return variableDependencies;
		}

		variableDependencies = new VariableDependencies(
			expressionVariable.getName());

		variableDependenciesMap.put(
			expressionVariable.getName(), variableDependencies);

		if (expressionVariable.getValueExpression() != null) {
			List<String> variableNames =
				_expressionVariablesExtractor.extractVariables(
					expressionVariable.getValueExpression());

			for (String variableName : variableNames) {
				VariableDependencies populateVariableDependencies =
					populateVariableDependenciesMap(
						_expressionVariablesMap.get(variableName),
						variableDependenciesMap);

				variableDependencies.addRequiredVariable(
					populateVariableDependencies.getVariableName());

				populateVariableDependencies.addAffectedVariable(
					variableDependencies.getVariableName());
			}
		}

		return variableDependencies;
	}

	private Map<String, Object> _evaluatedVariableValues =
		new HashMap<String, Object>();
	private String _expression;
	private Class<?> _expressionType;
	private ExpressionVariablesExtractor _expressionVariablesExtractor =
		new ExpressionVariablesExtractor();
	private Map<String, ExpressionVariable> _expressionVariablesMap =
		new TreeMap<String, ExpressionVariable>();

}