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

package com.liferay.oauth2.provider.token.endpoint.test;

import com.liferay.oauth2.provider.client.test.BaseClientTestCase;
import com.liferay.oauth2.provider.token.endpoint.util.AuthorizationGrant;
import com.liferay.oauth2.provider.token.endpoint.util.ClientAuthentication;
import com.liferay.oauth2.provider.token.endpoint.util.ClientPasswordClientAuthentication;
import com.liferay.portal.kernel.util.Validator;

import java.util.function.Function;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Arthur Chan
 */
public abstract class BaseTokenEndpointTestCase extends BaseClientTestCase {

	@Test
	public void testClientAuthentications() {
		Assert.assertTrue(
			Validator.isNotNull(
				_getToken(
					getDefaultAuthorizationGrant(),
					TEST_CLIENT_PASSWORD_CLIENT_AUTHENTICATION)));
	}

	protected static Invocation.Builder getInvocationBuilder() {
		return getInvocationBuilder(
			null, getTokenWebTarget(), Function.identity());
	}

	protected static WebTarget getTokenWebTarget() {
		WebTarget webTarget = getOAuth2WebTarget();

		return webTarget.path("token");
	}

	protected abstract AuthorizationGrant getDefaultAuthorizationGrant();

	protected String getToken(AuthorizationGrant authorizationGrant) {
		return _getToken(
			authorizationGrant, TEST_CLIENT_PASSWORD_CLIENT_AUTHENTICATION);
	}

	protected static final String TEST_CLIENT_ID_01 = "test_client_id_01";

	protected static final ClientPasswordClientAuthentication
		TEST_CLIENT_PASSWORD_CLIENT_AUTHENTICATION =
			new ClientPasswordClientAuthentication(
				TEST_CLIENT_ID_01,
				BaseTokenEndpointTestCase.TEST_CLIENT_SECRET);

	protected static final String TEST_CLIENT_SECRET =
		"oauthTestApplicationSecret";

	protected static Invocation.Builder invocationBuilder =
		getInvocationBuilder();

	private String _getToken(
		AuthorizationGrant authorizationGrant,
		ClientAuthentication clientAuthentication) {

		return parseTokenString(
			_getTokenResponse(authorizationGrant, clientAuthentication));
	}

	private Response _getTokenResponse(
		AuthorizationGrant authorizationGrant,
		ClientAuthentication clientAuthentication) {

		MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();

		formData.putAll(authorizationGrant.getAuthorizationGrantParameters());

		formData.putAll(
			clientAuthentication.getClientAuthenticationParameters());

		return invocationBuilder.post(Entity.form(formData));
	}

}