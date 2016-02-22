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
import com.liferay.dynamic.data.mapping.expression.DDMExpressionEvaluationException;
import com.liferay.dynamic.data.mapping.expression.VariableDependencies;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class DDMExpressionImpl<T> implements DDMExpression<T> {

	public DDMExpressionImpl(
		String expressionString, Class<T> expressionClass) {

		List<String> stringConstants = _stringConstantsExtractor.extract(
			expressionString);

		Map<String, String> stringConstantsMap = new HashMap<>();

		for (String stringConstant : stringConstants) {
			String variableName = StringUtil.randomId();

			stringConstantsMap.put(variableName, stringConstant);

			expressionString = StringUtil.replace(
				expressionString, "\"" + stringConstant + "\"", variableName);
		}

		List<String> variableNames = _variableNamesExtractor.extract(
			expressionString);

		for (String variableName : variableNames) {
			Variable variable = new Variable(variableName);

			String stringConstant = stringConstantsMap.get(variableName);

			if (stringConstant != null) {
				variable.setValue(encode(stringConstant));
			}

			_variables.put(variableName, variable);
		}

		_expressionString = expressionString;
		_expressionClass = expressionClass;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T evaluate() throws DDMExpressionEvaluationException {
		try {
			com.udojava.evalex.Expression expression =
				new com.udojava.evalex.Expression(_expressionString);

			for (Map.Entry<String, Variable> entry : _variables.entrySet()) {
				BigDecimal variableValue = getVariableValue(entry.getValue());

				expression.setVariable(entry.getKey(), variableValue);
			}

			BigDecimal result = evaluate(expression);

			return (T)toRetunType(result);
		}
		catch (Exception e) {
			throw new DDMExpressionEvaluationException(e);
		}
	}

	@Override
	public Map<String, VariableDependencies> getVariableDependenciesMap() {
		Map<String, VariableDependencies> variableDependenciesMap =
			new HashMap<>();

		for (Variable variable : _variables.values()) {
			populateVariableDependenciesMap(variable, variableDependenciesMap);
		}

		return variableDependenciesMap;
	}

	@Override
	public void setBooleanVariableValue(
		String variableName, Boolean variableValue) {

		setVariableValue(variableName, encode(variableValue));
	}

	@Override
	public void setDoubleVariableValue(
		String variableName, Double variableValue) {

		setVariableValue(variableName, new BigDecimal(variableValue));
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

		setVariableValue(variableName, new BigDecimal(variableValue));
	}

	@Override
	public void setIntegerVariableValue(
		String variableName, Integer variableValue) {

		setVariableValue(variableName, new BigDecimal(variableValue));
	}

	@Override
	public void setLongVariableValue(String variableName, Long variableValue) {
		setVariableValue(variableName, new BigDecimal(variableValue));
	}

	@Override
	public void setMathContext(MathContext mathContext) {
		_mathContext = mathContext;
	}

	@Override
	public void setStringVariableValue(
		String variableName, String variableValue) {

		setVariableValue(variableName, encode(variableValue));
	}

	protected Boolean decodeBoolean(BigDecimal bigDecimal) {
		if (bigDecimal.equals(BigDecimal.ONE)) {
			return Boolean.TRUE;
		}
		else {
			return Boolean.FALSE;
		}
	}

	protected String decodeString(BigDecimal bigDecimal) {
		if (bigDecimal.equals(BigDecimal.ZERO)) {
			return StringPool.BLANK;
		}

		BigInteger bigInteger = new BigInteger(bigDecimal.toPlainString());

		return new String(bigInteger.toByteArray());
	}

	protected BigDecimal encode(Boolean variableValue) {
		if (variableValue.equals(Boolean.TRUE)) {
			return BigDecimal.ONE;
		}

		return BigDecimal.ZERO;
	}

	protected BigDecimal encode(String variableValue) {
		if (Validator.isNull(variableValue)) {
			return BigDecimal.ZERO;
		}

		BigInteger bigInteger = new BigInteger(variableValue.getBytes());

		return new BigDecimal(bigInteger);
	}

	protected BigDecimal evaluate(com.udojava.evalex.Expression expression) {
		setExpressionCustomFunctions(expression);
		setExpressionMathContext(expression);

		return expression.eval();
	}

	protected com.udojava.evalex.Expression getExpression(
		String expressionString) {

		com.udojava.evalex.Expression expression =
			new com.udojava.evalex.Expression(expressionString);

		List<String> variableNames = _variableNamesExtractor.extract(
			expressionString);

		for (String variableName : variableNames) {
			Variable variable = _variables.get(variableName);

			BigDecimal variableValue = getVariableValue(variable);

			expression.setVariable(variableName, variableValue);
		}

		return expression;
	}

	protected com.udojava.evalex.Expression getExpression(Variable variable) {
		if (variable.getExpressionString() == null) {
			return null;
		}

		com.udojava.evalex.Expression expression = getExpression(
			variable.getExpressionString());

		return expression;
	}

	protected BigDecimal getVariableValue(Variable variable) {
		BigDecimal variableValue = _variableValues.get(variable.getName());

		if (variableValue != null) {
			return variableValue;
		}

		com.udojava.evalex.Expression expression = getExpression(variable);

		if (expression == null) {
			return variable.getValue();
		}

		variableValue = evaluate(expression);

		_variableValues.put(variable.getName(), variableValue);

		return variableValue;
	}

	protected boolean isStringBlank(BigDecimal... bigDecimals) {
		for (BigDecimal bigDecimal : bigDecimals) {
			if (!bigDecimal.equals(BigDecimal.ZERO)) {
				return false;
			}
		}

		return true;
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

	protected void setExpressionCustomFunctions(
		com.udojava.evalex.Expression expression) {

		expression.addFunction(
			expression.new Function("between", 3) {

				@Override
				public BigDecimal eval(List<BigDecimal> parameters) {
					BigDecimal parameter = parameters.get(0);

					BigDecimal minParameter = parameters.get(1);
					BigDecimal maxParameter = parameters.get(2);

					if ((parameter.compareTo(minParameter) >= 0) &&
						(parameter.compareTo(maxParameter) <= 0)) {

						return BigDecimal.ONE;
					}

					return BigDecimal.ZERO;
				}

			});

		expression.addFunction(
			expression.new Function("concat", -1) {

				@Override
				public BigDecimal eval(List<BigDecimal> parameters) {
					StringBundler sb = new StringBundler(parameters.size());

					for (BigDecimal parameter : parameters) {
						if (isStringBlank(parameter)) {
							continue;
						}

						String string = decodeString(parameter);

						sb.append(string);
					}

					if (sb.index() == 0) {
						return BigDecimal.ZERO;
					}

					return encode(sb.toString());
				}

			});

		expression.addFunction(
			expression.new Function("contains", 2) {

				@Override
				public BigDecimal eval(List<BigDecimal> parameters) {
					BigDecimal parameter1 = parameters.get(0);
					BigDecimal parameter2 = parameters.get(1);

					if (isStringBlank(parameter1, parameter2)) {
						return BigDecimal.ONE;
					}

					String string1 = decodeString(parameter1);
					String string2 = decodeString(parameter2);

					if (string1.contains(string2)) {
						return BigDecimal.ONE;
					}

					return BigDecimal.ZERO;
				}

			});

		expression.addFunction(
			expression.new Function("equals", 2) {

				@Override
				public BigDecimal eval(List<BigDecimal> parameters) {
					BigDecimal parameter1 = parameters.get(0);
					BigDecimal parameter2 = parameters.get(1);

					if (isStringBlank(parameter1, parameter2)) {
						return BigDecimal.ONE;
					}

					String string1 = decodeString(parameter1);
					String string2 = decodeString(parameter2);

					if (string1.equals(string2)) {
						return BigDecimal.ONE;
					}

					return BigDecimal.ZERO;
				}

			});

		expression.addFunction(
			expression.new Function("isEmailAddress", 1) {

				@Override
				public BigDecimal eval(List<BigDecimal> parameters) {
					String string = decodeString(parameters.get(0));

					if (Validator.isEmailAddress(string)) {
						return BigDecimal.ONE;
					}

					return BigDecimal.ZERO;
				}

			});

		expression.addFunction(
			expression.new Function("isURL", 1) {

				@Override
				public BigDecimal eval(List<BigDecimal> parameters) {
					String string = decodeString(parameters.get(0));

					if (Validator.isUrl(string)) {
						return BigDecimal.ONE;
					}

					return BigDecimal.ZERO;
				}

			});

		expression.addFunction(
			expression.new Function("sum", -1) {

				@Override
				public BigDecimal eval(List<BigDecimal> parameters) {
					BigDecimal sum = new BigDecimal(0);

					for (BigDecimal parameter : parameters) {
						sum = sum.add(parameter);
					}

					return sum;
				}

			});
	}

	protected void setExpressionMathContext(
		com.udojava.evalex.Expression expression) {

		expression.setPrecision(_mathContext.getPrecision());
		expression.setRoundingMode(_mathContext.getRoundingMode());
	}

	protected void setVariableValue(
		String variableName, BigDecimal variableValue) {

		Variable variable = _variables.get(variableName);

		if (variable == null) {
			return;
		}

		variable.setValue(variableValue);
	}

	protected Object toRetunType(BigDecimal result) {
		if (_expressionClass.isAssignableFrom(Boolean.class)) {
			return decodeBoolean(result);
		}
		else if (_expressionClass.isAssignableFrom(Double.class)) {
			return result.doubleValue();
		}
		else if (_expressionClass.isAssignableFrom(Float.class)) {
			return result.floatValue();
		}
		else if (_expressionClass.isAssignableFrom(Integer.class)) {
			return result.intValue();
		}
		else if (_expressionClass.isAssignableFrom(Long.class)) {
			return result.longValue();
		}
		else {
			return decodeString(result);
		}
	}

	private final Class<?> _expressionClass;
	private final String _expressionString;
	private MathContext _mathContext = MathContext.UNLIMITED;
	private final StringConstantsExtractor _stringConstantsExtractor =
		new StringConstantsExtractor();
	private final VariableNamesExtractor _variableNamesExtractor =
		new VariableNamesExtractor();
	private final Map<String, Variable> _variables = new TreeMap<>();
	private final Map<String, BigDecimal> _variableValues = new HashMap<>();

}