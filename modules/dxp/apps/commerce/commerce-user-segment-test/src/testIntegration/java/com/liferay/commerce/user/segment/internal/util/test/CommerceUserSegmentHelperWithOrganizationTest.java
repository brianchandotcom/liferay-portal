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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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

		_userGroup = UserGroupTestUtil.addUserGroup(_group.getGroupId());

		_user = UserTestUtil.addUser();
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

		_userLocalService.addOrganizationUser(
			_organization.getOrganizationId(), _user);

		Role role = _roleLocalService.getRole(
			_group.getCompanyId(), RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_userLocalService.addRoleUser(role.getRoleId(), _user);

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addRoleCommerceUserSegmentEntry(
				_group.getGroupId(), role.getRoleId());

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
			_userGroup.getUserGroupId(), new long[] {_user.getUserId()});

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addUserGroupCommerceUserSegmentEntry(
				_group.getGroupId(), _userGroup.getUserGroupId());

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

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

	private UserGroup _userGroup;

	@Inject
	private UserLocalService _userLocalService;

}