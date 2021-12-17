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

package com.liferay.portal.language;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageBuilderUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Shuyang Zhou
 * @author Kamesh Sampath
 */
public class LanguageResources {

	public static ResourceBundleLoader PORTAL_RESOURCE_BUNDLE_LOADER =
		new ResourceBundleLoader() {

			@Override
			public ResourceBundle loadResourceBundle(Locale locale) {
				return LanguageResources.getResourceBundle(locale);
			}

		};

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #PORTAL_RESOURCE_BUNDLE_LOADER}
	 */
	@Deprecated
	public static com.liferay.portal.kernel.util.ResourceBundleLoader
		RESOURCE_BUNDLE_LOADER =
			new com.liferay.portal.kernel.util.ResourceBundleLoader() {

				@Override
				public ResourceBundle loadResourceBundle(Locale locale) {
					return LanguageResources.getResourceBundle(locale);
				}

			};

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             LanguageBuilderUtil#fixValue(String)}
	 */
	@Deprecated
	public static String fixValue(String value) {
		return LanguageBuilderUtil.fixValue(value);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void fixValues(
		Map<String, String> languageMap, Properties properties) {

		_fixValues(languageMap, properties);
	}

	public static String getMessage(Locale locale, String key) {
		if (locale == null) {
			return null;
		}

		String overrideValue = _getOverrideValue(key, locale);

		if (overrideValue != null) {
			return overrideValue;
		}

		Map<String, String> languageMap = _languageMaps.get(locale);

		if (languageMap == null) {
			languageMap = _loadLocale(locale);
		}

		String value = languageMap.get(key);

		if (value == null) {
			return getMessage(getSuperLocale(locale), key);
		}

		return value;
	}

	public static ResourceBundle getResourceBundle(Locale locale) {
		return new LanguageResourcesBundle(locale);
	}

	public static Locale getSuperLocale(Locale locale) {
		Locale superLocale = _superLocales.get(locale);

		if (superLocale != null) {
			if (superLocale == _nullLocale) {
				return null;
			}

			return superLocale;
		}

		superLocale = _getSuperLocale(locale);

		if (superLocale == null) {
			_superLocales.put(locale, _nullLocale);
		}
		else {
			_superLocales.put(locale, superLocale);
		}

		return superLocale;
	}

	public void afterPropertiesSet() {
		Filter languageResourceFilter = SystemBundleUtil.createFilter(
			"(&(!(javax.portlet.name=*))(language.id=*)(objectClass=" +
				ResourceBundle.class.getName() + "))");

		_serviceTracker = new ServiceTracker<>(
			_bundleContext, languageResourceFilter,
			new LanguageResourceServiceTrackerCustomizer());

		_serviceTracker.open();

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			PORTAL_RESOURCE_BUNDLE_LOADER);
	}

	public void destroy() {
		_serviceTracker.close();
	}

	public void setConfig(String config) {
		_configNames = StringUtil.split(
			StringUtil.replace(config, CharPool.PERIOD, CharPool.SLASH));
	}

	private static void _fixValues(
		Map<String, String> languageMap, Properties properties) {

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String)entry.getKey();

			String value = (String)entry.getValue();

			value = LanguageBuilderUtil.fixValue(value);

