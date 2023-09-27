/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.security.io;

import com.liferay.document.library.internal.util.InputStreamUtil;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class SafePNGInputStreamTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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
	public void testCompressedITXTChunksAreFilteredOut() throws Exception {
		InputStream inputStream = _createInputStream(
			_PNG_SIGNATURE, _COMPRESSED_ITXT_CHUNK);

		byte[] bytes = new byte[_PNG_SIGNATURE.length];

		inputStream.read(bytes);

		Assert.assertArrayEquals(_PNG_SIGNATURE, bytes);

		Assert.assertEquals(-1, inputStream.read());
	}

	@Test
	public void testEmptyInputStreamReturnsNoData() throws Exception {
		InputStream inputStream = _createInputStream(new byte[0]);

		Assert.assertEquals(-1, inputStream.read());
	}

	@Test
	public void testICCPChunksAreFilteredOut() throws Exception {
		InputStream inputStream = _createInputStream(
			_PNG_SIGNATURE, _ICCP_CHUNK);

		byte[] bytes = new byte[_PNG_SIGNATURE.length];

		inputStream.read(bytes);

		Assert.assertArrayEquals(_PNG_SIGNATURE, bytes);

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
			_PNG_SIGNATURE, _MISC_CHUNK1, _ZTXT_CHUNK, _COMPRESSED_ITXT_CHUNK,
			_MISC_CHUNK2, _ICCP_CHUNK);

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
	public void testUncompressedITXTChunksArePreserved() throws Exception {
		InputStream inputStream = _createInputStream(
			_PNG_SIGNATURE, _UNCOMPRESSED_ITXT_CHUNK);

		byte[] bytes =
			new byte[_PNG_SIGNATURE.length + _UNCOMPRESSED_ITXT_CHUNK.length];

		inputStream.read(bytes);

		Assert.assertArrayEquals(
			ArrayUtil.append(_PNG_SIGNATURE, _UNCOMPRESSED_ITXT_CHUNK), bytes);

		Assert.assertEquals(-1, inputStream.read());
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

	private static byte[] _getBytes(InputStream inputStream) throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		StreamUtil.transfer(inputStream, unsyncByteArrayOutputStream);

		return unsyncByteArrayOutputStream.toByteArray();
	}

	private static byte[] _getBytes(String fileName) throws Exception {
		return _getBytes(
			SafePNGInputStreamTest.class.getResourceAsStream(
				"dependencies/" + fileName));
	}

	private InputStream _createInputStream(byte[]... bytes) {
		return new SafePNGInputStream(
			InputStreamUtil.toBufferedInputStream(
				new UnsyncByteArrayInputStream(ArrayUtil.append(bytes))));
	}

	private static final byte[] _COMPRESSED_ITXT_CHUNK = {
		0, 0, 0, 9, 105, 84, 88, 116, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
	};

	private static final byte[] _ICCP_CHUNK = {
		0, 0, 0, 4, 105, 67, 67, 80, 0, 0, 0, 0, 0, 0, 0, 0
	};

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

	private static final byte[] _UNCOMPRESSED_ITXT_CHUNK = {
		0, 0, 0, 9, 105, 84, 88, 116, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
	};

	private static final byte[] _ZTXT_CHUNK = {
		0, 0, 0, 4, 122, 84, 88, 116, 0, 0, 0, 0, 0, 0, 0, 0
	};

}