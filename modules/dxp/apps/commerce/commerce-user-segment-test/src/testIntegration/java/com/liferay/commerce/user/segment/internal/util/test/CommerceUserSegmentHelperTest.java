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
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionConstants;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionService;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.commerce.user.segment.test.util.CommerceUserSegmentEntryTestUtil;
import com.liferay.commerce.user.segment.util.CommerceUserSegmentHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

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
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();

		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addOrganizationAdminUser(_organization);
	}

	@Test
	public void testAddCommerceUserSegmentEntryInOtherGroup() throws Exception {
		frutillaRule.scenario(
			"Add a user segment entry in a different group"
		).given(
			"A user segment entry added to a group"
		).and(
			"A user"
		).when(
			"I try to get all user segment entries of the user for a " +
				"different group"
		).then(
			"The newly created user segment entry should not be returned"
		);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group2.getGroupId());

		_commerceUserSegmentEntry =
			CommerceUserSegmentEntryTestUtil.addCommerceUserSegmentEntry(
				_group2.getGroupId(), true, false, _user.getUserId(),
				serviceContext);

		_commerceUserSegmentCriterion =
			_comCommerceUserSegmentCriterionService.
				addCommerceUserSegmentCriterion(
					_commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
					CommerceUserSegmentCriterionConstants.TYPE_ORGANIZATION,
					String.valueOf(_organization.getOrganizationId()), 0,
					serviceContext);

		long[] commerceUserSegmentIDs =
			_commerceUserSegmentHelper.getCommerceUserSegmentIds(
				_group1.getGroupId(), 0, _user.getUserId());

		Assert.assertEquals(
			false,
			ArrayUtil.contains(
				commerceUserSegmentIDs,
				_commerceUserSegmentEntry.getCommerceUserSegmentEntryId()));
	}

	@Test
	public void testGetCommerceUserSegmentIdsOfOrganization() throws Exception {
		frutillaRule.scenario(
			"Test for CommerceUserSegmentHelper.getCommerceUserSegmentIds"
		).given(
			"A organization type user segment"
		).and(
			"A group"
		).and(
			"An organization"
		).and(
			"A user"
		).when(
			"The method getCommerceUserSegmentIds is invoked"
		).then(
			"A list of user segment IDs should be retrieved"
		);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		_commerceUserSegmentEntry =
			CommerceUserSegmentEntryTestUtil.addCommerceUserSegmentEntry(
				_group1.getGroupId(), true, false, _user.getUserId(),
				serviceContext);

		_commerceUserSegmentCriterion =
			_comCommerceUserSegmentCriterionService.
				addCommerceUserSegmentCriterion(
					_commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
					CommerceUserSegmentCriterionConstants.TYPE_ORGANIZATION,
					String.valueOf(_organization.getOrganizationId()), 0,
					serviceContext);

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
			"Test for CommerceUserSegmentHelper"
		).given(
			"A role type user segment"
		).and(
			"A group"
		).and(
			"An organization"
		).and(
			"A user"
		).when(
			"The method getCommerceUserSegmentIds is invoked"
		).then(
			"A list of user segment IDs should be retrieved"
		);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		_commerceUserSegmentEntry =
			CommerceUserSegmentEntryTestUtil.addCommerceUserSegmentEntry(
				_group1.getGroupId(), true, false, _user.getUserId(),
				serviceContext);

		_commerceUserSegmentCriterion =
			_comCommerceUserSegmentCriterionService.
				addCommerceUserSegmentCriterion(
					_commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
					CommerceUserSegmentCriterionConstants.TYPE_ROLE,
					String.valueOf(RoleConstants.ORGANIZATION_ADMINISTRATOR), 0,
					serviceContext);

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
			"Test for CommerceUserSegmentHelper"
		).given(
			"A user type user segment"
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
			CommerceUserSegmentEntryTestUtil.addCommerceUserSegmentEntry(
				_group1.getGroupId(), true, false, _user.getUserId(),
				serviceContext);

		_commerceUserSegmentCriterion =
			_comCommerceUserSegmentCriterionService.
				addCommerceUserSegmentCriterion(
					_commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
					CommerceUserSegmentCriterionConstants.TYPE_USER,
					String.valueOf(_user.getUserId()), 0, serviceContext);

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
	public void testRemoveOrganizationToUserWithOrganizationCommerceUserSegmentEntry()
		throws Exception {

		frutillaRule.scenario(
			"Remove the company associated to a user with user segment"
		).given(
			"A user associated to a company"
		).and(
			"A organization type user segment"
		).when(
			"The company associated to the user change"
		).then(
			"The segment should not be valid anymore"
		);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		_commerceUserSegmentEntry =
			CommerceUserSegmentEntryTestUtil.addCommerceUserSegmentEntry(
				_group1.getGroupId(), true, false, _user.getUserId(),
				serviceContext);

		_commerceUserSegmentCriterion =
			_comCommerceUserSegmentCriterionService.
				addCommerceUserSegmentCriterion(
					_commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
					CommerceUserSegmentCriterionConstants.TYPE_ORGANIZATION,
					String.valueOf(_organization.getOrganizationId()), 0,
					serviceContext);

		_userService.unsetOrganizationUsers(
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
	private CommerceUserSegmentCriterionService
		_comCommerceUserSegmentCriterionService;

	@DeleteAfterTestRun
	private CommerceUserSegmentCriterion _commerceUserSegmentCriterion;

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

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserService _userService;

}