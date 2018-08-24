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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class DDMFormLayoutValidationException extends PortalException {

	public DDMFormLayoutValidationException() {
	}

	public DDMFormLayoutValidationException(
		List<DDMFormLayoutValidatorError> ddmFormLayoutValidatorErrors) {

		_ddmFormLayoutValidatorErrors = ddmFormLayoutValidatorErrors;
	}

	public DDMFormLayoutValidationException(String msg) {
		super(msg);
	}

	public DDMFormLayoutValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DDMFormLayoutValidationException(Throwable cause) {
		super(cause);
	}

	public List<DDMFormLayoutValidatorError> getDDMFormLayoutValidatorErrors() {
		return _ddmFormLayoutValidatorErrors;
	}

	@Override
	public String getMessage() {
		if (_ddmFormLayoutValidatorErrors == null) {
			return super.getMessage();
		}

		Stream<DDMFormLayoutValidatorError> stream =
			_ddmFormLayoutValidatorErrors.stream();

		return stream.map(
			DDMFormLayoutValidatorError::getErrorMessage
		).collect(
			Collectors.joining(StringPool.NEW_LINE)
		);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no replacement
	 */
	@Deprecated
	public static class InvalidColumnSize
		extends DDMFormLayoutValidationException {

		public InvalidColumnSize() {
			super(
				"Column size must be positive and less than maximum row size " +
					"of 12");
		}

	}

	/**
	 * @deprecated As of Judson (7.1.x), with no replacement
	 */
	@Deprecated
	public static class InvalidRowSize
		extends DDMFormLayoutValidationException {

		public InvalidRowSize() {
			super(
				"The sum of all column sizes of a row must be less than the " +
					"maximum row size of 12");
		}

	}

	/**
	 * @deprecated As of Judson (7.1.x), with no replacement
	 */
	@Deprecated
	public static class MustNotDuplicateFieldName
		extends DDMFormLayoutValidationException {

		public MustNotDuplicateFieldName(Set<String> duplicatedFieldNames) {
			super(
				String.format(
					"Field names %s were defined more than once",
					duplicatedFieldNames));

			_duplicatedFieldNames = duplicatedFieldNames;
		}

		public Set<String> getDuplicatedFieldNames() {
			return _duplicatedFieldNames;
		}

		private final Set<String> _duplicatedFieldNames;

	}

	/**
	 * @deprecated As of Judson (7.1.x), with no replacement
	 */
	@Deprecated
	public static class MustSetDefaultLocale
		extends DDMFormLayoutValidationException {

		public MustSetDefaultLocale() {
			super("DDM form layout does not have a default locale");
		}

	}

	/**
	 * @deprecated As of Judson (7.1.x), with no replacement
	 */
	@Deprecated
	public static class MustSetEqualLocaleForLayoutAndTitle
		extends DDMFormLayoutValidationException {

		public MustSetEqualLocaleForLayoutAndTitle() {
			super(
				"The default locale for the DDM form layout's page title is " +
					"not the same as the DDM form layout's default locale");
		}

	}

	private List<DDMFormLayoutValidatorError> _ddmFormLayoutValidatorErrors;

}