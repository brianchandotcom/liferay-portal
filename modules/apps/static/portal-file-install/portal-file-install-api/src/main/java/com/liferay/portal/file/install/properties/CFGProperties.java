/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.file.install.properties;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		try (UnsyncBufferedReader unsyncBufferedReader = _wrap(reader)) {
			String line = unsyncBufferedReader.readLine();

			List<String> lines = new ArrayList<>();

			StringBundler sb = new StringBundler();

			while (line != null) {
				lines.add(line);

				if ((line.length() < 1) || line.startsWith(StringPool.POUND) ||
					line.startsWith(StringPool.EXCLAMATION)) {

					line = unsyncBufferedReader.readLine();

					continue;
				}

				if (line.endsWith(StringPool.BACK_SLASH)) {
					String token = line.substring(0, line.length() - 1);

					sb.append(token.trim());

					line = unsyncBufferedReader.readLine();

					continue;
				}

				sb.append(line.trim());

				line = sb.toString();

				Matcher matcher = _configPattern.matcher(line);

				if (matcher.matches()) {
					throw new IllegalArgumentException(
						"Detected .config format in .cfg file in line: " +
							line);
				}

				int index = line.indexOf(CharPool.EQUAL);

				String key = line.substring(0, index);

				String value = line.substring(index + 1);

				_storage.put(
					key.trim(),
					new AbstractMap.SimpleImmutableEntry<>(
						InterpolationUtil.substVars(value.trim()),
						new ArrayList<>(lines)));

				lines.clear();

				sb.setIndex(0);

				line = unsyncBufferedReader.readLine();
			}
		}
	}

	@Override
	public void put(String key, Object value) throws IOException {
		StringBundler sb = new StringBundler();

		if (value instanceof Collection) {
			Collection<?> collection = (Collection<?>)value;

			for (Object object : collection) {
				sb.append(object.toString());
				sb.append(StringPool.COMMA);
			}

			if (!collection.isEmpty()) {
				sb.setIndex(sb.index() - 1);
			}
		}
		else {
			Class<?> clazz = value.getClass();

			if (clazz.isArray()) {
				Object[] array = (Object[])value;

				for (Object object : array) {
					sb.append(object.toString());
					sb.append(StringPool.COMMA);
				}

				if (array.length > 0) {
					sb.setIndex(sb.index() - 1);
				}
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

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		writer.write(sb.toString());
	}

	private UnsyncBufferedReader _wrap(Reader reader) {
		if (reader == null) {
			return null;
		}

		return new UnsyncBufferedReader(reader);
	}

	private static final String _LINE_SEPARATOR = System.getProperty(
		"line.separator");

	private static final Pattern _configPattern = Pattern.compile(
		"(\\s*[0-9a-zA-Z-_\\.]+\\s*)=(\\s*[TILFDXSCBilfdxscb]?" +
			"(\\[[\\S\\s]*\\]|\\{[\\S\\s]*\\}|" +
				"\\([\\S\\s]*\\)|\"[\\S\\s]*\")\\s*)");

	private final Map<String, Map.Entry<String, List<String>>> _storage =
		new LinkedHashMap<>();

}