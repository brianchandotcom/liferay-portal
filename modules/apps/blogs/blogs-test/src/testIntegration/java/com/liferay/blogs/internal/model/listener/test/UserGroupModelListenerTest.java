/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.subscription.service.SubscriptionLocalService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcell Gyöpös
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class UserGroupModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testRemoveUserFromUserGroupWhenUserHasSiteMembershipFromOtherGroups()
		throws Exception {

		UserGroup userGroup1 = _addGroupUserGroup(_group.getGroupId());
		UserGroup userGroup2 = _addGroupUserGroup(_group.getGroupId());

		User user = _addUserGroupUser(
			userGroup1.getUserGroupId(), userGroup2.getUserGroupId());

		_blogsEntryLocalService.subscribe(
			user.getUserId(), _group.getGroupId());

		_userGroupLocalService.deleteUserUserGroup(
			user.getUserId(), userGroup1.getUserGroupId());

		Assert.assertTrue(
			_subscriptionLocalService.isSubscribed(
				_group.getCompanyId(), user.getUserId(),
				BlogsEntry.class.getName(), _group.getGroupId()));
	}

	@Test
	public void testRemoveUserFromUserGroupWhenUserLostSiteMembership()
		throws Exception {

		UserGroup userGroup = _addGroupUserGroup(_group.getGroupId());

		User user = _addUserGroupUser(userGroup.getUserGroupId());

		_blogsEntryLocalService.subscribe(
			user.getUserId(), _group.getGroupId());

		_userGroupLocalService.deleteUserUserGroup(
			user.getUserId(), userGroup.getUserGroupId());

		Assert.assertFalse(
			_subscriptionLocalService.isSubscribed(
				_group.getCompanyId(), user.getUserId(),
				BlogsEntry.class.getName(), _group.getGroupId()));
	}

	@Test
	public void testRemoveUserGroupFromSiteWhenUserHasDirectSiteMembership()
		throws Exception {

		UserGroup userGroup = _addGroupUserGroup(_group.getGroupId());

		User user = _addUserGroupUser(userGroup.getUserGroupId());

		_groupLocalService.addUserGroup(user.getUserId(), _group);

		_blogsEntryLocalService.subscribe(
			user.getUserId(), _group.getGroupId());

		_groupLocalService.deleteUserGroupGroup(
			userGroup.getUserGroupId(), _group.getGroupId());

		Assert.assertTrue(
			_subscriptionLocalService.isSubscribed(
				_group.getCompanyId(), user.getUserId(),
				BlogsEntry.class.getName(), _group.getGroupId()));
	}

	@Test
	public void testRemoveUserGroupFromSiteWhenUserHasSiteMembershipFromOtherGroups()
		throws Exception {

		UserGroup userGroup1 = _addGroupUserGroup(_group.getGroupId());
		UserGroup userGroup2 = _addGroupUserGroup(_group.getGroupId());

		User user = _addUserGroupUser(
			userGroup1.getUserGroupId(), userGroup2.getUserGroupId());

		_blogsEntryLocalService.subscribe(
			user.getUserId(), _group.getGroupId());

		_groupLocalService.deleteUserGroupGroup(
			userGroup1.getUserGroupId(), _group.getGroupId());

		Assert.assertTrue(
			_subscriptionLocalService.isSubscribed(
				_group.getCompanyId(), user.getUserId(),
				BlogsEntry.class.getName(), _group.getGroupId()));
	}

	@Test
	public void testRemoveUserGroupFromSiteWhenUserLostSiteMembership()
		throws Exception {

		UserGroup userGroup = _addGroupUserGroup(_group.getGroupId());

		User user = _addUserGroupUser(userGroup.getUserGroupId());

		_blogsEntryLocalService.subscribe(
			user.getUserId(), _group.getGroupId());

		_groupLocalService.deleteUserGroupGroup(
			userGroup.getUserGroupId(), _group.getGroupId());

		Assert.assertFalse(
			_subscriptionLocalService.isSubscribed(
				_group.getCompanyId(), user.getUserId(),
				BlogsEntry.class.getName(), _group.getGroupId()));
	}

	@Test
	public void testRemoveUsersFromUserGroupWhenUserHasDirectSiteMembership()
		throws Exception {

		UserGroup userGroup = _addGroupUserGroup(_group.getGroupId());

		User user = _addUserGroupUser(userGroup.getUserGroupId());

		_groupLocalService.addUserGroup(user.getUserId(), _group);

		_blogsEntryLocalService.subscribe(
			user.getUserId(), _group.getGroupId());

		_userGroupLocalService.deleteUserUserGroup(
			user.getUserId(), userGroup.getUserGroupId());

		Assert.assertTrue(
			_subscriptionLocalService.isSubscribed(
				_group.getCompanyId(), user.getUserId(),
				BlogsEntry.class.getName(), _group.getGroupId()));
	}

	private UserGroup _addGroupUserGroup(long groupId) throws Exception {
		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		_userGroupLocalService.addGroupUserGroup(groupId, userGroup);

		return userGroup;
	}

	private User _addUserGroupUser(long... userGroupIds) throws Exception {
		User user = UserTestUtil.addUser();

		for (long userGroupId : userGroupIds) {
			_userGroupLocalService.addUserUserGroup(
				user.getUserId(), userGroupId);
		}

		return user;
	}

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private SubscriptionLocalService _subscriptionLocalService;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

}