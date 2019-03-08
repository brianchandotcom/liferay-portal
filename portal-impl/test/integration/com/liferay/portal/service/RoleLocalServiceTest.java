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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.RoleNameException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.comparator.RoleRoleIdComparator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author László Csontos
 */
public class RoleLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		IndexerRegistryUtil.unregister(Organization.class.getName());
	}

	@Test(expected = RoleNameException.class)
	public void testAddRoleWithPlaceholderName() throws Exception {
		RoleTestUtil.addRole(
			RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE,
			RoleConstants.TYPE_REGULAR);
	}

	@Test
	public void testDeleteRole() throws Exception {
		_group = GroupTestUtil.addGroup();
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		UnicodeProperties typeSettingsProperties =
			_group.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"defaultSiteRoleIds", String.valueOf(_role.getRoleId()));

		GroupLocalServiceUtil.updateGroup(_group);

		RoleLocalServiceUtil.deleteRole(_role);

		_group = GroupLocalServiceUtil.getGroup(_group.getGroupId());

		typeSettingsProperties = _group.getTypeSettingsProperties();

		List<Long> defaultSiteRoleIds = ListUtil.toList(
			StringUtil.split(
				typeSettingsProperties.getProperty("defaultSiteRoleIds"), 0L));

		Assert.assertFalse(defaultSiteRoleIds.contains(_role.getRoleId()));
	}

	@Test
	public void testGetAssigneesTotalOrganizationRole() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_ORGANIZATION);
		_user = UserTestUtil.addUser();

		OrganizationLocalServiceUtil.addUserOrganization(
			_user.getUserId(), _organization);
		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			_user.getUserId(), _organization.getGroupId(),
			new long[] {_role.getRoleId()});

		Assert.assertEquals(
			1, RoleLocalServiceUtil.getAssigneesTotal(_role.getRoleId()));
	}

	@Test
	public void testGetAssigneesTotalRegularRole() throws Exception {
		_group = GroupTestUtil.addGroup();
		_organization = OrganizationTestUtil.addOrganization();
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);
		_user = UserTestUtil.addUser();
		_userGroup = UserGroupTestUtil.addUserGroup();

		RoleLocalServiceUtil.addUserRole(_user.getUserId(), _role);
		RoleLocalServiceUtil.addGroupRole(_group.getGroupId(), _role);
		RoleLocalServiceUtil.addGroupRole(_organization.getGroupId(), _role);
		RoleLocalServiceUtil.addGroupRole(_userGroup.getGroupId(), _role);

		Assert.assertEquals(
			4, RoleLocalServiceUtil.getAssigneesTotal(_role.getRoleId()));
	}

	@Test
	public void testGetAssigneesTotalSiteRole() throws Exception {
		_group = GroupTestUtil.addGroup();
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);
		_user = UserTestUtil.addUser();
		_userGroup = UserGroupTestUtil.addUserGroup();

		GroupLocalServiceUtil.addUserGroup(_user.getUserId(), _group);
		GroupLocalServiceUtil.addUserGroupGroup(
			_userGroup.getUserGroupId(), _group);

		long[] roleIds = {_role.getRoleId()};

		UserGroupGroupRoleLocalServiceUtil.addUserGroupGroupRoles(
			_userGroup.getGroupId(), _group.getGroupId(), roleIds);
		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			_user.getUserId(), _group.getGroupId(), roleIds);

		Assert.assertEquals(
			2, RoleLocalServiceUtil.getAssigneesTotal(_role.getRoleId()));
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Test
	public void testGetGroupRelatedRoles() throws Exception {
		Object[] objects = getOrganizationAndTeam();

		Organization organization = (Organization)objects[0];

		long companyId = organization.getCompanyId();

		long groupId = organization.getGroupId();

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		List<Role> actualRoles = RoleLocalServiceUtil.getGroupRelatedRoles(
			groupId);

		List<Role> allRoles = RoleLocalServiceUtil.getRoles(companyId);

		List<Role> expectedRoles = new ArrayList<>();

		for (Role role : allRoles) {
			int type = role.getType();

			if ((type == RoleConstants.TYPE_REGULAR) ||
				((type == RoleConstants.TYPE_ORGANIZATION) &&
				 group.isOrganization()) ||
				((type == RoleConstants.TYPE_SITE) &&
				 (group.isLayout() || group.isLayoutSetPrototype() ||
				  group.isSite()))) {

				expectedRoles.add(role);
			}
			else if ((type == RoleConstants.TYPE_PROVIDER) && role.isTeam()) {
				Team team = TeamLocalServiceUtil.getTeam(role.getClassPK());

				if (team.getGroupId() == groupId) {
					expectedRoles.add(role);
				}
			}
		}

		Comparator roleIdComparator = new RoleRoleIdComparator();

		Collections.sort(actualRoles, roleIdComparator);
		Collections.sort(expectedRoles, roleIdComparator);

		Assert.assertEquals(expectedRoles, actualRoles);
	}

	@Test
	public void testGetGroupRolesAndTeamRoles() throws Exception {
		Object[] organizationAndTeam = getOrganizationAndTeam();

		Organization organization = (Organization)organizationAndTeam[0];

		long companyId = organization.getCompanyId();
		long groupId = organization.getGroupId();

		int[] roleTypes = RoleConstants.TYPES_ORGANIZATION_AND_REGULAR;

		List<String> excludedRoleNames = new ArrayList<>();

		excludedRoleNames.add(RoleConstants.ADMINISTRATOR);
		excludedRoleNames.add(RoleConstants.GUEST);

		int count = RoleLocalServiceUtil.getGroupRolesAndTeamRolesCount(
			companyId, null, excludedRoleNames, roleTypes, 0, groupId);

		List<Role> actualRoles = RoleLocalServiceUtil.getGroupRolesAndTeamRoles(
			companyId, null, excludedRoleNames, roleTypes, 0, groupId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Stream<Role> expectedRolesStream = RoleLocalServiceUtil.getRoles(
			companyId
		).stream();

		expectedRolesStream = expectedRolesStream.filter(
			role -> !excludedRoleNames.contains(role.getName()));
		expectedRolesStream = expectedRolesStream.filter(
			role -> role.getType() != RoleConstants.TYPE_SITE);
		expectedRolesStream = expectedRolesStream.filter(
			role -> {
				if (role.getType() != RoleConstants.TYPE_PROVIDER) {
					return true;
				}

				if (!role.isTeam()) {
					return false;
				}

				Team team = TeamLocalServiceUtil.fetchTeam(role.getClassPK());

				if (team == null) {
					return false;
				}

				return team.getGroupId() == groupId;
			});

		List<Role> expectedRoles = expectedRolesStream.collect(
			Collectors.toList());

		Assert.assertEquals(expectedRoles.size(), count);

		actualRoles = new ArrayList(actualRoles);
		expectedRoles = new ArrayList(expectedRoles);

		Comparator roleIdComparator = new RoleRoleIdComparator();

		Collections.sort(actualRoles, roleIdComparator);
		Collections.sort(expectedRoles, roleIdComparator);

		Assert.assertEquals(expectedRoles, actualRoles);
	}

	@Test
	public void testGetGroupRolesAndTeamRolesWithKeyword() throws Exception {
		Object[] organizationAndTeam = getOrganizationAndTeam();

		Organization organization = (Organization)organizationAndTeam[0];
		Team team = (Team)organizationAndTeam[1];

		long companyId = organization.getCompanyId();
		long groupId = organization.getGroupId();

		int[] roleTypes = RoleConstants.TYPES_ORGANIZATION_AND_REGULAR_AND_SITE;

		List<String> excludedRoleNames = new ArrayList<>();

		excludedRoleNames.add(RoleConstants.GUEST);

		String keyword = RoleConstants.GUEST;

		int count = RoleLocalServiceUtil.getGroupRolesAndTeamRolesCount(
			companyId, keyword, excludedRoleNames, roleTypes, 0, groupId);

		Assert.assertEquals(0, count);

		List<Role> roles = RoleLocalServiceUtil.getGroupRolesAndTeamRoles(
			companyId, keyword, excludedRoleNames, roleTypes, 0, groupId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertTrue(roles.toString(), roles.isEmpty());

		keyword = team.getName();

		count = RoleLocalServiceUtil.getGroupRolesAndTeamRolesCount(
			companyId, keyword, excludedRoleNames, roleTypes, 0, groupId);

		Assert.assertEquals(1, count);

		roles = RoleLocalServiceUtil.getGroupRolesAndTeamRoles(
			companyId, keyword, excludedRoleNames, roleTypes, 0, groupId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		_role = roles.get(0);

		Assert.assertEquals(team.getTeamId(), _role.getClassPK());
	}

	@Test
	public void testGetTeamRoleMapWithExclusion() throws Exception {
		Object[] organizationAndTeam = getOrganizationAndTeam();

		Organization organization = (Organization)organizationAndTeam[0];
		Team team = (Team)organizationAndTeam[1];

		Map<Team, Role> teamRoleMap = RoleLocalServiceUtil.getTeamRoleMap(
			organization.getGroupId());

		Role role = teamRoleMap.get(team);

		Assert.assertNotNull(role);

		long[] excludedRoleIds = {role.getRoleId()};

		List<Role> roles = RoleLocalServiceUtil.getTeamRoles(
			organization.getGroupId(), excludedRoleIds);

		Assert.assertNotNull(roles);
		Assert.assertTrue(roles.toString(), roles.isEmpty());
	}

	@Test(expected = NoSuchGroupException.class)
	public void testGetTeamRoleMapWithInvalidGroupId() throws Exception {
		RoleLocalServiceUtil.getTeamRoleMap(0L);
	}

	@Test
	public void testGetTeamRoleMapWithOtherGroupId() throws Exception {
		Object[] organizationAndTeam1 = getOrganizationAndTeam();
		Object[] organizationAndTeam2 = getOrganizationAndTeam();

		Organization organization = (Organization)organizationAndTeam1[0];
		Team team = (Team)organizationAndTeam2[1];

		Map<Team, Role> teamRoleMap = RoleLocalServiceUtil.getTeamRoleMap(
			organization.getGroupId());

		testGetTeamRoleMap(teamRoleMap, team, false);
	}

	@Test
	public void testGetTeamRoleMapWithOwnGroupId() throws Exception {
		Object[] organizationAndTeam = getOrganizationAndTeam();

		Organization organization = (Organization)organizationAndTeam[0];
		Team team = (Team)organizationAndTeam[1];

		Map<Team, Role> teamRoleMap = RoleLocalServiceUtil.getTeamRoleMap(
			organization.getGroupId());

		testGetTeamRoleMap(teamRoleMap, team, true);
	}

	@Test
	public void testGetTeamRoleMapWithParentGroupId() throws Exception {
		Object[] organizationAndTeam = getOrganizationAndTeam();

		Organization organization = (Organization)organizationAndTeam[0];
		Team team = (Team)organizationAndTeam[1];

		Layout layout = LayoutTestUtil.addLayout(organization.getGroupId());

		Group group = GroupTestUtil.addGroup(
			TestPropsValues.getUserId(), organization.getGroupId(), layout);

		Map<Team, Role> teamRoleMap = RoleLocalServiceUtil.getTeamRoleMap(
			group.getGroupId());

		testGetTeamRoleMap(teamRoleMap, team, true);
	}

	@Test
	public void testGetUserTeamRoles() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		Team team = TeamLocalServiceUtil.addTeam(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new ServiceContext());

		List<Role> roles = RoleLocalServiceUtil.getUserTeamRoles(
			_user.getUserId(), _group.getGroupId());

		Assert.assertEquals(roles.toString(), 0, roles.size());

		TeamLocalServiceUtil.addUserTeam(_user.getUserId(), team.getTeamId());

		roles = RoleLocalServiceUtil.getUserTeamRoles(
			_user.getUserId(), _group.getGroupId());

		Role teamRole = team.getRole();

		Assert.assertEquals(roles.toString(), 1, roles.size());
		Assert.assertEquals(teamRole, roles.get(0));

		TeamLocalServiceUtil.deleteUserTeam(
			_user.getUserId(), team.getTeamId());

		_userGroup = UserGroupTestUtil.addUserGroup(_group.getGroupId());

		UserLocalServiceUtil.addUserGroupUser(
			_userGroup.getUserGroupId(), _user.getUserId());

		TeamLocalServiceUtil.addUserGroupTeam(
			_userGroup.getUserGroupId(), team.getTeamId());

		roles = RoleLocalServiceUtil.getUserTeamRoles(
			_user.getUserId(), _group.getGroupId());

		Assert.assertEquals(roles.toString(), 1, roles.size());
		Assert.assertEquals(teamRole, roles.get(0));

		TeamLocalServiceUtil.addUserTeam(_user.getUserId(), team.getTeamId());

		roles = RoleLocalServiceUtil.getUserTeamRoles(
			_user.getUserId(), _group.getGroupId());

		Assert.assertEquals(roles.toString(), 1, roles.size());
		Assert.assertEquals(teamRole, roles.get(0));
	}

	protected Object[] getOrganizationAndTeam() throws Exception {
		User user = TestPropsValues.getUser();

		_organization = OrganizationLocalServiceUtil.addOrganization(
			user.getUserId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(), false);

		Team team = TeamLocalServiceUtil.addTeam(
			user.getUserId(), _organization.getGroupId(),
			RandomTestUtil.randomString(), null, new ServiceContext());

		return new Object[] {_organization, team};
	}

	protected void testGetTeamRoleMap(
		Map<Team, Role> teamRoleMap, Team team, boolean hasTeam) {

		Assert.assertNotNull(teamRoleMap);
		Assert.assertFalse(teamRoleMap.toString(), teamRoleMap.isEmpty());

		if (hasTeam) {
			Assert.assertTrue(teamRoleMap.containsKey(team));

			Role role = teamRoleMap.get(team);

			Assert.assertEquals(role.getType(), RoleConstants.TYPE_PROVIDER);
		}
		else {
			Assert.assertFalse(teamRoleMap.containsKey(team));
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private User _user;

	@DeleteAfterTestRun
	private UserGroup _userGroup;

}