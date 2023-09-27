/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.crypto.hash;

import com.liferay.portal.crypto.hash.exception.CryptoHashException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
@ProviderType
public interface CryptoHashGenerator {

	public CryptoHashResponse generate(byte[] input) throws CryptoHashException;

}