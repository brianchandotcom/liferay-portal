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

package com.liferay.arquillian.util;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

/**
 * @author Shuyang Zhou
 */
public class PropsUtil {

	public static String get(String name) {
		return _properties.getProperty(name);
	}

	private static final Properties _properties = new Properties();

	static {
		try (InputStream inputStream = PropsUtil.class.getResourceAsStream(
				"/config.properties");
			InputStream extInputStream = PropsUtil.class.getResourceAsStream(
				"/config-ext.properties")) {

			if (inputStream != null) {
				_properties.load(inputStream);
			}

			if (extInputStream != null) {
				_properties.load(extInputStream);
			}
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

}