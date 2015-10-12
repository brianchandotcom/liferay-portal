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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class TeamFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();

		GroupLocalServiceUtil.addUserGroup(_user.getUserId(), _group);

		_organization = OrganizationTestUtil.addOrganization();
		_organizationUser = UserTestUtil.addUser();

		OrganizationLocalServiceUtil.addUserOrganization(
			_organizationUser.getUserId(), _organization);

		_userGroup = UserGroupTestUtil.addUserGroup();

		UserGroupLocalServiceUtil.addUserUserGroup(
			_user.getUserId(), _userGroup);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);
		UserLocalServiceUtil.deleteUser(_user);

		UserLocalServiceUtil.deleteUser(_organizationUser);

		OrganizationLocalServiceUtil.deleteOrganization(_organization);

		UserGroupLocalServiceUtil.deleteUserGroup(_userGroup);
	}

	@Before
	public void setUp() throws Exception {
		_inheritedUserGroupsParams = new LinkedHashMap<>();

		_inheritedUserGroupsParams.put("inherit", Boolean.TRUE);
		_inheritedUserGroupsParams.put(
			"usersGroups",
			new Long[] {
				_group.getGroupId(), _organization.getGroupId(),
				_userGroup.getGroupId()
			});

		_roleId = RoleTestUtil.addRegularRole(_group.getGroupId());

		_inheritedUserRolesParams = new LinkedHashMap<>();

		_inheritedUserRolesParams.put("inherit", Boolean.TRUE);
		_inheritedUserRolesParams.put("usersRoles", _roleId);
	}

	@After
	public void tearDown() throws Exception {
		RoleLocalServiceUtil.deleteRole(_roleId);

		GroupLocalServiceUtil.clearOrganizationGroups(
			_organization.getOrganizationId());
		GroupLocalServiceUtil.clearUserGroupGroups(_userGroup.getUserGroupId());
	}

	@Test
	public void testGetRecursiveUserTeams() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		Team directTeam = TeamLocalServiceUtil.addTeam(
			_user.getUserId(), _group.getGroupId(), "DirectTeam", "",
			serviceContext);

		TeamLocalServiceUtil.addUserTeam(
			_user.getUserId(), directTeam.getTeamId());

		List<Team> justDirectTeam = TeamLocalServiceUtil.getRecursiveUserTeams(
			_user.getUserId(), _group.getGroupId());

		Assert.assertEquals(1, justDirectTeam.size());
		Assert.assertTrue(justDirectTeam.contains(directTeam));

		Team groupTeam = TeamLocalServiceUtil.addTeam(
			_user.getUserId(), _group.getGroupId(), "GroupTeam", "",
			serviceContext);

		TeamLocalServiceUtil.addUserGroupTeam(
			_userGroup.getUserGroupId(), groupTeam.getTeamId());

		List<Team> directAndGroupTeams =
			TeamLocalServiceUtil.getRecursiveUserTeams(
				_user.getUserId(), _group.getGroupId());

		Assert.assertEquals(2, directAndGroupTeams.size());
		Assert.assertTrue(directAndGroupTeams.contains(directTeam));
		Assert.assertTrue(directAndGroupTeams.contains(groupTeam));
	}

	private static Group _group;
	private static Organization _organization;
	private static User _organizationUser;
	private static User _user;
	private static UserGroup _userGroup;

	private LinkedHashMap<String, Object> _inheritedUserGroupsParams;
	private LinkedHashMap<String, Object> _inheritedUserRolesParams;
	private long _roleId;

}