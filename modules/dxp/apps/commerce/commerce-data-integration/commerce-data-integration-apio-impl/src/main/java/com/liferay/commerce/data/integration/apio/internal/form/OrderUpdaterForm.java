package com.liferay.commerce.data.integration.apio.internal.form;

import com.liferay.apio.architect.form.Form;

/**
 * @author Rodrigo Guedes de Souza
 */
public class OrderUpdaterForm {

	public static Form<OrderUpdaterForm> buildForm(
		Form.Builder<OrderUpdaterForm> formBuilder) {

		return formBuilder.title(
			__ -> "The order creator form"
		).description(
			__ -> "This form can be used to create a account"
		).constructor(
			OrderUpdaterForm::new
		).addOptionalLong(
			"orderStatus", OrderUpdaterForm::_setOrderStatus
		).addOptionalLong(
			"paymentStatus", OrderUpdaterForm::_setPaymentStatus
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