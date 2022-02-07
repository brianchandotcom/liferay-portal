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

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.UserGroup;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class UserGroupResourceTest extends BaseUserGroupResourceTestCase {

	@Override
	@Test
	public void testDeleteUserGroupUsers() throws Exception {
		UserGroup userGroup = testDeleteUserGroupUsers_addUserGroup();

		User user = UserTestUtil.addUser();

		_userLocalService.addUserGroupUser(userGroup.getId(), user.getUserId());

		List<User> users = _userLocalService.getUserGroupUsers(
			userGroup.getId());

		Assert.assertTrue(users.contains(user));

		userGroupResource.deleteUserGroupUsers(
			userGroup.getId(), new Long[] {user.getUserId()});

		users = _userLocalService.getUserGroupUsers(userGroup.getId());

		Assert.assertFalse(users.contains(user));
	}

	@Override
	@Test
	public void testPostUserGroupUsers() throws Exception {
		UserGroup userGroup = _postUserGroup();

		Assert.assertEquals(0, (int)userGroup.getUsersCount());

		User user = UserTestUtil.addUser();

		Long[] userIds = {user.getUserId()};

		userGroupResource.postUserGroupUsers(userGroup.getId(), userIds);

		Assert.assertArrayEquals(
			_userGroupLocalService.getUserPrimaryKeys(userGroup.getId()),
			ArrayUtil.toArray(userIds));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "name"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"description"};
	}

	@Override
	protected UserGroup testDeleteUserGroup_addUserGroup() throws Exception {
		return _postUserGroup();
	}

	@Override
	protected UserGroup
			testDeleteUserGroupByExternalReferenceCode_addUserGroup()
		throws Exception {

		return _postUserGroup();
	}

	@Override
	protected UserGroup testDeleteUserGroupUsers_addUserGroup()
		throws Exception {

		return _postUserGroup();
	}

	@Override
	protected UserGroup testGetUserGroup_addUserGroup() throws Exception {
		return _postUserGroup();
	}

	@Override
	protected UserGroup testGetUserGroupByExternalReferenceCode_addUserGroup()
		throws Exception {

		return _postUserGroup();
	}

	@Override
	protected UserGroup testGetUserGroupsPage_addUserGroup(UserGroup userGroup)
		throws Exception {

		return _postUserGroup(userGroup);
	}

	@Override
	protected UserGroup testGraphQLUserGroup_addUserGroup() throws Exception {
		return _postUserGroup();
	}

	@Override
	protected UserGroup testPatchUserGroup_addUserGroup() throws Exception {
		return _postUserGroup();
	}

	@Override
	protected UserGroup testPatchUserGroupByExternalReferenceCode_addUserGroup()
		throws Exception {

		return _postUserGroup();
	}

	@Override
	protected UserGroup testPostUserGroup_addUserGroup(UserGroup userGroup)
		throws Exception {

		return _postUserGroup(userGroup);
	}

	@Override
	protected UserGroup testPostUserGroupUsers_addUserGroup() throws Exception {
		return _postUserGroup();
	}

	@Override
	protected UserGroup testPutUserGroup_addUserGroup() throws Exception {
		return _postUserGroup();
	}

	@Override
	protected UserGroup testPutUserGroupByExternalReferenceCode_addUserGroup()
		throws Exception {

		return _postUserGroup();
	}

	private UserGroup _postUserGroup() throws Exception {
		return _postUserGroup(randomUserGroup());
	}

	private UserGroup _postUserGroup(UserGroup userGroup) throws Exception {
		return userGroupResource.postUserGroup(userGroup);
	}

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@Inject
	private UserLocalService _userLocalService;

}