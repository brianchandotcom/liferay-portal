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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.BufferedReader;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

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

	public Object get(String key) {
		ObjectValuePair<String, List<String>> objectValuePair = _storage.get(
			key);

		if (objectValuePair == null) {
			return null;
		}

		String string = objectValuePair.getKey();

		if (string != null) {
			return _convertFromString(string);
		}

		return string;
	}

	public Set<String> keySet() {
		return _storage.keySet();
	}

	public void load(Reader reader) throws IOException {
		PropertiesReader propertiesReader = new PropertiesReader(reader);

		while (propertiesReader.nextProperty()) {
			_storage.put(
				propertiesReader.getPropertyName(),
				new ObjectValuePair<>(
					propertiesReader.getPropertyValue(),
					propertiesReader.getValues()));
		}

		_header = propertiesReader.getComment();
	}

	public void put(String key, Object value) {
		ObjectValuePair<String, List<String>> old = _storage.get(key);

		ObjectValuePair<String, List<String>> objectValuePair =
			new ObjectValuePair<>(_convertToString(value), null);

		if (old != null) {
			Object oldObject = _convertFromString(old.getKey());

			if (Objects.equals(oldObject, value)) {
				objectValuePair.setValue(old.getValue());
			}
			else {
				Class<?> clazz = value.getClass();

				if (clazz.isArray() && Objects.deepEquals(oldObject, value)) {
					objectValuePair.setValue(old.getValue());
				}
			}
		}

		_storage.put(key, objectValuePair);
	}

	public void remove(String key) {
		_storage.remove(key);
	}

	public void save(Writer writer) throws IOException {
		try (PropertiesWriter propertiesWriter = new PropertiesWriter(writer)) {
			if (_header != null) {
				propertiesWriter.writeln(_header);
			}

			for (Map.Entry<String, ObjectValuePair<String, List<String>>>
					entry : _storage.entrySet()) {

				ObjectValuePair<String, List<String>> objectValuePair =
					entry.getValue();

				List<String> layout = objectValuePair.getValue();

				if ((layout == null) || layout.isEmpty()) {
					propertiesWriter.writeProperty(
						entry.getKey(), objectValuePair.getKey());

					continue;
				}

				int size = layout.size();

				for (int i = 0; i < size; i++) {
					String string = layout.get(i);

					if (i < (size - 1)) {
						propertiesWriter.writeln(string + "\\");
					}
					else {
						propertiesWriter.writeln(string);
					}
				}
			}
		}
	}

	public class PropertiesReader extends BufferedReader {

		public PropertiesReader(Reader reader) {
			super(reader);
		}

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
				return false; // EOF
			}

			// parse the line

			Matcher matcher = _linePattern.matcher(line);

			if (!matcher.matches()) {
				_log.error("Unable to parse config line: " + line);

				return false;
			}

			String key = matcher.group(1);

			String value = matcher.group(2);

			value = value.trim();

			if (value.length() >= 2) {
				matcher = _pattern.matcher(value);

				if (!matcher.matches()) {
					_log.error("Unable to read property line " + line);

					return false;
				}
			}

			_propertyName = key.trim();

			_propertyValue = InterpolationUtil.substVars(value);

			return true;
		}

		private boolean _checkCombineLines(String line) {
			int bsCount = 0;

			for (int i = line.length() - 1;
				 (i >= 0) && (line.charAt(i) == '\\'); i--) {

				bsCount++;
			}

			if ((bsCount % 2) != 0) {
				return true;
			}

			return false;
		}

		private String _readProperty() throws IOException {
			_values.clear();

			StringBundler sb = new StringBundler();

			while (true) {
				String line = readLine();

				if (line == null) {

					// EOF

					return null;
				}

				if (_isCommentLine(line)) {
					if ((_comment == null) && _values.isEmpty()) {
						_comment = line;
					}
					else {
						if (_log.isWarnEnabled()) {
							_log.warn("Multiple comment lines found: " + line);
						}
					}

					continue;
				}

				boolean combine = _checkCombineLines(line);

				if (combine) {
					line = line.substring(0, line.length() - 1);
				}

				_values.add(line);

				while ((line.length() > 0) &&
					   ArrayUtil.contains(_WHITE_SPACE, line.charAt(0))) {

					line = line.substring(1);
				}

				sb.append(line);

				if (!combine) {
					break;
				}
			}

			return sb.toString();
		}

		private String _comment;
		private final Pattern _linePattern = Pattern.compile("(.+)=(.+)");
		private final Pattern _pattern = Pattern.compile(
			"\\s*[TILFDXSCBilfdxscb]?(\\[[\\S\\s]*\\]|\\{[\\S\\s]*\\}|" +
				"\\([\\S\\s]*\\)|\"[\\S\\s]*\")\\s*");
		private String _propertyName;
		private String _propertyValue;
		private final List<String> _values = new ArrayList<>();

	}

	public class PropertiesWriter extends FilterWriter {

		public PropertiesWriter(Writer writer) {
			super(writer);
		}

		public void writeln(String string) throws IOException {
			if (string != null) {
				write(string);
			}

			write(_LINE_SEPARATOR);
		}

		public void writeProperty(String key, String value) throws IOException {
			write(key);
			write(" = ");
			write(value);

			writeln(null);
		}

	}

	private Object _convertFromString(String value) {
		try {
			return ConfigurationHandler.read(value);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private String _convertToString(Object value) {
		try {
			return ConfigurationHandler.write(value);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private boolean _isCommentLine(String line) {
		String string = line.trim();

		// blank lines are also treated as comment lines

		if ((string.length() < 1) || (CharPool.POUND == string.charAt(0))) {
			return true;
		}

		return false;
	}

	private static final String _LINE_SEPARATOR = System.getProperty(
		"line.separator");

	private static final char[] _WHITE_SPACE = {CharPool.SPACE, '\t', '\f'};

	private static final Log _log = LogFactoryUtil.getLog(
		TypedProperties.class);

	private String _header;
	private final Map<String, ObjectValuePair<String, List<String>>> _storage =
		new LinkedHashMap<>();

}