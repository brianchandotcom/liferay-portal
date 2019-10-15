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

import java.io.IOException;

/**
 * @author Ivica Cardic
 */
public class DefaultOAuthMessage implements OAuthMessage {

	public DefaultOAuthMessage(net.oauth.OAuthMessage oAuthMessage) {
		_oAuthMessage = oAuthMessage;
	}

	@Override
	public String getConsumerKey() throws IOException {
		return _oAuthMessage.getConsumerKey();
	}

	@Override
	public String getParameter(String name) throws IOException {
		return _oAuthMessage.getParameter(name);
	}

	@Override
	public String getToken() throws IOException {
		return _oAuthMessage.getToken();
	}

	@Override
	public Object getWrappedOAuthMessage() {
		return _oAuthMessage;
	}

	private final net.oauth.OAuthMessage _oAuthMessage;

}