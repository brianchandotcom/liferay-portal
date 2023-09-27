/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.crypto.hash.spi;

import com.liferay.portal.crypto.hash.exception.CryptoHashException;

import java.util.Map;

/**
 * @author Arthur Chan
 */
public interface CryptoHashProviderFactory {

	public CryptoHashProvider create(
			Map<String, ?> cryptoHashProviderProperties)
		throws CryptoHashException;

	public String getCryptoHashProviderFactoryName();

}