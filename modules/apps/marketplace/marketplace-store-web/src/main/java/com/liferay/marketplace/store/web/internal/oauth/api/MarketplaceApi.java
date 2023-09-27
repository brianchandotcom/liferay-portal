/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.store.web.internal.oauth.api;

import com.liferay.marketplace.store.web.internal.configuration.MarketplaceStoreWebConfigurationValues;
import com.liferay.portal.kernel.util.HttpComponentsUtil;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.OAuthConstants;
import org.scribe.model.Token;

/**
 * @author Ryan Park
 */
public class MarketplaceApi extends DefaultApi10a {

	@Override
	public String getAccessTokenEndpoint() {
		return _ACCESS_TOKEN_ENDPOINT_URL;
	}

	@Override
	public String getAuthorizationUrl(Token token) {
		return HttpComponentsUtil.addParameter(
			_AUTHORIZATION_URL, OAuthConstants.TOKEN, token.getToken());
	}

	@Override
	public String getRequestTokenEndpoint() {
		return _REQUEST_TOKEN_ENDPOINT_URL;
	}

	private static final String _ACCESS_TOKEN_ENDPOINT_URL =
		MarketplaceStoreWebConfigurationValues.MARKETPLACE_URL +
			"/c/portal/oauth/access_token";

	private static final String _AUTHORIZATION_URL =
		MarketplaceStoreWebConfigurationValues.MARKETPLACE_URL +
			"/c/portal/oauth/authorize";

	private static final String _REQUEST_TOKEN_ENDPOINT_URL =
		MarketplaceStoreWebConfigurationValues.MARKETPLACE_URL +
			"/c/portal/oauth/request_token";

}