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

package com.liferay.portal.kernel.security;

import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.util.GetterUtil;

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
		else {
			return true;
		}
	}

	public static byte nextByte() {
		int index = _index.getAndIncrement();

		if (index < _BUFFER_SIZE) {
			return _bytes[index];
		}

		return (byte)_reload(index);
	}

	public static double nextDouble() {
		int index = _index.getAndAdd(8);

		if ((index + 7) < _BUFFER_SIZE) {
			return BigEndianCodec.getDouble(_bytes, index);
		}

		return Double.longBitsToDouble(_reload(index));
	}

	public static float nextFloat() {
		int index = _index.getAndAdd(4);

		if ((index + 3) < _BUFFER_SIZE) {
			return BigEndianCodec.getFloat(_bytes, index);
		}

		return Float.intBitsToFloat((int)_reload(index));
	}

	public static int nextInt() {
		int index = _index.getAndAdd(4);

		if ((index + 3) < _BUFFER_SIZE) {
			return BigEndianCodec.getInt(_bytes, index);
		}

		return (int)_reload(index);
	}

	public static long nextLong() {
		int index = _index.getAndAdd(8);

		if ((index + 7) < _BUFFER_SIZE) {
			return BigEndianCodec.getLong(_bytes, index);
		}

		return _reload(index);
	}

	private static long _reload(int index) {
		if (_reloadingFlag.compareAndSet(false, true)) {
			_random.nextBytes(_bytes);

			_gapRandom.setSeed(_random.nextLong());

			_index.set(0);

			_reloadingFlag.set(false);
		}

		return _gapRandom.nextLong() ^ BigEndianCodec.getLong(
			_bytes, Math.abs(index % (_BUFFER_SIZE - 7)));
	}

	private static final int _BUFFER_SIZE;

	private static final int _MIN_BUFFER_SIZE = 1024;

	private static final byte[] _bytes;
	private static final Random _gapRandom = new Random();
	private static final AtomicInteger _index = new AtomicInteger();
	private static final Random _random = new SecureRandom();
	private static final AtomicBoolean _reloadingFlag = new AtomicBoolean();

	static {
		int bufferSize = GetterUtil.getInteger(
			System.getProperty(
				SecureRandomUtil.class.getName() + ".buffer.size"));

		if (bufferSize < _MIN_BUFFER_SIZE) {
			bufferSize = _MIN_BUFFER_SIZE;
		}

		_BUFFER_SIZE = bufferSize;

		_bytes = new byte[_BUFFER_SIZE];

		_random.nextBytes(_bytes);

		_gapRandom.setSeed(_random.nextLong());
	}

}