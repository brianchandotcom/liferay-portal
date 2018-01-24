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

package com.liferay.portal.configuration.settings.internal.test;

import com.liferay.portal.configuration.settings.internal.constants.SettingsLocatorTestConstants;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;

/**
 * @author Drew Brokke
 */
public abstract class BaseSettingsLocatorTestCase {

	@After
	public void tearDown() throws Exception {
		for (String configurationPid : _configurationPids) {
			ConfigurationTestUtil.deleteConfiguration(configurationPid);
		}

		_configurationPids.clear();
	}

	protected String getSettingsValue() throws Exception {
		if (settingsLocator == null) {
			return null;
		}

		Settings settings = settingsLocator.getSettings();

		return settings.getValue(SettingsLocatorTestConstants.TEST_KEY, null);
	}

	protected String saveConfiguration(String configurationPid)
		throws Exception {

		String value = RandomTestUtil.randomString();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(SettingsLocatorTestConstants.TEST_KEY, value);

		ConfigurationTestUtil.saveConfiguration(configurationPid, properties);

		_configurationPids.add(configurationPid);

		return value;
	}

	protected SettingsLocator settingsLocator;

	private final Set<String> _configurationPids = new HashSet<>();

}