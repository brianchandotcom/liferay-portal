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
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionConstants;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionLocalService;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.commerce.user.segment.test.util.CommerceUserSegmentTestUtil;
import com.liferay.commerce.user.segment.util.CommerceUserSegmentHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
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
public class CommerceUserSegmentHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();

		_organization = OrganizationTestUtil.addOrganization();

		_userGroup = UserGroupTestUtil.addUserGroup(_group1.getGroupId());

		_user = UserTestUtil.addOrganizationAdminUser(_organization);

		_userLocalService.setUserGroupUsers(
			_userGroup.getUserGroupId(), new long[] {_user.getUserId()});
	}

	@Test
	public void testAddCommerceUserSegmentEntryInOtherGroup() throws Exception {
		frutillaRule.scenario(
			"Add a user segment entry in a group, and try to retrieve all " +
				"user segment entries of a user but for a different group"
		).given(
			"I add an organization user segment"
		).when(
			"The group is different from the one used for retrieving the " +
				"valid segment entries"
		).then(
			"The entry should not be available"
		);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group2.getGroupId());

		_commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addCommerceUserSegmentEntry(
				_group2.getGroupId(), true, false, _user.getUserId(),
				serviceContext);

		CommerceUserSegmentTestUtil.addCommerceUserSegmentCriterion(
			_group2.getGroupId(),
			_commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
			CommerceUserSegmentCriterionConstants.TYPE_ORGANIZATION,
			String.valueOf(_organization.getOrganizationId()));

		long[] commerceUserSegmentIds =
			_commerceUserSegmentHelper.getCommerceUserSegmentIds(
				_group1.getGroupId(), 0, _user.getUserId());

		Assert.assertEquals(
			false,
			ArrayUtil.contains(
				commerceUserSegmentIds,
				_commerceUserSegmentEntry.getCommerceUserSegmentEntryId()));
	}

	@Test
	public void testGetCommerceUserSegmentIdsOfOrganization() throws Exception {
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
			"I get all commerce user segment IDs of that group and user"
		).then(
			"A list of user segment IDs should be retrieved"
		);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		_commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addCommerceUserSegmentEntry(
				_group1.getGroupId(), true, false, _user.getUserId(),
				serviceContext);

		CommerceUserSegmentTestUtil.addCommerceUserSegmentCriterion(
			_group1.getGroupId(),
			_commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
			CommerceUserSegmentCriterionConstants.TYPE_ORGANIZATION,
			String.valueOf(_organization.getOrganizationId()));

		long[] commerceUserSegmentIDs =
			_commerceUserSegmentHelper.getCommerceUserSegmentIds(
				_group1.getGroupId(), 0, _user.getUserId());

		Assert.assertEquals(
			true,
			ArrayUtil.contains(
				commerceUserSegmentIDs,
				_commerceUserSegmentEntry.getCommerceUserSegmentEntryId()));
	}

	@Test
	public void testGetCommerceUserSegmentIdsOfRole() throws Exception {
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
			"I get all commerce user segment IDs of that group and user"
		).then(
			"A list of user segment IDs should be retrieved"
		);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		_commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addCommerceUserSegmentEntry(
				_group1.getGroupId(), true, false, _user.getUserId(),
				serviceContext);

		Role role = _roleLocalService.getRole(
			serviceContext.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		CommerceUserSegmentTestUtil.addCommerceUserSegmentCriterion(
			_group1.getGroupId(),
			_commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
			CommerceUserSegmentCriterionConstants.TYPE_ROLE,
			String.valueOf(role.getRoleId()));

		long[] commerceUserSegmentIDs =
			_commerceUserSegmentHelper.getCommerceUserSegmentIds(
				_group1.getGroupId(), _organization.getOrganizationId(),
				_user.getUserId());

		Assert.assertEquals(
			true,
			ArrayUtil.contains(
				commerceUserSegmentIDs,
				_commerceUserSegmentEntry.getCommerceUserSegmentEntryId()));
	}

	@Test
	public void testGetCommerceUserSegmentIdsOfUserGroup() throws Exception {
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
			"I get all commerce user segment IDs of that group and user"
		).then(
			"A list of user segment IDs should be retrieved"
		);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		_commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addCommerceUserSegmentEntry(
				_group1.getGroupId(), true, false, _user.getUserId(),
				serviceContext);

		CommerceUserSegmentTestUtil.addCommerceUserSegmentCriterion(
			_group1.getGroupId(),
			_commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
			CommerceUserSegmentCriterionConstants.TYPE_USER_GROUP,
			String.valueOf(_userGroup.getUserGroupId()));

		long[] commerceUserSegmentIDs =
			_commerceUserSegmentHelper.getCommerceUserSegmentIds(
				_group1.getGroupId(), 0, _user.getUserId());

		Assert.assertEquals(
			true,
			ArrayUtil.contains(
				commerceUserSegmentIDs,
				_commerceUserSegmentEntry.getCommerceUserSegmentEntryId()));
	}

	@Test
	public void testGetUserCommerceUserSegmentIds() throws Exception {
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
			"I get all commerce user segment IDs of that group and user"
		).then(
			"A list of user segment IDs should be retrieved"
		);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		_commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addCommerceUserSegmentEntry(
				_group1.getGroupId(), true, false, _user.getUserId(),
				serviceContext);

		CommerceUserSegmentTestUtil.addCommerceUserSegmentCriterion(
			_group1.getGroupId(),
			_commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
			CommerceUserSegmentCriterionConstants.TYPE_USER,
			String.valueOf(_user.getUserId()));

		long[] commerceUserSegmentIDs =
			_commerceUserSegmentHelper.getCommerceUserSegmentIds(
				_group1.getGroupId(), 0, _user.getUserId());

		Assert.assertEquals(
			true,
			ArrayUtil.contains(
				commerceUserSegmentIDs,
				_commerceUserSegmentEntry.getCommerceUserSegmentEntryId()));
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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		_commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addCommerceUserSegmentEntry(
				_group1.getGroupId(), true, false, _user.getUserId(),
				serviceContext);

		CommerceUserSegmentTestUtil.addCommerceUserSegmentCriterion(
			_group1.getGroupId(),
			_commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
			CommerceUserSegmentCriterionConstants.TYPE_ORGANIZATION,
			String.valueOf(_organization.getOrganizationId()));

		long[] commerceUserSegmentIDs =
			_commerceUserSegmentHelper.getCommerceUserSegmentIds(
				_group1.getGroupId(), _organization.getOrganizationId(),
				_user.getUserId());

		Assert.assertEquals(
			true,
			ArrayUtil.contains(
				commerceUserSegmentIDs,
				_commerceUserSegmentEntry.getCommerceUserSegmentEntryId()));
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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		_commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addCommerceUserSegmentEntry(
				_group1.getGroupId(), true, false, _user.getUserId(),
				serviceContext);

		CommerceUserSegmentTestUtil.addCommerceUserSegmentCriterion(
			_group1.getGroupId(),
			_commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
			CommerceUserSegmentCriterionConstants.TYPE_ROLE,
			String.valueOf(RoleConstants.ORGANIZATION_ADMINISTRATOR));

		long[] commerceUserSegmentIDs =
			_commerceUserSegmentHelper.getCommerceUserSegmentIds(
				_group1.getGroupId(), _organization.getOrganizationId(),
				_user.getUserId());

		Assert.assertEquals(
			true,
			ArrayUtil.contains(
				commerceUserSegmentIDs,
				_commerceUserSegmentEntry.getCommerceUserSegmentEntryId()));
	}

	@Test
	public void testGetWithOrganizationCommerceUserSegmentIdsOfUserGroup()
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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		_commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addCommerceUserSegmentEntry(
				_group1.getGroupId(), true, false, _user.getUserId(),
				serviceContext);

		CommerceUserSegmentTestUtil.addCommerceUserSegmentCriterion(
			_group1.getGroupId(),
			_commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
			CommerceUserSegmentCriterionConstants.TYPE_USER_GROUP,
			String.valueOf(_userGroup.getUserGroupId()));

		long[] commerceUserSegmentIDs =
			_commerceUserSegmentHelper.getCommerceUserSegmentIds(
				_group1.getGroupId(), _organization.getOrganizationId(),
				_user.getUserId());

		Assert.assertEquals(
			true,
			ArrayUtil.contains(
				commerceUserSegmentIDs,
				_commerceUserSegmentEntry.getCommerceUserSegmentEntryId()));
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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		_commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addCommerceUserSegmentEntry(
				_group1.getGroupId(), true, false, _user.getUserId(),
				serviceContext);

		CommerceUserSegmentTestUtil.addCommerceUserSegmentCriterion(
			_group1.getGroupId(),
			_commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
			CommerceUserSegmentCriterionConstants.TYPE_USER,
			String.valueOf(_user.getUserId()));

		long[] commerceUserSegmentIDs =
			_commerceUserSegmentHelper.getCommerceUserSegmentIds(
				_group1.getGroupId(), _organization.getOrganizationId(),
				_user.getUserId());

		Assert.assertEquals(
			true,
			ArrayUtil.contains(
				commerceUserSegmentIDs,
				_commerceUserSegmentEntry.getCommerceUserSegmentEntryId()));
	}

	@Test
	public void testUserOrganizationChangeWithOrganizationCommerceUserSegment()
		throws Exception {

		frutillaRule.scenario(
			"Remove the organization associated to a user that has an " +
				"organization user segment"
		).given(
			"A user associated to an organization"
		).and(
			"An organization type user segment"
		).when(
			"The organization associated to the user change"
		).then(
			"The segment should not be valid anymore"
		);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		_commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addCommerceUserSegmentEntry(
				_group1.getGroupId(), true, false, _user.getUserId(),
				serviceContext);

		CommerceUserSegmentTestUtil.addCommerceUserSegmentCriterion(
			_group1.getGroupId(),
			_commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
			CommerceUserSegmentCriterionConstants.TYPE_ORGANIZATION,
			String.valueOf(_organization.getOrganizationId()));

		_userLocalService.unsetOrganizationUsers(
			_organization.getOrganizationId(), new long[] {_user.getUserId()});

		_commerceUserSegmentEntryLocalService.cleanUserSegmentsChache(
			_group1.getGroupId());

		long[] commerceUserSegmentIDs =
			_commerceUserSegmentHelper.getCommerceUserSegmentIds(
				_group1.getGroupId(), 0, _user.getUserId());

		Assert.assertEquals(
			false,
			ArrayUtil.contains(
				commerceUserSegmentIDs,
				_commerceUserSegmentEntry.getCommerceUserSegmentEntryId()));
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	@Inject
	private CommerceUserSegmentCriterionLocalService
		_commerceUserSegmentCriterionLocalService;

	@DeleteAfterTestRun
	private CommerceUserSegmentEntry _commerceUserSegmentEntry;

	@Inject
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

	@Inject
	private CommerceUserSegmentHelper _commerceUserSegmentHelper;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@DeleteAfterTestRun
	private Organization _organization;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

	@DeleteAfterTestRun
	private UserGroup _userGroup;

	@Inject
	private UserLocalService _userLocalService;

}