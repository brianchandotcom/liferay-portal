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

package com.liferay.oauth2.provider.internal.test;

import com.liferay.oauth2.provider.internal.test.util.JWTAssertionUtil;

import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.rs.security.oauth2.grants.jwt.Constants;

/**
 * @author Arthur Chan
 */
public class TestJWTAssertionAuthorizationGrant
	implements TestAuthorizationGrant {

	public TestJWTAssertionAuthorizationGrant(
		String issuer, List<String> scopes, String subject,
		WebTarget audienceWebTarget) {

		_authorizationGrantData.add(
			"assertion",
			JWTAssertionUtil.getJWTAssertionRS256(
				audienceWebTarget.getUri(), issuer, JWTAssertionUtil.JWKS,
				subject));
		_authorizationGrantData.add("grant_type", Constants.JWT_BEARER_GRANT);

		if (scopes != null) {
			_authorizationGrantData.put("scope", scopes);
		}
	}

	@Override
	public MultivaluedMap<String, String> getAuthorizationGrantParameters() {
		return _authorizationGrantData;
	}

	private final MultivaluedMap<String, String> _authorizationGrantData =
		new MultivaluedHashMap<>();

}