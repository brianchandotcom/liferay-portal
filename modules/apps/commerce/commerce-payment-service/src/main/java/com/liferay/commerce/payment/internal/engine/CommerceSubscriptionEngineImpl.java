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

package com.liferay.commerce.payment.internal.engine;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceSubscriptionEntryConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.payment.engine.CommerceSubscriptionEngine;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.request.CommercePaymentRequest;
import com.liferay.commerce.payment.request.CommercePaymentRequestProvider;
import com.liferay.commerce.payment.result.CommercePaymentResult;
import com.liferay.commerce.payment.util.CommercePaymentUtils;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderPaymentLocalService;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceSubscriptionEngine.class
)
public class CommerceSubscriptionEngineImpl
	implements CommerceSubscriptionEngine {

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public boolean activateRecurringDelivery(long commerceSubscriptionEntryId)
		throws Exception {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			_commerceSubscriptionEntryLocalService.getCommerceSubscriptionEntry(
				commerceSubscriptionEntryId);

		if (Objects.equals(
				CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_ACTIVE,
				commerceSubscriptionEntry.getSubscriptionStatus())) {

			_commerceSubscriptionEntryLocalService.
				updateDeliverySubscriptionStatus(
					commerceSubscriptionEntryId,
					CommerceSubscriptionEntryConstants.
						SUBSCRIPTION_STATUS_ACTIVE);

			return true;
		}

		return false;
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public boolean activateRecurringPayment(long commerceSubscriptionEntryId)
		throws Exception {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			_commerceSubscriptionEntryLocalService.getCommerceSubscriptionEntry(
				commerceSubscriptionEntryId);

		CommerceOrderItem commerceOrderItem =
			commerceSubscriptionEntry.fetchCommerceOrderItem();

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		CommercePaymentMethod commercePaymentMethod =
			_commercePaymentUtils.getCommercePaymentMethod(
				commerceOrder.getCommerceOrderId());

		if ((commercePaymentMethod == null) ||
			!commercePaymentMethod.isProcessRecurringEnabled()) {

			return false;
		}

		CommercePaymentRequestProvider commercePaymentRequestProvider =
			_commercePaymentUtils.getCommercePaymentRequestProvider(
				commercePaymentMethod);

		CommercePaymentRequest commercePaymentRequest =
			commercePaymentRequestProvider.getCommercePaymentRequest(
				null, commerceOrder.getCommerceOrderId(), null, null, null,
				commerceOrder.getTransactionId());

		boolean activateSubscription =
			commercePaymentMethod.activateRecurringPayment(
				commercePaymentRequest);

		if (activateSubscription) {
			_commerceSubscriptionEntryLocalService.updateSubscriptionStatus(
				commerceSubscriptionEntry.getCommerceSubscriptionEntryId(),
				CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_ACTIVE);
		}

		return activateSubscription;
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public boolean cancelRecurringDelivery(long commerceSubscriptionEntryId)
		throws Exception {

		_commerceSubscriptionEntryLocalService.updateDeliverySubscriptionStatus(
			commerceSubscriptionEntryId,
			CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_CANCELLED);

		return true;
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public boolean cancelRecurringPayment(long commerceSubscriptionEntryId)
		throws Exception {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			_commerceSubscriptionEntryLocalService.getCommerceSubscriptionEntry(
				commerceSubscriptionEntryId);

		CommerceOrderItem commerceOrderItem =
			commerceSubscriptionEntry.fetchCommerceOrderItem();

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		CommercePaymentMethod commercePaymentMethod =
			_commercePaymentUtils.getCommercePaymentMethod(
				commerceOrder.getCommerceOrderId());

		if ((commercePaymentMethod == null) ||
			!commercePaymentMethod.isProcessRecurringEnabled()) {

			return false;
		}

		CommercePaymentRequestProvider commercePaymentRequestProvider =
			_commercePaymentUtils.getCommercePaymentRequestProvider(
				commercePaymentMethod);

		CommercePaymentRequest commercePaymentRequest =
			commercePaymentRequestProvider.getCommercePaymentRequest(
				null, commerceOrder.getCommerceOrderId(), null, null, null,
				commerceOrder.getTransactionId());

		boolean activateSubscription =
			commercePaymentMethod.cancelRecurringPayment(
				commercePaymentRequest);

		if (activateSubscription) {
			_commerceSubscriptionEntryLocalService.updateSubscriptionStatus(
				commerceSubscriptionEntry.getCommerceSubscriptionEntryId(),
				CommerceSubscriptionEntryConstants.
					SUBSCRIPTION_STATUS_CANCELLED);
		}

		return activateSubscription;
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public CommercePaymentResult completeRecurringPayment(
			long commerceOrderId, String transactionId,
			HttpServletRequest httpServletRequest)
		throws Exception {

		CommercePaymentMethod commercePaymentMethod =
			_commercePaymentUtils.getCommercePaymentMethod(commerceOrderId);

		if ((commercePaymentMethod == null) ||
			!commercePaymentMethod.isCompleteRecurringEnabled()) {

			return _commercePaymentUtils.emptyResult(
				commerceOrderId, transactionId);
		}

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		CommercePaymentRequest commercePaymentRequest =
			_commercePaymentUtils.getCommercePaymentRequest(
				commerceOrder, _portal.getLocale(httpServletRequest),
				transactionId, null, httpServletRequest, commercePaymentMethod);

		CommercePaymentResult commercePaymentResult =
			commercePaymentMethod.completeRecurringPayment(
				commercePaymentRequest);

		commerceOrder =
			_commerceOrderLocalService.updatePaymentStatusAndTransactionId(
				commerceOrder.getUserId(), commerceOrderId,
				commercePaymentResult.getNewPaymentStatus(),
				GetterUtil.getString(
					commercePaymentResult.getAuthTransactionId(),
					commerceOrder.getTransactionId()));

		_commerceOrderPaymentLocalService.addCommerceOrderPayment(
			commerceOrderId, commercePaymentResult.getNewPaymentStatus(),
			StringPool.BLANK);

		if (commercePaymentResult.getNewPaymentStatus() ==
				CommerceOrderConstants.PAYMENT_STATUS_PAID) {

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			_commerceOrderEngine.transitionCommerceOrder(
				commerceOrder, CommerceOrderConstants.ORDER_STATUS_PENDING,
				permissionChecker.getUserId());
		}

		return commercePaymentResult;
	}

	@Override
	public boolean getSubscriptionValidity(long commerceOrderId)
		throws Exception {

		CommercePaymentMethod commercePaymentMethod =
			_commercePaymentUtils.getCommercePaymentMethod(commerceOrderId);

		if ((commercePaymentMethod == null) ||
			!commercePaymentMethod.isProcessRecurringEnabled()) {

			return false;
		}

		CommercePaymentRequestProvider commercePaymentRequestProvider =
			_commercePaymentUtils.getCommercePaymentRequestProvider(
				commercePaymentMethod);

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		CommercePaymentRequest commercePaymentRequest =
			commercePaymentRequestProvider.getCommercePaymentRequest(
				null, commerceOrderId, null, null, null,
				commerceOrder.getTransactionId());

		return commercePaymentMethod.getSubscriptionValidity(
			commercePaymentRequest);
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public CommercePaymentResult processRecurringPayment(
			long commerceOrderId, String checkoutStepUrl,
			HttpServletRequest httpServletRequest)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		CommercePaymentMethod commercePaymentMethod =
			_commercePaymentUtils.getCommercePaymentMethod(commerceOrderId);

		if ((commercePaymentMethod == null) ||
			!commercePaymentMethod.isProcessRecurringEnabled()) {

			return _commercePaymentUtils.emptyResult(
				commerceOrderId, StringPool.BLANK);
		}

		CommercePaymentRequest commercePaymentRequest =
			_commercePaymentUtils.getCommercePaymentRequest(
				commerceOrder, _portal.getLocale(httpServletRequest), null,
				checkoutStepUrl, httpServletRequest, commercePaymentMethod);

		CommercePaymentResult commercePaymentResult =
			commercePaymentMethod.processRecurringPayment(
				commercePaymentRequest);

		_commerceOrderLocalService.updatePaymentStatusAndTransactionId(
			commerceOrder.getUserId(), commerceOrderId,
			commercePaymentResult.getNewPaymentStatus(),
			commercePaymentResult.getAuthTransactionId());

		_commerceOrderPaymentLocalService.addCommerceOrderPayment(
			commerceOrderId, commercePaymentResult.getNewPaymentStatus(),
			StringPool.BLANK);

		return commercePaymentResult;
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public boolean suspendRecurringDelivery(long commerceSubscriptionEntryId)
		throws Exception {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			_commerceSubscriptionEntryLocalService.getCommerceSubscriptionEntry(
				commerceSubscriptionEntryId);

		if (Objects.equals(
				CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_ACTIVE,
				commerceSubscriptionEntry.getSubscriptionStatus()) ||
			Objects.equals(
				CommerceSubscriptionEntryConstants.
					SUBSCRIPTION_STATUS_SUSPENDED,
				commerceSubscriptionEntry.getSubscriptionStatus())) {

			_commerceSubscriptionEntryLocalService.
				updateDeliverySubscriptionStatus(
					commerceSubscriptionEntryId,
					CommerceSubscriptionEntryConstants.
						SUBSCRIPTION_STATUS_SUSPENDED);

			return true;
		}

		return false;
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public boolean suspendRecurringPayment(long commerceSubscriptionEntryId)
		throws Exception {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			_commerceSubscriptionEntryLocalService.getCommerceSubscriptionEntry(
				commerceSubscriptionEntryId);

		CommerceOrderItem commerceOrderItem =
			commerceSubscriptionEntry.fetchCommerceOrderItem();

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		CommercePaymentMethod commercePaymentMethod =
			_commercePaymentUtils.getCommercePaymentMethod(
				commerceOrder.getCommerceOrderId());

		if ((commercePaymentMethod == null) ||
			!commercePaymentMethod.isProcessRecurringEnabled()) {

			return false;
		}

		CommercePaymentRequestProvider commercePaymentRequestProvider =
			_commercePaymentUtils.getCommercePaymentRequestProvider(
				commercePaymentMethod);

		CommercePaymentRequest commercePaymentRequest =
			commercePaymentRequestProvider.getCommercePaymentRequest(
				null, commerceOrder.getCommerceOrderId(), null, null, null,
				commerceOrder.getTransactionId());

		boolean suspendSubscription =
			commercePaymentMethod.suspendRecurringPayment(
				commercePaymentRequest);

		if (suspendSubscription) {
			_commerceSubscriptionEntryLocalService.updateSubscriptionStatus(
				commerceSubscriptionEntry.getCommerceSubscriptionEntryId(),
				CommerceSubscriptionEntryConstants.
					SUBSCRIPTION_STATUS_SUSPENDED);
		}

		return suspendSubscription;
	}

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommerceOrderPaymentLocalService _commerceOrderPaymentLocalService;

	@Reference
	private CommercePaymentUtils _commercePaymentUtils;

	@Reference
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	@Reference
	private Portal _portal;

}