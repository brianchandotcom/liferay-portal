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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalServiceUtil;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.WishList;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.After;
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

		_commerceAccount = CommerceAccountLocalServiceUtil.addCommerceAccount(
			RandomTestUtil.randomString(),
			CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
			RandomTestUtil.randomString() + "@liferay.com", null,
			CommerceAccountConstants.ACCOUNT_TYPE_PERSONAL, true,
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				_user.getUserId()));

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), RandomTestUtil.randomString());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		CommerceAccountLocalServiceUtil.deleteCommerceAccount(_commerceAccount);
	}

	@Override
	@Test
	public void testPatchChannelWishList() throws Exception {
		WishList postWishList = testPatchChannelWishList_addWishList();

		WishList randomPatchWishList = randomPatchWishList();

		wishListResource.patchChannelWishList(
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
		return _postChannelWishList();
	}

	@Override
	protected WishList testGetChannelWishListsPage_addWishList(
			Long channelId, WishList wishList)
		throws Exception {

		return wishListResource.postChannelWishList(
			channelId, _commerceAccount.getCommerceAccountId(), wishList);
	}

	@Override
	protected Long testGetChannelWishListsPage_getChannelId() throws Exception {
		return _commerceChannel.getCommerceChannelId();
	}

	@Override
	protected WishList testGetWishList_addWishList() throws Exception {
		return _postChannelWishList();
	}

	@Override
	protected WishList testGraphQLWishList_addWishList() throws Exception {
		return _postChannelWishList();
	}

	protected WishList testPatchChannelWishList_addWishList() throws Exception {
		return _postChannelWishList();
	}

	@Override
	protected WishList testPostChannelWishList_addWishList(WishList wishList)
		throws Exception {

		return wishListResource.postChannelWishList(
			_commerceChannel.getCommerceChannelId(),
			_commerceAccount.getCommerceAccountId(), wishList);
	}

	private WishList _postChannelWishList() throws Exception {
		return wishListResource.postChannelWishList(
			_commerceChannel.getCommerceChannelId(),
			_commerceAccount.getCommerceAccountId(), randomWishList());
	}

	private CommerceAccount _commerceAccount;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private User _user;

}