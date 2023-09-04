/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.module.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.configuration.BlogsGroupServiceConfiguration;
import com.liferay.blogs.configuration.BlogsGroupServiceOverriddenConfiguration;
import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class ConfigurationOverrideInstanceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testClearConfigurationOverrideInstanceOnConfigurationUpdate()
		throws Exception {

		_group = GroupTestUtil.addGroup();

		boolean rssEnabled = _isRssEnabled();

		ConfigurationTestUtil.saveConfiguration(
			BlogsGroupServiceConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"enableRss", !rssEnabled
			).build());

		Assert.assertEquals(!rssEnabled, _isRssEnabled());
	}

	@Test
	public void testClearConfigurationOverrideInstanceOnPortletPreferencesUpdate()
		throws Exception {

		_group = GroupTestUtil.addGroup();

		boolean currentValue = _isRssEnabled();

		Settings settings = FallbackKeysSettingsUtil.getSettings(
			new GroupServiceSettingsLocator(
				_group.getGroupId(), BlogsConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.setValue("enableRss", String.valueOf(!currentValue));

		modifiableSettings.store();

		Assert.assertEquals(!currentValue, _isRssEnabled());
	}

	private boolean _isRssEnabled() throws Exception {
		BlogsGroupServiceOverriddenConfiguration
			blogsGroupServiceOverriddenConfiguration =
				_configurationProvider.getConfiguration(
					BlogsGroupServiceOverriddenConfiguration.class,
					new GroupServiceSettingsLocator(
						_group.getGroupId(), BlogsConstants.SERVICE_NAME));

		return blogsGroupServiceOverriddenConfiguration.enableRss();
	}

	@Inject
	private static ConfigurationProvider _configurationProvider;

	private Group _group;

}