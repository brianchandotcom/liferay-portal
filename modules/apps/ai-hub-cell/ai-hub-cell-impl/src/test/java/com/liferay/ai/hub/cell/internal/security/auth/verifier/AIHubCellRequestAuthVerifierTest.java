/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.internal.security.auth.verifier;

import com.liferay.ai.hub.cell.configuration.AIHubCellConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;
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
public class AIHubCellRequestAuthVerifierTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetUserId() throws Exception {
		String token = null;

		try (MockedStatic<ConfigurationProviderUtil>
				configurationProviderUtilMockedStatic =
					_mockConfigurationProviderUtil()) {

			token = _generateToken(
				TimeUnit.MINUTES.toMillis(1), _ISSUER, _USER_ID);

			Assert.assertEquals(
				Long.valueOf(_USER_ID),
				ReflectionTestUtil.invoke(
					_aiHubCellRequestAuthVerifier, "_getUserId",
					new Class<?>[] {String.class, String.class}, _ISSUER,
					token));

			_testGetUserId(
				"Invalid JWT issuer", RandomTestUtil.randomString(),
				_generateToken(
					TimeUnit.MINUTES.toMillis(1), _ISSUER, _USER_ID));
			_testGetUserId(
				"Invalid JWT signature", _ISSUER,
				token.substring(0, token.length() - 5) + "abcde");
			_testGetUserId(
				"The JWT token is expired", _ISSUER,
				_generateToken(0, _ISSUER, _USER_ID));
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

	private String _generateToken(
			long expirationTime, String issuer, long userId)
		throws Exception {

		Date now = new Date();

		SignedJWT signedJWT = new SignedJWT(
			new JWSHeader(JWSAlgorithm.HS256),
			new JWTClaimsSet.Builder(
			).expirationTime(
				new Date(now.getTime() + expirationTime)
			).issuer(
				issuer
			).issueTime(
				now
			).subject(
				String.valueOf(userId)
			).build());

		signedJWT.sign(new MACSigner(_bytes));

		return signedJWT.serialize();
	}

	private MockedStatic<ConfigurationProviderUtil>
		_mockConfigurationProviderUtil() {

		MockedStatic<ConfigurationProviderUtil>
			configurationProviderUtilMockedStatic = Mockito.mockStatic(
				ConfigurationProviderUtil.class);

		AIHubCellConfiguration aiHubCellConfiguration = Mockito.mock(
			AIHubCellConfiguration.class);

		_bytes = new byte[64];

		for (int i = 0; i < _bytes.length; i++) {
			_bytes[i] = SecureRandomUtil.nextByte();
		}

		Mockito.when(
			aiHubCellConfiguration.secret()
		).thenReturn(
			Base64.encode(_bytes)
		);

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
				"com.liferay.ai.hub.cell.internal.security.auth.verifier." +
					"AIHubCellRequestAuthVerifier",
				LoggerTestUtil.DEBUG)) {

			ReflectionTestUtil.invoke(
				_aiHubCellRequestAuthVerifier, "_getUserId",
				new Class<?>[] {String.class, String.class}, issuer, token);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(LoggerTestUtil.DEBUG, logEntry.getPriority());

			Assert.assertEquals(expectedLogMessage, logEntry.getMessage());
		}
	}

	private static final String _ISSUER = RandomTestUtil.randomString();

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private static final AIHubCellRequestAuthVerifier
		_aiHubCellRequestAuthVerifier = new AIHubCellRequestAuthVerifier();

	private byte[] _bytes;

}