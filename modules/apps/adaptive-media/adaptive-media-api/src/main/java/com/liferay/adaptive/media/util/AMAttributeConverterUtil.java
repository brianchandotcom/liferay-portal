/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.util;

import com.liferay.adaptive.media.exception.AMRuntimeException;

/**
 * Provides a set of functions for converting data into {@link AMAttribute}
 * values.
 *
 * <p>
 * These functions should throw an {@link
 * AMRuntimeException.AMAttributeFormatException} if they cannot convert the
 * String.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class AMAttributeConverterUtil {

	public static Integer parseInt(String value)
		throws AMRuntimeException.AMAttributeFormatException {

		try {
			return Integer.parseInt(value);
		}
		catch (NumberFormatException numberFormatException) {
			throw new AMRuntimeException.AMAttributeFormatException(
				numberFormatException);
		}
	}

	public static Long parseLong(String value)
		throws AMRuntimeException.AMAttributeFormatException {

		try {
			return Long.parseLong(value);
		}
		catch (NumberFormatException numberFormatException) {
			throw new AMRuntimeException.AMAttributeFormatException(
				numberFormatException);
		}
	}

}