/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.service.CommerceWishListItemLocalService;
import com.liferay.commerce.wish.list.service.CommerceWishListService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class CommerceWishListItemLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();

		_cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());
	}

	@Test
	public void testAddOrUpdateCommerceWishListItem() throws Exception {
		_commerceWishList = _commerceWishListService.addCommerceWishList(
			_group.getGroupId(), _user.getUserId(),
			RandomTestUtil.randomString(), true);

		CPDefinition cpDefinition = _cpInstance.getCPDefinition();

		CProduct cProduct = cpDefinition.getCProduct();

		_commerceWishListItem =
			_commerceWishListItemLocalService.addOrUpdateCommerceWishListItem(
				_user.getUserId(), _commerceWishList.getCommerceWishListId(),
				cProduct.getCProductId(), _cpInstance.getCPInstanceUuid(), "");

		Assert.assertEquals(
			1,
			_commerceWishListItemLocalService.getCommerceWishListItemsCount(
				_commerceWishList.getCommerceWishListId()));

		_commerceWishListItemLocalService.addOrUpdateCommerceWishListItem(
			_user.getUserId(), _commerceWishList.getCommerceWishListId(),
			cProduct.getCProductId(), _cpInstance.getCPInstanceUuid(), "");

		Assert.assertEquals(
			1,
			_commerceWishListItemLocalService.getCommerceWishListItemsCount(
				_commerceWishList.getCommerceWishListId()));
	}

	private static User _user;

	@DeleteAfterTestRun
	private CommerceWishList _commerceWishList;

	@DeleteAfterTestRun
	private CommerceWishListItem _commerceWishListItem;

	@Inject
	private CommerceWishListItemLocalService _commerceWishListItemLocalService;

	@Inject
	private CommerceWishListService _commerceWishListService;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	private Group _group;

}