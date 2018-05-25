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

package com.liferay.commerce.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.test.util.CommercePriceListTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
public class CommercePriceListUserSegmentWithOrganizationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_organization1 = OrganizationTestUtil.addOrganization();
		_organization2 = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addUser();

		_role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);
		_role2 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);
	}

	@Test
	public void testPriceListPriority() throws Exception {
		frutillaRule.scenario(
			"When multiple price lists are available, check that only the " +
				"matching one is retrieved"
		).given(
			"I add some price lists with different priorities"
		).and(
			"An organization, a group and a user"
		).when(
			"I try to get the best matching price list"
		).then(
			"The price list with the highest priority should be retrieved"
		);

		CommercePriceListTestUtil.addUserPriceList(
			_group.getGroupId(), 1, _user.getUserId());

		CommercePriceListTestUtil.addUserPriceList(
			_group.getGroupId(), 2, _user.getUserId());

		CommercePriceList expectedCommercePriceList =
			CommercePriceListTestUtil.addUserPriceList(
				_group.getGroupId(), 3, _user.getUserId());

		Optional<CommercePriceList> actualCommercePriceList =
			CommercePriceListTestUtil.getCommercePriceList(
				_group.getGroupId(), _organization1.getOrganizationId(),
				_user.getUserId());

		Assert.assertNotNull(actualCommercePriceList.get());

		Assert.assertEquals(
			expectedCommercePriceList.getCommercePriceListId(),
			actualCommercePriceList.get().getCommercePriceListId());
	}

	@Test(expected = NoSuchElementException.class)
	public void testPriceListWithUserAndDifferentRoleUserSegments()
		throws Exception {

		frutillaRule.scenario(
			"Add a price list that is associated to multiple user segments"
		).given(
			"A group and a user and an organization"
		).and(
			"I add a price list"
		).and(
			"I associate to the price list a user segment and a role segment"
		).and(
			"The role segment's role is different from the one associated to " +
				"the user"
		).when(
			"I try to get the price list"
		).then(
			"The price list should be returned only if all segments " +
				"requirements are met. In this specific case the result " +
					"should be empty"
		);

		_userLocalService.addOrganizationUser(
			_organization1.getOrganizationId(), _user);

		List<UserGroupRole> userGroupRoles =
			_userGroupRoleLocalService.addUserGroupRoles(
				_user.getUserId(), _organization1.getGroupId(),
				new long[] {_role1.getRoleId()});

		_userLocalService.addRoleUser(userGroupRoles.get(0).getRoleId(), _user);

		CommercePriceList expectedCommercePriceList =
			CommercePriceListTestUtil.addUserPriceList(
				_group.getGroupId(), 1, _user.getUserId());

		CommercePriceListTestUtil.addRoleSegmentToPriceList(
			expectedCommercePriceList, _role2.getRoleId());

		Optional<CommercePriceList> actualCommercePriceList =
			CommercePriceListTestUtil.getCommercePriceList(
				_group.getGroupId(), _organization1.getOrganizationId(),
				_user.getUserId());

		actualCommercePriceList.get();
	}

	@Test
	public void testPriceListWithUserAndRoleAndMoreOrganizationUserSegments1()
		throws Exception {

		frutillaRule.scenario(
			"Add a price list that is associated to multiple user segments"
		).given(
			"A group, a user linked to 2 organizations with different roles"
		).and(
			"I add a price list"
		).and(
			"I associate to the price list a user segment, a role segment " +
				"and an organization segment"
		).when(
			"I try to get the price list"
		).and(
			"The user segment role is equal to the role of the user for that " +
				"organization"
		).then(
			"The price list should be returned only if all user segments " +
				"requirements are met"
		);

		_userLocalService.addOrganizationUser(
			_organization1.getOrganizationId(), _user);
		_userLocalService.addOrganizationUser(
			_organization2.getOrganizationId(), _user);

		List<UserGroupRole> userGroupRoles1 =
			_userGroupRoleLocalService.addUserGroupRoles(
				_user.getUserId(), _organization1.getGroupId(),
				new long[] {_role1.getRoleId()});

		List<UserGroupRole> userGroupRoles2 =
			_userGroupRoleLocalService.addUserGroupRoles(
				_user.getUserId(), _organization2.getGroupId(),
				new long[] {_role2.getRoleId()});

		_userLocalService.addRoleUser(
			userGroupRoles1.get(0).getRoleId(), _user);
		_userLocalService.addRoleUser(
			userGroupRoles2.get(0).getRoleId(), _user);

		CommercePriceList expectedCommercePriceList =
			CommercePriceListTestUtil.addUserPriceList(
				_group.getGroupId(), 1, _user.getUserId());

		CommercePriceListTestUtil.addRoleSegmentToPriceList(
			expectedCommercePriceList, _role1.getRoleId());

		CommercePriceListTestUtil.addOrganizationSegmentToPriceList(
			expectedCommercePriceList, _organization1.getOrganizationId());

		Optional<CommercePriceList> actualCommercePriceList =
			CommercePriceListTestUtil.getCommercePriceList(
				_group.getGroupId(), _organization1.getOrganizationId(),
				_user.getUserId());

		Assert.assertEquals(
			expectedCommercePriceList.getCommercePriceListId(),
			actualCommercePriceList.get().getCommercePriceListId());
	}

	@Test(expected = NoSuchElementException.class)
	public void testPriceListWithUserAndRoleAndMoreOrganizationUserSegments2()
		throws Exception {

		frutillaRule.scenario(
			"Add a price list that is associated to multiple user segments"
		).given(
			"A group, a user linked to 2 organizations with different roles"
		).and(
			"I add a price list"
		).and(
			"I associate to the price list a user segment, a role segment " +
				"and an organization segment"
		).when(
			"I try to get the price list"
		).and(
			"The user segment role is different from the role of the user " +
				"for that organization"
		).then(
			"The price list should be returned only if all user segments " +
				"requirements are met. In this specific case the result " +
					"should be empty"
		);

		_userLocalService.addOrganizationUser(
			_organization1.getOrganizationId(), _user);
		_userLocalService.addOrganizationUser(
			_organization2.getOrganizationId(), _user);

		List<UserGroupRole> userGroupRoles1 =
			_userGroupRoleLocalService.addUserGroupRoles(
				_user.getUserId(), _organization1.getGroupId(),
				new long[] {_role1.getRoleId()});

		_userLocalService.addRoleUser(
			userGroupRoles1.get(0).getRoleId(), _user);

		CommercePriceList expectedCommercePriceList =
			CommercePriceListTestUtil.addUserPriceList(
				_group.getGroupId(), 1, _user.getUserId());

		CommercePriceListTestUtil.addRoleSegmentToPriceList(
			expectedCommercePriceList, _role1.getRoleId());

		CommercePriceListTestUtil.addOrganizationSegmentToPriceList(
			expectedCommercePriceList, _organization1.getOrganizationId());

		Optional<CommercePriceList> actualCommercePriceList =
			CommercePriceListTestUtil.getCommercePriceList(
				_group.getGroupId(), _organization2.getOrganizationId(),
				_user.getUserId());

		actualCommercePriceList.get();
	}

	@Test
	public void testPriceListWithUserAndRoleAndOrganizationUserSegments()
		throws Exception {

		frutillaRule.scenario(
			"Add a price list that is associated to multiple user segments"
		).given(
			"A group, a user and an organization"
		).and(
			"I add a price list"
		).and(
			"I associate to the price list a user segment, a role segment " +
				"and an organization segment"
		).when(
			"I try to get the price list"
		).then(
			"The price list should be returned only if all user segments " +
				"requirements are met"
		);

		_userLocalService.addOrganizationUser(
			_organization1.getOrganizationId(), _user);

		List<UserGroupRole> userGroupRoles =
			_userGroupRoleLocalService.addUserGroupRoles(
				_user.getUserId(), _organization1.getGroupId(),
				new long[] {_role1.getRoleId()});

		_userLocalService.addRoleUser(userGroupRoles.get(0).getRoleId(), _user);

		CommercePriceList expectedCommercePriceList =
			CommercePriceListTestUtil.addUserPriceList(
				_group.getGroupId(), 1, _user.getUserId());

		CommercePriceListTestUtil.addRoleSegmentToPriceList(
			expectedCommercePriceList, _role1.getRoleId());

		CommercePriceListTestUtil.addOrganizationSegmentToPriceList(
			expectedCommercePriceList, _organization1.getOrganizationId());

		Optional<CommercePriceList> actualCommercePriceList =
			CommercePriceListTestUtil.getCommercePriceList(
				_group.getGroupId(), _organization1.getOrganizationId(),
				_user.getUserId());

		Assert.assertNotNull(actualCommercePriceList.get());

		Assert.assertEquals(
			expectedCommercePriceList.getCommercePriceListId(),
			actualCommercePriceList.get().getCommercePriceListId());
	}

	@Test
	public void testPriceListWithUserAndRoleUserSegments() throws Exception {
		frutillaRule.scenario(
			"Add a price list that is associated to multiple user segments"
		).given(
			"A group, a user and an organization"
		).and(
			"I add a price list"
		).and(
			"I associate to the price a user segment and a role segment"
		).when(
			"I try to get the price list"
		).then(
			"The price list should be returned only if all user segments " +
				"requirements are met"
		);

		_userLocalService.addOrganizationUser(
			_organization1.getOrganizationId(), _user);

		List<UserGroupRole> userGroupRoles =
			_userGroupRoleLocalService.addUserGroupRoles(
				_user.getUserId(), _organization1.getGroupId(),
				new long[] {_role1.getRoleId()});

		_userLocalService.addRoleUser(userGroupRoles.get(0).getRoleId(), _user);

		CommercePriceList expectedCommercePriceList =
			CommercePriceListTestUtil.addUserPriceList(
				_group.getGroupId(), 1, _user.getUserId());

		CommercePriceListTestUtil.addRoleSegmentToPriceList(
			expectedCommercePriceList, _role1.getRoleId());

		Optional<CommercePriceList> actualCommercePriceList =
			CommercePriceListTestUtil.getCommercePriceList(
				_group.getGroupId(), _organization1.getOrganizationId(),
				_user.getUserId());

		Assert.assertNotNull(actualCommercePriceList.get());

		Assert.assertEquals(
			expectedCommercePriceList.getCommercePriceListId(),
			actualCommercePriceList.get().getCommercePriceListId());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Organization _organization1;

	@DeleteAfterTestRun
	private Organization _organization2;

	@DeleteAfterTestRun
	private Role _role1;

	@DeleteAfterTestRun
	private Role _role2;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}