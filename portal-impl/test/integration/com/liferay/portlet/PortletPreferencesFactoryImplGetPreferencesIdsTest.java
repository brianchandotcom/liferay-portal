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

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.bundle.portletpreferencesfactoryimplgetpreferencesids.TestCompanyPortlet;
import com.liferay.portlet.bundle.portletpreferencesfactoryimplgetpreferencesids.TestGroupLayoutPortlet;
import com.liferay.portlet.bundle.portletpreferencesfactoryimplgetpreferencesids.TestGroupPortlet;
import com.liferay.portlet.bundle.portletpreferencesfactoryimplgetpreferencesids.TestUserLayoutPortlet;
import com.liferay.portlet.bundle.portletpreferencesfactoryimplgetpreferencesids.TestUserPortlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class PortletPreferencesFactoryImplGetPreferencesIdsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule(
				"bundle.portletpreferencesfactoryimplgetpreferencesids"));

	@Before
	public void setUp() throws Exception {
		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext());

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group, true);
	}

	@Test
	public void testPreferencesOwnedByCompany() throws Exception {
		long siteGroupId = _layout.getGroupId();
		String portletId = TestCompanyPortlet.PORTLET_NAME;
		boolean modeEditGuest = false;

		LayoutTestUtil.addPortletToLayout(
			TestPropsValues.getUserId(), _layout, portletId, "column-1", null);

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				siteGroupId, TestPropsValues.getUserId(), _layout, portletId,
				modeEditGuest);

		Assert.assertEquals(
			"The owner type should be of type company",
			PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			portletPreferencesIds.getOwnerType());
		Assert.assertEquals(
			"The owner ID should be the ID of the company",
			_layout.getCompanyId(), portletPreferencesIds.getOwnerId());
		Assert.assertEquals(
			"The PLID should not be a real value",
			PortletKeys.PREFS_PLID_SHARED, portletPreferencesIds.getPlid());
	}

	@Test
	public void testPreferencesOwnedByGroup() throws Exception {
		long siteGroupId = _layout.getGroupId();
		String portletId = TestGroupPortlet.PORTLET_NAME;
		boolean modeEditGuest = false;

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				siteGroupId, TestPropsValues.getUserId(), _layout, portletId,
				modeEditGuest);

		Assert.assertEquals(
			"The owner type should be of type group",
			PortletKeys.PREFS_OWNER_TYPE_GROUP,
			portletPreferencesIds.getOwnerType());
		Assert.assertEquals(
			"The owner ID should be the ID of the group", siteGroupId,
			portletPreferencesIds.getOwnerId());
		Assert.assertEquals(
			"The PLID should not be a real value",
			PortletKeys.PREFS_PLID_SHARED, portletPreferencesIds.getPlid());
	}

	@Test
	public void testPreferencesOwnedByGroupLayout() throws Exception {
		long siteGroupId = _layout.getGroupId();
		String portletId = TestGroupLayoutPortlet.PORTLET_NAME;
		boolean modeEditGuest = false;

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				siteGroupId, TestPropsValues.getUserId(), _layout, portletId,
				modeEditGuest);

		Assert.assertEquals(
			"The owner type should be of type layout",
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			portletPreferencesIds.getOwnerType());
		Assert.assertEquals(
			"The owner ID should be the default value",
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			portletPreferencesIds.getOwnerId());
		Assert.assertEquals(
			"The PLID should be the PLID of the layout", _layout.getPlid(),
			portletPreferencesIds.getPlid());
	}

	@Test
	public void testPreferencesOwnedByUser() throws Exception {
		long siteGroupId = _layout.getGroupId();
		String portletId = TestUserPortlet.PORTLET_NAME;
		boolean modeEditGuest = false;

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				siteGroupId, TestPropsValues.getUserId(), _layout, portletId,
				modeEditGuest);

		Assert.assertEquals(
			"The owner type should be of type user",
			PortletKeys.PREFS_OWNER_TYPE_USER,
			portletPreferencesIds.getOwnerType());
		Assert.assertEquals(
			"The owner ID should be the ID of the user who added it",
			TestPropsValues.getUserId(), portletPreferencesIds.getOwnerId());
		Assert.assertEquals(
			"The PLID should not be a real value",
			PortletKeys.PREFS_PLID_SHARED, portletPreferencesIds.getPlid());
	}

	@Test
	public void testPreferencesOwnedByUserLayout() throws Exception {
		long siteGroupId = _layout.getGroupId();
		String portletId = TestUserLayoutPortlet.PORTLET_NAME;
		boolean modeEditGuest = false;

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				siteGroupId, TestPropsValues.getUserId(), _layout, portletId,
				modeEditGuest);

		Assert.assertEquals(
			"The owner type should be of type user",
			PortletKeys.PREFS_OWNER_TYPE_USER,
			portletPreferencesIds.getOwnerType());
		Assert.assertEquals(
			"The owner ID should be the ID of the user who added it",
			TestPropsValues.getUserId(), portletPreferencesIds.getOwnerId());
		Assert.assertEquals(
			"The PLID should be the PLID of the layout", _layout.getPlid(),
			portletPreferencesIds.getPlid());
	}

	@Test
	public void testPreferencesWithModeEditGuestInPublicLayoutWithPermission()
		throws Exception {

		_layout = LayoutTestUtil.addLayout(_group, false);

		long siteGroupId = _layout.getGroupId();

		String portletId = TestGroupPortlet.PORTLET_NAME;
		boolean modeEditGuest = true;

		PortletPreferencesFactoryUtil.getPortletPreferencesIds(
			siteGroupId, TestPropsValues.getUserId(), _layout, portletId,
			modeEditGuest);
	}

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

}