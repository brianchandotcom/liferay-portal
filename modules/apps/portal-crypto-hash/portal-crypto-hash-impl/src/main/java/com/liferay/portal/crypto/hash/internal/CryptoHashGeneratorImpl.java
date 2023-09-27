/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.crypto.hash.internal;

import com.liferay.portal.crypto.hash.CryptoHashGenerator;
import com.liferay.portal.crypto.hash.CryptoHashResponse;
import com.liferay.portal.crypto.hash.CryptoHashVerificationContext;
import com.liferay.portal.crypto.hash.exception.CryptoHashException;
import com.liferay.portal.crypto.hash.spi.CryptoHashProvider;
import com.liferay.portal.crypto.hash.spi.CryptoHashProviderResponse;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
public class CryptoHashGeneratorImpl implements CryptoHashGenerator {

	public CryptoHashGeneratorImpl(CryptoHashProvider cryptoHashProvider) {
		_cryptoHashProvider = cryptoHashProvider;
	}

	@Override
	public CryptoHashResponse generate(byte[] input)
		throws CryptoHashException {

		byte[] salt = _cryptoHashProvider.generateSalt();

		CryptoHashProviderResponse cryptoHashProviderResponse =
			_cryptoHashProvider.generate(salt, input);

		return new CryptoHashResponse(
			new CryptoHashVerificationContext(
				cryptoHashProviderResponse.getCryptoHashProviderFactoryName(),
				cryptoHashProviderResponse.getCryptoHashProviderProperties(),
				salt),
			cryptoHashProviderResponse.getHash());
	}

	private final CryptoHashProvider _cryptoHashProvider;

}