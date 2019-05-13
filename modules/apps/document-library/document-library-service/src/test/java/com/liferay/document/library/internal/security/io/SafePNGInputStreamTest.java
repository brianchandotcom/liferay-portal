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

package com.liferay.document.library.internal.security.io;

import com.liferay.document.library.internal.util.InputStreamUtil;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class SafePNGInputStreamTest {

	@Test
	public void testChunksOtherThanZTXTArePreserved() throws Exception {
		InputStream inputStream = _createInputStream(
			_PNG_SIGNATURE, _MISC_CHUNK1);

		byte[] bytes = new byte[_PNG_SIGNATURE.length + _MISC_CHUNK1.length];

		inputStream.read(bytes);

		Assert.assertArrayEquals(
			ArrayUtil.append(_PNG_SIGNATURE, _MISC_CHUNK1), bytes);

		Assert.assertEquals(-1, inputStream.read());
	}

	@Test
	public void testEmptyInputStreamReturnsNoData() throws Exception {
		InputStream inputStream = _createInputStream(new byte[0]);

		Assert.assertEquals(-1, inputStream.read());
	}

	@Test
	public void testPNGFile() throws Exception {
		Assert.assertArrayEquals(
			_getBytes("filtered.png"),
			_getBytes(_createInputStream(_getBytes("original.png"))));
	}

	@Test
	public void testPNGSignatureIsPreserved() throws Exception {
		InputStream inputStream = _createInputStream(_PNG_SIGNATURE);

		byte[] bytes = new byte[_PNG_SIGNATURE.length];

		inputStream.read(bytes);

		Assert.assertArrayEquals(_PNG_SIGNATURE, bytes);

		Assert.assertEquals(-1, inputStream.read());
	}

	@Test
	public void testRemainingChunksOrderIsPreserved() throws Exception {
		InputStream inputStream = _createInputStream(
			_PNG_SIGNATURE, _MISC_CHUNK1, _ZTXT_CHUNK, _MISC_CHUNK2);

		byte[] bytes = new byte
			[_PNG_SIGNATURE.length + _MISC_CHUNK1.length + _MISC_CHUNK2.length];

		inputStream.read(bytes);

		Assert.assertArrayEquals(
			ArrayUtil.append(_PNG_SIGNATURE, _MISC_CHUNK1, _MISC_CHUNK2),
			bytes);

		Assert.assertEquals(-1, inputStream.read());
	}

	@Test
	public void testStreamNotModifiedWhenNotAPNG() throws Exception {
		InputStream inputStream = _createInputStream(_NOT_PNG_CONTENT_BYTES);

		byte[] bytes = new byte[_NOT_PNG_CONTENT_BYTES.length];

		inputStream.read(bytes);

		Assert.assertArrayEquals(_NOT_PNG_CONTENT_BYTES, bytes);
	}

	@Test
	public void testZTXTChunksAreFilteredOut() throws Exception {
		InputStream inputStream = _createInputStream(
			_PNG_SIGNATURE, _ZTXT_CHUNK);

		byte[] bytes = new byte[_PNG_SIGNATURE.length];

		inputStream.read(bytes);

		Assert.assertArrayEquals(_PNG_SIGNATURE, bytes);

		Assert.assertEquals(-1, inputStream.read());
	}

	private static InputStream _createInputStream(byte[]... bytes) {
		return new SafePNGInputStream(
			InputStreamUtil.toBufferedInputStream(
				new UnsyncByteArrayInputStream(ArrayUtil.append(bytes))));
	}

	private static byte[] _getBytes(InputStream inputStream)
		throws IOException {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		StreamUtil.transfer(inputStream, unsyncByteArrayOutputStream);

		return unsyncByteArrayOutputStream.toByteArray();
	}

	private static byte[] _getBytes(String fileName) throws IOException {
		return _getBytes(
			SafePNGInputStreamTest.class.getResourceAsStream(
				"/com/liferay/document/library/internal/security/io" +
					"/dependencies/" + fileName));
	}

	private static final byte[] _MISC_CHUNK1 = {
		0, 0, 0, 4, 42, 84, 88, 116, 0, 0, 0, 0, 0, 0, 0, 0
	};

	private static final byte[] _MISC_CHUNK2 = {
		0, 0, 0, 4, 43, 84, 88, 116, 0, 0, 0, 0, 0, 0, 0, 0
	};

	private static final byte[] _NOT_PNG_CONTENT_BYTES = "012345".getBytes();

	private static final byte[] _PNG_SIGNATURE = {
		-119, 80, 78, 71, 13, 10, 26, 10
	};

	private static final byte[] _ZTXT_CHUNK = {
		0, 0, 0, 4, 122, 84, 88, 116, 0, 0, 0, 0, 0, 0, 0, 0
	};

}