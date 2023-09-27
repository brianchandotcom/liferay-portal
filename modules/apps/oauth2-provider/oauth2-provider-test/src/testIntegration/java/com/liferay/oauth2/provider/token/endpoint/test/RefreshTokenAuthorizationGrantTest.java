/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.token.endpoint.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.internal.test.AuthorizationGrant;
import com.liferay.oauth2.provider.internal.test.PasswordAuthorizationGrant;
import com.liferay.oauth2.provider.internal.test.RefreshTokenAuthorizationGrant;
import com.liferay.oauth2.provider.internal.test.TestAnnotatedApplication;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.util.Arrays;
import java.util.Collections;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleActivator;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class RefreshTokenAuthorizationGrantTest
	extends BaseAuthorizationGrantTestCase {

	@Ignore
	@Test
	public void test() throws Exception {
		JSONObject jsonObject = getToken(
			"oauthTestApplication", null,
			getResourceOwnerPasswordBiFunction("test@liferay.com", "test"),
			this::parseJSONObject);

		WebTarget webTarget = getWebTarget("/annotated");

		String accessTokenString = jsonObject.getString("access_token");

		Invocation.Builder invocationBuilder = authorize(
			webTarget.request(), accessTokenString);

		Assert.assertEquals(
			"everything.read", invocationBuilder.get(String.class));

		WebTarget tokenWebTarget = getTokenWebTarget();

		Invocation.Builder tokenBuilder = tokenWebTarget.request();

		MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();

		formData.add("client_id", "oauthTestApplication");
		formData.add("client_secret", "oauthTestApplicationSecret");
		formData.add("grant_type", "refresh_token");
		formData.add("refresh_token", jsonObject.getString("refresh_token"));

		String tokenString = parseTokenString(
			tokenBuilder.post(Entity.form(formData)));

		Assert.assertNotEquals(tokenString, accessTokenString);

		invocationBuilder = authorize(webTarget.request(), tokenString);

		Assert.assertEquals(
			"everything.read", invocationBuilder.get(String.class));

		invocationBuilder = authorize(webTarget.request(), accessTokenString);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"portal_web.docroot.errors.code_jsp", LoggerTestUtil.WARN)) {

			Response response = invocationBuilder.get();

			Assert.assertEquals(401, response.getStatus());
		}
	}

	public static class TokenExpeditionTestPreparatorBundleActivator
		extends BaseTokenEndpointTestCase.TestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			registerJaxRsApplication(
				new TestAnnotatedApplication(), "annotated",
				HashMapDictionaryBuilder.<String, Object>put(
					"auth.verifier.guest.allowed", false
				).put(
					"oauth2.scope.checker.type", "annotations"
				).build());

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplication",
				Arrays.asList(
					GrantType.RESOURCE_OWNER_PASSWORD, GrantType.REFRESH_TOKEN),
				Collections.singletonList("everything"));

			super.prepareTest();
		}

	}

	protected AuthorizationGrant getAuthorizationGrant(String clientId) {
		return new RefreshTokenAuthorizationGrant(
			getRefreshToken(
				new PasswordAuthorizationGrant("test@liferay.com", "test"),
				clientAuthentications.get(clientId)));
	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new TokenExpeditionTestPreparatorBundleActivator();
	}

}