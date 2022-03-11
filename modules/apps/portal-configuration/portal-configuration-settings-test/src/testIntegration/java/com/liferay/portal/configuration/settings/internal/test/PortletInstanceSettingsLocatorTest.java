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
			getSettingsValue());

		Assert.assertEquals(
			saveScopedConfiguration(
				ExtendedObjectClassDefinition.Scope.COMPANY, companyId),
			getSettingsValue());

		Assert.assertEquals(
			savePortletPreferences(
				companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY),
			getSettingsValue());

		Assert.assertEquals(
			saveScopedConfiguration(
				ExtendedObjectClassDefinition.Scope.GROUP, groupId),
			getSettingsValue());

		Assert.assertEquals(
			savePortletPreferences(groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP),
			getSettingsValue());

		Assert.assertEquals(
			saveScopedConfiguration(
				ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE,
				_portletInstanceKey),
			getSettingsValue());

		Assert.assertEquals(
			savePortletPreferences(
				PortletKeys.PREFS_PLID_SHARED,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _portletInstanceKey,
				_layout.getPlid()),
			getSettingsValue());
	}

	@DeleteAfterTestRun
	private Layout _layout;

	private String _portletInstanceKey;

}