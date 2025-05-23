/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.constant.v1_0;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.annotation.Generated;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public enum StringTestEntity {

	VALUE("value");

	@JsonCreator
	public static StringTestEntity fromString(String value) {
		if ((value == null) || value.equals("")) {
			return null;
		}

		for (StringTestEntity stringTestEntity : values()) {
			if (Objects.equals(stringTestEntity.getValue(), value)) {
				return stringTestEntity;
			}
		}

		throw new IllegalArgumentException("Invalid enum value: " + value);
	}

	@JsonValue
	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private StringTestEntity(String value) {
		_value = value;
	}

	private final String _value;

}