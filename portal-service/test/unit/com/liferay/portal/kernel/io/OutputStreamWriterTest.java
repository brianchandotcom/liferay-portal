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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.OutputStream;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class OutputStreamWriterTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testClose() throws IOException {
		MarkerOutputStream markerOutputStream = new MarkerOutputStream();

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			markerOutputStream);

		Assert.assertFalse(markerOutputStream._closed);

		outputStreamWriter.close();

		Assert.assertTrue(markerOutputStream._closed);
	}

	@Test
	public void testConstructor() throws Exception {
		DummyOutputStream dummyOutputStream = new DummyOutputStream();

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			dummyOutputStream);

		OutputStream outputStream = _getOutputStream(outputStreamWriter);

		Assert.assertSame(dummyOutputStream, outputStream);
		Assert.assertSame(
			StringPool.DEFAULT_CHARSET_NAME, outputStreamWriter.getEncoding());

		outputStreamWriter = new OutputStreamWriter(dummyOutputStream, null);

		Assert.assertSame(dummyOutputStream, outputStream);
		Assert.assertSame(
			StringPool.DEFAULT_CHARSET_NAME, outputStreamWriter.getEncoding());

		String encoding = "US-ASCII";

		outputStreamWriter = new OutputStreamWriter(
			dummyOutputStream, encoding);

		Assert.assertSame(dummyOutputStream, outputStream);
		Assert.assertSame(encoding, outputStreamWriter.getEncoding());
	}

	@Test
	public void testFlush() throws IOException {
		MarkerOutputStream markerOutputStream = new MarkerOutputStream();

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			markerOutputStream);

		Assert.assertFalse(markerOutputStream._flushed);

		outputStreamWriter.flush();

		Assert.assertTrue(markerOutputStream._flushed);
	}

	@Test
	public void testWriteCharArray() throws IOException {
		MarkerOutputStream markerOutputStream = new MarkerOutputStream();

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			markerOutputStream);

		outputStreamWriter.write("abcdefg".toCharArray(), 3, 2);

		Assert.assertArrayEquals(
			new byte[] {'d', 'e'}, markerOutputStream._bytes);
		Assert.assertEquals(2, markerOutputStream._length);
		Assert.assertEquals(0, markerOutputStream._offset);
	}

	@Test
	public void testWriteInt() throws IOException {
		MarkerOutputStream markerOutputStream = new MarkerOutputStream();

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			markerOutputStream);

		outputStreamWriter.write('a');

		Assert.assertArrayEquals(new byte[] {'a'}, markerOutputStream._bytes);
		Assert.assertEquals(1, markerOutputStream._length);
		Assert.assertEquals(0, markerOutputStream._offset);
	}

	@Test
	public void testWriteString() throws IOException {
		MarkerOutputStream markerOutputStream = new MarkerOutputStream();

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			markerOutputStream);

		outputStreamWriter.write("abcdefg", 3, 2);

		Assert.assertArrayEquals(
			new byte[] {'d', 'e'}, markerOutputStream._bytes);
		Assert.assertEquals(2, markerOutputStream._length);
		Assert.assertEquals(0, markerOutputStream._offset);
	}

	private OutputStream _getOutputStream(OutputStreamWriter outputStreamWriter)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			OutputStreamWriter.class, "_outputStream");

		return (OutputStream)field.get(outputStreamWriter);
	}

	private class MarkerOutputStream extends OutputStream {

		@Override
		public void close() {
			_closed = true;
		}

		@Override
		public void flush() {
			_flushed = true;
		}

		@Override
		public void write(byte[] bytes, int offset, int length) {
			_bytes = bytes;
			_offset = offset;
			_length = length;
		}

		@Override
		public void write(int b) {
		}

		private byte[] _bytes;
		private boolean _closed;
		private boolean _flushed;
		private int _length;
		private int _offset;

	}

}