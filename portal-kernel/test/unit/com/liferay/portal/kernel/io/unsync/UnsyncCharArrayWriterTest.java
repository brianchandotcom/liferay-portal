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

package com.liferay.portal.kernel.io.unsync;

import com.liferay.petra.string.StringPool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import java.nio.CharBuffer;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncCharArrayWriterTest {

	@Test
	public void testAppendChar() {
		UnsyncCharArrayWriter unsyncCharArrayWriter =
			new UnsyncCharArrayWriter();

		unsyncCharArrayWriter.append('a');

		Assert.assertEquals(1, unsyncCharArrayWriter.size());
		Assert.assertEquals('a', unsyncCharArrayWriter.buffer[0]);

		unsyncCharArrayWriter.append('b');

		Assert.assertEquals(2, unsyncCharArrayWriter.size());
		Assert.assertEquals('a', unsyncCharArrayWriter.buffer[0]);
		Assert.assertEquals('b', unsyncCharArrayWriter.buffer[1]);
	}

	@Test
	public void testAppendCharSequence() {
		UnsyncCharArrayWriter unsyncCharArrayWriter =
			new UnsyncCharArrayWriter();

		unsyncCharArrayWriter.append(new StringBuilder("ab"));

		Assert.assertEquals(2, unsyncCharArrayWriter.size());
		Assert.assertEquals('a', unsyncCharArrayWriter.buffer[0]);
		Assert.assertEquals('b', unsyncCharArrayWriter.buffer[1]);

		unsyncCharArrayWriter.append(new StringBuilder("cd"));

		Assert.assertEquals(4, unsyncCharArrayWriter.size());
		Assert.assertEquals('a', unsyncCharArrayWriter.buffer[0]);
		Assert.assertEquals('b', unsyncCharArrayWriter.buffer[1]);
		Assert.assertEquals('c', unsyncCharArrayWriter.buffer[2]);
		Assert.assertEquals('d', unsyncCharArrayWriter.buffer[3]);
	}

	@Test
	public void testConstructor() {
		UnsyncCharArrayWriter unsyncCharArrayWriter =
			new UnsyncCharArrayWriter();

		Assert.assertEquals(0, unsyncCharArrayWriter.size());
		Assert.assertEquals(32, unsyncCharArrayWriter.buffer.length);

		unsyncCharArrayWriter = new UnsyncCharArrayWriter(64);

		Assert.assertEquals(0, unsyncCharArrayWriter.size());
		Assert.assertEquals(64, unsyncCharArrayWriter.buffer.length);
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
	public void testToCharBuffer() {
		UnsyncCharArrayWriter unsyncCharArrayWriter =
			new UnsyncCharArrayWriter();

		unsyncCharArrayWriter.write("test1");

		CharBuffer charBuffer = unsyncCharArrayWriter.toCharBuffer();

		Assert.assertEquals(unsyncCharArrayWriter.buffer, charBuffer.array());

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
	public void testWriteChar() {
		UnsyncCharArrayWriter unsyncCharArrayWriter =
			new UnsyncCharArrayWriter();

		unsyncCharArrayWriter.write('a');

		Assert.assertEquals(1, unsyncCharArrayWriter.size());
		Assert.assertEquals('a', unsyncCharArrayWriter.buffer[0]);

		unsyncCharArrayWriter.write('b');

		Assert.assertEquals(2, unsyncCharArrayWriter.size());
		Assert.assertEquals('a', unsyncCharArrayWriter.buffer[0]);
		Assert.assertEquals('b', unsyncCharArrayWriter.buffer[1]);
	}

	@Test
	public void testWriteCharArray() {
		UnsyncCharArrayWriter unsyncCharArrayWriter =
			new UnsyncCharArrayWriter();

		unsyncCharArrayWriter.write("ab".toCharArray());

		Assert.assertEquals(2, unsyncCharArrayWriter.size());
		Assert.assertEquals('a', unsyncCharArrayWriter.buffer[0]);
		Assert.assertEquals('b', unsyncCharArrayWriter.buffer[1]);

		unsyncCharArrayWriter.write("cd".toCharArray());

		Assert.assertEquals(4, unsyncCharArrayWriter.size());
		Assert.assertEquals('a', unsyncCharArrayWriter.buffer[0]);
		Assert.assertEquals('b', unsyncCharArrayWriter.buffer[1]);
		Assert.assertEquals('c', unsyncCharArrayWriter.buffer[2]);
		Assert.assertEquals('d', unsyncCharArrayWriter.buffer[3]);
	}

	@Test
	public void testWriteString() {
		UnsyncCharArrayWriter unsyncCharArrayWriter =
			new UnsyncCharArrayWriter();

		unsyncCharArrayWriter.write("ab");

		Assert.assertEquals(2, unsyncCharArrayWriter.size());
		Assert.assertEquals('a', unsyncCharArrayWriter.buffer[0]);
		Assert.assertEquals('b', unsyncCharArrayWriter.buffer[1]);

		unsyncCharArrayWriter.write("cd");

		Assert.assertEquals(4, unsyncCharArrayWriter.size());
		Assert.assertEquals('a', unsyncCharArrayWriter.buffer[0]);
		Assert.assertEquals('b', unsyncCharArrayWriter.buffer[1]);
		Assert.assertEquals('c', unsyncCharArrayWriter.buffer[2]);
		Assert.assertEquals('d', unsyncCharArrayWriter.buffer[3]);
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

}