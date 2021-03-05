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

import com.liferay.portal.crypto.hash.CryptoHashGenerator;
import com.liferay.portal.crypto.hash.CryptoHashResponse;
import com.liferay.portal.crypto.hash.CryptoHashVerificationContext;
import com.liferay.portal.crypto.hash.CryptoHashVerifier;
import com.liferay.portal.crypto.hash.provider.bcrypt.internal.BCryptCryptoHashProvider;
import com.liferay.portal.crypto.hash.provider.message.digest.internal.MessageDigestCryptoHashProvider;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.ArrayList;
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
		final CryptoVerifierRegistry cryptoVerifierRegistry =
			new CryptoVerifierRegistry();

		BCryptCryptoHashProvider bCryptCryptoHashProvider =
			new BCryptCryptoHashProvider();

		cryptoVerifierRegistry.register(bCryptCryptoHashProvider);

		MessageDigestCryptoHashProvider messageDigestCryptoHashProvider =
			new MessageDigestCryptoHashProvider();

		cryptoVerifierRegistry.register(messageDigestCryptoHashProvider);

		_cryptoHashGenerators = Arrays.asList(
			new CryptoHashGeneratorImpl(bCryptCryptoHashProvider),
			new CryptoHashGeneratorImpl(messageDigestCryptoHashProvider));

		_cryptoHashVerifier = cryptoVerifierRegistry.getCryptoHashVerifier();
	}

	@Test
	public void testGenerate() throws Exception {
		for (CryptoHashGenerator cryptoHashGenerator : _cryptoHashGenerators) {
			CryptoHashResponse cryptoHashResponse =
				cryptoHashGenerator.generate(_INPUT);

			Assert.assertFalse(
				_cryptoHashVerifier.verify(
					_randomBytes(), cryptoHashResponse.getHash(),
					cryptoHashResponse.getCryptoHashVerificationContext()));
			Assert.assertTrue(
				_cryptoHashVerifier.verify(
					_INPUT, cryptoHashResponse.getHash(),
					cryptoHashResponse.getCryptoHashVerificationContext()));
		}

		List<CryptoHashVerificationContext> cryptoHashVerificationContexts =
			new ArrayList<>();
		byte[] hash = _INPUT;

		for (CryptoHashGenerator cryptoHashGenerator : _cryptoHashGenerators) {
			CryptoHashResponse cryptoHashResponse =
				cryptoHashGenerator.generate(hash);

			cryptoHashVerificationContexts.add(
				cryptoHashResponse.getCryptoHashVerificationContext());

			hash = cryptoHashResponse.getHash();
		}

		Assert.assertFalse(
			_cryptoHashVerifier.verify(
				_randomBytes(), hash, cryptoHashVerificationContexts));
		Assert.assertTrue(
			_cryptoHashVerifier.verify(
				_INPUT, hash, cryptoHashVerificationContexts));
	}

	private static byte[] _randomBytes() {
		String string = RandomTestUtil.randomString();

		return string.getBytes();
	}

	private static final byte[] _INPUT = _randomBytes();

	private List<CryptoHashGenerator> _cryptoHashGenerators;
	private CryptoHashVerifier _cryptoHashVerifier;

}