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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Field;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.configuration.DefaultFileSystem;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.FileSystem;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;
import org.apache.commons.configuration.SubsetConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

/**
 * @author Raymond Augé
 */
public class ClassLoaderAggregateProperties extends CompositeConfiguration {

	public ClassLoaderAggregateProperties(
		ClassLoader classLoader, String companyId, String componentName) {

		_classLoader = classLoader;
		_companyId = companyId;
		_componentName = componentName;

		_prefixedSystemConfiguration = new SubsetConfiguration(
			_systemConfiguration, _getPrefix(), null);

		setThrowExceptionOnMissing(false);

		_addBaseFileName(componentName.concat(".properties"));
	}

	public CompositeConfiguration getBaseConfiguration() {
		return _baseCompositeConfiguration;
	}

	public Properties getProperties() {
		Properties properties = new Properties();

		Iterator<String> keyIterator = getKeys();

		while (keyIterator.hasNext()) {
			String key = keyIterator.next();

			properties.setProperty(key, StringUtil.merge(getList(key)));
		}

		return properties;
	}

	@Override
	public Object getProperty(String key) {
		Object value = null;

		if (value == null) {
			value = System.getProperty(_getPrefix().concat(key));
		}

		if (value == null) {
			value = _globalCompositeConfiguration.getProperty(
				_getPrefix().concat(key));
		}

		if (value == null) {
			value = _globalCompositeConfiguration.getProperty(key);
		}

		if (value == null) {
			value = _baseCompositeConfiguration.getProperty(key);
		}

		if (value == null) {
			value = super.getProperty(key);
		}

		if (value == null) {
			value = System.getProperty(key);
		}

		return value;
	}

	@Override
	public String getString(String key) {
		Object value = getProperty(key);

		if (value instanceof List) {
			return StringUtil.merge((List)value);
		}

		return super.getString(key);
	}

	public String getString(String key, Filter filter) {
		Iterator<String> iterator = filter.filterKeyIterator(key);

		while (iterator.hasNext()) {
			String value = super.getString(iterator.next(), null);

			if (value != null) {
				return value;
			}
		}

		return null;
	}

	public String[] getStringArray(String key, Filter filter) {
		Iterator<String> iterator = filter.filterKeyIterator(key);

		while (iterator.hasNext()) {
			List<?> value = getList(iterator.next(), null);

			if (value != null) {
				return value.toArray(new String[0]);
			}
		}

		return StringPool.EMPTY_ARRAY;
	}

	public List<String> loadedSources() {
		return _loadedSources;
	}

	private void _addBaseFileName(String fileName) {
		URL url = _classLoader.getResource(fileName);

		Configuration configuration = _addPropertiesSource(
			fileName, url, _baseCompositeConfiguration);

		if (configuration == null) {
			throw new SystemException(
				"The base properties file was not found for " + _componentName);
		}
		else if (configuration.isEmpty() && _log.isDebugEnabled()) {
			_log.debug("Empty configuration " + fileName);
		}
	}

	private Configuration _addFileProperties(
		String fileName, CompositeConfiguration loadedCompositeConfiguration) {

		URL url = ConfigurationUtils.locate(_fileSystem, null, fileName);

		if (url == null) {
			return null;
		}

		try {
			FileConfiguration newFileConfiguration =
				new PropertiesConfiguration(url) {

					@Override
					public String getEncoding() {
						return StringPool.UTF8;
					}

				};

			_addIncludedPropertiesSources(
				newFileConfiguration, loadedCompositeConfiguration);

			return newFileConfiguration;
		}
		catch (ConfigurationException ce) {
			if (_log.isDebugEnabled()) {
				_log.debug("Configuration source " + fileName + " ignored");
			}

			return null;
		}
	}

	private void _addIncludedPropertiesSources(
		Configuration newConfiguration,
		CompositeConfiguration loadedCompositeConfiguration) {

		CompositeConfiguration tempCompositeConfiguration =
			new CompositeConfiguration();

		tempCompositeConfiguration.addConfiguration(
			_prefixedSystemConfiguration);
		tempCompositeConfiguration.addConfiguration(newConfiguration);
		tempCompositeConfiguration.addConfiguration(_systemConfiguration);

		String[] fileNames = tempCompositeConfiguration.getStringArray(
			"include-and-override");

		ArrayUtil.reverse(fileNames);

		for (String fileName : fileNames) {
			URL url = null;

			try {
				url = _classLoader.getResource(fileName);
			}
			catch (RuntimeException re) {
				if (fileName.startsWith("file:/")) {
					throw re;
				}

				fileName = "file:/".concat(fileName);

				url = _classLoader.getResource(fileName);
			}

			_addPropertiesSource(fileName, url, loadedCompositeConfiguration);
		}
	}

