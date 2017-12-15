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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.util.test.LayoutTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class PortletInstanceSettingsLocatorTest
	extends BaseSettingsLocatorTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_layout = LayoutTestUtil.addLayout(groupId);

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

		Assert.assertEquals(
			saveScopedConfiguration(
				ExtendedObjectClassDefinition.Scope.GROUP,
				String.valueOf(groupId)),
			getValueFromSettings());

		Assert.assertEquals(
			savePortletPreferences(groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP),
			getValueFromSettings());

		Assert.assertEquals(
			saveScopedConfiguration(
				ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE,
				_portletInstanceKey),
			getValueFromSettings());

		Assert.assertEquals(
			savePortletPreferences(
				PortletKeys.PREFS_PLID_SHARED,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _portletInstanceKey,
				_layout.getPlid()),
			getValueFromSettings());
	}

	@DeleteAfterTestRun
	private Layout _layout;

	private String _portletInstanceKey;

}