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

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.TestPropsValues;

import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author László Csontos
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class RoleLocalServiceTest {

	@BeforeClass
	public static void setUpClass() {
		IndexerRegistryUtil.unregister(Organization.class.getName());
	}

	@Test
	public void testGetTeamRoleMapWithExclusion() throws Exception {
		Object[] objects = prepareOrganization();

		Organization organization = (Organization)objects[0];
		Team team = (Team)objects[1];

		Map<Team, Role> teamRoleMap = RoleLocalServiceUtil.getTeamRoleMap(
			organization.getGroupId());

		Role role = teamRoleMap.get(team);

		Assert.assertNotNull(role);

		long[] excludedRoleIds = new long[] {role.getRoleId()};

		List<Role> roles = RoleLocalServiceUtil.getTeamRoles(
			organization.getGroupId(), excludedRoleIds);

		Assert.assertNotNull(roles);
		Assert.assertTrue(roles.isEmpty());
	}

	@Test(expected = NoSuchGroupException.class)
	public void testGetTeamRoleMapWithInvalidGroupId() throws Exception {
		RoleLocalServiceUtil.getTeamRoleMap(0L);
	}

	@Test
	public void testGetTeamRoleMapWithOtherGroupId() throws Exception {
		Object[] objects1 = prepareOrganization();
		Object[] objects2 = prepareOrganization();

		Organization organization = (Organization)objects1[0];
		Team team = (Team)objects2[1];

		Map<Team, Role> teamRoleMap = RoleLocalServiceUtil.getTeamRoleMap(
			organization.getGroupId());

		doTestGetTeamRoleMap(teamRoleMap, team, false);
	}

	@Test
	public void testGetTeamRoleMapWithOwnGroupId() throws Exception {
		Object[] objects = prepareOrganization();

		Organization organization = (Organization)objects[0];
		Team team = (Team)objects[1];

		Map<Team, Role> teamRoleMap = RoleLocalServiceUtil.getTeamRoleMap(
			organization.getGroupId());

		doTestGetTeamRoleMap(teamRoleMap, team, true);
	}

	@Test
	public void testGetTeamRoleMapWithParentGroupId() throws Exception {
		Object[] objects = prepareOrganization();

		Organization organization = (Organization)objects[0];
		Team team = (Team)objects[1];

		Layout layout = LayoutTestUtil.addLayout(
			organization.getGroupId(), ServiceTestUtil.randomString());

		Group group = GroupTestUtil.addGroup(
			TestPropsValues.getUserId(), organization.getGroupId(), layout);

		Map<Team, Role> teamRoleMap = RoleLocalServiceUtil.getTeamRoleMap(
			group.getGroupId());

		doTestGetTeamRoleMap(teamRoleMap, team, true);
	}

	protected void doTestGetTeamRoleMap(
		Map<Team, Role> teamRoleMap, Team team, boolean shouldContain) {

		Assert.assertNotNull(teamRoleMap);
		Assert.assertFalse(teamRoleMap.isEmpty());

		if (shouldContain) {
			Assert.assertTrue(teamRoleMap.containsKey(team));

			Role role = teamRoleMap.get(team);

			Assert.assertEquals(role.getType(), RoleConstants.TYPE_PROVIDER);
		}
		else {
			Assert.assertFalse(teamRoleMap.containsKey(team));
		}
	}

	protected Object[] prepareOrganization() throws Exception {
		User user = TestPropsValues.getUser();

		Organization organization =
			OrganizationLocalServiceUtil.addOrganization(
				user.getUserId(),
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
				ServiceTestUtil.randomString(), false);

		Team team = TeamLocalServiceUtil.addTeam(
			user.getUserId(), organization.getGroupId(),
			ServiceTestUtil.randomString(), null);

		return new Object[] {organization, team};
	}

}