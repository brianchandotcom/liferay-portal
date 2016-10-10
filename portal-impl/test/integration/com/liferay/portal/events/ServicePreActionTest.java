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

package com.liferay.portal.events;

import com.liferay.portal.events.test.TestServicePreAction;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.LayoutTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Preston Crary
 */
public class ServicePreActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		LayoutTestUtil.addLayout(_group);

		_request.setRequestURI(PortalUtil.getPathMain() + "/portal/login");

		_request.setAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET, _group.getPublicLayoutSet());
	}

	@Test
	public void testInitThemeDisplayPlidDefaultUserPersonalSiteLayoutComposite()
		throws Exception {

		long plid = getThemeDisplayPlid(false, true);

		ServicePreAction.LayoutComposite defaultLayoutComposite =
			TestServicePreAction.INSTANCE.
				getDefaultUserPersonalSiteLayoutComposite(_user);

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
				TestServicePreAction.INSTANCE.
					getDefaultUserSitesLayoutComposite(_user);

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
			TestServicePreAction.INSTANCE.getGuestSiteLayoutComposite(_user);

		Layout layout = defaultLayoutComposite.getLayout();

		Assert.assertEquals(layout.getPlid(), plid);
	}

	@Test
	public void testInitThemeDisplayPlidVirtualHostLayoutComposite()
		throws Exception {

		long plid = getThemeDisplayPlid(true, false);

		ServicePreAction.LayoutComposite defaultLayoutComposite =
			TestServicePreAction.INSTANCE.getDefaultVirtualHostLayoutComposite(
				_request);

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

		ThemeDisplay themeDisplay =
			TestServicePreAction.INSTANCE.initThemeDisplay(_request, _response);

		return themeDisplay.getPlid();
	}

	@DeleteAfterTestRun
	private Group _group;

	private final MockHttpServletRequest _request =
		new MockHttpServletRequest();
	private final MockHttpServletResponse _response =
		new MockHttpServletResponse();
	private User _user;

}