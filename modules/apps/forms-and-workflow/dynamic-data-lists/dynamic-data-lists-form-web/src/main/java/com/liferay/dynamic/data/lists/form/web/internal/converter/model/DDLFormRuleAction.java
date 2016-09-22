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

package com.liferay.dynamic.data.lists.form.web.internal.converter.model;

import com.liferay.portal.kernel.util.HashUtil;

import java.util.Objects;

/**
 * @author Marcellus Tavares
 */
public class DDLFormRuleAction {

	public DDLFormRuleAction() {
	}

	public DDLFormRuleAction(String action, String target) {
		_action = action;
		_target = target;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDLFormRuleAction)) {
			return false;
		}

		DDLFormRuleAction ddlFormRuleAction = (DDLFormRuleAction)obj;

		if (Objects.equals(_action, ddlFormRuleAction._action) &&
			Objects.equals(_target, ddlFormRuleAction._target)) {

			return true;
		}

		return false;
	}

	public String getAction() {
		return _action;
	}

	public String getTarget() {
		return _target;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _action);

		return HashUtil.hash(hash, _target);
	}

	public void setAction(String action) {
		_action = action;
	}

	public void setTarget(String target) {
		_target = target;
	}

	private String _action;
	private String _target;

}