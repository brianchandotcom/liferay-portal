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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.file.install.properties.ConfigurationHandler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Matthew Tambara
 */
public class TypedProperties {

	public Object get(String key) throws IOException {
		Map.Entry<String, List<String>> entry = _storage.get(key);

		if (entry == null) {
			return null;
		}

		String string = entry.getKey();

		return ConfigurationHandler.read(string);
	}

	public Set<String> keySet() {
		return _storage.keySet();
	}

	public void load(Reader reader) throws IOException {
		PropertiesReader propertiesReader = new PropertiesReader(reader);

		while (propertiesReader.nextProperty()) {
			_storage.put(
				propertiesReader.getPropertyName(),
				new AbstractMap.SimpleImmutableEntry<>(
					propertiesReader.getPropertyValue(),
					propertiesReader.getValues()));
		}

		_header = propertiesReader.getComment();
	}

	public void put(String key, Object value) throws IOException {
		Map.Entry<String, List<String>> oldEntry = _storage.get(key);

		List<String> values = null;

		if (oldEntry != null) {
			Object oldObject = ConfigurationHandler.read(oldEntry.getKey());

			if (Objects.equals(oldObject, value)) {
				values = oldEntry.getValue();
			}
			else {
				Class<?> clazz = value.getClass();

				if (clazz.isArray() && Objects.deepEquals(oldObject, value)) {
					values = oldEntry.getValue();
				}
			}
		}

		_storage.put(
			key,
			new AbstractMap.SimpleImmutableEntry<>(
				ConfigurationHandler.write(value), values));
	}

	public void remove(String key) {
		_storage.remove(key);
	}

	public void save(Writer writer) throws IOException {
		if ((_header == null) && _storage.isEmpty()) {
			return;
		}

		StringBundler sb = new StringBundler();

		if (_header != null) {
			sb.append(_header);
			sb.append(_LINE_SEPARATOR);
		}

		for (Map.Entry<String, Map.Entry<String, List<String>>> entry :
				_storage.entrySet()) {

			Map.Entry<String, List<String>> valuesEntry = entry.getValue();

			List<String> layout = valuesEntry.getValue();

			if (layout == null) {
				sb.append(entry.getKey());
				sb.append(_EQUALS_WITH_SPACES);
				sb.append(valuesEntry.getKey());
				sb.append(_LINE_SEPARATOR);

				continue;
			}

			int size = layout.size();

			for (int i = 0; i < size; i++) {
				String string = layout.get(i);

				sb.append(string);

				if (i < (size - 1)) {
					sb.append("\\");
				}

				sb.append(_LINE_SEPARATOR);
			}
		}

		sb.setIndex(sb.index() - 1);

		writer.write(sb.toString());
	}

	private boolean _isCommentLine(String line) {
		String string = line.trim();

		if (CharPool.POUND == string.charAt(0)) {
			return true;
		}

		return false;
	}

	private static final String _EQUALS_WITH_SPACES = " = ";

	private static final String _LINE_SEPARATOR = System.getProperty(
		"line.separator");

	private static final Log _log = LogFactoryUtil.getLog(
		TypedProperties.class);

	private String _header;
	private final Map<String, Map.Entry<String, List<String>>> _storage =
		new LinkedHashMap<>();

	private class PropertiesReader extends BufferedReader {

		public String getComment() {
			return _comment;
		}

		public String getPropertyName() {
			return _propertyName;
		}

		public String getPropertyValue() {
			return _propertyValue;
		}

		public List<String> getValues() {
			return new ArrayList<>(_values);
		}

		public boolean nextProperty() throws IOException {
			String line = _readProperty();

			if (line == null) {
				return false;
			}

			Matcher matcher = _linePattern.matcher(line);

			if (!matcher.matches()) {
				_log.error("Unable to parse config line: " + line);

				return false;
			}

			String key = matcher.group(1);

			String value = matcher.group(2);

			value = InterpolationUtil.substVars(value.trim());

			_propertyName = key.trim();

			_propertyValue = value;

			return true;
		}

		private PropertiesReader(Reader reader) {
			super(reader);
		}

		private boolean _checkCombineLines(String line) {
			if (line.charAt(line.length() - 1) == '\\') {
				return true;
			}

			return false;
		}

		private String _readProperty() throws IOException {
			_values.clear();

			StringBundler sb = new StringBundler();

			List<String> comments = new ArrayList<>();

			while (true) {
				String line = readLine();

				if (line == null) {
					return null;
				}

				if (line.isEmpty()) {
					_values.add(line);

					continue;
				}

				if (_isCommentLine(line)) {
					comments.add(line);

					if (!_storage.isEmpty()) {
						_log.error(
							"Comment must be at beginning of config file: " +
								line);
					}
					else if (_comment == null) {
						_comment = line;
					}

					continue;
				}

				boolean combine = _checkCombineLines(line);

				if (combine) {
					line = line.substring(0, line.length() - 1);
				}

				_values.add(line);

				sb.append(line.trim());

				if (!combine) {
					break;
				}
			}

			if (comments.size() > 1) {
				_log.error("Multiple comment lines found: " + comments);
			}

			return sb.toString();
		}

		private String _comment;
		private final Pattern _linePattern = Pattern.compile(
			"(\\s*[0-9a-zA-Z-_\\.]+\\s*)=(\\s*[TILFDXSCBilfdxscb]?" +
				"(\\[[\\S\\s]*\\]|\\{[\\S\\s]*\\}|" +
					"\\([\\S\\s]*\\)|\"[\\S\\s]*\")\\s*)");
		private String _propertyName;
		private String _propertyValue;
		private final List<String> _values = new ArrayList<>();

	}

}