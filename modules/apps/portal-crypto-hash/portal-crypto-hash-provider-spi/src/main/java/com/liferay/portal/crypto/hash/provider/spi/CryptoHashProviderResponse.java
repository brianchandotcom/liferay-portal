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

package com.liferay.portal.crypto.hash.provider.spi;

import java.util.Map;

/**
 * @author Carlos Sierra Andrés
 */
public final class CryptoHashProviderResponse {

	public CryptoHashProviderResponse(
		byte[] hash, byte[] salt, String name, Map<String, ?> properties) {

		_hash = hash;
		_salt = salt;
		_name = name;
		_properties = properties;
	}

	public String getCryptoHashProviderName() {
		return _name;
	}

	public Map<String, ?> getCryptoHashProviderProperties() {
		return _properties;
	}

	public byte[] getHash() {
		return _hash;
	}

	public byte[] getSalt() {
		return _salt;
	}

	private final byte[] _hash;
	private final String _name;
	private final Map<String, ?> _properties;
	private final byte[] _salt;

}