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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.UserFinderUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.comparator.UserFirstNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.social.kernel.model.SocialRelationConstants;
import com.liferay.social.kernel.service.SocialRelationLocalServiceUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Jozsef Illes
 */
public class UserFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_group = GroupTestUtil.addGroup();
		_groupUser = UserTestUtil.addUser();

		GroupLocalServiceUtil.addUserGroup(_groupUser.getUserId(), _group);

		_organization = OrganizationTestUtil.addOrganization(true);
		_organizationUser = UserTestUtil.addUser();

		OrganizationLocalServiceUtil.addUserOrganization(
			_organizationUser.getUserId(), _organization);

		_socialUser = UserTestUtil.addUser();

		SocialRelationLocalServiceUtil.addRelation(
			_groupUser.getUserId(), _socialUser.getUserId(),
			SocialRelationConstants.TYPE_BI_CONNECTION);

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

		UserLocalServiceUtil.deleteUser(_socialUser);
		UserLocalServiceUtil.deleteUser(_userGroupUser);

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

		_inheritedUserGroupsExpectedCount = UserFinderUtil.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserGroupsParams);

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
	public void testCountByGroups() throws Exception {
		long groupId = _group.getGroupId();

		Map<Long, Integer> counts = UserFinderUtil.countByGroups(
			TestPropsValues.getCompanyId(), WorkflowConstants.STATUS_APPROVED,
			new long[] {groupId});

		Assert.assertEquals(counts.toString(), 1, counts.size());
		Assert.assertEquals(2, (int)counts.get(groupId));

		GroupLocalServiceUtil.addOrganizationGroup(
			_organization.getOrganizationId(), groupId);

		counts = UserFinderUtil.countByGroups(
			TestPropsValues.getCompanyId(), WorkflowConstants.STATUS_APPROVED,
			new long[] {groupId});

		Assert.assertEquals(counts.toString(), 1, counts.size());
		Assert.assertEquals(3, (int)counts.get(groupId));

		GroupLocalServiceUtil.addUserGroupGroup(
			_userGroup.getUserGroupId(), groupId);

		counts = UserFinderUtil.countByGroups(
			TestPropsValues.getCompanyId(), WorkflowConstants.STATUS_APPROVED,
			new long[] {groupId});

		Assert.assertEquals(counts.toString(), 1, counts.size());
		Assert.assertEquals(4, (int)counts.get(groupId));

		long organizationGroupId = _organization.getGroupId();

		counts = UserFinderUtil.countByGroups(
			TestPropsValues.getCompanyId(), WorkflowConstants.STATUS_APPROVED,
			new long[] {groupId, organizationGroupId});

		Assert.assertEquals(counts.toString(), 2, counts.size());
		Assert.assertEquals(1, (int)counts.get(organizationGroupId));
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
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("usersGroups", _group.getGroupId());

		List<User> users = UserFinderUtil.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, params, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.contains(_groupUser));
	}

	@Test
	public void testFindByKeywordsOrganizationUsers() throws Exception {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("usersOrgs", _organization.getOrganizationId());

		List<User> users = UserFinderUtil.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, params, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.contains(_organizationUser));
	}

	@Test
	public void testFindByKeywordsUserGroupUsers() throws Exception {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

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
		Assert.assertEquals(
			users.toString(), _inheritedUserGroupsExpectedCount, users.size());
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
		Assert.assertEquals(
			users.toString(), expectedUsers.size() + 2, users.size());
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
		Assert.assertEquals(
			users.toString(), expectedUsers.size() + 2, users.size());
	}

	@Test
	public void testFindBySocialUsers() throws Exception {
		List<User> users = UserFinderUtil.findBySocialUsers(
			TestPropsValues.getCompanyId(), _groupUser.getUserId(),
			SocialRelationConstants.TYPE_BI_CONNECTION, StringPool.EQUAL,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new UserFirstNameComparator(true));

		Assert.assertEquals(users.toString(), 1, users.size());
	}

	private static Group _group;
	private static User _groupUser;
	private static Organization _organization;
	private static User _organizationUser;
	private static User _socialUser;
	private static UserGroup _userGroup;
	private static User _userGroupUser;

	private int _inheritedUserGroupsExpectedCount;
	private LinkedHashMap<String, Object> _inheritedUserGroupsParams;
	private LinkedHashMap<String, Object> _inheritedUserRolesParams;
	private long _roleId;

}