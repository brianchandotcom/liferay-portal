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

	private List<String> _affectedVariableNames = new ArrayList<String>();
	private List<String> _requiredVariableNames = new ArrayList<String>();
	private String _variableName;

}