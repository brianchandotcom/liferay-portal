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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.internal.test.AuthorizationGrant;
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

	protected abstract AuthorizationGrant getAuthorizationGrant(
		String clientId);

}