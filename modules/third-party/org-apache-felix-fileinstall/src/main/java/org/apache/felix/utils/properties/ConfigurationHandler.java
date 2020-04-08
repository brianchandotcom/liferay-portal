/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.felix.utils.properties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PushbackReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The <code>ConfigurationHandler</code> class implements configuration reading
 * form a <code>InputStream</code> and writing to a
 * <code>OutputStream</code> on behalf of the
 * {@link FilePersistenceManager} class.
 *
 * <pre>
 * cfg = prop &quot;=&quot; value .
 *  prop = symbolic-name . // 1.4.2 of OSGi Core Specification
 *  symbolic-name = token { &quot;.&quot; token } .
 *  token = { [ 0..9 ] | [ a..z ] | [ A..Z ] | '_' | '-' } .
 *  value = [ type ] (&quot;[&quot; values &quot;]&quot; | &quot;(&quot; values &quot;)&quot; | simple) .
 *  values = simple { &quot;,&quot; simple } .
 *  simple = &quot;&quot;&quot; stringsimple &quot;&quot;&quot; .
 *  type = // 1-char type code .
 *  stringsimple = // quoted string representation of the value .
 * </pre>
 */
public class ConfigurationHandler {

	/**
	 * Reads configuration data from the given <code>InputStream</code> and
	 * returns a new <code>Dictionary</code> object containing the data.
	 * <p>
	 * This method reads from the current location in the stream upto the end of
	 * the stream but does not close the stream at the end.
	 *
	 * @param inputStream
	 *			The <code>InputStream</code> from which to read the
	 *			configuration data.
	 * @return A <code>Dictionary</code> object containing the configuration
	 *		 data. This object may be empty if the stream contains no
	 *		 configuration data.
	 * @throws IOException
	 *			 If an error occurrs reading from the stream. This exception
	 *			 is also thrown if a syntax error is encountered.
	 */
	public static Dictionary read(InputStream inputStream) throws IOException {
		return new ConfigurationHandler()._readInternal(inputStream);
	}

	public static Object read(String value) throws IOException {
		try (StringReader stringReader = new StringReader(value);
			PushbackReader pushbackReader = new PushbackReader(
				stringReader, 1)) {

			ConfigurationHandler configurationHandler =
				new ConfigurationHandler();

			return configurationHandler._readValue(pushbackReader);
		}
	}

	public static String write(Object value) throws IOException {
		StringWriter stringWriter = new StringWriter();

		_writeValue(stringWriter, value);

		return stringWriter.toString();
	}

