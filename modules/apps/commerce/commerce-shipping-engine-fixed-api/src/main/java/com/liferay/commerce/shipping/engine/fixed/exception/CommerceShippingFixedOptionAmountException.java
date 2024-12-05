/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionAmountException
	extends PortalException {

	public CommerceShippingFixedOptionAmountException() {
	}

	public CommerceShippingFixedOptionAmountException(String msg) {
		super(msg);
	}

	public CommerceShippingFixedOptionAmountException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CommerceShippingFixedOptionAmountException(Throwable throwable) {
		super(throwable);
	}

}