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

import com.liferay.portal.crypto.hash.exception.CryptoHashException;
import com.liferay.portal.crypto.hash.spi.CryptoHashProvider;
import com.liferay.portal.crypto.hash.spi.CryptoHashProviderFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
public class CryptoHashProviderFactoryRegistry {

	public CryptoHashProvider getCryptoHashProvider(
			String cryptoHashProviderFactoryName,
			Map<String, ?> cryptoHashProviderProperties)
		throws CryptoHashException {

		CryptoHashProviderFactory cryptoHashProviderFactory =
			_cryptoHashProviderFactories.get(cryptoHashProviderFactoryName);

		if (cryptoHashProviderFactory == null) {
			throw new CryptoHashException(
				"No crypto hash provider factory found for " +
					cryptoHashProviderFactoryName);
		}

		return cryptoHashProviderFactory.create(cryptoHashProviderProperties);
	}

	public void register(CryptoHashProviderFactory cryptoHashProviderFactory) {
		_cryptoHashProviderFactories.put(
			cryptoHashProviderFactory.getCryptoHashProviderFactoryName(),
			cryptoHashProviderFactory);
	}

	public void unregister(String cryptoHashProviderName) {
		_cryptoHashProviderFactories.remove(cryptoHashProviderName);
	}

	private final Map<String, CryptoHashProviderFactory>
		_cryptoHashProviderFactories = new ConcurrentHashMap<>();

}