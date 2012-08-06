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
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
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

		// ByteBuffer
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setByteBuffer(
			ByteBuffer.wrap(_TEST_BYTE_ARRAY));

		assertEquals(
			_TEST_BYTE_ARRAY.length,
			bufferCacheServletResponse.getBufferSize());

		// CharBuffer
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);
		bufferCacheServletResponse.setCharBuffer(CharBuffer.wrap(_TEST_STRING));

		assertEquals(
			_TEST_STRING.length(), bufferCacheServletResponse.getBufferSize());

		// ServletOutputStream
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		ServletOutputStream servletOutputStream =
			bufferCacheServletResponse.getOutputStream();

		servletOutputStream.write(_TEST_BYTE_ARRAY);

		assertEquals(
			_TEST_BYTE_ARRAY.length,
			bufferCacheServletResponse.getBufferSize());

		// PrintWriter
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		PrintWriter printWriter = bufferCacheServletResponse.getWriter();

		printWriter.print(_TEST_STRING);

		assertEquals(
			_TEST_STRING.length(), bufferCacheServletResponse.getBufferSize());

		// IOException on flushing
		OutputStream failFlushOutputStream = new UnsyncByteArrayOutputStream() {

			@Override
			public void flush() throws IOException {
				throw new IOException("Failed to flush");
			}

		};

		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

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

	public void testGetByteBuffer() throws IOException {
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

		// ByteBuffer
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		byteBuffer = ByteBuffer.wrap(_TEST_BYTE_ARRAY);

		bufferCacheServletResponse.setByteBuffer(byteBuffer);

		assertSame(byteBuffer, bufferCacheServletResponse.getByteBuffer());

		// CharBuffer
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setCharBuffer(CharBuffer.wrap(_TEST_STRING));

		byteBuffer = bufferCacheServletResponse.getByteBuffer();

		assertEquals(ByteBuffer.wrap(_TEST_BYTE_ARRAY), byteBuffer);

		// ServletOutputStream
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		ServletOutputStream servletOutputStream =
			bufferCacheServletResponse.getOutputStream();

		servletOutputStream.write(_TEST_BYTE_ARRAY);

		byteBuffer = bufferCacheServletResponse.getByteBuffer();

		assertEquals(ByteBuffer.wrap(_TEST_BYTE_ARRAY), byteBuffer);

		// PrintWriter

		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		PrintWriter printWriter = bufferCacheServletResponse.getWriter();

		printWriter.write(_TEST_STRING);

		byteBuffer = bufferCacheServletResponse.getByteBuffer();

		assertEquals(ByteBuffer.wrap(_TEST_BYTE_ARRAY), byteBuffer);
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

		// CharBuffer
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setCharBuffer(CharBuffer.wrap(_TEST_STRING));

		charBuffer = bufferCacheServletResponse.getCharBuffer();

		assertEquals(_TEST_STRING, charBuffer.toString());

		// ByteBuffer
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		bufferCacheServletResponse.setByteBuffer(
			ByteBuffer.wrap(_TEST_BYTE_ARRAY));

		charBuffer = bufferCacheServletResponse.getCharBuffer();

		assertEquals(_TEST_STRING, charBuffer.toString());

		// PrintWriter
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		PrintWriter printWriter = bufferCacheServletResponse.getWriter();

		printWriter.print(_TEST_STRING);

		charBuffer = bufferCacheServletResponse.getCharBuffer();

		assertEquals(_TEST_STRING, charBuffer.toString());

		// ServletOutputStream
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		ServletOutputStream servletOutputStream =
			bufferCacheServletResponse.getOutputStream();

		servletOutputStream.write(_TEST_BYTE_ARRAY);

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

		// Get ServletOutputStream after get PrintWriter
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

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

		String string = bufferCacheServletResponse.getString();

		assertEquals(StringPool.BLANK, string);

		// CharBuffer
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setCharBuffer(CharBuffer.wrap(_TEST_STRING));

		string = bufferCacheServletResponse.getString();

		assertEquals(_TEST_STRING, string);

		// ByteBuffer
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		bufferCacheServletResponse.setByteBuffer(
			ByteBuffer.wrap(_TEST_BYTE_ARRAY));

		string = bufferCacheServletResponse.getString();

		assertEquals(_TEST_STRING, string);

		// PrintWriter
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		PrintWriter printWriter = bufferCacheServletResponse.getWriter();

		printWriter.print(_TEST_STRING);

		string = bufferCacheServletResponse.getString();

		assertEquals(_TEST_STRING, string);

		// ServletOutputStream
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		ServletOutputStream servletOutputStream =
			bufferCacheServletResponse.getOutputStream();

		servletOutputStream.write(_TEST_BYTE_ARRAY);

		string = bufferCacheServletResponse.getString();

		assertEquals(_TEST_STRING, string);
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

		// CharBuffer
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setCharBuffer(CharBuffer.wrap(_TEST_STRING));

		sb = bufferCacheServletResponse.getStringBundler();

		assertEquals(1, sb.capacity());
		assertEquals(1, sb.index());
		assertEquals(_TEST_STRING, sb.toString());

		// ByteBuffer
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		bufferCacheServletResponse.setByteBuffer(
			ByteBuffer.wrap(_TEST_BYTE_ARRAY));

		sb = bufferCacheServletResponse.getStringBundler();

		assertEquals(1, sb.capacity());
		assertEquals(1, sb.index());
		assertEquals(_TEST_STRING, sb.toString());

		// PrintWriter
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		PrintWriter printWriter = bufferCacheServletResponse.getWriter();

		printWriter.print(_TEST_STRING);

		sb = bufferCacheServletResponse.getStringBundler();

		assertEquals(16, sb.capacity());
		assertEquals(1, sb.index());
		assertEquals(_TEST_STRING, sb.toString());

		// ServletOutputStream
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		ServletOutputStream servletOutputStream =
			bufferCacheServletResponse.getOutputStream();

		servletOutputStream.write(_TEST_BYTE_ARRAY);

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

		// Get PrintWriter after get ServletOutputStream
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

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

		// ByteBuffer
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setByteBuffer(
			ByteBuffer.wrap(_TEST_BYTE_ARRAY));

		assertTrue(bufferCacheServletResponse.isByteMode());
		assertFalse(bufferCacheServletResponse.isCharMode());

		// CharBuffer
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.setCharBuffer(CharBuffer.wrap(_TEST_STRING));

		assertFalse(bufferCacheServletResponse.isByteMode());
		assertTrue(bufferCacheServletResponse.isCharMode());

		// ServletOutputStream
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.getOutputStream();

		assertTrue(bufferCacheServletResponse.isByteMode());
		assertFalse(bufferCacheServletResponse.isCharMode());

		// PrintWriter
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		bufferCacheServletResponse.getWriter();

		assertFalse(bufferCacheServletResponse.isByteMode());
		assertTrue(bufferCacheServletResponse.isCharMode());
	}

	public void testOutputBuffer() throws Exception {
		// FinishResponse without output buffer, satisfy code coverage
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

			};

		// Mock
		PropsUtil.setProps(new Props() {

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
		});

		// Clean
		BufferCacheServletResponse toResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);
		BufferCacheServletResponse fromResponse =
			new BufferCacheServletResponse(toResponse);

		fromResponse.outputBuffer();

		ByteBuffer newByteBuffer = toResponse.getByteBuffer();
		assertEquals(0, newByteBuffer.limit());

		// ByteBuffer
		toResponse = new BufferCacheServletResponse(stubHttpServletResponse);
		fromResponse = new BufferCacheServletResponse(toResponse);

		ByteBuffer byteBuffer = ByteBuffer.wrap(_TEST_BYTE_ARRAY);

		fromResponse.setByteBuffer(byteBuffer);

		fromResponse.outputBuffer();

		newByteBuffer = toResponse.getByteBuffer();

		assertSame(byteBuffer, newByteBuffer);

		// CharBuffer
		toResponse = new BufferCacheServletResponse(stubHttpServletResponse);
		fromResponse = new BufferCacheServletResponse(toResponse);

		CharBuffer charBuffer = CharBuffer.wrap(_TEST_STRING);

		fromResponse.setCharBuffer(charBuffer);

		fromResponse.outputBuffer();

		CharBuffer newCharBuffer = toResponse.getCharBuffer();

		assertSame(charBuffer, newCharBuffer);

		// ServletOutputStream
		toResponse = new BufferCacheServletResponse(stubHttpServletResponse);
		fromResponse = new BufferCacheServletResponse(toResponse);

		ServletOutputStream servletOutputStream =
			fromResponse.getOutputStream();

		servletOutputStream.write(_TEST_BYTE_ARRAY);

		fromResponse.outputBuffer();

		newByteBuffer = toResponse.getByteBuffer();

		assertEquals(ByteBuffer.wrap(_TEST_BYTE_ARRAY), newByteBuffer);

		// PrintWriter
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

		// Null out ByteBuffer
		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		ByteBuffer byteBuffer = ByteBuffer.wrap(_TEST_BYTE_ARRAY);

		bufferCacheServletResponse.setByteBuffer(byteBuffer);

		assertSame(byteBuffer, bufferCacheServletResponse.getByteBuffer());

		bufferCacheServletResponse.resetBuffer(true);

		ByteBuffer newByteBuffer = bufferCacheServletResponse.getByteBuffer();
		assertNotSame(byteBuffer, newByteBuffer);
		assertEquals(0, newByteBuffer.capacity());

		// Null out CharBuffer
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		CharBuffer charBuffer = CharBuffer.wrap(_TEST_STRING);

		bufferCacheServletResponse.setCharBuffer(charBuffer);

		assertSame(charBuffer, bufferCacheServletResponse.getCharBuffer());

		bufferCacheServletResponse.resetBuffer(true);

		CharBuffer newCharBuffer = bufferCacheServletResponse.getCharBuffer();
		assertNotSame(charBuffer, newCharBuffer);
		assertEquals(0, newCharBuffer.capacity());

		// Null out ServletOutputStream
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

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

		// Null out PrintWriter
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

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

		// Reset ByteBuffer
		bufferCacheServletResponse = new BufferCacheServletResponse(
			stubHttpServletResponse);

		byteBuffer = ByteBuffer.wrap(_TEST_BYTE_ARRAY);

		bufferCacheServletResponse.setByteBuffer(byteBuffer);

		assertSame(byteBuffer, bufferCacheServletResponse.getByteBuffer());

		bufferCacheServletResponse.resetBuffer(false);

		newByteBuffer = bufferCacheServletResponse.getByteBuffer();

		assertNotSame(byteBuffer, newByteBuffer);
		assertEquals(0, newByteBuffer.capacity());

		// Reset CharBuffer
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		charBuffer = CharBuffer.wrap(_TEST_STRING);

		bufferCacheServletResponse.setCharBuffer(charBuffer);

		assertSame(charBuffer, bufferCacheServletResponse.getCharBuffer());

		bufferCacheServletResponse.resetBuffer(false);

		newCharBuffer = bufferCacheServletResponse.getCharBuffer();
		assertNotSame(charBuffer, newCharBuffer);
		assertEquals(0, newCharBuffer.capacity());

		// Reset ServletOutputStream
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

		servletOutputStream = bufferCacheServletResponse.getOutputStream();

		assertSame(
			servletOutputStream, bufferCacheServletResponse.getOutputStream());
		assertTrue(bufferCacheServletResponse.calledGetOutputStream);
		assertFalse(bufferCacheServletResponse.calledGetWriter);

		servletOutputStream.write(_TEST_BYTE_ARRAY);

		byteBuffer = bufferCacheServletResponse.getByteBuffer();

		assertEquals(ByteBuffer.wrap(_TEST_BYTE_ARRAY), byteBuffer);

		bufferCacheServletResponse.resetBuffer(false);

		assertSame(
			servletOutputStream, bufferCacheServletResponse.getOutputStream());
		assertTrue(bufferCacheServletResponse.calledGetOutputStream);
		assertFalse(bufferCacheServletResponse.calledGetWriter);

		byteBuffer = bufferCacheServletResponse.getByteBuffer();

		assertEquals(0, byteBuffer.limit());

		// Reset PrintWriter
		bufferCacheServletResponse =
			new BufferCacheServletResponse(stubHttpServletResponse);

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

	public void testSetBufferSize() {
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
		// Satisfy code coverage
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

	private static final byte[] _TEST_BYTE_ARRAY = new byte[] {'a', 'b', 'c'};
	private static final String _TEST_STRING = "abc";

}