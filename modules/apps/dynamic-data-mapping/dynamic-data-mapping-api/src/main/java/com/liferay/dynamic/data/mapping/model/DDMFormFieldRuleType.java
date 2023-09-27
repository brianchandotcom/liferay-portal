/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author     Leonardo Barros
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public enum DDMFormFieldRuleType implements Serializable {

	DATA_PROVIDER("DATA_PROVIDER"), READ_ONLY("READ_ONLY"),
	VALIDATION("VALIDATION"), VALUE("VALUE"), VISIBILITY("VISIBILITY");

	public static DDMFormFieldRuleType parse(String value) {
		if (Objects.equals(DATA_PROVIDER.getValue(), value)) {
			return DATA_PROVIDER;
		}
		else if (Objects.equals(READ_ONLY.getValue(), value)) {
			return READ_ONLY;
		}
		else if (Objects.equals(VALUE.getValue(), value)) {
			return VALUE;
		}
		else if (Objects.equals(VALIDATION.getValue(), value)) {
			return VALIDATION;
		}
		else if (Objects.equals(VISIBILITY.getValue(), value)) {
			return VISIBILITY;
		}

		throw new IllegalArgumentException("Invalid value " + value);
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private DDMFormFieldRuleType(String value) {
		_value = value;
	}

	private final String _value;

}