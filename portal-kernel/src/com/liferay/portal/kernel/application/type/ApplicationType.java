/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.application.type;

import java.util.Objects;

/**
 * @author Juergen Kappler
 */
public enum ApplicationType {

	FULL_PAGE_APPLICATION("full-page-application"), WIDGET("widget");

	public static ApplicationType parse(String value) {
		if (Objects.equals(FULL_PAGE_APPLICATION.getValue(), value)) {
			return FULL_PAGE_APPLICATION;
		}
		else if (Objects.equals(WIDGET.getValue(), value)) {
			return WIDGET;
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

	private ApplicationType(String value) {
		_value = value;
	}

	private final String _value;

}