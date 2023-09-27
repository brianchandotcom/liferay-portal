/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.storage;

import java.util.Objects;

/**
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
public enum StorageType {

	DEFAULT("default"),

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced with {@link #DEFAULT}
	 */
	@Deprecated
	JSON("json");

	public static StorageType parse(String value) {
		if (Objects.equals(DEFAULT.getValue(), value)) {
			return DEFAULT;
		}

		if (Objects.equals(JSON.getValue(), value)) {
			return JSON;
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

	private StorageType(String value) {
		_value = value;
	}

	private final String _value;

}