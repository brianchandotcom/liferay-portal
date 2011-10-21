/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.germinus.easyconf.AggregatedProperties;
import com.germinus.easyconf.ComponentConfiguration;
import com.germinus.easyconf.ComponentProperties;
import com.germinus.easyconf.Conventions;
import com.germinus.easyconf.EasyConf;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.service.CompanyLocalServiceUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import java.lang.reflect.Field;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

	public ConfigurationImpl(ClassLoader classLoader, String name) {
		this(classLoader, name, CompanyConstants.SYSTEM);
	}

	public ConfigurationImpl(
		ClassLoader classLoader, String name, long companyId) {

		try {
			URL url = classLoader.getResource(
				name + Conventions.PROPERTIES_EXTENSION);

			if ((url != null) && url.getProtocol().equals("file")) {
				String basePath = url.getPath();

				int pos = basePath.lastIndexOf(
					StringPool.SLASH + name + Conventions.PROPERTIES_EXTENSION);

				if (pos != -1) {
					basePath = basePath.substring(0, pos);
				}

				Properties properties = new Properties();

				properties.load(url.openStream());

				if (!properties.containsKey("base.path")) {
					String fileName = StringUtil.replace(
						url.getFile(), "%20", StringPool.SPACE);

					File file = new File(fileName);

					if (file.exists() && file.canWrite()) {
						Writer writer = new FileWriter(file, true);

						StringBundler sb = new StringBundler(4);

						sb.append(StringPool.OS_EOL);
						sb.append(StringPool.OS_EOL);
						sb.append("base.path=");
						sb.append(basePath);

						writer.write(sb.toString());

						writer.close();
					}
					else {
						if (_log.isWarnEnabled()) {
							_log.warn("Unable to write " + file);
						}
					}
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		String webId = null;

		if (companyId > CompanyConstants.SYSTEM) {
			try {
				Company company = CompanyLocalServiceUtil.getCompanyById(
					companyId);

				webId = company.getWebId();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		EasyConf.refreshAll();

		if (webId != null) {
			_componentConfiguration = EasyConf.getConfiguration(
				webId, getFileName(classLoader, name));
		}
		else {
			_componentConfiguration = EasyConf.getConfiguration(
				getFileName(classLoader, name));
		}

		printSources(companyId, webId);
	}

	public void addProperties(Properties properties) {
		try {
			ComponentProperties componentProperties =
				_componentConfiguration.getProperties();

			AggregatedProperties aggregatedProperties =
				(AggregatedProperties)componentProperties.toConfiguration();

			Field field1 = CompositeConfiguration.class.getDeclaredField(
				"configList");

			field1.setAccessible(true);

			// Add to configList of base conf

			List<Configuration> configurations =
				(List<Configuration>)field1.get(aggregatedProperties);

			MapConfiguration newConfiguration =
				new MapConfiguration(properties);

			configurations.add(0, newConfiguration);

			// Add to configList of AggregatedProperties itself

			Class<?> clazz = aggregatedProperties.getClass();

			Field field2 = clazz.getDeclaredField("baseConf");

			field2.setAccessible(true);

			CompositeConfiguration compositeConfiguration =
				(CompositeConfiguration)field2.get(aggregatedProperties);

			configurations = (List<Configuration>)field1.get(
				compositeConfiguration);

			configurations.add(0, newConfiguration);

			clearCache();
		}
		catch (Exception e) {
			_log.error("The properties could not be added", e);
		}
	}

	public void clearCache() {
		_values.clear();
	}

	public boolean contains(String key) {
		Object value = _values.get(key);

		if (value == null) {
			ComponentProperties componentProperties = getComponentProperties();

			value = componentProperties.getProperty(key);

			if (value == null) {
				value = _nullValue;
			}

			_values.put(key, value);
		}

		if (value == _nullValue) {
			return false;
		}
		else {
			return true;
		}
	}

	public String get(String key) {
		Object value = _values.get(key);

		if (value == null) {
			ComponentProperties componentProperties = getComponentProperties();

			value = componentProperties.getString(key);

			if (value == null) {
				value = _nullValue;
			}

			_values.put(key, value);
		}
		else if (_PRINT_DUPLICATE_CALLS_TO_GET) {
			System.out.println("Duplicate call to get " + key);
		}

		if (value instanceof String) {
			return (String)value;
		}
		else {
			return null;
		}
	}

	public String get(String key, Filter filter) {
		String filterCacheKey = buildFilterCacheKey(key, filter, false);

		Object value = null;

		if (filterCacheKey != null) {
			value = _values.get(filterCacheKey);
		}

		if (value == null) {
			ComponentProperties componentProperties = getComponentProperties();

			value = componentProperties.getString(
				key, getEasyConfFilter(filter));

			if (filterCacheKey != null) {
				if (value == null) {
					value = _nullValue;
				}

				_values.put(filterCacheKey, value);
			}
		}

		if (value instanceof String) {
			return (String)value;
		}
		else {
			return null;
		}

	}

	public String[] getArray(String key) {
		String cacheKey = _ARRAY_KEY_PREFIX.concat(key);

		Object value = _values.get(cacheKey);

		if (value == null) {
			ComponentProperties componentProperties = getComponentProperties();

			String[] array = componentProperties.getStringArray(key);

			value = fixArrayValue(cacheKey, array);
		}

		if (value instanceof String[]) {
			return (String[])value;
		}
		else {
			return _emptyArray;
		}
	}

	public String[] getArray(String key, Filter filter) {
		String filterCacheKey = buildFilterCacheKey(key, filter, true);

		Object value = null;

		if (filterCacheKey != null) {
			value = _values.get(filterCacheKey);
		}

		if (value == null) {
			ComponentProperties componentProperties = getComponentProperties();

			String[] array = componentProperties.getStringArray(
				key, getEasyConfFilter(filter));

			value = fixArrayValue(filterCacheKey, array);
		}

		if (value instanceof String[]) {
			return (String[])value;
		}
		else {
			return _emptyArray;
		}
	}

	public Properties getProperties() {

		// For some strange reason, componentProperties.getProperties() returns
		// values with spaces after commas. So a property setting of "xyz=1,2,3"
		// actually returns "xyz=1, 2, 3". This can break applications that
		// don't expect that extra space. However, getting the property value
		// directly through componentProperties returns the correct value. This
		// method fixes the weird behavior by returing properties with the
		// correct values.

		Properties properties = new Properties();

		ComponentProperties componentProperties = getComponentProperties();

		Properties componentPropertiesProperties =
			componentProperties.getProperties();

		for (Map.Entry<Object, Object> entry :
				componentPropertiesProperties.entrySet()) {

			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			properties.setProperty(key, value);
		}

		return properties;
	}

	public Properties getProperties(String prefix, boolean removePrefix) {
		Properties properties = getProperties();

		return PropertiesUtil.getProperties(properties, prefix, removePrefix);
	}

	public void removeProperties(Properties properties) {
		try {
			ComponentProperties componentProperties =
				_componentConfiguration.getProperties();

			AggregatedProperties aggregatedProperties =
				(AggregatedProperties)componentProperties.toConfiguration();

			Class<?> clazz = aggregatedProperties.getClass();

			Field field1 = clazz.getDeclaredField("baseConf");

			field1.setAccessible(true);

			CompositeConfiguration compositeConfiguration =
				(CompositeConfiguration)field1.get(aggregatedProperties);

			Field field2 = CompositeConfiguration.class.getDeclaredField(
				"configList");

			field2.setAccessible(true);

			List<Configuration> configurations =
				(List<Configuration>)field2.get(compositeConfiguration);

			Iterator<Configuration> itr = configurations.iterator();

			while (itr.hasNext()) {
				Configuration configuration = itr.next();

				if (!(configuration instanceof MapConfiguration)) {
					return;
				}

				MapConfiguration mapConfiguration =
					(MapConfiguration)configuration;

				if (mapConfiguration.getMap() == properties) {
					itr.remove();

					aggregatedProperties.removeConfiguration(configuration);
				}
			}

			clearCache();
		}
		catch (Exception e) {
			_log.error("The properties could not be removed", e);
		}
	}

	public void set(String key, String value) {
		ComponentProperties componentProperties = getComponentProperties();

		componentProperties.setProperty(key, value);

		_values.put(key, value);
	}

	protected String buildFilterCacheKey(
		String key, Filter filter, boolean arrayValue) {

		if (filter.getVariables() != null) {
			return null;
		}

		String[] selectors = filter.getSelectors();

		int length = 0;

		if (arrayValue) {
			length = selectors.length + 2;
		}
		else {
			length = selectors.length + 1;
		}

		StringBundler sb = new StringBundler(length);

		if (arrayValue) {
			sb.append(_ARRAY_KEY_PREFIX);
		}

		sb.append(key);
		sb.append(selectors);

		return sb.toString();
	}

	protected Object fixArrayValue(String cacheKey, String[] array) {
		if (cacheKey == null) {
			return array;
		}

		Object value = _nullValue;

		if ((array != null) && (array.length > 0)) {

			// Commons Configuration parses an empty property into a String
			// array with one String containing one space. It also leaves a
			// trailing array member if you set a property in more than one
			// line.

			if (Validator.isNull(array[array.length - 1])) {
				String[] subArray = new String[array.length - 1];

				System.arraycopy(array, 0, subArray, 0, subArray.length);

				array = subArray;
			}

			if (array.length > 0) {
				value = array;
			}
		}

		_values.put(cacheKey, value);

		return value;
	}

	protected ComponentProperties getComponentProperties() {
		return _componentConfiguration.getProperties();
	}

	protected com.germinus.easyconf.Filter getEasyConfFilter(Filter filter) {
		com.germinus.easyconf.Filter easyConfFilter =
			com.germinus.easyconf.Filter.by(filter.getSelectors());

		if (filter.getVariables() != null) {
			easyConfFilter.setVariables(filter.getVariables());
		}

		return easyConfFilter;
	}

	protected String getFileName(ClassLoader classLoader, String name) {
		URL url = classLoader.getResource(name + ".properties");

		// If the resource is located inside of a JAR, then EasyConf needs the
		// "jar:file:" prefix appended to the path. Use URL.toExternalForm() to
		// achieve that. When running under JBoss, the protocol returned is
		// "vfs", "vfsfile", or "vfszip". When running under OC4J, the protocol
		// returned is "code-source". When running under WebLogic, the protocol
		// returned is "zip". When running under WebSphere, the protocol
		// returned is "wsjar".

		String protocol = url.getProtocol();

		if (protocol.equals("code-source") || protocol.equals("jar") ||
			protocol.equals("vfs") || protocol.equals("vfsfile") ||
			protocol.equals("vfszip") || protocol.equals("wsjar") ||
			protocol.equals("zip")) {

			name = url.toExternalForm();
		}
		else {
			try {
				name = new URI(url.getPath()).getPath();
			}
			catch (URISyntaxException urise) {
				name = url.getFile();
			}
		}

		int pos = name.lastIndexOf(".properties");

		if (pos != -1) {
			name = name.substring(0, pos);
		}

		return name;
	}

	protected void printSources(long companyId, String webId) {
		ComponentProperties componentProperties = getComponentProperties();

		List<String> sources = componentProperties.getLoadedSources();

		for (int i = sources.size() - 1; i >= 0; i--) {
			String source = sources.get(i);

			if (_printedSources.contains(source)) {
				continue;
			}

			_printedSources.add(source);

			String info = "Loading " + source;

			if (companyId > CompanyConstants.SYSTEM) {
				info +=
					" for {companyId=" + companyId + ", webId=" + webId + "}";
			}

			System.out.println(info);
		}
	}

	private static final String _ARRAY_KEY_PREFIX = "ARRAY_";

	private static final boolean _PRINT_DUPLICATE_CALLS_TO_GET = false;

	private static Log _log = LogFactoryUtil.getLog(ConfigurationImpl.class);

	private static String[] _emptyArray = new String[0];
	private static Object _nullValue = new Object();

	private ComponentConfiguration _componentConfiguration;
	private Set<String> _printedSources = new HashSet<String>();
	private Map<String, Object> _values =
		new ConcurrentHashMap<String, Object>();

}