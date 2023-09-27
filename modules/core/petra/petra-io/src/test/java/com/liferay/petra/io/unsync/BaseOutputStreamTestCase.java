/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (NullPointerException nullPointerException) {
		}
	}

	@Test
	public void testWriteOutOfBoundsLength() throws IOException {
		OutputStream outputStream = getOutputStream();

		try {
			outputStream.write(_BYTES, 3, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testWriteOutOfBoundsNegativeLength() throws IOException {
		OutputStream outputStream = getOutputStream();

		try {
			outputStream.write(_BYTES, 0, -1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testWriteOutOfBoundsNegativeOffset() throws IOException {
		OutputStream outputStream = getOutputStream();

		try {
			outputStream.write(_BYTES, -1, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testWriteOutOfBoundsOffset() throws IOException {
		OutputStream outputStream = getOutputStream();

		try {
			outputStream.write(_BYTES, 4, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testWriteOutOfBoundsOverflow() throws IOException {
		OutputStream outputStream = getOutputStream();

		try {
			outputStream.write(_BYTES, 1, Integer.MAX_VALUE);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
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