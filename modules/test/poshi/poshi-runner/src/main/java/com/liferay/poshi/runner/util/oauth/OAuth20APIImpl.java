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