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

import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncByteArrayInputStreamTest extends TestCase {

	@Test
	public void testBlockRead() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		int size = _SIZE * 2 / 3;

		byte[] buffer = new byte[size];

		int read = unsyncByteArrayInputStream.read(buffer);

		assertEquals(size, read);

		for (int i = 0; i < read; i++) {
			assertEquals(i, buffer[i]);
		}

		read = unsyncByteArrayInputStream.read(buffer);

		assertEquals(_SIZE - size, read);

		for (int i = 0; i < read; i++) {
			assertEquals(i + size, buffer[i]);
		}
	}

	@Test
	public void testConstructor() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		assertEquals(_SIZE, unsyncByteArrayInputStream.available());

		unsyncByteArrayInputStream = new UnsyncByteArrayInputStream(
			_BUFFER, _SIZE / 2, _SIZE / 2);

		assertEquals(_SIZE / 2, unsyncByteArrayInputStream.available());
	}

	@Test
	public void testMarkAndReset() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		assertEquals(0, unsyncByteArrayInputStream.read());
		assertEquals(1, unsyncByteArrayInputStream.read());

		unsyncByteArrayInputStream.mark(-1);

		assertEquals(_SIZE - 2, unsyncByteArrayInputStream.available());
		assertEquals(2, unsyncByteArrayInputStream.read());
		assertEquals(3, unsyncByteArrayInputStream.read());
		assertEquals(_SIZE - 4, unsyncByteArrayInputStream.available());

		unsyncByteArrayInputStream.reset();

		assertEquals(_SIZE - 2, unsyncByteArrayInputStream.available());
		assertEquals(2, unsyncByteArrayInputStream.read());
		assertEquals(3, unsyncByteArrayInputStream.read());

		assertEquals(_SIZE - 4, unsyncByteArrayInputStream.available());
	}

	@Test
	public void testMarkSupported() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		assertTrue(unsyncByteArrayInputStream.markSupported());
	}

	@Test
	public void testRead() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		for (int i = 0; i < _SIZE; i++) {
			assertEquals(i, unsyncByteArrayInputStream.read());
		}

		assertEquals(-1, unsyncByteArrayInputStream.read());
	}

	@Test
	public void testSkip() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		long size = _SIZE * 2 / 3;

		assertEquals(size, unsyncByteArrayInputStream.skip(size));
		assertEquals(_SIZE - size, unsyncByteArrayInputStream.available());
		assertEquals(_SIZE - size, unsyncByteArrayInputStream.skip(size));
		assertEquals(0, unsyncByteArrayInputStream.available());
	}

	private static final byte[] _BUFFER =
		new byte[UnsyncByteArrayInputStreamTest._SIZE];

	private static final int _SIZE = 10;

	static {
		for (int i = 0; i < _SIZE; i++) {
			_BUFFER[i] = (byte)i;
		}
	}

}