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

import java.io.IOException;
import java.io.Reader;

import java.lang.reflect.Field;

import java.nio.CharBuffer;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncCharArrayReaderTest extends BaseReaderTestCase {

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
	public void testBlockRead() throws IOException {
		UnsyncCharArrayReader unsyncCharArrayReader = new UnsyncCharArrayReader(
			_BUFFER);

		int size = _SIZE * 2 / 3;

		char[] buffer = new char[size];

		int read = unsyncCharArrayReader.read(buffer);

		Assert.assertEquals(size, read);

		for (int i = 0; i < read; i++) {
			Assert.assertEquals('a' + i, buffer[i]);
		}

		read = unsyncCharArrayReader.read(buffer);

		Assert.assertEquals(_SIZE - size, read);

		for (int i = 0; i < read; i++) {
			Assert.assertEquals('a' + i + size, buffer[i]);
		}

		Assert.assertEquals(-1, unsyncCharArrayReader.read(new char[1]));
	}

	@Test
	public void testBufferRead() throws IOException {
		UnsyncCharArrayReader unsyncCharArrayReader = new UnsyncCharArrayReader(
			_BUFFER);

		int size = _SIZE * 2 / 3;

		CharBuffer charBuffer = CharBuffer.allocate(size);

		int read = unsyncCharArrayReader.read(charBuffer);

		Assert.assertEquals(size, read);

		for (int i = 0; i < read; i++) {
			Assert.assertEquals('a' + i, charBuffer.get(i));
		}

		charBuffer.position(0);

		read = unsyncCharArrayReader.read(charBuffer);

		Assert.assertEquals(_SIZE - size, read);

		for (int i = 0; i < read; i++) {
			Assert.assertEquals('a' + i + size, charBuffer.get(i));
		}

		charBuffer.position(charBuffer.limit());

		Assert.assertEquals(0, unsyncCharArrayReader.read(charBuffer));

		charBuffer.position(0);

		Assert.assertEquals(-1, unsyncCharArrayReader.read(charBuffer));
	}

	@Test
	public void testClose() throws Exception {
		UnsyncCharArrayReader unsyncCharArrayReader = new UnsyncCharArrayReader(
			_BUFFER);

		unsyncCharArrayReader.close();

		Assert.assertNull(_bufferField.get(unsyncCharArrayReader));

		try {
			unsyncCharArrayReader.read((CharBuffer)null);

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals("Stream closed", ioe.getMessage());
		}

		testClose(unsyncCharArrayReader, "Stream closed");
	}

	@Test
	public void testConstructor() throws Exception {
		new BoundaryCheckerUtil();

		UnsyncCharArrayReader unsyncCharArrayReader = new UnsyncCharArrayReader(
			_BUFFER);

		Assert.assertEquals(_BUFFER, _bufferField.get(unsyncCharArrayReader));
		Assert.assertEquals(
			_SIZE, _capacityField.getInt(unsyncCharArrayReader));
		Assert.assertEquals(0, _indexField.getInt(unsyncCharArrayReader));
		Assert.assertEquals(0, _markIndexField.getInt(unsyncCharArrayReader));

		unsyncCharArrayReader = new UnsyncCharArrayReader(
			_BUFFER, _SIZE / 2, _SIZE / 2);

		Assert.assertEquals(_BUFFER, _bufferField.get(unsyncCharArrayReader));
		Assert.assertEquals(
			_SIZE, _capacityField.getInt(unsyncCharArrayReader));
		Assert.assertEquals(
			_SIZE / 2, _indexField.getInt(unsyncCharArrayReader));
		Assert.assertEquals(
			_SIZE / 2, _markIndexField.getInt(unsyncCharArrayReader));
	}

	@Override
	@Test
	public void testReady() throws IOException {
		UnsyncCharArrayReader unsyncCharArrayReader = new UnsyncCharArrayReader(
			_BUFFER);

		Assert.assertTrue(unsyncCharArrayReader.ready());

		unsyncCharArrayReader.read(CharBuffer.allocate(_SIZE));

		Assert.assertFalse(unsyncCharArrayReader.ready());
	}

	@Override
	protected Reader getReader(String s) {
		return new UnsyncCharArrayReader(s.toCharArray());
	}

	private static final char[] _BUFFER =
		new char[UnsyncCharArrayReaderTest._SIZE];

	private static final int _SIZE = 10;

	private static final Field _bufferField = ReflectionTestUtil.getField(
		UnsyncCharArrayReader.class, "_buffer");
	private static final Field _capacityField = ReflectionTestUtil.getField(
		UnsyncCharArrayReader.class, "_capacity");
	private static final Field _indexField = ReflectionTestUtil.getField(
		UnsyncCharArrayReader.class, "_index");
	private static final Field _markIndexField = ReflectionTestUtil.getField(
		UnsyncCharArrayReader.class, "_markIndex");

	static {
		for (int i = 0; i < _SIZE; i++) {
			_BUFFER[i] = (char)('a' + i);
		}
	}

}