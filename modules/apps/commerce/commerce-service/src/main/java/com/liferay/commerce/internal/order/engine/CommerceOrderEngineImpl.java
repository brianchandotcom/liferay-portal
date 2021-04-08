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

package com.liferay.commerce.internal.order.engine;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.configuration.CommerceOrderCheckoutConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceDestinationNames;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommercePaymentConstants;
import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.discount.exception.CommerceDiscountLimitationTimesException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.discount.service.CommerceDiscountUsageEntryLocalService;
import com.liferay.commerce.exception.CommerceOrderBillingAddressException;
import com.liferay.commerce.exception.CommerceOrderGuestCheckoutException;
import com.liferay.commerce.exception.CommerceOrderShippingAddressException;
import com.liferay.commerce.exception.CommerceOrderShippingMethodException;
import com.liferay.commerce.exception.CommerceOrderStatusException;
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.internal.order.status.CompletedCommerceOrderStatusImpl;
import com.liferay.commerce.internal.order.status.ShippedCommerceOrderStatusImpl;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.inventory.type.constants.CommerceInventoryAuditTypeConstants;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.notification.util.CommerceNotificationHelper;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.method.CommercePaymentMethodRegistry;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceShipmentLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.subscription.CommerceSubscriptionEntryHelperUtil;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true, service = CommerceOrderEngine.class
)
public class CommerceOrderEngineImpl implements CommerceOrderEngine {

	@Override
	public CommerceOrder checkCommerceOrderShipmentStatus(
			CommerceOrder commerceOrder)
		throws PortalException {

		return _executeInTransaction(
			new Callable<CommerceOrder>() {

				@Override
				public CommerceOrder call() throws Exception {
					return _checkCommerceOrderShipmentStatus(commerceOrder);
				}

			});
	}

	@Override
	public CommerceOrder checkoutCommerceOrder(
			CommerceOrder commerceOrder, long userId)
		throws PortalException {

		return _executeInTransaction(
			new Callable<CommerceOrder>() {

				@Override
				public CommerceOrder call() throws Exception {
					return _checkoutCommerceOrder(commerceOrder, userId);
				}

			});
	}

	@Override
	public CommerceOrderStatus getCurrentCommerceOrderStatus(
		CommerceOrder commerceOrder) {

		return _commerceOrderStatusRegistry.getCommerceOrderStatus(
			commerceOrder.getOrderStatus());
	}

	@Override
	public List<CommerceOrderStatus> getNextCommerceOrderStatuses(
			CommerceOrder commerceOrder)
		throws PortalException {

		CommerceOrderStatus currentCommerceOrderStatus =
			_commerceOrderStatusRegistry.getCommerceOrderStatus(
				commerceOrder.getOrderStatus());

		List<CommerceOrderStatus> nextCommerceOrderStatuses = new ArrayList<>();

		if (currentCommerceOrderStatus == null) {
			return nextCommerceOrderStatuses;
		}
		else if (currentCommerceOrderStatus.getKey() ==
					CommerceOrderConstants.ORDER_STATUS_ON_HOLD) {

			nextCommerceOrderStatuses.add(
				_commerceOrderStatusRegistry.getCommerceOrderStatus(
					CommerceOrderConstants.ORDER_STATUS_ON_HOLD));

			return nextCommerceOrderStatuses;
		}

		List<CommerceOrderStatus> commerceOrderStatuses =
			_commerceOrderStatusRegistry.getCommerceOrderStatuses();

		int currentOrderStatusIndex = commerceOrderStatuses.indexOf(
			currentCommerceOrderStatus);

		if (currentOrderStatusIndex != (commerceOrderStatuses.size() - 1)) {
			CommerceOrderStatus nextCommerceOrderStatus =
				commerceOrderStatuses.get(currentOrderStatusIndex + 1);

			for (CommerceOrderStatus commerceOrderStatus :
					commerceOrderStatuses) {

				if ((commerceOrderStatus.isTransitionCriteriaMet(
						commerceOrder) &&
					 (((commerceOrderStatus.getPriority() ==
						 CommerceOrderConstants.ORDER_STATUS_ANY) &&
					   (currentCommerceOrderStatus.getKey() !=
						   CommerceOrderConstants.ORDER_STATUS_OPEN)) ||
					  (commerceOrderStatus.getPriority() ==
						  nextCommerceOrderStatus.getPriority()))) ||
					(!_commerceShippingHelper.isShippable(commerceOrder) &&
					 commerceOrderStatus.isValidForOrder(commerceOrder) &&
					 (commerceOrderStatus.getPriority() >
						 currentCommerceOrderStatus.getPriority()))) {

					nextCommerceOrderStatuses.add(commerceOrderStatus);
				}
			}
		}

		return nextCommerceOrderStatuses;
	}

