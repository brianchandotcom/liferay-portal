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

/**
 * @author Brian Wing Shun Chan
 */
public class UnicodeFormatter_IW {
	public static UnicodeFormatter_IW getInstance() {
		return _instance;
	}

	public java.lang.String bytesToHex(byte[] bytes) {
		return UnicodeFormatter.bytesToHex(bytes);
	}

	public java.lang.String byteToHex(byte b) {
		return UnicodeFormatter.byteToHex(b);
	}

	public char[] byteToHex(byte b, char[] hexes) {
		return UnicodeFormatter.byteToHex(b, hexes);
	}

	public char[] byteToHex(byte b, char[] hexes, boolean upperCase) {
		return UnicodeFormatter.byteToHex(b, hexes, upperCase);
	}

	public java.lang.String charToHex(char c) {
		return UnicodeFormatter.charToHex(c);
	}

	public byte[] hexToBytes(java.lang.String hexString) {
		return UnicodeFormatter.hexToBytes(hexString);
	}

	public java.lang.String parseString(java.lang.String hexString) {
		return UnicodeFormatter.parseString(hexString);
	}

	public java.lang.String toString(char[] array) {
		return UnicodeFormatter.toString(array);
	}

	public java.lang.String toString(java.lang.String s) {
		return UnicodeFormatter.toString(s);
	}

	private UnicodeFormatter_IW() {
	}

	private static UnicodeFormatter_IW _instance = new UnicodeFormatter_IW();
}