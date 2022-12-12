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
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.service.CommerceWishListLocalServiceUtil;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.WishListItem;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class WishListItemResourceTest extends BaseWishListItemResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				_user.getUserId());

		_commerceAccount = CommerceAccountLocalServiceUtil.addCommerceAccount(
			RandomTestUtil.randomString(),
			CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
			RandomTestUtil.randomString() + "@liferay.com", null,
			CommerceAccountConstants.ACCOUNT_TYPE_PERSONAL, true,
			RandomTestUtil.randomString(), serviceContext);

		_commerceCatalog = CommerceTestUtil.addCommerceCatalog(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId(), RandomTestUtil.randomString());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), RandomTestUtil.randomString());

		_commerceWishList =
			CommerceWishListLocalServiceUtil.addCommerceWishList(
				RandomTestUtil.randomString(), RandomTestUtil.randomBoolean(),
				serviceContext);

		_commerceCPInstance1 = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_commerceCPInstance2 = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.ONE);

		_commerceCPDefinition1 = _commerceCPInstance1.getCPDefinition();

		_commerceCPDefinition2 = _commerceCPInstance2.getCPDefinition();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		CommerceAccountLocalServiceUtil.deleteCommerceAccount(_commerceAccount);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"productId", "skuId"};
	}

	@Override
	protected WishListItem randomPatchWishListItem() throws Exception {
		CProduct commerceProduct = _commerceCPDefinition2.getCProduct();

		return new WishListItem() {
			{
				productId = commerceProduct.getCProductId();
				skuId = _commerceCPInstance2.getCPInstanceId();
			}
		};
	}

	@Override
	protected WishListItem randomWishListItem() throws Exception {
		CProduct commerceProduct = _commerceCPDefinition1.getCProduct();

		return new WishListItem() {
			{
				productId = commerceProduct.getCProductId();
				skuId = _commerceCPInstance1.getCPInstanceId();
			}
		};
	}

	@Override
	protected WishListItem testDeleteWishListItem_addWishListItem()
		throws Exception {

		return _postChannelWishListItem();
	}

	@Override
	protected Long testDeleteWishListItem_getAccountId() throws Exception {
		return _commerceAccount.getCommerceAccountId();
	}

	@Override
	protected WishListItem testGetWishListItem_addWishListItem()
		throws Exception {

		return _postChannelWishListItem();
	}

	@Override
	protected WishListItem testGetWishListItemsPage_addWishListItem(
			Long wishListId, WishListItem wishListItem)
		throws Exception {

		return wishListItemResource.postChannelWishListItem(
			wishListId, _commerceAccount.getCommerceAccountId(), wishListItem);
	}

	@Override
	protected Long testGetWishListItemsPage_getWishListId() throws Exception {
		return _commerceWishList.getCommerceWishListId();
	}

	@Override
	protected WishListItem testGraphQLWishListItem_addWishListItem()
		throws Exception {

		return _postChannelWishListItem();
	}

	@Override
	protected WishListItem testPostChannelWishListItem_addWishListItem(
			WishListItem wishListItem)
		throws Exception {

		return wishListItemResource.postChannelWishListItem(
			_commerceWishList.getCommerceWishListId(),
			_commerceAccount.getCommerceAccountId(), wishListItem);
	}

	private WishListItem _postChannelWishListItem() throws Exception {
		return wishListItemResource.postChannelWishListItem(
			_commerceWishList.getCommerceWishListId(),
			_commerceAccount.getCommerceAccountId(), randomWishListItem());
	}

	private CommerceAccount _commerceAccount;

	@DeleteAfterTestRun
	private CommerceCatalog _commerceCatalog;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CPDefinition _commerceCPDefinition1;

	@DeleteAfterTestRun
	private CPDefinition _commerceCPDefinition2;

	@DeleteAfterTestRun
	private CPInstance _commerceCPInstance1;

	@DeleteAfterTestRun
	private CPInstance _commerceCPInstance2;

	@DeleteAfterTestRun
	private CommerceWishList _commerceWishList;

	@DeleteAfterTestRun
	private User _user;

}