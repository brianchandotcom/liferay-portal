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

package com.liferay.portal.crypto.hash.spi;

import com.liferay.portal.crypto.hash.exception.CryptoHashException;

import java.util.Map;

/**
 * @author Arthur Chan
 */
public interface CryptoHashProviderFactory {

	/**
	 * Construct a {@link CryptoHashProvider} from given hash provider meta information.
	 *
	 * @param cryptoHashProviderProperties A map of meta info required by some providers
	 * @return An instance of CryptoHashProvider
	 */
	public CryptoHashProvider create(
			Map<String, ?> cryptoHashProviderProperties)
		throws CryptoHashException;

	public String getCryptoHashProviderFactoryName();

}