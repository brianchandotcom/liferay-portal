/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Mirco Tamburini
 * @author Brett Randall
 * @author Shuyang Zhou
 */
public class SystemProperties {

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static final String SYSTEM_PROPERTIES_FINAL =
		"system.properties.final";

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static final String SYSTEM_PROPERTIES_LOAD =
		"system.properties.load";

	public static final String SYSTEM_PROPERTIES_QUIET =
		"system.properties.quiet";

	public static final String SYSTEM_PROPERTIES_SET = "system.properties.set";

	public static final String SYSTEM_PROPERTIES_SET_OVERRIDE =
		"system.properties.set.override";

	public static final String TMP_DIR = "java.io.tmpdir";

	public static String get(String key) {
		String value = _properties.get(key);

		if (value == null) {
			value = System.getProperty(key);
		}

		return value;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String[] getArray(String key) {
		String value = get(key);

		if (value == null) {
			return new String[0];
		}
		else {
			return StringUtil.split(value);
		}
	}

	public static Properties getProperties() {
		return PropertiesUtil.fromMap(_properties);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static void reload() {
		if (_loaded) {
			return;
		}

		Properties properties = new Properties();

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		boolean systemPropertiesQuiet = GetterUtil.getBoolean(
			System.getProperty(SYSTEM_PROPERTIES_QUIET));

		// system.properties

		try {
			URL url = classLoader.getResource("system.properties");

			if (url != null) {
				InputStream inputStream = url.openStream();

				properties.load(inputStream);

				inputStream.close();

				if (!systemPropertiesQuiet) {
					System.out.println("Loading " + url);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// system-ext.properties

		try {
			URL url = classLoader.getResource("system-ext.properties");

			if (url != null) {
				_loaded = true;

				InputStream inputStream = url.openStream();

				properties.load(inputStream);

				inputStream.close();

				if (!systemPropertiesQuiet) {
					System.out.println("Loading " + url);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Set environment properties

		SystemEnv.setProperties(properties);

		// Set system properties

		boolean systemPropertiesLoad = GetterUtil.getBoolean(
			System.getProperty(SYSTEM_PROPERTIES_LOAD), true);

		boolean systemPropertiesFinal = GetterUtil.getBoolean(
			System.getProperty(SYSTEM_PROPERTIES_FINAL), true);

		if (systemPropertiesLoad) {
			Enumeration<String> enu =
				(Enumeration<String>)properties.propertyNames();

			while (enu.hasMoreElements()) {
				String key = enu.nextElement();

				if (systemPropertiesFinal ||
					Validator.isNull(System.getProperty(key))) {

					System.setProperty(key, properties.getProperty(key));
				}
			}
		}

		_properties = new ConcurrentHashMap<String, String>();

		// Use a fast concurrent hash map implementation instead of the slower
		// java.util.Properties

		PropertiesUtil.fromProperties(properties, _properties);
	}

	public static void set(String key, String value) {
		System.setProperty(key, value);

		_properties.put(key, value);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	private static boolean _loaded;
	private static Map<String, String> _properties;

	static {
		Properties properties = new Properties();

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		List<URL> urls = null;

		if (!GetterUtil.getBoolean(
				System.getProperty(SYSTEM_PROPERTIES_QUIET))) {

			urls = new ArrayList<URL>();
		}

		// system.properties

		try {
			URL url = classLoader.getResource("system.properties");

			if (url != null) {
				InputStream inputStream = url.openStream();

				properties.load(inputStream);

				inputStream.close();

				if (urls != null) {
					urls.add(url);
				}
			}
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}

		// system-ext.properties

		try {
			URL url = classLoader.getResource("system-ext.properties");

			if (url != null) {
				InputStream inputStream = url.openStream();

				properties.load(inputStream);

				inputStream.close();

				if (urls != null) {
					urls.add(url);
				}
			}
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}

		// Set environment properties

		SystemEnv.setProperties(properties);

		// Set system properties

		if (GetterUtil.getBoolean(
				System.getProperty(SYSTEM_PROPERTIES_SET), true)) {

			boolean systemPropertiesSetOverride = GetterUtil.getBoolean(
				System.getProperty(SYSTEM_PROPERTIES_SET_OVERRIDE), true);

			for (Entry<Object, Object> entry : properties.entrySet()) {
				String key = String.valueOf(entry.getKey());

				if (systemPropertiesSetOverride ||
					Validator.isNull(System.getProperty(key))) {

					System.setProperty(key, String.valueOf(entry.getValue()));
				}
			}
		}

		_properties = new ConcurrentHashMap<String, String>();

		// Use a fast concurrent hash map implementation instead of the slower
		// java.util.Properties

		PropertiesUtil.fromProperties(properties, _properties);

		if (urls != null) {
			for (URL url : urls) {
				System.out.println("Loading " + url);
			}
		}
	}

}