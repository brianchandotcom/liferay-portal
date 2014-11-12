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
 * @author Michael C. Han
 */
public class LongUtil {

	public static long fromByteArray(byte[] bytes) {
		long out = 0L;

		out |= (long)ByteUtils.toUnsigned(bytes[0]) << 56;
		out |= (long)ByteUtils.toUnsigned(bytes[1]) << 48;
		out |= (long)ByteUtils.toUnsigned(bytes[2]) << 40;
		out |= (long)ByteUtils.toUnsigned(bytes[3]) << 32;
		out |= (long)ByteUtils.toUnsigned(bytes[4]) << 24;
		out |= (long)ByteUtils.toUnsigned(bytes[5]) << 16;
		out |= (long)ByteUtils.toUnsigned(bytes[6]) << 8;
		out |= (long)ByteUtils.toUnsigned(bytes[7]);

		return out;
	}

	public static long shiftRightAndPadLeftMostByte(
		long longValue, int positions, byte padding) {

		if (positions > _MAXIMUM_BYTE_SHIFTS) {
			throw new IllegalArgumentException("Cannot shift more than 8 bits");
		}

		byte[] bytes = LongUtil.toByteArray(longValue);

		bytes[0] =
			(byte)((bytes[0] >>> positions) +
			(byte)(padding << (_MAXIMUM_BYTE_SHIFTS - positions)));

		return fromByteArray(bytes);
	}

	public static byte[] toByteArray(long l) {
		byte[] bytes = new byte[8];

		bytes[0] = ((byte)(int)(l >>> 56 & 0xFF));
		bytes[1] = ((byte)(int)(l >>> 48 & 0xFF));
		bytes[2] = ((byte)(int)(l >>> 40 & 0xFF));
		bytes[3] = ((byte)(int)(l >>> 32 & 0xFF));
		bytes[4] = ((byte)(int)(l >>> 24 & 0xFF));
		bytes[5] = ((byte)(int)(l >>> 16 & 0xFF));
		bytes[6] = ((byte)(int)(l >>> 8 & 0xFF));
		bytes[7] = ((byte)(int)(l & 0xFF));

		return bytes;
	}

	private static final int _MAXIMUM_BYTE_SHIFTS = 7;

}