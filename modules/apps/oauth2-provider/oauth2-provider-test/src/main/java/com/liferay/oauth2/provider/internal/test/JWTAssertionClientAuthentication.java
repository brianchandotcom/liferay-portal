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

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author Arthur Chan
 */
public class JWTAssertionClientAuthentication implements ClientAuthentication {

	public JWTAssertionClientAuthentication(
		WebTarget audienceWebTarget, String clientId, boolean clientIdInForm,
		String issuer, String key, boolean symmetricSignature) {

		String jwtAssertion = null;

		if (symmetricSignature) {
			jwtAssertion = JWTAssertionUtil.getJWTAssertionHS256(
				audienceWebTarget.getUri(), clientId, issuer, key);
		}
		else {
			jwtAssertion = JWTAssertionUtil.getJWTAssertionRS256(
				audienceWebTarget.getUri(), clientId, key, issuer);
		}

		_clientAuthenticationParameters.add("client_assertion", jwtAssertion);
		_clientAuthenticationParameters.add(
			"client_assertion_type",
			"urn:ietf:params:oauth:client-assertion-type:jwt-bearer");

		if (clientIdInForm) {
			_clientAuthenticationParameters.add("client_id", clientId);
		}
	}

	@Override
	public MultivaluedMap<String, String> getClientAuthenticationParameters() {
		return _clientAuthenticationParameters;
	}

	private final MultivaluedMap<String, String>
		_clientAuthenticationParameters = new MultivaluedHashMap<>();

}