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

package com.liferay.commerce.order;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderValidatorResult implements Serializable {

	public CommerceOrderValidatorResult(boolean valid) {
		this(0, valid, StringPool.BLANK);
	}

	public CommerceOrderValidatorResult(
		boolean valid, String localizedMessage) {

		this(0, valid, localizedMessage);
	}

	public CommerceOrderValidatorResult(
		long commerceOrderItemId, boolean valid, String localizedMessage) {

		_commerceOrderItemId = commerceOrderItemId;
		_valid = valid;
		_localizedMessage = localizedMessage;
	}

	public long getCommerceOrderItemId() {
		return _commerceOrderItemId;
	}

	public String getLocalizedMessage() {
		return _localizedMessage;
	}

	public boolean hasMessageResult() {
		if (Validator.isNotNull(getLocalizedMessage())) {
			return true;
		}

		return false;
	}

	public boolean isValid() {
		return _valid;
	}

	private long _commerceOrderItemId;
	private String _localizedMessage;
	private boolean _valid;

}