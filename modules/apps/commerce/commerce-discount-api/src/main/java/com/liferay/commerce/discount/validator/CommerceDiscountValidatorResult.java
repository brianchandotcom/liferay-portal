/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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