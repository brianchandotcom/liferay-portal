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

package com.liferay.dynamic.data.mapping.validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public final class DDMFormLayoutValidatorError {

	public String getErrorMessage() {
		return _errorMessage;
	}

	public Status getErrorStatus() {
		return _errorStatus;
	}

	public Map<String, Object> getProperties() {
		return Collections.unmodifiableMap(_properties);
	}

	public static class Builder {

		public static Builder newBuilder(
			String errorMessage, Status errorStatus) {

			return new Builder(errorMessage, errorStatus);
		}

		public static DDMFormLayoutValidatorError of(
			String errorMessage, Status errorStatus) {

			return newBuilder(
				errorMessage, errorStatus
			).build();
		}

		public DDMFormLayoutValidatorError build() {
			return _validateFormLayoutError;
		}

		public Builder withProperty(String key, Object value) {
			_validateFormLayoutError._properties.put(key, value);

			return this;
		}

		private Builder(String errorMessage, Status errorStatus) {
			_validateFormLayoutError._errorMessage = errorMessage;
			_validateFormLayoutError._errorStatus = errorStatus;
		}

		private final DDMFormLayoutValidatorError _validateFormLayoutError =
			new DDMFormLayoutValidatorError();

	}

	public enum Status {

		INVALID_COLUMN_SIZE_EXCEPTION, INVALID_ROW_SIZE_EXCEPTION,
		MUST_NOT_DUPLICATE_FIELD_NAME_EXCEPTION,
		MUST_SET_DEFAULT_LOCALE_EXCEPTION,
		MUST_SET_EQUAL_LOCALE_FOR_LAYOUT_EXCEPTION

	}

	private DDMFormLayoutValidatorError() {
	}

	private String _errorMessage;
	private Status _errorStatus;
	private final Map<String, Object> _properties = new HashMap<>();

}