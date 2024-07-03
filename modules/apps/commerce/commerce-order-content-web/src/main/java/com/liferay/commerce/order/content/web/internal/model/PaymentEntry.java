/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.model;

import com.liferay.commerce.frontend.model.LabelField;

/**
 * @author Andrea Sbarra
 */
public class PaymentEntry {

	public PaymentEntry(
		String amount, String createDate, LabelField paymentStatus) {

		_amount = amount;
		_createDate = createDate;
		_paymentStatus = paymentStatus;
	}

	public String getAmount() {
		return _amount;
	}

	public String getCreateDate() {
		return _createDate;
	}

	public LabelField getPaymentStatus() {
		return _paymentStatus;
	}

	private final String _amount;
	private final String _createDate;
	private final LabelField _paymentStatus;

}