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