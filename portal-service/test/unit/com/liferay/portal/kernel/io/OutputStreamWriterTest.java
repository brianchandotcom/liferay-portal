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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.OutputStream;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

import java.util.concurrent.atomic.AtomicBoolean;

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

		Assert.assertSame(
			dummyOutputStream, _getOutputStream(outputStreamWriter));
		Assert.assertSame(
			StringPool.DEFAULT_CHARSET_NAME, outputStreamWriter.getEncoding());
		Assert.assertEquals(
			_getDefaultOutputBufferSize(),
			_getOutputBufferSize(outputStreamWriter));
		Assert.assertFalse(_isAutoFlush(outputStreamWriter));

		outputStreamWriter = new OutputStreamWriter(dummyOutputStream, null);

		Assert.assertSame(
			dummyOutputStream, _getOutputStream(outputStreamWriter));
		Assert.assertSame(
			StringPool.DEFAULT_CHARSET_NAME, outputStreamWriter.getEncoding());
		Assert.assertEquals(
			_getDefaultOutputBufferSize(),
			_getOutputBufferSize(outputStreamWriter));
		Assert.assertFalse(_isAutoFlush(outputStreamWriter));

		String encoding = "US-ASCII";

		outputStreamWriter = new OutputStreamWriter(
			dummyOutputStream, encoding);

		Assert.assertSame(
			dummyOutputStream, _getOutputStream(outputStreamWriter));
		Assert.assertSame(encoding, outputStreamWriter.getEncoding());
		Assert.assertEquals(
			_getDefaultOutputBufferSize(),
			_getOutputBufferSize(outputStreamWriter));
		Assert.assertFalse(_isAutoFlush(outputStreamWriter));

		outputStreamWriter = new OutputStreamWriter(
			dummyOutputStream, encoding, true);

		Assert.assertSame(
			dummyOutputStream, _getOutputStream(outputStreamWriter));
		Assert.assertSame(encoding, outputStreamWriter.getEncoding());
		Assert.assertEquals(
			_getDefaultOutputBufferSize(),
			_getOutputBufferSize(outputStreamWriter));
		Assert.assertTrue(_isAutoFlush(outputStreamWriter));

		outputStreamWriter = new OutputStreamWriter(
			dummyOutputStream, encoding, 32);

		Assert.assertSame(
			dummyOutputStream, _getOutputStream(outputStreamWriter));
		Assert.assertSame(encoding, outputStreamWriter.getEncoding());
		Assert.assertEquals(1, _getInputBufferSize(outputStreamWriter));
		Assert.assertEquals(32, _getOutputBufferSize(outputStreamWriter));
		Assert.assertFalse(_isAutoFlush(outputStreamWriter));

		outputStreamWriter = new OutputStreamWriter(
			dummyOutputStream, encoding, 32, true);

		Assert.assertSame(
			dummyOutputStream, _getOutputStream(outputStreamWriter));
		Assert.assertSame(encoding, outputStreamWriter.getEncoding());
		Assert.assertEquals(1, _getInputBufferSize(outputStreamWriter));
		Assert.assertEquals(32, _getOutputBufferSize(outputStreamWriter));
		Assert.assertTrue(_isAutoFlush(outputStreamWriter));

		try {
			new OutputStreamWriter(dummyOutputStream, encoding, 3, true);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Output buffer size 3 is less than 4", iae.getMessage());
		}
	}

	@Test
	public void testFlush() throws IOException {
		MarkerOutputStream markerOutputStream = new MarkerOutputStream();

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			markerOutputStream);

		Assert.assertFalse(markerOutputStream._flushed);

		outputStreamWriter.flush();

		Assert.assertTrue(markerOutputStream._flushed);

		outputStreamWriter.write('a');

		outputStreamWriter.flush();

		Assert.assertTrue(markerOutputStream._flushed);
	}

	@Test
	public void testWriteCharArray() throws IOException {
		_testWriteCharArray(false);
		_testWriteCharArray(true);
	}

	@Test
	public void testWriteError() throws Exception {
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			new DummyOutputStream(), "US-ASCII");

		CharsetEncoder charsetEncoder =
			(CharsetEncoder)ReflectionTestUtil.getFieldValue(
				outputStreamWriter, "_charsetEncoder");

		charsetEncoder.onUnmappableCharacter(CodingErrorAction.REPORT);

		try {
			outputStreamWriter.write("测试");

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals(
				"Unexcepted coder result UNMAPPABLE[1]", ioe.getMessage());
		}
	}

	@Test
	public void testWriteInt() throws IOException {
		MarkerOutputStream markerOutputStream = new MarkerOutputStream();

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			markerOutputStream);

		outputStreamWriter.write('a');

		outputStreamWriter.flush();

		Assert.assertEquals((byte)'a', markerOutputStream._bytes[0]);
		Assert.assertEquals(1, markerOutputStream._length);
		Assert.assertEquals(0, markerOutputStream._offset);
	}

	@Test
	public void testWriteString() throws IOException {
		_testWriteString(false);
		_testWriteString(true);
	}

	private int _getDefaultOutputBufferSize() throws Exception {
		return (Integer)ReflectionTestUtil.getFieldValue(
			OutputStreamWriter.class, "_DEFAULT_OUTPUT_BUFFER_SIZE");
	}

	private int _getInputBufferSize(OutputStreamWriter outputStreamWriter)
		throws Exception {

		CharBuffer inputCharBuffer =
			(CharBuffer)ReflectionTestUtil.getFieldValue(
				outputStreamWriter, "_inputCharBuffer");

		return inputCharBuffer.capacity();
	}

	private int _getOutputBufferSize(OutputStreamWriter outputStreamWriter)
		throws Exception {

		ByteBuffer outputBuffer = (ByteBuffer)ReflectionTestUtil.getFieldValue(
			outputStreamWriter, "_outputByteBuffer");

		return outputBuffer.capacity();
	}

	private OutputStream _getOutputStream(OutputStreamWriter outputStreamWriter)
		throws Exception {

		return (OutputStream)ReflectionTestUtil.getFieldValue(
			outputStreamWriter, "_outputStream");
	}

	private boolean _isAutoFlush(OutputStreamWriter outputStreamWriter)
		throws Exception {

		return (Boolean)ReflectionTestUtil.getFieldValue(
			outputStreamWriter, "_autoFlush");
	}

	private void _testWriteCharArray(boolean autoFlush) throws IOException {
		final AtomicBoolean flushed = new AtomicBoolean();

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream() {

				@Override
				public void flush() {
					flushed.set(true);
				}

			};

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			unsyncByteArrayOutputStream, "US-ASCII", 4, autoFlush);

		outputStreamWriter.write("abcdefg".toCharArray(), 1, 5);

		Assert.assertFalse(flushed.get());

		if (!autoFlush) {
			outputStreamWriter.flush();

			Assert.assertTrue(flushed.get());

			flushed.set(false);
		}

		Assert.assertArrayEquals(
			new byte[] {'b', 'c', 'd', 'e', 'f'},
			unsyncByteArrayOutputStream.toByteArray());

		unsyncByteArrayOutputStream.reset();

		outputStreamWriter = new OutputStreamWriter(
			unsyncByteArrayOutputStream, "US-ASCII", autoFlush);

		outputStreamWriter.write("abc".toCharArray());

		Assert.assertFalse(flushed.get());

		if (!autoFlush) {
			outputStreamWriter.flush();

			Assert.assertTrue(flushed.get());

			flushed.set(false);
		}

		Assert.assertArrayEquals(
			new byte[] {'a', 'b', 'c'},
			unsyncByteArrayOutputStream.toByteArray());
	}

	private void _testWriteString(boolean autoFlush) throws IOException {
		final AtomicBoolean flushed = new AtomicBoolean();

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream() {

				@Override
				public void flush() {
					flushed.set(true);
				}

			};

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			unsyncByteArrayOutputStream, "US-ASCII", 4, autoFlush);

		outputStreamWriter.write("abcdefg", 1, 5);

		Assert.assertFalse(flushed.get());

		if (!autoFlush) {
			outputStreamWriter.flush();

			Assert.assertTrue(flushed.get());

			flushed.set(false);
		}

		Assert.assertArrayEquals(
			new byte[] {'b', 'c', 'd', 'e', 'f'},
			unsyncByteArrayOutputStream.toByteArray());

		unsyncByteArrayOutputStream.reset();

		outputStreamWriter = new OutputStreamWriter(
			unsyncByteArrayOutputStream, "US-ASCII", 4, autoFlush);

		outputStreamWriter.write("abcdefg", 1, 5);

		Assert.assertFalse(flushed.get());

		if (!autoFlush) {
			outputStreamWriter.flush();

			Assert.assertTrue(flushed.get());

			flushed.set(false);
		}

		Assert.assertArrayEquals(
			new byte[] {'b', 'c', 'd', 'e', 'f'},
			unsyncByteArrayOutputStream.toByteArray());

		unsyncByteArrayOutputStream.reset();

		outputStreamWriter = new OutputStreamWriter(
			unsyncByteArrayOutputStream, "US-ASCII", autoFlush);

		outputStreamWriter.write("abc");

		Assert.assertFalse(flushed.get());

		if (!autoFlush) {
			outputStreamWriter.flush();

			Assert.assertTrue(flushed.get());

			flushed.set(false);
		}

		Assert.assertArrayEquals(
			new byte[] {'a', 'b', 'c'},
			unsyncByteArrayOutputStream.toByteArray());
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