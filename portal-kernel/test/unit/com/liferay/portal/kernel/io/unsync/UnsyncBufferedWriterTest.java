/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		StringBuffer stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(0, stringBuffer.length());

		// Auto flush

		unsyncBufferedWriter.write("cd".toCharArray());

		Assert.assertEquals(2, unsyncBufferedWriter.count);
		Assert.assertEquals('c', unsyncBufferedWriter.buffer[0]);
		Assert.assertEquals('d', unsyncBufferedWriter.buffer[1]);

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(2, stringBuffer.length());
		Assert.assertEquals("ab", stringBuffer.toString());

		// Direct with auto flush

		unsyncBufferedWriter.write("efg".toCharArray());

		Assert.assertEquals(0, unsyncBufferedWriter.count);

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(7, stringBuffer.length());
		Assert.assertEquals("abcdefg", stringBuffer.toString());

		// Direct without auto flush

		unsyncBufferedWriter.write("hij".toCharArray());

		Assert.assertEquals(0, unsyncBufferedWriter.count);

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(10, stringBuffer.length());
		Assert.assertEquals("abcdefghij", stringBuffer.toString());
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
		catch (IOException ioException) {
		}

		try {
			unsyncBufferedWriter.write("abc".toCharArray(), 0, 3);

			Assert.fail();
		}
		catch (IOException ioException) {
		}

		try {
			unsyncBufferedWriter.write(1);

			Assert.fail();
		}
		catch (IOException ioException) {
		}

		try {
			unsyncBufferedWriter.write("abc", 0, 3);

			Assert.fail();
		}
		catch (IOException ioException) {
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
			(lineSeparator.length() * 2) + 1, unsyncBufferedWriter.count);

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

		StringBuffer stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(0, stringBuffer.length());

		// Auto flush

		unsyncBufferedWriter.write("cd");

		Assert.assertEquals(1, unsyncBufferedWriter.count);
		Assert.assertEquals('d', unsyncBufferedWriter.buffer[0]);

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(3, stringBuffer.length());
		Assert.assertEquals("abc", stringBuffer.toString());

		// Cycle

		unsyncBufferedWriter.write("efghi".toCharArray());

		Assert.assertEquals(0, unsyncBufferedWriter.count);

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(9, stringBuffer.length());
		Assert.assertEquals("abcdefghi", stringBuffer.toString());
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

		StringBuffer stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(0, stringBuffer.length());

		unsyncBufferedWriter.write('b');

		Assert.assertEquals(2, unsyncBufferedWriter.count);
		Assert.assertEquals('b', unsyncBufferedWriter.buffer[1]);

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(0, stringBuffer.length());

		unsyncBufferedWriter.write('c');

		Assert.assertEquals(3, unsyncBufferedWriter.count);
		Assert.assertEquals('c', unsyncBufferedWriter.buffer[2]);

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(0, stringBuffer.length());

		// Auto flush

		unsyncBufferedWriter.write('d');

		Assert.assertEquals(1, unsyncBufferedWriter.count);
		Assert.assertEquals('d', unsyncBufferedWriter.buffer[0]);

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(3, stringBuffer.length());
		Assert.assertEquals("abc", stringBuffer.toString());
	}

}