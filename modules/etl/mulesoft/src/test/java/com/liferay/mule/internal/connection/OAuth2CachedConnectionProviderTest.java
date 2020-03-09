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

package com.liferay.mule.internal.connection;

import com.liferay.mule.internal.operation.BaseLiferayOperationTestCase;

import java.util.regex.Matcher;

import org.junit.Assert;
import org.junit.Test;

import org.mule.runtime.api.exception.MuleException;

/**
 * @author Matija Petanjek
 */
public class OAuth2CachedConnectionProviderTest
	extends BaseLiferayOperationTestCase {

	@Test
	public void testInvalidOAuth2CachedConnectionProvider() throws Exception {
		try {
			getPayload("invalid-oauth2-connection-flow");

			Assert.fail();
		}
		catch (MuleException muleException) {
			String message = muleException.getMessage();

			Assert.assertTrue(
				message.contains(
					"Unable to fetch access token from authorization server."));
		}
	}

	@Test
	public void testValidOAuth2CachedConnectionProvider() throws Exception {
		String payload = getPayload("valid-oauth2-connection-flow");

		Matcher matcher = productPattern.matcher(payload);

		Assert.assertTrue(matcher.matches());
	}

	@Override
	protected String getConfigFile() {
		return "test-oauth2-authentication.xml";
	}

}