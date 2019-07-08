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
import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.LayoutTestUtil;

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

		_request.setRequestURI(PortalUtil.getPathMain() + "/portal/login");

		_request.setAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET, _group.getPublicLayoutSet());
	}

	@Test
	public void testHiddenLayoutsVirtualHostLayoutCompositeWithNonexistentLayout()
		throws Exception {

		_request.setRequestURI("/nonexistent_page");

		long plid = getThemeDisplayPlid(true, false);

		ServicePreAction.LayoutComposite defaultLayoutComposite =
			ReflectionTestUtil.invoke(
				_servicePreAction, "_getDefaultVirtualHostLayoutComposite",
				new Class<?>[] {HttpServletRequest.class}, _request);

		defaultLayoutComposite = ReflectionTestUtil.invoke(
			_servicePreAction, "_getViewableLayoutComposite",
			new Class<?>[] {
				HttpServletRequest.class, User.class, PermissionChecker.class,
				Layout.class, List.class, boolean.class
			},
			_request, _user, PermissionCheckerFactoryUtil.create(_user),
			defaultLayoutComposite.getLayout(),
			defaultLayoutComposite.getLayouts(), false);

		Layout layout = defaultLayoutComposite.getLayout();

		List<Layout> layouts = defaultLayoutComposite.getLayouts();

		Assert.assertEquals(layout.getPlid(), plid);

		Assert.assertEquals(layouts.toString(), 1, layouts.size());
	}

	@Test
	public void testInitThemeDisplayPlidDefaultUserPersonalSiteLayoutComposite()
		throws Exception {

		long plid = getThemeDisplayPlid(false, true);

		ServicePreAction.LayoutComposite defaultLayoutComposite =
			ReflectionTestUtil.invoke(
				_servicePreAction, "_getDefaultUserPersonalSiteLayoutComposite",
				new Class<?>[] {User.class}, _user);

		Layout layout = defaultLayoutComposite.getLayout();

		Assert.assertEquals(layout.getPlid(), plid);
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
			long plid = getThemeDisplayPlid(false, true);

			ServicePreAction.LayoutComposite defaultLayoutComposite =
				ReflectionTestUtil.invoke(
					_servicePreAction, "_getDefaultUserSitesLayoutComposite",
					new Class<?>[] {User.class}, _user);

			Layout layout = defaultLayoutComposite.getLayout();

			Assert.assertEquals(layout.getPlid(), plid);
		}
		finally {
			PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE =
				publicLayoutsAutoCreate;
			PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE =
				privateLayoutsAutoCreate;
		}
	}

	@Test
	public void testInitThemeDisplayPlidGuestSiteLayoutComposite()
		throws Exception {

		long plid = getThemeDisplayPlid(false, false);

		ServicePreAction.LayoutComposite defaultLayoutComposite =
			ReflectionTestUtil.invoke(
				_servicePreAction, "_getGuestSiteLayoutComposite",
				new Class<?>[] {User.class}, _user);

		Layout layout = defaultLayoutComposite.getLayout();

		Assert.assertEquals(layout.getPlid(), plid);
	}

	@Test
	public void testInitThemeDisplayPlidVirtualHostLayoutComposite()
		throws Exception {

		long plid = getThemeDisplayPlid(true, false);

		ServicePreAction.LayoutComposite defaultLayoutComposite =
			ReflectionTestUtil.invoke(
				_servicePreAction, "_getDefaultVirtualHostLayoutComposite",
				new Class<?>[] {HttpServletRequest.class}, _request);

		Layout layout = defaultLayoutComposite.getLayout();

		Assert.assertEquals(layout.getPlid(), plid);
	}

	protected long getThemeDisplayPlid(
			boolean hasGuestViewPermission, boolean signedIn)
		throws Exception {

		if (!hasGuestViewPermission) {
			Role role = RoleLocalServiceUtil.getRole(
				_group.getCompanyId(), RoleConstants.GUEST);

			ResourcePermissionLocalServiceUtil.removeResourcePermissions(
				_group.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, role.getRoleId(),
				ActionKeys.VIEW);
		}

		if (signedIn) {
			_user = UserTestUtil.addUser();
		}
		else {
			_user = PortalUtil.initUser(_request);
		}

		_request.setAttribute(WebKeys.USER, _user);

		_servicePreAction.run(_request, _response);

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getPlid();
	}

	@DeleteAfterTestRun
	private Group _group;

	private final MockHttpServletRequest _request =
		new MockHttpServletRequest();
	private final MockHttpServletResponse _response =
		new MockHttpServletResponse();
	private final ServicePreAction _servicePreAction = new ServicePreAction();
	private User _user;

}