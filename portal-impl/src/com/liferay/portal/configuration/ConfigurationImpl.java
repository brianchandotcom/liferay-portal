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

package com.liferay.portal.configuration;

import com.germinus.easyconf.ComponentProperties;

import com.liferay.portal.configuration.easyconf.ClassLoaderAggregateProperties;
import com.liferay.portal.configuration.easyconf.ComponentPropertiesUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.MapConfiguration;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ConfigurationImpl
	implements com.liferay.portal.kernel.configuration.Configuration {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #ConfigurationImpl(ClassLoader, String, long, String)}
	 */
	@Deprecated
	public ConfigurationImpl(ClassLoader classLoader, String name) {
		this(
			classLoader, name, CompanyConstants.SYSTEM,
			_getWebId(CompanyConstants.SYSTEM));
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #ConfigurationImpl(ClassLoader, String, long, String)}
	 */
	@Deprecated
	public ConfigurationImpl(
		ClassLoader classLoader, String name, long companyId) {

		this(classLoader, name, companyId, _getWebId(companyId));
	}

	public ConfigurationImpl(
		ClassLoader classLoader, String name, long companyId, String webId) {

		_componentProperties =
			ComponentPropertiesUtil.createComponentProperties(
				classLoader, webId, name);

		printSources(companyId, webId);
	}

	@Override
	public void addProperties(Properties properties) {
		try {
			ClassLoaderAggregateProperties classLoaderAggregateProperties =
				(ClassLoaderAggregateProperties)
					_componentProperties.toConfiguration();

			Field field1 = CompositeConfiguration.class.getDeclaredField(
				"configList");

			field1.setAccessible(true);

			// Add to configList of base conf

			List<Configuration> configurations =
				(List<Configuration>)field1.get(classLoaderAggregateProperties);

			MapConfiguration newConfiguration = new MapConfiguration(
				_castPropertiesToMap(properties));

			newConfiguration.setTrimmingDisabled(true);

			configurations.add(0, newConfiguration);

			// Add to configList of AggregatedProperties itself

			CompositeConfiguration compositeConfiguration =
				classLoaderAggregateProperties.getBaseConfiguration();

			configurations = (List<Configuration>)field1.get(
				compositeConfiguration);

			configurations.add(0, newConfiguration);

			_properties = null;

			clearCache();
		}
		catch (Exception e) {
			_log.error("The properties could not be added", e);
		}
	}

	@Override
	public void clearCache() {
		_configurationArrayCache.clear();
		_configurationCache.clear();
		_configurationFilterArrayCache.clear();
		_configurationFilterCache.clear();

		_properties = null;
	}

	@Override
	public boolean contains(String key) {
		Object value = _configurationCache.get(key);

		if (value == null) {
			value = _componentProperties.getProperty(key);

			if (value == null) {
				value = _nullValue;
			}

			_configurationCache.put(key, value);
		}

		if (value == _nullValue) {
			return false;
		}

		return true;
	}

	@Override
	public String get(String key) {
		Object value = _configurationCache.get(key);

		if (value == null) {
			value = _componentProperties.getString(key);

			if (value == null) {
				value = _nullValue;
			}

			_configurationCache.put(key, value);
		}
		else if (_PRINT_DUPLICATE_CALLS_TO_GET) {
			System.out.println("Duplicate call to get " + key);
		}

		if (value instanceof String) {
			return (String)value;
		}

		return null;
	}

	@Override
	public String get(String key, Filter filter) {
		FilterCacheKey filterCacheKey = _buildFilterCacheKey(key, filter);

		Object value = null;

		if (filterCacheKey != null) {
			value = _configurationFilterCache.get(filterCacheKey);
		}

		if (value == null) {
			value = _componentProperties.getString(
				key, getEasyConfFilter(filter));

			if (filterCacheKey != null) {
				if (value == null) {
					value = _nullValue;
				}

				_configurationFilterCache.put(filterCacheKey, value);
			}
		}

		if (value instanceof String) {
			return (String)value;
		}

		return null;
	}

	@Override
	public String[] getArray(String key) {
		Object value = _configurationArrayCache.get(key);

		if (value == null) {
			String[] array = _componentProperties.getStringArray(key);

			value = _fixArrayValue(array);

			_configurationArrayCache.put(key, value);
		}

		if (value instanceof String[]) {
			return (String[])value;
		}

		return _EMPTY_ARRAY;
	}

	@Override
	public String[] getArray(String key, Filter filter) {
		FilterCacheKey filterCacheKey = _buildFilterCacheKey(key, filter);

		Object value = null;

		if (filterCacheKey != null) {
			value = _configurationFilterArrayCache.get(filterCacheKey);
		}

		if (value == null) {
			String[] array = _componentProperties.getStringArray(
				key, getEasyConfFilter(filter));

			value = _fixArrayValue(array);

			if (filterCacheKey != null) {
				_configurationFilterArrayCache.put(filterCacheKey, value);
			}
		}

		if (value instanceof String[]) {
			return (String[])value;
		}

		return _EMPTY_ARRAY;
	}

	@Override
	public Properties getProperties() {
		if (_properties != null) {
			return _properties;
		}

		// For some strange reason, componentProperties.getProperties() returns
		// values with spaces after commas. So a property setting of "xyz=1,2,3"
		// actually returns "xyz=1, 2, 3". This can break applications that
		// don't expect that extra space. However, getting the property value
		// directly through componentProperties returns the correct value. This
		// method fixes the weird behavior by returning properties with the
		// correct values.

		Properties properties = new Properties();

		Properties componentPropertiesProperties =
			_componentProperties.getProperties();

		for (String key : componentPropertiesProperties.stringPropertyNames()) {
			properties.setProperty(key, _componentProperties.getString(key));
		}

		_properties = properties;

		return properties;
	}

	@Override
	public Properties getProperties(String prefix, boolean removePrefix) {
		Properties properties = getProperties();

		return PropertiesUtil.getProperties(properties, prefix, removePrefix);
	}

	@Override
	public void removeProperties(Properties properties) {
		try {
			ClassLoaderAggregateProperties classLoaderAggregateProperties =
				(ClassLoaderAggregateProperties)
					_componentProperties.toConfiguration();

			CompositeConfiguration compositeConfiguration =
				classLoaderAggregateProperties.getBaseConfiguration();

			Field field2 = CompositeConfiguration.class.getDeclaredField(
				"configList");

			field2.setAccessible(true);

			@SuppressWarnings("unchecked")
			List<Configuration> configurations =
				(List<Configuration>)field2.get(compositeConfiguration);

			Iterator<Configuration> itr = configurations.iterator();

			while (itr.hasNext()) {
				Configuration configuration = itr.next();

				if (!(configuration instanceof MapConfiguration)) {
					break;
				}

				MapConfiguration mapConfiguration =
					(MapConfiguration)configuration;

				if (mapConfiguration.getMap() == (Map<?, ?>)properties) {
					itr.remove();

					classLoaderAggregateProperties.removeConfiguration(
						configuration);
				}
			}

			_properties = null;

			clearCache();
		}
		catch (Exception e) {
			_log.error("The properties could not be removed", e);
		}
	}

	@Override
	public void set(String key, String value) {
		_componentProperties.setProperty(key, value);

		clearCache();
	}

	protected com.germinus.easyconf.Filter getEasyConfFilter(Filter filter) {
		com.germinus.easyconf.Filter easyConfFilter =
			com.germinus.easyconf.Filter.by(filter.getSelectors());

		if (filter.getVariables() != null) {
			easyConfFilter.setVariables(filter.getVariables());
		}

		return easyConfFilter;
	}

	protected void printSources(long companyId, String webId) {
		List<String> sources = _componentProperties.getLoadedSources();

		for (int i = sources.size() - 1; i >= 0; i--) {
			String source = sources.get(i);

			if (_printedSources.contains(source)) {
				continue;
			}

			_printedSources.add(source);

			if (source.startsWith("bundleresource://")) {
				continue;
			}

			String info = "Loading " + source;

			if (companyId > CompanyConstants.SYSTEM) {
				info +=
					StringBundler.concat(
						" for {companyId=", String.valueOf(companyId),
						", webId=", webId, "}");
			}

			System.out.println(info);
		}
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> _castPropertiesToMap(
		Properties properties) {

		return (Map)properties;
	}

	private static String _getWebId(long companyId) {
		if (companyId > CompanyConstants.SYSTEM) {
			try {
				Company company = CompanyLocalServiceUtil.getCompanyById(
					companyId);

				return company.getWebId();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return null;
	}

	private FilterCacheKey _buildFilterCacheKey(String key, Filter filter) {
		if (filter.getVariables() != null) {
			return null;
		}

		return new FilterCacheKey(key, filter);
	}

	private Object _fixArrayValue(String[] array) {
		Object value = _nullValue;

		if (ArrayUtil.isNotEmpty(array)) {

			// Commons Configuration parses an empty property into a String
			// array with one String containing one space. It also leaves a
			// trailing array member if you set a property in more than one
			// line.

			if (Validator.isNull(array[array.length - 1])) {
				String[] subarray = new String[array.length - 1];

				System.arraycopy(array, 0, subarray, 0, subarray.length);

				array = subarray;
			}

			if (array.length > 0) {
				value = array;
			}
		}

		return value;
	}

	private static final String[] _EMPTY_ARRAY = new String[0];

	private static final boolean _PRINT_DUPLICATE_CALLS_TO_GET = false;

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationImpl.class);

	private static final Object _nullValue = new Object();

	private final ComponentProperties _componentProperties;
	private final Map<String, Object> _configurationArrayCache =
		new ConcurrentHashMap<>();
	private final Map<String, Object> _configurationCache =
		new ConcurrentHashMap<>();
	private final Map<FilterCacheKey, Object> _configurationFilterArrayCache =
		new ConcurrentHashMap<>();
	private final Map<FilterCacheKey, Object> _configurationFilterCache =
		new ConcurrentHashMap<>();
	private final Set<String> _printedSources = new HashSet<>();
	private Properties _properties;

	private static class FilterCacheKey {

		@Override
		public boolean equals(Object object) {
			FilterCacheKey filterCacheKey = (FilterCacheKey)object;

			if (Objects.equals(_key, filterCacheKey._key) &&
				Arrays.equals(_selectors, filterCacheKey._selectors)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _key);

			for (String selector : _selectors) {
				hashCode = HashUtil.hash(hashCode, selector);
			}

			return hashCode;
		}

		private FilterCacheKey(String key, Filter filter) {
			_key = key;

			_selectors = filter.getSelectors();
		}

		private final String _key;
		private final String[] _selectors;

	}

}