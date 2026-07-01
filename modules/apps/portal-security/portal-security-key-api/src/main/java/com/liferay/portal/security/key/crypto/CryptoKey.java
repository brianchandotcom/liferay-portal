/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.crypto;

import com.liferay.portal.security.key.KeyReference;

/**
 * @author Tomas Polesovsky
 * @author Christopher Kian
 */
public class CryptoKey {

	public CryptoKey(
		String algorithm, String cipherSpec, long createDate,
		KeyReference keyReference) {

		_algorithm = algorithm;
		_cipherSpec = cipherSpec;
		_createDate = createDate;
		_keyReference = keyReference;
	}

	public String getAlgorithm() {
		return _algorithm;
	}

	public String getCipherSpec() {
		return _cipherSpec;
	}

	public long getCreateDate() {
		return _createDate;
	}

	public KeyReference getKeyReference() {
		return _keyReference;
	}

	private final String _algorithm;
	private final String _cipherSpec;
	private final long _createDate;
	private final KeyReference _keyReference;

}