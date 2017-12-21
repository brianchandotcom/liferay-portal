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
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.settings.internal.BaseSettingsLocatorTestCase;
import com.liferay.portal.configuration.settings.internal.SettingsLocatorTestConstants;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.PortletKeys;

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
	@Override
	public void setUp() throws Exception {
		super.setUp();

		settingsLocator = new CompanyServiceSettingsLocator(
			companyId, portletId,
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);
	}

	@Test
	public void testReturnsCompanyScopedValues() throws Exception {
		Assert.assertEquals(
			SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
			getValueFromSettings());

		Assert.assertEquals(
			saveScopedConfiguration(
				ExtendedObjectClassDefinition.Scope.COMPANY,
				String.valueOf(companyId)),
			getValueFromSettings());

		Assert.assertEquals(
			savePortletPreferences(
				companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY),
			getValueFromSettings());
	}

}