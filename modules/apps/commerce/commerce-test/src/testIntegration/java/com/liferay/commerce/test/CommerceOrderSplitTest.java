/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.constants.CommerceObjectActionExecutorConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.test.util.CommerceDiscountTestUtil;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.payment.test.util.TestCommercePaymentMethod;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.test.util.CommercePriceEntryTestUtil;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.object.action.trigger.ObjectActionTriggerRegistry;
import com.liferay.object.constants.ObjectActionConstants;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Crescenzo Rega
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
@Sync
public class CommerceOrderSplitTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_user = UserTestUtil.addUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId());

		_accountEntry = CommerceAccountTestUtil.addBusinessAccountEntry(
			_serviceContext.getUserId(), "Test Business Account", null, null,
			new long[] {_user.getUserId()}, null, _serviceContext);

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				_serviceContext);

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		CommerceTestUtil.addWarehouseCommerceChannelRel(
			_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		_commerceShippingMethod =
			CommerceTestUtil.addFixedRateCommerceShippingMethod(
				_user.getUserId(), _commerceChannel.getGroupId(),
				BigDecimal.ONE);

		_commerceCatalog1 = CommerceTestUtil.addCommerceCatalog(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId(),
			_commerceCurrency.getCode());

		_cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog1.getGroupId(), BigDecimal.valueOf(75),
			RandomTestUtil.randomString());

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), _commerceInventoryWarehouse, BigDecimal.ZERO,
			_cpInstance1.getSku(), StringPool.BLANK);

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(
			_cpInstance1.getCPDefinition());

		_commerceCatalog2 = CommerceTestUtil.addCommerceCatalog(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId(),
			_commerceCurrency.getCode());

		_cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			_commerceCatalog2.getGroupId(), BigDecimal.valueOf(25),
			RandomTestUtil.randomString());

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), _commerceInventoryWarehouse, BigDecimal.ZERO,
			RandomTestUtil.randomString(), StringPool.BLANK);

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(
			_cpInstance2.getCPDefinition());

		_objectAction = _addObjectAction(RandomTestUtil.randomString());
	}

	@After
	public void tearDown() throws Exception {
		for (CommerceOrder commerceOrder : _getCommerceOrders()) {
			_commerceOrderLocalService.deleteCommerceOrder(commerceOrder);
		}
	}

	@Test
	public void testCommerceOrderSplit1() throws Exception {
		AccountEntry accountEntry = _addSupplierAccountEntry(_user);

		_createAndCheckoutCommerceOrder(
			accountEntry, ListUtil.fromArray(_cpInstance1), _user);

		List<CommerceOrder> commerceOrders = _getCommerceOrders();

		Assert.assertEquals(
			commerceOrders.toString(), 2, commerceOrders.size());
	}

	@Test
	public void testCommerceOrderSplit2() throws Exception {
		CommerceDiscountTestUtil.addPercentageCommerceDiscount(
			_group.getGroupId(), BigDecimal.valueOf(10),
			CommerceDiscountConstants.LEVEL_L1,
			CommerceDiscountConstants.TARGET_TOTAL, null);

		CPDefinition bundleCPDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog2.getGroupId(), SimpleCPTypeConstants.NAME, true,
			false);

		CPOption dynamicPriceTypeCPOption1 = CPTestUtil.addCPOption(
			_commerceCatalog2.getGroupId(),
			CPTestUtil.getDefaultCommerceOptionTypeKey(true), true);

		CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
			_commerceCatalog1.getGroupId(),
			bundleCPDefinition.getCPDefinitionId(),
			_cpInstance1.getCPInstanceId(),
			dynamicPriceTypeCPOption1.getCPOptionId(),
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC,
			BigDecimal.valueOf(50), BigDecimal.ONE, true, true,
			_serviceContext);

		CPOption dynamicPriceTypeCPOption2 = CPTestUtil.addCPOption(
			_commerceCatalog2.getGroupId(),
			CPTestUtil.getDefaultCommerceOptionTypeKey(true), true);

		CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
			_commerceCatalog2.getGroupId(),
			bundleCPDefinition.getCPDefinitionId(),
			_cpInstance2.getCPInstanceId(),
			dynamicPriceTypeCPOption2.getCPOptionId(),
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC,
			BigDecimal.valueOf(100), BigDecimal.ONE, true, true,
			_serviceContext);

		_cpInstanceLocalService.buildCPInstances(
			bundleCPDefinition.getCPDefinitionId(), _serviceContext);

		List<CPInstance> bundleCPInstances =
			bundleCPDefinition.getCPInstances();

		Assert.assertEquals(
			bundleCPInstances.toString(), 1, bundleCPInstances.size());

		CPInstance cpInstance3 = bundleCPInstances.get(0);

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				cpInstance3.getGroupId());

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, bundleCPDefinition.getCProductId(),
			cpInstance3.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), BigDecimal.TEN);

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), _commerceInventoryWarehouse, BigDecimal.TEN,
			cpInstance3.getSku(), StringPool.BLANK);

		_createAndCheckoutCommerceOrder(
			_accountEntry, ListUtil.fromArray(cpInstance3), _user);

		List<CommerceOrder> commerceOrders = _getCommerceOrders();

		Assert.assertEquals(
			commerceOrders.toString(), 3, commerceOrders.size());

		_assertCommerceOrder(
			commerceOrders, BigDecimal.valueOf(7.5), 1, _cpInstance1);
		_assertCommerceOrder(
			commerceOrders, BigDecimal.valueOf(3.5), 2, _cpInstance2,
			cpInstance3);
		_assertCommerceOrder(
			commerceOrders, BigDecimal.valueOf(11), 3, _cpInstance1,
			_cpInstance2, cpInstance3);

		CommerceOrderItem commerceOrderItem = _getCommerceOrderItem(
			commerceOrders, _cpInstance1, 1);

		commerceOrderItem.setFinalPrice(BigDecimal.ONE);
		commerceOrderItem.setUnitPrice(BigDecimal.ONE);

		_commerceOrderItemLocalService.updateCommerceOrderItem(
			commerceOrderItem);

		commerceOrderItem = _getCommerceOrderItem(
			_getCommerceOrders(), _cpInstance1, 3);

		Assert.assertTrue(
			BigDecimalUtil.eq(
				commerceOrderItem.getFinalPrice(), BigDecimal.ONE));
		Assert.assertTrue(
			BigDecimalUtil.eq(
				commerceOrderItem.getUnitPrice(), BigDecimal.ONE));
	}

	@Test
	public void testCommerceOrderSplit3() throws Exception {
		_objectAction = _addObjectAction(RandomTestUtil.randomString());

		CommerceDiscountTestUtil.addPercentageCommerceDiscount(
			_group.getGroupId(), BigDecimal.valueOf(10),
			CommerceDiscountConstants.LEVEL_L1,
			CommerceDiscountConstants.TARGET_TOTAL,
			_cpInstance1.getCPDefinitionId());

		_createAndCheckoutCommerceOrder(
			_accountEntry, ListUtil.fromArray(_cpInstance1, _cpInstance2),
			_user);

		List<CommerceOrder> commerceOrders = _getCommerceOrders();

		Assert.assertEquals(
			commerceOrders.toString(), 3, commerceOrders.size());

		_assertCommerceOrder(
			commerceOrders, BigDecimal.valueOf(2.5), BigDecimal.valueOf(22.5));
		_assertCommerceOrder(
			commerceOrders, BigDecimal.valueOf(7.5), BigDecimal.valueOf(67.5));
		_assertCommerceOrder(
			commerceOrders, BigDecimal.valueOf(10), BigDecimal.valueOf(90));
	}

	@Test
	public void testCommerceOrderSplit4() throws Exception {
		User user = UserTestUtil.addUser();

		AccountEntry accountEntry = _addSupplierAccountEntry(user);

		_createAndCheckoutCommerceOrder(
			accountEntry, ListUtil.fromArray(_cpInstance1, _cpInstance2), user);

		CommerceOrderItem commerceOrderItem = _getCommerceOrderItem(
			_getCommerceOrders(), _cpInstance1, 1);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, PermissionCheckerFactoryUtil.create(user))) {

			commerceOrderItem.setQuantity(BigDecimal.valueOf(3));

			commerceOrderItem =
				_commerceOrderItemLocalService.updateCommerceOrderItem(
					commerceOrderItem);
		}

		commerceOrderItem = _getCommerceOrderItem(
			_getCommerceOrders(), _cpInstance1, 2);

		Assert.assertTrue(
			BigDecimalUtil.eq(
				commerceOrderItem.getQuantity(), BigDecimal.valueOf(3)));

		commerceOrderItem.setQuantity(BigDecimal.valueOf(6));

		_commerceOrderItemLocalService.updateCommerceOrderItem(
			commerceOrderItem);

		commerceOrderItem = _getCommerceOrderItem(
			_getCommerceOrders(), _cpInstance1, 1);

		Assert.assertTrue(
			BigDecimalUtil.eq(
				commerceOrderItem.getQuantity(), BigDecimal.valueOf(3)));
	}

	private ObjectAction _addObjectAction(String externalReferenceCode)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_COMMERCE_ORDER", _user.getCompanyId());

		return _objectActionLocalService.addObjectAction(
			null, _serviceContext.getUserId(),
			objectDefinition.getObjectDefinitionId(), true, "orderStatus = 10",
			RandomTestUtil.randomString(), null,
			HashMapBuilder.put(
				_serviceContext.getLocale(), RandomTestUtil.randomString()
			).build(),
			externalReferenceCode,
			CommerceObjectActionExecutorConstants.
				KEY_SPLIT_COMMERCE_ORDER_BY_CATALOG,
			"liferay/commerce_order_status",
			UnicodePropertiesBuilder.put(
				"objectDefinitionId", objectDefinition.getObjectDefinitionId()
			).build(),
			false);
	}

	private AccountEntry _addSupplierAccountEntry(User user) throws Exception {
		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			StringPool.BLANK, user.getUserId(), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null,
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_TYPE_SUPPLIER,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);

		_commerceCatalog1.setAccountEntryId(accountEntry.getAccountEntryId());

		_commerceCatalog1 = _commerceCatalogLocalService.updateCommerceCatalog(
			_commerceCatalog1);

		_commerceChannel.setAccountEntryId(accountEntry.getAccountEntryId());

		_commerceChannel = _commerceChannelLocalService.updateCommerceChannel(
			_commerceChannel);

		return accountEntry;
	}

	private void _assertCommerceOrder(
		List<CommerceOrder> commerceOrders,
		BigDecimal expectedTotalDiscountAmount, BigDecimal total) {

		CommerceOrder commerceOrder = null;

		for (CommerceOrder curCommerceOrder : commerceOrders) {
			if (BigDecimalUtil.eq(curCommerceOrder.getTotal(), total)) {
				commerceOrder = curCommerceOrder;
			}
		}

		Assert.assertTrue(
			BigDecimalUtil.eq(
				commerceOrder.getTotalDiscountAmount(),
				expectedTotalDiscountAmount));
	}

	private void _assertCommerceOrder(
		List<CommerceOrder> commerceOrders,
		BigDecimal expectedTotalDiscountAmount, int size,
		CPInstance... cpInstances) {

		CommerceOrder commerceOrder = null;

		for (CommerceOrder curCommerceOrder : commerceOrders) {
			List<CommerceOrderItem> commerceOrderItems =
				curCommerceOrder.getCommerceOrderItems();

			if (commerceOrderItems.size() == size) {
				commerceOrder = curCommerceOrder;
			}
		}

		Assert.assertTrue(
			BigDecimalUtil.eq(
				commerceOrder.getTotalDiscountAmount(),
				expectedTotalDiscountAmount));

		for (CPInstance cpInstance : cpInstances) {
			List<CommerceOrderItem> commerceOrderItems = ListUtil.filter(
				commerceOrder.getCommerceOrderItems(),
				commerceOrderItem -> StringUtil.equals(
					commerceOrderItem.getSku(), cpInstance.getSku()));

			CommerceOrderItem commerceOrderItem = commerceOrderItems.get(0);

			Assert.assertEquals(
				cpInstance.getCPDefinitionId(),
				commerceOrderItem.getCPDefinitionId());
			Assert.assertEquals(
				cpInstance.getCPInstanceId(),
				commerceOrderItem.getCPInstanceId());
			Assert.assertTrue(
				BigDecimalUtil.eq(
					BigDecimal.ONE, commerceOrderItem.getQuantity()));

			CommercePriceList commercePriceList =
				_commercePriceListLocalService.
					fetchCatalogBaseCommercePriceList(cpInstance.getGroupId());

			CommercePriceEntry commercePriceEntry =
				_commercePriceEntryLocalService.fetchCommercePriceEntry(
					commercePriceList.getCommercePriceListId(),
					cpInstance.getCPInstanceUuid(), null);

			Assert.assertTrue(
				BigDecimalUtil.eq(
					commercePriceEntry.getPrice(),
					commerceOrderItem.getUnitPrice()));
		}
	}

	private void _createAndCheckoutCommerceOrder(
			AccountEntry accountEntry, List<CPInstance> cpInstances, User user)
		throws Exception {

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), user.getUserId());

		Role role = _roleLocalService.getRole(
			_group.getCompanyId(),
			AccountRoleConstants.ROLE_NAME_ACCOUNT_BUYER);

		_userGroupRoleLocalService.addUserGroupRole(
			user.getUserId(), accountEntry.getAccountEntryGroupId(),
			role.getRoleId());

		CommerceOrder commerceOrder = null;

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, PermissionCheckerFactoryUtil.create(user))) {

			commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
				_commerceChannel.getSiteGroupId(), user.getUserId(),
				accountEntry.getAccountEntryId(),
				_commerceCurrency.getCommerceCurrencyId());

			for (CPInstance cpInstance : cpInstances) {
				CommerceTestUtil.addCommerceOrderItem(
					commerceOrder.getCommerceOrderId(),
					cpInstance.getCPInstanceId(), BigDecimal.ONE);
			}

			commerceOrder = _commerceOrderLocalService.fetchCommerceOrder(
				commerceOrder.getCommerceOrderId());

			Country country = CommerceInventoryTestUtil.addCountry(
				null, _serviceContext);

			Region region = CommerceInventoryTestUtil.addRegion(
				null, country.getCountryId(), _serviceContext);

			Address address = _addressLocalService.addAddress(
				RandomTestUtil.randomString(), user.getUserId(),
				AccountEntry.class.getName(), accountEntry.getAccountEntryId(),
				country.getCountryId(), 0, region.getRegionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				false, RandomTestUtil.randomString(), true,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				_serviceContext);

			commerceOrder.setBillingAddressId(address.getAddressId());

			commerceOrder.setCommerceShippingMethodId(
				_commerceShippingMethod.getCommerceShippingMethodId());

			commerceOrder.setShippingAddressId(address.getAddressId());

			commerceOrder.setCommercePaymentMethodKey(
				TestCommercePaymentMethod.KEY);

			commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
				commerceOrder);

			commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, user.getUserId());
		}

		_commerceOrderEngine.transitionCommerceOrder(
			commerceOrder, CommerceOrderConstants.ORDER_STATUS_PROCESSING,
			_user.getUserId(), true);

		for (int i = 0; i < 3; i++) {
			ObjectAction objectAction =
				_objectActionLocalService.getObjectAction(
					_objectAction.getObjectActionId());

			int status = objectAction.getStatus();

			if ((status == ObjectActionConstants.STATUS_FAILED) ||
				(status == ObjectActionConstants.STATUS_SUCCESS)) {

				Assert.assertEquals(
					ObjectActionConstants.STATUS_SUCCESS, status);

				return;
			}

			Thread.sleep(500);
		}
	}

	private CommerceOrderItem _getCommerceOrderItem(
		List<CommerceOrder> commerceOrders, CPInstance cpInstance, int size) {

		CommerceOrderItem commerceOrderItem = null;

		for (CommerceOrder commerceOrder : commerceOrders) {
			List<CommerceOrderItem> commerceOrderItems =
				commerceOrder.getCommerceOrderItems();

			if (commerceOrderItems.size() != size) {
				continue;
			}

			for (CommerceOrderItem curCommerceOrderItem : commerceOrderItems) {
				if (StringUtil.equals(
						curCommerceOrderItem.getSku(), cpInstance.getSku())) {

					commerceOrderItem = curCommerceOrderItem;
				}
			}
		}

		Assert.assertNotNull(commerceOrderItem);

		return commerceOrderItem;
	}

	private List<CommerceOrder> _getCommerceOrders() {
		return _commerceOrderLocalService.getCommerceOrders(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AddressLocalService _addressLocalService;

	private CommerceCatalog _commerceCatalog1;
	private CommerceCatalog _commerceCatalog2;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	private CommerceCurrency _commerceCurrency;
	private CommerceInventoryWarehouse _commerceInventoryWarehouse;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	private CommerceShippingMethod _commerceShippingMethod;
	private CPInstance _cpInstance1;
	private CPInstance _cpInstance2;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	private Group _group;
	private ObjectAction _objectAction;

	@Inject
	private ObjectActionLocalService _objectActionLocalService;

	@Inject
	private ObjectActionTriggerRegistry _objectActionTriggerRegistry;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private ServiceContext _serviceContext;
	private User _user;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}