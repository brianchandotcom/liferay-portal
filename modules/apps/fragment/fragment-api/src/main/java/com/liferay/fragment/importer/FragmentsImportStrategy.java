/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.importer;

import com.liferay.portal.kernel.util.Validator;
import java.util.Objects; /**
 * @author Mikel Lorza
 */
public enum FragmentsImportStrategy {

	DO_NOT_IMPORT("do_not_import"), DO_NOT_OVERWRITE("do_not_overwrite"),
	KEEP_BOTH("keep_both"), OVERWRITE("overwrite");

	public static FragmentsImportStrategy create(String value) {
		if (Validator.isNull(value)) {
			return null;
		}

		for (FragmentsImportStrategy fragmentsImportStrategy : values()) {
			if (Objects.equals(fragmentsImportStrategy.getValue(), value)) {
				return fragmentsImportStrategy;
			}
		}

		return null;
	}

	public String getValue() {
		return _value;
	}

	private FragmentsImportStrategy(String value) {
		_value = value;
	}

	private final String _value;

}