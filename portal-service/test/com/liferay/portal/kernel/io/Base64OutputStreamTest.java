/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.kernel.util.CharPool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author Tina Tian
 */
public class Base64OutputStreamTest extends TestCase {

	public void testClose() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			byteArrayOutputStream);

		base64OutputStream.write('A');

		base64OutputStream.close();

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			byteArrayOutputStream.toByteArray());

		byte[] bytes = new byte[4];

		byteArrayInputStream.read(bytes);

		byteArrayInputStream.close();

		if ((bytes[3] != CharPool.EQUAL) || (bytes[2] != CharPool.EQUAL)) {
			fail();
		}
	}

	public void testEncodeUnit1Byte() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			byteArrayOutputStream);

		base64OutputStream.encodeUnit((byte)'A');

		base64OutputStream.close();

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			byteArrayOutputStream.toByteArray());

		assertEquals(4, byteArrayInputStream.read(new byte[4]));

		byteArrayInputStream.close();
	}

	public void testEncodeUnit2Bytes() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			byteArrayOutputStream);

		base64OutputStream.encodeUnit((byte)'A', (byte)'B');

		base64OutputStream.close();

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			byteArrayOutputStream.toByteArray());

		assertEquals(4, byteArrayInputStream.read(new byte[4]));

		byteArrayInputStream.close();
	}

	public void testEncodeUnit3Args() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			byteArrayOutputStream);

		base64OutputStream.encodeUnit((byte)'A', (byte)'B', (byte)'C');

		base64OutputStream.close();

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			byteArrayOutputStream.toByteArray());

		assertEquals(4, byteArrayInputStream.read(new byte[4]));

		byteArrayInputStream.close();
	}

	public void testFlush() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			byteArrayOutputStream);

		base64OutputStream.write('A');

		base64OutputStream.flush();

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			byteArrayOutputStream.toByteArray());

		assertEquals(4, byteArrayInputStream.read(new byte[4]));

		byteArrayInputStream.close();

		byteArrayOutputStream = new ByteArrayOutputStream();

		base64OutputStream = new Base64OutputStream(byteArrayOutputStream);

		base64OutputStream.write('A');
		base64OutputStream.write('B');

		base64OutputStream.flush();

		byteArrayInputStream = new ByteArrayInputStream(
			byteArrayOutputStream.toByteArray());

		assertEquals(4, byteArrayInputStream.read(new byte[4]));

		byteArrayInputStream.close();
	}

	public void testGetChar() {
		try {
			Base64OutputStream base64OutputStream = new Base64OutputStream(
				new ByteArrayOutputStream());

			assertEquals('A', base64OutputStream.getChar(0));
			assertEquals('?', base64OutputStream.getChar(64));

			base64OutputStream.close();
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testWrite3Args() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			byteArrayOutputStream);

		byte[] bytes = {'A', 'B', 'C', 'A', 'B', 'C'};

		base64OutputStream.write(bytes, 0, 1);
		base64OutputStream.write(bytes, 1, 2);
		base64OutputStream.write(bytes, 3, 3);

		base64OutputStream.close();

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			byteArrayOutputStream.toByteArray());

		assertEquals(8, byteArrayInputStream.read(new byte[8]));

		byteArrayInputStream.close();

	}

	public void testWriteByteArray() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			byteArrayOutputStream);

		base64OutputStream.write(new byte[] {'A', 'B', 'C'});

		base64OutputStream.close();

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			byteArrayOutputStream.toByteArray());

		assertEquals(4, byteArrayInputStream.read(new byte[4]));

		byteArrayInputStream.close();
	}

	public void testWriteInt() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			byteArrayOutputStream);

		base64OutputStream.write('A');
		base64OutputStream.write('A');
		base64OutputStream.write('A');

		base64OutputStream.close();

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			byteArrayOutputStream.toByteArray());

		assertEquals(4, byteArrayInputStream.read(new byte[4]));

		byteArrayInputStream.close();
	}

}