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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition.Scope;
import com.liferay.portal.configuration.metatype.util.ConfigurationScopedPidUtil;
import com.liferay.portal.configuration.settings.internal.constants.SettingsLocatorTestConstants;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class CompanyServiceSettingsLocatorTest
	extends BaseSettingsLocatorTestCase {

	@Before
	public void setUp() throws Exception {
		_companyId = TestPropsValues.getCompanyId();
		_portletId = RandomTestUtil.randomString();

		_settingsLocator = new CompanyServiceSettingsLocator(
			_companyId, _portletId,
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);
	}

	@Test
	public void testReturnsCompanyScopedValues() throws Exception {
		Assert.assertEquals(
			SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
			getValueFromSettings());

		String scopedPid =
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				Scope.COMPANY, String.valueOf(_companyId));

		String companyConfigurationValue = saveConfiguration(scopedPid);

		Assert.assertEquals(companyConfigurationValue, getValueFromSettings());

		String companyPortletPreferencesValue = RandomTestUtil.randomString();

		_portletPreferencesList.add(
			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				_companyId, _companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY, 0,
				_portletId, null,
				String.format(
					_PORTLET_PREFERENCE_FORMAT,
					SettingsLocatorTestConstants.TEST_KEY,
					companyPortletPreferencesValue)));

		Assert.assertEquals(
			companyPortletPreferencesValue, getValueFromSettings());
	}

	protected String getValueFromSettings() throws Exception {
		if (_settingsLocator == null) {
			return null;
		}

		Settings settings = _settingsLocator.getSettings();

		return settings.getValue(SettingsLocatorTestConstants.TEST_KEY, null);
	}

	private static final String _PORTLET_PREFERENCE_FORMAT =
		"<portlet-preferences><preference><name>%s</name><value>%s</value>" +
			"</preference></portlet-preferences>";

	private long _companyId;
	private String _portletId;

	@DeleteAfterTestRun
	private final List<PortletPreferences> _portletPreferencesList =
		new ArrayList<>();

	private SettingsLocator _settingsLocator;

}