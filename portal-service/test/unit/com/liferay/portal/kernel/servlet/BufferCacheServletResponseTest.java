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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.io.DummyOutputStream;
import com.liferay.portal.kernel.io.DummyWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.lang.reflect.Field;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import java.util.Properties;

import javax.servlet.ServletOutputStream;

import org.junit.Assert;

/**
 * @author Shuyang Zhou
 */
public class BufferCacheServletResponseTest extends TestCase {

	public void testConstructor() {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse();

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		assertSame(
			stubHttpServletResponse, bufferCacheServletResponse.getResponse());
	}

	public void testGetBufferSize() throws Exception {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setCharacterEncoding(String characterEncoding) {
				}

			};

		// Clean

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		assertEquals(0, bufferCacheServletResponse.getBufferSize());

		// Byte buffer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.setByteBuffer(ByteBuffer.wrap(_TEST_BYTES));

		assertEquals(
			_TEST_BYTES.length, bufferCacheServletResponse.getBufferSize());

		// Character buffer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		CharBuffer charBuffer = CharBuffer.wrap(_TEST_STRING);

		bufferCacheServletResponse.setCharBuffer(CharBuffer.wrap(_TEST_STRING));

		assertEquals(
			_TEST_STRING.length(), bufferCacheServletResponse.getBufferSize());
		assertEquals(0, charBuffer.position());
		assertEquals(_TEST_STRING.length(), charBuffer.limit());
		assertEquals(_TEST_STRING.length(), charBuffer.capacity());

		// Servlet output stream

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		ServletOutputStream servletOutputStream =
			bufferCacheServletResponse.getOutputStream();

		servletOutputStream.write(_TEST_BYTES);

		assertEquals(
			_TEST_BYTES.length, bufferCacheServletResponse.getBufferSize());

		// Print writer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		PrintWriter printWriter = bufferCacheServletResponse.getWriter();

		printWriter.print(_TEST_STRING);

		assertEquals(
			_TEST_STRING.length(), bufferCacheServletResponse.getBufferSize());

		// Exception handling

		OutputStream failFlushOutputStream = new UnsyncByteArrayOutputStream() {

			@Override
			public void flush() throws IOException {
				throw new IOException("Failed to flush");
			}

		};

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		servletOutputStream = bufferCacheServletResponse.getOutputStream();

		Field outputStreamField = ReflectionUtil.getDeclaredField(
			ServletOutputStreamAdapter.class, "outputStream");

		outputStreamField.set(servletOutputStream, failFlushOutputStream);

