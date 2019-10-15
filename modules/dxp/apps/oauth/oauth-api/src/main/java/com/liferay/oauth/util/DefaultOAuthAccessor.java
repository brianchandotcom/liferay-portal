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

package com.liferay.oauth.util;

import java.io.Serializable;

/**
 * @author Ivica Cardic
 */
public class DefaultOAuthAccessor implements OAuthAccessor, Serializable {

	public DefaultOAuthAccessor(net.oauth.OAuthAccessor oAuthAccessor) {
		_oAuthAccessor = oAuthAccessor;

		_oAuthConsumer = null;
	}

	public DefaultOAuthAccessor(OAuthConsumer oAuthConsumer) {
		_oAuthConsumer = oAuthConsumer;

		_oAuthAccessor = new net.oauth.OAuthAccessor(
			(net.oauth.OAuthConsumer)oAuthConsumer.getWrappedOAuthConsumer());
	}

	@Override
	public String getAccessToken() {
		return _oAuthAccessor.accessToken;
	}

	@Override
	public OAuthConsumer getOAuthConsumer() {
		_oAuthConsumer.setWrappedOAuthConsumer(_oAuthAccessor.consumer);

		return _oAuthConsumer;
	}

	@Override
	public Object getProperty(String name) {
		return _oAuthAccessor.getProperty(name);
	}

	@Override
	public String getRequestToken() {
		return _oAuthAccessor.requestToken;
	}

	@Override
	public String getTokenSecret() {
		return _oAuthAccessor.tokenSecret;
	}

	@Override
	public Object getWrappedOAuthAccessor() {
		return _oAuthAccessor;
	}

	@Override
	public void setAccessToken(String accesToken) {
		_oAuthAccessor.accessToken = accesToken;
	}

	@Override
	public void setProperty(String name, Object value) {
		_oAuthAccessor.setProperty(name, value);
	}

	@Override
	public void setRequestToken(String requestToken) {
		_oAuthAccessor.requestToken = requestToken;
	}

	@Override
	public void setTokenSecret(String tokenSecret) {
		_oAuthAccessor.tokenSecret = tokenSecret;
	}

	private final net.oauth.OAuthAccessor _oAuthAccessor;
	private final OAuthConsumer _oAuthConsumer;

}