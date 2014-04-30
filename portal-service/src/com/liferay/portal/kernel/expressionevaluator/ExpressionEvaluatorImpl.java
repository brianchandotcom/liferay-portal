
package com.liferay.portal.kernel.expressionevaluator;

import com.liferay.portal.kernel.expressionevaluator.model.ExpressionData;
import com.liferay.portal.kernel.expressionevaluator.model.ExpressionVariable;
import com.liferay.portal.kernel.expressionevaluator.model.VariableDependencies;

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jodd.typeconverter.TypeConverterManager;

import org.codehaus.commons.compiler.CompileException;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class ExpressionEvaluatorImpl implements ExpressionEvaluator {

	public ExpressionEvaluatorImpl(Map<String, ExpressionVariable>
		variables) {

		this._calculatedVariableValues = new HashMap<String, Object>();
		this._variableDependenciesMap =
			new HashMap<String, VariableDependencies>();
		this._variables = variables;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.liferay.portlet.dynamicdatamapping.expressions.ExpressionEvaluator
	 *      #evaluateBooleanExpression(java.lang.String)
	 */
	@Override
	public Boolean evaluateBooleanExpression(String expression)
		throws CompileException, InvocationTargetException {

		return (Boolean)evaluateExpression(expression, boolean.class);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.liferay.portlet.dynamicdatamapping.expressions.ExpressionEvaluator
	 *      #evaluateDoubleExpression(java.lang.String)
	 */
	@Override
	public Double evaluateDoubleExpression(String expression)
		throws CompileException, InvocationTargetException {

		return (Double)evaluateExpression(expression, Double.class);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.liferay.portlet.dynamicdatamapping.expressions.ExpressionEvaluator
	 *      #evaluateExpression(java.lang.String, java.lang.Class)
	 */
	@Override
	public Object evaluateExpression(String expression, Class<?> returnedType)
		throws CompileException, InvocationTargetException {

		ExpressionData expressionData = extractExpressionData(expression);

		Object res = ExpressionEvaluatorUtil.evaluate(
			expressionData.getRewritedExpression(), returnedType,
			expressionData.getVariableNames(),
			expressionData.getVariableTypes(),
			expressionData.getVariableValues()
			);

		return res;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.liferay.portlet.dynamicdatamapping.expressions.ExpressionEvaluator
	 *      #evaluateFloatExpression(java.lang.String)
	 */
	@Override
	public Float evaluateFloatExpression(String expression)
		throws CompileException, InvocationTargetException {

		return (Float)evaluateExpression(expression, Float.class);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.liferay.portlet.dynamicdatamapping.expressions.ExpressionEvaluator
	 *      #evaluateIntegerExpression(java.lang.String)
	 */
	@Override
	public Integer evaluateIntegerExpression(String expression)
		throws CompileException, InvocationTargetException {

		return (Integer)evaluateExpression(expression, Integer.class);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.liferay.portlet.dynamicdatamapping.expressions.ExpressionEvaluator
	 *      #evaluateLongExpression(java.lang.String)
	 */
	@Override
	public Long evaluateLongExpression(String expression)
		throws CompileException, InvocationTargetException {

		return (Long)evaluateExpression(expression, Long.class);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.liferay.portlet.dynamicdatamapping.expressions.ExpressionEvaluator
	 *      #evaluateStringExpression(java.lang.String)
	 */
	@Override
	public String evaluateStringExpression(String expression)
		throws CompileException, InvocationTargetException {

		return (String)evaluateExpression(expression, String.class);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.liferay.portlet.dynamicdatamapping.expressions.ExpressionEvaluator
	 *      #getDependenciesMap()
	 */
	@Override
	public Map<String, VariableDependencies> getDependenciesMap() {

		for (ExpressionVariable variable : _variables.values()) {
			populateVariableDependencies(variable);
		}

		return _variableDependenciesMap;
	}

	protected Object convertToDataType(String value, Class<?> dataType) {

		return TypeConverterManager.convertType(value, dataType);
	}

	protected ExpressionData extractExpressionData(String expression)
		throws CompileException, InvocationTargetException {

		String[] expressionVariables = extractExpressionVariables(expression);

		ExpressionData expressionData = new ExpressionData(expression);

		for (String variableName : expressionVariables) {
			ExpressionVariable variable = _variables.get(variableName);
			expressionData.addVariable(variable.getName(), variable
				.getDataType(), getVariableValue(variable));
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
		throws CompileException, InvocationTargetException {

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

		VariableDependencies variableDependencies = null;

		if (_variableDependenciesMap.containsKey(variable.getName())) {
			variableDependencies = _variableDependenciesMap.get(
				variable.getName());
		}
		else {
			variableDependencies = new VariableDependencies(variable.getName());
			_variableDependenciesMap.put(variable.getName(),
				variableDependencies);

			if (variable.getValueExpression() != null) {
				String expression = variable.getValueExpression();
				String[] expressionVariables = extractExpressionVariables(
					expression);

				for (String dependency : expressionVariables) {
					VariableDependencies populateVariableDependencies =
						populateVariableDependencies(_variables.get(dependency)
						);
					variableDependencies.addDependency(
						populateVariableDependencies.getName());
					populateVariableDependencies.addDependent(
						variableDependencies.getName());
				}
			}
		}

		return variableDependencies;
	}

	private static final Pattern VARIABLE_REGEX_PATTERN = Pattern.compile(
		"\\b([a-zA-Z]+[\\w\\._]+)(?!\\()\\b", Pattern.MULTILINE);

	private final Map<String, Object> _calculatedVariableValues;
	private final Map<String, VariableDependencies> _variableDependenciesMap;
	private final Map<String, ExpressionVariable> _variables;

}