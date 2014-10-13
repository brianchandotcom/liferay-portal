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

	public ExpressionImpl(String expressionString, Class<T> expressionClass) {
		_expressionString = expressionString;
		_expressionClass = expressionClass;

		List<String> variableNames = _variableNamesExtractor.extract(
			expressionString);

		for (String variableName : variableNames) {
			Variable variable = new Variable(variableName);

			_variables.put(variableName, variable);
		}
	}

	@Override
	public T evaluate() throws ExpressionEvaluationException {
		try {
			ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();

			expressionEvaluator.setExpressionType(_expressionClass);
			expressionEvaluator.setExtendedClass(MathUtil.class);
			expressionEvaluator.setParameters(
				getVariableNames(), getVariableClasses());

			expressionEvaluator.cook(_expressionString);

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

		for (Variable variable : _variables.values()) {
			populateVariableDependenciesMap(variable, variableDependenciesMap);
		}

		return variableDependenciesMap;
	}

	@Override
	public void setBooleanVariableValue(
		String variableName, Boolean variableValue) {

		setVariableValue(variableName, variableValue, Boolean.class);
	}

	@Override
	public void setDoubleVariableValue(
		String variableName, Double variableValue) {

		setVariableValue(variableName, variableValue, Double.class);
	}

	@Override
	public void setExpressionStringVariableValue(
		String variableName, String variableValue, Class<?> variableClass) {

		Variable variable = _variables.get(variableName);

		if (variable == null) {
			return;
		}

		variable.setExpressionString(variableValue);
		variable.setVariableClass(variableClass);
	}

	@Override
	public void setFloatVariableValue(
		String variableName, Float variableValue) {

		setVariableValue(variableName, variableValue, Float.class);
	}

	@Override
	public void setIntegerVariableValue(
		String variableName, Integer variableValue) {

		setVariableValue(variableName, variableValue, Integer.class);
	}

	@Override
	public void setLongVariableValue(String variableName, Long variableValue) {
		setVariableValue(variableName, variableValue, Long.class);
	}

	@Override
	public void setStringVariableValue(
		String variableName, String variableValue) {

		setVariableValue(variableName, variableValue, String.class);
	}

	@Override
	public void setVariableValue(
		String variableName, Object variableValue, Class<?> variableClass) {

		Variable variable = _variables.get(variableName);

		if (variable == null) {
			return;
		}

		variable.setValue(variableValue);
		variable.setVariableClass(variableClass);
	}

	protected <V> Expression<V> getExpression(
			String expressionString, Class<V> expressionType)
		throws ExpressionEvaluationException {

		Expression<V> expression = new ExpressionImpl<V>(
			expressionString, expressionType);

		List<String> variableNames = _variableNamesExtractor.extract(
			expressionString);

		for (String variableName : variableNames) {
			Variable variable = _variables.get(variableName);

			expression.setVariableValue(
				variableName, getVariableValue(variable),
				variable.getVariableClass());
		}

		return expression;
	}

	protected Expression<?> getExpression(Variable variable)
		throws ExpressionEvaluationException {

		if (variable.getExpressionString() == null) {
			return null;
		}

		Expression<?> expression = getExpression(
			variable.getExpressionString(), variable.getVariableClass());

		return expression;
	}

	protected Class<?>[] getVariableClasses() {
		List<Class<?>> variableClasses = new ArrayList<Class<?>>();

		for (Variable variable : _variables.values()) {
			variableClasses.add(variable.getVariableClass());
		}

		return variableClasses.toArray(new Class<?>[variableClasses.size()]);
	}

	protected String[] getVariableNames() {
		List<String> variableNames = new ArrayList<String>();

		for (Variable variable : _variables.values()) {
			variableNames.add(variable.getName());
		}

		return variableNames.toArray(new String[variableNames.size()]);
	}

	protected Object getVariableValue(Variable variable)
		throws ExpressionEvaluationException {

		Object variableValue = _variableValues.get(variable.getName());

		if (variableValue != null) {
			return variableValue;
		}

		Expression<?> expression = getExpression(variable);

		if (expression == null) {
			return variable.getValue();
		}

		variableValue = expression.evaluate();

		_variableValues.put(variable.getName(), variableValue);

		return variableValue;
	}

	protected Object[] getVariableValues()
		throws ExpressionEvaluationException {

		List<Object> variableValues = new ArrayList<Object>();

		for (Variable variable : _variables.values()) {
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

		if (variable.getExpressionString() != null) {
			List<String> variableNames = _variableNamesExtractor.extract(
				variable.getExpressionString());

			for (String variableName : variableNames) {
				VariableDependencies variableVariableDependencies =
					populateVariableDependenciesMap(
						_variables.get(variableName), variableDependenciesMap);

				variableVariableDependencies.addAffectedVariable(
					variableDependencies.getVariableName());
				variableDependencies.addRequiredVariable(
					variableVariableDependencies.getVariableName());
			}
		}

		variableDependenciesMap.put(variable.getName(), variableDependencies);

		return variableDependencies;
	}

	private final Class<?> _expressionClass;
	private final String _expressionString;
	private final VariableNamesExtractor _variableNamesExtractor =
		new VariableNamesExtractor();
	private final Map<String, Variable> _variables =
		new TreeMap<String, Variable>();
	private final Map<String, Object> _variableValues =
		new HashMap<String, Object>();

}