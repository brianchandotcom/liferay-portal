/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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