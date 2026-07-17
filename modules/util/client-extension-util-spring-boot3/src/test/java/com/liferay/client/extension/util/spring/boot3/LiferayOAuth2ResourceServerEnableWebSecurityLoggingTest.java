/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.util.spring.boot3;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Allen Ziegenfus
 */
public class LiferayOAuth2ResourceServerEnableWebSecurityLoggingTest {

	@Test
	public void testGetClientIdLogMessage() {
		MockEnvironment mockEnvironment = new MockEnvironment();

		mockEnvironment.setProperty(
			"test-headless-server.oauth2.headless.server.client.id",
			"headless-server-id");

		LiferayOAuth2ResourceServerEnableWebSecurity
			liferayOAuth2ResourceServerEnableWebSecurity =
				new LiferayOAuth2ResourceServerEnableWebSecurity();

		ReflectionTestUtils.setField(
			liferayOAuth2ResourceServerEnableWebSecurity, "_environment",
			mockEnvironment);

		Assert.assertEquals(
			"External reference code test-user-agent has user agent client " +
				"ID user-agent-id",
			ReflectionTestUtils.invokeMethod(
				liferayOAuth2ResourceServerEnableWebSecurity,
				"_getClientIdLogMessage", "user-agent-id", "test-user-agent"));

		Assert.assertEquals(
			"External reference code test-external has no user agent client ID",
			ReflectionTestUtils.invokeMethod(
				liferayOAuth2ResourceServerEnableWebSecurity,
				"_getClientIdLogMessage", null, "test-external"));

		Assert.assertNull(
			ReflectionTestUtils.invokeMethod(
				liferayOAuth2ResourceServerEnableWebSecurity,
				"_getClientIdLogMessage", null, "test-headless-server"));
	}

}