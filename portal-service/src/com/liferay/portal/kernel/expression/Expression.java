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

	public void addBooleanVariable(String name, Boolean value);

	public void addDoubleVariable(String name, Double value);

	public void addExpressionVariable(
		String name, Class<?> variableType, String valueExpresion);

	public void addFloatVariable(String name, Float value);

	public void addIntegerVariable(String name, Integer value);

	public void addLongVariable(String name, Long value);

	public void addStringVariable(String name, String value);

	public void addVariable(String name, Class<?> variableType, Object value);

	public T evaluate() throws ExpressionEvaluationException;

	public Map<String, VariableDependencies> getVariableDependenciesMap();

}