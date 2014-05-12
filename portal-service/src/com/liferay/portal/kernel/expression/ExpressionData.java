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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class ExpressionData {

	public ExpressionData(String originalExpression) {
		_originalExpression = originalExpression;
	}

	public void addVariable(
		Class<?> variableType, String variableName, Object variableValue) {

		_variableTypes.add(variableType);
		_variableNames.add(variableName);
		_variableValues.add(variableValue);
	}

	public String getOriginalExpression() {
		return _originalExpression;
	}

	public String getRewritedExpression() {
		if (_rewritedExpression == null) {
			return _originalExpression;
		}

		return _rewritedExpression;
	}

	public String[] getVariableNames() {
		return _variableNames.toArray(new String[_variableNames.size()]);
	}

	public Class<?>[] getVariableTypes() {
		return _variableTypes.toArray(new Class<?>[_variableTypes.size()]);
	}

	public Object[] getVariableValues() {
		return _variableValues.toArray();
	}

	public void setRewritedExpression(String rewritedExpression) {
		_rewritedExpression = rewritedExpression;
	}

	private String _originalExpression;
	private String _rewritedExpression;
	private List<String> _variableNames = new ArrayList<String>();;
	private List<Class<?>> _variableTypes = new ArrayList<Class<?>>();
	private List<Object> _variableValues = new ArrayList<Object>();

}