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

package com.liferay.portal.kernel.io.unsync;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncBufferedWriterTest {

	@Test
	public void testBlockWrite() throws IOException {
		StringWriter stringWriter = new StringWriter();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			stringWriter, 3);

		// Normal

		unsyncBufferedWriter.write("ab".toCharArray());

		Assert.assertEquals(2, unsyncBufferedWriter.count);
		Assert.assertEquals('a', unsyncBufferedWriter.buffer[0]);
		Assert.assertEquals('b', unsyncBufferedWriter.buffer[1]);
		Assert.assertEquals(0, stringWriter.getBuffer().length());

		// Auto flush

		unsyncBufferedWriter.write("cd".toCharArray());

		Assert.assertEquals(2, unsyncBufferedWriter.count);
		Assert.assertEquals('c', unsyncBufferedWriter.buffer[0]);
		Assert.assertEquals('d', unsyncBufferedWriter.buffer[1]);
		Assert.assertEquals(2, stringWriter.getBuffer().length());
		Assert.assertEquals("ab", stringWriter.getBuffer().toString());

		// Direct with auto flush

		unsyncBufferedWriter.write("efg".toCharArray());

		Assert.assertEquals(0, unsyncBufferedWriter.count);
		Assert.assertEquals(7, stringWriter.getBuffer().length());
		Assert.assertEquals("abcdefg", stringWriter.getBuffer().toString());

		// Direct without auto flush

		unsyncBufferedWriter.write("hij".toCharArray());

		Assert.assertEquals(0, unsyncBufferedWriter.count);
		Assert.assertEquals(10, stringWriter.getBuffer().length());
		Assert.assertEquals("abcdefghij", stringWriter.getBuffer().toString());
	}

	@Test
	public void testClose() throws IOException {
		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			new StringWriter());

		Assert.assertNotNull(unsyncBufferedWriter.buffer);
		Assert.assertNotNull(unsyncBufferedWriter.writer);

		unsyncBufferedWriter.close();

		Assert.assertNull(unsyncBufferedWriter.buffer);
		Assert.assertNull(unsyncBufferedWriter.writer);

		try {
			unsyncBufferedWriter.flush();

			Assert.fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedWriter.write("abc".toCharArray(), 0, 3);

			Assert.fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedWriter.write(1);

			Assert.fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedWriter.write("abc", 0, 3);

			Assert.fail();
		}
		catch (IOException ioe) {
		}
	}

	@Test
	public void testConstructor() {
		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			new StringWriter());

		Assert.assertEquals(8192, unsyncBufferedWriter.buffer.length);
		Assert.assertEquals(0, unsyncBufferedWriter.count);

		unsyncBufferedWriter = new UnsyncBufferedWriter(new StringWriter(), 10);

		Assert.assertEquals(10, unsyncBufferedWriter.buffer.length);
		Assert.assertEquals(0, unsyncBufferedWriter.count);
	}

	@Test
	public void testNewLine() throws IOException {
		StringWriter stringWriter = new StringWriter();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			stringWriter, 10);

		unsyncBufferedWriter.newLine();

		String lineSeparator = System.getProperty("line.separator");

		Assert.assertEquals(lineSeparator.length(), unsyncBufferedWriter.count);

		unsyncBufferedWriter.write('a');

		Assert.assertEquals(
			lineSeparator.length() + 1, unsyncBufferedWriter.count);

		unsyncBufferedWriter.newLine();

		Assert.assertEquals(
			lineSeparator.length() * 2 + 1, unsyncBufferedWriter.count);

		unsyncBufferedWriter.flush();

		Assert.assertEquals(
			lineSeparator + "a" + lineSeparator, stringWriter.toString());
	}

	@Test
	public void testStringWrite() throws IOException {
		StringWriter stringWriter = new StringWriter();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			stringWriter, 3);

		// Normal

		unsyncBufferedWriter.write("ab");

		Assert.assertEquals(2, unsyncBufferedWriter.count);
		Assert.assertEquals('a', unsyncBufferedWriter.buffer[0]);
		Assert.assertEquals('b', unsyncBufferedWriter.buffer[1]);
		Assert.assertEquals(0, stringWriter.getBuffer().length());

		// Auto flush

		unsyncBufferedWriter.write("cd");

		Assert.assertEquals(1, unsyncBufferedWriter.count);
		Assert.assertEquals('d', unsyncBufferedWriter.buffer[0]);
		Assert.assertEquals(3, stringWriter.getBuffer().length());
		Assert.assertEquals("abc", stringWriter.getBuffer().toString());

		// Cycle

		unsyncBufferedWriter.write("efghi".toCharArray());

		Assert.assertEquals(0, unsyncBufferedWriter.count);
		Assert.assertEquals(9, stringWriter.getBuffer().length());
		Assert.assertEquals("abcdefghi", stringWriter.getBuffer().toString());
	}

	@Test
	public void testWrite() throws IOException {
		StringWriter stringWriter = new StringWriter();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			stringWriter, 3);

		// Normal

		unsyncBufferedWriter.write('a');

		Assert.assertEquals(1, unsyncBufferedWriter.count);
		Assert.assertEquals('a', unsyncBufferedWriter.buffer[0]);
		Assert.assertEquals(0, stringWriter.getBuffer().length());

		unsyncBufferedWriter.write('b');

		Assert.assertEquals(2, unsyncBufferedWriter.count);
		Assert.assertEquals('b', unsyncBufferedWriter.buffer[1]);
		Assert.assertEquals(0, stringWriter.getBuffer().length());

		unsyncBufferedWriter.write('c');

		Assert.assertEquals(3, unsyncBufferedWriter.count);
		Assert.assertEquals('c', unsyncBufferedWriter.buffer[2]);
		Assert.assertEquals(0, stringWriter.getBuffer().length());

		// Auto flush

		unsyncBufferedWriter.write('d');

		Assert.assertEquals(1, unsyncBufferedWriter.count);
		Assert.assertEquals('d', unsyncBufferedWriter.buffer[0]);
		Assert.assertEquals(3, stringWriter.getBuffer().length());
		Assert.assertEquals("abc", stringWriter.getBuffer().toString());
	}

}