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

package com.liferay.portal.kernel.test.util;

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