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
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class UserGroupLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_companyId = _role.getCompanyId();

		_baseUserGroupCount = UserGroupLocalServiceUtil.searchCount(
			_companyId, null, _emptyParams);

		_userGroup1 = UserGroupTestUtil.addUserGroup();
		_userGroup2 = UserGroupTestUtil.addUserGroup();

		GroupLocalServiceUtil.addRoleGroup(
			_role.getRoleId(), _userGroup1.getGroupId());

		_userGroupParams.put(
			UserGroupConstants.PARAM_USER_GROUPS_ROLES,
			Long.valueOf(_role.getRoleId()));

		_userGroupParams.put("invalidParamKey", "invalidParamValue");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		RoleLocalServiceUtil.deleteRole(_role);
		UserGroupLocalServiceUtil.deleteUserGroup(_userGroup1);
		UserGroupLocalServiceUtil.deleteUserGroup(_userGroup2);
	}

	@Test
	public void testSearchRoleUserGroups() {
		List<UserGroup> userGroups = _doSearchUserGroups(
			null, _userGroupParams);

		Assert.assertEquals(1, userGroups.size());
	}

	@Test
	public void testSearchRoleUserGroupsWithKeywords() {
		List<UserGroup> userGroups = _doSearchUserGroups(
			_userGroup2.getName(), _userGroupParams);

		Assert.assertEquals(0, userGroups.size());
	}

	@Test
	public void testSearchUserGroups() {
		List<UserGroup> userGroups = _doSearchUserGroups(null, _emptyParams);

		Assert.assertEquals(_baseUserGroupCount + 2, userGroups.size());
	}

	@Test
	public void testSearchUserGroupsWithKeywords() {
		List<UserGroup> userGroups = _doSearchUserGroups(
			_userGroup1.getName(), _emptyParams);

		Assert.assertEquals(1, userGroups.size());
	}

	private List<UserGroup> _doSearchUserGroups(
		String keywords, LinkedHashMap<String, Object> params) {

		return UserGroupLocalServiceUtil.search(
			_companyId, keywords, params, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			UsersAdminUtil.getUserGroupOrderByComparator("name", "asc"));
	}

	private static int _baseUserGroupCount;
	private static long _companyId;
	private static final LinkedHashMap<String, Object> _emptyParams =
		new LinkedHashMap<>();
	private static Role _role;
	private static UserGroup _userGroup1;
	private static UserGroup _userGroup2;
	private static final LinkedHashMap<String, Object> _userGroupParams =
		new LinkedHashMap<>();

}