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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.net.URI;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleActivator;

/**
 * @author Stian Sigvartsen
 */
@RunWith(Arquillian.class)
public class SecurityTest extends BaseClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	/**
	 * OAUTH2-99
	 */
	@Test
	public void testPreventClickJacking() {
		Assert.assertEquals(
			"SAMEORIGIN",
			parseXFrameOptionsHeader(
				getCodeResponse(
					"test@liferay.com", "test", null,
					getCodeFunction(
						webTarget -> webTarget.queryParam(
							"client_id", "oauthTestApplicationCode"
						).queryParam(
							"response_type", "code"
						)))));
	}

	/**
	 * OAUTH2-96
	 */
	@Ignore
	@Test
	public void testPreventCSRFUsingMandatoryStateParam() {
		Assert.assertEquals(
			"invalid_request",
			parseErrorParameter(
				getCodeResponse(
					"test@liferay.com", "test", null,
					getCodeFunction(
						webTarget -> webTarget.queryParam(
							"client_id", "oauthTestApplicationCode"
						).queryParam(
							"response_type", "code"
						)))));
	}

	/**
	 * OAUTH2-96
	 */
	@Test
	public void testPreventCSRFUsingPKCE() {
		String authorizationCode = parseAuthorizationCodeString(
			getCodeResponse(
				"test@liferay.com", "test", null,
				getCodeFunction(
					webTarget -> webTarget.queryParam(
						"client_id", "oauthTestApplicationCodePKCE"
					).queryParam(
						"code_challenge", "correctCodeChallenge"
					).queryParam(
						"response_type", "code"
					))));

		Assert.assertNotNull(authorizationCode);

		Assert.assertEquals(
			"invalid_grant",
			getToken(
				"oauthTestApplicationCodePKCE", null,
				getExchangeAuthorizationCodePKCEBiFunction(
					authorizationCode, null, "wrongCodeVerifier"),
				this::parseError));
	}

	/**
	 * OAUTH2-96
	 */
	@Test
	public void testPreventCSRFUsingStateParam() {
		String state = "csrf_token";

		String responseState = parseStateString(
			getCodeResponse(
				"test@liferay.com", "test", null,
				getCodeFunction(
					webTarget -> webTarget.queryParam(
						"client_id", "oauthTestApplicationCode"
					).queryParam(
						"response_type", "code"
					).queryParam(
						"state", state
					))));

		Assert.assertEquals(state, responseState);
	}

	/**
	 * OAUTH2-97
	 */
	@Test
	public void testPreventOpenRedirect() {
		Response response = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", "oauthTestApplicationCode"
				).queryParam(
					"redirect_uri", "http://invalid:8080"
				).queryParam(
					"response_type", "code"
				)));

		Assert.assertEquals(400, getStatus(response));
		Assert.assertEquals(
			"{\"error\":\"invalid_request\",\"error_description\":\"Client " +
				"Redirect Uri is invalid\"}",
			getBodyAsString(response));
	}

	@Test
	public void testRedirectUriMustMatch() {
		String authorizationCode = parseAuthorizationCodeString(
			getCodeResponse(
				"test@liferay.com", "test", null,
				getCodeFunction(
					webTarget -> webTarget.queryParam(
						"client_id", "oauthTestApplicationCode"
					).queryParam(
						"redirect_uri", "http://redirecturi:8080"
					).queryParam(
						"response_type", "code"
					))));

		Assert.assertNotNull(authorizationCode);

		Assert.assertEquals(
			"invalid_grant",
			getToken(
				"oauthTestApplicationCode", null,
				getExchangeAuthorizationCodeBiFunction(
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
				Collections.singletonList("http://redirecturi:8080"),
				Collections.singletonList("everything"));
		}

	}

	protected String getBodyAsString(Response response) {
		return response.readEntity(String.class);
	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new SecurityTestPreparatorBundleActivator();
	}

	protected int getStatus(Response response) {
		return response.getStatus();
	}

	protected String parseStateString(Response response) {
		URI uri = response.getLocation();

		if (uri == null) {
			throw new IllegalArgumentException(
				"Authorization service response missing \"Location\" header " +
					"from which state is extracted");
		}

		Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
			uri.getQuery());

		if (!parameterMap.containsKey("state")) {
			return null;
		}

		return parameterMap.get("state")[0];
	}

	protected String parseXFrameOptionsHeader(Response response) {
		return response.getHeaderString("x-frame-options");
	}

}