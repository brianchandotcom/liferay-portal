/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

	public StorageFieldValueException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public StorageFieldValueException(Throwable cause) {
		super(cause);
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