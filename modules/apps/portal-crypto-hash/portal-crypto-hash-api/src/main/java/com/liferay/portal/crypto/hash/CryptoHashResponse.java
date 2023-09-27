/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.crypto.hash;

/**
 * @author Carlos Sierra Andrés
 */
public class CryptoHashResponse {

	public CryptoHashResponse(
		CryptoHashVerificationContext cryptoHashVerificationContext,
		byte[] hash) {

		_cryptoHashVerificationContext = cryptoHashVerificationContext;
		_hash = hash;
	}

	public CryptoHashVerificationContext getCryptoHashVerificationContext() {
		return _cryptoHashVerificationContext;
	}

	public byte[] getHash() {
		return _hash;
	}

	private final CryptoHashVerificationContext _cryptoHashVerificationContext;
	private final byte[] _hash;

}