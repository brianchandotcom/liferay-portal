/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.feature.flag.test;

import com.liferay.ai.hub.cell.configuration.AIHubCellConfiguration;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-44511"), @FeatureFlag("LPD-62272")}
)
@RunWith(Arquillian.class)
public class SEOStudioFeatureFlagListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_deleteOAuth2Application();

		_configurationProvider.deleteCompanyConfiguration(
			AIHubCellConfiguration.class, TestPropsValues.getCompanyId());
	}

	@After
	public void tearDown() throws Exception {
		_deleteOAuth2Application();

		_configurationProvider.deleteCompanyConfiguration(
			AIHubCellConfiguration.class, TestPropsValues.getCompanyId());
	}

	@Test
	public void testOnValue() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		_seoStudioFeatureFlagListener.onValue(companyId, "LPD-44511", true);

		AIHubCellConfiguration aiHubCellConfiguration1 =
			_configurationProvider.getCompanyConfiguration(
				AIHubCellConfiguration.class, companyId);

		Company company = _companyLocalService.getCompany(companyId);

		Assert.assertEquals(
			company.getPortalURL(0), aiHubCellConfiguration1.serviceURL());

		String clientId = aiHubCellConfiguration1.clientId();
		String clientSecret = aiHubCellConfiguration1.clientSecret();

		Assert.assertFalse(Validator.isBlank(clientId));
		Assert.assertFalse(Validator.isBlank(clientSecret));

		OAuth2Application oAuth2Application1 =
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					_EXTERNAL_REFERENCE_CODE, companyId);

		Assert.assertEquals(oAuth2Application1.getClientId(), clientId);
		Assert.assertEquals(oAuth2Application1.getClientSecret(), clientSecret);

		_seoStudioFeatureFlagListener.onValue(companyId, "LPD-44511", true);

		AIHubCellConfiguration aiHubCellConfiguration2 =
			_configurationProvider.getCompanyConfiguration(
				AIHubCellConfiguration.class, companyId);

		Assert.assertEquals(
			aiHubCellConfiguration1.clientId(),
			aiHubCellConfiguration2.clientId());
		Assert.assertEquals(
			aiHubCellConfiguration1.clientSecret(),
			aiHubCellConfiguration2.clientSecret());
		Assert.assertEquals(
			aiHubCellConfiguration1.serviceURL(),
			aiHubCellConfiguration2.serviceURL());

		OAuth2Application oAuth2Application2 =
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					_EXTERNAL_REFERENCE_CODE, companyId);

		Assert.assertEquals(
			oAuth2Application1.getOAuth2ApplicationId(),
			oAuth2Application2.getOAuth2ApplicationId());
	}

	private void _deleteOAuth2Application() throws Exception {
		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					_EXTERNAL_REFERENCE_CODE, TestPropsValues.getCompanyId());

		if (oAuth2Application != null) {
			_oAuth2ApplicationLocalService.deleteOAuth2Application(
				oAuth2Application.getOAuth2ApplicationId());
		}
	}

	private static final String _EXTERNAL_REFERENCE_CODE = "AI-HUB-CELL";

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ConfigurationProvider _configurationProvider;

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Inject(filter = "feature.flag.key=LPD-44511")
	private FeatureFlagListener _seoStudioFeatureFlagListener;

}