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

import com.liferay.portal.kernel.io.BigEndianCodec;

/**
 * @author Michael C. Han
 */
public class LongUtil {

	public static long shiftRightAndPadLeftMostByte(
		long longValue, int positions, byte padding) {

		if (positions > _MAXIMUM_BYTE_SHIFTS) {
			throw new IllegalArgumentException("Cannot shift more than 8 bits");
		}

		byte[] bytes = new byte[8];

		BigEndianCodec.putLong(bytes, 0, longValue);

		bytes[0] =
			(byte)((bytes[0] >>> positions) +
			(byte)(padding << (_MAXIMUM_BYTE_SHIFTS - positions)));

		return BigEndianCodec.getLong(bytes, 0);
	}

	private static final int _MAXIMUM_BYTE_SHIFTS = 7;

}