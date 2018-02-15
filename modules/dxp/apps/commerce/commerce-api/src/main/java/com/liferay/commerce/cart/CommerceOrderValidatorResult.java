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

package com.liferay.commerce.cart;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderValidatorResult {

	public CommerceOrderValidatorResult(boolean valid) {
		this(0, valid, StringPool.BLANK, StringPool.BLANK);
	}

	public CommerceOrderValidatorResult(boolean valid, String messageKey) {
		this(0, valid, messageKey, StringPool.BLANK);
	}

	public CommerceOrderValidatorResult(
		boolean valid, String messageKey, String argument) {

		this(0, valid, messageKey, argument);
	}

	public CommerceOrderValidatorResult(
		long commerceCartItemId, boolean valid, String messageKey) {

		this(commerceCartItemId, valid, messageKey, StringPool.BLANK);
	}

	public CommerceOrderValidatorResult(
		long commerceCartItemId, boolean valid, String messageKey,
		String argument) {

		_commerceCartItemId = commerceCartItemId;
		_valid = valid;
		_messageKey = messageKey;
		_argument = argument;
	}

	public String getArgument() {
		return _argument;
	}

	public long getCommerceCartItemId() {
		return _commerceCartItemId;
	}

	public String getMessage() {
		return _messageKey;
	}

	public boolean hasArgument() {
		if (Validator.isNotNull(getArgument())) {
			return true;
		}

		return false;
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

	private String _argument;
	private long _commerceCartItemId;
	private String _messageKey;
	private boolean _valid;

}