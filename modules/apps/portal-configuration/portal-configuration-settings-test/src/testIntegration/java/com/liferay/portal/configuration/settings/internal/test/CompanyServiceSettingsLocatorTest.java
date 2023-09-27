/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.settings.internal.test;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.settings.internal.constants.SettingsLocatorTestConstants;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.PortletKeys;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class CompanyServiceSettingsLocatorTest
	extends BaseSettingsLocatorTestCase {

	@Before
	public void setUp() throws Exception {
		settingsLocator = new CompanyServiceSettingsLocator(
			companyId, portletId,
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);
	}

	@Test
	public void testReturnsCompanyScopedValues() throws Exception {
		Assert.assertEquals(
			SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
			getSettingsValue());

		String companyConfigurationValue = saveScopedConfiguration(
			ExtendedObjectClassDefinition.Scope.COMPANY, companyId);

		Assert.assertEquals(companyConfigurationValue, getSettingsValue());

		String companyPortletPreferencesValue = savePortletPreferences(
			companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		Assert.assertEquals(companyPortletPreferencesValue, getSettingsValue());
	}

}