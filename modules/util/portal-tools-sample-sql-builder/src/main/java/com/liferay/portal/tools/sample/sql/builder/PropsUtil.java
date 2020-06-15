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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java.time.ZoneId;

import java.util.Properties;
import java.util.TimeZone;

/**
 * @author Lily Chi
 */
public class PropsUtil {

	public static String get(String key) {
		return _properties.getProperty(key);
	}

	public static String getActualPropertiesContent() {
		StringBundler sb = new StringBundler();

		for (String key : _properties.stringPropertyNames()) {
			if (!key.startsWith("sample.sql")) {
				continue;
			}

			String value = _properties.getProperty(key);

			sb.append(key);
			sb.append(StringPool.EQUAL);
			sb.append(value);
			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	private static final Properties _properties = new SortedProperties() {
		{
			Reader reader = null;

			try {
				reader = new FileReader(
					System.getProperty("properties.file.path"));

				load(reader);

				TimeZone timeZone = TimeZone.getDefault();

				String timeZoneId = getProperty("sample.sql.db.time.zone");

				if (Validator.isNotNull(timeZoneId)) {
					timeZone = TimeZone.getTimeZone(ZoneId.of(timeZoneId));

					TimeZone.setDefault(timeZone);
				}
				else {
					setProperty("sample.sql.db.time.zone", timeZone.getID());
				}
			}
			catch (Exception exception) {
				exception.printStackTrace();
			}
			finally {
				if (reader != null) {
					try {
						reader.close();
					}
					catch (IOException ioException) {
						ioException.printStackTrace();
					}
				}
			}
		}
	};

}