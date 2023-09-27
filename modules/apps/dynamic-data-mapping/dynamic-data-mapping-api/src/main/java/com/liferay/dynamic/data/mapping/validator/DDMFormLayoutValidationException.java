/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.validator;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMFormLayoutValidationException extends PortalException {

	public DDMFormLayoutValidationException() {
	}

	public DDMFormLayoutValidationException(String msg) {
		super(msg);
	}

	public DDMFormLayoutValidationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DDMFormLayoutValidationException(Throwable throwable) {
		super(throwable);
	}

	public static class InvalidColumnSize
		extends DDMFormLayoutValidationException {

		public InvalidColumnSize() {
			super(
				"Column size must be positive and less than maximum row size " +
					"of 12");
		}

	}

	public static class InvalidRowSize
		extends DDMFormLayoutValidationException {

		public InvalidRowSize() {
			super(
				"The sum of all column sizes of a row must be less than the " +
					"maximum row size of 12");
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
				"The default locale for the DDM form layout's page title is " +
					"not the same as the DDM form layout's default locale");
		}

	}

}