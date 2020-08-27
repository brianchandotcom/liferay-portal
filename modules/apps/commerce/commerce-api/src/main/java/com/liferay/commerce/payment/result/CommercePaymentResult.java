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

package com.liferay.commerce.payment.result;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luca Pellizzon
 */
public class CommercePaymentResult implements Serializable {

	public CommercePaymentResult(
		String authTransactionId, long commerceOrderId, int newPaymentStatus,
		boolean onlineRedirect, String redirectUrl, String refundId,
		List<String> resultMessages, boolean success) {

		_authTransactionId = authTransactionId;
		_commerceOrderId = commerceOrderId;
		_newPaymentStatus = newPaymentStatus;
		_onlineRedirect = onlineRedirect;
		_redirectUrl = redirectUrl;
		_refundId = refundId;

		_resultMessages.addAll(resultMessages);
		_success = success;
	}

	public String getAuthTransactionId() {
		return _authTransactionId;
	}

	public long getCommerceOrderId() {
		return _commerceOrderId;
	}

	public int getNewPaymentStatus() {
		return _newPaymentStatus;
	}

	public String getRedirectUrl() {
		return _redirectUrl;
	}

	public String getRefundId() {
		return _refundId;
	}

	public List<String> getResultMessages() {
		return _resultMessages;
	}

	public boolean isOnlineRedirect() {
		return _onlineRedirect;
	}

	public boolean isSuccess() {
		return _success;
	}

	private final String _authTransactionId;
	private final long _commerceOrderId;
	private final int _newPaymentStatus;
	private final boolean _onlineRedirect;
	private final String _redirectUrl;
	private final String _refundId;
	private final List<String> _resultMessages = new ArrayList<>();
	private final boolean _success;

}