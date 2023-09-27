/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal;

import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.pkce.CodeVerifier;
import com.nimbusds.openid.connect.sdk.Nonce;

import java.io.Serializable;

/**
 * @author Arthur Chan
 */
public class OpenIdConnectAuthenticationSession implements Serializable {

	public OpenIdConnectAuthenticationSession(
		CodeVerifier codeVerifier, Nonce nonce, long oAuthClientEntryId,
		State state) {

		_codeVerifier = codeVerifier;
		_nonce = nonce;
		_oAuthClientEntryId = oAuthClientEntryId;
		_state = state;
	}

	public CodeVerifier getCodeVerifier() {
		return _codeVerifier;
	}

	public Nonce getNonce() {
		return _nonce;
	}

	public long getOAuthClientEntryId() {
		return _oAuthClientEntryId;
	}

	public State getState() {
		return _state;
	}

	private final CodeVerifier _codeVerifier;
	private final Nonce _nonce;
	private final long _oAuthClientEntryId;
	private final State _state;

}