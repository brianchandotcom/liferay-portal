/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.cart.resource.v1_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.CartItem;
import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.Price;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class CartItemResourceTest extends BaseCartItemResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				_user.getUserId());

		_accountEntry = CommerceAccountTestUtil.addBusinessAccountEntry(
			_user.getUserId(), "Test Business Account", null, null, null, null,
			serviceContext);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testGroup.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), _commerceCurrency.getCode());

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				serviceContext);

		_commerceOrder = _commerceOrderLocalService.addCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId(), 0);
	}

	@Ignore
	@Override
	@Test
	public void testDeleteCartItem() throws Exception {
	}

	@Override
	@Test
	public void testGetCartItem() throws Exception {
		super.testGetCartItem();

		_testGetCartItemPriceOnApplication();
	}

	@Ignore
	@Override
	@Test
	public void testGetCartItemsPageWithPagination() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteCartItem() throws Exception {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"quantity"};
	}

	@Override
	protected CartItem randomCartItem() throws Exception {
		return _randomCartItem(RandomTestUtil.randomBoolean());
	}

	@Override
	protected CartItem testDeleteCartItem_addCartItem() throws Exception {
		return cartItemResource.postCartItem(
			_commerceOrder.getCommerceOrderId(), randomCartItem());
	}

	@Override
	protected CartItem testGetCartItem_addCartItem() throws Exception {
		return cartItemResource.postCartItem(
			_commerceOrder.getCommerceOrderId(), randomCartItem());
	}

	@Override
	protected CartItem testGetCartItemsPage_addCartItem(
			Long cartId, CartItem cartItem)
		throws Exception {

		return cartItemResource.postCartItem(cartId, cartItem);
	}

	@Override
	protected Long testGetCartItemsPage_getCartId() throws Exception {
		return _commerceOrder.getCommerceOrderId();
	}

	@Override
	protected CartItem testGraphQLCartItem_addCartItem() throws Exception {
		return cartItemResource.postCartItem(
			_commerceOrder.getCommerceOrderId(), randomCartItem());
	}

	@Override
	protected CartItem testPatchCartItem_addCartItem() throws Exception {
		return cartItemResource.postCartItem(
			_commerceOrder.getCommerceOrderId(), randomCartItem());
	}

	@Override
	protected CartItem testPostCartItem_addCartItem(CartItem cartItem)
		throws Exception {

		return cartItemResource.postCartItem(
			_commerceOrder.getCommerceOrderId(), cartItem);
	}

	@Override
	protected CartItem testPutCartItem_addCartItem() throws Exception {
		return cartItemResource.postCartItem(
			_commerceOrder.getCommerceOrderId(), randomCartItem());
	}

	private CPInstance _addCPInstance(boolean priceOnApplication)
		throws Exception {

		CPInstance cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(),
			BigDecimal.valueOf(RandomTestUtil.randomDouble()));

		_cpInstances.add(cpInstance);

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), _commerceInventoryWarehouse, BigDecimal.TEN,
			cpInstance.getSku(), StringPool.BLANK);

		if (priceOnApplication) {
			_updateCommercePriceEntry(
				cpInstance, priceOnApplication,
				CommercePriceListConstants.TYPE_PRICE_LIST);
			_updateCommercePriceEntry(
				cpInstance, priceOnApplication,
				CommercePriceListConstants.TYPE_PROMOTION);
		}

		return cpInstance;
	}

	private CartItem _randomCartItem(boolean priceOnApplication)
		throws Exception {

		CPInstance cpInstance = _addCPInstance(priceOnApplication);

		return new CartItem() {
			{
				quantity = BigDecimal.valueOf(RandomTestUtil.randomInt(1, 10));
				sku = cpInstance.getSku();
				skuId = cpInstance.getCPInstanceId();
			}
		};
	}

	private void _testGetCartItemPriceOnApplication() throws Exception {
		boolean priceOnApplication = RandomTestUtil.randomBoolean();

		CartItem postCartItem = cartItemResource.postCartItem(
			_commerceOrder.getCommerceOrderId(),
			_randomCartItem(priceOnApplication));

		CartItem getCartItem = cartItemResource.getCartItem(
			postCartItem.getId());

		assertEquals(postCartItem, getCartItem);
		assertValid(getCartItem);

		Price price = getCartItem.getPrice();

		Assert.assertEquals(priceOnApplication, price.getPriceOnApplication());
	}

	private void _updateCommercePriceEntry(
		CPInstance cpInstance, boolean priceOnApplication,
		String typePriceList) {

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.getInstanceBaseCommercePriceEntry(
				cpInstance.getCPInstanceUuid(), typePriceList,
				StringPool.BLANK);

		if (commercePriceEntry == null) {
			return;
		}

		commercePriceEntry.setPriceOnApplication(priceOnApplication);

		_commercePriceEntryLocalService.updateCommercePriceEntry(
			commercePriceEntry);
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private CommerceInventoryWarehouse _commerceInventoryWarehouse;

	@DeleteAfterTestRun
	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@DeleteAfterTestRun
	private final List<CPInstance> _cpInstances = new ArrayList<>();

	@DeleteAfterTestRun
	private User _user;

}