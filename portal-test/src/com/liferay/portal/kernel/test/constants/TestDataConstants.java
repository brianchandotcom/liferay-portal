/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.constants;

/**
 * @author Matthew Tambara
 */
public class TestDataConstants {

	public static final byte[] TEST_BYTE_ARRAY = {
		(byte)-16, (byte)-96, (byte)-97, (byte)33, (byte)-36, (byte)-46,
		(byte)-91, (byte)127
	};

	public static byte[] repeatByteArray(int i) {
		if (i < 1) {
			throw new IllegalArgumentException("Input must be greater than 0");
		}

		if (i == 1) {
			return TEST_BYTE_ARRAY;
		}

		int length = TEST_BYTE_ARRAY.length;

		byte[] result = new byte[length * i];

		for (int n = 0; n < result.length; n += length) {
			System.arraycopy(TEST_BYTE_ARRAY, 0, result, n, length);
		}

		return result;
	}

}