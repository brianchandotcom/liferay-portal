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

package com.liferay.portal.verify;

import com.liferay.portal.events.test.TestServicePreAction;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.verify.test.BaseVerifyProcessTestCase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Manuel de la Peña
 * @author Preston Crary
 */
public class VerifyPermissionTest extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testFixUserDefaultRolePermissions() throws Exception {
		Layout layout = addPersonalLayoutWithDefaultResource();

		Role powerUserRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.POWER_USER);
		String primKey = PortletPermissionUtil.getPrimaryKey(
			layout.getPlid(), PortletKeys.DOCKBAR);

		ResourcePermission resourcePermission =
			ResourcePermissionLocalServiceUtil.fetchResourcePermission(
				TestPropsValues.getCompanyId(), PortletKeys.DOCKBAR,
				ResourceConstants.SCOPE_INDIVIDUAL, primKey,
				powerUserRole.getRoleId());

		Assert.assertNull(resourcePermission);

		Role userRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.USER);

		resourcePermission =
			ResourcePermissionLocalServiceUtil.getResourcePermission(
				TestPropsValues.getCompanyId(), PortletKeys.DOCKBAR,
				ResourceConstants.SCOPE_INDIVIDUAL, primKey,
				userRole.getRoleId());

		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.VIEW));

		resourcePermission.setRoleId(powerUserRole.getRoleId());

		ResourcePermissionLocalServiceUtil.updateResourcePermission(
			resourcePermission);

		doVerify();

		resourcePermission =
			ResourcePermissionLocalServiceUtil.fetchResourcePermission(
				TestPropsValues.getCompanyId(), PortletKeys.DOCKBAR,
				ResourceConstants.SCOPE_INDIVIDUAL, primKey,
				powerUserRole.getRoleId());

		Assert.assertNull(resourcePermission);

		resourcePermission =
			ResourcePermissionLocalServiceUtil.getResourcePermission(
				TestPropsValues.getCompanyId(), PortletKeys.DOCKBAR,
				ResourceConstants.SCOPE_INDIVIDUAL, primKey,
				userRole.getRoleId());

		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.VIEW));
	}

	protected Layout addPersonalLayoutWithDefaultResource() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		User user = UserTestUtil.addUser();

		themeDisplay.setUser(user);

		Company company = CompanyLocalServiceUtil.getCompany(
			user.getCompanyId());

		themeDisplay.setCompany(company);

		TestServicePreAction.INSTANCE.addDefaultUserPublicLayouts(user);

		Group userGroup = user.getGroup();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			userGroup.getGroupId(), LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		Layout layout = layouts.get(0);

		themeDisplay.setLayout(layout);

		_request.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			user.getCompanyId(), PortletKeys.DOCKBAR);

		PortalUtil.addPortletDefaultResource(_request, portlet);

		return layout;
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyPermission();
	}

	private final HttpServletRequest _request = new MockHttpServletRequest();

}