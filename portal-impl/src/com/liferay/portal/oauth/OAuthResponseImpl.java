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

package com.liferay.portal.oauth;

import com.liferay.portal.kernel.oauth.OAuthResponse;

import org.scribe.model.Response;

/**
 * @author Brian Wing Shun Chan
 */
public class OAuthResponseImpl implements OAuthResponse {

	public OAuthResponseImpl(Response response) {
		_response = response;
	}

	@Override
	public String getBody() {
		return _response.getBody();
	}

	@Override
	public int getStatus() {
		return _response.getCode();
	}

	@Override
	public Object getWrappedOAuthResponse() {
		return _response;
	}

	private final Response _response;

}