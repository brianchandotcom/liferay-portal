/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.display.contributor.field;

import java.util.Objects;

/**
 * @author Jürgen Kappler
 */
public enum InfoDisplayContributorFieldType {

	IMAGE("image"), TEXT("text"), URL("url");

	public static InfoDisplayContributorFieldType parse(String value) {
		if (Objects.equals(IMAGE.getValue(), value)) {
			return IMAGE;
		}
		else if (Objects.equals(TEXT.getValue(), value)) {
			return TEXT;
		}
		else if (Objects.equals(URL.getValue(), value)) {
			return URL;
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

	private InfoDisplayContributorFieldType(String value) {
		_value = value;
	}

	private final String _value;

}