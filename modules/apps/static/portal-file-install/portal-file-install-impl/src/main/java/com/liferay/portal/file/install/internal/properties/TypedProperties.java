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

package com.liferay.portal.file.install.internal.properties;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.util.Arrays;
import java.util.Set;

/**
 * @author Matthew Tambara
 */
public class TypedProperties {

	public void clear() {
		_storage.clear();
	}

	public Object get(String key) {
		String value = _storage.get(key);

		if ((value != null) && _storage.isTyped()) {
			return _convertFromString(value);
		}

		return value;
	}

	public Set<String> keySet() {
		return _storage.keySet();
	}

	public void load(Reader reader) throws IOException {
		_storage.loadLayout(reader);
	}

	public Object put(String key, Object value) {
		if ((value instanceof String) && !_storage.isTyped()) {
			return _storage.put(key, (String)value);
		}

		_ensureTyped();

		String old = _storage.put(key, _convertToString(value));

		if (old == null) {
			return null;
		}

		return _convertFromString(old);
	}

	public Object remove(Object key) {
		return _storage.remove(key);
	}

	public void save(Writer writer) throws IOException {
		_storage.save(writer);
	}

	private static Object _convertFromString(String value) {
		try {
			return ConfigurationHandler.read(value);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static String _convertToString(Object value) {
		try {
			return ConfigurationHandler.write(value);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _ensureTyped() {
		if (!_storage.isTyped()) {
			_storage.setTyped(true);

			for (String key : _storage.keySet()) {
				String string = _convertToString(_storage.get(key));

				_storage.put(
					key, _storage.getComments(key),
					Arrays.asList(string.split("\n")));
			}
		}
	}

	private final Properties _storage = new Properties();

}