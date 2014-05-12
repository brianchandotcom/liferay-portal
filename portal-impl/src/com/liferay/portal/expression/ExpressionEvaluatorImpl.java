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

import com.liferay.portal.kernel.expression.ExpressionData;
import com.liferay.portal.kernel.expression.ExpressionEvaluationException;
import com.liferay.portal.kernel.expression.ExpressionEvaluator;
import com.liferay.portal.kernel.expression.ExpressionVariable;
import com.liferay.portal.kernel.expression.VariableDependencies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jodd.typeconverter.TypeConverterManager;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class ExpressionEvaluatorImpl implements ExpressionEvaluator {

	public ExpressionEvaluatorImpl(Map<String, ExpressionVariable> variables) {
		_variablesMap = variables;
	}

	@Override
	public Boolean evaluateBooleanExpression(String expression)
		throws ExpressionEvaluationException {

		return (Boolean)evaluateExpression(expression, Boolean.class);
	}

	@Override
	public Double evaluateDoubleExpression(String expression)
		throws ExpressionEvaluationException {

		return (Double)evaluateExpression(expression, Double.class);
	}

	@Override
	public Object evaluateExpression(String expression, Class<?> returnedType)
		throws ExpressionEvaluationException {

		ExpressionData expressionData = extractExpressionData(expression);

		return ExpressionEvaluatorUtil.evaluate(
			expressionData.getRewritedExpression(), returnedType,
			expressionData.getVariableNames(),
			expressionData.getVariableTypes(),
			expressionData.getVariableValues());
	}

	@Override
	public Float evaluateFloatExpression(String expression)
		throws ExpressionEvaluationException {

		return (Float)evaluateExpression(expression, Float.class);
	}

	@Override
	public Integer evaluateIntegerExpression(String expression)
		throws ExpressionEvaluationException {

		return (Integer)evaluateExpression(expression, Integer.class);
	}

	@Override
	public Long evaluateLongExpression(String expression)
		throws ExpressionEvaluationException {

		return (Long)evaluateExpression(expression, Long.class);
	}

	@Override
	public String evaluateStringExpression(String expression)
		throws ExpressionEvaluationException {

		return (String)evaluateExpression(expression, String.class);
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

	protected ExpressionData extractExpressionData(String expression)
		throws ExpressionEvaluationException {

		String[] expressionVariables = extractExpressionVariables(expression);

		ExpressionData expressionData = new ExpressionData(expression);

		for (String variableName : expressionVariables) {
			ExpressionVariable variable = _variablesMap.get(variableName);

			expressionData.addVariable(
				variable.getDataType(), variable.getName(),
				getVariableValue(variable));
		}

		return expressionData;
	}

	protected String[] extractExpressionVariables(String expression) {
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

	protected VariableDependencies populateVariableDependencies(
		ExpressionVariable variable) {

		if (_variableDependenciesMap.containsKey(variable.getName())) {
			return _variableDependenciesMap.get(variable.getName());
		}

		VariableDependencies variableDependencies = new VariableDependencies(
			variable.getName());

		_variableDependenciesMap.put(variable.getName(), variableDependencies);

		if (variable.getValueExpression() != null) {
			String expression = variable.getValueExpression();
			String[] expressionVariables = extractExpressionVariables(
				expression);

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

	private static final Pattern VARIABLE_REGEX_PATTERN = Pattern.compile(
		"\\b([a-zA-Z]+[\\w\\._]+)(?!\\()\\b", Pattern.MULTILINE);

	private Map<String, Object> _calculatedVariableValues =
		new HashMap<String, Object>();
	private Map<String, VariableDependencies> _variableDependenciesMap =
		new HashMap<String, VariableDependencies>();
	private Map<String, ExpressionVariable> _variablesMap;

}