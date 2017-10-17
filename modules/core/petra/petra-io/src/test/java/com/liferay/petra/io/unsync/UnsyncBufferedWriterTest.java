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

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncBufferedWriterTest extends BaseWriterTestCase {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

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

		unsyncBufferedWriter.write("c".toCharArray());

		Assert.assertEquals(3, unsyncBufferedWriter.count);
		Assert.assertEquals('a', unsyncBufferedWriter.buffer[0]);
		Assert.assertEquals('b', unsyncBufferedWriter.buffer[1]);
		Assert.assertEquals('c', unsyncBufferedWriter.buffer[2]);

		Assert.assertEquals(0, stringWriter.getBuffer().length());

		// Auto flush

		unsyncBufferedWriter.write("de".toCharArray());

		Assert.assertEquals(2, unsyncBufferedWriter.count);
		Assert.assertEquals('d', unsyncBufferedWriter.buffer[0]);
		Assert.assertEquals('e', unsyncBufferedWriter.buffer[1]);
		Assert.assertEquals(3, stringWriter.getBuffer().length());
		Assert.assertEquals("abc", stringWriter.getBuffer().toString());

		// Direct with auto flush

		unsyncBufferedWriter.write("fgh".toCharArray());

		Assert.assertEquals(0, unsyncBufferedWriter.count);
		Assert.assertEquals(8, stringWriter.getBuffer().length());
		Assert.assertEquals("abcdefgh", stringWriter.getBuffer().toString());

		// Direct without auto flush

		unsyncBufferedWriter.write("ijk".toCharArray());

		Assert.assertEquals(0, unsyncBufferedWriter.count);
		Assert.assertEquals(11, stringWriter.getBuffer().length());
		Assert.assertEquals("abcdefghijk", stringWriter.getBuffer().toString());
	}

	@Test
	public void testClose() throws IOException {
		StringWriter stringWriter = new StringWriter();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			stringWriter, 10);

		Assert.assertNotNull(unsyncBufferedWriter.buffer);
		Assert.assertSame(stringWriter, unsyncBufferedWriter.writer);

		unsyncBufferedWriter.write("test");

		unsyncBufferedWriter.close();

		Assert.assertNull(unsyncBufferedWriter.buffer);
		Assert.assertNull(unsyncBufferedWriter.writer);

		Assert.assertEquals("test", stringWriter.toString());

		try {
			unsyncBufferedWriter.flush();

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals("Writer is null", ioe.getMessage());
		}

		try {
			unsyncBufferedWriter.write("abc".toCharArray(), 0, 3);

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals("Writer is null", ioe.getMessage());
		}

		try {
			unsyncBufferedWriter.write(1);

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals("Writer is null", ioe.getMessage());
		}

		try {
			unsyncBufferedWriter.write("abc", 0, 3);

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals("Writer is null", ioe.getMessage());
		}

		unsyncBufferedWriter.close();
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

		try {
			new UnsyncBufferedWriter(null, -1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Size is less than 1", iae.getMessage());
		}
	}

	@Test
	public void testFlush() throws IOException {
		StringWriter stringWriter = new StringWriter();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			stringWriter, 4);

		unsyncBufferedWriter.write("test");

		unsyncBufferedWriter.flush();

		Assert.assertEquals("test", stringWriter.toString());

		unsyncBufferedWriter.flush();

		Assert.assertEquals("test", stringWriter.toString());
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

	@Override
	protected Writer getWriter() {
		return new UnsyncBufferedWriter(new StringWriter());
	}

}