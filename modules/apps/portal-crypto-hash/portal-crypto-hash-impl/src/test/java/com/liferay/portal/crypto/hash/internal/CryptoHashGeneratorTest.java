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

package com.liferay.portal.crypto.hash.internal;

import com.liferay.portal.crypto.hash.CryptoHashResponse;
import com.liferay.portal.crypto.hash.CryptoHashVerificationContext;
import com.liferay.portal.crypto.hash.provider.bcrypt.internal.BCryptCryptoHashProvider;
import com.liferay.portal.crypto.hash.provider.message.digest.internal.MessageDigestCryptoHashProvider;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
public class CryptoHashGeneratorTest {

	@Before
	public void setUp() throws Exception {
		_cryptoHashGenerators = Arrays.asList(
			new CryptoHashGeneratorImpl(new MessageDigestCryptoHashProvider()),
			new CryptoHashGeneratorImpl(new BCryptCryptoHashProvider()));
	}

	@Test
	public void testGenerate() throws Exception {
		for (CryptoHashGeneratorImpl cryptoHashGeneratorImpl :
				_cryptoHashGenerators) {

			CryptoHashResponse cryptoHashResponse =
				cryptoHashGeneratorImpl.generate(_INPUT);

			CryptoHashVerificationContext cryptoHashVerificationContext =
				cryptoHashResponse.getCryptoHashVerificationContext();

			Assert.assertFalse(
				cryptoHashGeneratorImpl.verify(
					_randomBytes(), cryptoHashResponse.getHash(),
					cryptoHashVerificationContext.getSalt()));
			Assert.assertTrue(
				cryptoHashGeneratorImpl.verify(
					_INPUT, cryptoHashResponse.getHash(),
					cryptoHashVerificationContext.getSalt()));
		}
	}

	private static byte[] _randomBytes() {
		String string = RandomTestUtil.randomString();

		return string.getBytes();
	}

	private static final byte[] _INPUT = _randomBytes();

	private List<CryptoHashGeneratorImpl> _cryptoHashGenerators;

}