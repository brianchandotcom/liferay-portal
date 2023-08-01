/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.util;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;

import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class PropsUtil {

	public static void clear() {
		PoshiProperties poshiProperties = PoshiProperties.getPoshiProperties();

		poshiProperties.clear();

		_setProperties(_getClassProperties());
	}

	public static String get(String key) {
		PoshiProperties poshiProperties = PoshiProperties.getPoshiProperties();

		String value = poshiProperties.getProperty(key);

		if (Validator.isNull(value)) {
			value = System.getProperty(key);
		}

		return value;
	}

	public static String getEnvironmentVariable(String name) {
		return System.getenv(name);
	}

	public static Properties getProperties() {
		return PoshiProperties.getPoshiProperties();
	}

	public static void set(String key, String value) {
		PoshiProperties poshiProperties = PoshiProperties.getPoshiProperties();

		poshiProperties.setProperty(key, value);

		if (poshiProperties.debugStacktrace) {
			System.out.println("Setting property \"" + key + "\" to: " + value);
		}

		Class<?> poshiPropertiesClass = poshiProperties.getClass();

		try {
			Field field = poshiPropertiesClass.getField(_toCamelCase(key));

			field.set(poshiProperties, value);
		}
		catch (IllegalAccessException illegalAccessException) {
			if (poshiProperties.debugStacktrace) {
				System.out.println("Unable to set field " + _toCamelCase(key));
			}
		}
		catch (NoSuchFieldException noSuchFieldException) {
			if (poshiProperties.debugStacktrace) {
				System.out.println(
					"Field " + _toCamelCase(key) + " does not exist");
			}
		}
	}

	public static void setProperties(Properties properties) {
		_setProperties(properties);

		PoshiProperties poshiProperties = PoshiProperties.getPoshiProperties();

		poshiProperties.printProperties(true);
	}

	private static Properties _getClassProperties() {
		Properties classProperties = new Properties();

		String[] propertiesFileNames = {
			"poshi.properties", "poshi-ext.properties"
		};

		for (String propertiesFileName : propertiesFileNames) {
			Class<?> clazz = PropsUtil.class;

			ClassLoader classLoader = clazz.getClassLoader();

			InputStream inputStream = classLoader.getResourceAsStream(
				propertiesFileName);

			if (inputStream != null) {
				try {
					classProperties.load(inputStream);
				}
				catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		}

		return classProperties;
	}

	private static void _setProperties(Properties properties) {
		PoshiProperties poshiProperties = PoshiProperties.getPoshiProperties();

		for (String propertyName : properties.stringPropertyNames()) {
			String propertyValue = properties.getProperty(propertyName);

			if (propertyValue == null) {
				continue;
			}

			poshiProperties.setProperty(propertyName, propertyValue);
		}
	}

	private static String _toCamelCase(String propertyName) {
		String[] terms = propertyName.split("\\.");

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < terms.length; i++) {
			String term = terms[i];

			if (i != 0) {
				term = StringUtil.capitalize(term);
			}

			sb.append(term);
		}

		return sb.toString();
	}

}