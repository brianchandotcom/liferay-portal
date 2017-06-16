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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class Base64InputStreamTest {

	@Test
	public void testAvailable() throws Exception {
		byte[] bytes = {'a', 'b', 'c', 'd'};

		try (Base64InputStream base64InputStream = new Base64InputStream(
				new ByteArrayInputStream(bytes))) {

			int returnValue = base64InputStream.available();

			Assert.assertEquals(3, returnValue);
		}
	}

	@Test
	public void testDecode() throws IOException {
		byte[] bytes = {'a', 'b', 'c', 'd'};

		try (Base64InputStream base64InputStream = new Base64InputStream(
				new ByteArrayInputStream(bytes))) {

			byte[] outputBuffer = new byte[3];
			int position = 0;

			Assert.assertEquals(
				3, base64InputStream.decode(bytes, outputBuffer, position, 0));
			Assert.assertEquals(
				2, base64InputStream.decode(bytes, outputBuffer, position, 1));
			Assert.assertEquals(
				1, base64InputStream.decode(bytes, outputBuffer, position, 2));
			Assert.assertEquals(
				-1, base64InputStream.decode(bytes, outputBuffer, position, 3));
		}
	}

	@Test
	public void testDecodeUnit() throws Exception {
		byte[] bytes = {
			'a', 'b', 'c', 'd', 'e', 'f', 'h', '=', 'e', 'f', '=', '=', 'e',
			'=', 'e', 'f', '=', 'a'
		};

		try (Base64InputStream base64InputStream = new Base64InputStream(
				new ByteArrayInputStream(bytes))) {

			byte[] outputBuffer = new byte[3];
			int position = 0;

			Assert.assertEquals(
				3, base64InputStream.decodeUnit(outputBuffer, position));
			Assert.assertEquals(
				2, base64InputStream.decodeUnit(outputBuffer, position));
			Assert.assertEquals(
				1, base64InputStream.decodeUnit(outputBuffer, position));
			Assert.assertEquals(
				-1, base64InputStream.decodeUnit(outputBuffer, position));
			Assert.assertEquals(
				-1, base64InputStream.decodeUnit(outputBuffer, position));
			Assert.assertEquals(
				-1, base64InputStream.decodeUnit(outputBuffer, position));
		}
	}

	@Test
	public void testGetByte() throws IOException {
		byte[] bytes = {'a'};

		try (Base64InputStream base64InputStream = new Base64InputStream(
				new ByteArrayInputStream(bytes))) {

			Assert.assertEquals(0, base64InputStream.getByte('A'));
			Assert.assertEquals(0, base64InputStream.getByte('='));
			Assert.assertEquals(-1, base64InputStream.getByte('\n'));
			Assert.assertEquals(62, base64InputStream.getByte('+'));
		}
	}

	@Test
	public void testGetEncodedByte() throws Exception {
		byte[] bytes = {'A', '=', 'B', '\n'};

		try (Base64InputStream base64InputStream = new Base64InputStream(
				new ByteArrayInputStream(bytes))) {

			Assert.assertEquals(0, base64InputStream.getEncodedByte());
			Assert.assertEquals(-2, base64InputStream.getEncodedByte());
			Assert.assertEquals(1, base64InputStream.getEncodedByte());
			Assert.assertEquals(-1, base64InputStream.getEncodedByte());
		}
	}

	@Test
	public void testRead_0args() throws Exception {
		byte[] bytes = {'a', 'b', 'c', 'd'};

		try (Base64InputStream base64InputStream = new Base64InputStream(
				new ByteArrayInputStream(bytes))) {

			Assert.assertEquals(105, base64InputStream.read());

			base64InputStream.read();
			base64InputStream.read();

			Assert.assertEquals(-1, base64InputStream.read());
		}
	}

	@Test
	public void testRead_3args() throws Exception {
		byte[] bytes =
			{'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'e', 'f', 'g', '='};

		try (Base64InputStream base64InputStream = new Base64InputStream(
				new ByteArrayInputStream(bytes))) {

			byte[] buffer = new byte[5];
			int offset = 0;

			Assert.assertEquals(1, base64InputStream.read(buffer, offset, 1));
			Assert.assertEquals(2, base64InputStream.read(buffer, offset, 2));
			Assert.assertEquals(5, base64InputStream.read(buffer, offset, 6));
			Assert.assertEquals(-1, base64InputStream.read(buffer, offset, 3));
			Assert.assertEquals(-1, base64InputStream.read(buffer, offset, 1));
			Assert.assertEquals(-1, base64InputStream.read(buffer, offset, 0));
		}
	}

	@Test
	public void testSkip() throws Exception {
		byte[] bytes = {'a', 'b', 'c', 'd'};

		try (Base64InputStream base64InputStream = new Base64InputStream(
				new ByteArrayInputStream(bytes))) {

			Assert.assertEquals(3L, base64InputStream.skip(4L));
		}
	}

}