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

package com.liferay.commerce.order.engine.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.exception.CommerceOrderBillingAddressException;
import com.liferay.commerce.exception.CommerceOrderShippingAddressException;
import com.liferay.commerce.exception.CommerceOrderShippingMethodException;
import com.liferay.commerce.exception.CommerceOrderStatusException;
import com.liferay.commerce.internal.order.status.CancelledCommerceOrderStatusImpl;
import com.liferay.commerce.internal.order.status.InProgressCommerceOrderStatusImpl;
import com.liferay.commerce.internal.order.status.OnHoldCommerceOrderStatusImpl;
import com.liferay.commerce.internal.order.status.OpenCommerceOrderStatusImpl;
import com.liferay.commerce.internal.order.status.PendingCommerceOrderStatusImpl;
import com.liferay.commerce.internal.order.status.ProcessingCommerceOrderStatusImpl;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.payment.test.util.TestCommercePaymentMethod;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceShipmentItemLocalService;
import com.liferay.commerce.service.CommerceShipmentLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.test.util.context.TestCommerceContext;
import com.liferay.commerce.test.util.order.status.Test1CommerceOrderStatusImpl;
import com.liferay.commerce.test.util.order.status.Test2CommerceOrderStatusImpl;
import com.liferay.commerce.test.util.order.status.Test3CommerceOrderStatusImpl;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
import org.osgi.util.promise.Promise;

/**
 * @author Alec Sloan
 */
