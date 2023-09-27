/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.importer;

import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author Mikel Lorza
 */
public enum LayoutsImportStrategy {

	DO_NOT_IMPORT("do_not_import"), DO_NOT_OVERWRITE("do_not_overwrite"),
	KEEP_BOTH("keep_both"), OVERWRITE("overwrite");

	public static LayoutsImportStrategy create(String value) {
		if (Validator.isNull(value)) {
			return null;
		}

		for (LayoutsImportStrategy layoutsImportStrategy : values()) {
			if (Objects.equals(layoutsImportStrategy.getValue(), value)) {
				return layoutsImportStrategy;
			}
		}

		return null;
	}

	public String getValue() {
		return _value;
	}

	private LayoutsImportStrategy(String value) {
		_value = value;
	}

	private final String _value;

}