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
public final class DDMFormValidatorError {

	public String getErrorMessage() {
		return _errorMessage;
	}

	public DDMFormValidatorErrorStatus getErrorStatus() {
		return _errorStatus;
	}

	public Map<String, Object> getProperties() {
		return Collections.unmodifiableMap(_properties);
	}

	public static class Builder {

		public static Builder newBuilder(
			String errorMessage, DDMFormValidatorErrorStatus errorStatus) {

			return new Builder(errorMessage, errorStatus);
		}

		public static DDMFormValidatorError of(
			String errorMessage, DDMFormValidatorErrorStatus errorStatus) {

			return newBuilder(
				errorMessage, errorStatus
			).build();
		}

		public DDMFormValidatorError build() {
			return _validateFormError;
		}

		public Builder withProperty(String key, Object value) {
			_validateFormError._properties.put(key, value);

			return this;
		}

		private Builder(
			String errorMessage, DDMFormValidatorErrorStatus errorStatus) {

			_validateFormError._errorMessage = errorMessage;
			_validateFormError._errorStatus = errorStatus;
		}

		private final DDMFormValidatorError _validateFormError =
			new DDMFormValidatorError();

	}

	private DDMFormValidatorError() {
	}

	private String _errorMessage;
	private DDMFormValidatorErrorStatus _errorStatus;
	private final Map<String, Object> _properties = new HashMap<>();

}