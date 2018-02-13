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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;

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
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testClose() throws IOException {
		final AtomicBoolean closeAtomicBoolean = new AtomicBoolean();

		DummyWriter dummyWriter = new DummyWriter() {

			@Override
			public void close() {
				closeAtomicBoolean.set(true);
			}

		};

		try (WriterOutputStream writerOutputStream = new WriterOutputStream(
				dummyWriter)) {

			Assert.assertFalse(closeAtomicBoolean.get());
		}

		Assert.assertTrue(closeAtomicBoolean.get());
	}

	@Test
	public void testConstructor() {
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

		writerOutputStream = new WriterOutputStream(
			dummyWriter, encoding, 0, true);

		Assert.assertSame(dummyWriter, _getWriter(writerOutputStream));
		Assert.assertSame(encoding, writerOutputStream.getEncoding());
		Assert.assertEquals(1, _getInputBufferSize(writerOutputStream));
		Assert.assertEquals(
			_getDefaultOutputBufferSize(),
			_getOutputBufferSize(writerOutputStream));
		Assert.assertTrue(_isAutoFlush(writerOutputStream));

		try {
			new WriterOutputStream(dummyWriter, encoding, 0, false);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Output buffer size 0 must be a positive number",
				iae.getMessage());
		}
	}

	@Test
	public void testFlush() throws IOException {
		final AtomicBoolean flushed = new AtomicBoolean();

		CharArrayWriter charArrayWriter = new CharArrayWriter() {

			@Override
			public void flush() {
				flushed.set(true);
			}

		};

		WriterOutputStream writerOutputStream = new WriterOutputStream(
			charArrayWriter, StringPool.UTF8, 2);

		Assert.assertFalse(flushed.get());

		writerOutputStream.write('a');

		Assert.assertFalse(flushed.get());

		Assert.assertEquals(0, charArrayWriter.size());

		writerOutputStream.write('b');

		Assert.assertFalse(flushed.get());

		Assert.assertEquals(0, charArrayWriter.size());

		writerOutputStream.write('c');

		Assert.assertFalse(flushed.get());

		Assert.assertEquals("ab", charArrayWriter.toString());

		writerOutputStream.write('d');

		Assert.assertFalse(flushed.get());

		Assert.assertEquals("ab", charArrayWriter.toString());

		writerOutputStream.flush();

		Assert.assertTrue(flushed.get());

		Assert.assertEquals("abcd", charArrayWriter.toString());
	}

	@Test
	public void testWrite() throws IOException {
		CharArrayWriter charArrayWriter = new CharArrayWriter();

		WriterOutputStream writerOutputStream = new WriterOutputStream(
			charArrayWriter, StringPool.UTF8, true);

		int charNumber = 0;

		String unalignedOutput = "非对齐测试中文输出";

		byte[] unalignedInput = unalignedOutput.getBytes(StringPool.UTF8);

		for (byte b : unalignedInput) {
			writerOutputStream.write(b);

			int currentCharNumber = charArrayWriter.size();

			if (currentCharNumber > charNumber) {
				charNumber = currentCharNumber;

				Assert.assertEquals(
					unalignedOutput.charAt(charNumber - 1),
					charArrayWriter.toCharArray()[charNumber - 1]);
			}
		}

		Assert.assertEquals(unalignedOutput, charArrayWriter.toString());
	}

	@Test
	public void testWriteBlock() throws IOException {
		_testWriteBlock(false);
		_testWriteBlock(true);
	}

	@Test
	public void testWriteBlockUnaligned() throws IOException {
		CharArrayWriter charArrayWriter = new CharArrayWriter();

		String unalignedOutput = "非对齐测试中文输出";

		try (WriterOutputStream writerOutputStream = new WriterOutputStream(
				charArrayWriter, StringPool.UTF8, true)) {

			byte[] unalignedInput = unalignedOutput.getBytes(StringPool.UTF8);

			writerOutputStream.write(unalignedInput[0]);
			writerOutputStream.write(
				unalignedInput, 1, unalignedInput.length - 2);
			writerOutputStream.write(unalignedInput[unalignedInput.length - 1]);
		}

		Assert.assertEquals(unalignedOutput, charArrayWriter.toString());
	}

	@Test
	public void testWriteError() {
		WriterOutputStream writerOutputStream = new WriterOutputStream(
			new DummyWriter(), "US-ASCII");

		CharsetDecoder charsetDecoder = ReflectionTestUtil.getFieldValue(
			writerOutputStream, "_charsetDecoder");

		charsetDecoder.onMalformedInput(CodingErrorAction.REPORT);

		try {
			writerOutputStream.write(new byte[] {-1, -2, -3, -4});

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals(
				"Unexcepted coder result MALFORMED[1]", ioe.getMessage());
		}
	}

	private int _getDefaultOutputBufferSize() {
		return ReflectionTestUtil.getFieldValue(
			WriterOutputStream.class, "_DEFAULT_OUTPUT_BUFFER_SIZE");
	}

	private int _getInputBufferSize(WriterOutputStream writerOutputStream) {
		ByteBuffer inputByteBuffer = ReflectionTestUtil.getFieldValue(
			writerOutputStream, "_inputByteBuffer");

		return inputByteBuffer.capacity();
	}

	private int _getOutputBufferSize(WriterOutputStream writerOutputStream) {
		CharBuffer outputBuffer = ReflectionTestUtil.getFieldValue(
			writerOutputStream, "_outputCharBuffer");

		return outputBuffer.capacity();
	}

	private Writer _getWriter(WriterOutputStream writerOutputStream) {
		return ReflectionTestUtil.getFieldValue(writerOutputStream, "_writer");
	}

	private boolean _isAutoFlush(WriterOutputStream writerOutputStream) {
		return ReflectionTestUtil.getFieldValue(
			writerOutputStream, "_autoFlush");
	}

	private void _testWriteBlock(boolean autoFlush) throws IOException {
		final AtomicBoolean flushed = new AtomicBoolean();

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter() {

			@Override
			public void flush() {
				flushed.set(true);
			}

		};

		WriterOutputStream writerOutputStream = new WriterOutputStream(
			unsyncStringWriter, "US-ASCII", 2, autoFlush);

		writerOutputStream.write(
			new byte[] {
				(byte)'a', (byte)'b', (byte)'c', (byte)'d', (byte)'e',
				(byte)'f', (byte)'g'
			},
			1, 5);

		Assert.assertFalse(flushed.get());

		if (!autoFlush) {
			writerOutputStream.flush();

			Assert.assertTrue(flushed.get());

			flushed.set(false);
		}

		Assert.assertEquals("bcdef", unsyncStringWriter.toString());

		unsyncStringWriter.reset();

		writerOutputStream = new WriterOutputStream(
			unsyncStringWriter, "US-ASCII", 4, autoFlush);

		writerOutputStream.write(
			new byte[] {
				(byte)'a', (byte)'b', (byte)'c', (byte)'d', (byte)'e',
				(byte)'f', (byte)'g'
			},
			1, 5);

		Assert.assertFalse(flushed.get());

		if (!autoFlush) {
			writerOutputStream.flush();

			Assert.assertTrue(flushed.get());

			flushed.set(false);
		}

		Assert.assertEquals("bcdef", unsyncStringWriter.toString());

		unsyncStringWriter.reset();

		writerOutputStream = new WriterOutputStream(
			unsyncStringWriter, "US-ASCII", autoFlush);

		writerOutputStream.write(new byte[] {(byte)'a', (byte)'b', (byte)'c'});

		Assert.assertFalse(flushed.get());

		if (!autoFlush) {
			writerOutputStream.flush();

			Assert.assertTrue(flushed.get());

			flushed.set(false);
		}

		Assert.assertEquals("abc", unsyncStringWriter.toString());
	}

}