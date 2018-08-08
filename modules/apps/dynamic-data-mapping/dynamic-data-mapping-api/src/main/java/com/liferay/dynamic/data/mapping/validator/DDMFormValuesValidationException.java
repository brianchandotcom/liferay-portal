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

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.petra.string.StringPool;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Marcellus Tavares
 */
@ProviderType
public class DDMFormValuesValidationException extends StorageException {

	public DDMFormValuesValidationException() {
	}

	public DDMFormValuesValidationException(
		List<DDMFormValuesValidatorError> ddmFormValuesValidatorErrors) {

		_ddmFormValuesValidatorErrors = ddmFormValuesValidatorErrors;
	}

	public DDMFormValuesValidationException(String msg) {
		super(msg);
	}

	public DDMFormValuesValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DDMFormValuesValidationException(Throwable cause) {
		super(cause);
	}

	public List<DDMFormValuesValidatorError> getDDMFormValuesValidatorErrors() {
		return _ddmFormValuesValidatorErrors;
	}

	@Override
	public String getMessage() {
		if (_ddmFormValuesValidatorErrors == null) {
			return super.getMessage();
		}

		Stream<DDMFormValuesValidatorError> stream =
			_ddmFormValuesValidatorErrors.stream();

		return stream.map(
			DDMFormValuesValidatorError::getErrorMessage
		).collect(
			Collectors.joining(StringPool.NEW_LINE)
		);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no replacement
	 */
	@Deprecated
	public static class MustNotSetValue
		extends DDMFormValuesValidationException {

		public MustNotSetValue(String fieldName) {
			super(
				String.format(
					"Value should not be set for transient field name %s",
					fieldName));

			_fieldName = fieldName;
		}

		public String getFieldName() {
			return _fieldName;
		}

		private String _fieldName;

	}

	/**
	 * @deprecated As of Judson (7.1.x), with no replacement
	 */
	@Deprecated
	public static class MustSetValidAvailableLocales
		extends DDMFormValuesValidationException {

		public MustSetValidAvailableLocales(String fieldName) {
			super(
				String.format(
					"Invalid available locales set for field name %s",
					fieldName));

			_fieldName = fieldName;
		}

		public String getFieldName() {
			return _fieldName;
		}

		private String _fieldName;

	}

	/**
	 * @deprecated As of Judson (7.1.x), with no replacement
	 */
	@Deprecated
	public static class MustSetValidDefaultLocale
		extends DDMFormValuesValidationException {

		public MustSetValidDefaultLocale(String fieldName) {
			super(
				String.format(
					"Invalid default locale set for field name %s", fieldName));

			_fieldName = fieldName;
		}

		public String getFieldName() {
			return _fieldName;
		}

		private String _fieldName;

	}

	/**
	 * @deprecated As of Judson (7.1.x), with no replacement
	 */
	@Deprecated
	public static class MustSetValidField
		extends DDMFormValuesValidationException {

		public MustSetValidField(String fieldName) {
			super(
				String.format(
					"There is no field name %s defined on form", fieldName));

			_fieldName = fieldName;
		}

		public String getFieldName() {
			return _fieldName;
		}

		private String _fieldName;

	}

	/**
	 * @deprecated As of Judson (7.1.x), with no replacement
	 */
	@Deprecated
	public static class MustSetValidValue
		extends DDMFormValuesValidationException {

		public MustSetValidValue(String fieldName) {
			super(
				String.format(
					"Invalid value set for field name %s", fieldName));

			_fieldName = fieldName;
		}

		public MustSetValidValue(String fieldName, Throwable cause) {
			super(
				String.format("Invalid value set for field name %s", fieldName),
				cause);

			_fieldName = fieldName;
		}

		public String getFieldName() {
			return _fieldName;
		}

		private String _fieldName;

	}

	/**
	 * @deprecated As of Judson (7.1.x), with no replacement
	 */
	@Deprecated
	public static class MustSetValidValuesSize
		extends DDMFormValuesValidationException {

		public MustSetValidValuesSize(String fieldName) {
			super(
				String.format(
					"Incorrect number of values set for field name %s",
					fieldName));

			_fieldName = fieldName;
		}

		public String getFieldName() {
			return _fieldName;
		}

		private String _fieldName;

	}

	/**
	 * @deprecated As of Judson (7.1.x), with no replacement
	 */
	@Deprecated
	public static class RequiredValue extends DDMFormValuesValidationException {

		public RequiredValue(String fieldName) {
			super(
				String.format("No value defined for field name %s", fieldName));

			_fieldName = fieldName;
		}

		public String getFieldName() {
			return _fieldName;
		}

		private final String _fieldName;

	}

	private List<DDMFormValuesValidatorError> _ddmFormValuesValidatorErrors;

}