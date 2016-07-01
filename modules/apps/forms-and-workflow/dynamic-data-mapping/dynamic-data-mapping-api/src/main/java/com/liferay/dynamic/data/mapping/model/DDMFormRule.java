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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public class DDMFormRule implements Serializable {

	public DDMFormRule(DDMFormRule ddmFormRule) {
		_condition = ddmFormRule._condition;
		_enabled = ddmFormRule._enabled;
		_message = ddmFormRule._message;

		for (String action : ddmFormRule._actions) {
			addAction(action);
		}
	}

	public DDMFormRule(
		List<String> actions, String condition, boolean enabled,
		String message) {

		_actions = actions;
		_condition = condition;
		_enabled = enabled;
		_message = message;
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

	public String getMessage() {
		return _message;
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

	public void setMessage(String message) {
		_message = message;
	}

	private List<String> _actions = new ArrayList<>();
	private String _condition;
	private boolean _enabled = true;
	private String _message;

}