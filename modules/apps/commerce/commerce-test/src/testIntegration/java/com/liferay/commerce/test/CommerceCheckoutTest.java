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
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.service.CommerceAccountUserRelLocalServiceUtil;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.checkout.helper.CommerceCheckoutStepHttpHelper;
import com.liferay.commerce.constants.CPDefinitionInventoryConstants;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.exception.CommerceOrderGuestCheckoutException;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.service.CommerceAddressLocalServiceUtil;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderLocalServiceUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.test.util.context.TestCommerceContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.CountryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
@Sync
public class CommerceCheckoutTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = CompanyLocalServiceUtil.getCompany(_group.getCompanyId());

		_user = UserTestUtil.addUser(_company);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		_commerceAccount = CommerceAccountTestUtil.addBusinessCommerceAccount(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), new long[] {_user.getUserId()}, null,
			_serviceContext);

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				_commerceChannel.getGroupId(),
				CommerceConstants.SERVICE_NAME_COMMERCE_ORDER));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.setValue(
			"guestCheckoutEnabled", Boolean.TRUE.toString());

		modifiableSettings.store();
	}

	@Test
	public void testGuestUserCheckout() throws Exception {
		frutillaRule.scenario(
			"When a guest creates an order and the channel has guest " +
				"checkout enabled, the guest should be able to checkout the " +
					"order"
		).given(
			"An order created by an un-authenticated user"
		).and(
			"Guest Checkout has been enabled on the channel"
		).when(
			"The guest tries to checkout the order"
		).then(
			"The guest should be able to place the order"
		);

		User user = _company.getDefaultUser();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.getGuestCommerceAccount(
				_company.getCompanyId());

		CommerceOrder commerceOrder =
			CommerceOrderLocalServiceUtil.addCommerceOrder(
				user.getUserId(), _commerceChannel.getGroupId(),
				commerceAccount.getCommerceAccountId(),
				_commerceCurrency.getCommerceCurrencyId());

		CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			commerceOrder, commerceOrder.getUserId(), false);

		commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			commerceOrder, user.getUserId());

		Assert.assertEquals(
			commerceOrder.getOrderStatus(),
			CommerceOrderConstants.ORDER_STATUS_PENDING);
		Assert.assertTrue(commerceOrder.isGuestOrder());
	}

	@Test
	public void testGuestUserCheckoutFromAnotherGuestUser() throws Exception {
		frutillaRule.scenario(
			"When a guest creates an order other guests should not be able " +
				"to see that order"
		).given(
			"An order created by an un-authenticated user"
		).and(
			"Guest Checkout has been enabled on the channel"
		).when(
			"Another guest user tries to checkout that order"
		).then(
			"A Permission exception should be thrown"
		);

		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_company.getCompanyId(), _group.getGroupId(),
					_user.getUserId());

			User user1 = UserTestUtil.addUser(_company);

			serviceContext.setUserId(user1.getUserId());

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user1);

			PrincipalThreadLocal.setName(user1.getUserId());

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			CommerceAccount commerceAccount =
				_commerceAccountLocalService.addCommerceAccount(
					RandomTestUtil.randomString(),
					CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
					user1.getEmailAddress(), StringPool.BLANK,
					CommerceAccountConstants.ACCOUNT_TYPE_GUEST, true, null,
					serviceContext);

			Role role = RoleTestUtil.addRole(
				CommerceAccountConstants.ROLE_NAME_ACCOUNT_ADMINISTRATOR,
				RoleConstants.TYPE_SITE, "com.liferay.commerce.order",
				ResourceConstants.SCOPE_GROUP_TEMPLATE,
				String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
				CommerceOrderActionKeys.CHECKOUT_OPEN_COMMERCE_ORDERS);

			CommerceAccountUserRelLocalServiceUtil.addCommerceAccountUserRel(
				commerceAccount.getCommerceAccountId(), user1.getUserId(),
				new long[] {role.getRoleId()}, serviceContext);

			CommerceOrder commerceOrder =
				CommerceOrderLocalServiceUtil.addCommerceOrder(
					user1.getUserId(), _commerceChannel.getGroupId(),
					commerceAccount.getCommerceAccountId(),
					_commerceCurrency.getCommerceCurrencyId());

			CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
				commerceOrder, commerceOrder.getUserId(), false);

			User user2 = UserTestUtil.addUser(_company);

			permissionChecker = PermissionCheckerFactoryUtil.create(user2);

			PrincipalThreadLocal.setName(user2.getUserId());

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			_commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, user2.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				PrincipalException.MustHavePermission.class,
				throwable.getClass());
		}
	}

	@Test
	public void testGuestUserCheckoutWithGuestCheckoutDisabled()
		throws Exception {

		frutillaRule.scenario(
			"When a guest creates an order and the channel does not have " +
				"guest checkout enabled, the guest cart should not be " +
					"allowed to be checked out"
		).given(
			"An order created by an un-authenticated user"
		).and(
			"Guest Checkout has not been enabled on the channel"
		).when(
			"The guest tries to checkout the order"
		).then(
			"The guest should be able to place the order"
		);

		try {
			Settings settings = _settingsFactory.getSettings(
				new GroupServiceSettingsLocator(
					_commerceChannel.getGroupId(),
					CommerceConstants.SERVICE_NAME_COMMERCE_ORDER));

			ModifiableSettings modifiableSettings =
				settings.getModifiableSettings();

			modifiableSettings.setValue(
				"guestCheckoutEnabled", Boolean.FALSE.toString());

			modifiableSettings.store();

			User user = _company.getDefaultUser();

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			PrincipalThreadLocal.setName(user.getUserId());

			CommerceAccount commerceAccount =
				_commerceAccountLocalService.getGuestCommerceAccount(
					_company.getCompanyId());

			CommerceOrder commerceOrder =
				CommerceOrderLocalServiceUtil.addCommerceOrder(
					user.getUserId(), _commerceChannel.getGroupId(),
					commerceAccount.getCommerceAccountId(),
					_commerceCurrency.getCommerceCurrencyId());

			CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
				commerceOrder, commerceOrder.getUserId(), false);

			_commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, user.getUserId());

			Assert.assertNotEquals(
				commerceOrder.getOrderStatus(),
				CommerceOrderConstants.ORDER_STATUS_PENDING);
			Assert.assertTrue(commerceOrder.isGuestOrder());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderGuestCheckoutException.class,
				throwable.getClass());
		}
	}

	@Test
	public void testIsActiveBillingAddressCommerceCheckoutStepNoAddress()
		throws Exception {

		frutillaRule.scenario(
			"Check if BillingAddressCommerceCheckoutStep is active "
		).given(
			"An Order"
		).when(
			"Order does not have any addresses (no shipping address, no " +
				"billing address)"
		).then(
			"BillingAddressCommerceCheckoutStep is active"
		);

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceAccount, null);

		httpServletRequest.setAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT, commerceContext);

		User user = _company.getDefaultUser();

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.getGuestCommerceAccount(
				_company.getCompanyId());

		CommerceOrder commerceOrder =
			CommerceOrderLocalServiceUtil.addCommerceOrder(
				user.getUserId(), _commerceChannel.getGroupId(),
				commerceAccount.getCommerceAccountId(),
				_commerceCurrency.getCommerceCurrencyId());

		boolean activeBillingAddressCommerceCheckoutStep =
			_commerceCheckoutStepHttpHelper.
				isActiveBillingAddressCommerceCheckoutStep(
					httpServletRequest, commerceOrder);

		Assert.assertTrue(activeBillingAddressCommerceCheckoutStep);
	}

	@Test
	public void testIsActiveBillingAddressCommerceCheckoutStepSameShippingAndBillingAddress()
		throws Exception {

		frutillaRule.scenario(
			"Check if BillingAddressCommerceCheckoutStep is active "
		).given(
			"An Order"
		).when(
			"Order has one address as shipping address and the same address " +
				"as billing address"
		).then(
			"BillingAddressCommerceCheckoutStep is false"
		);

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceAccount, null);

		httpServletRequest.setAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT, commerceContext);

		User user = _company.getDefaultUser();

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.getGuestCommerceAccount(
				_company.getCompanyId());

		CommerceOrder commerceOrder =
			CommerceOrderLocalServiceUtil.addCommerceOrder(
				user.getUserId(), _commerceChannel.getGroupId(),
				commerceAccount.getCommerceAccountId(),
				_commerceCurrency.getCommerceCurrencyId());

		CommerceAddress commerceAddress = addCommerceAddress(
			commerceOrder,
			CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING);

		commerceOrder.setBillingAddressId(
			commerceAddress.getCommerceAddressId());
		commerceOrder.setShippingAddressId(
			commerceAddress.getCommerceAddressId());

		commerceOrder = CommerceOrderLocalServiceUtil.updateCommerceOrder(
			commerceOrder);

		boolean activeBillingAddressCommerceCheckoutStep =
			_commerceCheckoutStepHttpHelper.
				isActiveBillingAddressCommerceCheckoutStep(
					httpServletRequest, commerceOrder);

		Assert.assertFalse(activeBillingAddressCommerceCheckoutStep);
	}

	@Test
	public void testIsActiveBillingAddressCommerceCheckoutStepShippingAddressOnly()
		throws Exception {

		frutillaRule.scenario(
			"Check if BillingAddressCommerceCheckoutStep is active "
		).given(
			"An Order"
		).when(
			"Order has one address as shipping address and no address as " +
				"billing address"
		).then(
			"BillingAddressCommerceCheckoutStep is true"
		);

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceAccount, null);

		httpServletRequest.setAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT, commerceContext);

		User user = _company.getDefaultUser();

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.getGuestCommerceAccount(
				_company.getCompanyId());

		CommerceOrder commerceOrder =
			CommerceOrderLocalServiceUtil.addCommerceOrder(
				user.getUserId(), _commerceChannel.getGroupId(),
				commerceAccount.getCommerceAccountId(),
				_commerceCurrency.getCommerceCurrencyId());

		CommerceAddress commerceAddress = addCommerceAddress(
			commerceOrder, CommerceAddressConstants.ADDRESS_TYPE_SHIPPING);

		commerceOrder.setShippingAddressId(
			commerceAddress.getCommerceAddressId());

		commerceOrder = CommerceOrderLocalServiceUtil.updateCommerceOrder(
			commerceOrder);

		boolean activeBillingAddressCommerceCheckoutStep =
			_commerceCheckoutStepHttpHelper.
				isActiveBillingAddressCommerceCheckoutStep(
					httpServletRequest, commerceOrder);

		Assert.assertTrue(activeBillingAddressCommerceCheckoutStep);
	}

	@Test
	public void testIsActivePaymentMethodCommerceCheckoutStepNoPaymentMethodGroupRels()
		throws Exception {

		frutillaRule.scenario(
			"Check if PaymentMethodCommerceCheckoutStep is active "
		).given(
			"An Order"
		).when(
			"Order has no PaymentMethodGroupRel  "
		).then(
			"PaymentMethodCommerceCheckoutStep is false"
		);

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceAccount, null);

		httpServletRequest.setAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT, commerceContext);

		User user = _company.getDefaultUser();

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.getGuestCommerceAccount(
				_company.getCompanyId());

		CommerceOrder commerceOrder =
			CommerceOrderLocalServiceUtil.addCommerceOrder(
				user.getUserId(), _commerceChannel.getGroupId(),
				commerceAccount.getCommerceAccountId(),
				_commerceCurrency.getCommerceCurrencyId());

		boolean activePaymentMethod =
			_commerceCheckoutStepHttpHelper.
				isActivePaymentMethodCommerceCheckoutStep(
					httpServletRequest, commerceOrder);

		Assert.assertFalse(activePaymentMethod);
	}

	@Test
	public void testIsActivePaymentMethodCommerceCheckoutStepNoPaymentMethods()
		throws Exception {

		frutillaRule.scenario(
			"Check if PaymentMethodCommerceCheckoutStep is active "
		).given(
			"An Order"
		).when(
			"Order has no PaymentMethod "
		).then(
			"PaymentMethodCommerceCheckoutStep is false"
		);

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceAccount, null);

		httpServletRequest.setAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT, commerceContext);

		User user = _company.getDefaultUser();

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.getGuestCommerceAccount(
				_company.getCompanyId());

		CommerceOrder commerceOrder =
			CommerceOrderLocalServiceUtil.addCommerceOrder(
				user.getUserId(), _commerceChannel.getGroupId(),
				commerceAccount.getCommerceAccountId(),
				_commerceCurrency.getCommerceCurrencyId());

		CommerceTestUtil.addCommercePaymentMethodGroupRel(
			user.getUserId(), commerceOrder.getGroupId());

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		BigDecimal price = BigDecimal.valueOf(RandomTestUtil.randomDouble());

		CPInstance cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			catalog.getGroupId(), price);

		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance.getCPInstanceId(), 1,
			commerceContext);

		boolean activePaymentMethod =
			_commerceCheckoutStepHttpHelper.
				isActivePaymentMethodCommerceCheckoutStep(
					httpServletRequest, commerceOrder);

		Assert.assertFalse(activePaymentMethod);
	}

	@Test
	public void testIsActivePaymentMethodCommerceCheckoutStepZeroPrice()
		throws Exception {

		frutillaRule.scenario(
			"Check if PaymentMethodCommerceCheckoutStep is active "
		).given(
			"An Order"
		).when(
			"Order price is zero "
		).then(
			"PaymentMethodCommerceCheckoutStep is false"
		);

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceAccount, null);

		httpServletRequest.setAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT, commerceContext);

		User user = _company.getDefaultUser();

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.getGuestCommerceAccount(
				_company.getCompanyId());

		CommerceOrder commerceOrder =
			CommerceOrderLocalServiceUtil.addCommerceOrder(
				user.getUserId(), _commerceChannel.getGroupId(),
				commerceAccount.getCommerceAccountId(),
				_commerceCurrency.getCommerceCurrencyId());

		CommerceTestUtil.addCommercePaymentMethodGroupRel(
			user.getUserId(), commerceOrder.getGroupId());

		boolean activePaymentMethod =
			_commerceCheckoutStepHttpHelper.
				isActivePaymentMethodCommerceCheckoutStep(
					httpServletRequest, commerceOrder);

		Assert.assertFalse(activePaymentMethod);
	}

	@Test
	public void testLowStockActivityOnCheckout() throws Exception {
		frutillaRule.scenario(
			"Use the Order Engine to place an Order containing a Product " +
				"with low stock activity"
		).given(
			"An Order"
		).and(
			"A Product with low stock activity"
		).when(
			"We checkout the order"
		).then(
			"The product execute its low stock activity"
		);

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_commerceAccount.getCommerceAccountId(),
			_commerceCurrency.getCommerceCurrencyId());

		commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			commerceOrder, _user.getUserId(), false);

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		CommerceOrderItem commerceOrderItem = commerceOrderItems.get(0);

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			commerceOrderItem.getCPDefinitionId());

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpDefinition.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			_cpDefinitionInventoryLocalService.addCPDefinitionInventory(
				_user.getUserId(), cpDefinition.getCPDefinitionId(), "default",
				"default", false, false, 1, false, 0,
				CPDefinitionInventoryConstants.DEFAULT_MAX_ORDER_QUANTITY, null,
				1);
		}
		else {
			_cpDefinitionInventoryLocalService.updateCPDefinitionInventory(
				cpDefinitionInventory.getCPDefinitionInventoryId(), "default",
				"default", false, false, 1, false, 0,
				CPDefinitionInventoryConstants.DEFAULT_MAX_ORDER_QUANTITY, null,
				1);
		}

		int stockQuantity = _commerceInventoryEngine.getStockQuantity(
			_company.getCompanyId(), commerceOrderItem.getSku());

		commerceOrderItem.setQuantity(stockQuantity);

		_commerceOrderItemLocalService.updateCommerceOrderItem(
			commerceOrderItem);

		_commerceOrderEngine.checkoutCommerceOrder(
			commerceOrder, _user.getUserId());

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			commerceOrderItem.getCPInstanceId());

		Assert.assertEquals(cpInstance.isPublished(), false);
	}

	@Test
	public void testUserCheckout() throws Exception {
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

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		PrincipalThreadLocal.setName(_user.getUserId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId());

		CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			commerceOrder, commerceOrder.getUserId(), false);

		commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			commerceOrder, _user.getUserId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, commerceOrder.getStatus());

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		BigDecimal expectedSubtotal = BigDecimal.ZERO;

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

			if (cpInstance == null) {
				continue;
			}

			CommercePriceList commercePriceList =
				_commercePriceListLocalService.getCatalogBaseCommercePriceList(
					cpInstance.getGroupId());

			CommercePriceEntry commercePriceEntry =
				_commercePriceEntryLocalService.fetchCommercePriceEntry(
					commercePriceList.getCommercePriceListId(),
					cpInstance.getCPInstanceUuid());

			BigDecimal price = commercePriceEntry.getPrice();

			BigDecimal totalItemPrice = price.multiply(
				BigDecimal.valueOf(commerceOrderItem.getQuantity()));

			expectedSubtotal = expectedSubtotal.add(totalItemPrice);
		}

		BigDecimal actualSubtotal = commerceOrder.getSubtotal();

		Assert.assertEquals(
			expectedSubtotal.doubleValue(), actualSubtotal.doubleValue(),
			0.0001);

		BigDecimal expectedTotal = expectedSubtotal.add(
			commerceOrder.getShippingAmount());

		BigDecimal actualTotal = commerceOrder.getTotal();

		Assert.assertEquals(
			expectedTotal.doubleValue(), actualTotal.doubleValue(), 0.0001);
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	protected CommerceAddress addCommerceAddress(
			CommerceOrder commerceOrder, int addressType)
		throws Exception {

		String a2 = RandomTestUtil.randomString(2);
		String a3 = RandomTestUtil.randomString(3);

		Country country = CountryLocalServiceUtil.fetchCountryByA2(
			commerceOrder.getCompanyId(), a2);

		if (country == null) {
			country = CountryLocalServiceUtil.fetchCountryByA3(
				commerceOrder.getCompanyId(), a3);
		}

		if (country == null) {
			country = CountryLocalServiceUtil.addCountry(
				a2, a3, true, true, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				0D, true, true, false, _serviceContext);
		}

		return CommerceAddressLocalServiceUtil.addCommerceAddress(
			CommerceAccount.class.getName(),
			commerceOrder.getCommerceAccountId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0,
			country.getCountryId(), RandomTestUtil.randomString(), addressType,
			_serviceContext);
	}

	private static Company _company;
	private static User _user;

	private CommerceAccount _commerceAccount;

	@Inject
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceCheckoutStepHttpHelper _commerceCheckoutStepHttpHelper;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceInventoryEngine _commerceInventoryEngine;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Inject
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	@Inject
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Inject
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	private Group _group;
	private ServiceContext _serviceContext;

	@Inject
	private SettingsFactory _settingsFactory;

}