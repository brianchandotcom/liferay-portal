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
import com.liferay.portal.security.auth.PrincipalException;
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

		_layout.setCompanyId(RandomTestUtil.randomLong());
		_layout.setPlid(RandomTestUtil.randomLong());
	}

	@Test
	public void testPreferencesOwnedByCompany() throws Exception {
		PowerMockito.mockStatic(PortletLocalServiceUtil.class);

		Mockito.when(
			PortletLocalServiceUtil.getPortletById(
				_layout.getCompanyId(), _PORTLET_ID)
		).thenReturn(
			getCompanyPortlet()
		);

		long siteGroupId = _layout.getGroupId();
		boolean modeEditGuest = false;

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				siteGroupId, _USER_ID, _layout, _PORTLET_ID, modeEditGuest);

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
		PowerMockito.mockStatic(PortletLocalServiceUtil.class);

		Mockito.when(
			PortletLocalServiceUtil.getPortletById(
				_layout.getCompanyId(), _PORTLET_ID)
		).thenReturn(
			getGroupPortlet()
		);

		long siteGroupId = _layout.getGroupId();
		boolean modeEditGuest = false;

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				siteGroupId, _USER_ID, _layout, _PORTLET_ID, modeEditGuest);

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
		PowerMockito.mockStatic(PortletLocalServiceUtil.class);

		Mockito.when(
			PortletLocalServiceUtil.getPortletById(
				_layout.getCompanyId(), _PORTLET_ID)
		).thenReturn(
			getGroupLayoutPortlet()
		);

		long siteGroupId = _layout.getGroupId();
		boolean modeEditGuest = false;

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				siteGroupId, _USER_ID, _layout, _PORTLET_ID, modeEditGuest);

		Assert.assertEquals(
			"The owner type should be of type layout",
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			portletPreferencesIds.getOwnerType());
		Assert.assertEquals(
			"The owner ID should be the default value",
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			portletPreferencesIds.getOwnerId());
		Assert.assertEquals(
			"The PLID should be the PLID of the current layout",
			_layout.getPlid(), portletPreferencesIds.getPlid());
	}

	@Test
	public void testPreferencesOwnedByUser() throws Exception {
		PowerMockito.mockStatic(PortletLocalServiceUtil.class);

		Mockito.when(
			PortletLocalServiceUtil.getPortletById(
				_layout.getCompanyId(), _PORTLET_ID)
		).thenReturn(
			getUserPortlet()
		);

		long siteGroupId = _layout.getGroupId();
		boolean modeEditGuest = false;

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				siteGroupId, _USER_ID, _layout, _PORTLET_ID, modeEditGuest);

		Assert.assertEquals(
			"The owner type should be of type user",
			PortletKeys.PREFS_OWNER_TYPE_USER,
			portletPreferencesIds.getOwnerType());
		Assert.assertEquals(
			"The owner ID should be the ID of the user who added it", _USER_ID,
			portletPreferencesIds.getOwnerId());
		Assert.assertEquals(
			"The PLID should not be a real value",
			PortletKeys.PREFS_PLID_SHARED, portletPreferencesIds.getPlid());
	}

	@Test
	public void testPreferencesOwnedByUserLayout() throws Exception {
		PowerMockito.mockStatic(PortletLocalServiceUtil.class);

		Mockito.when(
			PortletLocalServiceUtil.getPortletById(
				_layout.getCompanyId(), _PORTLET_ID)
		).thenReturn(
			getUserLayoutPortlet()
		);

		long siteGroupId = _layout.getGroupId();
		boolean modeEditGuest = false;

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				siteGroupId, _USER_ID, _layout, _PORTLET_ID, modeEditGuest);

		Assert.assertEquals(
			"The owner type should be of type user",
			PortletKeys.PREFS_OWNER_TYPE_USER,
			portletPreferencesIds.getOwnerType());
		Assert.assertEquals(
			"The owner ID should be the ID of the user who added it", _USER_ID,
			portletPreferencesIds.getOwnerId());
		Assert.assertEquals(
			"The PLID should be the PLID of the current layout",
			_layout.getPlid(), portletPreferencesIds.getPlid());
	}

	@Test(expected = PrincipalException.class)
	public void testPreferencesWithModeEditGuest() throws Exception {
		PowerMockito.mockStatic(PortletLocalServiceUtil.class);

		Mockito.when(
			PortletLocalServiceUtil.getPortletById(
				_layout.getCompanyId(), _PORTLET_ID)
		).thenReturn(
			getGroupPortlet()
		);

		PowerMockito.mockStatic(LayoutPermissionUtil.class);

		Mockito.when(
			LayoutPermissionUtil.contains(
				Mockito.any(PermissionChecker.class), Mockito.eq(_layout),
				Mockito.eq(ActionKeys.UPDATE))
		).thenReturn(
			true
		);

		long siteGroupId = _layout.getGroupId();
		boolean modeEditGuest = true;

		PortletPreferencesFactoryUtil.getPortletPreferencesIds(
			siteGroupId, _USER_ID, _layout, _PORTLET_ID, modeEditGuest);
	}

	@Test(expected = PrincipalException.class)
	public void
			testPreferencesWithModeEditGuestWithoutPermission()
		throws Exception {

		PowerMockito.mockStatic(PortletLocalServiceUtil.class);

		Mockito.when(
			PortletLocalServiceUtil.getPortletById(
				_layout.getCompanyId(), _PORTLET_ID)
		).thenReturn(
			getGroupPortlet()
		);

		PowerMockito.mockStatic(LayoutPermissionUtil.class);

		Mockito.when(
			LayoutPermissionUtil.contains(
				Mockito.any(PermissionChecker.class), Mockito.eq(_layout),
				Mockito.eq(ActionKeys.UPDATE))
		).thenReturn(
			false
		);

		long siteGroupId = _layout.getGroupId();
		boolean modeEditGuest = true;

		PortletPreferencesFactoryUtil.getPortletPreferencesIds(
			siteGroupId, _USER_ID, _layout, _PORTLET_ID, modeEditGuest);
	}

	@Test
	public void testPreferencesWithModeEditGuestWithPermission()
		throws Exception {

		PowerMockito.mockStatic(PortletLocalServiceUtil.class);

		Mockito.when(
			PortletLocalServiceUtil.getPortletById(
				_layout.getCompanyId(), _PORTLET_ID)
		).thenReturn(
			getGroupPortlet()
		);

		PowerMockito.mockStatic(LayoutPermissionUtil.class);

		Mockito.when(
			LayoutPermissionUtil.contains(
				Mockito.any(PermissionChecker.class), Mockito.eq(_layout),
				Mockito.eq(ActionKeys.UPDATE))
		).thenReturn(
			true
		);

		long siteGroupId = _layout.getGroupId();
		boolean modeEditGuest = true;

		PortletPreferencesFactoryUtil.getPortletPreferencesIds(
			siteGroupId, _USER_ID, _layout, _PORTLET_ID, modeEditGuest);
	}

	protected Portlet getCompanyPortlet() {
		Portlet portlet = new PortletImpl();

		portlet.setPreferencesCompanyWide(true);

		return portlet;
	}

	protected Portlet getGroupLayoutPortlet() {
		Portlet portlet = new PortletImpl();

		portlet.setPreferencesCompanyWide(false);
		portlet.setPreferencesOwnedByGroup(true);
		portlet.setPreferencesUniquePerLayout(true);

		return portlet;
	}

	protected Portlet getGroupPortlet() {
		Portlet portlet = new PortletImpl();

		portlet.setPreferencesCompanyWide(false);
		portlet.setPreferencesOwnedByGroup(true);
		portlet.setPreferencesUniquePerLayout(false);

		return portlet;
	}

	protected Portlet getUserLayoutPortlet() {
		Portlet portlet = new PortletImpl();

		portlet.setPreferencesCompanyWide(false);
		portlet.setPreferencesOwnedByGroup(false);
		portlet.setPreferencesUniquePerLayout(true);

		return portlet;
	}

	protected Portlet getUserPortlet() {
		Portlet portlet = new PortletImpl();

		portlet.setPreferencesCompanyWide(false);
		portlet.setPreferencesOwnedByGroup(false);
		portlet.setPreferencesUniquePerLayout(false);

		return portlet;
	}

	private static final String _PORTLET_ID = RandomTestUtil.randomString(10);

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private final Layout _layout = new LayoutImpl();

}