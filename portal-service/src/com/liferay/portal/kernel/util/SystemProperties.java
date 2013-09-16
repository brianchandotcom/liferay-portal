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

import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Mirco Tamburini
 * @author Brett Randall
 */
public class SystemProperties {

	public static final String SYSTEM_PROPERTIES_FINAL =
		"system.properties.final";

	public static final String SYSTEM_PROPERTIES_LOAD =
		"system.properties.load";

	public static final String SYSTEM_PROPERTIES_QUIET =
		"system.properties.quiet";

	public static final String TMP_DIR = "java.io.tmpdir";

	public static String get(String key) {
		String value = _properties.get(key);

		if (value == null) {
			value = System.getProperty(key);
		}

		return value;
	}

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

	private static boolean _loaded;
	private static Map<String, String> _properties;

	static {
		reload();
	}

}