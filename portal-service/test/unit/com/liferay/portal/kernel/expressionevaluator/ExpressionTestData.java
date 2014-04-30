
package com.liferay.portal.kernel.expressionevaluator;

import com.liferay.portal.kernel.expressionevaluator.model.ExpressionVariable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class ExpressionTestData {

	public static ExpressionTestData newExpressionTestData(String expression) {

		return new ExpressionTestData(expression);
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

	public ExpressionTestData usingBooleanVariable(String name,
		String expression, Boolean value) {

		usingVariable(name, expression, Boolean.class, value);

		return this;
	}

	public ExpressionTestData usingDoubleVariable(String name,
		String expression, Double value) {

		usingVariable(name, expression, Double.class, value);

		return this;
	}

	public ExpressionTestData usingFloatVariable(String name,
		String expression, Float value) {

		usingVariable(name, expression, Float.class, value);

		return this;
	}

	public ExpressionTestData usingIntegerVariable(String name,
		String expression, Integer value) {

		usingVariable(name, expression, Integer.class, value);

		return this;
	}

	public ExpressionTestData usingLongVariable(String name,
		String expression, Long value) {

		usingVariable(name, expression, Long.class, value);

		return this;
	}

	public ExpressionTestData usingStringVariable(String name,
		String expression, String value) {

		usingVariable(name, expression, String.class, value);

		return this;
	}

	public ExpressionTestData withExpectedResult(Object value) {

		this._expectedResult = value;

		return this;
	}

	public ExpressionTestData withReturnType(Class<?> returnType) {

		this._returnType = returnType;

		return this;
	}

	private ExpressionTestData(String expression) {

		this._expression = expression;
		_variables = new HashMap<String, ExpressionVariable>();
	}

	private void usingVariable(String name,
		String expression, Class<?> type, Object value) {
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
	private final Map<String, ExpressionVariable> _variables;

}