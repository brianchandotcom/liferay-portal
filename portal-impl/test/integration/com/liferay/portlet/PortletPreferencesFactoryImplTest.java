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

package com.liferay.portlet;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.service.util.test.PortletPreferencesTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Jorge Ferrer
 */
public class PortletPreferencesFactoryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group);

		_portlet = PortletLocalServiceUtil.getPortletById(
			_layout.getCompanyId(), _PORTLET_ID);
	}

	@Test
	public void testGetLayoutPortletSetup() throws Exception {
		String name = RandomTestUtil.randomString(20);
		String[] values = new String[] {RandomTestUtil.randomString(20)};

		String portletPreferencesXML =
			PortletPreferencesTestUtil.getPortletPreferencesXML(name, values);

		PortletPreferencesTestUtil.addLayoutPortletPreferences(
			_layout, _portlet, portletPreferencesXML);

		PortletPreferences layoutPortletSetup =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				_layout, _PORTLET_ID);

		Assert.assertArrayEquals(
			layoutPortletSetup.getValues(name, null), values);
	}

	@Test
	public void testGetLayoutPortletSetupCustomizableColumn() throws Exception {
		long userId = RandomTestUtil.randomLong();

		long ownerId = userId;

		int ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		String customizableColumnPortletId = PortletIdCodec.encode(
			_PORTLET_ID, userId, null);

		String name = RandomTestUtil.randomString(20);
		String[] values = new String[] {RandomTestUtil.randomString(20)};

		String portletPreferencesXML =
			PortletPreferencesTestUtil.getPortletPreferencesXML(name, values);

		PortletPreferencesLocalServiceUtil.addPortletPreferences(
			TestPropsValues.getCompanyId(), ownerId, ownerType,
			_layout.getPlid(), customizableColumnPortletId, _portlet,
			portletPreferencesXML);

		PortletPreferences layoutPortletSetup =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				_layout, customizableColumnPortletId);

		Assert.assertArrayEquals(
			layoutPortletSetup.getValues(name, null), values);
	}

	private static final String _PORTLET_ID = RandomTestUtil.randomString(10);

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;
	private Portlet _portlet;

}