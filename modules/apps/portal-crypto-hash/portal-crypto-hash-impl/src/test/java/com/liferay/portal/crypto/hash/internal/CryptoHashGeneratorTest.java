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
import com.liferay.portal.crypto.hash.provider.message.digest.internal.MessageDigestCryptoHashProvider;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.UnicodeFormatter;

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
		_cryptoHashGenerator = new CryptoHashGeneratorImpl(
			new MessageDigestCryptoHashProvider());
	}

	@Test
	public void testGenerate() throws Exception {
		CryptoHashResponse cryptoHashResponse = _cryptoHashGenerator.generate(
			_INPUT);

		Assert.assertFalse(
			_cryptoHashGenerator.verify(
				_randomBytes(), cryptoHashResponse.getHash(),
				cryptoHashResponse.getSalt()));
		Assert.assertTrue(
			_cryptoHashGenerator.verify(
				_INPUT, cryptoHashResponse.getHash(),
				cryptoHashResponse.getSalt()));
	}

	@Test
	public void testVerify() throws Exception {
		_cryptoHashGenerator.verify(_INPUT, _HASH, _SALT);
	}

	private static byte[] _randomBytes() {
		String string = RandomTestUtil.randomString();

		return string.getBytes();
	}

	private static final byte[] _HASH = UnicodeFormatter.hexToBytes(
		RandomTestUtil.randomString(128));

	private static final byte[] _INPUT = _randomBytes();

	private static final byte[] _SALT = _randomBytes();

	private CryptoHashGenerator _cryptoHashGenerator;

}