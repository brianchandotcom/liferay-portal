/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security;

import com.liferay.petra.io.BigEndianCodec;
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
		Buffer buffer = _buffer;

		int index = buffer.getAndAdd(1);

		if (index < _BUFFER_SIZE) {
			return buffer._bytes[index];
		}

		return (byte)_reload();
	}

	public static double nextDouble() {
		Buffer buffer = _buffer;

		int index = buffer.getAndAdd(8);

		if ((index + 7) < _BUFFER_SIZE) {
			return BigEndianCodec.getDouble(buffer._bytes, index);
		}

		return Double.longBitsToDouble(_reload());
	}

	public static float nextFloat() {
		Buffer buffer = _buffer;

		int index = buffer.getAndAdd(4);

		if ((index + 3) < _BUFFER_SIZE) {
			return BigEndianCodec.getFloat(buffer._bytes, index);
		}

		return Float.intBitsToFloat((int)_reload());
	}

	public static int nextInt() {
		Buffer buffer = _buffer;

		int index = buffer.getAndAdd(4);

		if ((index + 3) < _BUFFER_SIZE) {
			return BigEndianCodec.getInt(buffer._bytes, index);
		}

		return (int)_reload();
	}

	public static long nextLong() {
		Buffer buffer = _buffer;

		int index = buffer.getAndAdd(8);

		if ((index + 7) < _BUFFER_SIZE) {
			return BigEndianCodec.getLong(buffer._bytes, index);
		}

		return _reload();
	}

	private static long _reload() {
		if (_reloadingFlag.compareAndSet(false, true)) {
			_buffer = new Buffer();

			_reloadingFlag.set(false);
		}

		return _random.nextLong();
	}

	private static final int _BUFFER_SIZE;

	private static final int _MIN_BUFFER_SIZE = 1024;

	private static volatile Buffer _buffer;
	private static final Random _random = new SecureRandom();
	private static final AtomicBoolean _reloadingFlag = new AtomicBoolean();

	private static class Buffer {

		public int getAndAdd(int delta) {
			return _index.getAndAdd(delta);
		}

		private Buffer() {
			_random.nextBytes(_bytes);
		}

		private final byte[] _bytes = new byte[_BUFFER_SIZE];
		private final AtomicInteger _index = new AtomicInteger();

	}

	static {
		int bufferSize = GetterUtil.getInteger(
			SystemProperties.get(
				SecureRandomUtil.class.getName() + ".buffer.size"));

		if (bufferSize < _MIN_BUFFER_SIZE) {
			bufferSize = _MIN_BUFFER_SIZE;
		}

		_BUFFER_SIZE = bufferSize;

		_buffer = new Buffer();
	}

}