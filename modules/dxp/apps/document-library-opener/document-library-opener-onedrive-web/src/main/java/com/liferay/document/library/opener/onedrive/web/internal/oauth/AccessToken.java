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

package com.liferay.document.library.opener.onedrive.web.internal.oauth;

import com.github.scribejava.core.model.OAuth2AccessToken;

/**
 * @author Cristina González
 */
public class AccessToken {

	public AccessToken(OAuth2AccessToken oAuth2AccessToken) {
		if (oAuth2AccessToken == null) {
			throw new IllegalArgumentException("Access token is null");
		}

		_oAuth2AccessToken = oAuth2AccessToken;
	}

	public String getAccessToken() {
		return _oAuth2AccessToken.getAccessToken();
	}

	public String getRefreshToken() {
		return _oAuth2AccessToken.getRefreshToken();
	}

	public boolean isValid() {
		if (_oAuth2AccessToken.getExpiresIn() > 0) {
			return true;
		}

		return false;
	}

	private final OAuth2AccessToken _oAuth2AccessToken;

}