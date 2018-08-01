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

import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.VariableDependencies;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionLexer;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser;
import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * @author Miguel Angelo Caldas Gallindo
 * @author Marcellus Tavares
 */
public class DDMExpressionImpl<T> implements DDMExpression<T> {

	public DDMExpressionImpl(String expressionString, Class<T> expressionClass)
		throws DDMExpressionException {

		if ((expressionString == null) || expressionString.isEmpty()) {
			throw new IllegalArgumentException();
		}

		_expressionString = expressionString;
		_expressionClass = expressionClass;

		_expressionContext = createExpressionContext();

		registerExpressionFunctionsAndVariables();
		registerExpressionModel();
	}

	@Override
	public T evaluate() throws DDMExpressionException {
		Set<String> undefinedFunctionNames = getUndefinedFunctionNames();

		if (!undefinedFunctionNames.isEmpty()) {
			throw new DDMExpressionException.FunctionNotDefined(
				undefinedFunctionNames);
		}

		try {
			DDMExpressionEvaluatorVisitor ddmExpressionEvaluatorVisitor =
				createDDMExpressionEvaluatorVisitor();

			Object result = _expressionContext.accept(
				ddmExpressionEvaluatorVisitor);

			return (T)toRetunType(result);
		}
		catch (DDMExpressionException ddmee) {
			throw ddmee;
		}
		catch (Exception e) {
			throw new DDMExpressionException(e);
		}
	}

	@Override
	public Expression getModel() {
		return _expressionModel;
	}

	@Override
	public Map<String, VariableDependencies> getVariableDependenciesMap()
		throws DDMExpressionException {

		Map<String, VariableDependencies> variableDependenciesMap =
			new HashMap<>();

		List<Variable> variables = ListUtil.fromCollection(_variables.values());

		for (Variable variable : variables) {
			populateVariableDependenciesMap(variable, variableDependenciesMap);
		}

		return variableDependenciesMap;
	}

	@Override
	public void setBooleanVariableValue(
		String variableName, Boolean variableValue) {

		setVariableValue(variableName, variableValue);
	}

	@Override
	public void setDDMExpressionFunction(
		String functionName, DDMExpressionFunction ddmExpressionFunction) {

		_ddmExpressionFunctions.put(functionName, ddmExpressionFunction);
	}

	@Override
	public void setDoubleVariableValue(
		String variableName, Double variableValue) {

		setVariableValue(variableName, variableValue);
	}

	@Override
	public void setExpressionStringVariableValue(
		String variableName, String variableValue) {

		Variable variable = _variables.get(variableName);

		if (variable == null) {
			return;
		}

		variable.setExpressionString(variableValue);
	}

	@Override
	public void setFloatVariableValue(
		String variableName, Float variableValue) {

		setVariableValue(variableName, variableValue.doubleValue());
	}

	@Override
	public void setIntegerVariableValue(
		String variableName, Integer variableValue) {

		setVariableValue(variableName, variableValue.doubleValue());
	}

	@Override
	public void setLongVariableValue(String variableName, Long variableValue) {
		setVariableValue(variableName, variableValue.doubleValue());
	}

	@Override
	public void setNumberVariableValue(
		String variableName, Number variableValue) {

		setVariableValue(variableName, variableValue.doubleValue());
	}

	@Override
	public void setObjectVariableValue(
		String variableName, Object variableValue) {

		Variable variable = _variables.get(variableName);

		if (variable == null) {
			return;
		}

		variable.setValue(variableValue);

		_variableValues.put(variableName, variableValue);
	}

	@Override
	public void setStringVariableValue(
			String variableName, String variableValue)
		throws DDMExpressionException {

		setVariableValue(variableName, variableValue);
	}

	protected void assertResultTypeClass(
			Class<?> expectedResultTypeClass, Class<?> resultTypeClass)
		throws DDMExpressionException {

		if (!expectedResultTypeClass.isAssignableFrom(resultTypeClass)) {
			throw new DDMExpressionException.IncompatipleReturnType();
		}
	}

	protected DDMExpressionEvaluatorVisitor
			createDDMExpressionEvaluatorVisitor()
		throws DDMExpressionException {

		DDMExpressionEvaluatorVisitor ddmExpressionEvaluatorVisitor =
			new DDMExpressionEvaluatorVisitor();

		// Functions

		ddmExpressionEvaluatorVisitor.addFunctions(_ddmExpressionFunctions);

		// Variables

		for (Map.Entry<String, Variable> entry : _variables.entrySet()) {
			ddmExpressionEvaluatorVisitor.addVariable(
				entry.getKey(), getVariableValue(entry.getValue()));
		}

		return ddmExpressionEvaluatorVisitor;
	}

	protected DDMExpression<Object> createExpression(String expressionString)
		throws DDMExpressionException {

		DDMExpressionImpl<Object> ddmExpression = new DDMExpressionImpl<>(
			expressionString, Object.class);

		for (String variableName : ddmExpression.getExpressionVariableNames()) {
			Variable variable = _variables.get(variableName);

			if (variable != null) {
				Object variableValue = getVariableValue(variable);

				ddmExpression.setVariableValue(variableName, variableValue);
			}
		}

		return ddmExpression;
	}

	protected DDMExpression<Object> createExpression(Variable variable)
		throws DDMExpressionException {

		if (variable.getExpressionString() == null) {
			return null;
		}

		DDMExpression<Object> ddmExpression = createExpression(
			variable.getExpressionString());

		return ddmExpression;
	}

