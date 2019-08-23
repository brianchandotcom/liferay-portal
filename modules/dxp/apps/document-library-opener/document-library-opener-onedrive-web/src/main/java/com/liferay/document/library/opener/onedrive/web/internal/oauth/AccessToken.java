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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Cristina González
 */
public class AccessToken {

	public AccessToken(OAuth2AccessToken oAuth2AccessToken) {
		if (oAuth2AccessToken == null) {
			throw new IllegalArgumentException("Access token is null");
		}

		Integer expiresIn = oAuth2AccessToken.getExpiresIn();

		if (expiresIn == null) {
			expiresIn = 0;
		}

		LocalDateTime now = LocalDateTime.now();

		_expirationDate = now.plus(expiresIn, ChronoUnit.SECONDS);

		_oAuth2AccessToken = oAuth2AccessToken;
	}

	public String getAccessToken() {
		return _oAuth2AccessToken.getAccessToken();
	}

	public String getRefreshToken() {
		return _oAuth2AccessToken.getRefreshToken();
	}

	public boolean isValid() {
		LocalDateTime now = LocalDateTime.now();

		if (now.isBefore(_expirationDate)) {
			return true;
		}

		return false;
	}

	private final LocalDateTime _expirationDate;
	private final OAuth2AccessToken _oAuth2AccessToken;

}