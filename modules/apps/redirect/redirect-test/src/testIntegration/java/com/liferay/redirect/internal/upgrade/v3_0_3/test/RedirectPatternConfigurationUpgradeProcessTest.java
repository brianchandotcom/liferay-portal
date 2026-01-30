/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.upgrade.v3_0_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.configuration.admin.util.ConfigurationFilterStringUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.Arrays;
import java.util.Dictionary;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class RedirectPatternConfigurationUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.redirect.internal.upgrade.v3_0_3." +
				"RedirectPatternConfigurationUpgradeProcess");
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
		_configurationProvider.saveGroupConfiguration(
			TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
			"com.liferay.redirect.internal.configuration." +
				"RedirectPatternConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"patternStrings", new String[] {"test*test liferay.com"}
			).build());

		_upgradeProcess.upgrade();

		Configuration[] configurations = _getConfigurations();

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);

		Configuration configuration = configurations[0];

		Dictionary<String, Object> properties = configuration.getProperties();

		Assert.assertNotNull(properties);

		String[] patternStrings = (String[])properties.get("patternStrings");

		Assert.assertEquals(
			Arrays.toString(patternStrings), 1, patternStrings.length);

		Assert.assertEquals("test*test liferay.com all", patternStrings[0]);
	}

	private Configuration[] _getConfigurations() throws Exception {
		return _configurationAdmin.listConfigurations(
			ConfigurationFilterStringUtil.getGroupScopedFilterString(
				TestPropsValues.getCompanyId(), null,
				"com.liferay.redirect.internal.configuration." +
					"RedirectPatternConfiguration",
				null));
	}

	private static UpgradeProcess _upgradeProcess;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject
	private ConfigurationProvider _configurationProvider;

	@Inject(
		filter = "(&(component.name=com.liferay.redirect.internal.upgrade.registry.RedirectServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}