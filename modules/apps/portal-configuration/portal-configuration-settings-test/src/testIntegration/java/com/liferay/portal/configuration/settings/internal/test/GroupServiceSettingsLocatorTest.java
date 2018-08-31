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

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.settings.internal.constants.SettingsLocatorTestConstants;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class GroupServiceSettingsLocatorTest
	extends BaseSettingsLocatorTestCase {

	@Before
	public void setUp() throws Exception {
		settingsLocator = new GroupServiceSettingsLocator(
			groupId, portletId,
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);
	}

	@Test
	public void testReturnsGroupScopedValues() throws Exception {
		Assert.assertEquals(
			SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
			getSettingsValue());

		String companyConfigurationValue = saveScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.COMPANY, companyId);

		Assert.assertEquals(companyConfigurationValue, getSettingsValue());

		String companyPortletPreferencesValue = savePortletPreferences(
			companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		Assert.assertEquals(companyPortletPreferencesValue, getSettingsValue());

		String groupConfigurationValue = saveScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.GROUP, groupId);

		Assert.assertEquals(groupConfigurationValue, getSettingsValue());

		String groupPortletPreferencesValue = savePortletPreferences(
			groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP);

		Assert.assertEquals(groupPortletPreferencesValue, getSettingsValue());
	}

	@DeleteAfterTestRun
	private final List<PortletPreferences> _portletPreferencesList =
		new ArrayList<>();

}