/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.constant.v1_0;

import java.util.Objects;

import jakarta.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public enum StringTestEntity {

	VALUE("value");

	public static StringTestEntity create(String value) {
		for (StringTestEntity stringTestEntity : values()) {
			if (Objects.equals(stringTestEntity.getValue(), value)) {
				return stringTestEntity;
			}
		}

		return null;
	}

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