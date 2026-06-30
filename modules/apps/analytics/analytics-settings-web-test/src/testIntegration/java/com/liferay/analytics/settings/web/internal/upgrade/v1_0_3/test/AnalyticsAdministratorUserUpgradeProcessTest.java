/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.web.internal.upgrade.v1_0_3.test;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.security.constants.AnalyticsSecurityConstants;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class AnalyticsAdministratorUserUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpgradeCreatesMissingAnalyticsAdministratorUser()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();

		String pid = ConfigurationTestUtil.createFactoryConfiguration(
			AnalyticsConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", companyId
			).put(
				"token", "test-token"
			).build());

		try {
			Assert.assertNull(
				_userLocalService.fetchUserByScreenName(
					companyId,
					AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN));

			_runUpgrade();

			User user = _userLocalService.fetchUserByScreenName(
				companyId,
				AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN);

			Assert.assertNotNull(user);

			_userLocalService.deleteUser(user);
		}
		finally {
			ConfigurationTestUtil.deleteConfiguration(pid);
		}
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.analytics.settings.web.internal.upgrade.v1_0_3." +
				"AnalyticsAdministratorUserUpgradeProcess");

		upgradeProcess.upgrade();
	}

	@Inject(
		filter = "(&(component.name=com.liferay.analytics.settings.web.internal.upgrade.registry.AnalyticsSettingsWebUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private UserLocalService _userLocalService;

}