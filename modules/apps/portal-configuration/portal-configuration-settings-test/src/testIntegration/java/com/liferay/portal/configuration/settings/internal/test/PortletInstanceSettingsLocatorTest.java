/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.settings.internal.test;

import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.settings.internal.constants.SettingsLocatorTestConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PortletKeys;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class PortletInstanceSettingsLocatorTest
	extends BaseSettingsLocatorTestCase {

	@Before
	public void setUp() throws Exception {
		_layout = LayoutTestUtil.addTypePortletLayout(groupId);

		_portletInstanceKey =
			portletId + "_INSTANCE_" + RandomTestUtil.randomString();

		settingsLocator = new PortletInstanceSettingsLocator(
			_layout, _portletInstanceKey,
			SettingsLocatorTestConstants.TEST_CONFIGURATION_PID);
	}

	@Test
	public void testReturnsPortletInstanceScopedValues() throws Exception {
		Assert.assertEquals(
			SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
			getSettingsValue(SettingsLocatorTestConstants.TEST_KEY));

		Assert.assertEquals(
			saveFactoryConfiguration(
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				ExtendedObjectClassDefinition.Scope.COMPANY, companyId),
			getSettingsValue(SettingsLocatorTestConstants.TEST_KEY));

		Assert.assertEquals(
			savePortletPreferences(
				companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY),
			getSettingsValue(SettingsLocatorTestConstants.TEST_KEY));

		Assert.assertEquals(
			saveFactoryConfiguration(
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				ExtendedObjectClassDefinition.Scope.GROUP, groupId),
			getSettingsValue(SettingsLocatorTestConstants.TEST_KEY));

		Assert.assertEquals(
			savePortletPreferences(groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP),
			getSettingsValue(SettingsLocatorTestConstants.TEST_KEY));

		Assert.assertEquals(
			saveFactoryConfiguration(
				SettingsLocatorTestConstants.TEST_CONFIGURATION_PID,
				ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE,
				_portletInstanceKey),
			getSettingsValue(SettingsLocatorTestConstants.TEST_KEY));

		Assert.assertEquals(
			savePortletPreferences(
				PortletKeys.PREFS_PLID_SHARED,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _portletInstanceKey,
				_layout.getPlid()),
			getSettingsValue(SettingsLocatorTestConstants.TEST_KEY));
	}

	@DeleteAfterTestRun
	private Layout _layout;

	private String _portletInstanceKey;

}