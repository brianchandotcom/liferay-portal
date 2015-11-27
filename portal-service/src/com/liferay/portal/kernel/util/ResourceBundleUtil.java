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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Shuyang Zhou
 * @author Neil Griffin
 */
public class ResourceBundleUtil {

	public static ResourceBundle getBundle(String baseName, Class<?> clazz) {
		return getBundle(baseName, clazz.getClassLoader());
	}

	public static ResourceBundle getBundle(
		String baseName, ClassLoader classLoader) {

		return ResourceBundle.getBundle(
			baseName, Locale.getDefault(), classLoader, UTF8Control.INSTANCE);
	}

	public static ResourceBundle getBundle(
		String baseName, Locale locale, Class<?> clazz) {

		return getBundle(baseName, locale, clazz.getClassLoader());
	}

	public static ResourceBundle getBundle(
		String baseName, Locale locale, ClassLoader classLoader) {

		return ResourceBundle.getBundle(
			baseName, locale, classLoader, UTF8Control.INSTANCE);
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

	public static void loadResourceBundleMap(
		Map<String, ResourceBundle> resourceBundles, Locale locale,
		ResourceBundleLoader resourceBundleLoader) {

		String languageId = LocaleUtil.toLanguageId(locale);

		String[] tokens = languageId.split("_");

		if (ArrayUtil.isEmpty(tokens)) {
			return;
		}

		String token = StringPool.BLANK;

		List<ResourceBundle> listBundles = new ArrayList<>();

		for (int i = 0; i < tokens.length; i++) {
			if ( i > 0 ) {
				token += "_";
			}

			token += tokens[i];

			ResourceBundle resourceBundle = resourceBundleLoader.loadBundle(
				token);

			if (resourceBundle != null) {
				listBundles.add(resourceBundle);
			}

			if (!listBundles.isEmpty()) {
				resourceBundles.put(
					token,
					new AggregateResourceBundle(
						listBundles.toArray(
							new ResourceBundle[listBundles.size()])));
			}
		}
	}

	public interface ResourceBundleLoader {
		public ResourceBundle loadBundle(String languageId);

	}

}