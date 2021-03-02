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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleActivator;

/**
 * @author Marta Medio
 */
@RunWith(Arquillian.class)
public class RememberDeviceApplicationClientTest extends BaseClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testResponseCookieApplication() {
		Response response = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", "oauthTestApplicationCode"
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				).queryParam(
					"response_type", "code"
				),
				_getExtraParameterForRememeberDevice()));

		Map<String, NewCookie> cookies = response.getCookies();

		Assert.assertFalse(cookies.containsKey(_COOKIE_REMEMBER_DEVICE));

		response = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", "oauthTestApplicationCodePKCE"
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				).queryParam(
					"response_type", "code"
				),
				_getExtraParameterForRememeberDevice()));

		cookies = response.getCookies();

		Assert.assertFalse(cookies.containsKey(_COOKIE_REMEMBER_DEVICE));
	}

	@Test
	public void testResponseCookieRememberApplication() {
		Response response = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", "oauthTestRememberApplicationCode"
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				).queryParam(
					"response_type", "code"
				),
				_getExtraParameterForRememeberDevice()));

		Map<String, NewCookie> cookies = response.getCookies();

		Assert.assertTrue(cookies.containsKey(_COOKIE_REMEMBER_DEVICE));

		response = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", "oauthTestRememberApplicationCode"
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				).queryParam(
					"response_type", "code"
				)));

		cookies = response.getCookies();

		Assert.assertFalse(cookies.containsKey(_COOKIE_REMEMBER_DEVICE));
	}

	@Test
	public void testResponseCookieRememberApplicationPKCE() {
		Response response = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", "oauthTestRememberApplicationCodePKCE"
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				).queryParam(
					"response_type", "code"
				),
				_getExtraParameterForRememeberDevice()));

		Map<String, NewCookie> cookies = response.getCookies();

		Assert.assertTrue(cookies.containsKey(_COOKIE_REMEMBER_DEVICE));

		response = getCodeResponse(
			"test@liferay.com", "test", null,
			getCodeFunction(
				webTarget -> webTarget.queryParam(
					"client_id", "oauthTestRememberApplicationCodePKCE"
				).queryParam(
					"redirect_uri", "http://redirecturi:8080"
				).queryParam(
					"response_type", "code"
				)));

		cookies = response.getCookies();

		Assert.assertFalse(cookies.containsKey(_COOKIE_REMEMBER_DEVICE));
	}

	public static class RememberApplicationClientTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationCode",
				Collections.singletonList(GrantType.AUTHORIZATION_CODE), false,
				Collections.singletonList("everything"), false);
			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationCodePKCE", null,
				Collections.singletonList(GrantType.AUTHORIZATION_CODE_PKCE),
				Collections.singletonList("everything"),
				Collections.singletonList("http://redirecturi:8080"), false,
				false);
			createOAuth2Application(
				defaultCompanyId, user, "oauthTestRememberApplicationCode",
				Collections.singletonList(GrantType.AUTHORIZATION_CODE), true,
				Collections.singletonList("everything"), false);
			createOAuth2Application(
				defaultCompanyId, user, "oauthTestRememberApplicationCodePKCE",
				null,
				Collections.singletonList(GrantType.AUTHORIZATION_CODE_PKCE),
				Collections.singletonList("everything"),
				Collections.singletonList("http://redirecturi:8080"), true,
				false);
		}

	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new RememberApplicationClientTestPreparatorBundleActivator();
	}

	private MultivaluedMap<String, String>
		_getExtraParameterForRememeberDevice() {

		MultivaluedMap<String, String> extraParameters =
			new MultivaluedHashMap<>();

		extraParameters.add(_REMEMBER_DEVICE_PARAMETER, StringPool.TRUE);

		return extraParameters;
	}

	private static final String _COOKIE_REMEMBER_DEVICE =
		"OAUTH2_REMEMBER_DEVICE";

	private static final String _REMEMBER_DEVICE_PARAMETER =
		"_com_liferay_oauth2_provider_web_internal_portlet_" +
			"OAuth2AuthorizePortlet_rememberDevice";

}