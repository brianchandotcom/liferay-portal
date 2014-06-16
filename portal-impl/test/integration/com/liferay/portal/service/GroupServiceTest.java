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

package com.liferay.portal.service;

import com.liferay.portal.GroupParentException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.Organization;
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
import com.liferay.portal.test.ResetDatabaseExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.test.CompanyTestUtil;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portal.util.test.OrganizationTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.RoleTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.util.test.BlogsTestUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 * @author Roberto Díaz
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		ResetDatabaseExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class GroupServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddCompanyStagingGroup() throws Exception {
		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			TestPropsValues.getCompanyId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute("staging", Boolean.TRUE);

		Group companyStagingGroup = GroupLocalServiceUtil.addGroup(
			TestPropsValues.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			companyGroup.getClassName(), companyGroup.getClassPK(),
			companyGroup.getGroupId(), companyGroup.getDescriptiveName(),
			companyGroup.getDescription(), companyGroup.getType(),
			companyGroup.isManualMembership(),
			companyGroup.getMembershipRestriction(),
			companyGroup.getFriendlyURL(), false, companyGroup.isActive(),
			serviceContext);

		Assert.assertTrue(companyStagingGroup.isCompanyStagingGroup());

		Assert.assertEquals(
			companyGroup.getGroupId(), companyStagingGroup.getLiveGroupId());
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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTagsCount = AssetTagLocalServiceUtil.getGroupTagsCount(
			group.getGroupId());

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), null,
			serviceContext);

		Assert.assertEquals(
			initialTagsCount + 1,
			AssetTagLocalServiceUtil.getGroupTagsCount(group.getGroupId()));

		User user = UserTestUtil.addUser(
			RandomTestUtil.randomString(), group.getGroupId());

		BlogsEntry blogsEntry = BlogsTestUtil.addEntry(
			user.getUserId(), group, true);

		Assert.assertNotNull(
			BlogsEntryLocalServiceUtil.fetchBlogsEntry(
				blogsEntry.getEntryId()));

		GroupLocalServiceUtil.deleteGroup(group.getGroupId());

		Assert.assertEquals(
			initialTagsCount,
			AssetTagLocalServiceUtil.getGroupTagsCount(group.getGroupId()));
		Assert.assertNull(
			BlogsEntryLocalServiceUtil.fetchBlogsEntry(
				blogsEntry.getEntryId()));
	}

	@Test
	public void testFindGroupByDescription() throws Exception {
		String description = RandomTestUtil.randomString();

		GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID,
			RandomTestUtil.randomString(), description);

		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put("manualMembership", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);

		Assert.assertEquals(
			1,
			GroupLocalServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), null, description,
				groupParams));
	}

	@Test
	public void testFindGroupByDescriptionWithSpaces() throws Exception {
		String description =
			RandomTestUtil.randomString() + StringPool.SPACE +
				RandomTestUtil.randomString();

		GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID,
			RandomTestUtil.randomString(), description);

		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put("manualMembership", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);

		Assert.assertEquals(
			1,
			GroupLocalServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), null, description,
				groupParams));
	}

	@Test
	public void testFindGroupByName() throws Exception {
		String name = RandomTestUtil.randomString();

		GroupTestUtil.addGroup(GroupConstants.DEFAULT_PARENT_GROUP_ID, name);

		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put("manualMembership", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);

		Assert.assertEquals(
			1,
			GroupLocalServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), null, name, groupParams));
	}

	@Test
	public void testFindGroupByNameWithSpaces() throws Exception {
		String name =
			RandomTestUtil.randomString() + StringPool.SPACE +
				RandomTestUtil.randomString();

		GroupTestUtil.addGroup(GroupConstants.DEFAULT_PARENT_GROUP_ID, name);

		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put("manualMembership", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);

		Assert.assertEquals(
			1,
			GroupLocalServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), null, name, groupParams));
	}

	@Test
	public void testFindGuestGroupByCompanyName() throws Exception {
		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put("manualMembership", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);

		Assert.assertEquals(
			1,
			GroupLocalServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), null, "liferay", groupParams));
	}

	@Test
	public void testFindGuestGroupByCompanyNameCapitalized() throws Exception {
		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put("manualMembership", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);

		Assert.assertEquals(
			1,
			GroupLocalServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), null, "Liferay", groupParams));
	}

	@Test
	public void testFindNonexistentGroup() throws Exception {
		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put("manualMembership", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);

		Assert.assertEquals(
			0,
			GroupLocalServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), null, "cabina14", groupParams));
	}

	@Test
	public void testGetUserSitesGroups() throws Exception {
		Organization parentOrganization = OrganizationTestUtil.addOrganization(
			true);

		Group parentOrganizationGroup = parentOrganization.getGroup();

		LayoutTestUtil.addLayout(
			parentOrganizationGroup.getGroupId(),
			RandomTestUtil.randomString());

		Organization organization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		UserLocalServiceUtil.addOrganizationUsers(
			organization.getOrganizationId(),
			new long[] {TestPropsValues.getUserId()});

		List<Group> groups = GroupServiceUtil.getUserSitesGroups(
			TestPropsValues.getUserId(), null, false, QueryUtil.ALL_POS);

		Assert.assertTrue(groups.contains(parentOrganizationGroup));
	}

	@Test
	public void testGroupHasCurrentPageScopeDescriptiveName() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = addGroup(false, true, false);

		themeDisplay.setPlid(group.getClassPK());

		themeDisplay.setScopeGroupId(_group.getGroupId());

		String scopeDescriptiveName = group.getScopeDescriptiveName(
			themeDisplay);

		Assert.assertTrue(scopeDescriptiveName.contains("current-page"));
	}

	@Test
	public void testGroupHasCurrentSiteScopeDescriptiveName() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = addGroup(true, false, false);

		themeDisplay.setScopeGroupId(group.getGroupId());

		String scopeDescriptiveName = group.getScopeDescriptiveName(
			themeDisplay);

		Assert.assertTrue(scopeDescriptiveName.contains("current-site"));
	}

	@Test
	public void testGroupHasDefaultScopeDescriptiveName() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = addGroup(false, false, true);

		themeDisplay.setScopeGroupId(_group.getGroupId());

		String scopeDescriptiveName = group.getScopeDescriptiveName(
			themeDisplay);

		Assert.assertTrue(scopeDescriptiveName.contains("default"));
	}

	@Test
	public void testGroupHasLocalizedName() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = GroupTestUtil.addGroup("Localized Name");

		String scopeDescriptiveName = group.getScopeDescriptiveName(
			themeDisplay);

		Assert.assertTrue(scopeDescriptiveName.equals("Localized Name"));
	}

	@Test
	public void testGroupIsChildSiteScopeLabel() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = GroupTestUtil.addGroup();

		themeDisplay.setScopeGroupId(group.getGroupId());

		Group subgroup = GroupTestUtil.addGroup(
			group.getGroupId(), RandomTestUtil.randomString());

		String scopeLabel = subgroup.getScopeLabel(themeDisplay);

		Assert.assertEquals("child-site", scopeLabel);
	}

	@Test
	public void testGroupIsCurrentSiteScopeLabel() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = addGroup(true, false, false);

		themeDisplay.setScopeGroupId(group.getGroupId());

		String scopeLabel = group.getScopeLabel(themeDisplay);

		Assert.assertEquals(scopeLabel, "current-site");
	}

	@Test
	public void testGroupIsGlobalScopeLabel() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group  = addGroup(false, false, false);

		Company company = CompanyLocalServiceUtil.getCompany(
			group.getCompanyId());

		themeDisplay.setCompany(company);

		Group companyGroup = company.getGroup();

		String scopeLabel = companyGroup.getScopeLabel(themeDisplay);

		Assert.assertEquals("global", scopeLabel);
	}

	@Test
	public void testGroupIsPageScopeLabel() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = addGroup(false, true, false);

		themeDisplay.setPlid(group.getClassPK());

		themeDisplay.setScopeGroupId(_group.getGroupId());

		String scopeLabel = group.getScopeLabel(themeDisplay);

		Assert.assertEquals("page", scopeLabel);
	}

	@Test
	public void testGroupIsParentSiteScopeLabel() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = GroupTestUtil.addGroup();

		Group subgroup = GroupTestUtil.addGroup(
			group.getGroupId(), RandomTestUtil.randomString());

		themeDisplay.setScopeGroupId(subgroup.getGroupId());

		String scopeLabel = group.getScopeLabel(themeDisplay);

		Assert.assertEquals("parent-site", scopeLabel);
	}

	@Test
	public void testGroupIsSiteScopeLabel() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = GroupTestUtil.addGroup();

		themeDisplay.setScopeGroupId(_group.getGroupId());

		String scopeLabel = group.getScopeLabel(themeDisplay);

		Assert.assertEquals("site", scopeLabel);
	}

	@Test
	public void testInheritLocalesByDefault() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Assert.assertTrue(LanguageUtil.isInheritLocales(group.getGroupId()));

		Locale[] portalAvailableLocales = LanguageUtil.getAvailableLocales();

		Locale[] groupAvailableLocales = LanguageUtil.getAvailableLocales(
			group.getGroupId());

		Assert.assertArrayEquals(portalAvailableLocales, groupAvailableLocales);
	}

	@Test
	public void testInvalidChangeAvailableLanguageIds() throws Exception {
		testUpdateDisplaySettings(
			new Locale[] {LocaleUtil.SPAIN, LocaleUtil.US},
			new Locale[] {LocaleUtil.GERMANY, LocaleUtil.US}, null, true);
	}

	@Test
	public void testInvalidChangeDefaultLanguageId() throws Exception {
		testUpdateDisplaySettings(
			new Locale[] {LocaleUtil.SPAIN, LocaleUtil.US},
			new Locale[] {LocaleUtil.SPAIN, LocaleUtil.US}, LocaleUtil.GERMANY,
			true);
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
			layout.getName(LocaleUtil.getDefault()), null, 0, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, true,
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
				group1.isManualMembership(), group1.getMembershipRestriction(),
				group1.getFriendlyURL(), group1.isActive(),
				ServiceContextTestUtil.getServiceContext());

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
				group1.isManualMembership(), group1.getMembershipRestriction(),
				group1.getFriendlyURL(), group1.isActive(),
				ServiceContextTestUtil.getServiceContext());

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
				stagingGroup.getType(), stagingGroup.isManualMembership(),
				stagingGroup.getMembershipRestriction(),
				stagingGroup.getFriendlyURL(), stagingGroup.isActive(),
				ServiceContextTestUtil.getServiceContext());

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
				group.getDescription(), group.getType(),
				group.isManualMembership(), group.getMembershipRestriction(),
				group.getFriendlyURL(), group.isActive(),
				ServiceContextTestUtil.getServiceContext());

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
	public void testUpdateAvailableLocales() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Locale[] availableLocales =
			new Locale[] {LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US};

		group = GroupTestUtil.updateDisplaySettings(
			group.getGroupId(), availableLocales, null);

		Assert.assertArrayEquals(
			availableLocales,
			LanguageUtil.getAvailableLocales(group.getGroupId()));
	}

	@Test
	public void testUpdateDefaultLocale() throws Exception {
		Group group = GroupTestUtil.addGroup();

		group = GroupTestUtil.updateDisplaySettings(
			group.getGroupId(), null, LocaleUtil.SPAIN);

		Assert.assertEquals(
			LocaleUtil.SPAIN,
			PortalUtil.getSiteDefaultLocale(group.getGroupId()));
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

	@Test
	public void testValidChangeAvailableLanguageIds() throws Exception {
		testUpdateDisplaySettings(
			new Locale[] {LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			new Locale[] {LocaleUtil.SPAIN, LocaleUtil.US}, null, false);
	}

	@Test
	public void testValidChangeDefaultLanguageId() throws Exception {
		testUpdateDisplaySettings(
			new Locale[] {LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			new Locale[] {LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			LocaleUtil.GERMANY, false);
	}

	protected Group addGroup(
			boolean site, boolean layout, boolean layoutPrototype)
		throws Exception {

		if (site) {
			return GroupTestUtil.addGroup(RandomTestUtil.randomString());
		}
		else if (layout) {
			Group group = GroupTestUtil.addGroup(RandomTestUtil.randomString());

			Layout scopeLayout = LayoutTestUtil.addLayout(
				group.getGroupId(), RandomTestUtil.randomString());

			return GroupLocalServiceUtil.addGroup(
				TestPropsValues.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID, Layout.class.getName(),
				scopeLayout.getPlid(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
				RandomTestUtil.randomString(), null, 0, true,
				GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false,
				true, null);
		}
		else if (layoutPrototype) {
			Group group = GroupTestUtil.addGroup(RandomTestUtil.randomString());

			group.setClassName(LayoutPrototype.class.getName());

			return group;
		}
		else {
			return GroupTestUtil.addGroup();
		}
	}

	protected void givePermissionToManageSubsites(User user, Group group)
		throws Exception {

		Role role = RoleTestUtil.addRole(
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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
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

	protected void testUpdateDisplaySettings(
			Locale[] portalAvailableLocales, Locale[] groupAvailableLocales,
			Locale groupDefaultLocale, boolean expectFailure)
		throws Exception {

		Locale[] availableLocales = LanguageUtil.getAvailableLocales();

		CompanyTestUtil.resetCompanyLocales(
			TestPropsValues.getCompanyId(), portalAvailableLocales,
			LocaleUtil.getDefault());

		Group group = GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID,
			RandomTestUtil.randomString());

		try {
			GroupTestUtil.updateDisplaySettings(
				group.getGroupId(), groupAvailableLocales, groupDefaultLocale);

			if (expectFailure) {
				Assert.fail();
			}
		}
		catch (LocaleException le) {
			if (!expectFailure) {
				Assert.fail();
			}
		}
		finally {
			CompanyTestUtil.resetCompanyLocales(
				TestPropsValues.getCompanyId(), availableLocales,
				LocaleUtil.getDefault());
		}
	}

	private Group _group;

}