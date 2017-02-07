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

package com.liferay.portal.kernel.flash;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tomas Polesovsky
 */
public class FlashMagicBytesUtilTest {

	@Test
	public void testCheckGZIPFlash() throws IOException {
		test(_CWS, true);
	}

	@Test
	public void testCheckLZMAFlash() throws IOException {
		test(_ZWS, true);
	}

	@Test
	public void testCheckUncompressedFlash() throws IOException {
		test(_FWS, true);
	}

	@Test
	public void testNoFlash() throws IOException {
		for (int i = 0; i < 5; i++) {
			test(new byte[i], false);
		}
	}

	protected void test(byte[] bytes, boolean expectFlash) throws IOException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

		FlashMagicBytesUtil.Result result = FlashMagicBytesUtil.check(
			inputStream);

		Assert.assertEquals(expectFlash, result.isFlash());

		InputStream returnedInputStream = result.getInputStream();

		byte[] buffer = new byte[4096];

		int length = returnedInputStream.read(buffer);

		if (length == _EOF) {
			Assert.assertEquals(Arrays.toString(bytes), 0, bytes.length);

			return;
		}

		Assert.assertEquals(bytes.length, length);
	}

	private static final byte[] _CWS = {0x43, 0x57, 0x53};

	private static final int _EOF = -1;

	private static final byte[] _FWS = {0x46, 0x57, 0x53};

	private static final byte[] _ZWS = {0x5a, 0x57, 0x53};

}