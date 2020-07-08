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
import com.liferay.petra.string.StringPool;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Tambara
 */
public class ConfigurationHandler {

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

			if ((c == '\\') || (c == _TOKEN_VAL_CLOS) ||
				(c == CharPool.SPACE) || (c == _TOKEN_EQ) ||
				(c == _TOKEN_BRACE_OPEN) || (c == _TOKEN_BRACE_CLOS)) {

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
			else if (c < CharPool.SPACE) {
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
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return new IOException(sb.toString());
	}

	private String _readQuoted(PushbackReader pushbackReader)
		throws IOException {

		StringBundler sb = new StringBundler();

		while (true) {
			int c = _read(pushbackReader);

			if (c == '\\') {
				c = _read(pushbackReader);

				if (c == 'b') {
					sb.append('\b');
				}
				else if (c == 't') {
					sb.append('\t');
				}
				else if (c == 'n') {
					sb.append('\n');
				}
				else if (c == 'f') {
					sb.append('\f');
				}
				else if (c == 'r') {
					sb.append('\r');
				}
				else if (c == 'u') {
					char[] charBuffer = new char[4];

					if (_read(pushbackReader, charBuffer) == 4) {
						c = Integer.parseInt(new String(charBuffer), 16);

						sb.append((char)c);
					}
				}
				else {
					sb.append((char)c);
				}
			}
			else if ((c == -1) || (c == _TOKEN_VAL_CLOS)) {
				pushbackReader.unread(c);

				return sb.toString();
			}
			else {
				sb.append((char)c);
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

			if (floatString.indexOf(CharPool.PERIOD) >= 0) {
				return Float.valueOf(floatString);
			}

			return Float.intBitsToFloat(Integer.parseInt(floatString));
		}
		else if ((code == _TOKEN_SIMPLE_DOUBLE) ||
				 (code == _TOKEN_PRIMITIVE_DOUBLE)) {

			String doubleString = _readQuoted(pushbackReader);

			if (doubleString.indexOf(CharPool.PERIOD) >= 0) {
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

	private static final String _INDENT = "  ";

	private static final int _TOKEN_ARR_CLOS = ']';

	private static final int _TOKEN_ARR_OPEN = '[';

	private static final int _TOKEN_BRACE_CLOS = '}';

	private static final int _TOKEN_BRACE_OPEN = '{';

	private static final int _TOKEN_COMMA = ',';

	private static final int _TOKEN_EQ = '=';

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

	private static final int _TOKEN_VAL_CLOS = '"'; // '}';

	private static final int _TOKEN_VAL_OPEN = '"'; // '{';

	private static final int _TOKEN_VEC_CLOS = ')';

	// ---------- Configuration Output Implementation --------------------------

	private static final int _TOKEN_VEC_OPEN = '(';

	private static final Map<Object, Object> _codeToType = new HashMap<>();

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

	static {
		for (Map.Entry<Object, Object> entry : _typeToCode.entrySet()) {
			_codeToType.put(entry.getValue(), entry.getKey());
		}

		_codeToType.put(Integer.valueOf(_TOKEN_SIMPLE_STRING), String.class);
	}

	private int _line;
	private int _pos;

}