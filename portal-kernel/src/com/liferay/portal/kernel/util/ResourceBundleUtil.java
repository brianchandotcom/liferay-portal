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

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageBuilderUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UTF8Control;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;

import java.text.MessageFormat;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleReference;

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

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no replacement
	 */
	@Deprecated
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

		String symbolicName = null;

		if (classLoader instanceof BundleReference) {
			BundleReference bundleReference = (BundleReference)classLoader;

			Bundle bundle = bundleReference.getBundle();

			symbolicName = bundle.getSymbolicName();
		}

		return _getBundle(baseName, locale, classLoader, symbolicName);
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

	public static String getString(ResourceBundle resourceBundle, String key) {
		if (!resourceBundle.containsKey(key)) {
			return null;
		}

		try {
			return LanguageBuilderUtil.fixValue(resourceBundle.getString(key));
		}
		catch (MissingResourceException missingResourceException) {
			if (_log.isDebugEnabled()) {
				_log.debug(missingResourceException);
			}

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
				_escapePattern(value), resourceBundle.getLocale());

			value = messageFormat.format(arguments);
		}

		return value;
	}

	private static String _escapePattern(String pattern) {
		return StringUtil.replace(
			pattern, CharPool.APOSTROPHE, StringPool.DOUBLE_APOSTROPHE);
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
			if (!_portalResourceBundleClassLoaders.contains(classLoader)) {
				try {
					return ResourceBundle.getBundle(
						baseName, locale, classLoader, UTF8Control.INSTANCE);
				}
				catch (MissingResourceException missingResourceException) {
					if (_log.isDebugEnabled()) {
						_log.debug(missingResourceException);
					}

					_portalResourceBundleClassLoaders.add(classLoader);
				}
			}

			resourceBundleLoader =
				ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
		}

		return resourceBundleLoader.loadResourceBundle(locale);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourceBundleUtil.class);

	private static final Set<ClassLoader> _portalResourceBundleClassLoaders =
		Collections.newSetFromMap(
			new ConcurrentReferenceKeyHashMap<>(
				FinalizeManager.WEAK_REFERENCE_FACTORY));

}