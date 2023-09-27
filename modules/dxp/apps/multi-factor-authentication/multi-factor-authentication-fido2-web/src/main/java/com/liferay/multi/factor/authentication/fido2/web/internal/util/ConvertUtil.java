/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.fido2.web.internal.util;

import com.yubico.webauthn.data.ByteArray;

/**
 * @author Arthur Chan
 */
public class ConvertUtil {

	public static ByteArray toByteArray(long l) {
		return new ByteArray(toBytes(l));
	}

	public static byte[] toBytes(long l) {
		byte[] bytes = new byte[Long.BYTES];

		for (int i = Long.BYTES - 1; i >= 0; i--) {
			bytes[i] = (byte)(l & 0xFF);

			l >>= Byte.SIZE;
		}

		return bytes;
	}

	public static long toLong(byte[] bytes) throws IllegalArgumentException {
		if (bytes.length != Long.BYTES) {
			throw new IllegalArgumentException();
		}

		long l = 0;

		for (int i = 0; i < Long.BYTES; i++) {
			l <<= Byte.SIZE;

			l |= bytes[i] & 0xFF;
		}

		return l;
	}

	public static long toLong(ByteArray byteArray)
		throws IllegalArgumentException {

		return toLong(byteArray.getBytes());
	}

}