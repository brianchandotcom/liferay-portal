/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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