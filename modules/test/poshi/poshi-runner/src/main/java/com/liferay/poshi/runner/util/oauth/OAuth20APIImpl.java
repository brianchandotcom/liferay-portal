/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.util.oauth;

import com.github.scribejava.core.builder.api.DefaultApi20;

/**
 * @author Leslie Wong
 */
public class OAuth20APIImpl extends DefaultApi20 {

	public OAuth20APIImpl(
		String accessTokenEndpoint, String authorizationBaseURL) {

		_accessTokenEndpoint = accessTokenEndpoint;
		_authorizationBaseURL = authorizationBaseURL;
	}

	@Override
	public String getAccessTokenEndpoint() {
		return _accessTokenEndpoint;
	}

	@Override
	public String getAuthorizationBaseUrl() {
		return _authorizationBaseURL;
	}

	private final String _accessTokenEndpoint;
	private final String _authorizationBaseURL;

}