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

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class DDMFormLayoutValidationException extends PortalException {

	public DDMFormLayoutValidationException() {
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

	public static class InvalidColumnSize
		extends DDMFormLayoutValidationException {

		public InvalidColumnSize() {
			super(
				"Invalid column size, it must be positive and " +
					"less than maximum row size of 12");
		}

	}

	public static class InvalidRowSize
		extends DDMFormLayoutValidationException {

		public InvalidRowSize() {
			super(
				"Invalid row size, the sum of all column sizes of a " +
					"row must be less than maximum row size of 12");
		}

	}

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

	public static class MustSetDefaultLocale
		extends DDMFormLayoutValidationException {

		public MustSetDefaultLocale() {
			super("DDM form layout does not have a default locale");
		}

	}

	public static class MustSetEqualLocaleForLayoutAndTitle
		extends DDMFormLayoutValidationException {

		public MustSetEqualLocaleForLayoutAndTitle() {
			super(
				"DDM form layout page title's default locale is not the " +
					"same as the DDM form layout's default locale");
		}

	}

}