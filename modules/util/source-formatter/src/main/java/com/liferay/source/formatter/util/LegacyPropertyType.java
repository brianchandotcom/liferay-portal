/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.util;

/**
 * @author Hugo Huijser
 */
public enum LegacyPropertyType {

	PORTAL("Portal"), SYSTEM("System");

	public String getValue() {
		return _value;
	}

	private LegacyPropertyType(String value) {
		_value = value;
	}

	private final String _value;

}