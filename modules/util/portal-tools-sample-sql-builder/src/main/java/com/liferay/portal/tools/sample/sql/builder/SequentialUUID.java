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

package com.liferay.portal.tools.sample.sql.builder;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.concurrent.atomic.AtomicLong;

/**
 * A simplified UUID generator for sample SQL generation that generates UUID in
 * a sequential order. This should not be used for any other purposes.
 *
 * @author Shuyang Zhou
 */
public class SequentialUUID {

	public static String generate() {
		long count = _counter.getAndIncrement();

		long high = (count >> 48) & 0xffff;
		long low = count & 0xffffffffffffL;

		StringBundler sb = new StringBundler(4);

		sb.append(_UUID_PREFIX);
		sb.append(_toHexString(high, 4));
		sb.append(StringPool.MINUS);
		sb.append(_toHexString(low, 8));

		return sb.toString();
	}

	public static SequentialUUID getSequentialUUID() {
		return _sequentialUUID;
	}

	private static String _toHexString(long number, int digits) {
		char[] buffer = new char[digits];

		for (int i = 0; i < digits; i++) {
			buffer[i] = '0';
		}

		int index = digits;

		do {
			buffer[--index] = _HEX_DIGITS[(int)(number & 15)];

			number >>>= 4;
		}
		while (number != 0);

		return new String(buffer);
	}

	private static final char[] _HEX_DIGITS = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
		'e', 'f'
	};

	private static final String _UUID_PREFIX = "00000000-0000-0000-";

	private static final AtomicLong _counter = new AtomicLong();
	private static final SequentialUUID _sequentialUUID = new SequentialUUID();

}