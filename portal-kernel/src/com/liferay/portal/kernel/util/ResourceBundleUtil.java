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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UTF8Control;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.text.MessageFormat;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Shuyang Zhou
 * @author Neil Griffin
 */
public class ResourceBundleUtil {

	public static final ResourceBundle EMPTY_RESOURCE_BUNDLE =
		new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return Collections.emptyEnumeration();
			}

			@Override
			protected Object handleGetObject(String key) {
				return key;
			}

		};

	public static ResourceBundle getBundle(Locale locale, Class<?> clazz) {
		return getBundle("content.Language", locale, clazz);
	}

	public static ResourceBundle getBundle(
		Locale locale, ClassLoader classLoader) {

		return getBundle("content.Language", locale, classLoader);
	}

	public static ResourceBundle getBundle(Locale locale, String symbolicName) {
		return _getBundle(
			"content.Language", locale,
			ResourceBundleUtil.class.getClassLoader(), symbolicName);
	}

	public static ResourceBundle getBundle(String baseName, Class<?> clazz) {
		return getBundle(baseName, clazz.getClassLoader());
	}

	public static ResourceBundle getBundle(
		String baseName, ClassLoader classLoader) {

		return getBundle(baseName, LocaleUtil.getDefault(), classLoader);
	}

	public static ResourceBundle getBundle(
		String baseName, Locale locale, Class<?> clazz) {

		return getBundle(baseName, locale, clazz.getClassLoader());
	}

	public static ResourceBundle getBundle(
		String baseName, Locale locale, ClassLoader classLoader) {

		Registry registry = RegistryUtil.getRegistry();

		return _getBundle(
			baseName, locale, classLoader,
			registry.getSymbolicName(classLoader));
	}

	public static Map<Locale, String> getLocalizationMap(
		ResourceBundleLoader resourceBundleLoader, String key) {

		Map<Locale, String> map = new HashMap<>();

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			ResourceBundle resourceBundle =
				resourceBundleLoader.loadResourceBundle(locale);

			map.put(locale, getString(resourceBundle, key));
		}

		return map;
	}

	public static ResourceBundle getModuleAndPortalResourceBundle(
		Locale locale, Class<?> clazz) {

		return new AggregateResourceBundle(
			getBundle(locale, clazz), PortalUtil.getResourceBundle(locale));
	}

	public static ResourceBundleLoader getResourceBundleLoader(
		final String baseName, final ClassLoader classLoader) {

		return new ClassResourceBundleLoader(baseName, classLoader);
	}

	public static String getString(ResourceBundle resourceBundle, String key) {
		if (!resourceBundle.containsKey(key)) {
			return null;
		}

		try {
			return resourceBundle.getString(key);
		}
		catch (MissingResourceException missingResourceException) {
			return null;
		}
	}

	public static String getString(
		ResourceBundle resourceBundle, String key, Object... arguments) {

		String value = getString(resourceBundle, key);

		if (value == null) {
			return null;
		}

		// Get the value associated with the specified key, and substitute any
		// arguments like {0}, {1}, {2}, etc. with the specified argument
		// values.

		if (ArrayUtil.isNotEmpty(arguments)) {
			MessageFormat messageFormat = new MessageFormat(
				value, resourceBundle.getLocale());

			value = messageFormat.format(arguments);
		}

		return value;
	}

	private static ResourceBundle _getBundle(
		String baseName, Locale locale, ClassLoader classLoader,
		String symbolicName) {

		ResourceBundleLoader resourceBundleLoader = null;

		if (symbolicName == null) {
			ClassLoader portalClassLoader =
				PortalClassLoaderUtil.getClassLoader();

			if (classLoader == portalClassLoader) {
				resourceBundleLoader =
					ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
			}
		}
		else {
			resourceBundleLoader =
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByBundleSymbolicName(symbolicName);
		}

		if (resourceBundleLoader == null) {
			return ResourceBundle.getBundle(
				baseName, locale, classLoader, UTF8Control.INSTANCE);
		}

		return resourceBundleLoader.loadResourceBundle(locale);
	}

}