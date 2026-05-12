/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.internal.security.auth.verifier;

import com.liferay.ai.hub.cell.configuration.AIHubCellConfiguration;
import com.liferay.ai.hub.cell.rest.internal.security.JWTTokenUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

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

		try (MockedStatic<CompanyLocalServiceUtil>
				companyLocalServiceUtilMockedStatic =
					_mockCompanyLocalServiceUtil();
			MockedStatic<ConfigurationProviderUtil>
				configurationProviderUtilMockedStatic =
					_mockConfigurationProviderUtil()) {

			PrincipalThreadLocal.setName(_USER_ID);

			token = JWTTokenUtil.generateToken();

			Assert.assertFalse(token.isEmpty());

			Assert.assertEquals(
				Long.valueOf(_USER_ID),
				ReflectionTestUtil.invoke(
					_aiHubCellRequestAuthVerifier, "_getUserId",
					new Class<?>[] {String.class, String.class},
					_VIRTUAL_HOSTNAME, token));

			_testGetUserId(
				"Invalid JWT issuer", RandomTestUtil.randomString(), token);
			_testGetUserId(
				"Invalid JWT signature", _VIRTUAL_HOSTNAME,
				token.substring(0, token.length() - 5) + "abcde");

			_testGetUserId(
				"Unable to parse and verify the JWT token", _VIRTUAL_HOSTNAME,
				RandomTestUtil.randomString());
		}

		try (MockedStatic<ConfigurationProviderUtil>
				configurationProviderUtilMockedStatic =
					_mockConfigurationProviderUtil()) {

			_testGetUserId("Invalid JWT signature", _VIRTUAL_HOSTNAME, token);
		}
	}

	private MockedStatic<CompanyLocalServiceUtil>
		_mockCompanyLocalServiceUtil() {

		MockedStatic<CompanyLocalServiceUtil>
			companyLocalServiceUtilMockedStatic = Mockito.mockStatic(
				CompanyLocalServiceUtil.class);

		Company company = Mockito.mock(Company.class);

		Mockito.when(
			company.getVirtualHostname()
		).thenReturn(
			_VIRTUAL_HOSTNAME
		);

		companyLocalServiceUtilMockedStatic.when(
			() -> CompanyLocalServiceUtil.getCompany(Mockito.anyLong())
		).thenReturn(
			company
		);

		return companyLocalServiceUtilMockedStatic;
	}

	private MockedStatic<ConfigurationProviderUtil>
		_mockConfigurationProviderUtil() {

		MockedStatic<ConfigurationProviderUtil>
			configurationProviderUtilMockedStatic = Mockito.mockStatic(
				ConfigurationProviderUtil.class);

		AIHubCellConfiguration aiHubCellConfiguration = Mockito.mock(
			AIHubCellConfiguration.class);

		byte[] bytes = new byte[64];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = SecureRandomUtil.nextByte();
		}

		Mockito.when(
			aiHubCellConfiguration.secret()
		).thenReturn(
			Base64.encode(bytes)
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

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private static final String _VIRTUAL_HOSTNAME =
		RandomTestUtil.randomString();

	private static final AIHubCellRequestAuthVerifier
		_aiHubCellRequestAuthVerifier = new AIHubCellRequestAuthVerifier();

}