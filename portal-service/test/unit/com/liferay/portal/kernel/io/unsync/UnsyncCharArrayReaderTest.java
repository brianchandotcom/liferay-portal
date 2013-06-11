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

import java.io.IOException;

import java.nio.CharBuffer;

import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncCharArrayReaderTest extends TestCase {

	@Test
	public void testBlockRead() throws IOException {
		UnsyncCharArrayReader unsyncCharArrayReader = new UnsyncCharArrayReader(
			_BUFFER);

		int size = _SIZE * 2 / 3;

		char[] buffer = new char[size];

		int read = unsyncCharArrayReader.read(buffer);

		assertEquals(size, read);

		for (int i = 0; i < read; i++) {
			assertEquals('a' + i, buffer[i]);
		}

		read = unsyncCharArrayReader.read(buffer);

		assertEquals(_SIZE - size, read);

		for (int i = 0; i < read; i++) {
			assertEquals('a' + i + size, buffer[i]);
		}
	}

	@Test
	public void testBufferRead() throws IOException {
		UnsyncCharArrayReader unsyncCharArrayReader = new UnsyncCharArrayReader(
			_BUFFER);

		int size = _SIZE * 2 / 3;

		CharBuffer charBuffer = CharBuffer.allocate(size);

		int read = unsyncCharArrayReader.read(charBuffer);

		assertEquals(size, read);

		for (int i = 0; i < read; i++) {
			assertEquals('a' + i, charBuffer.get(i));
		}

		charBuffer.position(0);

		read = unsyncCharArrayReader.read(charBuffer);

		assertEquals(_SIZE - size, read);

		for (int i = 0; i < read; i++) {
			assertEquals('a' + i + size, charBuffer.get(i));
		}
	}

	@Test
	public void testClose() {
		UnsyncCharArrayReader unsyncCharArrayReader = new UnsyncCharArrayReader(
			_BUFFER);

		unsyncCharArrayReader.close();

		assertTrue(unsyncCharArrayReader.buffer == null);

		try {
			unsyncCharArrayReader.mark(0);

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncCharArrayReader.read();

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncCharArrayReader.read(new char[5]);

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncCharArrayReader.read(new char[5], 1, 2);

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncCharArrayReader.read(CharBuffer.allocate(5));

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncCharArrayReader.ready();

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncCharArrayReader.reset();

			fail();
		}
		catch (IOException ioe) {
		}

		unsyncCharArrayReader.close();
	}

	@Test
	public void testConstructor() {
		UnsyncCharArrayReader unsyncCharArrayReader = new UnsyncCharArrayReader(
			_BUFFER);

		assertEquals(_BUFFER, unsyncCharArrayReader.buffer);
		assertEquals(_SIZE, unsyncCharArrayReader.capacity);
		assertEquals(0, unsyncCharArrayReader.index);
		assertEquals(0, unsyncCharArrayReader.markIndex);

		unsyncCharArrayReader = new UnsyncCharArrayReader(
			_BUFFER, _SIZE / 2, _SIZE / 2);

		assertEquals(_BUFFER, unsyncCharArrayReader.buffer);
		assertEquals(_SIZE, unsyncCharArrayReader.capacity);
		assertEquals(_SIZE / 2, unsyncCharArrayReader.index);
		assertEquals(_SIZE / 2, unsyncCharArrayReader.markIndex);
	}

	@Test
	public void testMarkAndReset() throws IOException {
		UnsyncCharArrayReader unsyncCharArrayReader = new UnsyncCharArrayReader(
			_BUFFER);

		assertEquals('a', unsyncCharArrayReader.read());
		assertEquals('b', unsyncCharArrayReader.read());

		unsyncCharArrayReader.mark(-1);

		assertEquals(2, unsyncCharArrayReader.index);
		assertEquals('c', unsyncCharArrayReader.read());
		assertEquals('d', unsyncCharArrayReader.read());
		assertEquals(4, unsyncCharArrayReader.index);

		unsyncCharArrayReader.reset();

		assertEquals(2, unsyncCharArrayReader.index);
		assertEquals('c', unsyncCharArrayReader.read());
		assertEquals('d', unsyncCharArrayReader.read());
		assertEquals(4, unsyncCharArrayReader.index);
	}

	@Test
	public void testMarkSupported() {
		UnsyncCharArrayReader unsyncCharArrayReader = new UnsyncCharArrayReader(
			_BUFFER);

		assertTrue(unsyncCharArrayReader.markSupported());
	}

	@Test
	public void testRead() throws IOException {
		UnsyncCharArrayReader unsyncCharArrayReader = new UnsyncCharArrayReader(
			_BUFFER);

		for (int i = 0; i < _SIZE; i++) {
			assertEquals('a' + i, unsyncCharArrayReader.read());
		}

		assertEquals(-1, unsyncCharArrayReader.read());
	}

	@Test
	public void testReady() throws IOException {
		UnsyncCharArrayReader unsyncCharArrayReader = new UnsyncCharArrayReader(
			_BUFFER);

		assertTrue(unsyncCharArrayReader.ready());

		unsyncCharArrayReader.read(CharBuffer.allocate(_SIZE));

		assertFalse(unsyncCharArrayReader.ready());
	}

	@Test
	public void testSkip() throws IOException {
		UnsyncCharArrayReader unsyncCharArrayReader = new UnsyncCharArrayReader(
			_BUFFER);

		long size = _SIZE * 2 / 3;

		assertEquals(size, unsyncCharArrayReader.skip(size));
		assertEquals(size, unsyncCharArrayReader.index);
		assertEquals(_SIZE - size, unsyncCharArrayReader.skip(size));
		assertEquals(_SIZE, unsyncCharArrayReader.index);
	}

	private static final char[] _BUFFER =
		new char[UnsyncCharArrayReaderTest._SIZE];

	private static final int _SIZE = 10;

	static {
		for (int i = 0; i < _SIZE; i++) {
			_BUFFER[i] = (char) ('a' + i);
		}
	}

}