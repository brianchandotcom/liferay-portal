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

package com.liferay.portal.crypto.hash.provider.spi.factory;

import com.liferay.portal.crypto.hash.provider.spi.CryptoHashProvider;

import java.util.Map;

import org.osgi.annotation.versioning.ConsumerType;

/**
 * @author Arthur Chan
 */
@ConsumerType
public interface CryptoHashProviderFactory {

	/**
	 * Construct a {@link CryptoHashProvider} from given hash generator information.
	 *
	 * @param cryptoHashProviderName The name of the Hash Provider
	 * @param cryptoHashProviderProperties A map of meta info required by some algorithms
	 * @return An instance of CryptoHashProvider
	 */
	public CryptoHashProvider create(
			String cryptoHashProviderName,
			Map<String, ?> cryptoHashProviderProperties)
		throws Exception;

}