/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.UserGroup;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.Arrays;
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
	public void testGetUserUserGroups() throws Exception {
		Long userAccountId = testGetUserUserGroups_getUserAccountId();

		Page<UserGroup> page = userGroupResource.getUserUserGroups(
			userAccountId);

		Assert.assertEquals(0, page.getTotalCount());

		UserGroup userGroup1 = testGetUserUserGroups_addUserGroup(
			userAccountId, randomUserGroup());
		UserGroup userGroup2 = testGetUserUserGroups_addUserGroup(
			userAccountId, randomUserGroup());

		page = userGroupResource.getUserUserGroups(userAccountId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(userGroup1, userGroup2),
			(List<UserGroup>)page.getItems());
		assertValid(page);
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
	protected UserGroup testGetUserUserGroups_addUserGroup(
			Long userAccountId, UserGroup userGroup)
		throws Exception {

		userGroup = _postUserGroup(userGroup);

		userGroupResource.postUserGroupUsers(
			userGroup.getId(), new Long[] {userAccountId});

		return userGroup;
	}

	@Override
	protected Long testGetUserUserGroups_getUserAccountId() throws Exception {
		User user = UserTestUtil.addUser();

		return user.getUserId();
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