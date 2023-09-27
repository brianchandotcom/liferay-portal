/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.crypto.hash.spi;

import java.util.Map;

/**
 * @author Carlos Sierra Andrés
 * @author Arthur Chan
 */
public class CryptoHashProviderResponse {

	public CryptoHashProviderResponse(
		String cryptoHashProviderFactoryName,
		Map<String, ?> cryptoHashProviderProperties, byte[] hash) {

		_cryptoHashProviderFactoryName = cryptoHashProviderFactoryName;
		_cryptoHashProviderProperties = cryptoHashProviderProperties;
		_hash = hash;
	}

	public String getCryptoHashProviderFactoryName() {
		return _cryptoHashProviderFactoryName;
	}

	public Map<String, ?> getCryptoHashProviderProperties() {
		return _cryptoHashProviderProperties;
	}

	public byte[] getHash() {
		return _hash;
	}

	private final String _cryptoHashProviderFactoryName;
	private final Map<String, ?> _cryptoHashProviderProperties;
	private final byte[] _hash;

}