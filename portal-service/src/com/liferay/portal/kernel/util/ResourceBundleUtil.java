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

import java.util.Deque;
import java.util.LinkedList;
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

	public static void loadResourceBundles(
		Map<String, ResourceBundle> resourceBundles, Locale locale,
		ResourceBundleLoader resourceBundleLoader) {

		String languageId = LocaleUtil.toLanguageId(locale);

		loadResourceBundles(resourceBundles, languageId, resourceBundleLoader);
	}

	public static void loadResourceBundles(
		Map<String, ResourceBundle> resourceBundles, String languageId,
		ResourceBundleLoader resourceBundleLoader) {

		String[] languageIdParts = ("_" + languageId).split("_");

		String currentLanguageId = StringPool.BLANK;
		Deque<ResourceBundle> currentResourceBundles = new LinkedList<>();

		for (int i = 0; i < languageIdParts.length; i++) {
			if ( i > 1 ) {
				currentLanguageId += "_";
			}

			currentLanguageId += languageIdParts[i];

			ResourceBundle resourceBundle =
				resourceBundleLoader.loadResourceBundle(currentLanguageId);

			if (resourceBundle != null) {
				currentResourceBundles.addFirst(resourceBundle);
			}

			if (currentResourceBundles.isEmpty()) {
				continue;
			}

			if (currentResourceBundles.size() == 1) {
				resourceBundles.put(
					currentLanguageId, currentResourceBundles.peek());
			}
			else {
				int size = currentResourceBundles.size();

				resourceBundles.put(
					currentLanguageId,
					new AggregateResourceBundle(
						currentResourceBundles.toArray(
							new ResourceBundle[size])));
			}
		}
	}

	public interface ResourceBundleLoader {

		public ResourceBundle loadResourceBundle(String languageId);

	}

}