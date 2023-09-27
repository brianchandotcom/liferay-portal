/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (NullPointerException nullPointerException) {
		}
	}

	@Test
	public void testReadOutOfBoundsLength() throws IOException {
		InputStream inputStream = getInputStream(_BYTES);

		try {
			inputStream.read(_BYTES, 3, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testReadOutOfBoundsNegativeLength() throws IOException {
		InputStream inputStream = getInputStream(_BYTES);

		try {
			inputStream.read(_BYTES, 0, -1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testReadOutOfBoundsNegativeOffset() throws IOException {
		InputStream inputStream = getInputStream(_BYTES);

		try {
			inputStream.read(_BYTES, -1, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testReadOutOfBoundsOffset() throws IOException {
		InputStream inputStream = getInputStream(_BYTES);

		try {
			inputStream.read(_BYTES, 4, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testReadOutOfBoundsOverflow() throws IOException {
		InputStream inputStream = getInputStream(_BYTES);

		try {
			inputStream.read(_BYTES, 1, Integer.MAX_VALUE);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
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
		catch (IOException ioException) {
			Assert.assertEquals(message, ioException.getMessage());
		}

		try {
			inputStream.read();

			Assert.fail();
		}
		catch (IOException ioException) {
			Assert.assertEquals(message, ioException.getMessage());
		}

		try {
			inputStream.read(new byte[5]);

			Assert.fail();
		}
		catch (IOException ioException) {
			Assert.assertEquals(message, ioException.getMessage());
		}

		try {
			inputStream.reset();

			Assert.fail();
		}
		catch (IOException ioException) {
			Assert.assertEquals(message, ioException.getMessage());
		}

		try {
			inputStream.skip(0);

			Assert.fail();
		}
		catch (IOException ioException) {
			Assert.assertEquals(message, ioException.getMessage());
		}

		inputStream.close();
	}

	private static final byte[] _BYTES = new byte[3];

}