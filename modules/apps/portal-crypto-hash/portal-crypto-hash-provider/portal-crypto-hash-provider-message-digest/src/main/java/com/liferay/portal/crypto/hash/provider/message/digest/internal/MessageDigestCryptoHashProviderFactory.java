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

package com.liferay.portal.crypto.hash.provider.message.digest.internal;

import com.liferay.portal.crypto.hash.spi.CryptoHashProvider;
import com.liferay.portal.crypto.hash.spi.CryptoHashProviderFactory;

import java.util.Map;

/**
 * @author Arthur Chan
 */
public class MessageDigestCryptoHashProviderFactory
	implements CryptoHashProviderFactory {

	@Override
	public CryptoHashProvider create(
			Map<String, ?> cryptoHashProviderProperties)
		throws Exception {

		if ((cryptoHashProviderProperties == null) ||
			cryptoHashProviderProperties.isEmpty()) {

			return new MessageDigestCryptoHashProvider(
				getCryptoHashProviderFactoryName());
		}

		return new MessageDigestCryptoHashProvider(
			getCryptoHashProviderFactoryName(), cryptoHashProviderProperties);
	}

	@Override
	public String getCryptoHashProviderFactoryName() {
		return _CRYPTO_HASH_PROVIDER_FACTORY_NAME;
	}

	private static final String _CRYPTO_HASH_PROVIDER_FACTORY_NAME =
		"MessageDigest";

}