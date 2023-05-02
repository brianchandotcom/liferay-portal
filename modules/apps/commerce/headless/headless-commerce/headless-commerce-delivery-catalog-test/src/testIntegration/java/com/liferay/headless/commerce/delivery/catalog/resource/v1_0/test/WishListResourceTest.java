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

package com.liferay.headless.commerce.delivery.catalog.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.WishList;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class WishListResourceTest extends BaseWishListResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_accountEntry = AccountEntryLocalServiceUtil.addAccountEntry(
			_user.getUserId(), AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null,
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS, 1,
			ServiceContextTestUtil.getServiceContext(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				_user.getUserId()));

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), RandomTestUtil.randomString());
	}

	@Override
	@Test
	public void testPatchWishList() throws Exception {
		WishList postWishList = testPatchChannelWishList_addWishList();

		WishList randomPatchWishList = randomPatchWishList();

		wishListResource.patchWishList(
			postWishList.getId(), randomPatchWishList);

		WishList expectedPatchWishList = postWishList.clone();

		BeanTestUtil.copyProperties(randomPatchWishList, expectedPatchWishList);

		WishList getWishList = wishListResource.getWishList(
			postWishList.getId());

		assertEquals(expectedPatchWishList, getWishList);
		assertValid(getWishList);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"defaultWishList", "name"};
	}

	@Override
	protected WishList randomWishList() throws Exception {
		return new WishList() {
			{
				defaultWishList = false;
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	@Override
	protected WishList testDeleteWishList_addWishList() throws Exception {
		return wishListResource.postChannelWishList(
			_commerceChannel.getCommerceChannelId(),
			_accountEntry.getAccountEntryId(), randomWishList());
	}

	@Override
	protected WishList testGetChannelWishListsPage_addWishList(
			Long channelId, WishList wishList)
		throws Exception {

		return wishListResource.postChannelWishList(
			channelId, _accountEntry.getAccountEntryId(), wishList);
	}

	@Override
	protected Long testGetChannelWishListsPage_getChannelId() throws Exception {
		return _commerceChannel.getCommerceChannelId();
	}

	@Override
	protected WishList testGetWishList_addWishList() throws Exception {
		return wishListResource.postChannelWishList(
			_commerceChannel.getCommerceChannelId(),
			_accountEntry.getAccountEntryId(), randomWishList());
	}

	@Override
	protected WishList testGraphQLWishList_addWishList() throws Exception {
		return wishListResource.postChannelWishList(
			_commerceChannel.getCommerceChannelId(),
			_accountEntry.getAccountEntryId(), randomWishList());
	}

	protected WishList testPatchChannelWishList_addWishList() throws Exception {
		return wishListResource.postChannelWishList(
			_commerceChannel.getCommerceChannelId(),
			_accountEntry.getAccountEntryId(), randomWishList());
	}

	@Override
	protected WishList testPostChannelWishList_addWishList(WishList wishList)
		throws Exception {

		return wishListResource.postChannelWishList(
			_commerceChannel.getCommerceChannelId(),
			_accountEntry.getAccountEntryId(), wishList);
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private User _user;

}