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