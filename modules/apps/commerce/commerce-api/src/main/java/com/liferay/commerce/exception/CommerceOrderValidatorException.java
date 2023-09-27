/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.exception;

import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
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

	public CommerceOrderValidatorException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CommerceOrderValidatorException(Throwable throwable) {
		super(throwable);
	}

	public List<CommerceOrderValidatorResult>
		getCommerceOrderValidatorResults() {

		return _commerceOrderValidatorResults;
	}

	private List<CommerceOrderValidatorResult> _commerceOrderValidatorResults;

}