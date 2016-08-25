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

package com.liferay.dynamic.data.mapping.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Leonardo Barros
 */
@ProviderType
public class DDMFormRule implements Serializable {

	public DDMFormRule() {
	}

	public DDMFormRule(DDMFormRule ddmFormRule) {
		_condition = ddmFormRule._condition;
		_actions.addAll(ddmFormRule.getActions());
		_enabled = ddmFormRule._enabled;
	}

	public DDMFormRule(String condition, List<String> actions) {
		_condition = condition;
		_actions.addAll(actions);
	}

	public DDMFormRule(String condition, String... actions) {
		_condition = condition;

		_actions.addAll(Arrays.asList(actions));
	}

	public void addAction(String action) {
		_actions.add(action);
	}

	public List<String> getActions() {
		return _actions;
	}

	public String getCondition() {
		return _condition;
	}

	public boolean isEnabled() {
		return _enabled;
	}

	public void setActions(List<String> actions) {
		_actions = actions;
	}

	public void setCondition(String condition) {
		_condition = condition;
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	private List<String> _actions = new ArrayList<>();
	private String _condition;
	private boolean _enabled = true;

}