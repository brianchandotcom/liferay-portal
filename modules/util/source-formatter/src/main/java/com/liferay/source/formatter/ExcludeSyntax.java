/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter;

/**
 * @author Peter Shin
 */
public enum ExcludeSyntax {

	GLOB("glob"), REGEX("regex");

	public static ExcludeSyntax parse(String value) {
		String globValue = GLOB.getValue();

		if (globValue.equals(value)) {
			return GLOB;
		}

		String regexValue = REGEX.getValue();

		if (regexValue.equals(value)) {
			return REGEX;
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

	private ExcludeSyntax(String value) {
		_value = value;
	}

	private final String _value;

}