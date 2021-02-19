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

import com.liferay.petra.io.unsync.UnsyncBufferedReader;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Matthew Tambara
 */
public class CFGProperties implements ConfigurationProperties {

	@Override
	public Object get(String key) throws IOException {
		Map.Entry<String, List<String>> entry = _storage.get(key);

		if (entry == null) {
			return null;
		}

		return entry.getKey();
	}

	@Override
	public Set<String> keySet() {
		return _storage.keySet();
	}

	@Override
	public void load(Reader reader) throws IOException {
		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(reader)) {

			String line = unsyncBufferedReader.readLine();

			List<String> lines = new ArrayList<>();

			String value = StringPool.BLANK;

			String key = null;

			while (line != null) {
				lines.add(line);

				if ((line.length() < 1) || line.startsWith(StringPool.POUND) ||
					line.startsWith(StringPool.EXCLAMATION)) {

					line = unsyncBufferedReader.readLine();

					continue;
				}

				int index = line.indexOf(CharPool.EQUAL);

				if (index == -1) {
					value = value.concat(line.trim());
				}
				else {
					key = line.substring(0, index);

					String valueToken = line.substring(index + 1);

					value = value.concat(valueToken);
				}

				if (line.endsWith(StringPool.BACK_SLASH)) {
					value = value.substring(0, value.length() - 1);

					line = unsyncBufferedReader.readLine();

					continue;
				}

				_storage.put(
					key.trim(),
					new AbstractMap.SimpleImmutableEntry<>(
						InterpolationUtil.substVars(value.trim()),
						new ArrayList<>(lines)));

				value = StringPool.BLANK;

				lines.clear();

				line = unsyncBufferedReader.readLine();
			}
		}
	}

	@Override
	public void put(String key, Object value) throws IOException {
		StringBundler sb = new StringBundler();

		if (value instanceof Collection) {
			for (Object object : (Collection)value) {
				sb.append(object.toString());
				sb.append(StringPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);
		}
		else {
			Class<?> clazz = value.getClass();

			if (clazz.isArray()) {
				for (Object object : (Object[])value) {
					sb.append(object.toString());
					sb.append(StringPool.COMMA);
				}

				sb.setIndex(sb.index() - 1);
			}
			else {
				sb.append(value.toString());
			}
		}

		_storage.put(
			key, new AbstractMap.SimpleImmutableEntry<>(sb.toString(), null));
	}

	@Override
	public void remove(String key) {
		_storage.remove(key);
	}

	@Override
	public void save(Writer writer) throws IOException {
		StringBundler sb = new StringBundler();

		for (Map.Entry<String, Map.Entry<String, List<String>>> entry :
				_storage.entrySet()) {

			Map.Entry<String, List<String>> values = entry.getValue();

			List<String> lines = values.getValue();

			if (lines == null) {
				sb.append(entry.getKey());
				sb.append(StringPool.EQUAL);
				sb.append(values.getKey());
				sb.append(_LINE_SEPARATOR);
			}
			else {
				for (String line : lines) {
					sb.append(line);
					sb.append(_LINE_SEPARATOR);
				}
			}
		}

		sb.setIndex(sb.index() - 1);

		writer.write(sb.toString());
	}

	private static final String _LINE_SEPARATOR = System.getProperty(
		"line.separator");

	private final Map<String, Map.Entry<String, List<String>>> _storage =
		new LinkedHashMap<>();

}