/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.io.unsync;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncByteArrayInputStreamTest extends BaseInputStreamTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

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