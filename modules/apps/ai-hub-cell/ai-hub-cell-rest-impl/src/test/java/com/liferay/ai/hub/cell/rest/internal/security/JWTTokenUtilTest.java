/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.rest.internal.security;

import com.liferay.ai.hub.cell.configuration.AIHubCellConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

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
		try (MockedStatic<CompanyLocalServiceUtil>
				companyLocalServiceUtilMockedStatic =
					_mockCompanyLocalServiceUtil();
			MockedStatic<ConfigurationProviderUtil>
				configurationProviderUtilMockedStatic =
					_mockConfigurationProviderUtil()) {

			PrincipalThreadLocal.setName(_USER_ID);

			String token = JWTTokenUtil.generateToken();

			Assert.assertFalse(token.isEmpty());

			SignedJWT signedJWT = SignedJWT.parse(token);

			JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();

			Assert.assertEquals(_VIRTUAL_HOSTNAME, jwtClaimsSet.getIssuer());
			Assert.assertEquals(
				String.valueOf(_USER_ID), jwtClaimsSet.getSubject());
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

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private static final String _VIRTUAL_HOSTNAME =
		RandomTestUtil.randomString();

}