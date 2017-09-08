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
		catch (NumberFormatException nfe) {
			throw new AMRuntimeException.AMAttributeFormatException(nfe);
		}
	}

}