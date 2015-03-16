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

import com.liferay.portal.cache.SingleVMPoolImpl;
import com.liferay.portal.cache.memory.MemoryPortalCacheManager;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.util.PortletKeys;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
@PrepareForTest( {
	LayoutPermissionUtil.class, PortletLocalServiceUtil.class
})
@RunWith(PowerMockRunner.class)
public class PortletPreferencesFactoryImplGetPreferencesIdsTest {

	@Before
	public void setUp() {
		SingleVMPoolUtil singleVMPoolUtil = new SingleVMPoolUtil();

		SingleVMPoolImpl singleVMPoolImpl = new SingleVMPoolImpl();

		singleVMPoolImpl.setPortalCacheManager(
			MemoryPortalCacheManager.createMemoryPortalCacheManager(
				PortletPreferencesFactoryImplUnitTest.class.getName()));

		singleVMPoolUtil.setSingleVMPool(singleVMPoolImpl);

		PortletPreferencesFactoryUtil portletPreferencesFactoryUtil =
			new PortletPreferencesFactoryUtil();

		portletPreferencesFactoryUtil.setPortletPreferencesFactory(
			new PortletPreferencesFactoryImpl());
	}

	@Test
	public void testWhenPreferencesOwnedByCompany() throws Exception {
		Layout layout = new LayoutImpl();

		layout.setCompanyId(RandomTestUtil.randomLong());
		layout.setPlid(RandomTestUtil.randomLong());
		layout.setPrivateLayout(true);

		boolean modeEditGuest = false;

		PowerMockito.mockStatic(PortletLocalServiceUtil.class);

		Mockito.when(
			PortletLocalServiceUtil.getPortletById(
				layout.getCompanyId(), _PORTLET_ID)
		).thenReturn(
			_getPortlet()
		);

		PowerMockito.mockStatic(LayoutPermissionUtil.class);

		Mockito.when(
			LayoutPermissionUtil.contains(
				Mockito.any(PermissionChecker.class), Mockito.eq(layout),
				Mockito.eq(ActionKeys.UPDATE))
		).thenReturn(
			true
		);

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				0, _USER_ID, layout, _PORTLET_ID, modeEditGuest);

		Assert.assertEquals(
			"The owner type was not the one expected",
			PortletKeys.PREFS_OWNER_TYPE_USER,
			portletPreferencesIds.getOwnerType());
	}

	private Portlet _getPortlet() {
		Portlet portlet = new PortletImpl();

		portlet.setPreferencesCompanyWide(false);

		return portlet;
	}

	private static final String _PORTLET_ID = RandomTestUtil.randomString(10);

	private static final long _USER_ID = RandomTestUtil.randomLong();

}