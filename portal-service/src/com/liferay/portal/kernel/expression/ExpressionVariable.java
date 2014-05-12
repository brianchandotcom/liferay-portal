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

package com.liferay.portal.kernel.expression;

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
		_calculatedValue = calculatedValue;
	}

	public void setDataType(Class<?> dataType) {
		_dataType = dataType;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setValueExpression(String valueExpression) {
		_valueExpression = valueExpression;
	}

	private String _calculatedValue;
	private Class<?> _dataType;
	private String _name;
	private String _valueExpression;

}