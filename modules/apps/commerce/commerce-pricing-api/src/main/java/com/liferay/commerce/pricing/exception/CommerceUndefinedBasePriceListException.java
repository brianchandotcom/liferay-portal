/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Riccardo Alberti
 */
public class CommerceUndefinedBasePriceListException extends PortalException {

	public CommerceUndefinedBasePriceListException() {
	}

	public CommerceUndefinedBasePriceListException(String msg) {
		super(msg);
	}

	public CommerceUndefinedBasePriceListException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CommerceUndefinedBasePriceListException(Throwable throwable) {
		super(throwable);
	}

}