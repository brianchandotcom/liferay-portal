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

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Shuyang Zhou
 * @author Samuel Kong
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

		return (byte)_reload();
	}

	public static double nextDouble() {
		int index = _index.getAndAdd(8);

		if ((index + 7) < _BUFFER_SIZE) {
			return BigEndianCodec.getDouble(_bytes, index);
		}

		return Double.longBitsToDouble(_reload());
	}

	public static float nextFloat() {
		int index = _index.getAndAdd(4);

		if ((index + 3) < _BUFFER_SIZE) {
			return BigEndianCodec.getFloat(_bytes, index);
		}

		return Float.intBitsToFloat((int)_reload());
	}

	public static int nextInt() {
		int index = _index.getAndAdd(4);

		if ((index + 3) < _BUFFER_SIZE) {
			return BigEndianCodec.getInt(_bytes, index);
		}

		return (int)_reload();
	}

	public static int nextInt(int n) throws IllegalArgumentException {
		if (n <= 0) {
			throw new IllegalArgumentException("n must be positive");
		}

		int x = nextInt() % n;

		if (x >= 0) {
			return x;
		}

		return x + n;
	}

	public static long nextLong() {
		int index = _index.getAndAdd(8);

		if ((index + 7) < _BUFFER_SIZE) {
			return BigEndianCodec.getLong(_bytes, index);
		}

		return _reload();
	}

	public static void randomize(char[] array) {
		int length = array.length;

		for (int i = 0; i < length - 1; i++) {
			int x = nextInt(length);
			char y = array[i];

			array[i] = array[i + x];
			array[i + x] = y;

			length--;
		}
	}

	public static void randomize(int[] array) {
		int length = array.length;

		for (int i = 0; i < length - 1; i++) {
			int x = nextInt(length);
			int y = array[i];

			array[i] = array[i + x];
			array[i + x] = y;

			length--;
		}
	}

	public static void randomize(List<Object> list) {
		int size = list.size();

		for (int i = 0; i <= size; i++) {
			Object obj = list.get(i);

			int j = nextInt(size);

			list.set(i, list.get(i + j));
			list.set(i + j, obj);

			size--;
		}
	}

	public static void randomize(Object[] array) {
		int length = array.length;

		for (int i = 0; i < length - 1; i++) {
			int x = nextInt(length);
			Object y = array[i];

			array[i] = array[i + x];
			array[i + x] = y;

			length--;
		}
	}

	public static String randomize(String s) {
		if (s == null) {
			return null;
		}

		char[] array = s.toCharArray();

		randomize(array);

		return new String(array);
	}

	private static long _reload() {
		if (_reloadingFlag.compareAndSet(false, true)) {
			_random.nextBytes(_bytes);

			_index.set(0);

			_reloadingFlag.set(false);
		}

		int offset = _index.get() % (_BUFFER_SIZE - 7);

		long l = BigEndianCodec.getLong(_bytes, offset) ^ _gapSeed;

		_gapSeed = l;

		return l;
	}

	private static final int _BUFFER_SIZE;

	private static final int _MIN_BUFFER_SIZE = 1024;

	private static final byte[] _bytes;
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

		_gapSeed = _random.nextLong();
	}

	private static long _gapSeed;

}