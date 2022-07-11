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

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.crypto.hash.CryptoHashVerificationContext;
import com.liferay.portal.crypto.hash.CryptoHashVerifier;
import com.liferay.portal.crypto.hash.exception.CryptoHashException;
import com.liferay.portal.crypto.hash.spi.CryptoHashProvider;
import com.liferay.portal.crypto.hash.spi.CryptoHashProviderFactory;
import com.liferay.portal.crypto.hash.spi.CryptoHashProviderResponse;

import java.security.MessageDigest;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = CryptoHashVerifier.class)
public class CryptoHashVerifierImpl implements CryptoHashVerifier {

	@Override
	public boolean verify(
			byte[] input, byte[] hash,
			CryptoHashVerificationContext... cryptoHashVerificationContexts)
		throws CryptoHashException {

		for (CryptoHashVerificationContext cryptoHashVerificationContext :
				cryptoHashVerificationContexts) {

			CryptoHashProvider cryptoHashProvider = _getCryptoHashProvider(
				cryptoHashVerificationContext.
					getCryptoHashProviderFactoryName(),
				cryptoHashVerificationContext.
					getCryptoHashProviderProperties());

			CryptoHashProviderResponse cryptoHashProviderResponse =
				cryptoHashProvider.generate(
					cryptoHashVerificationContext.getSalt(), input);

			input = cryptoHashProviderResponse.getHash();
		}

		return MessageDigest.isEqual(input, hash);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CryptoHashProviderFactory.class, null,
			ServiceReferenceMapperFactory.createFromFunction(
				bundleContext,
				CryptoHashProviderFactory::getCryptoHashProviderFactoryName));
	}

	private CryptoHashProvider _getCryptoHashProvider(
			String cryptoHashProviderFactoryName,
			Map<String, ?> cryptoHashProviderProperties)
		throws CryptoHashException {

		CryptoHashProviderFactory cryptoHashProviderFactory =
			_serviceTrackerMap.getService(cryptoHashProviderFactoryName);

		if (cryptoHashProviderFactory == null) {
			throw new CryptoHashException(
				"No crypto hash provider factory found for " +
					cryptoHashProviderFactoryName);
		}

		return cryptoHashProviderFactory.create(cryptoHashProviderProperties);
	}

	private ServiceTrackerMap<String, CryptoHashProviderFactory>
		_serviceTrackerMap;

}