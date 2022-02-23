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

package com.liferay.site.initializer.testray.extra.java.function.util;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

/**
 * @author José Abelenda
 */
public class PropsUtil {

	public static String get(String key) {
		return _propsUtil._get(key);
	}

	private PropsUtil() {
		try (InputStream inputStream = PropsUtil.class.getResourceAsStream(
				"/application.properties")) {

			_properties.load(inputStream);
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	private String _get(String key) {
		return _properties.getProperty(key);
	}

	private static final PropsUtil _propsUtil = new PropsUtil();

	private final Properties _properties = new Properties();

}