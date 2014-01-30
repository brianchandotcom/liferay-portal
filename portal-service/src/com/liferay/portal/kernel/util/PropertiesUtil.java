/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.nio.charset.CharsetDecoderUtil;
import com.liferay.portal.kernel.nio.charset.CharsetEncoderUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;

import java.lang.reflect.Method;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class PropertiesUtil {

	public static void copyProperties(
		Properties sourceProperties, Properties targetProperties) {

		for (Map.Entry<Object, Object> entry : sourceProperties.entrySet()) {
			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			targetProperties.setProperty(key, value);
		}
	}

	public static Properties fromMap(Map<String, String> map) {
		Properties properties = new Properties();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if (value != null) {
				properties.setProperty(key, value);
			}
		}

		return properties;
	}

	public static Properties fromMap(Properties properties) {
		return properties;
	}

	public static void fromProperties(
		Properties properties, Map<String, String> map) {

		map.clear();

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			map.put((String)entry.getKey(), (String)entry.getValue());
		}
	}

	public static Properties getProperties(
		Properties properties, String prefix, boolean removePrefix) {

		Properties newProperties = new Properties();

		Enumeration<String> enu =
			(Enumeration<String>)properties.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();

			if (key.startsWith(prefix)) {
				String value = properties.getProperty(key);

				if (removePrefix) {
					key = key.substring(prefix.length());
				}

				newProperties.setProperty(key, value);
			}
		}

		return newProperties;
	}

	public static String list(Map<String, String> map) {
		Properties properties = fromMap(map);

		return list(properties);
	}

	public static void list(Map<String, String> map, PrintStream printWriter) {
		Properties properties = fromMap(map);

		properties.list(printWriter);
	}

	public static void list(Map<String, String> map, PrintWriter printWriter) {
		Properties properties = fromMap(map);

		properties.list(printWriter);
	}

	public static String list(Properties properties) {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		PrintStream printStream = new PrintStream(unsyncByteArrayOutputStream);

		properties.list(printStream);

		return unsyncByteArrayOutputStream.toString();
	}

	public static Properties load(InputStream is, String charsetName)
		throws IOException {

		if (JavaDetector.isJDK6() || JavaDetector.isJDK7()) {
			return loadJDK6(new InputStreamReader(is, charsetName));
		}
		else {
			return loadJDK5(is, charsetName);
		}
	}

	public static void load(Properties properties, String s)
		throws IOException {

		if (Validator.isNull(s)) {
			return;
		}

		s = UnicodeFormatter.toString(s);

		s = StringUtil.replace(s, "\\u003d", "=");
		s = StringUtil.replace(s, "\\u000a", "\n");
		s = StringUtil.replace(s, "\\u0021", "!");
		s = StringUtil.replace(s, "\\u0023", "#");
		s = StringUtil.replace(s, "\\u0020", " ");
		s = StringUtil.replace(s, "\\u005c", "\\");

		properties.load(new UnsyncByteArrayInputStream(s.getBytes()));

		List<String> propertyNames = Collections.list(
			(Enumeration<String>)properties.propertyNames());

		for (int i = 0; i < propertyNames.size(); i++) {
			String key = propertyNames.get(i);

			String value = properties.getProperty(key);

			// Trim values because it may leave a trailing \r in certain Windows
			// environments. This is a known case for loading SQL scripts in SQL
			// Server.

			if (value != null) {
				value = value.trim();

				properties.setProperty(key, value);
			}
		}
	}

	public static Properties load(String s) throws IOException {
		return load(s, StringPool.UTF8);
	}

	public static Properties load(String s, String charsetName)
		throws IOException {

		if (JavaDetector.isJDK6() || JavaDetector.isJDK7()) {
			return loadJDK6(new UnsyncStringReader(s));
		}

		ByteBuffer byteBuffer = CharsetEncoderUtil.encode(charsetName, s);

		InputStream is = new UnsyncByteArrayInputStream(
			byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.limit());

		return loadJDK5(is, charsetName);
	}

	public static Properties loadJDK5(InputStream is, String charsetName)
		throws IOException {

		Properties iso8859_1Properties = new Properties();

		iso8859_1Properties.load(is);

		Properties properties = new Properties();

		CharsetEncoder charsetEncoder = CharsetEncoderUtil.getCharsetEncoder(
			StringPool.ISO_8859_1);

		CharsetDecoder charsetDecoder = CharsetDecoderUtil.getCharsetDecoder(
			charsetName);

		for (Map.Entry<Object, Object> entry : iso8859_1Properties.entrySet()) {
			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			CharBuffer keyCharBuffer = charsetDecoder.decode(
				charsetEncoder.encode(CharBuffer.wrap(key)));
			CharBuffer valueCharBuffer = charsetDecoder.decode(
				charsetEncoder.encode(CharBuffer.wrap(value)));

			properties.put(
				keyCharBuffer.toString(), valueCharBuffer.toString());
		}

		return properties;
	}

	public static Properties loadJDK6(Reader reader) throws IOException {
		try {
			Properties properties = new Properties();

			if (_jdk6LoadMethod == null) {
				_jdk6LoadMethod = ReflectionUtil.getDeclaredMethod(
					Properties.class, "load", Reader.class);
			}

			_jdk6LoadMethod.invoke(properties, reader);

			return properties;
		}
		catch (Exception e) {
			Throwable cause = e.getCause();

			if (cause instanceof IOException) {
				throw (IOException)cause;
			}

			throw new IllegalStateException(
				"Failed to invoke java.util.Properties.load(Reader reader)", e);
		}
	}

	public static void merge(Properties properties1, Properties properties2) {
		Enumeration<String> enu =
			(Enumeration<String>)properties2.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();
			String value = properties2.getProperty(key);

			properties1.setProperty(key, value);
		}
	}

	public static String toString(Properties properties) {
		SafeProperties safeProperties = null;

		if (properties instanceof SafeProperties) {
			safeProperties = (SafeProperties)properties;
		}

		StringBundler sb = null;

		if (properties.isEmpty()) {
			sb = new StringBundler();
		}
		else {
			sb = new StringBundler(properties.size() * 4);
		}

		Enumeration<String> enu =
			(Enumeration<String>)properties.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();

			sb.append(key);
			sb.append(StringPool.EQUAL);

			if (safeProperties != null) {
				sb.append(safeProperties.getEncodedProperty(key));
			}
			else {
				sb.append(properties.getProperty(key));
			}

			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	public static void trimKeys(Properties properties) {
		Enumeration<String> enu =
			(Enumeration<String>)properties.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();
			String value = properties.getProperty(key);

			String trimmedKey = key.trim();

			if (!key.equals(trimmedKey)) {
				properties.remove(key);
				properties.setProperty(trimmedKey, value);
			}
		}
	}

	private static Method _jdk6LoadMethod;

}