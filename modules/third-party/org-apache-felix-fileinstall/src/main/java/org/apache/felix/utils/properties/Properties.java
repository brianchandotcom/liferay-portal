/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.felix.utils.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import java.net.URL;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.BundleContext;

/**
 * <p>
 * Enhancement of the standard <code>Properties</code>
 * managing the maintain of comments, etc.
 * </p>
 *
 * @author gnodet, jbonofre
 */
public class Properties extends AbstractMap<String, String> {

	/**
	 * The default encoding (ISO-8859-1 as specified by
	 * http://java.sun.com/j2se/1.5.0/docs/api/java/util/Properties.html)
	 */
	public static final String DEFAULT_ENCODING = "ISO-8859-1";

	/**
	 * <p>Checks if the value is in the given array.</p>
	 *
	 * <p>The method returns <code>false</code> if a <code>null</code> array is passed in.</p>
	 *
	 * @param array  the array to search through
	 * @param valueToFind  the value to find
	 * @return <code>true</code> if the array contains the object
	 */
	public static boolean contains(char[] array, char valueToFind) {
		if (array == null) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (valueToFind == array[i]) {
				return true;
			}
		}

		return false;
	}

	public Properties() {
	}

	public Properties(boolean substitute) {
		_substitute = substitute;
	}

	public Properties(File location) throws IOException {
		this(location, (InterpolationHelper.SubstitutionCallback)null);
	}

	public Properties(File location, boolean substitute) {
		_location = location;
		_substitute = substitute;
	}

	public Properties(File location, BundleContext context) throws IOException {
		this(
			location,
			new InterpolationHelper.BundleContextSubstitutionCallback(context));
	}

	public Properties(
			File location, InterpolationHelper.SubstitutionCallback callback)
		throws IOException {

		_location = location;
		_callback = callback;

		if (location.exists()) {
			load(location);
		}
	}

	@Override
	public void clear() {
		for (Layout layout : _layoutMap.values()) {
			layout.clearValue();
		}

		_storage.clear();
	}

	@Override
	public Set<Entry<String, String>> entrySet() {
		return new AbstractSet<Entry<String, String>>() {

			@Override
			public Iterator<Entry<String, String>> iterator() {
				return new KeyIterator();
			}

			@Override
			public int size() {
				return _storage.size();
			}

		};
	}

	public List<String> getComments(String key) {
		Layout layout = _layoutMap.get(key);

		if (layout != null) {
			List<String> commentLines = layout.getCommentLines();

			if (commentLines != null) {
				return new ArrayList<>(commentLines);
			}
		}

		return new ArrayList<>();
	}

	/**
	 * Return the comment footer.
	 *
	 * @return the comment footer
	 */
	public List<String> getFooter() {
		return _footer;
	}

	/**
	 * Return the comment header.
	 *
	 * @return the comment header
	 */
	public List<String> getHeader() {
		return _header;
	}

	/**
	 * Searches for the property with the specified key in this property list.
	 *
	 * @param key the property key.
	 * @return the value in this property list with the specified key value.
	 */
	public String getProperty(String key) {
		return get(key);
	}

	/**
	 * Searches for the property with the specified key in this property list. If the key is not found in this property
	 * list, the default property list, and its defaults, recursively, are then checked. The method returns the default
	 * value argument if the property is not found.
	 *
	 * @param key the property key.
	 * @param defaultValue a default value.
	 * @return The property value of the default value
	 */
	public String getProperty(String key, String defaultValue) {
		if (get(key) != null) {
			return get(key);
		}

		return defaultValue;
	}

	public List<String> getRaw(String key) {
		Layout layout = _layoutMap.get(key);

		if (layout != null) {
			List<String> valueLines = layout.getValueLines();

			if (valueLines != null) {
				return new ArrayList<>(valueLines);
			}
		}

		List<String> result = new ArrayList<>();

		if (_storage.containsKey(key)) {
			result.add(_storage.get(key));
		}

		return result;
	}

	public boolean isTyped() {
		return _typed;
	}

	public void load(File location) throws IOException {
		try (InputStream inputStream = new FileInputStream(location)) {
			load(inputStream);
		}
	}

	public void load(InputStream inputStream) throws IOException {
		load(new InputStreamReader(inputStream, DEFAULT_ENCODING));
	}

	public void load(Reader reader) throws IOException {
		loadLayout(reader, false);
	}

	public void load(URL url) throws IOException {
		try (InputStream inputStream = url.openStream()) {
			load(inputStream);
		}
	}

	/**
	 * Reads a properties file and stores its internal structure. The found
	 * properties will be added to the associated configuration object.
	 *
	 * @param reader the reader to the properties file
	 * @throws IOException if an error occurs
	 */
	public void loadLayout(Reader reader, boolean maybeTyped)
		throws IOException {

		PropertiesReader propertiesReader = new PropertiesReader(
			reader, maybeTyped);

		boolean hasProperty = false;

		while (propertiesReader.nextProperty()) {
			hasProperty = true;

			_storage.put(
				propertiesReader.getPropertyName(),
				propertiesReader.getPropertyValue());

			int index = _checkHeaderComment(propertiesReader.getCommentLines());

			List<String> commentLines = propertiesReader.getCommentLines();

			int size = commentLines.size();

			if (index < commentLines.size()) {
				commentLines = commentLines.subList(index, size);
			}
			else {
				commentLines = null;
			}

			_layoutMap.put(
				propertiesReader.getPropertyName(),
				new Layout(
					commentLines,
					new ArrayList<>(propertiesReader.getValueLines())));
		}

		Boolean typed = propertiesReader.isTyped();

		if (!maybeTyped || (typed == null) || !typed) {
			_typed = false;

			for (Entry<String, String> entry : _storage.entrySet()) {
				entry.setValue(_unescapeJava(entry.getValue()));
			}
		}
		else {
			_typed = true;
		}

		if (hasProperty) {
			_footer = new ArrayList<>(propertiesReader.getCommentLines());
		}
		else {
			_header = new ArrayList<>(propertiesReader.getCommentLines());
		}

		if (_substitute) {
			substitute();
		}
	}

	/**
	 * Returns an enumeration of all the keys in this property list, including distinct keys in the default property
	 * list if a key of the same name has not already been found from the main properties list.
	 *
	 * @return an enumeration of all the keys in this property list, including the keys in the default property list.
	 */
	public Enumeration<?> propertyNames() {
		return Collections.enumeration(_storage.keySet());
	}

	public String put(
		String key, List<String> commentLines, List<String> valueLines) {

		commentLines = new ArrayList<>(commentLines);

		valueLines = new ArrayList<>(valueLines);

		String escapedKey = _escapeKey(key);

		StringBuilder sb = new StringBuilder();

		if (valueLines.isEmpty()) {
			valueLines.add(escapedKey + "=");

			sb.append(escapedKey);
			sb.append("=");
		}
		else {
			String value = valueLines.get(0);

			String realValue = value;

			if (!_typed) {
				realValue = _escapeJava(value);
			}

			value = value.trim();

			if (!value.startsWith(escapedKey)) {
				valueLines.set(0, escapedKey + " = " + realValue);

				sb.append(escapedKey);
				sb.append(" = ");
				sb.append(realValue);
			}
			else {
				valueLines.set(0, realValue);
				sb.append(realValue);
			}
		}

		for (int i = 1; i < valueLines.size(); i++) {
			String value = valueLines.get(i);

			if (_typed) {
				valueLines.set(i, value);
			}
			else {
				valueLines.set(i, _escapeJava(value));
			}

			while ((value.length() > 0) &&
				   Character.isWhitespace(value.charAt(0))) {

				value = value.substring(1);
			}

			sb.append(value);
		}

		String[] property = PropertiesReader._parseProperty(sb.toString());

		_layoutMap.put(key, new Layout(commentLines, valueLines));

		return _storage.put(key, property[1]);
	}

	public String put(String key, List<String> commentLines, String value) {
		commentLines = new ArrayList<>(commentLines);

		_layoutMap.put(key, new Layout(commentLines, null));

		return _storage.put(key, value);
	}

	@Override
	public String put(String key, String value) {
		String old = _storage.put(key, value);

		if ((old == null) || !old.equals(value)) {
			Layout layout = _layoutMap.get(key);

			if (layout != null) {
				layout.clearValue();
			}
		}

		return old;
	}

	public String put(String key, String comment, String value) {
		return put(key, Collections.singletonList(comment), value);
	}

	public void putAllSubstituted(Map<? extends String, ? extends String> map) {
		_storage.putAll(map);
	}

	@Override
	public String remove(Object key) {
		Layout layout = _layoutMap.get(key);

		if (layout != null) {
			layout.clearValue();
		}

		return _storage.remove(key);
	}

	public void save() throws IOException {
		save(_location);
	}

	public void save(File location) throws IOException {
		try (OutputStream outputStream = new FileOutputStream(location)) {
			save(outputStream);
		}
	}

	public void save(OutputStream outputStream) throws IOException {
		save(new OutputStreamWriter(outputStream, DEFAULT_ENCODING));
	}

	public void save(Writer writer) throws IOException {
		saveLayout(writer, _typed);
	}

	/**
	 * Set the comment footer.
	 *
	 * @param footer the footer to use
	 */
	public void setFooter(List<String> footer) {
		_footer = footer;
	}

	/**
	 * Set the comment header.
	 *
	 * @param header the header to use
	 */
	public void setHeader(List<String> header) {
		_header = header;
	}

	/**
	 * Calls the map method put. Provided for parallelism with the getProperty method.
	 * Enforces use of strings for property keys and values. The value returned is the result of the map call to put.
	 *
	 * @param key the key to be placed into this property list.
	 * @param value the value corresponding to the key.
	 * @return the previous value of the specified key in this property list, or null if it did not have one.
	 */
	public Object setProperty(String key, String value) {
		return put(key, value);
	}

	public void setTyped(boolean typed) {
		_typed = typed;
	}

	/**
	 * Store a properties into a output stream, preserving comments, special character, etc.
	 * This method is mainly to be compatible with the java.util.Properties class.
	 *
	 * @param outputStream an output stream.
	 * @param comment this parameter is ignored as this Properties
	 * @throws IOException If storing fails
	 */
	public void store(OutputStream outputStream, String comment)
		throws IOException {

		save(outputStream);
	}

	public void substitute() {
		substitute(_callback);
	}

	public void substitute(InterpolationHelper.SubstitutionCallback callback) {
		if (callback == null) {
			callback =
				new InterpolationHelper.BundleContextSubstitutionCallback(null);
		}

		InterpolationHelper.performSubstitution(_storage, callback);
	}

	public boolean update(Map<String, String> props) {
		Properties properties = new Properties();

		if (props instanceof Properties) {
			properties = (Properties)props;
		}
		else {
			for (Map.Entry<? extends String, ? extends String> e :
					props.entrySet()) {

				properties.put(e.getKey(), e.getValue());
			}
		}

		return update(properties);
	}

	public boolean update(Properties properties) {
		boolean modified = false;

		// Remove "removed" properties from the cfg file

		for (String key : new ArrayList<>(keySet())) {
			if (!properties.containsKey(key)) {
				remove(key);

				modified = true;
			}
		}

		// Update existing keys

		for (String key : properties.keySet()) {
			String value = get(key);

			List<String> comments = properties.getComments(key);

			List<String> rawValue = properties.getRaw(key);

			if (value == null) {
				put(key, comments, rawValue);

				modified = true;
			}
			else if (!value.equals(properties.get(key))) {
				if (comments.isEmpty()) {
					comments = getComments(key);
				}

				put(key, comments, rawValue);

				modified = true;
			}
		}

		return modified;
	}

	/**
	 * This class is used to read properties lines. These lines do
	 * not terminate with new-line chars but rather when there is no
	 * backslash sign a the end of the line.  This is used to
	 * concatenate multiple lines for readability.
	 */
	public static class PropertiesReader extends LineNumberReader {

		/**
		 * Creates a new instance of <code>PropertiesReader</code> and sets
		 * the underlaying reader and the list delimiter.
		 *
		 * @param reader the reader
		 */
		public PropertiesReader(Reader reader, boolean maybeTyped) {
			super(reader);

			_maybeTyped = maybeTyped;
		}

		/**
		 * Returns the comment lines that have been read for the last property.
		 *
		 * @return the comment lines for the last property returned by
		 * <code>readProperty()</code>
		 */
		public List<String> getCommentLines() {
			return _commentLines;
		}

		/**
		 * Returns the name of the last read property. This method can be called
		 * after <code>{@link #nextProperty()}</code> was invoked and its
		 * return value was <b>true</b>.
		 *
		 * @return the name of the last read property
		 */
		public String getPropertyName() {
			return _propertyName;
		}

		/**
		 * Returns the value of the last read property. This method can be
		 * called after <code>{@link #nextProperty()}</code> was invoked and
		 * its return value was <b>true</b>.
		 *
		 * @return the value of the last read property
		 */
		public String getPropertyValue() {
			return _propertyValue;
		}

		/**
		 * Returns the value lines that have been read for the last property.
		 *
		 * @return the raw value lines for the last property returned by
		 * <code>readProperty()</code>
		 */
		public List<String> getValueLines() {
			return _valueLines;
		}

		public Boolean isTyped() {
			return _typed;
		}

		/**
		 * Parses the next property from the input stream and stores the found
		 * name and value in internal fields. These fields can be obtained using
		 * the provided getter methods. The return value indicates whether EOF
		 * was reached (<b>false</b>) or whether further properties are
		 * available (<b>true</b>).
		 *
		 * @return a flag if further properties are available
		 * @throws IOException if an error occurs
		 */
		public boolean nextProperty() throws IOException {
			String line = readProperty();

			if (line == null) {
				return false; // EOF
			}

			// parse the line

			String[] property = _parseProperty(line);

			boolean typed = false;

			if (_maybeTyped && (property[1].length() >= 2)) {
				Matcher matcher = _pattern.matcher(property[1]);

				typed = matcher.matches();
			}

			if (_typed == null) {
				_typed = typed;
			} else {
				_typed = _typed & typed;
			}

			_propertyName = _unescapeJava(property[0]);

			_propertyValue = property[1];

			return true;
		}

		/**
		 * Reads a property line. Returns null if Stream is
		 * at EOF. Concatenates lines ending with "\".
		 * Skips lines beginning with "#" or "!" and empty lines.
		 * The return value is a property definition (<code>&lt;name&gt;</code>
		 * = <code>&lt;value&gt;</code>)
		 *
		 * @return A string containing a property value or null
		 *
		 * @throws IOException in case of an I/O error
		 */
		public String readProperty() throws IOException {
			_commentLines.clear();
			_valueLines.clear();

			StringBuffer buffer = new StringBuffer();

			while (true) {
				String line = readLine();

				if (line == null) {

					// EOF

					return null;
				}

				if (_isCommentLine(line)) {
					_commentLines.add(line);

					continue;
				}

				boolean combine = _checkCombineLines(line);

				if (combine) {
					line = line.substring(0, line.length() - 1);
				}

				_valueLines.add(line);

				while ((line.length() > 0) &&
					   contains(_WHITE_SPACE, line.charAt(0))) {

					line = line.substring(1);
				}

				buffer.append(line);

				if (!combine) {
					break;
				}
			}

			return buffer.toString();
		}

		/**
		 * Checks if the passed in line should be combined with the following.
		 * This is true, if the line ends with an odd number of backslashes.
		 *
		 * @param line the line
		 * @return a flag if the lines should be combined
		 */
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

		/**
		 * Parse a property line and return the key and the value in an array.
		 *
		 * @param line the line to parse
		 * @return an array with the property's key and value
		 */
		private static String[] _parseProperty(String line) {

			// sorry for this spaghetti code, please replace it as soon as
			// possible with a regexp when the Java 1.3 requirement is dropped

			String[] result = new String[2];

			StringBuffer key = new StringBuffer();
			StringBuffer value = new StringBuffer();

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
					else if (contains(_WHITE_SPACE, c)) {

						// switch to the separator crossing state

						state = 2;
					}
					else if (contains(_SEPARATORS, c)) {

						// switch to the value parsing state

						state = 3;
					}
					else {
						key.append(c);
					}
				}
				else if (state == 1) {
					if (contains(_SEPARATORS, c) || contains(_WHITE_SPACE, c)) {

						// this is an escaped separator or white space

						key.append(c);
					}
					else {

						// another escaped character, the '\' is preserved

						key.append('\\');
						key.append(c);
					}

					// return to the key parsing state

					state = 0;
				}
				else if (state == 2) {
					if (contains(_WHITE_SPACE, c)) {

						// do nothing, eat all white spaces

						state = 2;
					}
					else if (contains(_SEPARATORS, c)) {

						// switch to the value parsing state

						state = 3;
					}
					else {

						// any other character indicates we encoutered the
						// beginning of the value

						value.append(c);

						// switch to the value parsing state

						state = 4;
					}
				}
				else if (state == 3) {
					if (contains(_WHITE_SPACE, c)) {

						// do nothing, eat all white spaces

						state = 3;
					}
					else {

						// any other character indicates we encoutered the
						// beginning of the value

						value.append(c);

						// switch to the value parsing state

						state = 4;
					}
				}
				else if (state == 4) {
					value.append(c);
				}
			}

			result[0] = key.toString();
			result[1] = value.toString();

			return result;
		}

		/** Stores the comment lines for the currently processed property.*/
		private final List<String> _commentLines = new ArrayList<>();

		private final boolean _maybeTyped;
		private Pattern _pattern = Pattern.compile(
			"\\s*[TILFDXSCBilfdxscb]?(\\[[\\S\\s]*\\]|\\{[\\S\\s]*\\}|" +
				"\"[\\S\\s]*\")\\s*");

		/** Stores the name of the last read property.*/
		private String _propertyName;

		/** Stores the value of the last read property.*/
		private String _propertyValue;

		/** Stores if the properties are typed or not */
		private Boolean _typed;

		/** Stores the value lines for the currently processed property.*/
		private final List<String> _valueLines = new ArrayList<>();

	}

	/**
	 * This class is used to write properties lines.
	 */
	public static class PropertiesWriter extends FilterWriter {

		/**
		 * Constructor.
		 *
		 * @param writer a Writer object providing the underlying stream
		 */
		public PropertiesWriter(Writer writer, boolean typed) {
			super(writer);

			_typed = typed;
		}

		/**
		 * Helper method for writing a line with the platform specific line
		 * ending.
		 *
		 * @param string the content of the line (may be <b>null</b>)
		 * @throws IOException if an error occurs
		 */
		public void writeln(String string) throws IOException {
			if (string != null) {
				write(string);
			}

			write(_LINE_SEPARATOR);
		}

		/**
		 * Writes the given property and its value.
		 *
		 * @param key the property key
		 * @param value the property value
		 * @throws IOException if an error occurs
		 */
		public void writeProperty(String key, String value) throws IOException {
			write(_escapeKey(key));
			write(" = ");
			write(_typed ? value : _escapeJava(value));
			writeln(null);
		}

		private boolean _typed;

	}

	/**
	 * Writes the properties file to the given writer, preserving as much of its
	 * structure as possible.
	 *
	 * @param writer the writer
	 * @throws IOException if an error occurs
	 */
	protected void saveLayout(Writer writer, boolean typed) throws IOException {
		try (PropertiesWriter propertiesWriter = new PropertiesWriter(
				writer, typed)) {

			if (_header != null) {
				for (String s : _header) {
					propertiesWriter.writeln(s);
				}
			}

			for (Entry<String, String> entry : _storage.entrySet()) {
				String key = entry.getKey();

				String value = entry.getValue();

				Layout layout = _layoutMap.get(key);

				if (layout == null) {
					propertiesWriter.writeProperty(key, value);

					continue;
				}

				List<String> commentLines = layout.getCommentLines();

				if (commentLines != null) {
					for (String string : commentLines) {
						propertiesWriter.writeln(string);
					}
				}

				List<String> valueLines = layout.getValueLines();

				if (valueLines == null) {
					propertiesWriter.writeProperty(key, value);

					continue;
				}

				int size = valueLines.size();

				for (int i = 0; i < size; i++) {
					String string = valueLines.get(i);

					if (i < (size - 1)) {
						propertiesWriter.writeln(string + "\\");
					}
					else {
						propertiesWriter.writeln(string);
					}
				}
			}

			if (_footer != null) {
				for (String string : _footer) {
					propertiesWriter.writeln(string);
				}
			}
		}
	}

	/**
	 * <p>Escapes the characters in a <code>String</code> using Java String rules.</p>
	 *
	 * <p>Deals correctly with quotes and control-chars (tab, backslash, cr, ff, etc.) </p>
	 *
	 * <p>So a tab becomes the characters <code>'\\'</code> and
	 * <code>'t'</code>.</p>
	 *
	 * <p>The only difference between Java strings and JavaScript strings
	 * is that in JavaScript, a single quote must be escaped.</p>
	 *
	 * <p>Example:</p>
	 * <pre>
	 * input string: He didn't say, "Stop!"
	 * output string: He didn't say, \"Stop!\"
	 * </pre>
	 *
	 *
	 * @param string  String to escape values in, may be null
	 * @return String with escaped values, <code>null</code> if null string input
	 */
	private static String _escapeJava(String string) {
		if (string == null) {
			return null;
		}

		int length = string.length();

		StringBuffer stringBuffer = new StringBuffer(length * 2);

		for (int i = 0; i < length; i++) {
			char c = string.charAt(i);

			// handle unicode

			if (c > 0xfff) {
				stringBuffer.append("\\u");
				stringBuffer.append(_hex(c));
			}
			else if (c > 0xff) {
				stringBuffer.append("\\u0");
				stringBuffer.append(_hex(c));
			}
			else if (c > 0x7f) {
				stringBuffer.append("\\u00");
				stringBuffer.append(_hex(c));
			}
			else if (c < 32) {
				if (c == 'b') {
					stringBuffer.append('\\');
					stringBuffer.append('b');
				}
				else if (c == 'n') {
					stringBuffer.append('\\');
					stringBuffer.append('n');
				}
				else if (c == 't') {
					stringBuffer.append('\\');
					stringBuffer.append('t');
				}
				else if (c == 'f') {
					stringBuffer.append('\\');
					stringBuffer.append('f');
				}
				else if (c == 'r') {
					stringBuffer.append('\\');
					stringBuffer.append('r');
				}
				else {
					if (c > 0xf) {
						stringBuffer.append("\\u00");
						stringBuffer.append(_hex(c));
					}
					else {
						stringBuffer.append("\\u000");
						stringBuffer.append(_hex(c));
					}
				}
			}
			else {
				if (c == '"') {
					stringBuffer.append('\\');
					stringBuffer.append('"');
				}
				else if (c == '\\') {
					stringBuffer.append('\\');
					stringBuffer.append('\\');
				}
				else {
					stringBuffer.append(c);
				}
			}
		}

		return stringBuffer.toString();
	}

	/**
	 * Escape the separators in the key.
	 *
	 * @param key the key
	 * @return the escaped key
	 */
	private static String _escapeKey(String key) {
		StringBuffer stringBuffer = new StringBuffer();

		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);

			if (contains(_SEPARATORS, c) || contains(_WHITE_SPACE, c)) {

				// escape the separator

				stringBuffer.append('\\');
				stringBuffer.append(c);
			}
			else {
				stringBuffer.append(c);
			}
		}

		return stringBuffer.toString();
	}

	/**
	 * <p>Returns an upper case hexadecimal <code>String</code> for the given
	 * character.</p>
	 *
	 * @param ch The character to convert.
	 * @return An upper case hexadecimal <code>String</code>
	 */
	private static String _hex(char ch) {
		String hexString = Integer.toHexString(ch);

		return hexString.toUpperCase(Locale.ENGLISH);
	}

	/**
	 * Tests whether a line is a comment, i.e. whether it starts with a comment
	 * character.
	 *
	 * @param line the line
	 * @return a flag if this is a comment line
	 */
	private static boolean _isCommentLine(String line) {
		String string = line.trim();

		// blank lines are also treated as comment lines

		if ((string.length() < 1) || (string.indexOf(_COMMENT_CHARS) == 0)) {
			return true;
		}

		return false;
	}

	/**
	 * <p>Unescapes any Java literals found in the <code>String</code> to a
	 * <code>Writer</code>.</p> This is a slightly modified version of the
	 * StringEscapeUtils.unescapeJava() function in commons-lang that doesn't
	 * drop escaped separators (i.e '\,').
	 *
	 * @param string  the <code>String</code> to unescape, may be null
	 * @return the processed string
	 * @throws IllegalArgumentException if the Writer is <code>null</code>
	 */
	private static String _unescapeJava(String string) {
		if (string == null) {
			return null;
		}

		int size = string.length();

		StringBuffer stringBuffer = new StringBuffer(size);

		StringBuffer unicode = new StringBuffer(_UNICODE_LEN);

		boolean hadSlash = false;

		boolean inUnicode = false;

		for (int i = 0; i < size; i++) {
			char c = string.charAt(i);

			if (inUnicode) {

				// if in unicode, then we're reading unicode
				// values in somehow

				unicode.append(c);

				if (unicode.length() == _UNICODE_LEN) {

					// unicode now contains the four hex digits
					// which represents our unicode character

					try {
						int value = Integer.parseInt(
							unicode.toString(), _HEX_RADIX);

						stringBuffer.append((char)value);

						unicode.setLength(0);

						inUnicode = false;

						hadSlash = false;
					}
					catch (NumberFormatException numberFormatException) {
						throw new IllegalArgumentException(
							"Unable to parse unicode value: " + unicode,
							numberFormatException);
					}
				}

				continue;
			}

			if (hadSlash) {

				// handle an escaped value

				hadSlash = false;

				if (c == '\\') {
					stringBuffer.append("\\");
				}
				else if (c == '\'') {
					stringBuffer.append('\'');
				}
				else if (c == '"') {
					stringBuffer.append('"');
				}
				else if (c == 'r') {
					stringBuffer.append('\r');
				}
				else if (c == 'f') {
					stringBuffer.append('\f');
				}
				else if (c == 't') {
					stringBuffer.append('\t');
				}
				else if (c == 'n') {
					stringBuffer.append('\n');
				}
				else if (c == 'b') {
					stringBuffer.append('\b');
				}
				else if (c == 'u') {
					inUnicode = true;
				}
				else {
					stringBuffer.append(c);
				}

				continue;
			}
			else if (c == '\\') {
				hadSlash = true;

				continue;
			}

			stringBuffer.append(c);
		}

		if (hadSlash) {

			// then we're in the weird case of a \ at the end of the
			// string, let's output it anyway.

			stringBuffer.append('\\');
		}

		return stringBuffer.toString();
	}

	/**
	 * Checks if parts of the passed in comment can be used as header comment.
	 * This method checks whether a header comment can be defined (i.e. whether
	 * this is the first comment in the loaded file). If this is the case, it is
	 * searched for the lates blank line. This line will mark the end of the
	 * header comment. The return value is the index of the first line in the
	 * passed in list, which does not belong to the header comment.
	 *
	 * @param commentLines the comment lines
	 * @return the index of the next line after the header comment
	 */
	private int _checkHeaderComment(List<String> commentLines) {
		if ((getHeader() == null) && _layoutMap.isEmpty()) {

			// This is the first comment. Search for blank lines.

			int index = commentLines.size() - 1;

			while (index >= 0) {
				String commentLine = commentLines.get(index);

				if (commentLine.length() <= 0) {
					break;
				}

				index--;
			}

			setHeader(new ArrayList<>(commentLines.subList(0, index + 1)));

			return index + 1;
		}

		return 0;
	}

	/** Constant for the supported comment characters.*/
	private static final String _COMMENT_CHARS = "#!";

	/** Constant for the radix of hex numbers.*/
	private static final int _HEX_RADIX = 16;

	/** Constant for the platform specific line separator.*/
	private static final String _LINE_SEPARATOR = System.getProperty(
		"line.separator");

	/** The list of possible key/value separators */
	private static final char[] _SEPARATORS = {'=', ':'};

	/** Constant for the length of a unicode literal.*/
	private static final int _UNICODE_LEN = 4;

	/** The white space characters used as key/value separators. */
	private static final char[] _WHITE_SPACE = {' ', '\t', '\f'};

	private InterpolationHelper.SubstitutionCallback _callback;
	private List<String> _footer;
	private List<String> _header;
	private final Map<String, Layout> _layoutMap = new LinkedHashMap<>();
	private File _location;
	private final Map<String, String> _storage = new LinkedHashMap<>();
	private boolean _substitute = true;
	private boolean _typed;

	private static class Layout {

		public Layout() {
		}

		public Layout(List<String> commentLines, List<String> valueLines) {
			_commentLines = commentLines;
			_valueLines = valueLines;
		}

		public void clearValue() {
			_valueLines = null;
		}

		public List<String> getCommentLines() {
			return _commentLines;
		}

		public List<String> getValueLines() {
			return _valueLines;
		}

		public void setCommentLines(List<String> commentLines) {
			_commentLines = commentLines;
		}

		public void setValueLines(List<String> valueLines) {
			_valueLines = valueLines;
		}

		private List<String> _commentLines;
		private List<String> _valueLines;

	}

	private class KeyIterator implements Iterator {

		public KeyIterator() {
			Set<Entry<String, String>> entries = _storage.entrySet();

			_iterator = entries.iterator();
		}

		@Override
		public boolean hasNext() {
			return _iterator.hasNext();
		}

		@Override
		public Entry<String, String> next() {
			final Entry<String, String> entry = _iterator.next();

			return new Entry<String, String>() {

				@Override
				public String getKey() {
					return entry.getKey();
				}

				@Override
				public String getValue() {
					return entry.getValue();
				}

				@Override
				public String setValue(String value) {
					String old = entry.setValue(value);

					if ((old == null) || !old.equals(value)) {
						Layout layout = _layoutMap.get(entry.getKey());

						if (layout != null) {
							layout.clearValue();
						}
					}

					return old;
				}

			};
		}

		@Override
		public void remove() {
			_iterator.remove();
		}

		private final Iterator<Entry<String, String>> _iterator;

	}

}
/* @generated */