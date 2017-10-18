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

import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public abstract class BaseOutputStreamTestCase {

	@Test
	public void testFlush() throws IOException {
		OutputStream outputStream = getOutputStream();

		outputStream.flush();
	}

	@Test
	public void testWriteNullByteArray() throws IOException {
		OutputStream outputStream = getOutputStream();

		try {
			outputStream.write(null, 0, 1);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}
	}

	@Test
	public void testWriteOutOfBoundsLength() throws IOException {
		OutputStream outputStream = getOutputStream();

		try {
			outputStream.write(_BYTES, 3, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException ioobe) {
		}
	}

	@Test
	public void testWriteOutOfBoundsNegativeLength() throws IOException {
		OutputStream outputStream = getOutputStream();

		try {
			outputStream.write(_BYTES, 0, -1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException ioobe) {
		}
	}

	@Test
	public void testWriteOutOfBoundsNegativeOffset() throws IOException {
		OutputStream outputStream = getOutputStream();

		try {
			outputStream.write(_BYTES, -1, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException ioobe) {
		}
	}

	@Test
	public void testWriteOutOfBoundsOffset() throws IOException {
		OutputStream outputStream = getOutputStream();

		try {
			outputStream.write(_BYTES, 4, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException ioobe) {
		}
	}

	@Test
	public void testWriteOutOfBoundsOverflow() throws IOException {
		OutputStream outputStream = getOutputStream();

		try {
			outputStream.write(_BYTES, 1, Integer.MAX_VALUE);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException ioobe) {
		}
	}

	@Test
	public void testWriteZeroLengthString() throws IOException {
		OutputStream outputStream = getOutputStream();

		outputStream.write(_BYTES, 0, 0);
	}

	protected abstract OutputStream getOutputStream();

	private static final byte[] _BYTES = new byte[3];

}