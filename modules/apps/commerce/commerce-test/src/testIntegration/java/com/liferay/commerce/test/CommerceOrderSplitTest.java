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
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
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

		_activeObjectAction();

		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@After
	public void tearDown() throws Exception {
		if (_objectActionActivated) {
			_objectActionLocalService.updateActive(_objectAction, false);
		}

		if (_objectActionCreated) {
			_objectActionLocalService.deleteObjectAction(_objectAction);
		}

		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testCanCreateOrderUsingProductBundleCorrectly()
		throws Exception {

		CommerceCatalog commerceCatalog1 = CommerceTestUtil.addCommerceCatalog(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId(),
			_commerceCurrency.getCode());

		_cpInstance1 = CPTestUtil.addCPInstanceFromCatalog(
			commerceCatalog1.getGroupId(), BigDecimal.valueOf(5),
			RandomTestUtil.randomString());

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), _commerceInventoryWarehouse, BigDecimal.ZERO,
			_cpInstance1.getSku(), StringPool.BLANK);

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(
			_cpInstance1.getCPDefinition());

		CommerceCatalog commerceCatalog2 = CommerceTestUtil.addCommerceCatalog(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId(),
			_commerceCurrency.getCode());

		_cpInstance2 = CPTestUtil.addCPInstanceFromCatalog(
			commerceCatalog2.getGroupId(), BigDecimal.valueOf(25),
			RandomTestUtil.randomString());

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), _commerceInventoryWarehouse, BigDecimal.ZERO,
			RandomTestUtil.randomString(), StringPool.BLANK);

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(
			_cpInstance2.getCPDefinition());

		CPDefinition bundleCPDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			commerceCatalog2.getGroupId(), SimpleCPTypeConstants.NAME, true,
			false);

		CPOption dynamicPriceTypeCPOption1 = CPTestUtil.addCPOption(
			commerceCatalog2.getGroupId(),
			CPTestUtil.getDefaultCommerceOptionTypeKey(true), true);

		CPTestUtil.addCPDefinitionOptionValueRelWithPrice(
			commerceCatalog2.getGroupId(),
			bundleCPDefinition.getCPDefinitionId(),
			_cpInstance1.getCPInstanceId(),
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

		_cpInstance3 = bundleCPInstances.get(0);

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				_cpInstance3.getGroupId());

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			StringPool.BLANK, bundleCPDefinition.getCProductId(),
			_cpInstance3.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), BigDecimal.TEN);

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), _commerceInventoryWarehouse, BigDecimal.TEN,
			_cpInstance3.getSku(), StringPool.BLANK);

		User user = UserTestUtil.addUser();

		PrincipalThreadLocal.setName(user.getUserId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), user.getUserId());

		Role role = _roleLocalService.getRole(
			_group.getCompanyId(),
			AccountRoleConstants.ROLE_NAME_ACCOUNT_BUYER);

		_userGroupRoleLocalService.addUserGroupRole(
			user.getUserId(), _accountEntry.getAccountEntryGroupId(),
			role.getRoleId());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		_quantity = BigDecimal.valueOf(3);

		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), _cpInstance3.getCPInstanceId(),
			_quantity);

		commerceOrder = _commerceOrderLocalService.fetchCommerceOrder(
			commerceOrder.getCommerceOrderId());

		Country country = CommerceInventoryTestUtil.addCountry(_serviceContext);

		Region region = CommerceInventoryTestUtil.addRegion(
			country.getCountryId(), _serviceContext);

		Address address = _addressLocalService.addAddress(
			RandomTestUtil.randomString(), _user.getUserId(),
			AccountEntry.class.getName(), _accountEntry.getAccountEntryId(),
			country.getCountryId(), 0, region.getRegionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			RandomTestUtil.randomString(), true, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
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

		user = UserTestUtil.addOmniadminUser();

		PrincipalThreadLocal.setName(user.getUserId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			commerceOrder, user.getUserId());

		_commerceOrderEngine.transitionCommerceOrder(
			commerceOrder, CommerceOrderConstants.ORDER_STATUS_PROCESSING,
			user.getUserId(), true);

		_waitForObjectActionFinish(_objectAction.getObjectActionId());

		List<CommerceOrder> commerceOrders =
			_commerceOrderLocalService.getCommerceOrders(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceOrders.toString(), 3, commerceOrders.size());

		for (CommerceOrder cuCommerceOrder : commerceOrders) {
			for (CommerceOrderItem commerceOrderItem :
					cuCommerceOrder.getCommerceOrderItems()) {

				String sku = commerceOrderItem.getSku();

				if (StringUtil.equals(sku, _cpInstance1.getSku())) {
					_assertEqual(commerceOrderItem, _cpInstance1);
				}
				else if (StringUtil.equals(sku, _cpInstance2.getSku())) {
					_assertEqual(commerceOrderItem, _cpInstance2);
				}
				else if (StringUtil.equals(sku, _cpInstance3.getSku())) {
					_assertEqual(commerceOrderItem, _cpInstance3);
				}
				else {
					Assert.assertTrue(false);
				}
			}
		}
	}

	private void _activeObjectAction() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_COMMERCE_ORDER", _user.getCompanyId());

		_objectAction = _objectActionLocalService.fetchObjectAction(
			objectDefinition.getObjectDefinitionId(), "SplitOrderByCatalog");

		if (_objectAction != null) {
			if (_objectAction.isActive()) {
				return;
			}

			_objectActionLocalService.updateActive(_objectAction, true);
			_objectActionActivated = true;

			return;
		}

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

		_objectActionCreated = true;
	}

	private void _assertEqual(
			CommerceOrderItem commerceOrderItem, CPInstance cpInstance)
		throws Exception {

		CPDefinition actualCPDefinition = commerceOrderItem.getCPDefinition();
		CPDefinition expectedCPDefinition = cpInstance.getCPDefinition();

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				cpInstance.getGroupId());

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceList.getCommercePriceListId(),
				cpInstance.getCPInstanceUuid(), null);

		BigDecimal expectedPrice = commercePriceEntry.getPrice();

		BigDecimal actualPrice = commerceOrderItem.getUnitPrice();

		Assert.assertEquals(
			expectedPrice.doubleValue(), actualPrice.doubleValue(), 0);

		Assert.assertEquals(cpInstance.getSku(), commerceOrderItem.getSku());
		Assert.assertEquals(
			expectedCPDefinition.getName(), actualCPDefinition.getName());
		Assert.assertEquals(_quantity, commerceOrderItem.getQuantity());
	}

	private void _waitForObjectActionFinish(long objectActionId)
		throws Exception {

		int count = 0;

		while (true) {
			ObjectAction objectAction =
				_objectActionLocalService.getObjectAction(objectActionId);

			int status = objectAction.getStatus();

			if ((status == ObjectActionConstants.STATUS_SUCCESS) ||
				(status == ObjectActionConstants.STATUS_FAILED)) {

				Assert.assertEquals(
					ObjectActionConstants.STATUS_SUCCESS, status);

				return;
			}

			if (count++ > 3) {
				return;
			}

			Thread.sleep(1000);
		}
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
	private CPInstance _cpInstance1;
	private CPInstance _cpInstance2;
	private CPInstance _cpInstance3;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	private Group _group;
	private ObjectAction _objectAction;
	private boolean _objectActionActivated;
	private boolean _objectActionCreated;

	@Inject
	private ObjectActionLocalService _objectActionLocalService;

	@Inject
	private ObjectActionTriggerRegistry _objectActionTriggerRegistry;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private BigDecimal _quantity;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private ServiceContext _serviceContext;
	private User _user;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}