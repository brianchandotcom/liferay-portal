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

import com.liferay.portal.crypto.hash.CryptoHashVerificationContext;
import com.liferay.portal.crypto.hash.CryptoHashVerifier;
import com.liferay.portal.crypto.hash.exception.CryptoHashException;
import com.liferay.portal.crypto.hash.spi.CryptoHashProvider;
import com.liferay.portal.crypto.hash.spi.CryptoHashProviderResponse;

import java.security.MessageDigest;

/**
 * @author Carlos Sierra Andrés
 */
public class CryptoHashVerifierImpl implements CryptoHashVerifier {

	public CryptoHashVerifierImpl(
		CryptoHashProviderFactoryRegistry cryptoHashProviderRegistry) {

		_cryptoHashProviderFactoryRegistry = cryptoHashProviderRegistry;
	}

	@Override
	public boolean verify(
			byte[] input, byte[] hash,
			CryptoHashVerificationContext... cryptoHashVerificationContexts)
		throws CryptoHashException {

		for (CryptoHashVerificationContext cryptoHashVerificationContext :
				cryptoHashVerificationContexts) {

			CryptoHashProvider cryptoHashProvider;

			try {
				cryptoHashProvider =
					_cryptoHashProviderFactoryRegistry.getCryptoHashProvider(
						cryptoHashVerificationContext.
							getCryptoHashProviderFactoryName(),
						cryptoHashVerificationContext.
							getCryptoHashProviderProperties());
			}
			catch (Exception exception) {
				throw new CryptoHashException(exception);
			}

			CryptoHashProviderResponse cryptoHashProviderResponse =
				cryptoHashProvider.generate(
					cryptoHashVerificationContext.getSalt(), input);

			input = cryptoHashProviderResponse.getHash();
		}

		return MessageDigest.isEqual(input, hash);
	}

	private final CryptoHashProviderFactoryRegistry
		_cryptoHashProviderFactoryRegistry;

}