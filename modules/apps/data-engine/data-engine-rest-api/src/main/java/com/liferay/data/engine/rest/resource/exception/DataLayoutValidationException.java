/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.resource.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Set;

/**
 * @author Leonardo Barros
 */
public class DataLayoutValidationException extends PortalException {

	public DataLayoutValidationException() {
	}

	public DataLayoutValidationException(String msg) {
		super(msg);
	}

	public DataLayoutValidationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DataLayoutValidationException(Throwable throwable) {
		super(throwable);
	}

	public static class InvalidColumnSize
		extends DataLayoutValidationException {

		public InvalidColumnSize() {
			super(
				"Column size must be positive and less than maximum row size " +
					"of 12");
		}

	}

	public static class InvalidRowSize extends DataLayoutValidationException {

		public InvalidRowSize() {
			super(
				"The sum of all column sizes of a row must be less than the " +
					"maximum row size of 12");
		}

	}

	public static class MustNotDuplicateFieldName
		extends DataLayoutValidationException {

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
		extends DataLayoutValidationException {

		public MustSetDefaultLocale() {
			super("Data layout does not have a default locale");
		}

	}

	public static class MustSetEqualLocaleForLayoutAndTitle
		extends DataLayoutValidationException {

		public MustSetEqualLocaleForLayoutAndTitle() {
			super(
				"The default locale for the data layout's page title is not " +
					"the same as the data layout's default locale");
		}

	}

	public static class MustSetValidRuleExpression
		extends DataLayoutValidationException {

		public MustSetValidRuleExpression() {
			super("There are invalid rule expressions");
		}

	}

}