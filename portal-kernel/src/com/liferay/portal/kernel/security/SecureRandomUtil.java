/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security;

import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SystemProperties;

import java.security.SecureRandom;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Shuyang Zhou
 */
public class SecureRandomUtil {

	public static boolean nextBoolean() {
		byte b = nextByte();

		if (b < 0) {
			return false;
		}

		return true;
	}

	public static byte nextByte() {
		int index = _index.getAndIncrement();

		if (index < _BUFFER_SIZE) {
			return _BYTES[index];
		}

		return (byte)_reload(index);
	}

	public static double nextDouble() {
		int index = _index.getAndAdd(8);

		if ((index + 7) < _BUFFER_SIZE) {
			return BigEndianCodec.getDouble(_BYTES, index);
		}

		return Double.longBitsToDouble(_reload(index));
	}

	public static float nextFloat() {
		int index = _index.getAndAdd(4);

		if ((index + 3) < _BUFFER_SIZE) {
			return BigEndianCodec.getFloat(_BYTES, index);
		}

		return Float.intBitsToFloat((int)_reload(index));
	}

	public static int nextInt() {
		int index = _index.getAndAdd(4);

		if ((index + 3) < _BUFFER_SIZE) {
			return BigEndianCodec.getInt(_BYTES, index);
		}

		return (int)_reload(index);
	}

	public static long nextLong() {
		int index = _index.getAndAdd(8);

		if ((index + 7) < _BUFFER_SIZE) {
			return BigEndianCodec.getLong(_BYTES, index);
		}

		return _reload(index);
	}

	private static long _reload(int index) {
		if (_reloadingFlag.compareAndSet(false, true)) {
			_random.nextBytes(_BYTES);

			_gapRandom.setSeed(_random.nextLong());

			_index.set(0);

			_reloadingFlag.set(false);
		}

		return _gapRandom.nextLong() ^
			   BigEndianCodec.getLong(
				   _BYTES, Math.abs(index % (_BUFFER_SIZE - 7)));
	}

	private static final int _BUFFER_SIZE;

	private static final byte[] _BYTES;

	private static final int _MIN_BUFFER_SIZE = 1024;

	private static final Random _gapRandom = new Random();
	private static final AtomicInteger _index = new AtomicInteger();
	private static final Random _random = new SecureRandom();
	private static final AtomicBoolean _reloadingFlag = new AtomicBoolean();

	static {
		int bufferSize = GetterUtil.getInteger(
			SystemProperties.get(
				SecureRandomUtil.class.getName() + ".buffer.size"));

		if (bufferSize < _MIN_BUFFER_SIZE) {
			bufferSize = _MIN_BUFFER_SIZE;
		}

		_BUFFER_SIZE = bufferSize;

		_BYTES = new byte[_BUFFER_SIZE];

		_random.nextBytes(_BYTES);

		_gapRandom.setSeed(_random.nextLong());
	}

}