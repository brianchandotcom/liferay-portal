/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Shuyang Zhou
 */
public class CharBufferPool {

	public static char[] borrow(int size) {
		if (!isEnabled()) {
			return new char[size];
		}

		_cleanUpDeadBuffers();

		int poolSize = -1;

		_modifyLock.lock();

		try {
			int index = Collections.binarySearch(
				_charBufferHoldersPool, new CharBufferHolder(size));

			if (index < 0) {
				index = -(index + 1);
			}

			while (index < _charBufferHoldersPool.size()) {
				CharBufferHolder charBufferHolder = _charBufferHoldersPool.get(
					index);

				if (charBufferHolder._borrowed) {
					index++;
				}
				else {
					char[] charBuffer = charBufferHolder.get();

					if (charBuffer != null) {
						charBufferHolder._borrowed = true;

						List<CharBufferHolder> borrowedCharBufferHolders =
							_borrowedCharBufferHoldersThreadLocal.get();

						borrowedCharBufferHolders.add(charBufferHolder);

						return charBuffer;
					}

					_charBufferHoldersPool.remove(index);
				}
			}
		}
		finally {
			poolSize = _charBufferHoldersPool.size();

			_modifyLock.unlock();
		}

		char[] charBuffer = new char[size + (size >> 9)];

		if (poolSize < _MAX_POOL_SIZE) {
			CharBufferHolder charBufferHolder = new CharBufferHolder(
				charBuffer);

			List<CharBufferHolder> borrowedCharBufferHolders =
				_borrowedCharBufferHoldersThreadLocal.get();

			borrowedCharBufferHolders.add(charBufferHolder);
		}

		return charBuffer;
	}

	public static void cleanUp() {
		List<CharBufferHolder> charBufferHolders =
			_borrowedCharBufferHoldersThreadLocal.get();

		_modifyLock.lock();

		try {
			for (CharBufferHolder charBufferHolder : charBufferHolders) {
				if (charBufferHolder._borrowed) {
					charBufferHolder._borrowed = false;
				}
				else {
					int index = Collections.binarySearch(
						_charBufferHoldersPool, charBufferHolder);

					if (index < 0) {
						index = -(index + 1);
					}

					_charBufferHoldersPool.add(index, charBufferHolder);
				}
			}
		}
		finally {
			_modifyLock.unlock();
		}

		charBufferHolders.clear();

		_cleanUpDeadBuffers();
	}

	public static boolean isEnabled() {
		return _enabledThreadLocal.get();
	}

	public static void setEnabled(boolean enabled) {
		_enabledThreadLocal.set(enabled);
	}

	private static void _cleanUpDeadBuffers() {

		// Peek before acquiring a Lock. This is crucial for concurrency since
		// SoftReferences will only be freed when there is a full GC or CMS
		// rescan. This means that the ReferenceQueue will be empty most of time
		// and should return immediately without touching the Lock. But when the
		// ReferenceQueue is not empty because a SoftReference has been freed by
		// the GC, it is more efficient to hold the Lock outside the while loop
		// rather than acquiring many dead CharBufferHolders.

		CharBufferHolder charBufferHolder =
			(CharBufferHolder)_referenceQueue.poll();

		if (charBufferHolder == null) {
			return;
		}

		_modifyLock.lock();

		try {
			do {
				_charBufferHoldersPool.remove(charBufferHolder);
			}
			while ((charBufferHolder =
						(CharBufferHolder)_referenceQueue.poll()) != null);
		}
		finally {
			_modifyLock.unlock();
		}
	}

	/**
	 * The initial pool size should be set slightly higher than the maximum real
	 * world concurrent processing request number. This value should not be
	 * tuned because it is based on extensive performance tests that tie to the
	 * overall system's inherent nature.
	 */
	private static final int _INITIAL_POOL_SIZE = 50;

	/**
	 * Make the actual maximum pool size twice the initial pool size to prevent
	 * random peaks that may cause an unnecessarily high use of old generation
	 * memory.
	 */
	private static final int _MAX_POOL_SIZE = _INITIAL_POOL_SIZE * 2;

	private static ThreadLocal<List<CharBufferHolder>>
		_borrowedCharBufferHoldersThreadLocal =
			new AutoResetThreadLocal<List<CharBufferHolder>>(
				CharBufferPool.class.getName() +
					"._borrowedCharBufferHoldersThreadLocal",
				new ArrayList<CharBufferHolder>());
	private static List<CharBufferHolder> _charBufferHoldersPool =
		new ArrayList<CharBufferHolder>(_INITIAL_POOL_SIZE);
	private static ThreadLocal<Boolean> _enabledThreadLocal =
		new AutoResetThreadLocal<Boolean>(
			CharBufferPool.class.getName() + "._enabledThreadLocal", false);
	private static Lock _modifyLock = new ReentrantLock();
	private static ReferenceQueue<Object> _referenceQueue =
		new ReferenceQueue<Object>();

	private static class CharBufferHolder
		extends SoftReference<char[]> implements Comparable<CharBufferHolder> {

		public CharBufferHolder(char[] charBuffer) {
			super(charBuffer, _referenceQueue);

			_length = charBuffer.length;
		}

		public CharBufferHolder(int length) {
			super(null);

			_length = length;
		}

		public int compareTo(CharBufferHolder charBufferHolder) {
			return _length - charBufferHolder._length;
		}

		private boolean _borrowed;
		private int _length;

	}

}