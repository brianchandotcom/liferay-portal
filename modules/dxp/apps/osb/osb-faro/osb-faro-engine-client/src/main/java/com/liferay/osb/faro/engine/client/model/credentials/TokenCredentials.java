/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model.credentials;

import com.liferay.osb.faro.engine.client.model.Credentials;

/**
 * @author Matthew Kong
 */
public class TokenCredentials implements Credentials {

	public static final String TYPE = "Token Authentication";

	@Override
	public void clearPasswords() {
		_privateKey = null;
		_publicKey = null;
	}

	public String getPrivateKey() {
		return _privateKey;
	}

	public String getPublicKey() {
		return _publicKey;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	public void setPrivateKey(String privateKey) {
		_privateKey = privateKey;
	}

	public void setPublicKey(String publicKey) {
		_publicKey = publicKey;
	}

	private String _privateKey;
	private String _publicKey;

}