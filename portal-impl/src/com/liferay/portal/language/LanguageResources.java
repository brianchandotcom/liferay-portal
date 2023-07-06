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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

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

	public static String getMessage(Locale locale, String key) {
		if (locale == null) {
			return null;
		}

		ResourceBundle overrideResourceBundle = _getOverrideResourceBundle(
			locale);

		if (overrideResourceBundle != null) {
			String overrideValue = ResourceBundleUtil.getString(
				overrideResourceBundle, key);

			if (overrideValue != null) {
				return overrideValue;
			}
		}

		MapHolder mapHolder = _getMapHolder(locale);

		Map<String, String> languageMap = mapHolder.getMap();

		String value = languageMap.get(key);

		if (value == null) {
			return getMessage(getSuperLocale(locale), key);
		}

		return value;
	}

	public static ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle resourceBundle = new LanguageResourcesBundle(locale);

		ResourceBundle overrideResourceBundle = _getOverrideResourceBundle(
			locale);

		if (overrideResourceBundle != null) {
			resourceBundle = new AggregateResourceBundle(
				overrideResourceBundle, resourceBundle);
		}

		return resourceBundle;
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
		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			PORTAL_RESOURCE_BUNDLE_LOADER);
	}

	public void destroy() {
		for (MapHolder mapHolder : _mapHolders.values()) {
			mapHolder.close();
		}

		_mapHolders.clear();
	}

	private static MapHolder _getMapHolder(Locale locale) {
		MapHolder mapHolder = _mapHolders.get(locale);

		if (mapHolder == null) {
			mapHolder = new MapHolder(locale);

			MapHolder previousMapHolder = _mapHolders.putIfAbsent(
				locale, mapHolder);

			if (previousMapHolder != null) {
				mapHolder.close();

				mapHolder = previousMapHolder;
			}
		}

		return mapHolder;
	}

	private static ResourceBundle _getOverrideResourceBundle(Locale locale) {
		LanguageOverrideProvider languageOverrideProvider =
			_languageOverrideProvider;

		if (languageOverrideProvider == null) {
			return null;
		}

		Set<String> overrideKeySet = languageOverrideProvider.keySet(locale);

		if (SetUtil.isEmpty(overrideKeySet)) {
			return null;
		}

		return new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return Collections.enumeration(overrideKeySet);
			}

			@Override
			protected Object handleGetObject(String key) {
				return languageOverrideProvider.get(key, locale);
			}

			@Override
			protected Set<String> handleKeySet() {
				return overrideKeySet;
			}

		};
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

	private static final Locale _blankLocale = new Locale(StringPool.BLANK);
	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static volatile LanguageOverrideProvider _languageOverrideProvider =
		ServiceProxyFactory.newServiceTrackedInstance(
			LanguageOverrideProvider.class, LanguageResources.class,
			"_languageOverrideProvider", false, true);
	private static final Map<Locale, MapHolder> _mapHolders =
		new ConcurrentHashMap<>();
	private static final Locale _nullLocale = new Locale(StringPool.BLANK);
	private static final Map<Locale, Locale> _superLocales =
		new ConcurrentHashMap<>();

	private static class LanguageResourcesBundle extends ResourceBundle {

		@Override
		public Enumeration<String> getKeys() {
			MapHolder mapHolder = _getMapHolder(_locale);

			Map<String, String> languageMap = mapHolder.getMap();

			Set<String> keySet = languageMap.keySet();

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
			MapHolder mapHolder = _getMapHolder(_locale);

			Map<String, String> languageMap = mapHolder.getMap();

			return languageMap.get(key);
		}

		@Override
		protected Set<String> handleKeySet() {
			MapHolder mapHolder = _getMapHolder(_locale);

			Map<String, String> languageMap = mapHolder.getMap();

			return languageMap.keySet();
		}

		private LanguageResourcesBundle(Locale locale) {
			_locale = locale;

			Locale superLocale = getSuperLocale(locale);

			if (superLocale != null) {
				ResourceBundle superResourceBundle =
					new LanguageResourcesBundle(superLocale);

				ResourceBundle superLocaleOverrideResourceBundle =
					_getOverrideResourceBundle(superLocale);

				if (superLocaleOverrideResourceBundle != null) {
					superResourceBundle = new AggregateResourceBundle(
						superLocaleOverrideResourceBundle, superResourceBundle);
				}

				setParent(superResourceBundle);
			}
		}

		private final Locale _locale;

	}

	private static class MapHolder {

		public void close() {
			for (ServiceReference<ResourceBundle> serviceReference :
					_serviceReferences) {

				_bundleContext.ungetService(serviceReference);
			}
		}

		public Map<String, String> getMap() {
			return _map;
		}

		private MapHolder(Locale locale) {
			try {
				_serviceReferences = _bundleContext.getServiceReferences(
					ResourceBundle.class,
					"(&(!(javax.portlet.name=*))(language.id=" +
						LocaleUtil.toLanguageId(locale) + "))");
			}
			catch (InvalidSyntaxException invalidSyntaxException) {
				throw new RuntimeException(invalidSyntaxException);
			}

			for (ServiceReference<ResourceBundle> serviceReference :
					_serviceReferences) {

				ResourceBundle resourceBundle = _bundleContext.getService(
					serviceReference);

				Enumeration<String> enumeration = resourceBundle.getKeys();

				while (enumeration.hasMoreElements()) {
					String key = enumeration.nextElement();

					_map.putIfAbsent(
						key, ResourceBundleUtil.getString(resourceBundle, key));
				}
			}
		}

		private final Map<String, String> _map = new HashMap<>();
		private final Collection<ServiceReference<ResourceBundle>>
			_serviceReferences;

		static {
			try {
				_bundleContext.addServiceListener(
					new ServiceListener() {

						@Override
						public void serviceChanged(ServiceEvent serviceEvent) {
							ServiceReference<?> serviceReference =
								serviceEvent.getServiceReference();

							MapHolder mapHolder = _mapHolders.remove(
								LocaleUtil.fromLanguageId(
									String.valueOf(
										serviceReference.getProperty(
											"language.id")),
									false));

							if (mapHolder != null) {
								mapHolder.close();
							}
						}

					},
					"(&(!(javax.portlet.name=*))(language.id=*))");
			}
			catch (InvalidSyntaxException invalidSyntaxException) {
				throw new ExceptionInInitializerError(invalidSyntaxException);
			}
		}

	}

}