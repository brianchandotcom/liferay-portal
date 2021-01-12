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

package com.liferay.commerce.discount.validator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

/**
 * @author Riccardo Alberti
 */
public class CommerceDiscountValidatorResult implements Serializable {

	public CommerceDiscountValidatorResult(boolean valid) {
		this(0, valid, StringPool.BLANK);
	}

	public CommerceDiscountValidatorResult(boolean valid, String message) {
		this(0, valid, message);
	}

	public CommerceDiscountValidatorResult(
		long commerceDiscountId, boolean valid, String message) {

		_commerceDiscountId = commerceDiscountId;
		_valid = valid;
		_message = message;
	}

	public long getCommerceDiscountId() {
		return _commerceDiscountId;
	}

	public String getMessage() {
		return _message;
	}

	public boolean hasMessageResult() {
		if (Validator.isNotNull(getMessage())) {
			return true;
		}

		return false;
	}

	public boolean isValid() {
		return _valid;
	}

	private long _commerceDiscountId;
	private String _message;
	private boolean _valid;

}