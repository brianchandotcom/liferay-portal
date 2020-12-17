/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.configuration.module.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.configuration.BlogsGroupServiceConfiguration;
import com.liferay.blogs.configuration.BlogsGroupServiceOverriddenConfiguration;
import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;

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

		boolean currentValue = _getCurrentValue();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("enableRss", !currentValue);

		ConfigurationTestUtil.saveConfiguration(
			BlogsGroupServiceConfiguration.class.getName(), properties);

		Assert.assertEquals(!currentValue, _getCurrentValue());
	}

	@Test
	public void testClearConfigurationOverrideInstanceOnPortletPreferencesUpdate()
		throws Exception {

		_group = GroupTestUtil.addGroup();

		boolean currentValue = _getCurrentValue();

		Settings settings = SettingsFactoryUtil.getSettings(
			new GroupServiceSettingsLocator(
				_group.getGroupId(), BlogsConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.setValue("enableRss", String.valueOf(!currentValue));

		modifiableSettings.store();

		Assert.assertEquals(!currentValue, _getCurrentValue());
	}

	private boolean _getCurrentValue() throws Exception {
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