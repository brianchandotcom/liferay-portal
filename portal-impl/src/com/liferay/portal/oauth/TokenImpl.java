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

import com.liferay.portal.kernel.oauth.Token;

/**
 * @author Brian Wing Shun Chan
 */
public class TokenImpl implements Token {

	public TokenImpl(org.scribe.model.Token token) {
		_token = token;
	}

	@Override
	public String getSecret() {
		return _token.getSecret();
	}

	@Override
	public String getToken() {
		return _token.getToken();
	}

	@Override
	public Object getWrappedToken() {
		return _token;
	}

	private final org.scribe.model.Token _token;

}