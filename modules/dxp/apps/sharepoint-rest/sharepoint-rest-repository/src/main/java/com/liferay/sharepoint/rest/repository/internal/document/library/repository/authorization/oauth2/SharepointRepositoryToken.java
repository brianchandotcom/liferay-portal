/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.oauth2;

import com.liferay.document.library.repository.authorization.oauth2.Token;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class SharepointRepositoryToken implements Token {

	public static Token newInstance(
		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {

		if (sharepointOAuth2TokenEntry == null) {
			return null;
		}

		return new SharepointRepositoryToken(
			sharepointOAuth2TokenEntry.getAccessToken(),
			sharepointOAuth2TokenEntry.getRefreshToken(),
			sharepointOAuth2TokenEntry.getExpirationDate());
	}

	public static Token newInstance(String accessToken) {
		if (Validator.isNull(accessToken)) {
			return null;
		}

		return new SharepointRepositoryToken(accessToken, null, null);
	}

	@Override
	public String getAccessToken() {
		return _accessToken;
	}

	@Override
	public Date getExpirationDate() {
		return _expirationDate;
	}

	@Override
	public String getRefreshToken() {
		return _refreshToken;
	}

	@Override
	public boolean isExpired() {
		return false;
	}

	private SharepointRepositoryToken(
		String accessToken, String refreshToken, Date expirationDate) {

		_accessToken = accessToken;
		_refreshToken = refreshToken;
		_expirationDate = expirationDate;
	}

	private final String _accessToken;
	private final Date _expirationDate;
	private final String _refreshToken;

}