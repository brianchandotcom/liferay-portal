/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;

import java.util.Objects;

/**
 * @author Michael C. Han
 */
public enum RecipientType {

	ADDRESS("address"), ASSIGNEES("assignees"), ROLE(Role.class.getName()),
	SCRIPT("script"), USER(User.class.getName());

	public static RecipientType parse(String value)
		throws KaleoDefinitionValidationException {

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

		throw new KaleoDefinitionValidationException.InvalidRecipientType(
			value);
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