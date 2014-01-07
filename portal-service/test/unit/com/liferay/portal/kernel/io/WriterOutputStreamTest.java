
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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;

import java.lang.reflect.Field;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class WriterOutputStreamTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testClose() throws IOException {
		final AtomicBoolean closeAtomicBoolean = new AtomicBoolean();

		DummyWriter dummyWriter = new DummyWriter() {

			@Override
			public void close() {
				closeAtomicBoolean.set(true);
			}

		};

		WriterOutputStream writerOutputStream = new WriterOutputStream(
			dummyWriter);

		Assert.assertFalse(closeAtomicBoolean.get());

		writerOutputStream.close();

		Assert.assertTrue(closeAtomicBoolean.get());
	}

	@Test
	public void testConstructor() throws Exception {
		DummyWriter dummyWriter = new DummyWriter();

		WriterOutputStream writerOutputStream = new WriterOutputStream(
			dummyWriter);

		Assert.assertSame(dummyWriter, _getWriter(writerOutputStream));
		Assert.assertSame(
			StringPool.DEFAULT_CHARSET_NAME, writerOutputStream.getEncoding());
		Assert.assertEquals(
			_getDefaultOutputBufferSize(),
			_getOutputBufferSize(writerOutputStream));
		Assert.assertFalse(_isAutoFlush(writerOutputStream));

		writerOutputStream = new WriterOutputStream(dummyWriter, null);

		Assert.assertSame(dummyWriter, _getWriter(writerOutputStream));
		Assert.assertSame(
			StringPool.DEFAULT_CHARSET_NAME, writerOutputStream.getEncoding());
		Assert.assertEquals(
			_getDefaultOutputBufferSize(),
			_getOutputBufferSize(writerOutputStream));
		Assert.assertFalse(_isAutoFlush(writerOutputStream));

		String encoding = "US-ASCII";

		writerOutputStream = new WriterOutputStream(dummyWriter, encoding);

		Assert.assertSame(dummyWriter, _getWriter(writerOutputStream));
		Assert.assertSame(encoding, writerOutputStream.getEncoding());
		Assert.assertEquals(
			_getDefaultOutputBufferSize(),
			_getOutputBufferSize(writerOutputStream));
		Assert.assertFalse(_isAutoFlush(writerOutputStream));

		writerOutputStream = new WriterOutputStream(
			dummyWriter, encoding, true);

		Assert.assertSame(dummyWriter, _getWriter(writerOutputStream));
		Assert.assertSame(encoding, writerOutputStream.getEncoding());
		Assert.assertEquals(
			_getDefaultOutputBufferSize(),
			_getOutputBufferSize(writerOutputStream));
		Assert.assertTrue(_isAutoFlush(writerOutputStream));

		writerOutputStream = new WriterOutputStream(dummyWriter, encoding, 32);

		Assert.assertSame(dummyWriter, _getWriter(writerOutputStream));
		Assert.assertSame(encoding, writerOutputStream.getEncoding());
		Assert.assertEquals(1, _getInputBufferSize(writerOutputStream));
		Assert.assertEquals(32, _getOutputBufferSize(writerOutputStream));
		Assert.assertFalse(_isAutoFlush(writerOutputStream));

		writerOutputStream = new WriterOutputStream(
			dummyWriter, encoding, 32, true);

		Assert.assertSame(dummyWriter, _getWriter(writerOutputStream));
		Assert.assertSame(encoding, writerOutputStream.getEncoding());
		Assert.assertEquals(1, _getInputBufferSize(writerOutputStream));
		Assert.assertEquals(32, _getOutputBufferSize(writerOutputStream));
		Assert.assertTrue(_isAutoFlush(writerOutputStream));

		try {
			new WriterOutputStream(dummyWriter, encoding, 0, true);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Output buffer size 0 must be a positive number",
				iae.getMessage());
		}
	}

	@Test
	public void testWrite() throws IOException {
		CharArrayWriter charArrayWriter = new CharArrayWriter();

		WriterOutputStream writerOutputStream = new WriterOutputStream(
			charArrayWriter, StringPool.UTF8, true);

		int charNumber = 0;

		String nonAlignOutput = "非对齐测试中文输出";
		byte[] nonAlignInput = nonAlignOutput.getBytes(StringPool.UTF8);

		for (byte b : nonAlignInput) {
			writerOutputStream.write(b);

			int currentCharNumber = charArrayWriter.size();

			if (currentCharNumber > charNumber) {
				charNumber = currentCharNumber;

				Assert.assertEquals(
					nonAlignOutput.charAt(charNumber - 1),
					charArrayWriter.toCharArray()[charNumber - 1]);
			}
		}

		Assert.assertEquals(nonAlignOutput, charArrayWriter.toString());
	}

	@Test
	public void testWriteBlock() throws IOException {
		doTestWriteBlock(false);
		doTestWriteBlock(true);
	}

	@Test
	public void testWriteBlockNotAlign() throws IOException {
		CharArrayWriter charArrayWriter = new CharArrayWriter();

		WriterOutputStream writerOutputStream = new WriterOutputStream(
			charArrayWriter, StringPool.UTF8, true);

		String nonAlignOutput = "非对齐测试中文输出";
		byte[] nonAlignInput = nonAlignOutput.getBytes(StringPool.UTF8);

		writerOutputStream.write(nonAlignInput[0]);
		writerOutputStream.write(nonAlignInput, 1, nonAlignInput.length - 2);
		writerOutputStream.write(nonAlignInput[nonAlignInput.length - 1]);

		writerOutputStream.close();

		Assert.assertEquals(nonAlignOutput, charArrayWriter.toString());
	}

	@Test
	public void testWriteError() throws Exception {
		WriterOutputStream writerOutputStream = new WriterOutputStream(
			new DummyWriter(), "US-ASCII");

		CharsetDecoder charsetDecoder = _getCharsetDecoder(writerOutputStream);

		charsetDecoder.onMalformedInput(CodingErrorAction.REPORT);

		try {
			writerOutputStream.write(new byte[]{-1, -2, -3, -4});

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals(
				"Unexcepted coder result MALFORMED[1]", ioe.getMessage());
		}
	}

	private CharsetDecoder _getCharsetDecoder(
			WriterOutputStream writerOutputStream)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			WriterOutputStream.class, "_charsetDecoder");

		return (CharsetDecoder)field.get(writerOutputStream);
	}

	private int _getDefaultOutputBufferSize() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			WriterOutputStream.class, "_DEFAULT_OUTPUT_BUFFER_SIZE");

		return field.getInt(null);
	}

	private int _getInputBufferSize(WriterOutputStream writerOutputStream)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			WriterOutputStream.class, "_inputBuffer");

		ByteBuffer inputBuffer = (ByteBuffer)field.get(writerOutputStream);

		return inputBuffer.capacity();
	}

	private int _getOutputBufferSize(WriterOutputStream writerOutputStream)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			WriterOutputStream.class, "_outputBuffer");

		CharBuffer outputBuffer = (CharBuffer)field.get(writerOutputStream);

		return outputBuffer.capacity();
	}

	private Writer _getWriter(WriterOutputStream writerOutputStream)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			WriterOutputStream.class, "_writer");

		return (Writer)field.get(writerOutputStream);
	}

	private boolean _isAutoFlush(WriterOutputStream writerOutputStream)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			WriterOutputStream.class, "_autoFlush");

		return field.getBoolean(writerOutputStream);
	}

	private void doTestWriteBlock(boolean autoFlush) throws IOException {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		WriterOutputStream writerOutputStream = new WriterOutputStream(
			unsyncStringWriter, "US-ASCII", 2, autoFlush);

		writerOutputStream.write(
			new byte[] {
				(byte)'a', (byte)'b', (byte)'c', (byte)'d', (byte)'e',
				(byte)'f', (byte)'g'},
			1, 5);

		if (!autoFlush) {
			writerOutputStream.flush();
		}

		Assert.assertEquals("bcdef", unsyncStringWriter.toString());

		unsyncStringWriter = new UnsyncStringWriter();

		writerOutputStream = new WriterOutputStream(
			unsyncStringWriter, "US-ASCII", 4, autoFlush);

		writerOutputStream.write(
			new byte[] {
				(byte)'a', (byte)'b', (byte)'c', (byte)'d', (byte)'e',
				(byte)'f', (byte)'g'},
			1, 5);

		if (!autoFlush) {
			writerOutputStream.flush();
		}

		Assert.assertEquals("bcdef", unsyncStringWriter.toString());

		unsyncStringWriter = new UnsyncStringWriter();

		writerOutputStream = new WriterOutputStream(
			unsyncStringWriter, "US-ASCII", autoFlush);

		writerOutputStream.write(new byte[]{(byte)'a', (byte)'b', (byte)'c'});

		if (!autoFlush) {
			writerOutputStream.flush();
		}

		Assert.assertEquals("abc", unsyncStringWriter.toString());
	}

}