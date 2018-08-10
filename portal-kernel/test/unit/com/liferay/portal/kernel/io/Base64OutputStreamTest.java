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

package com.liferay.portal.kernel.io;

import com.liferay.petra.string.CharPool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author     Tina Tian
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class Base64OutputStreamTest {

	@Test
	public void testClose() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try (Base64OutputStream base64OutputStream = new Base64OutputStream(
				byteArrayOutputStream)) {

			base64OutputStream.write('A');
		}

		byte[] bytes = new byte[4];

		try (ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(byteArrayOutputStream.toByteArray())) {

			byteArrayInputStream.read(bytes);
		}

		Assert.assertTrue(
			(bytes[3] == CharPool.EQUAL) && (bytes[2] == CharPool.EQUAL));
	}

	@Test
	public void testEncodeUnit1Byte() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try (Base64OutputStream base64OutputStream = new Base64OutputStream(
				byteArrayOutputStream)) {

			base64OutputStream.encodeUnit((byte)'A');
		}

		try (ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(byteArrayOutputStream.toByteArray())) {

			Assert.assertEquals(4, byteArrayInputStream.read(new byte[4]));
		}
	}

	@Test
	public void testEncodeUnit2Bytes() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try (Base64OutputStream base64OutputStream = new Base64OutputStream(
				byteArrayOutputStream)) {

			base64OutputStream.encodeUnit((byte)'A', (byte)'B');
		}

		try (ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(byteArrayOutputStream.toByteArray())) {

			Assert.assertEquals(4, byteArrayInputStream.read(new byte[4]));
		}
	}

	@Test
	public void testEncodeUnit3Args() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try (Base64OutputStream base64OutputStream = new Base64OutputStream(
				byteArrayOutputStream)) {

			base64OutputStream.encodeUnit((byte)'A', (byte)'B', (byte)'C');
		}

		try (ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(byteArrayOutputStream.toByteArray())) {

			Assert.assertEquals(4, byteArrayInputStream.read(new byte[4]));
		}
	}

	@Test
	public void testFlush() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			byteArrayOutputStream);

		base64OutputStream.write('A');

		base64OutputStream.flush();

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			byteArrayOutputStream.toByteArray());

		Assert.assertEquals(4, byteArrayInputStream.read(new byte[4]));

		byteArrayInputStream.close();

		byteArrayOutputStream = new ByteArrayOutputStream();

		base64OutputStream = new Base64OutputStream(byteArrayOutputStream);

		base64OutputStream.write('A');
		base64OutputStream.write('B');

		base64OutputStream.flush();

		byteArrayInputStream = new ByteArrayInputStream(
			byteArrayOutputStream.toByteArray());

		Assert.assertEquals(4, byteArrayInputStream.read(new byte[4]));

		byteArrayInputStream.close();
	}

	@Test
	public void testGetChar() throws IOException {
		try (Base64OutputStream base64OutputStream = new Base64OutputStream(
				new ByteArrayOutputStream())) {

			Assert.assertEquals('A', base64OutputStream.getChar(0));
			Assert.assertEquals('?', base64OutputStream.getChar(64));
		}
	}

	@Test
	public void testWrite3Args() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try (Base64OutputStream base64OutputStream = new Base64OutputStream(
				byteArrayOutputStream)) {

			byte[] bytes = {'A', 'B', 'C', 'A', 'B', 'C'};

			base64OutputStream.write(bytes, 0, 1);
			base64OutputStream.write(bytes, 1, 2);
			base64OutputStream.write(bytes, 3, 3);
		}

		try (ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(byteArrayOutputStream.toByteArray())) {

			Assert.assertEquals(8, byteArrayInputStream.read(new byte[8]));
		}
	}

	@Test
	public void testWriteByteArray() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try (Base64OutputStream base64OutputStream = new Base64OutputStream(
				byteArrayOutputStream)) {

			base64OutputStream.write(new byte[] {'A', 'B', 'C'});
		}

		try (ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(byteArrayOutputStream.toByteArray())) {

			Assert.assertEquals(4, byteArrayInputStream.read(new byte[4]));
		}
	}

	@Test
	public void testWriteInt() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try (Base64OutputStream base64OutputStream = new Base64OutputStream(
				byteArrayOutputStream)) {

			base64OutputStream.write('A');
			base64OutputStream.write('A');
			base64OutputStream.write('A');
		}

		try (ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(byteArrayOutputStream.toByteArray())) {

			Assert.assertEquals(4, byteArrayInputStream.read(new byte[4]));
		}
	}

}