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

package com.liferay.portal.workflow.kaleo.definition;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;

import java.util.Objects;

/**
 * @author Michael C. Han
 */
public enum RecipientType {

	ADDRESS("address"), ASSIGNEES("assignees"), ROLE(Role.class.getName()),
	SCRIPT("script"), USER(User.class.getName());

	public static RecipientType parse(String value) {
		if (Objects.equals(ADDRESS.getValue(), value)) {
			return ADDRESS;
		}
		else if (Objects.equals(ASSIGNEES.getValue(), value)) {
			return ASSIGNEES;
		}
		else if (Objects.equals(ROLE.getValue(), value)) {
			return ROLE;
		}
		else if (Objects.equals(SCRIPT.getValue(), value)) {
			return SCRIPT;
		}
		else if (Objects.equals(USER.getValue(), value)) {
			return USER;
		}
		else {
			throw new IllegalArgumentException("Invalid value " + value);
		}
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private RecipientType(String value) {
		_value = value;
	}

	private final String _value;

}