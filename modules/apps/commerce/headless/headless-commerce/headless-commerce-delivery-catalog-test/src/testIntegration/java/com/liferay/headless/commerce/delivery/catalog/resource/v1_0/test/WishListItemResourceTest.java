/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
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

		_accountEntry = AccountEntryLocalServiceUtil.addAccountEntry(
			_user.getUserId(), AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null,
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS, 1, serviceContext);

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), RandomTestUtil.randomString());
		_commerceCPInstance1 = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);
		_commerceCPInstance2 = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.ONE);
		_commerceCPDefinition1 = _commerceCPInstance1.getCPDefinition();
		_commerceCPDefinition2 = _commerceCPInstance2.getCPDefinition();
		_commerceWishList =
			CommerceWishListLocalServiceUtil.addCommerceWishList(
				RandomTestUtil.randomString(), RandomTestUtil.randomBoolean(),
				serviceContext);
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

		return wishListItemResource.postWishlistWishListWishListItem(
			_commerceWishList.getCommerceWishListId(),
			_accountEntry.getAccountEntryId(), randomWishListItem());
	}

	@Override
	protected Long testDeleteWishListItem_getAccountId() throws Exception {
		return _accountEntry.getAccountEntryId();
	}

	@Override
	protected WishListItem testGetWishListItem_addWishListItem()
		throws Exception {

		return wishListItemResource.postWishlistWishListWishListItem(
			_commerceWishList.getCommerceWishListId(),
			_accountEntry.getAccountEntryId(), randomWishListItem());
	}

	@Override
	protected WishListItem
			testGetWishlistWishListWishListItemsPage_addWishListItem(
				Long wishListId, WishListItem wishListItem)
		throws Exception {

		return wishListItemResource.postWishlistWishListWishListItem(
			wishListId, _accountEntry.getAccountEntryId(), wishListItem);
	}

	@Override
	protected Long testGetWishlistWishListWishListItemsPage_getWishListId()
		throws Exception {

		return _commerceWishList.getCommerceWishListId();
	}

	@Override
	protected WishListItem testGraphQLWishListItem_addWishListItem()
		throws Exception {

		return wishListItemResource.postWishlistWishListWishListItem(
			_commerceWishList.getCommerceWishListId(),
			_accountEntry.getAccountEntryId(), randomWishListItem());
	}

	@Override
	protected WishListItem testPostWishlistWishListWishListItem_addWishListItem(
			WishListItem wishListItem)
		throws Exception {

		return wishListItemResource.postWishlistWishListWishListItem(
			_commerceWishList.getCommerceWishListId(),
			_accountEntry.getAccountEntryId(), wishListItem);
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

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