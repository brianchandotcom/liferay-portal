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

package com.liferay.portal.service;

import com.liferay.portal.GroupParentException;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.util.BlogsTestUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class GroupServiceTest {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	public void testAddPermissionsCustomRole() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(null, group.getGroupId());

		givePermissionToManageSubsites(user, group);

		testGroup(
			user, group, null, null, true, false, false, false, true, true,
			true);
	}

	@Test
	public void testAddPermissionsCustomRoleInSubsite() throws Exception {
		Group group1 = GroupTestUtil.addGroup("Test 1");

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId(), "Test 1.1");

		User user = UserTestUtil.addUser(null, group11.getGroupId());

		givePermissionToManageSubsites(user, group11);

		testGroup(
			user, group1, group11, null, true, false, false, false, false, true,
			true);
	}

	@Test
	public void testAddPermissionsRegularUser() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(null, group.getGroupId());

		testGroup(
			user, group, null, null, true, false, false, false, false, false,
			false);
	}

	@Test
	public void testAddPermissionsSiteAdmin() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(null, group.getGroupId());

		giveSiteAdminRole(user, group);

		testGroup(
			user, group, null, null, true, false, true, false, true, true,
			true);
	}

	@Test
	public void testAddPermissionsSubsiteAdmin() throws Exception {
		Group group1 = GroupTestUtil.addGroup("Test 1");

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId(), "Test 1.1");

		User user = UserTestUtil.addUser(null, group11.getGroupId());

		giveSiteAdminRole(user, group11);

		testGroup(
			user, group1, group11, null, true, false, false, true, false, true,
			true);
	}

	@Test
	public void testDeleteSite() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), group.getGroupId());

		BlogsEntry blogsEntry = BlogsTestUtil.addEntry(
			user.getUserId(), group, true);

		Assert.assertNotNull(
			BlogsEntryLocalServiceUtil.fetchBlogsEntry(
				blogsEntry.getEntryId()));

		GroupLocalServiceUtil.deleteGroup(group.getGroupId());

		Assert.assertNull(
			BlogsEntryLocalServiceUtil.fetchBlogsEntry(
				blogsEntry.getEntryId()));
	}

	@Test
	public void testGroupHasCurrentPageScopeName() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = addGroup(false, true, false);

		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());

		themeDisplay.setPlid(group.getClassPK());

		String scopeName = group.getScopeName(themeDisplay);

		Assert.assertTrue(scopeName.contains("current-page"));
	}

	@Test
	public void testGroupHasCurrentSiteScopeName() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = addGroup(true, false, false);

		themeDisplay.setScopeGroupId(group.getGroupId());

		String scopeName = group.getScopeName(themeDisplay);

		Assert.assertTrue(scopeName.contains("current-site"));
	}

	@Test
	public void testGroupHasDefaultScopeName() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = addGroup(false, false, true);

		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());

		String scopeName = group.getScopeName(themeDisplay);

		Assert.assertTrue(scopeName.contains("default"));
	}

	@Test
	public void testGroupHasLocalizedName() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = GroupTestUtil.addGroup("Localized name");

		String scopeName = group.getScopeName(themeDisplay);

		Assert.assertTrue(scopeName.equals("Localized name"));
	}

	@Test
	public void testGroupIsChildSiteScopeType() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = GroupTestUtil.addGroup();

		Group childrenGroup = GroupTestUtil.addGroup(
			group.getGroupId(), ServiceTestUtil.randomString());

		themeDisplay.setScopeGroupId(group.getGroupId());

		String scopeType = childrenGroup.getScopeType(themeDisplay);

		Assert.assertEquals("child-site", scopeType);
	}

	@Test
	public void testGroupIsCurrentSiteScopeType() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = addGroup(true, false, false);

		themeDisplay.setScopeGroupId(group.getGroupId());

		String scopeType = group.getScopeType(themeDisplay);

		Assert.assertEquals(scopeType, "current-site");
	}

	@Test
	public void testGroupIsGlobalScopeType() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group  = addGroup(false, false, false);

		Company company = CompanyLocalServiceUtil.getCompany(
			group.getCompanyId());

		themeDisplay.setCompany(company);

		Group companyGroup = company.getGroup();

		String scopeType = companyGroup.getScopeType(themeDisplay);

		Assert.assertEquals("global", scopeType);
	}

	@Test
	public void testGroupIsPageScopeType() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = addGroup(false, true, false);

		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());

		themeDisplay.setPlid(group.getClassPK());

		String scopeType = group.getScopeType(themeDisplay);

		Assert.assertEquals("page", scopeType);
	}

	@Test
	public void testGroupIsParentSiteScopeType() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = GroupTestUtil.addGroup();

		Group childrenGroup = GroupTestUtil.addGroup(
			group.getGroupId(), ServiceTestUtil.randomString());

		themeDisplay.setScopeGroupId(childrenGroup.getGroupId());

		String scopeType = group.getScopeType(themeDisplay);

		Assert.assertEquals("parent-site", scopeType);
	}

	@Test
	public void testGroupIsSiteScopeType() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = GroupTestUtil.addGroup();

		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());

		String scopeType = group.getScopeType(themeDisplay);

		Assert.assertEquals("site", scopeType);
	}

	@Test
	public void testScopes() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(group.getGroupId(), "Page 1");

		Assert.assertFalse(layout.hasScopeGroup());

		Group scope = GroupLocalServiceUtil.addGroup(
			TestPropsValues.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			layout.getName(LocaleUtil.getDefault()), null, 0, null, false, true,
			null);

		Assert.assertFalse(scope.isRoot());
		Assert.assertEquals(scope.getParentGroupId(), group.getGroupId());
	}

	@Test
	public void testSelectableParentSites() throws Exception {
		testSelectableParentSites(false);
	}

	@Test
	public void testSelectableParentSitesStaging() throws Exception {
		testSelectableParentSites(true);
	}

	@Test
	public void testSelectFirstChildGroupAsParentSite() throws Exception {
		Group group1 = GroupTestUtil.addGroup("Test 1");

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId(),"Test 1.1");

		try {
			GroupLocalServiceUtil.updateGroup(
				group1.getGroupId(), group11.getGroupId(), group1.getName(),
				group1.getDescription(), group1.getType(),
				group1.getFriendlyURL(), group1.isActive(),
				ServiceTestUtil.getServiceContext());

			Assert.fail("A child group cannot be its parent group");
		}
		catch (GroupParentException gpe) {
			Assert.assertEquals(
				GroupParentException.CHILD_DESCENDANT, gpe.getType());
		}
	}

	@Test
	public void testSelectLastChildGroupAsParentSite() throws Exception {
		Group group1 = GroupTestUtil.addGroup("Test 1");

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId(), "Test 1.1");

		Group group111 = GroupTestUtil.addGroup(
			group11.getGroupId(), "Test 1.1.1");

		Group group1111 = GroupTestUtil.addGroup(
			group111.getGroupId(), "Test 1.1.1.1");

		try {
			GroupLocalServiceUtil.updateGroup(
				group1.getGroupId(), group1111.getGroupId(), group1.getName(),
				group1.getDescription(), group1.getType(),
				group1.getFriendlyURL(), group1.isActive(),
				ServiceTestUtil.getServiceContext());

			Assert.fail("A child group cannot be its parent group");
		}
		catch (GroupParentException gpe) {
			Assert.assertEquals(
				GroupParentException.CHILD_DESCENDANT, gpe.getType());
		}
	}

	@Test
	public void testSelectLiveGroupAsParentSite() throws Exception {
		Group group = GroupTestUtil.addGroup();

		GroupTestUtil.enableLocalStaging(group);

		Assert.assertTrue(group.hasStagingGroup());

		try {
			Group stagingGroup = group.getStagingGroup();

			GroupLocalServiceUtil.updateGroup(
				stagingGroup.getGroupId(), group.getGroupId(),
				stagingGroup.getName(), stagingGroup.getDescription(),
				stagingGroup.getType(), stagingGroup.getFriendlyURL(),
				stagingGroup.isActive(), ServiceTestUtil.getServiceContext());

			Assert.fail("A group cannot have its live group as parent");
		}
		catch (GroupParentException gpe) {
			Assert.assertEquals(
				GroupParentException.STAGING_DESCENDANT, gpe.getType());
		}
	}

	@Test
	public void testSelectOwnGroupAsParentSite() throws Exception {
		Group group = GroupTestUtil.addGroup();

		try {
			GroupLocalServiceUtil.updateGroup(
				group.getGroupId(), group.getGroupId(), group.getName(),
				group.getDescription(), group.getType(), group.getFriendlyURL(),
				group.isActive(), ServiceTestUtil.getServiceContext());

			Assert.fail("A group cannot be its own parent");
		}
		catch (GroupParentException gpe) {
			Assert.assertEquals(
				GroupParentException.SELF_DESCENDANT, gpe.getType());
		}
	}

	@Test
	public void testSubsites() throws Exception {
		Group group1 = GroupTestUtil.addGroup("Test 1");

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId(), "Test 1.1");

		Group group111 = GroupTestUtil.addGroup(
			group11.getGroupId(), "Test 1.1.1");

		Assert.assertTrue(group1.isRoot());
		Assert.assertFalse(group11.isRoot());
		Assert.assertFalse(group111.isRoot());
		Assert.assertEquals(group1.getGroupId(), group11.getParentGroupId());
		Assert.assertEquals(group11.getGroupId(), group111.getParentGroupId());
	}

	@Test
	public void testUpdatePermissionsCustomRole() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(null, group.getGroupId());

		givePermissionToManageSubsites(user, group);

		testGroup(
			user, group, null, null, false, true, false, false, true, true,
			true);
	}

	@Test
	public void testUpdatePermissionsCustomRoleInSubsite() throws Exception {
		Group group1 = GroupTestUtil.addGroup("Test 1");

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId(), "Test 1.1");

		User user = UserTestUtil.addUser(null, group11.getGroupId());

		givePermissionToManageSubsites(user, group11);

		testGroup(
			user, group1, group11, null, false, true, false, false, false, true,
			true);
	}

	@Test
	public void testUpdatePermissionsRegularUser() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(null, group.getGroupId());

		testGroup(
			user, group, null, null, false, true, false, false, false, false,
			false);
	}

	@Test
	public void testUpdatePermissionsSiteAdmin() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(null, group.getGroupId());

		giveSiteAdminRole(user, group);

		testGroup(
			user, group, null, null, false, true, true, false, true, true,
			true);
	}

	@Test
	public void testUpdatePermissionsSubsiteAdmin() throws Exception {
		Group group1 = GroupTestUtil.addGroup("Test 1");

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId(), "Test 1.1");

		User user = UserTestUtil.addUser(null, group11.getGroupId());

		giveSiteAdminRole(user, group11);

		testGroup(
			user, group1, group11, null, false, true, false, true, false, true,
			true);
	}

	protected Group addGroup(
			boolean site, boolean layout, boolean layoutPrototype)
		throws Exception {

		if (site) {
			return GroupTestUtil.addGroup(ServiceTestUtil.randomString());
		}
		else if (layout) {
			Group group = GroupTestUtil.addGroup(
				ServiceTestUtil.randomString());

			Layout scopeLayout = LayoutTestUtil.addLayout(
				group.getGroupId(), ServiceTestUtil.randomString());

			return GroupLocalServiceUtil.addGroup(
				TestPropsValues.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID, Layout.class.getName(),
				scopeLayout.getPlid(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
				ServiceTestUtil.randomString(), null, 0, null, false, true,
				null);
		}
		else if (layoutPrototype) {
			Group group = GroupTestUtil.addGroup(
				ServiceTestUtil.randomString());

			group.setClassName(LayoutPrototype.class.getName());

			return group;
		}
		else {
			return GroupTestUtil.addGroup();
		}
	}

	protected void givePermissionToManageSubsites(User user, Group group)
		throws Exception {

		Role role = ServiceTestUtil.addRole(
			"Subsites Admin", RoleConstants.TYPE_SITE, Group.class.getName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			ActionKeys.MANAGE_SUBGROUPS);

		long[] roleIds = new long[] {role.getRoleId()};

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), group.getGroupId(), roleIds);
	}

	protected void giveSiteAdminRole(User user, Group group) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		long[] roleIds = new long[] {role.getRoleId()};

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), group.getGroupId(), roleIds);
	}

	protected void testGroup(
			User user, Group group1, Group group11, Group group111,
			boolean addGroup, boolean updateGroup, boolean hasManageSite1,
			boolean hasManageSite11, boolean hasManageSubsitePermisionOnGroup1,
			boolean hasManageSubsitePermisionOnGroup11,
			boolean hasManageSubsitePermisionOnGroup111)
		throws Exception {

		if (group1 == null) {
			group1 = GroupTestUtil.addGroup("Example1");
		}

		if (group11 == null) {
			group11 = GroupTestUtil.addGroup(group1.getGroupId(), "Example11");
		}

		if (group111 == null) {
			group111 = GroupTestUtil.addGroup(
				group11.getGroupId(), "Example111");
		}

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group1.getGroupId(), user.getUserId());

		if (addGroup) {
			try {
				GroupTestUtil.addGroup(
					"Test 2", GroupConstants.DEFAULT_PARENT_GROUP_ID,
					serviceContext);

				Assert.fail(
					"The user should not be able to add top level sites");
			}
			catch (PrincipalException pe) {
			}

			try {
				GroupTestUtil.addGroup(
					"Test 1.2", group1.getGroupId(), serviceContext);

				if (!hasManageSubsitePermisionOnGroup1 && !hasManageSite1) {
					Assert.fail("The user should not be able to add this site");
				}
			}
			catch (PrincipalException pe) {
				if (hasManageSubsitePermisionOnGroup1 || hasManageSite1) {
					Assert.fail("The user should be able to add this site");
				}
			}

			try {
				GroupTestUtil.addGroup(
					"Test 1.1.2", group11.getGroupId(), serviceContext);

				if (!hasManageSubsitePermisionOnGroup11 && !hasManageSite1) {
					Assert.fail("The user should not be able to add this site");
				}
			}
			catch (PrincipalException pe) {
				if (hasManageSubsitePermisionOnGroup11 || hasManageSite1) {
					Assert.fail("The user should be able to add this site");
				}
			}

			try {
				GroupTestUtil.addGroup(
					"Test 1.1.1.2", group111.getGroupId(), serviceContext);

				if (!hasManageSubsitePermisionOnGroup111 && !hasManageSite1) {
					Assert.fail("The user should not be able to add this site");
				}
			}
			catch (PrincipalException pe) {
				if (hasManageSubsitePermisionOnGroup111 || hasManageSite1) {
					Assert.fail("The user should be able to add this site");
				}
			}
		}

		if (updateGroup) {
			try {
				GroupServiceUtil.updateGroup(group1.getGroupId(), "");

				if (!hasManageSite1) {
					Assert.fail(
						"The user should not be able to update this site");
				}
			}
			catch (PrincipalException pe) {
				if (hasManageSite1) {
					Assert.fail("The user should be able to update this site");
				}
			}

			try {
				GroupServiceUtil.updateGroup(group11.getGroupId(), "");

				if (!hasManageSubsitePermisionOnGroup1 && !hasManageSite1 &&
					!hasManageSite11) {

					Assert.fail(
						"The user should not be able to update this site");
				}
			}
			catch (PrincipalException pe) {
				if (hasManageSubsitePermisionOnGroup1 || hasManageSite1 ||
					hasManageSite11) {

					Assert.fail("The user should be able to update this site");
				}
			}

			try {
				GroupServiceUtil.updateGroup(group111.getGroupId(), "");

				if (!hasManageSubsitePermisionOnGroup11 && !hasManageSite1) {
					Assert.fail(
						"The user should not be able to update this site");
				}
			}
			catch (PrincipalException pe) {
				if (hasManageSubsitePermisionOnGroup1 || hasManageSite1) {
					Assert.fail("The user should be able to update this site");
				}
			}
		}
	}

	protected void testSelectableParentSites(boolean staging) throws Exception {
		Group group = GroupTestUtil.addGroup();

		Assert.assertTrue(group.isRoot());

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("site", Boolean.TRUE);

		List<Long> excludedGroupIds = new ArrayList<Long>();

		excludedGroupIds.add(group.getGroupId());

		if (staging) {
			GroupTestUtil.enableLocalStaging(group);

			Assert.assertTrue(group.hasStagingGroup());

			excludedGroupIds.add(group.getStagingGroup().getGroupId());
		}

		params.put("excludedGroupIds", excludedGroupIds);

		List<Group> selectableGroups = GroupLocalServiceUtil.search(
			group.getCompanyId(), null, StringPool.BLANK, params,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (Group selectableGroup : selectableGroups) {
			long selectableGroupId = selectableGroup.getGroupId();

			if (selectableGroupId == group.getGroupId()) {
				Assert.fail("A group cannot be its own parent");
			}
			else if (staging) {
				if (selectableGroupId == group.getLiveGroupId()) {
					Assert.fail("A group cannot have its live group as parent");
				}
			}
		}
	}

}