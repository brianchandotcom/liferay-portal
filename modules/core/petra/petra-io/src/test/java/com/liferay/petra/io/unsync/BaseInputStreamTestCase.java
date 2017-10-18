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
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public abstract class BaseInputStreamTestCase {

	@Test
	public void testReadNullByteArray() throws IOException {
		InputStream inputStream = getInputStream(_BYTES);

		try {
			inputStream.read(null, 0, 1);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}
	}

	@Test
	public void testReadOutOfBoundsLength() throws IOException {
		InputStream inputStream = getInputStream(_BYTES);

		try {
			inputStream.read(_BYTES, 3, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException ioobe) {
		}
	}

	@Test
	public void testReadOutOfBoundsNegativeLength() throws IOException {
		InputStream inputStream = getInputStream(_BYTES);

		try {
			inputStream.read(_BYTES, 0, -1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException ioobe) {
		}
	}

	@Test
	public void testReadOutOfBoundsNegativeOffset() throws IOException {
		InputStream inputStream = getInputStream(_BYTES);

		try {
			inputStream.read(_BYTES, -1, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException ioobe) {
		}
	}

	@Test
	public void testReadOutOfBoundsOffset() throws IOException {
		InputStream inputStream = getInputStream(_BYTES);

		try {
			inputStream.read(_BYTES, 4, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException ioobe) {
		}
	}

	@Test
	public void testReadOutOfBoundsOverflow() throws IOException {
		InputStream inputStream = getInputStream(_BYTES);

		try {
			inputStream.read(_BYTES, 1, Integer.MAX_VALUE);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException ioobe) {
		}
	}

	@Test
	public void testReadZeroLength() throws IOException {
		InputStream inputStream = getInputStream(_BYTES);

		Assert.assertEquals(0, inputStream.read(new byte[0], 0, 0));
	}

	protected abstract InputStream getInputStream(byte[] bytes);

	protected void testClose(InputStream inputStream, String message)
		throws IOException {

		try {
			inputStream.available();

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals(message, ioe.getMessage());
		}

		try {
			inputStream.read();

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals(message, ioe.getMessage());
		}

		try {
			inputStream.read(new byte[5]);

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals(message, ioe.getMessage());
		}

		try {
			inputStream.reset();

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals(message, ioe.getMessage());
		}

		try {
			inputStream.skip(0);

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals(message, ioe.getMessage());
		}

		inputStream.close();
	}

	private static final byte[] _BYTES = new byte[3];

}