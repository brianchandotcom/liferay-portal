/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.exception;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class ValueDataException extends PortalException {

	public static class MismatchColumnType extends ValueDataException {

		public MismatchColumnType(
			long columnId, String columnType, String expectedColumnType) {

			super(
				StringBundler.concat(
					"Column ", columnId, " has type ", columnType,
					" and is not compatible with type ", expectedColumnType));
		}

	}

	public static class MustInformDefaultLocale extends ValueDataException {

		public MustInformDefaultLocale(Locale locale) {
			super(
				"A value for the default locale (" + locale.getLanguage() +
					") must be defined");
		}

	}

	public static class UnsupportedColumnType extends ValueDataException {

		public UnsupportedColumnType(long columnId, String columnType) {
			super(
				StringBundler.concat(
					"Unsupported column ", columnId, " type ", columnType));
		}

	}

	private ValueDataException(String msg) {
		super(msg);
	}

}