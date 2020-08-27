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

package com.liferay.commerce.payment.engine;

import com.liferay.commerce.payment.result.CommercePaymentResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Luca Pellizzon
 */
public interface CommerceSubscriptionEngine {

	public boolean activateRecurringDelivery(long commerceSubscriptionEntryId)
		throws Exception;

	public boolean activateRecurringPayment(long commerceSubscriptionEntryId)
		throws Exception;

	public boolean cancelRecurringDelivery(long commerceSubscriptionEntryId)
		throws Exception;

	public boolean cancelRecurringPayment(long commerceSubscriptionEntryId)
		throws Exception;

	public CommercePaymentResult completeRecurringPayment(
			long commerceOrderId, String transactionId,
			HttpServletRequest httpServletRequest)
		throws Exception;

	public boolean getSubscriptionValidity(long commerceOrderId)
		throws Exception;

	public CommercePaymentResult processRecurringPayment(
			long commerceOrderId, String checkoutStepUrl,
			HttpServletRequest httpServletRequest)
		throws Exception;

	public boolean suspendRecurringDelivery(long commerceSubscriptionEntryId)
		throws Exception;

	public boolean suspendRecurringPayment(long commerceSubscriptionEntryId)
		throws Exception;

}