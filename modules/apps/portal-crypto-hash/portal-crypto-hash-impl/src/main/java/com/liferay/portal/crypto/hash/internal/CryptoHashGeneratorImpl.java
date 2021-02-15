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
import com.liferay.portal.crypto.hash.exception.CryptoHashException;
import com.liferay.portal.crypto.hash.spi.CryptoHashProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
@Component(service = CryptoHashGenerator.class)
public class CryptoHashGeneratorImpl implements CryptoHashGenerator {

	public CryptoHashGeneratorImpl(CryptoHashProvider cryptoHashProvider)
		throws NoSuchAlgorithmException {

		_cryptoHashProvider = cryptoHashProvider;
	}

	@Override
	public CryptoHashResponse generate(byte[] input)
		throws CryptoHashException {

		byte[] salt = _cryptoHashProvider.generateSalt();

		return new CryptoHashResponse(
			_cryptoHashProvider.generate(salt, input), salt);
	}

	@Override
	public boolean verify(byte[] input, byte[] hash, byte[] salt)
		throws CryptoHashException {

		return MessageDigest.isEqual(
			_cryptoHashProvider.generate(salt, input), hash);
	}

	private final CryptoHashProvider _cryptoHashProvider;

}