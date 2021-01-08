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

import com.liferay.oauth.model.OAuthApplication;

import java.io.Serializable;

/**
 * @author Ivica Cardic
 */
public class DefaultOAuthConsumer implements OAuthConsumer, Serializable {

	public DefaultOAuthConsumer(net.oauth.OAuthConsumer oAuthConsumer) {
		_oAuthConsumer = oAuthConsumer;

		_oAuthApplication = null;
	}

	public DefaultOAuthConsumer(OAuthApplication oAuthApplication) {
		_oAuthApplication = oAuthApplication;

		_oAuthConsumer = new net.oauth.OAuthConsumer(
			oAuthApplication.getCallbackURI(),
			oAuthApplication.getConsumerKey(),
			oAuthApplication.getConsumerSecret(), null);
	}

	@Override
	public String getCallbackURL() {
		return _oAuthConsumer.callbackURL;
	}

	@Override
	public OAuthApplication getOAuthApplication() {
		return _oAuthApplication;
	}

	@Override
	public Object getProperty(String name) {
		return _oAuthConsumer.getProperty(name);
	}

	@Override
	public Object getWrappedOAuthConsumer() {
		return _oAuthConsumer;
	}

	@Override
	public void setWrappedOAuthConsumer(Object oAuthConsumer) {
		_oAuthConsumer = (net.oauth.OAuthConsumer)oAuthConsumer;
	}

	private final OAuthApplication _oAuthApplication;
	private net.oauth.OAuthConsumer _oAuthConsumer;

}