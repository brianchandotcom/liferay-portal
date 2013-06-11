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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.test.TestCase;

import java.io.ByteArrayInputStream;

import org.junit.Test;

/**
 * @author Tina Tian
 */
public class Base64InputStreamTest extends TestCase {

	@Test
	public void testAvailable() throws Exception {
		byte[] bytes = {'a', 'b', 'c', 'd'};

		Base64InputStream base64InputStream = new Base64InputStream(
			new ByteArrayInputStream(bytes));

		int returnValue = base64InputStream.available();

		assertEquals(3, returnValue);

		base64InputStream.close();
	}

	@Test
	public void testDecode() {
		try {
			byte[] bytes = {'a', 'b', 'c', 'd'};

			Base64InputStream base64InputStream = new Base64InputStream(
				new ByteArrayInputStream(bytes));

			byte[] outputBuffer = new byte[3];
			int position = 0;

			assertEquals(
				3, base64InputStream.decode(bytes, outputBuffer, position, 0));
			assertEquals(
				2, base64InputStream.decode(bytes, outputBuffer, position, 1));
			assertEquals(
				1, base64InputStream.decode(bytes, outputBuffer, position, 2));
			assertEquals(
				-1, base64InputStream.decode(bytes, outputBuffer, position, 3));

			base64InputStream.close();
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDecodeUnit() throws Exception {
		byte[] bytes = {
			'a', 'b', 'c', 'd', 'e', 'f', 'h', '=', 'e', 'f', '=', '=', 'e',
			'=', 'e', 'f', '=', 'a'};

		Base64InputStream base64InputStream = new Base64InputStream(
			new ByteArrayInputStream(bytes));

		byte[] outputBuffer = new byte[3];
		int position = 0;

		assertEquals(3, base64InputStream.decodeUnit(outputBuffer, position));
		assertEquals(2, base64InputStream.decodeUnit(outputBuffer, position));
		assertEquals(1, base64InputStream.decodeUnit(outputBuffer, position));
		assertEquals(-1, base64InputStream.decodeUnit(outputBuffer, position));
		assertEquals(-1, base64InputStream.decodeUnit(outputBuffer, position));
		assertEquals(-1, base64InputStream.decodeUnit(outputBuffer, position));

		base64InputStream.close();
	}

	@Test
	public void testGetByte() {
		try {
			byte[] bytes = {'a'};

			Base64InputStream base64InputStream = new Base64InputStream(
				new ByteArrayInputStream(bytes));

			assertEquals(0, base64InputStream.getByte('A'));
			assertEquals(0, base64InputStream.getByte('='));
			assertEquals(-1, base64InputStream.getByte('\n'));
			assertEquals(62, base64InputStream.getByte('+'));

			base64InputStream.close();
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetEncodedByte() throws Exception {
		try {
			byte[] bytes = {'A', '=', 'B', '\n'};

			Base64InputStream base64InputStream = new Base64InputStream(
				new ByteArrayInputStream(bytes));

			assertEquals(0, base64InputStream.getEncodedByte());
			assertEquals(-2, base64InputStream.getEncodedByte());
			assertEquals(1, base64InputStream.getEncodedByte());
			assertEquals(-1, base64InputStream.getEncodedByte());

			base64InputStream.close();
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testRead_0args() throws Exception {
		byte[] bytes = {'a', 'b', 'c', 'd'};

		Base64InputStream base64InputStream = new Base64InputStream(
			new ByteArrayInputStream(bytes));

		assertEquals(105, base64InputStream.read());

		base64InputStream.read();
		base64InputStream.read();

		assertEquals(-1, base64InputStream.read());

		base64InputStream.close();
	}

	@Test
	public void testRead_3args() throws Exception {
		byte[] bytes = {
			'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'e', 'f', 'g', '='};

		Base64InputStream base64InputStream = new Base64InputStream(
			new ByteArrayInputStream(bytes));

		byte[] buffer = new byte[5];
		int offset = 0;

		assertEquals(1, base64InputStream.read(buffer, offset, 1));
		assertEquals(2, base64InputStream.read(buffer, offset, 2));
		assertEquals(5, base64InputStream.read(buffer, offset, 6));
		assertEquals(-1, base64InputStream.read(buffer, offset, 3));
		assertEquals(-1, base64InputStream.read(buffer, offset, 1));
		assertEquals(-1, base64InputStream.read(buffer, offset, 0));

		base64InputStream.close();
	}

	@Test
	public void testSkip() throws Exception {
		byte[] bytes = {'a', 'b', 'c', 'd'};

		Base64InputStream base64InputStream = new Base64InputStream(
			new ByteArrayInputStream(bytes));

		assertEquals(3L, base64InputStream.skip(4L));

		base64InputStream.close();
	}

}