	protected DDMExpressionParser.ExpressionContext createExpressionContext()
		throws DDMExpressionException {

		try {
			CharStream charStream = new ANTLRInputStream(_expressionString);

			DDMExpressionLexer ddmExpressionLexer = new DDMExpressionLexer(
				charStream);

			DDMExpressionParser ddmExpressionParser = new DDMExpressionParser(
				new CommonTokenStream(ddmExpressionLexer));

			ddmExpressionParser.setErrorHandler(new BailErrorStrategy());

			return ddmExpressionParser.expression();
		}
		catch (Exception e) {
			throw new DDMExpressionException.InvalidSyntax(e);
		}
	}

	protected Set<String> getExpressionFunctionNames() {
		return _expressionFunctionNames;
	}

	protected Set<String> getExpressionVariableNames() {
		return _variables.keySet();
	}

	protected Set<String> getUndefinedFunctionNames() {
		Set<String> undefinedFunctionNames = new HashSet<>(
			getExpressionFunctionNames());

		undefinedFunctionNames.removeAll(_ddmExpressionFunctions.keySet());

		return undefinedFunctionNames;
	}

	protected Object getVariableValue(Variable variable)
		throws DDMExpressionException {

		Object variableValue = _variableValues.get(variable.getName());

		if (variableValue != null) {
			return variableValue;
		}

		DDMExpression<Object> ddmExpression = createExpression(variable);

		variableValue = ddmExpression.evaluate();

		_variableValues.put(variable.getName(), variableValue);

		return variableValue;
	}

	protected VariableDependencies populateVariableDependenciesMap(
			Variable variable,
			Map<String, VariableDependencies> variableDependenciesMap)
		throws DDMExpressionException {

		VariableDependencies variableDependencies = variableDependenciesMap.get(
			variable.getName());

		if (variableDependencies != null) {
			return variableDependencies;
		}

		variableDependencies = new VariableDependencies(variable.getName());

		if (variable.getExpressionString() != null) {
			DDMExpressionImpl<?> ddmExpression = new DDMExpressionImpl<>(
				variable.getExpressionString(), Object.class);

			for (String variableName :
					ddmExpression.getExpressionVariableNames()) {

				if (!_variables.containsKey(variableName)) {
					Variable newVariable = new Variable(variableName);

					_variables.put(variableName, newVariable);
				}

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

	protected void registerExpressionFunctionsAndVariables() {
		ParseTreeWalker parseTreeWalker = new ParseTreeWalker();

		DDMExpressionListener ddmExpressionListener =
			new DDMExpressionListener();

		parseTreeWalker.walk(ddmExpressionListener, _expressionContext);

		// Function names

		_expressionFunctionNames.addAll(
			ddmExpressionListener.getFunctionNames());

		// Variables

		for (String variableName : ddmExpressionListener.getVariableNames()) {
			_variables.put(variableName, new Variable(variableName));
		}
	}

	protected void registerExpressionModel() {
		DDMExpressionModelVisitor ddmExpressionModelVisitor =
			new DDMExpressionModelVisitor();

		_expressionModel = _expressionContext.accept(ddmExpressionModelVisitor);
	}

	protected void setVariableValue(String variableName, Object variableValue) {
		Variable variable = _variables.get(variableName);

		if (variable == null) {
			return;
		}

		variable.setValue(variableValue);

		_variableValues.put(variableName, variableValue);
	}

	protected double toDouble(Object result) throws DDMExpressionException {
		Number number = (Number)result;

		return number.doubleValue();
	}

	protected float toFloat(Object result) throws DDMExpressionException {
		Number number = (Number)result;

		return number.floatValue();
	}

	protected int toInteger(Object result) throws DDMExpressionException {
		Number number = (Number)result;

		return number.intValue();
	}

	protected long toLong(Object result) throws DDMExpressionException {
		Number number = (Number)result;

		return number.longValue();
	}

	protected Object toRetunType(Object result) throws DDMExpressionException {
		if (String.class.isAssignableFrom(_expressionClass)) {
			return String.valueOf(result);
		}
		else if (Boolean.class.isAssignableFrom(_expressionClass)) {
			assertResultTypeClass(Boolean.class, result.getClass());

			return result;
		}
		else if (Double.class.isAssignableFrom(_expressionClass)) {
			assertResultTypeClass(Number.class, result.getClass());

			return toDouble(result);
		}
		else if (Float.class.isAssignableFrom(_expressionClass)) {
			assertResultTypeClass(Number.class, result.getClass());

			return toFloat(result);
		}
		else if (Integer.class.isAssignableFrom(_expressionClass)) {
			assertResultTypeClass(Number.class, result.getClass());

			return toInteger(result);
		}
		else if (Long.class.isAssignableFrom(_expressionClass)) {
			assertResultTypeClass(Number.class, result.getClass());

			return toLong(result);
		}
		else if (Number.class.isAssignableFrom(_expressionClass)) {
			assertResultTypeClass(Number.class, result.getClass());

			return result;
		}

		return result;
	}

	private final Map<String, DDMExpressionFunction> _ddmExpressionFunctions =
		new HashMap<>();
	private final Class<?> _expressionClass;
	private final DDMExpressionParser.ExpressionContext _expressionContext;
	private final Set<String> _expressionFunctionNames = new HashSet<>();
	private Expression _expressionModel;
	private final String _expressionString;
	private final Map<String, Variable> _variables = new TreeMap<>();
	private final Map<String, Object> _variableValues = new HashMap<>();

}