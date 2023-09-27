/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.crypto.hash.spi;

import com.liferay.portal.crypto.hash.exception.CryptoHashException;
import com.liferay.portal.kernel.security.SecureRandomUtil;

/**
 * @author Arthur Chan
 */
public interface CryptoHashProvider {

	public CryptoHashProviderResponse generate(byte[] salt, byte[] input)
		throws CryptoHashException;

	public default byte[] generateSalt() {
		byte[] salt = new byte[16];

		for (int i = 0; i < 16; ++i) {
			salt[i] = SecureRandomUtil.nextByte();
		}

		return salt;
	}

}