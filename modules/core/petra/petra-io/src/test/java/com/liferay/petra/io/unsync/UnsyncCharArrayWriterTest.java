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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import java.lang.reflect.Field;

import java.nio.CharBuffer;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncCharArrayWriterTest extends BaseWriterTestCase {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(BoundaryCheckerUtil.class);
			}

		};

	@Test
	public void testAppendChar() throws Exception {
		UnsyncCharArrayWriter unsyncCharArrayWriter =
			new UnsyncCharArrayWriter();

		char[] buffer = (char[])_bufferField.get(unsyncCharArrayWriter);

		unsyncCharArrayWriter.append('a');

		Assert.assertEquals(1, unsyncCharArrayWriter.size());

		Assert.assertEquals('a', buffer[0]);

		unsyncCharArrayWriter.append('b');

		Assert.assertEquals(2, unsyncCharArrayWriter.size());

		Assert.assertEquals('a', buffer[0]);
		Assert.assertEquals('b', buffer[1]);
	}

	@Test
	public void testAppendCharSequence() throws Exception {
		UnsyncCharArrayWriter unsyncCharArrayWriter =
			new UnsyncCharArrayWriter();

		char[] buffer = (char[])_bufferField.get(unsyncCharArrayWriter);

		unsyncCharArrayWriter.append(new StringBuilder("ab"));

		Assert.assertEquals(2, unsyncCharArrayWriter.size());

		Assert.assertEquals('a', buffer[0]);
		Assert.assertEquals('b', buffer[1]);

		unsyncCharArrayWriter.append(new StringBuilder("abcd"), 2, 4);

		Assert.assertEquals(4, unsyncCharArrayWriter.size());
		Assert.assertEquals('a', buffer[0]);
		Assert.assertEquals('b', buffer[1]);
		Assert.assertEquals('c', buffer[2]);
		Assert.assertEquals('d', buffer[3]);

		unsyncCharArrayWriter.reset();

		unsyncCharArrayWriter.append(null);

		Assert.assertEquals(4, unsyncCharArrayWriter.size());
		Assert.assertEquals('n', buffer[0]);
		Assert.assertEquals('u', buffer[1]);
		Assert.assertEquals('l', buffer[2]);
		Assert.assertEquals('l', buffer[3]);

		unsyncCharArrayWriter.reset();

		unsyncCharArrayWriter.append(null, 0, 4);

		Assert.assertEquals(4, unsyncCharArrayWriter.size());
		Assert.assertEquals('n', buffer[0]);
		Assert.assertEquals('u', buffer[1]);
		Assert.assertEquals('l', buffer[2]);
		Assert.assertEquals('l', buffer[3]);
	}

	@Test
	public void testConstructor() throws Exception {
		new BoundaryCheckerUtil();

		UnsyncCharArrayWriter unsyncCharArrayWriter =
			new UnsyncCharArrayWriter();

		char[] buffer = (char[])_bufferField.get(unsyncCharArrayWriter);

		Assert.assertEquals(0, unsyncCharArrayWriter.size());

		Assert.assertEquals(Arrays.toString(buffer), 32, buffer.length);

		unsyncCharArrayWriter = new UnsyncCharArrayWriter(64);

		buffer = (char[])_bufferField.get(unsyncCharArrayWriter);

		Assert.assertEquals(0, unsyncCharArrayWriter.size());

		Assert.assertEquals(Arrays.toString(buffer), 64, buffer.length);
	}

	@Test
	public void testFlushAndClose() {
		UnsyncCharArrayWriter unsyncCharArrayWriter =
			new UnsyncCharArrayWriter();

		unsyncCharArrayWriter.flush();

		unsyncCharArrayWriter.close();
	}

	@Test
	public void testReset() {
		UnsyncCharArrayWriter unsyncCharArrayWriter =
			new UnsyncCharArrayWriter();

		unsyncCharArrayWriter.write("test1");

		Assert.assertEquals(5, unsyncCharArrayWriter.size());

		unsyncCharArrayWriter.reset();

		Assert.assertEquals(0, unsyncCharArrayWriter.size());
	}

	@Test
	public void testToCharBuffer() throws Exception {
		UnsyncCharArrayWriter unsyncCharArrayWriter =
			new UnsyncCharArrayWriter();

		char[] buffer = (char[])_bufferField.get(unsyncCharArrayWriter);

		unsyncCharArrayWriter.write("test1");

		CharBuffer charBuffer = unsyncCharArrayWriter.toCharBuffer();

		Assert.assertSame(buffer, charBuffer.array());

		Assert.assertEquals(0, charBuffer.position());
		Assert.assertEquals(5, charBuffer.limit());
		Assert.assertEquals("test1", charBuffer.toString());
	}

	@Test
	public void testToString() {
		UnsyncCharArrayWriter unsyncCharArrayWriter =
			new UnsyncCharArrayWriter();

		unsyncCharArrayWriter.write("test1");

		Assert.assertEquals("test1", unsyncCharArrayWriter.toString());
	}

	@Test
	public void testWriteChar() throws Exception {
		UnsyncCharArrayWriter unsyncCharArrayWriter = new UnsyncCharArrayWriter(
			1);

		char[] buffer = (char[])_bufferField.get(unsyncCharArrayWriter);

		unsyncCharArrayWriter.write('a');

		Assert.assertEquals(1, unsyncCharArrayWriter.size());

		Assert.assertEquals('a', buffer[0]);

		unsyncCharArrayWriter.write('b');

		buffer = (char[])_bufferField.get(unsyncCharArrayWriter);

		Assert.assertEquals(2, unsyncCharArrayWriter.size());
		Assert.assertEquals('a', buffer[0]);
		Assert.assertEquals('b', buffer[1]);
	}

	@Test
	public void testWriteCharArray() throws Exception {
		UnsyncCharArrayWriter unsyncCharArrayWriter = new UnsyncCharArrayWriter(
			3);

		char[] buffer = (char[])_bufferField.get(unsyncCharArrayWriter);

		unsyncCharArrayWriter.write("ab".toCharArray());

		Assert.assertEquals(2, unsyncCharArrayWriter.size());

		Assert.assertEquals('a', buffer[0]);
		Assert.assertEquals('b', buffer[1]);

		unsyncCharArrayWriter.write("cd".toCharArray());

		buffer = (char[])_bufferField.get(unsyncCharArrayWriter);

		Assert.assertEquals(4, unsyncCharArrayWriter.size());

		Assert.assertEquals('a', buffer[0]);
		Assert.assertEquals('b', buffer[1]);
		Assert.assertEquals('c', buffer[2]);
		Assert.assertEquals('d', buffer[3]);
	}

	@Test
	public void testWriteString() throws Exception {
		UnsyncCharArrayWriter unsyncCharArrayWriter = new UnsyncCharArrayWriter(
			3);

		char[] buffer1 = (char[])_bufferField.get(unsyncCharArrayWriter);

		unsyncCharArrayWriter.write("ab");

		Assert.assertEquals(2, unsyncCharArrayWriter.size());

		Assert.assertEquals('a', buffer1[0]);
		Assert.assertEquals('b', buffer1[1]);

		unsyncCharArrayWriter.write("cd");

		char[] buffer2 = (char[])_bufferField.get(unsyncCharArrayWriter);

		Assert.assertNotSame(buffer1, buffer2);

		Assert.assertEquals(4, unsyncCharArrayWriter.size());

		Assert.assertEquals('a', buffer2[0]);
		Assert.assertEquals('b', buffer2[1]);
		Assert.assertEquals('c', buffer2[2]);
		Assert.assertEquals('d', buffer2[3]);
	}

	@Test
	public void testWriteTo() throws IOException {
		UnsyncCharArrayWriter unsyncCharArrayWriter =
			new UnsyncCharArrayWriter();

		unsyncCharArrayWriter.write("abcd");

		CharBuffer charBuffer = CharBuffer.allocate(2);

		int length = unsyncCharArrayWriter.writeTo(charBuffer);

		Assert.assertEquals(2, length);

		Assert.assertEquals(2, charBuffer.position());
		Assert.assertEquals(2, charBuffer.limit());

		charBuffer.position(0);

		Assert.assertEquals("ab", charBuffer.toString());

		charBuffer = CharBuffer.allocate(5);

		length = unsyncCharArrayWriter.writeTo(charBuffer);

		Assert.assertEquals(4, length);

		Assert.assertEquals(4, charBuffer.position());
		Assert.assertEquals(5, charBuffer.limit());

		charBuffer.position(0);
		charBuffer.limit(4);

		Assert.assertEquals("abcd", charBuffer.toString());

		charBuffer = CharBuffer.allocate(0);

		length = unsyncCharArrayWriter.writeTo(charBuffer);

		Assert.assertEquals(0, length);

		Assert.assertEquals(0, charBuffer.position());
		Assert.assertEquals(0, charBuffer.limit());

		charBuffer.position(0);

		Assert.assertEquals("", charBuffer.toString());

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		length = unsyncCharArrayWriter.writeTo(
			byteArrayOutputStream, StringPool.UTF8);

		Assert.assertEquals(4, length);

		Assert.assertEquals(4, byteArrayOutputStream.size());
		Assert.assertTrue(
			Arrays.equals(
				"abcd".getBytes(), byteArrayOutputStream.toByteArray()));

		StringWriter stringWriter = new StringWriter();

		length = unsyncCharArrayWriter.writeTo(stringWriter);

		Assert.assertEquals(4, length);

		Assert.assertEquals("abcd", stringWriter.toString());
	}

	@Override
	protected Writer getWriter() {
		return new UnsyncCharArrayWriter();
	}

	private static final Field _bufferField = ReflectionTestUtil.getField(
		UnsyncCharArrayWriter.class, "_buffer");

}