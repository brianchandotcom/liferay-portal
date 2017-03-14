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

package com.liferay.portal.tools.db.upgrade.client.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.nio.charset.Charset;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author David Truong
 */
public class Properties {

	public Properties() {
		_properties = new LinkedHashMap<>();
	}

	public String getProperty(String key) {
		return _properties.get(key);
	}

	public void load(File file) throws IOException {
		String line;

		try (InputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(
				fis, Charset.forName("UTF-8"));
			BufferedReader br = new BufferedReader(isr)) {

			String key = "";

			while ((line = br.readLine()) != null) {
				if (!line.startsWith("#")) {
					int index = line.indexOf("=");

					String value;

					if (index > 0) {
						key = line.substring(0, index);
						value = line.substring(index + 1, line.length());
					}
					else {
						value = _properties.get(key) + "\n" + line;
					}

					_properties.put(key, value);
				}
			}
		}
	}

	public Set<String> propertyNames() {
		return _properties.keySet();
	}

	public void setProperty(String key, String value) {
		_properties.put(key, value);
	}

	public void store(File file) throws IOException {
		try (PrintWriter printWriter = new PrintWriter(file)) {
			Set<String> keys = propertyNames();

			for (String key : keys) {
				String value = getProperty(key);

				if (key.endsWith(".dir") || key.endsWith(".dirs") ||
					key.endsWith("liferay.home")) {

					value = value.replace('\\', '/');
				}

				printWriter.println(key + "=" + value);
			}
		}
	}

	private final Map<String, String> _properties;

}