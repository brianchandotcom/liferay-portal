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

package com.liferay.petra.io.unsync;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncBufferedReaderTest extends BaseReaderTestCase {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(BoundaryCheckerUtil.class);
			}

		};

	@Override
	@Test
	public void testBlockRead() throws Exception {
		super.testBlockRead();

		StringReader stringReader = new StringReader("abcdefghi");

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			stringReader, 5);

		char[] buffer = (char[])_bufferField.get(unsyncBufferedReader);

		Assert.assertEquals(Arrays.toString(buffer), 5, buffer.length);

		Assert.assertTrue(stringReader.ready());
		Assert.assertTrue(unsyncBufferedReader.ready());

		buffer = new char[3];

		// Zero length read

		Assert.assertEquals(0, unsyncBufferedReader.read(buffer, 0, 0));

		// In-memory

		Assert.assertEquals('a', unsyncBufferedReader.read());
		Assert.assertEquals(1, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals(
			5, _firstInvalidIndexField.getInt(unsyncBufferedReader));

		int read = unsyncBufferedReader.read(buffer);

		Assert.assertEquals(buffer.length, read);

		Assert.assertEquals('b', buffer[0]);
		Assert.assertEquals('c', buffer[1]);
		Assert.assertEquals('d', buffer[2]);
		Assert.assertEquals(4, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals(
			5, _firstInvalidIndexField.getInt(unsyncBufferedReader));

		// Exhaust buffer

		Assert.assertEquals('e', unsyncBufferedReader.read());
		Assert.assertEquals(5, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals(
			5, _firstInvalidIndexField.getInt(unsyncBufferedReader));

		// Force reload

		read = unsyncBufferedReader.read(buffer);

		Assert.assertEquals(buffer.length, read);

		Assert.assertEquals('f', buffer[0]);
		Assert.assertEquals('g', buffer[1]);
		Assert.assertEquals('h', buffer[2]);

		Assert.assertEquals(3, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals(
			4, _firstInvalidIndexField.getInt(unsyncBufferedReader));

		// Finish

		stringReader = new StringReader(new String(_BUFFER));

		unsyncBufferedReader = new UnsyncBufferedReader(stringReader, _SIZE);

		char[] tempBuffer = new char[_SIZE];

		Assert.assertEquals(_SIZE, unsyncBufferedReader.read(tempBuffer));

		// Mark and EOF

		stringReader = new StringReader(new String(_BUFFER));

		unsyncBufferedReader = new UnsyncBufferedReader(stringReader, 5);

		unsyncBufferedReader.mark(_SIZE);

		Assert.assertEquals(_SIZE, unsyncBufferedReader.read(tempBuffer));
		Assert.assertEquals(-1, unsyncBufferedReader.read(tempBuffer));
	}

	@Test
	public void testClose() throws Exception {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(""));

		unsyncBufferedReader.close();

		Assert.assertNull(_bufferField.get(unsyncBufferedReader));
		Assert.assertNull(_readerField.get(unsyncBufferedReader));

		try {
			unsyncBufferedReader.readLine();

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals("Reader is null", ioe.getMessage());
		}

		testClose(unsyncBufferedReader, "Reader is null");
	}

	@Test
	public void testConstructor() throws Exception {
		new BoundaryCheckerUtil();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(""));

		char[] buffer = (char[])_bufferField.get(unsyncBufferedReader);

		Assert.assertEquals(Arrays.toString(buffer), 8192, buffer.length);

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(""), 10);

		buffer = (char[])_bufferField.get(unsyncBufferedReader);

		Assert.assertEquals(Arrays.toString(buffer), 10, buffer.length);

		try {
			new UnsyncBufferedReader(new StringReader(""), 0);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Size is less than 1", iae.getMessage());
		}

		try {
			new UnsyncBufferedReader(new StringReader(""), -1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Size is less than 1", iae.getMessage());
		}
	}

	@Override
	@Test
	public void testMarkAndReset() throws Exception {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghi"), 5);

		Assert.assertEquals(
			-1, _markLimitIndexField.getInt(unsyncBufferedReader));

		// Zero marking

		unsyncBufferedReader.mark(0);

		Assert.assertEquals(
			-1, _markLimitIndexField.getInt(unsyncBufferedReader));

		// Negative marking

		try {
			unsyncBufferedReader.mark(-2);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Mark limit is less than 0", iae.getMessage());
		}

		Assert.assertEquals(
			-1, _markLimitIndexField.getInt(unsyncBufferedReader));

		// Normal

		int markLimit = 3;

		unsyncBufferedReader.mark(markLimit);

		Assert.assertEquals(
			markLimit, _markLimitIndexField.getInt(unsyncBufferedReader));

		Assert.assertEquals(0, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals('a', unsyncBufferedReader.read());
		Assert.assertEquals('b', unsyncBufferedReader.read());
		Assert.assertEquals('c', unsyncBufferedReader.read());
		Assert.assertEquals(3, _indexField.getInt(unsyncBufferedReader));

		unsyncBufferedReader.reset();

		Assert.assertEquals(0, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals('a', unsyncBufferedReader.read());
		Assert.assertEquals('b', unsyncBufferedReader.read());
		Assert.assertEquals('c', unsyncBufferedReader.read());
		Assert.assertEquals(3, _indexField.getInt(unsyncBufferedReader));

		// Overrun

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghi"), 5);

		Assert.assertEquals(
			-1, _markLimitIndexField.getInt(unsyncBufferedReader));

		unsyncBufferedReader.mark(markLimit);

		Assert.assertEquals(
			markLimit, _markLimitIndexField.getInt(unsyncBufferedReader));

		Assert.assertEquals('a', unsyncBufferedReader.read());
		Assert.assertEquals('b', unsyncBufferedReader.read());
		Assert.assertEquals('c', unsyncBufferedReader.read());
		Assert.assertEquals('d', unsyncBufferedReader.read());
		Assert.assertEquals('e', unsyncBufferedReader.read());
		Assert.assertEquals('f', unsyncBufferedReader.read());
		Assert.assertEquals(1, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals(
			-1, _markLimitIndexField.getInt(unsyncBufferedReader));

		try {
			unsyncBufferedReader.reset();

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals("Resetting to invalid mark", ioe.getMessage());
		}

		// Shuffle

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghi"), 5);

		Assert.assertEquals('a', unsyncBufferedReader.read());
		Assert.assertEquals('b', unsyncBufferedReader.read());
		Assert.assertEquals('c', unsyncBufferedReader.read());
		Assert.assertEquals(3, _indexField.getInt(unsyncBufferedReader));

		unsyncBufferedReader.mark(markLimit);

		Assert.assertEquals(0, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals('d', unsyncBufferedReader.read());
		Assert.assertEquals('e', unsyncBufferedReader.read());
		Assert.assertEquals('f', unsyncBufferedReader.read());

		// Reset buffer

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(new String(_BUFFER)), _SIZE);

		char[] tempBuffer = new char[_SIZE / 2];

		Assert.assertEquals(_SIZE / 2, unsyncBufferedReader.read(tempBuffer));
		Assert.assertEquals(_SIZE / 2, unsyncBufferedReader.read(tempBuffer));

		Assert.assertEquals(_SIZE, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals(
			_SIZE, _firstInvalidIndexField.getInt(unsyncBufferedReader));

		unsyncBufferedReader.mark(markLimit);

		Assert.assertEquals(0, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals(
			0, _firstInvalidIndexField.getInt(unsyncBufferedReader));

		// Read line without buffer

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghi"), 3);

		Assert.assertEquals('a', unsyncBufferedReader.read());

		unsyncBufferedReader.mark(3);

		Assert.assertEquals("bcdefghi", unsyncBufferedReader.readLine());
	}

	@Override
	@Test
	public void testRead() throws Exception {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("ab\r\nef"), 3);

		char[] buffer = (char[])_bufferField.get(unsyncBufferedReader);

		Assert.assertEquals(Arrays.toString(buffer), 3, buffer.length);

		Assert.assertEquals(0, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals('a', unsyncBufferedReader.read());
		Assert.assertEquals(1, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals('b', unsyncBufferedReader.read());
		Assert.assertEquals(2, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals('\r', unsyncBufferedReader.read());
		Assert.assertEquals(3, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals('\n', unsyncBufferedReader.read());
		Assert.assertEquals(1, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals('e', unsyncBufferedReader.read());
		Assert.assertEquals(2, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals('f', unsyncBufferedReader.read());
		Assert.assertEquals(3, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals(-1, unsyncBufferedReader.read());
	}

	@Test
	public void testReadLine() throws Exception {

		// With \r

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abc\rde"), 5);

		Assert.assertEquals("abc", unsyncBufferedReader.readLine());
		Assert.assertEquals(4, _indexField.getInt(unsyncBufferedReader));

		// With \n

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abc\nde"), 5);

		Assert.assertEquals("abc", unsyncBufferedReader.readLine());
		Assert.assertEquals(4, _indexField.getInt(unsyncBufferedReader));

		// With \r\n

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abc\r\nde"), 5);

		Assert.assertEquals("abc", unsyncBufferedReader.readLine());
		Assert.assertEquals(5, _indexField.getInt(unsyncBufferedReader));

		// Without \r or \n

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abc"), 5);

		Assert.assertEquals("abc", unsyncBufferedReader.readLine());
		Assert.assertEquals(0, _indexField.getInt(unsyncBufferedReader));

		// Empty

		Assert.assertNull(unsyncBufferedReader.readLine());

		// Load multiple times for one line

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghijklmn\r"), 5);

		Assert.assertEquals("abcdefghijklmn", unsyncBufferedReader.readLine());
		Assert.assertEquals(5, _indexField.getInt(unsyncBufferedReader));

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghijklmn\r"), 5);

		Assert.assertEquals('a', unsyncBufferedReader.read());
		Assert.assertEquals('b', unsyncBufferedReader.read());

		unsyncBufferedReader.mark(1);
	}

	@Override
	@Test
	public void testReady() throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(""));

		Assert.assertTrue(unsyncBufferedReader.ready());

		unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(new ByteArrayInputStream(new byte[0])));

		Assert.assertFalse(unsyncBufferedReader.ready());

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcd") {

				@Override
				public int read(char[] chars, int offset, int length)
					throws IOException {

					_ready = false;

					return super.read(chars, offset, length);
				}

				@Override
				public boolean ready() {
					return _ready;
				}

				private boolean _ready = true;

			});

		char[] chars = new char[2];

		Assert.assertEquals(2, unsyncBufferedReader.read(chars));

		Assert.assertEquals("ab", new String(chars));

		unsyncBufferedReader.mark(2);

		Assert.assertTrue(unsyncBufferedReader.ready());
	}

	@Override
	@Test
	public void testSkip() throws Exception {
		int size = 10;

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(new String(_BUFFER)), size);

		// Zero skip

		Assert.assertEquals(0, unsyncBufferedReader.skip(0));

		// Negetive skip

		try {
			unsyncBufferedReader.skip(-1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Skip is less than 0", iae.getMessage());
		}

		// Load data into buffer

		Assert.assertEquals('a', unsyncBufferedReader.read());
		Assert.assertEquals(1, _indexField.getInt(unsyncBufferedReader));
		Assert.assertEquals(
			size, _firstInvalidIndexField.getInt(unsyncBufferedReader));

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

	@Override
	protected Reader getReader(String s) {
		return new UnsyncBufferedReader(new StringReader(s));
	}

	private static final char[] _BUFFER =
		new char[UnsyncBufferedReaderTest._SIZE];

	private static final int _SIZE = 16 * 1024;

	private static final Field _bufferField = ReflectionTestUtil.getField(
		UnsyncBufferedReader.class, "_buffer");
	private static final Field _firstInvalidIndexField =
		ReflectionTestUtil.getField(
			UnsyncBufferedReader.class, "_firstInvalidIndex");
	private static final Field _indexField = ReflectionTestUtil.getField(
		UnsyncBufferedReader.class, "_index");
	private static final Field _markLimitIndexField =
		ReflectionTestUtil.getField(
			UnsyncBufferedReader.class, "_markLimitIndex");
	private static final Field _readerField = ReflectionTestUtil.getField(
		UnsyncBufferedReader.class, "_reader");

	static {
		for (int i = 0; i < _SIZE; i++) {
			_BUFFER[i] = (char)(i % 26 + 'a');
		}
	}

}