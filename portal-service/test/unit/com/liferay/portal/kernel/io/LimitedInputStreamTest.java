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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.test.TestCase;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Shuyang Zhou
 */
public class LimitedInputStreamTest extends TestCase {

	public void testAvailable() throws IOException {
		LimitedInputStream limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 3);

		assertEquals(3, limitedInputStream.available());

		limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 5);

		assertEquals(5, limitedInputStream.available());
	}

	public void testClose() throws IOException {
		LimitedInputStream limitedInputStream = new LimitedInputStream(
			new BufferedInputStream(
				new ByteArrayInputStream(new byte[10])), 5, 3);

		limitedInputStream.close();

		try {
			limitedInputStream.skip(1);

			fail();
		}
		catch (IOException ioe) {
		}
	}

	public void testConstructor() throws IOException {

		// Negative offset

		try {
			new LimitedInputStream(
				new ByteArrayInputStream(new byte[10]), -1, 10);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// Negative length

		try {
			new LimitedInputStream(
				new ByteArrayInputStream(new byte[10]), 5, -1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// Not enough data to skip offset

		try {
			new LimitedInputStream(
				new ByteArrayInputStream(new byte[10]), 50, 10);

			fail();
		}
		catch (IOException ioe) {
		}

		// Normal

		new LimitedInputStream(new ByteArrayInputStream(new byte[10]), 5, 5);
	}

	public void testMarkSupported() throws IOException {
		LimitedInputStream limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 5);

		assertFalse(limitedInputStream.markSupported());
	}

	public void testRead() throws IOException {
		LimitedInputStream limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 5);

		for (int i = 0; i < 5; i++) {
			assertEquals(0, limitedInputStream.read());
		}

		assertEquals(-1, limitedInputStream.read());

		limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 3);

		for (int i = 0; i < 3; i++) {
			assertEquals(0, limitedInputStream.read());
		}

		assertEquals(-1, limitedInputStream.read());
	}

	public void testReadBlock() throws IOException {
		LimitedInputStream limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 5);

		byte[] buffer = new byte[4];

		assertEquals(4, limitedInputStream.read(buffer));
		assertEquals(1, limitedInputStream.read(buffer));
		assertEquals(-1, limitedInputStream.read(buffer));

		limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 3);

		assertEquals(3, limitedInputStream.read(buffer));
		assertEquals(-1, limitedInputStream.read(buffer));
	}

	public void testReadBlockWithRange() throws IOException {
		LimitedInputStream limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 5);

		byte[] buffer = new byte[6];

		assertEquals(4, limitedInputStream.read(buffer, 1, 4));
		assertEquals(1, limitedInputStream.read(buffer, 1, 4));
		assertEquals(-1, limitedInputStream.read(buffer, 1, 4));

		limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 3);

		assertEquals(3, limitedInputStream.read(buffer, 1, 4));
		assertEquals(-1, limitedInputStream.read(buffer, 1, 4));
	}

	public void testSkip() throws IOException {
		LimitedInputStream limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 3);

		assertEquals(3, limitedInputStream.skip(5));
		assertEquals(0, limitedInputStream.skip(5));

		limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 5);

		assertEquals(5, limitedInputStream.skip(5));
		assertEquals(0, limitedInputStream.skip(5));
	}

}