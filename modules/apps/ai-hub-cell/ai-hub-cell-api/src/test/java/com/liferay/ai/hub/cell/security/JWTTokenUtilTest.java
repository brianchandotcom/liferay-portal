/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.security;

import com.liferay.ai.hub.cell.configuration.AIHubCellConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Rafael Praxedes
 */
public class JWTTokenUtilTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGenerateToken() throws Exception {
		try (MockedStatic<ConfigurationProviderUtil>
				configurationProviderUtilMockedStatic =
					_mockConfigurationProviderUtil()) {

			String token = JWTTokenUtil.generateToken(
				TimeUnit.MINUTES.toMillis(1), _ISSUER, _USER_ID);

			Assert.assertFalse(token.isEmpty());

			JWTClaimsSet jwtClaimsSet = SignedJWT.parse(
				token
			).getJWTClaimsSet();

			Assert.assertEquals(_ISSUER, jwtClaimsSet.getIssuer());
			Assert.assertEquals(
				String.valueOf(_USER_ID), jwtClaimsSet.getSubject());
		}
	}

	@Test
	public void testGetUserId() throws Exception {
		String token = null;

		try (MockedStatic<ConfigurationProviderUtil>
				configurationProviderUtilMockedStatic =
					_mockConfigurationProviderUtil()) {

			token = JWTTokenUtil.generateToken(
				TimeUnit.MINUTES.toMillis(1), _ISSUER, _USER_ID);

			Assert.assertEquals(
				_USER_ID, JWTTokenUtil.getUserId(_ISSUER, token));

			_testGetUserId(
				"Invalid JWT issuer", RandomTestUtil.randomString(),
				JWTTokenUtil.generateToken(
					TimeUnit.MINUTES.toMillis(1), _ISSUER, _USER_ID));
			_testGetUserId(
				"Invalid JWT signature", _ISSUER,
				token.substring(0, token.length() - 5) + "abcde");

			_testGetUserId(
				"The JWT token is expired", _ISSUER,
				JWTTokenUtil.generateToken(0, _ISSUER, _USER_ID));
			_testGetUserId(
				"Unable to parse and verify the JWT token", _ISSUER,
				RandomTestUtil.randomString());
		}

		try (MockedStatic<ConfigurationProviderUtil>
				configurationProviderUtilMockedStatic =
					_mockConfigurationProviderUtil()) {

			_testGetUserId("Invalid JWT signature", _ISSUER, token);
		}
	}

	private AIHubCellConfiguration _mockAIHubCellConfiguration() {
		int sha256BlockSize = 64;

		byte[] secretBytes = new byte[sha256BlockSize];

		for (int i = 0; i < secretBytes.length; i++) {
			secretBytes[i] = SecureRandomUtil.nextByte();
		}

		AIHubCellConfiguration aiHubCellConfiguration = Mockito.mock(
			AIHubCellConfiguration.class);

		Mockito.when(
			aiHubCellConfiguration.secret()
		).thenReturn(
			Base64.encode(secretBytes)
		);

		return aiHubCellConfiguration;
	}

	private MockedStatic<ConfigurationProviderUtil>
		_mockConfigurationProviderUtil() {

		MockedStatic<ConfigurationProviderUtil>
			configurationProviderUtilMockedStatic = Mockito.mockStatic(
				ConfigurationProviderUtil.class);

		AIHubCellConfiguration aiHubCellConfiguration =
			_mockAIHubCellConfiguration();

		configurationProviderUtilMockedStatic.when(
			() -> ConfigurationProviderUtil.getCompanyConfiguration(
				Mockito.eq(AIHubCellConfiguration.class), Mockito.anyLong())
		).thenReturn(
			aiHubCellConfiguration
		);

		return configurationProviderUtilMockedStatic;
	}

	private void _testGetUserId(
		String expectedLogMessage, String issuer, String token) {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.ai.hub.cell.security.JWTTokenUtil",
				LoggerTestUtil.DEBUG)) {

			JWTTokenUtil.getUserId(issuer, token);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(LoggerTestUtil.DEBUG, logEntry.getPriority());

			Assert.assertEquals(expectedLogMessage, logEntry.getMessage());
		}
	}

	private static final String _ISSUER = RandomTestUtil.randomString();

	private static final long _USER_ID = RandomTestUtil.randomLong();

}