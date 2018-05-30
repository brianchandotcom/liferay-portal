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

package com.liferay.commerce.user.segment.internal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.test.util.CommerceUserSegmentTestUtil;
import com.liferay.commerce.user.segment.util.CommerceUserSegmentHelper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class CommerceUserSegmentHelperWithOrganizationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_organization = OrganizationTestUtil.addOrganization();

		_userGroup1 = UserGroupTestUtil.addUserGroup();
		_userGroup2 = UserGroupTestUtil.addUserGroup();

		_user = UserTestUtil.addUser();

		_users = new User[10];

		_role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);
		_role2 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);
	}

	@Test
	public void testGetUsersWithOrganizationFromUserGroupAndUserAndRoleUserSegment()
		throws Exception {

		frutillaRule.scenario(
			"Get all User IDs related to a user segment"
		).given(
			"Some users associated to different user segments"
		).and(
			"3 types of user segments: user, role and user group"
		).when(
			"I try to get all users IDs associated to a user group segment"
		).then(
			"Only IDs of users associated to that specific user segment are " +
				"retrieved"
		);

		for (int i = 0; i < 5; i++) {
			User user = UserTestUtil.addUser();

			_users[i] = user;
		}

		_userLocalService.setRoleUsers(
			_role1.getRoleId(),
			new long[] {_users[1].getUserId(), _users[2].getUserId()});

		_userLocalService.setRoleUsers(
			_role2.getRoleId(),
			new long[] {_users[2].getUserId(), _users[3].getUserId()});

		_userLocalService.setUserGroupUsers(
			_userGroup1.getUserGroupId(), new long[] {_users[3].getUserId()});

		_userLocalService.setUserGroupUsers(
			_userGroup2.getUserGroupId(), new long[] {_users[4].getUserId()});

		_userLocalService.setOrganizationUsers(
			_organization.getOrganizationId(),
			new long[] {_users[1].getUserId(), _users[3].getUserId()});

		CommerceUserSegmentEntry userCommerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addUserCommerceUserSegmentEntry(
				_group.getGroupId(),
				new long[] {_users[0].getUserId(), _users[1].getUserId()});

		CommerceUserSegmentEntry roleCommerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addRoleCommerceUserSegmentEntry(
				_group.getGroupId(), new long[] {_role2.getRoleId()});

		CommerceUserSegmentEntry userGroupCommerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addUserGroupCommerceUserSegmentEntry(
				_group.getGroupId(), new long[] {_userGroup1.getUserGroupId()});

		long[] userIds = _commerceUserSegmentHelper.getUserIds(
			_group.getGroupId(), _organization.getOrganizationId(),
			new long[] {
				userCommerceUserSegmentEntry.getCommerceUserSegmentEntryId()
			},
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			true, ArrayUtil.contains(userIds, _users[0].getUserId()));

		Assert.assertEquals(
			true, ArrayUtil.contains(userIds, _users[1].getUserId()));

		userIds = _commerceUserSegmentHelper.getUserIds(
			_group.getGroupId(), _organization.getOrganizationId(),
			new long[] {
				roleCommerceUserSegmentEntry.getCommerceUserSegmentEntryId()
			},
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			false, ArrayUtil.contains(userIds, _users[1].getUserId()));

		Assert.assertEquals(
			true, ArrayUtil.contains(userIds, _users[2].getUserId()));

		Assert.assertEquals(
			true, ArrayUtil.contains(userIds, _users[3].getUserId()));

		userIds = _commerceUserSegmentHelper.getUserIds(
			_group.getGroupId(), _organization.getOrganizationId(),
			new long[] {
				userGroupCommerceUserSegmentEntry.
					getCommerceUserSegmentEntryId()
			},
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			true, ArrayUtil.contains(userIds, _users[3].getUserId()));

		Assert.assertEquals(
			false, ArrayUtil.contains(userIds, _users[4].getUserId()));
	}

	@Test
	public void testGetUsersWithOrganizationFromUserGroupUserSegments()
		throws Exception {

		frutillaRule.scenario(
			"Get all User IDs related to a user segment"
		).given(
			"A number of users"
		).and(
			"Some of them associated to a user group"
		).and(
			"Some other associated to a different user group"
		).and(
			"A user group segment for each user group"
		).when(
			"I try to get all users IDs associated to a user group segment"
		).then(
			"Only IDs of users associated to that specific user group " +
				"segment are retrieved"
		);

		List<Long> userGroup1UserIds = new ArrayList<>();

		List<Long> userGroup2UserIds = new ArrayList<>();

		long[] usersIds = new long[10];

		for (int i = 0; i < 10; i++) {
			User user = UserTestUtil.addUser();

			usersIds[i] = user.getUserId();

			_users[i] = user;

			if (RandomTestUtil.randomBoolean()) {
				userGroup1UserIds.add(user.getUserId());

				_userGroupLocalService.setUserUserGroups(
					user.getUserId(),
					new long[] {_userGroup1.getUserGroupId()});
			}
			else {
				userGroup2UserIds.add(user.getUserId());

				_userGroupLocalService.setUserUserGroups(
					user.getUserId(),
					new long[] {_userGroup2.getUserGroupId()});
			}
		}

		_userLocalService.setOrganizationUsers(
			_organization.getOrganizationId(), usersIds);

		CommerceUserSegmentEntry userGroup1UserSegmentEntry =
			CommerceUserSegmentTestUtil.addUserGroupCommerceUserSegmentEntry(
				_group.getGroupId(), _userGroup1.getUserGroupId());

		CommerceUserSegmentEntry userGroup2UserSegmentEntry =
			CommerceUserSegmentTestUtil.addUserGroupCommerceUserSegmentEntry(
				_group.getGroupId(), _userGroup2.getUserGroupId());

		long[] userIds = _commerceUserSegmentHelper.getUserIds(
			_group.getGroupId(), _organization.getOrganizationId(),
			new long[] {
				userGroup1UserSegmentEntry.getCommerceUserSegmentEntryId()
			},
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			true,
			ArrayUtil.containsAll(
				userIds, ArrayUtil.toLongArray(userGroup1UserIds)));

		userIds = _commerceUserSegmentHelper.getUserIds(
			_group.getGroupId(), _organization.getOrganizationId(),
			new long[] {
				userGroup2UserSegmentEntry.getCommerceUserSegmentEntryId()
			},
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			true,
			ArrayUtil.containsAll(
				userIds, ArrayUtil.toLongArray(userGroup2UserIds)));
	}

	@Test
	public void testGetWithOrganizationCommerceUserSegmentIdsOfOrganization()
		throws Exception {

		frutillaRule.scenario(
			"Test for CommerceUserSegmentHelper with organization type user " +
				"segment"
		).given(
			"An organization type user segment"
		).and(
			"A group"
		).and(
			"An organization"
		).and(
			"A user"
		).when(
			"I get all commerce user segment IDs of that group, organization " +
				"and user"
		).then(
			"A list of user segment IDs should be retrieved"
		);

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addOrganizationCommerceUserSegmentEntry(
				_group.getGroupId(), _organization.getOrganizationId());

		long[] commerceUserSegmentIDs =
			_commerceUserSegmentHelper.getCommerceUserSegmentIds(
				_group.getGroupId(), _organization.getOrganizationId(),
				_user.getUserId());

		Assert.assertEquals(
			true,
			ArrayUtil.contains(
				commerceUserSegmentIDs,
				commerceUserSegmentEntry.getCommerceUserSegmentEntryId()));
	}

	@Test
	public void testGetWithOrganizationCommerceUserSegmentIdsOfRole()
		throws Exception {

		frutillaRule.scenario(
			"Test for CommerceUserSegmentHelper with role type user segment"
		).given(
			"A role type user segment"
		).and(
			"A group"
		).and(
			"An organization"
		).and(
			"A user"
		).when(
			"I get all commerce user segment IDs of that group, organization " +
				"and user"
		).then(
			"A list of user segment IDs should be retrieved"
		);

		_userLocalService.setOrganizationUsers(
			_organization.getOrganizationId(), new long[] {_user.getUserId()});

		List<UserGroupRole> userGroupRoles =
			_userGroupRoleLocalService.addUserGroupRoles(
				_user.getUserId(), _organization.getGroupId(),
				new long[] {_role1.getRoleId()});

		_userLocalService.addRoleUser(userGroupRoles.get(0).getRoleId(), _user);

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addRoleCommerceUserSegmentEntry(
				_group.getGroupId(), _role1.getRoleId());

		long[] commerceUserSegmentIDs =
			_commerceUserSegmentHelper.getCommerceUserSegmentIds(
				_group.getGroupId(), _organization.getOrganizationId(),
				_user.getUserId());

		Assert.assertEquals(
			true,
			ArrayUtil.contains(
				commerceUserSegmentIDs,
				commerceUserSegmentEntry.getCommerceUserSegmentEntryId()));
	}

	@Test
	public void testGetWithOrganizationCommerceUserSegmentIdsOfUserGroup()
		throws Exception {

		frutillaRule.scenario(
			"Test for CommerceUserSegmentHelper with role type user segment"
		).given(
			"A user group type user segment"
		).and(
			"A group"
		).and(
			"An organization"
		).and(
			"A user"
		).when(
			"I get all commerce user segment IDs of that group, organization " +
				"and user"
		).then(
			"A list of user segment IDs should be retrieved"
		);

		_userLocalService.setUserGroupUsers(
			_userGroup1.getUserGroupId(), new long[] {_user.getUserId()});

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addUserGroupCommerceUserSegmentEntry(
				_group.getGroupId(), _userGroup1.getUserGroupId());

		long[] commerceUserSegmentIDs =
			_commerceUserSegmentHelper.getCommerceUserSegmentIds(
				_group.getGroupId(), _organization.getOrganizationId(),
				_user.getUserId());

		Assert.assertEquals(
			true,
			ArrayUtil.contains(
				commerceUserSegmentIDs,
				commerceUserSegmentEntry.getCommerceUserSegmentEntryId()));
	}

	@Test
	public void testGetWithOrganizationUserCommerceUserSegmentIds()
		throws Exception {

		frutillaRule.scenario(
			"Test for CommerceUserSegmentHelper with user type user segment"
		).given(
			"An user type user segment"
		).and(
			"A group"
		).and(
			"An organization"
		).and(
			"A user"
		).when(
			"I get all commerce user segment IDs of that group, organization " +
				"and user"
		).then(
			"A list of user segment IDs should be retrieved"
		);

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addUserCommerceUserSegmentEntry(
				_group.getGroupId(), _user.getUserId());

		long[] commerceUserSegmentIDs =
			_commerceUserSegmentHelper.getCommerceUserSegmentIds(
				_group.getGroupId(), _organization.getOrganizationId(),
				_user.getUserId());

		Assert.assertEquals(
			true,
			ArrayUtil.contains(
				commerceUserSegmentIDs,
				commerceUserSegmentEntry.getCommerceUserSegmentEntryId()));
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	@Inject
	private CommerceUserSegmentHelper _commerceUserSegmentHelper;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private Role _role1;

	@DeleteAfterTestRun
	private Role _role2;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

	@DeleteAfterTestRun
	private UserGroup _userGroup1;

	@DeleteAfterTestRun
	private UserGroup _userGroup2;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@DeleteAfterTestRun
	private User[] _users;

}