	@Override
	public CommerceOrder transitionCommerceOrder(
			CommerceOrder commerceOrder, int orderStatus, long userId)
		throws PortalException {

		return _executeInTransaction(
			new Callable<CommerceOrder>() {

				@Override
				public CommerceOrder call() throws Exception {
					return _transitionCommerceOrder(
						commerceOrder, orderStatus, userId);
				}

			});
	}

	private void _bookQuantities(long commerceOrderId) throws Exception {
		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
				_commerceInventoryBookedQuantityLocalService.
					addCommerceBookedQuantity(
						commerceOrderItem.getUserId(),
						commerceOrderItem.getSku(),
						commerceOrderItem.getQuantity(), null,
						HashMapBuilder.put(
							CommerceInventoryAuditTypeConstants.ACCOUNT_NAME,
							commerceAccount.getName()
						).put(
							CommerceInventoryAuditTypeConstants.ORDER_ID,
							String.valueOf(
								commerceOrderItem.getCommerceOrderId())
						).put(
							CommerceInventoryAuditTypeConstants.ORDER_ITEM_ID,
							String.valueOf(
								commerceOrderItem.getCommerceOrderItemId())
						).build());

			_commerceOrderItemLocalService.updateCommerceOrderItem(
				commerceOrderItem.getCommerceOrderItemId(),
				commerceInventoryBookedQuantity.
					getCommerceInventoryBookedQuantityId());
		}
	}

	private CommerceOrder _checkCommerceOrderShipmentStatus(
			CommerceOrder commerceOrder)
		throws Exception {

		CommerceOrderStatus shippedCommerceOrderStatus =
			_commerceOrderStatusRegistry.getCommerceOrderStatus(
				ShippedCommerceOrderStatusImpl.KEY);

		CommerceOrderStatus completedCommerceOrderStatus =
			_commerceOrderStatusRegistry.getCommerceOrderStatus(
				CompletedCommerceOrderStatusImpl.KEY);

		int[] commerceShipmentStatuses =
			_commerceShipmentLocalService.
				getCommerceShipmentStatusesByCommerceOrderId(
					commerceOrder.getCommerceOrderId());

		if (completedCommerceOrderStatus.isTransitionCriteriaMet(
				commerceOrder) &&
			(commerceShipmentStatuses.length == 1) &&
			(commerceShipmentStatuses[0] ==
				CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED)) {

			commerceOrder = transitionCommerceOrder(
				commerceOrder, CommerceOrderConstants.ORDER_STATUS_COMPLETED,
				0);
		}
		else if (shippedCommerceOrderStatus.isTransitionCriteriaMet(
					commerceOrder)) {

			commerceOrder = transitionCommerceOrder(
				commerceOrder, CommerceOrderConstants.ORDER_STATUS_SHIPPED, 0);
		}
		else {
			commerceOrder = transitionCommerceOrder(
				commerceOrder,
				CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED, 0);
		}

		return commerceOrder;
	}

	private CommerceOrder _checkoutCommerceOrder(
			CommerceOrder commerceOrder, long userId)
		throws Exception {

		if (commerceOrder.isGuestOrder() &&
			!_isGuestCheckoutEnabled(commerceOrder.getGroupId())) {

			throw new CommerceOrderGuestCheckoutException();
		}

		_commerceOrderModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), commerceOrder,
			CommerceOrderActionKeys.CHECKOUT_COMMERCE_ORDER);

		CommerceOrderStatus currentCommerceOrderStatus =
			_commerceOrderStatusRegistry.getCommerceOrderStatus(
				commerceOrder.getOrderStatus());

		if ((currentCommerceOrderStatus == null) ||
			(currentCommerceOrderStatus.getKey() !=
				CommerceOrderConstants.ORDER_STATUS_OPEN)) {

			throw new CommerceOrderStatusException();
		}

		_validateCheckout(commerceOrder);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(commerceOrder.getGroupId());

		if (userId == 0) {
			User defaultUser = _userLocalService.getDefaultUser(
				commerceOrder.getCompanyId());

			userId = defaultUser.getUserId();
		}

		serviceContext.setUserId(userId);

		long commerceOrderId = commerceOrder.getCommerceOrderId();

		CommerceContext commerceContext = _commerceContextFactory.create(
			commerceOrder.getCompanyId(), commerceOrder.getGroupId(), userId,
			commerceOrderId, commerceOrder.getCommerceAccountId());

		TransactionCommitCallbackUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					_bookQuantities(commerceOrderId);

					return null;
				}

			});

		commerceOrder = _commerceOrderLocalService.recalculatePrice(
			commerceOrderId, commerceContext);

		commerceOrder.setOrderDate(new Date());

		_updateCommerceDiscountUsageEntry(
			commerceOrder.getCompanyId(), commerceOrder.getCommerceAccountId(),
			commerceOrderId, commerceOrder.getCouponCode(), serviceContext);

		// Commerce addresses

		if (commerceOrder.getBillingAddressId() > 0) {
			CommerceAddress commerceAddress =
				_commerceAddressLocalService.copyCommerceAddress(
					commerceOrder.getBillingAddressId(),
					commerceOrder.getModelClassName(), commerceOrderId,
					serviceContext);

			commerceOrder.setBillingAddressId(
				commerceAddress.getCommerceAddressId());
		}

		if (commerceOrder.getShippingAddressId() > 0) {
			CommerceAddress commerceAddress =
				_commerceAddressLocalService.copyCommerceAddress(
					commerceOrder.getShippingAddressId(),
					commerceOrder.getModelClassName(), commerceOrderId,
					serviceContext);

			commerceOrder.setShippingAddressId(
				commerceAddress.getCommerceAddressId());
		}

		CommercePaymentMethod commercePaymentMethod =
			_commercePaymentMethodRegistry.getCommercePaymentMethod(
				commerceOrder.getCommercePaymentMethodKey());

		if ((commerceOrder.getPaymentStatus() ==
				CommerceOrderConstants.PAYMENT_STATUS_PAID) ||
			(commercePaymentMethod == null) ||
			((commercePaymentMethod != null) &&
			 (commercePaymentMethod.getPaymentType() ==
				 CommercePaymentConstants.
					 COMMERCE_PAYMENT_METHOD_TYPE_OFFLINE) &&
			 (commerceOrder.getPaymentStatus() ==
				 CommerceOrderConstants.PAYMENT_STATUS_PENDING))) {

			return transitionCommerceOrder(
				commerceOrder, CommerceOrderConstants.ORDER_STATUS_PENDING,
				userId);
		}

		return transitionCommerceOrder(
			commerceOrder, CommerceOrderConstants.ORDER_STATUS_IN_PROGRESS,
			userId);
	}

	private CommerceOrder _executeInTransaction(
			Callable<CommerceOrder> callable)
		throws PortalException {

		try {
			return TransactionInvokerUtil.invoke(_transactionConfig, callable);
		}
		catch (Throwable throwable) {
			throw new PortalException(throwable);
		}
	}

	private boolean _isGuestCheckoutEnabled(long groupId) throws Exception {
		CommerceOrderCheckoutConfiguration commerceOrderCheckoutConfiguration =
			_configurationProvider.getConfiguration(
				CommerceOrderCheckoutConfiguration.class,
				new GroupServiceSettingsLocator(
					groupId, CommerceConstants.SERVICE_NAME_ORDER));

		return commerceOrderCheckoutConfiguration.guestCheckoutEnabled();
	}

	private void _sendOrderStatusMessage(
		CommerceOrder commerceOrder, int orderStatus) {

		TransactionCommitCallbackUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {

					//Commerce Subscription

					if ((orderStatus ==
							CommerceOrderConstants.ORDER_STATUS_PENDING) &&
						(commerceOrder.getPaymentStatus() ==
							CommerceOrderConstants.PAYMENT_STATUS_PAID)) {

						CommerceSubscriptionEntryHelperUtil.
							checkCommerceSubscriptions(commerceOrder);
					}

					//Commerce Notification

					_commerceNotificationHelper.sendNotifications(
						commerceOrder.getGroupId(), commerceOrder.getUserId(),
						CommerceOrderConstants.getNotificationKey(orderStatus),
						commerceOrder);

					//Commerce Order Status Message

					Message message = new Message();

					message.put(
						"commerceOrderId", commerceOrder.getCommerceOrderId());
					message.put("orderStatus", commerceOrder.getOrderStatus());

					MessageBusUtil.sendMessage(
						CommerceDestinationNames.ORDER_STATUS, message);

					return null;
				}

			});
	}

	private CommerceOrder _transitionCommerceOrder(
			CommerceOrder commerceOrder, int orderStatus, long userId)
		throws Exception {

		CommerceOrderStatus commerceOrderStatus =
			_commerceOrderStatusRegistry.getCommerceOrderStatus(orderStatus);

		if (commerceOrderStatus == null) {
			throw new CommerceOrderStatusException();
		}

		CommerceOrderStatus currentCommerceOrderStatus =
			_commerceOrderStatusRegistry.getCommerceOrderStatus(
				commerceOrder.getOrderStatus());

		if (!currentCommerceOrderStatus.isComplete(commerceOrder) ||
			!commerceOrderStatus.isTransitionCriteriaMet(commerceOrder) ||
			((currentCommerceOrderStatus.getKey() ==
				CommerceOrderConstants.ORDER_STATUS_ON_HOLD) &&
			 (commerceOrderStatus.getKey() !=
				 CommerceOrderConstants.ORDER_STATUS_ON_HOLD) &&
			 (commerceOrderStatus.getKey() !=
				 CommerceOrderConstants.ORDER_STATUS_PROCESSING))) {

			throw new CommerceOrderStatusException();
		}

		_sendOrderStatusMessage(commerceOrder, commerceOrderStatus.getKey());

		return commerceOrderStatus.doTransition(commerceOrder, userId);
	}

	private void _updateCommerceDiscountUsageEntry(
			long companyId, long commerceAccountId, long commerceOrderId,
			String couponCode, ServiceContext serviceContext)
		throws Exception {

		if (!Validator.isBlank(couponCode)) {
			CommerceDiscount commerceDiscount =
				_commerceDiscountLocalService.getActiveCommerceDiscount(
					companyId, couponCode, true);

			if (!_commerceDiscountUsageEntryLocalService.
					validateDiscountLimitationUsage(
						commerceAccountId,
						commerceDiscount.getCommerceDiscountId())) {

				throw new CommerceDiscountLimitationTimesException();
			}

			_commerceDiscountUsageEntryLocalService.
				addCommerceDiscountUsageEntry(
					commerceAccountId, commerceOrderId,
					commerceDiscount.getCommerceDiscountId(), serviceContext);
		}
	}

	private void _validateCheckout(CommerceOrder commerceOrder)
		throws Exception {

		if (!_commerceOrderValidatorRegistry.isValid(null, commerceOrder)) {
			throw new CommerceOrderValidatorException();
		}

		if (commerceOrder.isB2B() &&
			(commerceOrder.getBillingAddressId() <= 0)) {

			throw new CommerceOrderBillingAddressException();
		}

		CommerceShippingMethod commerceShippingMethod = null;

		long commerceShippingMethodId =
			commerceOrder.getCommerceShippingMethodId();

		if (commerceShippingMethodId > 0) {
			commerceShippingMethod =
				_commerceShippingMethodLocalService.getCommerceShippingMethod(
					commerceShippingMethodId);

			if (!commerceShippingMethod.isActive()) {
				commerceShippingMethod = null;
			}
			else if (commerceOrder.getShippingAddressId() <= 0) {
				throw new CommerceOrderShippingAddressException();
			}
		}

		int count =
			_commerceShippingMethodLocalService.getCommerceShippingMethodsCount(
				commerceOrder.getGroupId(), true);

		if ((commerceShippingMethod == null) && (count > 0) &&
			_commerceShippingHelper.isShippable(commerceOrder) &&
			!_commerceShippingHelper.isFreeShipping(commerceOrder)) {

			throw new CommerceOrderShippingMethodException();
		}
	}

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private CommerceAddressLocalService _commerceAddressLocalService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceDiscountLocalService _commerceDiscountLocalService;

	@Reference
	private CommerceDiscountUsageEntryLocalService
		_commerceDiscountUsageEntryLocalService;

	@Reference
	private CommerceInventoryBookedQuantityLocalService
		_commerceInventoryBookedQuantityLocalService;

	@Reference
	private CommerceNotificationHelper _commerceNotificationHelper;

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	private ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;

	@Reference
	private CommerceOrderStatusRegistry _commerceOrderStatusRegistry;

	@Reference
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;

	@Reference
	private CommercePaymentMethodRegistry _commercePaymentMethodRegistry;

	@Reference
	private CommerceShipmentLocalService _commerceShipmentLocalService;

	@Reference
	private CommerceShippingHelper _commerceShippingHelper;

	@Reference
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private UserLocalService _userLocalService;

}