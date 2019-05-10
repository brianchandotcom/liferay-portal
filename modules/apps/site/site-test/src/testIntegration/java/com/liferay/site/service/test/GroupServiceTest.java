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

package com.liferay.site.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.GroupFriendlyURLException;
import com.liferay.portal.kernel.exception.GroupParentException;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.NoSuchResourcePermissionException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
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
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 * @author Julio Camarero
 * @author Roberto Díaz
 * @author Sergio González
 */
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class GroupServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testAddCompanyStagingGroup() throws Exception {
		Group companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute("staging", Boolean.TRUE);

		_group = _groupService.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, companyGroup.getGroupId(),
			companyGroup.getNameMap(), companyGroup.getDescriptionMap(),
			companyGroup.getType(), companyGroup.isManualMembership(),
			companyGroup.getMembershipRestriction(),
			companyGroup.getFriendlyURL(), false, companyGroup.isActive(),
			serviceContext);

		Assert.assertTrue(_group.isCompanyStagingGroup());

		Assert.assertEquals(companyGroup.getGroupId(), _group.getLiveGroupId());
	}

	@Test
	public void testAddParentToStagedGroup() throws Exception {
		Group parentGroup = GroupTestUtil.addGroup();

		Group childGroup = GroupTestUtil.addGroup();

		try {
			GroupTestUtil.enableLocalStaging(childGroup);

			childGroup = _groupService.updateGroup(
				childGroup.getGroupId(), parentGroup.getGroupId(),
				childGroup.getNameMap(), childGroup.getDescriptionMap(),
				childGroup.getType(), childGroup.isManualMembership(),
				childGroup.getMembershipRestriction(),
				childGroup.getFriendlyURL(), childGroup.isInheritContent(),
				childGroup.isActive(), null);

			Group stagingGroup = childGroup.getStagingGroup();

			Assert.assertEquals(
				parentGroup.getGroupId(), stagingGroup.getParentGroupId());
		}
		finally {
			_groupLocalService.deleteGroup(childGroup);

			_groupLocalService.deleteGroup(parentGroup);
		}
	}

	@Test
	public void testAddStagedParentToStagedGroup() throws Exception {
		Group parentGroup = GroupTestUtil.addGroup();

		Group childGroup = GroupTestUtil.addGroup();

		try {
			GroupTestUtil.enableLocalStaging(childGroup);

			GroupTestUtil.enableLocalStaging(parentGroup);

			childGroup = _groupService.updateGroup(
				childGroup.getGroupId(), parentGroup.getGroupId(),
				childGroup.getNameMap(), childGroup.getDescriptionMap(),
				childGroup.getType(), childGroup.isManualMembership(),
				childGroup.getMembershipRestriction(),
				childGroup.getFriendlyURL(), childGroup.isInheritContent(),
				childGroup.isActive(), null);

			Group childGroupStagingGroup = childGroup.getStagingGroup();

			Assert.assertEquals(
				parentGroup.getGroupId(),
				childGroupStagingGroup.getParentGroupId());
		}
		finally {
			_groupLocalService.deleteGroup(childGroup);

			_groupLocalService.deleteGroup(parentGroup);
		}
	}

	@Test
	public void testDeleteGroupRemovesSharedPortletPreferences()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		PortletPreferences portletPreferences =
			_portletPreferencesLocalService.addPortletPreferences(
				group.getCompanyId(), group.getGroupId(),
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				PortletKeys.PREFS_PLID_SHARED, RandomTestUtil.randomString(),
				null, null);

		_groupService.deleteGroup(group.getGroupId());

		Assert.assertNull(
			"Deleting the group should also delete layout type portlet " +
				"preferences that do no belong to a single layout",
			_portletPreferencesLocalService.fetchPortletPreferences(
				portletPreferences.getPortletPreferencesId()));
	}

	@Test(expected = NoSuchResourcePermissionException.class)
	public void testDeleteGroupWithStagingGroupRemovesStagingResource()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		GroupTestUtil.enableLocalStaging(group);

		Assert.assertTrue(group.hasStagingGroup());

		Group stagingGroup = group.getStagingGroup();

		_groupService.deleteGroup(group.getGroupId());

		Role role = _roleLocalService.getRole(
			stagingGroup.getCompanyId(), RoleConstants.OWNER);

		_resourcePermissionLocalService.getResourcePermission(
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
			_userGroupRoleLocalService.getUserGroupRolesByGroup(
				stagingGroup.getGroupId());

		int stagingUserGroupRolesCount = stagingUserGroupRoles.size();

		Assert.assertEquals(1, stagingUserGroupRolesCount);

		_groupService.deleteGroup(group.getGroupId());

		stagingUserGroupRoles =
			_userGroupRoleLocalService.getUserGroupRolesByGroup(
				stagingGroup.getGroupId());

		stagingUserGroupRolesCount = stagingUserGroupRoles.size();

		Assert.assertEquals(0, stagingUserGroupRolesCount);
	}

	@Test
	public void testDeleteOrganizationSiteOnlyRemovesSiteRoles()
		throws Exception {

		Organization organization = _organizationLocalService.addOrganization(
			TestPropsValues.getUserId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(), true);

		Group organizationSite = _groupLocalService.getOrganizationGroup(
			TestPropsValues.getCompanyId(), organization.getOrganizationId());

		organizationSite.setManualMembership(true);

		User user = UserTestUtil.addOrganizationOwnerUser(organization);

		_userLocalService.addGroupUser(
			organizationSite.getGroupId(), user.getUserId());
		_userLocalService.addOrganizationUsers(
			organization.getOrganizationId(), new long[] {user.getUserId()});

		Role siteRole = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		_userGroupRoleLocalService.addUserGroupRoles(
			user.getUserId(), organizationSite.getGroupId(),
			new long[] {siteRole.getRoleId()});

		_groupService.deleteGroup(organizationSite.getGroupId());

		Assert.assertEquals(
			1,
			_userGroupRoleLocalService.getUserGroupRolesCount(
				user.getUserId(), organizationSite.getGroupId()));

		_userLocalService.deleteUser(user);

		_organizationLocalService.deleteOrganization(organization);
	}

	@Test
	public void testDeleteSite() throws Exception {
		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTagsCount = _assetTagLocalService.getGroupTagsCount(
			group.getGroupId());

		_assetTagLocalService.addTag(
			TestPropsValues.getUserId(), group.getGroupId(),
			RandomTestUtil.randomString(), serviceContext);

		Assert.assertEquals(
			initialTagsCount + 1,
			_assetTagLocalService.getGroupTagsCount(group.getGroupId()));

		_groupService.deleteGroup(group.getGroupId());

		Assert.assertEquals(
			initialTagsCount,
			_assetTagLocalService.getGroupTagsCount(group.getGroupId()));
	}

	@Test
	public void testFindGroupByDescription() throws Exception {
		_group = GroupTestUtil.addGroup(GroupConstants.DEFAULT_PARENT_GROUP_ID);

		Assert.assertEquals(
			1,
			_groupService.searchCount(
				TestPropsValues.getCompanyId(), null,
				_group.getDescription(getLocale()),
				new String[] {
					"manualMembership:true:boolean", "site:true:boolean"
				}));
	}

	@Test
	public void testFindGroupByDescriptionWithSpaces() throws Exception {
		_group = GroupTestUtil.addGroup(GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_group.setDescription(
			RandomTestUtil.randomString() + StringPool.SPACE +
				RandomTestUtil.randomString());

		_groupLocalService.updateGroup(_group);

		Assert.assertEquals(
			1,
			_groupService.searchCount(
				TestPropsValues.getCompanyId(), null,
				_group.getDescription(getLocale()),
				new String[] {
					"manualMembership:true:boolean", "site:true:boolean"
				}));
	}

	@Test
	public void testFindGroupByName() throws Exception {
		_group = GroupTestUtil.addGroup(GroupConstants.DEFAULT_PARENT_GROUP_ID);

		Assert.assertEquals(
			1,
			_groupService.searchCount(
				TestPropsValues.getCompanyId(), _group.getName(getLocale()),
				null,
				new String[] {
					"manualMembership:true:boolean", "site:true:boolean"
				}));
	}

	@Test
	public void testFindGroupByNameWithSpaces() throws Exception {
		_group = GroupTestUtil.addGroup(GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_group.setName(
			RandomTestUtil.randomString() + StringPool.SPACE +
				RandomTestUtil.randomString());

		_groupLocalService.updateGroup(_group);

		Assert.assertEquals(
			1,
			_groupService.searchCount(
				TestPropsValues.getCompanyId(), _group.getName(getLocale()),
				null,
				new String[] {
					"manualMembership:true:boolean", "site:true:boolean"
				}));
	}

	@Test
	public void testFindGroupByRole() throws Exception {
		_group = GroupTestUtil.addGroup(GroupConstants.DEFAULT_PARENT_GROUP_ID);

		long roleId = RoleTestUtil.addGroupRole(_group.getGroupId());

		String[] groupParams = {
			"groupsRoles:" + String.valueOf(roleId) + ":long",
			"site:true:boolean"
		};

		Assert.assertEquals(
			1,
			_groupService.searchCount(
				TestPropsValues.getCompanyId(), null, null, groupParams));

		List<Group> groups = _groupService.search(
			TestPropsValues.getCompanyId(), null, null, groupParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(groups.toString(), 1, groups.size());
		Assert.assertEquals(_group, groups.get(0));

		Assert.assertEquals(1, _groupLocalService.getRoleGroupsCount(roleId));

		groups = _groupLocalService.getRoleGroups(roleId);

		Assert.assertEquals(groups.toString(), 1, groups.size());
		Assert.assertEquals(_group, groups.get(0));
	}

	@Test
	public void testFindGuestGroupByCompanyName() throws Exception {
		Assert.assertEquals(
			1,
			_groupService.searchCount(
				TestPropsValues.getCompanyId(), "liferay%", null,
				new String[] {
					"manualMembership:true:boolean", "site:true:boolean"
				}));
	}

	@Test
	public void testFindGuestGroupByCompanyNameCapitalized() throws Exception {
		Assert.assertEquals(
			1,
			_groupService.searchCount(
				TestPropsValues.getCompanyId(), "Liferay%", null,
				new String[] {
					"manualMembership:true:boolean", "site:true:boolean"
				}));
	}

	@Test
	public void testFindNonexistentGroup() throws Exception {
		Assert.assertEquals(
			0,
			_groupService.searchCount(
				TestPropsValues.getCompanyId(), "cabina14", null,
				new String[] {
					"manualMembership:true:boolean", "site:true:boolean"
				}));
	}

	@Test
	public void testFriendlyURLDefaults() throws Exception {
		_group = GroupTestUtil.addGroup();

		long companyId = _group.getCompanyId();

		String defaultNewGroupFriendlyURL =
			StringPool.SLASH +
				_friendlyURLNormalizer.normalize(
					_group.getName(LocaleUtil.getDefault()));

		Assert.assertNotNull(
			_groupLocalService.fetchFriendlyURLGroup(
				companyId, defaultNewGroupFriendlyURL));

		_groupService.updateFriendlyURL(_group.getGroupId(), null);

		Assert.assertNull(
			_groupLocalService.fetchFriendlyURLGroup(
				companyId, defaultNewGroupFriendlyURL));

		String defaultFriendlyURL = "/group-" + _group.getGroupId();

		Assert.assertNotNull(
			_groupLocalService.fetchFriendlyURLGroup(
				companyId, defaultFriendlyURL));

		_groupService.updateFriendlyURL(
			_group.getGroupId(),
			StringPool.SLASH + RandomTestUtil.randomString());

		Group group = GroupTestUtil.addGroup();

		try {
			_groupService.updateFriendlyURL(
				group.getGroupId(), defaultFriendlyURL);

			_groupService.updateFriendlyURL(_group.getGroupId(), null);

			Assert.assertNotNull(
				_groupLocalService.fetchFriendlyURLGroup(
					companyId, defaultFriendlyURL + "-1"));
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test(expected = GroupFriendlyURLException.class)
	public void testFriendlyURLSetToGroupId() throws Exception {
		_group = GroupTestUtil.addGroup();

		String friendlyURL = "/" + _group.getGroupId();

		_groupService.updateFriendlyURL(_group.getGroupId(), friendlyURL);
	}

	@Test(expected = GroupFriendlyURLException.class)
	public void testFriendlyURLSetToRandomLong() throws Exception {
		_group = GroupTestUtil.addGroup();

		String friendlyURL = "/" + RandomTestUtil.nextLong();

		_groupService.updateFriendlyURL(_group.getGroupId(), friendlyURL);
	}

	@Test
	public void testGetGlobalSiteDefaultLocale() throws Exception {
		_group = GroupTestUtil.addGroup();

		Company company = _companyLocalService.getCompany(
			_group.getCompanyId());

		Assert.assertEquals(
			company.getLocale(),
			_portal.getSiteDefaultLocale(company.getGroupId()));
	}

	@Test
	public void testGetGlobalSiteDefaultLocaleWhenCompanyLocaleModified()
		throws Exception {

		_group = GroupTestUtil.addGroup();

		Company company = _companyLocalService.getCompany(
			_group.getCompanyId());

		User defaultUser = company.getDefaultUser();

		String languageId = defaultUser.getLanguageId();

		try {
			defaultUser.setLanguageId(
				_language.getLanguageId(LocaleUtil.BRAZIL));

			defaultUser = _userLocalService.updateUser(defaultUser);

			Assert.assertEquals(
				LocaleUtil.BRAZIL,
				_portal.getSiteDefaultLocale(company.getGroupId()));
		}
		finally {
			defaultUser.setLanguageId(languageId);

			_userLocalService.updateUser(defaultUser);
		}
	}

	@Test
	public void testGetGroupsLikeName() throws Exception {
		List<Group> allChildGroups = new ArrayList<>();
		Group parentGroup = GroupTestUtil.addGroup();

		List<Group> allGroups = new ArrayList<>(
			_groupLocalService.getGroups(
				TestPropsValues.getCompanyId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID, true));

		try {
			String name = RandomTestUtil.randomString(10);

			long parentGroupId = parentGroup.getGroupId();

			List<Group> likeNameChildGroups = new ArrayList<>();

			for (int i = 0; i < 10; i++) {
				Group group = GroupTestUtil.addGroup(parentGroupId);

				group.setName(name + i);

				group = _groupLocalService.updateGroup(group);

				likeNameChildGroups.add(group);
			}

			allChildGroups.addAll(likeNameChildGroups);
			allChildGroups.add(GroupTestUtil.addGroup(parentGroupId));
			allChildGroups.add(GroupTestUtil.addGroup(parentGroupId));
			allChildGroups.add(GroupTestUtil.addGroup(parentGroupId));

			allGroups.addAll(allChildGroups);

			assertExpectedGroups(
				likeNameChildGroups, parentGroupId, name + "%");
			assertExpectedGroups(
				likeNameChildGroups, parentGroupId,
				StringUtil.toLowerCase(name) + "%");
			assertExpectedGroups(
				likeNameChildGroups, parentGroupId,
				StringUtil.toUpperCase(name) + "%");
			assertExpectedGroups(
				likeNameChildGroups, GroupConstants.ANY_PARENT_GROUP_ID,
				name + "%");
			assertExpectedGroups(allChildGroups, parentGroupId, null);
			assertExpectedGroups(allChildGroups, parentGroupId, "");
			assertExpectedGroups(
				allGroups, GroupConstants.ANY_PARENT_GROUP_ID, "");
		}
		finally {
			for (Group childGroup : allChildGroups) {
				GroupTestUtil.deleteGroup(childGroup);
			}

			GroupTestUtil.deleteGroup(parentGroup);
		}
	}

	@Test
	public void testGetGtGroups() throws Exception {
		for (int i = 0; i < 10; i++) {
			_groups.add(GroupTestUtil.addGroup());
		}

		long parentGroupId = 0;
		int size = 5;

		List<Group> groups = _groupService.getGtGroups(
			0, TestPropsValues.getCompanyId(), parentGroupId, true, size);

		Assert.assertFalse(groups.isEmpty());
		Assert.assertEquals(groups.toString(), size, groups.size());

		Group lastGroup = groups.get(groups.size() - 1);

		groups = _groupService.getGtGroups(
			lastGroup.getGroupId(), TestPropsValues.getCompanyId(),
			parentGroupId, true, size);

		Assert.assertFalse(groups.isEmpty());
		Assert.assertEquals(groups.toString(), size, groups.size());

		long previousGroupId = 0;

		for (Group group : groups) {
			long groupId = group.getGroupId();

			Assert.assertTrue(groupId > lastGroup.getGroupId());
			Assert.assertTrue(groupId > previousGroupId);

			previousGroupId = groupId;
		}
	}

	@Test
	public void testGetSiteDefaultInheritLocale() throws Exception {
		_group = GroupTestUtil.addGroup();

		Company company = _companyLocalService.getCompany(
			_group.getCompanyId());

		Assert.assertEquals(
			company.getLocale(),
			_portal.getSiteDefaultLocale(_group.getGroupId()));
	}

	@Test
	public void testGetSiteDefaultInheritLocaleWhenCompanyLocaleModified()
		throws Exception {

		_group = GroupTestUtil.addGroup();

		Company company = _companyLocalService.getCompany(
			_group.getCompanyId());

		User defaultUser = company.getDefaultUser();

		String languageId = defaultUser.getLanguageId();

		try {
			defaultUser.setLanguageId(
				_language.getLanguageId(LocaleUtil.CHINA));

			defaultUser = _userLocalService.updateUser(defaultUser);

			Assert.assertEquals(
				LocaleUtil.CHINA,
				_portal.getSiteDefaultLocale(_group.getGroupId()));
		}
		finally {
			defaultUser.setLanguageId(languageId);

			_userLocalService.updateUser(defaultUser);
		}
	}

	@Test
	public void testGroupHasCurrentPageScopeDescriptiveName() throws Exception {
		_group = GroupTestUtil.addGroup();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = GroupTestUtil.addGroup();

		Group scopeGroup = addScopeGroup(group);

		themeDisplay.setPlid(scopeGroup.getClassPK());

		themeDisplay.setScopeGroupId(_group.getGroupId());

		String scopeDescriptiveName = scopeGroup.getScopeDescriptiveName(
			themeDisplay);

		Assert.assertTrue(
			scopeDescriptiveName,
			scopeDescriptiveName.contains("current-page"));

		_groupLocalService.deleteGroup(scopeGroup);

		_groupLocalService.deleteGroup(group);
	}

	@Test
	public void testGroupHasCurrentSiteScopeDescriptiveName() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		_group = GroupTestUtil.addGroup();

		themeDisplay.setScopeGroupId(_group.getGroupId());

		String scopeDescriptiveName = _group.getScopeDescriptiveName(
			themeDisplay);

		Assert.assertTrue(
			scopeDescriptiveName,
			scopeDescriptiveName.contains("current-site"));
	}

	@Test
	public void testGroupHasDefaultScopeDescriptiveName() throws Exception {
		_group = GroupTestUtil.addGroup();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = GroupTestUtil.addGroup();

		group.setClassName(LayoutPrototype.class.getName());

		themeDisplay.setScopeGroupId(_group.getGroupId());

		String scopeDescriptiveName = group.getScopeDescriptiveName(
			themeDisplay);

		Assert.assertTrue(
			scopeDescriptiveName, scopeDescriptiveName.contains("default"));

		_groupLocalService.deleteGroup(group);
	}

	@Test
	public void testGroupHasLocalizedName() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		_group = GroupTestUtil.addGroup();

		String scopeDescriptiveName = _group.getScopeDescriptiveName(
			themeDisplay);

		Assert.assertTrue(
			scopeDescriptiveName.equals(
				_group.getName(themeDisplay.getLocale())));
	}

	@Test
	public void testGroupIsChildSiteScopeLabel() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = GroupTestUtil.addGroup();

		themeDisplay.setScopeGroupId(group.getGroupId());

		Group subgroup = GroupTestUtil.addGroup(group.getGroupId());

		String scopeLabel = subgroup.getScopeLabel(themeDisplay);

		Assert.assertEquals("child-site", scopeLabel);

		_groupLocalService.deleteGroup(subgroup);

		_groupLocalService.deleteGroup(group);
	}

	@Test
	public void testGroupIsCurrentSiteScopeLabel() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		_group = GroupTestUtil.addGroup();

		themeDisplay.setScopeGroupId(_group.getGroupId());

		String scopeLabel = _group.getScopeLabel(themeDisplay);

		Assert.assertEquals("current-site", scopeLabel);
	}

	@Test
	public void testGroupIsGlobalScopeLabel() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		_group = GroupTestUtil.addGroup();

		Company company = _companyLocalService.getCompany(
			_group.getCompanyId());

		themeDisplay.setCompany(company);

		Group companyGroup = company.getGroup();

		String scopeLabel = companyGroup.getScopeLabel(themeDisplay);

		Assert.assertEquals("global", scopeLabel);
	}

	@Test
	public void testGroupIsPageScopeLabel() throws Exception {
		_group = GroupTestUtil.addGroup();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = GroupTestUtil.addGroup();

		Group scopeGroup = addScopeGroup(group);

		themeDisplay.setPlid(scopeGroup.getClassPK());

		themeDisplay.setScopeGroupId(_group.getGroupId());

		String scopeLabel = scopeGroup.getScopeLabel(themeDisplay);

		Assert.assertEquals("page", scopeLabel);

		_groupLocalService.deleteGroup(scopeGroup);

		_groupLocalService.deleteGroup(group);
	}

	@Test
	public void testGroupIsParentSiteScopeLabel() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = GroupTestUtil.addGroup();

		Group subgroup = GroupTestUtil.addGroup(group.getGroupId());

		themeDisplay.setScopeGroupId(subgroup.getGroupId());

		String scopeLabel = group.getScopeLabel(themeDisplay);

		Assert.assertEquals("parent-site", scopeLabel);

		_groupLocalService.deleteGroup(subgroup);

		_groupLocalService.deleteGroup(group);
	}

	@Test
	public void testGroupIsSiteScopeLabel() throws Exception {
		_group = GroupTestUtil.addGroup();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Group group = GroupTestUtil.addGroup();

		themeDisplay.setScopeGroupId(_group.getGroupId());

		String scopeLabel = group.getScopeLabel(themeDisplay);

		Assert.assertEquals("site", scopeLabel);

		_groupLocalService.deleteGroup(group);
	}

	@Test
	public void testIndividualResourcePermission() throws Exception {
		_group = GroupTestUtil.addGroup();

		int resourcePermissionsCount =
			_resourcePermissionLocalService.getResourcePermissionsCount(
				_group.getCompanyId(), Group.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(_group.getGroupId()));

		Assert.assertEquals(1, resourcePermissionsCount);
	}

	@Test
	public void testInheritLocalesByDefault() throws Exception {
		_group = GroupTestUtil.addGroup();

		Assert.assertTrue(_language.isInheritLocales(_group.getGroupId()));
		Assert.assertEquals(
			_language.getAvailableLocales(),
			_language.getAvailableLocales(_group.getGroupId()));
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
	public void testRemoveParentFromStagedGroup() throws Exception {
		Group parentGroup = GroupTestUtil.addGroup();

		Group childGroup = GroupTestUtil.addGroup();

		try {
			GroupTestUtil.enableLocalStaging(childGroup);

			_groupService.updateGroup(
				childGroup.getGroupId(), parentGroup.getGroupId(),
				childGroup.getNameMap(), childGroup.getDescriptionMap(),
				childGroup.getType(), childGroup.isManualMembership(),
				childGroup.getMembershipRestriction(),
				childGroup.getFriendlyURL(), childGroup.isInheritContent(),
				childGroup.isActive(), null);

			childGroup = _groupService.updateGroup(
				childGroup.getGroupId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
				childGroup.getNameMap(), childGroup.getDescriptionMap(),
				childGroup.getType(), childGroup.isManualMembership(),
				childGroup.getMembershipRestriction(),
				childGroup.getFriendlyURL(), childGroup.isInheritContent(),
				childGroup.isActive(), null);

			Group childGroupStagingGroup = childGroup.getStagingGroup();

			Assert.assertEquals(
				GroupConstants.DEFAULT_PARENT_GROUP_ID,
				childGroupStagingGroup.getParentGroupId());
		}
		finally {
			_groupLocalService.deleteGroup(childGroup);

			_groupLocalService.deleteGroup(parentGroup);
		}
	}

	@Test
	public void testRemoveStagedParentFromStagedGroup() throws Exception {
		Group parentGroup = GroupTestUtil.addGroup();

		Group childGroup = GroupTestUtil.addGroup();

		try {
			GroupTestUtil.enableLocalStaging(childGroup);

			GroupTestUtil.enableLocalStaging(parentGroup);

			_groupService.updateGroup(
				childGroup.getGroupId(), parentGroup.getGroupId(),
				childGroup.getNameMap(), childGroup.getDescriptionMap(),
				childGroup.getType(), childGroup.isManualMembership(),
				childGroup.getMembershipRestriction(),
				childGroup.getFriendlyURL(), childGroup.isInheritContent(),
				childGroup.isActive(), null);

			childGroup = _groupService.updateGroup(
				childGroup.getGroupId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
				childGroup.getNameMap(), childGroup.getDescriptionMap(),
				childGroup.getType(), childGroup.isManualMembership(),
				childGroup.getMembershipRestriction(),
				childGroup.getFriendlyURL(), childGroup.isInheritContent(),
				childGroup.isActive(), null);

			Group childGroupStagingGroup = childGroup.getStagingGroup();

			Assert.assertEquals(
				GroupConstants.DEFAULT_PARENT_GROUP_ID,
				childGroupStagingGroup.getParentGroupId());
		}
		finally {
			_groupLocalService.deleteGroup(childGroup);

			_groupLocalService.deleteGroup(parentGroup);
		}
	}

	@Test
	public void testScopes() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(group);

		Assert.assertFalse(layout.hasScopeGroup());

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(
			LocaleUtil.getDefault(), layout.getName(LocaleUtil.getDefault()));

		Group scope = _groupLocalService.addGroup(
			TestPropsValues.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap,
			(Map<Locale, String>)null, 0, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, true,
			null);

		Assert.assertFalse(scope.isRoot());
		Assert.assertEquals(scope.getParentGroupId(), group.getGroupId());

		_groupLocalService.deleteGroup(scope);

		_groupLocalService.deleteGroup(group);
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

		try {
			_groupService.updateGroup(
				group1.getGroupId(), group11.getGroupId(), group1.getNameMap(),
				group1.getDescriptionMap(), group1.getType(),
				group1.isManualMembership(), group1.getMembershipRestriction(),
				group1.getFriendlyURL(), group1.isInheritContent(),
				group1.isActive(), ServiceContextTestUtil.getServiceContext());
		}
		finally {
			_groupLocalService.deleteGroup(group11);

			_groupLocalService.deleteGroup(group1);
		}
	}

	@Test(expected = GroupParentException.MustNotHaveChildParent.class)
	public void testSelectLastChildGroupAsParentSite() throws Exception {
		Group group1 = GroupTestUtil.addGroup();

		Group group11 = GroupTestUtil.addGroup(group1.getGroupId());

		Group group111 = GroupTestUtil.addGroup(group11.getGroupId());

		Group group1111 = GroupTestUtil.addGroup(group111.getGroupId());

		try {
			_groupService.updateGroup(
				group1.getGroupId(), group1111.getGroupId(),
				group1.getNameMap(), group1.getDescriptionMap(),
				group1.getType(), group1.isManualMembership(),
				group1.getMembershipRestriction(), group1.getFriendlyURL(),
				group1.isInheritContent(), group1.isActive(),
				ServiceContextTestUtil.getServiceContext());
		}
		finally {
			_groupLocalService.deleteGroup(group1111);

			_groupLocalService.deleteGroup(group111);

			_groupLocalService.deleteGroup(group11);

			_groupLocalService.deleteGroup(group1);
		}
	}

	@Test(expected = GroupParentException.MustNotHaveStagingParent.class)
	public void testSelectLiveGroupAsParentSite() throws Exception {
		_group = GroupTestUtil.addGroup();

		GroupTestUtil.enableLocalStaging(_group);

		Assert.assertTrue(_group.hasStagingGroup());

		Group stagingGroup = _group.getStagingGroup();

		_groupService.updateGroup(
			stagingGroup.getGroupId(), _group.getGroupId(),
			stagingGroup.getNameMap(), stagingGroup.getDescriptionMap(),
			stagingGroup.getType(), stagingGroup.isManualMembership(),
			stagingGroup.getMembershipRestriction(),
			stagingGroup.getFriendlyURL(), stagingGroup.isInheritContent(),
			stagingGroup.isActive(),
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = GroupParentException.MustNotBeOwnParent.class)
	public void testSelectOwnGroupAsParentSite() throws Exception {
		_group = GroupTestUtil.addGroup();

		_groupService.updateGroup(
			_group.getGroupId(), _group.getGroupId(), _group.getNameMap(),
			_group.getDescriptionMap(), _group.getType(),
			_group.isManualMembership(), _group.getMembershipRestriction(),
			_group.getFriendlyURL(), _group.isInheritContent(),
			_group.isActive(), ServiceContextTestUtil.getServiceContext());
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

		_groupLocalService.deleteGroup(group111);

		_groupLocalService.deleteGroup(group11);

		_groupLocalService.deleteGroup(group1);
	}

	@Test
	public void testUpdateAvailableLocales() throws Exception {
		_group = GroupTestUtil.addGroup();

		List<Locale> availableLocales = Arrays.asList(
			LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US);

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), availableLocales, null);

		Assert.assertEquals(
			new HashSet<>(availableLocales),
			_language.getAvailableLocales(_group.getGroupId()));
	}

	@Test
	public void testUpdateDefaultLocale() throws Exception {
		_group = GroupTestUtil.addGroup();

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), null, LocaleUtil.SPAIN);

		Assert.assertEquals(
			LocaleUtil.SPAIN,
			_portal.getSiteDefaultLocale(_group.getGroupId()));
	}

	@Test
	public void testUpdateGroupParentFromStagedParentToStagedParentInStagedGroup()
		throws Exception {

		Group childGroup = GroupTestUtil.addGroup();
		Group parentGroup1 = GroupTestUtil.addGroup();
		Group parentGroup2 = GroupTestUtil.addGroup();

		try {
			GroupTestUtil.enableLocalStaging(childGroup);
			GroupTestUtil.enableLocalStaging(parentGroup1);
			GroupTestUtil.enableLocalStaging(parentGroup2);

			_groupService.updateGroup(
				childGroup.getGroupId(), parentGroup1.getGroupId(),
				childGroup.getNameMap(), childGroup.getDescriptionMap(),
				childGroup.getType(), childGroup.isManualMembership(),
				childGroup.getMembershipRestriction(),
				childGroup.getFriendlyURL(), childGroup.isInheritContent(),
				childGroup.isActive(), null);

			childGroup = _groupService.updateGroup(
				childGroup.getGroupId(), parentGroup2.getGroupId(),
				childGroup.getNameMap(), childGroup.getDescriptionMap(),
				childGroup.getType(), childGroup.isManualMembership(),
				childGroup.getMembershipRestriction(),
				childGroup.getFriendlyURL(), childGroup.isInheritContent(),
				childGroup.isActive(), null);

			Group childGroupStagingGroup = childGroup.getStagingGroup();

			Assert.assertEquals(
				parentGroup2.getGroupId(),
				childGroupStagingGroup.getParentGroupId());
		}
		finally {
			_groupLocalService.deleteGroup(childGroup);
			_groupLocalService.deleteGroup(parentGroup1);
			_groupLocalService.deleteGroup(parentGroup2);
		}
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

	protected Group addScopeGroup(Group group) throws Exception {
		Layout scopeLayout = LayoutTestUtil.addLayout(group);

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		return _groupLocalService.addGroup(
			TestPropsValues.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			Layout.class.getName(), scopeLayout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap,
			(Map<Locale, String>)null, 0, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, true,
			null);
	}

	protected void assertExpectedGroups(
			List<Group> expectedGroups, long parentGroupId, String nameSearch)
		throws Exception {

		List<Group> actualGroups = _groupService.getGroups(
			TestPropsValues.getCompanyId(), parentGroupId, nameSearch, true,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			actualGroups.toString(), expectedGroups.size(),
			actualGroups.size());
		Assert.assertTrue(
			actualGroups.toString(), actualGroups.containsAll(expectedGroups));

		Assert.assertEquals(
			expectedGroups.size(),
			_groupService.getGroupsCount(
				TestPropsValues.getCompanyId(), parentGroupId, nameSearch,
				true));
	}

	protected Locale getLocale() {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		return themeDisplay.getLocale();
	}

	protected void testSelectableParentSites(boolean staging) throws Exception {
		_group = GroupTestUtil.addGroup();

		Assert.assertTrue(_group.isRoot());

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("site", Boolean.TRUE);

		List<Long> excludedGroupIds = new ArrayList<>();

		excludedGroupIds.add(_group.getGroupId());

		if (staging) {
			GroupTestUtil.enableLocalStaging(_group);

			Assert.assertTrue(_group.hasStagingGroup());

			Group stagingGroup = _group.getStagingGroup();

			excludedGroupIds.add(stagingGroup.getGroupId());
		}

		params.put("excludedGroupIds", excludedGroupIds);

		List<Group> selectableGroups = _groupService.search(
			_group.getCompanyId(), null, StringPool.BLANK, params,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (Group selectableGroup : selectableGroups) {
			long selectableGroupId = selectableGroup.getGroupId();

			Assert.assertNotEquals(
				"A group cannot be its own parent", _group.getGroupId(),
				selectableGroupId);

			if (staging) {
				Assert.assertNotEquals(
					"A group cannot have its live group as parent",
					_group.getLiveGroupId(), selectableGroupId);
			}
		}
	}

	protected void testUpdateDisplaySettings(
			Collection<Locale> portalAvailableLocales,
			Collection<Locale> groupAvailableLocales, Locale groupDefaultLocale,
			boolean expectFailure)
		throws Exception {

		Set<Locale> availableLocales = _language.getAvailableLocales();

		CompanyTestUtil.resetCompanyLocales(
			TestPropsValues.getCompanyId(), portalAvailableLocales,
			LocaleUtil.getDefault());

		_group = GroupTestUtil.addGroup(GroupConstants.DEFAULT_PARENT_GROUP_ID);

		try {
			GroupTestUtil.updateDisplaySettings(
				_group.getGroupId(), groupAvailableLocales, groupDefaultLocale);

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

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FriendlyURLNormalizer _friendlyURLNormalizer;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

	@Inject
	private GroupService _groupService;

	@Inject
	private Language _language;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}