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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
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

import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		createGroupsWithUsers();
	}

	@Test
	public void testCountByKeywordsWithGroupUnion()
		throws PortalException, SystemException {

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put(
			"usersGroups",
			new Long[] {
				_site.getGroupId(), _org.getGroupId(),
				_userGroup.getGroup().getGroupId()
			});
		params.put("inherit", Boolean.TRUE);

		int userCount = countByKeywords(params);

		Assert.assertEquals(4, userCount);
	}

	@Test
	public void testCountByKeywordsWithRoleUnion() throws Exception {
		long roleId = RoleTestUtil.addRegularRole(_site.getGroupId());

		RoleLocalServiceUtil.addGroupRole(_org.getGroupId(), roleId);

		RoleLocalServiceUtil.addGroupRole(
			_userGroup.getGroup().getGroupId(), roleId);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersRoles", roleId);
		params.put("inherit", Boolean.TRUE);

		int userCount = countByKeywords(params);

		Assert.assertEquals(4, userCount);
	}

	@Test
	public void testCountByKeywordsWithRoleUnionThroughSite() throws Exception {
		GroupLocalServiceUtil.addOrganizationGroup(
			_org.getOrganizationId(), _site);

		GroupLocalServiceUtil.addUserGroupGroup(
			_userGroup.getUserGroupId(), _site);

		long roleId = RoleTestUtil.addRegularRole(_site.getGroupId());

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersRoles", roleId);
		params.put("inherit", Boolean.TRUE);

		int userCount = countByKeywords(params);

		Assert.assertEquals(4, userCount);
	}

	@Test
	public void testFindByKeywordsWithGroupUnion()
		throws PortalException, SystemException {

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put(
			"usersGroups",
			new Long[] {
				_site.getGroupId(), _org.getGroupId(),
				_userGroup.getGroup().getGroupId()
			});
		params.put("inherit", Boolean.TRUE);

		List<User> usersFound =
			UserFinderUtil.findByKeywords(
				TestPropsValues.getCompanyId(), null,
				WorkflowConstants.STATUS_APPROVED, params, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, (OrderByComparator)null);

		Assert.assertTrue(usersFound.contains(_siteUser));

		Assert.assertTrue(usersFound.contains(_orgUser));

		Assert.assertTrue(usersFound.contains(_userGroupUser));

		User testUser =
			UserLocalServiceUtil.getUserByScreenName(
				TestPropsValues.getCompanyId(), "test");

		Assert.assertTrue(usersFound.contains(testUser));

		Assert.assertEquals(4, usersFound.size());
	}

	@Test
	public void testFindByKeywordsWithRoleUnion() throws Exception {
		long roleId = RoleTestUtil.addRegularRole(_site.getGroupId());

		RoleLocalServiceUtil.addGroupRole(_org.getGroupId(), roleId);

		RoleLocalServiceUtil.addGroupRole(
			_userGroup.getGroup().getGroupId(), roleId);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersRoles", roleId);
		params.put("inherit", Boolean.TRUE);

		List<User> usersFound = findByKeywords(params);

		Assert.assertTrue(
			"Site user must inherit Role", usersFound.contains(_siteUser));

		Assert.assertTrue(
			"Organization user must inherit Role",
			usersFound.contains(_orgUser));

		Assert.assertTrue(
			"User Group user must inherit Role",
			usersFound.contains(_userGroupUser));

		User testUser =
			UserLocalServiceUtil.getUserByScreenName(
				TestPropsValues.getCompanyId(), "test");

		Assert.assertTrue(usersFound.contains(testUser));

		Assert.assertEquals(4, usersFound.size());
	}

	@Test
	public void testFindByKeywordsWithRoleUnionThroughSite() throws Exception {
		GroupLocalServiceUtil.addOrganizationGroup(
			_org.getOrganizationId(), _site);

		GroupLocalServiceUtil.addUserGroupGroup(
			_userGroup.getUserGroupId(), _site);

		long roleId = RoleTestUtil.addRegularRole(_site.getGroupId());

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersRoles", roleId);
		params.put("inherit", Boolean.TRUE);

		List<User> usersFound = findByKeywords(params);

		Assert.assertTrue(
			"Site user must inherit Role", usersFound.contains(_siteUser));

		Assert.assertTrue(
			"Organization user must inherit Role",
			usersFound.contains(_orgUser));

		Assert.assertTrue(
			"User Group user must inherit Role",
			usersFound.contains(_userGroupUser));

		User testUser =
			UserLocalServiceUtil.getUserByScreenName(
				TestPropsValues.getCompanyId(), "test");

		Assert.assertTrue(usersFound.contains(testUser));

		Assert.assertEquals(4, usersFound.size());
	}

	@Test
	public void testFindGroupUsersByKeywords()
		throws PortalException, SystemException {

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersGroups", _site.getGroupId());

		List<User> usersFound = findByKeywords(params);

		Assert.assertTrue(usersFound.contains(_siteUser));
	}

	@Test
	public void testFindOrgUsersByKeywords()
		throws PortalException, SystemException {

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersOrgs", _org.getOrganizationId());

		List<User> usersFound = findByKeywords(params);

		Assert.assertTrue(usersFound.contains(_orgUser));
	}

	@Test
	public void testFindUserGroupUsersByKeywords()
		throws PortalException, SystemException {

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersUserGroups", _userGroup.getUserGroupId());

		List<User> usersFound = findByKeywords(params);

		Assert.assertTrue(usersFound.contains(_userGroupUser));
	}

	protected int countByKeywords(LinkedHashMap<String, Object> params)
		throws PortalException, SystemException {

		return UserFinderUtil.countByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, params);
	}

	protected User createApprovedUser(String screenName) throws Exception {
		User user = UserTestUtil.addUser(
			screenName, TestPropsValues.getGroupId());

		user.setStatus(WorkflowConstants.STATUS_APPROVED);

		UserLocalServiceUtil.updateUser(user);

		return user;
	}

	protected void createGroupsWithUsers() throws Exception {
		_site = GroupTestUtil.addGroup();
		_siteUser = createApprovedUser("siteMember");
		GroupLocalServiceUtil.addUserGroup(_siteUser.getUserId(), _site);

		_org = OrganizationTestUtil.addOrganization();
		_orgUser = createApprovedUser("orgMember");
		OrganizationLocalServiceUtil.addUserOrganization(
			_orgUser.getUserId(), _org);

		_userGroup = UserGroupTestUtil.addUserGroup();
		_userGroupUser = createApprovedUser("userGroupMember");
		UserGroupLocalServiceUtil.addUserUserGroup(
			_userGroupUser.getUserId(), _userGroup);
	}

	protected List<User> findByKeywords(LinkedHashMap<String, Object> params)
		throws PortalException, SystemException {

		return UserFinderUtil.findByKeywords(
			TestPropsValues.getCompanyId(), null,
			WorkflowConstants.STATUS_APPROVED, params, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, (OrderByComparator)null);
	}

	private Organization _org;
	private User _orgUser;
	private Group _site;
	private User _siteUser;
	private UserGroup _userGroup;
	private User _userGroupUser;

}