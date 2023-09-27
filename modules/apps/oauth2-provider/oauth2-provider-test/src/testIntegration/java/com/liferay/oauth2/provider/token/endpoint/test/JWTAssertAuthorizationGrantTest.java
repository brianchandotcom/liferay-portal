/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.token.endpoint.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.internal.test.AuthorizationGrant;
import com.liferay.oauth2.provider.internal.test.JWTAssertionAuthorizationGrant;
import com.liferay.oauth2.provider.internal.test.util.JWTAssertionUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleActivator;

/**
 * @author Arthur Chan
 */
@RunWith(Arquillian.class)
public class JWTAssertAuthorizationGrantTest
	extends BaseAuthorizationGrantTestCase {

	@Test
	public void testGrantWithCorrectAudience() throws Exception {
		User user = UserTestUtil.getAdminUser(PortalUtil.getDefaultCompanyId());

		JWTAssertionAuthorizationGrant jwtAssertionAuthorizationGrant =
			new JWTAssertionAuthorizationGrant(
				TEST_CLIENT_ID_1, null, user.getUuid(), getTokenWebTarget());

		Assert.assertTrue(
			Validator.isNotNull(
				getAccessToken(
					jwtAssertionAuthorizationGrant,
					clientAuthentications.get(TEST_CLIENT_ID_1))));
	}

	@Test
	public void testGrantWithWrongAudience() throws Exception {
		User user = UserTestUtil.getAdminUser(PortalUtil.getDefaultCompanyId());

		JWTAssertionAuthorizationGrant jwtAssertionAuthorizationGrant =
			new JWTAssertionAuthorizationGrant(
				TEST_CLIENT_ID_1, null, user.getUuid(),
				getJsonWebTarget("wrongPath"));

		Assert.assertTrue(
			Validator.isNull(
				getAccessToken(
					jwtAssertionAuthorizationGrant,
					clientAuthentications.get(TEST_CLIENT_ID_1))));
	}

	@Override
	protected AuthorizationGrant getAuthorizationGrant(String clientId) {
		User user = null;

		try {
			user = UserTestUtil.getAdminUser(PortalUtil.getDefaultCompanyId());

			return new JWTAssertionAuthorizationGrant(
				TEST_CLIENT_ID_1, null, user.getUuid(), getTokenWebTarget());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new JWTBearerGrantTestPreparatorBundleActivator();
	}

	private static class JWTBearerGrantTestPreparatorBundleActivator
		extends BaseTokenEndpointTestCase.TestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			createFactoryConfiguration(
				"com.liferay.oauth2.provider.rest.internal.configuration." +
					"OAuth2InAssertionConfiguration",
				HashMapDictionaryBuilder.<String, Object>put(
					"oauth2.in.assertion.issuer", TEST_CLIENT_ID_1
				).put(
					"oauth2.in.assertion.signature.json.web.key.set",
					JWTAssertionUtil.JWKS
				).put(
					"oauth2.in.assertion.user.auth.type", "UUID"
				).build());

			super.prepareTest();
		}

	}

}