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

import java.nio.charset.StandardCharsets;

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
		load(new FileInputStream(file));
	}

	public Set<String> propertyNames() {
		return _properties.keySet();
	}

	public void setProperty(String key, String value) {
		_properties.put(key, value);
	}

	public void store(File file) throws IOException {
		try (PrintWriter printWriter = new PrintWriter(
				file, StandardCharsets.UTF_8.name())) {

			for (String name : propertyNames()) {
				String value = getProperty(name);

				if (name.endsWith(".dir") || name.endsWith(".dirs") ||
					name.endsWith("liferay.home")) {

					value = value.replace('\\', '/');
				}

				printWriter.println(name + "=" + value);
			}
		}
	}

	protected void load(InputStream inputStream) throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

			String name = null;
			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();

				if (line.isEmpty() || (line.charAt(0) == '#')) {
					continue;
				}

				int index = line.indexOf("=");

				String value;

				if (index > 0) {
					name = line.substring(0, index);
					value = line.substring(index + 1);
				}
				else {
					value = _properties.get(name) + "\n" + line;
				}

				_properties.put(name, value);
			}
		}
	}

	private final Map<String, String> _properties;

}