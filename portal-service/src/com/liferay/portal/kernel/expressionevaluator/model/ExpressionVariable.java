
package com.liferay.portal.kernel.expressionevaluator.model;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class ExpressionVariable {

	public String getCalculatedValue() {

		return _calculatedValue;
	}

	public Class<?> getDataType() {

		return _dataType;
	}

	public String getName() {

		return _name;
	}

	public String getValueExpression() {

		return _valueExpression;
	}

	public void setCalculatedValue(String calculatedValue) {

		this._calculatedValue = calculatedValue;
	}

	public void setDataType(Class<?> dataType) {

		this._dataType = dataType;
	}

	public void setName(String name) {

		this._name = name;
	}

	public void setValueExpression(String valueExpression) {

		this._valueExpression = valueExpression;
	}

	private String _calculatedValue;
	private Class<?> _dataType;
	private String _name;
	private String _valueExpression;
}