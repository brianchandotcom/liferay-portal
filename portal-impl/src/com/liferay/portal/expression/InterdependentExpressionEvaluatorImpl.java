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
import com.liferay.portal.kernel.expression.ExpressionVariable;
import com.liferay.portal.kernel.expression.InterdependentExpressionEvaluator;
import com.liferay.portal.kernel.expression.VariableDependencies;

import java.util.HashMap;
import java.util.Map;

import jodd.typeconverter.TypeConverterManager;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class InterdependentExpressionEvaluatorImpl
implements InterdependentExpressionEvaluator {

	public InterdependentExpressionEvaluatorImpl(Map<String,
	ExpressionVariable> variables) {
		_variablesMap = variables;
	}

	@Override
	public Boolean evaluateBooleanExpression(String stringExpression)
		throws ExpressionEvaluationException {

		Expression<Boolean> expression = populateExpression(
			stringExpression, Boolean.class);

		return expression.evaluate();
	}

	@Override
	public Double evaluateDoubleExpression(String stringExpression)
		throws ExpressionEvaluationException {

		Expression<Double> expression = populateExpression(
			stringExpression, Double.class);

		return expression.evaluate();
	}

	@Override
	public <T> T evaluateExpression(String stringExpression,
	Class<T> returnedType) throws ExpressionEvaluationException {

		Expression<T> expression = populateExpression(stringExpression,
		returnedType);

		return expression.evaluate();
	}

	@Override
	public Float evaluateFloatExpression(String stringExpression)
		throws ExpressionEvaluationException {

		Expression<Float> expression = populateExpression(
			stringExpression, Float.class);

		return expression.evaluate();
	}

	@Override
	public Integer evaluateIntegerExpression(String stringExpression)
		throws ExpressionEvaluationException {

		Expression<Integer> expression = populateExpression(
			stringExpression, Integer.class);

		return expression.evaluate();
	}

	@Override
	public Long evaluateLongExpression(String stringExpression)
		throws ExpressionEvaluationException {

		Expression<Long> expression = populateExpression(
			stringExpression, Long.class);

		return expression.evaluate();
	}

	@Override
	public String evaluateStringExpression(String stringExpression)
		throws ExpressionEvaluationException {

		Expression<String> expression = populateExpression(
			stringExpression, String.class);

		return expression.evaluate();
	}

	@Override
	public Map<String, VariableDependencies> getVariableDependenciesMap() {
		for (ExpressionVariable variable : _variablesMap.values()) {
			populateVariableDependencies(variable);
		}

		return _variableDependenciesMap;
	}

	protected Object convertToDataType(String value, Class<?> dataType) {
		return TypeConverterManager.convertType(value, dataType);
	}

	protected Object getVariableValue(ExpressionVariable variable)
		throws ExpressionEvaluationException {

		if (_calculatedVariableValues.containsKey(variable.getName())) {
			return _calculatedVariableValues.get(variable.getName());
		}
		else if (variable.getValueExpression() != null) {
			Object variableValue = evaluateExpression(
				variable.getValueExpression(), variable.getDataType());

			_calculatedVariableValues.put(variable.getName(), variableValue);

			return variableValue;
		}
		else {
			String value = variable.getCalculatedValue();

			return convertToDataType(value, variable.getDataType());
		}
	}

	protected <T> Expression<T> populateExpression(String stringExpression,
	Class<T> returnedType) throws ExpressionEvaluationException {

		Expression<T> expression = Expression.createExpression(stringExpression,
		returnedType);

		String[] expressionVariables = expression.getVariableNames();

		for (String variableName : expressionVariables) {
			ExpressionVariable variable = _variablesMap.get(variableName);

			expression.addVariable(variable.getDataType(), variable.getName(),
			getVariableValue(variable));
		}

		return expression;
	}

	protected VariableDependencies populateVariableDependencies(
		ExpressionVariable variable) {

		if (_variableDependenciesMap.containsKey(variable.getName())) {
			return _variableDependenciesMap.get(variable.getName());
		}

		VariableDependencies variableDependencies = new VariableDependencies(
			variable.getName());

		_variableDependenciesMap.put(variable.getName(), variableDependencies);

		if (variable.getValueExpression() != null) {
			Expression expression = Expression.createExpression(
				variable.getValueExpression(), variable.getDataType());

			String[] expressionVariables = expression.getVariableNames();

			for (String dependency : expressionVariables) {
				VariableDependencies populateVariableDependencies =
					populateVariableDependencies(_variablesMap.get(dependency));

				variableDependencies.addRequiredVariable(
					populateVariableDependencies.getVariableName());

				populateVariableDependencies.addAffectedVariable(
					variableDependencies.getVariableName());
			}
		}

		return variableDependencies;
	}

	private Map<String, Object> _calculatedVariableValues =
		new HashMap<String, Object>();
	private Map<String, VariableDependencies> _variableDependenciesMap =
		new HashMap<String, VariableDependencies>();
	private Map<String, ExpressionVariable> _variablesMap;

}