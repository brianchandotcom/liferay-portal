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

import com.liferay.petra.lang.HashUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class DDMFormRule implements Serializable {

	public DDMFormRule() {
	}

	public DDMFormRule(DDMFormRule ddmFormRule) {
		_actions = new ArrayList<>(ddmFormRule._actions);
		_condition = ddmFormRule._condition;
		_enabled = ddmFormRule._enabled;
		_name = ddmFormRule._name;
	}

	public DDMFormRule(List<String> actions, String condition) {
		_actions = actions;
		_condition = condition;
	}

	public DDMFormRule(
		List<String> actions, String condition, LocalizedValue name) {

		_actions = actions;
		_condition = condition;
		_name = name;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #DDMFormRule(List, String)}
	 */
	@Deprecated
	public DDMFormRule(String condition, List<String> actions) {
		_condition = condition;
		_actions = actions;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #DDMFormRule(List, String, LocalizedValue)}
	 */
	@Deprecated
	public DDMFormRule(
		String condition, List<String> actions, LocalizedValue name) {

		_condition = condition;
		_actions = actions;
		_name = name;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #DDMFormRule(List, String)}
	 */
	@Deprecated
	public DDMFormRule(String condition, String... actions) {
		this(Arrays.asList(actions), condition);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormRule)) {
			return false;
		}

		DDMFormRule ddmFormRule = (DDMFormRule)obj;

		if (Objects.equals(_actions, ddmFormRule._actions) &&
			Objects.equals(_condition, ddmFormRule._condition) &&
			Objects.equals(_enabled, ddmFormRule._enabled) &&
			Objects.equals(_name, ddmFormRule._name)) {

			return true;
		}

		return false;
	}

	public List<String> getActions() {
		return _actions;
	}

	public String getCondition() {
		return _condition;
	}

	public LocalizedValue getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _actions);

		hash = HashUtil.hash(hash, _condition);
		hash = HashUtil.hash(hash, _enabled);

		return HashUtil.hash(hash, _name);
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

	public void setName(LocalizedValue name) {
		_name = name;
	}

	private List<String> _actions = new ArrayList<>();
	private String _condition;
	private boolean _enabled = true;
	private LocalizedValue _name;

}