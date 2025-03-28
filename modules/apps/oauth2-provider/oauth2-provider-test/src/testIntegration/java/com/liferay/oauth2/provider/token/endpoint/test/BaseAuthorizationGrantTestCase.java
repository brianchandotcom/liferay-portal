/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.token.endpoint.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.internal.test.AuthorizationGrant;
import com.liferay.oauth2.provider.internal.test.JWTAssertionClientSecretAuthorizationGrant;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Arthur Chan
 */
@RunWith(Arquillian.class)
public abstract class BaseAuthorizationGrantTestCase
	extends BaseTokenEndpointTestCase {

	@Test
	public void testClientAuthentication1() {
		Assert.assertTrue(
			Validator.isNotNull(
				getAccessToken(
					getAuthorizationGrant(TEST_CLIENT_ID_1),
					clientAuthentications.get(TEST_CLIENT_ID_1))));
	}

	@Test
	public void testClientAuthentication2() {
		Assert.assertTrue(
			Validator.isNotNull(
				getAccessToken(
					getAuthorizationGrant(TEST_CLIENT_ID_2),
					clientAuthentications.get(TEST_CLIENT_ID_2))));
	}

	@Test
	public void testClientAuthentication3() {
		Assert.assertTrue(
			Validator.isNotNull(
				getAccessToken(
					getAuthorizationGrant(TEST_CLIENT_ID_3),
					clientAuthentications.get(TEST_CLIENT_ID_3))));
	}

	@Test
	public void testClientAuthentication4() throws PortalException {
		User user = UserTestUtil.getAdminUser(PortalUtil.getDefaultCompanyId());

		JWTAssertionClientSecretAuthorizationGrant
			jwtAssertionClientSecretAuthorizationGrant =
				new JWTAssertionClientSecretAuthorizationGrant(
					TEST_CLIENT_ID_4, null, user.getUuid(),
					getTokenWebTarget());

		Assert.assertTrue(
			Validator.isNotNull(
				getAccessToken(
					jwtAssertionClientSecretAuthorizationGrant,
					clientAuthentications.get(TEST_CLIENT_ID_4))));
	}

	protected abstract AuthorizationGrant getAuthorizationGrant(
		String clientId);

}