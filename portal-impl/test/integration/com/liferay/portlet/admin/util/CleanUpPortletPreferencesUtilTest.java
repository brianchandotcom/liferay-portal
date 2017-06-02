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

package com.liferay.portlet.admin.util;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.LayoutBranchLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Andrew Betts
 */
public class CleanUpPortletPreferencesUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testCleanUpOrphanePortletPreferences() throws Exception {
		LayoutRevision layoutRevision = getLayoutRevision();

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0,
				layoutRevision.getLayoutRevisionId(),
				RandomTestUtil.randomString(), new PortletImpl(),
				StringPool.BLANK);

		Assert.assertNotNull(portletPreferences);

		CleanUpPortletPreferencesUtil.cleanUpLayoutRevisionPortletPreferences();

		portletPreferences =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences.getPortletPreferencesId());

		Assert.assertNull(portletPreferences);
	}

	@Test
	public void testCleanUpProperPortletPreferences() throws Exception {
		LayoutRevision layoutRevision = getLayoutRevision();

		String portletId = PortletIdCodec.encode(
			com.liferay.portlet.util.test.PortletKeys.TEST);

		UnicodeProperties typeSettingProperties =
			layoutRevision.getTypeSettingsProperties();

		typeSettingProperties.setProperty("column-1", portletId);

		layoutRevision = LayoutRevisionLocalServiceUtil.updateLayoutRevision(
			layoutRevision);

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0,
				layoutRevision.getLayoutRevisionId(), portletId, null,
				StringPool.BLANK);

		Assert.assertNotNull(portletPreferences);

		Layout layout = LayoutLocalServiceUtil.getLayout(
			layoutRevision.getPlid());

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		Assert.assertTrue(layoutTypePortlet.getPortletIds().isEmpty());

		CleanUpPortletPreferencesUtil.cleanUpLayoutRevisionPortletPreferences();

		portletPreferences =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences.getPortletPreferencesId());

		Assert.assertNotNull(portletPreferences);
	}

	protected LayoutRevision getLayoutRevision() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group, false);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		LayoutSetBranch layoutSetBranch =
			LayoutSetBranchLocalServiceUtil.addLayoutSetBranch(
				TestPropsValues.getUserId(), _group.getGroupId(), false,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				true, 0, serviceContext);

		LayoutBranch layoutBranch =
			LayoutBranchLocalServiceUtil.getMasterLayoutBranch(
				layoutSetBranch.getLayoutSetBranchId(), layout.getPlid());

		return LayoutRevisionLocalServiceUtil.getLayoutRevision(
			layoutSetBranch.getLayoutSetBranchId(),
			layoutBranch.getLayoutBranchId(), layout.getPlid());
	}

	@DeleteAfterTestRun
	private Group _group;

}