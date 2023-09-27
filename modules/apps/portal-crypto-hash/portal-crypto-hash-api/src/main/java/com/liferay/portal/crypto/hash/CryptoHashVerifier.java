/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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