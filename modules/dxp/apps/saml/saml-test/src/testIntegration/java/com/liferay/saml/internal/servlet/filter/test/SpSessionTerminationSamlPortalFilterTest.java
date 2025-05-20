/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.internal.servlet.filter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
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
	public void testDoProcessFilter() throws Exception {
		try (SafeCloseable safeCloseable1 = _setSamlEnabledWithSafeCloseable(
				true, TestPropsValues.getCompanyId());
			SafeCloseable safeCloseable2 = _setSamlEnabledWithSafeCloseable(
				false, CompanyConstants.SYSTEM)) {

			SamlSpSession samlSpSession =
				SamlSpSessionLocalServiceUtil.createSamlSpSession(
					_counterLocalService.increment());

			samlSpSession.setSamlSpSessionKey(RandomTestUtil.randomString());
			samlSpSession.setTerminated(true);

			SamlSpSessionLocalServiceUtil.addSamlSpSession(samlSpSession);

			Company company = _companyLocalService.getCompany(
				TestPropsValues.getCompanyId());

			URL url = new URL(
				"http://" + company.getVirtualHostname() + ":8080");

			HttpURLConnection httpURLConnection =
				(HttpURLConnection)url.openConnection();

			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty(
				"Cookie",
				SamlWebKeys.SAML_SP_SESSION_KEY + "=" +
					samlSpSession.getSamlSpSessionKey());

			httpURLConnection.getInputStream();

			Assert.assertNull(
				SamlSpSessionLocalServiceUtil.fetchSamlSpSession(
					samlSpSession.getSamlSpSessionId()));
		}
	}

	private SafeCloseable _setSamlEnabledWithSafeCloseable(
			boolean enabled, long companyId)
		throws Exception {

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(companyId)) {

			SamlProviderConfigurationHelper samlProviderConfigurationHelper =
				SamlProviderConfigurationHelperUtil.
					getSamlProviderConfigurationHelper();

			boolean originalEnabled =
				samlProviderConfigurationHelper.isEnabled();

			samlProviderConfigurationHelper.updateProperties(
				UnicodePropertiesBuilder.create(
					true
				).put(
					"saml.enabled", String.valueOf(enabled)
				).build());

			return () -> {
				try {
					samlProviderConfigurationHelper.updateProperties(
						UnicodePropertiesBuilder.create(
							true
						).put(
							"saml.enabled", String.valueOf(originalEnabled)
						).build());
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			};
		}
	}

	@Inject
	private static CounterLocalService _counterLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

}