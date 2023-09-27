/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.util.hash;

import java.math.BigInteger;

/**
 * @author Brian Wing Shun Chan
 */
public class HashValue {

	public static HashValue parse(String inputString) {
		if ((inputString == null) || (inputString.length() == 0)) {
			return null;
		}

		return new HashValue(_parseInput(inputString));
	}

	public HashValue(byte[] digest) {
		_bigInteger = new BigInteger(1, digest);
	}

	public HashValue(String hexString) {
		_bigInteger = new BigInteger(hexString, 16);
	}

	public byte[] asByteArray() {
		return _bigInteger.toByteArray();
	}

	public String asCompactString() {
		return _bigInteger.toString(36);
	}

	public String asHexString() {
		return _bigInteger.toString(16);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (!(other instanceof HashValue)) {
			return false;
		}

		HashValue otherHashValue = (HashValue)other;

		return _bigInteger.equals(otherHashValue._bigInteger);
	}

	@Override
	public int hashCode() {
		return _bigInteger.hashCode();
	}

	private static String _parseInput(String inputString) {
		if (inputString == null) {
			return null;
		}

		String cleaned = inputString.trim();

		cleaned = cleaned.toLowerCase();

		int spaceIndex = cleaned.indexOf(' ');

		if (spaceIndex != -1) {
			String firstPart = cleaned.substring(0, spaceIndex);

			if (firstPart.startsWith("md") || firstPart.startsWith("sha")) {
				cleaned = cleaned.substring(cleaned.lastIndexOf(' ') + 1);
			}
			else if (firstPart.endsWith(":")) {
				cleaned = cleaned.substring(spaceIndex + 1);

				cleaned = cleaned.replace(" ", "");
			}
			else {
				cleaned = cleaned.substring(0, spaceIndex);
			}
		}

		return cleaned;
	}

	private final BigInteger _bigInteger;

}