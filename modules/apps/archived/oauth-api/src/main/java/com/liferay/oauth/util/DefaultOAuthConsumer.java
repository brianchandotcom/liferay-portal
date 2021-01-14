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