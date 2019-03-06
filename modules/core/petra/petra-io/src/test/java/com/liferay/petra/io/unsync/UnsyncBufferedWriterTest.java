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

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncBufferedWriterTest extends BaseWriterTestCase {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(BoundaryCheckerUtil.class);
			}

		};

	@Test
	public void testBlockWrite() throws Exception {
		StringWriter stringWriter = new StringWriter();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			stringWriter, 3);

		// Normal

		unsyncBufferedWriter.write("ab".toCharArray());

		char[] buffer = (char[])_bufferField.get(unsyncBufferedWriter);

		Assert.assertEquals(2, _countField.getInt(unsyncBufferedWriter));

		Assert.assertEquals('a', buffer[0]);
		Assert.assertEquals('b', buffer[1]);

		StringBuffer stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(0, stringBuffer.length());

		unsyncBufferedWriter.write("c".toCharArray());

		buffer = (char[])_bufferField.get(unsyncBufferedWriter);

		Assert.assertEquals(3, _countField.getInt(unsyncBufferedWriter));
		Assert.assertEquals('a', buffer[0]);
		Assert.assertEquals('b', buffer[1]);
		Assert.assertEquals('c', buffer[2]);

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(0, stringBuffer.length());

		// Auto flush

		unsyncBufferedWriter.write("de".toCharArray());

		buffer = (char[])_bufferField.get(unsyncBufferedWriter);

		Assert.assertEquals(2, _countField.getInt(unsyncBufferedWriter));
		Assert.assertEquals('d', buffer[0]);
		Assert.assertEquals('e', buffer[1]);

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(3, stringBuffer.length());
		Assert.assertEquals("abc", stringBuffer.toString());

		// Direct with auto flush

		unsyncBufferedWriter.write("fgh".toCharArray());

		buffer = (char[])_bufferField.get(unsyncBufferedWriter);

		Assert.assertEquals(0, _countField.getInt(unsyncBufferedWriter));

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(8, stringBuffer.length());
		Assert.assertEquals("abcdefgh", stringBuffer.toString());

		// Direct without auto flush

		unsyncBufferedWriter.write("ijk".toCharArray());

		Assert.assertEquals(0, _countField.getInt(unsyncBufferedWriter));

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(11, stringBuffer.length());
		Assert.assertEquals("abcdefghijk", stringBuffer.toString());
	}

	@Test
	public void testClose() throws Exception {
		StringWriter stringWriter = new StringWriter();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			stringWriter, 10);

		Assert.assertNotNull(_bufferField.get(unsyncBufferedWriter));
		Assert.assertSame(stringWriter, _writerField.get(unsyncBufferedWriter));

		unsyncBufferedWriter.write("test");

		unsyncBufferedWriter.close();

		Assert.assertNull(_bufferField.get(unsyncBufferedWriter));
		Assert.assertNull(_writerField.get(unsyncBufferedWriter));

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
	public void testConstructor() throws Exception {
		new BoundaryCheckerUtil();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			new StringWriter());

		char[] buffer = (char[])_bufferField.get(unsyncBufferedWriter);

		Assert.assertEquals(Arrays.toString(buffer), 8192, buffer.length);

		Assert.assertEquals(0, _countField.getInt(unsyncBufferedWriter));

		unsyncBufferedWriter = new UnsyncBufferedWriter(new StringWriter(), 10);

		buffer = (char[])_bufferField.get(unsyncBufferedWriter);

		Assert.assertEquals(Arrays.toString(buffer), 10, buffer.length);

		Assert.assertEquals(0, _countField.getInt(unsyncBufferedWriter));

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
	public void testNewLine() throws Exception {
		StringWriter stringWriter = new StringWriter();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			stringWriter, 10);

		unsyncBufferedWriter.newLine();

		String lineSeparator = System.getProperty("line.separator");

		Assert.assertEquals(
			lineSeparator.length(), _countField.getInt(unsyncBufferedWriter));

		unsyncBufferedWriter.write('a');

		Assert.assertEquals(
			lineSeparator.length() + 1,
			_countField.getInt(unsyncBufferedWriter));

		unsyncBufferedWriter.newLine();

		Assert.assertEquals(
			lineSeparator.length() * 2 + 1,
			_countField.getInt(unsyncBufferedWriter));

		unsyncBufferedWriter.flush();

		Assert.assertEquals(
			lineSeparator + "a" + lineSeparator, stringWriter.toString());
	}

	@Test
	public void testStringWrite() throws Exception {
		StringWriter stringWriter = new StringWriter();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			stringWriter, 3);

		// Normal

		unsyncBufferedWriter.write("ab");

		char[] buffer = (char[])_bufferField.get(unsyncBufferedWriter);

		Assert.assertEquals(2, _countField.getInt(unsyncBufferedWriter));

		Assert.assertEquals('a', buffer[0]);
		Assert.assertEquals('b', buffer[1]);

		StringBuffer stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(0, stringBuffer.length());

		// Auto flush

		unsyncBufferedWriter.write("cd");

		buffer = (char[])_bufferField.get(unsyncBufferedWriter);

		Assert.assertEquals(1, _countField.getInt(unsyncBufferedWriter));

		Assert.assertEquals('d', buffer[0]);

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(3, stringBuffer.length());
		Assert.assertEquals("abc", stringBuffer.toString());

		// Cycle

		unsyncBufferedWriter.write("efghi".toCharArray());

		Assert.assertEquals(0, _countField.getInt(unsyncBufferedWriter));

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(9, stringBuffer.length());
		Assert.assertEquals("abcdefghi", stringBuffer.toString());
	}

	@Test
	public void testWrite() throws Exception {
		StringWriter stringWriter = new StringWriter();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			stringWriter, 3);

		// Normal

		unsyncBufferedWriter.write('a');

		char[] buffer = (char[])_bufferField.get(unsyncBufferedWriter);

		Assert.assertEquals(1, _countField.getInt(unsyncBufferedWriter));

		Assert.assertEquals('a', buffer[0]);

		StringBuffer stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(0, stringBuffer.length());

		unsyncBufferedWriter.write('b');

		Assert.assertEquals(2, _countField.getInt(unsyncBufferedWriter));
		Assert.assertEquals('b', buffer[1]);

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(0, stringBuffer.length());

		unsyncBufferedWriter.write('c');

		Assert.assertEquals(3, _countField.getInt(unsyncBufferedWriter));
		Assert.assertEquals('c', buffer[2]);

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(0, stringBuffer.length());

		// Auto flush

		unsyncBufferedWriter.write('d');

		buffer = (char[])_bufferField.get(unsyncBufferedWriter);

		Assert.assertEquals(1, _countField.getInt(unsyncBufferedWriter));
		Assert.assertEquals('d', buffer[0]);

		stringBuffer = stringWriter.getBuffer();

		Assert.assertEquals(3, stringBuffer.length());
		Assert.assertEquals("abc", stringBuffer.toString());
	}

	@Override
	protected Writer getWriter() {
		return new UnsyncBufferedWriter(new StringWriter());
	}

	private static final Field _bufferField = ReflectionTestUtil.getField(
		UnsyncBufferedWriter.class, "_buffer");
	private static final Field _countField = ReflectionTestUtil.getField(
		UnsyncBufferedWriter.class, "_count");
	private static final Field _writerField = ReflectionTestUtil.getField(
		UnsyncBufferedWriter.class, "_writer");

}