/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.internal.cart.model;

/**
 * @author Alessio Antonio Rendina
 */
public class Coupon {

	public Coupon(String couponCode) {
		_couponCode = couponCode;

		_success = true;
	}

	public Coupon(String[] errorMessages) {
		_errorMessages = errorMessages;

		_success = false;
	}

	public String getCouponCode() {
		return _couponCode;
	}

	public String[] getErrorMessages() {
		return _errorMessages;
	}

	public boolean getSuccess() {
		return _success;
	}

	public void setErrorMessages(String[] errorMessages) {
		_errorMessages = errorMessages;
	}

	public void setSuccess(boolean success) {
		_success = success;
	}

	private String _couponCode;
	private String[] _errorMessages;
	private boolean _success;

}