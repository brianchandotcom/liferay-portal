/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import java.text.MessageFormat;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Shuyang Zhou
 * @author Neil Griffin
 */
public class ResourceBundleUtil {

	public static final String NULL_VALUE = "NULL_VALUE";

	public static String getString(
		ResourceBundle resourceBundle, Locale locale, String key,
		Object[] arguments) {

		String value = getString(resourceBundle, key);

		if (value == null) {
			return null;
		}

		// Get the value associated with the specified key, and substitute any
		// arguuments like {0}, {1}, {2}, etc. with the specified argument
		// values.

		if (ArrayUtil.isNotEmpty(arguments)) {
			MessageFormat messageFormat = new MessageFormat(value, locale);

			value = messageFormat.format(arguments);
		}

		return value;
	}

	public static String getString(ResourceBundle resourceBundle, String key) {
		ResourceBundleThreadLocal.setReplace(true);

		String value = null;

		try {
			value = resourceBundle.getString(key);
		}
		finally {
			ResourceBundleThreadLocal.setReplace(false);
		}

		if (NULL_VALUE.equals(value)) {
			value = null;
		}

		return value;
	}

}