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

import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.GroupParentException;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.NoSuchResourcePermissionException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Julio Camarero
 * @author Roberto Díaz
 * @author Sergio González
 */
@Sync(cleanTransaction = true)
public class GroupServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

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
			companyGroup.getGroupId(), companyGroup.getNameMap(),
			companyGroup.getDescriptionMap(), companyGroup.getType(),
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
		Group group1 = GroupTestUtil.addGroup();

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId());

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
		Group group1 = GroupTestUtil.addGroup();

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId());

		User user = UserTestUtil.addUser(null, group11.getGroupId());

		giveSiteAdminRole(user, group11);

		testGroup(
			user, group1, group11, null, true, false, false, true, false, true,
			true);
	}

	@Test(expected = NoSuchResourcePermissionException.class)
	public void testDeleteGroupWithStagingGroupRemovesStagingResource()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		GroupTestUtil.enableLocalStaging(group);

		Assert.assertTrue(group.hasStagingGroup());

		Group stagingGroup = group.getStagingGroup();

		GroupServiceUtil.deleteGroup(group.getGroupId());

		Role role = RoleLocalServiceUtil.getRole(
			stagingGroup.getCompanyId(), RoleConstants.OWNER);

		ResourcePermissionLocalServiceUtil.getResourcePermission(
			stagingGroup.getCompanyId(), Group.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(stagingGroup.getGroupId()), role.getRoleId());
	}

	@Test
	public void testDeleteGroupWithStagingGroupRemovesStagingUserGroupRoles()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		GroupTestUtil.enableLocalStaging(group);

		Assert.assertTrue(group.hasStagingGroup());

		Group stagingGroup = group.getStagingGroup();

		List<UserGroupRole> stagingUserGroupRoles =
			UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroup(
				stagingGroup.getGroupId());

		int stagingUserGroupRolesCount = stagingUserGroupRoles.size();

		Assert.assertEquals(1, stagingUserGroupRolesCount);

		GroupServiceUtil.deleteGroup(group.getGroupId());

		stagingUserGroupRoles =
			UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroup(
				stagingGroup.getGroupId());

		stagingUserGroupRolesCount = stagingUserGroupRoles.size();

		Assert.assertEquals(0, stagingUserGroupRolesCount);
	}

	@Test
	public void testDeleteOrganizationSiteOnlyRemovesSiteRoles()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization(true);

		Group organizationSite = GroupLocalServiceUtil.getOrganizationGroup(
			TestPropsValues.getCompanyId(), organization.getOrganizationId());

		organizationSite.setManualMembership(true);

		User user = UserTestUtil.addOrganizationOwnerUser(organization);

		UserLocalServiceUtil.addGroupUser(
			organizationSite.getGroupId(), user.getUserId());
		UserLocalServiceUtil.addOrganizationUsers(
			organization.getOrganizationId(), new long[] {user.getUserId()});

		Role siteRole = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), organizationSite.getGroupId(),
			new long[] {siteRole.getRoleId()});

		GroupLocalServiceUtil.deleteGroup(organizationSite);

		Assert.assertEquals(
			1,
			UserGroupRoleLocalServiceUtil.getUserGroupRolesCount(
				user.getUserId(), organizationSite.getGroupId()));
	}

	@Test
	public void testDeleteSite() throws Exception {
		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTagsCount = AssetTagLocalServiceUtil.getGroupTagsCount(
			group.getGroupId());

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), group.getGroupId(),
			RandomTestUtil.randomString(), serviceContext);

		Assert.assertEquals(
			initialTagsCount + 1,
			AssetTagLocalServiceUtil.getGroupTagsCount(group.getGroupId()));

		User user = UserTestUtil.addUser(group.getGroupId());

		GroupLocalServiceUtil.deleteGroup(group.getGroupId());

		Assert.assertEquals(
			initialTagsCount,
			AssetTagLocalServiceUtil.getGroupTagsCount(group.getGroupId()));
	}

	@Test
	public void testFindGroupByDescription() throws Exception {
		Group group = GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("manualMembership", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);

		Assert.assertEquals(
			1,
			GroupLocalServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), null,
				group.getDescription(getLocale()), groupParams));
	}

	@Test
	public void testFindGroupByDescriptionWithSpaces() throws Exception {
		Group group = GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		group.setDescription(
			RandomTestUtil.randomString() + StringPool.SPACE +
				RandomTestUtil.randomString());

		GroupLocalServiceUtil.updateGroup(group);

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("manualMembership", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);

		Assert.assertEquals(
			1,
			GroupLocalServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), null,
				group.getDescription(getLocale()), groupParams));
	}

	@Test
	public void testFindGroupByName() throws Exception {
		Group group = GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("manualMembership", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);

		Assert.assertEquals(
			1,
			GroupLocalServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), null,
				group.getName(getLocale()), groupParams));
	}

	@Test
	public void testFindGroupByNameWithSpaces() throws Exception {
		Group group = GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		group.setName(
			RandomTestUtil.randomString() + StringPool.SPACE +
				RandomTestUtil.randomString());

		GroupLocalServiceUtil.updateGroup(group);

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("manualMembership", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);

		Assert.assertEquals(
			1,
			GroupLocalServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), null,
				group.getName(getLocale()), groupParams));
	}

	@Test
	public void testFindGroupByRole() throws Exception {
		Group group = GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		long roleId = RoleTestUtil.addGroupRole(group.getGroupId());

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("groupsRoles", Long.valueOf(roleId));
		groupParams.put("site", Boolean.TRUE);

		Assert.assertEquals(
			1,
			GroupLocalServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), null, groupParams));

		List<Group> groups = GroupLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), null, groupParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(1, groups.size());
		Assert.assertEquals(group, groups.get(0));
		Assert.assertEquals(
			1, GroupLocalServiceUtil.getRoleGroupsCount(roleId));

		groups = GroupLocalServiceUtil.getRoleGroups(roleId);

		Assert.assertEquals(1, groups.size());
		Assert.assertEquals(group, groups.get(0));
	}

	@Test
	public void testFindGuestGroupByCompanyName() throws Exception {
		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("manualMembership", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);

		Assert.assertEquals(
			1,
			GroupLocalServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), null, "liferay%", groupParams));
	}

	@Test
	public void testFindGuestGroupByCompanyNameCapitalized() throws Exception {
		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("manualMembership", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);

		Assert.assertEquals(
			1,
			GroupLocalServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), null, "Liferay%", groupParams));
	}

	@Test
	public void testFindNonexistentGroup() throws Exception {
		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("manualMembership", Boolean.TRUE);
		groupParams.put("site", Boolean.TRUE);

		Assert.assertEquals(
			0,
			GroupLocalServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), null, "cabina14", groupParams));
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

		Group group = GroupTestUtil.addGroup();

		String scopeDescriptiveName = group.getScopeDescriptiveName(
			themeDisplay);

		Assert.assertTrue(
			scopeDescriptiveName.equals(
				group.getName(themeDisplay.getLocale())));
	}

	@Test
	public void testGroupIsChildSiteScopeLabel() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = GroupTestUtil.addGroup();

		themeDisplay.setScopeGroupId(group.getGroupId());

		Group subgroup = GroupTestUtil.addGroup(group.getGroupId());

		String scopeLabel = subgroup.getScopeLabel(themeDisplay);

		Assert.assertEquals("child-site", scopeLabel);
	}

	@Test
	public void testGroupIsCurrentSiteScopeLabel() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = addGroup(true, false, false);

		themeDisplay.setScopeGroupId(group.getGroupId());

		String scopeLabel = group.getScopeLabel(themeDisplay);

		Assert.assertEquals("current-site", scopeLabel);
	}

	@Test
	public void testGroupIsGlobalScopeLabel() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = addGroup(false, false, false);

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

		Group subgroup = GroupTestUtil.addGroup(group.getGroupId());

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
	public void testIndividualResourcePermission() throws Exception {
		int resourcePermissionsCount =
			ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
				_group.getCompanyId(), Group.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(_group.getGroupId()));

		Assert.assertEquals(1, resourcePermissionsCount);
	}

	@Test
	public void testInheritLocalesByDefault() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Assert.assertTrue(LanguageUtil.isInheritLocales(group.getGroupId()));
		Assert.assertEquals(
			LanguageUtil.getAvailableLocales(),
			LanguageUtil.getAvailableLocales(group.getGroupId()));
	}

	@Test
	public void testInvalidChangeAvailableLanguageIds() throws Exception {
		testUpdateDisplaySettings(
			Arrays.asList(LocaleUtil.SPAIN, LocaleUtil.US),
			Arrays.asList(LocaleUtil.GERMANY, LocaleUtil.US), null, true);
	}

	@Test
	public void testInvalidChangeDefaultLanguageId() throws Exception {
		testUpdateDisplaySettings(
			Arrays.asList(LocaleUtil.SPAIN, LocaleUtil.US),
			Arrays.asList(LocaleUtil.SPAIN, LocaleUtil.US), LocaleUtil.GERMANY,
			true);
	}

	@Test
	public void testScopes() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(group);

		Assert.assertFalse(layout.hasScopeGroup());

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(
			LocaleUtil.getDefault(), layout.getName(LocaleUtil.getDefault()));

		Group scope = GroupLocalServiceUtil.addGroup(
			TestPropsValues.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap,
			(Map<Locale, String>)null, 0, true,
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

	@Test(expected = GroupParentException.MustNotHaveChildParent.class)
	public void testSelectFirstChildGroupAsParentSite() throws Exception {
		Group group1 = GroupTestUtil.addGroup();

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId());

		GroupLocalServiceUtil.updateGroup(
			group1.getGroupId(), group11.getGroupId(), group1.getNameMap(),
			group1.getDescriptionMap(), group1.getType(),
			group1.isManualMembership(), group1.getMembershipRestriction(),
			group1.getFriendlyURL(), group1.isInheritContent(),
			group1.isActive(), ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = GroupParentException.MustNotHaveChildParent.class)
	public void testSelectLastChildGroupAsParentSite() throws Exception {
		Group group1 = GroupTestUtil.addGroup();

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId());

		Group group111 = GroupTestUtil.addGroup(group11.getGroupId());

		Group group1111 = GroupTestUtil.addGroup(group111.getGroupId());

		GroupLocalServiceUtil.updateGroup(
			group1.getGroupId(), group1111.getGroupId(), group1.getNameMap(),
			group1.getDescriptionMap(), group1.getType(),
			group1.isManualMembership(), group1.getMembershipRestriction(),
			group1.getFriendlyURL(), group1.isInheritContent(),
			group1.isActive(), ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = GroupParentException.MustNotHaveStagingParent.class)
	public void testSelectLiveGroupAsParentSite() throws Exception {
		Group group = GroupTestUtil.addGroup();

		GroupTestUtil.enableLocalStaging(group);

		Assert.assertTrue(group.hasStagingGroup());

		Group stagingGroup = group.getStagingGroup();

		GroupLocalServiceUtil.updateGroup(
			stagingGroup.getGroupId(), group.getGroupId(),
			stagingGroup.getNameMap(), stagingGroup.getDescriptionMap(),
			stagingGroup.getType(), stagingGroup.isManualMembership(),
			stagingGroup.getMembershipRestriction(),
			stagingGroup.getFriendlyURL(), stagingGroup.isInheritContent(),
			stagingGroup.isActive(),
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = GroupParentException.MustNotBeOwnParent.class)
	public void testSelectOwnGroupAsParentSite() throws Exception {
		Group group = GroupTestUtil.addGroup();

		GroupLocalServiceUtil.updateGroup(
			group.getGroupId(), group.getGroupId(), group.getNameMap(),
			group.getDescriptionMap(), group.getType(),
			group.isManualMembership(), group.getMembershipRestriction(),
			group.getFriendlyURL(), group.isInheritContent(), group.isActive(),
			ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testSubsites() throws Exception {
		Group group1 = GroupTestUtil.addGroup();

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId());

		Group group111 = GroupTestUtil.addGroup(group11.getGroupId());

		Assert.assertTrue(group1.isRoot());
		Assert.assertFalse(group11.isRoot());
		Assert.assertFalse(group111.isRoot());
		Assert.assertEquals(group1.getGroupId(), group11.getParentGroupId());
		Assert.assertEquals(group11.getGroupId(), group111.getParentGroupId());
	}

	@Test
	public void testUpdateAvailableLocales() throws Exception {
		Group group = GroupTestUtil.addGroup();

		List<Locale> availableLocales = Arrays.asList(
			LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US);

		group = GroupTestUtil.updateDisplaySettings(
			group.getGroupId(), availableLocales, null);

		Assert.assertEquals(
			new HashSet<>(availableLocales),
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
		Group group1 = GroupTestUtil.addGroup();

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId());

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
		Group group1 = GroupTestUtil.addGroup();

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId());

		User user = UserTestUtil.addUser(null, group11.getGroupId());

		giveSiteAdminRole(user, group11);

		testGroup(
			user, group1, group11, null, false, true, false, true, false, true,
			true);
	}

	@Test
	public void testValidChangeAvailableLanguageIds() throws Exception {
		testUpdateDisplaySettings(
			Arrays.asList(LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US),
			Arrays.asList(LocaleUtil.SPAIN, LocaleUtil.US), null, false);
	}

	@Test
	public void testValidChangeDefaultLanguageId() throws Exception {
		testUpdateDisplaySettings(
			Arrays.asList(LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US),
			Arrays.asList(LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US),
			LocaleUtil.GERMANY, false);
	}

	protected Group addGroup(
			boolean site, boolean layout, boolean layoutPrototype)
		throws Exception {

		if (site) {
			return GroupTestUtil.addGroup();
		}
		else if (layout) {
			Group group = GroupTestUtil.addGroup();

			Layout scopeLayout = LayoutTestUtil.addLayout(group);

			Map<Locale, String> nameMap = new HashMap<>();

			nameMap.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

			return GroupLocalServiceUtil.addGroup(
				TestPropsValues.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID, Layout.class.getName(),
				scopeLayout.getPlid(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
				nameMap, (Map<Locale, String>)null, 0, true,
				GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false,
				true, null);
		}
		else if (layoutPrototype) {
			Group group = GroupTestUtil.addGroup();

			group.setClassName(LayoutPrototype.class.getName());

			return group;
		}
		else {
			return GroupTestUtil.addGroup();
		}
	}

	protected Locale getLocale() {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		return themeDisplay.getLocale();
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
			group1 = GroupTestUtil.addGroup();
		}

		if (group11 == null) {
			group11 = GroupTestUtil.addGroup(group1.getGroupId());
		}

		if (group111 == null) {
			group111 = GroupTestUtil.addGroup(group11.getGroupId());
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
					GroupConstants.DEFAULT_PARENT_GROUP_ID, serviceContext);

				Assert.fail(
					"The user should not be able to add top level sites");
			}
			catch (PrincipalException pe) {
			}

			try {
				GroupTestUtil.addGroup(group1.getGroupId(), serviceContext);

				Assert.assertTrue(
					"The user should not be able to add this site",
					hasManageSubsitePermisionOnGroup1 || hasManageSite1);
			}
			catch (PrincipalException pe) {
				Assert.assertFalse(
					"The user should be able to add this site",
					hasManageSubsitePermisionOnGroup1 || hasManageSite1);
			}

			try {
				GroupTestUtil.addGroup(group11.getGroupId(), serviceContext);

				Assert.assertTrue(
					"The user should not be able to add this site",
					hasManageSubsitePermisionOnGroup11 || hasManageSite1);
			}
			catch (PrincipalException pe) {
				Assert.assertFalse(
					"The user should be able to add this site",
					hasManageSubsitePermisionOnGroup11 || hasManageSite1);
			}

			try {
				GroupTestUtil.addGroup(group111.getGroupId(), serviceContext);

				Assert.assertTrue(
					"The user should not be able to add this site",
					hasManageSubsitePermisionOnGroup111 || hasManageSite1);
			}
			catch (PrincipalException pe) {
				Assert.assertFalse(
					"The user should be able to add this site",
					hasManageSubsitePermisionOnGroup111 || hasManageSite1);
			}
		}

		if (updateGroup) {
			try {
				GroupServiceUtil.updateGroup(group1.getGroupId(), "");

				Assert.assertTrue(
					"The user should not be able to update this site",
					hasManageSite1);
			}
			catch (PrincipalException pe) {
				Assert.assertFalse(
					"The user should be able to update this site",
					hasManageSite1);
			}

			try {
				GroupServiceUtil.updateGroup(group11.getGroupId(), "");

				Assert.assertTrue(
					"The user should not be able to update this site",
					hasManageSubsitePermisionOnGroup1 || hasManageSite1 ||
						hasManageSite11);
			}
			catch (PrincipalException pe) {
				Assert.assertFalse(
					"The user should be able to update this site",
					hasManageSubsitePermisionOnGroup1 || hasManageSite1 ||
						hasManageSite11);
			}

			try {
				GroupServiceUtil.updateGroup(group111.getGroupId(), "");

				Assert.assertTrue(
					"The user should not be able to update this site",
					hasManageSubsitePermisionOnGroup11 || hasManageSite1);
			}
			catch (PrincipalException pe) {
				Assert.assertFalse(
					"The user should be able to update this site",
					hasManageSubsitePermisionOnGroup1 || hasManageSite1);
			}
		}
	}

	protected void testSelectableParentSites(boolean staging) throws Exception {
		Group group = GroupTestUtil.addGroup();

		Assert.assertTrue(group.isRoot());

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("site", Boolean.TRUE);

		List<Long> excludedGroupIds = new ArrayList<>();

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

			Assert.assertNotEquals(
				"A group cannot be its own parent", group.getGroupId(),
				selectableGroupId);

			if (staging) {
				Assert.assertNotEquals(
					"A group cannot have its live group as parent",
					group.getLiveGroupId(), selectableGroupId);
			}
		}
	}

	protected void testUpdateDisplaySettings(
			Collection<Locale> portalAvailableLocales,
			Collection<Locale> groupAvailableLocales, Locale groupDefaultLocale,
			boolean expectFailure)
		throws Exception {

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		CompanyTestUtil.resetCompanyLocales(
			TestPropsValues.getCompanyId(), portalAvailableLocales,
			LocaleUtil.getDefault());

		Group group = GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		try {
			GroupTestUtil.updateDisplaySettings(
				group.getGroupId(), groupAvailableLocales, groupDefaultLocale);

			Assert.assertFalse(expectFailure);
		}
		catch (LocaleException le) {
			Assert.assertTrue(expectFailure);
		}
		finally {
			CompanyTestUtil.resetCompanyLocales(
				TestPropsValues.getCompanyId(), availableLocales,
				LocaleUtil.getDefault());
		}
	}

	@DeleteAfterTestRun
	private Group _group;

}