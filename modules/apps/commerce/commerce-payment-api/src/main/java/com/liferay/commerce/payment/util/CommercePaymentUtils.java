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

package com.liferay.commerce.payment.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.request.CommercePaymentRequest;
import com.liferay.commerce.payment.request.CommercePaymentRequestProvider;
import com.liferay.commerce.payment.result.CommercePaymentResult;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Luca Pellizzon
 */
@ProviderType
public interface CommercePaymentUtils {

	public CommercePaymentResult emptyResult(
		long commerceOrderId, String transactionId);

	public CommercePaymentMethod getCommercePaymentMethod(long commerceOrderId)
		throws PortalException;

	public CommercePaymentRequest getCommercePaymentRequest(
			CommerceOrder commerceOrder, Locale locale, String transactionId,
			String checkoutStepUrl, HttpServletRequest httpServletRequest,
			CommercePaymentMethod commercePaymentMethod)
		throws Exception;

	public CommercePaymentRequestProvider getCommercePaymentRequestProvider(
		CommercePaymentMethod commercePaymentMethod);

	public boolean isDeliveryOnlySubscription(CommerceOrder commerceOrder);

}