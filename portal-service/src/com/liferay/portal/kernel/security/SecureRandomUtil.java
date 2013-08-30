/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * <p>A weak coherence thread-safe lock-free SecureRandom buffering utility.</p>
 *
 * <p>Fetch random bytes from SecureRandom by a buffer, then consume the buffer
 * by various number formats. The purpose is to reduce the accessing frequency
 * to SecureRandom, in order to avoid the system level synchronization.</p>
 *
 * <p>The "weak coherence" happens on concurrent buffer reloading. To achieve
 * lock-free only one thread is doing the reloading, the rest currently arrived
 * threads(if there is any) will get their random number by doing a BIT-XOR
 * between first long in the buffer and the non-threadsafe gap seed. The gap
 * seed is replaced by the output random long afterward.</p>
 *
 * <p>The non-threadsafe natual of the gap seed, introduces a new source of
 * randomness(the thread scheduling at OS level), which is making the random
 * output on current reloading even safer. The non-threadsafe property of gap
 * seed won't affact the threadsafe property of this utility class.</p>
 *
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

		if (index < _bufferSize) {
			return _bytes[index];
		}

		return (byte)_reload();
	}

	public static double nextDouble() {
		int index = _index.getAndAdd(8);

		if ((index + 7) < _bufferSize) {
			return BigEndianCodec.getDouble(_bytes, index);
		}

		return Double.longBitsToDouble(_reload());
	}

	public static float nextFloat() {
		int index = _index.getAndAdd(4);

		if ((index + 3) < _bufferSize) {
			return BigEndianCodec.getFloat(_bytes, index);
		}

		return Float.intBitsToFloat((int)_reload());
	}

	public static int nextInt() {
		int index = _index.getAndAdd(4);

		if ((index + 3) < _bufferSize) {
			return BigEndianCodec.getInt(_bytes, index);
		}

		return (int)_reload();
	}

	public static long nextLong() {
		int index = _index.getAndAdd(8);

		if ((index + 7) < _bufferSize) {
			return BigEndianCodec.getLong(_bytes, index);
		}

		return _reload();
	}

	private static long _reload() {
		if (_reloadingFlag.compareAndSet(false, true)) {
			_random.nextBytes(_bytes);

			_index.set(0);
			_reloadingFlag.set(false);
		}

		long l = BigEndianCodec.getLong(_bytes, 0) ^ _gapSeed;

		_gapSeed = l;

		return l;
	}

	private static final int _bufferSize;
	private static final byte[] _bytes;
	private static final AtomicInteger _index = new AtomicInteger();

	private static final int _MIN_BUFFER_SIZE = 1024;

	private static final Random _random = new SecureRandom();
	private static final AtomicBoolean _reloadingFlag = new AtomicBoolean();
	private static long _gapSeed = _random.nextLong();

	static {
		int bufferSize = GetterUtil.getInteger(
			System.getProperty(
				SecureRandomUtil.class.getName() + ".buffer.size"));

		if (bufferSize < _MIN_BUFFER_SIZE) {
			bufferSize = _MIN_BUFFER_SIZE;
		}

		_bufferSize = bufferSize;

		_bytes = new byte[_bufferSize];

		_random.nextBytes(_bytes);
	}

}