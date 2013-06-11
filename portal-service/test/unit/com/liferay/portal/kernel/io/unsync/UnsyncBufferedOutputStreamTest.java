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

package com.liferay.portal.kernel.io.unsync;

import com.liferay.portal.kernel.test.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncBufferedOutputStreamTest extends TestCase {

	@Test
	public void testBlockWrite() throws IOException {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		UnsyncBufferedOutputStream unsyncBufferedOutputStream =
			new UnsyncBufferedOutputStream(byteArrayOutputStream, _SIZE * 2);

		assertEquals(_SIZE * 2, unsyncBufferedOutputStream.buffer.length);

		unsyncBufferedOutputStream.write(_BUFFER);

		for (int i = 0; i < _SIZE; i++) {
			assertEquals(i, unsyncBufferedOutputStream.buffer[i]);
		}

		unsyncBufferedOutputStream.write(_BUFFER);

		for (int i = _SIZE; i < _SIZE * 2; i++) {
			assertEquals(i - _SIZE, unsyncBufferedOutputStream.buffer[i]);
		}

		unsyncBufferedOutputStream.write(100);

		assertEquals(100, unsyncBufferedOutputStream.buffer[0]);
		assertEquals(_SIZE * 2, byteArrayOutputStream.size());
	}

	@Test
	public void testConstructor() {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		UnsyncBufferedOutputStream unsyncBufferedOutputStream =
			new UnsyncBufferedOutputStream(byteArrayOutputStream);

		assertEquals(8192, unsyncBufferedOutputStream.buffer.length);

		unsyncBufferedOutputStream = new UnsyncBufferedOutputStream(
			byteArrayOutputStream, 10);

		assertEquals(10, unsyncBufferedOutputStream.buffer.length);

		try {
			new UnsyncBufferedOutputStream(byteArrayOutputStream, 0);
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new UnsyncBufferedOutputStream(byteArrayOutputStream, -1);
		}
		catch (IllegalArgumentException iae) {
		}
	}

	@Test
	public void testWrite() throws IOException {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		UnsyncBufferedOutputStream unsyncBufferedOutputStream =
			new UnsyncBufferedOutputStream(byteArrayOutputStream, _SIZE * 2);

		assertEquals(_SIZE * 2, unsyncBufferedOutputStream.buffer.length);

		for (int i = 0; i < _SIZE; i++) {
			unsyncBufferedOutputStream.write(i);

			assertEquals(i, unsyncBufferedOutputStream.buffer[i]);
		}

		unsyncBufferedOutputStream.flush();

		assertTrue(Arrays.equals(_BUFFER, byteArrayOutputStream.toByteArray()));
	}

	private static final byte[] _BUFFER =
		new byte[UnsyncBufferedOutputStreamTest._SIZE];

	private static final int _SIZE = 10;

	static {
		for (int i = 0; i < _SIZE; i++) {
			_BUFFER[i] = (byte)i;
		}
	}

}