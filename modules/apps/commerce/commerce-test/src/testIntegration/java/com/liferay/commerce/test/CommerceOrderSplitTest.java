/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.test;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.constants.CommerceObjectActionExecutorConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
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
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
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
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.List;

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

		CommerceChannel commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		CommerceTestUtil.addWarehouseCommerceChannelRel(
			_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			commerceChannel.getCommerceChannelId());

		_commerceShippingMethod =
			CommerceTestUtil.addFixedRateCommerceShippingMethod(
				_user.getUserId(), commerceChannel.getGroupId(),
				BigDecimal.ONE);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_COMMERCE_ORDER", _user.getCompanyId());

		_objectAction = _objectActionLocalService.addObjectAction(
			null, _serviceContext.getUserId(),
			objectDefinition.getObjectDefinitionId(), true, "orderStatus = 10",
			"This action splits an order into supplier orders by catalog", null,
			HashMapBuilder.put(
				_serviceContext.getLocale(), "Split order by catalog"
			).build(),
			"SplitOrderByCatalog",
			CommerceObjectActionExecutorConstants.
				KEY_SPLIT_COMMERCE_ORDER_BY_CATALOG,
			"liferay/commerce_order_status",
			UnicodePropertiesBuilder.put(
				"objectDefinitionId", objectDefinition.getObjectDefinitionId()
			).build(),
			false);
	}

	@Test
	public void testCommerceOrderSplitWithProductBundle() throws Exception {
		CommerceCatalog commerceCatalog1 = CommerceTestUtil.addCommerceCatalog(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId(),
			_commerceCurrency.getCode());

		CPInstance cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			commerceCatalog1.getGroupId(), BigDecimal.valueOf(5),
			RandomTestUtil.randomString());

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), _commerceInventoryWarehouse, BigDecimal.ZERO,
			cpInstance1.getSku(), StringPool.BLANK);

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(
			cpInstance1.getCPDefinition());

		CommerceCatalog commerceCatalog2 = CommerceTestUtil.addCommerceCatalog(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId(),
			_commerceCurrency.getCode());

		CPInstance cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			commerceCatalog2.getGroupId(), BigDecimal.valueOf(25),
			RandomTestUtil.randomString());

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), _commerceInventoryWarehouse, BigDecimal.ZERO,
			RandomTestUtil.randomString(), StringPool.BLANK);

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(
			cpInstance2.getCPDefinition());

		CPDefinition bundleCPDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			commerceCatalog2.getGroupId(), SimpleCPTypeConstants.NAME, true,
			false);

		CPOption dynamicPriceTypeCPOption1 = CPTestUtil.addCPOption(
			commerceCatalog2.getGroupId(),
			CPTestUtil.getDefaultCommerceOptionTypeKey(true), true);

		CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
			commerceCatalog2.getGroupId(),
			bundleCPDefinition.getCPDefinitionId(),
			cpInstance1.getCPInstanceId(),
			dynamicPriceTypeCPOption1.getCPOptionId(),
			CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC,
			BigDecimal.valueOf(50), BigDecimal.ONE, true, true,
			_serviceContext);

		CPOption dynamicPriceTypeCPOption2 = CPTestUtil.addCPOption(
			commerceCatalog2.getGroupId(),
			CPTestUtil.getDefaultCommerceOptionTypeKey(true), true);

		CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
			commerceCatalog2.getGroupId(),
			bundleCPDefinition.getCPDefinitionId(),
			cpInstance2.getCPInstanceId(),
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

		User user = UserTestUtil.addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), user.getUserId());

		Role role = _roleLocalService.getRole(
			_group.getCompanyId(),
			AccountRoleConstants.ROLE_NAME_ACCOUNT_BUYER);

		_userGroupRoleLocalService.addUserGroupRole(
			user.getUserId(), _accountEntry.getAccountEntryGroupId(),
			role.getRoleId());

		CommerceOrder commerceOrder = null;

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, PermissionCheckerFactoryUtil.create(user))) {

			commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
				_group.getGroupId(), user.getUserId(),
				_accountEntry.getAccountEntryId(),
				_commerceCurrency.getCommerceCurrencyId());

			CommerceTestUtil.addCommerceOrderItem(
				commerceOrder.getCommerceOrderId(),
				cpInstance3.getCPInstanceId(), BigDecimal.valueOf(3));

			commerceOrder = _commerceOrderLocalService.fetchCommerceOrder(
				commerceOrder.getCommerceOrderId());

			Country country = CommerceInventoryTestUtil.addCountry(
				_serviceContext);

			Region region = CommerceInventoryTestUtil.addRegion(
				country.getCountryId(), _serviceContext);

			Address address = _addressLocalService.addAddress(
				RandomTestUtil.randomString(), _user.getUserId(),
				AccountEntry.class.getName(), _accountEntry.getAccountEntryId(),
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
			user.getUserId(), true);

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

		List<CommerceOrder> commerceOrders =
			_commerceOrderLocalService.getCommerceOrders(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceOrders.toString(), 3, commerceOrders.size());

		for (CommerceOrder curCommerceOrder : commerceOrders) {
			List<CommerceOrderItem> commerceOrderItems =
				curCommerceOrder.getCommerceOrderItems();

			if (commerceOrderItems.size() == 1) {
				_assertEquals(commerceOrderItems, cpInstance1);
			}
			else if (commerceOrderItems.size() == 2) {
				_assertEquals(
					ListUtil.filter(
						curCommerceOrder.getCommerceOrderItems(),
						commerceOrderItem -> StringUtil.equals(
							commerceOrderItem.getSku(), cpInstance2.getSku())),
					cpInstance2);

				_assertEquals(
					ListUtil.filter(
						curCommerceOrder.getCommerceOrderItems(),
						commerceOrderItem -> StringUtil.equals(
							commerceOrderItem.getSku(), cpInstance3.getSku())),
					cpInstance3);
			}
			else if (commerceOrderItems.size() == 3) {
				_assertEquals(
					ListUtil.filter(
						curCommerceOrder.getCommerceOrderItems(),
						commerceOrderItem -> StringUtil.equals(
							commerceOrderItem.getSku(), cpInstance1.getSku())),
					cpInstance1);

				_assertEquals(
					ListUtil.filter(
						curCommerceOrder.getCommerceOrderItems(),
						commerceOrderItem -> StringUtil.equals(
							commerceOrderItem.getSku(), cpInstance2.getSku())),
					cpInstance2);

				_assertEquals(
					ListUtil.filter(
						curCommerceOrder.getCommerceOrderItems(),
						commerceOrderItem -> StringUtil.equals(
							commerceOrderItem.getSku(), cpInstance3.getSku())),
					cpInstance3);
			}
			else {
				Assert.assertTrue(false);
			}
		}
	}

	private void _assertEquals(
			List<CommerceOrderItem> commerceOrderItems, CPInstance cpInstance)
		throws Exception {

		CommerceOrderItem commerceOrderItem = commerceOrderItems.get(0);

		Assert.assertEquals(
			cpInstance.getCPDefinitionId(),
			commerceOrderItem.getCPDefinitionId());
		Assert.assertEquals(
			cpInstance.getCPInstanceId(), commerceOrderItem.getCPInstanceId());
		Assert.assertTrue(
			BigDecimalUtil.eq(
				BigDecimal.valueOf(3), commerceOrderItem.getQuantity()));

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				cpInstance.getGroupId());

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceList.getCommercePriceListId(),
				cpInstance.getCPInstanceUuid(), null);

		Assert.assertTrue(
			BigDecimalUtil.eq(
				commercePriceEntry.getPrice(),
				commerceOrderItem.getUnitPrice()));
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AddressLocalService _addressLocalService;

	private CommerceCurrency _commerceCurrency;
	private CommerceInventoryWarehouse _commerceInventoryWarehouse;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	private CommerceShippingMethod _commerceShippingMethod;

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