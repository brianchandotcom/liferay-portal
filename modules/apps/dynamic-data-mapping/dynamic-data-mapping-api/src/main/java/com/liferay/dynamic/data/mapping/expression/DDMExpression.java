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

package com.liferay.dynamic.data.mapping.expression;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.expression.model.Expression;

import java.util.Map;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
@ProviderType
public interface DDMExpression<T> {

	public T evaluate() throws DDMExpressionException;

	public Expression getModel();

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public Map<String, VariableDependencies> getVariableDependenciesMap()
		throws DDMExpressionException;

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setBooleanVariableValue(
		String variableName, Boolean variableValue);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setDDMExpressionFunction(
		String functionName, DDMExpressionFunction ddmExpressionFunction);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setDoubleVariableValue(
		String variableName, Double variableValue);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setExpressionStringVariableValue(
		String variableName, String variableValue);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setFloatVariableValue(String variableName, Float variableValue);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setIntegerVariableValue(
		String variableName, Integer variableValue);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setLongVariableValue(String variableName, Long variableValue);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setNumberVariableValue(
		String variableName, Number variableValue);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setObjectVariableValue(
		String variableName, Object variableValue);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setStringVariableValue(
			String variableName, String variableValue)
		throws DDMExpressionException;

}