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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.io.ProtectedObjectInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Brian Wing Shun Chan
 */
public class Base64 {

	public static byte[] decode(String base64) {
		if (Validator.isNull(base64)) {
			return new byte[0];
		}

		int pad = 0;

		for (int i = base64.length() - 1; base64.charAt(i) == CharPool.EQUAL;
			i--) {

			pad++;
		}

		int length = (base64.length() * 6) / 8 - pad;

		byte[] raw = new byte[length];

		int rawindex = 0;

		for (int i = 0; i < base64.length(); i += 4) {
			int block = getValue(base64.charAt(i)) << 18;

			block += getValue(base64.charAt(i + 1)) << 12;
			block += getValue(base64.charAt(i + 2)) << 6;
			block += getValue(base64.charAt(i + 3));

			for (int j = 0; j < 3 && rawindex + j < raw.length; j++) {
				raw[rawindex + j] = (byte)(block >> 8 * (2 - j) & 0xff);
			}

			rawindex += 3;
		}

		return raw;
	}

	public static String encode(byte[] raw) {
		return encode(raw, 0, raw.length);
	}

	public static String encode(byte[] raw, int offset, int length) {
		int lastIndex = Math.min(raw.length, offset + length);

		StringBuilder sb = new StringBuilder(
			((lastIndex - offset) / 3 + 1) * 4);

		for (int i = offset; i < lastIndex; i += 3) {
			sb.append(encodeBlock(raw, i, lastIndex));
		}

		return sb.toString();
	}

	public static String fromURLSafe(String base64) {
		return StringUtil.replace(
			base64,
			new char[] {CharPool.MINUS, CharPool.STAR, CharPool.UNDERLINE},
			new char[] {CharPool.PLUS, CharPool.EQUAL, CharPool.SLASH});
	}

	public static String objectToString(Object o) {
		if (o == null) {
			return null;
		}

		UnsyncByteArrayOutputStream ubaos = new UnsyncByteArrayOutputStream(
			32000);

		try (ObjectOutputStream os = new ObjectOutputStream(ubaos)) {
			os.writeObject(o);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return encode(ubaos.unsafeGetByteArray(), 0, ubaos.size());
	}

	public static Object stringToObject(String s) {
		return _stringToObject(s, null, false);
	}

	public static Object stringToObject(String s, ClassLoader classLoader) {
		return _stringToObject(s, classLoader, false);
	}

	public static Object stringToObjectSilent(String s) {
		return _stringToObject(s, null, true);
	}

	public static Object stringToObjectSilent(
		String s, ClassLoader classLoader) {

		return _stringToObject(s, classLoader, true);
	}

	public static String toURLSafe(String base64) {
		return StringUtil.replace(
			base64, new char[] {CharPool.PLUS, CharPool.EQUAL, CharPool.SLASH},
			new char[] {CharPool.MINUS, CharPool.STAR, CharPool.UNDERLINE});
	}

	protected static char[] encodeBlock(byte[] raw, int offset, int lastIndex) {
		int block = 0;

		int slack = lastIndex - offset - 1;

		int end = slack < 2 ? slack : 2;

		for (int i = 0; i <= end; i++) {
			byte b = raw[offset + i];

			int neuter = b >= 0 ? ((int) (b)) : b + 256;

			block += neuter << 8 * (2 - i);
		}

		char[] base64 = new char[4];

		for (int i = 0; i < 4; i++) {
			int sixbit = block >>> 6 * (3 - i) & 0x3f;

			base64[i] = getChar(sixbit);
		}

		if (slack < 1) {
			base64[2] = CharPool.EQUAL;
		}

		if (slack < 2) {
			base64[3] = CharPool.EQUAL;
		}

		return base64;
	}

	protected static char getChar(int sixbit) {
		if ((sixbit >= 0) && (sixbit <= 25)) {
			return (char)(65 + sixbit);
		}

		if ((sixbit >= 26) && (sixbit <= 51)) {
			return (char)(97 + (sixbit - 26));
		}

		if ((sixbit >= 52) && (sixbit <= 61)) {
			return (char)(48 + (sixbit - 52));
		}

		if (sixbit == 62) {
			return CharPool.PLUS;
		}

		if (sixbit != 63) {
			return CharPool.QUESTION;
		}

		return CharPool.SLASH;
	}

	protected static int getValue(char c) {
		if ((c >= CharPool.UPPER_CASE_A) && (c <= CharPool.UPPER_CASE_Z)) {
			return c - 65;
		}

		if ((c >= CharPool.LOWER_CASE_A) && (c <= CharPool.LOWER_CASE_Z)) {
			return (c - 97) + 26;
		}

		if ((c >= CharPool.NUMBER_0) && (c <= CharPool.NUMBER_9)) {
			return (c - 48) + 52;
		}

		if (c == CharPool.PLUS) {
			return 62;
		}

		if (c == CharPool.SLASH) {
			return 63;
		}

		if (c != CharPool.EQUAL) {
			return -1;
		}

		return 0;
	}

	private static Object _stringToObject(
		String s, ClassLoader classLoader, boolean silent) {

		if (s == null) {
			return null;
		}

		byte[] bytes = decode(s);

		UnsyncByteArrayInputStream ubais = new UnsyncByteArrayInputStream(
			bytes);

		try {
			ObjectInputStream is = null;

			if (classLoader == null) {
				is = new ProtectedObjectInputStream(ubais);
			}
			else {
				is = new ProtectedClassLoaderObjectInputStream(
					ubais, classLoader);
			}

			return is.readObject();
		}
		catch (Exception e) {
			if (!silent) {
				_log.error(e, e);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(Base64.class);

}