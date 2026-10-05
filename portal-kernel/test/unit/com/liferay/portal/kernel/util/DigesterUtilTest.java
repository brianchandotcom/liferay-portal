/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.util.FIPSModeTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.io.IOException;
import java.io.InputStream;

import java.nio.ByteBuffer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

/**
 * @author Lucas Miranda
 */
public class DigesterUtilTest {

	@Test
	public void testDigestHex() throws Exception {
		FIPSModeTestUtil.assertAlgorithmSwitch(
			DigesterUtil.SHA, MessageDigest.class, DigesterUtil.SHA_256,
			MessageDigest::getInstance,
			() -> DigesterUtil.digestHex(RandomTestUtil.randomString()));
	}

	@Test
	public void testDigestRaw() {
		_assertSystemException(
			IOException.class,
			() -> DigesterUtil.digestRaw(
				DigesterUtil.SHA_256,
				new InputStream() {

					@Override
					public int read() throws IOException {
						throw new IOException();
					}

				}));
		_assertSystemException(
			NoSuchAlgorithmException.class,
			() -> DigesterUtil.digestRaw(
				RandomTestUtil.randomString(),
				ByteBuffer.wrap(RandomTestUtil.randomBytes())));
		_assertSystemException(
			NoSuchAlgorithmException.class,
			() -> DigesterUtil.digestRaw(
				RandomTestUtil.randomString(), RandomTestUtil.randomString()));
	}

	private void _assertSystemException(
		Class<? extends Exception> expectedExceptionClass,
		ThrowingRunnable throwingRunnable) {

		SystemException systemException = Assert.assertThrows(
			SystemException.class, throwingRunnable);

		Assert.assertTrue(
			expectedExceptionClass.isInstance(systemException.getCause()));
	}

}