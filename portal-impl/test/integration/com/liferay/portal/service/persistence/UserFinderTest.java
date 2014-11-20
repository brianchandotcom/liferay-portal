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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.OrganizationTestUtil;
import com.liferay.portal.util.test.RoleTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserGroupTestUtil;
import com.liferay.portal.util.test.UserTestUtil;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jozsef Illes
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class UserFinderTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_group = GroupTestUtil.addGroup();
		_groupUser = UserTestUtil.addUser();

		GroupLocalServiceUtil.addUserGroup(_groupUser.getUserId(), _group);

		_organization = OrganizationTestUtil.addOrganization();
		_organizationUser = UserTestUtil.addUser();

		OrganizationLocalServiceUtil.addUserOrganization(
			_organizationUser.getUserId(), _organization);

		_userGroup = UserGroupTestUtil.addUserGroup();
		_userGroupUser = UserTestUtil.addUser();

		UserGroupLocalServiceUtil.addUserUserGroup(
			_userGroupUser.getUserId(), _userGroup);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);
		UserLocalServiceUtil.deleteUser(_groupUser);

		UserLocalServiceUtil.deleteUser(_organizationUser);

		OrganizationLocalServiceUtil.deleteOrganization(_organization);

		UserLocalServiceUtil.deleteUser(_userGroupUser);

		UserGroupLocalServiceUtil.deleteUserGroup(_userGroup);
	}

	@Before
	public void setUp() throws Exception {
		_inheritedUserGroupsParams = new LinkedHashMap<String, Object>();

		_inheritedUserGroupsParams.put("inherit", Boolean.TRUE);
		_inheritedUserGroupsParams.put(
			"usersGroups",
			new Long[] {
				_group.getGroupId(), _organization.getGroupId(),
				_userGroup.getGroupId()
			});

		_inheritedUserGroupsExpectedCount = UserFinderUtil.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserGroupsParams);

		_roleId = RoleTestUtil.addRegularRole(_group.getGroupId());

		_inheritedUserRolesParams = new LinkedHashMap<String, Object>();

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
	public void testCountByKeywordsWithInheritedGroups() throws Exception {
		int count = UserFinderUtil.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserGroupsParams);

		Assert.assertEquals(_inheritedUserGroupsExpectedCount, count);
	}

	@Test
	public void testCountByKeywordsWithInheritedRoles() throws Exception {
		int expectedCount = UserFinderUtil.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserRolesParams);

		RoleLocalServiceUtil.addGroupRole(_organization.getGroupId(), _roleId);
		RoleLocalServiceUtil.addGroupRole(_userGroup.getGroupId(), _roleId);

		int count = UserFinderUtil.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserRolesParams);

		Assert.assertEquals(expectedCount + 2, count);
	}

	@Test
	public void testCountByKeywordsWithInheritedRolesThroughSite()
		throws Exception {

		int expectedCount = UserFinderUtil.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserRolesParams);

		GroupLocalServiceUtil.addOrganizationGroup(
			_organization.getOrganizationId(), _group);
		GroupLocalServiceUtil.addUserGroupGroup(
			_userGroup.getUserGroupId(), _group);

		int count = UserFinderUtil.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserRolesParams);

		Assert.assertEquals(expectedCount + 2, count);
	}

	@Test
	public void testFindByKeywordsGroupUsers() throws Exception {
		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersGroups", _group.getGroupId());

		List<User> users = UserFinderUtil.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, params, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.contains(_groupUser));
	}

	@Test
	public void testFindByKeywordsOrganizationUsers() throws Exception {
		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersOrgs", _organization.getOrganizationId());

		List<User> users = UserFinderUtil.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, params, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.contains(_organizationUser));
	}

	@Test
	public void testFindByKeywordsUserGroupUsers() throws Exception {
		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersUserGroups", _userGroup.getUserGroupId());

		List<User> users = UserFinderUtil.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, params, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.contains(_userGroupUser));
	}

	@Test
	public void testFindByKeywordsWithInheritedGroups() throws Exception {
		List<User> users = UserFinderUtil.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserGroupsParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.contains(_groupUser));
		Assert.assertTrue(users.contains(_organizationUser));
		Assert.assertTrue(users.contains(_userGroupUser));
		Assert.assertTrue(users.contains(TestPropsValues.getUser()));
		Assert.assertEquals(_inheritedUserGroupsExpectedCount, users.size());
	}

	@Test
	public void testFindByKeywordsWithInheritedRoles() throws Exception {
		List<User> expectedUsers = UserFinderUtil.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserRolesParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		RoleLocalServiceUtil.addGroupRole(_organization.getGroupId(), _roleId);
		RoleLocalServiceUtil.addGroupRole(_userGroup.getGroupId(), _roleId);

		List<User> users = UserFinderUtil.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserRolesParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.contains(_groupUser));
		Assert.assertTrue(users.contains(_organizationUser));
		Assert.assertTrue(users.contains(_userGroupUser));
		Assert.assertTrue(users.contains(TestPropsValues.getUser()));
		Assert.assertEquals(expectedUsers.size() + 2, users.size());
	}

	@Test
	public void testFindByKeywordsWithInheritedRolesThroughSite()
		throws Exception {

		List<User> expectedUsers = UserFinderUtil.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserRolesParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		GroupLocalServiceUtil.addOrganizationGroup(
			_organization.getOrganizationId(), _group);
		GroupLocalServiceUtil.addUserGroupGroup(
			_userGroup.getUserGroupId(), _group);

		List<User> users = UserFinderUtil.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserRolesParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.contains(_groupUser));
		Assert.assertTrue(users.contains(_organizationUser));
		Assert.assertTrue(users.contains(_userGroupUser));
		Assert.assertTrue(users.contains(TestPropsValues.getUser()));
		Assert.assertEquals(expectedUsers.size() + 2, users.size());
	}

	@Rule
	public TransactionalTestRule transactionalTestRule =
		new TransactionalTestRule();

	private static Group _group;
	private static User _groupUser;
	private static Organization _organization;
	private static User _organizationUser;
	private static UserGroup _userGroup;
	private static User _userGroupUser;

	private int _inheritedUserGroupsExpectedCount;
	private LinkedHashMap<String, Object> _inheritedUserGroupsParams;
	private LinkedHashMap<String, Object> _inheritedUserRolesParams;
	private long _roleId;

}