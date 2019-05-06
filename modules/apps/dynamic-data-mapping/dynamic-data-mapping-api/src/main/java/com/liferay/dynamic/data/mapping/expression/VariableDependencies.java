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

import java.util.ArrayList;
import java.util.List;

/**
 * @author     Miguel Angelo Caldas Gallindo
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class VariableDependencies {

	public VariableDependencies(String variableName) {
		_variableName = variableName;
	}

	public void addAffectedVariable(String variableName) {
		_affectedVariableNames.add(variableName);
	}

	public void addRequiredVariable(String variableName) {
		_requiredVariableNames.add(variableName);
	}

	public List<String> getAffectedVariableNames() {
		return _affectedVariableNames;
	}

	public List<String> getRequiredVariableNames() {
		return _requiredVariableNames;
	}

	public String getVariableName() {
		return _variableName;
	}

	private final List<String> _affectedVariableNames = new ArrayList<>();
	private final List<String> _requiredVariableNames = new ArrayList<>();
	private final String _variableName;

}