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

import java.math.BigDecimal;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class Variable {

	public Variable(String name) {
		_name = name;
	}

	public Variable(String name, BigDecimal value) {
		_name = name;
		_value = value;
	}

	public String getExpressionString() {
		return _expressionString;
	}

	public String getName() {
		return _name;
	}

	public BigDecimal getValue() {
		return _value;
	}

	public void setExpressionString(String expressionString) {
		_expressionString = expressionString;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setValue(BigDecimal value) {
		_value = value;
	}

	private String _expressionString;
	private String _name;
	private BigDecimal _value;

}