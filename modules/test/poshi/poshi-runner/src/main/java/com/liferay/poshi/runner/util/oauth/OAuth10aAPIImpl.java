/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.util.oauth;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;

import com.liferay.poshi.core.util.StringUtil;

/**
 * @author Leslie Wong
 */
public class OAuth10aAPIImpl extends DefaultApi10a {

	public OAuth10aAPIImpl(
		String accessTokenEndpoint, String authorizationURL,
		String requestTokenEndpoint) {

		_accessTokenEndpoint = accessTokenEndpoint;
		_authorizationURL = authorizationURL;
		_requestTokenEndpoint = requestTokenEndpoint;
	}

	@Override
	public String getAccessTokenEndpoint() {
		return _accessTokenEndpoint;
	}

	@Override
	public String getAuthorizationUrl(OAuth1RequestToken oAuth1RequestToken) {
		return StringUtil.replace(
			_authorizationURL, "{0}", oAuth1RequestToken.getToken());
	}

	@Override
	public String getRequestTokenEndpoint() {
		return _requestTokenEndpoint;
	}

	private final String _accessTokenEndpoint;
	private final String _authorizationURL;
	private final String _requestTokenEndpoint;

}