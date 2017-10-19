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

import java.io.InputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncByteArrayInputStreamTest extends BaseInputStreamTestCase {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testBlockRead() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		int size = _SIZE * 2 / 3;

		byte[] buffer = new byte[size];

		int read = unsyncByteArrayInputStream.read(buffer);

		Assert.assertEquals(size, read);

		for (int i = 0; i < read; i++) {
			Assert.assertEquals(i, buffer[i]);
		}

		read = unsyncByteArrayInputStream.read(buffer);

		Assert.assertEquals(_SIZE - size, read);

		for (int i = 0; i < read; i++) {
			Assert.assertEquals(i + size, buffer[i]);
		}

		// Empty stream

		unsyncByteArrayInputStream = new UnsyncByteArrayInputStream(
			new byte[0]);

		Assert.assertEquals(-1, unsyncByteArrayInputStream.read(buffer));
	}

	@Test
	public void testConstructor() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		Assert.assertEquals(_SIZE, unsyncByteArrayInputStream.available());

		unsyncByteArrayInputStream = new UnsyncByteArrayInputStream(
			_BUFFER, _SIZE / 2, _SIZE / 2);

		Assert.assertEquals(_SIZE / 2, unsyncByteArrayInputStream.available());
	}

	@Test
	public void testMarkAndReset() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		Assert.assertEquals(0, unsyncByteArrayInputStream.read());
		Assert.assertEquals(1, unsyncByteArrayInputStream.read());

		unsyncByteArrayInputStream.mark(-1);

		Assert.assertEquals(_SIZE - 2, unsyncByteArrayInputStream.available());
		Assert.assertEquals(2, unsyncByteArrayInputStream.read());
		Assert.assertEquals(3, unsyncByteArrayInputStream.read());
		Assert.assertEquals(_SIZE - 4, unsyncByteArrayInputStream.available());

		unsyncByteArrayInputStream.reset();

		Assert.assertEquals(_SIZE - 2, unsyncByteArrayInputStream.available());
		Assert.assertEquals(2, unsyncByteArrayInputStream.read());
		Assert.assertEquals(3, unsyncByteArrayInputStream.read());

		Assert.assertEquals(_SIZE - 4, unsyncByteArrayInputStream.available());
	}

	@Test
	public void testMarkSupported() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		Assert.assertTrue(unsyncByteArrayInputStream.markSupported());
	}

	@Test
	public void testRead() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		for (int i = 0; i < _SIZE; i++) {
			Assert.assertEquals(i, unsyncByteArrayInputStream.read());
		}

		Assert.assertEquals(-1, unsyncByteArrayInputStream.read());
	}

	@Test
	public void testSkip() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		long size = _SIZE * 2 / 3;

		Assert.assertEquals(size, unsyncByteArrayInputStream.skip(size));
		Assert.assertEquals(
			_SIZE - size, unsyncByteArrayInputStream.available());
		Assert.assertEquals(
			_SIZE - size, unsyncByteArrayInputStream.skip(size));

		Assert.assertEquals(0, unsyncByteArrayInputStream.available());

		Assert.assertEquals(0, unsyncByteArrayInputStream.skip(-1));
	}

	@Override
	protected InputStream getInputStream(byte[] bytes) {
		return new UnsyncByteArrayInputStream(bytes);
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