	/**
	 * Writes the configuration data from the <code>Dictionary</code> to the
	 * given <code>OutputStream</code>.
	 * <p>
	 * This method writes at the current location in the stream and does not
	 * close the outputstream.
	 *
	 * @param outputStream
	 *			The <code>OutputStream</code> to write the configurtion data
	 *			to.
	 * @param properties
	 *			The <code>Dictionary</code> to write.
	 * @throws IOException
	 *			 If an error occurrs writing to the output stream.
	 */
	public static void write(OutputStream outputStream, Dictionary properties)
		throws IOException {

		try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				outputStream, _ENCODING);
			BufferedWriter bufferedWriter = new BufferedWriter(
				outputStreamWriter)) {

			Enumeration<String> enumeration = _orderedKeys(properties);

			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement();

				// cfg = prop "=" value "." .

				_writeQuoted(bufferedWriter, key);

				bufferedWriter.write(_TOKEN_EQ);

				_writeValue(bufferedWriter, properties.get(key));

				bufferedWriter.write(_CRLF);
			}

			bufferedWriter.flush();
		}
	}

	/**
	 * Generates an <code>Enumeration</code> for the given
	 * <code>Dictionary</code> where the keys of the <code>Dictionary</code>
	 * are provided in sorted order.
	 *
	 * @param properties
	 *				   The <code>Dictionary</code> that keys are sorted.
	 * @return An <code>Enumeration</code> that provides the keys of
	 *		 properties in an ordered manner.
	 */
	private static Enumeration<String> _orderedKeys(Dictionary properties) {
		String[] keyArray = new String[properties.size()];
		int i = 0;

		for (Enumeration ce = properties.keys(); ce.hasMoreElements();) {
			keyArray[i] = (String)ce.nextElement();
			i++;
		}

		Arrays.sort(keyArray);

		return Collections.enumeration(Arrays.asList(keyArray));
	}

	private static void _writeArray(Writer writer, Object arrayValue)
		throws IOException {

		int size = Array.getLength(arrayValue);

		Class<?> clazz = arrayValue.getClass();

		_writeType(writer, clazz.getComponentType());

		writer.write(_TOKEN_ARR_OPEN);

		writer.write(_COLLECTION_LINE_BREAK);

		for (int i = 0; i < size; i++) {
			_writeCollectionElement(writer, Array.get(arrayValue, i));
		}

		writer.write(_INDENT);
		writer.write(_TOKEN_ARR_CLOS);
	}

	private static void _writeCollection(Writer writer, Collection collection)
		throws IOException {

		if (collection.isEmpty()) {
			writer.write(_TOKEN_VEC_OPEN);
			writer.write(_COLLECTION_LINE_BREAK);
			writer.write(_TOKEN_VEC_CLOS);
		}
		else {
			Iterator iterator = collection.iterator();

			Object firstElement = iterator.next();

			_writeType(writer, firstElement.getClass());

			writer.write(_TOKEN_VEC_OPEN);
			writer.write(_COLLECTION_LINE_BREAK);

			_writeCollectionElement(writer, firstElement);

			while (iterator.hasNext()) {
				_writeCollectionElement(writer, iterator.next());
			}

			writer.write(_TOKEN_VEC_CLOS);
		}
	}

	private static void _writeCollectionElement(Writer writer, Object element)
		throws IOException {

		writer.write(_INDENT);

		_writeSimple(writer, element);

		writer.write(_TOKEN_COMMA);
		writer.write(_COLLECTION_LINE_BREAK);
	}

	private static void _writeQuoted(Writer writer, String simple)
		throws IOException {

		if ((simple == null) || (simple.length() == 0)) {
			return;
		}

		char c = 0;

		int length = simple.length();

		for (int i = 0; i < length; i++) {
			c = simple.charAt(i);

			if ((c == '\\') || (c == _TOKEN_VAL_CLOS)) {
				writer.write('\\');
				writer.write(c);
			}
			else if (c == '\b') {
				writer.write("\\b");
			}
			else if (c == '\t') {
				writer.write("\\t");
			}
			else if (c == '\n') {
				writer.write("\\n");
			}
			else if (c == '\f') {
				writer.write("\\f");
			}
			else if (c == '\r') {
				writer.write("\\r");
			}
			else if (c < ' ') {
				String hexString = "000" + Integer.toHexString(c);

				writer.write(
					"\\u" + hexString.substring(hexString.length() - 4));
			}
			else {
				writer.write(c);
			}
		}
	}

	private static void _writeSimple(Writer writer, Object value)
		throws IOException {

		writer.write(_TOKEN_VAL_OPEN);

		_writeQuoted(writer, String.valueOf(value));

		writer.write(_TOKEN_VAL_CLOS);
	}

	private static void _writeType(Writer writer, Class valueType)
		throws IOException {

		Integer code = (Integer)_typeToCode.get(valueType);

		if (code != null) {
			writer.write((char)code.intValue());
		}
	}

	private static void _writeValue(Writer writer, Object value)
		throws IOException {

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			_writeArray(writer, value);
		}
		else if (value instanceof Collection) {
			_writeCollection(writer, (Collection)value);
		}
		else {
			_writeType(writer, clazz);
			_writeSimple(writer, value);
		}
	}

	// simple types (string & primitive wrappers)

	private ConfigurationHandler() {
	}

	private void _ensureNext(PushbackReader pushbackReader, int expected)
		throws IOException {

		int next = _read(pushbackReader);

		if (next != expected) {
			_readFailure(next, expected);
		}
	}

	private int _ignorablePageBreakAndWhiteSpace(PushbackReader pushbackReader)
		throws IOException {

		int c1 = _ignorableWhiteSpace(pushbackReader);

		while (true) {
			if (c1 != '\\') {
				break;
			}

			int c2 = pushbackReader.read();

			if ((c2 == '\r') || (c2 == '\n')) {
				c1 = _ignorableWhiteSpace(pushbackReader);
			}
			else {
				pushbackReader.unread(c2);

				break;
			}
		}

		return c1;
	}

	private int _ignorableWhiteSpace(PushbackReader pushbackReader)
		throws IOException {

		int c = _read(pushbackReader);

		while ((c >= 0) && Character.isWhitespace((char)c)) {
			c = _read(pushbackReader);
		}

		return c;
	}

	private int _nextToken(PushbackReader pushbackReader, boolean newLine)
		throws IOException {

		int c = _ignorableWhiteSpace(pushbackReader);

		// immediately return EOF

		if (c < 0) {
			return _token = c;
		}

		// check for comment

		if (newLine && (c == _TOKEN_COMMENT)) {

			// skip everything until end of line

			do {
				c = _read(pushbackReader);
			}
			while ((c != -1) && (c != '\n'));

			if (c == -1) {
				return _token = c;
			}

			// and start over

			return _nextToken(pushbackReader, true);
		}

		// check whether there is a name

		if (_NAME_CHARS.get(c) || !_TOKEN_CHARS.get(c)) {

			// read the property name

			pushbackReader.unread(c);

			_tokenValue = _readUnquoted(pushbackReader);

			_token = _TOKEN_NAME;

			return _TOKEN_NAME;
		}

		// check another token

		if (_TOKEN_CHARS.get(c)) {
			_token = c;

			return c;
		}

		// unexpected character -> so what ??

		_token = -1;

		return _token;
	}

	private int _read(PushbackReader pushbackReader) throws IOException {
		int c = pushbackReader.read();

		if (c == '\r') {
			int c1 = pushbackReader.read();

			if (c1 != '\n') {
				pushbackReader.unread(c1);
			}

			c = '\n';
		}

		if (c == '\n') {
			_line++;
			_pos = 0;
		}
		else {
			_pos++;
		}

		return c;
	}

	private int _read(PushbackReader pushbackReader, char[] buffer)
		throws IOException {

		for (int i = 0; i < buffer.length; i++) {
			int c = _read(pushbackReader);

			if (c >= 0) {
				buffer[i] = (char)c;
			}
			else {
				return i;
			}
		}

		return buffer.length;
	}

	private Object _readArray(int typeCode, PushbackReader pushbackReader)
		throws IOException {

		List<Object> list = new ArrayList<>();

		while (true) {
			int spaces = _ignorablePageBreakAndWhiteSpace(pushbackReader);

			if (spaces == _TOKEN_VAL_OPEN) {
				Object value = _readSimple(typeCode, pushbackReader);

				if (value == null) {

					// abort due to error

					return null;
				}

				_ensureNext(pushbackReader, _TOKEN_VAL_CLOS);

				list.add(value);

				spaces = _ignorablePageBreakAndWhiteSpace(pushbackReader);
			}

			if (spaces == _TOKEN_ARR_CLOS) {
				Class<?> type = (Class)_codeToType.get(typeCode);

				Object array = Array.newInstance(type, list.size());

				for (int i = 0; i < list.size(); i++) {
					Array.set(array, i, list.get(i));
				}

				return array;
			}
			else if (spaces < 0) {
				return null;
			}
			else if (spaces != _TOKEN_COMMA) {
				return null;
			}
		}
	}

	private Collection<Object> _readCollection(
			int typeCode, PushbackReader pushbackReader)
		throws IOException {

		Collection<Object> collection = new ArrayList<>();

		while (true) {
			int spaces = _ignorablePageBreakAndWhiteSpace(pushbackReader);

			if (spaces == _TOKEN_VAL_OPEN) {
				Object value = _readSimple(typeCode, pushbackReader);

				if (value == null) {

					// abort due to error

					return null;
				}

				_ensureNext(pushbackReader, _TOKEN_VAL_CLOS);

				collection.add(value);

				spaces = _ignorablePageBreakAndWhiteSpace(pushbackReader);
			}

			if (spaces == _TOKEN_VEC_CLOS) {
				return collection;
			}
			else if (spaces < 0) {
				return null;
			}
			else if (spaces != _TOKEN_COMMA) {
				return null;
			}
		}
	}

	// primitives

	private IOException _readFailure(int current, int expected) {
		StringBuilder sb = new StringBuilder();

		sb.append("Unexpected token ");
		sb.append(current);
		sb.append("; expected: ");
		sb.append(expected);
		sb.append(" (line=");
		sb.append(_line);
		sb.append(", pos=");
		sb.append(_pos);
		sb.append(")");

		return new IOException(sb.toString());
	}

	private Dictionary<String, Object> _readInternal(InputStream inputStream)
		throws IOException {

		try (InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, _ENCODING);
			BufferedReader bufferedReader = new BufferedReader(
				inputStreamReader);
			PushbackReader pushbackReader = new PushbackReader(
				bufferedReader, 1)) {

			_token = 0;
			_tokenValue = null;
			_line = 0;
			_pos = 0;

			Dictionary<String, Object> configuration = new Hashtable<>();

			_token = 0;

			while (_nextToken(pushbackReader, true) == _TOKEN_NAME) {
				String key = _tokenValue;

				// expect equal sign

				if (_nextToken(pushbackReader, false) != _TOKEN_EQ) {
					throw _readFailure(_token, _TOKEN_EQ);
				}

				// expect the token value

				Object value = _readValue(pushbackReader);

				if (value != null) {
					configuration.put(key, value);
				}
			}

			return configuration;
		}
	}

	private String _readQuoted(PushbackReader pushbackReader)
		throws IOException {

		StringBuffer buffer = new StringBuffer();

		while (true) {
			int c = _read(pushbackReader);

			if (c == '\\') {
				c = _read(pushbackReader);

				if (c == 'b') {
					buffer.append('\b');
				}
				else if (c == 't') {
					buffer.append('\t');
				}
				else if (c == 'n') {
					buffer.append('\n');
				}
				else if (c == 'f') {
					buffer.append('\f');
				}
				else if (c == 'r') {
					buffer.append('\r');
				}
				else if (c == 'u') {
					char[] charBuffer = new char[4];

					if (_read(pushbackReader, charBuffer) == 4) {
						c = Integer.parseInt(new String(charBuffer), 16);

						buffer.append((char)c);
					}
				}
				else {
					buffer.append((char)c);
				}
			}
			else if ((c == -1) || (c == _TOKEN_VAL_CLOS)) {
				pushbackReader.unread(c);

				return buffer.toString();
			}
			else {
				buffer.append((char)c);
			}
		}
	}

	private Object _readSimple(int code, PushbackReader pushbackReader)
		throws IOException {

		if (code == -1) {
			return null;
		}
		else if (code == _TOKEN_SIMPLE_STRING) {
			return _readQuoted(pushbackReader);
		}
		else if ((code == _TOKEN_SIMPLE_INTEGER) ||
				 (code == _TOKEN_PRIMITIVE_INT)) {

			return Integer.valueOf(_readQuoted(pushbackReader));
		}
		else if ((code == _TOKEN_SIMPLE_LONG) ||
				 (code == _TOKEN_PRIMITIVE_LONG)) {

			return Long.valueOf(_readQuoted(pushbackReader));
		}
		else if ((code == _TOKEN_SIMPLE_FLOAT) ||
				 (code == _TOKEN_PRIMITIVE_FLOAT)) {

			String floatString = _readQuoted(pushbackReader);

			if (floatString.indexOf('.') >= 0) {
				return Float.valueOf(floatString);
			}

			return Float.intBitsToFloat(Integer.parseInt(floatString));
		}
		else if ((code == _TOKEN_SIMPLE_DOUBLE) ||
				 (code == _TOKEN_PRIMITIVE_DOUBLE)) {

			String doubleString = _readQuoted(pushbackReader);

			if (doubleString.indexOf('.') >= 0) {
				return Double.valueOf(doubleString);
			}

			return Double.longBitsToDouble(Long.parseLong(doubleString));
		}
		else if ((code == _TOKEN_SIMPLE_BYTE) ||
				 (code == _TOKEN_PRIMITIVE_BYTE)) {

			return Byte.valueOf(_readQuoted(pushbackReader));
		}
		else if ((code == _TOKEN_SIMPLE_SHORT) ||
				 (code == _TOKEN_PRIMITIVE_SHORT)) {

			return Short.valueOf(_readQuoted(pushbackReader));
		}
		else if ((code == _TOKEN_SIMPLE_CHARACTER) ||
				 (code == _TOKEN_PRIMITIVE_CHAR)) {

			String charString = _readQuoted(pushbackReader);

			if ((charString != null) && (charString.length() > 0)) {
				return Character.valueOf(charString.charAt(0));
			}

			return null;
		}
		else if ((code == _TOKEN_SIMPLE_BOOLEAN) ||
				 (code == _TOKEN_PRIMITIVE_BOOLEAN)) {

			return Boolean.valueOf(_readQuoted(pushbackReader));
		}
		else {
			return null;
		}
	}

	private String _readUnquoted(PushbackReader pushbackReader)
		throws IOException {

		StringBuffer buffer = new StringBuffer();

		while (true) {
			int c = _read(pushbackReader);

			if (c == '\\') {
				c = _read(pushbackReader);

				if (c == 'b') {
					buffer.append('\b');
				}
				else if (c == 't') {
					buffer.append('\t');
				}
				else if (c == 'n') {
					buffer.append('\n');
				}
				else if (c == 'f') {
					buffer.append('\f');
				}
				else if (c == 'r') {
					buffer.append('\r');
				}
				else if (c == 'u') {
					char[] charBuffer = new char[4];

					if (_read(pushbackReader, charBuffer) == 4) {
						c = Integer.parseInt(new String(charBuffer), 16);

						buffer.append((char)c);
					}
				}
				else {
					buffer.append((char)c);
				}
			}
			else if ((c == -1) || (c == _TOKEN_VAL_CLOS) ||
					 (c == _TOKEN_SPACE) || (c == _TOKEN_EQ)) {

				pushbackReader.unread(c);

				return buffer.toString();
			}
			else {
				buffer.append((char)c);
			}
		}
	}

	/**
	 * value = type ("[" values "]" | "(" values ")" | simple) .
	 * values = value { "," value } .
	 * simple = "{" stringsimple "}" .
	 * type = // 1-char type code .
	 * stringsimple = // quoted string representation of the value .
	 *
	 * @param pushbackReader
	 * @return
	 * @throws IOException
	 */
	private Object _readValue(PushbackReader pushbackReader)
		throws IOException {

		// read past any whitespace and (optional) type code

		int type = _ignorableWhiteSpace(pushbackReader);

		// read value kind code if type code is not a value kinde code

		int code = type;

		if (_codeToType.containsKey(type)) {
			code = _read(pushbackReader);
		}
		else {
			type = _TOKEN_SIMPLE_STRING;
		}

		if (code == _TOKEN_ARR_OPEN) {
			return _readArray(type, pushbackReader);
		}
		else if (code == _TOKEN_VEC_OPEN) {
			return _readCollection(type, pushbackReader);
		}
		else if (code == _TOKEN_VAL_OPEN) {
			Object value = _readSimple(type, pushbackReader);

			_ensureNext(pushbackReader, _TOKEN_VAL_CLOS);

			return value;
		}
		else {
			return null;
		}
	}

	private static final String _COLLECTION_LINE_BREAK = " \\\r\n";

	private static final String _CRLF = "\r\n";

	private static final String _ENCODING = "UTF-8";

	private static final String _INDENT = "  ";

	private static final BitSet _NAME_CHARS = new BitSet() {
		{
			for (int i = '0'; i <= '9'; i++) {
				set(i);
			}

			for (int i = 'a'; i <= 'z'; i++) {
				set(i);
			}

			for (int i = 'A'; i <= 'Z'; i++) {
				set(i);
			}

			set('_');
			set('-');
			set('.');
			set('\\');
		}
	};

	private static final int _TOKEN_ARR_CLOS = ']';

	private static final int _TOKEN_ARR_OPEN = '[';

	// set of valid characters for "symblic-name"

	private static final BitSet _TOKEN_CHARS = new BitSet() {
		{
			set(_TOKEN_EQ);
			set(_TOKEN_ARR_OPEN);
			set(_TOKEN_ARR_CLOS);
			set(_TOKEN_VEC_OPEN);
			set(_TOKEN_VEC_CLOS);
			set(_TOKEN_COMMA);
			set(_TOKEN_VAL_OPEN);
			set(_TOKEN_VAL_CLOS);
			set(_TOKEN_SIMPLE_STRING);
			set(_TOKEN_SIMPLE_INTEGER);
			set(_TOKEN_SIMPLE_LONG);
			set(_TOKEN_SIMPLE_FLOAT);
			set(_TOKEN_SIMPLE_DOUBLE);
			set(_TOKEN_SIMPLE_BYTE);
			set(_TOKEN_SIMPLE_SHORT);
			set(_TOKEN_SIMPLE_CHARACTER);
			set(_TOKEN_SIMPLE_BOOLEAN);

			// primitives

			set(_TOKEN_PRIMITIVE_INT);
			set(_TOKEN_PRIMITIVE_LONG);
			set(_TOKEN_PRIMITIVE_FLOAT);
			set(_TOKEN_PRIMITIVE_DOUBLE);
			set(_TOKEN_PRIMITIVE_BYTE);
			set(_TOKEN_PRIMITIVE_SHORT);
			set(_TOKEN_PRIMITIVE_CHAR);
			set(_TOKEN_PRIMITIVE_BOOLEAN);
		}
	};

	private static final int _TOKEN_COMMA = ',';

	private static final int _TOKEN_COMMENT = '#';

	private static final int _TOKEN_EQ = '=';

	private static final int _TOKEN_NAME = 'N';

	private static final int _TOKEN_PRIMITIVE_BOOLEAN = 'b';

	private static final int _TOKEN_PRIMITIVE_BYTE = 'x';

	// private constructor, this class is not to be instantiated from the
	// outside

	private static final int _TOKEN_PRIMITIVE_CHAR = 'c';

	// ---------- Configuration Input Implementation ---------------------------

	private static final int _TOKEN_PRIMITIVE_DOUBLE = 'd';

	private static final int _TOKEN_PRIMITIVE_FLOAT = 'f';

	private static final int _TOKEN_PRIMITIVE_INT = 'i';

	private static final int _TOKEN_PRIMITIVE_LONG = 'l';

	private static final int _TOKEN_PRIMITIVE_SHORT = 's';

	private static final int _TOKEN_SIMPLE_BOOLEAN = 'B';

	private static final int _TOKEN_SIMPLE_BYTE = 'X';

	private static final int _TOKEN_SIMPLE_CHARACTER = 'C';

	private static final int _TOKEN_SIMPLE_DOUBLE = 'D';

	private static final int _TOKEN_SIMPLE_FLOAT = 'F';

	private static final int _TOKEN_SIMPLE_INTEGER = 'I';

	private static final int _TOKEN_SIMPLE_LONG = 'L';

	private static final int _TOKEN_SIMPLE_SHORT = 'S';

	private static final int _TOKEN_SIMPLE_STRING = 'T';

	private static final int _TOKEN_SPACE = ' ';

	private static final int _TOKEN_VAL_CLOS = '"'; // '}';

	private static final int _TOKEN_VAL_OPEN = '"'; // '{';

	private static final int _TOKEN_VEC_CLOS = ')';

	// ---------- Configuration Output Implementation --------------------------

	private static final int _TOKEN_VEC_OPEN = '(';

	private static final Map<Object, Object> _typeToCode =
		new HashMap<Object, Object>() {
			{
				put(Boolean.class, Integer.valueOf(_TOKEN_SIMPLE_BOOLEAN));
				put(Byte.class, Integer.valueOf(_TOKEN_SIMPLE_BYTE));
				put(Character.class, Integer.valueOf(_TOKEN_SIMPLE_CHARACTER));
				put(Double.class, Integer.valueOf(_TOKEN_SIMPLE_DOUBLE));
				put(Float.class, Integer.valueOf(_TOKEN_SIMPLE_FLOAT));
				put(Integer.class, Integer.valueOf(_TOKEN_SIMPLE_INTEGER));
				put(Long.class, Integer.valueOf(_TOKEN_SIMPLE_LONG));
				put(Short.class, Integer.valueOf(_TOKEN_SIMPLE_SHORT));

				// primitives

				put(Boolean.TYPE, Integer.valueOf(_TOKEN_PRIMITIVE_BOOLEAN));
				put(Byte.TYPE, Integer.valueOf(_TOKEN_PRIMITIVE_BYTE));
				put(Character.TYPE, Integer.valueOf(_TOKEN_PRIMITIVE_CHAR));
				put(Double.TYPE, Integer.valueOf(_TOKEN_PRIMITIVE_DOUBLE));
				put(Float.TYPE, Integer.valueOf(_TOKEN_PRIMITIVE_FLOAT));
				put(Integer.TYPE, Integer.valueOf(_TOKEN_PRIMITIVE_INT));
				put(Long.TYPE, Integer.valueOf(_TOKEN_PRIMITIVE_LONG));
				put(Short.TYPE, Integer.valueOf(_TOKEN_PRIMITIVE_SHORT));
			}
		};

	private static final Map<Object, Object> _codeToType =
		new HashMap<Object, Object>() {
			{

				// reverse map to map type codes to classes, string class
				// mapping to be added manually, as the string type code is not
				// written and hence not included in the type2Code map

				for (Map.Entry<Object, Object> entry : _typeToCode.entrySet()) {
					put(entry.getValue(), entry.getKey());
				}

				put(Integer.valueOf(_TOKEN_SIMPLE_STRING), String.class);
			}
		};

	private int _line;
	private int _pos;
	private int _token;
	private String _tokenValue;

}
/* @generated */