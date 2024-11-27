/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.internal.servlet.filter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.persistence.service.SamlSpSessionLocalServiceUtil;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelperUtil;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Christian Moura
 */
@RunWith(Arquillian.class)
public class SpSessionTerminationSamlPortalFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testSpSessionTerminationHasCompanyIdSet() throws Exception {
		long companyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		SamlProviderConfigurationHelper samlProviderConfigurationHelper =
			SamlProviderConfigurationHelperUtil.
				getSamlProviderConfigurationHelper();

		boolean enabledInstanceScope =
			samlProviderConfigurationHelper.isEnabled();

		samlProviderConfigurationHelper.updateProperties(
			UnicodePropertiesBuilder.create(
				true
			).put(
				"saml.enabled", "true"
			).build());

		CompanyThreadLocal.setCompanyId(CompanyConstants.SYSTEM);

		samlProviderConfigurationHelper =
			SamlProviderConfigurationHelperUtil.
				getSamlProviderConfigurationHelper();

		boolean enabledSystemScope =
			samlProviderConfigurationHelper.isEnabled();

		samlProviderConfigurationHelper.updateProperties(
			UnicodePropertiesBuilder.create(
				true
			).put(
				"saml.enabled", "false"
			).build());

		URL url = new URL("http://localhost:8080/c/portal/saml/acs");

		SamlSpSession samlSpSession =
			SamlSpSessionLocalServiceUtil.createSamlSpSession(1234);

		samlSpSession.setSamlSpSessionKey("testSamlSpSessionKey");
		samlSpSession.setTerminated(true);

		SamlSpSessionLocalServiceUtil.addSamlSpSession(samlSpSession);

		String cookie =
			SamlWebKeys.SAML_SP_SESSION_KEY + "=testSamlSpSessionKey";

		HttpURLConnection httpClient = (HttpURLConnection)url.openConnection();

		httpClient.setDoOutput(true);
		httpClient.setRequestMethod("POST");
		httpClient.setRequestProperty("Cookie", cookie);

		Assert.assertNotNull(
			SamlSpSessionLocalServiceUtil.fetchSamlSpSession(
				samlSpSession.getSamlSpSessionId()));

		httpClient.getHeaderField("Content-Type");

		Assert.assertNull(
			SamlSpSessionLocalServiceUtil.fetchSamlSpSession(
				samlSpSession.getSamlSpSessionId()));

		CompanyThreadLocal.setCompanyId(CompanyConstants.SYSTEM);

		samlProviderConfigurationHelper.updateProperties(
			UnicodePropertiesBuilder.create(
				true
			).put(
				"saml.enabled", String.valueOf(enabledSystemScope)
			).build());

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		samlProviderConfigurationHelper.updateProperties(
			UnicodePropertiesBuilder.create(
				true
			).put(
				"saml.enabled", String.valueOf(enabledInstanceScope)
			).build());

		CompanyThreadLocal.setCompanyId(companyId);
	}

}