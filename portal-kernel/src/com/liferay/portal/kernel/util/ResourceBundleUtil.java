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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UTF8Control;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
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

	public static ResourceBundleLoader getResourceBundleLoader(
		final String baseName, final ClassLoader classLoader) {

		return new ClassResourceBundleLoader(baseName, classLoader);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getString(ResourceBundle, String, Object...)}
	 */
	@Deprecated
	public static String getString(
		ResourceBundle resourceBundle, Locale locale, String key,
		Object[] arguments) {

		return getString(resourceBundle, key, arguments);
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

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static void loadResourceBundles(
		Map<String, ResourceBundle> resourceBundles, Locale locale,
		ResourceBundleLoader resourceBundleLoader) {

		String languageId = LocaleUtil.toLanguageId(locale);

		loadResourceBundles(resourceBundles, languageId, resourceBundleLoader);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static void loadResourceBundles(
		Map<String, ResourceBundle> resourceBundles, String languageId,
		ResourceBundleLoader resourceBundleLoader) {

		Deque<ResourceBundle> currentResourceBundles = new LinkedList<>();

		for (String currentLanguageId : _getLanguageIds(languageId)) {
			ResourceBundle resourceBundle =
				resourceBundleLoader.loadResourceBundle(currentLanguageId);

			if (resourceBundle != null) {
				currentResourceBundles.addFirst(resourceBundle);
			}
			else if (currentResourceBundles.isEmpty()) {
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

	private static List<String> _getLanguageIds(String languageId) {
		List<String> languageIds = new ArrayList<>();

		languageIds.add(StringPool.BLANK);

		int index = 0;

		while ((index = languageId.indexOf(CharPool.UNDERLINE, index + 1)) !=
					-1) {

			languageIds.add(languageId.substring(0, index));
		}

		languageIds.add(languageId);

		return languageIds;
	}

}