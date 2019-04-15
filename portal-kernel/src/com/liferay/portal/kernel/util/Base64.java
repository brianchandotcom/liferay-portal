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

import com.liferay.petra.string.CharPool;
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
		return _decode(base64, false);
	}

	public static byte[] decodeFromURL(String base64) {
		return _decode(base64, true);
	}

	public static String encode(byte[] raw) {
		return _encode(raw, 0, raw.length, false);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static String encode(byte[] raw, int offset, int length) {
		return _encode(raw, offset, length, false);
	}

	public static String encodeToURL(byte[] raw) {
		return _encode(raw, 0, raw.length, true);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             ##decodeFromURL(String)}
	 */
	@Deprecated
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

		return _encode(ubaos.unsafeGetByteArray(), 0, ubaos.size(), false);
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

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static Object stringToObjectSilent(
		String s, ClassLoader classLoader) {

		return _stringToObject(s, classLoader, true);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #encodeToURL(byte[])}
	 */
	@Deprecated
	public static String toURLSafe(String base64) {
		return StringUtil.replace(
			base64, new char[] {CharPool.PLUS, CharPool.EQUAL, CharPool.SLASH},
			new char[] {CharPool.MINUS, CharPool.STAR, CharPool.UNDERLINE});
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected static char[] encodeBlock(byte[] raw, int offset, int lastIndex) {
		return _encodeBlock(raw, offset, lastIndex, false);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected static char getChar(int sixbit) {
		return _getChar(sixbit, false);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected static int getValue(char c) {
		return _getValue(c, false);
	}

	private static byte[] _decode(String base64, boolean url) {
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
			int block = _getValue(base64.charAt(i), url) << 18;

			block += _getValue(base64.charAt(i + 1), url) << 12;
			block += _getValue(base64.charAt(i + 2), url) << 6;
			block += _getValue(base64.charAt(i + 3), url);

			for (int j = 0; (j < 3) && ((rawindex + j) < raw.length); j++) {
				raw[rawindex + j] = (byte)((block >> (8 * (2 - j))) & 0xff);
			}

			rawindex += 3;
		}

		return raw;
	}

	private static String _encode(
		byte[] raw, int offset, int length, boolean url) {

		int lastIndex = Math.min(raw.length, offset + length);

		StringBuilder sb = new StringBuilder(
			((lastIndex - offset) / 3 + 1) * 4);

		for (int i = offset; i < lastIndex; i += 3) {
			sb.append(_encodeBlock(raw, i, lastIndex, url));
		}

		return sb.toString();
	}

	private static char[] _encodeBlock(
		byte[] raw, int offset, int lastIndex, boolean url) {

		int block = 0;

		int slack = lastIndex - offset - 1;

		int end = (slack < 2) ? slack : 2;

		for (int i = 0; i <= end; i++) {
			byte b = raw[offset + i];

			int neuter = (b >= 0) ? (int)b : b + 256;

			block += neuter << (8 * (2 - i));
		}

		char[] base64 = new char[4];

		for (int i = 0; i < 4; i++) {
			int sixbit = (block >>> (6 * (3 - i))) & 0x3f;

			base64[i] = _getChar(sixbit, url);
		}

		if (url) {
			if (slack < 1) {
				base64[2] = CharPool.STAR;
			}

			if (slack < 2) {
				base64[3] = CharPool.STAR;
			}
		}
		else {
			if (slack < 1) {
				base64[2] = CharPool.EQUAL;
			}

			if (slack < 2) {
				base64[3] = CharPool.EQUAL;
			}
		}

		return base64;
	}

	private static char _getChar(int sixbit, boolean url) {
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
			if (url) {
				return CharPool.MINUS;
			}

			return CharPool.PLUS;
		}

		if (sixbit != 63) {
			return CharPool.QUESTION;
		}

		if (url) {
			return CharPool.UNDERLINE;
		}

		return CharPool.SLASH;
	}

	private static int _getValue(char c, boolean url) {
		if ((c >= CharPool.UPPER_CASE_A) && (c <= CharPool.UPPER_CASE_Z)) {
			return c - 65;
		}

		if ((c >= CharPool.LOWER_CASE_A) && (c <= CharPool.LOWER_CASE_Z)) {
			return (c - 97) + 26;
		}

		if ((c >= CharPool.NUMBER_0) && (c <= CharPool.NUMBER_9)) {
			return (c - 48) + 52;
		}

		if (url) {
			if (c == CharPool.MINUS) {
				return 62;
			}

			if (c == CharPool.UNDERLINE) {
				return 63;
			}

			if (c != CharPool.STAR) {
				return -1;
			}
		}
		else {
			if (c == CharPool.PLUS) {
				return 62;
			}

			if (c == CharPool.SLASH) {
				return 63;
			}

			if (c != CharPool.EQUAL) {
				return -1;
			}
		}

		return 0;
	}

	private static Object _stringToObject(
		String s, ClassLoader classLoader, boolean silent) {

		if (s == null) {
			return null;
		}

		byte[] bytes = _decode(s, false);

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