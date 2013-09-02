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

package com.liferay.portal.kernel.io;

import java.io.IOException;
import java.io.OutputStream;

import java.nio.ByteBuffer;

/**
 * @author Shuyang Zhou
 */
public class RestrictedByteArrayCacheOutputStream extends OutputStream {

	public RestrictedByteArrayCacheOutputStream(
		OutputStream outputStream, int cacheCapacity,
		FlushPreAction flushPreAction) {

		this(outputStream, 32, cacheCapacity, flushPreAction);
	}

	public RestrictedByteArrayCacheOutputStream(
		OutputStream outputStream, int initialCacheSize, int cacheCapacity,
		FlushPreAction flushPreAction) {

		if (initialCacheSize > cacheCapacity) {
			throw new IllegalArgumentException(
				"Initial cache size " + initialCacheSize +
					" is larger than cache capacity " + cacheCapacity);
		}

		this.outputStream = outputStream;
		this.cacheCapacity = cacheCapacity;
		this.flushPreAction = flushPreAction;

		cache = new byte[initialCacheSize];
	}

	@Override
	public void flush() throws IOException {
		if (overflowed) {
			return;
		}

		if (flushPreAction != null) {
			flushPreAction.beforeFlush();
		}

		overflowed = true;

		outputStream.write(cache, 0, index);

		cache = null;
		index = -1;
	}

	public int getCacheCapacity() {
		return cacheCapacity;
	}

	public boolean isOverflowed() {
		return overflowed;
	}

	public void reset() {
		if (overflowed) {
			throw new IllegalStateException("Cache overflowed");
		}

		index = 0;
	}

	public int size() {
		return index;
	}

	public byte[] toByteArray() {
		if (overflowed) {
			throw new IllegalStateException("Cache overflowed");
		}

		byte[] newCache = new byte[index];

		System.arraycopy(cache, 0, newCache, 0, index);

		return newCache;
	}

	public byte[] unsafeGetByteArray() {
		if (overflowed) {
			throw new IllegalStateException("Cache overflowed");
		}

		return cache;
	}

	public ByteBuffer unsafeGetByteBuffer() {
		if (overflowed) {
			throw new IllegalStateException("Cache overflowed");
		}

		return ByteBuffer.wrap(cache, 0, index);
	}

	@Override
	public void write(byte[] bytes) throws IOException {
		write(bytes, 0, bytes.length);
	}

	@Override
	public void write(byte[] bytes, int offset, int length) throws IOException {
		if (length <= 0) {
			return;
		}

		if (overflowed) {
			outputStream.write(bytes, offset, length);

			return;
		}

		int newIndex = index + length;

		if (newIndex > cacheCapacity) {
			flush();

			outputStream.write(bytes, offset, length);

			return;
		}

		ensureCacheSize(newIndex);

		System.arraycopy(bytes, offset, cache, index, length);

		index = newIndex;
	}

	@Override
	public void write(int b) throws IOException {
		if (overflowed) {
			outputStream.write(b);

			return;
		}

		int newIndex = index + 1;

		if (newIndex > cacheCapacity) {
			flush();

			outputStream.write(b);

			return;
		}

		ensureCacheSize(newIndex);

		cache[index] = (byte)b;

		index = newIndex;
	}

	public static interface FlushPreAction {

		public void beforeFlush() throws IOException;

	}

	protected void ensureCacheSize(int newIndex) {
		if (newIndex <= cache.length) {
			return;
		}

		int newCacheSize = Math.max(cache.length << 1, newIndex);

		if (newCacheSize > cacheCapacity) {
			newCacheSize = cacheCapacity;
		}

		byte[] newCache = new byte[newCacheSize];

		System.arraycopy(cache, 0, newCache, 0, cache.length);

		cache = newCache;
	}

	protected byte[] cache;
	protected int cacheCapacity;
	protected FlushPreAction flushPreAction;
	protected int index;
	protected OutputStream outputStream;
	protected boolean overflowed;

}