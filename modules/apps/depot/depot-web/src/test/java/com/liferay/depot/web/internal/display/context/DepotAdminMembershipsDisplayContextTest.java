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

package com.liferay.depot.web.internal.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.GroupPermission;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
public class DepotAdminMembershipsDisplayContextTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_user = Mockito.mock(User.class);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(Mockito.mock(Portal.class));

		Mockito.when(
			PortalUtil.getSelectedUser(Mockito.any(HttpServletRequest.class))
		).thenReturn(
			_user
		);

		GroupPermissionUtil groupPermissionUtil = new GroupPermissionUtil();

		groupPermissionUtil.setGroupPermission(
			Mockito.mock(GroupPermission.class));
	}

	@Test
	public void testGetDepotGroupsWithDepotAndCompanyAdmin() throws Exception {
		Group group = getDepotGroup();

		Mockito.when(
			_user.getGroups()
		).thenReturn(
			Collections.singletonList(group)
		);

		DepotAdminMembershipsDisplayContext
			depotAdminMembershipsDisplayContext =
				new DepotAdminMembershipsDisplayContext(
					getHttpServletRequest(
						new ThemeDisplayBuilder().withPermissionChecker(
							getPermissionCheckerWithCompanyAdmin()
						).build()));

		List<Group> depots = depotAdminMembershipsDisplayContext.getDepotGroups(
			0, 20);

		Assert.assertEquals(depots.toString(), 1, depots.size());
	}

	@Test
	public void testGetDepotGroupsWithDepotAndNoCompanyAdminAndAssignMember()
		throws Exception {

		Group group = getDepotGroup();

		Mockito.when(
			_user.getGroups()
		).thenReturn(
			Collections.singletonList(group)
		);

		DepotAdminMembershipsDisplayContext
			depotAdminMembershipsDisplayContext =
				new DepotAdminMembershipsDisplayContext(
					getHttpServletRequest(
						new ThemeDisplayBuilder().withPermissionChecker(
							getPermissionCheckerWithNoCompanyAdminAndAssignMember()
						).build()));

		List<Group> depots = depotAdminMembershipsDisplayContext.getDepotGroups(
			0, 20);

		Assert.assertEquals(depots.toString(), 1, depots.size());
	}

	@Test
	public void testGetDepotGroupsWithDepotAndNoCompanyAdminAndNoAssignMember()
		throws Exception {

		Group group = getDepotGroup();

		Mockito.when(
			_user.getGroups()
		).thenReturn(
			Collections.singletonList(group)
		);

		DepotAdminMembershipsDisplayContext
			depotAdminMembershipsDisplayContext =
				new DepotAdminMembershipsDisplayContext(
					getHttpServletRequest(
						new ThemeDisplayBuilder().withPermissionChecker(
							getPermissionCheckerWithNoCompanyAdminAndNoAssignMember()
						).build()));

		List<Group> depots = depotAdminMembershipsDisplayContext.getDepotGroups(
			0, 20);

		Assert.assertEquals(depots.toString(), 0, depots.size());
	}

	@Test
	public void testGetDepotGroupsWithSite() throws Exception {
		Group group = getSiteGroup();

		Mockito.when(
			_user.getGroups()
		).thenReturn(
			Collections.singletonList(group)
		);

		DepotAdminMembershipsDisplayContext
			depotAdminMembershipsDisplayContext =
				new DepotAdminMembershipsDisplayContext(
					getHttpServletRequest(new ThemeDisplayBuilder().build()));

		List<Group> depots = depotAdminMembershipsDisplayContext.getDepotGroups(
			0, 20);

		Assert.assertEquals(depots.toString(), 0, depots.size());
	}

	@Test
	public void testGetDepotsGroupCountWithDepotAndCompanyAdmin()
		throws Exception {

		Group group = getDepotGroup();

		Mockito.when(
			_user.getGroups()
		).thenReturn(
			Collections.singletonList(group)
		);

		DepotAdminMembershipsDisplayContext
			depotAdminMembershipsDisplayContext =
				new DepotAdminMembershipsDisplayContext(
					getHttpServletRequest(
						new ThemeDisplayBuilder().withPermissionChecker(
							getPermissionCheckerWithCompanyAdmin()
						).build()));

		Assert.assertEquals(
			1, depotAdminMembershipsDisplayContext.getDepotGroupsCount());
	}

	@Test
	public void testGetDepotsGroupCountWithoutDepot() throws Exception {
		Mockito.when(
			_user.getGroups()
		).thenReturn(
			Collections.emptyList()
		);

		DepotAdminMembershipsDisplayContext
			depotAdminMembershipsDisplayContext =
				new DepotAdminMembershipsDisplayContext(
					getHttpServletRequest(
						new ThemeDisplayBuilder().withPermissionChecker(
							getPermissionCheckerWithCompanyAdmin()
						).build()));

		Assert.assertEquals(
			0, depotAdminMembershipsDisplayContext.getDepotGroupsCount());
	}

	protected Group getDepotGroup() {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.isSite()
		).thenReturn(
			false
		);

		Mockito.when(
			group.getType()
		).thenReturn(
			GroupConstants.TYPE_DEPOT
		);

		return group;
	}

	protected HttpServletRequest getHttpServletRequest(
		ThemeDisplay themeDisplay) {

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return httpServletRequest;
	}

	protected PermissionChecker getPermissionCheckerWithCompanyAdmin() {
		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			permissionChecker.isCompanyAdmin()
		).thenReturn(
			true
		);

		return permissionChecker;
	}

	protected PermissionChecker
			getPermissionCheckerWithNoCompanyAdminAndAssignMember()
		throws PortalException {

		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			permissionChecker.isCompanyAdmin()
		).thenReturn(
			false
		);

		Mockito.when(
			GroupPermissionUtil.contains(
				Mockito.any(PermissionChecker.class), Mockito.any(Group.class),
				Mockito.anyString())
		).thenReturn(
			true
		);

		return permissionChecker;
	}

	protected PermissionChecker
			getPermissionCheckerWithNoCompanyAdminAndNoAssignMember()
		throws PortalException {

		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			permissionChecker.isCompanyAdmin()
		).thenReturn(
			false
		);

		Mockito.when(
			GroupPermissionUtil.contains(
				Mockito.any(PermissionChecker.class), Mockito.any(Group.class),
				Mockito.anyString())
		).thenReturn(
			false
		);

		return permissionChecker;
	}

	protected Group getSiteGroup() {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.isSite()
		).thenReturn(
			true
		);

		return group;
	}

	private static User _user;

	private static class ThemeDisplayBuilder {

		public ThemeDisplayBuilder() {
			_themeDisplay = new ThemeDisplay();
		}

		public ThemeDisplay build() {
			return _themeDisplay;
		}

		public ThemeDisplayBuilder withPermissionChecker(
			PermissionChecker permissionChecker) {

			_themeDisplay.setPermissionChecker(permissionChecker);

			return this;
		}

		private final ThemeDisplay _themeDisplay;

	}

}