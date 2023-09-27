/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.internal.dao.jdbc;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class LimitedInputStreamTest {

	@Test
	public void testAvailable() throws IOException {
		LimitedInputStream limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 3);

		Assert.assertEquals(3, limitedInputStream.available());

		limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 5);

		Assert.assertEquals(5, limitedInputStream.available());
	}

	@Test
	public void testClose() throws IOException {
		LimitedInputStream limitedInputStream = new LimitedInputStream(
			new BufferedInputStream(new ByteArrayInputStream(new byte[10])), 5,
			3);

		limitedInputStream.close();
	}

	@Test(expected = IOException.class)
	public void testClosed() throws IOException {
		LimitedInputStream limitedInputStream = new LimitedInputStream(
			new BufferedInputStream(new ByteArrayInputStream(new byte[10])), 5,
			3);

		limitedInputStream.close();

		limitedInputStream.skip(1);
	}

	@Test
	public void testConstructor() throws IOException {
		new LimitedInputStream(new ByteArrayInputStream(new byte[10]), 5, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNegativeLength() throws IOException {
		new LimitedInputStream(new ByteArrayInputStream(new byte[10]), 5, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNegativeOffset() throws IOException {
		new LimitedInputStream(new ByteArrayInputStream(new byte[10]), -1, 10);
	}

	@Test(expected = IOException.class)
	public void testConstructorWithNotEnoughDataForOffset() throws IOException {
		new LimitedInputStream(new ByteArrayInputStream(new byte[10]), 50, 10);
	}

	@Test
	public void testMarkSupported() throws IOException {
		LimitedInputStream limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 5);

		Assert.assertFalse(limitedInputStream.markSupported());
	}

	@Test
	public void testRead() throws IOException {
		LimitedInputStream limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 5);

		for (int i = 0; i < 5; i++) {
			Assert.assertEquals(0, limitedInputStream.read());
		}

		Assert.assertEquals(-1, limitedInputStream.read());

		limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 3);

		for (int i = 0; i < 3; i++) {
			Assert.assertEquals(0, limitedInputStream.read());
		}

		Assert.assertEquals(-1, limitedInputStream.read());
	}

	@Test
	public void testReadBlock() throws IOException {
		LimitedInputStream limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 5);

		byte[] buffer = new byte[4];

		Assert.assertEquals(4, limitedInputStream.read(buffer));
		Assert.assertEquals(1, limitedInputStream.read(buffer));
		Assert.assertEquals(-1, limitedInputStream.read(buffer));

		limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 3);

		Assert.assertEquals(3, limitedInputStream.read(buffer));
		Assert.assertEquals(-1, limitedInputStream.read(buffer));
	}

	@Test
	public void testReadBlockWithRange() throws IOException {
		LimitedInputStream limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 5);

		byte[] buffer = new byte[6];

		Assert.assertEquals(4, limitedInputStream.read(buffer, 1, 4));
		Assert.assertEquals(1, limitedInputStream.read(buffer, 1, 4));
		Assert.assertEquals(-1, limitedInputStream.read(buffer, 1, 4));

		limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 3);

		Assert.assertEquals(3, limitedInputStream.read(buffer, 1, 4));
		Assert.assertEquals(-1, limitedInputStream.read(buffer, 1, 4));
	}

	@Test
	public void testSkip() throws IOException {
		LimitedInputStream limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 3);

		Assert.assertEquals(3, limitedInputStream.skip(5));
		Assert.assertEquals(0, limitedInputStream.skip(5));

		limitedInputStream = new LimitedInputStream(
			new ByteArrayInputStream(new byte[10]), 5, 5);

		Assert.assertEquals(5, limitedInputStream.skip(5));
		Assert.assertEquals(0, limitedInputStream.skip(5));
	}

}