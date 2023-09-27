/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.io.unsync;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import java.lang.reflect.Field;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncBufferedOutputStreamTest extends BaseOutputStreamTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testBlockWrite() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		UnsyncBufferedOutputStream unsyncBufferedOutputStream =
			new UnsyncBufferedOutputStream(byteArrayOutputStream, _SIZE * 2);

		byte[] buffer = (byte[])_bufferField.get(unsyncBufferedOutputStream);

		Assert.assertEquals(Arrays.toString(buffer), _SIZE * 2, buffer.length);

		unsyncBufferedOutputStream.write(_BUFFER);

		for (int i = 0; i < _SIZE; i++) {
			Assert.assertEquals(i, buffer[i]);
		}

		unsyncBufferedOutputStream.write(_BUFFER);

		for (int i = _SIZE; i < (_SIZE * 2); i++) {
			Assert.assertEquals(i - _SIZE, buffer[i]);
		}

		unsyncBufferedOutputStream.write(100);

		buffer = (byte[])_bufferField.get(unsyncBufferedOutputStream);

		Assert.assertEquals(100, buffer[0]);

		Assert.assertEquals(_SIZE * 2, byteArrayOutputStream.size());

		byteArrayOutputStream.reset();

		unsyncBufferedOutputStream = new UnsyncBufferedOutputStream(
			byteArrayOutputStream, _SIZE + 1);

		unsyncBufferedOutputStream.write(100);
		unsyncBufferedOutputStream.write(110);

		Assert.assertEquals(0, byteArrayOutputStream.size());

		unsyncBufferedOutputStream.write(_BUFFER);

		Assert.assertEquals(2, byteArrayOutputStream.size());

		byte[] bytes = byteArrayOutputStream.toByteArray();

		Assert.assertEquals(100, bytes[0]);
		Assert.assertEquals(110, bytes[1]);

		buffer = (byte[])_bufferField.get(unsyncBufferedOutputStream);

		for (int i = 0; i < _SIZE; i++) {
			Assert.assertEquals(i, buffer[i]);
		}

		byteArrayOutputStream.reset();

		unsyncBufferedOutputStream = new UnsyncBufferedOutputStream(
			byteArrayOutputStream, _SIZE / 2);

		unsyncBufferedOutputStream.write(_BUFFER);

		Assert.assertArrayEquals(_BUFFER, byteArrayOutputStream.toByteArray());
	}

	@Test
	public void testConstructor() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		UnsyncBufferedOutputStream unsyncBufferedOutputStream =
			new UnsyncBufferedOutputStream(byteArrayOutputStream);

		byte[] buffer = (byte[])_bufferField.get(unsyncBufferedOutputStream);

		Assert.assertEquals(Arrays.toString(buffer), 8192, buffer.length);

		unsyncBufferedOutputStream = new UnsyncBufferedOutputStream(
			byteArrayOutputStream, 10);

		buffer = (byte[])_bufferField.get(unsyncBufferedOutputStream);

		Assert.assertEquals(Arrays.toString(buffer), 10, buffer.length);

		try {
			new UnsyncBufferedOutputStream(byteArrayOutputStream, 0);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"Size is less than 1", illegalArgumentException.getMessage());
		}

		try {
			new UnsyncBufferedOutputStream(byteArrayOutputStream, -1);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"Size is less than 1", illegalArgumentException.getMessage());
		}
	}

	@Test
	public void testWrite() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		UnsyncBufferedOutputStream unsyncBufferedOutputStream =
			new UnsyncBufferedOutputStream(byteArrayOutputStream, _SIZE * 2);

		byte[] buffer = (byte[])_bufferField.get(unsyncBufferedOutputStream);

		Assert.assertEquals(Arrays.toString(buffer), _SIZE * 2, buffer.length);

		for (int i = 0; i < _SIZE; i++) {
			unsyncBufferedOutputStream.write(i);

			Assert.assertEquals(i, buffer[i]);
		}

		unsyncBufferedOutputStream.flush();

		Assert.assertTrue(
			Arrays.equals(_BUFFER, byteArrayOutputStream.toByteArray()));
	}

	@Override
	protected OutputStream getOutputStream() {
		return new UnsyncBufferedOutputStream(new ByteArrayOutputStream());
	}

	private static final byte[] _BUFFER =
		new byte[UnsyncBufferedOutputStreamTest._SIZE];

	private static final int _SIZE = 10;

	private static final Field _bufferField = ReflectionTestUtil.getField(
		UnsyncBufferedOutputStream.class, "_buffer");

	static {
		for (int i = 0; i < _SIZE; i++) {
			_BUFFER[i] = (byte)i;
		}
	}

}