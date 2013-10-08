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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
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
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.OrganizationTestUtil;
import com.liferay.portal.util.RoleTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserGroupTestUtil;
import com.liferay.portal.util.UserTestUtil;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jozsef Illes
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
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

	@Test
	public void testCountByKeywordsWithInheritedGroups() throws Exception {
		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("inherit", Boolean.TRUE);
		params.put(
			"usersGroups",
			new Long[] {
				_group.getGroupId(), _organization.getGroupId(),
				_userGroup.getGroupId()
			});

		int count = UserFinderUtil.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, params);

		Assert.assertEquals(4, count);
	}

	@Test
	public void testCountByKeywordsWithInheritedRoles() throws Exception {
		long roleId = RoleTestUtil.addRegularRole(_group.getGroupId());

		RoleLocalServiceUtil.addGroupRole(_organization.getGroupId(), roleId);
		RoleLocalServiceUtil.addGroupRole(_userGroup.getGroupId(), roleId);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("inherit", Boolean.TRUE);
		params.put("usersRoles", roleId);

		int count = UserFinderUtil.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, params);

		Assert.assertEquals(4, count);
	}

	@Test
	public void testCountByKeywordsWithInheritedRolesThroughSite()
		throws Exception {

		GroupLocalServiceUtil.addOrganizationGroup(
			_organization.getOrganizationId(), _group);
		GroupLocalServiceUtil.addUserGroupGroup(
			_userGroup.getUserGroupId(), _group);

		long roleId = RoleTestUtil.addRegularRole(_group.getGroupId());

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("inherit", Boolean.TRUE);
		params.put("usersRoles", roleId);

		int count = UserFinderUtil.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, params);

		Assert.assertEquals(4, count);
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
		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("inherit", Boolean.TRUE);
		params.put(
			"usersGroups",
			new Long[] {
				_group.getGroupId(), _organization.getGroupId(),
				_userGroup.getGroupId()
			});

		List<User> users = UserFinderUtil.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, params, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.contains(_groupUser));
		Assert.assertTrue(users.contains(_organizationUser));
		Assert.assertTrue(users.contains(_userGroupUser));
		Assert.assertTrue(users.contains(TestPropsValues.getUser()));
		Assert.assertEquals(4, users.size());
	}

	@Test
	public void testFindByKeywordsWithInheritedRoles() throws Exception {
		long roleId = RoleTestUtil.addRegularRole(_group.getGroupId());

		RoleLocalServiceUtil.addGroupRole(_organization.getGroupId(), roleId);
		RoleLocalServiceUtil.addGroupRole(_userGroup.getGroupId(), roleId);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("inherit", Boolean.TRUE);
		params.put("usersRoles", roleId);

		List<User> users = UserFinderUtil.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, params, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.contains(_groupUser));
		Assert.assertTrue(users.contains(_organizationUser));
		Assert.assertTrue(users.contains(_userGroupUser));
		Assert.assertTrue(users.contains(TestPropsValues.getUser()));
		Assert.assertEquals(4, users.size());
	}

	@Test
	public void testFindByKeywordsWithInheritedRolesThroughSite()
		throws Exception {

		GroupLocalServiceUtil.addOrganizationGroup(
			_organization.getOrganizationId(), _group);
		GroupLocalServiceUtil.addUserGroupGroup(
			_userGroup.getUserGroupId(), _group);

		long roleId = RoleTestUtil.addRegularRole(_group.getGroupId());

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("inherit", Boolean.TRUE);
		params.put("usersRoles", roleId);

		List<User> users = UserFinderUtil.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, params, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.contains(_groupUser));
		Assert.assertTrue(users.contains(_organizationUser));
		Assert.assertTrue(users.contains(_userGroupUser));
		Assert.assertTrue(users.contains(TestPropsValues.getUser()));
		Assert.assertEquals(4, users.size());
	}

	private static Group _group;
	private static User _groupUser;
	private static Organization _organization;
	private static User _organizationUser;
	private static UserGroup _userGroup;
	private static User _userGroupUser;

}