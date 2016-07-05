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

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class Variable {

	public Variable(String name) {
		_name = name;
	}

	public String getExpressionString() {
		return _expressionString;
	}

	public String getName() {
		return _name;
	}

	public Object getValue() {
		return _value;
	}

	public void setExpressionString(String expressionString) {
		_expressionString = expressionString;
	}

	public void setValue(Object value) {
		_value = value;
	}

	private String _expressionString;
	private final String _name;
	private Object _value;

}