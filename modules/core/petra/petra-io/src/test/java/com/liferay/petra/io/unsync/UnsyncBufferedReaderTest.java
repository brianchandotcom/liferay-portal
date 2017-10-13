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

package com.liferay.portal.kernel.io.unsync;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncBufferedReaderTest {

	@Test
	public void testBlockRead() throws IOException {
		StringReader stringReader = new StringReader("abcdefghi");

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			stringReader, 5);

		Assert.assertEquals(5, unsyncBufferedReader.buffer.length);

		Assert.assertTrue(stringReader.ready());
		Assert.assertTrue(unsyncBufferedReader.ready());

		char[] buffer = new char[3];

		// Zero length read

		Assert.assertEquals(0, unsyncBufferedReader.read(buffer, 0, 0));

		// Negative length read

		Assert.assertEquals(0, unsyncBufferedReader.read(buffer, 0, -1));

		// In-memory

		Assert.assertEquals('a', unsyncBufferedReader.read());
		Assert.assertEquals(1, unsyncBufferedReader.index);
		Assert.assertEquals(5, unsyncBufferedReader.firstInvalidIndex);

		int read = unsyncBufferedReader.read(buffer);

		Assert.assertEquals(buffer.length, read);

		Assert.assertEquals('b', buffer[0]);
		Assert.assertEquals('c', buffer[1]);
		Assert.assertEquals('d', buffer[2]);
		Assert.assertEquals(4, unsyncBufferedReader.index);
		Assert.assertEquals(5, unsyncBufferedReader.firstInvalidIndex);

		// Exhaust buffer

		Assert.assertEquals('e', unsyncBufferedReader.read());
		Assert.assertEquals(5, unsyncBufferedReader.index);
		Assert.assertEquals(5, unsyncBufferedReader.firstInvalidIndex);

		// Force reload

		read = unsyncBufferedReader.read(buffer);

		Assert.assertEquals(buffer.length, read);

		Assert.assertEquals('f', buffer[0]);
		Assert.assertEquals('g', buffer[1]);
		Assert.assertEquals('h', buffer[2]);

		Assert.assertEquals(3, unsyncBufferedReader.index);
		Assert.assertEquals(4, unsyncBufferedReader.firstInvalidIndex);

		// Finish

		stringReader = new StringReader(new String(_BUFFER));

		unsyncBufferedReader = new UnsyncBufferedReader(stringReader, _SIZE);

		char[] tempBuffer = new char[_SIZE];

		unsyncBufferedReader.read(tempBuffer);

		// Mark and EOF

		stringReader = new StringReader(new String(_BUFFER));

		unsyncBufferedReader = new UnsyncBufferedReader(stringReader, 5);

		unsyncBufferedReader.mark(_SIZE);

		Assert.assertEquals(_SIZE, unsyncBufferedReader.read(tempBuffer));
		Assert.assertEquals(-1, unsyncBufferedReader.read(tempBuffer));
	}

	@Test
	public void testClose() throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(""));

		unsyncBufferedReader.close();

		Assert.assertNull(unsyncBufferedReader.buffer);
		Assert.assertNull(unsyncBufferedReader.reader);

		try {
			unsyncBufferedReader.mark(0);

			Assert.fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedReader.read();

			Assert.fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedReader.read(new char[5]);

			Assert.fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedReader.readLine();

			Assert.fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedReader.ready();

			Assert.fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedReader.reset();

			Assert.fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedReader.skip(0);

			Assert.fail();
		}
		catch (IOException ioe) {
		}

		unsyncBufferedReader.close();
	}

	@Test
	public void testConstructor() {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(""));

		Assert.assertEquals(8192, unsyncBufferedReader.buffer.length);

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(""), 10);

		Assert.assertEquals(10, unsyncBufferedReader.buffer.length);

		try {
			new UnsyncBufferedReader(new StringReader(""), 0);
			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new UnsyncBufferedReader(new StringReader(""), -1);
			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}
	}

	@Test
	public void testMarkAndReset() throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghi"), 5);

		Assert.assertEquals(-1, unsyncBufferedReader.markLimitIndex);

		// Zero marking

		unsyncBufferedReader.mark(0);

		Assert.assertEquals(-1, unsyncBufferedReader.markLimitIndex);

		// Negative marking

		try {
			unsyncBufferedReader.mark(-2);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		Assert.assertEquals(-1, unsyncBufferedReader.markLimitIndex);

		// Normal

		int markLimit = 3;

		unsyncBufferedReader.mark(markLimit);

		Assert.assertEquals(markLimit, unsyncBufferedReader.markLimitIndex);

		Assert.assertEquals(0, unsyncBufferedReader.index);
		Assert.assertEquals('a', unsyncBufferedReader.read());
		Assert.assertEquals('b', unsyncBufferedReader.read());
		Assert.assertEquals('c', unsyncBufferedReader.read());
		Assert.assertEquals(3, unsyncBufferedReader.index);

		unsyncBufferedReader.reset();

		Assert.assertEquals(0, unsyncBufferedReader.index);
		Assert.assertEquals('a', unsyncBufferedReader.read());
		Assert.assertEquals('b', unsyncBufferedReader.read());
		Assert.assertEquals('c', unsyncBufferedReader.read());
		Assert.assertEquals(3, unsyncBufferedReader.index);

		// Overrun

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghi"), 5);

		Assert.assertEquals(-1, unsyncBufferedReader.markLimitIndex);

		unsyncBufferedReader.mark(markLimit);

		Assert.assertEquals(markLimit, unsyncBufferedReader.markLimitIndex);

		Assert.assertEquals('a', unsyncBufferedReader.read());
		Assert.assertEquals('b', unsyncBufferedReader.read());
		Assert.assertEquals('c', unsyncBufferedReader.read());
		Assert.assertEquals('d', unsyncBufferedReader.read());
		Assert.assertEquals('e', unsyncBufferedReader.read());
		Assert.assertEquals('f', unsyncBufferedReader.read());
		Assert.assertEquals(1, unsyncBufferedReader.index);
		Assert.assertEquals(-1, unsyncBufferedReader.markLimitIndex);

		try {
			unsyncBufferedReader.reset();

			Assert.fail();
		}
		catch (IOException ioe) {
		}

		// Shuffle

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghi"), 5);

		Assert.assertEquals('a', unsyncBufferedReader.read());
		Assert.assertEquals('b', unsyncBufferedReader.read());
		Assert.assertEquals('c', unsyncBufferedReader.read());
		Assert.assertEquals(3, unsyncBufferedReader.index);

		unsyncBufferedReader.mark(markLimit);

		Assert.assertEquals(0, unsyncBufferedReader.index);
		Assert.assertEquals('d', unsyncBufferedReader.read());
		Assert.assertEquals('e', unsyncBufferedReader.read());
		Assert.assertEquals('f', unsyncBufferedReader.read());

		// Reset buffer

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(new String(_BUFFER)), _SIZE);

		char[] tempBuffer = new char[_SIZE / 2];

		Assert.assertEquals(_SIZE / 2, unsyncBufferedReader.read(tempBuffer));
		Assert.assertEquals(_SIZE / 2, unsyncBufferedReader.read(tempBuffer));

		Assert.assertEquals(_SIZE, unsyncBufferedReader.index);
		Assert.assertEquals(_SIZE, unsyncBufferedReader.firstInvalidIndex);

		unsyncBufferedReader.mark(markLimit);

		Assert.assertEquals(0, unsyncBufferedReader.index);
		Assert.assertEquals(0, unsyncBufferedReader.firstInvalidIndex);
	}

	@Test
	public void testMarkSupported() {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghi"), 5);

		Assert.assertTrue(unsyncBufferedReader.markSupported());
	}

	@Test
	public void testRead() throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("ab\r\nef"), 3);

		Assert.assertEquals(3, unsyncBufferedReader.buffer.length);
		Assert.assertEquals(0, unsyncBufferedReader.index);
		Assert.assertEquals('a', unsyncBufferedReader.read());
		Assert.assertEquals(1, unsyncBufferedReader.index);
		Assert.assertEquals('b', unsyncBufferedReader.read());
		Assert.assertEquals(2, unsyncBufferedReader.index);
		Assert.assertEquals('\r', unsyncBufferedReader.read());
		Assert.assertEquals(3, unsyncBufferedReader.index);
		Assert.assertEquals('\n', unsyncBufferedReader.read());
		Assert.assertEquals(1, unsyncBufferedReader.index);
		Assert.assertEquals('e', unsyncBufferedReader.read());
		Assert.assertEquals(2, unsyncBufferedReader.index);
		Assert.assertEquals('f', unsyncBufferedReader.read());
		Assert.assertEquals(3, unsyncBufferedReader.index);
		Assert.assertEquals(-1, unsyncBufferedReader.read());
	}

	@Test
	public void testReadLine() throws IOException {

		// With \r

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abc\rde"), 5);

		Assert.assertEquals("abc", unsyncBufferedReader.readLine());
		Assert.assertEquals(4, unsyncBufferedReader.index);

		// With \n

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abc\nde"), 5);

		Assert.assertEquals("abc", unsyncBufferedReader.readLine());
		Assert.assertEquals(4, unsyncBufferedReader.index);

		// With \r\n

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abc\r\nde"), 5);

		Assert.assertEquals("abc", unsyncBufferedReader.readLine());
		Assert.assertEquals(5, unsyncBufferedReader.index);

		// Without \r or \n

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abc"), 5);

		Assert.assertEquals("abc", unsyncBufferedReader.readLine());
		Assert.assertEquals(0, unsyncBufferedReader.index);

		// Empty

		Assert.assertNull(unsyncBufferedReader.readLine());

		// Load multiple times for one line

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghijklmn\r"), 5);

		Assert.assertEquals("abcdefghijklmn", unsyncBufferedReader.readLine());
		Assert.assertEquals(5, unsyncBufferedReader.index);
	}

	@Test
	public void testReady() throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(""));

		Assert.assertTrue(unsyncBufferedReader.ready());

		unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(new ByteArrayInputStream(new byte[0])));

		Assert.assertFalse(unsyncBufferedReader.ready());
	}

	@Test
	public void testSkip() throws IOException {
		int size = 10;

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(new String(_BUFFER)), size);

		// Zero skip

		Assert.assertEquals(0, unsyncBufferedReader.skip(0));

		// Negetive skip

		try {
			unsyncBufferedReader.skip(-1);
		}
		catch (IllegalArgumentException iae) {
		}

		// Load data into buffer

		Assert.assertEquals('a', unsyncBufferedReader.read());
		Assert.assertEquals(1, unsyncBufferedReader.index);
		Assert.assertEquals(size, unsyncBufferedReader.firstInvalidIndex);

		// In-memory

		Assert.assertEquals(size - 1, unsyncBufferedReader.skip(size * 2));
		Assert.assertEquals('a' + 10, unsyncBufferedReader.read());
		Assert.assertEquals(size - 1, unsyncBufferedReader.skip(size * 2));

		// Underlying reader

		Assert.assertEquals(size * 2, unsyncBufferedReader.skip(size * 2));
		Assert.assertEquals('a' + (40 % 26), unsyncBufferedReader.read());

		// Clear out buffer

		Assert.assertEquals(size - 1, unsyncBufferedReader.skip(size));

		// Mark

		unsyncBufferedReader.mark(size * 2);

		// Load data into buffer for skipping

		Assert.assertEquals(size, unsyncBufferedReader.skip(size * 2));

		// In-memory

		Assert.assertEquals(size / 2, unsyncBufferedReader.skip(size / 2));

		unsyncBufferedReader.reset();

		Assert.assertEquals('a' + (50 % 26), unsyncBufferedReader.read());

		// Clear out buffer

		Assert.assertEquals(size * 2 - 1, unsyncBufferedReader.skip(size * 2));

		// Mark a large size for EOF

		unsyncBufferedReader.mark(_SIZE);

		// Consume all the data

		while (unsyncBufferedReader.read() != -1);

		// Skip on EOF

		Assert.assertEquals(0, unsyncBufferedReader.skip(1));
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