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

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectFlowState;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.persistence.service.OpenIdConnectSessionLocalServiceUtil;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;
import com.nimbusds.openid.connect.sdk.Nonce;

import java.io.Serializable;

/**
 * @author Edward C. Han
 */
public class OpenIdConnectSessionImpl
	implements OpenIdConnectSession, Serializable {

	public OpenIdConnectSessionImpl(
		long openIdConnectSessionId, String openIdProviderName, Nonce nonce,
		State state, long userId) {

		_openIdConnectSessionId = openIdConnectSessionId;
		_openIdProviderName = openIdProviderName;
		_nonce = nonce;
		_state = state;

		_loginUserId = userId;
	}

	@Override
	public String getAccessTokenValue() {
		com.liferay.portal.security.sso.openid.connect.persistence.model.
			OpenIdConnectSession openIdConnectSession =
				OpenIdConnectSessionLocalServiceUtil.fetchOpenIdConnectSession(
					_openIdConnectSessionId);

		if (openIdConnectSession == null) {
			return null;
		}

		try {
			AccessToken accessToken = AccessToken.parse(
				JSONObjectUtils.parse(openIdConnectSession.getAccessToken()));

			return accessToken.getValue();
		}
		catch (ParseException parseException) {
			if (_log.isDebugEnabled()) {
				_log.debug(parseException);
			}

			return null;
		}
	}

	@Override
	public long getLoginTime() {
		return _LOGIN_TIME;
	}

	@Override
	public long getLoginUserId() {
		return _loginUserId;
	}

	@Override
	public String getNonceValue() {
		return _nonce.getValue();
	}

	@Override
	public OpenIdConnectFlowState getOpenIdConnectFlowState() {
		return _openIdConnectFlowState;
	}

	@Override
	public String getOpenIdProviderName() {
		return _openIdProviderName;
	}

	@Override
	public String getRefreshTokenValue() {
		com.liferay.portal.security.sso.openid.connect.persistence.model.
			OpenIdConnectSession openIdConnectSession =
				OpenIdConnectSessionLocalServiceUtil.fetchOpenIdConnectSession(
					_openIdConnectSessionId);

		if (openIdConnectSession == null) {
			return null;
		}

		return openIdConnectSession.getRefreshToken();
	}

	@Override
	public String getStateValue() {
		return _state.getValue();
	}

	@Override
	public void setOpenIdConnectFlowState(
		OpenIdConnectFlowState openIdConnectFlowState) {

		_openIdConnectFlowState = openIdConnectFlowState;
	}

	private static final long _LOGIN_TIME = System.currentTimeMillis();

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectSessionImpl.class);

	private final long _loginUserId;
	private final Nonce _nonce;
	private OpenIdConnectFlowState _openIdConnectFlowState =
		OpenIdConnectFlowState.PORTAL_AUTH_COMPLETE;
	private final long _openIdConnectSessionId;
	private final String _openIdProviderName;
	private final State _state;

}