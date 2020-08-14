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

import java.io.BufferedReader;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Matthew Tambara
 */
public class TypedProperties {

	public void clear() {
		for (Layout layout : _layoutMap.values()) {
			layout.clearValue();
		}

		_storage.clear();
	}

	public Object get(String key) {
		String value = _storage.get(key);

		if (value != null) {
			return _convertFromString(value);
		}

		return value;
	}

	public Set<String> keySet() {
		return _storage.keySet();
	}

	public void load(Reader reader) throws IOException {
		PropertiesReader propertiesReader = new PropertiesReader(reader);

		while (propertiesReader.nextProperty()) {
			_storage.put(
				propertiesReader.getPropertyName(),
				propertiesReader.getPropertyValue());

			_layoutMap.put(
				propertiesReader.getPropertyName(),
				new Layout(new ArrayList<>(propertiesReader.getValues())));
		}

		_header = propertiesReader.getComment();
	}

	public Object put(String key, Object value) {
		String old = _storage.put(key, _convertToString(value));

		if ((old == null) || !old.equals(value)) {
			Layout layout = _layoutMap.get(key);

			if (layout != null) {
				layout.clearValue();
			}
		}

		if (old == null) {
			return null;
		}

		return _convertFromString(old);
	}

	public String remove(String key) {
		Layout layout = _layoutMap.get(key);

		if (layout != null) {
			layout.clearValue();
		}

		return _storage.remove(key);
	}

	public void save(Writer writer) throws IOException {
		try (PropertiesWriter propertiesWriter = new PropertiesWriter(writer)) {
			if (_header != null) {
				propertiesWriter.writeln(_header);
			}

			for (Map.Entry<String, String> entry : _storage.entrySet()) {
				String key = entry.getKey();

				String value = entry.getValue();

				Layout layout = _layoutMap.get(key);

				if (layout == null) {
					propertiesWriter.writeProperty(key, value);

					continue;
				}

				List<String> values = layout.getValues();

				if (values == null) {
					propertiesWriter.writeProperty(key, value);

					continue;
				}

				int size = values.size();

				for (int i = 0; i < size; i++) {
					String string = values.get(i);

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
			return _values;
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
	private final Map<String, Layout> _layoutMap = new LinkedHashMap<>();
	private final Map<String, String> _storage = new LinkedHashMap<>();

	private static class Layout {

		public Layout(List<String> values) {
			_values = values;
		}

		public void clearValue() {
			_values = null;
		}

		public List<String> getValues() {
			return _values;
		}

		private List<String> _values;

	}

}