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

		List<String> variableNames = _variableNamesExtractor.extract(
			expression);

		for (String variableName : variableNames) {
			Variable variable = new Variable(variableName);

			_variablesMap.put(variableName, variable);
		}
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

		for (Variable variable : _variablesMap.values()) {
			populateVariableDependenciesMap(variable, variableDependenciesMap);
		}

		return variableDependenciesMap;
	}

	@Override
	public void setBooleanVariableValue(
		String variableName, Boolean variableValue) {

		setVariableValue(variableName, Boolean.class, variableValue);
	}

	@Override
	public void setDoubleVariableValue(
		String variableName, Double variableValue) {

		setVariableValue(variableName, Double.class, variableValue);
	}

	@Override
	public void setExpressionVariableValue(
		String variableName, Class<?> type, String variableValueExpression) {

		Variable variable = _variablesMap.get(variableName);

		if (variable == null) {
			return;
		}

		variable.setType(type);
		variable.setValueExpression(variableValueExpression);
	}

	@Override
	public void setFloatVariableValue(
		String variableName, Float variableValue) {

		setVariableValue(variableName, Float.class, variableValue);
	}

	@Override
	public void setIntegerVariableValue(
		String variableName, Integer variableValue) {

		setVariableValue(variableName, Integer.class, variableValue);
	}

	@Override
	public void setLongVariableValue(String variableName, Long variableValue) {
		setVariableValue(variableName, Long.class, variableValue);
	}

	@Override
	public void setStringVariableValue(
		String variableName, String variableValue) {

		setVariableValue(variableName, String.class, variableValue);
	}

	@Override
	public void setVariableValue(
		String variableName, Class<?> variableType, Object variableValue) {

		Variable variable = _variablesMap.get(variableName);

		if (variable == null) {
			return;
		}

		variable.setType(variableType);
		variable.setValue(variableValue);
	}

	protected <V> Expression<V> createVariableValueExpression(
			String expression, Class<V> expressionType)
		throws ExpressionEvaluationException {

		Expression<V> variableValueExpression = new ExpressionImpl<V>(
			expression, expressionType);

		List<String> variableNames = _variableNamesExtractor.extract(
			expression);

		for (String variableName : variableNames) {
			Variable variable = _variablesMap.get(variableName);

			variableValueExpression.setVariableValue(
				variableName, variable.getType(), getVariableValue(variable));
		}

		return variableValueExpression;
	}

	protected String[] getVariableNames() {
		List<String> variableNames = new ArrayList<String>();

		for (Variable variable : _variablesMap.values()) {
			variableNames.add(variable.getName());
		}

		return variableNames.toArray(new String[variableNames.size()]);
	}

	protected Class<?>[] getVariableTypes() {
		List<Class<?>> variableTypes = new ArrayList<Class<?>>();

		for (Variable variable : _variablesMap.values()) {
			variableTypes.add(variable.getType());
		}

		return variableTypes.toArray(new Class<?>[variableTypes.size()]);
	}

	protected Object getVariableValue(Variable variable)
		throws ExpressionEvaluationException {

		Object variableValue = _evaluatedVariableValues.get(variable.getName());

		if (variableValue != null) {
			return variableValue;
		}

		Expression<?> variableValueExpression = getVariableValueExpression(
			variable);

		if (variableValueExpression == null) {
			return variable.getValue();
		}

		variableValue = variableValueExpression.evaluate();

		_evaluatedVariableValues.put(variable.getName(), variableValue);

		return variableValue;
	}

	protected Expression<?> getVariableValueExpression(Variable variable)
		throws ExpressionEvaluationException {

		if (variable.getValueExpression() == null) {
			return null;
		}

		Expression<?> variableValueExpression = createVariableValueExpression(
			variable.getValueExpression(), variable.getType());

		return variableValueExpression;
	}

	protected Object[] getVariableValues()
		throws ExpressionEvaluationException {

		List<Object> variableValues = new ArrayList<Object>();

		for (Variable variable : _variablesMap.values()) {
			Object variableValue = getVariableValue(variable);

			variableValues.add(variableValue);
		}

		return variableValues.toArray(new Object[variableValues.size()]);
	}

	protected VariableDependencies populateVariableDependenciesMap(
		Variable variable,
		Map<String, VariableDependencies> variableDependenciesMap) {

		VariableDependencies variableDependencies = variableDependenciesMap.get(
			variable.getName());

		if (variableDependencies != null) {
			return variableDependencies;
		}

		variableDependencies = new VariableDependencies(variable.getName());

		variableDependenciesMap.put(variable.getName(), variableDependencies);

		if (variable.getValueExpression() != null) {
			List<String> variableNames = _variableNamesExtractor.extract(
				variable.getValueExpression());

			for (String variableName : variableNames) {
				VariableDependencies populateVariableDependencies =
					populateVariableDependenciesMap(
						_variablesMap.get(variableName),
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
	private VariableNamesExtractor _variableNamesExtractor =
		new VariableNamesExtractor();
	private Map<String, Variable> _variablesMap =
		new TreeMap<String, Variable>();

}