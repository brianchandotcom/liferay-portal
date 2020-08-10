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

package com.liferay.commerce.payment.method;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.payment.request.CommercePaymentRequest;
import com.liferay.commerce.payment.result.CommercePaymentResult;
import com.liferay.commerce.payment.result.CommerceSubscriptionStatusResult;

import java.util.Locale;

/**
 * @author Luca Pellizzon
 */
@ProviderType
public interface CommercePaymentMethod {

	public default boolean activateRecurringPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public default CommercePaymentResult authorizePayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public default CommercePaymentResult cancelPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public default boolean cancelRecurringPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public default CommercePaymentResult capturePayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public default CommercePaymentResult completePayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public default CommercePaymentResult completeRecurringPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public String getDescription(Locale locale);

	public String getKey();

	public String getName(Locale locale);

	/**
	 * @deprecated As of Mueller (7.2.x), this method will be removed
	 */
	@Deprecated
	public default int getOrderStatusUpdateMaxIntervalMinutes() {
		return 0;
	}

	public int getPaymentType();

	public String getServletPath();

	public default CommerceSubscriptionStatusResult
			getSubscriptionPaymentDetails(
				CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public default boolean getSubscriptionValidity(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public default boolean isAuthorizeEnabled() {
		return true;
	}

	public default boolean isCancelEnabled() {
		return false;
	}

	public default boolean isCancelRecurringEnabled() {
		return false;
	}

	public default boolean isCaptureEnabled() {
		return false;
	}

	public default boolean isCompleteEnabled() {
		return false;
	}

	public default boolean isCompleteRecurringEnabled() {
		return false;
	}

	public default boolean isPartialRefundEnabled() {
		return false;
	}

	public default boolean isPostProcessEnabled() {
		return false;
	}

	public default boolean isProcessPaymentEnabled() {
		return false;
	}

	public default boolean isProcessRecurringEnabled() {
		return false;
	}

	public default boolean isRefundEnabled() {
		return false;
	}

	public default boolean isVoidEnabled() {
		return false;
	}

	public default CommercePaymentResult partiallyRefundPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public default CommercePaymentResult postProcessPayment() throws Exception {
		throw new UnsupportedOperationException();
	}

	public default CommercePaymentResult processPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public default CommercePaymentResult processRecurringPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public default CommercePaymentResult refundPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public default boolean suspendRecurringPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public default CommercePaymentResult voidTransaction(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

}