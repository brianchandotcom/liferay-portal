/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class CPInstancePriceException extends PortalException {

	public CPInstancePriceException() {
	}

	public CPInstancePriceException(String msg) {
		super(msg);
	}

	public CPInstancePriceException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CPInstancePriceException(Throwable throwable) {
		super(throwable);
	}

}