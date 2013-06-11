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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncBufferedReaderTest extends TestCase {

	@Test
	public void testBlockRead() throws IOException {
		StringReader stringReader = new StringReader("abcdefghi");

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			stringReader, 5);

		assertEquals(5, unsyncBufferedReader.buffer.length);
		assertTrue(stringReader.ready());
		assertTrue(unsyncBufferedReader.ready());

		char[] buffer = new char[3];

		// Zero length read

		assertEquals(0, unsyncBufferedReader.read(buffer, 0, 0));

		// Negative length read

		assertEquals(0, unsyncBufferedReader.read(buffer, 0, -1));

		// In-memory

		assertEquals('a', unsyncBufferedReader.read());
		assertEquals(1, unsyncBufferedReader.index);
		assertEquals(5, unsyncBufferedReader.firstInvalidIndex);

		int read = unsyncBufferedReader.read(buffer);

		assertEquals(buffer.length, read);
		assertEquals('b', buffer[0]);
		assertEquals('c', buffer[1]);
		assertEquals('d', buffer[2]);
		assertEquals(4, unsyncBufferedReader.index);
		assertEquals(5, unsyncBufferedReader.firstInvalidIndex);

		// Exhaust buffer

		assertEquals('e', unsyncBufferedReader.read());
		assertEquals(5, unsyncBufferedReader.index);
		assertEquals(5, unsyncBufferedReader.firstInvalidIndex);

		// Force reload

		read = unsyncBufferedReader.read(buffer);

		assertEquals(buffer.length, read);

		assertEquals('f', buffer[0]);
		assertEquals('g', buffer[1]);
		assertEquals('h', buffer[2]);

		assertEquals(3, unsyncBufferedReader.index);
		assertEquals(4, unsyncBufferedReader.firstInvalidIndex);

		// Finish

		stringReader = new StringReader(new String(_BUFFER));

		unsyncBufferedReader = new UnsyncBufferedReader(stringReader, _SIZE);

		char[] tempBuffer = new char[_SIZE];

		unsyncBufferedReader.read(tempBuffer);

		// Mark and EOF

		stringReader = new StringReader(new String(_BUFFER));

		unsyncBufferedReader = new UnsyncBufferedReader(stringReader, 5);

		unsyncBufferedReader.mark(_SIZE);

		assertEquals(_SIZE, unsyncBufferedReader.read(tempBuffer));
		assertEquals(-1, unsyncBufferedReader.read(tempBuffer));
	}

	@Test
	public void testClose() throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(""));

		unsyncBufferedReader.close();

		assertNull(unsyncBufferedReader.buffer);
		assertNull(unsyncBufferedReader.reader);

		try {
			unsyncBufferedReader.mark(0);

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedReader.read();

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedReader.read(new char[5]);

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedReader.readLine();

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedReader.ready();

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedReader.reset();

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedReader.skip(0);

			fail();
		}
		catch (IOException ioe) {
		}

		unsyncBufferedReader.close();
	}

	@Test
	public void testConstructor() {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(""));

		assertEquals(8192, unsyncBufferedReader.buffer.length);

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(""), 10);

		assertEquals(10, unsyncBufferedReader.buffer.length);

		try {
			new UnsyncBufferedReader(new StringReader(""), 0);
			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new UnsyncBufferedReader(new StringReader(""), -1);
			fail();
		}
		catch (IllegalArgumentException iae) {
		}
	}

	@Test
	public void testMarkAndReset() throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghi"), 5);

		assertEquals(-1, unsyncBufferedReader.markLimitIndex);

		// Zero marking

		unsyncBufferedReader.mark(0);

		assertEquals(-1, unsyncBufferedReader.markLimitIndex);

		// Negative marking

		try {
			unsyncBufferedReader.mark(-2);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		assertEquals(-1, unsyncBufferedReader.markLimitIndex);

		// Normal

		int markLimit = 3;

		unsyncBufferedReader.mark(markLimit);

		assertEquals(markLimit, unsyncBufferedReader.markLimitIndex);
		assertEquals(0, unsyncBufferedReader.index);
		assertEquals('a', unsyncBufferedReader.read());
		assertEquals('b', unsyncBufferedReader.read());
		assertEquals('c', unsyncBufferedReader.read());
		assertEquals(3, unsyncBufferedReader.index);

		unsyncBufferedReader.reset();

		assertEquals(0, unsyncBufferedReader.index);
		assertEquals('a', unsyncBufferedReader.read());
		assertEquals('b', unsyncBufferedReader.read());
		assertEquals('c', unsyncBufferedReader.read());
		assertEquals(3, unsyncBufferedReader.index);

		// Overrun

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghi"), 5);

		assertEquals(-1, unsyncBufferedReader.markLimitIndex);

		unsyncBufferedReader.mark(markLimit);

		assertEquals(markLimit, unsyncBufferedReader.markLimitIndex);

		assertEquals('a', unsyncBufferedReader.read());
		assertEquals('b', unsyncBufferedReader.read());
		assertEquals('c', unsyncBufferedReader.read());
		assertEquals('d', unsyncBufferedReader.read());
		assertEquals('e', unsyncBufferedReader.read());
		assertEquals('f', unsyncBufferedReader.read());
		assertEquals(1, unsyncBufferedReader.index);
		assertEquals(-1, unsyncBufferedReader.markLimitIndex);

		try {
			unsyncBufferedReader.reset();

			fail();
		}
		catch (IOException ioe) {
		}

		// Shuffle

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghi"), 5);

		assertEquals('a', unsyncBufferedReader.read());
		assertEquals('b', unsyncBufferedReader.read());
		assertEquals('c', unsyncBufferedReader.read());
		assertEquals(3, unsyncBufferedReader.index);

		unsyncBufferedReader.mark(markLimit);

		assertEquals(0, unsyncBufferedReader.index);
		assertEquals('d', unsyncBufferedReader.read());
		assertEquals('e', unsyncBufferedReader.read());
		assertEquals('f', unsyncBufferedReader.read());

		// Reset buffer

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(new String(_BUFFER)), _SIZE);

		char[] tempBuffer = new char[_SIZE / 2];

		assertEquals(_SIZE / 2, unsyncBufferedReader.read(tempBuffer));
		assertEquals(_SIZE / 2, unsyncBufferedReader.read(tempBuffer));

		assertEquals(_SIZE, unsyncBufferedReader.index);
		assertEquals(_SIZE, unsyncBufferedReader.firstInvalidIndex);

		unsyncBufferedReader.mark(markLimit);

		assertEquals(0, unsyncBufferedReader.index);
		assertEquals(0, unsyncBufferedReader.firstInvalidIndex);
	}

	@Test
	public void testMarkSupported() {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghi"), 5);

		assertTrue(unsyncBufferedReader.markSupported());
	}

	@Test
	public void testRead() throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("ab\r\nef"), 3);

		assertEquals(3, unsyncBufferedReader.buffer.length);
		assertEquals(0, unsyncBufferedReader.index);
		assertEquals('a', unsyncBufferedReader.read());
		assertEquals(1, unsyncBufferedReader.index);
		assertEquals('b', unsyncBufferedReader.read());
		assertEquals(2, unsyncBufferedReader.index);
		assertEquals('\r', unsyncBufferedReader.read());
		assertEquals(3, unsyncBufferedReader.index);
		assertEquals('\n', unsyncBufferedReader.read());
		assertEquals(1, unsyncBufferedReader.index);
		assertEquals('e', unsyncBufferedReader.read());
		assertEquals(2, unsyncBufferedReader.index);
		assertEquals('f', unsyncBufferedReader.read());
		assertEquals(3, unsyncBufferedReader.index);
		assertEquals(-1, unsyncBufferedReader.read());
	}

	@Test
	public void testReadLine() throws IOException {

		// With \r

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abc\rde"), 5);

		assertEquals("abc", unsyncBufferedReader.readLine());
		assertEquals(4, unsyncBufferedReader.index);

		// With \n

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abc\nde"), 5);

		assertEquals("abc", unsyncBufferedReader.readLine());
		assertEquals(4, unsyncBufferedReader.index);

		// With \r\n

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abc\r\nde"), 5);

		assertEquals("abc", unsyncBufferedReader.readLine());
		assertEquals(5, unsyncBufferedReader.index);

		// Without \r or \n

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abc"), 5);

		assertEquals("abc", unsyncBufferedReader.readLine());
		assertEquals(0, unsyncBufferedReader.index);

		// Empty

		assertNull(unsyncBufferedReader.readLine());

		// Load multiple times for one line

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghijklmn\r"), 5);

		assertEquals("abcdefghijklmn", unsyncBufferedReader.readLine());
		assertEquals(5, unsyncBufferedReader.index);
	}

	@Test
	public void testReady() throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(""));

		assertTrue(unsyncBufferedReader.ready());

		unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(new ByteArrayInputStream(new byte[0])));

		assertFalse(unsyncBufferedReader.ready());
	}

	@Test
	public void testSkip() throws IOException {
		int size = 10;

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(new String(_BUFFER)), size);

		// Zero skip

		assertEquals(0, unsyncBufferedReader.skip(0));

		// Negetive skip

		try {
			unsyncBufferedReader.skip(-1);
		}
		catch (IllegalArgumentException iae) {
		}

		// Load data into buffer

		assertEquals('a', unsyncBufferedReader.read());
		assertEquals(1, unsyncBufferedReader.index);
		assertEquals(size, unsyncBufferedReader.firstInvalidIndex);

		// In-memory

		assertEquals(size - 1, unsyncBufferedReader.skip(size * 2));
		assertEquals('a' + 10, unsyncBufferedReader.read());
		assertEquals(size - 1, unsyncBufferedReader.skip(size * 2));

		// Underlying reader

		assertEquals(size * 2, unsyncBufferedReader.skip(size * 2));
		assertEquals('a' + (40 % 26), unsyncBufferedReader.read());

		// Clear out buffer

		assertEquals(size - 1, unsyncBufferedReader.skip(size));

		// Mark

		unsyncBufferedReader.mark(size * 2);

		// Load data into buffer for skipping

		assertEquals(size, unsyncBufferedReader.skip(size * 2));

		// In-memory

		assertEquals(size / 2, unsyncBufferedReader.skip(size / 2));

		unsyncBufferedReader.reset();

		assertEquals('a' + (50 % 26), unsyncBufferedReader.read());

		// Clear out buffer

		assertEquals(size * 2 - 1, unsyncBufferedReader.skip(size * 2));

		// Mark a large size for EOF

		unsyncBufferedReader.mark(_SIZE);

		// Consume all the data

		while (unsyncBufferedReader.read() != -1);

		// Skip on EOF

		assertEquals(0, unsyncBufferedReader.skip(1));
	}

	private static final char[] _BUFFER =
		new char[UnsyncBufferedReaderTest._SIZE];

	private static final int _SIZE = 16 * 1024;

	static {
		for (int i = 0; i < _SIZE; i++) {
			_BUFFER[i] = (char)(i % 26 + 'a');
		}
	}

}