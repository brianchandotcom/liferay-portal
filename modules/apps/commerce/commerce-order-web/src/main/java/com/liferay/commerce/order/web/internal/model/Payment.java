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

package com.liferay.commerce.order.web.internal.model;

import com.liferay.commerce.frontend.model.LabelField;

/**
 * @author Alessio Antonio Rendina
 */
public class Payment {

	public Payment(
		long paymentId, LabelField type, String amount, String createDateString,
		String content) {

		_paymentId = paymentId;
		_type = type;
		_amount = amount;
		_createDateString = createDateString;
		_content = content;
	}

	public String getAmount() {
		return _amount;
	}

	public String getContent() {
		return _content;
	}

	public String getCreateDateString() {
		return _createDateString;
	}

	public long getPaymentId() {
		return _paymentId;
	}

	public LabelField getType() {
		return _type;
	}

	private final String _amount;
	private final String _content;
	private final String _createDateString;
	private final long _paymentId;
	private final LabelField _type;

}