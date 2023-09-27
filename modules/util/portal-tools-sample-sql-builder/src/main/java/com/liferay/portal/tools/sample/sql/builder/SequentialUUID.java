/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		return StringBundler.concat(
			_UUID_PREFIX, _toHexString(high, 4), StringPool.MINUS,
			_toHexString(low, 8));
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