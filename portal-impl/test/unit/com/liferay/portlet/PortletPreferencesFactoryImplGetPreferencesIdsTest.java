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

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.tools.ToolDependencies;

import org.junit.Before;
import org.junit.BeforeClass;
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
	LayoutPermissionUtil.class, PermissionThreadLocal.class,
	PortletLocalServiceUtil.class
})
@RunWith(PowerMockRunner.class)
public class PortletPreferencesFactoryImplGetPreferencesIdsTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		ToolDependencies.wireCaches();
	}

	@Before
	public void setUp() {
		PortletPreferencesFactoryUtil portletPreferencesFactoryUtil =
			new PortletPreferencesFactoryUtil();

		portletPreferencesFactoryUtil.setPortletPreferencesFactory(
			new PortletPreferencesFactoryImpl());

		_layout.setCompanyId(RandomTestUtil.randomLong());
		_layout.setPlid(RandomTestUtil.randomLong());
		_layout.setPrivateLayout(true);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testPreferencesWithModeEditGuestInPrivateLayout()
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

		PowerMockito.mockStatic(PermissionThreadLocal.class);

		Mockito.when(
			PermissionThreadLocal.getPermissionChecker()
		).thenReturn(
			PowerMockito.mock(PermissionChecker.class)
		);

		long siteGroupId = _layout.getGroupId();
		boolean modeEditGuest = true;

		PortletPreferencesFactoryUtil.getPortletPreferencesIds(
			siteGroupId, _USER_ID, _layout, _PORTLET_ID, modeEditGuest);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void
			testPreferencesWithModeEditGuestInPublicLayoutWithoutPermission()
		throws Exception {

		_layout.setPrivateLayout(false);

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

		PowerMockito.mockStatic(PermissionThreadLocal.class);

		Mockito.when(
			PermissionThreadLocal.getPermissionChecker()
		).thenReturn(
			PowerMockito.mock(PermissionChecker.class)
		);

		long siteGroupId = _layout.getGroupId();
		boolean modeEditGuest = true;

		PortletPreferencesFactoryUtil.getPortletPreferencesIds(
			siteGroupId, _USER_ID, _layout, _PORTLET_ID, modeEditGuest);
	}

	protected Portlet getGroupPortlet() {
		Portlet portlet = new PortletImpl();

		portlet.setPreferencesCompanyWide(false);
		portlet.setPreferencesOwnedByGroup(true);
		portlet.setPreferencesUniquePerLayout(false);

		return portlet;
	}

	private static final String _PORTLET_ID = RandomTestUtil.randomString(10);

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private final Layout _layout = new LayoutImpl();

}