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

import com.liferay.portal.kernel.test.TestCase;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncBufferedWriterTest extends TestCase {

	@Test
	public void testBlockWrite() throws IOException {
		StringWriter stringWriter = new StringWriter();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			stringWriter, 3);

		// Normal

		unsyncBufferedWriter.write("ab".toCharArray());

		assertEquals(2, unsyncBufferedWriter.count);
		assertEquals('a', unsyncBufferedWriter.buffer[0]);
		assertEquals('b', unsyncBufferedWriter.buffer[1]);
		assertEquals(0, stringWriter.getBuffer().length());

		// Auto flush

		unsyncBufferedWriter.write("cd".toCharArray());

		assertEquals(2, unsyncBufferedWriter.count);
		assertEquals('c', unsyncBufferedWriter.buffer[0]);
		assertEquals('d', unsyncBufferedWriter.buffer[1]);
		assertEquals(2, stringWriter.getBuffer().length());
		assertEquals("ab", stringWriter.getBuffer().toString());

		// Direct with auto flush

		unsyncBufferedWriter.write("efg".toCharArray());

		assertEquals(0, unsyncBufferedWriter.count);
		assertEquals(7, stringWriter.getBuffer().length());
		assertEquals("abcdefg", stringWriter.getBuffer().toString());

		// Direct without auto flush

		unsyncBufferedWriter.write("hij".toCharArray());

		assertEquals(0, unsyncBufferedWriter.count);
		assertEquals(10, stringWriter.getBuffer().length());
		assertEquals("abcdefghij", stringWriter.getBuffer().toString());
	}

	@Test
	public void testClose() throws IOException {
		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			new StringWriter());

		assertNotNull(unsyncBufferedWriter.buffer);
		assertNotNull(unsyncBufferedWriter.writer);

		unsyncBufferedWriter.close();

		assertNull(unsyncBufferedWriter.buffer);
		assertNull(unsyncBufferedWriter.writer);

		try {
			unsyncBufferedWriter.flush();

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedWriter.write("abc".toCharArray(), 0, 3);

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedWriter.write(1);

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncBufferedWriter.write("abc", 0, 3);

			fail();
		}
		catch (IOException ioe) {
		}
	}

	@Test
	public void testConstructor() {
		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			new StringWriter());

		assertEquals(8192, unsyncBufferedWriter.buffer.length);
		assertEquals(0, unsyncBufferedWriter.count);

		unsyncBufferedWriter = new UnsyncBufferedWriter(new StringWriter(), 10);

		assertEquals(10, unsyncBufferedWriter.buffer.length);
		assertEquals(0, unsyncBufferedWriter.count);
	}

	@Test
	public void testNewLine() throws IOException {
		StringWriter stringWriter = new StringWriter();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			stringWriter, 10);

		unsyncBufferedWriter.newLine();

		String lineSeparator = System.getProperty("line.separator");

		assertEquals(lineSeparator.length(), unsyncBufferedWriter.count);

		unsyncBufferedWriter.write('a');

		assertEquals(lineSeparator.length() + 1, unsyncBufferedWriter.count);

		unsyncBufferedWriter.newLine();

		assertEquals(
			lineSeparator.length() * 2 + 1, unsyncBufferedWriter.count);

		unsyncBufferedWriter.flush();

		assertEquals(
			lineSeparator + "a" + lineSeparator, stringWriter.toString());
	}

	@Test
	public void testStringWrite() throws IOException {
		StringWriter stringWriter = new StringWriter();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			stringWriter, 3);

		// Normal

		unsyncBufferedWriter.write("ab");

		assertEquals(2, unsyncBufferedWriter.count);
		assertEquals('a', unsyncBufferedWriter.buffer[0]);
		assertEquals('b', unsyncBufferedWriter.buffer[1]);
		assertEquals(0, stringWriter.getBuffer().length());

		// Auto flush

		unsyncBufferedWriter.write("cd");

		assertEquals(1, unsyncBufferedWriter.count);
		assertEquals('d', unsyncBufferedWriter.buffer[0]);
		assertEquals(3, stringWriter.getBuffer().length());
		assertEquals("abc", stringWriter.getBuffer().toString());

		// Cycle

		unsyncBufferedWriter.write("efghi".toCharArray());

		assertEquals(0, unsyncBufferedWriter.count);
		assertEquals(9, stringWriter.getBuffer().length());
		assertEquals("abcdefghi", stringWriter.getBuffer().toString());
	}

	@Test
	public void testWrite() throws IOException {
		StringWriter stringWriter = new StringWriter();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			stringWriter, 3);

		// Normal

		unsyncBufferedWriter.write('a');

		assertEquals(1, unsyncBufferedWriter.count);
		assertEquals('a', unsyncBufferedWriter.buffer[0]);
		assertEquals(0, stringWriter.getBuffer().length());

		unsyncBufferedWriter.write('b');

		assertEquals(2, unsyncBufferedWriter.count);
		assertEquals('b', unsyncBufferedWriter.buffer[1]);
		assertEquals(0, stringWriter.getBuffer().length());

		unsyncBufferedWriter.write('c');

		assertEquals(3, unsyncBufferedWriter.count);
		assertEquals('c', unsyncBufferedWriter.buffer[2]);
		assertEquals(0, stringWriter.getBuffer().length());

		// Auto flush

		unsyncBufferedWriter.write('d');

		assertEquals(1, unsyncBufferedWriter.count);
		assertEquals('d', unsyncBufferedWriter.buffer[0]);
		assertEquals(3, stringWriter.getBuffer().length());
		assertEquals("abc", stringWriter.getBuffer().toString());
	}

}