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

package com.liferay.portal.expression;

import java.util.Map;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public interface Expression<T> {

	public T evaluate() throws ExpressionEvaluationException;

	public Map<String, VariableDependencies> getVariableDependenciesMap();

	public void setBooleanVariableValue(
		String variableName, Boolean variableValue);

	public void setDoubleVariableValue(
		String variableName, Double variableValue);

	public void setExpressionStringVariableValue(
		String variableName, String variableValue, Class<?> variableClass);

	public void setFloatVariableValue(String variableName, Float variableValue);

	public void setIntegerVariableValue(
		String variableName, Integer variableValue);

	public void setLongVariableValue(String variableName, Long variableValue);

	public void setStringVariableValue(
		String variableName, String variableValue);

	public void setVariableValue(
		String variableName, Object variableValue, Class<?> variableClass);

}