		try {
			bufferCacheServletResponse.getBufferSize();

			fail();
		}
		catch (RuntimeException re) {
			Throwable throwable = re.getCause();

			assertTrue(throwable instanceof IOException);
			assertEquals("Failed to flush", throwable.getMessage());
		}
	}

	public void testGetByteBuffer() throws Exception {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setCharacterEncoding(String characterEncoding) {
				}

			};

		// Clean

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		ByteBuffer byteBuffer = bufferCacheServletResponse.getByteBuffer();

		assertEquals(0, byteBuffer.limit());

		// Byte buffer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		byteBuffer = ByteBuffer.wrap(_TEST_BYTES);

		bufferCacheServletResponse.setByteBuffer(byteBuffer);

		assertSame(byteBuffer, bufferCacheServletResponse.getByteBuffer());

		ServletOutputStreamAdapter servletOutputStreamAdapter =
			(ServletOutputStreamAdapter)
				bufferCacheServletResponse.getOutputStream();

		Assert.assertTrue(
			servletOutputStreamAdapter.outputStream instanceof
				DummyOutputStream);
		Assert.assertTrue(bufferCacheServletResponse.calledGetOutputStream);

		bufferCacheServletResponse.setByteBuffer(null);

		Assert.assertFalse(bufferCacheServletResponse.calledGetOutputStream);

		servletOutputStreamAdapter =
			(ServletOutputStreamAdapter)
				bufferCacheServletResponse.getOutputStream();

		Assert.assertTrue(
			servletOutputStreamAdapter.outputStream instanceof
				UnsyncByteArrayOutputStream);
		Assert.assertTrue(bufferCacheServletResponse.calledGetOutputStream);

		// Char buffer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		CharBuffer charBuffer = CharBuffer.wrap(_TEST_STRING);

		bufferCacheServletResponse.setCharBuffer(charBuffer);

		byteBuffer = bufferCacheServletResponse.getByteBuffer();

		assertEquals(ByteBuffer.wrap(_TEST_BYTES), byteBuffer);
		assertEquals(0, charBuffer.position());
		assertEquals(_TEST_STRING.length(), charBuffer.limit());
		assertEquals(_TEST_STRING.length(), charBuffer.capacity());

		UnsyncPrintWriter unsyncPrintWriter =
			(UnsyncPrintWriter)bufferCacheServletResponse.getWriter();

		Field writerField = ReflectionUtil.getDeclaredField(
			UnsyncPrintWriter.class, "_writer");

		Assert.assertTrue(
			writerField.get(unsyncPrintWriter) instanceof DummyWriter);
		Assert.assertTrue(bufferCacheServletResponse.calledGetWriter);

		bufferCacheServletResponse.setCharBuffer(null);

		Assert.assertFalse(bufferCacheServletResponse.calledGetWriter);

		unsyncPrintWriter =
			(UnsyncPrintWriter)bufferCacheServletResponse.getWriter();

		Assert.assertTrue(
			writerField.get(unsyncPrintWriter) instanceof UnsyncStringWriter);
		Assert.assertTrue(bufferCacheServletResponse.calledGetWriter);

		// Servlet output stream

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		ServletOutputStream servletOutputStream =
			bufferCacheServletResponse.getOutputStream();

		servletOutputStream.write(_TEST_BYTES);

		byteBuffer = bufferCacheServletResponse.getByteBuffer();

		assertEquals(ByteBuffer.wrap(_TEST_BYTES), byteBuffer);

		// Print writer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		PrintWriter printWriter = bufferCacheServletResponse.getWriter();

		printWriter.write(_TEST_STRING);

		byteBuffer = bufferCacheServletResponse.getByteBuffer();

		assertEquals(ByteBuffer.wrap(_TEST_BYTES), byteBuffer);
	}

	public void testGetCharBuffer() throws IOException {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setCharacterEncoding(String characterEncoding) {
				}

			};

		// Clean

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		CharBuffer charBuffer = bufferCacheServletResponse.getCharBuffer();

		assertEquals(0, charBuffer.limit());

		// Character buffer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.setCharBuffer(CharBuffer.wrap(_TEST_STRING));

		charBuffer = bufferCacheServletResponse.getCharBuffer();

		assertEquals(_TEST_STRING, charBuffer.toString());

		// Byte buffer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		ByteBuffer byteBuffer = ByteBuffer.wrap(_TEST_BYTES);

		bufferCacheServletResponse.setByteBuffer(byteBuffer);

		charBuffer = bufferCacheServletResponse.getCharBuffer();

		assertEquals(_TEST_STRING, charBuffer.toString());
		assertEquals(0, byteBuffer.position());
		assertEquals(_TEST_BYTES.length, byteBuffer.limit());
		assertEquals(_TEST_BYTES.length, byteBuffer.capacity());

		// Print writer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		PrintWriter printWriter = bufferCacheServletResponse.getWriter();

		printWriter.print(_TEST_STRING);

		charBuffer = bufferCacheServletResponse.getCharBuffer();

		assertEquals(_TEST_STRING, charBuffer.toString());

		// Servlet output stream

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		ServletOutputStream servletOutputStream =
			bufferCacheServletResponse.getOutputStream();

		servletOutputStream.write(_TEST_BYTES);

		charBuffer = bufferCacheServletResponse.getCharBuffer();

		assertEquals(_TEST_STRING, charBuffer.toString());
	}

	public void testGetOutputStream() {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse();

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		// Two gets

		ServletOutputStream servletOutputStream1 =
			bufferCacheServletResponse.getOutputStream();
		ServletOutputStream servletOutputStream2 =
			bufferCacheServletResponse.getOutputStream();

		assertSame(servletOutputStream1, servletOutputStream2);

		// Get servlet output stream after getting print writer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.getWriter();

		try {
			bufferCacheServletResponse.getOutputStream();
		}
		catch (IllegalStateException ise) {
		}
	}

	public void testGetString() throws IOException {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setCharacterEncoding(String characterEncoding) {
				}

			};

		// Clean

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		assertEquals(StringPool.BLANK, bufferCacheServletResponse.getString());

		// Character buffer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.setCharBuffer(CharBuffer.wrap(_TEST_STRING));

		assertEquals(_TEST_STRING, bufferCacheServletResponse.getString());

		// Byte buffer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		ByteBuffer byteBuffer = ByteBuffer.wrap(_TEST_BYTES);

		bufferCacheServletResponse.setByteBuffer(byteBuffer);

		assertEquals(_TEST_STRING, bufferCacheServletResponse.getString());
		assertEquals(0, byteBuffer.position());
		assertEquals(_TEST_BYTES.length, byteBuffer.limit());
		assertEquals(_TEST_BYTES.length, byteBuffer.capacity());

		// Print writer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		PrintWriter printWriter = bufferCacheServletResponse.getWriter();

		printWriter.print(_TEST_STRING);

		assertEquals(_TEST_STRING, bufferCacheServletResponse.getString());

		// Servlet output stream

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		ServletOutputStream servletOutputStream =
			bufferCacheServletResponse.getOutputStream();

		servletOutputStream.write(_TEST_BYTES);

		assertEquals(_TEST_STRING, bufferCacheServletResponse.getString());
	}

	public void testGetStringBundler() throws IOException {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setCharacterEncoding(String characterEncoding) {
				}

			};

		// Clean

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		StringBundler sb = bufferCacheServletResponse.getStringBundler();

		assertEquals(1, sb.capacity());
		assertEquals(0, sb.index());

		// Character buffer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.setCharBuffer(CharBuffer.wrap(_TEST_STRING));

		sb = bufferCacheServletResponse.getStringBundler();

		assertEquals(1, sb.capacity());
		assertEquals(1, sb.index());
		assertEquals(_TEST_STRING, sb.toString());

		// Byte buffer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		ByteBuffer byteBuffer = ByteBuffer.wrap(_TEST_BYTES);

		bufferCacheServletResponse.setByteBuffer(byteBuffer);

		sb = bufferCacheServletResponse.getStringBundler();

		assertEquals(1, sb.capacity());
		assertEquals(1, sb.index());
		assertEquals(_TEST_STRING, sb.toString());
		assertEquals(0, byteBuffer.position());
		assertEquals(_TEST_BYTES.length, byteBuffer.limit());
		assertEquals(_TEST_BYTES.length, byteBuffer.capacity());

		// Print writer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		PrintWriter printWriter = bufferCacheServletResponse.getWriter();

		printWriter.print(_TEST_STRING);

		sb = bufferCacheServletResponse.getStringBundler();

		assertEquals(16, sb.capacity());
		assertEquals(1, sb.index());
		assertEquals(_TEST_STRING, sb.toString());

		// Servlet output stream

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		ServletOutputStream servletOutputStream =
			bufferCacheServletResponse.getOutputStream();

		servletOutputStream.write(_TEST_BYTES);

		sb = bufferCacheServletResponse.getStringBundler();

		assertEquals(1, sb.capacity());
		assertEquals(1, sb.index());
		assertEquals(_TEST_STRING, sb.toString());
	}

	public void testGetWriter() {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse();

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		// Two gets

		PrintWriter printWriter1 = bufferCacheServletResponse.getWriter();
		PrintWriter printWriter2 = bufferCacheServletResponse.getWriter();

		assertSame(printWriter1, printWriter2);

		// Get printWriter after getting servlet output stream

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.getOutputStream();

		try {
			bufferCacheServletResponse.getWriter();
		}
		catch (IllegalStateException ise) {
		}
	}

	public void testIsByteCharMode() throws Exception {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setCharacterEncoding(String characterEncoding) {
				}

			};

		// Clean

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		assertFalse(bufferCacheServletResponse.isByteMode());
		assertFalse(bufferCacheServletResponse.isCharMode());

		// Byte buffer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.setByteBuffer(ByteBuffer.wrap(_TEST_BYTES));

		assertTrue(bufferCacheServletResponse.isByteMode());
		assertFalse(bufferCacheServletResponse.isCharMode());

		// Character buffer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.setCharBuffer(CharBuffer.wrap(_TEST_STRING));

		assertFalse(bufferCacheServletResponse.isByteMode());
		assertTrue(bufferCacheServletResponse.isCharMode());

		// Servlet output stream

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.getOutputStream();

		assertTrue(bufferCacheServletResponse.isByteMode());
		assertFalse(bufferCacheServletResponse.isCharMode());

		// Print writer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		bufferCacheServletResponse.getWriter();

		assertFalse(bufferCacheServletResponse.isByteMode());
		assertTrue(bufferCacheServletResponse.isCharMode());
	}

	public void testOutputBuffer() throws Exception {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

			};

		PropsUtil.setProps(
			new Props() {

				public boolean contains(String key) {
					return false;
				}

				public String get(String key) {
					return null;
				}

				public String get(String key, Filter filter) {
					return null;
				}

				public String[] getArray(String key) {
					return null;
				}

				public String[] getArray(String key, Filter filter) {
					return null;
				}

				public Properties getProperties() {
					return null;
				}

				public Properties getProperties(
					String prefix, boolean removePrefix) {

					return null;
				}

			}
		);

		// Clean

		BufferCacheServletResponse toResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		BufferCacheServletResponse fromResponse =
			new BufferCacheServletResponse(toResponse);

		fromResponse.outputBuffer();

		ByteBuffer newByteBuffer = toResponse.getByteBuffer();

		assertEquals(0, newByteBuffer.limit());

		// Byte buffer

		toResponse = new BufferCacheServletResponse(stubHttpServletResponse);

		fromResponse = new BufferCacheServletResponse(toResponse);

		ByteBuffer byteBuffer = ByteBuffer.wrap(_TEST_BYTES);

		fromResponse.setByteBuffer(byteBuffer);

		fromResponse.outputBuffer();

		newByteBuffer = toResponse.getByteBuffer();

		assertSame(byteBuffer, newByteBuffer);

		// Character buffer

		toResponse = new BufferCacheServletResponse(stubHttpServletResponse);

		fromResponse = new BufferCacheServletResponse(toResponse);

		CharBuffer charBuffer = CharBuffer.wrap(_TEST_STRING);

		fromResponse.setCharBuffer(charBuffer);

		fromResponse.outputBuffer();

		CharBuffer newCharBuffer = toResponse.getCharBuffer();

		assertSame(charBuffer, newCharBuffer);

		// Servlet output stream

		toResponse = new BufferCacheServletResponse(stubHttpServletResponse);

		fromResponse = new BufferCacheServletResponse(toResponse);

		ServletOutputStream servletOutputStream =
			fromResponse.getOutputStream();

		servletOutputStream.write(_TEST_BYTES);

		fromResponse.outputBuffer();

		newByteBuffer = toResponse.getByteBuffer();

		assertEquals(ByteBuffer.wrap(_TEST_BYTES), newByteBuffer);

		// Print writer

		toResponse = new BufferCacheServletResponse(stubHttpServletResponse);

		fromResponse = new BufferCacheServletResponse(toResponse);

		PrintWriter printWriter = fromResponse.getWriter();

		printWriter.write(_TEST_STRING);

		fromResponse.outputBuffer();

		newCharBuffer = toResponse.getCharBuffer();

		assertEquals(_TEST_STRING, newCharBuffer.toString());
	}

	public void testResetBuffer() throws Exception {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse();

		// Null out byte buffer

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		ByteBuffer byteBuffer = ByteBuffer.wrap(_TEST_BYTES);

		bufferCacheServletResponse.setByteBuffer(byteBuffer);

		assertSame(byteBuffer, bufferCacheServletResponse.getByteBuffer());

		bufferCacheServletResponse.resetBuffer(true);

		ByteBuffer newByteBuffer = bufferCacheServletResponse.getByteBuffer();

		assertNotSame(byteBuffer, newByteBuffer);
		assertEquals(0, newByteBuffer.capacity());

		// Null out character buffer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		CharBuffer charBuffer = CharBuffer.wrap(_TEST_STRING);

		bufferCacheServletResponse.setCharBuffer(charBuffer);

		assertSame(charBuffer, bufferCacheServletResponse.getCharBuffer());

		bufferCacheServletResponse.resetBuffer(true);

		CharBuffer newCharBuffer = bufferCacheServletResponse.getCharBuffer();

		assertNotSame(charBuffer, newCharBuffer);
		assertEquals(0, newCharBuffer.capacity());

		// Null out servlet output stream

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		ServletOutputStream servletOutputStream =
			bufferCacheServletResponse.getOutputStream();

		assertSame(
			servletOutputStream, bufferCacheServletResponse.getOutputStream());
		assertTrue(bufferCacheServletResponse.calledGetOutputStream);
		assertFalse(bufferCacheServletResponse.calledGetWriter);

		bufferCacheServletResponse.resetBuffer(true);

		assertFalse(bufferCacheServletResponse.calledGetOutputStream);
		assertFalse(bufferCacheServletResponse.calledGetWriter);
		assertNotSame(
			servletOutputStream, bufferCacheServletResponse.getOutputStream());
		assertTrue(bufferCacheServletResponse.calledGetOutputStream);
		assertFalse(bufferCacheServletResponse.calledGetWriter);

		// Null out print writer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		PrintWriter printWriter = bufferCacheServletResponse.getWriter();

		assertSame(printWriter, bufferCacheServletResponse.getWriter());
		assertFalse(bufferCacheServletResponse.calledGetOutputStream);
		assertTrue(bufferCacheServletResponse.calledGetWriter);

		bufferCacheServletResponse.resetBuffer(true);

		assertFalse(bufferCacheServletResponse.calledGetOutputStream);
		assertFalse(bufferCacheServletResponse.calledGetWriter);
		assertNotSame(printWriter, bufferCacheServletResponse.getWriter());
		assertFalse(bufferCacheServletResponse.calledGetOutputStream);
		assertTrue(bufferCacheServletResponse.calledGetWriter);

		// Reset byte buffer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		byteBuffer = ByteBuffer.wrap(_TEST_BYTES);

		bufferCacheServletResponse.setByteBuffer(byteBuffer);

		assertSame(byteBuffer, bufferCacheServletResponse.getByteBuffer());

		bufferCacheServletResponse.resetBuffer(false);

		newByteBuffer = bufferCacheServletResponse.getByteBuffer();

		assertNotSame(byteBuffer, newByteBuffer);
		assertEquals(0, newByteBuffer.capacity());

		// Reset character buffer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		charBuffer = CharBuffer.wrap(_TEST_STRING);

		bufferCacheServletResponse.setCharBuffer(charBuffer);

		assertSame(charBuffer, bufferCacheServletResponse.getCharBuffer());

		bufferCacheServletResponse.resetBuffer(false);

		newCharBuffer = bufferCacheServletResponse.getCharBuffer();

		assertNotSame(charBuffer, newCharBuffer);
		assertEquals(0, newCharBuffer.capacity());

		// Reset servlet output stream

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		servletOutputStream = bufferCacheServletResponse.getOutputStream();

		assertSame(
			servletOutputStream, bufferCacheServletResponse.getOutputStream());
		assertTrue(bufferCacheServletResponse.calledGetOutputStream);
		assertFalse(bufferCacheServletResponse.calledGetWriter);

		servletOutputStream.write(_TEST_BYTES);

		byteBuffer = bufferCacheServletResponse.getByteBuffer();

		assertEquals(ByteBuffer.wrap(_TEST_BYTES), byteBuffer);

		bufferCacheServletResponse.resetBuffer(false);

		assertSame(
			servletOutputStream, bufferCacheServletResponse.getOutputStream());
		assertTrue(bufferCacheServletResponse.calledGetOutputStream);
		assertFalse(bufferCacheServletResponse.calledGetWriter);

		byteBuffer = bufferCacheServletResponse.getByteBuffer();

		assertEquals(0, byteBuffer.limit());

		// Reset print writer

		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		printWriter = bufferCacheServletResponse.getWriter();

		assertSame(printWriter, bufferCacheServletResponse.getWriter());
		assertFalse(bufferCacheServletResponse.calledGetOutputStream);
		assertTrue(bufferCacheServletResponse.calledGetWriter);

		printWriter.write(_TEST_STRING);

		charBuffer = bufferCacheServletResponse.getCharBuffer();

		assertEquals(_TEST_STRING, charBuffer.toString());

		bufferCacheServletResponse.resetBuffer(false);

		assertSame(printWriter, bufferCacheServletResponse.getWriter());
		assertFalse(bufferCacheServletResponse.calledGetOutputStream);
		assertTrue(bufferCacheServletResponse.calledGetWriter);

		charBuffer = bufferCacheServletResponse.getCharBuffer();

		assertEquals(0, charBuffer.limit());
	}

	public void testSetBufferSize() throws IOException {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

			};

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		// Normal

		bufferCacheServletResponse.setBufferSize(1024);

		// Set after commit

		bufferCacheServletResponse.flushBuffer();

		try {
			bufferCacheServletResponse.setBufferSize(2048);

			fail();
		}
		catch (IllegalStateException ise) {
		}
	}

	public void testSetContentLength() {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse();

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setContentLength(1024);
	}

	public void testSetString() throws IOException {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse();

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setString(_TEST_STRING);

		assertEquals(_TEST_STRING, bufferCacheServletResponse.getString());
	}

	private static final byte[] _TEST_BYTES = {'a', 'b', 'c'};

	private static final String _TEST_STRING = "abc";

}