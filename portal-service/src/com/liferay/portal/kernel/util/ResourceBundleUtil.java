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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.language.UTF8Control;

import java.text.MessageFormat;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Shuyang Zhou
 * @author Neil Griffin
 */
public class ResourceBundleUtil {

	public static ResourceBundle getBundle(
		String baseName, ClassLoader loader) {

		return ResourceBundle.getBundle(
			baseName, Locale.getDefault(), loader, UTF8Control.INSTANCE);
	}

	public static ResourceBundle getBundle(
		String baseName, Locale locale, ClassLoader loader) {

		return ResourceBundle.getBundle(
			baseName, locale, loader, UTF8Control.INSTANCE);
	}

	public static String getString(
		ResourceBundle resourceBundle, Locale locale, String key,
		Object[] arguments) {

		String value = getString(resourceBundle, key);

		if (value == null) {
			return null;
		}

		// Get the value associated with the specified key, and substitute any
		// arguments like {0}, {1}, {2}, etc. with the specified argument
		// values.

		if (ArrayUtil.isNotEmpty(arguments)) {
			MessageFormat messageFormat = new MessageFormat(value, locale);

			value = messageFormat.format(arguments);
		}

		return value;
	}

	public static String getString(ResourceBundle resourceBundle, String key) {
		if (!resourceBundle.containsKey(key)) {
			return null;
		}

		try {
			return resourceBundle.getString(key);
		}
		catch (MissingResourceException mre) {
			return null;
		}
	}

}