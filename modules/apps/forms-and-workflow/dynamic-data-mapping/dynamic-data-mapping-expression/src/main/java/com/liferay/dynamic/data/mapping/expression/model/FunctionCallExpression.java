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

package com.liferay.dynamic.data.mapping.expression.model;

import aQute.bnd.annotation.ProviderType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leonardo Barros
 */
@ProviderType
public class FunctionCallExpression extends Expression {

	public FunctionCallExpression(String name, int numberOfParameters) {
		_name = name;
		_numberOfParameters = numberOfParameters;
	}

	public String getName() {
		return _name;
	}

	public int getNumberOfParameters() {
		return _numberOfParameters;
	}

	public List<Expression> getParameters() {
		return _parameters;
	}

	public void setParameters(List<Expression> parameters) {
		_parameters.addAll(parameters);
	}

	private final String _name;
	private final int _numberOfParameters;
	private final List<Expression> _parameters = new ArrayList<>();

}