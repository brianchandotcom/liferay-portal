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

package com.liferay.commerce.data.integration.apio.internal.form;

import com.liferay.apio.architect.form.Form;

/**
 * @author Rodrigo Guedes de Souza
 */
public class CommerceOrderUpdaterForm {

	public static Form<CommerceOrderUpdaterForm> buildForm(
		Form.Builder<CommerceOrderUpdaterForm> formBuilder) {

		return formBuilder.title(
			__ -> "The order updater form"
		).description(
			__ -> "This form can be used to update an order"
		).constructor(
			CommerceOrderUpdaterForm::new
		).addOptionalLong(
			"orderStatus", CommerceOrderUpdaterForm::_setOrderStatus
		).addOptionalLong(
			"paymentStatus", CommerceOrderUpdaterForm::_setPaymentStatus
		).build();
	}

	public Long getOrderStatus() {
		return _orderStatus;
	}

	public Long getPaymentStatus() {
		return _paymentStatus;
	}

	private void _setOrderStatus(Long orderStatus) {
		_orderStatus = orderStatus;
	}

	private void _setPaymentStatus(Long paymentStatus) {
		_paymentStatus = paymentStatus;
	}

	private Long _orderStatus;
	private Long _paymentStatus;

}