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

package com.liferay.commerce.exception;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public class CommerceOrderValidatorException extends PortalException {

	public CommerceOrderValidatorException() {
	}

	public CommerceOrderValidatorException(
		List<CommerceOrderValidatorResult> commerceOrderValidatorResults) {

		_commerceOrderValidatorResults = commerceOrderValidatorResults;
	}

	public CommerceOrderValidatorException(String msg) {
		super(msg);
	}

	public CommerceOrderValidatorException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CommerceOrderValidatorException(Throwable cause) {
		super(cause);
	}

	public List<CommerceOrderValidatorResult>
		getCommerceOrderValidatorResults() {

		return _commerceOrderValidatorResults;
	}

	private List<CommerceOrderValidatorResult> _commerceOrderValidatorResults;

}