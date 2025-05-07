/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.upgrade.v2_13_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

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
@RunWith(Arquillian.class)
public class CTConflictConfigurationUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.change.tracking.internal.upgrade.v2_13_1." +
				"CTConflictConfigurationUpgradeProcess");
	}

	@After
	public void tearDown() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(_pid);

		_configurationProvider.deleteSystemConfiguration(
			CTSettingsConfiguration.class);

		_configurationProvider.deleteCompanyConfiguration(
			CTSettingsConfiguration.class, TestPropsValues.getCompanyId());
	}

	@Test
	public void testUpgradeCompanyConfiguration() throws Exception {
		_pid = ConfigurationTestUtil.createFactoryConfiguration(
			_CLASS_NAME + ".scoped", StringPool.QUESTION,
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"modificationDeletionConflictCheckEnabled", true
			).put(
				"schemaVersionCheckEnabled", true
			).build());

		_upgradeProcess.upgrade();

		CTSettingsConfiguration ctSettingsConfiguration =
			_configurationProvider.getCompanyConfiguration(
				CTSettingsConfiguration.class, TestPropsValues.getCompanyId());

		Assert.assertTrue(
			ctSettingsConfiguration.modificationDeletionConflictCheckEnabled());

		Assert.assertTrue(ctSettingsConfiguration.schemaVersionCheckEnabled());
	}

	@Test
	public void testUpgradeSystemConfiguration() throws Exception {
		ConfigurationTestUtil.saveConfiguration(
			_CLASS_NAME,
			HashMapDictionaryBuilder.<String, Object>put(
				"modificationDeletionConflictCheckEnabled", true
			).put(
				"schemaVersionCheckEnabled", true
			).build());

		_pid = _CLASS_NAME;

		_upgradeProcess.upgrade();

		CTSettingsConfiguration ctSettingsConfiguration =
			_configurationProvider.getSystemConfiguration(
				CTSettingsConfiguration.class);

		Assert.assertTrue(
			ctSettingsConfiguration.modificationDeletionConflictCheckEnabled());

		Assert.assertTrue(ctSettingsConfiguration.schemaVersionCheckEnabled());
	}

	private static final String _CLASS_NAME =
		"com.liferay.change.tracking.configuration.CTConflictConfiguration";

	@Inject
	private ConfigurationProvider _configurationProvider;

	private String _pid;
	private UpgradeProcess _upgradeProcess;

	@Inject(
		filter = "(&(component.name=com.liferay.change.tracking.internal.upgrade.registry.ChangeTrackingServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}