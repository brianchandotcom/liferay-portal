/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.inventory.constants.CommerceInventoryActionKeys;
import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.test.util.context.TestCommerceContext;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.Collections;

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
public class CommerceInventoryBookedQuantityServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(_group.getCompanyId());

		CommerceChannel commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), commerceCurrency.getCode());

		CommerceTestUtil.addWarehouseCommerceChannelRel(
			_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			commerceChannel.getCommerceChannelId());

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			_group.getGroupId());

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			TestPropsValues.getUserId(), _commerceInventoryWarehouse,
			BigDecimal.valueOf(2), _cpInstance.getSku(), StringPool.BLANK);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AccountEntry accountEntry =
			CommerceAccountTestUtil.addPersonAccountEntry(
				TestPropsValues.getUserId(), serviceContext);

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.addCommerceOrder(
				TestPropsValues.getUserId(), commerceChannel.getGroupId(),
				accountEntry.getAccountEntryId(), commerceCurrency.getCode(),
				0);

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.addCommerceOrderItem(
				TestPropsValues.getUserId(), commerceOrder.getCommerceOrderId(),
				_cpInstance.getCPInstanceId(), null, BigDecimal.valueOf(2), 0,
				BigDecimal.ZERO, StringPool.BLANK,
				new TestCommerceContext(
					null, commerceCurrency, commerceChannel,
					TestPropsValues.getUser(), _group, null),
				serviceContext);

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			_commerceInventoryBookedQuantityLocalService.
				addCommerceInventoryBookedQuantity(
					TestPropsValues.getUserId(), null, new BigDecimal(2),
					_cpInstance.getSku(), StringPool.BLANK,
					Collections.emptyMap());

		_commerceOrderItemLocalService.updateCommerceOrderItem(
			commerceOrderItem.getCommerceOrderItemId(),
			commerceInventoryBookedQuantity.
				getCommerceInventoryBookedQuantityId());

		_role = _roleLocalService.addRole(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(), null, 0,
			RandomTestUtil.randomString(), null, null,
			RoleConstants.TYPE_REGULAR, null,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
		_user = UserTestUtil.addUser();

		_roleLocalService.addUserRole(_user.getUserId(), _role);
	}

	@Test
	public void testGetCommerceInventoryBookedQuantities1() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryBookedQuantityService.
				getCommerceInventoryBookedQuantities(
					_user.getCompanyId(), _cpInstance.getSku(),
					StringPool.BLANK, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				CommerceInventoryActionKeys.VIEW_INVENTORIES,
				exception.getMessage(), _user.getUserId());
		}

		RoleTestUtil.addResourcePermission(
			_role, CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryBookedQuantityService.
				getCommerceInventoryBookedQuantities(
					_user.getCompanyId(), _cpInstance.getSku(),
					StringPool.BLANK, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
	}

	@Test
	public void testGetCommerceInventoryBookedQuantities2() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			Assert.assertTrue(
				ListUtil.isEmpty(
					_commerceInventoryBookedQuantityService.
						getCommerceInventoryBookedQuantities(
							_user.getCompanyId(), StringPool.BLANK,
							_cpInstance.getSku(), StringPool.BLANK,
							QueryUtil.ALL_POS, QueryUtil.ALL_POS)));
		}

		RoleTestUtil.addResourcePermission(
			_role, CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			Assert.assertFalse(
				ListUtil.isEmpty(
					_commerceInventoryBookedQuantityService.
						getCommerceInventoryBookedQuantities(
							_user.getCompanyId(), StringPool.BLANK,
							_cpInstance.getSku(), StringPool.BLANK,
							QueryUtil.ALL_POS, QueryUtil.ALL_POS)));
		}
	}

	@Test
	public void testGetCommerceInventoryBookedQuantitiesCount1()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryBookedQuantityService.
				getCommerceInventoryBookedQuantitiesCount(
					TestPropsValues.getCompanyId(),
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				CommerceInventoryActionKeys.VIEW_INVENTORIES,
				exception.getMessage(), _user.getUserId());
		}

		RoleTestUtil.addResourcePermission(
			_role, CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commerceInventoryBookedQuantityService.
				getCommerceInventoryBookedQuantitiesCount(
					TestPropsValues.getCompanyId(),
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString());
		}
	}

	@Test
	public void testGetCommerceInventoryBookedQuantitiesCount2()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			Assert.assertEquals(
				0,
				_commerceInventoryBookedQuantityService.
					getCommerceInventoryBookedQuantitiesCount(
						_user.getCompanyId(), StringPool.BLANK,
						_cpInstance.getSku(), StringPool.BLANK));
		}

		RoleTestUtil.addResourcePermission(
			_role, CommerceInventoryConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(_user.getCompanyId()),
			CommerceInventoryActionKeys.VIEW_INVENTORIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			Assert.assertNotEquals(
				0,
				_commerceInventoryBookedQuantityService.
					getCommerceInventoryBookedQuantitiesCount(
						_user.getCompanyId(), StringPool.BLANK,
						_cpInstance.getSku(), StringPool.BLANK));
		}
	}

	private void _assertMessage(String actionKey, String message, long userId) {
		Assert.assertTrue(
			message.contains(
				StringBundler.concat(
					"User ", userId, " must have ", actionKey,
					" permission for")));
	}

	@Inject
	private CommerceInventoryBookedQuantityLocalService
		_commerceInventoryBookedQuantityLocalService;

	@Inject
	private CommerceInventoryBookedQuantityService
		_commerceInventoryBookedQuantityService;

	private CommerceInventoryWarehouse _commerceInventoryWarehouse;

	@Inject
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	private CPInstance _cpInstance;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	private Group _group;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private Role _role;

	@Inject
	private RoleLocalService _roleLocalService;

	private User _user;

}