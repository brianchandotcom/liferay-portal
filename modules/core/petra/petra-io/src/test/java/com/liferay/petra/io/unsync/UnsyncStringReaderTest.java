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

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncStringReaderTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testBlockRead() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader(
			"abcdefg");

		char[] chars = new char[4];

		Assert.assertEquals(4, unsyncStringReader.read(chars));

		Assert.assertEquals(4, unsyncStringReader.index);
		Assert.assertTrue(Arrays.equals("abcd".toCharArray(), chars));

		Assert.assertEquals(3, unsyncStringReader.read(chars));
		Assert.assertEquals('e', chars[0]);
		Assert.assertEquals('f', chars[1]);
		Assert.assertEquals('g', chars[2]);

		Assert.assertEquals(-1, unsyncStringReader.read(chars));
	}

	@Test
	public void testClose() {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader(
			"abcdefg");

		unsyncStringReader.close();

		Assert.assertTrue(unsyncStringReader.string == null);

		try {
			unsyncStringReader.mark(0);

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals("String is null", ioe.getMessage());
		}

		try {
			unsyncStringReader.read();

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals("String is null", ioe.getMessage());
		}

		try {
			unsyncStringReader.read(new char[5]);

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals("String is null", ioe.getMessage());
		}

		try {
			unsyncStringReader.ready();

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals("String is null", ioe.getMessage());
		}

		try {
			unsyncStringReader.reset();

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals("String is null", ioe.getMessage());
		}

		unsyncStringReader.close();
	}

	@Test
	public void testConstructor() {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		Assert.assertEquals("abc", unsyncStringReader.string);
		Assert.assertEquals(3, unsyncStringReader.stringLength);

		unsyncStringReader = new UnsyncStringReader("defg");

		Assert.assertEquals("defg", unsyncStringReader.string);
		Assert.assertEquals(4, unsyncStringReader.stringLength);
	}

	@Test
	public void testMarkAndReset() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		Assert.assertEquals('a', unsyncStringReader.read());

		unsyncStringReader.mark(-1);

		Assert.assertEquals('b', unsyncStringReader.read());
		Assert.assertEquals('c', unsyncStringReader.read());
		Assert.assertEquals(-1, unsyncStringReader.read());

		unsyncStringReader.reset();

		Assert.assertEquals('b', unsyncStringReader.read());
		Assert.assertEquals('c', unsyncStringReader.read());
		Assert.assertEquals(-1, unsyncStringReader.read());
	}

	@Test
	public void testMarkSupported() {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		Assert.assertTrue(unsyncStringReader.markSupported());
	}

	@Test
	public void testRead() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		Assert.assertEquals('a', unsyncStringReader.read());
		Assert.assertEquals('b', unsyncStringReader.read());
		Assert.assertEquals('c', unsyncStringReader.read());
		Assert.assertEquals(-1, unsyncStringReader.read());
	}

	@Test(expected = NullPointerException.class)
	public void testReadNullCharArray() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		unsyncStringReader.read(null, 0, 1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testReadOutOfBoundsLength() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		unsyncStringReader.read(new char[3], 3, 1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testReadOutOfBoundsNegativeLength() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		unsyncStringReader.read(new char[3], 0, -1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testReadOutOfBoundsNegativeOffset() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		unsyncStringReader.read(new char[3], -1, 1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testReadOutOfBoundsOffset() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		unsyncStringReader.read(new char[3], 4, 1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testReadOutOfBoundsOverflow() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		unsyncStringReader.read(new char[3], 1, Integer.MAX_VALUE);
	}

	@Test
	public void testReady() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		Assert.assertTrue(unsyncStringReader.ready());
	}

	@Test
	public void testReadZeroLength() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		Assert.assertEquals(0, unsyncStringReader.read(new char[0], 0, 0));
	}

	@Test
	public void testSkip() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader(
			"abcdef");

		Assert.assertEquals('a', unsyncStringReader.read());
		Assert.assertEquals(2, unsyncStringReader.skip(2));
		Assert.assertEquals('d', unsyncStringReader.read());
		Assert.assertEquals(2, unsyncStringReader.skip(3));
		Assert.assertEquals(-1, unsyncStringReader.read());
		Assert.assertEquals(0, unsyncStringReader.skip(3));
	}

}