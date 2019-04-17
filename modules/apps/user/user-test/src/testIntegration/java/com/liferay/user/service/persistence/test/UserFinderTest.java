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

package com.liferay.user.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.persistence.UserFinder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.comparator.UserFirstNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.social.kernel.model.SocialRelationConstants;
import com.liferay.social.kernel.service.SocialRelationLocalService;

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
import org.junit.runner.RunWith;

/**
 * @author Jozsef Illes
 */
@RunWith(Arquillian.class)
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

		_groupLocalService.addUserGroup(_groupUser.getUserId(), _group);

		_organization = OrganizationTestUtil.addOrganization(true);
		_organizationUser = UserTestUtil.addUser();

		_organizationLocalService.addUserOrganization(
			_organizationUser.getUserId(), _organization);

		_socialUser = UserTestUtil.addUser();

		_socialRelationLocalService.addRelation(
			_groupUser.getUserId(), _socialUser.getUserId(),
			SocialRelationConstants.TYPE_BI_CONNECTION);

		_userGroup = UserGroupTestUtil.addUserGroup();
		_userGroupUser = UserTestUtil.addUser();

		_userGroupLocalService.addUserUserGroup(
			_userGroupUser.getUserId(), _userGroup);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_groupLocalService.deleteGroup(_group);
		_userLocalService.deleteUser(_groupUser);

		_userLocalService.deleteUser(_organizationUser);

		_organizationLocalService.deleteOrganization(_organization);

		_userLocalService.deleteUser(_socialUser);
		_userLocalService.deleteUser(_userGroupUser);

		_userGroupLocalService.deleteUserGroup(_userGroup);
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

		_inheritedUserGroupsExpectedCount = _userFinder.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserGroupsParams);

		_roleId = RoleTestUtil.addRegularRole(_group.getGroupId());

		_inheritedUserRolesParams = new LinkedHashMap<>();

		_inheritedUserRolesParams.put("inherit", Boolean.TRUE);
		_inheritedUserRolesParams.put("usersRoles", _roleId);
	}

	@After
	public void tearDown() throws Exception {
		_roleLocalService.deleteRole(_roleId);

		_groupLocalService.clearOrganizationGroups(
			_organization.getOrganizationId());
		_groupLocalService.clearUserGroupGroups(_userGroup.getUserGroupId());
	}

	@Test
	public void testCountByGroups() throws Exception {
		long groupId = _group.getGroupId();

		Map<Long, Integer> counts = _userFinder.countByGroups(
			TestPropsValues.getCompanyId(), WorkflowConstants.STATUS_APPROVED,
			new long[] {groupId});

		Assert.assertEquals(counts.toString(), 1, counts.size());
		Assert.assertEquals(2, (int)counts.get(groupId));

		_groupLocalService.addOrganizationGroup(
			_organization.getOrganizationId(), groupId);

		counts = _userFinder.countByGroups(
			TestPropsValues.getCompanyId(), WorkflowConstants.STATUS_APPROVED,
			new long[] {groupId});

		Assert.assertEquals(counts.toString(), 1, counts.size());
		Assert.assertEquals(3, (int)counts.get(groupId));

		_groupLocalService.addUserGroupGroup(
			_userGroup.getUserGroupId(), groupId);

		counts = _userFinder.countByGroups(
			TestPropsValues.getCompanyId(), WorkflowConstants.STATUS_APPROVED,
			new long[] {groupId});

		Assert.assertEquals(counts.toString(), 1, counts.size());
		Assert.assertEquals(4, (int)counts.get(groupId));

		long organizationGroupId = _organization.getGroupId();

		counts = _userFinder.countByGroups(
			TestPropsValues.getCompanyId(), WorkflowConstants.STATUS_APPROVED,
			new long[] {groupId, organizationGroupId});

		Assert.assertEquals(counts.toString(), 2, counts.size());
		Assert.assertEquals(1, (int)counts.get(organizationGroupId));
	}

	@Test
	public void testCountByKeywordsWithInheritedGroups() throws Exception {
		int count = _userFinder.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserGroupsParams);

		Assert.assertEquals(_inheritedUserGroupsExpectedCount, count);
	}

	@Test
	public void testCountByKeywordsWithInheritedRoles() throws Exception {
		int expectedCount = _userFinder.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserRolesParams);

		_roleLocalService.addGroupRole(_organization.getGroupId(), _roleId);
		_roleLocalService.addGroupRole(_userGroup.getGroupId(), _roleId);

		int count = _userFinder.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserRolesParams);

		Assert.assertEquals(expectedCount + 2, count);
	}

	@Test
	public void testCountByKeywordsWithInheritedRolesThroughSite()
		throws Exception {

		int expectedCount = _userFinder.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserRolesParams);

		_groupLocalService.addOrganizationGroup(
			_organization.getOrganizationId(), _group);
		_groupLocalService.addUserGroupGroup(
			_userGroup.getUserGroupId(), _group);

		int count = _userFinder.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserRolesParams);

		Assert.assertEquals(expectedCount + 2, count);
	}

	@Test
	public void testFindByKeywordsGroupUsers() throws Exception {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("usersGroups", _group.getGroupId());

		List<User> users = _userFinder.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, params, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.toString(), users.contains(_groupUser));
	}

	@Test
	public void testFindByKeywordsOrganizationUsers() throws Exception {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("usersOrgs", _organization.getOrganizationId());

		List<User> users = _userFinder.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, params, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.toString(), users.contains(_organizationUser));
	}

	@Test
	public void testFindByKeywordsUserGroupUsers() throws Exception {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("usersUserGroups", _userGroup.getUserGroupId());

		List<User> users = _userFinder.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, params, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.toString(), users.contains(_userGroupUser));
	}

	@Test
	public void testFindByKeywordsWithInheritedGroups() throws Exception {
		List<User> users = _userFinder.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserGroupsParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.toString(), users.contains(_groupUser));
		Assert.assertTrue(users.toString(), users.contains(_organizationUser));
		Assert.assertTrue(users.toString(), users.contains(_userGroupUser));
		Assert.assertTrue(
			users.toString(), users.contains(TestPropsValues.getUser()));
		Assert.assertEquals(
			users.toString(), _inheritedUserGroupsExpectedCount, users.size());
	}

	@Test
	public void testFindByKeywordsWithInheritedRoles() throws Exception {
		List<User> expectedUsers = _userFinder.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserRolesParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		_roleLocalService.addGroupRole(_organization.getGroupId(), _roleId);
		_roleLocalService.addGroupRole(_userGroup.getGroupId(), _roleId);

		List<User> users = _userFinder.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserRolesParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.toString(), users.contains(_groupUser));
		Assert.assertTrue(users.toString(), users.contains(_organizationUser));
		Assert.assertTrue(users.toString(), users.contains(_userGroupUser));
		Assert.assertTrue(
			users.toString(), users.contains(TestPropsValues.getUser()));
		Assert.assertEquals(
			users.toString(), expectedUsers.size() + 2, users.size());
	}

	@Test
	public void testFindByKeywordsWithInheritedRolesThroughSite()
		throws Exception {

		List<User> expectedUsers = _userFinder.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserRolesParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		_groupLocalService.addOrganizationGroup(
			_organization.getOrganizationId(), _group);
		_groupLocalService.addUserGroupGroup(
			_userGroup.getUserGroupId(), _group);

		List<User> users = _userFinder.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, _inheritedUserRolesParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertTrue(users.toString(), users.contains(_groupUser));
		Assert.assertTrue(users.toString(), users.contains(_organizationUser));
		Assert.assertTrue(users.toString(), users.contains(_userGroupUser));
		Assert.assertTrue(
			users.toString(), users.contains(TestPropsValues.getUser()));
		Assert.assertEquals(
			users.toString(), expectedUsers.size() + 2, users.size());
	}

	@Test
	public void testFindBySocialUsers() throws Exception {
		List<User> users = _userFinder.findBySocialUsers(
			TestPropsValues.getCompanyId(), _groupUser.getUserId(),
			SocialRelationConstants.TYPE_BI_CONNECTION, StringPool.EQUAL,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new UserFirstNameComparator(true));

		Assert.assertEquals(users.toString(), 1, users.size());
	}

	private static Group _group;

	@Inject
	private static GroupLocalService _groupLocalService;

	private static User _groupUser;
	private static Organization _organization;

	@Inject
	private static OrganizationLocalService _organizationLocalService;

	private static User _organizationUser;

	@Inject
	private static SocialRelationLocalService _socialRelationLocalService;

	private static User _socialUser;
	private static UserGroup _userGroup;

	@Inject
	private static UserGroupLocalService _userGroupLocalService;

	private static User _userGroupUser;

	@Inject
	private static UserLocalService _userLocalService;

	private int _inheritedUserGroupsExpectedCount;
	private LinkedHashMap<String, Object> _inheritedUserGroupsParams;
	private LinkedHashMap<String, Object> _inheritedUserRolesParams;
	private long _roleId;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserFinder _userFinder;

}