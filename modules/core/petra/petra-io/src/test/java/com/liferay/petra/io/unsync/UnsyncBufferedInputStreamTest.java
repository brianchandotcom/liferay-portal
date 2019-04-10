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
import java.io.InputStream;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncBufferedInputStreamTest extends BaseInputStreamTestCase {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testBlockRead() throws IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			_BUFFER);

		int size = 10;

		UnsyncBufferedInputStream unsyncBufferedInputStream =
			new UnsyncBufferedInputStream(byteArrayInputStream, size);

		Assert.assertEquals(_SIZE, byteArrayInputStream.available());
		Assert.assertEquals(_SIZE, unsyncBufferedInputStream.available());

		byte[] buffer = new byte[5];

		// Zero length read

		Assert.assertEquals(0, unsyncBufferedInputStream.read(buffer, 0, 0));

		// In-memory

		Assert.assertEquals(0, unsyncBufferedInputStream.read());
		Assert.assertEquals(_SIZE - size, byteArrayInputStream.available());
		Assert.assertEquals(_SIZE - 1, unsyncBufferedInputStream.available());

		int read = unsyncBufferedInputStream.read(buffer);

		Assert.assertEquals(buffer.length, read);

		for (int i = 1; i < (buffer.length + 1); i++) {
			Assert.assertEquals(i, buffer[i - 1]);
		}

		// Exhaust buffer

		Assert.assertEquals(6, unsyncBufferedInputStream.read());
		Assert.assertEquals(7, unsyncBufferedInputStream.read());
		Assert.assertEquals(8, unsyncBufferedInputStream.read());
		Assert.assertEquals(9, unsyncBufferedInputStream.read());

		// Force reload

		read = unsyncBufferedInputStream.read(buffer);

		Assert.assertEquals(buffer.length, read);

		for (int i = 10; i < (buffer.length + 10); i++) {
			Assert.assertEquals(i, buffer[i - 10]);
		}

		Assert.assertEquals(_SIZE - size * 2, byteArrayInputStream.available());
		Assert.assertEquals(_SIZE - 15, unsyncBufferedInputStream.available());

		// Fill the buffer

		buffer = new byte[10];

		read = unsyncBufferedInputStream.read(buffer);

		Assert.assertEquals(buffer.length, read);

		for (int i = 15; i < (buffer.length + 15); i++) {
			Assert.assertEquals(i, buffer[i - 15]);
		}

		Assert.assertEquals(_SIZE - size * 3, byteArrayInputStream.available());
		Assert.assertEquals(_SIZE - 25, unsyncBufferedInputStream.available());

		// Leave 5 bytes

		for (int i = 25; i < (_SIZE - 5); i++) {
			Assert.assertEquals(i & 0xff, unsyncBufferedInputStream.read());
		}

		Assert.assertEquals(_SIZE % 5, byteArrayInputStream.available());
		Assert.assertEquals(5, unsyncBufferedInputStream.available());

		// Finish

		read = unsyncBufferedInputStream.read(buffer);

		Assert.assertEquals(5, read);
		Assert.assertEquals(-1, unsyncBufferedInputStream.read(buffer));

		// Mark and EOF

		byteArrayInputStream = new ByteArrayInputStream(_BUFFER);

		unsyncBufferedInputStream = new UnsyncBufferedInputStream(
			byteArrayInputStream, size);

		unsyncBufferedInputStream.mark(_SIZE);

		byte[] tempBuffer = new byte[_SIZE];

		Assert.assertEquals(_SIZE, unsyncBufferedInputStream.read(tempBuffer));
		Assert.assertEquals(-1, unsyncBufferedInputStream.read(tempBuffer));

		// Second read is blocked after first read

		unsyncBufferedInputStream = new UnsyncBufferedInputStream(
			new InputStream() {

				@Override
				public int available() {
					return 1;
				}

				@Override
				public int read() {
					if (_firstRead) {
						_firstRead = false;

						return 2;
					}

					return -1;
				}

				private boolean _firstRead = true;

			});

		byte[] bytes = new byte[2];

		Assert.assertEquals(1, unsyncBufferedInputStream.read(bytes));
		Assert.assertEquals(2, bytes[0]);
		Assert.assertEquals(0, bytes[1]);
	}

	@Test
	public void testClose() throws Exception {
		int size = 10;

		UnsyncBufferedInputStream unsyncBufferedInputStream =
			new UnsyncBufferedInputStream(
				new ByteArrayInputStream(new byte[size]));

		unsyncBufferedInputStream.close();

		Assert.assertNull(unsyncBufferedInputStream.inputStream);
		Assert.assertNull(_bufferField.get(unsyncBufferedInputStream));

		testClose(unsyncBufferedInputStream, "Input stream is null");
	}

	@Test
	public void testConstructor() throws IOException {
		int size = 10;

		UnsyncBufferedInputStream unsyncBufferedInputStream =
			new UnsyncBufferedInputStream(
				new ByteArrayInputStream(new byte[size]));

		Assert.assertEquals(size, unsyncBufferedInputStream.available());

		unsyncBufferedInputStream = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(new byte[size]), _SIZE);

		Assert.assertEquals(size, unsyncBufferedInputStream.available());

		try {
			new UnsyncBufferedInputStream(
				new ByteArrayInputStream(new byte[size]), 0);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Size is less than 1", iae.getMessage());
		}

		try {
			new UnsyncBufferedInputStream(
				new ByteArrayInputStream(new byte[size]), -1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Size is less than 1", iae.getMessage());
		}
	}

	@Test
	public void testMarkAndReset() throws Exception {
		UnsyncBufferedInputStream unsyncBufferedInputStream =
			new UnsyncBufferedInputStream(new ByteArrayInputStream(_BUFFER));

		Assert.assertEquals(
			-1, _markLimitIndexField.getInt(unsyncBufferedInputStream));

		// Zero marking

		unsyncBufferedInputStream.mark(0);

		Assert.assertEquals(
			-1, _markLimitIndexField.getInt(unsyncBufferedInputStream));

		// Negative marking

		unsyncBufferedInputStream.mark(-2);

		Assert.assertEquals(
			-1, _markLimitIndexField.getInt(unsyncBufferedInputStream));

		// Normal

		int markLimitIndex = 10;

		unsyncBufferedInputStream.mark(markLimitIndex);

		Assert.assertEquals(
			markLimitIndex,
			_markLimitIndexField.getInt(unsyncBufferedInputStream));

		Assert.assertEquals(_SIZE, unsyncBufferedInputStream.available());
		Assert.assertEquals(0, unsyncBufferedInputStream.read());
		Assert.assertEquals(1, unsyncBufferedInputStream.read());
		Assert.assertEquals(2, unsyncBufferedInputStream.read());
		Assert.assertEquals(3, _indexField.getInt(unsyncBufferedInputStream));

		unsyncBufferedInputStream.reset();

		Assert.assertEquals(_SIZE, unsyncBufferedInputStream.available());
		Assert.assertEquals(0, unsyncBufferedInputStream.read());
		Assert.assertEquals(1, unsyncBufferedInputStream.read());
		Assert.assertEquals(2, unsyncBufferedInputStream.read());
		Assert.assertEquals(3, _indexField.getInt(unsyncBufferedInputStream));

		// Overrun

		int bufferSize = 20;

		unsyncBufferedInputStream = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(_BUFFER), bufferSize);

		Assert.assertEquals(
			-1, _markLimitIndexField.getInt(unsyncBufferedInputStream));

		unsyncBufferedInputStream.mark(markLimitIndex);

		Assert.assertEquals(
			markLimitIndex,
			_markLimitIndexField.getInt(unsyncBufferedInputStream));

		for (int i = 0; i < (bufferSize * 2); i++) {
			Assert.assertEquals(i, unsyncBufferedInputStream.read());
		}

		Assert.assertEquals(
			bufferSize, _indexField.getInt(unsyncBufferedInputStream));

		Assert.assertEquals(
			_SIZE - bufferSize * 2, unsyncBufferedInputStream.available());
		Assert.assertEquals(
			-1, _markLimitIndexField.getInt(unsyncBufferedInputStream));

		try {
			unsyncBufferedInputStream.reset();

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals("Resetting to invalid mark", ioe.getMessage());
		}

		// Shuffle

		unsyncBufferedInputStream = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(_BUFFER));

		Assert.assertEquals(0, unsyncBufferedInputStream.read());
		Assert.assertEquals(1, unsyncBufferedInputStream.read());
		Assert.assertEquals(2, unsyncBufferedInputStream.read());
		Assert.assertEquals(3, _indexField.getInt(unsyncBufferedInputStream));

		unsyncBufferedInputStream.mark(markLimitIndex);

		Assert.assertEquals(0, _indexField.getInt(unsyncBufferedInputStream));
		Assert.assertEquals(3, unsyncBufferedInputStream.read());
		Assert.assertEquals(4, unsyncBufferedInputStream.read());
		Assert.assertEquals(5, unsyncBufferedInputStream.read());

		// Reset buffer

		unsyncBufferedInputStream = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(_BUFFER), _SIZE);

		byte[] tempBuffer = new byte[_SIZE / 2];

		Assert.assertEquals(
			_SIZE / 2, unsyncBufferedInputStream.read(tempBuffer));
		Assert.assertEquals(
			_SIZE / 2, unsyncBufferedInputStream.read(tempBuffer));

		Assert.assertEquals(
			_SIZE, _indexField.getInt(unsyncBufferedInputStream));
		Assert.assertEquals(
			_SIZE, _firstInvalidIndexField.getInt(unsyncBufferedInputStream));

		unsyncBufferedInputStream.mark(markLimitIndex);

		Assert.assertEquals(0, _indexField.getInt(unsyncBufferedInputStream));
		Assert.assertEquals(
			0, _firstInvalidIndexField.getInt(unsyncBufferedInputStream));
	}

	@Test
	public void testMarkSupported() {
		int size = 10;

		UnsyncBufferedInputStream unsyncBufferedInputStream =
			new UnsyncBufferedInputStream(
				new ByteArrayInputStream(new byte[size]));

		Assert.assertTrue(unsyncBufferedInputStream.markSupported());
	}

	@Test
	public void testRead() throws IOException {
		int size = 10;

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			_BUFFER);

		UnsyncBufferedInputStream unsyncBufferedInputStream =
			new UnsyncBufferedInputStream(byteArrayInputStream, size);

		Assert.assertEquals(_SIZE, byteArrayInputStream.available());

		Assert.assertEquals(_SIZE, unsyncBufferedInputStream.available());
		Assert.assertEquals(0, unsyncBufferedInputStream.read());
		Assert.assertEquals(_SIZE - size, byteArrayInputStream.available());
		Assert.assertEquals(_SIZE - 1, unsyncBufferedInputStream.available());

		for (int i = 1; i < (size + 1); i++) {
			Assert.assertEquals(i, unsyncBufferedInputStream.read());
		}

		Assert.assertEquals(_SIZE - size * 2, byteArrayInputStream.available());
		Assert.assertEquals(
			_SIZE - size - 1, unsyncBufferedInputStream.available());

		for (int i = size + 1; i < _SIZE; i++) {
			Assert.assertEquals(
				(byte)i, (byte)unsyncBufferedInputStream.read());
		}

		Assert.assertEquals(-1, unsyncBufferedInputStream.read());
	}

	@Test
	public void testSkip() throws IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			_BUFFER);

		int size = 10;

		UnsyncBufferedInputStream unsyncBufferedInputStream =
			new UnsyncBufferedInputStream(byteArrayInputStream, size);

		// Zero skip

		Assert.assertEquals(0, unsyncBufferedInputStream.skip(0));

		Assert.assertEquals(_SIZE, byteArrayInputStream.available());
		Assert.assertEquals(_SIZE, unsyncBufferedInputStream.available());

		// Negative skip

		Assert.assertEquals(0, unsyncBufferedInputStream.skip(-1));
		Assert.assertEquals(_SIZE, byteArrayInputStream.available());
		Assert.assertEquals(_SIZE, unsyncBufferedInputStream.available());

		// Load data into buffer

		Assert.assertEquals(0, unsyncBufferedInputStream.read());
		Assert.assertEquals(_SIZE - size, byteArrayInputStream.available());
		Assert.assertEquals(_SIZE - 1, unsyncBufferedInputStream.available());

		// In-memory

		Assert.assertEquals(size - 1, unsyncBufferedInputStream.skip(size * 2));
		Assert.assertEquals(10, unsyncBufferedInputStream.read());
		Assert.assertEquals(size - 1, unsyncBufferedInputStream.skip(size * 2));

		// Underlying input stream

		Assert.assertEquals(size * 2, unsyncBufferedInputStream.skip(size * 2));
		Assert.assertEquals(40, unsyncBufferedInputStream.read());

		// Clear out buffer

		Assert.assertEquals(size - 1, unsyncBufferedInputStream.skip(size));

		// Mark

		unsyncBufferedInputStream.mark(size * 2);

		// Load data into buffer for skipping

		Assert.assertEquals(size, unsyncBufferedInputStream.skip(size * 2));

		// In-memory

		Assert.assertEquals(size / 2, unsyncBufferedInputStream.skip(size / 2));

		unsyncBufferedInputStream.reset();

		Assert.assertEquals(50, unsyncBufferedInputStream.read());

		// Clear out buffer

		Assert.assertEquals(
			size * 2 - 1, unsyncBufferedInputStream.skip(size * 2));

		// Mark a large size for EOF

		unsyncBufferedInputStream.mark(_SIZE);

		// Consume all the data

		while (unsyncBufferedInputStream.read() != -1);

		// Skip on EOF

		Assert.assertEquals(0, unsyncBufferedInputStream.skip(1));
	}

	@Override
	protected InputStream getInputStream(byte[] bytes) {
		return new UnsyncBufferedInputStream(new ByteArrayInputStream(bytes));
	}

	private static final byte[] _BUFFER =
		new byte[UnsyncBufferedInputStreamTest._SIZE];

	private static final int _SIZE = 16 * 1024;

	private static final Field _bufferField = ReflectionTestUtil.getField(
		UnsyncBufferedInputStream.class, "_buffer");
	private static final Field _firstInvalidIndexField =
		ReflectionTestUtil.getField(
			UnsyncBufferedInputStream.class, "_firstInvalidIndex");
	private static final Field _indexField = ReflectionTestUtil.getField(
		UnsyncBufferedInputStream.class, "_index");
	private static final Field _markLimitIndexField =
		ReflectionTestUtil.getField(
			UnsyncBufferedInputStream.class, "_markLimitIndex");

	static {
		for (int i = 0; i < _SIZE; i++) {
			_BUFFER[i] = (byte)i;
		}
	}

}