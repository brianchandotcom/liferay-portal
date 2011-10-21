/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Shuyang Zhou
 */
public class UnsyncBufferedInputStreamTest extends TestCase {

	public void testBlockRead() throws IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			_BUFFER);

		int size = 10;

		UnsyncBufferedInputStream unsyncBufferedInputStream =
			new UnsyncBufferedInputStream(byteArrayInputStream, size);

		assertEquals(_SIZE, byteArrayInputStream.available());
		assertEquals(_SIZE, unsyncBufferedInputStream.available());

		byte[] buffer = new byte[5];

		// Zero length read

		assertEquals(0, unsyncBufferedInputStream.read(buffer, 0, 0));

		// Negative length read

		assertEquals(0, unsyncBufferedInputStream.read(buffer, 0, -1));

		// In-memory

		assertEquals(0, unsyncBufferedInputStream.read());
		assertEquals(_SIZE - size, byteArrayInputStream.available());
		assertEquals(_SIZE - 1, unsyncBufferedInputStream.available());

		int read = unsyncBufferedInputStream.read(buffer);

		assertEquals(buffer.length, read);

		for (int i = 1; i < buffer.length + 1; i++) {
			assertEquals(i, buffer[i - 1]);
		}

		// Exhaust buffer

		assertEquals(6, unsyncBufferedInputStream.read());
		assertEquals(7, unsyncBufferedInputStream.read());
		assertEquals(8, unsyncBufferedInputStream.read());
		assertEquals(9, unsyncBufferedInputStream.read());

		// Force reload

		read = unsyncBufferedInputStream.read(buffer);

		assertEquals(buffer.length, read);

		for (int i = 10; i < buffer.length + 10; i++) {
			assertEquals(i, buffer[i - 10]);
		}

		assertEquals(_SIZE - size * 2, byteArrayInputStream.available());
		assertEquals(_SIZE - 15, unsyncBufferedInputStream.available());

		// Fill the buffer

		buffer = new byte[10];

		read = unsyncBufferedInputStream.read(buffer);

		assertEquals(buffer.length, read);

		for (int i = 15; i < buffer.length + 15; i++) {
			assertEquals(i, buffer[i - 15]);
		}

		assertEquals(_SIZE - size * 3, byteArrayInputStream.available());
		assertEquals(_SIZE - 25, unsyncBufferedInputStream.available());

		// Leave 5 bytes

		for (int i = 25; i < _SIZE - 5; i++) {
			assertEquals(i & 0xff, unsyncBufferedInputStream.read());
		}

		assertEquals(_SIZE % 5, byteArrayInputStream.available());
		assertEquals(5, unsyncBufferedInputStream.available());

		// Finish

		read = unsyncBufferedInputStream.read(buffer);

		assertEquals(5, read);
		assertEquals(-1, unsyncBufferedInputStream.read(buffer));

		// Mark and EOF

		byteArrayInputStream = new ByteArrayInputStream(_BUFFER);

		unsyncBufferedInputStream = new UnsyncBufferedInputStream(
			byteArrayInputStream, size);

		unsyncBufferedInputStream.mark(_SIZE);

		byte[] tempBuffer = new byte[_SIZE];

		assertEquals(_SIZE, unsyncBufferedInputStream.read(tempBuffer));
		assertEquals(-1, unsyncBufferedInputStream.read(tempBuffer));
	}

	public void testClose() throws IOException {
		int size = 10;

		UnsyncBufferedInputStream unsyncBufferedInputStream =
			new UnsyncBufferedInputStream(
				new ByteArrayInputStream(new byte[size]));

		unsyncBufferedInputStream.close();

		assertTrue(unsyncBufferedInputStream.inputStream == null);
		assertTrue(unsyncBufferedInputStream.buffer == null);

		try {
			unsyncBufferedInputStream.available();

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedInputStream.read();

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedInputStream.read(new byte[5]);

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedInputStream.reset();

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedInputStream.skip(0);

			fail();
		}
		catch (IOException ioe) {
		}

		unsyncBufferedInputStream.close();
	}

	public void testConstructor() throws IOException {
		int size = 10;

		UnsyncBufferedInputStream unsyncBufferedInputStream =
			new UnsyncBufferedInputStream(
				new ByteArrayInputStream(new byte[size]));

		assertEquals(size, unsyncBufferedInputStream.available());

		unsyncBufferedInputStream = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(new byte[size]), _SIZE);

		assertEquals(size, unsyncBufferedInputStream.available());

		try {
			new UnsyncBufferedInputStream(
				new ByteArrayInputStream(new byte[size]), 0);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new UnsyncBufferedInputStream(
				new ByteArrayInputStream(new byte[size]), -1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}
	}

	public void testMarkAndReset() throws IOException {
		UnsyncBufferedInputStream unsyncBufferedInputStream =
			new UnsyncBufferedInputStream(new ByteArrayInputStream(_BUFFER));

		assertEquals(-1, unsyncBufferedInputStream.markLimitIndex);

		// Zero marking

		unsyncBufferedInputStream.mark(0);

		assertEquals(-1, unsyncBufferedInputStream.markLimitIndex);

		// Negative marking

		unsyncBufferedInputStream.mark(-2);

		assertEquals(-1, unsyncBufferedInputStream.markLimitIndex);

		// Normal

		int markLimitIndex = 10;

		unsyncBufferedInputStream.mark(markLimitIndex);

		assertEquals(markLimitIndex, unsyncBufferedInputStream.markLimitIndex);
		assertEquals(_SIZE, unsyncBufferedInputStream.available());
		assertEquals(0, unsyncBufferedInputStream.read());
		assertEquals(1, unsyncBufferedInputStream.read());
		assertEquals(2, unsyncBufferedInputStream.read());
		assertEquals(3, unsyncBufferedInputStream.index);

		unsyncBufferedInputStream.reset();

		assertEquals(_SIZE, unsyncBufferedInputStream.available());
		assertEquals(0, unsyncBufferedInputStream.read());
		assertEquals(1, unsyncBufferedInputStream.read());
		assertEquals(2, unsyncBufferedInputStream.read());
		assertEquals(3, unsyncBufferedInputStream.index);

		// Overrun

		int bufferSize = 20;

		unsyncBufferedInputStream = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(_BUFFER), bufferSize);

		assertEquals(-1, unsyncBufferedInputStream.markLimitIndex);

		unsyncBufferedInputStream.mark(markLimitIndex);

		assertEquals(markLimitIndex, unsyncBufferedInputStream.markLimitIndex);

		for (int i = 0; i < bufferSize * 2; i++) {
			assertEquals(i, unsyncBufferedInputStream.read());
		}

		assertEquals(bufferSize, unsyncBufferedInputStream.index);
		assertEquals(
			_SIZE - bufferSize * 2, unsyncBufferedInputStream.available());
		assertEquals(-1, unsyncBufferedInputStream.markLimitIndex);

		try {
			unsyncBufferedInputStream.reset();

			fail();
		}
		catch (IOException ioe) {
		}

		// Shuffle

		unsyncBufferedInputStream = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(_BUFFER));

		assertEquals(0, unsyncBufferedInputStream.read());
		assertEquals(1, unsyncBufferedInputStream.read());
		assertEquals(2, unsyncBufferedInputStream.read());
		assertEquals(3, unsyncBufferedInputStream.index);

		unsyncBufferedInputStream.mark(markLimitIndex);

		assertEquals(0, unsyncBufferedInputStream.index);
		assertEquals(3, unsyncBufferedInputStream.read());
		assertEquals(4, unsyncBufferedInputStream.read());
		assertEquals(5, unsyncBufferedInputStream.read());

		// Reset buffer

		unsyncBufferedInputStream = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(_BUFFER), _SIZE);

		byte[] tempBuffer = new byte[_SIZE / 2];

		assertEquals(_SIZE / 2, unsyncBufferedInputStream.read(tempBuffer));
		assertEquals(_SIZE / 2, unsyncBufferedInputStream.read(tempBuffer));

		assertEquals(_SIZE, unsyncBufferedInputStream.index);
		assertEquals(_SIZE, unsyncBufferedInputStream.firstInvalidIndex);

		unsyncBufferedInputStream.mark(markLimitIndex);

		assertEquals(0, unsyncBufferedInputStream.index);
		assertEquals(0, unsyncBufferedInputStream.firstInvalidIndex);
	}

	public void testMarkSupported() {
		int size = 10;

		UnsyncBufferedInputStream unsyncBufferedInputStream =
			new UnsyncBufferedInputStream(
				new ByteArrayInputStream(new byte[size]));

		assertTrue(unsyncBufferedInputStream.markSupported());
	}

	public void testRead() throws IOException {
		int size = 10;

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			_BUFFER);

		UnsyncBufferedInputStream unsyncBufferedInputStream =
			new UnsyncBufferedInputStream(byteArrayInputStream, size);

		assertEquals(_SIZE, byteArrayInputStream.available());
		assertEquals(_SIZE, unsyncBufferedInputStream.available());
		assertEquals(0, unsyncBufferedInputStream.read());
		assertEquals(_SIZE - size, byteArrayInputStream.available());
		assertEquals(_SIZE - 1, unsyncBufferedInputStream.available());

		for (int i = 1; i < size + 1; i++) {
			assertEquals(i, unsyncBufferedInputStream.read());
		}

		assertEquals(_SIZE - size * 2, byteArrayInputStream.available());
		assertEquals(_SIZE - size - 1, unsyncBufferedInputStream.available());

		for (int i = size + 1; i < _SIZE; i++) {
			assertEquals((byte)i, (byte)unsyncBufferedInputStream.read());
		}

		assertEquals(-1, unsyncBufferedInputStream.read());
	}

	public void testSkip() throws IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			_BUFFER);

		int size = 10;

		UnsyncBufferedInputStream unsyncBufferedInputStream =
			new UnsyncBufferedInputStream(byteArrayInputStream, size);

		// Zero skip

		assertEquals(0, unsyncBufferedInputStream.skip(0));
		assertEquals(_SIZE, byteArrayInputStream.available());
		assertEquals(_SIZE, unsyncBufferedInputStream.available());

		// Negative skip

		assertEquals(0, unsyncBufferedInputStream.skip(-1));
		assertEquals(_SIZE, byteArrayInputStream.available());
		assertEquals(_SIZE, unsyncBufferedInputStream.available());

		// Load data into buffer

		assertEquals(0, unsyncBufferedInputStream.read());
		assertEquals(_SIZE - size, byteArrayInputStream.available());
		assertEquals(_SIZE - 1, unsyncBufferedInputStream.available());

		// In-memory

		assertEquals(size - 1, unsyncBufferedInputStream.skip(size * 2));
		assertEquals(10, unsyncBufferedInputStream.read());
		assertEquals(size - 1, unsyncBufferedInputStream.skip(size * 2));

		// Underlying input stream

		assertEquals(size * 2, unsyncBufferedInputStream.skip(size * 2));
		assertEquals(40, unsyncBufferedInputStream.read());

		// Clear out buffer

		assertEquals(size - 1, unsyncBufferedInputStream.skip(size));

		// Mark

		unsyncBufferedInputStream.mark(size * 2);

		// Load data into buffer for skipping

		assertEquals(size, unsyncBufferedInputStream.skip(size * 2));

		// In-memory

		assertEquals(size / 2, unsyncBufferedInputStream.skip(size / 2));

		unsyncBufferedInputStream.reset();

		assertEquals(50, unsyncBufferedInputStream.read());

		// Clear out buffer

		assertEquals(size * 2 - 1, unsyncBufferedInputStream.skip(size * 2));

		// Mark a large size for EOF

		unsyncBufferedInputStream.mark(_SIZE);

		// Consume all the data

		while (unsyncBufferedInputStream.read() != -1);

		// Skip on EOF

		assertEquals(0, unsyncBufferedInputStream.skip(1));
	}

	private static final int _SIZE = 16 * 1024;

	private static final byte[] _BUFFER = new byte[_SIZE];

	static {
		for (int i = 0; i < _SIZE; i++) {
			_BUFFER[i] = (byte)i;
		}
	}

}