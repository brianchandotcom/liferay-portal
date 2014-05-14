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

import java.util.Map;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public interface Expression<T> {

	public void addBooleanVariable(String variableName, Boolean variableValue);

	public void addDoubleVariable(String variableName, Double variableValue);

	public void addExpressionVariable(
		String variableName, Class<?> variableType,
		String variableValueExpresion);

	public void addFloatVariable(String variableName, Float variableValue);

	public void addIntegerVariable(String variableName, Integer variableValue);

	public void addLongVariable(String variableName, Long variableValue);

	public void addStringVariable(String variableName, String variableValue);

	public void addVariable(
		String variableName, Class<?> variableType, Object variableValue);

	public T evaluate() throws ExpressionEvaluationException;

	public Map<String, VariableDependencies> getVariableDependenciesMap();

	public String[] getVariableNames();

}