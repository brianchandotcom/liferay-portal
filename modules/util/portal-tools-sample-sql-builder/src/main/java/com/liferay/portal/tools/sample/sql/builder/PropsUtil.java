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

package com.liferay.portal.tools.sample.sql.builder;

import com.liferay.portal.kernel.util.SortedProperties;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java.util.Properties;

/**
 * @author Lily Chi
 */
public class PropsUtil {

	public static String get(String key) {
		return _properties.getProperty(key);
	}

	private static final Properties _properties = new SortedProperties() {
		{
			Reader reader = null;

			try {
				reader = new FileReader(
					System.getProperty("properties.file.path"));

				load(reader);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (reader != null) {
					try {
						reader.close();
					}
					catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}
		}
	};

}