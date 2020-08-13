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

		boolean hasProperty = false;

		while (propertiesReader.nextProperty()) {
			hasProperty = true;

			_storage.put(
				propertiesReader.getPropertyName(),
				propertiesReader.getPropertyValue());

			int index = _checkHeaderComment(propertiesReader.getComments());

			List<String> comments = propertiesReader.getComments();

			int size = comments.size();

			if (index < comments.size()) {
				comments = new ArrayList<>(comments.subList(index, size));
			}
			else {
				comments = null;
			}

			_layoutMap.put(
				propertiesReader.getPropertyName(),
				new Layout(
					comments, new ArrayList<>(propertiesReader.getValues())));
		}

		if (hasProperty) {
			_footers = new ArrayList<>(propertiesReader.getComments());
		}
		else {
			_headers = new ArrayList<>(propertiesReader.getComments());
		}
	}

	public Object put(String key, Object value) {
		String old = _put(key, _convertToString(value));

		if (old == null) {
			return null;
		}

		return _convertFromString(old);
	}

	public String remove(Object key) {
		Layout layout = _layoutMap.get(key);

		if (layout != null) {
			layout.clearValue();
		}

		return _storage.remove(key);
	}

	public void save(Writer writer) throws IOException {
		_saveLayout(writer);
	}

	public static class PropertiesReader extends BufferedReader {

		public PropertiesReader(Reader reader) {
			super(reader);
		}

		public List<String> getComments() {
			return _comments;
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

			String[] property = _parseProperty(line);

			if (property[1].length() >= 2) {
				Matcher matcher = _pattern.matcher(property[1]);

				if (!matcher.matches()) {
					_log.error("Unable to read property line " + line);

					return false;
				}
			}

			_propertyName = property[0];

			_propertyValue = InterpolationUtil.substVars(property[1]);

			return true;
		}

		private static boolean _checkCombineLines(String line) {
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

		private static String[] _parseProperty(String line) {

			// sorry for this spaghetti code, please replace it as soon as
			// possible with a regexp when the Java 1.3 requirement is dropped

			String[] result = new String[2];

			StringBundler keySB = new StringBundler();
			StringBundler valueSB = new StringBundler();

			// state of the automaton:
			// 0: key parsing
			// 1: antislash found while parsing the key
			// 2: separator crossing
			// 3: white spaces
			// 4: value parsing

			int state = 0;

			for (int pos = 0; pos < line.length(); pos++) {
				char c = line.charAt(pos);

				if (state == 0) {
					if (c == '\\') {
						state = 1;
					}
					else if (_contains(_WHITE_SPACE, c)) {

						// switch to the separator crossing state

						state = 2;
					}
					else if (_contains(_SEPARATORS, c)) {

						// switch to the value parsing state

						state = 3;
					}
					else {
						keySB.append(c);
					}
				}
				else if (state == 1) {
					if (_contains(_SEPARATORS, c) ||
						_contains(_WHITE_SPACE, c)) {

						// this is an escaped separator or white space

						keySB.append(c);
					}
					else {

						// another escaped character, the '\' is preserved

						keySB.append('\\');
						keySB.append(c);
					}

					// return to the key parsing state

					state = 0;
				}
				else if (state == 2) {
					if (_contains(_WHITE_SPACE, c)) {

						// do nothing, eat all white spaces

						state = 2;
					}
					else if (_contains(_SEPARATORS, c)) {

						// switch to the value parsing state

						state = 3;
					}
					else {

						// any other character indicates we encoutered the
						// beginning of the value

						valueSB.append(c);

						// switch to the value parsing state

						state = 4;
					}
				}
				else if (state == 3) {
					if (_contains(_WHITE_SPACE, c)) {

						// do nothing, eat all white spaces

						state = 3;
					}
					else {

						// any other character indicates we encoutered the
						// beginning of the value

						valueSB.append(c);

						// switch to the value parsing state

						state = 4;
					}
				}
				else if (state == 4) {
					valueSB.append(c);
				}
			}

			result[0] = keySB.toString();
			result[1] = valueSB.toString();

			return result;
		}

		private String _readProperty() throws IOException {
			_comments.clear();
			_values.clear();

			StringBundler sb = new StringBundler();

			while (true) {
				String line = readLine();

				if (line == null) {

					// EOF

					return null;
				}

				if (_isCommentLine(line)) {
					_comments.add(line);

					continue;
				}

				boolean combine = _checkCombineLines(line);

				if (combine) {
					line = line.substring(0, line.length() - 1);
				}

				_values.add(line);

				while ((line.length() > 0) &&
					   _contains(_WHITE_SPACE, line.charAt(0))) {

					line = line.substring(1);
				}

				sb.append(line);

				if (!combine) {
					break;
				}
			}

			return sb.toString();
		}

		private final List<String> _comments = new ArrayList<>();
		private final Pattern _pattern = Pattern.compile(
			"\\s*[TILFDXSCBilfdxscb]?(\\[[\\S\\s]*\\]|\\{[\\S\\s]*\\}|" +
				"\\([\\S\\s]*\\)|\"[\\S\\s]*\")\\s*");
		private String _propertyName;
		private String _propertyValue;
		private final List<String> _values = new ArrayList<>();

	}

	public static class PropertiesWriter extends FilterWriter {

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

	private static boolean _contains(char[] array, char valueToFind) {
		if (array == null) {
			return false;
		}

		for (char c : array) {
			if (valueToFind == c) {
				return true;
			}
		}

		return false;
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

	private static boolean _isCommentLine(String line) {
		String string = line.trim();

		// blank lines are also treated as comment lines

		if ((string.length() < 1) ||
			(_COMMENT_CHARS.indexOf(string.charAt(0)) >= 0)) {

			return true;
		}

		return false;
	}

	private int _checkHeaderComment(List<String> comments) {
		if ((_headers == null) && _layoutMap.isEmpty()) {

			// This is the first comment. Search for blank lines.

			int index = comments.size() - 1;

			while (index >= 0) {
				String commentLine = comments.get(index);

				if (commentLine.length() <= 0) {
					break;
				}

				index--;
			}

			_setHeader(new ArrayList<>(comments.subList(0, index + 1)));

			return index + 1;
		}

		return 0;
	}

	private String _put(String key, String value) {
		String old = _storage.put(key, value);

		if ((old == null) || !old.equals(value)) {
			Layout layout = _layoutMap.get(key);

			if (layout != null) {
				layout.clearValue();
			}
		}

		return old;
	}

	private void _saveLayout(Writer writer) throws IOException {
		try (PropertiesWriter propertiesWriter = new PropertiesWriter(writer)) {
			if (_headers != null) {
				for (String s : _headers) {
					propertiesWriter.writeln(s);
				}
			}

			for (Map.Entry<String, String> entry : _storage.entrySet()) {
				String key = entry.getKey();

				String value = entry.getValue();

				Layout layout = _layoutMap.get(key);

				if (layout == null) {
					propertiesWriter.writeProperty(key, value);

					continue;
				}

				List<String> comments = layout.getComments();

				if (comments != null) {
					for (String string : comments) {
						propertiesWriter.writeln(string);
					}
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

			if (_footers != null) {
				for (String string : _footers) {
					propertiesWriter.writeln(string);
				}
			}
		}
	}

	private void _setHeader(List<String> headers) {
		_headers = headers;
	}

	private static final String _COMMENT_CHARS = "#!";

	private static final String _LINE_SEPARATOR = System.getProperty(
		"line.separator");

	private static final char[] _SEPARATORS = {CharPool.EQUAL, CharPool.COLON};

	private static final char[] _WHITE_SPACE = {CharPool.SPACE, '\t', '\f'};

	private static final Log _log = LogFactoryUtil.getLog(
		TypedProperties.class);

	private List<String> _footers;
	private List<String> _headers;
	private final Map<String, Layout> _layoutMap = new LinkedHashMap<>();
	private final Map<String, String> _storage = new LinkedHashMap<>();

	private static class Layout {

		public Layout(List<String> comments, List<String> values) {
			_comments = comments;
			_values = values;
		}

		public void clearValue() {
			_values = null;
		}

		public List<String> getComments() {
			return _comments;
		}

		public List<String> getValues() {
			return _values;
		}

		private final List<String> _comments;
		private List<String> _values;

	}

}