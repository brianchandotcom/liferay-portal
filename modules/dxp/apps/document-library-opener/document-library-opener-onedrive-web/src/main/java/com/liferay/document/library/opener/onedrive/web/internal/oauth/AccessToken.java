/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.onedrive.web.internal.oauth;

import com.github.scribejava.core.model.OAuth2AccessToken;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Cristina González
 */
public class AccessToken implements Serializable {

	public AccessToken(OAuth2AccessToken oAuth2AccessToken) {
		if (oAuth2AccessToken == null) {
			throw new IllegalArgumentException("Access token is null");
		}

		Integer expiresIn = oAuth2AccessToken.getExpiresIn();

		if (expiresIn == null) {
			expiresIn = 0;
		}

		LocalDateTime localDateTime = LocalDateTime.now();

		_expirationLocalDateTime = localDateTime.plus(
			expiresIn, ChronoUnit.SECONDS);

		_oAuth2AccessToken = oAuth2AccessToken;
	}

	public String getAccessToken() {
		return _oAuth2AccessToken.getAccessToken();
	}

	public String getRefreshToken() {
		return _oAuth2AccessToken.getRefreshToken();
	}

	public boolean isValid() {
		LocalDateTime localDateTime = LocalDateTime.now();

		if (localDateTime.isBefore(_expirationLocalDateTime)) {
			return true;
		}

		return false;
	}

	private final LocalDateTime _expirationLocalDateTime;
	private final OAuth2AccessToken _oAuth2AccessToken;

}