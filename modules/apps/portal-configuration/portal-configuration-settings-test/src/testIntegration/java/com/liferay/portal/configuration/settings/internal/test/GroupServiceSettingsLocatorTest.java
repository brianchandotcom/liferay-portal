/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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