			languageMap.put(key, value);
		}
	}

	private static String _getOverrideValue(String key, Locale locale) {
		LanguageOverrideProvider languageOverrideProvider =
			_languageOverrideProvider;

		if (languageOverrideProvider == null) {
			return null;
		}

		String value = languageOverrideProvider.get(key, locale);

		if (value == null) {
			return null;
		}

		return value;
	}

	private static Set<String> _getSetWithOverrideKeys(
		Set<String> keySet, Locale locale) {

		LanguageOverrideProvider languageOverrideProvider =
			_languageOverrideProvider;

		if (languageOverrideProvider == null) {
			return keySet;
		}

		Set<String> overrideKeySet = languageOverrideProvider.keySet(locale);

		if (SetUtil.isEmpty(overrideKeySet)) {
			return keySet;
		}

		Set<String> resultSet = new HashSet<>(keySet);

		resultSet.addAll(overrideKeySet);

		return resultSet;
	}

	private static Locale _getSuperLocale(Locale locale) {
		String variant = locale.getVariant();

		if (variant.length() > 0) {
			return new Locale(locale.getLanguage(), locale.getCountry());
		}

		String country = locale.getCountry();

		if (country.length() > 0) {
			Locale priorityLocale = LanguageUtil.getLocale(
				locale.getLanguage());

			if (priorityLocale != null) {
				variant = priorityLocale.getVariant();
			}

			if ((priorityLocale != null) && !locale.equals(priorityLocale) &&
				(variant.length() <= 0)) {

				return new Locale(
					priorityLocale.getLanguage(), priorityLocale.getCountry());
			}

			return LocaleUtil.fromLanguageId(locale.getLanguage(), false, true);
		}

		String language = locale.getLanguage();

		if (language.length() > 0) {
			return _blankLocale;
		}

		return null;
	}

	private static Map<String, String> _loadLocale(Locale locale) {
		Map<String, String> languageMap = null;

		if (_configNames.length > 0) {
			String localeName = locale.toString();

			languageMap = new HashMap<>();

			for (String name : _configNames) {
				StringBundler sb = new StringBundler(4);

				sb.append(name);

				if (localeName.length() > 0) {
					sb.append(StringPool.UNDERLINE);
					sb.append(localeName);
				}

				sb.append(".properties");

				Properties properties = _loadProperties(sb.toString());

				_fixValues(languageMap, properties);
			}
		}
		else {
			languageMap = Collections.emptyMap();
		}

		_languageMaps.put(locale, languageMap);

		return languageMap;
	}

	private static Properties _loadProperties(String name) {
		Properties properties = new Properties();

		try {
			ClassLoader classLoader = LanguageResources.class.getClassLoader();

			Enumeration<URL> enumeration = classLoader.getResources(name);

			if (_log.isDebugEnabled() && !enumeration.hasMoreElements()) {
				_log.debug("No resources found for " + name);
			}

			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat("Loading ", name, " from ", url));
				}

				try (InputStream inputStream = url.openStream()) {
					Properties inputStreamProperties = PropertiesUtil.load(
						inputStream, StringPool.UTF8);

					properties.putAll(inputStreamProperties);

					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								"Loading ", url, " with ",
								inputStreamProperties.size(), " values"));
					}
				}
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		return properties;
	}

	private Map<String, String> _putLanguageMap(
		Locale locale, Map<String, String> languageMap) {

		Map<String, String> oldLanguageMap = _languageMaps.get(locale);

		if (oldLanguageMap == null) {
			_loadLocale(locale);

			oldLanguageMap = _languageMaps.get(locale);
		}

		Map<String, String> newLanguageMap = new HashMap<>();

		if (oldLanguageMap != null) {
			newLanguageMap.putAll(oldLanguageMap);
		}

		Map<String, String> diffLanguageMap = new HashMap<>();

		for (Map.Entry<String, String> entry : languageMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			String oldValue = null;

			if (value == null) {
				oldValue = newLanguageMap.remove(key);
			}
			else {
				oldValue = newLanguageMap.put(key, value);
			}

			diffLanguageMap.put(entry.getKey(), oldValue);
		}

		_languageMaps.put(locale, newLanguageMap);

		return diffLanguageMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LanguageResources.class);

	private static final Locale _blankLocale = new Locale(StringPool.BLANK);
	private static String[] _configNames = new String[0];
	private static final Map<Locale, Map<String, String>> _languageMaps =
		new ConcurrentHashMap<>(64);
	private static volatile LanguageOverrideProvider _languageOverrideProvider =
		ServiceProxyFactory.newServiceTrackedInstance(
			LanguageOverrideProvider.class, LanguageResources.class,
			"_languageOverrideProvider", false);
	private static final Locale _nullLocale = new Locale(StringPool.BLANK);
	private static final Map<Locale, Locale> _superLocales =
		new ConcurrentHashMap<>();

	private final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private ServiceTracker<?, ?> _serviceTracker;

	private static class LanguageResourcesBundle extends ResourceBundle {

		@Override
		public Enumeration<String> getKeys() {
			Map<String, String> languageMap = _getLanguageMap();

			Set<String> keySet = _getSetWithOverrideKeys(
				languageMap.keySet(), _locale);

			if (parent == null) {
				return Collections.enumeration(keySet);
			}

			return new ResourceBundleEnumeration(keySet, parent.getKeys());
		}

		@Override
		public Locale getLocale() {
			return _locale;
		}

		@Override
		protected Object handleGetObject(String key) {
			String overrideValue = _getOverrideValue(key, _locale);

			if (overrideValue != null) {
				return overrideValue;
			}

			Map<String, String> languageMap = _getLanguageMap();

			return languageMap.get(key);
		}

		@Override
		protected Set<String> handleKeySet() {
			Map<String, String> languageMap = _getLanguageMap();

			return _getSetWithOverrideKeys(languageMap.keySet(), _locale);
		}

		private LanguageResourcesBundle(Locale locale) {
			_locale = locale;

			Locale superLocale = getSuperLocale(locale);

			if (superLocale != null) {
				setParent(new LanguageResourcesBundle(superLocale));
			}
		}

		private Map<String, String> _getLanguageMap() {
			Map<String, String> languageMap = _languageMaps.get(_locale);

			if (languageMap == null) {
				languageMap = _loadLocale(_locale);
			}

			return languageMap;
		}

		private final Locale _locale;

	}

	private static class ResourceBundleInfo
		implements Comparable<ResourceBundleInfo> {

		@Override
		public int compareTo(ResourceBundleInfo resourceBundleInfo) {
			return _serviceReference.compareTo(
				resourceBundleInfo._serviceReference);
		}

		private ResourceBundleInfo(
			String languageId, Locale locale,
			ServiceReference<?> serviceReference) {

			_languageId = languageId;
			_locale = locale;
			_serviceReference = serviceReference;
		}

		private Map<String, String> _diffLanguageMap;
		private final String _languageId;
		private final Locale _locale;
		private final ServiceReference<?> _serviceReference;

	}

	private class LanguageResourceServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ResourceBundle, ResourceBundleInfo> {

		@Override
		public ResourceBundleInfo addingService(
			ServiceReference<ResourceBundle> serviceReference) {

			ResourceBundle resourceBundle = _bundleContext.getService(
				serviceReference);

			String languageId = GetterUtil.getString(
				serviceReference.getProperty("language.id"));
			Locale locale = null;

			if (Validator.isNotNull(languageId)) {
				locale = LocaleUtil.fromLanguageId(languageId, true);
			}
			else {
				languageId = StringPool.BLANK;

				locale = new Locale(StringPool.BLANK);
			}

			Map<String, String> languageMap = _getLanguageMap(resourceBundle);

			synchronized (languageId.intern()) {
				List<ResourceBundleInfo> resourceBundleInfos =
					_languageResourceExtensions.computeIfAbsent(
						languageId, key -> new ArrayList<>());

				ResourceBundleInfo resourceBundleInfo = new ResourceBundleInfo(
					languageId, locale, serviceReference);

				int index = Collections.binarySearch(
					resourceBundleInfos, resourceBundleInfo);

				index = -index - 1;

				resourceBundleInfos.add(index, resourceBundleInfo);

				Map<String, String> diffLanguageMap = new HashMap<>();

				for (int i = index + 1; i < resourceBundleInfos.size(); i++) {
					ResourceBundleInfo nextResourceBundleInfo =
						resourceBundleInfos.get(i);

					Map<String, String> nextDiffLanguageMap =
						nextResourceBundleInfo._diffLanguageMap;

					for (Map.Entry<String, String> entry :
							nextDiffLanguageMap.entrySet()) {

						String key = entry.getKey();

						if (languageMap.containsKey(key)) {
							diffLanguageMap.put(key, entry.getValue());

							entry.setValue(languageMap.remove(key));
						}
					}
				}

				if (diffLanguageMap.isEmpty()) {
					diffLanguageMap = _putLanguageMap(locale, languageMap);
				}
				else {
					diffLanguageMap.putAll(
						_putLanguageMap(locale, languageMap));
				}

				resourceBundleInfo._diffLanguageMap = diffLanguageMap;

				return resourceBundleInfo;
			}
		}

		@Override
		public void modifiedService(
			ServiceReference<ResourceBundle> serviceReference,
			ResourceBundleInfo resourceBundleInfo) {
		}

		@Override
		public void removedService(
			ServiceReference<ResourceBundle> serviceReference,
			ResourceBundleInfo resourceBundleInfo) {

			_bundleContext.ungetService(serviceReference);

			String languageId = resourceBundleInfo._languageId;

			synchronized (languageId.intern()) {
				List<ResourceBundleInfo> resourceBundleInfos =
					_languageResourceExtensions.get(languageId);

				int index = Collections.binarySearch(
					resourceBundleInfos, resourceBundleInfo);

				Map<String, String> diffLanguageMap =
					resourceBundleInfo._diffLanguageMap;

				for (int i = index + 1; i < resourceBundleInfos.size(); i++) {
					ResourceBundleInfo nextResourceBundleInfo =
						resourceBundleInfos.get(i);

					Map<String, String> nextDiffLanguageMap =
						nextResourceBundleInfo._diffLanguageMap;

					for (Map.Entry<String, String> entry :
							nextDiffLanguageMap.entrySet()) {

						String key = entry.getKey();

						if (diffLanguageMap.containsKey(key)) {
							entry.setValue(diffLanguageMap.remove(key));
						}
					}
				}

				_putLanguageMap(resourceBundleInfo._locale, diffLanguageMap);

				resourceBundleInfos.remove(index);
			}
		}

		private Map<String, String> _getLanguageMap(
			ResourceBundle resourceBundle) {

			Map<String, String> languageMap = new HashMap<>();
			Enumeration<String> enumeration = resourceBundle.getKeys();

			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement();

				String value = ResourceBundleUtil.getString(
					resourceBundle, key);

				languageMap.put(key, value);
			}

			return languageMap;
		}

		private final Map<String, List<ResourceBundleInfo>>
			_languageResourceExtensions = new HashMap<>();

	}

}