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

package com.liferay.portal.events.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class ServicePreActionTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		LayoutTestUtil.addLayout(_group);

		LayoutTestUtil.addLayout(
			_group.getGroupId(), "Page not visible", false, null, false, true);

		_request.setRequestURI(_portal.getPathMain() + "/portal/login");

		_request.setAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET, _group.getPublicLayoutSet());
	}

	@Test
	public void testHiddenLayoutsVirtualHostLayoutCompositeWithNonexistentLayout()
		throws Exception {

		_request.setRequestURI("/nonexistent_page");

		long plid = _getThemeDisplayPlid(true, false);

		Object defaultLayoutComposite = ReflectionTestUtil.invoke(
			_servicePreAction, "_getDefaultVirtualHostLayoutComposite",
			new Class<?>[] {HttpServletRequest.class}, _request);

		Object viewableLayoutComposite = ReflectionTestUtil.invoke(
			_servicePreAction, "_getViewableLayoutComposite",
			new Class<?>[] {
				HttpServletRequest.class, User.class, PermissionChecker.class,
				Layout.class, List.class, boolean.class
			},
			_request, _user, _permissionCheckerFactory.create(_user),
			_getLayout(defaultLayoutComposite),
			_getLayouts(defaultLayoutComposite), false);

		Layout layout = _getLayout(viewableLayoutComposite);

		List<Layout> layouts = _getLayouts(viewableLayoutComposite);

		Assert.assertEquals(layout.getPlid(), plid);

		Assert.assertEquals(layouts.toString(), 1, layouts.size());
	}

	@Test
	public void testInitThemeDisplayPlidDefaultUserPersonalSiteLayoutComposite()
		throws Exception {

		try {
			long plid = _getThemeDisplayPlid(false, true);

			Layout layout = _getLayout(
				ReflectionTestUtil.invoke(
					_servicePreAction,
					"_getDefaultUserPersonalSiteLayoutComposite",
					new Class<?>[] {User.class}, _user));

			Assert.assertEquals(layout.getPlid(), plid);
		}
		finally {
			if (_user != null) {
				_userLocalService.deleteUser(_user);
			}
		}
	}

	@Test
	public void testInitThemeDisplayPlidDefaultUserSitesLayoutComposite()
		throws Exception {

		boolean publicLayoutsAutoCreate =
			PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE;
		boolean privateLayoutsAutoCreate =
			PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE;

		PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE = false;
		PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE = false;

		try {
			long plid = _getThemeDisplayPlid(false, true);

			Layout layout = _getLayout(
				ReflectionTestUtil.invoke(
					_servicePreAction, "_getDefaultUserSitesLayoutComposite",
					new Class<?>[] {User.class}, _user));

			Assert.assertEquals(layout.getPlid(), plid);
		}
		finally {
			PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE =
				publicLayoutsAutoCreate;
			PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE =
				privateLayoutsAutoCreate;

			if (_user != null) {
				_userLocalService.deleteUser(_user);
			}
		}
	}

	@Test
	public void testInitThemeDisplayPlidGuestSiteLayoutComposite()
		throws Exception {

		long plid = _getThemeDisplayPlid(false, false);

		Layout layout = _getLayout(
			ReflectionTestUtil.invoke(
				_servicePreAction, "_getGuestSiteLayoutComposite",
				new Class<?>[] {User.class}, _user));

		Assert.assertEquals(layout.getPlid(), plid);
	}

	@Test
	public void testInitThemeDisplayPlidVirtualHostLayoutComposite()
		throws Exception {

		long plid = _getThemeDisplayPlid(true, false);

		Layout layout = _getLayout(
			ReflectionTestUtil.invoke(
				_servicePreAction, "_getDefaultVirtualHostLayoutComposite",
				new Class<?>[] {HttpServletRequest.class}, _request));

		Assert.assertEquals(layout.getPlid(), plid);
	}

	private Layout _getLayout(Object layoutComposite) {
		return ReflectionTestUtil.invoke(
			layoutComposite, "getLayout", null, null);
	}

	private List<Layout> _getLayouts(Object layoutComposite) {
		return ReflectionTestUtil.invoke(
			layoutComposite, "getLayouts", null, null);
	}

	private long _getThemeDisplayPlid(
			boolean hasGuestViewPermission, boolean signedIn)
		throws Exception {

		if (!hasGuestViewPermission) {
			Role role = _roleLocalService.getRole(
				_group.getCompanyId(), RoleConstants.GUEST);

			_resourcePermissionLocalService.removeResourcePermissions(
				_group.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, role.getRoleId(),
				ActionKeys.VIEW);
		}

		if (signedIn) {
			_user = UserTestUtil.addUser();
		}
		else {
			_user = _portal.initUser(_request);
		}

		_request.setAttribute(WebKeys.USER, _user);

		_servicePreAction.run(_request, _response);

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getPlid();
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private Portal _portal;

	private final MockHttpServletRequest _request =
		new MockHttpServletRequest();

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private final MockHttpServletResponse _response =
		new MockHttpServletResponse();

	@Inject
	private RoleLocalService _roleLocalService;

	private final ServicePreAction _servicePreAction = new ServicePreAction();
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}