/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.internal.servlet.filter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
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
		SamlProviderConfigurationHelper samlProviderConfigurationHelper =
			SamlProviderConfigurationHelperUtil.
				getSamlProviderConfigurationHelper();

		boolean enabled = samlProviderConfigurationHelper.isEnabled();

		samlProviderConfigurationHelper.updateProperties(
			UnicodePropertiesBuilder.create(
				true
			).put(
				"saml.enabled", "true"
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

		Assert.assertNotEquals(
			0,
			CompanyThreadLocal.getCompanyId(
			).longValue());

		Assert.assertNotNull(
			SamlSpSessionLocalServiceUtil.fetchSamlSpSession(
				samlSpSession.getSamlSpSessionId()));

		String contentType = httpClient.getHeaderField("Content-Type");

		Assert.assertNull(
			SamlSpSessionLocalServiceUtil.fetchSamlSpSession(
				samlSpSession.getSamlSpSessionId()));

		Assert.assertTrue(
			"Response content type is not text/html",
			Validator.isNotNull(contentType) &&
			contentType.startsWith("text/html"));

		Assert.assertNotEquals(
			0,
			CompanyThreadLocal.getCompanyId(
			).longValue());

		samlProviderConfigurationHelper.updateProperties(
			UnicodePropertiesBuilder.create(
				true
			).put(
				"saml.enabled", String.valueOf(enabled)
			).build());
	}

}