@RunWith(Arquillian.class)
@Sync
public class CommerceOrderEngineTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		PrincipalThreadLocal.setName(_user.getUserId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		_commerceChannel = CommerceChannelLocalServiceUtil.addCommerceChannel(
			null, _group.getGroupId(), "Test Channel",
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			_commerceCurrency.getCode(), _serviceContext);

		_commerceAccount = CommerceAccountTestUtil.addBusinessCommerceAccount(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), new long[] {_user.getUserId()}, null,
			_serviceContext);

		_commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_commerceAccount.getCommerceAccountId(),
			_commerceCurrency.getCommerceCurrencyId());

		_commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			_commerceOrder, _user.getUserId(), false);

		_commerceShipment1 = _commerceShipmentLocalService.addCommerceShipment(
			_commerceOrder.getCommerceOrderId(), _serviceContext);
		_commerceShipment2 = _commerceShipmentLocalService.addCommerceShipment(
			_commerceOrder.getCommerceOrderId(), _serviceContext);

		_commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceOrder.getCommerceAccount(), _commerceOrder);
	}

	@After
	public void tearDown() throws PortalException {
		_commerceOrderLocalService.deleteCommerceOrder(_commerceOrder);
	}

	@Test
	public void testAutomaticallyTransitionOrderToCompleted() throws Exception {
		frutillaRule.scenario(
			"Use the Order Engine to checkout an Order, transition it to " +
				"processing then create a shipment with all of the order " +
					"items and mark that shipment as delivered"
		).given(
			"An Open Order that has an order item"
		).and(
			"A user who has checkout permissions"
		).when(
			"We create a shipment with the only order item and deliver it"
		).then(
			"The order should automatically be transitioned to Completed"
		);

		Assert.assertEquals(
			_commerceOrder.getOrderStatus(), OpenCommerceOrderStatusImpl.KEY);

		_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			_commerceOrder, _user.getUserId());

		_commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
			_commerceOrder, CommerceOrderConstants.ORDER_STATUS_PROCESSING,
			_user.getUserId());

		List<CommerceOrderItem> commerceOrderItems =
			_commerceOrder.getCommerceOrderItems();

		Assert.assertEquals(
			commerceOrderItems.toString(), 1, commerceOrderItems.size());

		CommerceOrderItem commerceOrderItem = commerceOrderItems.get(0);

		List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouses(
					_commerceChannel.getGroupId(), commerceOrderItem.getSku());

		Assert.assertFalse(commerceInventoryWarehouses.isEmpty());

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceInventoryWarehouses.get(0);

		_commerceShipmentItemLocalService.addCommerceShipmentItem(
			null, _commerceShipment1.getCommerceShipmentId(),
			commerceOrderItem.getCommerceOrderItemId(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			commerceOrderItem.getQuantity(), _serviceContext);

		_commerceShipment1 = _commerceShipmentLocalService.updateStatus(
			_commerceShipment1.getCommerceShipmentId(),
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED);

		Assert.assertEquals(
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED,
			_commerceShipment1.getStatus());

		_commerceShipment1 = _commerceShipmentLocalService.updateStatus(
			_commerceShipment1.getCommerceShipmentId(),
			CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED);

		Assert.assertEquals(
			CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED,
			_commerceShipment1.getStatus());

		_commerceOrder = _commerceOrderLocalService.fetchCommerceOrder(
			_commerceOrder.getCommerceOrderId());

		Assert.assertEquals(
			CommerceOrderConstants.ORDER_STATUS_COMPLETED,
			_commerceOrder.getOrderStatus());
	}

	@Test
	public void testAutomaticallyTransitionOrderToPartiallyShipped()
		throws Exception {

		frutillaRule.scenario(
			"Use the Order Engine to checkout an Order, transition it to " +
				"processing then create a shipment with one but not all of " +
					"the order items"
		).given(
			"An Open Order that has an order item with a quantity greater " +
				"than 1"
		).and(
			"A user who has checkout permissions"
		).when(
			"We create a shipment with 1 of the order items and mark it as " +
				"shipped"
		).then(
			"The order should automatically be transitioned to Partially " +
				"Shipped"
		);

		Assert.assertEquals(
			_commerceOrder.getOrderStatus(), OpenCommerceOrderStatusImpl.KEY);

		_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			_commerceOrder, _user.getUserId());

		_commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
			_commerceOrder, CommerceOrderConstants.ORDER_STATUS_PROCESSING,
			_user.getUserId());

		List<CommerceOrderItem> commerceOrderItems =
			_commerceOrder.getCommerceOrderItems();

		Assert.assertEquals(
			commerceOrderItems.toString(), 1, commerceOrderItems.size());

		CommerceOrderItem commerceOrderItem = commerceOrderItems.get(0);

		List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouses(
					_commerceChannel.getGroupId(), commerceOrderItem.getSku());

		Assert.assertFalse(commerceInventoryWarehouses.isEmpty());

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceInventoryWarehouses.get(0);

		_commerceShipmentItemLocalService.addCommerceShipmentItem(
			null, _commerceShipment1.getCommerceShipmentId(),
			commerceOrderItem.getCommerceOrderItemId(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			commerceOrderItem.getQuantity() / 2, _serviceContext);

		_commerceShipment1 = _commerceShipmentLocalService.updateStatus(
			_commerceShipment1.getCommerceShipmentId(),
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED);

		Assert.assertEquals(
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED,
			_commerceShipment1.getStatus());

		_commerceOrder = _commerceOrderLocalService.fetchCommerceOrder(
			_commerceOrder.getCommerceOrderId());

		Assert.assertEquals(
			CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED,
			_commerceOrder.getOrderStatus());
	}

	@Test
	public void testAutomaticallyTransitionOrderToShipped() throws Exception {
		frutillaRule.scenario(
			"Use the Order Engine to checkout an Order, transition it to" +
				"processing then create a shipment with all of the order items"
		).given(
			"An Open Order that has an order item"
		).and(
			"A user who has checkout permissions"
		).when(
			"We create a shipment with the only order item"
		).then(
			"The order should automatically be transitioned to Shipped"
		);

		Assert.assertEquals(
			_commerceOrder.getOrderStatus(), OpenCommerceOrderStatusImpl.KEY);

		_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			_commerceOrder, _user.getUserId());

		_commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
			_commerceOrder, CommerceOrderConstants.ORDER_STATUS_PROCESSING,
			_user.getUserId());

		List<CommerceOrderItem> commerceOrderItems =
			_commerceOrder.getCommerceOrderItems();

		Assert.assertEquals(
			commerceOrderItems.toString(), 1, commerceOrderItems.size());

		CommerceOrderItem commerceOrderItem = commerceOrderItems.get(0);

		List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouses(
					_commerceChannel.getGroupId(), commerceOrderItem.getSku());

		Assert.assertFalse(commerceInventoryWarehouses.isEmpty());

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceInventoryWarehouses.get(0);

		_commerceShipmentItemLocalService.addCommerceShipmentItem(
			null, _commerceShipment1.getCommerceShipmentId(),
			commerceOrderItem.getCommerceOrderItemId(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			commerceOrderItem.getQuantity(), _serviceContext);

		_commerceShipment1 = _commerceShipmentLocalService.updateStatus(
			_commerceShipment1.getCommerceShipmentId(),
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED);

		Assert.assertEquals(
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED,
			_commerceShipment1.getStatus());

		_commerceOrder = _commerceOrderLocalService.fetchCommerceOrder(
			_commerceOrder.getCommerceOrderId());

		Assert.assertEquals(
			CommerceOrderConstants.ORDER_STATUS_SHIPPED,
			_commerceOrder.getOrderStatus());
	}

	@Test
	public void testCancelOrder() throws Exception {
		frutillaRule.scenario(
			"Use the Order Engine to cancel a placed Order"
		).given(
			"An Open Order"
		).when(
			"We checkout the order and cancel it"
		).then(
			"The order status should be cancelled and the order should not " +
				"be able to be transitioned to anything else."
		);

		try {
			_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
				_commerceOrder, _user.getUserId());

			_commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
				_commerceOrder, CommerceOrderConstants.ORDER_STATUS_CANCELLED,
				_user.getUserId());

			Assert.assertEquals(
				_commerceOrder.getOrderStatus(),
				CancelledCommerceOrderStatusImpl.KEY);

			_commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
				_commerceOrder, CommerceOrderConstants.ORDER_STATUS_PROCESSING,
				_user.getUserId());

			Assert.assertNotEquals(
				ProcessingCommerceOrderStatusImpl.KEY,
				_commerceOrder.getOrderStatus());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderStatusException.class, throwable.getClass());
		}
	}

	@Test
	public void testCheckOrderWithoutPermissions() throws Exception {
		frutillaRule.scenario(
			"Use the Order Engine to try to checkout an order that a user " +
				"does not have permissions to checkout"
		).given(
			"An Open Order"
		).and(
			"A user who does not have checkout permissions"
		).when(
			"The user tries to checkout the order"
		).then(
			"The order engine should throw a permission exception"
		);

		try {
			Assert.assertEquals(
				_commerceOrder.getOrderStatus(),
				OpenCommerceOrderStatusImpl.KEY);

			User nonadminUser = UserTestUtil.addUser();

			PrincipalThreadLocal.setName(nonadminUser.getUserId());

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(nonadminUser));

			_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
				_commerceOrder, nonadminUser.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				PrincipalException.MustHavePermission.class,
				throwable.getClass());
		}
	}

	@Test
	public void testCheckoutAlreadyCheckedOutOrder() throws Exception {
		frutillaRule.scenario(
			"Use the Order Engine to try to checkout an order twice"
		).given(
			"An Open Order"
		).and(
			"A user who has checkout permissions"
		).when(
			"We checkout an order once"
		).then(
			"We should not be able to check it out again"
		);

		try {
			Assert.assertEquals(
				_commerceOrder.getOrderStatus(),
				OpenCommerceOrderStatusImpl.KEY);

			_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
				_commerceOrder, _user.getUserId());

			_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
				_commerceOrder, _user.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderStatusException.class, throwable.getClass());
		}
	}

	@Test
	public void testCheckoutOrderUnpaidWithoutPaymentMethod() throws Exception {
		frutillaRule.scenario(
			"Use the Order Engine to checkout an Order"
		).given(
			"An Open Order that is unpaid and without a payment method"
		).and(
			"A user who has checkout permissions"
		).when(
			"We try to checkout the order"
		).then(
			"The Order should be in the Pending status"
		);

		Assert.assertEquals(
			_commerceOrder.getOrderStatus(), OpenCommerceOrderStatusImpl.KEY);

		CommerceOrderStatus openCommerceOrderStatus =
			_commerceOrderEngine.getCurrentCommerceOrderStatus(_commerceOrder);

		Assert.assertEquals(
			openCommerceOrderStatus.getKey(), OpenCommerceOrderStatusImpl.KEY);
		Assert.assertTrue(openCommerceOrderStatus.isComplete(_commerceOrder));

		List<CommerceOrderStatus> nextCommerceOrderStatuses =
			_commerceOrderEngine.getNextCommerceOrderStatuses(_commerceOrder);

		Stream<CommerceOrderStatus> stream = nextCommerceOrderStatuses.stream();

		List<CommerceOrderStatus> inProgressCommerceOrderStatuses =
			stream.filter(
				entry -> entry.getKey() == InProgressCommerceOrderStatusImpl.KEY
			).collect(
				Collectors.toList()
			);

		Assert.assertEquals(
			inProgressCommerceOrderStatuses.toString(), 1,
			inProgressCommerceOrderStatuses.size());

		CommerceOrderStatus inProgresscommerceOrderStatus =
			inProgressCommerceOrderStatuses.get(0);

		Assert.assertTrue(
			inProgresscommerceOrderStatus.isTransitionCriteriaMet(
				_commerceOrder));

		_commerceOrder =
			_commerceOrderLocalService.updateCommercePaymentMethodKey(
				_commerceOrder.getCommerceOrderId(), null);

		_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			_commerceOrder, _user.getUserId());

		Assert.assertEquals(
			PendingCommerceOrderStatusImpl.KEY,
			_commerceOrder.getOrderStatus());
	}

	@Test
	public void testCheckoutOrderWithOfflinePaymentMethod() throws Exception {
		frutillaRule.scenario(
			"Use the Order Engine to checkout an Order"
		).given(
			"An Open Order that has an offline payment method"
		).and(
			"A user who has checkout permissions"
		).when(
			"We try to checkout the order"
		).then(
			"The Order should be in the Pending status"
		);

		CommerceOrderStatus openCommerceOrderStatus =
			_commerceOrderEngine.getCurrentCommerceOrderStatus(_commerceOrder);

		Assert.assertEquals(
			openCommerceOrderStatus.getKey(), OpenCommerceOrderStatusImpl.KEY);
		Assert.assertTrue(openCommerceOrderStatus.isComplete(_commerceOrder));

		_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			_commerceOrder, _user.getUserId());

		Assert.assertEquals(
			PendingCommerceOrderStatusImpl.KEY,
			_commerceOrder.getOrderStatus());
	}

	@Test
	public void testCheckoutOrderWithoutBillingAddress() throws Exception {
		frutillaRule.scenario(
			"Use the Order Engine to checkout an Order without billing address"
		).given(
			"An Open Order that does not have a billing address"
		).and(
			"A user who has checkout permissions"
		).when(
			"We try to checkout the order"
		).then(
			"An exception should be thrown indicating that billing address " +
				"is required"
		);

		try {
			Assert.assertEquals(
				_commerceOrder.getOrderStatus(),
				OpenCommerceOrderStatusImpl.KEY);

			_commerceOrder = _commerceOrderLocalService.updateBillingAddress(
				_commerceOrder.getCommerceOrderId(), 0);

			_commerceOrderEngine.checkoutCommerceOrder(
				_commerceOrder, _user.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderBillingAddressException.class,
				throwable.getClass());
		}
	}

	@Test
	public void testCheckoutOrderWithoutShippingAddress() throws Exception {
		frutillaRule.scenario(
			"Use the Order Engine to checkout an Order without shipping address"
		).given(
			"An Open Order that does not have a shipping address"
		).and(
			"A user who has checkout permissions"
		).when(
			"We try to checkout the order"
		).then(
			"An exception should be thrown indicating that shipping address " +
				"is required"
		);

		try {
			Assert.assertEquals(
				_commerceOrder.getOrderStatus(),
				OpenCommerceOrderStatusImpl.KEY);

			_commerceOrder = _commerceOrderLocalService.updateShippingAddress(
				_commerceOrder.getCommerceOrderId(), 0);

			_commerceOrderEngine.checkoutCommerceOrder(
				_commerceOrder, _user.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderShippingAddressException.class,
				throwable.getClass());
		}
	}

	@Test
	public void testCheckoutOrderWithoutShippingMethod() throws Exception {
		frutillaRule.scenario(
			"Use the Order Engine to checkout an Order without shipping " +
				"method even though there is at least 1 active"
		).given(
			"An Open Order that does not have a shipping method"
		).and(
			"A user who has checkout permissions"
		).and(
			"The instance has atleast 1 shipping method active"
		).when(
			"We try to checkout the order"
		).then(
			"An exception should be thrown indicating that shipping method " +
				"is required"
		);

		try {
			Assert.assertEquals(
				_commerceOrder.getOrderStatus(),
				OpenCommerceOrderStatusImpl.KEY);

			_commerceOrder =
				_commerceOrderLocalService.updateCommerceShippingMethod(
					_commerceOrder.getCommerceOrderId(), 0, null,
					BigDecimal.ZERO, _commerceContext);

			_commerceOrder = _commerceOrderLocalService.recalculatePrice(
				_commerceOrder.getCommerceOrderId(), _commerceContext);

			_commerceOrderEngine.checkoutCommerceOrder(
				_commerceOrder, _user.getUserId());
		}
		catch (PortalException portalException) {
			Throwable throwable = portalException.getCause();

			Assert.assertSame(
				CommerceOrderShippingMethodException.class,
				throwable.getClass());
		}
	}

	@Test
	public void testCustomOrderStatusOrderFlow() throws Exception {
		frutillaRule.scenario(
			"Use the Order Engine to transition an Order to one of two " +
				"custom order statuses with equivalent priorities, then to a " +
					"third custom order status."
		).given(
			"An Open Order that has an order item"
		).and(
			"A user who has checkout permissions"
		).when(
			"We transition that order to one of two custom order statuses " +
				"with equivalent priorites"
		).then(
			"The order should be able to transition to an order status that " +
				"has a higher priority than those two"
		);

		Collection<ComponentDescriptionDTO> componentDescriptionDTOs =
			_serviceComponentRuntime.getComponentDescriptionDTOs(
				FrameworkUtil.getBundle(Test1CommerceOrderStatusImpl.class),
				FrameworkUtil.getBundle(Test2CommerceOrderStatusImpl.class),
				FrameworkUtil.getBundle(Test3CommerceOrderStatusImpl.class));

		for (ComponentDescriptionDTO componentDescriptionDTO :
				componentDescriptionDTOs) {

			Promise<Void> voidPromise =
				_serviceComponentRuntime.enableComponent(
					componentDescriptionDTO);

			voidPromise.getValue();
		}

		Assert.assertEquals(
			_commerceOrder.getOrderStatus(), OpenCommerceOrderStatusImpl.KEY);

		_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			_commerceOrder, _user.getUserId());

		_commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
			_commerceOrder, CommerceOrderConstants.ORDER_STATUS_PROCESSING,
			_user.getUserId());

		Assert.assertEquals(
			_commerceOrder.getOrderStatus(),
			ProcessingCommerceOrderStatusImpl.KEY);

		List<CommerceOrderStatus> nextCommerceOrderStatuses =
			_commerceOrderEngine.getNextCommerceOrderStatuses(_commerceOrder);

		nextCommerceOrderStatuses.removeIf(
			commerceOrderStatus -> commerceOrderStatus.getPriority() == -1);

		Assert.assertEquals(
			nextCommerceOrderStatuses.toString(), 2,
			nextCommerceOrderStatuses.size());

		CommerceOrderStatus test1CommerceOrderStatus =
			nextCommerceOrderStatuses.get(0);

		Assert.assertEquals(53, test1CommerceOrderStatus.getPriority());

		CommerceOrderStatus test2CommerceOrderStatus =
			nextCommerceOrderStatuses.get(1);

		Assert.assertEquals(53, test2CommerceOrderStatus.getPriority());

		CommerceOrderStatus randomCommerceOrderStatus =
			nextCommerceOrderStatuses.get(RandomUtil.nextInt(2));

		_commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
			_commerceOrder, randomCommerceOrderStatus.getKey(),
			_user.getUserId());

		Assert.assertEquals(
			randomCommerceOrderStatus.getKey(),
			_commerceOrder.getOrderStatus());

		nextCommerceOrderStatuses =
			_commerceOrderEngine.getNextCommerceOrderStatuses(_commerceOrder);

		nextCommerceOrderStatuses.removeIf(
			commerceOrderStatus -> commerceOrderStatus.getPriority() == -1);

		Assert.assertEquals(
			nextCommerceOrderStatuses.toString(), 1,
			nextCommerceOrderStatuses.size());

		CommerceOrderStatus test3CommerceOrderStatus =
			nextCommerceOrderStatuses.get(0);

		Assert.assertEquals(
			Test3CommerceOrderStatusImpl.KEY,
			test3CommerceOrderStatus.getKey());

		_commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
			_commerceOrder, test3CommerceOrderStatus.getKey(),
			_user.getUserId());

		Assert.assertEquals(
			Test3CommerceOrderStatusImpl.KEY, _commerceOrder.getOrderStatus());

		List<CommerceOrderItem> commerceOrderItems =
			_commerceOrder.getCommerceOrderItems();

		Assert.assertEquals(
			commerceOrderItems.toString(), 1, commerceOrderItems.size());

		CommerceOrderItem commerceOrderItem = commerceOrderItems.get(0);

		List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouses(
					_commerceChannel.getGroupId(), commerceOrderItem.getSku());

		Assert.assertFalse(commerceInventoryWarehouses.isEmpty());

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceInventoryWarehouses.get(0);

		_commerceShipmentItemLocalService.addCommerceShipmentItem(
			null, _commerceShipment1.getCommerceShipmentId(),
			commerceOrderItem.getCommerceOrderItemId(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			commerceOrderItem.getQuantity() / 2, _serviceContext);

		_commerceShipment1 = _commerceShipmentLocalService.updateStatus(
			_commerceShipment1.getCommerceShipmentId(),
			CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED);

		_commerceOrder = _commerceOrderLocalService.fetchCommerceOrder(
			_commerceOrder.getCommerceOrderId());

		Assert.assertEquals(
			CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED,
			_commerceOrder.getOrderStatus());

		int remainingQuantity =
			commerceOrderItem.getQuantity() -
				commerceOrderItem.getShippedQuantity();

		_commerceShipmentItemLocalService.addCommerceShipmentItem(
			null, _commerceShipment2.getCommerceShipmentId(),
			commerceOrderItem.getCommerceOrderItemId(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			remainingQuantity, _serviceContext);

		_commerceShipment2 = _commerceShipmentLocalService.updateStatus(
			_commerceShipment2.getCommerceShipmentId(),
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED);

		_commerceOrder = _commerceOrderLocalService.fetchCommerceOrder(
			_commerceOrder.getCommerceOrderId());

		Assert.assertEquals(
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED,
			_commerceShipment2.getStatus());
		Assert.assertEquals(
			CommerceOrderConstants.ORDER_STATUS_SHIPPED,
			_commerceOrder.getOrderStatus());

		_commerceShipment2 = _commerceShipmentLocalService.updateStatus(
			_commerceShipment2.getCommerceShipmentId(),
			CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED);

		_commerceOrder = _commerceOrderLocalService.fetchCommerceOrder(
			_commerceOrder.getCommerceOrderId());

		Assert.assertEquals(
			CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED,
			_commerceShipment2.getStatus());
		Assert.assertEquals(
			CommerceOrderConstants.ORDER_STATUS_COMPLETED,
			_commerceOrder.getOrderStatus());

		CentralizedThreadLocal.clearShortLivedThreadLocals();

		for (ComponentDescriptionDTO componentDescriptionDTO :
				componentDescriptionDTOs) {

			Promise<Void> voidPromise =
				_serviceComponentRuntime.disableComponent(
					componentDescriptionDTO);

			voidPromise.getValue();
		}
	}

	@Test
	public void testDefaultOrderFlow() throws Exception {
		frutillaRule.scenario(
			"Use the Order Engine to transition an Order through the default " +
				"order statuses"
		).given(
			"An Open Order that has an order item"
		).and(
			"A user who has checkout permissions"
		).when(
			"We transition that order through each order status"
		).then(
			"Two shipments should be created and marked as delivered, and " +
				"the order should be transitioned to Completed in the end"
		);

		Assert.assertEquals(
			_commerceOrder.getOrderStatus(), OpenCommerceOrderStatusImpl.KEY);

		_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			_commerceOrder, _user.getUserId());

		_commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
			_commerceOrder, CommerceOrderConstants.ORDER_STATUS_PROCESSING,
			_user.getUserId());

		List<CommerceOrderItem> commerceOrderItems =
			_commerceOrder.getCommerceOrderItems();

		Assert.assertEquals(
			commerceOrderItems.toString(), 1, commerceOrderItems.size());

		CommerceOrderItem commerceOrderItem = commerceOrderItems.get(0);

		List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouses(
					_commerceChannel.getGroupId(), commerceOrderItem.getSku());

		Assert.assertFalse(commerceInventoryWarehouses.isEmpty());

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceInventoryWarehouses.get(0);

		_commerceShipmentItemLocalService.addCommerceShipmentItem(
			null, _commerceShipment1.getCommerceShipmentId(),
			commerceOrderItem.getCommerceOrderItemId(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			commerceOrderItem.getQuantity() / 2, _serviceContext);

		_commerceShipment1 = _commerceShipmentLocalService.updateStatus(
			_commerceShipment1.getCommerceShipmentId(),
			CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED);

		_commerceOrder = _commerceOrderLocalService.fetchCommerceOrder(
			_commerceOrder.getCommerceOrderId());

		Assert.assertEquals(
			CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED,
			_commerceOrder.getOrderStatus());

		int remainingQuantity =
			commerceOrderItem.getQuantity() -
				commerceOrderItem.getShippedQuantity();

		_commerceShipmentItemLocalService.addCommerceShipmentItem(
			null, _commerceShipment2.getCommerceShipmentId(),
			commerceOrderItem.getCommerceOrderItemId(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			remainingQuantity, _serviceContext);

		_commerceShipment2 = _commerceShipmentLocalService.updateStatus(
			_commerceShipment2.getCommerceShipmentId(),
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED);

		_commerceOrder = _commerceOrderLocalService.fetchCommerceOrder(
			_commerceOrder.getCommerceOrderId());

		Assert.assertEquals(
			CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED,
			_commerceShipment2.getStatus());
		Assert.assertEquals(
			CommerceOrderConstants.ORDER_STATUS_SHIPPED,
			_commerceOrder.getOrderStatus());

		_commerceShipment2 = _commerceShipmentLocalService.updateStatus(
			_commerceShipment2.getCommerceShipmentId(),
			CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED);

		_commerceOrder = _commerceOrderLocalService.fetchCommerceOrder(
			_commerceOrder.getCommerceOrderId());

		Assert.assertEquals(
			CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED,
			_commerceShipment2.getStatus());
		Assert.assertEquals(
			CommerceOrderConstants.ORDER_STATUS_COMPLETED,
			_commerceOrder.getOrderStatus());
	}

	@Test
	public void testGetNextOrderStatusesWhileOrderNotOpen() throws Exception {
		frutillaRule.scenario(
			"When an order is not open, next order statuses should contain " +
				"CommerceOrderStatuses that contain a -1 priority"
		).given(
			"An Open Order"
		).and(
			"A user who has checkout permissions"
		).when(
			"We pull next order statuses"
		).then(
			"We should see statuses with a -1 priority"
		);

		Assert.assertEquals(
			_commerceOrder.getOrderStatus(), OpenCommerceOrderStatusImpl.KEY);

		CommerceOrderStatus openCommerceOrderStatus =
			_commerceOrderEngine.getCurrentCommerceOrderStatus(_commerceOrder);

		Assert.assertEquals(
			openCommerceOrderStatus.getKey(), OpenCommerceOrderStatusImpl.KEY);

		_commerceOrderLocalService.updateCommercePaymentMethodKey(
			_commerceOrder.getCommerceOrderId(), TestCommercePaymentMethod.KEY);

		_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			_commerceOrder, _user.getUserId());

		Assert.assertEquals(
			PendingCommerceOrderStatusImpl.KEY,
			_commerceOrder.getOrderStatus());

		List<CommerceOrderStatus> nextCommerceOrderStatuses =
			_commerceOrderEngine.getNextCommerceOrderStatuses(_commerceOrder);

		Stream<CommerceOrderStatus> stream = nextCommerceOrderStatuses.stream();

		List<CommerceOrderStatus> inProgressCommerceOrderStatuses =
			stream.filter(
				entry -> entry.getPriority() == -1
			).collect(
				Collectors.toList()
			);

		Assert.assertFalse(inProgressCommerceOrderStatuses.isEmpty());
	}

	@Test
	public void testGetNextOrderStatusesWhileOrderOpen() throws Exception {
		frutillaRule.scenario(
			"When an order is open, next order statuses should never contain " +
				"CommerceOrderStatuses that contain a -1 priority"
		).given(
			"An Open Order"
		).and(
			"A user who has checkout permissions"
		).when(
			"We pull next order statuses"
		).then(
			"We should not see any with a -1 priority"
		);

		Assert.assertEquals(
			_commerceOrder.getOrderStatus(), OpenCommerceOrderStatusImpl.KEY);

		CommerceOrderStatus openCommerceOrderStatus =
			_commerceOrderEngine.getCurrentCommerceOrderStatus(_commerceOrder);

		Assert.assertEquals(
			openCommerceOrderStatus.getKey(), OpenCommerceOrderStatusImpl.KEY);

		List<CommerceOrderStatus> nextCommerceOrderStatuses =
			_commerceOrderEngine.getNextCommerceOrderStatuses(_commerceOrder);

		Stream<CommerceOrderStatus> stream = nextCommerceOrderStatuses.stream();

		List<CommerceOrderStatus> inProgressCommerceOrderStatuses =
			stream.filter(
				entry -> entry.getPriority() == -1
			).collect(
				Collectors.toList()
			);

		Assert.assertTrue(inProgressCommerceOrderStatuses.isEmpty());
	}

	@Test
	public void testPlaceOrderOnHoldAndRemoveHold() throws Exception {
		frutillaRule.scenario(
			"Use the Order Engine to place an Order on hold then remove the " +
				"hold"
		).given(
			"An Order"
		).when(
			"We put an order on hold"
		).then(
			"The order status should be on hold"
		).but(
			"If we remove the order from being on hold, the order status " +
				"should be processing"
		);

		_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			_commerceOrder, _user.getUserId());

		_commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
			_commerceOrder, CommerceOrderConstants.ORDER_STATUS_ON_HOLD,
			_user.getUserId());

		Assert.assertEquals(
			_commerceOrder.getOrderStatus(), OnHoldCommerceOrderStatusImpl.KEY);

		_commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
			_commerceOrder, CommerceOrderConstants.ORDER_STATUS_ON_HOLD,
			_user.getUserId());

		Assert.assertEquals(
			_commerceOrder.getOrderStatus(),
			ProcessingCommerceOrderStatusImpl.KEY);
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private static User _user;

	private CommerceAccount _commerceAccount;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	private CommerceContext _commerceContext;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceInventoryWarehouseLocalService
		_commerceInventoryWarehouseLocalService;

	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@DeleteAfterTestRun
	private CommerceShipment _commerceShipment1;

	@DeleteAfterTestRun
	private CommerceShipment _commerceShipment2;

	@Inject
	private CommerceShipmentItemLocalService _commerceShipmentItemLocalService;

	@Inject
	private CommerceShipmentLocalService _commerceShipmentLocalService;

	private Group _group;

	@Inject
	private ServiceComponentRuntime _serviceComponentRuntime;

	private ServiceContext _serviceContext;

}