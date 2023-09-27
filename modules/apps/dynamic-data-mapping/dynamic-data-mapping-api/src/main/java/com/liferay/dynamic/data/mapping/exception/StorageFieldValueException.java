/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class StorageFieldValueException extends StorageException {

	public StorageFieldValueException() {
	}

	public StorageFieldValueException(String msg) {
		super(msg);
	}

	public StorageFieldValueException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public StorageFieldValueException(Throwable throwable) {
		super(throwable);
	}

	public static class RequiredValue extends StorageFieldValueException {

		public RequiredValue(String fieldName) {
			super(String.format("No value defined for field %s", fieldName));

			_fieldName = fieldName;
		}

		public String getFieldName() {
			return _fieldName;
		}

		private final String _fieldName;

	}

}