	private Configuration _addPropertiesSource(
		String sourceName, URL url,
		CompositeConfiguration loadedCompositeConfiguration) {

		try {
			Configuration newConfiguration = null;

			if (url != null) {
				newConfiguration = _addURLProperties(
					url, loadedCompositeConfiguration);
			}
			else {
				newConfiguration = _addFileProperties(
					sourceName, loadedCompositeConfiguration);
			}

			if (newConfiguration == null) {
				return newConfiguration;
			}

			loadedCompositeConfiguration.addConfiguration(newConfiguration);

			super.addConfiguration(newConfiguration);

			if (newConfiguration instanceof AbstractFileConfiguration) {
				AbstractFileConfiguration abstractFileConfiguration =
					(AbstractFileConfiguration)newConfiguration;

				URL abstractFileConfigurationURL =
					abstractFileConfiguration.getURL();

				_loadedSources.add(abstractFileConfigurationURL.toString());
			}
			else {
				_loadedSources.add(sourceName);
			}

			return newConfiguration;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Configuration source ", sourceName, " ignored: ",
						e.getMessage()));
			}

			return null;
		}
	}

	private Configuration _addURLProperties(
		URL url, CompositeConfiguration loadedCompositeConfiguration) {

		try {
			PropertiesConfiguration propertiesConfiguration =
				new PropertiesConfiguration(url) {

					@Override
					public String getEncoding() {
						return StringPool.UTF8;
					}

				};

			PropertiesConfigurationLayout propertiesConfigurationLayout =
				propertiesConfiguration.getLayout();

			try {
				Map<String, Object> layoutData =
					(Map<String, Object>)_layoutDataField.get(
						propertiesConfigurationLayout);

				for (Object propertyLayoutData : layoutData.values()) {
					_commentField.set(propertyLayoutData, null);
				}
			}
			catch (ReflectiveOperationException roe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to clear out comments from " +
							propertiesConfiguration,
						roe);
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Adding resource " + url);
			}

			_addIncludedPropertiesSources(
				propertiesConfiguration, loadedCompositeConfiguration);

			return propertiesConfiguration;
		}
		catch (ConfigurationException ce) {
			if (_log.isDebugEnabled()) {
				_log.debug("Configuration source " + url + " ignored");
			}

			return null;
		}
	}

	private String _getPrefix() {
		return _componentName.concat(":");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClassLoaderAggregateProperties.class);

	private static final Field _commentField;

	private static final FileSystem _fileSystem = new DefaultFileSystem() {

		@Override
		public URL locateFromURL(String basePath, String fileName) {
			if (fileName.indexOf(CharPool.SEMICOLON) != -1) {
				try {
					return new URL(fileName);
				}
				catch (MalformedURLException murle) {
					return null;
				}
			}

			return null;
		}

	};

	private static final Field _layoutDataField;

	static {
		try {
			ClassLoader classLoader =
				PropertiesConfigurationLayout.class.getClassLoader();

			Class<?> propertyLayoutDataClass = classLoader.loadClass(
				PropertiesConfigurationLayout.class.getName() +
					"$PropertyLayoutData");

			_commentField = ReflectionUtil.getDeclaredField(
				propertyLayoutDataClass, "comment");

			_layoutDataField = ReflectionUtil.getDeclaredField(
				PropertiesConfigurationLayout.class, "layoutData");
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private final CompositeConfiguration _baseCompositeConfiguration =
		new CompositeConfiguration();
	private final ClassLoader _classLoader;
	private final String _companyId;
	private final String _componentName;
	private final CompositeConfiguration _globalCompositeConfiguration =
		new CompositeConfiguration();
	private final List<String> _loadedSources = new ArrayList<>();
	private final Configuration _prefixedSystemConfiguration;
	private final SystemConfiguration _systemConfiguration =
		new SystemConfiguration();

}