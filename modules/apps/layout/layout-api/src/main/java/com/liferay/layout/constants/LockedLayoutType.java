/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.constants;

import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author Mikel Lorza
 */
public enum LockedLayoutType {

	COLLECTION_PAGE("collection-page"), CONTENT_PAGE("content-page"),
	CONTENT_PAGE_TEMPLATE("content-page-template"),
	DISPLAY_PAGE_TEMPLATE("display-page-template"), MASTER_PAGE("master-page"),
	UTILITY_PAGE("utility-page");

	public static LockedLayoutType create(String value) {
		if (Validator.isNull(value)) {
			return null;
		}

		for (LockedLayoutType lockedLayoutType : values()) {
			if (Objects.equals(lockedLayoutType.getValue(), value)) {
				return lockedLayoutType;
			}
		}

		return null;
	}

	public String getValue() {
		return _value;
	}

	private LockedLayoutType(String value) {
		_value = value;
	}

	private final String _value;

}