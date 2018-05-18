/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
		long commerceOrderItemId, boolean valid, String messageKey) {

		this(commerceOrderItemId, valid, messageKey, StringPool.BLANK);
	}

	public CommerceOrderValidatorResult(
		long commerceOrderItemId, boolean valid, String messageKey,
		String argument) {

		_commerceOrderItemId = commerceOrderItemId;
		_valid = valid;
		_messageKey = messageKey;
		_argument = argument;
	}

	public String getArgument() {
		return _argument;
	}

	public long getCommerceOrderItemId() {
		return _commerceOrderItemId;
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
	private long _commerceOrderItemId;
	private String _messageKey;
	private boolean _valid;

}