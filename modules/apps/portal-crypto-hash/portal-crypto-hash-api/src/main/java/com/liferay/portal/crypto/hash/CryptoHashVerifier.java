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

package com.liferay.portal.crypto.hash;

import com.liferay.portal.crypto.hash.exception.CryptoHashException;

import java.util.Collection;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Carlos Sierra Andrés
 */
@ProviderType
public interface CryptoHashVerifier {

	public default boolean verify(
			byte[] input, byte[] hash,
			Collection<CryptoHashVerificationContext>
				cryptoHashVerificationContexts)
		throws CryptoHashException {

		return verify(
			input, hash,
			cryptoHashVerificationContexts.toArray(
				new CryptoHashVerificationContext[0]));
	}

	public boolean verify(
			byte[] input, byte[] hash,
			CryptoHashVerificationContext... cryptoHashVerificationContexts)
		throws CryptoHashException;

}