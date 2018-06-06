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

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.tools.ToolDependencies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class PortletPreferencesFactoryImplGetPreferencesIdsUnitTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		ToolDependencies.wireCaches();

		PermissionThreadLocal.setPermissionChecker(
			(PermissionChecker)ProxyUtil.newProxyInstance(
				PermissionChecker.class.getClassLoader(),
				new Class<?>[] {PermissionChecker.class},
				new PermissionCheckerInvocationHandler()));
	}

	@Before
	public void setUp() {
		PortletPreferencesFactoryUtil portletPreferencesFactoryUtil =
			new PortletPreferencesFactoryUtil();

		portletPreferencesFactoryUtil.setPortletPreferencesFactory(
			new PortletPreferencesFactoryImpl());

		_layout.setPlid(RandomTestUtil.randomLong());
		_layout.setCompanyId(RandomTestUtil.randomLong());
		_layout.setPrivateLayout(true);

		ReflectionTestUtil.setFieldValue(
			PortletLocalServiceUtil.class, "_service",
			ProxyUtil.newProxyInstance(
				PortletLocalService.class.getClassLoader(),
				new Class<?>[] {PortletLocalService.class},
				new PortletLocalServiceInvocationHandler(
					_layout.getCompanyId())));
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testPreferencesWithModeEditGuestInPrivateLayout()
		throws Exception {

		LayoutPermissionUtil layoutPermissionUtil = new LayoutPermissionUtil();

		layoutPermissionUtil.setLayoutPermission(
			(LayoutPermission)ProxyUtil.newProxyInstance(
				LayoutPermission.class.getClassLoader(),
				new Class<?>[] {LayoutPermission.class},
				new LayoutPermissionInvocationHandler(true)));

		long siteGroupId = _layout.getGroupId();
		boolean modeEditGuest = true;

		PortletPreferencesFactoryUtil.getPortletPreferencesIds(
			siteGroupId, _USER_ID, _layout, _PORTLET_ID, modeEditGuest);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testPreferencesWithModeEditGuestInPublicLayoutWithoutPermission()
		throws Exception {

		_layout.setPrivateLayout(false);

		LayoutPermissionUtil layoutPermissionUtil = new LayoutPermissionUtil();

		layoutPermissionUtil.setLayoutPermission(
			(LayoutPermission)ProxyUtil.newProxyInstance(
				LayoutPermission.class.getClassLoader(),
				new Class<?>[] {LayoutPermission.class},
				new LayoutPermissionInvocationHandler(false)));

		long siteGroupId = _layout.getGroupId();
		boolean modeEditGuest = true;

		PortletPreferencesFactoryUtil.getPortletPreferencesIds(
			siteGroupId, _USER_ID, _layout, _PORTLET_ID, modeEditGuest);
	}

	private static final String _PORTLET_ID = RandomTestUtil.randomString(10);

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private final Layout _layout = new LayoutImpl();

	private static class LayoutPermissionInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			if (_containsMethod.equals(method)) {
				return _contains;
			}

			throw new UnsupportedOperationException();
		}

		private LayoutPermissionInvocationHandler(boolean contains) {
			_contains = contains;

			try {
				_containsMethod = LayoutPermission.class.getMethod(
					"contains", PermissionChecker.class, Layout.class,
					String.class);
			}
			catch (NoSuchMethodException nsme) {
				throw new RuntimeException(nsme);
			}
		}

		private final boolean _contains;
		private final Method _containsMethod;

	}

	private static class PermissionCheckerInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (_getUserIdMethod.equals(method)) {
				return _USER_ID;
			}

			throw new UnsupportedOperationException();
		}

		private PermissionCheckerInvocationHandler() {
			try {
				_getUserIdMethod = PermissionChecker.class.getMethod(
					"getUserId");
			}
			catch (NoSuchMethodException nsme) {
				throw new RuntimeException(nsme);
			}
		}

		private final Method _getUserIdMethod;

	}

	private static class PortletLocalServiceInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			if (_getPortletByIdMethod.equals(method) &&
				args[0].equals(_companyId) && args[1].equals(_PORTLET_ID)) {

				Portlet portlet = new PortletImpl();

				portlet.setPreferencesCompanyWide(false);
				portlet.setPreferencesOwnedByGroup(true);
				portlet.setPreferencesUniquePerLayout(false);

				return portlet;
			}

			throw new UnsupportedOperationException();
		}

		private PortletLocalServiceInvocationHandler(long companyId) {
			_companyId = companyId;

			try {
				_getPortletByIdMethod = PortletLocalService.class.getMethod(
					"getPortletById", long.class, String.class);
			}
			catch (NoSuchMethodException nsme) {
				throw new RuntimeException(nsme);
			}
		}

		private final long _companyId;
		private final Method _getPortletByIdMethod;

	}

}