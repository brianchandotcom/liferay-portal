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

		System.out.println("Setting property \"" + key + "\" to: " + value);

		Class<?> poshiPropertiesClass = poshiProperties.getClass();

		try {
			Field field = poshiPropertiesClass.getField(toCamelCase(key));

			field.set(poshiProperties, value);
		}
		catch (IllegalAccessException illegalAccessException) {
			System.out.println(
				"Unable to set PoshiProperties." + toCamelCase(key));
		}
		catch (NoSuchFieldException noSuchFieldException) {
			System.out.println(
				"PoshiProperties. " + toCamelCase(key) + " does not exist");
		}
	}

	public static void setProperties(Properties properties) {
		_setProperties(properties);

		PoshiProperties poshiProperties = PoshiProperties.getPoshiProperties();

		poshiProperties.printProperties(true);
	}

	public static String toCamelCase(String propertyName) {
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

}