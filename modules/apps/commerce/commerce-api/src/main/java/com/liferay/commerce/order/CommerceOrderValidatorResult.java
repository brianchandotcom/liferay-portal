/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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