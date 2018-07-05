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

package com.liferay.oauth2.provider.client.test;

import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.test.internal.activator.BaseTestPreparatorBundleActivator;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.net.URI;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stian Sigvartsen
 */
@RunAsClient
@RunWith(Arquillian.class)
public class SecurityTest extends BaseClientTestCase {

	@Deployment
	public static Archive<?> getDeployment() throws Exception {
		return BaseClientTestCase.getDeployment(
			SecurityTestPreparatorBundleActivator.class);
	}

	/**
	 * OAUTH2-99
	 */
	@Test
	public void testPreventClickJacking() {
		Assert.assertEquals(
			"SAMEORIGIN",
			getCodeResponse(
				"test@liferay.com", "test", null,
				getCode(
					webTarget -> webTarget.queryParam(
						"client_id", "oauthTestApplicationCode"
					).queryParam(
						"response_type", "code"
					)),
				this::parseXFrameOptionsHeader));
	}

	/**
	 * OAUTH2-96
	 */
	@Ignore
	@Test
	public void testPreventCSRFUsingMandatoryStateParam() {
		Assert.assertEquals(
			"invalid_request",
			getCodeResponse(
				"test@liferay.com", "test", null,
				getCode(
					webTarget -> webTarget.queryParam(
						"client_id", "oauthTestApplicationCode"
					).queryParam(
						"response_type", "code"
					)),
				this::parseErrorParameter));
	}

	/**
	 * OAUTH2-96
	 */
	@Test
	public void testPreventCSRFUsingPKCE() {
		String authorizationCode = getCodeResponse(
			"test@liferay.com", "test", null,
			getCode(
				webTarget -> webTarget.queryParam(
					"client_id", "oauthTestApplicationCodePKCE"
				).queryParam(
					"response_type", "code"
				).queryParam(
					"code_challenge", "correctCodeChallenge"
				)),
			this::parseAuthorizationCodeString);

		Assert.assertNotNull(authorizationCode);

		Assert.assertEquals(
			"invalid_grant",
			getToken(
				"oauthTestApplicationCodePKCE", null,
				getExchangeAuthorizationCodePKCE(
					authorizationCode, null, "wrongCodeVerifier"),
				this::parseError));
	}

	/**
	 * OAUTH2-96
	 */
	@Test
	public void testPreventCSRFUsingStateParam() {
		String state = "csrf_token";

		String responseState = getCodeResponse(
			"test@liferay.com", "test", null,
			getCode(
				webTarget -> webTarget.queryParam(
					"client_id", "oauthTestApplicationCode"
				).queryParam(
					"response_type", "code"
				).queryParam(
					"state", state
				)),
			this::parseStateString);

		Assert.assertEquals(state, responseState);
	}

	/**
	 * OAUTH2-97
	 */
	@Test
	public void testPreventOpenRedirect() {
		Assert.assertEquals(
			"invalid_request",
			getCodeResponse(
				"test@liferay.com", "test", null,
				getCode(
					webTarget -> webTarget.queryParam(
						"client_id", "oauthTestApplicationCode"
					).queryParam(
						"response_type", "code"
					).queryParam(
						"redirect_uri", "http://invalid:8080"
					)),
				this::parseError));
	}

	@Test
	public void testRedirectUriMustMatch() {
		String authorizationCode = getCodeResponse(
			"test@liferay.com", "test", null,
			getCode(
				webTarget -> webTarget.queryParam(
					"client_id", "oauthTestApplicationCode"
				).queryParam(
					"response_type", "code"
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				)),
			this::parseAuthorizationCodeString);

		Assert.assertNotNull(authorizationCode);

		Assert.assertEquals(
			"invalid_grant",
			getToken(
				"oauthTestApplicationCode", null,
				getExchangeAuthorizationCode(
					authorizationCode, "http://invalid:8080"),
				this::parseError));
	}

	public static class SecurityTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationCode",
				Collections.singletonList(GrantType.AUTHORIZATION_CODE),
				Collections.singletonList("everything"));

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationCodePKCE", null,
				Collections.singletonList(GrantType.AUTHORIZATION_CODE_PKCE),
				Collections.singletonList("everything"),
				Collections.singletonList("http://redirecturi:8080"));
		}

	}

	protected String parseStateString(Response response) {
		URI location = response.getLocation();

		if (location == null) {
			throw new IllegalArgumentException(
				"Authorization service response missing Location header from " +
					"which state is extracted");
		}

		Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
			location.getQuery());

		if (!parameterMap.containsKey("state")) {
			return null;
		}

		return parameterMap.get("state")[0];
	}

	protected String parseXFrameOptionsHeader(Response response) {
		return response.getHeaderString("x-frame-options");
	}

}