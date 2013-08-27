/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class PortletPreferencesFinderTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), ServiceTestUtil.randomString());

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		preferenceMap.put(
			"displayStyleGroupId", new String[] {StringPool.BLANK});

		LayoutTestUtil.addPortletToLayout(
			_layout, PortletKeys.BOOKMARKS, preferenceMap);

		LayoutTestUtil.addPortletToLayout(_layout, PortletKeys.LOGIN);

		PortletPreferencesLocalServiceUtil.getPreferences(
			TestPropsValues.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
			PortletKeys.LOGIN);
	}

	@Test
	public void testCountByC_G_O_O_P_P_P() throws Exception {
		long companyId = TestPropsValues.getCompanyId();
		long groupId = _group.getGroupId();
		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;
		long plid = _layout.getPlid();
		String portletId = PortletKeys.BOOKMARKS;
		boolean privateLayout = _layout.isPrivateLayout();
		boolean excludePortletPreferences = true;

		long count = PortletPreferencesFinderUtil.countByC_G_O_O_P_P_P(
			companyId, groupId, ownerId, ownerType, plid, portletId,
			privateLayout, excludePortletPreferences);

		Assert.assertEquals(1, count);

		portletId = PortletKeys.LOGIN;

		count = PortletPreferencesFinderUtil.countByC_G_O_O_P_P_P(
			companyId, groupId, ownerId, ownerType, plid, portletId,
			privateLayout, excludePortletPreferences);

		Assert.assertEquals(0, count);

		List<PortletPreferences> portletPreferences =
			PortletPreferencesFinderUtil.findByPortletId(portletId);

		excludePortletPreferences = false;

		count = PortletPreferencesFinderUtil.countByC_G_O_O_P_P_P(
			companyId, groupId, ownerId, ownerType, plid, portletId,
			privateLayout, excludePortletPreferences);

		Assert.assertEquals(1, count);
	}

	private Group _group;
	private Layout _layout;

}