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

import java.util.Arrays;

import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncStringReaderTest extends TestCase {

	@Test
	public void testBlockRead() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader(
			"abcdefg");

		char[] chars = new char[4];

		assertEquals(4, unsyncStringReader.read(chars));
		assertEquals(4, unsyncStringReader.index);
		assertTrue(Arrays.equals("abcd".toCharArray(), chars));

		assertEquals(3, unsyncStringReader.read(chars));
		assertEquals('e', chars[0]);
		assertEquals('f', chars[1]);
		assertEquals('g', chars[2]);

		assertEquals(-1, unsyncStringReader.read(chars));
	}

	@Test
	public void testClose() {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader(
			"abcdefg");

		unsyncStringReader.close();

		assertTrue(unsyncStringReader.string == null);

		try {
			unsyncStringReader.mark(0);

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncStringReader.read();

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncStringReader.read(new char[5]);

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncStringReader.ready();

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			unsyncStringReader.reset();

			fail();
		}
		catch (IOException ioe) {
		}

		unsyncStringReader.close();
	}

	@Test
	public void testConstructor() {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		assertEquals("abc", unsyncStringReader.string);
		assertEquals(3, unsyncStringReader.stringLength);

		unsyncStringReader = new UnsyncStringReader("defg");

		assertEquals("defg", unsyncStringReader.string);
		assertEquals(4, unsyncStringReader.stringLength);
	}

	@Test
	public void testMarkAndReset() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		assertEquals('a', unsyncStringReader.read());

		unsyncStringReader.mark(-1);

		assertEquals('b', unsyncStringReader.read());
		assertEquals('c', unsyncStringReader.read());
		assertEquals(-1, unsyncStringReader.read());

		unsyncStringReader.reset();

		assertEquals('b', unsyncStringReader.read());
		assertEquals('c', unsyncStringReader.read());
		assertEquals(-1, unsyncStringReader.read());
	}

	@Test
	public void testMarkSupported() {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		assertTrue(unsyncStringReader.markSupported());
	}

	@Test
	public void testRead() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		assertEquals('a', unsyncStringReader.read());
		assertEquals('b', unsyncStringReader.read());
		assertEquals('c', unsyncStringReader.read());
		assertEquals(-1, unsyncStringReader.read());
	}

	@Test
	public void testSkip() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader(
			"abcdef");

		assertEquals('a', unsyncStringReader.read());
		assertEquals(2, unsyncStringReader.skip(2));
		assertEquals('d', unsyncStringReader.read());
		assertEquals(2, unsyncStringReader.skip(3));
		assertEquals(-1, unsyncStringReader.read());
	}

}