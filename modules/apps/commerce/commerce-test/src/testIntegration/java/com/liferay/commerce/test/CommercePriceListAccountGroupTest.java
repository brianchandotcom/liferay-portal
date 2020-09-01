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
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.test.util.CommercePriceListTestUtil;
import com.liferay.commerce.test.util.CommerceAccountGroupTestUtil;
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
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class CommercePriceListAccountGroupTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addUser();

		_commerceAccount =
			_commerceAccountLocalService.getPersonalCommerceAccount(
				_user.getUserId());

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
		).when(
			"I try to get the best matching price list"
		).then(
			"The price list with the highest priority should be retrieved"
		);

		CommerceAccountGroup commerceAccountGroup =
			CommerceAccountGroupTestUtil.addCommerceAccountToAccountGroup(
				_commerceAccount);

		CommercePriceListTestUtil.addUserPriceList(
			_group.getGroupId(), 1, commerceAccountGroup);

		CommercePriceListTestUtil.addUserPriceList(
			_group.getGroupId(), 2, commerceAccountGroup);

		CommercePriceList expectedCommercePriceList =
			CommercePriceListTestUtil.addUserPriceList(
				_group.getGroupId(), 3, commerceAccountGroup);

		Optional<CommercePriceList> actualCommercePriceList =
			CommercePriceListTestUtil.getCommercePriceList(
				_group.getGroupId(), _commerceAccount.getCommerceAccountId(),
				_user.getUserId());

		Assert.assertEquals(
			expectedCommercePriceList.getCommercePriceListId(),
			actualCommercePriceList.get(
			).getCommercePriceListId());
	}

	@Test(expected = NoSuchElementException.class)
	public void testPriceListWithDifferentAccountGroups() throws Exception {
		frutillaRule.scenario(
			"Add a price list with different account groups"
		).given(
			"A group and a user"
		).and(
			"I add a price list"
		).and(
			"I associate to the price list one account group"
		).and(
			"The account trying to get the price list is in a different " +
				"account group to the one associated with it"
		).when(
			"I try to get the price list"
		).then(
			"The price list should be returned only if the account belongs " +
				"to the account group associated with it"
		);

		CommerceAccountGroupTestUtil.addCommerceAccountToAccountGroup(
			_commerceAccount);

		CommerceAccountGroup associatedAccountGroup =
			CommerceAccountGroupTestUtil.addCommerceAccountGroup(
				_group.getGroupId());

		CommercePriceListTestUtil.addUserPriceList(
			_group.getGroupId(), 1, associatedAccountGroup);

		Optional<CommercePriceList> actualCommercePriceList =
			CommercePriceListTestUtil.getCommercePriceList(
				_group.getGroupId(), _commerceAccount.getCommerceAccountId(),
				_user.getUserId());

		actualCommercePriceList.get();
	}

	@Test
	public void testPriceListWithMultipleAccountGroups() throws Exception {
		frutillaRule.scenario(
			"Add a price list with multiple account groups"
		).given(
			"A group and a user"
		).and(
			"I add a price list"
		).and(
			"I associate to the price list to many account groups"
		).when(
			"I try to get the price list"
		).then(
			"The price list should be returned only if the account belongs " +
				"to every account group associated with it"
		);

		CommerceAccountGroup commerceAccountGroup1 =
			CommerceAccountGroupTestUtil.addCommerceAccountToAccountGroup(
				_commerceAccount);
		CommerceAccountGroup commerceAccountGroup2 =
			CommerceAccountGroupTestUtil.addCommerceAccountToAccountGroup(
				_commerceAccount);
		CommerceAccountGroup commerceAccountGroup3 =
			CommerceAccountGroupTestUtil.addCommerceAccountToAccountGroup(
				_commerceAccount);
		CommerceAccountGroup commerceAccountGroup4 =
			CommerceAccountGroupTestUtil.addCommerceAccountToAccountGroup(
				_commerceAccount);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addUserPriceList(
				_group.getGroupId(), 1, commerceAccountGroup1);

		CommercePriceListTestUtil.addUserPriceListToAccountGroup(
			commercePriceList, commerceAccountGroup2);

		CommercePriceListTestUtil.addUserPriceListToAccountGroup(
			commercePriceList, commerceAccountGroup3);

		CommercePriceListTestUtil.addUserPriceListToAccountGroup(
			commercePriceList, commerceAccountGroup4);

		Optional<CommercePriceList> actualCommercePriceList =
			CommercePriceListTestUtil.getCommercePriceList(
				_group.getGroupId(), _commerceAccount.getCommerceAccountId(),
				_user.getUserId());

		actualCommercePriceList.get();
	}

	@Ignore
	@Test(expected = NoSuchElementException.class)
	public void testPriceListWithUserAndDifferentRoleAccountGroups()
		throws Exception {

		frutillaRule.scenario(
			"Add a price list with multiple account groups"
		).given(
			"A group and a user"
		).and(
			"I add a price list"
		).and(
			"I associate to the price list an account groups and a role segment"
		).and(
			"The role segment's role is different from the one associated " +
				"with the user"
		).when(
			"I try to get the price list"
		).then(
			"The price list should be returned only if all segments' " +
				"requirements are met. In this specific case the result " +
					"should be empty"
		);

		_userLocalService.addRoleUser(_role1.getRoleId(), _user);

		CommerceAccountGroup commerceAccountGroup =
			CommerceAccountGroupTestUtil.addCommerceAccountToAccountGroup(
				_commerceAccount);

		CommercePriceList expectedCommercePriceList =
			CommercePriceListTestUtil.addUserPriceList(
				_group.getGroupId(), 1, commerceAccountGroup);

		CommercePriceListTestUtil.addRoleSegmentToPriceList(
			expectedCommercePriceList, _role2.getRoleId());

		Optional<CommercePriceList> actualCommercePriceList =
			CommercePriceListTestUtil.getCommercePriceList(
				_group.getGroupId(), _commerceAccount.getCommerceAccountId(),
				_user.getUserId());

		actualCommercePriceList.get();
	}

	@Ignore
	@Test
	public void testPriceListWithUserAndRoleAccountGroups() throws Exception {
		frutillaRule.scenario(
			"Add a price list with multiple account groups"
		).given(
			"A group and a user"
		).and(
			"I add a price list"
		).and(
			"I associate to the price list an account group and a role segment"
		).when(
			"I try to get the price list"
		).then(
			"The price list should be returned only if all segments' " +
				"requirements are met"
		);

		_userLocalService.addOrganizationUser(
			_organization.getOrganizationId(), _user);

		List<UserGroupRole> userGroupRoles =
			_userGroupRoleLocalService.addUserGroupRoles(
				_user.getUserId(), _organization.getGroupId(),
				new long[] {_role1.getRoleId()});

		UserGroupRole userGroupRole = userGroupRoles.get(0);

		_userLocalService.addRoleUser(userGroupRole.getRoleId(), _user);

		CommercePriceList expectedCommercePriceList =
			CommercePriceListTestUtil.addUserPriceList(
				_group.getGroupId(), 1, _user.getUserId());

		CommercePriceListTestUtil.addRoleSegmentToPriceList(
			expectedCommercePriceList, _role1.getRoleId());

		Optional<CommercePriceList> actualCommercePriceList =
			CommercePriceListTestUtil.getCommercePriceList(
				_group.getGroupId(), 0, _user.getUserId());

		Assert.assertEquals(
			expectedCommercePriceList.getCommercePriceListId(),
			actualCommercePriceList.get(
			).getCommercePriceListId());
	}

	@Ignore
	@Test
	public void testPriceListWithUserAndRoleAndOrganizationAccountGroups()
		throws Exception {

		frutillaRule.scenario(
			"Add a price list with multiple account groups"
		).given(
			"A group and a user"
		).and(
			"I add a price list"
		).and(
			"I associate to the price list to an account group, a role " +
				"segment and an organization segment"
		).when(
			"I try to get the price list"
		).then(
			"The price list should be returned only if all segments " +
				"requirements are met"
		);

		_userLocalService.addOrganizationUser(
			_organization.getOrganizationId(), _user);

		List<UserGroupRole> userGroupRoles =
			_userGroupRoleLocalService.addUserGroupRoles(
				_user.getUserId(), _organization.getGroupId(),
				new long[] {_role1.getRoleId()});

		UserGroupRole userGroupRole = userGroupRoles.get(0);

		_userLocalService.addRoleUser(userGroupRole.getRoleId(), _user);

		CommercePriceList expectedCommercePriceList =
			CommercePriceListTestUtil.addUserPriceList(
				_group.getGroupId(), 1, _user.getUserId());

		CommercePriceListTestUtil.addRoleSegmentToPriceList(
			expectedCommercePriceList, _role1.getRoleId());

		CommercePriceListTestUtil.addOrganizationSegmentToPriceList(
			expectedCommercePriceList, _organization.getOrganizationId());

		Optional<CommercePriceList> actualCommercePriceList =
			CommercePriceListTestUtil.getCommercePriceList(
				_group.getGroupId(), 0, _user.getUserId());

		Assert.assertEquals(
			expectedCommercePriceList.getCommercePriceListId(),
			actualCommercePriceList.get(
			).getCommercePriceListId());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private CommerceAccount _commerceAccount;

	@Inject
	private CommerceAccountLocalService _commerceAccountLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Organization _organization;

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