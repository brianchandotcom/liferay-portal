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

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import java.lang.reflect.Field;

import java.nio.ByteBuffer;

import java.util.Arrays;
import java.util.List;

import javassist.Modifier;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncByteArrayOutputStreamTest extends BaseOutputStreamTestCase {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(BoundaryCheckerUtil.class);
			}

		};

	@Test
	public void testBlockWrite() {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		unsyncByteArrayOutputStream.write(_BUFFER);

		Assert.assertEquals(_BUFFER_SIZE, unsyncByteArrayOutputStream.size());
		Assert.assertTrue(
			Arrays.equals(_BUFFER, unsyncByteArrayOutputStream.toByteArray()));
	}

	@Test
	public void testConstructor() {
		new BoundaryCheckerUtil();

		Assert.assertTrue(
			Modifier.isPackage(BoundaryCheckerUtil.class.getModifiers()));

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		Assert.assertEquals(0, unsyncByteArrayOutputStream.size());

		unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream(64);

		Assert.assertEquals(0, unsyncByteArrayOutputStream.size());
	}

	@Test
	public void testSizeAndReset() {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		Assert.assertEquals(0, unsyncByteArrayOutputStream.size());

		unsyncByteArrayOutputStream.write(0);

		Assert.assertEquals(1, unsyncByteArrayOutputStream.size());

		unsyncByteArrayOutputStream.write(1);

		Assert.assertEquals(2, unsyncByteArrayOutputStream.size());

		unsyncByteArrayOutputStream.reset();

		Assert.assertEquals(0, unsyncByteArrayOutputStream.size());

		unsyncByteArrayOutputStream.write(0);

		Assert.assertEquals(1, unsyncByteArrayOutputStream.size());

		unsyncByteArrayOutputStream.write(1);

		Assert.assertEquals(2, unsyncByteArrayOutputStream.size());
	}

	@Test
	public void testToByteArray() {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		unsyncByteArrayOutputStream.write(_BUFFER);

		byte[] bytes1 = unsyncByteArrayOutputStream.toByteArray();

		Assert.assertTrue(Arrays.equals(_BUFFER, bytes1));

		byte[] bytes2 = unsyncByteArrayOutputStream.toByteArray();

		Assert.assertTrue(Arrays.equals(_BUFFER, bytes2));

		Assert.assertNotSame(bytes1, bytes2);
	}

	@Test
	public void testToString() throws UnsupportedEncodingException {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		unsyncByteArrayOutputStream.write(_BUFFER);

		Assert.assertEquals(
			new String(_BUFFER), unsyncByteArrayOutputStream.toString());

		String charsetName1 = "UTF-16BE";
		String charsetName2 = "UTF-16LE";

		Assert.assertFalse(
			new String(
				_BUFFER, charsetName1
			).equals(
				unsyncByteArrayOutputStream.toString(charsetName2)
			));

		Assert.assertEquals(
			new String(_BUFFER, charsetName1),
			unsyncByteArrayOutputStream.toString(charsetName1));
		Assert.assertEquals(
			new String(_BUFFER, charsetName2),
			unsyncByteArrayOutputStream.toString(charsetName2));
	}

	@Test
	public void testUnsafeGetByteArray() throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		unsyncByteArrayOutputStream.write(_BUFFER);

		byte[] bytes1 = unsyncByteArrayOutputStream.unsafeGetByteArray();

		Assert.assertTrue(Arrays.equals(_BUFFER, bytes1));
		Assert.assertSame(
			_bufferField.get(unsyncByteArrayOutputStream), bytes1);

		byte[] bytes2 = unsyncByteArrayOutputStream.unsafeGetByteArray();

		Assert.assertTrue(Arrays.equals(_BUFFER, bytes2));

		Assert.assertSame(bytes1, bytes2);

		ByteBuffer byteBuffer =
			unsyncByteArrayOutputStream.unsafeGetByteBuffer();

		Assert.assertSame(bytes1, byteBuffer.array());
	}

	@Test
	public void testWrite() {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		for (int i = 0; i < _BUFFER_SIZE; i++) {
			unsyncByteArrayOutputStream.write(i);

			Assert.assertEquals(i + 1, unsyncByteArrayOutputStream.size());
		}

		Assert.assertTrue(
			Arrays.equals(_BUFFER, unsyncByteArrayOutputStream.toByteArray()));
	}

	@Test
	public void testWriteTo() throws IOException {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		unsyncByteArrayOutputStream.write(_BUFFER);

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		unsyncByteArrayOutputStream.writeTo(byteArrayOutputStream);

		Assert.assertTrue(
			Arrays.equals(_BUFFER, byteArrayOutputStream.toByteArray()));
	}

	@Override
	protected OutputStream getOutputStream() {
		return new UnsyncByteArrayOutputStream();
	}

	private static final byte[] _BUFFER =
		new byte[UnsyncByteArrayOutputStreamTest._BUFFER_SIZE];

	private static final int _BUFFER_SIZE = 64;

	private static final Field _bufferField = ReflectionTestUtil.getField(
		UnsyncByteArrayOutputStream.class, "_buffer");

	static {
		for (int i = 0; i < _BUFFER_SIZE; i++) {
			_BUFFER[i] = (byte)i;
		}
	}

}