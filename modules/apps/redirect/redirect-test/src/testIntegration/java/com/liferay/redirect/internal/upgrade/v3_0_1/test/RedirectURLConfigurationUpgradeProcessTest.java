/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.upgrade.v3_0_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.redirect.internal.constants.LegacyRedirectURLPropsKeys;

import java.util.Arrays;
import java.util.Dictionary;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class RedirectURLConfigurationUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.redirect.internal.upgrade.v3_0_1." +
				"RedirectURLConfigurationUpgradeProcess");
	}

	@After
	public void tearDown() throws Exception {
		Configuration[] configurations = _getConfigurations();

		if (configurations == null) {
			return;
		}

		for (Configuration configuration : configurations) {
			configuration.delete();
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		String allowedDomain = RandomTestUtil.randomString();
		String allowedIP = RandomTestUtil.randomString();
		String securityMode = RandomTestUtil.randomString();

		_companyLocalService.updatePreferences(
			0,
			UnicodePropertiesBuilder.put(
				LegacyRedirectURLPropsKeys.REDIRECT_URL_DOMAINS_ALLOWED,
				allowedDomain
			).put(
				LegacyRedirectURLPropsKeys.REDIRECT_URL_IPS_ALLOWED, allowedIP
			).put(
				LegacyRedirectURLPropsKeys.REDIRECT_URL_SECURITY_MODE,
				securityMode
			).build());

		_upgradeProcess.upgrade();

		Configuration[] configurations = _getConfigurations();

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);

		Configuration configuration = configurations[0];

		Dictionary<String, Object> properties = configuration.getProperties();

		Assert.assertNotNull(properties);

		String[] allowedDomains = (String[])properties.get("allowedDomains");

		Assert.assertEquals(
			Arrays.toString(allowedDomains), 1, allowedDomains.length);
		Assert.assertEquals(allowedDomain, allowedDomains[0]);

		String[] allowedIPs = (String[])properties.get("allowedIPs");

		Assert.assertEquals(Arrays.toString(allowedIPs), 1, allowedIPs.length);
		Assert.assertEquals(allowedIP, allowedIPs[0]);

		Assert.assertEquals(securityMode, properties.get("securityMode"));
	}

	private Configuration[] _getConfigurations() throws Exception {
		String filterString = StringBundler.concat(
			"(", Constants.SERVICE_PID, "=",
			"com.liferay.redirect.internal.configuration.",
			"RedirectURLConfiguration)");

		return _configurationAdmin.listConfigurations(filterString);
	}

	private static UpgradeProcess _upgradeProcess;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject(
		filter = "(&(component.name=com.liferay.redirect.internal.upgrade.registry.RedirectServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}